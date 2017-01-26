<!DOCTYPE HTML>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Order List</title>

    <jsp:include page="include/angular_common.jsp"/>
</head>

<body ng-app="BaseApp" ng-cloak>

<jsp:include page="include/toolbar.jsp">
    <jsp:param name="user" value="${sessionScope.user}"/>
</jsp:include>

<div ng-controller="CardController as controller">
    <div layout="row" flex layout-wrap class='md-padding'>
        <md-content flex-gt-md="33"
                    flex-xs="100"
                    flex-gt-xs="50"
                    flex-xl="25"
                    layout="column"
                    ng-repeat="order in orders">
            <md-card flex md-whiteframe="4">
                <md-card-title>
                    <md-card-title-text>
                            <span class="md-headline">{{order.buying_item_name}}
                                <span class="md-caption" ng-if="order.is_canceled">canceled</span>
                                <span class="md-caption"
                                      ng-if="order.is_done && !order.is_canceled">done</span>
                            </span>

                        <span class="md-subhead" style="overflow: auto">{{order.buying_comment}}</span>
                    </md-card-title-text>
                </md-card-title>

                <%--Active order--%>
                <md-card-actions layout="row"
                                 layout-align="end center"
                                 ng-if="!order.is_done && !order.is_canceled && !order.is_archived">
                    <md-button class="md-icon-button" aria-label="Done">
                        <md-icon md-svg-icon="refresh"></md-icon>
                    </md-button>
                </md-card-actions>

                <%--Done order--%>
                <md-card-actions layout="row" layout-align="end center" ng-if="order.is_done && !order.is_archived">
                    <md-button class="md-icon-button"
                               aria-label="Settings"
                               ng-click="ctrl.showOrderInfoCard($event, $index)">
                        <md-icon md-svg-icon="information-outline"></md-icon>
                    </md-button>

                    <md-button class="md-icon-button" aria-label="Done"
                               ng-click="ctrl.archiveOrder($index)">
                        <md-icon md-svg-icon="archive"></md-icon>
                    </md-button>
                </md-card-actions>

                <%--Canceled order--%>
                <md-card-actions layout="row" layout-align="end center" ng-if="order.is_canceled">
                    <md-button class="md-icon-button"
                               aria-label="Settings"
                               ng-click="ctrl.showOrderInfoCard($event, $index)">
                        <md-icon md-svg-icon="information-outline"></md-icon>
                    </md-button>
                </md-card-actions>

                <%--Archived order--%>
                <md-card-actions layout="row" layout-align="end center" ng-if="order.is_archived">
                    <md-button class="md-icon-button"
                               aria-label="Settings"
                               ng-click="ctrl.showOrderInfoCard($event, $index)">
                        <md-icon md-svg-icon="information-outline"></md-icon>
                    </md-button>
                </md-card-actions>
            </md-card>
        </md-content>
    </div>
</div>

<script type="text/javascript">
    app.controller('CardController', ['$scope', '$window', '$mdDialog', '$mdToast', function ($scope, $window, $mdDialog, $mdToast) {
        $scope.orders = ${orders};

        this.showUserInfoCard = function (ev, index) {
            $mdDialog.show({
                controller: DialogController,
                templateUrl: '/jsp/template/user_info.tmpl.jsp',
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
                $scope.status = 'You said the information was "' + answer + '".';
            }, function () {
                $scope.status = 'You cancelled the dialog.';
            });
        };

        this.showOrderInfoCard = function (ev, index) {
            $mdDialog.show({
                controller: DialogController,
                templateUrl: '/jsp/template/order_info.tmpl.jsp',
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
                $scope.status = 'You said the information was "' + answer + '".';
            }, function () {
                $scope.status = 'You cancelled the dialog.';
            });
        };

        function DialogController($scope, $mdDialog, order) {
            $scope.order = order;

            $scope.hide = function () {
                $mdDialog.hide();
            };

            $scope.cancel = function () {
                $mdDialog.cancel();
            };

            $scope.answer = function (answer) {
                $mdDialog.hide(answer);
            };
        }
    }]);
</script>

</body>
</html>
