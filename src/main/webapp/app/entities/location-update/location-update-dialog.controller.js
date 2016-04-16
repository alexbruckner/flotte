(function() {
    'use strict';

    angular
        .module('flotteApp')
        .controller('LocationUpdateDialogController', LocationUpdateDialogController);

    LocationUpdateDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'LocationUpdate'];

    function LocationUpdateDialogController ($scope, $stateParams, $uibModalInstance, entity, LocationUpdate) {
        var vm = this;
        vm.locationUpdate = entity;
        vm.load = function(id) {
            LocationUpdate.get({id : id}, function(result) {
                vm.locationUpdate = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('flotteApp:locationUpdateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.locationUpdate.id !== null) {
                LocationUpdate.update(vm.locationUpdate, onSaveSuccess, onSaveError);
            } else {
                LocationUpdate.save(vm.locationUpdate, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.time = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
