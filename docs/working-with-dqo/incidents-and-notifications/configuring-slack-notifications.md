# Configuring slack notifications

DQO supports integration with slack webhooks which are used to set up in-app slack notifications.


Notification with use of slack webhooks are **available to use in Free Slack** as well as paid versions.
Webhooks in Slack are free in all pricing plans, including free one.

> If Free Slack is used and you configured integrations with other tools previously, verify if you do not pass the Free Slack limit.
> 
> You can check your tool integrations under the below url. Replace the **[slack_team_id]** with your Slack Team ID.
> 
> https[]()://app.slack.com/apps-manage/**[slack_team_id]**/integrations/installed


A prerequisite involves:
- having a slack application with the ownership or a management permissions for a slack workspace, 
- a free slot for tool integration.

Slack webhooks reference: https://api.slack.com/messaging/webhooks

---

### Configuration steps

1. Open Slack webpage

Open the slack api webpage: https://api.slack.com/apps and select your app (or create it if you did not already done so)

2. Enable Incoming Webhooks

Make sure you enabled an **Incoming Webhooks** feature on Slack web page. It can be found in the Features section as presented below:

![activate-incoming-webhook](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/activate-incoming-webhook.png)

3. Down on the same page your webhooks are available. Create a new webhook or copy an existing one that will be used in the following steps later.

> At this step testing webhooks is suggested by running curl sample in command line such as _terminal_, _cmd_ or _powershell_. It will inform you the current slack webhook configuration is valid.
> 
> When curl command is executed after a while Slack notification with test message will appear.

![copy-webhook](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/copy-webhook.png)

> Slack application supports to configure multiple webhooks within a single app.
>
> You can divide connections or even different status of incidents and link them to an another slack channels within a single Slack application.

4. Open DQO application and select the **Data Sources** button in on the main page menu.

![dqo-main-page](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/dqo-main-page.png)

5. Select a previously created connection and reach **Incidents and Notifications** card on the right side.

> The url path part can be also used : /sources/connection/**[connection_name]**/incidents
> 
> Remember to replace [connection_name] with your connection name, created in DQO application.

6. In the **Webhooks for notifications of an incident state change** section paste the slack webhook url and save the changes by **Save** button.

![dqo-incidents](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/dqo-incidents.png)

Each time an incident changes its state in DQO application you get a notification to keep track your data quality.

## Notification message content

### Notification popup

Notification message begins with a data source the incident occured. It makes the most important information visible in the popup window. 

> If you cannot see a notification popup make sure your system notifications are not muted and the application process is started on your device.

More details are avaialble when opening the message. You can check it in the [Message content](#message-content) section.

#### Desktop notification

Notification popup contains crucial information.

![slack-notification-desktop](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/slack-notification-desktop.png)

#### Smartphone notification

If you would like to be up-to-date if you are out of keyboard you can use a mobile Slack application.

![android-notification](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/slack-notification-android.jpg)

> Size of notification message shown on your mobile device can vary depending on the device.

---
### Message content

Notification message differentiate the following resolution status values:

- **Open**
- **Acknowledged** 
- **Resolved** 
- **Muted**

Each of them differ in the message of the notification.

#### New incident message example

![slack-message-open](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/slack-message-open.png)


#### Acknowledged incident message example
![slack-message-acknowledged](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/slack-message-acknowledged.png)

**Acknowledged**, **Resolved** and **Muted** resolution status messages differs in the header of a notification. 
You can also see it when indicent change the status.


### Links

Notification message contains fast access links to:
- **an incident** - View in DQOps link on the bottom of a message, 
- **a data source** - Full table name present in the header of a message, 
- **an issue url**. 

The last one will be present when set manually on an incident. Check the [External system linking](#external-system-linking) section.


### External system linking

While an incident occurs for the first time, user can link the external ticketing system with the incident.
This will allow to track the issue simple way.
