angular.module('RamenApp').factory('Account', function($http) {
     var urlBase = 'http://localhost:3001/';
    return {
      getProfile: function() {
        return $http.get( urlBase +'api/me');
      },
      updateProfile: function(profileData) {
        return $http.put( urlBase +'api/me', profileData);
      }
    };
  });