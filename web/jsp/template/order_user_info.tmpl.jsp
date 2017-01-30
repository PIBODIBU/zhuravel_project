<md-dialog style="overflow: hidden; margin-top:36px; margin-bottom:36px;" flex="auto">
    <md-toolbar md-scroll-shrink md-whiteframe="4">
        <div class="md-toolbar-tools">
            {{order.buyer.name}} {{order.buyer.surname}}
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
                        <p ng-if="order.buyer.name">
                            {{order.buyer.name}}</p>
                        <p ng-if="!order.buyer.name">null</p>
                    </div>
                </md-list-item>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Surname</h3>
                        <p ng-if="order.buyer.surname">
                            {{order.buyer.surname}}</p>
                        <p ng-if="!order.buyer.surname">null</p>
                    </div>
                </md-list-item>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Middle name</h3>
                        <p ng-if="order.buyer.middleName">
                            {{order.buyer.middleName}}</p>
                        <p ng-if="!order.buyer.middleName">null</p>
                    </div>
                </md-list-item>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Email</h3>
                        <p ng-if="order.buyer.email">
                            {{order.buyer.email}}</p>
                        <p ng-if="!order.buyer.email">null</p>
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
                        <p ng-if="order.buyer.userData.passportSeries">
                            {{order.buyer.userData.passportSeries}}</p>
                        <p ng-if="!order.buyer.userData.passportSeries">null</p>
                    </div>
                </md-list-item>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Passport number</h3>
                        <p ng-if="order.buyer.userData.passportNumber">
                            {{order.buyer.userData.passportNumber}}</p>
                        <p ng-if="!order.buyer.userData.passportNumber">null</p>
                    </div>
                </md-list-item>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Passport validity</h3>
                        <p ng-if="order.buyer.userData.passportValidity">
                            {{order.buyer.userData.passportValidity}}</p>
                        <p ng-if="!order.buyer.userData.passportValidity">null</p>
                    </div>
                </md-list-item>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Passport registration</h3>
                        <p ng-if="order.buyer.userData.passportRegistration">
                            {{order.buyer.userData.passportRegistration}}</p>
                        <p ng-if="!order.buyer.userData.passportRegistration">null</p>
                    </div>
                </md-list-item>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Passport scan</h3>
                        <a ng-if="order.buyer.userData.passportUrl"
                           ng-href="/uploads/scan/{{order.buyer.userData.passportUrl}}" target="_blank">
                            Open
                        </a>
                        <p ng-if="!order.buyer.userData.passportUrl">null</p>
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
                        <p ng-if="order.buyer.userData.phone">
                            {{order.buyer.userData.phone}}</p>
                        <p ng-if="!order.buyer.userData.phone">null</p>
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
                        <p ng-if="order.buyer.userData.companyName">
                            {{order.buyer.userData.companyName}}</p>
                        <p ng-if="!order.buyer.userData.companyName">null</p>
                    </div>
                </md-list-item>

                <md-list-item class="md-2-line">
                    <div class="md-list-item-text">
                        <h3>Card number</h3>
                        <p ng-if="order.buyer.userData.bonusCardNumber">
                            {{order.buyer.userData.bonusCardNumber}}</p>
                        <p ng-if="!order.buyer.userData.bonusCardNumber">null</p>
                    </div>
                </md-list-item>
            </md-list>
        </section>
    </md-content>

    <%--<md-dialog-actions layout="row">
        <span flex></span>
        <md-button ng-click="cancel()">
            OK
        </md-button>
    </md-dialog-actions>--%>
</md-dialog>
