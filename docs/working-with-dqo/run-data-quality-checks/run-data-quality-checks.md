# Run data quality check

In DQO there are two ways to enable and run data quality checks:

- using the graphical interface 
- using DQO Shell

To enable checks, you need to add a connection. You can learn [how to add connection here](../adding-data-source-connection/index.md).

For more information about different categories of checks, see [DQO concepts section](../../dqo-concepts/checks/index.md).

## Run data quality checks using the graphical interface

1. In DQO graphical interface navigate to the check section Profiling, Monitoring Checks or Partition Checks at the top of the screen.

    ![Navigate to check section](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/navigate-to-the-check-section.jpg)
   
2. On the tree view on the left, select a table or column of interest by expanding the connection.

    ![Select a table or column of interest](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/select-a-table-or-colum-of-interest.jpg)

3. Enable the check of interest by clicking the switch button next to the name of the check in the list on the right.

    ![Enable check](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/enable-check.jpg)

4. Set the threshold levels or leave default values. Set parameters if the check has any. Click the Save button in the upper right corner.
    You can read more about threshold severity levels in [DQO concepts section](../../dqo-concepts/checks/#severity-levels).

    ![Set threshold levels](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/set-threshold-levels.jpg)

5. Run data quality check by clicking the Run Check icon

    ![Enable check](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/run-check.jpg)

    A square should appear next to the name of the checks indicating the results of the run check:
    - green for a valid result
    - yellow for a warning
    - orange for an error
    - red for a fatal error

    You can view the details by placing the mouse cursor on the square.

6. Click the "Results" icon to view more details of the results.

    ![Checking results](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/check-results.jpg)

    A table will appear with more details about the run check.

7. Synchronize locally stored results with your DQO Cloud account to be able to view the results on the dashboards.

    To synchronize all the data click on Synchronize button in the upper right corner of the navigation menu.

### **Configure date or datetime column for partition checks**

Partition checks measure data quality for each daily or monthly partition by creating a separate data quality score.

To learn more about partition checks, go to [DQO concepts section](../../dqo-concepts/checks/partition-checks/partition-checks.md)

To run partition checks you need to configure a date or datetime columns which will be used as the time partitioning key for the table.

To configure the date or datetime colum:

1. Go to the **Data Sources** section.

2. Select the table of interest from the tree view.

3. Select the **Data and Time Columns** tab and select a column from the drop-down list in the "Date or datetime 
    column name for partition checks" input field.

    ![Checking results](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/date-or-datetime-column-configuration-for-partion-checks.jpg)

4. Click the Save button in the upper right corner.


### **Configure event and ingestion timestamp columns for timeliness checks**

To run timeliness checks you need to configure event and/or ingestion timestamp columns.

To configure the event and/or ingestion timestamp columns:

1. Go to the **Data Sources** section.

2. Select the table of interest from the tree view.

3. Select the **Data and Time Columns** tab and select a column from the drop-down list in the "Event timestamp column name 
    for timeliness checks" and/or "Ingestion timestamp column name for timeliness checks" input fields.

    ![Checking results](https://dqops.com/docs/images/working-with-dqo/run-data-quality-checks/even-and-ingestion-columns-configuration-for-timeliness-checks.jpg)

4. Click the Save button in the upper right corner.


## Run data quality checks using the DQO Shell

Data quality checks are stored in YAML configuration files. YAMl configuration files are located in the `./sources` folder.
The complete DQO YAML schema can be found [here](https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json).

To add and run data quality checks using the DQO Shell, follow the steps below. 

1. Run the following command in DQO Shell to edit YAMl configuration file and define data quality checks.
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
   
4. To execute the check, run the following command in DQO Shell:

    ```
    dqo> check run
    ```
   
    You can execute the check run for the whole connection, table or specyfic check type using additional parameters. 
    For more details check the [CLI section](../../command-line-interface/check.md/#dqo-check-run)

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
ingestion timestamps column `ingestion_timestamp_column` and datetime column for partition checks `partitioned_checks_timestamp_source`. 

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
    partitioned_checks_timestamp_source: event_timestamp
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

- [Learn about setting schedules](../schedules/index.md) to easily customize when checks are run.
- [Learn how to delete data quality results](../delete-data-quality-results/delete-data-quality-results.md).