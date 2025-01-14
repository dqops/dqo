---
title: How to Send Data Quality Notifications to Webhooks, with Examples
---
# How to Send Data Quality Notifications to Webhooks, with Examples
This guide describes how to integrate DQOps with external ticketing systems or send data quality incident notifications, for example to Microsoft Teams.

## Overview

DQOps can send alert notifications whenever a new incident is created or modified.

An incident aggregates data quality issues based on different categories such as a table, data quality, dimension, check category,
or check type. Thanks to the issue grouping the number of notifications is reduced. 
You can read [more about incidents and their configuration here](../../working-with-dqo/managing-data-quality-incidents-with-dqops.md).

Notifications allow you to monitor data in real-time and receive alerts when active data quality checks exceed alerting thresholds.

A notification contains the most important information about an incident you may want to review in order to resolve an issue.


## Configure notifications

You can receive a notification with use of **webhooks** to receive notifications for new data quality incidents or changes in the incident status.

To configure a webhook notification, follow these steps:

1. Go to the **Configuration** section.
2. Open the **Global incident notifications** in tree and select the **default**.  

    ![Connection notifications configuration](https://dqops.com/docs/images/concepts/grouping-data-quality-issues-to-incidents/global-incident-notifications.png){ loading=lazy; width="1200px" }

3. Enter the Webhooks URL in the specified input fields and save your changes using the **Save** button.

    ![Global incident notifications default](https://dqops.com/docs/images/concepts/grouping-data-quality-issues-to-incidents/global-incident-notification-default.png){ loading=lazy; width="1200px" }


## Types of webhooks configuration

Configuration of webhooks in DQOps platform can be done in two different ways.

First, is to configure webhooks on a connection level under the Notifications and Incidents tab. 
Second, is the default webhooks configuration available under Configuration menu.

DQOps uses both of them when preparing notifications.
When the connection does not set the webhook link on it's level,
the corresponding value from default webhooks is set (e.g. opened incident webhook value).


## Content of notification message

An incident notification message contains complete information about the incident. 
In DQOps the notification is a message payload that is posted through an HTTP POST request to a notification endpoint. 
A payload has a JSON format with multiple fields that together create a message.

The fields of a JSON payload include the following:

| Field name              | Description                                                                                                                                                                                                                                                                                                 | Type     | 
|-------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------|
| **incident_id**         | The primary key that identifies each data quality incident.                                                                                                                                                                                                                                                 | String   |
| **connection**          | Connection name affected by a data quality incident.                                                                                                                                                                                                                                                        | String   |
| **schema**              | Schema name affected by a data quality incident.                                                                                                                                                                                                                                                            | String   |
| **table**               | Table name affected by a data quality incident.                                                                                                                                                                                                                                                             | String   |
| **table_priority**      | Table priority of the table that was affected by a data quality incident.                                                                                                                                                                                                                                   | Integer  |
| **incident_hash**       | Data quality incident hash that identifies similar incidents on the same incident grouping level.                                                                                                                                                                                                           | Long     |
| **first_seen**          | The UTC timestamp when the data quality incident was first seen.                                                                                                                                                                                                                                            | Datetime |
| **last_seen**           | The UTC timestamp when the data quality incident was last seen.                                                                                                                                                                                                                                             | Datetime  |
| **incident_until**      | The UTC timestamp when the data quality incident is valid. Any failed data quality checks that occur before this date will be included in the incident, unless the incident status is changed to resolved. If a status is changed to resolved, a new failed data quality checks will create a new incident. | Datetime  |
| **data_group_name**     | The data group name that was affected by a data quality incident.                                                                                                                                                                                                                                           | String   |
| **quality_dimension**   | The data quality dimension that was affected by a data quality incident.                                                                                                                                                                                                                                    | String   |
| **check_category**      | The data quality check category that was affected by a data quality incident.                                                                                                                                                                                                                               | String   |
| **highest_severity**    | The highest severity of the failed check detected in this data quality incident. Possible values include warning, error and fatal.                                                                                                                                                                          | Integer  |
| **failed_checks_count** | The total number of failed data quality checks that were seen when the incident was raised for the first time.                                                                                                                                                                                              | String   |
| **issue_url**           | The link (URL) to a ticket in an external system that is tracking this incident.                                                                                                                                                                                                                            | String   |
| **status**              | Possible values for incident status include open, acknowledged, resolved and muted.                                                                                                                                                                                                                         | String   |
| **message**             | Additional message of the notification message configured by user from the filtered notifications. Default notificaitons does not contain such field.                                                                                                                                                       | String   |
| **text**                | Notification text in Markdown format that contains the most important fields from the payload.                                                                                                                                                                                                              | String   |


The json object presents as below:

``` json
{
    "incident_id": "588af5cf-8ab9-a296-0af4-126772dbb2c7",
    "connection": "first-connection",
    "schema": "maven_restaurant_ratings",
    "table": "consumers",
    "incident_hash": 6380182093858185878,
    "first_seen": "2023-09-04T10:13:19.255Z",
    "last_seen": "2023-09-04T12:35:51.327Z",
    "incident_until": "2023-11-03T10:13:19.255Z",
    "quality_dimension": "Reasonableness",
    "check_category": "volume",
    "highest_severity": 3,
    "failed_checks_count": 10,
    "status": "acknowledged",
    "message": "An informative message about the incident",
    "text": "> *The incident in <http://localhost:8888/sources/connection/first-connection/schema/maven_restaurant_ratings/table/consumers/detail|maven_restaurant_ratings.consumers> table has been acknowledged.* \n>  \n> *First seen*: 2023-09-04 12:13:19 (GTM+2) \n> *Last seen*: 2023-09-04 14:35:51 (GTM+2) \n> *Quality dimension*: Reasonableness \n> *Check category*: volume \n> *Highest severity*: fatal \n> *Total data quality issues*: 10 \n>  \n> <http://localhost:8888/incidents/first-connection/2023/9/588af5cf-8ab9-a296-0af4-126772dbb2c7| *View in DQOps*> \n"
}
```

## Notification message statuses

Notification message can have the following status:

- **Open**: This is the initial status of a new incident, which is automatically assigned by DQOps. The remaining statuses of the incidents (Acknowledged, Resolved, and Muted) have to be set manually. An open incident indicates that a data quality team together with the data owner or someone else responsible for identifying issues should take action to address the data quality issue. You can change an incident's status back to Open at any time.
- **Acknowledged**: This status indicates that the incident has been acknowledged, and someone has begun to investigate the data quality issue. The Acknowledged status has to be manually marked in the Incident section. Usually, the data quality issues present at the data source level can be fixed by the data producer. Issues caused by a bug in the data pipeline or an ETL process should be fixed by the Data Engineering Team.
- **Resolved**: This status indicates that the root cause of the issue has been identified and fixed. The resolved status has to be set manually in the Incident section.
- **Muted**: This status hides the incident from your list. By default, any data quality issues associated with the incident will be muted for 60 days. If an incident is muted, DQOps will not create a new one for 60 days. You can change the duration for muted incidents, by simply clicking the **Configure button** and changing the **Incidents and Notifications** settings. Muting the incident might be useful, when, for example, vendor’s data has been intentionally changed in comparison to the previous version.


## Text field content and format

Payload's text field is built with the use of the following data:

- **Full table name**: concatenated field from schema and table name.
- **Status**: One of four resolution statuses (open, acknowledged, resolved and muted).
- **Datetimes of the incident**: UTC date times with offset specific to local user setup. Values of JSON fields firstSeen and lastSeen.
- **Data quality dimension**: Name of the dimension. [Read more](../../dqo-concepts/data-quality-dimensions.md)
- **Highest severity level**: A severity level from the data quality rule. [Read more](../../dqo-concepts/definition-of-data-quality-checks/index.md)
- **Total data quality issues**: A value from failedChecksCount JSON field.
- **Message**: Additional message of the notification message configured by user from the filtered notifications. Default notifications does not contain such field.
- **Links**: Quick access links.
 
**Links**

In the text field you can find links to:

  - **incident**: the "View in DQOps" link at the bottom of a message that redirects to the incident in the DQOps application.
  - **full table name**: the full table name present in the message header that leads to the table details in the DQOps application.
  - **issue URL**: a link to an external ticketing system. It is present when set manually on the incident. Check the [External system linking](#external-system-linking) section.

**Markdown format**

The text field uses elements of the Markdown language, which include:

- **Blockquotes**: the **>** character in front of the paragraph
- **Emphasis (Bold)**: two asterisks ** around a word or phrase
- **Links**: the format looks as **< link | text >** 

If you would like to use formatted text for notification make sure your tool supports at least those elements of Markdown format.

Below is the example of raw text message in the Markdown language which you can test it in your tools:

``` markdown 
> **The incident in <http://localhost:8888/sources/connection/your_connection_name/schema/your_schema_name/table/your_table_name/detail | your_schema_name.your_table_name> table has been acknowledged.**
>
> **First seen**: 2023-09-01 14:30:20 (GMT+2)
> **Last seen**: 2023-09-02 14:31:28 (GMT+2)
> **Quality dimension**: Reasonableness
> **Check category**: volume
> **Highest severity**: fatal
> **Total data quality issues**: 10
> **Table priority**: 2
> **Issue url**: <https://www.your_fancy_ticketing_system.com/ | LINK>
>
> <http://localhost:8888/incidents/your_connection_name/2023/9/1 | **View in DQOps**>
```

## Linking incidents to external ticketing systems

A notification has a special field called **issue URL**, which allows user to add a URL from an external ticketing system. 
The issue URL can be added at any time. 
Adding an Issue URL to an incident provides easy access to the issue in the ticketing system.

### Manipulate the notification incidents with Python webhook service

The incident notification message can be pushed to a microservice webhook that handles the notification in an approachable way.
This allows you to apply a custom logic according to your business requirements.

DQOps provides an example of the Python Flask REST API application that works as a webhook service for incident notifications.

The below example shows how to retrieve the table name, the incident notification status and additional data about the incident notification.

```python
from flask import Flask, request
from dqops.client.models.table_current_data_quality_status_model import (
    TableCurrentDataQualityStatusModel
)
from incident_notification_message import IncidentNotificationMessage

app = Flask(__name__)

@app.post("/pushnotification")
def sent_incident_notification():

    json_body = request.get_json()
    incident_notification_message: IncidentNotificationMessage = IncidentNotificationMessage().from_dict(json_body)

    print("The table's " + incident_notification_message.connection + "." + incident_notification_message.schema + "." + incident_notification_message.table + " incident status is " + incident_notification_message.status + "\n" +
        "First seen: " + incident_notification_message.first_seen + "\n" +
        "Last seen: " + incident_notification_message.last_seen + "\n" +
        "Quality dimension: " + incident_notification_message.quality_dimension + "\n" +
        "Check category: " + incident_notification_message.check_category + "\n" +
        "Highest severity: " + str(incident_notification_message.highest_severity) + "\n" +
        "Total data quality issues: " + str(incident_notification_message.failed_checks_count))

    return ""

if __name__ == '__main__':
    app.run()
```

When the webhook is configured in DQOps by providing the Flask address on the notification configuration page, 
e.g. http://localhost:5000/pushnotification, the received notification will show a similar to the below message:   

```text
The table's bigquery-public-data.country_codes.country_codes incident status is acknowledged
First seen: 2024-09-10T07:00:45.034Z
Last seen: 2024-10-04T07:00:43.598Z
Quality dimension: Validity
Check category: patterns
Highest severity: 2
Total data quality issues: 10
```

The complete solution of the example is available at the DQOps' repository in Github.

https://github.com/dqops/dqo/tree/develop/examples/incident-notification/incident-notification-wrapper-template


## Next steps

- Read about [detailed notifications configuration](../../dqo-concepts/grouping-data-quality-issues-to-incidents.md)
- Now that you have learned about notifications, [set up Slack integration](../slack/configuring-slack-notifications.md) to receive them directly in Slack.
- Learn how the [data quality incident workflow](../../dqo-concepts/grouping-data-quality-issues-to-incidents.md) is managed
  by DQOps by grouping similar data quality issues into data quality incidents.
- Learn about data quality incident management, grouping and notifications [grouping-data-quality-issues-to-incidents.md](../../dqo-concepts/grouping-data-quality-issues-to-incidents.md)