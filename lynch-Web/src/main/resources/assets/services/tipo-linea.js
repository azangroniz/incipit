angular.module('RamenApp').factory('TipoLineaFac', function TipolineaFactory($http) {
        var urlBase = 'http://localhost:3003/v1/';
   return {
    all: function() {
      return $http({method: 'GET', url: urlBase + 'lineas'});
    },
    find: function(id){
      return $http({method: 'GET', url: urlBase + 'lineas/' + id});
    }   
  };
});


