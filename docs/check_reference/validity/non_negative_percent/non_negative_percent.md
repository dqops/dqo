# Non negative percent
## Description
The `non_negative_percent` check calculates the percentage of non-negative column's values.
___
## When to use
The usability of this check is obvious. It is useful for validation of numerical values that we expect to be 
non-negative.
___
## Used sensor
[__Non negative percent__](../../../sensor_reference/validity/non_negative_percent/non_negative_percent.md)
___
## Accepted rules
[__Min count__](../../../rule_reference/comparison/min_count.md)

[__Count equals__](../../../rule_reference/comparison/count_equals.md)
___
## Parameters
This check takes no parameters.

___
## How to use
The default configuration of column validity check `non_negative_percent` on columns `id` with `min_count` rule, looks like this
```yaml hl_lines="16-28" linenums="1"
--8<-- "docs/check_reference/validity/non_negative_percent/yamls/default.yaml"
```
The rendered query is
```SQL
{{ process_template_request(get_request("docs/check_reference/validity/non_negative_percent/requests/default.json")) }}
```

