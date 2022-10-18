## Description
The `column_datetime_differnece_percent` check calculates a time difference between records in corresponding columns,
compares those values to a threshold, and shows the percentage of rows where time difference is greater than the 
threshold.

User has to configure three parameters: `column1`, `column2` check calculates datetime differences between records
in these columns , `max_difference` which is a threshold value. The `time_scale` corresponds to the time scale of the 
datetime difference. This parameter is optional, and the default time scale is a `DAY`.

!!! Info
    Although this sensor is designed to work on `DATETIME` columns, it is possible to provide `DATE`, `TIMESTASMP` or 
    `STRING` columns, only they will be cast as `DATETIME`.

## When to use

This check aims at informing the user about the amount of data that is considered timely.

## Used sensor

The query for this check calculates the timestamp difference between two columns and check if the difference is less than the max_difference parameter.

Successfully classified records are assigned value of 1, and any other values, 0. Those values are then summed (so the counting of valid values is effectively performed), divided by the number of records, and multiplicated by a 100.0 so that the results are in percent.

Please see [Column datetime difference percent](../../../sensor_reference/timeliness/column_datetime_difference_percent/column_datetime_difference_percent.md) for more information.

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

For more information please refer to [Min count](../../../rule_reference/comparison/min_count.md)

## Parameters

- `column1`: _str_
  <br/>the first column to calculate the time difference
- `column2`: _str_
  <br/>the second column to calculate the time difference
- `time_scale`: _str_ (optional)
  <br/>a datetime difference timescale, accepted values: `DAY`,`HOUR`, `MINUTE`, `SECOND` (default: `DAY`)
- `max_diffference`: _int_
  <br/> threshold to compare time differences, anything above this threshold is considered as delayed

## How to use

In order to understand how to use this check, let's walk through the [example](../../../examples/timeliness/column_datetime_difference_percent/column_datetime_difference_percent.md) step by step.

### Walkthrough the example

The table is `bigquery-public-data.austin_crime.crime`, here are the first 5 rows:

| Row | unique_key  | timestamp_column        | timestamp_column2       |
|-----|-------------|-------------------------|-------------------------|
| 1   | 22-00377488 | 2022-10-10 14:59:05 UTC | 2022-10-11 14:59:05 UTC |
| 2   | 22-00377376 | 2022-10-10 14:17:17 UTC | 2022-10-11 14:17:17 UTC |
| 3   | 22-00376929 | 2022-10-10 16:10:52 UTC | 2022-10-11 16:10:52 UTC |
| 4   | 22-00376839 | 2022-10-10 16:46:44 UTC | 2022-10-11 16:46:44 UTC |
| 5   | 22-00376178 | 2022-10-10 15:40:32 UTC | 2022-10-11 15:40:32 UTC |

#### Check configuration
Having added connection and imported tables (in the [example](../../../examples/timeliness/column_datetime_difference_percent/column_datetime_difference_percent.md)
connection, table and check are ready) now it is possible to access the table configuration by running:

```
table edit -c=conn_bq_13 -t=austin_crime.crime
```

The YAML configuration looks like this:

```yaml hl_lines="11-25" linenums="1"
--8<-- "docs/check_reference/timeliness/column_datetime_difference_percent/yamls/austin_crime.crime.dqotable.yaml"
```

Let's review what this configuration means.

#### Sensor

```
  checks:
    timeliness:
      column_datetime_difference_percent:
        parameters:
          column1: "clearance_date"
          column2: "timestamp"
          max_difference: 10
```
Sensor for this check is :

- dimension: `timeliness`

- sensor: `column_datetime_difference_percent`

which will be executed on a columns `clearance_date` and `timestamp`.

`max_difference` is a threshold value, above which a value is invalid, in this case it is 10.

Then this parameter is passed to the rendered query:

```
SELECT
    100.0*SUM(
        CASE
            WHEN
                ABS(DATETIME_DIFF(CAST(analyzed_table.clearance_date AS DATETIME), CAST(analyzed_table.timestamp AS DATETIME), DAY)) < 10 THEN 1
            ELSE 0 END
            )/COUNT(*) AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
FROM `bigquery-public-data`.`austin_crime`.`crime` AS analyzed_table
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
+-----------------+-----------+
|actual_value     |time_period|
+-----------------+-----------+
|58.01757017355903|2022-10-14 |
+-----------------+-----------+
```
The actual_value(in this example is the percentage of valid values) is 58.01, it means the result is invalid.

The table above is the exact same as the one you would see on the provider's platform (in this case BigQuery).

The query returns two columns: 

- actual_value which is a percent of valid records,

- time_period, configured with time_series. With mode=current_time the goal of time_period is to record a date of check execution.

#### Check summary
Check evaluation summary briefly informs us about check execution:

```
+----------+------------------+------+--------------+-------------+------------+---------------+-------------+
|Connection|Table             |Checks|Sensor results|Valid results|Alerts (low)|Alerts (medium)|Alerts (high)|
+----------+------------------+------+--------------+-------------+------------+---------------+-------------+
|conn_bq_13|austin_crime.crime|1     |1             |0            |0           |0              |1            |
+----------+------------------+------+--------------+-------------+------------+---------------+-------------+
```

There is only one check defined, 1 sensor result, where:

- 0 result is valid

- 0 results with low severity

- 0 results with medium severity

- 1 result with high severity