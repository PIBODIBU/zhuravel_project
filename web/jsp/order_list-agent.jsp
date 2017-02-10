<!DOCTYPE HTML>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${title}</title>

    <jsp:include page="include/angular_common.jsp"/>
</head>

<body ng-app="BaseApp" ng-cloak>

<jsp:include page="include/toolbar.jsp">
    <jsp:param name="title" value="${title}"/>
</jsp:include>

<md-content>
    <div ng-controller="CardController as ctrl">
        <div ng-if="orders.length == 0"
             layout="row"
             class="grey-bg"
             layout-align="center center">
            <p class="md-display-2"
               layout="column"
               layout-align="center">You have no orders</p>
        </div>

        <div layout="row"
             flex
             layout-wrap
             class="md-padding grey-bg">
            <md-content flex-gt-md="33"
                        flex-xs="100"
                        flex-gt-xs="50"
                        flex-xl="25"
                        layout="column"
                        ng-repeat="order in orders">

                <md-card flex md-whiteframe="4">
                    <md-card-header class="md-card-header"
                                    ng-click="ctrl.showUserInfoCard($event, $index)"
                                    md-whiteframe="{{height}}"
                                    ng-init="height = 1"
                                    ng-mouseenter="height = 6"
                                    ng-mouseleave="height = 1">
                        <md-card-avatar>
                            <md-icon md-svg-icon="account"></md-icon>
                        </md-card-avatar>
                        <md-card-header-text>
                            <span class="md-title">{{order.buyer.name}} {{order.buyer.surname}}</span>
                            <span class="md-subhead">{{order.buyer.email}}</span>
                        </md-card-header-text>
                    </md-card-header>

                    <md-divider></md-divider>

                    <md-card-title>
                        <md-card-title-text>
                            <span class="md-headline">{{order.buying_item_name}}
                                <span class="md-caption" ng-if="order.is_canceled">canceled</span>
                                <span class="md-caption" ng-if="order.is_archived">archived</span>
                                <span class="md-caption"
                                      ng-if="order.is_done && !order.is_canceled && !order.is_archived">done</span>
                            </span>

                            <span class="md-subhead" style="overflow: auto">{{order.buying_comment}}</span>
                        </md-card-title-text>
                    </md-card-title>

                    <md-divider></md-divider>

                    <%--Undefined order--%>
                    <md-card-actions layout="row" layout-align="end center"
                                     ng-if="!order.agent">
                        <md-button class="md-icon-button"
                                   aria-label="Settings"
                                   ng-click="ctrl.showOrderInfoCard($event, $index)">
                            <md-tooltip md-direction="bottom" md-direction="left">Order info</md-tooltip>
                            <md-icon md-svg-icon="information-outline"></md-icon>
                        </md-button>

                        <md-button class="md-icon-button"
                                   aria-label="Settings"
                                   ng-click="ctrl.becomeAgentOfOrder($index)">
                            <md-tooltip md-direction="bottom" md-direction="left">Become agent</md-tooltip>
                            <md-icon md-svg-icon="face"></md-icon>
                        </md-button>
                    </md-card-actions>

                    <%--Active order--%>
                    <md-card-actions layout="row" layout-align="end center"
                                     ng-if="order.agent && !order.is_done && !order.is_canceled && !order.is_archived">
                        <md-button class="md-icon-button"
                                   aria-label="Settings"
                                   ng-click="ctrl.showOrderInfoCard($event, $index)">
                            <md-tooltip md-direction="bottom" md-direction="left">Order info</md-tooltip>
                            <md-icon md-svg-icon="information-outline"></md-icon>
                        </md-button>

                        <md-button class="md-icon-button"
                                   aria-label="Settings"
                                   ng-click="ctrl.cancelOrder($index)">
                            <md-tooltip md-direction="bottom" md-direction="left">Cancel order</md-tooltip>
                            <md-icon md-svg-icon="close"></md-icon>
                        </md-button>

                        <md-button class="md-icon-button"
                                   ng-click="ctrl.completeOrder($event, $index)"
                                   aria-label="Done">
                            <md-tooltip md-direction="bottom" md-direction="left">Complete order</md-tooltip>
                            <md-icon md-svg-icon="check"></md-icon>
                        </md-button>
                    </md-card-actions>

                    <%--Done order--%>
                    <md-card-actions layout="row" layout-align="end center"
                                     ng-if="order.agent && order.is_done && !order.is_archived">
                        <md-button class="md-icon-button"
                                   aria-label="Settings"
                                   ng-click="ctrl.showOrderInfoCard($event, $index)">
                            <md-tooltip md-direction="bottom" md-direction="left">Order info</md-tooltip>
                            <md-icon md-svg-icon="information-outline"></md-icon>
                        </md-button>

                        <md-button class="md-icon-button" aria-label="Done"
                                   ng-click="ctrl.archiveOrder($index)">
                            <md-tooltip md-direction="bottom" md-direction="left">Archive order</md-tooltip>
                            <md-icon md-svg-icon="archive"></md-icon>
                        </md-button>
                    </md-card-actions>

                    <%--Canceled order--%>
                    <md-card-actions layout="row" layout-align="end center"
                                     ng-if="order.agent && order.is_canceled">
                        <md-button class="md-icon-button"
                                   aria-label="Settings"
                                   ng-click="ctrl.showOrderInfoCard($event, $index)">
                            <md-tooltip md-direction="bottom" md-direction="left">Order info</md-tooltip>
                            <md-icon md-svg-icon="information-outline"></md-icon>
                        </md-button>
                    </md-card-actions>

                    <%--Archived order--%>
                    <md-card-actions layout="row" layout-align="end center"
                                     ng-if="order.agent && order.is_archived">
                        <md-button class="md-icon-button"
                                   aria-label="Settings"
                                   ng-click="ctrl.showOrderInfoCard($event, $index)">
                            <md-tooltip md-direction="bottom" md-direction="left">Order info</md-tooltip>
                            <md-icon md-svg-icon="information-outline"></md-icon>
                        </md-button>
                    </md-card-actions>
                </md-card>

            </md-content>
        </div>
    </div>
