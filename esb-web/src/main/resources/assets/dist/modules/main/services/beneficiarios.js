angular.module('RamenApp').factory('BeneficiariosFac', function BeneficiariosFacFactory($http) {
        var urlBase = 'http://localhost:4000/v1/';
   return {
    findBeneficiarioByDni: function(paramDni) {
      return $http({method: 'GET', url: urlBase + 'beneficiario/dni/'+ paramDni});
    },
    findBeneficiarioByNroAfiliado: function(paramNro){
      return $http({method: 'GET', url: urlBase + 'beneficiario/numero/' + paramNro});
    }  
  };
});