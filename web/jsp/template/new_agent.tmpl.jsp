<md-dialog>
    <form name="newAgentForm">
        <md-toolbar md-whiteframe="4">
            <div class="md-toolbar-tools">
                <h3 ng-if="!agent.name && !agent.surname">Add new agent</h3>
                <h3>{{agent.name}} {{agent.surname}}</h3>
            </div>
        </md-toolbar>

        <md-content class="md-padding"
                    md-theme="docs-dark"
                    layout="row">
            <md-input-container flex="50">
                <label>First name</label>
                <input required="required"
                       ng-model="agent.name"/>
                <div ng-messages="newAgentForm.name.$error">
                    <div ng-message="required">This is required.</div>
                </div>
            </md-input-container>

            <md-input-container flex="50">
                <label>Last name</label>
                <input required="required"
                       path="surname"
                       ng-model="agent.surname"/>
                <div ng-messages="newAgentForm.surname.$error">
                    <div ng-message="required">This is required.</div>
                </div>
            </md-input-container>
        </md-content>

        <md-dialog-content>
            <div class="md-dialog-content">
                <md-subheader class="md-primary">Main info</md-subheader>
                <md-content class="md-padding" layout="row" layout-wrap>
                    <md-input-container flex="50">
                        <label>Middle name</label>
                        <input ng-model="agent.middleName"/>
                        <div ng-messages="newAgentForm.middleName.$error">
                            <div ng-message="md-maxlength">Username must be less than 30 characters long.</div>
                        </div>
                    </md-input-container>

                    <md-input-container flex="50">
                        <label>Email</label>
                        <input type="email"
                               required="required"
                               ng-model="agent.email"/>
                        <div ng-messages="newAgentForm.email.$error">
                            <div ng-message="required">This is required.</div>
                        </div>
                    </md-input-container>

                    <md-input-container flex="50">
                        <label>Username</label>
                        <input required="required"
                               md-maxlength="30"
                               ng-model="agent.username"/>
                        <div ng-messages="newAgentForm.username.$error">
                            <div ng-message="required">This is required.</div>
                            <div ng-message="md-maxlength">Username must be less than 30 characters long.</div>
                        </div>
                    </md-input-container>

                    <md-input-container flex="50">
                        <label>Password</label>
                        <input required="required"
                               type="password"
                               md-maxlength="30"
                               ng-model="agent.password"/>
                        <div ng-messages="newAgentForm.password.$error">
                            <div ng-message="required">This is required.</div>
                            <div ng-message="md-maxlength">Password must be less than 30 characters long.</div>
                        </div>
                    </md-input-container>
                </md-content>

                <md-subheader class="md-primary">Additional info</md-subheader>
                <md-content class="md-padding" layout="row" layout-wrap>
                    <md-input-container flex="50">
                        <label>Phone</label>
                        <input name="phone"
                               md-maxlength="10"
                               ng-pattern="/^[0-9]{10}$/"
                               ng-model="agent.userData.phone"/>
                        <div ng-messages="newAgentForm.phone.$error">
                            <div ng-message="md-maxlength">Phone must be less than 30 characters
                                long.
                            </div>
                            <div ng-message="pattern">Please enter a valid phone number.</div>
                        </div>
                    </md-input-container>
                </md-content>
            </div>
        </md-dialog-content>
    </form>

    <md-dialog-actions layout="row">
        <span flex></span>
        <md-button ng-click="cancel()">
            Cancel
        </md-button>
        <md-button ng-click="done(agent)">
            Add
        </md-button>
    </md-dialog-actions>
</md-dialog>