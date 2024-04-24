---
title: How to detect data type changes in text columns
---
# How to detect data type changes in text columns
Read this guide to learn how DQOps detect the effective data type in text columns. Data type detection verifies that values are castable to a given data type.

The data quality checks that detect data types in text columns are configured in the `datatype` category in DQOps.

## Data type detection
The data type detection checks in DQOps are designed to analyze text columns in raw tables
before the data pipeline or an ETL process converts the values to a 
target data type, such as an integer, float, date, or timestamp.

Data type detection prevents issues in the data pipelines.

- The data loading process can break on the first value that cannot be converted to the target data types. 
  It often happens when a CAST SQL function is used to convert text values to a target data type. 
  The default behavior of most database engines is to stop processing an INSERT or UPDATE command, returning an error code.
  If that happens and the error is not handled correctly, the batch is not loaded.

- The data transformation logic can skip rows containing values that were not transformed (converted). 
  It leads to data completeness issues and missing rows.

- A data transformation logic that uses safe casting will convert values in a wrong format to a null value,
  leading to incomplete values in the target column.

!!! tip "Physical data type detection"

    The data type detection checks in the `datatype` category analyze values in text columns (string, char, varchar, nvarchar, ...). 
    They are sensing the desired data type based on the values in the column.

    If you want to detect that the physical data type of a column has changed, use the
    [table schema drift checks](how-to-detect-table-schema-changes.md) in the `schema` category.    


### Mixed data type issues
The following example shows an issue related to storing mixed data types in a text column.
The tested column is *street_name*. 
We assume that all values are integer values, allowing us to use an *INTEGER* data type instead of a *STRING* type. 
The column statistics show that the most common street numbers are integers, 
but DQOps detected that the column contains mixed data types.

![Mixed data types detected in column that has mostly numeric values](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/mixed-data-types-example-with-numeric-values-min.png){ loading=lazy; width="1200px" }

We can change the table filter to return only rows containing non-numeric values
that failed to be converted to an *INTEGER* data type.

![Configure table filter for data profiling](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/setting-table-filter-to-rerun-profiling-min.png){ loading=lazy; width="1200px" }

The SQL filter that we used is shown below.

```sql
{alias}.street_number IS NOT NULL AND safe_cast({alias}.street_number AS INTEGER) IS NULL
```

After capturing statistics for the column again, the only column value samples that were captured were non-numeric values.

![Data profiling results showing non numeric values](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/data-profiling-result-not-numeric-values-min.png){ loading=lazy; width="300px" }

### Data sample
The following sample of the column values shows both integer and non-integer values.

| street_number |
|---------------|
| 1520          |
| 500           |
| 1500          |
| 600           |
| **915 1/2**   |


## Data type detections by DQOps
DQOps has two dedicated data quality checks for detecting data types in text columns. 
The SQL queries in these checks try to convert all values in a column to all supported data types. 
If all values are convertible to the same data type, DQOps returns the category code of that type.
Otherwise, DQOps returns a special data type category code that identifies a mixed data type.

### Detected data types
DQOps detects the following categories of data types.

| Data type category | Data type identifier<br/>(*actual_value*) | Description                                                                 | Sample values                                                      |
|--------------------|-------------------------------------------|-----------------------------------------------------------------------------|--------------------------------------------------------------------|
| integers           | 1                                         | Detect integer and long values                                              | 0, 1, -12, 12345                                                   |
| floats             | 2                                         | Detect numeric values, but also supports integer values                     | 1.23, -234.333, 1, 3444                                            |
| dates              | 3                                         | Dates without the time in ISO 8601 format and US format                     | 19/02/2020, 2020-04-01                                             |
| datetimes          | 4                                         | Date and time values containing both the date and time components           | 10/12/2020 0:00:01, 2020-12-10 0:00:01                             |
| timestamps         | 5                                         | *Datetime* extended by fraction of second or time zone                      | 2020-12-10T00:00:01.123456-07:00, 2020-12-10 00:00:01 GMT+01:00    |
| booleans           | 6                                         | Placeholders of a *true* and *false* values                                 | true, false, TRUE, FALSE, yes, no, YES, NO, y, n, Y, N, t, f, T, F |
| texts              | 7                                         | Text values that are not numbers, dates or boolean placeholders             | New York, Austin, TX                                               |
| mixed              | 8                                         | The values found in the column are mixed including values of multiple types | 1, 43.11, 2020-04-01, true, Austin                                 |


