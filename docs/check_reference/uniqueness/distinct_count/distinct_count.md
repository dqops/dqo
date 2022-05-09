# Distinct count
## Description
The `distinct_count` sensor counts unique values in a specified column.

## When to use
This is a standard quality check, mostly used when we would like to simply ensure that columns contain only unique
values.

## Used template

[__Distinct count__](../../../sensor_reference/uniqueness/distinct_count/distinct_count.md)

## Accepted rules
[__Min count__](../../../rule_reference/comparison/min_count.md)

[__Count equals__](../../../rule_reference/comparison/count_equals.md)
___
## Parameters
This check takes no parameters.
## How to use

```yaml hl_lines="16-28" linenums="1"
--8<-- "docs/check_reference/uniqueness/distinct_count/yamls/default.yaml"
```

```SQL
{{ process_template_request(get_request("docs/check_reference/uniqueness/distinct_count/requests/default.json")) }}
```
