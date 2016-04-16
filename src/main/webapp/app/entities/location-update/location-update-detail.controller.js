(function() {
    'use strict';

    angular
        .module('flotteApp')
        .controller('LocationUpdateDetailController', LocationUpdateDetailController);

    LocationUpdateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'LocationUpdate'];

    function LocationUpdateDetailController($scope, $rootScope, $stateParams, entity, LocationUpdate) {
        var vm = this;
        vm.locationUpdate = entity;
        vm.load = function (id) {
            LocationUpdate.get({id: id}, function(result) {
                vm.locationUpdate = result;
            });
        };
        var unsubscribe = $rootScope.$on('flotteApp:locationUpdateUpdate', function(event, result) {
            vm.locationUpdate = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