</md-content>

<script type="text/javascript">
    app.controller('CardController', ['$scope', '$window', '$http', '$mdDialog', '$mdToast', function ($scope, $window, $http, $mdDialog, $mdToast) {
        $http.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
        $scope.orders = ${orders};

        this.archiveOrder = function (index) {
            $http({
                method: 'POST',
                url: '/api/order/archive',
                data: $.param({'order_id': $scope.orders[index]['id']}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(
                    function (response) {
                        console.log(response.data);
                        $scope.orders.splice(index, 1);

                        $mdToast.show($mdToast.simple()
                                .textContent('Archived successfully')
                                .position('bottom')
                                .hideDelay(3000));
                    },
                    function (response) {
                        console.log(response);
//                        deferred.resolve(err);

                        $mdToast.show($mdToast.simple()
                                .textContent('Error occurred. Try again later')
                                .position('bottom')
                                .hideDelay(3000));
                    }
            );
        };

        this.becomeAgentOfOrder = function (index) {
            $http({
                method: 'POST',
                url: '/api/order/become',
                data: $.param({'order_id': $scope.orders[index]['id']}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(
                    function (response) {
                        console.log(response.data);
                        $scope.orders.splice(index, 1);

                        $mdToast.show($mdToast.simple()
                                .textContent('Success! Now you are agent of this order')
                                .position('bottom')
                                .hideDelay(3000));
                    },
                    function (response) {
                        console.log(response);
                        $mdToast.show($mdToast.simple()
                                .textContent('Error occurred. Try again later')
                                .position('bottom')
                                .hideDelay(3000));
                    }
            );
        };

        this.cancelOrder = function (index) {
            $http({
                method: 'POST',
                url: '/api/order/cancel',
                data: $.param({'order_id': $scope.orders[index]['id']}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(
                    function (response) {
                        console.log(response.data);
                        $scope.orders.splice(index, 1);

                        $mdToast.show($mdToast.simple()
                                .textContent('Order is canceled')
                                .position('bottom')
                                .hideDelay(3000));
                    },
                    function (response) {
                        console.log(response);
                        $mdToast.show($mdToast.simple()
                                .textContent('Error occurred. Try again later')
                                .position('bottom')
                                .hideDelay(3000));
                    }
            );
        };

        this.completeOrder = function (ev, index) {
            $mdDialog.show({
                controller: DialogCompleteController,
                templateUrl: '/jsp/template/order_complete_dialog.tmpl.jsp',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
                resolve: {
                    order: function () {
                        return $scope.orders[index];
                    }
                }
            }).then(function (order) {
                $http({
                    method: 'POST',
                    url: '/api/order/complete',
                    data: $.param({'order_id': order.id, 'comment': order.sold_comment}),
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                }).then(function (response) {
                    $scope.orders.splice(index, 1);

                    $mdToast.show($mdToast.simple()
                            .textContent('Completed successfully')
                            .position('bottom')
                            .hideDelay(3000));
                }, function (response) {
                    $mdToast.show($mdToast.simple()
                            .textContent('Error occurred. Try again later')
                            .position('bottom')
                            .hideDelay(3000));
                })
            }, function () {
            });
        };

        this.showUserInfoCard = function (ev, index) {
            $mdDialog.show({
                controller: DialogController,
                templateUrl: '/jsp/template/order_user_info.tmpl.jsp',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
                resolve: {
                    order: function () {
                        return $scope.orders[index];
                    }
                }
            }).then(function (answer) {
            }, function () {
            });
        };

        this.showOrderInfoCard = function (ev, index) {
            $mdDialog.show({
                controller: DialogController,
                templateUrl: '/jsp/template/order_info.tmpl.jsp',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: false,
                fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
                resolve: {
                    order: function () {
                        return $scope.orders[index];
                    }
                }
            }).then(function (answer) {
                $scope.status = 'You said the information was "' + answer + '".';
            }, function () {
                $scope.status = 'You cancelled the dialog.';
            });
        };

        function DialogController($scope, $mdDialog, order) {
            $scope.order = order;

            $scope.cancel = function () {
                $mdDialog.cancel();
            };
        }

        function DialogCompleteController($scope, $mdDialog, order) {
            $scope.order = order;

            $scope.cancel = function () {
                $mdDialog.cancel();
            };

            $scope.done = function () {
                $mdDialog.hide(order);
            };
        }
    }]);
</script>

</body>
</html>