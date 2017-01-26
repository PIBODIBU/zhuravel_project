<md-dialog>
    <md-dialog-content>
        <div class="md-dialog-content">
            <h3>Make new order</h3>

            <md-input-container class="md-block">
                <label>Item name</label>
                <input ng-model="order.buying_item_name" md-autofocus>
            </md-input-container>

            <md-input-container class="md-block">
                <label>Comment</label>
                <textarea ng-model="order.buying_comment" md-maxlength="500" rows="5"></textarea>
            </md-input-container>
        </div>
    </md-dialog-content>

    <md-dialog-actions layout="row">
        <span flex></span>
        <md-button ng-click="cancel()">
            Cancel
        </md-button>
        <md-button ng-click="done(order)">
            Done
        </md-button>
    </md-dialog-actions>
</md-dialog>
