# Configuring Slack notifications

DQOps supports integration with Slack webhooks which are used to set up in-app Slack notifications. 

Slack integration allows you to receive notifications whenever an issue is detected in your data and a new incident is created or modified.

---

## Configuration steps in Slack API

1. Open Slack webpage

    Open the slack API webpage: [https://api.slack.com/apps](https://api.slack.com/apps) and select the app you would like to receive notifications.
    
    In case you have not already created a Slack app or want to configure notifications with a new Slack app, select the **Create New App button**. 
    It will trigger a pop-up window with the app creation.

    ![slack-api](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/slack-api-1.png)

    Next, select the **From scratch**. 

    ![slack-api](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/slack-api-2.png)

    On a final window fill the **App Name** as DQOps Notifications and pick a workspace to integrate notifications.

    ![slack-api](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/slack-api-3.png)

2. Enable Incoming Webhooks in the Features section

    To be able to receive **Incoming Webhooks** messages on Slack you need to enable this feature on the Slack API webpage using the ON/OFF toggle button.

    You can find more information about [sending messages using Incoming Webhooks here](https://api.slack.com/messaging/webhooks)

    ![activate-incoming-webhook](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/activate-incoming-webhook.png)


3. Create a new webhook URL or copy an existing one that you will paste into the DQOps at the later steps.
    You can find Webhooks at the bottom of the same page.

    We suggest testing if the set Webhooks configuration is correct by running a sample curl command in the command line.

    Soon after executing the curl command, you should get a message in Slack

    ![copy-webhook](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/copy-webhook.png)

    Slack application supports configuration of multiple webhooks within a single app.

    You can divide connections or even different statuses of incidents and link them to a different Slack channel within a single Slack application.


## Configuration steps in DQO

To add a Webhook URL to DQO:

1. Open the DQOps application and go to the **Data Sources** section.

    ![dqo-incidents](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/dqo-incidents.png)

2. In the tree view on the left, select the data source of interest, and select the **Incidents and Notifications** tab.

    The url path part can be also used : /sources/connection/**[connection_name]**/incidents

    Remember to replace [connection_name] with your connection name, created in DQOps application.

3. In the **Webhooks for notifications of an incident state change** section, paste the Webhook URL that you get in the Slack app into the  “A new incident was opened (detected)” field. 

4. Save the changes by clicking the **Save button**. Now each time a new incident is open you will receive a notification in Slack.
   
    If you would like to receive notification on statuses other than Open, fill in other input fields with Webhook URLs.

   ![Configuring webhooks](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/webhook-fields.png)

## Notification example in Slack

Each notification has a slightly different message.

#### **New incident message example**

![slack-message-open](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/slack-message-open.png)


#### **Acknowledged incident message example**
![slack-message-acknowledged](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/slack-message-acknowledged.png)

**Acknowledged**, **Resolved** and **Muted** status messages only differ in the headers.
