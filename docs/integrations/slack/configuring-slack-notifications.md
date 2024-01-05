# Configure Slack notifications

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

    !!! warning "Test the webhook"
   
        We suggest testing if the set Webhooks configuration is correct by running a sample curl command in the command line.
      
        Soon after executing the curl command, you should get a message in Slack

    ![copy-webhook](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/copy-webhook.png)

    Slack application supports configuration of multiple webhooks within a single app.

    !!! tip "Use different Slack channels"

        You can divide connections or even different statuses of incidents and link them to different Slack channels within a single Slack application.


## Types of webhooks configuration in DQOps

Configuration of webhooks in DQOps platform can be done in two different ways.

First of them is the default webhooks configuration available under Configuration menu, shown on screen below.

![default-webhooks-page](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/default-webhooks-page.png)

The second way is to configure webhooks on a connection level under the Notifications and Incidents tab, 
presented in [Configuration steps in DQOps](#configuration-steps-in-dqops) section.

DQOps uses both of them when preparing notifications. 
When the connection does not set the webhook link on it's level,
the corresponding value from default webhooks is set (e.g. opened incident webhook value).

## Configuration steps in DQOps on a connection level

Here are the steps to add a Webhook URL to DQOps. 
The example shows the steps to configure webhooks on a connection level, which is similar to the default webhooks.

1. Open the DQOps application and go to the **Data Sources** section.

    ![dqo-incidents](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/dqo-incidents.png)

2. In the tree view on the left, select the data source of interest, and select the **Incidents and Notifications** tab.

    The url path part can be also used : /sources/connection/**[connection_name]**/incidents

    Remember to replace [connection_name] with your connection name, created in DQOps application.

3. In the **Webhooks for notifications of an incident state change** section, paste the Webhook URL that you get in the Slack app into the  “A new incident was opened (detected)” field. 

4. Save the changes by clicking the **Save button**. Now each time a new incident is open you will receive a notification in Slack.

    ![Configuring webhooks](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/webhook-fields.png)

    !!! tip "Use another statuses"

        If you would like to receive notification on statuses other than Open, fill in other input fields with Webhook URLs.

## Notification example in Slack

Each notification type has a slightly different message.

### **New incident message example**
The notifications about a new data quality incident should be published to a dedicated Slack channel visible 
to the support team that is monitoring and assessing new data quality incidents. It could be the data quality team
or a 2nd level support team.

This type of notification should be configured in the **A new incident was opened (detected)** field on
the [webhook configuration](../webhooks/index.md) page. The Slack webhook url for this parameter should
point to the Slack channel used by the 2nd level support team that is monitoring and reviewing new data quality incidents.

A new incident preview in Slack is shown below.

![slack-message-open](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/slack-message-open.png)

### **Acknowledged incident message example**
After the support team reviews a new data quality [incident](../../working-with-dqo/incidents-and-notifications/incidents.md) in DQOps,
the support user will change the status of a valid incident to **Acknowledged**.
Changing the incident status to **Acknowledged** is the next status in the incident management workflow, which means
that the incident was acknowledged (confirmed), and is assigned to the engineering team (the 3rd level support) to be fixed. 

This type of notification should be configured in the **An incident was acknowledged** field on
the [webhook configuration](../webhooks/index.md) page. The Slack webhook url for this parameter should
point to the Slack channel used by the 3nd level support team that is fixing data quality incidents.

An acknowledged incident preview in Slack is shown below.

![slack-message-acknowledged](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/slack-message-acknowledged.png)

The difference from the message published for a new incident is subtle.
Only the **Acknowledged**, **Resolved** and **Muted** status messages differ in the title.


## Setting incident base url

The **View in DQOps** link will open an incident url in DQOps. If your DQOps instance is running in production mode
and the DQOps web interface is assigned a host name, the base url must be configured. The default base url of DQOps
is derived from the port number configured using the [--server.port](../../command-line-interface/dqo.md#--server.port) parameter.
When no additional configuration is applied, the incident url will look like *http://localhost:8888/incidents/bigquery-public-data/2023/10/70cc4354-344f-bf9c-9bab-34ffae2f1179*.

The *http://localhost:8888/* base url can be configured when starting DQOps using
the [--dqo.instance.return-base-url](../../command-line-interface/dqo.md#--dqo.instance.return-base-url) startup parameter
as shown in the following example.

```
dqo> dqo --dqo.instance.return-base-url=https://dqops.mycompany.com/ run
```

The base url for a DQOps Cloud SaaS instance is already configured, 
and the base address is *https://&lt;customer&gt;.us.dqops.com* for instances hosted in the USA. 

