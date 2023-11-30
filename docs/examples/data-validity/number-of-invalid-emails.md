# Number of invalid emails

Verifies that the number of invalid emails in a monitored column does not exceed the maximum accepted count.

**PROBLEM**

Here is a table with some sample customer data. In this example, we will monitor the `email` column.

The `email` column contains email values. We want to verify that the number of invalid emails does not exceed set thresholds.

**SOLUTION**

We will verify the data using monitoring [string_invalid_email_count](../../checks/column/strings/string-invalid-email-count.md) column check.
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

A detailed explanation of [how to run the example is described here](../../#running-the-use-cases).

To execute the check prepared in the example using the [user interface](../../dqo-concepts/user-interface-overview/user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-string-invalid-email-count-checks.png)

1. Go to the **Monitoring** section.

    The Monitoring Checks section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../working-with-dqo/adding-data-source-connection/index.md).


3. Select the **Daily checks** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../../dqo-concepts/user-interface-overview/user-interface-overview/#check-editor).


4. Run the enabled check using the **Run check** button.

    You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

    ![Run check](https://dqops.com/docs/images/examples/daily-string-invalid-email-count-run-checks.png)


5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Sensor readouts**, **Check results**, and **Execution errors**. The Sensor readouts category
    displays the values obtained by the sensors from the data source. The Check results category shows the severity level
    that result from the verification of sensor readouts by set rule thresholds. The Execution errors category displays any error
    that occurred during the check's execution.

    ![Check details](https://dqops.com/docs/images/examples/daily-string-invalid-email-count-check-details.png)

    Review the results which should be similar to the one below.
   
    The actual value in this example is 22, which is above the maximum threshold level set in the warning (0).
    The check gives a fatal error (notice the red square on the left of the name of the check).

    ![String-invalid-email-count check results](https://dqops.com/docs/images/examples/daily-string-invalid-email-count-check-results.png)

6. Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner of the user interface.

    Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

7. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
    go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. 
 
    Below you can see the results displayed on the Issues count summary dashboard showing results by results per check, number of issues per connection and number of issues per table.

    ![String-invalid-email-count results on Issues count summary dashboard](https://dqops.com/docs/images/examples/daily-string-invalid-email-count-check-results-on-issues-count-summary-dashboard.png)

## Change a schedule at the connection level

With DQOps, you can easily customize when checks are run by setting schedules. You can set schedules for an entire connection,
table, or individual check.

After importing new tables, DQOps sets the schedule for 12:00 every day. Follow the steps below to change the schedule.

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

You can [read more about scheduling here](../../working-with-dqo/schedules/index.md).

You might also want to check the [Running checks with a scheduler](../data-quality-monitoring/running-checks-with-a-scheduler.md) example.

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three maximum thresholds levels for the check:

- warning: 0
- error: 10
- fatal: 15

The highlighted fragments in the YAML file below represent the segment where the monitoring `daly_string_invalid_email_count` check is configured.

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
          strings:
            daily_string_invalid_email_count:
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

A detailed explanation of [how to run the example is described here](../../#running-the-use-cases).

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
Finished executing a sensor for a check profile_string_invalid_email_count on the table dqo_ai_test_data.string_test_data_3888926926528139965 using a sensor definition column/strings/string_invalid_email_count, sensor result count: 1

Results returned by the sensor:
+------------+-----------+--------------------+
|actual_value|time_period|time_period_utc     |
+------------+-----------+--------------------+
|22          |2023-08-01 |2023-08-01T00:00:00Z|
+------------+-----------+--------------------+
**************************************************
```

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../working-with-dqo/installation/install-dqo-using-pip.md) or [run DQOps as a Docker container](../../working-with-dqo/installation/run-dqo-as-docker-container.md).
- For details on the [string_invalid_email_count check used in this example, go to the check details section](../../checks/column/strings/string-invalid-email-count.md).
- You might be interested in another validity check that [evaluates that the number of invalid IP4 address in a column does not exceed the maximum accepted count](../data-validity/number-of-invalid-IP4-address.md).
- With DQOps, you can easily customize when the checks are run at the level of the entire connection, table, or individual check. [Learn more about how to set schedules here](../../working-with-dqo/schedules/index.md). 
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/incidents-and-notifications/incidents.md) and [Slack notifications](../../integrations/slack/configuring-slack-notifications.md).