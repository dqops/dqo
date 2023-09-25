# Table availability

Verifies the availability of a table in the database using a simple row count.

**PROBLEM**

For any database analysis, it is important that the tables exist and are available.

Typical table availability issues are:

- the table does not exist because it has been deleted,
- the table is corrupted and cannot be queried,
- the database is down or is unreachable,
- database credentials are incorrect,
- access rights to the table have changed.

In this example, we will use a simple row count to verify table availability in the database and ensure that queries 
can be executed without errors.

As an example we use [America’s Health Rankings](https://www.americashealthrankings.org/about/methodology/our-reports) database. 
This database provides an analysis of national health on a state-by-state basis by evaluating a historical and comprehensive set of health, 
environmental and socioeconomic data to determine national health benchmarks and state rankings.

**SOLUTION**

We will verify the data using monitoring [table_availability](../../checks/table/availability/table-availability.md) check.
Our goal is to verify that table availability check failures do not exceed the set thresholds.
In this check, you can only get two values in the result 1 or 0. If you get a value of 1, it means the table exists and is available, so the result is valid. 

However, if you receive a value of 0, then there is a problem, and you need to run this check again after fixing the issue with the table. 
The number of failed attempts are failures, which we set in thresholds.

Table availability checks has a max_failures parameter which indicates a maximum number of consecutive check failures.
A check is failed when the sensor's query failed to execute due to a connection error, missing table or a corrupted table.

In this example, we will set the following maximum failures for the check:

- warning: 0
- error: 5
- fatal: 10

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the number of failures will exceed 0, a warning alert will be triggered.

## Running the checks in the example and evaluating the results using the graphical interface

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example using the [graphical interface](../../working-with-dqo/navigating-the-graphical-interface/navigating-the-graphical-interface.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-table-availability-check-1.png)

1. Go to the **Profiling** section.

    The Profiling section enables the configuration of advanced profiling data quality checks that are designed for the initial evaluation of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../working-with-dqo/adding-data-source-connection/index.md).


3. Select the **Profiling Checks** tab.

    In this tab you can find a list of data quality checks. On **Profiling** section, there is also a second tab [Basic data statistics](../../working-with-dqo/basic-data-statistics/basic-data-statistics.md) that allows you to collect summary information about your tables and columns.


