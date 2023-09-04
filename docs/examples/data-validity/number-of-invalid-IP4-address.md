# Number of invalid IP4 address

Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count.

**PROBLEM**

Here is a table with some sample customer data. In this example, we will monitor the `ip4` column.

The `ip4` column contains IP4 address values. We want to verify the number of invalid IP4 address values on `ip4` column.

**SOLUTION**

We will verify the data using profiling [string_invalid_ip4_address_count](../../checks/column/strings/string-invalid-ip4-address-count.md) column check.
Our goal is to verify if the number of invalid IP4 address values in `ip4` column does not exceed set thresholds.

In this example, we will set three maximum count thresholds levels for the check:

- warning: 0
- error: 5
- fatal: 10

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the number of IP4 address values exceed 0, a warning alert will be triggered.

## Data structure

The following is a fragment of the `DQO` dataset. Some columns were omitted for clarity.  
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


## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three maximum count thresholds levels for the check:

- warning: 0
- error: 5
- fatal: 10

The highlighted fragments in the YAML file below represent the segment where the profiling `string_invalid_ip4_address_count` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="14-25"
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
      profiling_checks:
        strings:
          profile_string_invalid_ip4_address_count:
            comments:
            - date: 2023-09-01T09:56:57.736
              comment_by: user
              comment: "In this example, values in \"ip4\" column are verified whether\
                \ the number of IP4 address values does not exceed the indicated thresholds."
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
## Running the checks in the example and evaluating the results using the graphical interface

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example using the [graphical interface](../../working-with-dqo/navigating-the-graphical-interface/navigating-the-graphical-interface.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-invalid-IP4-address-check.png)

1. Go to **Profiling** section.

2. Select the table or column mentioned in the example description from the tree view on the left.

3. Select **Profiling Checks** tab.

4. Run the enabled check using the **Run check** button.
   ![Run check](https://dqops.com/docs/images/examples/invalid-IP4-address-run-check.png)

5. Review the results by opening the **Check details** button.
   ![Check details](https://dqops.com/docs/images/examples/invalid-IP4-address-check-details.png)

6. You should see the results as the one below.
   The actual value in this example is 10, which is above the maximum threshold level set in the warning (0).
   The check gives an error (notice the orange square on the left of the name of the check).

   ![String-invalid-ip4-address-count check results](https://dqops.com/docs/images/examples/invalid-IP4-address-check-results.png)

7. After executing the checks, synchronize the results with your DQO cloud account sing the **Synchronize** button
   located in the upper right corner of the graphical interface.

8. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
   go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. Below you can see
   the results displayed on the Current column status by dimension dashboard showing results by connection, schema, dimension and data group.

   ![String-invalid-ip4-address-count check on Current column status by dimension dashboard](https://dqops.com/docs/images/examples/invalid-ip4-address-on-current-column-status-by-dimension-dashboard.png)

## Running the checks in the example and evaluating the results using DQO Shell

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
You should see the results as the one below.
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