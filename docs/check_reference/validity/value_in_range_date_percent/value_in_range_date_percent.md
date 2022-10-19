## Description
The `valuein_range_date_percent` calculates the percentage of records that are within a certain date range.
Date range is configurable by the user by defining `min_value` and `max_value` check parameters. It is also possible to
decide whether to include upper and lower bounds of the range, they are included by default.

!!! warning
    There are four acceptable data types: `DATE`, `DATETIME`, `TIMESTAMP`, `STRING`. This check is set to validate
    `date` values, so in the other three cases `CAST()` is performed, so keep in mind that casting is not always
    possible. The safest option is to provide the data in `ISO 8601` format, in other cases formatting might be
    necessary.


## When to use
This sensor is used when you need to confirm that the event took place in a certain time period.

## Used sensor

The query for this check calculates the percent of date values that are within a range provided by the user. The value_in_range_date_percent checks values from column which are within a range of min_value and max_value. The user decides whether to include these values to the range, using optional parameters include_min_value and include_max_value. Default in check min_value and max_value are included in the range.

Successfully classified records are assigned value of 1, and any other values, 0. Those values are then summed (so effectively we perform count of valid values), divided by the number of records, and multiplicated by a 100.0 so that the results is in percent.

Please see [Value in range date percent](../../../sensor_reference/validity/value_in_range_date_percent/value_in_range_date_percent.md) for more information.

___
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

___
## Parameters

- `min_value`: _str_
<br/> minimal value range variable, should be provided in `ISO 8601` format
- `max_value`: _str_
<br/> maximal value range variable, should be provided in `ISO 8601` format
- `include_min_value`: _bool_ (Optional)
<br/> a variable deciding whether to include the lower limit of the range (default: `true`)
- `include_min_value`: _bool_ (Optional)
<br/> a variable deciding whether to include the upper limit of the range (default: `true`)

!!! warning
    The default date format is `ISO 8601`, if your data set contains date values it should be formatted correctly,
    otherwise you might not get the results.

## How to use

### Default configuration

YAML file for `value_in_range_date_percent` check on a column `real_datetime` in 
`string_dates`table from `test_data` dataset in `dqo-ai` project on BigQuery with `min_count` rule and passed parameters `min_value = "2022-04-01"`, `max_value = "2022-04-10"`, looks like this:

```yaml hl_lines="21-34" linenums="1"
--8<-- "docs/check_reference/validity/value_in_range_date_percent/yamls/default_config_on_datetime.yml"
```

```SQL
{{ process_template_request(get_request("docs/check_reference/validity/value_in_range_date_percent/requests/default_config_on_datetime.json")) }}
```

In order to exclude min and max values, provide `false` to:
- include_min_value

- include_max_value

Please see lines 26 and 27 below:

```yaml hl_lines="20-35" linenums="1"
--8<-- "docs/check_reference/validity/value_in_range_date_percent/yamls/include_bounds_false.yml"
```

In that case a rendered query looks like this:

```SQL
{{ process_template_request(get_request("docs/check_reference/validity/value_in_range_date_percent/requests/include_bounds_false.json")) }}
```

---
### Walkthrough the example

In order to understand how to use this check, let's walk through the [example](../../../examples/validity/string_length_in_range_percent/string_length_in_range_percent.md) step by step.

Let's have a look at the first five rows from the table used in the example - `bigquery-public-data.austin_311.311_service_requests`

| Row | unique_key  | timestamp_column        | timestamp_column2       |
|-----|-------------|-------------------------|-------------------------|
| 1   | 22-00377488 | 2022-10-10 14:59:05 UTC | 2022-10-11 14:59:05 UTC |
| 2   | 22-00377376 | 2022-10-10 14:17:17 UTC | 2022-10-11 14:17:17 UTC |
| 3   | 22-00376929 | 2022-10-10 16:10:52 UTC | 2022-10-11 16:10:52 UTC |
| 4   | 22-00376839 | 2022-10-10 16:46:44 UTC | 2022-10-11 16:46:44 UTC |
| 5   | 22-00376178 | 2022-10-10 15:40:32 UTC | 2022-10-11 15:40:32 UTC |

#### Check configuration
Having added connection and imported tables (in the [example](../../../examples/validity/value_in_range_date_percent/value_in_range_date_percent.md)
connection, table and check are ready) now it is possible to access the table configuration by running:

```
table edit -c=conn_bq_9 -t=austin_waste.waste_and_diversion
```

The YAML configuration looks like this:

```yaml hl_lines="29-42" linenums="1"
--8<-- "docs/check_reference/validity/value_in_range_date_percent/yamls/austin_waste.waste_and_diversion.dqotable.yaml"
```

Let's review what this configuration means.

#### Sensor

```
      checks:
        validity:
          value_in_range_date_percent:
            parameters:
              min_value:  "2017-06-05"
              max_value:  "2022-05-01"
```

Sensor for this check is :

- dimension: `validity`

- sensor: `value_in_range_date_percent`

The `min_value` is `2017-06-05` and `max_value` is `2022-05-01`.

As the check is written under the section of `load_time` column, the check will be done on that column.

Then this parameter is passed to the rendered query:

```
SELECT
    CASE
        WHEN COUNT(analyzed_table.`unique_key`) = 0 THEN NULL
        ELSE
            100.0 * SUM(
                        CASE WHEN LENGTH( analyzed_table.`unique_key` ) BETWEEN 11 AND 11 THEN 1
                                ELSE 0
                            END
    ) / COUNT(analyzed_table.`unique_key`) END AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
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
                  min_value: 60.0
                medium:
                  min_value: 40.0
                high:
                  min_value: 20.0
```
The `min_count` rule configuration says that :

- if actual value is above 60.0 then the result is valid,

- if actual value is below 60.0 and above 40.0 then severity is 1 (low),

- if actual value is below 40.0 and above 20.0 then severity is 2 (medium),

- if actual value is below 20.0 then severity is 3 (high).

Below are the results of our example:

```
+------------------+-----------+
|actual_value      |time_period|
+------------------+-----------+
|24.983499196218514|2022-10-19 |
+------------------+-----------+
```

The actual value is above the lowest threshold (20.0), so it is valid, but it is also below the highest and medium threshold, so the severity in this case is medium.

The table above is the exact same as the one you would see on the provider's platform (in this case BigQuery).

The query returns two columns:

- actual_value which is the percent of records within the passed parameters,

- time_period, configured with time_series. With mode=current_time the goal of time_period is to record a date of check execution.

#### Check summary
Check evaluation summary briefly informs us about check execution:

```
+----------+--------------------------------+------+--------------+-------------+------------+---------------+-------------+
|Connection|Table                           |Checks|Sensor results|Valid results|Alerts (low)|Alerts (medium)|Alerts (high)|
+----------+--------------------------------+------+--------------+-------------+------------+---------------+-------------+
|conn_bq_9 |austin_waste.waste_and_diversion|1     |1             |0            |0           |1              |0            |
+----------+--------------------------------+------+--------------+-------------+------------+---------------+-------------+
```

There is only one check defined, 1 sensor result, where:

- 0 result is valid

- 0 results with low severity

- 1 results with medium severity

- 0 result with high severity