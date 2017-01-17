angular.module('RamenApp').factory('TipoNomencladorFac', function TipoNomencladorFactory($http) {
        var urlBase = 'http://localhost:3003/v1/';
   return {
    all: function() {
      return $http({method: 'GET', url: urlBase + 'nomencladores'});
    },
    find: function(id){
      return $http({method: 'GET', url: urlBase + 'nomenclador/' + id});
    }   
  };
});

