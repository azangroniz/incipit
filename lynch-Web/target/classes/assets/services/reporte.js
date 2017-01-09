angular.module('RamenApp').factory('ReporteFac', ['$http', function ReporteFactory($http) {
                var urlBase = 'http://localhost:3300/v1/';
                return {
                    fillGrid: function () {
                        return $http({method: 'GET', url: urlBase + 'reporte'});
                    },
                    exportExcel: function (afilObj) {
                        return $http({method: 'POST', url: urlBase + 'reporte/exportar', data: afilObj,responseType: 'arraybuffer'});
                    },
                    findByDni: function (dni) {
                        return $http({method: 'GET', url: urlBase + 'reporte/dni/' + dni});
                    },
                     findByProfesional: function (idProfesional) {
                        return $http({method: 'GET', url: urlBase + 'reporte/profesional/' + idProfesional});
                    },
                     findByEspecialidad: function (idEspecialidad) {
                        return $http({method: 'GET', url: urlBase + 'reporte/especialidad/' + idEspecialidad});
                    },                    
                    findByFecha: function (fechaDesde,fechaHasta) {
                        return $http({method: 'GET', url: urlBase + 'reporte/fecha/' + fechaDesde+"/"+fechaHasta});
                    }
                };
            }]);

