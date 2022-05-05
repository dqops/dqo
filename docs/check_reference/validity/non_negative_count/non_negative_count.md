# Non negative count
## Description
The `non_negative_count` check calculates the number of non-negative values from column.
___
## When to use

The usability of this check is obvious. It is useful for validation of numerical values that we expect to be
non-negative.
___
## Used sensor
[__Non negative count__](/sensor_reference/validity/non_negative_count/non_negative_count/)
___
## Accepted rules
[__Min count__](/rule_reference/comparison/min_count/)

[__Count equals__](/rule_reference/comparison/count_equals/)
___
## Parameters
The sensor does not accept any parameters
___
## How to use
The default configuration of column validity check `non_negative_count` on columns `id` with `min_count` rule, looks like this
```yaml hl_lines="16-28" linenums="1"
--8<-- "docs/check_reference/validity/non_negative_count/yamls/default.yaml"
```
The rendered query is
```SQL
{{ process_template_request(get_request("docs/check_reference/validity/non_negative_count/requests/default.json")) }}
```

