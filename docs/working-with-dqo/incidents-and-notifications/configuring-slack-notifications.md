# Configuring slack notifications

DQO supports integration with slack webhooks which are used to set up in-app Slack notifications. 

A simple Slack integration allows you to receive notifications as fast as possible on your data quality.

---

## Configuration steps

1. Open Slack webpage

    Open the slack api webpage: [https://api.slack.com/apps](https://api.slack.com/apps) and select your app (or create it if you did not already done so)


2. Enable Incoming Webhooks

    Make sure you enabled an **Incoming Webhooks** feature on Slack web page. It can be found in the Features section as presented below:

    If you would like to learn more about slack webhooks: [https://api.slack.com/messaging/webhooks](https://api.slack.com/messaging/webhooks) 

    ![activate-incoming-webhook](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/activate-incoming-webhook.png)


3. Down on the same page your webhooks are available. Create a new webhook or copy an existing one that will be used in the following steps later.

    At this step testing webhooks is suggested by running curl sample in command line such as _terminal_, _cmd_ or _powershell_. It will inform you the current slack webhook configuration is valid.

    When curl command is executed after a while Slack notification with test message will appear.

    ![copy-webhook](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/copy-webhook.png)

    Slack application supports to configure multiple webhooks within a single app.

    You can divide connections or even different status of incidents and link them to an another slack channels within a single Slack application.


4. Open DQO application and select the **Data Sources** section in on the main page menu in navigation bar.

    ![dqo-main-page](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/dqo-main-page.png)


5. Select a previously created connection and reach **Incidents and Notifications** card on the right side.

    The url path part can be also used : /sources/connection/**[connection_name]**/incidents

    Remember to replace [connection_name] with your connection name, created in DQO application.


6. In the **Webhooks for notifications of an incident state change** section paste the slack webhook url and save the changes by **Save** button.

    Let's fill the first field in **Webhooks for notificaiton** section. It will lead to a new notification each time a new incident is observed.
    If you would like to receive notification on another states, fill proper fields.

    ![dqo-incidents](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/dqo-incidents.png)

7. Receive the notification

    Each time a new incident is observed in DQO application you get a notification to keep track your data quality.
    If you set another fields of webhook notifications, you will receive a notification each time resolution state changes on an incident to that state set in webhook. 

## Notification example

Each notification differ in the message. 

#### New incident message example

![slack-message-open](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/slack-message-open.png)


#### Acknowledged incident message example
![slack-message-acknowledged](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/slack-message-acknowledged.png)

**Acknowledged**, **Resolved** and **Muted** resolution status messages differs in the header of a notification.
You can also see it when indicent change the status.