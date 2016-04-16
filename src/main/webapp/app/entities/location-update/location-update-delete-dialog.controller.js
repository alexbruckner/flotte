(function() {
    'use strict';

    angular
        .module('flotteApp')
        .controller('LocationUpdateDeleteController',LocationUpdateDeleteController);

    LocationUpdateDeleteController.$inject = ['$uibModalInstance', 'entity', 'LocationUpdate'];

    function LocationUpdateDeleteController($uibModalInstance, entity, LocationUpdate) {
        var vm = this;
        vm.locationUpdate = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            LocationUpdate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
