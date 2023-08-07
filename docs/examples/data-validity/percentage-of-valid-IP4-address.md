# Percentage of valid IP4 address

Verifies that the percentage of valid IP4 address in a column does not fall below the minimum accepted percentage.

**PROBLEM**

Here is a table with some sample customer data. In this example, we will monitor the `ip4` column and verify that each IP4 address is in the correct format.

The `ip4` column contains IP4 address values. We want to verify the percent of valid IP4 address values on `ip4` column.

**SOLUTION**

We will verify the data using profiling [valid_ip4_address_percent](../../checks/column/pii/valid-ip4-address-percent.md) column check.
Our goal is to verify if the percentage of valid IP4 address values in `ip4` column does not fall below set thresholds.

In this example, we will set three minimum percentage thresholds levels for the check:

- warning: 99.0%
- error: 98.0%
- fatal: 95.0%

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of IP4 address values falls below 99.0%, a warning alert will be triggered.

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

In this example, we have set three minimum percentage thresholds levels for the check:

- warning: 99.0%
- error: 98.0%
- fatal: 95.0%

The highlighted fragments in the YAML file below represent the segment where the profiling `valid_ip4_address_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="8-25"
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
        pii:
          valid_ip4_address_percent:
            comments:
            - date: 2023-05-11T08:28:27.484+00:00
              comment_by: user
              comment: "\"In this example, values in \"ip4\" column are verified whether\
                \ the percentage of IP4 address values reaches the indicated thresholds.\""
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

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-valid-ip4-address-percent-check.png)

1. Go to **Profiling** section.

2. Select the table or column mentioned in the example description from the tree view on the left.

3. Select **Advanced Profiling** tab.

4. Run the enabled check using the **Run check** button.
   ![Run check](https://dqops.com/docs/images/examples/valid-ip4-address-percent-run-check.png)

5. Review the results by opening the **Check details** button.
   ![Check details](https://dqops.com/docs/images/examples/valid-ip4-address-percent-check-details.png)

6. You should see the results as the one below.
   The actual value in this example is 50, which is below the minimum threshold level set in the warning (99.0%).
   The check gives a fatal error (notice the red square on the left of the name of the check).

   ![Valid-ip4-address-percent check results](https://dqops.com/docs/images/examples/valid-ip4-address-percent-check-results.png)

7. After executing the checks, synchronize the results with your DQO cloud account sing the **Synchronize** button
   located in the upper right corner of the graphical interface.

8. You can now [review the results on the data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md) as described in Working with DQO section.

## Running the checks in the example and evaluating the results using DQO Shell

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
You should see the results as the one below.
The percent of the valid IP4 address values in the `ip4` column is below 95.0% and the check raised fatal error.
```
Check evaluation summary per table:
+-----------+------------------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection |Table                                                 |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-----------+------------------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|ip4_percent|dqo_ai_test_data.contains_ip4_test_8451858895743974825|1     |1             |0            |0       |0     |1           |0               |
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
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN REGEXP_CONTAINS(CAST(analyzed_table.`ip4` AS STRING), r"^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$")
                    THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `dqo-ai-testing`.`dqo_ai_test_data`.`contains_ip4_test_8451858895743974825` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```
You can also see the results returned by the sensor. The actual value in this example is 50.0%, which is below the minimal
threshold level set in the warning (99.0%).

```
**************************************************
Finished executing a sensor for a check valid_ip4_address_percent on the table dqo_ai_test_data.contains_ip4_test_8451858895743974825 using a sensor definition column/pii/valid_ip4_address_percent, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|50.0        |2023-05-11T08:28:47.548Z|2023-05-11T08:28:47.548Z|
+------------+------------------------+------------------------+
**************************************************
```