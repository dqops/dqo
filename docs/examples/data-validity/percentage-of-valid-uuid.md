# Percentage of valid UUID

We are verifying if the rows in the table contain valid UUID values.

**PROBLEM**

Here is a table with some sample customer data. The `uuid` column contains UUID data. 

We want to verify the percent of valid UUID on `uuid` column does not fall below the set threshold.

**SOLUTION**

We will verify the data using profiling [string_valid_uuid_percent](../../checks/column/strings/string-valid-uuid-percent.md) column check.
Our goal is to verify that the percent of valid UUID values in a `uuid` column does not fall below the set threshold.

In this example, we will set three minimum percent thresholds levels for the check:

- warning: 99
- error: 98
- fatal: 95

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

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

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three minimum percent thresholds levels for the check:

- warning: 99
- error: 98
- fatal: 95

The highlighted fragments in the YAML file below represent the segment where the profiling `string_valid_uuid_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="14-26"
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
      profiling_checks:
        strings:
          profile_string_valid_uuid_percent:
            comments:
            - date: 2023-05-22T12:08:09.275+00:00
              comment_by: user
              comment: "\"In this example, values in \"uuid\" column are verified\
                \ whether the percentage of valid UUID values does not fall below\
                \ the indicated thresholds.\""
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
    date:
      type_snapshot:
        column_type: DATE
        nullable: true
```
## Running the checks in the example and evaluating the results using the graphical interface

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example using the [graphical interface](../../working-with-dqo/navigating-the-graphical-interface/navigating-the-graphical-interface.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-string-valid-uuid-percent-check.png)

1. Go to **Profiling** section.

2. Select the table or column mentioned in the example description from the tree view on the left.

3. Select **Profiling Checks** tab.

4. Run the enabled check using the **Run check** button.
   ![Run check](https://dqops.com/docs/images/examples/string-valid-uuid-percent-run-check.png)

5. Access the results by clicking the **Results** button.
   ![Check details](https://dqops.com/docs/images/examples/string-valid-uuid-percent-check-details.png)

6. Review the results which should be similar to the one below.
   The actual value in this example is 75, which is below the minimum threshold level set in the warning (100.0%).
   The check gives a fatal error (notice the red square on the left of the name of the check).

   ![String-valid-uuid-percent check results](https://dqops.com/docs/images/examples/string-valid-uuid-percent-check-results.png)

7. After executing the checks, synchronize the results with your DQO cloud account using the **Synchronize** button
   located in the upper right corner of the graphical interface.

8. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
   go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. Below you can see
   the results displayed on the Issues per check dashboard showing results by check category, check and failed tests.

   ![String-valid-uuid-percent check results on Issues per check dashboard](https://dqops.com/docs/images/examples/string-valid-uuid-percent-check-results-on-issues-per-check-dashboard.png)

## Running the checks in the example and evaluating the results using DQO Shell

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

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