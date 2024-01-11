# Daily monitoring of data quality 

## Overview 

Problem: Monitor data quality daily and get notified when an issue is found.

Solution: Set up daily monitoring of data quality checks, set up notifications and review the results on data quality dashboards.


## Edit default configuration of checks 

DQOps comes with a set of default configuration for **profiling** and **daily monitoring** checks that are automatically 
activated for every newly imported table. 

The [profiling checks](../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md) capture advanced data quality
statistics and store the most current value for each month. Their role is to monitor the overall quality of data,
without affecting the [data quality KPIs](../dqo-concepts/definition-of-data-quality-kpis.md).

The [monitoring checks](../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md) are the primary type of data quality checks
used in DQOps for continuous monitoring of the data quality of the data source.

You can learn more about each default check by clicking on the links below.

**Profiling checks type**

| Target | Check name                                                          | Description                                                                                                                                        |
|--------|---------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| table  | [profile row count](../checks/table/volume/row-count.md)         | Counts the number of rows in a table.                                                                                                              |
| table  | [profile column count](../checks/table/schema/column-count.md)   | Retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to an expected number of columns. |
| column | [profile nulls count](../checks/column/nulls/nulls-count.md)     | Ensures that there are no more than a set number of null values in the monitored column.                                                           |
| column | [profile nulls percent](../checks/column/nulls/nulls-percent.md) | Ensures that there are no more than a set percentage of null values in the monitored column.                                                       |

**Daily monitoring checks type**

| Target | Check name                                                                                                   | Description                                                                                                                                        |
|--------|--------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| table  | [daily row count](../checks/table/volume/row-count.md)                                                    | Counts the number of rows in a table.                                                                                                              |
| table  | [daily row count change](../checks/table/volume/row-count-change.md)                                      | Ensures that the row count changed by a fixed rate since the last readout.                                                                         |
| table  | [daily row count anomaly](../checks/table/volume/row-count-anomaly.md)                                    | Ensures that the row count is within a two-tailed percentile from measurements made during the last 90 days.                                       |
| table  | [daily column count](../checks/table/schema/column-count.md)                                              | Retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to an expected number of columns. |
| table  | [daily table availability](../checks/table/availability/table-availability.md)                            | Verifies that a table exists, can be accessed, and queried without errors.                                                                         |
| table  | [daily column count changed](../checks/table/schema/column-count-changed.md)                              | Detects whether the number of columns in a table has changed since the last time the check (checkpoint) was run.                                   |
| table  | [daily column list changed](../checks/table/schema/column-list-changed.md)                                | Detects if the list of columns has changed since the last time the check was run.                                                                  |
| table  | [daily column list or order changed](../checks/table/schema/column-list-or-order-changed.md)              | Detects whether the list of columns and the order of columns have changed since the last time the check was run.                                   |
| table  | [daily column types changed](../checks/table/schema/column-types-changed.md)                              | Detects if the column names or column types have changed since the last time the check was run.                                                    |
| column | [daily nulls count](../checks/column/nulls/nulls-count.md)                                                | Ensures that there are no more than a set number of null values in the monitored column.                                                           |
| column | [daily nulls percent](../checks/column/nulls/nulls-percent.md)                                            | Ensures that there are no more than a set percentage of null values in the monitored column.                                                       |
| column | [daily nulls percent anomaly](../checks/column/nulls/nulls-percent-anomaly.md)                            | Ensures that the null percent value in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.        |
| column | [daily nulls percent change 1 day](../checks/column/nulls/nulls-percent-change-1-day.md)                  | Ensures that the null percent in a monitored column has changed by a fixed rate since the last readout from yesterday.                             |
| column | [daily not nulls percent](../checks/column/nulls/not-nulls-percent.md)                                    | Ensures that there are no more than a set percentage of not null values in the monitored column.                                                   |
| column | [daily detected datatype in text changed](../checks/column/datatype/detected-datatype-in-text-changed.md) | Scans all values in a string column and detects the data type of all values in a column.                                                           |
| column | [daily mean anomaly](../checks/column/anomaly/mean-anomaly.md)                                            | Ensures that the mean value in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.                |
| column | [daily sum anomaly](../checks/column/anomaly/sum-anomaly.md)                                              | Ensures that the sum in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.                       |
| column | [daily column exists](../checks/column/schema/column-exists.md)                                           | Reads the metadata of the monitored table and verifies that the column still exists in the data source.                                            |
| column | [daily column type changed](../checks/column/schema/column-type-changed.md)                               | Detects if the data type of the column has changed since the last time it was retrieved.                                                           |

