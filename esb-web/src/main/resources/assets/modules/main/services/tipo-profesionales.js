angular.module('RamenApp').factory('TipoProfesionalFac', function TipoProfesionalFactory($http) {
        var urlBase = 'http://localhost:3300/v1/';
   return {
    all: function() {
      return $http({method: 'GET', url: urlBase + 'profesionales'});
    },
    find: function(id){
      return $http({method: 'GET', url: urlBase + 'profesional/' + id});
    }   
  };
});


