angular.module('RamenApp').controller('AdmissionController', function ($scope, $filter, $state, TipoEspecialidadFac, TipoProfesionalFac, TipoLineaFac, BeneficiariosFac, AdmissionFac,VistasServiciosFac) {


    $scope.admissionForm  ={};
    $scope.admission = {};
    $scope.admission1 = {};
    $scope.admission.afiliadoDni ;
    $scope.admission.afiliadoNumero = "";
    $scope.admission.afiliadoNombre = "";
    $scope.admission.afiliadoApellido = "";
    $scope.admission.idLinea = 0;
    $scope.admission.fecha = new Date();
    $scope.admission.estado = 0;
    $scope.tipoProfesionales = [];
    $scope.tipoLineas = [];
    $scope.Linea;
    $scope.tipoEspecialidades = [];
    $scope.gridAdmission={};
    

    TipoEspecialidadFac.all().success(function (tipoEspecialidadData) {
        $scope.tipoEspecialidades = tipoEspecialidadData;
        $scope.admission.idEspecialidad = tipoEspecialidadData[0].id;
    });
    TipoLineaFac.all().success(function (tipoLineaData) {
        $scope.tipoLineas = tipoLineaData;        
    });
    TipoProfesionalFac.all().success(function (tipoProfesionalData) {
        $scope.tipoProfesionales = tipoProfesionalData;
        
        $scope.admission.idProfesional = tipoProfesionalData[0].id;
    });

    VistasServiciosFac.fillPacientesPendientes().success(function (gridData) {
            $scope.gridAdmission = gridData;

        });
    
    $scope.SaveAdmision = function () {
        $scope.errors = null;
        $scope.updating = true;

        AdmissionFac.update($scope.admission).success(function (admissionData) {
//                    $scope.profesional = profesionalData;
//                    $scope.profesional.id = profesionalData;
        }).catch(function (admissionData) {
            $scope.errors = [admissionData.data.error];
        }).finally(function () {

            $scope.admission = {};
            $scope.admission1 = "";
            
            $scope.FillGrid2();

        });
    };


    $scope.keyEnterNroAfiliado = function () {
        BeneficiariosFac.findBeneficiarioByNroAfiliado($scope.admission.afiliadoNumero).success(function (beneficiarioData) {
            $scope.admission.afiliadoDni = beneficiarioData.docId;
            $scope.admission1.nombreCompleto = beneficiarioData.benApe + " " + beneficiarioData.benNom;
            $scope.admission.afiliadoNombre = beneficiarioData.benNom;
            $scope.admission.afiliadoApellido = beneficiarioData.benApe;
        });
    };
    $scope.keyEnterDni = function () {
        BeneficiariosFac.findBeneficiarioByDni($scope.admission.afiliadoDni).success(function (beneficiarioData) {
            $scope.admission.afiliadoNumero = beneficiarioData.numero.trim();
            $scope.admission1.nombreCompleto = beneficiarioData.benApe + " " + beneficiarioData.benNom;
            $scope.admission.afiliadoNombre = beneficiarioData.benNom;
            $scope.admission.afiliadoApellido = beneficiarioData.benApe;
        });
    };


    $scope.FillGrid2 = function () {
        VistasServiciosFac.fillPacientesPendientes().success(function (gridData) {
            $scope.gridAdmission = gridData;

        });

    };
});