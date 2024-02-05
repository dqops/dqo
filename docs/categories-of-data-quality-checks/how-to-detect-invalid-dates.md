# Detecting invalid dates
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

![Example of a date in the future in a datetime column](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/datetime-column-statistics-date-in-the-future-min.png){ loading=lazy }

### Detecting future dates in UI
The [*date_values_in_future_percent*](../checks/column/datetime/date-values-in-future-percent.md) data quality check finds dates in the future
and measures the percentage of rows having future dates.
The *max_percent* parameter controls the maximum accepted percentage of invalid rows.

![Date in the future invalid date example](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/date-in-the-future-percent-check-editor-min.png){ loading=lazy }

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
The [*datetime_value_in_range_date_percent*](../checks/column/datetime/datetime-value-in-range-date-percent.md) asserts that all date values are within a reasonable range. 
This check detects rows with corrupted or fake dates, such as 1900-01-01 or 2099-12-31.

### Configure date in range check in UI
The [*datetime_value_in_range_date_percent*](../checks/column/datetime/datetime-value-in-range-date-percent.md) checks needs 

## Detecting datetime issues
How to detect datetime data quality issues.

## List of datetime checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*date_values_in_future_percent*](../checks/column/datetime/date-values-in-future-percent.md)|Validity|A column-level check that ensures that there are no more than a set percentage of date values in the future in a monitored column.|:material-check-bold:|
|[*date_in_range_percent*](../checks/column/datetime/date-in-range-percent.md)|Validity|A column-level check that ensures that the dates are within a range of reasonable values. Measures the percentage of valid|:material-check-bold:|
|[*date_match_format_percent*](../checks/column/datetime/date-match-format-percent.md)|Validity|A column-level check that validates the values in text columns to ensure that they are valid dates, matching one of predefined date formats. It measures the percentage of rows that match the expected date format in a column and raises an issue if not enough rows match the format. The default value 100.0 (percent) verifies that all values match a given date format.|:material-check-bold:|


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/datetime](../checks/column/datetime/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