## Data type detection checks
DQOps has two data quality checks for data type detection. An assertion check that verifies
if all values in a column match a given data type.
And a data type change detection that detects if new rows contain values of a different data type.

## Assert data type check
The [*detected_datatype_in_text*](../checks/column/datatype/detected-datatype-in-text.md) data quality check
analyzes all values in a column and asserts that all values are of an expected data type.

### Configure detection check in UI
The [*detected_datatype_in_text*](../checks/column/datatype/detected-datatype-in-text.md) data quality check
is easy to activate. The parameter of the rule is the expected data type.

![Configure data type detection check in UI](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/assert-data-type-in-text-column-street-number-check-min.png){ loading=lazy; width="1200px" }

### Configure detection check in YAML
The [*detected_datatype_in_text*](../checks/column/datatype/detected-datatype-in-text.md)
check uses the *data type category* names listed in the table above.

``` { .yaml linenums="1" hl_lines="15" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    street_number:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          datatype:
            daily_detected_datatype_in_text:
              error:
                expected_datatype: integers
```

## Detect type changed check
The data type change detection check
[*detected_datatype_in_text_changed*](../checks/column/datatype/detected-datatype-in-text-changed.md) 
monitors changes to the data type, comparing the detected data type to the last known detected data type. 
DQOps uses the historical data quality results for change detection.

### Configure type change check in UI
The [*detected_datatype_in_text_changed*](../checks/column/datatype/detected-datatype-in-text-changed.md)
check uses parameterless data quality rules to select the severity level for reported issues.

![Detect data type changed in text column data quality check](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/detect-data-type-changed-check-min.png){ loading=lazy; width="1200px" }

The example above shows the first execution of the check when historical results are not yet present. 
Hence, the *expected_value* is missing. 
When DQOps reruns this check the next day, the *expected_value* will be the value of the *actual_value* from the previous day.

### Configure type change check in YAML
The [*detected_datatype_in_text_changed*](../checks/column/datatype/detected-datatype-in-text-changed.md)
check uses [parameterless rules](../dqo-concepts/configuring-data-quality-checks-and-rules.md#rules-without-parameters) 
to activate the desired [alerting severity level](../dqo-concepts/definition-of-data-quality-checks/index.md#issue-severity-levels).

``` { .yaml linenums="1" hl_lines="13-14" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    street_number:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          datatype:
            daily_detected_datatype_in_text_changed:
              error: {}
```

## Detecting data types across partitions
The data type detection checks also work on partitioned data.
A partitioned variant of the check is enabled in the [*Partitioned Checks*](../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)
section. DQOps will analyze rows for each daily or monthly partition.
By using a partitioned version of the data type detection checks, it is possible to detect data type drifts between partitions.

The following example shows that the last five daily partitions contained only integer values in the *street_number* column, 
and the detected data type has not changed day-to-day.

![Data type detection in daily partitions](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/data-type-detection-in-partitioned-data-min.png){ loading=lazy; width="1200px" }


## List of datatype checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*detected_datatype_in_text*](../checks/column/datatype/detected-datatype-in-text.md)|[Consistency](../dqo-concepts/data-quality-dimensions.md#data-consistency)|A column-level check that scans all values in a text column and detects the data type of all values in a monitored column. The actual_value returned from the sensor can be one of seven codes: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 6 - booleans, 7 - strings, 8 - mixed data types. The check compares the data type detected in all non-null columns to an expected data type. The rule compares the value using equals and requires values in the range 1..8, which are the codes of detected data types.|:material-check-bold:|
|[*detected_datatype_in_text_changed*](../checks/column/datatype/detected-datatype-in-text-changed.md)|[Consistency](../dqo-concepts/data-quality-dimensions.md#data-consistency)|A column-level check that scans all values in a text column, finds the right data type and detects when the desired data type changes. The actual_value returned from the sensor can be one of seven codes: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 6 - booleans, 7 - strings, 8 - mixed data types. The check compares the data type detected during the current run to the last known data type detected during a previous run. For daily monitoring checks, it compares the value to yesterday&#x27;s value (or an earlier date). For partitioned checks, it compares the current data type to the data type in the previous daily or monthly partition. The last partition with data is used for comparison.|:material-check-bold:|


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/datatype](../checks/column/datatype/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
