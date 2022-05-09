# Distinct count percent
## Description
The `distinct_count_percent` calculates the percentage of unique values.

## When to use
This is a standard quality check, mostly used when we would like to simply ensure that columns contain only unique
values.

## Used template

[__Distinct count percent__](../../../sensor_reference/uniqueness/distinct_count_percent/distinct_count_percent.md)

## Accepted rules
[__Min count__](../../../rule_reference/comparison/min_count.md)

[__Count equals__](../../../rule_reference/comparison/count_equals.md)
___
## Parameters
This check takes no parameters.
## How to use

```yaml hl_lines="16-28" linenums="1"
--8<-- "docs/check_reference/uniqueness/distinct_count_percent/yamls/default.yaml"
```

```SQL
{{ process_template_request(get_request("docs/check_reference/uniqueness/distinct_count_percent/requests/default.json")) }}
```
