angular.module('RamenApp')
        .factory('ProfesionalFac', ['$http', function ProfesionalFactory($http) {
                var urlBase = 'http://localhost:3300/v1/';
                return {
                    all: function () {
                        return $http({method: 'GET', url: urlBase + 'profesionales'});
                    },
                    find: function (id) {
                        return $http({method: 'GET', url: urlBase + 'profesionales/' + id});
                    },
                    create: function (Obj) {
                        return $http({method: 'PUT', url: urlBase + 'profesional', data: Obj});
                    },
                    update: function (Obj) {
                        return $http({method: 'POST', url: urlBase + 'profesional', data: Obj});
                    },
                    delete: function (id) {
                        return $http({method: 'DELETE', url: urlBase + 'profesional/' + id});
                    }
                };
            }]);



