## Description
The `date_type_percent` check validates `STRING` or `VARCHAR` as `DATE`.
The check runs three forms of safe casts on the column and calculates the percent of values that can be interpreted as dates.

Safe cast (try cast) works like the usual cast function, when a given value is castable as selected data type. When cast is not possible, it returns `NULL` value,
so the risk of getting an error is significantly lower, although it is still possible.

The three mentioned forms of safe casts are:

- built-in safe cast function that casts value as `DATE`,
- built-in safe cast function that casts value as `FLOAT` (for UNIX time),
- safe parse date for a given date format.

The last one comes in handy when we have to deal with non-standard date formats, e.g. `Jan 1, 2022`. For such situations there are [built in date formats](date_type_percent.md#using-named-date-formats). In other cases, you can provide your own date format
with [custom date format](date_type_percent.md#using-custom-date-formats) parameter.

!!! Info
    `custom_date_format` is prioritized, meaning that 
    if `named_date_format` is default or chosen, and `custom_date_format` is provided, the query
    will run with date format provided by `custom_date_format`.


## When to use
This sensor comes in handy when we receive a dataset where a column associated with 
date is `STRING` type, and we would like to validate those records as actual dates.

!!! Warning
    Running this check on `date` or `numeric` column might result with an error.
    This is because safe casting date as numeric and vice versa is impossible. That is why we
    recommend using this query on `string` data types, where such errors do not occur.

___

## Used sensor

The query for this sensor calculates the percentage of records that can be interpreted as dates. It performed by casting values as dates, parsing them with a certain date format and casting as float.

Please see [Date type percent](../../../sensor_reference/validity/date_type_percent/date_type_percent.md) for more information.
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
This check has two optional parameters that configure date format to parse:

- `named_date_format`: _str_
    <br/>predefined date format used for parsing string, formats are listed in [BuiltInDateFormats](date_type_percent.md#list-of-built-in-date-formats)
- `custom_date_format`: _str_
    <br/>custom date format used for parsing string

The default format is `ISO8601`, which is one of the values of `named_date_format`.
In case of `custom_date_format` user has to provide a string with desired date format (see the [examples](date_type_percent.md#how-to-use)).

If `custom_date_format` is provided with a non-empty string, `named_date_format` is neglected, even if configured.


### List of built in date formats

=== "BigQuery"

    | Name                 | Format                                                               |
    |----------------------|---------------------------------------------------------------------------|
    | `ISO8601`  | '%Y-%m-%d'|
    | `MonthDayYear` | '%m/%d/%Y|
    |`DayMonthYear`| '%d/%m/%Y' |
    |`YearMonthDay`| '%Y/%m/%d' |
    |`MonthNameDayYear`| '%b %d, %Y' |

=== "Snowflake"

    | Name                 | Format                                                               |
    |----------------------|---------------------------------------------------------------------------|
    | `ISO8601`  | 'YYYY-MM-DD'|
    | `MonthDayYear` |'MM/DD/YYYY'|
    |`DayMonthYear`| 'DD/MM/YYYY' |
    |`YearMonthDay`| 'YYYY/MM/DD' |
    |`MonthNameDayYear`| 'MON DD, YYYY' |                              

___

## How to use

The following examples picture when and how to use the `date_type_percent` check. Here we provide a description
of the usage, the whole example is ready to run [here](../../../examples/validity/date_type_percent/date_type_percent.md).

### Default configuration

The default configuration of column validity check `date_type_percent` with `min_count` rule.
If the parameter `named_date_format` is not chosen or `custom_date_format` is not specified, the
default parsing date format is `ISO8601`.


Table configuration in YAML file for column check `date_type_percent` on a column `dates` in `string_dates`
table in `test_data` dataset in `dqo-ai` project on BigQuery and `min_count` rule looks like this

```yaml hl_lines="24-34" linenums="1"
--8<-- "docs/check_reference/validity/date_type_percent/yamls/default_config.yml"
```

For default check configuration the rendered query is

```SQL linenums="1"
{{ process_template_request(get_request("docs/check_reference/validity/date_type_percent/requests/default_config.json")) }}
```

### Using named date formats
Table configuration in YAML file for column check `date_type_percent` on a column `dates` in `string_dates` table in
`test_data` dataset in `dqo-ai` project on BigQuery, `min_count` rule and passed parameter `named_date_format: 'MonthNameDayYear'` looks like this

```yaml hl_lines="24-36" linenums="1"
--8<-- "docs/check_reference/validity/date_type_percent/yamls/named_date_format_MonthNameDayYear.yml"
```
The rendered query is

``` SQL hl_lines="6" linenums="1"
{{ process_template_request(get_request("docs/check_reference/validity/date_type_percent/requests/named_date_format_MonthNameDayYear.json")) }}
```

### Using custom date formats
Table configuration in YAML file for column check `date_type_percent` on a column `dates` in `string_dates` table in
`test_data` dataset in `dqo-ai` project on BigQuery, `count_equals` rule and passed parameter `custom_date_format: '%Y, %b, %d'` looks like this

```yaml hl_lines="24-36" linenums="1"
--8<-- "docs/check_reference/validity/date_type_percent/yamls/custom_date_format.yml"
```

The rendered query is

``` SQL hl_lines="6" linenums="1"
{{ process_template_request(get_request("docs/check_reference/validity/date_type_percent/requests/custom_date_format.json")) }}
```

``` hl_lines="5"
--8<-- "docs/check_reference/validity/date_type_percent/tables/extracted_data.txt"
```

### Walkthrough the example

In order to understand how to use this check, let's walk through the [example](../../../examples/validity/date_type_percent/date_type_percent.md) step by step.

Let's have a look at the first five rows from the table used in the example - `bigquery-public-data.labeled_patents.extracted_data`

| Row | unique_key  | timestamp_column        | timestamp_column2       |
|-----|-------------|-------------------------|-------------------------|
| 1   | 22-00377488 | 2022-10-10 14:59:05 UTC | 2022-10-11 14:59:05 UTC |
| 2   | 22-00377376 | 2022-10-10 14:17:17 UTC | 2022-10-11 14:17:17 UTC |
| 3   | 22-00376929 | 2022-10-10 16:10:52 UTC | 2022-10-11 16:10:52 UTC |
| 4   | 22-00376839 | 2022-10-10 16:46:44 UTC | 2022-10-11 16:46:44 UTC |
| 5   | 22-00376178 | 2022-10-10 15:40:32 UTC | 2022-10-11 15:40:32 UTC |

#### Check configuration
Having added connection and imported tables (in the [example](../../../examples/uniqueness/distinct_count_percent/distinct_count_percent.md)
connection, table and check are ready) now it is possible to access the table configuration by running:

```
table edit -c=conn_bq_1 -t=labeled_patents.extracted_data
```

The YAML configuration looks like this:

```yaml hl_lines="29-41" linenums="1"
--8<-- "docs/check_reference/validity/date_percent/yamls/labeled_patents.extracted_data.dqotable.yaml"
```

Let's review what this configuration means.

#### Sensor

```
      checks:
        validity:
          date_type_percent:
            parameters:
              custom_date_format: "%d.%m.%Y"
```

Sensor for this check is :

- dimension: `validity`

- sensor: `date_type_percent`

Date format used in this example is custom: `%d.%m.%Y`

As the check is written under the section of `publication_date` column, the check will be done on that column.

Then this parameter is passed to the rendered query:

```
SELECT
    100.0 * SUM(
        CASE
            WHEN SAFE_CAST(analyzed_table.`publication_date` AS FLOAT64) IS NOT NULL
            OR SAFE_CAST(analyzed_table.`publication_date` AS DATE) IS NOT NULL
            OR SAFE.PARSE_DATE('%d.%m.%Y', analyzed_table.`publication_date`) IS NOT NULL THEN 1
            ELSE 0
        END
    ) / COUNT(*) AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
FROM `bigquery-public-data`.`labeled_patents`.`extracted_data` AS analyzed_table
GROUP BY time_period
ORDER BY time_period
```

#### Rule
To evaluate check results, we have to define a rule:

```
            rules:
              min_count:
                low:
                  min_value: 98.0
                medium:
                  min_value: 95.0
                high:
                  min_value: 90.0
```
The `min_count` rule configuration says that :

- if actual value is above 98.0 then the result is valid,

- if actual value is below 98.0 and above 95.0 then severity is 1 (low),

- if actual value is below 95.0 and above 90.0 then severity is 2 (medium),

- if actual value is below 90.0 then severity is 3 (high).

Below are the results of our example:

```
+----------------+-----------+
|actual_value    |time_period|
+----------------+-----------+
|71.7201166180758|2022-10-18 |
+----------------+-----------+
```

The actual value is below the lowest threshold (90.0), so the severity is high.

The table above is the exact same as the one you would see on the provider's platform (in this case BigQuery).

The query returns two columns:

- actual_value which is a percent of valid records,

- time_period, configured with time_series. With mode=current_time the goal of time_period is to record a date of check execution.

#### Check summary
Check evaluation summary briefly informs us about check execution:

```
+----------+------------------------------+------+--------------+-------------+------------+---------------+-------------+
|Connection|Table                         |Checks|Sensor results|Valid results|Alerts (low)|Alerts (medium)|Alerts (high)|
+----------+------------------------------+------+--------------+-------------+------------+---------------+-------------+
|conn_bq_1 |labeled_patents.extracted_data|1     |1             |0            |0           |0              |1            |
+----------+------------------------------+------+--------------+-------------+------------+---------------+-------------+
```

There is only one check defined, 1 sensor result, where:

- 0 result is valid

- 0 results with low severity

- 0 results with medium severity

- 1 result with high severity