## Description

The `value_in_range_numerical_percent` check calculates the percent of numerical values that are within a range provided
by the user (with `min_value` and `max_value`).

Range bounds are required parameters. There are two more parameters: `include_min_value` and `include_max_value`, that
you can use to (as the name suggests) include min and\or max value. By default, range bounds are included.

---
## When to use
This can be used on numerical columns, where you want to validate values to be in a certain range. For example a 
customer's age should be greater or equal to 18, and lesser than e.g. 100 (see the 
[example](value_in_range_numerical_percent.md#using-only-one-optional-parameter)).

!!! Warning
    Running this check on a non-numeric column may result with an error.
___

## Used sensor

The query for this check calculates the percent of numerical values that are within a range provided by the user.
The `value_in_range_numerical_percent` checks values from column which are within a range of `min_value` and `max_value`.
The user decides whether to include these values to the range, using optional parameters `include_min_value` and `include_max_value`.
Default in check `min_value` and `max_value`  are included in the range.

Successfully classified records are assigned value of 1, and any other values, 0.
Those values are then summed (so effectively we perform count of valid values), divided by the number of records,
and multiplicated by a 100.0 so that the results is in percent.

Please see [Value in range numerical percent](../../../sensor_reference/validity/value_in_range_numerical_percent/value_in_range_numerical_percent.md) for more information.

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
table in `test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule, looks like this:
```yaml hl_lines="16-29" linenums="1"
--8<-- "docs/check_reference/validity/value_in_range_numerical_percent/yamls/default_configuration.yaml"
```

For default check configuration the rendered query is:
``` SQL
{{ process_template_request(get_request("docs/check_reference/validity/value_in_range_numerical_percent/requests/default_configuration.json")) }}
```

#### Using all optional parameters

Table configuration in YAML file for column check `value_in_range_numerical_percent` on a column `dates` in `string_dates`
table in `test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule, looks like this:
```yaml hl_lines="16-31" linenums="1"
--8<-- "docs/check_reference/validity/value_in_range_numerical_percent/yamls/all_optional_parameters.yaml"
```

In this case, we set two optional parameters `include_min_value: false` and `include_max_value: false`.

The rendered query is:
``` SQL
{{ process_template_request(get_request("docs/check_reference/validity/value_in_range_numerical_percent/requests/all_optional_parameters.json")) }}
```

#### Using only one optional parameter

Table configuration in YAML file for column check `value_in_range_numerical_percent` on a column `customer_age` in 
`customers`
table in `test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule, looks like this:
```yaml hl_lines="16-30" linenums="1"
--8<-- "docs/check_reference/validity/value_in_range_numerical_percent/yamls/one_optional_parameter.yaml"
```

In this case, we set only one optional parameter `include_max_value: false`.

The rendered query is:
``` SQL
{{ process_template_request(get_request("docs/check_reference/validity/value_in_range_numerical_percent/requests/one_optional_parameter.json")) }}
```
The same is for the upper bound.

---
### Walkthrough the example

In order to understand how to use this check, let's walk through the [example](../../../examples/validity/value_in_range_numerical_percent/value_in_range_numerical_percent.md) step by step.

Let's have a look at the first five rows from the table used in the example - `bigquery-public-data.covid19_italy.data_by_region`

| Row | unique_key  | timestamp_column        | timestamp_column2       |
|-----|-------------|-------------------------|-------------------------|
| 1   | 22-00377488 | 2022-10-10 14:59:05 UTC | 2022-10-11 14:59:05 UTC |
| 2   | 22-00377376 | 2022-10-10 14:17:17 UTC | 2022-10-11 14:17:17 UTC |
| 3   | 22-00376929 | 2022-10-10 16:10:52 UTC | 2022-10-11 16:10:52 UTC |
| 4   | 22-00376839 | 2022-10-10 16:46:44 UTC | 2022-10-11 16:46:44 UTC |
| 5   | 22-00376178 | 2022-10-10 15:40:32 UTC | 2022-10-11 15:40:32 UTC |

#### Check configuration
Having added connection and imported tables (in the [example](../../../examples/validity/value_in_range_numerical_percent/value_in_range_numerical_percent.md)
connection, table and check are ready) now it is possible to access the table configuration by running:

```
table edit -c=conn_bq_10 -t=covid19_italy.data_by_region
```

The YAML configuration looks like this:

```yaml hl_lines="73-86" linenums="1"
--8<-- "docs/check_reference/validity/value_in_range_numerical_percent/yamls/covid19_italy.data_by_region.dqotable.yaml"
```

Let's review what this configuration means.

#### Sensor

```
      checks:
        validity:
          value_in_range_numerical_percent:
            parameters:
              min_value: 0.0
              max_value: 500.0
```

Sensor for this check is :

- dimension: `validity`

- sensor: `value_in_range_numerical_percent`

The `min_value` is `0.0` and `max_value` is `500.0`.

As the check is written under the section of `recovered` column, the check will be done on that column.

Then this parameter is passed to the rendered query:

```
SELECT
    100.0 * SUM(
        CASE
            WHEN analyzed_table.`recovered` >= 0.0 AND analyzed_table.`recovered` <= 500.0 THEN 1
            ELSE 0
        END
    ) / COUNT(*) AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
FROM `bigquery-public-data`.`covid19_italy`.`data_by_region` AS analyzed_table
WHERE region_name='Sicilia'
GROUP BY time_period
ORDER BY time_period
```

#### Rule
To evaluate check results, we have to define a rule:

```
            rules:
              min_count:
                low:
                  min_value: 90.0
                medium:
                  min_value: 80.0
                high:
                  min_value: 70.0
```
The `min_count` rule configuration says that :

- if actual value is above 90.0 then the result is valid,

- if actual value is below 90.0 and above 80.0 then severity is 1 (low),

- if actual value is below 80.0 and above 70.0 then severity is 2 (medium),

- if actual value is below 70.0 then severity is 3 (high).

Below are the results of our example:

```
+-----------------+-----------+
|actual_value     |time_period|
+-----------------+-----------+
|6.295149638802889|2022-10-20 |
+-----------------+-----------+
```

The actual value is below the lowest threshold (70.0), hence it is invalid.

The table above is the exact same as the one you would see on the provider's platform (in this case BigQuery).

The query returns two columns:

- actual_value which is the percent of records within the passed parameters,

- time_period, configured with time_series. With mode=current_time the goal of time_period is to record a date of check execution.

#### Check summary
Check evaluation summary briefly informs us about check execution:

```
+----------+----------------------------+------+--------------+-------------+------------+---------------+-------------+
|Connection|Table                       |Checks|Sensor results|Valid results|Alerts (low)|Alerts (medium)|Alerts (high)|
+----------+----------------------------+------+--------------+-------------+------------+---------------+-------------+
|conn_bq_10|covid19_italy.data_by_region|1     |1             |0            |0           |0              |1            |
+----------+----------------------------+------+--------------+-------------+------------+---------------+-------------+
```

There is only one check defined, 1 sensor result, where:

- 0 result is valid

- 0 results with low severity

- 0 results with medium severity

- 1 result with high severity