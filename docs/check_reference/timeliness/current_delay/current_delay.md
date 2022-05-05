# Current delay


## Description

The `current_delay` sensor calculates the timestamp difference between last record
(the newest one appointed by `MAX()` function) and a `CURRENT_TIMESTAMP()`.

## When to use

Current delay check is mostly used, when we deal with a dataset that is updated frequently.

## Used sensor

[Current delay](/sensor_reference/timeliness/current_delay/current_delay/)

## Accepted rules

[__Max count__](/rule_reference/comparison/max_count/)


Although there are several options for the rule choice, the most logical one to use is `max_count`...
## Parameters

This checks has two parameters that configure date format to parse:

- `column`: _str_
  <br/>name of the column to calculate timestamp difference with `CURRENT_TIMESTAMP()`
- `time_scale`: _str_ (Optional)
  <br/>time scale to measure timestamp difference,the default value is DAY, acceptable values: MONTH, WEEK, DAY, HOUR, 
MINUTE, SECOND (default DAY)

## How to use



```yaml hl_lines="11-23" linenums="1"
--8<-- "docs/check_reference/timeliness/current_delay/yamls/default.yml"
```

```SQL
{{ process_template_request(get_request("docs/check_reference/timeliness/current_delay/requests/default.json")) }}
```


```yaml hl_lines="11-24" linenums="1"
--8<-- "docs/check_reference/timeliness/current_delay/yamls/cast_and_hour.yml"
```

```SQL
{{ process_template_request(get_request("docs/check_reference/timeliness/current_delay/requests/cast_and_hour.json")) }}
```
