# Percentage of valid UUID

Verifies that the percentage of valid UUID values in a column does not fall below the minimum accepted percentage.

**PROBLEM**

Here is a table with some sample customer data. The `uuid` column contains UUID data. 

We want to verify the percent of valid UUID on `uuid` column does not fall below the set threshold.

**SOLUTION**

We will verify the data using monitoring [string_valid_uuid_percent](../../checks/column/strings/string-valid-uuid-percent.md) column check.
Our goal is to verify that the percent of valid UUID values in a `uuid` column does not fall below the set threshold.

In this example, we will set three minimum percent thresholds levels for the check:

- warning: 99
- error: 98
- fatal: 95

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of valid UUID values fall below 99, a warning alert will be triggered.

## Data structure

The following is a fragment of the `DQOps` dataset.
The `uuid ` column of interest contains both valid and invalid UUID values.

| uuid                                      | result | date      |
|:------------------------------------------|:-------|:----------|
| **26x5e2be-925b-11ed-a1eb-0242ac120002**  | 0      | 2/12/2023 |
| **wrong UUID**                            | 0      | 5/15/2022 |
| **26b5e2be-925b-112ed-a1eb-0242ac120002** | 0      | 3/13/2022 |
| **2137**                                  | 0      | 6/16/2022 |
| **26b5d9a4-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5c586-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5dd64-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5dc24-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5cdc4-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5e2be-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5cc84-925b 11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5d5ee-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5ca7c 925b 11ed a1eb 0242ac120002**  | 1      | 1/11/2023 |
| **26b5d486-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5df9e-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5d21a-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5e124-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5daf8-925b-11ed-a1eb-0242ac120002**  | 1      | 1/11/2023 |
| **26b5c8b0925b11eda1eb0242ac120002**      | 1      | 1/11/2023 |

## Running the checks in the example and evaluating the results using the user interface

A detailed explanation of [how to run the example is described here](../../#running-the-use-cases).

To execute the check prepared in the example using the [user interface](../../dqo-concepts/user-interface-overview/user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-string-valid-uuid-percent-checks.png)

1. Go to the **Monitoring** section.

    The Monitoring Checks section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Daily checks** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../../dqo-concepts/user-interface-overview/user-interface-overview/#check-editor).


4. Run the enabled check using the **Run check** button.

    You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

    ![Run check](https://dqops.com/docs/images/examples/daily-string-valid-uuid-percent-run-checks.png)


5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Check results**, **Sensor readouts**, and **Execution errors**.
    The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
    The Sensor readouts category displays the values obtained by the sensors from the data source.
    The Execution errors category displays any error that occurred during the check's execution.

    ![Check details](https://dqops.com/docs/images/examples/daily-string-valid-uuid-percent-checks-details.png)

    Review the results which should be similar to the one below.
   
    The actual value in this example is 75, which is below the minimum threshold level set in the warning (100.0%).
    The check gives a fatal error (notice the red square to the left of the check name).

    ![String-valid-uuid-percent check results](https://dqops.com/docs/images/examples/daily-string-valid-uuid-percent-checks-result.png)

6. Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner of the user interface.

    Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

7. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
    go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. 

    Below you can see the results displayed on the Issue severity status per check and day dashboard showing results by connections, schemas, tables, columns and highest issue severity per check and day of month.

    ![String-valid-uuid-percent check results on Issue severity status per check and day dashboard](https://dqops.com/docs/images/examples/daily-string-valid-uuid-percent-checks-result-on-issue-severity-status-per-check-and-day-dashboard.png)

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three minimum percent thresholds levels for the check:

- warning: 99
- error: 98
- fatal: 95

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_string_valid_uuid_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="8-21"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    uuid:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          strings:
            daily_string_valid_uuid_percent:
              warning:
                min_percent: 99.0
              error:
                min_percent: 98.0
              fatal:
                min_percent: 95.0
    result:
      type_snapshot:
        column_type: INT64
        nullable: true
```

## Running the checks in the example and evaluating the results using DQOps Shell

A detailed explanation of [how to run the example is described here](../../#running-the-use-cases).

To execute the check prepared in the example, run the following command in DQOps Shell:

``` 
check run
```

Review the results which should be similar to the one below.
The percent of valid UUID values in the `uuid` column is below 95 and the check raised fatal error.

```
Check evaluation summary per table:
+------------------+----------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection        |Table                                         |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+------------------+----------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|valid_uuid_percent|dqo_ai_test_data.uuid_test_7014625119307825231|1     |1             |0            |0       |0     |1           |0               |
+------------------+----------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
```

For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection valid_uuid_percent (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN REGEXP_CONTAINS(SAFE_CAST(analyzed_table.`uuid` AS STRING), r"^[0-9a-fA-F]{8}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{4}[\s-]?[0-9a-fA-F]{12}$")
                    THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `dqo-ai-testing`.`dqo_ai_test_data`.`uuid_test_7014625119307825231` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

You can also see the results returned by the sensor. The actual value in this example is 75, which is below the minimum 
threshold level set in the warning (99).

```
**************************************************
Finished executing a sensor for a check string_valid_uuid_percent on the table dqo_ai_test_data.uuid_test_7014625119307825231 using a sensor definition column/strings/string_valid_uuid_percent, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|75.0        |2023-05-22T12:08:24.199Z|2023-05-22T12:08:24.199Z|
+------------+------------------------+------------------------+
**************************************************
```

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../working-with-dqo/installation/install-dqo-using-pip.md) or [run DQOps as a Docker container](../../working-with-dqo/installation/run-dqo-as-docker-container.md).
- For details on the [string_valid_uuid_percent check used in this example, go to the check details section](../../checks/column/strings/string-valid-uuid-percent.md).
- You might be interested in another validity check that [evaluates that ensures that the percentage of rows containing valid currency codes does not exceed set thresholds](../data-validity/percentage-of-values-that-contains-usa-zipcode.md).
- With DQOps, you can easily customize when the checks are run at the level of the entire connection, table, or individual check. [Learn more about how to set schedules here](../../working-with-dqo/schedules/index.md). 
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/incidents-and-notifications/incidents.md) and [Slack notifications](../../integrations/slack/configuring-slack-notifications.md).