'use strict';

describe('Controller Tests', function() {

    describe('LocationUpdate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLocationUpdate;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLocationUpdate = jasmine.createSpy('MockLocationUpdate');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'LocationUpdate': MockLocationUpdate
            };
            createController = function() {
                $injector.get('$controller')("LocationUpdateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'flotteApp:locationUpdateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
