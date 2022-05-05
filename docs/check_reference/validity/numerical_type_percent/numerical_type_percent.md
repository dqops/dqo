# Numerical type percent
## Description
The `numerical_type_percent` check calculates the percentage of numerical values from column.
___
## When to use
This sensor can be used when we receive a `STRING` column containing numerical values, and we would like to find the 
number of values that can be cast as `NUMERICAL`
___
## Used sensor
[__Numerical type percent__](/sensor_reference/validity/numerical_type_percent/numerical_type_percent/)
___
## Accepted rules
[__Min count__](/rule_reference/comparison/min_count/)

[__Count equals__](/rule_reference/comparison/count_equals/)
___
## Parameters
The sensor does not accept any parameters
___
## How to use
The default configuration of column validity check `numerical_type_percent` on column `id` with `min_count` rule, looks like this
```yaml hl_lines="16-28" linenums="1"
--8<-- "docs/check_reference/validity/numerical_type_percent/yamls/default.yaml"
```
The rendered query is
```SQL
{{ process_template_request(get_request("docs/check_reference/validity/numerical_type_percent/requests/default.json")) }}
```

