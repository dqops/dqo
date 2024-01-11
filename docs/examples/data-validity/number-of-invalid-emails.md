# Number of invalid emails

## Overview

This example verifies that the number of invalid emails in a monitored column does not exceed the maximum accepted count.

**PROBLEM**

Here is a table with some sample customer data. In this example, we will monitor the `email` column.

The `email` column contains email values. We want to verify that the number of invalid emails does not exceed set thresholds.


**SOLUTION**

We will verify the data using monitoring [invalid_email_format_found](../../checks/column/patterns/invalid-email-format-found.md) column check.
Our goal is to verify if the number of invalid email values in `email` column does not exceed set thresholds.

In this example, we will set three maximum thresholds levels for the check:

- warning: 0
- error: 10
- fatal: 15

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the number of invalid email values exceed 0, a warning alert will be triggered.

## Data structure

The following is a fragment of the `DQOps` dataset. Some columns were omitted for clarity.  
The `email` column of interest contains both valid and invalid email values.

| id  | email                       | email_ok | surrounded_by_whitespace | surrounded_by_whitespace_ok | null_placeholder |
|:----|:----------------------------|:---------|:-------------------------|:----------------------------|:-----------------|
| 24  | **sam.black@coca-cola.com** | 0        | Iowa                     | 1                           | n/d              |
| 20  | **jon.doe@mail.com**        | 0        | Hawaii                   | 0                           | married          |
| 29  | **user9@mail.com**          | 0        | Texas                    | 1                           | married          |
| 5   | **!@user5@mail.com**        | 1        | Philade lphia            | 1                           | married          |
| 27  | **_example@mail.com**       | 0        | Louisiana                | 1                           |                  |
| 7   | **^&*user7@mail.com**       | 1        | Delaware                 | 1                           | empty            |
| 15  | **user7@mail**              | 1        | Connecticu               | 1                           | missing          |


## Running the checks in the example and evaluating the results using the user interface

A detailed explanation of [how to run the example is described here](../index.md#running-the-use-cases).

To execute the check prepared in the example using the [user interface](../../dqo-concepts/user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-string-invalid-email-count-checks1.png)

1. Go to the **Monitoring** section.

    The **Monitoring Checks** section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Daily checks** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../dqo-concepts/user-interface-overview.md#check-editor).


4. Run the activated check using the **Run check** button.

    You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

    ![Run check](https://dqops.com/docs/images/examples/daily-string-invalid-email-count-run-checks1.png)


5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Check results**, **Sensor readouts**, and **Execution errors**.
    The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
    The Sensor readouts category displays the values obtained by the sensors from the data source.
    The Execution errors category displays any error that occurred during the check's execution.

    Review the results which should be similar to the one below.

    ![String-invalid-email-count check results](https://dqops.com/docs/images/examples/daily-string-invalid-email-count-check-results1.png)

    The actual value in this example is 22, which is above the maximum threshold level set in the warning (0).
    The check gives a fatal error (notice the red square to the left of the check name).


6. Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner of the user interface.

    Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

7. To review the results on the [data quality dashboards](../../working-with-dqo/reviewing-results-on-data-quality-dashboards.md)
    go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. 
 
    Below you can see the results displayed on the **Current column status** dashboard located in Current status group.
    This dashboard allow data engineers and data owners to quickly evaluate the data quality of monitored
    tables and columns. The dashboards display a color-coded status that indicates the severity level detected by run
    checks. When the status is green, it means that the monitored column has no data quality issues. However, if the status
    is yellow, orange, or red, it indicates that there were some issues detected. The dashboard also displays the number
    of detected issues per severity threshold, making it easier to identify and address tables and columns with issues.

    These dashboards allow filtering data by:
    
    * time frame,
    * connection,
    * schema,
    * data quality dimension,
    * check category,
    * data group,
    * table,
    * column.
    
    ![String-invalid-email-count results on Current column status dashboard](https://dqops.com/docs/images/examples/daily-string-invalid-email-count-check-results-on-current-column-status-dashboard.png)

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

In this example, we have set three maximum thresholds levels for the check:

- warning: 0
- error: 10
- fatal: 15

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_invalid_email_format_found` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="12-25"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    id:
      type_snapshot:
        column_type: INT64
        nullable: true
    email:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          patterns:
            daily_invalid_email_format_found:
              warning:
                max_count: 0
              error:
                max_count: 10
              fatal:
                max_count: 15
    email_ok:
      type_snapshot:
        column_type: INT64
        nullable: true
    surrounded_by_whitespace:
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
The number of the invalid email values in the `email` column above 15 and the check raised the fatal error.

```
+-------------------+-----------------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection         |Table                                                |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-------------------+-----------------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|invalid_email_count|dqo_ai_test_data.string_test_data_3888926926528139965|1     |1             |0            |0       |0     |1           |0               |
+-------------------+-----------------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
```

For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection invalid_email_count (bigquery)
SQL to be executed on the connection:
SELECT
    SUM(
        CASE
            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`email` AS STRING), r"^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$")
                THEN 0
            ELSE 1
        END
    ) AS actual_value,
    DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
    TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
FROM `dqo-ai-testing`.`dqo_ai_test_data`.`string_test_data_3888926926528139965` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

You can also see the results returned by the sensor. The actual value of invalid emails in this example is 22, which is above the maximum
threshold level set in the fatal error (15).

```
**************************************************
Finished executing a sensor for a check profile_invalid_email_format_found on the table dqo_ai_test_data.string_test_data_3888926926528139965 using a sensor definition column/patterns/invalid_email_format_found, sensor result count: 1

Results returned by the sensor:
+------------+-----------+--------------------+
|actual_value|time_period|time_period_utc     |
+------------+-----------+--------------------+
|22          |2023-08-01 |2023-08-01T00:00:00Z|
+------------+-----------+--------------------+
**************************************************
```

In this example, we have demonstrated how to use DQOps to verify the validity of data in a column.
By using the [invalid_email_format_found](../../checks/column/patterns/invalid-email-format-found.md) column check, we can monitor that
the number of invalid emails in a monitored column does not exceed the maximum accepted count. If it does, you will get a warning, error or fatal result.

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../../dqops-installation/run-dqops-as-docker-container.md).
- For details on the [invalid_email_format_found check used in this example, go to the check details section](../../checks/column/patterns/invalid-email-format-found.md).
- You might be interested in another validity check that [evaluates that the number of invalid IP4 address in a column does not exceed the maximum accepted count](../data-validity/number-of-invalid-IP4-address.md).
- With DQOps, you can easily customize when the checks are run at the level of the entire connection, table, or individual check. [Learn more about how to set schedules here](../../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md). 
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [Slack notifications](../../integrations/slack/configuring-slack-notifications.md).