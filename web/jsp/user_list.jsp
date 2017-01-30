<!DOCTYPE HTML>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${title}</title>

    <jsp:include page="include/angular_common.jsp"/>
</head>

<body ng-app="BaseApp" ng-cloak style="overflow: hidden;">

<jsp:include page="include/toolbar.jsp">
    <jsp:param name="title" value="${title}"/>
</jsp:include>

<md-content ng-controller="CardController as ctrl"
            layout="column"
            layout-fill
            ng-cloak
            style="padding-bottom: 60px; !important;">

    <section>
        <md-list>
            <md-subheader class="md-primary">Users</md-subheader>

            <md-list-item class="md-3-line" ng-repeat="user in users" ng-click="ctrl.showUserInfoCard($event, $index)">
                <md-icon md-svg-icon="account" class="md-avatar"></md-icon>

                <div class="md-list-item-text" layout="column">
                    <h3>{{user.name}} {{user.surname}}</h3>
                    <h4>{{user.middleName}}</h4>
                    <p>{{user.email}}</p>
                </div>
            </md-list-item>
        </md-list>
    </section>

    <section>
        <md-list>
            <md-subheader class="md-primary">Agents</md-subheader>

            <md-list-item class="md-3-line"
                          ng-repeat="agent in agents"
                          ng-click="ctrl.showAgentInfoCard($event, $index)">
                <md-icon md-svg-icon="account" class="md-avatar"></md-icon>

                <div class="md-list-item-text" layout="column">
                    <h3>{{agent.name}} {{agent.surname}}</h3>
                    <h4>{{agent.middleName}}</h4>
                    <p>{{agent.email}}</p>
                </div>
            </md-list-item>
        </md-list>
    </section>
</md-content>

<script type="text/javascript">
    app.controller('CardController', ['$scope', '$window', '$http', '$mdDialog', '$mdToast', function ($scope, $window, $http, $mdDialog, $mdToast) {
        $http.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
        $scope.users = ${users};
        $scope.agents = ${agents};

        this.showUserInfoCard = function (ev, index) {
            window.location.href = "/user/" + $scope.users[index].id;
            /*$mdDialog.show({
             controller: DialogController,
             templateUrl: '/jsp/template/user_info.tmpl.jsp',
             parent: angular.element(document.body),
             targetEvent: ev,
             clickOutsideToClose: true,
             fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
             resolve: {
             user: function () {
             return $scope.users[index];
             }
             }
             }).then(function (answer) {
             $scope.status = 'You said the information was "' + answer + '".';
             }, function () {
             $scope.status = 'You cancelled the dialog.';
             });*/
        };

        this.showAgentInfoCard = function (ev, index) {
            window.location.href = "/user/" + $scope.users[index].id;

            /*$mdDialog.show({
             controller: DialogController,
             templateUrl: '/jsp/template/user_info.tmpl.jsp',
             parent: angular.element(document.body),
             targetEvent: ev,
             clickOutsideToClose: true,
             fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
             resolve: {
             user: function () {
             return $scope.agents[index];
             }
             }
             }).then(function (answer) {
             $scope.status = 'You said the information was "' + answer + '".';
             }, function () {
             $scope.status = 'You cancelled the dialog.';
             });*/
        };

        function DialogController($scope, $mdDialog, user) {
            $scope.user = user;

            $scope.cancel = function () {
                $mdDialog.cancel();
            };
        }
    }]);
</script>
</body>
</html>