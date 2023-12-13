# Number of invalid IP4 address

Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count.

**PROBLEM**

Here is a table with some sample data. In this example, we will monitor the `ip4` column.

The `ip4` column contains IP4 address values. We want to verify the number of invalid IP4 address values on `ip4` column.

**SOLUTION**

We will verify the data using monitoring [string_invalid_ip4_address_count](../../checks/column/strings/string-invalid-ip4-address-count.md) column check.
Our goal is to verify if the number of invalid IP4 address values in `ip4` column does not exceed set thresholds.

In this example, we will set three maximum count thresholds levels for the check:

- warning: 0
- error: 5
- fatal: 10

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the number of IP4 address values exceed 0, a warning alert will be triggered.

## Data structure

The following is a fragment of the `DQOps` dataset. Some columns were omitted for clarity.  
The `ip4` column of interest contains both valid and invalid IP4 address values.

| ip4                 | result | date      |
|:--------------------|:-------|:----------|
| 256.212.62.31       | 0      | 2/12/2023 |
| 206212177195        | 0      | 3/13/2022 |
| 206-212-177-195     | 0      | 5/15/2022 |
| 138.181.31.220      | 1      | 1/11/2023 |
| 225.129.88.137000   | 1      | 1/11/2023 |
| 116.229.97.20 text  | 1      | 1/11/2023 |
| 111218.203.183.163  | 1      | 1/11/2023 |
| 239.62.26.116       | 1      | 1/11/2023 |
| 189.133.75.23       | 1      | 1/11/2023 |
| 63.219.239.5        | 1      | 1/11/2023 |
| (67.170.154.241)    | 1      | 1/11/2023 |
| 206.212.177.195     | 1      | 1/11/2023 |
| 217.22.25.65        | 1      | 1/11/2023 |
| 198.235.37.157      | 1      | 1/11/2023 |
| text 127.186.60.232 | 1      | 1/11/2023 |
| 55.190.92.1         | 1      | 1/11/2023 |
| 150.238.182.105     | 1      | 1/11/2023 |
| 233.227.62.33       | 1      | 1/11/2023 |

## Running the checks in the example and evaluating the results using the user interface

A detailed explanation of [how to run the example is described here](../../#running-the-use-cases).

To execute the check prepared in the example using the [user interface](../../dqo-concepts/user-interface-overview/user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-string-invalid-ip4-address-count-checks.png)

1. Go to the **Monitoring** section.

    The Monitoring Checks section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Daily checks** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../../dqo-concepts/user-interface-overview/user-interface-overview/#check-editor).


4. Run the enabled check using the **Run check** button.

    You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

    ![Run check](https://dqops.com/docs/images/examples/daily-string-invalid-ip4-address-count-run-checks.png)


5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Check results**, **Sensor readouts**, and **Execution errors**.
    The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
    The Sensor readouts category displays the values obtained by the sensors from the data source.
    The Execution errors category displays any error that occurred during the check's execution.

    ![Check details](https://dqops.com/docs/images/examples/daily-string-invalid-ip4-address-count-checks-details.png)

    Review the results which should be similar to the one below.
   
    The actual value in this example is 5, which is above the maximum threshold level set in the warning (0).
    The check gives a warning (notice the yellow square to the left of the check name).

    ![String-invalid-ip4-address-count check results](https://dqops.com/docs/images/examples/daily-string-invalid-ip4-address-count-checks-results.png)

6. Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner of the user interface.

    Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

7. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
    go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. 

    Below you can see the results displayed on the Issue severity status per table and day dashboard showing results by connection, schema, columns and data group.

    ![String-invalid-ip4-address-count check on Issue severity status per table and day dashboard](https://dqops.com/docs/images/examples/daily-string-invalid-ip4-address-count-checks-results-on-issue-severity-status-per-table-and-day-dashboard.png)

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

    ![Turn off job scheduler](https://dqops.com/docs/images/examples/turning-off-scheduler.png)

Once a schedule is set up for a particular connection, it will execute all the checks that have been configured across
all tables associated with that connection.

You can [read more about scheduling here](../../working-with-dqo/schedules/index.md).

You might also want to check the [Running checks with a scheduler](../data-quality-monitoring/running-checks-with-a-scheduler.md) example.

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three maximum count thresholds levels for the check:

- warning: 0
- error: 5
- fatal: 10

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_string_invalid_ip4_address_count` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="8-21"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    ip4:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          strings:
            daily_string_invalid_ip4_address_count:
              warning:
                max_count: 0
              error:
                max_count: 5
              fatal:
                max_count: 10
    result:
      type_snapshot:
        column_type: INT64
        nullable: true
    date:
      type_snapshot:
        column_type: DATE
        nullable: true
```

## Running the checks in the example and evaluating the results using DQOps Shell

A detailed explanation of [how to run the example is described here](../../#running-the-use-cases).

To execute the check prepared in the example, run the following command in DQOps Shell:

``` 
check run
```

Review the results which should be similar to the one below.
The number of the invalid IP4 address values in the `ip4` column is above 0 and the check raised an error.

```
+-----------+------------------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection |Table                                                 |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-----------+------------------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|ip4_percent|dqo_ai_test_data.contains_ip4_test_8451858895743974825|1     |1             |0            |0       |1     |0           |0               |
+-----------+------------------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
```

For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection ip4_percent (bigquery)
SQL to be executed on the connection:
SELECT
    SUM(
        CASE
            WHEN REGEXP_CONTAINS(CAST(analyzed_table.`ip4` AS STRING), r"^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$")
                THEN 0
            ELSE 1
        END
    ) AS actual_value,
    DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
    TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
FROM `dqo-ai-testing`.`dqo_ai_test_data`.`contains_ip4_test_8451858895743974825` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

You can also see the results returned by the sensor. The actual value in this example is 10, which is above the maximum
threshold level set in the warning (0).

```

**************************************************
Finished executing a sensor for a check profile_string_invalid_ip4_address_count on the table dqo_ai_test_data.contains_ip4_test_8451858895743974825 using a sensor definition column/strings/string_invalid_ip4_address_count, sensor result count: 1

Results returned by the sensor:
+------------+-----------+--------------------+
|actual_value|time_period|time_period_utc     |
+------------+-----------+--------------------+
|10          |2023-09-01 |2023-09-01T00:00:00Z|
+------------+-----------+--------------------+
**************************************************
```

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../working-with-dqo/installation/install-dqo-using-pip.md) or [run DQOps as a Docker container](../../working-with-dqo/installation/run-dqo-as-docker-container.md).
- For details on the [string_invalid_ip4_address_count check used in this example, go to the check details section](../../checks/column/strings/string-invalid-ip4-address-count.md).
- You might be interested in another validity check that [evaluates that the percentage of negative values in a column does not exceed the maximum accepted percentage](../data-validity/percentage-of-negative-values.md).
- DQOps provide you with summary statistics about your table and column. This information can be valuable in deciding which data quality checks and threshold levels should be set to monitor data quality. For more details about [Basic data statistics, click here](../../working-with-dqo/basic-data-statistics/basic-data-statistics.md). 
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/incidents-and-notifications/incidents.md) and [notifications](../../integrations/webhooks/index.md).