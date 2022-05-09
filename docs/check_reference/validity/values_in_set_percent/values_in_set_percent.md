# Values in set percent

## Description

The `values_in_set_percent` calculates a percentage of records that match provided values.
This check accepts two parameters: a list of values to match (`values_list`) and a desired type of data (`values_type`).

The check is performed by using SQL function `IN()`. Depending on the provider, the given values are passed 
differently to query engine in accordance with provider's rules.

When user specifies a different data type than the one in the column, sensor tries to cast column values to specified 
type. If a data type is not provided, and data types do not match, the default type to cast is `STRING`.

!!! INFO
    In all cases except for `DATE`, check casts the column for selected data type. In case of `DATE`, values provided in
    the list are cast. This is done for a technical reasons, e.g. when table is time partitioned. 
___

## When to use
We recommend using this check when you would like to validate the data for certain values. For example when you expect
`country` column to contain records from few countries, e.g. Poland, United States, Germany (see the 
[example](values_in_set_percent.md#string-to-numeric)).

!!! Warning
    Running this check defining the wrong data type for the column might result with an error.
    This is because casting some types to another is impossible, for e.g. `DATE` to `NUMERIC`. That is why we
    recommend using this query on `STRING` types, where such errors do not occur.
___

## Used sensor

[__Values in set percent__](../../../sensor_reference/validity/values_in_set_percent/values_in_set_percent.md)
___

## Accepted rules
[__Min count__](../../../rule_reference/comparison/min_count.md)

[__Count equals__](../../../rule_reference/comparison/count_equals.md)
___

## Parameters
This checks has two parameters that configure sensor:

- `values_type`: _str_ (Optional)
  <br/>predefined values type provided from user, types are listed in enum (default: `STRING`)
  [_BuiltInListFormats_](values_in_set_percent.md#list-of-built-in-list-formats)
- `values_list`: _list_
  <br/>list of values provided by the user

The default type is `STRING`, which is one of the types of `values_type`.
In case of `values_list` user has to provide a list of values compatible with `values_type`(see the [examples](values_in_set_percent.md#how-to-use)).
___

### List of built in list formats

=== "BigQuery"

    | Name                 | Type                                                               |
    |----------------------|---------------------------------------------------------------------------|
    | `NUMERIC`  | 'NUMERIC'|
    | `STRING` | 'STRING'|
    |`DATE`| 'DATE' |

=== "Snowflake"

    | Name                 | Type                                                               |
    |----------------------|---------------------------------------------------------------------------|
    | `NUMERIC`  | 'NUMERIC'|
    | `STRING` | 'STRING'|
    |`DATE`| 'DATE' |                            

___

## How to use

### Default configuration
The default configuration of column validity check `values_in_set_percent` with `min_count` rule.
If the parameter `values_type` is not chosen, the
default data type for values provided from user is `STRING`.

Table configuration in YAML file for column check `values_in_set_percent` on a column `countries` in `table`
table in `test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule ,looks like this
```yaml hl_lines="16-28" linenums="1"
--8<-- "docs/check_reference/validity/values_in_set_percent/yamls/default_configuration.yaml"
```
For default check configuration and passed `values_list: ["PL","US","DE"]` the rendered query is
``` SQL
{{ process_template_request(get_request("docs/check_reference/validity/values_in_set_percent/requests/default_configuration.json")) }}
```

### String to numeric
Table configuration in YAML file for column check `values_in_set_percent` on a column `mix_string_int` in `table` table in
`test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule, looks like this
```yaml hl_lines="16-29" linenums="1"
--8<-- "docs/check_reference/validity/values_in_set_percent/yamls/string_type_numeric_values.yaml"
```
In this case user provided `values_type` as `STRING` but `values_list` has numeric values so sensor convert `NUMERIC` values to `STRING`.
The rendered query is
``` SQL
{{ process_template_request(get_request("docs/check_reference/validity/values_in_set_percent/requests/string_type_numeric_values.json")) }}
```

### Numeric to numeric
Table configuration in YAML file for column check `values_in_set_percent` on a column `id` in `table` table in
`test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule, looks like this
```yaml hl_lines="16-29" linenums="1"
--8<-- "docs/check_reference/validity/values_in_set_percent/yamls/numeric_type_numeric_values.yaml"
```
In this case `values_type` is the same to values type into `values_list`, so sensor tries cast column to provided type.
The rendered query is
``` SQL
{{ process_template_request(get_request("docs/check_reference/validity/values_in_set_percent/requests/numeric_type_numeric_values.json")) }}
```

### Date to date
Table configuration in YAML file for column check `values_in_set_percent` on a column `date` in `table` table in
`test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule, looks like this
```yaml hl_lines="16-29" linenums="1"
--8<-- "docs/check_reference/validity/values_in_set_percent/yamls/date_type_date_values.yaml"
```
In this case user provided `values_type` as a `DATE`, so sensor cast provided values to `DATE`, even then type is equal to `DATE`.
The rendered query is
``` SQL
{{ process_template_request(get_request("docs/check_reference/validity/values_in_set_percent/requests/date_type_date_values.json")) }}
```