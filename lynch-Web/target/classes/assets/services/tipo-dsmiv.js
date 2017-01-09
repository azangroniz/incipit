angular.module('RamenApp').factory('TipoDsmivFac', function TipoDsmivFactory($http) {
        var urlBase = 'http://localhost:3003/v1/';
   return {
    all: function() {
      return $http({method: 'GET', url: urlBase + 'dsmivs'});
    },
    find: function(id){
      return $http({method: 'GET', url: urlBase + 'dsmiv/' + id});
    }   
  };
});

