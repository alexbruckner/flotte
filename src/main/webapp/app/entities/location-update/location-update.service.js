(function() {
    'use strict';
    angular
        .module('flotteApp')
        .factory('LocationUpdate', LocationUpdate);

    LocationUpdate.$inject = ['$resource', 'DateUtils'];

    function LocationUpdate ($resource, DateUtils) {
        var resourceUrl =  'api/location-updates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.time = DateUtils.convertDateTimeFromServer(data.time);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
