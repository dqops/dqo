# String length in range percent
## Description

The `string_length_in_range_percent` check calculates the percent of string values whose length is within
a certain range provided by the user.
If a provided data type is not `STRING`, the column is cast as one.




## When to use
This check is useful when there is a need to validate value's length, e.g. social security number, postal code.

!!! info
    To find value's length we use built in `STRING` function `LENGTH`. Keep in mind that white spaces are 
    included into the count.

___

## Used sensor

[__String length in range percent__](/sensor_reference/validity/string_length_in_range_percent/string_length_in_range_percent/)
___
## Accepted rules
[__Min count__](/rule_reference/comparison/min_count/)

[__Count equals__](/rule_reference/comparison/count_equals/)

___

## Parameters
This checks has two optional parameters that configure date format to parse:

- `min_length`: _Integer_
  <br/>minimal string's length
- `max_length`: _Integer_
  <br/>maximal string's length


## How to use
### On the string column
Table configuration in YAML file for column check `string_length_in_range_percent` on a column `string_length` in `table`
table in `test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule ,looks like this
```yaml hl_lines="16-29" linenums="1"
--8<-- "docs/check_reference/validity/string_length_in_range_percent/yamls/default_configuration.yaml"
```
{% import 'bigquery.sql.jinja2' as lib_bq with context -%}
For default check configuration and passed `min_length: 6`, `max_length: 9`. The rendered query is
```SQL
{{ process_template_request(get_request("docs/check_reference/validity/string_length_in_range_percent/requests/default_configuration.json")) }}
```
### On the numeric column
Table configuration in YAML file for column check `string_length_in_range_percent` on a column `int_length` in `table`
table in `test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule ,looks like this
```yaml hl_lines="16-29" linenums="1"
--8<-- "docs/check_reference/validity/string_length_in_range_percent/yamls/check_on_numeric_column.yaml"
```
In this case the check working on numeric column. The rendered query is
```SQL
{{ process_template_request(get_request("docs/check_reference/validity/string_length_in_range_percent/requests/check_on_numeric_column.json")) }}
```