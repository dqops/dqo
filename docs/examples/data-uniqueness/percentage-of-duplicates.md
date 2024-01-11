# Percentage of duplicates

## Overview

This example verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.

**PROBLEM**

[Austin-311-Public-Data](https://data.austintexas.gov/Utilities-and-City-Services/Austin-311-Public-Data/xwdj-i9he) provides the residents of Austin with a simple single point of contact for every city department.

What started as police non-emergency line for the City of Austin has become a robust Citywide Information Center
where ambassadors are available to answer residents’ concerns 24 hours a day, 7 days a week, and 365 days a year.

The `unique_key` column contains unique key data. We want to verify the percent of duplicated values on `unique_key` column.

**SOLUTION**

We will verify the data of `bigquery-public-data.austin_311.311_service_requests` using monitoring
[duplicate_percent](../../checks/column/uniqueness/duplicate-percent.md) column check.
Our goal is to verify if the percentage of duplicated values in `unique_key` column does not exceed set thresholds.

In this example, we will set three maximum percentage thresholds levels for the check:

- warning: 1.0%
- error: 2.0%
- fatal: 5.0%

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of duplicated values on `unqiue_key` column exceed 1.0%, a warning alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.austin_311.311_service_requests` dataset. Some columns were omitted for clarity.  
The `unique_key` column of interest contains unique values.

| unique_key      | complaint_description                               | source | source | status_change_date  | created_date        |
|:----------------|:----------------------------------------------------|:-------|:-------|:--------------------|:--------------------|
| **19-00454912** | Parking Machine Issue                               | Phone  | Closed | 12/3/2019 6:54:59   | 11/30/2019 11:33:22 |
| **20-00288726** | Community Connections - Coronavirus                 | Phone  | Closed | 7/16/2020 11:26:40  | 7/16/2020 10:21:17  |
| **19-00458482** | Parking Machine Issue                               | Phone  | Closed | 12/5/2019 6:41:42   | 12/3/2019 12:57:47  |
| **17-00207653** | Street Light Issue- Address                         | Web    | Closed | 7/20/2017 12:33:20  | 7/20/2017 11:19:51  |
| **18-00118937** | Parking Machine Issue                               | Phone  | Closed | 4/25/2018 13:30:43  | 4/24/2018 8:30:23   |
| **20-00525858** | Community Connections - Coronavirus                 | Phone  | Closed | 12/29/2020 14:13:49 | 12/28/2020 17:26:17 |
| **14-00150037** | Street Light Issue- Multiple poles/multiple streets | Phone  | Closed | 7/21/2014 14:52:20  | 7/21/2014 14:36:47  |
| **14-00181676** | Parking Machine Issue                               | Phone  | Closed | 8/28/2014 10:40:32  | 8/27/2014 11:32:21  |

## Running the checks in the example and evaluating the results using the user interface

A detailed explanation of [how to run the example is described here](../index.md#running-the-use-cases).

To execute the check prepared in the example using the [user interface](../../dqo-concepts/user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-duplicate-percent-checks1.png)

1. Go to the **Monitoring** section.

    The **Monitoring Checks** section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Daily checks** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../dqo-concepts/user-interface-overview.md#check-editor).


4. Run the activated check using the **Run check** button.

    You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

    ![Run check](https://dqops.com/docs/images/examples/daily-duplicate-percent-run-checks1.png)


5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Check results**, **Sensor readouts**, and **Execution errors**.
    The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
    The Sensor readouts category displays the values obtained by the sensors from the data source.
    The Execution errors category displays any error that occurred during the check's execution.

    Review the results which should be similar to the one below.

    ![Duplicate-percent check results](https://dqops.com/docs/images/examples/daily-duplicate-percent-checks-results1.png)

    The actual value in this example is 0%, which is below the maximum threshold level set in the warning (1.0%).
    The check gives a valid result (notice the green square to the left of the check name).

6. Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner of the user interface.

    Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

7. To review the results on the [data quality dashboards](../../working-with-dqo/reviewing-results-on-data-quality-dashboards.md)
    go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. 
 
    Below you can see the results displayed on the **Highest issue severity per column and day** dashboard located in Highest issue severity group.
    This dashboard allows for reviewing and filtering a summary number of issues that arise from data quality checks per day. 
    This dashboard help evaluate the areas with the highest number of data quality issues that should be addressed.
    It also allows to review how the issue severity changed per day of the month.

    This dashboard allows filtering data by:
    
    * current and previous month,
    * connection,
    * schema,
    * data group,
    * data quality dimension,
    * check category,
    * check name,
    * table,
    * column.

    ![Duplicate-percent check results on Highest issue severity per column and day dashboard](https://dqops.com/docs/images/examples/highest-issue-severity-per-column-and-day-dashboard.png)

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

In this example, we have set three maximum percentage thresholds levels for the check:

- warning: 1.0%
- error: 2.0%
- fatal: 5.0%

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_duplicate_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="8-21"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    unique_key:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          uniqueness:
            daily_duplicate_percent:
              warning:
                max_percent: 1.0
              error:
                max_percent: 2.0
              fatal:
                max_percent: 5.0
    source:
      type_snapshot:
        column_type: STRING
        nullable: true
    created_date:
      type_snapshot:
        column_type: TIMESTAMP
        nullable: true
```

## Running the checks in the example and evaluating the results using DQOps Shell

A detailed explanation of [how to run the example is described here](../index.md#running-the-use-cases).

To execute the check prepared in the example, run the following command in DQOps Shell:

``` 
check run
```

Review the results which should be similar to the one below.
The percent of the duplicate values in the `unique_key` column is below 5.0% and the check gives valid result.

```
Check evaluation summary per table:
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection|Table                          |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|austin_311|austin_311.311_service_requests|1     |1             |1            |0       |0     |0           |0               |
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
```

For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection austin_311 (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
        WHEN COUNT(analyzed_table.`unique_key`) = 0 THEN 100.0
        ELSE 100.0 * (
            COUNT(analyzed_table.`unique_key`) - COUNT(DISTINCT analyzed_table.`unique_key`)
        ) / COUNT(analyzed_table.`unique_key`)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`austin_311`.`311_service_requests` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

You can also see the results returned by the sensor. The actual value in this example is 0.0%, which is below the maximum
threshold level set in the warning (5.0%).

```
**************************************************
Finished executing a sensor for a check duplicate_percent on the table austin_311.311_service_requests using a sensor definition column/uniqueness/duplicate_percent, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|0.0         |2023-04-25T14:37:23.670Z|2023-04-25T14:37:23.670Z|
+------------+------------------------+------------------------+
**************************************************
```

In this example, we have demonstrated how to use DQOps to verify the uniqueness of data in a column.
By using the [duplicate_percent](../../checks/column/uniqueness/duplicate-percent.md) column check, we can monitor that
the percentage of duplicate values in a column does not exceed the maximum accepted percentage. If it does, you will get a warning, error or fatal result.

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../../dqops-installation/run-dqops-as-docker-container.md).
- For details on the [profile_duplicate_percent check used in this example, go to the check details section](../../checks/column/uniqueness/duplicate-percent.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [Slack notifications](../../integrations/slack/configuring-slack-notifications.md). 
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you to calculate separate data quality KPI scores for different groups of rows.
- DQOps provide you with summary statistics about your table and column. This information can be valuable in deciding which data quality checks and threshold levels should be set to monitor data quality. For more details about [Basic data statistics, click here](../../working-with-dqo/collecting-basic-data-statistics.md). 

