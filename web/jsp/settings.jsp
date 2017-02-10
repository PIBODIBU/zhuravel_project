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
                <md-chips ng-model="ctrl.settings.serviceEmails" md-removable="true" md-enable-chip-edit="true"
                          placeholder="Enter service emails...">
                </md-chips>
            </md-list-item>
        </md-list>
    </md-content>
</div>

<script type="text/javascript">
    app.controller('PageController', ['$scope', '$window', '$http', '$mdDialog', '$mdToast',
        function ($scope, $window, $http, $mdDialog, $mdToast) {
            this.settings = [];
            this.settings.serviceEmails = ['service@email.com', 'noreply@email.com'];
        }]);
</script>
</body>
</html>