You can modify the default list of checks either through the user interface or by modifying the configuration file.

### **Edit default checks configuration with the user interface**

To review and edit the default checks configuration using the user interface, navigate to the **Configuration** section.
Then, select the **Profiling check**, **Monitoring daily** or **Monitoring monthly** options from the **Default checks configuration** folder 
in the tree view on the left.

![Navigate to default check configuration](https://dqops.com/docs/images/examples/detect-empty-tables-check-default-check-configuration.png)

The screen on the right will display the current configuration of checks, which can be modified. 
You can switch between the Table and Column checks using the tabs. For more information check the description of the [check editor screen](../dqo-concepts/user-interface-overview.md#Check-editor).

After making the necessary modifications, click on the **Save** button to save the changes. The new default checks configuration will be 
set on every newly imported table. 

### **Modification of the defaultchecks.dqochecks.yaml file**

The default checks configuration can be also modified by editing the configuration file `defaultchecks.dqochecks.yaml` in Visual Studio Code.
The file is located in the `userhome/settings/` folder.

Thanks to the DQOps YAML schema files, configuring data quality checks in Visual Studio Code is very simple. 
Code completion, syntax validation and help hints are shown by Visual Studio Code and many other editors when editing DQOps YAML files.
You can read more about [integration of DQOps with Visual Studio Code](../integrations/visual-studio-code/index.md).

Below is an example of default checks configuration in the `defaultchecks.dqochecks.yaml` file. 

```yaml
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/DefaultObservabilityChecksYaml-schema.json
apiVersion: dqo/v1
kind: default_checks
spec:
  profiling:
    table:
      volume:
        profile_row_count:
          warning:
            min_count: 1
      schema:
        profile_column_count: {}
    column:
      nulls:
        profile_nulls_count:
          warning:
            max_count: 1
        profile_nulls_percent: {}
        profile_not_nulls_count:
          warning:
            min_count: 1
  monitoring_daily:
    table:
      volume:
        daily_row_count:
          warning:
            min_count: 1
        daily_row_count_change:
          warning:
            max_percent: 10.0
        daily_row_count_anomaly:
          warning:
            anomaly_percent: 1.0
      availability:
        daily_table_availability:
          warning:
            max_failures: 0
      schema:
        daily_column_count_changed:
          warning: {}
        daily_column_list_changed:
          warning: {}
        daily_column_list_or_order_changed:
          warning: {}
        daily_column_types_changed:
          warning: {}
    column:
      nulls:
        daily_nulls_count: {}
        daily_nulls_percent: {}
        daily_nulls_percent_anomaly:
          warning:
            anomaly_percent: 1.0
        daily_nulls_percent_change_1_day:
          warning:
            max_percent: 10.0
            exact_day: false
        daily_not_nulls_count:
          warning:
            min_count: 1
        daily_not_nulls_percent: {}
      datatype:
        daily_detected_datatype_in_text_changed:
          warning: {}
      anomaly:
        daily_mean_anomaly:
          warning:
            anomaly_percent: 1.0
        daily_sum_anomaly:
          warning:
            anomaly_percent: 1.0
      schema:
        daily_column_exists:
          warning: {}
        daily_column_type_changed:
          warning: {}
```

## Import a new table

To import tables, you first need to have added connection. You can check [supported data sources and a guide how to add a new
connection here](../data-sources/index.md). 

To import source schemas and tables:

1. Go to the **Data Sources** section
2. Select the connection from the tree view on the left
3. Click on the **Import metadata** button on the right. 

    ![Importing metadata](https://dqops.com/docs/images/working-with-dqo/daily-monitoring-of-data-quality/importing-metadata.png)

4. Select the source schema name you want to import by clicking on the **Import tables** button.

    ![Importing schemas](https://dqops.com/docs/images/working-with-dqo/daily-monitoring-of-data-quality/importing-schemas.png)

5. Select the tables you want to import or import all tables using the buttons in the upper right corner.

    ![Importing tables](https://dqops.com/docs/images/working-with-dqo/daily-monitoring-of-data-quality/importing-tables.png)

When new tables are imported, DQOps automatically activates profiling and monitoring checks, and opens an Advisor 
that allows you to quickly collect basic statistics, run profiling checks or modify the schedule for newly imported tables.
But let's close the Advisor with the X button in the top right corner and verify the activation of the default checks.

![Importing tables - advisor](https://dqops.com/docs/images/working-with-dqo/daily-monitoring-of-data-quality/advisor.png)

## Verify activation of the default checks 

To verify the activation of the default checks, go to the **Monitoring checks** section and select the table of interest from
the tree view on the left. The main workspace on the right will show the check editor. 

![Default table checks](https://dqops.com/docs/images/working-with-dqo/daily-monitoring-of-data-quality/default-table-checks.png)

Similarly, you can view the column checks. Just select the column of interest from the tree view on the left. 

![Default table checks](https://dqops.com/docs/images/working-with-dqo/daily-monitoring-of-data-quality/default-column-checks.png)

## Verify schedule configuration

With DQOps, you can easily customize when checks are run by setting schedules. You can set schedules for an entire connection,
table, or individual check. When you import a new table, DQOPs automatically set the schedule to run check daily at 12:00 a.m.

To verify and modify the current schedule, go to **Data Source** section, select the data source of interest from the tree view on the left,
and click on the **Schedule** tab on the main workspace. 

![Default schedule](https://dqops.com/docs/images/working-with-dqo/daily-monitoring-of-data-quality/default-schedule.png)

## Run default checks

To run checks, navigate to the check editor: go to the **Profiling** or **Monitoring Checks** section, select a table or column from the
tree view on the left, click on the Profiling checks or Daily Checks.

![Navigating to the check editor](https://dqops.com/docs/images/working-with-dqo/daily-monitoring-of-data-quality/navigate-to-the-check-editor.png)

The table with data quality checks contains a list of checks divided into different data quality subcategories that you
can expand and collapse by clicking on an arrow. [Learn more about the different check subcategories.](../dqo-concepts/definition-of-data-quality-checks/index.md)

The right side of the table allows setting different threshold levels (severity levels). [Learn more about threshold levels ](../dqo-concepts/definition-of-data-quality-checks/index.md#issue-severity-levels)

On the left of the name of each check, there are several buttons and icons that allows you to:

- Activate and deactivate checks by using the Switch button,
- Disable checks,
- Access check **Settings**,
- Check schedule status,
- Run data quality checks,
- View detailed results for Checks, Sensor readouts, and Execution errors
- View detailed information about check,
- Check the results of the run check shown as a color square
      - Green for a valid result
      - Yellow for a warning
      - Orange for an error
      - Red for a fatal error
      - Black for execution error.

To run the check, just click on the **Run check** button.

![Run the profiling check](https://dqops.com/docs/images/working-with-dqo/daily-monitoring-of-data-quality/run-profiling-check.png)

You can also run all checks activated on connection or table. Click on the three dot icon next to the name of connection
or table and click on the **Run checks** option.

![Run all checks activated on connection](https://dqops.com/docs/images/working-with-dqo/daily-monitoring-of-data-quality/run-all-checks-activated-on-connection.png)

## Review the check results

To access the results click on the **Results** button.

![Review check results](https://dqops.com/docs/images/working-with-dqo/daily-monitoring-of-data-quality/review-check-results.png)


Within the Results window, you will see three categories: **Check results**, **Sensor readouts**, and **Execution errors**.
The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
The Sensor readouts category displays the values obtained by the sensors from the data source.
The Execution errors category displays any error that occurred during the check's execution.


## Review the Incidents 

With the help of DQOps, you can conveniently keep track of the issues that arise during data quality monitoring.
The Incidents section aggregates these issues into incident and allows you to view and filter them, as well as manage their status.

To review the if incidents go to the **Incidents** section. On the left side of this section, there is a list displaying
the connections and the number of incidents that have occurred for each one. On the right panel, you can view incidents 
for the connections you have selected, and you can filter, sort, and view detailed information about the incidents.

![Navigating to the Incidents section](https://dqops.com/docs/images/working-with-dqo/daily-monitoring-of-data-quality/navigating-to-incidents-section.png)

We can see that we have one incident with 10 data quality issues. To review more details click on the link in the
**Data quality issue grouping** column.

![Incidents details](https://dqops.com/docs/images/working-with-dqo/daily-monitoring-of-data-quality/incidents-details.png)

Read the full [description of the Incidents section here](managing-data-quality-incidents-with-dqops.md).


## Configure notifications

DQOps can send alert notifications whenever a new incident is created or modified. Notifications allow you to monitor 
data in real-time and receive alerts when active data quality checks detects an issue.

### **Configure Webhooks**

To configure Webhooks, follow these steps:

1. Go to the **Data Sources** section.
2. Select the relevant data source.
3. Select the **Incidents And Notifications** tab.
4. Enter the Webhooks URL in the specified input fields and save your changes using the **Save** button.

![Configuring webhooks](https://dqops.com/docs/images/working-with-dqo/daily-monitoring-of-data-quality/configuring-webhooks.png)

Follow the link for [more information about Webhooks](../integrations/webhooks/index.md). 


### **Configure Slack notifications**

DQOps supports integration with Slack Webhooks which are used to set up in-app Slack notifications.

Click the link for more information [how to set up in-app Slack notifications](../integrations/slack/configuring-slack-notifications.md).


## Review the check results on dashboards

DQOps has multiple built-in data quality dashboards dedicated to analyzing results from
data quality checks. You can [learn more about different types of dashboards](../dqo-concepts/types-of-data-quality-dashboards.md)
in the Concept section.

To be able to display results on data quality dashboards, you need to have a DQOps account and synchronize locally stored results with it.  
To synchronize the data just click on the **Synchronize** button in the upper right corner of the navigation tab.

To view dashboards, simply go to the **Data Quality Dashboard** section, and select the dashboard of interest from the tree
view on the left.

Below are examples of several dashboards.

### **Current table status dashboards**

**Current table status** dashboards allow data engineers and data owners to quickly evaluate the data quality of monitored
tables and columns. The dashboards display a color-coded status that indicates the severity level detected by run
checks. When the status is green, it means that the monitored column has no data quality issues. However, if the status
is yellow, orange, or red, it indicates that there were some issues detected. The dashboard also displays the number
of detected issues per severity threshold, making it easier to identify and address tables and columns with issues.

These dashboards allow filtering data by:

* time frame,
* connection,
* schema,
* data quality dimension,
* check category,
* data group,
* table,
* column.

**Current table status per data quality dimension** dashboard is a representative of this group.

![Current table status per data quality dimension](https://dqops.com/docs/images/concepts/data-quality-dashboards/current-table-status-per-data-quality-dimension.png)


### **Data quality KPIs**

**Data quality KPIs** dashboards show high-level data quality KPIs, aggregated on a macro scale that can be shared at a
corporate level. DQOps calculates data quality KPIs as a percentage of passed [data quality checks](../dqo-concepts/definition-of-data-quality-checks/index.md)
for each connection, schema, data grouping, etc.

With these dashboards, senior management can review a summary of data quality KPIs or evaluate the KPIs per table, column,
[data quality dimensions](../dqo-concepts/data-quality-dimensions.md),
[check category](../dqo-concepts/definition-of-data-quality-checks/index.md), [data grouping](../dqo-concepts/measuring-data-quality-with-data-grouping.md) and day.

**KPIs per table and day** is a representative of this group. It allows reviewing data quality KPIs per table
and day of the month.

This dashboard allows filtering data by:

* current and previous month,
* connection,
* schema,
* data group,
* stage,
* priority,
* data quality dimension,
* check category,
* check name,
* column
* table,
* day of the month.

![KPIs per table and day](https://dqops.com/docs/images/concepts/data-quality-dashboards/kpis-per-table-and-day.png)

### **Data quality issues count**

**Data quality issues** dashboards allow evaluation of the number issues and its severity level per table, or per check category.
Another two types of dashboards in this subgroup allows reviewing the total number of failed checks (warning, error, fatal)
per day of the month, or per table and day of the month.

**Data quality failed checks per table and day** is a representative of this group.

This dashboard allows filtering data by:

* severity
* current and previous month,
* connection,
* schema,
* stage
* priority,
* data group,
* data quality dimension,
* check category,
* check name,
* table,
* day of the month.

![Data quality failed checks per table and day](https://dqops.com/docs/images/concepts/data-quality-dashboards/data-quality-failed-checks-per-table-and-day.png)


## What's next

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../dqops-installation/run-dqops-as-docker-container.md).
- DQOps provide you with summary statistics about your table and column. This information can be valuable in deciding which data quality checks and threshold levels should be set to monitor data quality. For more details about [Basic data statistics, click here](collecting-basic-data-statistics.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](set-up-data-grouping-for-data-quality-checks.md) can help you to calculate separate data quality KPI scores for different groups of rows.