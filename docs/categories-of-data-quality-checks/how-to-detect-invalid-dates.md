---
title: How to detect invalid dates
---
# How to detect invalid dates
Read this guide to learn how to detect invalid dates in data, such as dates in the future or out of reasonable range and dates in a wrong format.

The data quality checks that detect invalid dates are configured in the `datetime` category in DQOps.

## Types of invalid dates
The most common reasons why invalid dates enter into the data platform are:

- A human error occurs when entering the date into an application manually.  
  A user made a small mistake, typing the wrong day or month. Many manual errors lead to entering dates in the future.

- The data pipeline was converting text values to dates, but the dates were written in an invalid format. 
  For example, the transformation logic expected dates in the *MM/DD/YYYY* format,
  but dates were written in a reversed *DD/MM/YYYY* format.

- The developers used a distant date in the future or in the past to identify special or missing dates. 
  The most common special dates are *1900-01-01* and *2099-12-31*.

Invalid dates in the dataset lead to wrong results in dashboards that are grouping values by date.


## Dates in the future
Future dates are caused mainly by manual data entry. 
The following example shows profiling results of a *delivered_at* column for an e-commerce platform.
The most recent (maximum) date is after the current time when the table was profiled.
It indicates that the delivery date is in the future.

![Example of a date in the future in a datetime column revealed by profiling](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/datetime-column-statistics-date-in-the-future-min.png){ loading=lazy }

### Detecting future dates in UI
The [*date_values_in_future_percent*](../checks/column/datetime/date-values-in-future-percent.md) data quality check finds dates in the future
and measures the percentage of rows having future dates.
The *max_percent* parameter controls the maximum accepted percentage of invalid rows.

![Date in the future invalid date example](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/date-in-future-found-check-editor-min.png){ loading=lazy }

