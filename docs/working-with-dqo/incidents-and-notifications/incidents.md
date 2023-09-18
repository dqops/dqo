# Incidents overview

With the help of DQO, you can conveniently keep track of the issues that arise during data quality monitoring. The Incidents section aggregates
these issues into incident and allows you to view and filter them, as well as manage their status.

Issues can be grouped into incidents based on the following categories:

- Table
- Table dimension
- Table dimension category
- Table dimension category type
- Table dimension category name

## Configure incidents

Incidents are the default function of DQO and automatically groups issues.

To modify the settings of the Incidents, follow these steps:

1. Go to the **Data Sources** section.
2. Select the relevant data source.
3. Select the **Incidents And Notifications** tab.

   ![Incidents And Notifications tab](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/incidents-and-notifications-settings.png)

4. Update the settings and click the **Save** button

On the Incidents and Notifications tab, you can customize:

- The level of grouping for data quality incidents.
- The minimum severity level required for generating an incident.
- Whether incidents should be created for the entire data source or for each data stream separately.
- The maximum duration of an incident in days. After this time, DQO creates a new incident.
- The time duration for muted incidents. If the incident is muted, DQO will not create a new one.

## View and manage Incidents

To access a summary of incidents that occur during data quality monitoring, navigate to the **Incidents** section.

![Incidents screen](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/incidents-screen.png)

On the left side of this screen, there is a list displaying the connections and the number of incidents that have
occurred for each one. On the right panel, you can view incidents for the connections you have selected, and you can
filter, sort, and view detailed information about the incidents.

For each incident the following information is provided:

- **Resolution status**: A status assigned to the incident (Open, Acknowledged, Resolved or Muted)
- **Failed check count**: The number of times the check has occurred in the selected time range
- **Schema**: Name of the schema in which data quality issues were detected
- **Table**: Name of the table in which data quality issues were detected
- **Checks**: Name of the failed data quality check or a group of checks. Click this link to view details of the incident.
- **First seen**: The time at which this issue first appeared.
- **Last seen**: The time at which this issue last occurred.
- **Issue link**: A link to the issue tracker

By default, incidents are grouped by table name. To change the grouping method click the **Configure** button in the upper right which
will link you to the **Incidents and Notifications** tab in the **Data Source** section.
Change the level of grouping for data quality incidents and click the **Save** button.

### Filter incidents

Use filtering to restrict the types of issue groups that appear in the list.

You can filter the list of incidents **by the resolution status** by selecting or deselecting status type in the resolution status filter.

![Resolution status filter](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/resolution-status-filter.png)

You can filter the list of incidents **by the keyword**, such as schema, table or check name, by typing it in the input box.

![Input box filter](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/input-box-filter.png)

You can restrict the incidents that appear in the list to a **specific time range**: current month, last 2 months, or last 3
months using the time range filter buttons.

![Time range filter](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/time-range-filter.png)

### Sort incidents

You can sort the incidents table. Simply click on the sorting icon next to any column header to sort the incidents table by that column.

![Sorting issues](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/sort-incidents.png)

### Change the resolution status

To help you manage the incidents, each of them can have the following resolution status values:

- **Open**: The default initial state of all incidents. The other states are set manually. You can revert an incident's status back to Open at any time.
- **Acknowledged**: A state indicating that the incident was acknowledged.
- **Resolved**: A state indicating that an incident is fixed.
- **Muted**: A state for hiding the incident from your list. By default, any data quality issues associated with that
  incident will be muted for 60 days. If an incident is muted, DQO will not create a new one. To change the time duration for muted incidents
  click the **Configure** button.

![Resolution status](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/resolution-status.png)

To change the resolution status, simply chose the status from the dropdown menu.

### Add Issue link

You can add an **issue tracker link** to an incident by clicking the "+" button in the **Issue Link** column.

![Issue Link](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/issue-link2.png)


## View details of the incident

To view detailed data quality issues of the incident, click the link in the **Checks** column.

![Incident details](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/incident-details.png)

The detailed data quality incident screen shows a list of data quality issues which are grouped in the incident and allows you to
filter and sort them.

![Incident details screen](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/incident-details-screen.png)


### Filter data quality checks

Use filtering to restrict the types of data quality issues that appear in the list and on the bar chart.

You can filter the list of issues **by the keyword** by typing it in the input box.

![Detailed input box filter](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/details-input-box-filter.png)

You can use time range filter buttons to show only the data quality issues from the last day, 7 days, 30 days, or view all issues.

![Detailed time range filter](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/details-time-range-filter.png)

You can filter the list of issues **by column** or **check name** by clicking on the name of the column or the check name in the appropriate boxes.

![Detailed input box filter](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/details-column-or-check-filter.png)

### Sort incidents

To sort the issue table, simply click on the sorting icon next to any column header.

![Sorting issues](https://dqops.com/docs/images/working-with-dqo/incidents-and-notifications/sort-issues.png)