4. Run the enabled check using the **Run check** button.

    You can also run all checks for the check category using the **Run check** button located at the end of the row with the name of the check group.

    ![Run check](https://dqops.com/docs/images/examples/table-availability-run-check-1.png)

5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Sensor readouts**, **Check results**, and **Execution errors**. The Sensor readouts category
    displays the values obtained by the sensors from the data source. The Check results category shows the severity level
    that result from the verification of sensor readouts by set rule thresholds. The Execution errors category displays any error
    that occurred during the check's execution.
 
    ![Check details](https://dqops.com/docs/images/examples/table-availability-check-details-1.png)

6. Review the results which should be similar to the one below.
   
    The actual value in this example is 1.
    The check gives a warning result (notice the yellow square on the left of the name of the check).

    ![Table-availability check results](https://dqops.com/docs/images/examples/table-availability-check-results-1.png)

7. Synchronize the results with your DQO cloud account using the **Synchronize** button located in the upper right corner of the graphical interface.

    Synchronization ensures that the locally stored results are synced with your DQO Cloud account, allowing you to view them on the dashboards.

8. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
   go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. 

    Below you can see the results displayed on the Issue severity status per check dashboard showing results by connection, schema, table and column.

    ![Table-availability check results on the Issue severity status per check dashboard](https://dqops.com/docs/images/examples/table-availability-check-results-on-issue-severity-status-per-check.png)

    Also, you can see results on the Table availability dashboard showing affected tables and connections, 
    and a list of checks where the check result was > 0 which means that the table was corrupted or did not exist on a particular day.

    ![Table-availability check results on the Table availability dashboard](https://dqops.com/docs/images/examples/table-availability-check-result-on-table-availability-dashboard.png)


## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set maximum failures for the check:

- warning: 0
- error: 5
- fatal: 10

The highlighted fragments in the YAML file below represent the segment where the profiling `table_availability` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="9-21"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  monitoring_checks:
    daily:
      availability:
        daily_table_availability:
          comments:
          - date: 2023-09-04T11:56:57.753
            comment_by: user
            comment: "In this example, we verify availability on table in database\
              \ using simple row count."
          warning:
            max_failures: 0
          error:
            max_failures: 5
          fatal:
            max_failures: 10
  columns:
    edition:
      type_snapshot:
        column_type: INT64
        nullable: true
    report_type:
      type_snapshot:
        column_type: STRING
        nullable: true
    measure_name:
      type_snapshot:
        column_type: STRING
        nullable: true
    state_name:
      type_snapshot:
        column_type: STRING
        nullable: true
```
## Running the checks in the example and evaluating the results using DQO Shell

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:
``` 
check run
```
Review the results which should be similar to the one below.
The number of failures is 1 and the check gives warning result.
```
Finished executing rules (thresholds) for a check daily_table_availability on the table america_health_rankings.ahr, verified rules count: 1

Rule evaluation results:
+------------------------------------+------------+--------------+----------------+--------------------+-------------+---------------+---------------+---------------------------+-------------------+------------------+--------+------------------+-----------------------+----------+------------------+-------------------+------------------------+------------------------+----------+--------------+-----------------+-------------------------------------+------------------------------------+------------------------+-----------+--------+-------------------+--------------+--------------+-----------------+-----------------+-------------------+
|id                                  |actual_value|expected_value|time_period     |time_period_utc     |time_gradient|data_group_hash|data_group_name|data_grouping_configuration|connection_hash    |connection_name   |provider|table_hash        |schema_name            |table_name|table_name_pattern|check_hash         |check_name              |check_display_name      |check_type|check_category|quality_dimension|sensor_name                          |time_series_id                      |executed_at             |duration_ms|severity|incident_hash      |include_in_kpi|include_in_sla|fatal_upper_bound|error_upper_bound|warning_upper_bound|
+------------------------------------+------------+--------------+----------------+--------------------+-------------+---------------+---------------+---------------------------+-------------------+------------------+--------+------------------+-----------------------+----------+------------------+-------------------+------------------------+------------------------+----------+--------------+-----------------+-------------------------------------+------------------------------------+------------------------+-----------+--------+-------------------+--------------+--------------+-----------------+-----------------+-------------------+
|e2b1a9b0-023f-2baf-5e0f-a8d9427e379f|1.0         |0.0           |2023-09-04T00:00|2023-09-04T00:00:00Z|day          |0              |no grouping    |default                    |3492051126176682112|table_availability|bigquery|615130806917224725|america_health_rankings|ahr       |ahr               |5460786772265777882|daily_table_availability|daily_table_availability|monitoring|availability  |Availability     |table/availability/table_availability|4bc89ca3-0b0d-bada-0889-6235b4b35915|2023-09-04T12:41:15.179Z|910        |1       |3531467509473078844|true          |false         |10.0             |5.0              |0.0                |
+------------------------------------+------------+--------------+----------------+--------------------+-------------+---------------+---------------+---------------------------+-------------------+------------------+--------+------------------+-----------------------+----------+------------------+-------------------+------------------------+------------------------+----------+--------------+-----------------+-------------------------------------+------------------------------------+------------------------+-----------+--------+-------------------+--------------+--------------+-----------------+-----------------+-------------------+
```
For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection table_availability (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
       WHEN COUNT(*) > 0 THEN COUNT(*)
       ELSE 1.0
    END AS actual_value,
    CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
    TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
FROM
    (
        SELECT
            *,
    CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
    TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `bigquery-public-data`.`america_health_rankings`.`ahr` AS analyzed_table

        LIMIT 1
    ) AS tab_scan
GROUP BY time_period
ORDER BY time_period
**************************************************
```
You can also see the results returned by the sensor. The actual value of the check is 1.0.
```
**************************************************
Finished executing a sensor for a check daily_table_availability on the table america_health_rankings.ahr using a sensor definition table/availability/table_availability, sensor result count: 1

Results returned by the sensor:
+------------+-----------+--------------------+
|actual_value|time_period|time_period_utc     |
+------------+-----------+--------------------+
|1.0         |2023-09-04 |2023-09-04T00:00:00Z|
+------------+-----------+--------------------+
**************************************************
```
## Next steps

- You haven't installed DQO yet? Check the detailed guide on how to [install DQO using pip](../../working-with-dqo/installation/install-dqo-using-pip.md) or [run DQO as a Docker container](../../working-with-dqo/installation/run-dqo-as-docker-container.md).
- For details on the [table_availability check used in this example, go to the check details section](../../checks/table/availability/table-availability.md).
- DQO provides you with summary statistics about your table and column. This information can be valuable in deciding which data quality checks and threshold levels should be set to monitor data quality. For more details about [Basic data statistics, click here](../../working-with-dqo/basic-data-statistics/basic-data-statistics.md). 
- DQO allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/incidents-and-notifications/incidents.md) and [notifications](../../working-with-dqo/incidents-and-notifications/notifications.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQO](../../working-with-dqo/set-up-data-grouping/set-up-data-grouping.md) can help you to calculate separate data quality KPI scores for different groups of rows.