### Detecting future dates in YAML
The [*date_values_in_future_percent*](../checks/column/datetime/date-values-in-future-percent.md) check is easy to enable. 
The following example shows the configuration as a 
[daily monitoring check](../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md#daily-monitoring-checks).

``` { .yaml linenums="1" hl_lines="13-15" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    delivered_at:
      type_snapshot:
        column_type: TIMESTAMP
        nullable: true
      monitoring_checks:
        daily:
          datetime:
            daily_date_values_in_future_percent:
              error:
                max_percent: 0.0
```

!!! tip "How to configure a tolerance time window"

    The [*date_values_in_future_percent*](../checks/column/datetime/date-values-in-future-percent.md)  check detects future dates, 
    rejecting all dates even a few seconds after the current time. 
    You can customize the [*date_values_in_future_percent*](../checks/column/datetime/date-values-in-future-percent.md)  check by 
    [creating a custom data quality check](../working-with-dqo/creating-custom-data-quality-checks.md).
    It can use a different calculation formula that rejects dates at least seven days ahead of now.
    
    An alternative method is to define a [calculated column](../dqo-concepts/configuring-table-metadata.md#calculated-columns)
    that subtracts a few days from the monitored column.


## Dates out of range
The [*date_in_range_percent*](../checks/column/datetime/date-in-range-percent.md) asserts that all date values are within a reasonable range. 
This check detects rows with corrupted or fake dates, such as 1900-01-01 or 2099-12-31.

### Configure date in range check in UI
The [*date_in_range_percent*](../checks/column/datetime/date-in-range-percent.md) check is configured with three parameters.

- **min_date** parameter that is the earliest accepted date inclusive. 
  The default value is *1900-01-02* to exclude a common placeholder date *1900-01-01*.

- **max_date** parameter that is the latest accepted date inclusive.
  The default value is *2099-12-30* to exclude a common placeholder date *2099-12-31*.

- **min_percent** parameter that is the minimum percentage of valid dates. The default value is *100%*. 
  DQOps measures only the percentage of not null date values. 
  If only two rows store not null values and one value is out of range, the calculated percentage is 50% independent of the table size.

![Date in range percent data quality check with valid date ranges](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/date-in-range-percent-check-min.png){ loading=lazy }

### Configure date in range check in YAML
The [*date_in_range_percent*](../checks/column/datetime/date-in-range-percent.md) check requires the configuration of the parameters described before.

``` { .yaml linenums="1" hl_lines="13-18" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    delivered_at:
      type_snapshot:
        column_type: TIMESTAMP
        nullable: true
      monitoring_checks:
        daily:
          datetime:
            daily_date_in_range_percent:
              parameters:
                min_date: 1900-01-02
                max_date: 2099-12-30
              error:
                min_percent: 100.0
```


## Text column match date format
Text columns in the landing zone tables are often loaded as raw tables with only text columns. 
The data pipeline parses text values to a desired data type. Parsing integer or numeric values is simple, 
not generating confusion about the data format.
But date values must match one of the popular formats. 
We cannot let the transformation engine detect the date format because the date detection logic cannot distinguish
"*01/02/2024*" between *February 1st, 2024*, and *January 2nd, 2024*, leading to loading invalid dates.
We can prevent these errors by asserting that all values match the same expected date format.

The following sample shows an extract from a text column that contains dates in mixed formats. The last row is different.

| Text column that should be a date |
|-----------------------------------|
| 2024-02-01                        |
| 2024-02-02                        |
| 2024-02-03                        |
| 2024-02-04                        |
| **02/05/2024**                    |

### Asserting date formats in UI
We will use a sample column that contains dates in the ISO 8601 format. The profiling result shows some samples from the column.

![Text column profiling result with valid ISO 8601 date](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/date-column-profile-statistics-min.png){ loading=lazy; width="300px" }

The [*text_match_date_format_percent*](../checks/column/datetime/text-match-date-format-percent.md) 
examines text columns (char, varchar, string, etc.), trying to match all column values to an expected date format.
The parameters of the [*text_match_date_format_percent*](../checks/column/datetime/text-match-date-format-percent.md) check are:

- **date_format** parameter accepts one of the date formats supported by DQOps.

- **min_percent** is the rule parameter to decide how many percent of non-null column values must match the expected format.


![Text column match an expected date format data quality check](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/text-match-date-format-percent-check-min.png){ loading=lazy }

### Asserting date formats in YAML
The configuration of the [*text_match_date_format_percent*](../checks/column/datetime/text-match-date-format-percent.md) 
check uses a list of date formats supported by DQOps.

| Date format constant | Sample date |
|----------------------|-------------|
| `MM/DD/YYYY`         | 12/31/2023  |
| `YYYY-MM-DD`         | 2023-12-31  |
| `DD/MM/YYYY`         | 31/12/2023  |
| `DD-MM-YYYY`         | 31-12-2024  |
| `DD.MM.YYYY`         | 31.12.2024  |

The configuration of the [*text_match_date_format_percent*](../checks/column/datetime/text-match-date-format-percent.md)
check in YAML is shown below.

``` { .yaml linenums="1" hl_lines="10-14" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    delivered_at_text:
      monitoring_checks:
        daily:
          datetime:
            daily_text_match_date_format_percent:
              parameters:
                date_format: YYYY-MM-DD
              error:
                min_percent: 100.0 
```


## List of datetime checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*date_values_in_future_percent*](../checks/column/datetime/date-values-in-future-percent.md)|Validity|Detects dates in the future in date, datetime and timestamp columns. Measures a percentage of dates in the future. Raises a data quality issue when too many future dates are found.|:material-check-bold:|
|[*date_in_range_percent*](../checks/column/datetime/date-in-range-percent.md)|Validity|Verifies that the dates in date, datetime, or timestamp columns are within a reasonable range of dates. The default configuration detects fake dates such as 1900-01-01 and 2099-12-31. Measures the percentage of valid dates and raises a data quality issue when too many dates are found.|:material-check-bold:|
|[*text_match_date_format_percent*](../checks/column/datetime/text-match-date-format-percent.md)|Validity|Verifies that the values in text columns match one of the predefined date formats, such as an ISO 8601 date. Measures the percentage of valid date strings and raises a data quality issue when too many invalid date strings are found.|:material-check-bold:|


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/datetime](../checks/column/datetime/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
