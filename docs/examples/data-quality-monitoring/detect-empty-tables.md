# Detect empty tables

## Overview

The following example shows how to detect empty tables using the default data quality checks which are activated in DQOps 
once new tables are imported.

**PROBLEM**

[Austin-311-Public-Data](https://data.austintexas.gov/Utilities-and-City-Services/Austin-311-Public-Data/xwdj-i9he) 
provides the residents of Austin with a simple single point of contact for every city department.

What started as police non-emergency line for the City of Austin has become a robust Citywide Information Center
where ambassadors are available to answer residents’ concerns 24 hours a day, 7 days a week, and 365 days a year.

We want to detect if the newly imported table contains rows.

**SOLUTION**

Using default monitoring [daily_row_count](../../checks/table/volume/row-count.md) table check, we will
verify if the table `311_servie_request` is not empty. The default threshold of [daily_row_count](../../checks/table/volume/row-count.md) is set as warning 1. 

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the newly imported column is empty a warning alert will be triggered.


## Default checks configuration 

Once new tables are imported, DQOps automatically activates the following profiling and monitoring checks.
To learn more about each check, click on the links below.

**Profiling checks type**

| Target | Check name                                                          | Description                                                                                                                                        |
|--------|---------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| table  | [profile row count](../../checks/table/volume/row-count.md)         | Counts the number of rows in a table.                                                                                                              |
| table  | [profile column count](../../checks/table/schema/column-count.md)   | Retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to an expected number of columns. |
| column | [profile nulls count](../../checks/column/nulls/nulls-count.md)     | Ensures that there are no more than a set number of null values in the monitored column.                                                           |
| column | [profile nulls percent](../../checks/column/nulls/nulls-percent.md) | Ensures that there are no more than a set percentage of null values in the monitored column.                                                       |

**Daily monitoring checks type**

| Target | Check name                                                                                                   | Description                                                                                                                                 |
|--------|--------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------|
| table  | [daily row count](../../checks/table/volume/row-count.md)                                                    | Counts the number of rows in a table.                                                                                                       |
| table  | [daily row count change](../../checks/table/volume/row-count-change.md)                                      | Ensures that the row count changed by a fixed rate since the last readout.                                                                  |
| table  | [daily row count anomaly](../../checks/table/volume/row-count-anomaly.md)                                    | Ensures that the row count is within a two-tailed percentile from measurements made during the last 90 days.                                |
| table  | [daily table availability](../../checks/table/availability/table-availability.md)                            | Verifies that a table exists, can be accessed, and queried without errors.                                                                  |
| table  | [daily column count changed](../../checks/table/schema/column-count-changed.md)                              | Detects whether the number of columns in a table has changed since the last time the check (checkpoint) was run.                            |
| table  | [daily column list changed](../../checks/table/schema/column-list-changed.md)                                | Detects if the list of columns has changed since the last time the check was run.                                                           |
| table  | [daily column list or order changed](../../checks/table/schema/column-list-or-order-changed.md)              | Detects whether the list of columns and the order of columns have changed since the last time the check was run.                            |
| table  | [daily column types changed](../../checks/table/schema/column-types-changed.md)                              | Detects if the column names or column types have changed since the last time the check was run.                                             |
| column | [daily nulls count](../../checks/column/nulls/nulls-count.md)                                                | Ensures that there are no more than a set number of null values in the monitored column.                                                    |
| column | [daily nulls percent](../../checks/column/nulls/nulls-percent.md)                                            | Ensures that there are no more than a set percentage of null values in the monitored column.                                                |
| column | [daily nulls percent anomaly](../../checks/column/nulls/nulls-percent-anomaly.md)                            | Ensures that the null percent value in a monitored column is within a two-tailed percentile from measurements made during the last 90 days. |
| column | [daily nulls percent change 1 day](../../checks/column/nulls/nulls-percent-change-1-day.md)                  | Ensures that the null percent in a monitored column has changed by a fixed rate since the last readout from yesterday.                      |
| column | [daily not nulls percent](../../checks/column/nulls/not-nulls-percent.md)                                    | Ensures that there are no more than a set percentage of not null values in the monitored column.                                            |
| column | [daily detected datatype in text changed](../../checks/column/datatype/detected-datatype-in-text-changed.md) | Scans all values in a string column and detects the data type of all values in a column.                                                    |
| column | [daily mean anomaly](../../checks/column/anomaly/mean-anomaly.md)                                            | Ensures that the mean value in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.         |
| column | [daily sum anomaly](../../checks/column/anomaly/sum-anomaly.md)                                              | Ensures that the sum in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.                |
| column | [daily column exists](../../checks/column/schema/column-exists.md)                                           | Reads the metadata of the monitored table and verifies that the column still exists in the data source.                                     |
| column | [daily column type changed](../../checks/column/schema/column-type-changed.md)                               | Detects if the data type of the column has changed since the last time it was retrieved.                                                    |

All checks are scheduled to run daily at 12:00 a.m.

You can check and modify the default configuration of checks. 
Simply select the **Configuration** section and then click on **Default checks configuration** on the tree view on the left.
You can then modify the **Profiling checks**, **Monitoring daily**, and **Monitoring monthly** default check configuration in the workspace on the right.

![Check default check configuration](https://dqops.com/docs/images/examples/detect-empty-tables-check-default-check-configuration.png)

For more [general information about checks, see the DQOps concepts](../../dqo-concepts/checks/index.md) section.


## Running the checks in the example and evaluating the results using the user interface

A detailed explanation of [how to run the example is described here](../index.md#running-the-use-cases).

To execute the check prepared in the example using the [user interface](../../dqo-concepts/user-interface-overview/user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/detect-empty-tables-navigating-to-a-list-of-checks.png)

1. Go to the **Monitoring** section.

    The **Monitoring Checks** section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the `311_service_requests` table from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Daily checks** tab.

    This tab displays a list of data quality checks in the check editor.
    Here you can view the list of all table monitoring checks. Noticed the checks that have been activated upon importing new tables have switched on the toggle button.
    The icons located before the name of each check allow you to: activate and deactivate it, configure settings, run a check, review results, and get more information about it.

    Learn more about [navigating the check editor](../../dqo-concepts/user-interface-overview/user-interface-overview.md#check-editor).


4. Run the activated [daily_row_count](../../checks/table/volume/row-count.md) check using the **Run check** button.

    You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

    ![Run check](https://dqops.com/docs/images/examples/detect-empty-tables-run-checks.png)


5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Check results**, **Sensor readouts**, and **Execution errors**.
    The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
    The Sensor readouts category displays the values obtained by the sensors from the data source.
    The Execution errors category displays any error that occurred during the check's execution.

    Review the results which should be similar to the one below.

    ![Daily row count check results](https://dqops.com/docs/images/examples/detect-empty-tables-checks-results.png)

    The actual value in this example is 1 748 850, which is above the maximum threshold level set in the warning (1).
    The check gives a valid result (notice the green square to the left of the check name). 


6. Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner of the user interface.

    Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.


7. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
    go to the Data Quality Dashboards section and select the dashboard from the tree view on the left.

    Below you can see the results displayed on the **Current table status** dashboard located in the Current status group.     
    
    This dashboard allows data engineers and data owners to quickly evaluate the data quality of monitored
    tables. The dashboards display a color-coded status that indicates the severity level detected by run
    checks. When the status is green, it means that the monitored table has no data quality issues. However, if the status
    is yellow, orange, or red, it indicates that there were some issues detected. The dashboard also displays the number
    of detected issues per severity threshold, making it easier to identify and address tables and columns with issues.
 
    This dashboard allow filtering data by:
 
    * time frame,
    * connection,
    * schema,
    * data quality dimension,
    * check category,
    * data group,
    * table.
 
    ![Daily row count check results on Current table status dashboard](https://dqops.com/docs/images/examples/daily-row-count-check-results-on-current-table-status-dashboard.png)

## YAML configuration file

The YAML configuration file is where the details about tables and their check configurations are stored. 
In the example provided, only the configuration for one column, `unique_key`, is visible for clarity. 
However, the rest of the columns' configurations can also be found in the YAML file.

The highlighted sections in the YAML file below show where the [daily_row_count](../../checks/table/volume/row-count.md)
monitoring check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="17-20"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  profiling_checks:
    volume:
      profile_row_count:
        warning:
          min_count: 1
    schema:
      profile_expected_column_count: {}
  monitoring_checks:
    daily:
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
  columns:
    unique_key:
      type_snapshot:
        column_type: STRING
        nullable: true
      profiling_checks:
        nulls:
          profile_nulls_count: {}
          profile_nulls_percent: {}
      monitoring_checks:
        daily:
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
            daily_not_nulls_percent: {}
          datatype:
            daily_detected_datatype_in_text_changed:
              warning: {}
          schema:
            daily_column_exists:
              warning: {}
            daily_column_type_changed:
              warning: {}
```

In this example, we have demonstrated how to use DQOps to detect empty tables.
By using the default [daily_row_count](../../checks/table/volume/row-count.md) table check,
we can monitor that the newly imported tables are not empty. If they are, you will get a warning result.

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../working-with-dqo/installation/install-dqo-using-pip.md) or [run DQOps as a Docker container](../../working-with-dqo/installation/run-dqo-as-docker-container.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/incidents-and-notifications/incidents.md) and [Slack notifications](../../integrations/slack/configuring-slack-notifications.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../../working-with-dqo/set-up-data-grouping/set-up-data-grouping.md) can help you to calculate separate data quality KPI scores for different groups of rows.