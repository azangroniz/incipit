angular.module('RamenApp')
        .factory('PracticaFac', ['$http', function PracticaFactory($http) {
                var urlBase = 'http://localhost:3300/v1/';
                return {
                    all: function () {
                        return $http({method: 'GET', url: urlBase + 'practicas'});
                    },
                    find: function (id) {
                        return $http({method: 'GET', url: urlBase + 'practica/' + id});
                    },
                    create: function (Obj) {
                        return $http({method: 'PUT', url: urlBase + 'practica', data: Obj});
                    },
                    update: function (Obj) {
                        return $http({method: 'POST', url: urlBase + 'practica', data: Obj});
                    },
                    delete: function (id) {
                        return $http({method: 'DELETE', url: urlBase + 'practica/' + id});
                    },
                    createList: function (ListObj) {
                        return $http({method: 'PUT', url: urlBase + 'practicas', data: ListObj});
                    }
                     
                };
            }]);






