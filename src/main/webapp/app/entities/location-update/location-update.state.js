(function() {
    'use strict';

    angular
        .module('flotteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('location-update', {
            parent: 'entity',
            url: '/location-update?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'flotteApp.locationUpdate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/location-update/location-updates.html',
                    controller: 'LocationUpdateController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('locationUpdate');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('location-update-detail', {
            parent: 'entity',
            url: '/location-update/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'flotteApp.locationUpdate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/location-update/location-update-detail.html',
                    controller: 'LocationUpdateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('locationUpdate');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LocationUpdate', function($stateParams, LocationUpdate) {
                    return LocationUpdate.get({id : $stateParams.id});
                }]
            }
        })
        .state('location-update.new', {
            parent: 'location-update',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/location-update/location-update-dialog.html',
                    controller: 'LocationUpdateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                source: null,
                                time: null,
                                longitude: null,
                                lattitude: null,
                                comment: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('location-update', null, { reload: true });
                }, function() {
                    $state.go('location-update');
                });
            }]
        })
        .state('location-update.edit', {
            parent: 'location-update',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/location-update/location-update-dialog.html',
                    controller: 'LocationUpdateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LocationUpdate', function(LocationUpdate) {
                            return LocationUpdate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('location-update', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('location-update.delete', {
            parent: 'location-update',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/location-update/location-update-delete-dialog.html',
                    controller: 'LocationUpdateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LocationUpdate', function(LocationUpdate) {
                            return LocationUpdate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('location-update', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
