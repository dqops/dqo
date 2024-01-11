# Table availability

## Overview

The following example shows how to verify the availability of a table in the database using a simple row count.

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

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the number of failures exceeds 0, a warning alert will be triggered.

## Running the checks in the example and evaluating the results using the user interface

A detailed explanation of [how to run the example is described here](../index.md#running-the-use-cases).

To execute the check prepared in the example using the [user interface](../../dqo-concepts/user-interface-overview/user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-table-availability-checks1.png)

1. Go to the **Monitoring** section.

    The **Monitoring Checks** section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Daily checks** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../dqo-concepts/user-interface-overview/user-interface-overview.md#check-editor).


4. Run the activated check using the **Run check** button.

    You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

    ![Run check](https://dqops.com/docs/images/examples/daily-table-availability-run-checks1.png)


5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Check results**, **Sensor readouts**, and **Execution errors**.
    The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
    The Sensor readouts category displays the values obtained by the sensors from the data source.
    The Execution errors category displays any error that occurred during the check's execution.

    Review the results which should be similar to the one below.

    ![Table-availability check results](https://dqops.com/docs/images/examples/daily-table-availability-checks-results1.png)
  
    The actual value in this example is 0.
    The check gives a valid result (notice the green square to the left of the check name).


6. Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner of the user interface.

    Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

7. To review the results on the [data quality dashboards](../../working-with-dqo/reviewing-results-on-data-quality-dashboards.md)
    go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. 

    Below you can see the results displayed on the **Current table availability** dashboard located in the Data Quality Dimension/Availability group. 
    This dashboard summarizes results from [table_availability](../../checks/table/availability/table-availability.md) checks.
    Because we did not detect any issues there is no data on the tables. You can view the Correct results by clicking on
    the checkbox **Only availability issues** and show also correct results. 

    This dashboard allows filtering data by:
    
    * Current and previous month,
    * connection,
    * schema,
    * data group,
    * stages,
    * priorities,
    * issue severity level,
    * table

    ![Table-availability check results on the Table availability dashboard](https://dqops.com/docs/images/examples/table-availability-check-result-on-table-availability-dashboard1.png)


## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set maximum failures for the check:

- warning: 0
- error: 5
- fatal: 10

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_table_availability` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="7-21"
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
```

## Running the checks in the example and evaluating the results using DQOps Shell

A detailed explanation of [how to run the example is described here](../index.md#running-the-use-cases).

To execute the check prepared in the example, run the following command in DQOps Shell:

``` 
check run
```

Review the results which should be similar to the one below.
The number of failures is 0 and the check gives a valid result.

```
Check evaluation summary per table:
+------------------+---------------------------+------+------------+------------+--------+------+-----------+--------------+
|Connection        |Table                      |Checks|Sensor      |Valid       |Warnings|Errors|Fatal      |Execution     |
|                  |                           |      |results     |results     |        |      |errors     |errors        |
+------------------+---------------------------+------+------------+------------+--------+------+-----------+--------------+
|table_availability|america_health_rankings.ahr|1     |1           |1           |0       |0     |0          |0             |
+------------------+---------------------------+------+------------+------------+--------+------+-----------+--------------+
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

You can also see the results returned by the sensor. The actual value of the check is 0.0.

```
**************************************************
Finished executing a sensor for a check daily_table_availability on the table america_health_rankings.ahr using a sensor definition table/availability/table_availability, sensor result count: 1

Results returned by the sensor:
+------------+----------------+--------------------+
|actual_value|time_period     |time_period_utc     |
+------------+----------------+--------------------+
|0.0         |2023-12-15T00:00|2023-12-15T00:00:00Z|
+------------+----------------+--------------------+
```

In this example, we have demonstrated how to use DQOps to verify the availability of a table in the database. 
By using the [table_availability](../../checks/table/availability/table-availability.md) check,
we can monitor whether the tables exist and are available. 

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../../dqops-installation/run-dqops-as-docker-container.md).
- For details on the [table_availability check used in this example, go to the check details section](../../checks/table/availability/table-availability.md).
- DQOps provides you with summary statistics about your table and column. This information can be valuable in deciding which data quality checks and threshold levels should be set to monitor data quality. For more details about [Basic data statistics, click here](../../working-with-dqo/collecting-basic-data-statistics.md). 
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [Slack notifications](../../integrations/slack/configuring-slack-notifications.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you to calculate separate data quality KPI scores for different groups of rows.