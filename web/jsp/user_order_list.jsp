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
    <jsp:param name="title" value="My orders"/>
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
                    <md-button class="md-icon-button" aria-label="Done">
                        <md-icon md-svg-icon="refresh"></md-icon>
                    </md-button>
                </md-card-actions>
            </md-card>
        </md-content>
    </div>
</div>

<script type="text/javascript">
    app.controller('CardController', ['$scope', '$window', function ($scope, $window) {
        $scope.orders = ${orders};

        this.redirectToGroupList = function (instituteId) {
            $window.location.href = '/structure/institutes/' + instituteId + '/groups';
        }
    }]);
</script>

</body>
</html>
