## Description
The `non_negative_percent` check calculates the percentage of non-negative column's values.

___
## When to use
The usability of this check is obvious. It is useful for validation of numerical values that we expect to be 
non-negative.

___
## Used sensor

The query for this check calculates the percentage of non-negative values from column.

Successfully classified records are assigned value of 1, and any other values, 0. Those values are then summed (so effectively we perform count of valid values), divided by the number of records, and multiplicated by a 100.0 so that the results is in percent.

Please see [Non-negative percent](../../../sensor_reference/validity/non_negative_percent/non_negative_percent.md) for more information.

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
This check takes no parameters.

___
## How to use

### Default configuration

The default configuration of column validity check `non_negative_percent` on columns `id` with `min_count` rule, looks like this:
```yaml hl_lines="16-28" linenums="1"
--8<-- "docs/check_reference/validity/non_negative_percent/yamls/default.yaml"
```

The rendered query is:
```SQL
{{ process_template_request(get_request("docs/check_reference/validity/non_negative_percent/requests/default.json")) }}
```

---
### Walkthrough the example

In order to understand how to use this check, let's walk through the [example](../../../examples/validity/non_negative_percent/non_negative_percent.md) step by step.

Let's have a look at the first five rows from the table used in the example - `bigquery-public-data.covid19_italy.data_by_region`

| Row | unique_key  | timestamp_column        | timestamp_column2       |
|-----|-------------|-------------------------|-------------------------|
| 1   | 22-00377488 | 2022-10-10 14:59:05 UTC | 2022-10-11 14:59:05 UTC |
| 2   | 22-00377376 | 2022-10-10 14:17:17 UTC | 2022-10-11 14:17:17 UTC |
| 3   | 22-00376929 | 2022-10-10 16:10:52 UTC | 2022-10-11 16:10:52 UTC |
| 4   | 22-00376839 | 2022-10-10 16:46:44 UTC | 2022-10-11 16:46:44 UTC |
| 5   | 22-00376178 | 2022-10-10 15:40:32 UTC | 2022-10-11 15:40:32 UTC |

#### Check configuration
Having added connection and imported tables (in the [example](../../../examples/validity/non_negative_percent/non_negative_percent.md)
connection, table and check are ready) now it is possible to access the table configuration by running:

```
table edit -c=conn_bq_4 -t=covid19_italy.data_by_region
```

The YAML configuration looks like this:

```yaml hl_lines="65-75" linenums="1"
--8<-- "docs/check_reference/validity/non_negative_percent/yamls/covid19_italy.data_by_region.dqotable.yaml"
```

Let's review what this configuration means.

#### Sensor

```
      checks:
        validity:
          non_negative_percent:
```

Sensor for this check is :

- dimension: `validity`

- sensor: `non_negative_percent`

As the check is written under the section of `new_current_confirmed_cases` column, the check will be done on that column.

Then this parameter is passed to the rendered query:

```
SELECT
    100.0 * SUM(
        CASE
            WHEN analyzed_table.`new_current_confirmed_cases` < 0 THEN 0
            ELSE 1
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
+------------------+-----------+
|actual_value      |time_period|
+------------------+-----------+
|61.944157187176835|2022-10-18 |
+------------------+-----------+
```

The actual value is below the lowest threshold (70.0), hence it is invalid.

The table above is the exact same as the one you would see on the provider's platform (in this case BigQuery).

The query returns two columns:

- actual_value which is a percent of valid records,

- time_period, configured with time_series. With mode=current_time the goal of time_period is to record a date of check execution.

#### Check summary
Check evaluation summary briefly informs us about check execution:

```
+----------+----------------------------+------+--------------+-------------+------------+---------------+-------------+
|Connection|Table                       |Checks|Sensor results|Valid results|Alerts (low)|Alerts (medium)|Alerts (high)|
+----------+----------------------------+------+--------------+-------------+------------+---------------+-------------+
|conn_bq_4 |covid19_italy.data_by_region|1     |1             |0            |0           |0              |1            |
+----------+----------------------------+------+--------------+-------------+------------+---------------+-------------+
```

There is only one check defined, 1 sensor result, where:

- 0 result is valid

- 0 results with low severity

- 0 results with medium severity

- 1 result with high severity