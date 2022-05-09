# Average delay


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

[__Average delay__](../../../sensor_reference/timeliness/average_delay/average_delay.md)

## Accepted rules

[__Moving average__](../../../rule_reference/averages/moving_average.md)

## Parameters

This checks has three parameters :

- `column1`: _str_
  <br/>first column's name to calculate a timestamp difference
- `column2`: _str_
  <br/>second column's name to calculate a timestamp difference
- `time_scale`: _str_ (Optional)
  <br/>time scale to measure timestamp difference,the default value is DAY, acceptable values: MONTH, WEEK, DAY, HOUR, 
MINUTE, SECOND (default DAY)

## How to use
The default configuration of column timeliness check `average_delay` on columns `date1`
and `date2` with `moving_average` rule and default time scale.
```yaml hl_lines="12-28" linenums="1"
--8<-- "docs/check_reference/timeliness/average_delay/yamls/default.yml"
```
```SQL
{{ process_template_request(get_request("docs/check_reference/timeliness/average_delay/requests/default.json")) }}
```



