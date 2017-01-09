angular.module('RamenApp').factory('TipoEspecialidadFac', function TipoEspecialidadFactory($http) {
        var urlBase = 'http://localhost:3300/v1/';
   return {
    all: function() {
      return $http({method: 'GET', url: urlBase + 'especialidades'});
    },
    find: function(id){
      return $http({method: 'GET', url: urlBase + 'especialidad/' + id});
    }   
  };
});


