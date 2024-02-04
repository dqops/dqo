# Detecting data type changes in text columns
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


### Detected data types
DQOps detects the following categories of data types.

| Data type category | Data type identifier<br/>(*actual_value*) | Description                                                                 | Sample values                                                      |
|--------------------|-------------------------------------------|-----------------------------------------------------------------------------|--------------------------------------------------------------------|
| integers           | 1                                         | Detect integer and long values                                              | 0, 1, -12, 12345                                                   |
| floats             | 2                                         | Detect numeric values, but also supports integer values                     | 1.23, -234.333, 1, 3444                                            |
| dates              | 3                                         | Dates without the time in ISO 8601 format and US format                     | 19/02/2020, 2020-04-01                                             |
| datetimes          | 4                                         | Date and time values containing both the date and time components           | 10/12/2020 0:00:01, 2020-12-10 0:00:01                             | 
| booleans           | 6                                         | Placeholders of a *true* and *false* values                                 | true, false, TRUE, FALSE, yes, no, YES, NO, y, n, Y, N, t, f, T, F |
| texts              | 7                                         | Text values that are not numbers, dates or boolean placeholders             | New York, Austin, TX                                               |
| mixed              | 8                                         | The values found in the column are mixed including values of multiple types | 1, 43.11, 2020-04-01, true, Austin                                 |


## List of datatype checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*detected_datatype_in_text*](../checks/column/datatype/detected-datatype-in-text.md)|Consistency|A table-level check that scans all values in a string column and detects the data type of all values in a monitored column. The actual_value returned from the sensor can be one of seven codes: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 6 - booleans, 7 - strings, 8 - mixed data types. The check compares the data type detected in all non-null columns to an expected data type. The rule compares the value using equals and requires values in the range 1..8, which are the codes of detected data types.|:material-check-bold:|
|[*detected_datatype_in_text_changed*](../checks/column/datatype/detected-datatype-in-text-changed.md)|Consistency|A table-level check that scans all values in a string column and detects the data type of all values in a monitored column. The actual_value returned from the sensor can be one of seven codes: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 6 - booleans, 7 - strings, 8 - mixed data types. The check compares the data type detected during the current run to the last known data type detected during a previous run. For daily monitoring checks, it will compare the value to yesterday&#x27;s value (or an earlier date). For partitioned checks, it will compare the current data type to the data type in the previous daily or monthly partition. The last partition with data is used for comparison.|:material-check-bold:|


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/datatype](../checks/column/datatype/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
