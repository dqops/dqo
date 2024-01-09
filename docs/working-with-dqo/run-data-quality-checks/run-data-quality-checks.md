# Run data quality check

## Overview

In DQOps there are two ways to activate and run data quality checks:

- using the user interface 
- using DQOps Shell

To activate checks, you need to add a connection. You can learn [how to add connection here](../../../data-sources/index.md).

For more information about [different categories of checks, see DQOps concepts section](../../../dqo-concepts/checks/index.md).

## Run data quality checks using the user interface

### **Navigate to the check editor**

To navigate to the checks editor:

1. Click on the **Profiling**, **Monitoring checks** or **Partition checks** section at the top of the screen.

    ![Navigate to check section](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/navigate-to-the-check-section2.png)
   
2. On the tree view on the left, select a table or column of interest by expanding the connection.
    This will open a [**Check editor**](../../../dqo-concepts/user-interface-overview/user-interface-overview.md#check-editor) screen when you can work with checks. 

    ![Select a table or column of interest](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/select-a-table-or-colum-of-interest2.png)
   
    The Check editor screen has tabs that allow you to switch between Profiling (only in Profiling section) or Daily
    and Monthly checks, review Table quality status, access the screen for setting Comparisons, view Basic data statistics
    (only in Profiling section), or preview tables (only in Profiling section).

    The table with data quality checks contains a list of checks divided into different data quality subcategories that you
    can expand and collapse by clicking on an arrow. [Learn more about the different check categories.](../../../dqo-concepts/checks/index.md#categories-of-checks)

    The right side of the table allows setting different threshold levels (severity levels). [Learn more about threshold levels ](../../../dqo-concepts/checks/index.md#issue-severity-levels)

### **Run a check**

To run a check:

1. Enable the check of interest by clicking the switch button next to the name of the check in the list on the right.

    ![Enable check](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/enable-check2.png)

2. Set the threshold levels or leave default values. Set parameters if the check has any. Click the **Save** button in the upper right corner.
    
    You can read more about [threshold severity levels in DQOps concepts section](../../../dqo-concepts/checks/index.md#issue-severity-levels).

    ![Set threshold levels](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/set-threshold-levels2.png)

3. Run data quality check by clicking the **Run Check** icon

    ![Enable check](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/run-check2.png)

    A square should appear next to the name of the checks indicating the results of the run check:

    - Green for a valid result
    - Yellow for a warning
    - Orange for an error
    - Red for a fatal error
    - Black for execution error
   
    You can view the details by placing the mouse cursor on the square.

    ![View quick check results](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/view-quick-check-results.png)

    This check run resulted in a valid result. The daily_row_count sensor readout was 18 155, which was higher than the min_count error threshold 1 000.

### **View detailed check results**

To view detailed check results, sensor readouts, and execution errors, click on the **Results** icon.

![Checking results](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/detailed-check-results.png)

A table will appear with detailed information about the run check. You can filter the table by data group and month 
using the dropdowns. Additionally, you can switch between the table and chart view by clicking on the icons on the 
right of the month filter dropdown. To close the detailed results view, click on the **Results** icon again or use 
the X button on the right.

[Learn here how to delete data quality results](../../delete-data-quality-results/delete-data-quality-results.md).

Synchronize locally stored results with your DQOps Cloud account to be able to view the results on the dashboards.

To synchronize all the data click on the **Synchronize** button in the upper right corner of the navigation menu.

You can learn here how to [Review the results of data quality monitoring on dashboards.](../../data-quality-dashboards/data-quality-dashboards.md)


## Configure date or datetime column for partition checks

Partition checks measure data quality for each daily or monthly partition by creating a separate data quality score.

To learn more about partition checks, go to [DQOps concepts section](../../../dqo-concepts/checks/partition-checks/partition-checks.md)

To run partition checks you need to configure a date or datetime columns which will be used as the time partitioning key for the table.

To configure the date or datetime column:

1. Go to the **Data Sources** section.

2. Select the table of interest from the tree view.

3. Select the **Data and Time Columns** tab and select a column from the drop-down list in the "Date or datetime 
    column name for partition checks" input field.

    ![Partitioning column configuration](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/date-or-datetime-column-configuration-for-partion-checks.png)

4. Click the Save button in the upper right corner.


## Configure event and ingestion timestamp columns for timeliness checks

To run timeliness checks you need to configure event and/or ingestion timestamp columns.

To configure the event and/or ingestion timestamp columns:

1. Go to the **Data Sources** section.

2. Select the table of interest from the tree view.

3. Select the **Data and Time Columns** tab and select a column from the drop-down list in the "Event timestamp column name 
    for timeliness checks" and/or "Ingestion timestamp column name for timeliness checks" input fields.

    ![Configure event and ingestion timestamp columns](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/event-and-ingestion-columns-configuration-for-timeliness-checks.png)

4. Click the Save button in the upper right corner.


## Run data quality checks using the DQOps Shell

Data quality checks are stored in YAML configuration files. YAMl configuration files are located in the `./sources` folder.
The [complete DQOps YAML schema can be found here](https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json).

To add and run data quality checks using the DQOps Shell, follow the steps below. 

1. Run the following command in DQOps Shell to edit YAMl configuration file and define data quality checks.
    ```
    dqo> table edit
    ```
   
2. Provide the connection name and full table name in a schema.table format.

    ```
    Connection name (--connection): testconnection
    Full table name (schema.table), supports wildcard patterns 'sch*.tab*': austin_crime.crime
    ```
    After entering the above data, Visual Studio Code will be automatically launched.

3. Add the check to the YAML file using Visual Studio Code editor and save the file. 

    Below is an example of the YAML file showing sample configuration of a profiling table check row_count. 
    Some columns were truncated for clarity

    ```yaml hl_lines="9-13"
    apiVersion: dqo/v1
    kind: table
    spec:
       timestamp_columns:
          partition_by_column: timestamp
       incremental_time_window:
          daily_partitioning_recent_days: 7
          monthly_partitioning_recent_months: 1
       profiling_checks:
          volume:
             row_count:
                error:
                   min_count: 0
       columns:
          unique_key:
             type_snapshot:
                column_type: INT64
                nullable: true
          address:
             type_snapshot:
                column_type: STRING
                nullable: true
          census_tract:
             type_snapshot:
                column_type: FLOAT64
                nullable: true
          clearance_date:
             type_snapshot:
                column_type: TIMESTAMP
                nullable: true
    ```
   
4. To execute the check, run the following command in DQOps Shell:

    ```
    dqo> check run
    ```
   
    You can execute the check run for the whole connection, table or specific check type using additional parameters. 
    For more details check the [CLI section](../../../command-line-interface/check.md#dqo-check-run)

    You should see the table with the results similar to the one below.
 
    ```
    Check evaluation summary per table:
    +--------------+------------------+------+--------------+-------------+--------+------+------------+----------------+
    |Connection    |Table             |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
    +--------------+------------------+------+--------------+-------------+--------+------+------------+----------------+
    |testconnection|austin_crime.crime|1     |1             |1            |0       |0     |0           |0               |
    +--------------+------------------+------+--------------+-------------+--------+------+------------+----------------+
    ```

    For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
    following command:
    
    ```
    check run --mode=debug
    ```

    In the debug mode you can view the SQL query (sensor) executed in the check.
    
    ```
    **************************************************
    Executing SQL on connection testconnection (bigquery)
    SQL to be executed on the connection:
    SELECT
        COUNT(*) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `bigquery-public-data`.`austin_crime`.`crime` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    **************************************************
    ```
    
    You can also see the results returned by the sensor. 
    
    ```
    Results returned by the sensor:
    +------------+------------------------+------------------------+
    |actual_value|time_period             |time_period_utc         |
    +------------+------------------------+------------------------+
    |116675      |2023-05-12T13:45:46.602Z|2023-05-12T13:45:46.602Z|
    +------------+------------------------+------------------------+
    **************************************************
    ```

### **Configuring date or datetime column for partition checks and event and ingestion timestamps for timeliness checks**

Partition checks measure data quality for each daily or monthly partition by creating a separate data quality score.
To run partition checks you need to configure Date or datetime colum which will be used as the time partitioning key for the table.

To run timeliness checks you need to configure event and/or ingestion timestamp columns.

The date or datetime column for partition checks and the event and ingestion timestamps for timeliness checks can by configured by adding 
the appropriate parameters to the YAML configuration file.

Below is an example of the YAML file showing sample configuration with set event timestamps column `event_timestamp_column`,
ingestion timestamps column `ingestion_timestamp_column` and datetime column for partition checks `partition_by_column`. 

``` yaml hl_lines="7-10"
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: target_schema
    table_name: target_table
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
    partition_by_column: event_timestamp
  columns:
    target_column:
      partition_checks:
        daily:
          nulls:
            daily_partition_checks_nulls_percent:
              warning:
                max_percent: 1.0
              error:
                max_percent: 5.0
              fatal:
                max_percent: 30.0
```

## What's next

- [Learn about setting schedules](../../schedules/index.md) to easily customize when checks are run.
- [Learn how to delete data quality results](../../delete-data-quality-results/delete-data-quality-results.md).