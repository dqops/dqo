# Not null count
## Description
The `not_null_count` check calculates the number of not null values in a  column.
___
## When to use
This is a standard quality check, mostly used when we would like to simply ensure that columns do not contain `NULL` 
values
___
## Used sensor
[__Not null count__](../../../sensor_reference/validity/not_null_count/not_null_count.md)
___
## Accepted rules
[__Min count__](../../../rule_reference/comparison/min_count.md)

[__Count equals__](../../../rule_reference/comparison/count_equals.md)
___
## Parameters
The sensor does not accept any parameters
___
## How to use
The default configuration of column validity check `not_null_count` on column `id` with `min_count` rule, looks like this
```yaml hl_lines="16-28" linenums="1"
--8<-- "docs/check_reference/validity/not_null_count/yamls/default.yaml"
```
The rendered query is
```SQL
{{ process_template_request(get_request("docs/check_reference/validity/not_null_count/requests/default.json")) }}
```

