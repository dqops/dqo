# Notifications overview

DQO has built-in notifications. It means to you that you can follow you data quality in real time, without need to look through your tables.

A notifications contains the most important information about an incident you may want to discover in order to resolution.


## Configure notifications

You can send a notification about new data quality incident or when the incident state changes using Webhooks.

To configure Webhooks, follow these steps:

1. Go to the **Data Sources** section.
2. Select the relevant data source.
3. Select the **Incidents And Notifications** tab.
4. Enter the Webhooks URL in the specified input fields and save your changes using the **Save** button.

![Configuring webhooks](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/configuring-webhooks.png)

## Notification message content

An incident notification message contains complete information about the incident. 
In DQO the notification is a message payload that is posted through HTTP POST request to a notification endpoint. 
A payload has a json format with multiple fields that together create a message.

The fields of a json payload include the following:

| Field name              | Description                                                                                                                                                                                                                                         | Type     | 
|-------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------|
| **incident_id**         | The primary key that identifies each data quality incident.                                                                                                                                                                                         | String   |
| **connection**          | Connection name affected by a data quality incident.                                                                                                                                                                                                | String   |
| **schema**              | Schema name affected by a data quality incident.                                                                                                                                                                                                    | String   |
| **table**               | Table name affected by a data quality incident.                                                                                                                                                                                                     | String   |
| **table_priority**      | Table priority of the table that was affected by a data quality incident.                                                                                                                                                                           | Integer  |
| **incident_hash**       | Data quality incident hash that identifies similar incidents on the same incident grouping level.                                                                                                                                                   | Long     |
| **first_seen**          | The UTC timestamp when the data quality incident was first seen.                                                                                                                                                                                    | Datetime |
| **last_seen**           | The UTC timestamp when the data quality incident was last seen.                                                                                                                                                                                     | Datetime  |
| **incident_until**      | The UTC timestamp when the data quality incident is valid until. All new failed data quality check results until that date will be included in this incident, unless the incident status is changed to resolved, so a new incident must be created. | Datetime  |
| **data_stream_name**    | The data stream name that was affected by a data quality incident.                                                                                                                                                                                  | String   |
| **quality_dimension**   | The data quality dimension that was affected by a data quality incident.                                                                                                                                                                            | String   |
| **check_category**      | The data quality check category that was affected by a data quality incident.                                                                                                                                                                       | String   |
| **highest_severity**    | The highest failed check severity that was detected as part of this data quality incident. Possible values are: 1 - warning, 2 - error, 3 - fatal.                                                                                                  | Integer  |
| **failed_checks_count** | The total number of failed data quality checks that were seen when the incident was raised for the first time.                                                                                                                                      | String   |
| **issue_url**           | The link (url) to a ticket in an external system that is tracking this incident.                                                                                                                                                                    | String   |
| **status**              | Incident status, one of possible values: open, acknowledged, resolved, muted                                                                                                                                                                        | String   |
| **text**                | Notification text in Markdown format that contains the most important fields from the payload.                                                                                                                                                      | String   |


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
    "text": "> *The incident in <http://localhost:8888/sources/connection/first-connection/schema/maven_restaurant_ratings/table/consumers/detail|maven_restaurant_ratings.consumers> table has been acknowledged.* \n>  \n> *First seen*: 2023-09-04 12:13:19 (GTM+2) \n> *Last seen*: 2023-09-04 14:35:51 (GTM+2) \n> *Quality dimension*: Reasonableness \n> *Check category*: volume \n> *Highest severity*: fatal \n> *Total data quality issues*: 10 \n>  \n> <http://localhost:8888/incidents/first-connection/2023/9/588af5cf-8ab9-a296-0af4-126772dbb2c7| *View in DQOps*> \n"
}
```

## Resolution status

Notification message distinguishes the following resolution status values:

- **Open**: This is a status a new incident starts its existence with. The system automatically marks incidents with this status. DQO is waiting until an action is manually taken by a data asset manager or someone responsible for recognition of issues. You can revert an incident's status back to Open at any time.


- **Acknowledged**: A state indicating that the incident was acknowledged. It has to be verified by a data engineers teem to decide about further steps. It is for a DQO user to decide if it should be treated as indecent or not. As an example imagine verification the data delivered by a vendor that made a change in its data which influenced sensor readouts in order to fail rule outputs or a data engineers are executing some pipelines for testing purposes.


- **Resolved**: A state indicating that an incident is fixed. Data has been fixed and data quality improved.


- **Muted**: A state for hiding the incident from your list. By default, any data quality issues associated with that
  incident will be muted for 60 days. If an incident is muted, DQO will not create a new one. To change the time duration for muted incidents
  click the **Configure** button.


## Text field content and format

Payload's text field is built with use of data such as:

- **Full table name**: concatenated field from schema and table name.
- **Status**: One of four resolution status.
- **Date times of incident**: UTC date times with offset specific to local user setup. Values of json fields firstSeen and lastSeen.
- **Data quality dimension**: Name of dimension. [Read more](../../dqo-concepts/data-quality-dimensions/data-quality-dimensions.md)
- **Highest severity level**: A severity level from data quality rule. [Read more](../../dqo-concepts/checks/index.md)
- **Total data quality issues**: A value from failedChecksCount json field.
- **Links**: Quick access links.
 
**Links**

In the text field you can find links to:

  - **an incident**: a "View in DQOps" link on the bottom of a message that forwards straight to the incident in DQO application.
  - **a full table name**: Full table name present in the header of a message that forwards to table details in DQO application.
  - **an issue url**: Link to an external ticketing system. It will be present when set manually on an incident. Check the [External system linking](#external-system-linking) section.

### Markdown format

The text field use elements of Markdown which includes:

- **Blockquotes**: the **>** character 
- **Emphasis (Bold)**: the ** characters
- **Links**: the format looks as **< link | text >** 

If you would like to use formatted text for notification make sure your tool supports at least those elements of Markdown format.

Raw text field content will look like this, you can test it in your tools:

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

## External system linking

A notification has an especial field called **issue url**, so user can store an url to an external ticketing system. It allows to save a handy link that kind of system in incident. 

A new incident comes with an empty issue url field. Issue url field can be filled on an existing incident any time.


## What's next

- [Check the notification configuration with Slack](configuring-slack-notifications.md)