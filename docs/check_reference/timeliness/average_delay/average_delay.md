## Description

The `average_delay` sensor calculates the average timestamp difference between two columns provided
with `column1` and `column2` parameters.
To run this check you have to specify those two columns by specifying their names in check's parameters.

It is also possible to specify a time scale - a time gradient for a timestamp difference. The possible time scales are:
month, week, day, hour, minute, second. Time scale is specified with `time_scale` check parameter.

!!! Info
    The average is calculated with an absolute values of the timestamp differences.
## When to use

This check can be used when you need to keep track of the overall timeliness in your database.

## Used sensor

Average delay sensor calculates the average timestamp difference between two columns provided by the user. Please see
[Average delay](../../../sensor_reference/timeliness/average_delay/average_delay.md) for more information.

## Accepted rules

Moving average verifies if a data quality check reading is comparable to an average.
An average is calculated based on previous results. The time window in which we define a number of previous results used to calculate average is customizable.

This rule has two parameters that should be declared for each alert.

- `low`:
  <br/>rule threshold for a low severity (1) alert
    - `max_percent_above`: _float_
    - `max_percent_below`: _float_
- `medium`:
  <br/>rule threshold for a medium severity (2) alert
    - `max_percent_above`: _float_
    - `max_percent_below`: _float_
- `high`:
  <br/>rule threshold for a high severity (3) alert
    - `max_percent_above`: _float_
    - `max_percent_below`: _float_

For more information please refer to [Moving average](../../../rule_reference/averages/moving_average.md)

## Parameters

This checks has four parameters :

- `disabled`: false/true
  <br/>type `false` to enable or `true` to disable a check
- `column1`: _str_
  <br/>first column's name to calculate a timestamp difference
- `column2`: _str_
  <br/>second column's name to calculate a timestamp difference
- `time_scale`: _str_ (Optional)
  <br/>time scale to measure timestamp difference,the default value is DAY, acceptable values: MONTH, WEEK, DAY, HOUR, 
MINUTE, SECOND (default DAY)

## How to use

### Default configuration

The default configuration of `average_delay` check on columns `date1` and `date2` with `moving_average` rule and default time_scale:

```yaml hl_lines="12-28" linenums="1"
--8<-- "docs/check_reference/timeliness/average_delay/yamls/default.yml"
```

The query is rendered with time_scale: `DAY`:

```SQL
{{ process_template_request(get_request("docs/check_reference/timeliness/average_delay/requests/default.json")) }}
```

### Walkthrough the example

In order to understand how to use this check, let's walk through the [example](../../../examples/timeliness/average_delay/average_delay.md) step by step.

The table is `bigquery-public-data.austin_crime.crime`, here are the first 5 rows:

| Row | unique_key  | timestamp_column        | timestamp_column2       |
|-----|-------------|-------------------------|-------------------------|
| 1   | 22-00377488 | 2022-10-10 14:59:05 UTC | 2022-10-11 14:59:05 UTC |
| 2   | 22-00377376 | 2022-10-10 14:17:17 UTC | 2022-10-11 14:17:17 UTC |
| 3   | 22-00376929 | 2022-10-10 16:10:52 UTC | 2022-10-11 16:10:52 UTC |
| 4   | 22-00376839 | 2022-10-10 16:46:44 UTC | 2022-10-11 16:46:44 UTC |
| 5   | 22-00376178 | 2022-10-10 15:40:32 UTC | 2022-10-11 15:40:32 UTC |

#### Check configuration
Having added connection and imported tables (in the [example](../../../examples/timeliness/average_delay/average_delay.md)
connection, table and check are ready) now it is possible to access the table configuration by running:

```
table edit -c=connection_1 -t=austin_crime.crime
```

The YAML configuration looks like this:

```yaml hl_lines="12-33" linenums="1"
--8<-- "docs/check_reference/timeliness/average_delay/yamls/austin_crime.crime.dqotable.yml"
```

Let's review what this configuration means.

#### Sensor

```
  checks:
    timeliness:
      average_delay:
        parameters:
          disabled: false
          column1: clearance_date
          column2: timestamp
          time_scale: DAY
```

Sensor for this check is :

- dimension: `timeliness`
  
- sensor: `average_delay`

which will be executed on a columns `clearance_date` and `timestamp`.

Timescale to measure timestamp difference in this case is day.


Then this parameter is passed to the rendered query:

```
SELECT
    AVG(ABS(TIMESTAMP_DIFF(analyzed_table.clearance_date, analyzed_table.timestamp, DAY))) AS actual_value, CAST(analyzed_table.`timestamp` AS date) AS time_period
FROM `bigquery-public-data`.`austin_crime`.`crime` AS analyzed_table
GROUP BY time_period
ORDER BY time_period
```

#### Rule
To evaluate check results, we have to define a rule:

```
rules:
          moving_average:
            time_window:
              prediction_time_window: 14
              min_periods_with_reading: 7
            high:
              max_percent_above: 15.0
              max_percent_below: 15.0
            low:
              max_percent_above: 5.0
              max_percent_below: 5.0
            medium:
              max_percent_above: 10.0
              max_percent_below: 10.0
```

The `moving_average` rule configuration says that :

- if actual value is below 5.0 then the result is valid,

- if actual value is above 5.0 and below 10.0 then severity is 1 (low),

- if actual value is above 10.0 and below 15.0 then severity is 2 (medium),

- if actual value is above 15.0 then severity is 3 (high).

Below are the results of our example:
```
+------------------+-----------+
|actual_value      |time_period|
+------------------+-----------+
|18.989583333333336|2014-01-01 |
+------------------+-----------+
|19.49122807017543 |2014-01-02 |
+------------------+-----------+
|21.253968253968246|2014-01-03 |
+------------------+-----------+
|19.614457831325293|2014-01-04 |
+------------------+-----------+
|19.990196078431367|2014-01-05 |
+------------------+-----------+
|17.62015503875969 |2014-01-06 |
+------------------+-----------+
|24.454545454545457|2014-01-07 |
+------------------+-----------+
|16.343749999999996|2014-01-08 |
+------------------+-----------+
|30.16666666666667 |2014-01-09 |
+------------------+-----------+
|21.54455445544554 |2014-01-10 |
+------------------+-----------+
```
The table above is the exact same as the one you would see on the provider's platform (in this case BigQuery).

The query returns two columns: 

- actual_value which is the average timestamp difference between `clearance_date` and `timestamp` column,

- time_period, configured with time_series. With mode=current_time the goal of time_period is to record a date of check execution.

#### Check summary
Check evaluation summary briefly informs us about check execution:

```
Check evaluation summary per table:
+----------+------------------+------+--------------+-------------+------------+---------------+-------------+
|Connection|Table             |Checks|Sensor results|Valid results|Alerts (low)|Alerts (medium)|Alerts (high)|
+----------+------------------+------+--------------+-------------+------------+---------------+-------------+
|conn_bq_12|austin_crime.crime|1     |1096          |232          |201         |181            |475          |
+----------+------------------+------+--------------+-------------+------------+---------------+-------------+
```

There is only one check defined, 1096 sensor results, where:

- 232 are valid

- 201 results with low severity

- 181 results with medium severity

- 475 results with high severity