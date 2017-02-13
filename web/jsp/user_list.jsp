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

<md-content ng-controller="CardController as ctrl"
            layout-padding
            layout="column"
            class="grey-bg">

    <div layout="row"
         layout-align="center">
        <md-list flex="70"
                 flex-xs="100"
                 style="background: #ffffff"
                 md-whiteframe="8">
            <md-subheader class="md-primary">Users</md-subheader>

            <div ng-repeat="user in users">
                <md-list-item class="md-2-line"
                              ng-mouseenter="showControls=true"
                              ng-mouseleave="showControls=false"
                              ng-click="ctrl.goToUserProfile($event, $index)">
                    <md-icon md-svg-icon="account" class="md-avatar"></md-icon>

                    <div class="md-list-item-text"
                         layout="column">
                        <h3>{{user.name}} {{user.surname}} {{user.middleName}}</h3>
                        <p>{{user.email}}</p>
                    </div>

                    <md-button class="md-secondary md-icon-button"
                               ng-show="showControls"
                               ng-click="ctrl.deleteUser(ev, $index)"
                               aria-label="Done">
                        <md-tooltip md-direction="bottom" md-direction="left">Delete user</md-tooltip>
                        <md-icon md-svg-icon="delete"></md-icon>
                    </md-button>

                    <md-button class="md-secondary md-icon-button"
                               ng-show="showControls"
                               ng-click="ctrl.goToUserProfile($event, $index)"
                               aria-label="Done">
                        <md-tooltip md-direction="bottom" md-direction="left">Profile</md-tooltip>
                        <md-icon md-svg-icon="information-outline"></md-icon>
                    </md-button>
                </md-list-item>
            </div>
        </md-list>
    </div>

    <div layout="row"
         layout-align="center">
        <md-list flex="70"
                 flex-xs="100"
                 style="background: #ffffff"
                 md-whiteframe="8">
            <md-subheader class="md-primary">Agents</md-subheader>

            <md-list-item class="md-2-line"
                          ng-repeat="agent in agents"
                          ng-mouseenter="showControls=true"
                          ng-mouseleave="showControls=false"
                          ng-click="ctrl.goToAgentProfile($event, $index)">
                <md-icon md-svg-icon="account" class="md-avatar"></md-icon>

                <div class="md-list-item-text" layout="column">
                    <h3>{{agent.name}} {{agent.surname}} {{agent.middleName}}</h3>
                    <p>{{agent.email}}</p>
                </div>

                <md-button class="md-secondary md-icon-button"
                           ng-show="showControls"
                           ng-click="ctrl.deleteAgent(ev, $index)"
                           aria-label="Done">
                    <md-tooltip md-direction="bottom" md-direction="left">Delete agent</md-tooltip>
                    <md-icon md-svg-icon="delete"></md-icon>
                </md-button>

                <md-button class="md-secondary md-icon-button"
                           ng-show="showControls"
                           ng-click="ctrl.goToAgentProfile($event, $index)"
                           aria-label="Done">
                    <md-tooltip md-direction="bottom" md-direction="left">Profile</md-tooltip>
                    <md-icon md-svg-icon="information-outline"></md-icon>
                </md-button>
            </md-list-item>
        </md-list>
    </div>

    <md-button class="md-fab fab"
               aria-label="Add agent"
               ng-click="ctrl.addAgent($event)">
        <md-tooltip md-direction="left" md-direction="left">Add agent</md-tooltip>
        <md-icon md-svg-src="plus"></md-icon>
    </md-button>
</md-content>

<script type="text/javascript">
    app.controller('CardController', ['$scope', '$window', '$http', '$mdDialog', '$mdToast',
        function ($scope, $window, $http, $mdDialog, $mdToast) {
            $http.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
            $scope.users = ${users};
            $scope.agents = ${agents};

            this.redirect = function (location) {
                window.location.href = location;
            };

            this.goToUserProfile = function (ev, index) {
                window.location.href = "/user/" + $scope.users[index].id;
            };

            this.goToAgentProfile = function (ev, index) {
                window.location.href = "/user/" + $scope.agents[index].id;
            };

            this.deleteUser = function (ev, index) {
                $mdDialog.show($mdDialog.confirm()
                        .clickOutsideToClose(true)
                        .title('Attention')
                        .textContent('This action cannot be undone. Delete user?')
                        .ariaLabel('delete_dialog')
                        .ok('Yes')
                        .cancel('No')
                        .targetEvent(ev)
                ).then(function () {
                    $http({
                        method: 'DELETE',
                        url: '/api/users/' + $scope.users[index].id
                    }).then(function (response) {
                        $scope.users.splice(index, 1);

                        $mdToast.show($mdToast.simple()
                                .textContent('User deleted')
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

            this.deleteAgent = function (ev, index) {
                $mdDialog.show($mdDialog.confirm()
                        .clickOutsideToClose(true)
                        .title('Attention')
                        .textContent('This action cannot be undone. Delete agent?')
                        .ariaLabel('delete_dialog')
                        .ok('Yes')
                        .cancel('No')
                        .targetEvent(ev)
                ).then(function () {
                    $http({
                        method: 'DELETE',
                        url: '/api/users/',
                        data: $.param({'user_id': $scope.agents[index].id}),
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                    }).then(function (response) {
                        $scope.agents.splice(index, 1);

                        $mdToast.show($mdToast.simple()
                                .textContent('Agent deleted')
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

            this.addAgent = function (ev) {
                $mdDialog.show({
                    controller: DialogController,
                    templateUrl: '/jsp/template/new_agent.tmpl.jsp',
                    parent: angular.element(document.body),
                    targetEvent: ev,
                    clickOutsideToClose: true,
                    fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
                    resolve: {
                        agent: function () {
                            return {};
                        }
                    }
                }).then(function (agent) {
                    $http({
                        method: 'POST',
                        url: '/api/users/agents/add',
                        data: $.param({'data': JSON.stringify(agent)}),
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                    }).then(function (response) {
                        var agent = response.data;

                        if ($scope.agents[0] == undefined)
                            $scope.agents = [];

                        $scope.agents.push(agent);

                        $mdToast.show($mdToast.simple()
                                .textContent('Agent added')
                                .position('bottom')
                                .hideDelay(3000));
                    }, function (response) {
                        $mdToast.show($mdToast.simple()
                                .textContent('Error occurred. Try again later')
                                .position('bottom')
                                .hideDelay(3000));
                    });
                }, function () {
                });
            };

            function DialogController($scope, $mdDialog, agent) {
                $scope.agent = agent;

                $scope.cancel = function () {
                    $mdDialog.cancel();
                };

                $scope.done = function () {
                    $mdDialog.hide(agent);
                };
            }
        }]);
</script>
</body>
</html>