angular.module('RamenApp').controller('ProfesionalController', function ($scope, $filter, $state,TipoEspecialidadFac,ProfesionalFac) {

    $scope.profesional = {};
    $scope.tipoProfesionales = [];    
    $scope.tipoEspecialidades = [];
    
 TipoEspecialidadFac.all().success(function (tipoEspecialidadData) {
        $scope.tipoEspecialidades = tipoEspecialidadData;
        //$scope.profesional.idListadoEspecialidades = tipoEspecialidadData[0].id;
    });


 $scope.SaveProfesional = function () {
        $scope.errors = null;
        $scope.updating = true;        
        ProfesionalFac.update($scope.profesional).success(function (profesionalData) {
//                    $scope.profesional = profesionalData;
//                    $scope.profesional.id = profesionalData;
                }).catch(function (profesionalData) {
                    $scope.errors = [profesionalData.data.error];
                }).finally(function () {
                    $scope.profesional ={};
                    
                });

    };   

});


