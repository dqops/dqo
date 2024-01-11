# Configure Slack notifications
Read this guide to learn how to integrate DQOps with Slack, sending data quality incident notification when new data quality issues are detected.

## Overview

DQOps supports integration with Slack webhooks which are used to set up in-app Slack notifications. 

Slack integration allows you to receive notifications whenever an issue is detected in your data and a new incident is created or modified.

---

## Configuration steps in Slack API

### **Create a new app**

1. Open the Slack API webpage: [https://api.slack.com/apps](https://api.slack.com/apps) and select the app you would like to receive notifications.
    
    In case you have not already created a Slack app or want to configure notifications with a new Slack app, select the **Create an App** button. 
    This will open the pop-up window where you can create a new app.

    ![Create a new app](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/slack-api-1.png)

2. Select the **From scratch** option. 

    ![Create a new app from scratch](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/slack-api-2.png)

3. On the final window, add the **App Name** as **DQOps Notifications** and pick a workspace to integrate notifications.

    ![Name app and choose workspace](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/slack-api-3.png)


### **Enable Webhooks in the new app**

Select the **Features** section on the left and activate incoming Webhooks by clicking on the toggle button on the right.

You can find more information about [sending messages using Incoming Webhooks here](https://api.slack.com/messaging/webhooks)

![Enable incoming webhooks](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/activate-incoming-webhook.png)

### **Create a new webhook URL**

Create a new Webhook URL by clicking the **Add New Webhook to Workspace button**

![Create a new Webhook URL](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/copy-webhook.png)


!!! warning "Test the webhook"
       
    We suggest testing if the set Webhooks configuration is correct by running a sample curl command in the command line.
      
Soon after executing the curl command, you should get a message in Slack

Slack application supports configuration of multiple webhooks within a single app.

!!! tip "Use different Slack channels"
    
    You can divide connections or even different statuses of incidents and link them to different Slack channels within a single Slack application.


## Webhooks configuration in DQOps

There are two ways to configure webhooks in the DQOps platform. The first method is using the default webhooks 
configuration available under the Configuration menu.

The second method is configuring webhooks on a connection level under the Notifications and Incidents tab, which is presented
in the Configuration steps in DQOps section.

DQOps uses the default webhooks configuration by default. If the connection has a different webhooks configuration, this configuration is used.


### Configure Default webhooks configuration

You can configure default webhooks configuration for all connections.  
Simply go to the **Configuration** section and select the **Default webhooks** from the tree view on the left. Add the 
Webhook URLs generated in Slack to appropriate input fields. 

![Default webhook configuration](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/default-webhooks-page.png)


### Configure Webhooks on connection level

To configure webhooks on a connection level in DQOps, follow the steps below. 

1. Open the DQOps application and go to the **Data Sources** section.

    ![Navigating to the Incidents section](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/dqo-incidents.png)

2. In the tree view on the left, select the data source of interest, and select the **Incidents and Notifications** tab.

    The URL path part can be also used : /sources/connection/**[connection_name]**/incidents

    Remember to replace [connection_name] with your connection name, created in DQOps application.

3. In the **Webhooks for notifications of an incident state change** section, paste the Webhook URL that you get in the Slack app into the  “A new incident was opened (detected)” field. 

4. Save the changes by clicking the **Save button**. Now each time a new incident is open you will receive a notification in Slack.

    ![Configuring webhooks](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/webhook-fields.png)

    !!! tip "Use another statuses"

        If you would like to receive notification on statuses other than Open, fill in other input fields with Webhook URLs.

## Examples of notifications in Slack

Each notification type has a slightly different message.

### **New incident message example**
The notifications about a new data quality incident should be published to a dedicated Slack channel visible 
to the support team that is monitoring and assessing new data quality incidents. It could be the data quality team
or a 2nd level support team.

This type of notification should be configured in the **A new incident was opened (detected)** field on
the [webhook configuration](../webhooks/index.md) page. The Slack webhook URL for this parameter should
point to the Slack channel used by the 2nd level support team that is monitoring and reviewing new data quality incidents.

A new incident preview in Slack is shown below.

![slack-message-open](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/slack-message-open.png)

### **Acknowledged incident message example**
After the support team reviews a new data quality [incident](../../working-with-dqo/managing-data-quality-incidents-with-dqops.md) in DQOps,
the support user will change the status of a valid incident to **Acknowledged**.
Changing the incident status to **Acknowledged** is the next status in the incident management workflow, which means
that the incident was acknowledged (confirmed), and is assigned to the engineering team (the 3rd level support) to be fixed. 

This type of notification should be configured in the **An incident was acknowledged** field on
the [webhook configuration](../webhooks/index.md) page. The Slack webhook URL for this parameter should
point to the Slack channel used by the 3nd level support team that is fixing data quality incidents.

An acknowledged incident preview in Slack is shown below.

![slack-message-acknowledged](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-slack-notifications/slack-message-acknowledged.png)

The difference from the message published for a new incident is subtle.
Only the **Acknowledged**, **Resolved** and **Muted** status messages differ in the title.


## Set the incident base URL

The **View in DQOps** link will open an incident URL in DQOps. If your DQOps instance is running in production mode
and the DQOps web interface is assigned a host name, the base URL must be configured. The default base URL of DQOps
is derived from the port number configured using the [--server.port](../../command-line-interface/dqo.md#--server.port) parameter.
When no additional configuration is applied, the incident URL will look like *http://localhost:8888/incidents/bigquery-public-data/2023/10/70cc4354-344f-bf9c-9bab-34ffae2f1179*.

The *http://localhost:8888/* base URL can be configured when starting DQOps using
the [--dqo.instance.return-base-url](../../command-line-interface/dqo.md#--dqo.instance.return-base-url) startup parameter
as shown in the following example.

```
dqo> dqo --dqo.instance.return-base-url=https://dqops.mycompany.com/ run
```

The base URL for a DQOps Cloud SaaS instance is already configured, 
and the base address is *https://&lt;customer&gt;.us.dqops.com* for instances hosted in the USA. 

