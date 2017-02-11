'use strict';

var app = angular.module('BaseApp',
    ['ngMaterial', 'ngMessages', 'ngFileUpload', 'material.svgAssetsCache']);

app.config(function ($mdIconProvider, $mdThemingProvider) {
    $mdIconProvider.defaultIconSet('/resources/mdi.svg');

    $mdThemingProvider.theme('default')
        .primaryPalette('blue')
        .accentPalette('orange');

    $mdThemingProvider.enableBrowserColor({
        palette: 'primary', // Default is 'primary', any basic material palette and extended palettes are available
        hue: '800' // Default is '800'
    });
});

app.controller('ToolbarController', ['$rootScope', '$scope', '$window', '$mdDialog', '$mdToast', '$http',
    function ($rootScope, $scope, $window, $mdDialog, $mdToast, $http) {
        var originatorEv;

        this.openMenu = function ($mdOpenMenu, ev) {
            originatorEv = ev;
            $mdOpenMenu(ev);
        };

        this.redirect = function (url) {
            window.location.href = url;
        };

        this.openNewOrderDialog = function (ev) {
            $mdDialog.show({
                controller: DialogNewOrderController,
                templateUrl: '/jsp/template/order_new_dialog.tmpl.jsp',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: false,
                fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
                resolve: {
                    order: function () {
                        return {};
                    }
                }
            }).then(function (order) {
                $http({
                    method: 'POST',
                    url: '/api/order/new',
                    data: $.param({'comment': order.buying_comment, 'name': order.buying_item_name}),
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                }).then(function (response) {
                    if ($rootScope.onOrderAddingSuccess != undefined)
                        $rootScope.onOrderAddingSuccess(response);

                    $mdToast.show($mdToast.simple()
                        .textContent('Order created successfully')
                        .position('bottom')
                        .hideDelay(3000));
                }, function (response) {
                    $mdToast.show($mdToast.simple()
                        .textContent('Error occurred. Try again later')
                        .position('bottom')
                        .hideDelay(3000));
                });
            }, function () {
                if ($rootScope.onOrderAddingFailure != undefined)
                    $rootScope.onOrderAddingFailure();
            });
        };

        function DialogNewOrderController($scope, $mdDialog, order) {
            $scope.order = order;

            $scope.cancel = function () {
                $mdDialog.cancel();
            };

            $scope.done = function () {
                $mdDialog.hide(order);
            };
        }
    }
]);

app.directive('chooseFile', function () {
    return {
        link: function (scope, elem, attrs) {
            var button = elem.find('button');
            var input = angular.element(elem[0].querySelector('input#fileInput'));

            button.bind('click', function () {
                input[0].click();
            });

            scope.onFilesChanged = function ($files, $file, $newFiles, $duplicateFiles, $invalidFiles, $event) {
                scope.fileName = "";

                for (var i = 0; i < scope.files.length; i++) {
                    scope.fileName += scope.files[i].name + ", ";
                }
            };
        }
    };
});

app.filter('firstLetter', function () {
    return function (input) {
        return (input.charAt(0));
    }
});