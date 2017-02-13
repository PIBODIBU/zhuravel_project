<!DOCTYPE HTML>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title>${user.name} ${user.surname}</title>

    <jsp:include page="include/angular_common.jsp"/>
</head>

<body ng-app="BaseApp" ng-cloak>

<jsp:include page="include/toolbar.jsp">
    <jsp:param name="title" value="${user.name.concat(' ').concat(user.surname).concat(' ')}"/>
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
            <h1>{{user.name}} {{user.surname}} {{user.middleName}}</h1>
        </div>

        <div md-whiteframe="16"
             flex="70">
            <md-content>
                <form name="editForm"
                      action="/user/me/edit/"
                      enctype="multipart/form-data"
                      method="post">

                    <spring:bind path="user.userId">
                        <input name="${status.expression}"
                               value="${status.value}"
                               type="hidden"/>
                    </spring:bind>
                    <spring:bind path="userData.userDataId">
                        <input name="${status.expression}"
                               value="${status.value}"
                               type="hidden"/>
                    </spring:bind>

                    <md-content class="md-padding"
                                md-theme="docs-dark"
                                layout="row">
                        <md-input-container flex="50">
                            <label>First name</label>

                            <spring:bind path="user.name">
                                <input required
                                       name="${status.expression}"
                                       value="${status.value}"
                                       readonly
                                       ng-model="user.name"/>
                            </spring:bind>

                            <div ng-messages="editForm.name.$error">
                                <div ng-message="required">This is required.</div>
                            </div>
                        </md-input-container>

                        <md-input-container flex="50">
                            <label>Last name</label>

                            <spring:bind path="user.surname">
                                <input required
                                       name="${status.expression}"
                                       value="${status.value}"
                                       readonly
                                       ng-model="user.surname"/>
                            </spring:bind>

                            <div ng-messages="editForm.surname.$error">
                                <div ng-message="required">This is required.</div>
                            </div>
                        </md-input-container>
                    </md-content>

                    <md-subheader class="md-primary">Main info</md-subheader>
                    <md-content class="md-padding" layout="row" layout-wrap>
                        <md-input-container flex="50">
                            <label>Middle name</label>

                            <spring:bind path="user.middleName">
                                <input name="${status.expression}"
                                       value="${status.value}"
                                       ng-model="user.middleName"/>
                            </spring:bind>

                            <div ng-messages="editForm.middleName.$error">
                                <div ng-message="md-maxlength">Username must be less than 30 characters long.</div>
                            </div>
                        </md-input-container>

                        <md-input-container flex="50">
                            <label>Email</label>

                            <spring:bind path="user.email">
                                <input required
                                       name="${status.expression}"
                                       value="${status.value}"
                                       type="email"
                                       readonly
                                       required
                                       ng-model="user.email"/>
                            </spring:bind>

                            <div ng-messages="editForm.email.$error">
                                <div ng-message="required">This is required.</div>
                            </div>
                        </md-input-container>

                        <md-input-container flex="50">
                            <label>Username</label>

                            <spring:bind path="user.username">
                                <input required
                                       name="${status.expression}"
                                       value="${status.value}"
                                       md-maxlength="30"
                                       readonly
                                       ng-model="user.username"/>
                            </spring:bind>

                            <div ng-messages="editForm.username.$error">
                                <div ng-message="required">This is required.</div>
                                <div ng-message="md-maxlength">Username must be less than 30 characters long.</div>
                            </div>
                        </md-input-container>

                        <md-input-container flex="50">
                            <label>Password</label>

                            <spring:bind path="user.password">
                                <input required
                                       name="${status.expression}"
                                       value="${status.value}"
                                       md-maxlength="30"
                                       type="password"
                                       readonly
                                       ng-model="user.password"/>
                            </spring:bind>

                            <div ng-messages="editForm.password.$error">
                                <div ng-message="required">This is required.</div>
                                <div ng-message="md-maxlength">Password must be less than 30 characters long.</div>
                            </div>
                        </md-input-container>
                    </md-content>

                    <md-subheader class="md-primary">Passport info</md-subheader>
                    <md-content class="md-padding" layout="row" layout-wrap>
                        <md-input-container flex="50">
                            <label>Passport series</label>

                            <spring:bind path="userData.passportSeries">
                                <input required
                                       name="${status.expression}"
                                       value="${status.value}"
                                       readonly
                                       ng-model="user.userData.passportSeries"/>
                            </spring:bind>

                            <div ng-messages="editForm.passportSeries.$error">
                                <div ng-message="required">This is required.</div>
                            </div>
                        </md-input-container>

                        <md-input-container flex="50">
                            <label>Passport number</label>

                            <spring:bind path="userData.passportNumber">
                                <input name="${status.expression}"
                                       value="${status.value}"
                                       readonly
                                       required
                                       ng-model="user.userData.passportNumber"/>
                            </spring:bind>

                            <div ng-messages="editForm.passportNumber.$error">
                                <div ng-message="required">This is required.</div>
                            </div>
                        </md-input-container>

                        <md-input-container flex="50">
                            <label>Passport validity</label>

                            <spring:bind path="userData.passportValidity">
                                <input required
                                       name="${status.expression}"
                                       value="${status.value}"
                                       readonly
                                       path="userData.passportValidity"
                                       ng-pattern="/^[0-9]{2}\.[0-9]{2}\.[0-9]{4}$/"
                                       ng-model="user.userData.passportValidity"/>
                            </spring:bind>

                            <div class="hint">##.##.####</div>

                            <div ng-messages="editForm.passportValidity.$error">
                                <div ng-message="required">This is required.</div>
                                <div ng-message="pattern">##.##.#### - Please enter a valid date.</div>
                            </div>
                        </md-input-container>

                        <md-input-container flex="50">
                            <label>Passport registration</label>

                            <spring:bind path="userData.passportRegistration">
                                <input name="${status.expression}"
                                       value="${status.value}"
                                       md-maxlength="100"
                                       path="userData.passportRegistration"
                                       ng-model="user.userData.passportRegistration"/>
                            </spring:bind>

                            <div ng-messages="editForm.passportRegistration.$error">
                                <div ng-message="md-maxlength">Passport registration must be less than 30 characters
                                    long.
                                </div>
                            </div>
                        </md-input-container>

                        <choose-file flex="100"
                                     layout="row">
                            <input id="fileInput"
                                   class="ng-hide"
                                   name="passportPhoto"
                                   type="file"
                                   ngf-select="onFilesChanged($files, $file, $newFiles, $duplicateFiles, $invalidFiles, $event)"
                                   ng-model="files"
                                   multiple="multiple">

                            <md-input-container flex
                                                class="md-block">
                                <input type="text"
                                       ng-model="fileName"
                                       readonly>
                                <div class="hint">Select your passport photo</div>
                            </md-input-container>
                            <div>
                                <md-button id="uploadButton"
                                           class="md-fab md-mini">
                                    <md-icon class="material-icons">attach_file</md-icon>
                                </md-button>
                            </div>
                        </choose-file>
                    </md-content>

                    <md-subheader class="md-primary">Additional info</md-subheader>
                    <md-content class="md-padding" layout="row" layout-wrap>
                        <md-input-container flex="50">
                            <label>Phone</label>

                            <spring:bind path="userData.phone">
                                <input name="${status.expression}"
                                       value="${status.value}"
                                       md-maxlength="10"
                                       ng-pattern="/^[0-9]{10}$/"
                                       ng-model="user.userData.phone"/>
                            </spring:bind>

                            <div ng-messages="editForm.phone.$error">
                                <div ng-message="md-maxlength">Phone must be less than 30 characters
                                    long.
                                </div>
                                <div ng-message="pattern">Please enter a valid phone number.</div>
                            </div>
                        </md-input-container>
                    </md-content>

                    <md-subheader class="md-primary">Bonus card</md-subheader>
                    <md-content class="md-padding" layout="row" layout-wrap>
                        <md-input-container flex="50">
                            <label>Company name</label>

                            <spring:bind path="userData.companyName">
                                <input name="${status.expression}"
                                       value="${status.value}"
                                       ng-model="user.userData.companyName"/>
                            </spring:bind>

                        </md-input-container>

                        <md-input-container flex="50">
                            <label>Card number</label>

                            <spring:bind path="userData.bonusCardNumber">
                                <input name="${status.expression}"
                                       value="${status.value}"
                                       ng-model="user.userData.bonusCardNumber"/>
                            </spring:bind>

                        </md-input-container>
                    </md-content>

                    <div layout="row"
                         flex="100"
                         class="md-padding"
                         layout-align="center center">
                        <md-button class="md-raised md-primary"
                                   flex="33"
                                   layout-align="center"
                                   type="submit">Submit
                        </md-button>
                    </div>
                </form>
            </md-content>
        </div>
    </div>

    <md-button class="md-fab fab"
               aria-label="Edit profile"
               ng-if="isMyPage"
               ng-click="ctrl.redirect('/user/me/edit')">
        <md-tooltip md-direction="bottom" md-direction="left">Edit profile</md-tooltip>
        <md-icon md-svg-src="account-edit"></md-icon>
    </md-button>
</md-content>

<script type="text/javascript">
    app.controller('PageController', ['$scope', '$window', '$http', '$mdDialog', '$mdToast',
        function ($scope, $window, $http, $mdDialog, $mdToast) {
            $scope.user = ${userJson};

            this.redirect = function (url) {
                window.location.href = url;
            };
        }]);
</script>
</body>
</html>