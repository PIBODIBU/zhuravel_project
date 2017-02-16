<%@ page import="main.controller.api.SettingsAPIController" %>
<%@ page import="main.dao.impl.SettingDAOImpl" %>
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

<div ng-controller="PageController as ctrl"
     layout-padding
     layout-align="center center"
     layout="row">
    <md-content flex="60"
                md-whiteframe="16">
        <md-list>
            <md-subheader class="md-no-sticky">Service</md-subheader>
            <md-list-item>
                <md-chips ng-model="ctrl.settings.serviceEmails"
                          md-removable="true"
                          md-enable-chip-edit="true"
                          flex
                          placeholder="Enter service emails...">
                </md-chips>

                <md-icon class="md-secondary"
                         ng-click="ctrl.setEmails()"
                         md-svg-src="check"></md-icon>
            </md-list-item>
        </md-list>
    </md-content>
</div>

<script type="text/javascript">
    app.controller('PageController', ['$scope', '$window', '$http', '$mdDialog', '$mdToast',
        function ($scope, $window, $http, $mdDialog, $mdToast) {
            this.settings = [];
            this.settings.serviceEmails = ${serviceEmails};

            this.setEmails = function () {
                var name = '<%=SettingDAOImpl.SETTING_SERVICE_EMAILS%>';
                var emails = '';

                this.settings.serviceEmails.forEach(function (email, i, array) {
                    console.log(email);
                    emails += email;
                    emails += ','
                });

                this.setSetting(name, emails);
            };

            this.setSetting = function (name, value) {
                $http({
                    method: 'POST',
                    url: '/api/settings/set/',
                    data: $.param({'name': name, 'value': value}),
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                }).then(
                        function (response) {
                            $mdToast.show($mdToast.simple()
                                    .textContent('Changed successfully')
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
            }
        }]);
</script>
</body>
</html>