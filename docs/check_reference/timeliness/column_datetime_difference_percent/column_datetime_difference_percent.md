# Column datetime difference percent
## Description
The `column_datetime_differnece_percent` check calculates a time difference between records in corresponding columns,
compares those values to a threshold, and shows the percentage of rows where time difference is greater than the 
threshold.

User has to configure three parameters: `column1`, `column2` check calculates datetime differences between records
in these columns , `max_difference` which is a threshold value. The `time_scale` corresponds to the time scale of the 
datetime difference. This parameter is optional, and the default time scale is a `DAY`.

!!! Info
    Although this sensor is designed to work on `DATETIME` columns, it is possible to provide `DATE`, `TIMESTSMP` or 
    `STRING` columns, only they will be cast as `DATETIME`.

## When to use

This check aims at informing the user about the amount of data that is considered timely.

## Used template

[__Column datetime difference percent__](/sensor_reference/timeliness/column_datetime_difference_percent/column_datetime_difference_percent/)

## Accepted rules

[__Min count__](/rule_reference/comparison/min_count/)

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

The default configuration of column validity check `column_datetime_differnece_percent` on columns `reference_datetime`
and `real_datetime` with `min_count` rule and default time scale.

```yaml hl_lines="11-25" linenums="1"
--8<-- "docs/check_reference/timeliness/column_datetime_difference_percent/yamls/default.yml"
```
The query is rendered with default time scale: `DAY`.
```SQL
{{ process_template_request(get_request("docs/check_reference/timeliness/column_datetime_difference_percent/requests/default.json")) }}
```

```yaml hl_lines="11-26" linenums="1"
--8<-- "docs/check_reference/timeliness/column_datetime_difference_percent/yamls/time_scale_hour.yml"
```

```SQL
{{ process_template_request(get_request("docs/check_reference/timeliness/column_datetime_difference_percent/requests/time_scale_hour.json")) }}
```