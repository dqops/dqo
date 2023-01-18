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

The query for this sensor calculates the percentage of records matching with provided list of values from user.
It performed by using SQL function IN(). This function takes value/values of the same type, then checks the values from table is match to provided values.
If types of given values don't match to the `values_type`, sensor will convert this values to the correct type.
For e.g. `values_type: STRING` and `values_list: [1,2,3]`, after converting function IN takes ('1', '2', '3'), not (1, 2, 3).

In the case, when `values_type` and type of values in the list is the same, function IN takes same values from list without converting.
The special case is when we declare `DATE` values, because sensor will cast this values to `DATE`, even then type is correct.
This works that to avoid problem with `DATE` types in databases.

In the case, when `values_list` length is equal to zero (it's mean empty list), function IN takes `NULL` as parameter.

Furthermore, when user specifying a different data type than the represented by the column, sensor tries to cast column values to specified type.
For e.g. column in databases represent `NUMERIC` values, but `values_type: STRING`, sensor will cast column values to `STRING`.

Successfully matched records are assigned value of 1, and any other values, 0.
Those values are then summed (so effectively we perform count of valid values), divided by the number of records,
and multiplicated by a 100.0 so that the results is in percent.

!!! Warning
    Running this check defining the wrong data type for the column might result with an error.
    This is because casting some types to another is impossible, for e.g. `DATE` to `NUMERIC`. That is why we
    recommend using this query on `STRING` types, where such errors do not occur.


Please see [Values in set percent](../../../sensor_reference/validity/values_in_set_percent/values_in_set_percent.md) for more information.

___
## Accepted rules
`Min_count` rule verifies if a data quality check reading is greater or equal a minimum value.

This rule has one parameter that should be configured for each alert :

- `low` :
  <br/>rule threshold for a low severity (1) alert
    - `min_value`: float
    - minimum accepted value for the `actual_value` returned by the sensor (inclusive)
- `medium` :
  <br/>rule threshold for a medium severity (2) alert
    - `min_value`: float
    - minimum accepted value for the `actual_value` returned by the sensor (inclusive)
- `high` :
  <br/>rule threshold for a high severity (3) alert
    - `min_value`: float
    - minimum accepted value for the `actual_value` returned by the sensor (inclusive)

`Count_equals` verifies that a data quality check reading equals a given value. A margin of error may be configured.

This check has two parameters that should be configured for each alert:

- `low`:
  <br/>rule threshold for a low severity (1) alert
    - `expectedValue`: _float_
      <br/>expected value for the actual_value returned by the sensor. The sensor value should equal `expected_value +/- the error_margin`.
    - `errorMargin`: _float_
      <br/>error margin for comparison.
- `medium`:
  <br/>rule threshold for a medium severity (2) alert
    - `expectedValue`: _float_
      <br/>expected value for the actual_value returned by the sensor. The sensor value should equal `expected_value +/- the error_margin`.
    - `errorMargin`: _float_
      <br/>error margin for comparison.
- `high`:
  <br/>rule threshold for a high severity (3) alert
    - `expectedValue`: _float_
      <br/>expected value for the actual_value returned by the sensor. The sensor value should equal `expected_value +/- the error_margin`.
    - `errorMargin`: _float_
      <br/>error margin for comparison.

For more information please refer to [Min count](../../../rule_reference/comparison/min_count.md) and [Count equals](../../../rule_reference/comparison/count_equals.md)

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
table in `test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule ,looks like this:
```yaml hl_lines="16-28" linenums="1"
--8<-- "docs/check_reference/validity/values_in_set_percent/yamls/default_configuration.yaml"
```

For default check configuration and passed `values_list: ["PL","US","DE"]` the rendered query is:
``` SQL
{{ process_template_request(get_request("docs/check_reference/validity/values_in_set_percent/requests/default_configuration.json")) }}
```

#### String to numeric

Table configuration in YAML file for column check `values_in_set_percent` on a column `mix_string_int` in `table` table in
`test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule, looks like this:
```yaml hl_lines="16-29" linenums="1"
--8<-- "docs/check_reference/validity/values_in_set_percent/yamls/string_type_numeric_values.yaml"
```

In this case user provided `values_type` as `STRING` but `values_list` has numeric values so sensor convert `NUMERIC` values to `STRING`.

The rendered query is:
``` SQL
{{ process_template_request(get_request("docs/check_reference/validity/values_in_set_percent/requests/string_type_numeric_values.json")) }}
```

#### Numeric to numeric

Table configuration in YAML file for column check `values_in_set_percent` on a column `id` in `table` table in
`test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule, looks like this:
```yaml hl_lines="16-29" linenums="1"
--8<-- "docs/check_reference/validity/values_in_set_percent/yamls/numeric_type_numeric_values.yaml"
```

In this case `values_type` is the same to values type into `values_list`, so sensor tries cast column to provided type.

The rendered query is:
``` SQL
{{ process_template_request(get_request("docs/check_reference/validity/values_in_set_percent/requests/numeric_type_numeric_values.json")) }}
```

#### Date to date

Table configuration in YAML file for column check `values_in_set_percent` on a column `date` in `table` table in
`test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule, looks like this:
```yaml hl_lines="16-29" linenums="1"
--8<-- "docs/check_reference/validity/values_in_set_percent/yamls/date_type_date_values.yaml"
```

In this case user provided `values_type` as a `DATE`, so sensor cast provided values to `DATE`, even then type is equal to `DATE`.

The rendered query is:
``` SQL
{{ process_template_request(get_request("docs/check_reference/validity/values_in_set_percent/requests/date_type_date_values.json")) }}
```

---
### Walkthrough the example

In order to understand how to use this check, let's walk through the [example](../../../examples/validity/values_in_set_percent/values_in_set_percent.md) step by step.

Let's have a look at the first five rows from the table used in the example - `bigquery-public-data.austin_crime.crime`

| Row | unique_key  | timestamp_column        | timestamp_column2       |
|-----|-------------|-------------------------|-------------------------|
| 1   | 22-00377488 | 2022-10-10 14:59:05 UTC | 2022-10-11 14:59:05 UTC |
| 2   | 22-00377376 | 2022-10-10 14:17:17 UTC | 2022-10-11 14:17:17 UTC |
| 3   | 22-00376929 | 2022-10-10 16:10:52 UTC | 2022-10-11 16:10:52 UTC |
| 4   | 22-00376839 | 2022-10-10 16:46:44 UTC | 2022-10-11 16:46:44 UTC |
| 5   | 22-00376178 | 2022-10-10 15:40:32 UTC | 2022-10-11 15:40:32 UTC |

#### Check configuration
Having added connection and imported tables (in the [example](../../../examples/validity/values_in_set_percent/values_in_set_percent.md)
connection, table and check are ready) now it is possible to access the table configuration by running:

```
table edit -c=conn_bq_11 -t=austin_crime.crime
```

The YAML configuration looks like this:

```yaml hl_lines="44-56" linenums="1"
--8<-- "docs/check_reference/validity/values_in_set_percent/yamls/austin_crime.crime.dqotable.yaml"
```

Let's review what this configuration means.

#### Sensor

```
      checks:
        validity:
          values_in_set_percent:
            parameters:
              values_list: [ "UK","A","B","C","D" ]
```

Sensor for this check is :

- dimension: `validity`

- sensor: `values_in_set_percent`

The `values_list` is `[ "UK","A","B","C","D" ]`.

As the check is written under the section of `district` column, the check will be done on that column.

Then this parameter is passed to the rendered query:

```
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN NULL
        ELSE
            100.0 * SUM(
                CASE
                    WHEN (analyzed_table.`district` in ('UK', 'A', 'B', 'C', 'D')) IS TRUE THEN 1
                    ELSE 0
                END
            )/COUNT(*)
    END AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
FROM `bigquery-public-data`.`austin_crime`.`crime` AS analyzed_table
GROUP BY time_period
ORDER BY time_period
```

#### Rule
To evaluate check results, we have to define a rule:

```
            rules:
              min_count:
                low:
                  min_value: 80.0
                medium:
                  min_value: 40.0
                high:
                  min_value: 30.0
```
The `min_count` rule configuration says that :

- if actual value is above 80.0 then the result is valid,

- if actual value is below 80.0 and above 40.0 then severity is 1 (low),

- if actual value is below 40.0 and above 30.0 then severity is 2 (medium),

- if actual value is below 30.0 then severity is 3 (high).

Below are the results of our example:

```
+-----------------+-----------+
|actual_value     |time_period|
+-----------------+-----------+
|45.21019927148061|2022-10-20 |
+-----------------+-----------+
```

The actual value is below 80.0, so it is invalid, it is also above 40.0, so the severity in this case is low.

The table above is the exact same as the one you would see on the provider's platform (in this case BigQuery).

The query returns two columns:

- actual_value which is the percent of records within the passed parameters,

- time_period, configured with time_series. With mode=current_time the goal of time_period is to record a date of check execution.

#### Check summary
Check evaluation summary briefly informs us about check execution:

```
+----------+------------------+------+--------------+-------------+------------+---------------+-------------+
|Connection|Table             |Checks|Sensor results|Valid results|Alerts (low)|Alerts (medium)|Alerts (high)|
+----------+------------------+------+--------------+-------------+------------+---------------+-------------+
|conn_bq_11|austin_crime.crime|1     |1             |0            |1           |0              |0            |
+----------+------------------+------+--------------+-------------+------------+---------------+-------------+
```

There is only one check defined, 1 sensor result, where:

- 0 result is valid

- 1 results with low severity

- 0 results with medium severity

- 0 result with high severity