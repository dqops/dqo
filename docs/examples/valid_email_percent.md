# Valid email percent

Verifies that the percentage of valid email values in a column does not exceed the maximum accepted percentage.

**PROBLEM**

Here is a table with some sample customer data. In this example, we will monitor the `email` column and verify that each email is in the correct format.

The `email` column contains email values. We want to verify the percent of invalid email values on `email` column.

**SOLUTION**

We will verify the data using profiling [valid_email_percent](../checks/column/pii/valid-email-percent.md) column check.
Our goal is to verify if the percentage of valid email values on `email` column does not fall below setup thresholds.

In this example, we will set three minimum percentage thresholds levels for the check:

- warning: 99.0%
- error: 98.0%
- fatal: 95.0%

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of valid email values falls below 99.0%, a warning alert will be triggered.

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

In this example, we have set three minimum percentage thresholds levels for the check:

- warning: 99.0%
- error: 98.0%
- fatal: 95.0%

The highlighted fragments in the YAML file below represent the segment where the profiling `valid_email_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../dqo-concepts/checks/index.md).

```yaml hl_lines="12-31"
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
      profiling_checks:
        pii:
          valid_email_percent:
            comments:
            - date: 2023-05-04T11:13:34.182+00:00
              comment_by: user
              comment: "In this example, values in \"email\" column are verified whether\
                \ the percentage of invalid email values does not exceed the indicated\
                \ thresholds."
            parameters: {}
            warning:
              min_percent: 99.0
            error:
              min_percent: 98.0
            fatal:
              min_percent: 95.0
    email_ok:
      type_snapshot:
        column_type: INT64
        nullable: true
    surrounded_by_whitespace:
      type_snapshot:
        column_type: STRING
        nullable: true
    surrounded_by_whitespace_ok:
      type_snapshot:
        column_type: INT64
        nullable: true
    null_placeholder:
      type_snapshot:
        column_type: STRING
        nullable: true
```
## Running the checks in the example and evaluating the results

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
You should see the results as the one below.
The percent of the invalid email values in the `email` column is above 95.0% and the check raised the Fatal error.
```
Check evaluation summary per table:
+--------------------+-----------------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection          |Table                                                |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+--------------------+-----------------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|valid_email_percent_|dqo_ai_test_data.string_test_data_3888926926528139965|1     |1             |0            |0       |0     |1           |0               |
+--------------------+-----------------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
```
For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection valid_email_percent_ (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`email` AS STRING), r"^[A-Za-z_]+[A-Za-z0-9._]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$")
                    THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `dqo-ai-testing`.`dqo_ai_test_data`.`string_test_data_3888926926528139965` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```
You can also see the results returned by the sensor. The actual value of valid email values in this example is 40.0%, which is below the minimum
threshold level set in the warning (99.0%).
```
**************************************************
Finished executing a sensor for a check valid_email_percent on the table dqo_ai_test_data.string_test_data_3888926926528139965 using a sensor definition column/pii/valid_email_percent, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|40.0        |2023-05-04T11:14:02.697Z|2023-05-04T11:14:02.697Z|
+------------+------------------------+------------------------+
**************************************************
```