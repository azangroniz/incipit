angular.module('RamenApp').factory('VistasServiciosFac', ['$http', function VistasServiciosFactory($http) {
                var urlBase = 'http://localhost:3300/v1/';
                return {
                    fillPacientesPendientes: function () {
                        return $http({method: 'GET', url: urlBase + 'vista/pendientes'});
                    },
                    fillInConsultaParameter: function (page, from, to, campos) {
                        return $http({method: 'GET', url: urlBase + 'vista/consulta/' + page + '/' + from + '/' + to + '/' + campos});
                    },
                    fillInPendiente: function (page) {
                        //$http.defaults.headers.common.Authorization = 'bearer ' + $cookies.token;
                        return $http({method: 'GET', url: urlBase + 'vista/pendientes/' + page});
                    }
                };
            }]);

