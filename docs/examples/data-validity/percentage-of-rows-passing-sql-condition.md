---
title: How to detect rows not passing an SQL condition on a table using a data quality check
---
# How to detect rows not passing an SQL condition on a table using a data quality check
This sample shows how to use data quality checks to measure the percentage of passed SQL condition on table and view the results on data quality dashboards.

## Overview

The following example shows how you can verify that a minimum percentage of rows passed a custom SQL condition (expression)
using DQOps platform. By using the [sql_condition_passed_percent_on_table](../../checks/table/custom_sql/sql-condition-passed-percent-on-table.md) check, we can monitor that
the percentage of rows passed a custom SQL condition does not fall below the set thresholds. If it does, you will get a warning, error or fatal result.

**PROBLEM**

[America’s Health Rankings](https://www.americashealthrankings.org/about/methodology/our-reports) provides an analysis of national health on a state-by-state basis
by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

The platform analyzes more than 340 measures of behaviors, social and economic factors, physical environment and clinical care data.
Data is based on public-use data sets, such as the U.S. Census and the Centers for Disease Control and Prevention’s Behavioral Risk Factor Surveillance System (BRFSS),
the world’s largest, annual population-based telephone survey of over 400,000 people.

We want to verify that the upper_ci column is always greater or equal to the lower_ci column.
The check result should tell us if we have any rows not matching an SQL expression `upper_ci >= lower_ci` evaluated on each row.

The SQL query that can calculate the percentage of rows that passed the check should look like the one below:

```sql hl_lines="5-5"
SELECT
     SUM(
          CASE
              WHEN 
                   upper_ci >= lower_ci
              THEN 1
              ELSE 0
          END) * 100.0 / COUNT(*)  AS actual_value
FROM `bigquery-public-data`.`america_health_rankings`.`ahr` AS analyzed_table
```

We want to verify that the percent of rows passed a custom SQL condition (expression) matches the expected threshold.


**SOLUTION**

We will verify the data of `bigquery-public-data.america_health_rankings.ahr` using monitoring
[sql_condition_passed_percent_on_table](../../checks/table/custom_sql/sql-condition-passed-percent-on-table.md) check.
Our goal is to verify if the percentage of rows passed a custom SQL condition does not fall below the set thresholds.

In this example, we will set three minimum percentage thresholds levels for the check:

- warning: 100.0%
- error: 99.0%
- fatal: 95.0%

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

**VALUE**

If the percentage of data falls below 100.0%, a warning alert will be triggered.


### Data structure

The following is a fragment of the `bigquery-public-data.america_health_rankings.ahr` dataset. Some columns were omitted for clarity.

| value  | lower_ci | upper_ci | source                                                 | source_date   |
|:-------|:---------|:---------|:-------------------------------------------------------|:--------------|
| 87     | **87**   | **87**   | U.S. Census Bureau, American Community Survey PUMS     | 2015-2019     |
| 87     | **87**   | **87**   | U.S. Census Bureau, American Community Survey PUMS     | 2015-2019     |
| 87     | **86**   | **87**   | U.S. Census Bureau, American Community Survey PUMS     | 2015-2019     |
| **79** | **82**   | **79**   | **U.S. Census Bureau, American Community Survey PUMS** | **2015-2019** |
| 87     | **86**   | **87**   | U.S. Census Bureau, American Community Survey PUMS     | 2015-2019     |
| 87     | **87**   | **88**   | U.S. Census Bureau, American Community Survey PUMS     | 2015-2019     |
| 88     | **88**   | **88**   | U.S. Census Bureau, American Community Survey PUMS     | 2015-2019     |
| 77     | **76**   | **77**   | U.S. Census Bureau, American Community Survey PUMS     | 2015-2019     |
| 78     | **78**   | **79**   | U.S. Census Bureau, American Community Survey PUMS     | 2015-2019     |

You can see that in the fourth row the value in the lower_ci column is bigger than in the upper_ci column. 

## Run the example using the user interface

A detailed explanation of [how to start DQOps platform and run the example is described here](../index.md#running-the-use-cases).

### **Navigate to a list of checks**

To navigate to a list of checks prepared in the example using the [user interface](../../dqo-concepts/dqops-user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-sql-condition-passed-percent-on-table-checks1.png){ loading=lazy; width="1200px" }

1. Go to the **Monitoring** section.

    The **Monitoring Checks** section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Daily checks** tab.

    This tab displays a list of data quality checks in the check editor. The daily_sql_condition_passed_percent_on_table check
    has additional parameter sql_condition that allows you to input a SQL condition (expression) that returns true or false.
    The condition is evaluated for each row. The expression can use {table} placeholder that is replaced with a full table name.
    
    The condition in our example is `upper_ci >= lower_ci`

Learn more about [navigating the check editor](../../dqo-concepts/dqops-user-interface-overview.md#check-editor).


### **Run checks**

Run the activated check using the **Run check** button.

You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

![Run check](https://dqops.com/docs/images/examples/daily-sql-condition-passed-percent-on-table-run-checks1.png){ loading=lazy; width="1200px" }

### **View detailed check results**

To access the results, click on the **Results** button.

![sql_condition_passed_percent_on_table check results](https://dqops.com/docs/images/examples/daily-sql-condition-passed-percent-on-table-checks-results1.png){ loading=lazy; width="1200px" }

Within the Results window, you will see three categories: **Check results**, **Sensor readouts**, and **Execution errors**.
The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
The Sensor readouts category displays the values obtained by the sensors from the data source.
The Execution errors category displays any error that occurred during the check's execution.

The actual value in this example is 92.6%, which is below the minimum threshold level set in the fatal error (95.0%).
The check gives a fatal error (notice the red square to the left of the check name).

### **Synchronize the results with the cloud account**

Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner
of the user interface.

Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

### **Review the results on the data quality dashboards**

To review the results on the [data quality dashboards](../../working-with-dqo/review-the-data-quality-results-on-dashboards.md)
go to the Data Quality Dashboards section and select the dashboard from the tree view on the left.

Below you can see the results displayed on the **Current validity issues on columns** dashboard located in Data Quality Dimension/Validity group.
This dashboard summarizes results from most recently executed checks categorized to Validity dimension.

This dashboard allows filtering data by:
    
* time window (from last 7 days to last 3 months)
* connection,
* schema,
* data group,
* check category,
* check name,
* stages,
* priorities,
* table,
* column,
* issue severity.
    
![sql_condition_passed_percent_on_table results on Current validity issues on columns dashboard](https://dqops.com/docs/images/examples/daily-sql-condition-passed-percent-on-table-checks-results-on-current-validity-issues-dashboard.png){ loading=lazy; width="1200px" }

## Change a schedule at the connection level

With DQOps, you can easily customize when checks are run by setting schedules. You can set schedules for an entire connection,
table, or individual check.

After importing new tables, DQOps sets the schedule for 12:00 P.M. (noon) every day. Follow the steps below to change the schedule.

![Change a schedule at the connection level](https://dqops.com/docs/images/examples/change-schedule-for-connection.png){ loading=lazy; width="1200px" }

1. Navigate to the **Data Source** section.

2. Choose the connection from the tree view on the left.

3. Click on the **Schedule** tab.

4. Select the **Monitoring daily** tab

5. Select the **Run every day at** and change the time, for example, to 10:00. You can also select any other option.

6. Once you have set the schedule, click on the **Save** button to save your changes.

   By default, scheduler is active. You can turn it off by clicking on notification icon in the top right corner of the screen, and clicking the toggle button.

   ![Turn off scheduler](https://dqops.com/docs/images/examples/turning-off-scheduler.png){ loading=lazy; width="1200px" }

   Once a schedule is set up for a particular connection, it will execute all the checks that have been configured across
   all tables associated with that connection.

   You can [read more about scheduling here](../../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md).

   You might also want to check the [Running checks with a scheduler](../data-quality-monitoring/running-checks-with-a-scheduler.md) example.

## Review the incidents

When DQOps detect a new issue, it creates a new incident. An incident aggregates data quality issues based on different 
categories such as a table, data quality, dimension, check category, or check type. 
To access a summary of incidents that occur during data quality monitoring, navigate to the **Incidents** section.

![Review incidents](https://dqops.com/docs/images/examples/daily-sql-condition-passed-percent-on-table-incidents.png){ loading=lazy; width="1200px" }

On the left side of this screen, there is a list displaying the connections and the number of incidents that have
occurred for each one. On the right panel, you can view incidents for the connections you have selected, and you can change the status of the incident, 
as well as filter, sort, and view detailed information about the incidents.

To learn more about incidents go to the [Incidents overview section](../../working-with-dqo/managing-data-quality-incidents-with-dqops.md).

## Notifications

With DQOps you can also receive notifications whenever an issue is detected in your data and a new incident is created or modified.
A notification contains the most important information about an incident you may want to review in order to resolve an issue.

An example of notification in Slack is shown below.

![Example of Slack notification](https://dqops.com/docs/images/examples/daily-sql-condition-passed-percent-on-table-slack-notification.png){ loading=lazy; width="1200px" }

Follow the link to learn more about [configuring Slack notification](../../integrations/slack/configuring-slack-notifications.md). 

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three minimum percentage thresholds levels for the check:

- warning: 100.0%
- error: 99.0%
- fatal: 95.0%

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_sql_condition_passed_percent_on_table` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

```yaml hl_lines="7-28"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  monitoring_checks:
    daily:
      custom_sql:
        daily_sql_condition_passed_percent_on_table:
          parameters:
            sql_condition: upper_ci >= lower_ci
          warning:
            min_percent: 100.0
          error:
            min_percent: 99.0
          fatal:
            min_percent: 95.0
  columns:
    edition:
      type_snapshot:
        column_type: INT64
        nullable: true
```

## Run the checks in the example using the DQOps Shell

A detailed explanation of [how to start DQOps platform and run the example is described here](../index.md#running-the-use-cases).

To execute the check prepared in the example, run the following command in DQOps Shell:

``` 
check run
```

Review the results which should be similar to the one below.

```
Check evaluation summary per table:
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection             |Table                      |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|america_health_rankings|america_health_rankings.ahr|1     |1             |0            |0       |0     |**1**       |0               |
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
```

As you can see, the table failed the data quality check raising a fatal severity error.
The percent of passed SQL expressions is below the 95.0% and the check raised the Fatal error.

For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection america_health_rankings (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
                         CASE
                             WHEN (upper_ci >= lower_ci)
                                  THEN 1
                             ELSE 0
                         END) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`america_health_rankings`.`ahr` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

You can also see the results returned by the sensor. Now the actual value in this example is 92.6025888185073%, which is below the minimum
threshold level set in the Fatal error (95.0%).

```
**************************************************
Finished executing a sensor for a check sql_condition_passed_percent_on_table on the table america_health_rankings.ahr using a sensor definition table/custom_sql/sql_condition_passed_percent_on_table, sensor result count: 1

Results returned by the sensor:
+----------------+------------------------+------------------------+
|actual_value    |time_period             |time_period_utc         |
+----------------+------------------------+------------------------+
|92.6025888185073|2023-05-18T08:29:25.667Z|2023-05-18T08:29:25.667Z|
+----------------+------------------------+------------------------+
**************************************************
```

The expression `upper_ci >= lower_ci` was false for almost 7.4% rows probably because the column upper_ci or lower_ci is NULL so the expression was false. 

Let's update the SQL expression and count rows with NULL values as valid.

```yaml hl_lines="7-29"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  monitoring_checks:
    daily:
      custom_sql:
        daily_sql_condition_passed_percent_on_table:
          parameters:
            sql_condition: upper_ci >= lower_ci or upper_ci is NULL or lower_ci is
              NULL
          warning:
            min_percent: 100.0
          error:
            min_percent: 99.0
          fatal:
            min_percent: 95.0
        min_sql_condition_passed_percent_on_table:
          parameters:
            sql_condition: upper_ci >= lower_ci or upper_ci is NULL or lower_ci is
              NULL
          warning:
            min_percent: 100.0
          error:
            min_percent: 99.0
          fatal:
            min_percent: 95.0
  columns:
    edition:
      type_snapshot:
        column_type: INT64
        nullable: true
```

In this example, we have demonstrated how to use DQOps to verify the validity of data in a table.
By using the [sql_condition_passed_percent_on_table](../../checks/table/custom_sql/sql-condition-passed-percent-on-table.md) check, we can monitor that
the percentage of rows passed a custom SQL condition does not fall below the set thresholds. If it does, you will get a warning, error or fatal result.

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../../dqops-installation/run-dqops-as-docker-container.md).
- For details on the [sql_condition_passed_percent_on_table check used in this example, go to the check details section](../../checks/table/custom_sql/sql-condition-passed-percent-on-table.md).
- You might be interested in another validity check that [evaluates that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage](./percentage-of-texts-matching-date-regex.md).
- Would you like to add your own connection? Here you can find [information about supported databases and how to add new connection](../../data-sources/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.