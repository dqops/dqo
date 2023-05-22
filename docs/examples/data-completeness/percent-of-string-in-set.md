# Percent of string in set

Verifies that the percentage of strings from a set in a column does not fall below the minimum accepted percentage.

**PROBLEM**

Dream Housing Finance company deals in all kinds of home loans. They have presence across all urban, semi urban and rural areas. 
Customer first applies for home loan and after that company validates the customer eligibility for loan. 
Company wants to automate the loan eligibility process (real time) based on customer detail provided while filling online application form. 
These details are Gender, Marital Status, Education, Number of Dependents, Income, Loan Amount, Credit History and others. 
To automate this process, they have provided a dataset to identify the customers segments that are eligible for loan amount so that they can specifically target these customers.

We want to verify that the percent of string values in a `Gender` column with a custom parameter does not fall below the set threshold.

**SOLUTION**

We will verify the data using profiling [string_in_set_percent](../../checks/column/strings/string-in-set-percent.md) column check.
We will provide 'Male' in the check parameter and verify if the percent of such values in the `Gender` column does not fall below the set threshold.

In this example, we will set three minimum percent thresholds levels for the check:

- warning: 75
- error: 65
- fatal: 55

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percent of string from a set values fall below 75, a warning alert will be triggered.

## Data structure

The following is a fragment of the Dream Housing Finance company dataset. Some columns were omitted for clarity.  
The `Gender` column of interest contains gender data values.

| Loan_ID  | Gender     | Married | Dependents | Education    | Self_Employed |
|:---------|:-----------|:--------|:-----------|:-------------|:--------------|
| LP002188 | **Male**   | FALSE   | 0          | Graduate     | FALSE         |
| LP001849 | **Male**   | FALSE   | 0          | Not Graduate | FALSE         |
| LP002140 | **Male**   | FALSE   | 0          | Graduate     | FALSE         |
| LP002006 | **Female** | FALSE   | 0          | Graduate     | FALSE         |
| LP002958 | **Male**   | FALSE   | 0          | Graduate     | FALSE         |
| LP002545 | **Male**   | FALSE   | 2          | Graduate     | FALSE         |
| LP002472 | **Male**   | FALSE   | 2          | Graduate     | FALSE         |
| LP002255 | **Male**   | FALSE   | 3+         | Graduate     | FALSE         |
| LP002494 | **Male**   | FALSE   | 0          | Graduate     | FALSE         |
| LP002637 | **Male**   | FALSE   | 0          | Not Graduate | FALSE         |
| LP001875 | **Male**   | FALSE   | 0          | Graduate     | FALSE         |
| LP001841 | **Male**   | FALSE   | 0          | Not Graduate | TRUE          |
| LP002940 | **Male**   | FALSE   | 0          | Not Graduate | FALSE         |
| LP001519 | **Female** | FALSE   | 0          | Graduate     | FALSE         |

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three minimum percent thresholds levels for the check:

- warning: 75
- error: 65
- fatal: 55

The highlighted fragments in the YAML file below represent the segment where the profiling `string_in_set_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="12-33"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    Loan_ID:
      type_snapshot:
        column_type: STRING
        nullable: true
    Gender:
      type_snapshot:
        column_type: STRING
        nullable: true
      profiling_checks:
        strings:
          string_in_set_percent:
            comments:
            - date: 2023-05-22T12:08:09.275+00:00
              comment_by: user
              comment: "\"In this example, values in the \"Gender\" column are verified\
                \ whether the percentage of string values does not fall below\
                \ the indicated thresholds.\""
            parameters:
              values:
              - Male
            warning:
              min_percent: 75.0
            error:
              min_percent: 65.0
            fatal:
              min_percent: 55.0
    Married:
      type_snapshot:
        column_type: BOOL
        nullable: true
    Dependents:
      type_snapshot:
        column_type: STRING
        nullable: true
    Education:
      type_snapshot:
        column_type: STRING
        nullable: true
    Self_Employed:
      type_snapshot:
        column_type: BOOL
        nullable: true
```

## Running the checks in the example and evaluating the results

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
You should see the results as the one below.
The percent of string values set in the `Gender` column is above 75% and the check gives a valid result.
```
Check evaluation summary per table:
+-------------+-------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection   |Table                                      |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-------------+-------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|string_in_set|kaggle_loan.eligibility_prediction_for_loan|1     |1             |1            |0       |0     |0           |0               |
+-------------+-------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
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
                WHEN analyzed_table.`Gender` IN ('Male')
                    THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `dqo-ai-testing`.`kaggle_loan`.`eligibility_prediction_for_loan` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

You can also see the results returned by the sensor. The actual value in this example is 79.64169381107492, which is above the minimum 
threshold level set in the warning (75).

```
**************************************************
Finished executing a sensor for a check string_in_set_percent on the table kaggle_loan.eligibility_prediction_for_loan using a sensor definition column/strings/string_in_set_percent, sensor result count: 1

Results returned by the sensor:
+-----------------+------------------------+------------------------+
|actual_value     |time_period             |time_period_utc         |
+-----------------+------------------------+------------------------+
|79.64169381107492|2023-05-22T11:53:09.695Z|2023-05-22T11:53:09.695Z|
+-----------------+------------------------+------------------------+
**************************************************
```