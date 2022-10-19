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

Table configuration in YAML file for column check `value_in_range_date_percent` on a column `real_datetime` in 
`string_dates`table in test_data 
dataset in dqo-ai project on BigQuery, min_count rule and passed parameters `min_value = "2022-04-01"`, `max_value
= "2022-04-10"`

```yaml hl_lines="21-34" linenums="1"
--8<-- "docs/check_reference/validity/value_in_range_date_percent/yamls/default_config_on_datetime.yml"
```

```SQL
{{ process_template_request(get_request("docs/check_reference/validity/value_in_range_date_percent/requests/default_config_on_datetime.json")) }}
```

### Exclude min and max values
```yaml hl_lines="20-35" linenums="1"
--8<-- "docs/check_reference/validity/value_in_range_date_percent/yamls/include_bounds_false.yml"
```

```SQL
{{ process_template_request(get_request("docs/check_reference/validity/value_in_range_date_percent/requests/include_bounds_false.json")) }}
```