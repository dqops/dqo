## Description
The `distinct_count` sensor counts unique values in a specified column.

## When to use
This is a standard quality check, mostly used when we would like to simply ensure that columns contain only unique
values.

## Used sensor

The query for this check counts unique values in a specified column.

Successfully classified records are assigned value of 1, and any other values, 0. Those values are then summed (so effectively we perform count of valid values).

Please see [Distinct count](../../../sensor_reference/uniqueness/distinct_count/distinct_count.md) for more information.

## Accepted rules

`Min_count` rule verifies if a data quality check reading is greater or equal a minimum value.

This rule has one parameter that should be configured for each alert :

- `low` :
  <br/>rule threshold for a low severity (1) alert
    - `min_value`: float
    - minimum accepted value for the `actual_value` returned by the sensor (inclusive)
- `medium` :
  <br/>rule threshold for a medium severity (2) alert
    - `min_value`: float
    - minimum accepted value for the `actual_value` returned by the sensor (inclusive)
- `high` :
  <br/>rule threshold for a high severity (3) alert
    - `min_value`: float
    - minimum accepted value for the `actual_value` returned by the sensor (inclusive)

`Count_equals` verifies that a data quality check reading equals a given value. A margin of error may be configured.

This check has two parameters that should be configured for each alert:

- `low`:
  <br/>rule threshold for a low severity (1) alert
    - `expectedValue`: _float_
      <br/>expected value for the actual_value returned by the sensor. The sensor value should equal `expected_value +/- the error_margin`.
    - `errorMargin`: _float_
      <br/>error margin for comparison.
- `medium`:
  <br/>rule threshold for a medium severity (2) alert
    - `expectedValue`: _float_
      <br/>expected value for the actual_value returned by the sensor. The sensor value should equal `expected_value +/- the error_margin`.
    - `errorMargin`: _float_
      <br/>error margin for comparison.
- `high`:
  <br/>rule threshold for a high severity (3) alert
    - `expectedValue`: _float_
      <br/>expected value for the actual_value returned by the sensor. The sensor value should equal `expected_value +/- the error_margin`.
    - `errorMargin`: _float_
      <br/>error margin for comparison.

For more information please refer to [Min count](../../../rule_reference/comparison/min_count.md) and [Count equals](../../../rule_reference/comparison/count_equals.md)

## Parameters

This check takes no parameters.

## How to use

In order to understand how to use this check, let's walk through the [example](../../../examples/uniqueness/distinct_count/distinct_count.md) step by step.

### Walkthrough the example

The table is `bigquery-public-data.austin_311.311_service_requests`, here are the first 5 rows:

| Row | unique_key  | timestamp_column        | timestamp_column2       |
|-----|-------------|-------------------------|-------------------------|
| 1   | 22-00377488 | 2022-10-10 14:59:05 UTC | 2022-10-11 14:59:05 UTC |
| 2   | 22-00377376 | 2022-10-10 14:17:17 UTC | 2022-10-11 14:17:17 UTC |
| 3   | 22-00376929 | 2022-10-10 16:10:52 UTC | 2022-10-11 16:10:52 UTC |
| 4   | 22-00376839 | 2022-10-10 16:46:44 UTC | 2022-10-11 16:46:44 UTC |
| 5   | 22-00376178 | 2022-10-10 15:40:32 UTC | 2022-10-11 15:40:32 UTC |

#### Check configuration
Having added connection and imported tables (in the [example](../../../examples/uniqueness/distinct_count/distinct_count.md)
connection, table and check are ready) now it is possible to access the table configuration by running:

```
table edit -c=conn_bq_17 -t=austin_311.311_service_requests
```

The YAML configuration looks like this:

```yaml hl_lines="17-27" linenums="1"
--8<-- "docs/check_reference/uniqueness/distinct_count/yamls/austin_311.311_service_requests.dqotable.yaml"
```

Let's review what this configuration means.

#### Sensor

```
      checks:
        uniqueness:
          distinct_count:
```

Sensor for this check is :

- dimension: `uniqueness`

- sensor: `distinct_count`

As the check is written under the section of `unique_key` column, the check will be done on that column.

Then this parameter is passed to the rendered query:

```
SELECT
    count(distinct analyzed_table.`unique_key`) AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
FROM `bigquery-public-data`.`austin_311`.`311_service_requests` AS analyzed_table
GROUP BY time_period
ORDER BY time_period
```

#### Rule
To evaluate check results, we have to define a rule:

```
            rules:
              min_count:
                low:
                  min_value: 90.0
                medium:
                  min_value: 80.0
                high:
                  min_value: 70.0
```
The `min_count` rule configuration says that :

- if actual value is above 90.0 then the result is valid,

- if actual value is below 90.0 and above 80.0 then severity is 1 (low),

- if actual value is below 80.0 and above 70.0 then severity is 2 (medium),

- if actual value is below 70.0 then severity is 3 (high).

Below are the results of our example:

```
+------------+-----------+
|actual_value|time_period|
+------------+-----------+
|1000        |2022-10-17 |
+------------+-----------+
```

The table above is the exact same as the one you would see on the provider's platform (in this case BigQuery).

The query returns two columns: 

- actual_value which is a count of valid records,

- time_period, configured with time_series. With mode=current_time the goal of time_period is to record a date of check execution.

#### Check summary
Check evaluation summary briefly informs us about check execution:

```
+----------+-------------------------------+------+--------------+-------------+------------+---------------+-------------+
|Connection|Table                          |Checks|Sensor results|Valid results|Alerts (low)|Alerts (medium)|Alerts (high)|
+----------+-------------------------------+------+--------------+-------------+------------+---------------+-------------+
|conn_bq_17|austin_311.311_service_requests|1     |1             |1            |0           |0              |0            |
+----------+-------------------------------+------+--------------+-------------+------------+---------------+-------------+
```

There is only one check defined, 1 sensor result, where:

- 1 result is valid

- 0 results with low severity

- 0 results with medium severity

- 0 result with high severity