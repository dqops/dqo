## Description

The `numerical_type_percent` check calculates the percentage of numerical values from column.

___
## When to use
This sensor can be used when we receive a `STRING` column containing numerical values, and we would like to find the 
number of values that can be cast as `NUMERICAL`

___
## Used sensor

The query for this check calculates the percentage of numerical values from column.

Successfully classified records are assigned value of 1, and any other values, 0. Those values are then summed (so effectively we perform count of valid values), divided by the number of records, and multiplicated by a 100.0 so that the results is in percent.

Please see [Numerical type percent](../../../sensor_reference/validity/numerical_type_percent/numerical_type_percent.md) for more information.

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
The sensor does not accept any parameters

___
## How to use

### Default configuration

The default configuration of column validity check `numerical_type_percent` on column `id` with `min_count` rule, looks like this:
```yaml hl_lines="16-28" linenums="1"
--8<-- "docs/check_reference/validity/numerical_type_percent/yamls/default.yaml"
```
The rendered query is:
```SQL
{{ process_template_request(get_request("docs/check_reference/validity/numerical_type_percent/requests/default.json")) }}
```

---
### Walkthrough the example

In order to understand how to use this check, let's walk through the [example](../../../examples/validity/numerical_type_percent/numerical_type_percent.md) step by step.

Let's have a look at the first five rows from the table used in the example - `bigquery-public-data.austin_311.311_service_requests`

| Row | unique_key  | timestamp_column        | timestamp_column2       |
|-----|-------------|-------------------------|-------------------------|
| 1   | 22-00377488 | 2022-10-10 14:59:05 UTC | 2022-10-11 14:59:05 UTC |
| 2   | 22-00377376 | 2022-10-10 14:17:17 UTC | 2022-10-11 14:17:17 UTC |
| 3   | 22-00376929 | 2022-10-10 16:10:52 UTC | 2022-10-11 16:10:52 UTC |
| 4   | 22-00376839 | 2022-10-10 16:46:44 UTC | 2022-10-11 16:46:44 UTC |
| 5   | 22-00376178 | 2022-10-10 15:40:32 UTC | 2022-10-11 15:40:32 UTC |

#### Check configuration
Having added connection and imported tables (in the [example](../../../examples/validity/numerical_type_percent/numerical_type_percent.md)
connection, table and check are ready) now it is possible to access the table configuration by running:

```
table edit -c=conn_bq_6 -t=austin_311.311_service_requests
```

The YAML configuration looks like this:

```yaml hl_lines="53-63" linenums="1"
--8<-- "docs/check_reference/validity/numerical_type_percent/yamls/austin_311.311_service_requests.dqotable.yaml"
```

Let's review what this configuration means.

#### Sensor

```
      checks:
        validity:
          numerical_type_percent:
```

Sensor for this check is :

- dimension: `validity`

- sensor: `numerical_type_percent`

As the check is written under the section of `street_number` column, the check will be done on that column.

Then this parameter is passed to the rendered query:

```
SELECT
    CASE
        WHEN COUNT(analyzed_table.`street_number`)=0 THEN NULL
        ELSE 100.0*SUM(
                    CASE
                        WHEN SAFE_CAST( analyzed_table.`street_number` AS NUMERIC ) IS NOT NULL THEN 1
                        ELSE 0
                    END
                    )/COUNT(analyzed_table.`street_number`)
    END AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
FROM `bigquery-public-data`.`austin_311`.`311_service_requests` AS analyzed_table
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
+------------+-----------+
|actual_value|time_period|
+------------+-----------+
|100.0       |2022-10-19 |
+------------+-----------+
```

The actual value is above the highest threshold (90.0), hence it is valid.

The table above is the exact same as the one you would see on the provider's platform (in this case BigQuery).

The query returns two columns:

- actual_value which is the percent of numerical type records,

- time_period, configured with time_series. With mode=current_time the goal of time_period is to record a date of check execution.

#### Check summary
Check evaluation summary briefly informs us about check execution:

```
+----------+-------------------------------+------+--------------+-------------+------------+---------------+-------------+
|Connection|Table                          |Checks|Sensor results|Valid results|Alerts (low)|Alerts (medium)|Alerts (high)|
+----------+-------------------------------+------+--------------+-------------+------------+---------------+-------------+
|conn_bq_6 |austin_311.311_service_requests|1     |1             |1            |0           |0              |0            |
+----------+-------------------------------+------+--------------+-------------+------------+---------------+-------------+
```

There is only one check defined, 1 sensor result, where:

- 1 result is valid

- 0 results with low severity

- 0 results with medium severity

- 0 result with high severity