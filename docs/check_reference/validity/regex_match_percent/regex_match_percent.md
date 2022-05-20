# Regex match percent
## Description
The column validity check `ragex_match_percent` counts the records matching a provided regex, then divides this 
number by the row count. This figure multiplicated by 100.0 tells us about the percent of records that follow 
a certain format.

You can use this check on any column that you can run regular expressions on. Typically, it would be a `STRING` or 
a `VARCHAR` data type.

This check has two optional parameters: `named_regex` which utilizes 
[predefined regexes](regex_match_percent.md#list-of-built-in-regexes), and `custom_regex`, where you provide a regular
expression yourself. Those parameters are described [here](regex_match_percent.md#parameters).


## BigQuery example with custom regex
First let's walk through the
[example for this check](../../../examples/validity/regex_match_percent/regex_match_percent.md) step by step.

**We refer to a query result as `actual_value`.**
### Sample data from BigQuery
The table is `bigquery-public-data.austin_311.311_service_requests`, here are the first 5 rows:

```
--8<-- "docs/check_reference/validity/regex_match_percent/tables/table.txt"
```

and the column we are checking for validity is `unique_key`
We will check if the records hold the format by using a `custom_regex`.

### Check configuration

After [adding connection](../../../commands/connection/connection.md#add)
and [importing tables](../../../commands/table/table.md#import)
(**in [the example](../../../examples/validity/regex_match_percent/regex_match_percent.md)
you can go ahead to running the check. Connection, table and check are ready**) you can access the table configuration
by running.

```
table edit -c=conn_bq_7 -t=austin_311.311_service_requests
```

The YAML configuration looks like this (all the code necessary to define this check is highlighted below):

```yaml hl_lines="18-30" linenums="1"
--8<-- "docs/check_reference/validity/regex_match_percent/yamls/austin_311.311_service_requests.dqotable.yaml"
```

Let's review what this configuration means.

#### Sensor

Sensor for this check is
[`column/validity/regex_match_percent`](../../../check_reference/validity/regex_match_percent/regex_match_percent.md),
which will be executed on a `unique_key` column (lines 19-20).
We passed a `custom_regex` parameter to run `^22-[0-9]{8}$` regex, so we expect the records to start with `22-` and 8
digits following (lines 20-21). This parameter is passed to the rendered query:

```SQL linenums="1" hl_lines="7"
{{ process_template_request(get_request("docs/check_reference/validity/regex_match_percent/requests/austin_311.311_service_requests.dqotable.json")) }}
```


```
Finished executing a sensor for a check regex_match_percent on the table austin_311.311_service_requests using a sensor definition column/validity/regex_match_percent, sensor result count: 2

Results returned by the sensor:
+------------+-----------+
|actual_value|time_period|
+------------+-----------+
|100.0       |2022-05-10 | <--- The timestamp of a data quality snapshot 
+------------+-----------+      (current timestamp)

```

The table above is the exact same as the one you would see on the provider's platform (in this case BigQuery).

The query returned two columns: `actual_value` which is a percent of records with valid format, and `time_period`,
configured with [`time_series`](../../../dqo_concept/time_series/time_series.md). With `mode=current_time` the goal of
`time_period` is to record a date of check execution.
#### Rule

To evaluate check results, we have to define a rule, which is done in lines 22-29:

```yaml linenums="22"
rules:
  min_count:
    low:
      min_value: 98.0
    medium:
      min_value: 95.0
    high:
      min_value: 90.0
```

The `min_count` rule configuration says that if the `actual_value >= 98.0` then the result is valid,
if `98.0 > actual_value >= 95.0` then severity is 1 (low), if `95.0 > actual_value >= 90.0` then severity is 2 (medium)
if `90.0 > actual_value` then severity is 3 (high).

```
Finished executing rules (thresholds) for a check regex_match_percent on the table austin_311.311_service_requests, verified rules count: 2

Rule evaluation results:
+------------+--------------+----------------+-------------+------------+-------------------+---------------+--------+-------------------+-----------+--------------------+-------------------+-----------+-------------------+-------------------+-----------------+-----------------------------------+------------------------+-----------+--------+-------------------+---------+----------------+------------------+---------------+
|actual_value|expected_value|time_period     |time_gradient|dimension_id|connection_hash    |connection_name|provider|table_hash         |schema_name|table_name          |column_hash        |column_name|check_hash         |check_name         |quality_dimension|sensor_name                        |executed_at             |duration_ms|severity|rule_hash          |rule_name|high_lower_bound|medium_lower_bound|low_lower_bound|
+------------+--------------+----------------+-------------+------------+-------------------+---------------+--------+-------------------+-----------+--------------------+-------------------+-----------+-------------------+-------------------+-----------------+-----------------------------------+------------------------+-----------+--------+-------------------+---------+----------------+------------------+---------------+
|100.0       |90.0          |2022-05-10T00:00|day          |0           |5183897182689125639|conn_bq_7      |bigquery|1483428340984869430|austin_311 |311_service_requests|6580082490137205204|unique_key |3062396720704446212|regex_match_percent|validity         |column/validity/regex_match_percent|2022-05-12T08:25:57.528Z|2711       |0       |2231697474420509704|min_count|90.0            |95.0              |98.0           |
+------------+--------------+----------------+-------------+------------+-------------------+---------------+--------+-------------------+-----------+--------------------+-------------------+-----------+-------------------+-------------------+-----------------+-----------------------------------+------------------------+-----------+--------+-------------------+---------+----------------+------------------+---------------+

```
The other table we receive is rule evaluation results. Here in `severity` column we are told about assigned severity
levels. Because our result is `100.0` which is greater that low severity threshold (in this case `98.0`), the severity
is `0` - a valid result.

[//]: # (### Check evaluation)

[//]: # ()
[//]: # (After saving the configuration, you can run the check with [`check run`]&#40;../../../commands/check/check.md#run&#41; command.)

[//]: # (To see the whole process of check execution, you can enable a)

[//]: # ([debug mode]&#40;../../../commands/check/check.md#--modedebug&#41;:)

[//]: # ()
[//]: # (```)

[//]: # (check run -m=debug)

[//]: # (```)

[//]: # ()
[//]: # (Here we will look at some information from a debug mode, where you are told about step by step execution.)

[//]: # ()
[//]: # (#### Query result)

[//]: # ()
[//]: # (``` yaml)

[//]: # (Finished executing a sensor for a check regex_match_percent on the table austin_311.311_service_requests using a sensor definition column/validity/regex_match_percent, sensor result count: 2)

[//]: # ()
[//]: # (Results returned by the sensor:)

[//]: # (+------------+-----------+)

[//]: # (|actual_value|time_period|)

[//]: # (+------------+-----------+)

[//]: # (|100.0       |2022-05-10 |)

[//]: # (+------------+-----------+)

[//]: # (```)

[//]: # ()
[//]: # (The table above is the exact same as the one you would see on the provider's platform &#40;in this case BigQuery&#41;.)

[//]: # ()
[//]: # (The query returned two columns: `actual_value` which is a percent of records with valid format, and `time_period`,)

[//]: # (configured with [`time_series`]&#40;../../../dqo_concept/time_series/time_series.md&#41;. With `mode = current` the goal of)

[//]: # (`time_period` is to record a date of check execution.)

[//]: # ()
[//]: # (#### Rule evaluation)

[//]: # ()
[//]: # (The other table we receive is rule evaluation results. Here in `severity` column we are told about assigned severity)

[//]: # (levels. Because our result is `100.0` which is greater that low severity threshold &#40;in this case `98.0`&#41;, the severity)

[//]: # (is `0` - a valid result.)

[//]: # ()
[//]: # (``` yaml)

[//]: # (Finished executing rules &#40;thresholds&#41; for a check regex_match_percent on the table austin_311.311_service_requests, verified rules count: 2)

[//]: # ()
[//]: # (Rule evaluation results:)

[//]: # (+------------+--------------+----------------+-------------+------------+-------------------+---------------+--------+-------------------+-----------+--------------------+-------------------+-----------+-------------------+-------------------+-----------------+-----------------------------------+------------------------+-----------+--------+-------------------+---------+----------------+------------------+---------------+)

[//]: # (|actual_value|expected_value|time_period     |time_gradient|dimension_id|connection_hash    |connection_name|provider|table_hash         |schema_name|table_name          |column_hash        |column_name|check_hash         |check_name         |quality_dimension|sensor_name                        |executed_at             |duration_ms|severity|rule_hash          |rule_name|high_lower_bound|medium_lower_bound|low_lower_bound|)

[//]: # (+------------+--------------+----------------+-------------+------------+-------------------+---------------+--------+-------------------+-----------+--------------------+-------------------+-----------+-------------------+-------------------+-----------------+-----------------------------------+------------------------+-----------+--------+-------------------+---------+----------------+------------------+---------------+)

[//]: # (|100.0       |90.0          |2022-05-10T00:00|day          |0           |5183897182689125639|conn_bq_7      |bigquery|1483428340984869430|austin_311 |311_service_requests|6580082490137205204|unique_key |3062396720704446212|regex_match_percent|validity         |column/validity/regex_match_percent|2022-05-12T08:25:57.528Z|2711       |0       |2231697474420509704|min_count|90.0            |95.0              |98.0           |)

[//]: # (+------------+--------------+----------------+-------------+------------+-------------------+---------------+--------+-------------------+-----------+--------------------+-------------------+-----------+-------------------+-------------------+-----------------+-----------------------------------+------------------------+-----------+--------+-------------------+---------+----------------+------------------+---------------+)

[//]: # (```)

#### Check summary
Check evaluation summary briefly informs us about check execution:

``` yaml
Check evaluation summary per table:
+----------+-------------------------------+------+--------------+-------------+------------+---------------+-------------+
|Connection|Table                          |Checks|Sensor results|Valid results|Alerts (low)|Alerts (medium)|Alerts (high)|
+----------+-------------------------------+------+--------------+-------------+------------+---------------+-------------+
|conn_bq_7 |austin_311.311_service_requests|1     |1             |1            |0           |0              |0            |
+----------+-------------------------------+------+--------------+-------------+------------+---------------+-------------+
```

There is only one check defined, and we received one sensor result, to which severity level 0 (valid result) was
assigned.



## Example with built-in regexes

### Sample email and phone numbers
Suppose you have the following table,

```
--8<-- "docs/check_reference/validity/regex_match_percent/tables/dqo-data.txt"
```



### Check configuration
We will check correct formatting on two column: `email_mixed` and `phone_numbers_mixed`, with predefined regexes
`phoneNumber` and `email` respectively.

```yaml linenums="1"
--8<-- "docs/check_reference/validity/regex_match_percent/yamls/named_regex.yml"
```

1. Here is a check configuration for phone number validation using `named_regex = phoneNumber`.
2. Here is a check configuration for phone number validation using `named_regex = email`.


#### Sensor
Here are the rendered queries with [predefined regular expressions](#list-of-built-in-regexes).

For the following query the parameter to pass phone number format regex was defined in line 24

```SQL linenums="1" hl_lines="7"
{{ process_template_request(get_request("docs/check_reference/validity/regex_match_percent/requests/named_regex_phone.json")) }}
```
and for the email format regex in line 41.

```SQL linenums="1" hl_lines="7"
{{ process_template_request(get_request("docs/check_reference/validity/regex_match_percent/requests/named_regex_email.json")) }}
```


``` yaml
Finished executing a sensor for a check regex_match_percent on the table dqo_ai_test_data.test_data_regex_sensor_179306422851143075 using a sensor definition column/validity/regex_match_percent, sensor result count: 1

Results returned by the sensor: #(1)
+------------+-----------+
|actual_value|time_period|
+------------+-----------+
|60.0        |2022-05-10 |
+------------+-----------+

Results returned by the sensor: #(2)
+------------+-----------+
|actual_value|time_period|
+------------+-----------+
|60.0        |2022-05-10 |
+------------+-----------+
```

1. Result for phone number
2. Result for email.

#### Rule
To evaluate check results, we have to define rules for both checks. They are defined in lines 25-32 and 42-49 in 
the YAML configuration.

```yaml 
rules:
  min_count:
    low:
      min_value: 90.0
    medium:
      min_value: 70.0
    high:
      min_value: 50.0
```

The `min_count` rule configuration says that if the `actual_value >= 90.0` then the result is valid,
if `90.0 > actual_value >= 70.0` then severity is 1 (low), if `70.0 > actual_value >= 50.0` then severity is 2 (medium)
if `50.0 > actual_value` then severity is 3 (high).

``` yaml
Finished executing rules (thresholds) for a check regex_match_percent on the table dqo_ai_test_data.test_data_regex_sensor_179306422851143075, verified rules count: 1

Rule evaluation results: #(1)
+------------+--------------+----------------+-------------+------------+-------------------+---------------+--------+-------------------+----------------+-----------------------------------------+-------------------+-------------------+-------------------+-------------------+-----------------+-----------------------------------+------------------------+-----------+--------+-------------------+---------+----------------+------------------+---------------+
|actual_value|expected_value|time_period     |time_gradient|dimension_id|connection_hash    |connection_name|provider|table_hash         |schema_name     |table_name                               |column_hash        |column_name        |check_hash         |check_name         |quality_dimension|sensor_name                        |executed_at             |duration_ms|severity|rule_hash          |rule_name|high_lower_bound|medium_lower_bound|low_lower_bound|
+------------+--------------+----------------+-------------+------------+-------------------+---------------+--------+-------------------+----------------+-----------------------------------------+-------------------+-------------------+-------------------+-------------------+-----------------+-----------------------------------+------------------------+-----------+--------+-------------------+---------+----------------+------------------+---------------+
|60.0        |50.0          |2022-05-10T00:00|day          |0           |5914958588201149627|dqo-ai-testing |bigquery|4257768357366869193|dqo_ai_test_data|test_data_regex_sensor_179306422851143075|1236866524037266806|phone_numbers_mixed|5188358281981702970|regex_match_percent|validity         |column/validity/regex_match_percent|2022-05-10T13:54:38.032Z|3540       |2       |1391659706564390862|min_count|50.0            |70.0              |90.0           |
+------------+--------------+----------------+-------------+------------+-------------------+---------------+--------+-------------------+----------------+-----------------------------------------+-------------------+-------------------+-------------------+-------------------+-----------------+-----------------------------------+------------------------+-----------+--------+-------------------+---------+----------------+------------------+---------------+

Rule evaluation results: #(2)
+------------+--------------+----------------+-------------+------------+-------------------+---------------+--------+-------------------+----------------+-----------------------------------------+-------------------+-----------+-------------------+-------------------+-----------------+-----------------------------------+------------------------+-----------+--------+-------------------+---------+----------------+------------------+---------------+
|actual_value|expected_value|time_period     |time_gradient|dimension_id|connection_hash    |connection_name|provider|table_hash         |schema_name     |table_name                               |column_hash        |column_name|check_hash         |check_name         |quality_dimension|sensor_name                        |executed_at             |duration_ms|severity|rule_hash          |rule_name|high_lower_bound|medium_lower_bound|low_lower_bound|
+------------+--------------+----------------+-------------+------------+-------------------+---------------+--------+-------------------+----------------+-----------------------------------------+-------------------+-----------+-------------------+-------------------+-----------------+-----------------------------------+------------------------+-----------+--------+-------------------+---------+----------------+------------------+---------------+
|60.0        |50.0          |2022-05-10T00:00|day          |0           |5914958588201149627|dqo-ai-testing |bigquery|4257768357366869193|dqo_ai_test_data|test_data_regex_sensor_179306422851143075|8879968814725603742|email_mixed|1043048580860418850|regex_match_percent|validity         |column/validity/regex_match_percent|2022-05-10T13:54:42.046Z|1517       |2       |7881665242135922542|min_count|50.0            |70.0              |90.0           |
+------------+--------------+----------------+-------------+------------+-------------------+---------------+--------+-------------------+----------------+-----------------------------------------+-------------------+-----------+-------------------+-------------------+-----------------+-----------------------------------+------------------------+-----------+--------+-------------------+---------+----------------+------------------+---------------+
```

1. Rule evaluation for phone number
2. Rule evaluation for email.



[//]: # (### Check evaluation)

[//]: # (#### Query results)

[//]: # ()
[//]: # (Check execution details are displayed separately for each check, but here we will show them next to each other.)

[//]: # ()
[//]: # (Looking at the defined rule thresholds, we expect both results to be level 2 &#40;medium&#41; severity.)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (#### Rule evaluation)

[//]: # ()
[//]: # (Severity levels are assigned as we expected.)



#### Check summary

This connection contains more than one table, summary is displayed for all of them. Check evaluation summary for our
table is highlighted below.


``` yaml hl_lines="12"

Check evaluation summary per table:
+--------------+-------------------------------------------------------------------------------------------+------+-------+-------+------+--------+------+
|Connection    |Table                                                                                      |Checks|Sensor |Valid  |Alerts|Alerts  |Alerts|
|              |                                                                                           |      |results|results|(low) |(medium)|(high)|
+--------------+-------------------------------------------------------------------------------------------+------+-------+-------+------+--------+------+
|dqo-ai-testing|dqo_ai_test_data.continuous_days_date_and_string_formats_7998702180845642887               |0     |0      |0      |0     |0       |0     |
+--------------+-------------------------------------------------------------------------------------------+------+-------+-------+------+--------+------+
 ...
+--------------+-------------------------------------------------------------------------------------------+------+-------+-------+------+--------+------+
|dqo-ai-testing|dqo_ai_test_data.test_average_goog_8863335308813025204                                     |0     |0      |0      |0     |0       |0     |
+--------------+-------------------------------------------------------------------------------------------+------+-------+-------+------+--------+------+
|dqo-ai-testing|dqo_ai_test_data.test_data_regex_sensor_179306422851143075                                 |2     |2      |0      |0     |2       |0     |
+--------------+-------------------------------------------------------------------------------------------+------+-------+-------+------+--------+------+
|dqo-ai-testing|dqo_ai_test_data.test_data_timeliness_sensors_1287831065858823464                          |0     |0      |0      |0     |0       |0     |
+--------------+-------------------------------------------------------------------------------------------+------+-------+-------+------+--------+------+
 ...
+--------------+-------------------------------------------------------------------------------------------+------+-------+-------+------+--------+------+
|dqo-ai-testing|dqo_ai_test_data.test_data_time_series_1901211650245440619                                 |0     |0      |0      |0     |0       |0     |
+--------------+-------------------------------------------------------------------------------------------+------+-------+-------+------+--------+------+

```
___

## When to use
This check is most useful when you need to validate your data for correct formatting, for example email addresses are
allowed to contain only permitted symbols and expected to have only one `@` symbol. That is when you could use a 
`named_regex = email`, or provide your own regex with `custom_regex`

Let's have a look at [ready to run example](../../../examples/validity/regex_match_percent/regex_match_percent.md),
which is described [below](#bigquery-example-with-custom-regex).
___

## Used sensor

###[__Regex match percent__](../../../sensor_reference/validity/regex_match_percent/regex_match_percent.md)

#### Errors detected
Invalid records that do not follow a specified format. It could be wrong email formatting with ,
or anything that could be matched with regex.

#### Parameters
This checks has two optional parameters that configure sensor:

- `named_regex`: _str_ (optional, default = missing)
  <br/>predefined regex, regexes are listed in [here](#list-of-built-in-regexes)
- `custom_regex`: _str_ (optional, default = missing)
  <br/>used for defining custom regexes, provided as strings, e.g. `custom_regex="^[0-9 -]{11}$"`

!!! note "`named_regex` vs. `custom_regex`"
    When defining a check keep in mind that `named_regex` overrides `custom_regex`. Meaning that if you specify both
    `named_regex` and `custom_regex`, a query will run with `named_regex` only. So for example
    ```yaml
    ...
      checks:
        validity:
          regex_match_percent:
            parameters:
              named_regex: email
              custom_regex: "^22-[0-9]{8}$"
            rules:
              min_count:
    ...
    ```
    will result with
    ```SQL
    SELECT CASE
            WHEN COUNT(analyzed_table.`col`) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        analyzed_table.`unique_key`,
                        r'^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$'
                    ) IS TRUE THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table.`col`)
        END AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
    FROM `project`.`dataset`.`table` AS analyzed_table
    GROUP BY time_period
    ORDER BY time_period
    ```
    where rendered regex is `email`, found in [built-in regexes](#list-of-built-in-regexes).    


### List of built-in regexes

| Name          | Regex                                                    |
|---------------|----------------------------------------------------------|
| `email`       | '^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$' |
| `phoneNumber` | '^[pP]?[tT]?[nN]?[oO]?[0-9]{7,11}$'                      |

!!! Info
    Regex parameter is required in a certain sense. No regex provided will result with matching an empty string, so all
    the data will be valid, giving a score of 100%:


    === "BigQuery"

        ```SQL
        SELECT 100.0* SUM(
                CASE
                    WHEN REGEXP_CONTAINS(analyzed_table.`unique_key`, r'') IS TRUE THEN 1
                    ELSE 0
                END
            ) / COUNT(*) AS actual_value,
        FROM `bigquery-public-data`.`austin_311`.`311_service_requests` AS analyzed_table;


        +--------------+
        | actual_value |
        +--------------+
        |        100.0 |
        +--------------+
    
        ```

    === "Snowflake"

        ```SQL
        SELECT 100.0* SUM(
                CASE
                    WHEN REGEXP_LIKE(analyzed_table.`unique_key`, '') THEN 1
                    ELSE 0
                END
            ) / COUNT(*) AS actual_value,
        FROM `bigquery-public-data`.`austin_311`.`311_service_requests` AS analyzed_table;
        
        
        +--------------+
        | actual_value |
        +--------------+
        |        100.0 |
        +--------------+
    
        ```

    Errors do not show up if there is no regex passed, but this is a thing you should to avoid. 
___

## Accepted rules



###[__min_count__](../../../rule_reference/comparison/min_count.md)

**Errors detected**: a minimal percent of records matching certain format. 

**Parameters**:

If `actual_value >= low` then severity level is 0 - valid.

- `low`:
  <br/>rule threshold for a low severity (1) alert
    - `min_value`: _float_
      <br/>minimum accepted value for the `actual_value` returned by the sensor (inclusive)
- `medium`:
  <br/>rule threshold for a medium severity (2) alert
    - `min_value`: _float_
      <br/>minimum accepted value for the `actual_value` returned by the sensor (inclusive)
- `high`:
  <br/>rule threshold for a high severity (3) alert
    - `min_value`: _float_
      <br/>minimum accepted value for the `actual_value` returned by the sensor (inclusive)

### [__count_equals__](../../../rule_reference/comparison/count_equals.md)

**Errors detected**: a specified percent of records within error band match certain format.

**Parameters**:

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