<md-dialog>
    <md-dialog-content>
        <div class="md-dialog-content">
            <h3>Complete the order</h3>

            <md-input-container class="md-block">
                <label>Comment</label>
                <textarea ng-model="order.sold_comment" md-maxlength="500" rows="5" md-autofocus></textarea>
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
