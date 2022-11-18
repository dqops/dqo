## Description

The `current_delay` sensor calculates the timestamp difference between last record
(the newest one appointed by `MAX()` function) and a `CURRENT_TIMESTAMP()`.

## When to use

Current delay check is mostly used, when we deal with a dataset that is updated frequently.

## Used sensor
The `current_delay` sensor calculates the timestamp difference between the latest record and a `CURRENT_TIMESTAMP()`. Please see
[Current delay](../../../sensor_reference/timeliness/current_delay/current_delay.md) for more information.

## Accepted rules

Although there are several options for the rule choice, the most logical one to use is `max_count`.
Data quality rule that verifies if a data quality check reading is lesser or equal a maximal value.

This checks has one parameter that should be configured for each alert:

- `low` :
  <br/>rule threshold for a low severity (1) alert 
    - `max_value`: float 
    - maximal accepted value for the `actual_value` returned by the sensor (inclusive)
- `medium` :
  <br/>rule threshold for a medium severity (2) alert
    - `max_value`: float
    - maximal accepted value for the `actual_value` returned by the sensor (inclusive)
- `high` :
  <br/>rule threshold for a high severity (3) alert 
    - `max_value`: float
    - maximal accepted value for the `actual_value` returned by the sensor (inclusive)

For more information please refer to [__Max count__](../../../rule_reference/comparison/max_count.md)

## Parameters

This checks has two parameters that configure date format to parse:

- `column`: _str_
  <br/>name of the column to calculate timestamp difference with `CURRENT_TIMESTAMP()`
- `time_scale`: _str_ (Optional)
  <br/>time scale to measure timestamp difference,the default value is DAY, acceptable values: MONTH, WEEK, DAY, HOUR, 
MINUTE, SECOND (default DAY)

## How to use

### Default configuration

The default configuration of `current_delay` check on column `timestamp_column` with max_count rule and default `time_scale`:

```yaml hl_lines="11-23" linenums="1"
--8<-- "docs/check_reference/timeliness/current_delay/yamls/default.yml"
```

The query is rendered with default time_scale: DAY:

```SQL
{{ process_template_request(get_request("docs/check_reference/timeliness/current_delay/requests/default.json")) }}
```

The default configuration of `current_delay` check on column `dates` with max_count rule and `time_scale: "HOUR"`:

```yaml hl_lines="11-24" linenums="1"
--8<-- "docs/check_reference/timeliness/current_delay/yamls/cast_and_hour.yml"
```

In that case the rendered query is:

```SQL
{{ process_template_request(get_request("docs/check_reference/timeliness/current_delay/requests/cast_and_hour.json")) }}
```

### Walkthrough the example

In order to understand how to use this check, let's walk through the [example](../../../examples/timeliness/current_delay/current_delay.md) step by step.

The table is `bigquery-public-data.covid19_italy.national_trends`, here are the first 5 rows:

| Row | unique_key  | timestamp_column        | timestamp_column2       |
|-----|-------------|-------------------------|-------------------------|
| 1   | 22-00377488 | 2022-10-10 14:59:05 UTC | 2022-10-11 14:59:05 UTC |
| 2   | 22-00377376 | 2022-10-10 14:17:17 UTC | 2022-10-11 14:17:17 UTC |
| 3   | 22-00376929 | 2022-10-10 16:10:52 UTC | 2022-10-11 16:10:52 UTC |
| 4   | 22-00376839 | 2022-10-10 16:46:44 UTC | 2022-10-11 16:46:44 UTC |
| 5   | 22-00376178 | 2022-10-10 15:40:32 UTC | 2022-10-11 15:40:32 UTC |

#### Check configuration
Having added connection and imported tables (in the [example](../../../examples/timeliness/current_delay/current_delay.md)
connection, table and check are ready) now it is possible to access the table configuration by running:

```
table edit -c=conn_bq_14 -t=covid19_italy.national_trends
```

The YAML configuration looks like this:

```yaml hl_lines="11-24" linenums="1"
--8<-- "docs/check_reference/timeliness/current_delay/yamls/covid19_italy.national_trends.dqotable.yaml"
```

Let's review what this configuration means.

#### Sensor

```
  checks:
    timeliness:
      current_delay:
        parameters:
          column: "date"
          time_scale: "HOUR"
```
Sensor for this check is :

- dimension: `timeliness`

- sensor: `current_delay`

which will be executed on a column `date`.

Timescale to measure timestamp difference in this case is hour.

Then this parameter is passed to the rendered query:

```
SELECT
    TIMESTAMP_DIFF(CURRENT_TIMESTAMP(),
                   MAX(analyzed_table.date),
                   HOUR) AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
FROM `bigquery-public-data`.`covid19_italy`.`national_trends` AS analyzed_table
GROUP BY time_period
ORDER BY time_period
```

#### Rule
To evaluate check results, we have to define a rule:

```
        rules:
          max_count:
            low:
              max_value: 24.0
            medium:
              max_value: 26.0
            high:
              max_value: 30.0
```
The `max_count` rule configuration says that :

- if actual value is below 24.0 then the result is valid,

- if actual value is above 24.0 and below 26.0 then severity is 1 (low),

- if actual value is above 26.0 and below 30.0 then severity is 2 (medium),

- if actual value is above 30.0 then severity is 3 (high).

Below are the results of our example:

```
+------------+-----------+
|actual_value|time_period|
+------------+-----------+
|17          |2022-10-14 |
+------------+-----------+
```

The actual_value is 17, which means the result is valid.

The table above is the exact same as the one you would see on the provider's platform (in this case BigQuery).

The query returns two columns: 

- actual_value which is a number of hours difference between the last record
(the newest one appointed by `MAX()` function) and a `CURRENT_TIMESTAMP()` , 

- time_period, configured with time_series. With mode=current_time the goal of time_period is to record a date of check execution.

#### Check summary
Check evaluation summary briefly informs us about check execution:

```
+----------+-----------------------------+------+--------------+-------------+------------+---------------+-------------+
|Connection|Table                        |Checks|Sensor results|Valid results|Alerts (low)|Alerts (medium)|Alerts (high)|
+----------+-----------------------------+------+--------------+-------------+------------+---------------+-------------+
|conn_bq_14|covid19_italy.national_trends|1     |1             |1            |0           |0              |0            |
+----------+-----------------------------+------+--------------+-------------+------------+---------------+-------------+
```

There is only one check defined, 1 sensor result, where:

- 1 result is valid

- 0 results with low severity

- 0 results with medium severity

- 0 results with high severity