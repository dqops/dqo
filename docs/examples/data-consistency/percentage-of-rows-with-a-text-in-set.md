# Percent of rows with a text in set

## Overview

Verifies that the percentage of text values from a set in a column does not fall below the minimum accepted percentage.

**PROBLEM**

We will be testing [Student Performance](https://www.kaggle.com/datasets/whenamancodes/student-performance) dataset. 
This data approach student achievement in secondary education of two Portuguese schools. 
The data attributes include student grades, demographic, social and school related features, and it was collected using school reports and questionnaires. 
Two datasets are provided regarding the student's performance in mathematics. 

In the `Fjob` column, which contains information about the student's father's job, we want to track the number of rows 
with values outside specified set.

**SOLUTION**

We will verify the data using monitoring [text_found_in_set_percent](../../checks/column/accepted_values/text-found-in-set-percent.md) column check.
Our data quality check will compare the values in the tested column to a set of accepted values. We accept only
`services`, `at_home`,and  `teacher` values.

The SQL query that will be executed use will use the following IN SQL clause:

```sql
SELECT
      100.0 * SUM(
            CASE
                WHEN analyzed_table.`Fjob` IN (`services`, `at_home`, `teacher`)
                    THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
FROM `dqo-ai-testing`.`kaggle_student_performance`.`maths` AS analyzed_table
```

In this example, we will set three minimum percent thresholds levels for the check (a minimum accepted percentage of valid rows):

- warning: 99
- error: 98
- fatal: 95

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

**VALUE**

If the percent of text values from a set fall below 99%, a warning alert will be triggered.

## Data structure

The following is a fragment of the [Student Performance](https://www.kaggle.com/datasets/whenamancodes/student-performance).
Some columns were omitted for clarity.  
The `Fjob` column of interest contains information about student's father job.

| Medu | Fedu | Mjob     | Fjob         | reason     | guardian | traveltime | studytime |
|:-----|:-----|:---------|:-------------|:-----------|:---------|:-----------|:----------|
| 4    | 4    | services | **at_home**  | course     | mother   | 1          | 3         |
| 3    | 3    | other    | **other**    | course     | other    | 2          | 1         |
| 4    | 3    | teacher  | **services** | course     | father   | 2          | 4         |
| 3    | 2    | health   | **services** | home       | father   | 1          | 2         |
| 4    | 4    | teacher  | **teacher**  | course     | mother   | 1          | 1         |
| 3    | 2    | services | **at_home**  | home       | mother   | 1          | 1         |
| 2    | 2    | other    | **other**    | home       | mother   | 1          | 2         |
| 1    | 3    | at_home  | **services** | home       | mother   | 1          | 2         |
| 1    | 1    | at_home  | **other**    | reputation | mother   | 1          | 3         |
| 4    | 3    | teacher  | **other**    | course     | mother   | 1          | 1         |
| 2    | 1    | other    | **other**    | course     | other    | 2          | 3         |
| 2    | 2    | services | **services** | course     | father   | 1          | 4         |
| 2    | 2    | at_home  | **services** | home       | mother   | 1          | 3         |
| 3    | 3    | services | **services** | home       | mother   | 1          | 2         |
| 2    | 2    | other    | **other**    | home       | other    | 1          | 2         |


## Running the checks in the example and evaluating the results using the user interface

A detailed explanation of [how to run the example is described here](../index.md#running-the-use-cases).

To execute the check prepared in the example using the [user interface](../../dqo-concepts/user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-string-in-set-percent-checks1.png)

1. Go to the **Monitoring** section.

    The **Monitoring Checks** section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Daily checks** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../dqo-concepts/user-interface-overview.md#check-editor).

    The **daily_text_found_in_set_percent** check has an additional parameter to select the **expected_values** that must
    be present in a string column. In our example, these values are `services`, `at_home` and `teacher`.


4. Run the activated check using the **Run check** button.

    You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

    ![Run check](https://dqops.com/docs/images/examples/daily-string-in-set-percent-run-check1.png)


5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Check results**, **Sensor readouts**, and **Execution errors**.
    The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
    The Sensor readouts category displays the values obtained by the sensors from the data source.
    The Execution errors category displays any error that occurred during the check's execution.

    Review the results which should be similar to the one below.

    ![String-in-set-percent check results](https://dqops.com/docs/images/examples/daily-string-in-set-percent-check-results1.png)

    The actual value in this example is 40%, which is below the minimum threshold level set in the warning (99%).
    The check gives a fatal result (notice the red square to the left of the check name).


6. Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner of the user interface.

    Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

7. To review the results on the [data quality dashboards](../../working-with-dqo/reviewing-results-on-data-quality-dashboards.md)
    go to the Data Quality Dashboards section and select the dashboard from the tree view on the left.

    Below you can see the results displayed on the **Current data quality checks results** dashboard located in the Check results group. This dashboard
    displays all executed checks run on tables and columns and allows reviewing their set parameters, as well as actual and expected values.

    This dashboard allows filtering data by:
    
    * time window (from last 7 days to last 6 months)
    * connection,
    * schema,
    * data group,
    * data quality dimension,
    * check category,
    * stages,
    * priorities,
    * table,
    * column,
    * check name,
    * issue severity.

    ![String-in-set-percent check results on Current data quality checks results dashboard](https://dqops.com/docs/images/examples/daily-string-in-set-percent-check-results-on-current-results-dashboard.png)

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

    ![Turn off job scheduler](https://dqops.com/docs/images/examples/turning-off-scheduler.png)

Once a schedule is set up for a particular connection, it will execute all the checks that have been configured across
all tables associated with that connection.

You can [read more about scheduling here](../../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md).

You might also want to check the [Running checks with a scheduler](../data-quality-monitoring/running-checks-with-a-scheduler.md) example.

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three minimum percent thresholds levels for the check:

- warning: 99
- error: 98
- fatal: 95

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_text_found_in_set_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/definition-of-data-quality-checks/index.md).

```yaml hl_lines="12-30"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    school:
      type_snapshot:
        column_type: STRING
        nullable: true
    Fjob:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          accepted_values:
            daily_text_found_in_set_percent:
              parameters:
                expected_values:
                - services
                - at_home
                - teacher
              warning:
                min_percent: 99.0
              error:
                min_percent: 98.0
              fatal:
                min_percent: 95.0
```

## Running the checks in the example and evaluating the results using DQOps Shell

A detailed explanation of [how to run the example is described here](../index.md#running-the-use-cases).

To execute the check prepared in the example, run the following command in DQOps Shell:

``` 
check run
```

Review the results which should be similar to the one below.
The percent of text values set in the `Fjob` column is below 95% and the check gives a fatal error.

```
Check evaluation summary per table:
+-------------+--------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection   |Table                           |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-------------+--------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|text_in_set|kaggle_student_performance.maths|1     |1             |0            |0       |0     |1           |0               |
+-------------+--------------------------------+------+--------------+-------------+--------+------+------------+----------------+
```

For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection text_in_set (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN analyzed_table.`Fjob` IN ('services', 'at_home', 'teacher')
                    THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `dqo-ai-testing`.`kaggle_student_performance`.`maths` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

You can also see the results returned by the sensor. The actual value in this example is 40.806045340050375, which is below the minimum 
threshold level set in the warning (99).

```
**************************************************
Finished executing a sensor for a check text_found_in_set_percent on the table kaggle_student_performance.maths using a sensor definition column/accepted_values/text_found_in_set_percent, sensor result count: 1

Results returned by the sensor:
+------------------+------------------------+------------------------+
|actual_value      |time_period             |time_period_utc         |
+------------------+------------------------+------------------------+
|40.806045340050375|2023-05-23T09:49:20.472Z|2023-05-23T09:49:20.472Z|
+------------------+------------------------+------------------------+
**************************************************
```

In this example, we have demonstrated how to use DQOps to verify the consistency of data in a column.
By using the [text_found_in_set_percent](../../checks/column/accepted_values/text-found-in-set-percent.md) column check,
we can monitor that the percentage of string values from a set in a column does not fall below the minimum accepted percentage.
If it does, you will get a warning, error or fatal result.

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../../dqops-installation/run-dqops-as-docker-container.md).
- For details on the [text_found_in_set_percent check used in this example, go to the check details section](../../checks/column/accepted_values/text-found-in-set-percent.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [Slack notifications](../../integrations/slack/configuring-slack-notifications.md).
- Would you like to add your own connection? Here you can find [information about supported databases and how to add new connection](../../data-sources/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you to calculate separate data quality KPI scores for different groups of rows.
