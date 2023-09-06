# Configuring slack notifications

DQO supports integration with slack webhooks which are used to set up in-app slack notifications.

A prerequisite involves having a slack application with the ownership or a management permissions for a slack workspace.

Slack webhooks reference: https://api.slack.com/messaging/webhooks

---

#### Configuration steps

1. Open the slack api webpage: https://api.slack.com/apps and select your app (or create it if you did not already done so)

2. Make sure you enabled an Incoming Webhooks feature on slack web page.

![Autocomplete-parameters](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/activate-incoming-webhook.png)

3. Down on the same page your webhooks are available. Create a new webhook or copy an existing one that will be used in the following steps later.

![Autocomplete-parameters](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/copy-webhook.png)

4. Open DQO application and select the **Data Sources** button in on the main page menu.

![Autocomplete-parameters](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/dqo-main-page.png)

5. Select a previously created connection and reach **Incidents and Notifications** card on the right side.

> You can also use an url path part: /sources/connection/**[connection_name]**/incidents
>
> Remember to replace [connection_name] with your connection name, created in DQO application.

6. In the **Webhooks for notifications of an incident state change** section paste the slack webhook url and save the changes by **Save** button.

![Autocomplete-parameters](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/dqo-incidents.png)

7. Each time a new incident is observed in DQO application you get a notification to keep track your data quality

![Autocomplete-parameters](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/slack-notification.png)
