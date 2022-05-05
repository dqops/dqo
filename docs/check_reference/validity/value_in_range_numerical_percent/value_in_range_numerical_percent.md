# Value in range numerical percent
## Description

The `value_in_range_numerical_percent` check calculates the percent of numerical values that are within a range provided
by the user (with `min_value` and `max_value`).

Range bounds are required parameters. There are two more parameters: `include_min_value` and `include_max_value`, that
you can use to (as the name suggests) include min and\or max value. By default, range bounds are included.


## When to use
This can be used on numerical columns, where you want to validate values to be in a certain range. For example a 
customer's age should be greater or equal to 18, and lesser than e.g. 100 (see the 
[example](/check_reference/validity/value_in_range_numerical_percent/value_in_range_numerical_percent/#using-only-one-optional-parameter)).

!!! Warning
    Running this check on a non-numeric column may result with an error.
___

## Used sensor

[__Value in range numerical percent__](/sensor_reference/validity/value_in_range_numerical_percent/value_in_range_numerical_percent/)
___
## Accepted rules
[__Min count__](/rule_reference/comparison/min_count/)

[__Count equals__](/rule_reference/comparison/count_equals/)

___

## Parameters
This checks has four parameters, two of which are optional:

- `min_value`: _float_
  <br/>minimal value of a range
- `max_value`: _float_
  <br/>maximal value of a range
- `include_min_value`: _bool_ (Optional)
  <br/>a variable deciding whether to include the minimal value of the range (default: True)
- `include_max_value`: _bool_ (Optional)
  <br/>a variable deciding whether to include the maximal value of the range (default: True)

## How to use

### Default configuration
The default configuration of column validity check `value_in_range_numerical_percent` with `min_count` rule.
If the parameters `include_min_value` and `include_max_value` are not chosen, the
default value for these parameters is `true`.

Table configuration in YAML file for column check `value_in_range_numerical_percent` on a column `id` in `table`
table in `test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule, looks like this
```yaml hl_lines="16-29" linenums="1"
--8<-- "docs/check_reference/validity/value_in_range_numerical_percent/yamls/default_configuration.yaml"
```
For default check configuration the rendered query is
``` SQL
{{ process_template_request(get_request("docs/check_reference/validity/value_in_range_numerical_percent/requests/default_configuration.json")) }}
```

### Using all optional parameters
Table configuration in YAML file for column check `value_in_range_numerical_percent` on a column `dates` in `string_dates`
table in `test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule, looks like this
```yaml hl_lines="16-31" linenums="1"
--8<-- "docs/check_reference/validity/value_in_range_numerical_percent/yamls/all_optional_parameters.yaml"
```
In this case, we set two optional parameters `include_min_value: false` and `include_max_value: false`.
The rendered query is
``` SQL
{{ process_template_request(get_request("docs/check_reference/validity/value_in_range_numerical_percent/requests/all_optional_parameters.json")) }}
```
### Using only one optional parameter
Table configuration in YAML file for column check `value_in_range_numerical_percent` on a column `customer_age` in 
`customers`
table in `test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule, looks like this
```yaml hl_lines="16-30" linenums="1"
--8<-- "docs/check_reference/validity/value_in_range_numerical_percent/yamls/one_optional_parameter.yaml"
```
In this case, we set only one optional parameter `include_max_value: false`.
The rendered query is
``` SQL
{{ process_template_request(get_request("docs/check_reference/validity/value_in_range_numerical_percent/requests/one_optional_parameter.json")) }}
```
The same is for the upper bound.