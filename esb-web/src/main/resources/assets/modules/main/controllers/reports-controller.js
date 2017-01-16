angular.module('RamenApp').controller('ReportsController', function ($scope, $filter, $parse, TipoEspecialidadFac, TipoProfesionalFac, BeneficiariosFac, AdmissionFac, ReporteFac) {


    $scope.reporte = {};
    $scope.tipoProfesionales = [];
    $scope.tipoLineas = [];
    $scope.tipoEspecialidades = [];
    $scope.reporte.afiliadoDni = 0;
    $scope.reporte.afiliadoNumero = "0";
    $scope.reporte.nombreCompleto = "";
    $scope.gridReporte1 = {};
    $scope.gridReporte1.profesional = "";
    $scope.gridReporte1.especialidad = "";
    $scope.gridReporte2 = {};
    $scope.gridReporte3 = {};
    $scope.gridReporte4 = {};
    $scope.gridReporte4.profesional = "";
    $scope.gridReporte4.especialidad = "";
    $scope.cell = {
        consulta: "CIE10"
    };
    $scope.consultas = [{
            name: "Afiliado"
        }, {
            name: "Profesional"
        },
        {
            name: "Especialidad"
        },
        {
            name: "Fecha"
        }
    ];
    $scope.getConsultas = function () {
        return $scope.consultas;
    };

    TipoEspecialidadFac.all().success(function (tipoEspecialidadData) {
        $scope.tipoEspecialidades = tipoEspecialidadData;
        $scope.reporte.idEspecialidad = tipoEspecialidadData[0].id;
    });

    TipoProfesionalFac.all().success(function (tipoProfesionalData) {
        $scope.tipoProfesionales = tipoProfesionalData;
        $scope.reporte.idProfesional = tipoProfesionalData[0].id;
    });


    $scope.keyEnterNroAfiliado = function () {
        BeneficiariosFac.findBeneficiarioByNroAfiliado($scope.reporte.afiliadoNumero).success(function (beneficiarioData) {
            $scope.reporte.afiliadoDni = beneficiarioData.docId;
            $scope.reporte.nombreCompleto = beneficiarioData.benApe + " " + beneficiarioData.benNom;
        });
    };
    $scope.keyEnterDni = function () {
        BeneficiariosFac.findBeneficiarioByDni($scope.reporte.afiliadoDni).success(function (beneficiarioData) {
            $scope.reporte.afiliadoNumero = beneficiarioData.numero.trim();
            $scope.reporte.nombreCompleto = beneficiarioData.benApe + " " + beneficiarioData.benNom;

        });
    };
    $scope.cleanGrids = function () {
        $scope.gridReporte1 = {};
        $scope.gridReporte2 = {};
        $scope.gridReporte3 = {};
        $scope.gridReporte4 = {};
        $scope.reporte.afiliadoNumero = "";
        $scope.reporte.afiliadoDni = "";
        $scope.reporte.nombreCompleto = "";
        $scope.reporte.fechaDesde = "";
        $scope.reporte.fechaHasta = "";
        $scope.reporte.idProfesional = $scope.tipoProfesionales[0].id;
        $scope.reporte.idEspecialidad = $scope.tipoEspecialidades[0].id;
    };

    $scope.ExportGrid1 = function () {

        ReporteFac.exportExcel($scope.gridReporte1).success(function (data) {
            var blob = new Blob([data], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"});
            saveAs(blob, "Report.xls");

        });

    };
    $scope.ExportGrid2 = function () {
        ReporteFac.exportExcel($scope.gridReporte2).success(function (data) {
            var blob = new Blob([data], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"});
            saveAs(blob, "Report.xls");

        });

    };
    $scope.ExportGrid3 = function () {

        ReporteFac.exportExcel($scope.gridReporte3).success(function (data) {
            var blob = new Blob([data], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"});
            saveAs(blob, "Report.xls");

        });

    };
    $scope.ExportGrid4 = function () {
       
        ReporteFac.exportExcel($scope.gridReporte4).success(function (data) {
            var blob = new Blob([data], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"});
            saveAs(blob, "Report.xls");

        });

    };


    $scope.FillGrid1 = function () {
        ReporteFac.findByDni($scope.reporte.afiliadoDni).success(function (gridData) {
            $scope.gridReporte1 = gridData;
        });
    };
    $scope.FillGrid4 = function () {
        var fechaDesde=new Date();
        var fechaHasta=new Date();
        fechaDesde = $filter('date')($scope.reporte.fechaDesde, 'yyyy-MM-dd');
        fechaHasta= $filter('date')($scope.reporte.fechaHasta, 'yyyy-MM-dd');        
        
        ReporteFac.findByFecha(fechaDesde,fechaHasta).success(function (gridData) {
            $scope.gridReporte4 = gridData;

        });
        

    };
    $scope.FillGrid2 = function () {
        $scope.gridReporte2 = {};
        ReporteFac.findByProfesional($scope.reporte.idProfesional).success(function (gridData) {
            $scope.gridReporte2 = gridData;

        });

    };
    $scope.FillGrid3 = function () {
        $scope.gridReporte3 = {};
        ReporteFac.findByEspecialidad($scope.reporte.idEspecialidad).success(function (gridData) {
            $scope.gridReporte3 = gridData;

        });

    };


});


