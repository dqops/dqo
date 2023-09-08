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

We will verify the data using profiling [string_value_in_set_percent](../../checks/column/strings/string-value-in-set-percent.md) column check.
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

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percent of string from a set values fall below 99, a warning alert will be triggered.

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three minimum percent thresholds levels for the check:

- warning: 99
- error: 98
- fatal: 95

The highlighted fragments in the YAML file below represent the segment where the profiling `string_in_set_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="50-61"
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
    sex:
      type_snapshot:
        column_type: STRING
        nullable: true
    age:
      type_snapshot:
        column_type: INT64
        nullable: true
    address:
      type_snapshot:
        column_type: STRING
        nullable: true
    famsize:
      type_snapshot:
        column_type: STRING
        nullable: true
    Pstatus:
      type_snapshot:
        column_type: STRING
        nullable: true
    Medu:
      type_snapshot:
        column_type: INT64
        nullable: true
    Fedu:
      type_snapshot:
        column_type: INT64
        nullable: true
    Mjob:
      type_snapshot:
        column_type: STRING
        nullable: true
    Fjob:
      type_snapshot:
        column_type: STRING
        nullable: true
      profiling_checks:
        strings:
          profile_string_value_in_set_percent:
            parameters:
              values:
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
## Running the checks in the example and evaluating the results using the graphical interface

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example using the [graphical interface](../../working-with-dqo/navigating-the-graphical-interface/navigating-the-graphical-interface.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-string-in-set-percent-checks.png)

1. Go to **Profiling** section.

2. Select the table or column mentioned in the example description from the tree view on the left.

3. Select **Profiling Checks** tab.

4. Run the enabled check using the **Run check** button.
   ![Run check](https://dqops.com/docs/images/examples/string-in-set-percent-run-checks.png)

5. Access the results by clicking the **Results** button.
   ![Check details](https://dqops.com/docs/images/examples/string-in-set-percent-check-details.png)

6. Review the results which should be similar to the one below.
   The actual value in this example is 40, which is below the minimum threshold level set in the warning (99.0%).
   The check gives a fatal error (notice the red square on the left of the name of the check).

   ![String-in-set-percent check results](https://dqops.com/docs/images/examples/string-in-set-percent-check-results.png)

7. After executing the checks, synchronize the results with your DQO cloud account using the **Synchronize** button
   located in the upper right corner of the graphical interface.

8. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
   go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. Below you can see
   the results displayed on the Issues per check dashboard showing results by issues per check category, check and failed tests.

   ![String-in-set-percent check results on issues per check dashboard](https://dqops.com/docs/images/examples/string-in-set-percent-check-results-on-issues-per-check-dashboard.png)

## Running the checks in the example and evaluating the results using DQO Shell

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

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
