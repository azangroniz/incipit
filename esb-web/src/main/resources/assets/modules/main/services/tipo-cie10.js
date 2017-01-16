angular.module('RamenApp').factory('TipoCie10Fac', function TipoCie10Factory($http) {
        var urlBase = 'http://localhost:3003/v1/';
   return {
    all: function() {
      return $http({method: 'GET', url: urlBase + 'cie10s'});
    },
    find: function(id){
      return $http({method: 'GET', url: urlBase + 'cie10/' + id});
    }   
  };
});

