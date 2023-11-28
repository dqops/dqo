# Percent of string in set

Verifies that the percentage of strings from a set in a column does not fall below the minimum accepted percentage.

**PROBLEM**

We will be testing [Student Performance](https://www.kaggle.com/datasets/whenamancodes/student-performance) dataset. 
This data approach student achievement in secondary education of two Portuguese schools. 
The data attributes include student grades, demographic, social and school related features) and it was collected by using school reports and questionnaires. 
Two datasets are provided regarding the performance in two distinct subjects: Mathematics (mat) and Portuguese language (por). 
In [Cortez and Silva, 2008], the two datasets were modeled under binary/five-level classification and regression tasks. 
Important note: the target attribute G3 has a strong correlation with attributes G2 and G1.

We are verifying if values in the tested column `Fjob` are one of accepted values.  

## Data structure

The following is a fragment of the [Student Performance](https://www.kaggle.com/datasets/whenamancodes/student-performance).
Some columns were omitted for clarity.  
The `Fjob` column of interest contains father job's values.

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

**SOLUTION**

We will verify the data using profiling [daily_string_value_in_set_percent](../../checks/column/strings/string-value-in-set-percent.md) column check.
Our data quality check will compare the values in the tested column to a set of accepted values. We're accepting only `services`, `at_home`, `teacher`.
The SQL query that will be executed will use an IN SQL clause:

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

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percent of string from a set values fall below 99, a warning alert will be triggered.

## Running the checks in the example and evaluating the results using the user interface

A detailed explanation of [how to run the example is described here](../../#running-the-use-cases).

To execute the check prepared in the example using the [user interface](../../dqo-concepts/user-interface-overview/user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-string-in-set-percent-checks.png)

1. Go to the **Monitoring** section.

    The Monitoring Checks section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../working-with-dqo/adding-data-source-connection/index.md).


3. Select the **Daily checks** tab.

    In this tab you can find a list of data quality checks.


4. Run the enabled check using the **Run check** button.

    You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

    ![Run check](https://dqops.com/docs/images/examples/daily-string-in-set-percent-run-check.png)


5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Sensor readouts**, **Check results**, and **Execution errors**. The Sensor readouts category
    displays the values obtained by the sensors from the data source. The Check results category shows the severity level
    that result from the verification of sensor readouts by set rule thresholds. The Execution errors category displays any error
    that occurred during the check's execution.

    ![Check details](https://dqops.com/docs/images/examples/daily-string-in-set-percent-check-details.png)


6. Review the results which should be similar to the one below.

    The actual value in this example is 40, which is below the minimum threshold level set in the warning (99).
    The check gives a fatal result (notice the red square on the left of the name of the check).

    ![String-in-set-percent check results](https://dqops.com/docs/images/examples/daily-string-in-set-percent-check-results.png)


7. Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner of the user interface.

    Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

8. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
    go to the Data Quality Dashboards section and select the dashboard from the tree view on the left.

    Below you can see the results displayed on the Issues count per check dashboard showing results by check category, check, failed tests and one day details.

    ![String-in-set-percent check results on Issues count per check dashboard](https://dqops.com/docs/images/examples/daily-string-in-set-percent-check-results-on-issues-count-per-check-dashboard.png)

## Configuring a schedule at connection level

With DQOps, you can easily customize when checks are run by setting schedules. You can set schedules for an entire connection,
table, or individual check.

After running the daily monitoring checks, let's set up a schedule for the entire connection to execute the checks every day at 12:00.

![Configure scheduler for the connection](https://dqops.com/docs/images/examples/configure-scheduler-for-connection.png)

1. Navigate to the **Data Source** section.

2. Choose the connection from the tree view on the left.

3. Click on the **Schedule** tab.

4. Select the Monitoring Daily tab

5. Select the **Run every day at** option and specify the time as 12:00.

6. Once you have set the schedule, click on the **Save** button to save your changes.

7. Enable the scheduler by clicking the toggle button.

![Enable job scheduler](https://dqops.com/docs/images/examples/enable-job-scheduler.png)

Once a schedule is set up for a particular connection, it will execute all the checks that have been configured across
all tables associated with that connection.

You can [read more about scheduling here](../../working-with-dqo/schedules/index.md).

You might also want to check the [Running checks with a scheduler](../data-quality-monitoring/running-checks-with-a-scheduler.md) example.

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three minimum percent thresholds levels for the check:

- warning: 99
- error: 98
- fatal: 95

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_string_in_set_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

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
          strings:
            daily_string_value_in_set_percent:
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

A detailed explanation of [how to run the example is described here](../../#running-the-use-cases).

To execute the check prepared in the example, run the following command in DQOps Shell:

``` 
check run
```

Review the results which should be similar to the one below.
The percent of string values set in the `Fjob` column is below 95% and the check gives a fatal error.

```
Check evaluation summary per table:
+-------------+--------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection   |Table                           |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-------------+--------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|string_in_set|kaggle_student_performance.maths|1     |1             |0            |0       |0     |1           |0               |
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
Executing SQL on connection string_in_set (bigquery)
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
Finished executing a sensor for a check string_in_set_percent on the table kaggle_student_performance.maths using a sensor definition column/strings/string_in_set_percent, sensor result count: 1

Results returned by the sensor:
+------------------+------------------------+------------------------+
|actual_value      |time_period             |time_period_utc         |
+------------------+------------------------+------------------------+
|40.806045340050375|2023-05-23T09:49:20.472Z|2023-05-23T09:49:20.472Z|
+------------------+------------------------+------------------------+
**************************************************
```

As you can see, the result of the check is quite low - only 40% of rows had valid values.
We will extend the list of accepted values adding also `other`, `health` to achieve a valid result. 

```
**************************************************
Executing SQL on connection string_in_set (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN analyzed_table.`Fjob` IN ('services', 'at_home', 'teacher', 'health', 'other')
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

**************************************************
Finished executing a sensor for a check string_in_set_percent on the table kaggle_student_performance.maths using a sensor definition column/strings/string_in_set_percent, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|100.0       |2023-05-23T09:58:42.010Z|2023-05-23T09:58:42.010Z|
+------------+------------------------+------------------------+
```

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../working-with-dqo/installation/install-dqo-using-pip.md) or [run DQOps as a Docker container](../../working-with-dqo/installation/run-dqo-as-docker-container.md).
- For details on the [string_value_in_set_percent check used in this example, go to the check details section](../../checks/column/strings/string-value-in-set-percent.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/incidents-and-notifications/incidents.md) and [Slack notifications](../../integrations/slack/configuring-slack-notifications.md).
- Would you like to add your own connection? Here you can find [information about supported databases and how to add new connection](../../working-with-dqo/adding-data-source-connection/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../../working-with-dqo/set-up-data-grouping/set-up-data-grouping.md) can help you to calculate separate data quality KPI scores for different groups of rows.
