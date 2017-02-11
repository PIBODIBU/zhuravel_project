<!DOCTYPE HTML>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
            <h1>${user.name} ${user.surname}</h1>
        </div>

        <div md-whiteframe="16"
             flex="70">
            <md-content>
                <form:form name="editForm"
                           action="/user/me/edit/"
                           enctype="multipart/form-data"
                           modelAttribute="user"
                           method="post">
                    <md-content class="md-padding"
                                md-theme="docs-dark"
                                layout="row">
                        <md-input-container flex="50">
                            <label>First name</label>
                            <form:input required="required"
                                        name="name"
                                        disabled="true"
                                        ng-model="user.name"
                                        path="name"/>

                            <div ng-messages="editForm.name.$error">
                                <div ng-message="required">This is required.</div>
                            </div>
                        </md-input-container>

                        <md-input-container flex="50">
                            <label>Last name</label>
                            <form:input required="required"
                                        path="surname"
                                        disabled="true"
                                        ng-model="user.surname"/>
                            <div ng-messages="editForm.surname.$error">
                                <div ng-message="required">This is required.</div>
                            </div>
                        </md-input-container>
                    </md-content>

                    <md-subheader class="md-primary">Main info</md-subheader>
                    <md-content class="md-padding" layout="row" layout-wrap>
                        <md-input-container flex="50">
                            <label>Middle name</label>
                            <form:input path="middleName"
                                        ng-model="user.middleName"/>
                            <div ng-messages="editForm.middleName.$error">
                                <div ng-message="md-maxlength">Username must be less than 30 characters long.</div>
                            </div>
                        </md-input-container>

                        <md-input-container flex="50">
                            <label>Email</label>
                            <form:input name="email"
                                        type="email"
                                        path="email"
                                        disabled="true"
                                        required="required"
                                        ng-model="user.email"/>
                            <div ng-messages="editForm.email.$error">
                                <div ng-message="required">This is required.</div>
                            </div>
                        </md-input-container>

                        <md-input-container flex="50">
                            <label>Username</label>
                            <form:input required="required"
                                        md-maxlength="30"
                                        name="username"
                                        disabled="true"
                                        path="username"
                                        ng-model="user.username"/>
                            <div ng-messages="editForm.username.$error">
                                <div ng-message="required">This is required.</div>
                                <div ng-message="md-maxlength">Username must be less than 30 characters long.</div>
                            </div>
                        </md-input-container>

                        <md-input-container flex="50">
                            <label>Password</label>
                            <form:input required="required"
                                        md-maxlength="30"
                                        type="password"
                                        name="password"
                                        disabled="true"
                                        path="password"
                                        ng-model="user.password"/>
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
                            <form:input name="passportSeries"
                                        disabled="true"
                                        path="userData.passportSeries"
                                        required="required"
                                        ng-model="user.userData.passportSeries"/>
                            <div ng-messages="editForm.passportSeries.$error">
                                <div ng-message="required">This is required.</div>
                            </div>
                        </md-input-container>

                        <md-input-container flex="50">
                            <label>Passport number</label>
                            <form:input name="passportNumber"
                                        disabled="true"
                                        required="required"
                                        path="userData.passportNumber"
                                        ng-model="user.userData.passportNumber"/>
                            <div ng-messages="editForm.passportNumber.$error">
                                <div ng-message="required">This is required.</div>
                            </div>
                        </md-input-container>

                        <md-input-container flex="50">
                            <label>Passport validity</label>
                            <form:input name="passportValidity"
                                        required="required"
                                        disabled="true"
                                        path="userData.passportValidity"
                                        ng-pattern="/^[0-9]{2}\.[0-9]{2}\.[0-9]{4}$/"
                                        ng-model="user.userData.passportValidity"/>

                            <div class="hint">##.##.####</div>

                            <div ng-messages="editForm.passportValidity.$error">
                                <div ng-message="required">This is required.</div>
                                <div ng-message="pattern">##.##.#### - Please enter a valid date.</div>
                            </div>
                        </md-input-container>

                        <md-input-container flex="50">
                            <label>Passport registration</label>
                            <form:input name="passportRegistration"
                                        md-maxlength="100"
                                        path="userData.passportRegistration"
                                        ng-model="user.userData.passportRegistration"/>
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
                                       disabled>
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
                            <form:input name="phone"
                                        path="userData.phone"
                                        md-maxlength="10"
                                        ng-pattern="/^[0-9]{10}$/"
                                        ng-model="user.userData.phone"/>
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
                            <form:input name="companyName"
                                        path="userData.companyName"
                                        ng-model="user.userData.companyName"/>
                        </md-input-container>

                        <md-input-container flex="50">
                            <label>Card number</label>
                            <form:input name="cardNumber"
                                        path="userData.bonusCardNumber"
                                        ng-model="user.userData.cardNumber"/>
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
                </form:form>
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