# Number of invalid emails

Verifies that the number of invalid emails in a monitored column does not exceed the maximum accepted count.

**PROBLEM**

Here is a table with some sample customer data. In this example, we will monitor the `email` column.

The `email` column contains email values. We want to verify that the number of invalid emails does not exceed set thresholds.

**SOLUTION**

We will verify the data using profiling [string_invalid_email_count](../../checks/column/strings/string-invalid-email-count.md) column check.
Our goal is to verify if the number of invalid email values in `email` column does not exceed set thresholds.

In this example, we will set three maximum thresholds levels for the check:

- warning: 0
- error: 10
- fatal: 15

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the number of invalid email values exceed 0, a warning alert will be triggered.

## Data structure

The following is a fragment of the `DQO` dataset. Some columns were omitted for clarity.  
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

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three maximum thresholds levels for the check:

- warning: 0
- error: 10
- fatal: 15

The highlighted fragments in the YAML file below represent the segment where the profiling `string_invalid_email_count` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="13-20"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    email:
      type_snapshot:
        column_type: STRING
        nullable: true
      profiling_checks:
        strings:
          profile_string_invalid_email_count:
            warning:
              max_count: 0
            error:
              max_count: 10
            fatal:
              max_count: 15
        schema:
          profile_column_exists:
            warning:
              expected_value: 1
          profile_column_type_changed:
            warning: {}
      monitoring_checks:
        daily:
          nulls:
            daily_nulls_percent_change_yesterday:
              warning:
                max_percent: 10.0
                exact_day: false
            daily_nulls_percent_anomaly_stationary_30_days:
              warning:
                anomaly_percent: 1.0
          datatype:
            daily_string_datatype_changed:
              warning: {}
          schema:
            daily_column_exists:
              warning:
                expected_value: 1
            daily_column_type_changed:
              warning: {}
```
## Running the checks in the example and evaluating the results using the graphical interface

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example using the [graphical interface](../../working-with-dqo/navigating-the-graphical-interface/navigating-the-graphical-interface.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-string-invalid-email-count-check.png)

1. Go to **Profiling** section.

2. Select the table or column mentioned in the example description from the tree view on the left.

3. Select **Profiling Checks** tab.

4. Run the enabled check using the **Run check** button.
   ![Run check](https://dqops.com/docs/images/examples/string-invalid-email-count-run-check.png)

5. Review the results by opening the **Check details** button.
   ![Check details](https://dqops.com/docs/images/examples/string-invalid-email-count-check-details.png)

6. You should see the results as the one below.
   The actual value in this example is 22, which is above the maximum threshold level set in the warning (0).
   The check gives a fatal error (notice the red square on the left of the name of the check).

   ![String-invalid-email-count check results](https://dqops.com/docs/images/examples/string-invalid-email-count-check-results.png)

7. After executing the checks, synchronize the results with your DQO cloud account sing the **Synchronize** button
   located in the upper right corner of the graphical interface.

8. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
   go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. Below you can see
   the results displayed on the Issues per check dashboard showing results by check category, check, failed tests and one day details.

   ![String-invalid-email-count results on Issues per check dashboard](https://dqops.com/docs/images/examples/invalid-email-count-results-on-issues-per-check-dashboard.png)

## Running the checks in the example and evaluating the results using DQO Shell

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
You should see the results as the one below.
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