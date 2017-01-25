<!DOCTYPE HTML>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Order List</title>

    <jsp:include page="template/angular_common.jsp"/>
</head>

<body ng-app="BaseApp" ng-cloak>

<jsp:include page="template/toolbar.jsp">
    <jsp:param name="title" value="My orders"/>
</jsp:include>

<md-content>
    <div ng-controller="CardController as ctrl">
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
                                <span class="md-caption" ng-if="order.is_archived == true">archived</span>
                                <span class="md-caption"
                                      ng-if="order.is_done == true && order.is_canceled == false && order.is_archived == false">done</span>
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
                        <md-button class="md-icon-button" aria-label="Done" ng-click="ctrl.archiveOrder($index)">
                            <md-icon md-svg-icon="archive"></md-icon>
                        </md-button>
                    </md-card-actions>
                </md-card>
            </md-content>
        </div>
    </div>
</md-content>

<script type="text/javascript">
    app.controller('CardController', ['$scope', '$window', '$http', function ($scope, $window, $http) {
        $http.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
        $scope.orders = ${orders};

        this.redirectToGroupList = function (instituteId) {
            $window.location.href = '/structure/institutes/' + instituteId + '/groups';
        };

        this.archiveOrder = function (index) {
            /*$http.post('/api/order/archive', {"order_id": $scope.orders[index]['id']})
             .success(function (response) {
             console.log(response);
             $scope.orders.splice(index, 1);
             })
             .error(function (response) {
             console.log(response);
             });*/

            $http({
                method: 'POST',
                url: '/api/order/archive',
                data: $.param({'order_id': $scope.orders[index]['id']}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(
                    function (response) {
                        console.log(response.data);
                        $scope.orders.splice(index, 1);
                    },
                    function (response) {
                        console.log(response);
//                        deferred.resolve(err);
                    }
            );
        };

        /*$http({
         method: 'POST',
         url: '/api/order/archive',
         params: {"order_id": $scope.orders[index]['id']},
         headers: {
         'Content-Type': 'x-www-form-urlencoded'
         },
         }).then(function successCallback(response) {
         console.log(response);
         $scope.orders.splice(index, 1);
         }, function errorCallback(response) {
         console.log(response);
         });*/
    }]);
</script>

</body>
</html>