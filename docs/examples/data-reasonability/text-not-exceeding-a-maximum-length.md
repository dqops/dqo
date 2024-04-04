---
title: How to detect texts exceeding a maximum length using a data quality check
---
# How to detect texts exceeding a maximum length using a data quality check 
This sample shows how to use data quality checks to detect a text not exceeding a maximum length and view the results on data quality dashboards.

## Overview

This example shows how to verify that the length of the text in a column does not exceed the maximum length.

**PROBLEM**

[America’s Health Rankings](https://www.americashealthrankings.org/about/methodology/our-reports) provides an analysis of national health on a state-by-state basis by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

The platform analyzes more than 340 measures of behaviors, social and economic factors, physical environment and clinical care data.
Data is based on public-use data sets, such as the U.S. Census and the Centers for Disease Control and Prevention’s Behavioral Risk Factor Surveillance System (BRFSS),
the world’s largest, annual population-based telephone survey of over 400,000 people.

The `measure_name` contains measure name data. We want to verify that the length of the string values in this column does not exceed 30 characters.

**SOLUTION**

We will verify the data of `bigquery-public-data.america_health_rankings.ahr` using monitoring
[text_max_length](../../checks/column/text/text-max-length.md) column check.
Our goal is to verify if the length of the strings in `measure_name` column does not exceed the set range.

In this example, we will set one threshold level for the check:

- error: range from 0 to 30

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

**VALUE**

If the string length exceed the range 0.0 - 30.0, en error alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.america_health_rankings.ahr` dataset. Some columns were omitted for clarity.  
The `measure_name` column of interest contains values that shouldn't exceed the length indicated thresholds.

| edition | report_type             | measure_name    | state_name    | subpopulation |
|:--------|:------------------------|:----------------|:--------------|:--------------|
| 2021    | 2021 Health Disparities | **Able-Bodied** | California    |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | Colorado      |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | Hawaii        |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | Kentucky      |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | Maryland      |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | New Jersey    |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | Utah          |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | West Virginia |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | Arkansas      | Female        |

## Run the example using the user interface

A detailed explanation of [how to start DQOps platform and run the example is described here](../index.md#running-the-use-cases).

### **Navigate to a list of checks**

To navigate to a list of checks prepared in the example using the [user interface](../../dqo-concepts/dqops-user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-text-max-length-checks2.png)

1. Go to the **Monitoring** section.

    The **Monitoring Checks** section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Daily checks** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../dqo-concepts/dqops-user-interface-overview.md#check-editor).


### **Run checks**

Run the activated check using the **Run check** button.

You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

![Run check](https://dqops.com/docs/images/examples/daily-text-max-length-run-checks2.png)


### **View detailed check results**

Access the detailed results by clicking the **Results** button. The results should be similar to the one below.

![Text max length check results](https://dqops.com/docs/images/examples/daily-text-max-length-checks-results3.png)

Within the Results window, you will see three categories: **Check results**, **Sensor readouts**, and **Execution errors**.
The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
The Sensor readouts category displays the values obtained by the sensors from the data source.
The Execution errors category displays any error that occurred during the check's execution.

The actual value in this example is 31, which is above the range threshold level set in the error field (0 - 30).
The check result in an error issue (notice the orange square to the left of the check name).


### **Synchronize the results with the cloud account**

Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner
of the user interface.

Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

### **Review the results on the data quality dashboards**

To review the results on the [data quality dashboards](../../working-with-dqo/review-the-data-quality-results-on-dashboards.md)
go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. 

Below you can see the results displayed on the **Highest issue severity per column and day** dashboard located in the Highest issue severity per day group.
This dashboard allows for reviewing and filtering a summary number of issues that arise
from data quality checks per column and day. This dashboard helps evaluate the areas with the highest number of data 
quality issues that should be addressed. It also allows to review how the issue severity changed per day of the month.

These dashboards allow filtering data by:
    
* current and previous month,
* connection,
* schema,
* data group,
* data quality dimension,
* check category,
* check name,
* table,
* column.

![String-max-length check results on Highest issue severity per column and day dashboard](https://dqops.com/docs/images/examples/daily-string-max-length-checks-results-on-highest-issue-severuty-dashboard.png)

## Change a schedule at the connection level

With DQOps, you can easily customize when checks are run by setting schedules. You can set schedules for an entire connection,
table, or individual check.

After importing new tables, DQOps sets the schedule for 12:00 P.M. (noon) every day. Follow the steps below to change the schedule.

![Change a schedule at the connection level](https://dqops.com/docs/images/examples/change-schedule-for-connection.png)

1. Navigate to the **Data Source** section.

2. Choose the connection from the tree view on the left.

3. Click on the **Schedule** tab.

4. Select the **Monitoring daily** tab

5. Select the **Run every day at** and change the time, for example, to 10:00. You can also select any other option. 

6. Once you have set the schedule, click on the **Save** button to save your changes.

    By default, scheduler is active. You can turn it off by clicking on notification icon in the top right corner of the screen, and clicking the toggle button.

    ![Turn off scheduler](https://dqops.com/docs/images/examples/turning-off-scheduler.png)

Once a schedule is set up for a particular connection, it will execute all the checks that have been configured across
all tables associated with that connection.

You can [read more about scheduling here](../../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md).

You might also want to check the [Running checks with a scheduler](../data-quality-monitoring/running-checks-with-a-scheduler.md) example.

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three maximum number thresholds levels for the check:

- warning: 30.0

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_text_max_length` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

```yaml hl_lines="16-26"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
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
      monitoring_checks:
        daily:
          text:
            daily_text_max_length:
              error:
                from: 0
                to: 30
    state_name:
      type_snapshot:
        column_type: STRING
        nullable: true
    subpopulation:
      type_snapshot:
        column_type: STRING
        nullable: true
```

## Run the checks in the example using the DQOps Shell

A detailed explanation of [how to start DQOps platform and run the example is described here](../index.md#running-the-use-cases).

To execute the check prepared in the example, run the following command in DQOps Shell:

``` 
check run
```

Review the results which should be similar to the one below.
The number of the valid string length in the `measure_name` column is above 30.0 and the check raised an error.

```
Check evaluation summary per table:
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection             |Table                      |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|america_health_rankings|america_health_rankings.ahr|1     |1             |0            |0       |1     |0           |0               |
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
```

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
    MAX(
        LENGTH(analyzed_table.`measure_name`)
    ) AS actual_value,
    CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
    TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
FROM `bigquery-public-data`.`america_health_rankings`.`ahr` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

You can also see the results returned by the sensor. The actual value in this example is 31, which is above the maximum
threshold level set in the error (30.0).

```
**************************************************
Finished executing a sensor for a check daily_text_max_length on the table america_health_rankings.ahr using a sensor
 definition column/text/text_max_length, sensor result count: 1

Results returned by the sensor:
+------------+-----------+--------------------+
|actual_value|time_period|time_period_utc     |
+------------+-----------+--------------------+
|31          |2024-02-14 |2024-02-14T00:00:00Z|
+------------+-----------+--------------------+
```

In this example, we have demonstrated how to use DQOps to verify the reasonability of data in a column.
By using the [text_max_length](../../checks/column/text/text-max-length.md) column check, we can monitor that 
the length of the text in a column does not exceed the length in a set range. If it does, you will get a warning, error or fatal result.

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../../dqops-installation/run-dqops-as-docker-container.md).
- For details on the [profile_text_max_length check used in this example, go to the check details section](../../checks/column/text/text-max-length.md).
- You might be interested in another reasonability check that [evaluates that the percentage of false values does not fall below the minimum percentage](../data-reasonability/percentage-of-false-values.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [Slack notifications](../../integrations/slack/configuring-slack-notifications.md). 
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.