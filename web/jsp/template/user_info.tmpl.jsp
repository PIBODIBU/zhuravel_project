<md-dialog style="overflow: hidden; margin-top:36px; margin-bottom:36px;" flex="auto">
    <md-toolbar md-scroll-shrink md-whiteframe="4">
        <div class="md-toolbar-tools">
            {{user.name}} {{user.surname}}
            <span flex></span>
            <md-button class="md-icon-button"
                       ng-click="cancel()">
                <md-icon md-svg-icon="close"></md-icon>
            </md-button>
        </div>
    </md-toolbar>

    <md-content style="padding-top: 0; margin-top:0;">
        <section>
            <md-list flex>
                <md-subheader class="md-primary">Main info</md-subheader>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Name</h3>
                        <p ng-if="user.name">
                            {{user.name}}</p>
                        <p ng-if="!user.name">null</p>
                    </div>
                </md-list-item>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Surname</h3>
                        <p ng-if="user.surname">
                            {{user.surname}}</p>
                        <p ng-if="!user.surname">null</p>
                    </div>
                </md-list-item>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Middle name</h3>
                        <p ng-if="user.middleName">
                            {{user.middleName}}</p>
                        <p ng-if="!user.middleName">null</p>
                    </div>
                </md-list-item>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Email</h3>
                        <p ng-if="user.email">
                            {{user.email}}</p>
                        <p ng-if="!user.email">null</p>
                    </div>
                </md-list-item>
            </md-list>
        </section>

        <section>
            <md-list flex>
                <md-subheader class="md-primary">Passport info</md-subheader>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Passport series</h3>
                        <p ng-if="user.userData.passportSeries">
                            {{user.userData.passportSeries}}</p>
                        <p ng-if="!user.userData.passportSeries">null</p>
                    </div>
                </md-list-item>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Passport number</h3>
                        <p ng-if="user.userData.passportNumber">
                            {{user.userData.passportNumber}}</p>
                        <p ng-if="!user.userData.passportNumber">null</p>
                    </div>
                </md-list-item>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Passport validity</h3>
                        <p ng-if="user.userData.passportValidity">
                            {{user.userData.passportValidity}}</p>
                        <p ng-if="!user.userData.passportValidity">null</p>
                    </div>
                </md-list-item>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Passport registration</h3>
                        <p ng-if="user.userData.passportRegistration">
                            {{user.userData.passportRegistration}}</p>
                        <p ng-if="!user.userData.passportRegistration">null</p>
                    </div>
                </md-list-item>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Passport scan</h3>
                        <a ng-if="user.userData.passportUrl"
                           ng-href="/uploads/scan/{{user.userData.passportUrl}}" target="_blank">
                            Open
                        </a>
                        <p ng-if="!user.userData.passportUrl">null</p>
                    </div>
                </md-list-item>
            </md-list>
        </section>

        <section>
            <md-list flex>
                <md-subheader class="md-primary">Additional info</md-subheader>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Phone</h3>
                        <p ng-if="user.userData.phone">
                            {{user.userData.phone}}</p>
                        <p ng-if="!user.userData.phone">null</p>
                    </div>
                </md-list-item>
            </md-list>
        </section>

        <section>
            <md-list flex>
                <md-subheader class="md-primary">Bonus card</md-subheader>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Company name</h3>
                        <p ng-if="user.userData.companyName">
                            {{user.userData.companyName}}</p>
                        <p ng-if="!user.userData.companyName">null</p>
                    </div>
                </md-list-item>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Card number</h3>
                        <p ng-if="user.userData.bonusCardNumber">
                            {{user.userData.bonusCardNumber}}</p>
                        <p ng-if="!user.userData.bonusCardNumber">null</p>
                    </div>
                </md-list-item>
            </md-list>
        </section>
    </md-content>
</md-dialog>
