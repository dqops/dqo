# Percentage of passed SQL condition on table

Table level check that ensures that a minimum percentage of rows passed a custom SQL condition (expression).

**PROBLEM**

[America’s Health Rankings](https://www.americashealthrankings.org/about/methodology/our-reports) provides an analysis of national health on a state-by-state basis
by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

The platform analyzes more than 340 measures of behaviors, social and economic factors, physical environment and clinical care data.
Data is based on public-use data sets, such as the U.S. Census and the Centers for Disease Control and Prevention’s Behavioral Risk Factor Surveillance System (BRFSS),
the world’s largest, annual population-based telephone survey of over 400,000 people.

We want to verify the percent of rows passed a custom SQL condition (expression).

We will try 3 SQL expressions, so you can see that improving your expression configuration can achieve more accurate results.

1. upper_ci > lower_ci - here we assume that upper_ci column is greater than lower_ci
2. upper_ci >= lower_ci - here we add a possibility that it also can be equal
3. upper_ci >= lower_ci or upper_ci is NULL or lower_ci is NULL - and here we add a possibility of NULLs in rows

**SOLUTION**

We will verify the data of `bigquery-public-data.america_health_rankings.ahr` using profiling
[sql_condition_passed_percent](../../checks/table/sql/sql-condition-passed-percent-on-table.md) table check.
Our goal is to verify if the percentage of rows passed a custom SQL condition does not fall below the setup thresholds.

In this example, we will set three minimum percentage thresholds levels for the check:

- warning: 100.0%
- error: 99.0%
- fatal: 95.0%

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of data falls below 100.0%, a warning alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.america_health_rankings.ahr` dataset. Some columns were omitted for clarity.

| value | lower_ci | upper_ci | source                                             | source_date |
|:------|:---------|:---------|:---------------------------------------------------|:------------|
| 87    | **87**   | **87**   | U.S. Census Bureau, American Community Survey PUMS | 2015-2019   |
| 87    | **87**   | **87**   | U.S. Census Bureau, American Community Survey PUMS | 2015-2019   |
| 87    | **86**   | **87**   | U.S. Census Bureau, American Community Survey PUMS | 2015-2019   |
| 79    | **79**   | **79**   | U.S. Census Bureau, American Community Survey PUMS | 2015-2019   |
| 87    | **86**   | **87**   | U.S. Census Bureau, American Community Survey PUMS | 2015-2019   |
| 87    | **87**   | **88**   | U.S. Census Bureau, American Community Survey PUMS | 2015-2019   |
| 88    | **88**   | **88**   | U.S. Census Bureau, American Community Survey PUMS | 2015-2019   |
| 77    | **76**   | **77**   | U.S. Census Bureau, American Community Survey PUMS | 2015-2019   |
| 78    | **78**   | **79**   | U.S. Census Bureau, American Community Survey PUMS | 2015-2019   |

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three minimum percentage thresholds levels for the check:

- warning: 100.0%
- error: 99.0%
- fatal: 95.0%

The highlighted fragments in the YAML file below represent the segment where the profiling `sql_condition_passed_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

### 1. upper_ci > lower_ci

```yaml hl_lines="7-17"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  profiling_checks:
    sql:
      sql_condition_passed_percent_on_table:
        parameters:
          sql_condition: upper_ci > lower_ci
        warning:
          min_percent: 100.0
        error:
          min_percent: 99.0
        fatal:
          min_percent: 95.0
  columns:
    edition:
      type_snapshot:
        column_type: INT64
        nullable: true
```

## Running the checks in the example and evaluating the results

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
You should see the results as the one below.
The percent of passed SQL expressions is below the 95.0% and the check raised the Fatal error.

```
Check evaluation summary per table:
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection             |Table                      |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|america_health_rankings|america_health_rankings.ahr|1     |1             |0            |0       |0     |1           |0               |
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
```
For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection america_health_rankings (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
                         CASE
                             WHEN (upper_ci > lower_ci)
                                  THEN 1
                             ELSE 0
                         END) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`america_health_rankings`.`ahr` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```
You can also see the results returned by the sensor. The actual value in this example is 85.585238226383920%, which is below the minimum
threshold level set in the Fatal error (95.0%).
```
**************************************************
Finished executing a sensor for a check sql_condition_passed_percent_on_table on the table america_health_rankings.ahr using a sensor definition table/sql/sql_condition_passed_percent, sensor result count: 1

Results returned by the sensor:
+-----------------+------------------------+------------------------+
|actual_value     |time_period             |time_period_utc         |
+-----------------+------------------------+------------------------+
|85.58523822638392|2023-05-18T08:25:58.911Z|2023-05-18T08:25:58.911Z|
+-----------------+------------------------+------------------------+
**************************************************
```
### 2. upper_ci >= lower_ci

```yaml hl_lines="7-17"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  profiling_checks:
    sql:
      sql_condition_passed_percent_on_table:
        parameters:
          sql_condition: upper_ci >= lower_ci
        warning:
          min_percent: 100.0
        error:
          min_percent: 99.0
        fatal:
          min_percent: 95.0
  columns:
    edition:
      type_snapshot:
        column_type: INT64
        nullable: true
```

## Running the checks in the example and evaluating the results

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
You should see the results as the one below.
The percent of passed SQL expressions is still below the 95.0% and the check raised the Fatal error.

```
Check evaluation summary per table:
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection             |Table                      |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|america_health_rankings|america_health_rankings.ahr|1     |1             |0            |0       |0     |1           |0               |
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
```
For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection america_health_rankings (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
                         CASE
                             WHEN (upper_ci >= lower_ci)
                                  THEN 1
                             ELSE 0
                         END) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`america_health_rankings`.`ahr` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```
You can also see the results returned by the sensor. Now the actual value in this example is 92.6025888185073%, which is below the minimum
threshold level set in the Fatal error (95.0%). 
```
**************************************************
Finished executing a sensor for a check sql_condition_passed_percent_on_table on the table america_health_rankings.ahr using a sensor definition table/sql/sql_condition_passed_percent, sensor result count: 1

Results returned by the sensor:
+----------------+------------------------+------------------------+
|actual_value    |time_period             |time_period_utc         |
+----------------+------------------------+------------------------+
|92.6025888185073|2023-05-18T08:29:25.667Z|2023-05-18T08:29:25.667Z|
+----------------+------------------------+------------------------+
**************************************************
```
### 3. upper_ci >= lower_ci or upper_ci is NULL or lower_ci is NULL

```yaml hl_lines="7-17"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  profiling_checks:
    sql:
      sql_condition_passed_percent_on_table:
        parameters:
          sql_condition: upper_ci >= lower_ci or upper_ci is NULL or lower_ci is NULL
        warning:
          min_percent: 100.0
        error:
          min_percent: 99.0
        fatal:
          min_percent: 95.0
  columns:
    edition:
      type_snapshot:
        column_type: INT64
        nullable: true
```

## Running the checks in the example and evaluating the results

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
You should see the results as the one below.
The percent of passed SQL expressions is 100.0% so the result is valid.

```
Check evaluation summary per table:
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection             |Table                      |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|america_health_rankings|america_health_rankings.ahr|1     |1             |1            |0       |0     |0           |0               |
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
```
For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection america_health_rankings (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
                         CASE
                             WHEN (upper_ci >= lower_ci or upper_ci is NULL or lower_ci is NULL)
                                  THEN 1
                             ELSE 0
                         END) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`america_health_rankings`.`ahr` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```
You can also see the results returned by the sensor. The actual value in this example is 100.0%.
```
**************************************************
Finished executing a sensor for a check sql_condition_passed_percent_on_table on the table america_health_rankings.ahr using a sensor definition table/sql/sql_condition_passed_percent, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|100.0       |2023-05-18T08:38:22.311Z|2023-05-18T08:38:22.311Z|
+------------+------------------------+------------------------+
**************************************************
```