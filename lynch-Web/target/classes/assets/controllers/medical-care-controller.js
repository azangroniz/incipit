angular.module('RamenApp').controller('MedicalCareController', function ($scope, $filter, $state, $parse, TipoCie10Fac, TipoDsmivFac, TipoNomencladorFac, VistasServiciosFac, AdmissionFac, PracticaFac) {


    $scope.items = [];
    $scope.items.data = [{
            id: '', codigo: '', desc: ''}];

    $scope.listaPractica = [];
    $scope.listaPractica.data = [{
            idVisita: '', idCIE10: '', idDSMIV: '', idNomenclador: ''
        }];

    $scope.cell = {
        diagnostico: "CIE10"
    };
    $scope.diagnosticos = [{
            name: "CIE10"
        }, {
            name: "DSMIV"
        }];
    $scope.getDiagnosticos = function () {
        return $scope.diagnosticos;
    };
    $scope.atencionFormModel = {};
    $scope.diagnostico = {};
    $scope.admission = {};
    $scope.tiposCie10 = [];
    $scope.tiposDsmiv = [];
    $scope.tiposNomenclador = [];
    $scope.gridPacientesPendientes = {};
    $scope.viewForm = true;
    $scope.admission.descripcionNomenclador = "";
    $scope.admission.codigoNomenclador = "";

    VistasServiciosFac.fillPacientesPendientes().success(function (gridData) {
        $scope.gridPacientesPendientes = gridData;
    });

    TipoCie10Fac.all().success(function (tipoCie10Data) {
        $scope.tiposCie10 = tipoCie10Data;
//        $scope.admission.idCIE10 = tipoCie10Data[0].id;
    });
    TipoDsmivFac.all().success(function (tipoDsmivData) {
        $scope.tiposDsmiv = tipoDsmivData;
//        $scope.admission.idDSMIV = tipoDsmivData[0].id;
    });
    TipoNomencladorFac.all().success(function (tipoNomecladorData) {
        $scope.tiposNomenclador = tipoNomecladorData;
//        $scope.admission.idNomenclador = tipoNomecladorData[0].id;
    });



    $scope.selectedRow = null; // initialize our variable to null
    $scope.setClickedRow = function (index, grid) {

        $scope.selectedRow = index;
        $scope.admission.afiliadoNombre = grid.afiliadoNombre;
        $scope.admission.afiliadoApellido = grid.afiliadoApellido;
        $scope.admission.nombreCompleto = grid.afiliadoApellido + " " + grid.afiliadoNombre;
        $scope.admission.afiliadoNumero = grid.afiliadoNumero;
        $scope.admission.id = grid.id;
        $scope.viewForm = false;

    };

    $scope.UpdateAdmision = function () {
        $scope.errors = null;
        $scope.updating = true;

        AdmissionFac.find($scope.admission.id).success(function (admissionData) {
            $scope.diagnostico = admissionData;
            $scope.diagnostico.estado = 1;
           //AREGLAR LA FECHA QUE RESTA DE A UNO DIA PO UPDAE
            AdmissionFac.update($scope.diagnostico).success(function (diagnosticoData) {
                PracticaFac.createList($scope.listaPractica.data).success(function (practicalistData) {
                    $scope.admission = {};
                    $scope.diagnostico = {};
                    $scope.listaPractica.data.length = 0;
                    $scope.items.data.length = 0;
                    VistasServiciosFac.fillPacientesPendientes().success(function (gridData) {
                        $scope.gridPacientesPendientes = {};
                        $scope.gridPacientesPendientes = gridData;
                    });
                }).catch(function (practicalistData) {
                    $scope.errors = [practicalistData.data.error];
                });
            }).catch(function (diagnosticoData) {
                $scope.errors = [diagnosticoData.data.error];
            });
        }).catch(function (admissionData) {
            $scope.errors = [admissionData.data.error];
        });


    };

    $scope.setNomencladorCode = function (nomenclador) {
        $scope.admission.codigoNomenclador = nomenclador.codigo;
        $scope.admission.idNomenclador = nomenclador.id;

    };
    $scope.setCIE10Id = function (cie10) {
        $scope.admission.idCIE10 = cie10.id;
        $scope.admission.idDSMIV = 0;
    };
    $scope.setDSMIVId = function (dsmiv) {
        $scope.admission.idDSMIV = dsmiv.id;
        $scope.admission.idCIE10 = 0;
    };

    $scope.keyCodigoNomenclador = function () {

        angular.forEach($scope.tiposNomenclador, function (value, key) {
            if (value.codigo == $scope.admission.codigoNomenclador) {
                $scope.admission.descripcionNomenclador = value.descripcion;
                $scope.admission.idNomenclador = value.id;
            }
        });
    };

    $scope.deleteItem = function (index) {
        $scope.items.data.splice(index, 1);
        $scope.listaPractica.data.splice(index, 1);
    };
    $scope.items.data.length = 0;
    $scope.listaPractica.data.length = 0;

    $scope.addItem = function (index) {
        var valido = true;
        angular.forEach($scope.listaPractica.data, function (value, key) {
            if (value.idNomenclador == $scope.admission.idNomenclador) {
                valido = false;
            }
        });
        if (($scope.listaPractica.data.length < 4) && (valido)) {
            $scope.items.data.push({
                id: $scope.items.data.length + 1,
                codigo: $scope.admission.codigoNomenclador,
                desc: $scope.admission.descripcionNomenclador
            });

            $scope.listaPractica.data.push({
                idVisita: $scope.admission.id,
                idCIE10: $scope.admission.idCIE10,
                idDSMIV: $scope.admission.idDSMIV,
                idNomenclador: $scope.admission.idNomenclador
            });
            $scope.admission.codigoNomenclador = "";
            $scope.admission.descripcionNomenclador = "";

        }


    };



});