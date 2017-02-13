<!DOCTYPE HTML>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${userModel.name} ${userModel.surname}</title>

    <jsp:include page="include/angular_common.jsp"/>
</head>

<body ng-app="BaseApp" ng-cloak>

<jsp:include page="include/toolbar.jsp">
    <jsp:param name="title" value="${userModel.name.concat(' ').concat(userModel.surname)}"/>
</jsp:include>

<md-content ng-controller="PageController as ctrl"
            layout-padding
            layout="column"
            style="background: #e8e8e8">
    <div layout="row"
         layout-wrap
         layout-align="center">

        <div flex="70"
             layout-padding
             style="height: 200px"
             layout="row"
             layout-align="start end"
             md-colors="{backgroundColor: 'default-primary-500'}">
            <h1 ng-if="isMyPage">{{user.name}} {{user.surname}} {{user.middleName}} (me)</h1>
            <h1 ng-if="!isMyPage">{{user.name}} {{user.surname}} {{user.middleName}}</h1>
        </div>

        <div md-whiteframe="16"
             flex="70">
            <section class="list-holder">
                <md-list>
                    <md-subheader class="md-primary">Main info</md-subheader>

                    <md-list-item class="md-2-line">
                        <md-icon md-svg-icon="account-card-details"></md-icon>

                        <div class="md-list-item-text">
                            <h3 ng-if="user.name">
                                {{user.name}} {{user.surname}} {{user.middleName}}
                            </h3>
                            <h3 ng-if="!user.name">- Not specified -</h3>

                            <p>Name</p>
                        </div>
                    </md-list-item>

                    <md-list-item class="md-2-line">
                        <md-icon md-svg-icon="email"></md-icon>

                        <div class="md-list-item-text">
                            <h3 ng-if="user.email">
                                {{user.email}}</h3>
                            <h3 ng-if="!user.email">- Not specified -</h3>

                            <p>Email</p>
                        </div>
                    </md-list-item>
                </md-list>
            </section>

            <section class="list-holder">
                <md-list flex>
                    <md-subheader class="md-primary">Additional info</md-subheader>

                    <md-list-item class="md-2-line">
                        <md-icon md-svg-icon="phone"></md-icon>

                        <div class="md-list-item-text">
                            <h3 ng-if="user.userData.phone">
                                {{user.userData.phone}}</h3>
                            <h3 ng-if="!user.userData.phone">- Not specified -</h3>

                            <p>Phone</p>
                        </div>
                    </md-list-item>
                </md-list>
            </section>
        </div>
    </div>

    <md-button class="md-fab fab"
               aria-label="Edit profile"
               ng-if="isMyPage"
               ng-click="ctrl.redirect('/user/me/edit/')">
        <md-tooltip md-direction="bottom" md-direction="left">Edit profile</md-tooltip>
        <md-icon md-svg-src="account-edit"></md-icon>
    </md-button>
</md-content>

<script type="text/javascript">
    app.controller('PageController', ['$scope', '$window', '$http', '$mdDialog', '$mdToast',
        function ($scope, $window, $http, $mdDialog, $mdToast) {
            $http.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
            $scope.user = ${user};
            $scope.isMyPage = ${isMyPage};

            this.redirect = function (url) {
                window.location.href = url;
            };
        }]);
</script>
</body>
</html>