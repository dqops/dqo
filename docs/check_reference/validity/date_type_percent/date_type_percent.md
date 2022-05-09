# Date type percent
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

[__Date type percent__](../../../sensor_reference/validity/date_type_percent/date_type_percent.md)
___
## Accepted rules
[__Min count__](../../../rule_reference/comparison/min_count.md)

[__Count equals__](../../../rule_reference/comparison/count_equals.md)

___

## Parameters
This checks has two optional parameters that configure date format to parse:

- `named_date_format`: _str_
    <br/>predefined date format used for parsing string, formats are listed in [_BuiltInDateFormats_](date_type_percent.md#list-of-built-in-date-formats)
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
of the usage, the whole example is ready to run [here](../../examples/validity/date_type_percent.md).

Let's have a look at the first ten rows from the table used in the example -
`bigquery-public-data.labeled_patents.extracted_data`




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