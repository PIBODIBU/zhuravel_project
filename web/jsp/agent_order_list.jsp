<!DOCTYPE HTML>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Order List</title>

    <jsp:include page="template/angular_common.jsp"/>
</head>

<body ng-app="MyApp" ng-cloak>

<md-toolbar ng-controller="ToolbarController as ctrl">
    <div class="md-toolbar-tools">
        <h3>
            <span>My orders</span>
        </h3>

        <span flex></span>

        <md-menu>
            <md-button aria-label="Open phone interactions menu" class="md-icon-button"
                       ng-click="ctrl.openMenu($mdOpenMenu, $event)">
                <md-icon md-menu-origin md-svg-icon="dots-vertical"></md-icon>
            </md-button>

            <md-menu-content width="4">
                <md-menu-item>
                    <md-button ng-click="ctrl.checkVoicemail()">
                        <md-icon md-svg-icon="tag-plus"></md-icon>
                        New order
                    </md-button>
                </md-menu-item>

                <md-menu-item>
                    <md-button ng-click="ctrl.redial($event)">
                        <md-icon md-svg-icon="archive" md-menu-align-target></md-icon>
                        Archived orders
                    </md-button>
                </md-menu-item>

                <md-menu-item>
                    <md-button ng-click="ctrl.checkVoicemail()">
                        <md-icon md-svg-icon="close-circle"></md-icon>
                        Closed orders
                    </md-button>
                </md-menu-item>

                <md-menu-divider></md-menu-divider>

                <md-menu-item>
                    <md-button ng-click="ctrl.toggleNotifications()">
                        <md-icon md-svg-icon="account"></md-icon>
                        My profile
                    </md-button>
                </md-menu-item>

                <md-menu-divider></md-menu-divider>

                <md-menu-item>
                    <md-button ng-click="ctrl.toggleNotifications()">
                        Logout
                    </md-button>
                </md-menu-item>
            </md-menu-content>
        </md-menu>
    </div>
</md-toolbar>

<md-content>
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
                                <span class="md-caption" ng-if="order.is_canceled == true">canceled</span>
                            <span class="md-caption"
                                  ng-if="order.is_done == true && order.is_canceled == false">done</span>
                            </span>

                            <span class="md-subhead"
                                  ng-if="order.is_done == true"
                                  style="max-height: 100px;overflow: auto">{{order.sold_comment}}</span>

                            <span class="md-subhead"
                                  ng-if="order.is_done == false"
                                  style="max-height: 100px;overflow: auto">{{order.buying_comment}}</span>
                        </md-card-title-text>
                    </md-card-title>

                    <md-card-actions layout="row" layout-align="end center" ng-if="order.is_done == false">
                        <md-button class="md-icon-button" aria-label="Settings">
                            <md-icon md-svg-icon="information-outline"></md-icon>
                        </md-button>
                        <md-button class="md-icon-button" aria-label="Done">
                            <md-icon md-svg-icon="check"></md-icon>
                        </md-button>
                    </md-card-actions>

                    <md-card-actions layout="row" layout-align="end center" ng-if="order.is_done == true">
                        <md-button class="md-icon-button" aria-label="Done">
                            <md-icon md-svg-icon="archive"></md-icon>
                        </md-button>
                    </md-card-actions>
                </md-card>
            </md-content>
        </div>
    </div>
</md-content>

<script type="text/javascript">
    var app = angular.module('MyApp',
            ['ngMaterial', 'ngMessages', 'material.svgAssetsCache']);

    app.config(function ($mdIconProvider, $mdThemingProvider) {
        $mdIconProvider.defaultIconSet('/resources/mdi.svg');

        $mdThemingProvider.theme('default')
                .primaryPalette('blue')
                .accentPalette('orange');

        $mdThemingProvider.enableBrowserColor({
            palette: 'accent', // Default is 'primary', any basic material palette and extended palettes are available
            hue: '200' // Default is '800'
        });
    });

    app.controller('CardController', ['$scope', '$window', function ($scope, $window) {
        $scope.orders = ${orders};

        this.redirectToGroupList = function (instituteId) {
            $window.location.href = '/structure/institutes/' + instituteId + '/groups';
        }
    }]);

    app.controller('ToolbarController', ['$scope', '$window', function ($scope, $window) {
        var originatorEv;

        this.openMenu = function ($mdOpenMenu, ev) {
            originatorEv = ev;
            $mdOpenMenu(ev);
        };
    }]);
</script>

</body>
</html>