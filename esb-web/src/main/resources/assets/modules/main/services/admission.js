angular.module('RamenApp')
        .factory('AdmissionFac', ['$http', function AdmissionFactory($http) {
                var urlBase = 'http://localhost:3300/v1/';
                return {
                    all: function () {
                        return $http({method: 'GET', url: urlBase + 'visitas'});
                    },
                    find: function (id) {
                        return $http({method: 'GET', url: urlBase + 'visita/' + id});
                    },
                    create: function (Obj) {
                        return $http({method: 'PUT', url: urlBase + 'visita', data: Obj});
                    },
                    update: function (Obj) {
                        return $http({method: 'POST', url: urlBase + 'visita', data: Obj});
                    },
                    delete: function (id) {
                        return $http({method: 'DELETE', url: urlBase + 'visita/' + id});
                    },
                     findByDni: function (dni) {
                        return $http({method: 'GET', url: urlBase + 'visitas/dni/' + dni});
                    },
                     findByProfesional: function (idProfesional) {
                        return $http({method: 'GET', url: urlBase + 'visitas/profesional/' + idProfesional});
                    },
                     findByEspecialidad: function (idEspecialidad) {
                        return $http({method: 'GET', url: urlBase + 'visitas/especialidad/' + idEspecialidad});
                    },                    
                    findByFecha: function (fechaDesde,fechaHasta) {
                        return $http({method: 'GET', url: urlBase + 'visitas/fecha/' + fechaDesde+"/"+fechaHasta});
                    }
                };
            }]);



