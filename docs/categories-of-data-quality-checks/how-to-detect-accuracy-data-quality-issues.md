---
title: How to detect data accuracy issues
---
# How to detect data accuracy issues
Data accuracy checks in DQOps compare an aggregated value in a tested table to the same aggregated value in a reference table.

The accuracy checks in DQOps are configured in the `accuracy` category of data quality checks.

## How data accuracy checks work in DQOps
Data accuracy checks in DQOps run queries with aggregate expressions on two tables: the tested table and the reference table.
Both tables must be in the same database.

DQOps stores the measure captured from the tested table in the [`sensor_readout`](../reference/parquetfiles/sensor_readouts.md) table
in an `actual_value` column. The other measure captured from the reference table is stored in the `expected_value` column.

### Aggregate functions
DQOps uses the following list of aggregate functions in SQL queries used in
the [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md).

| SQL aggregate function | Where to measure it | Description                                                                                                                                                                                                             |
|------------------------|---------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `COUNT(*)`             | *table-level*       | The number of rows.                                                                                                                                                                                                     |
| `SUM(column)`          | *column-level*      | The aggregate sum of all values in a column.                                                                                                                                                                            |
| `MIN(column)`          | *column-level*      | The minimum value in a column.                                                                                                                                                                                          |
| `MAX(column)`          | *column-level*      | The maximum value in a column.                                                                                                                                                                                          |
| `AVG(column)`          | *column-level*      | The average (mean) value in a column, which is equal to `SUM(column) / COUNT(*)`.                                                                                                                                       |
| `COUNT(column)`        | *column-level*      | The count of not-null values in a column. Comparing the count of non-null values between up-stream and down-stream tables enables detection of data casting issues, when invalid values were changed to a *null* value. |


### Compared tables
Data accuracy checks in DQOps are designed to compare tables that can be updated in the same transaction.
In order to ensure that the comparison is accurate, DQOps runs two aggregate queries on both tables.

Data accuracy check that compares the number of rows in two tables will be demonstrated on two tables that are on the same data lineage. 
The analyzed table is a staging table, and the reference table is a table in the landing zone on the same database.

### SQL queries
DQOps must run the following query on the analyzed table.

```sql
SELECT
    COUNT(*) AS actual_value
FROM "your_postgresql_database"."staging"."staging_customers"" AS analyzed_table
```

A similar query must be run on the reference table in the landing zone.

```sql
SELECT
    COUNT(*)
FROM your_postgresql_database.landing.landing_customers AS referenced_table
```

DQOps merges both queries into a single query.
A merged query is executed in a single transaction, and a second round-trip to the database is avoided.

```sql
SELECT
    (SELECT
        COUNT(*)
    FROM your_postgresql_database.landing.landing_customers AS referenced_table
    ) AS expected_value,
    COUNT(*) AS actual_value
FROM "your_postgresql_database"."staging"."staging_customers"" AS analyzed_table
```

## Configuring data accuracy checks
The configuration of data accuracy checks requires setting two types of parameters.

- `referenced_table` parameter for the data quality sensor, it is a fully qualified name of the reference table

- `max_diff_percent` parameter(s) for the data quality rules, which are the alerting thresholds.

### Configuring in YAML
An example DQOps YAML file is shown below.

``` { .yaml linenums="1" hl_lines="8-16" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  monitoring_checks:
    daily:
      accuracy:
        daily_total_row_count_match_percent:
          parameters:
            referenced_table: your_postgresql_database.landing.landing_customers
          warning:
            max_diff_percent: 0.0
          error:
            max_diff_percent: 1.0
          fatal:
            max_diff_percent: 5.0
  columns: {}
```

### Data quality rule parameters
The data accuracy checks support raising data quality issues at [different severity levels](../dqo-concepts/definition-of-data-quality-checks/index.md#issue-severity-levels). 
The [rule parameter](../dqo-concepts/definition-of-data-quality-rules.md) used in the accuracy checks 
is the maximum allowed error percentage between the measures captured on the analyzed and the reference tables.

DQOps uses the following default configuration.

``` { .yaml linenums="1" hl_lines="8-16" }
          warning:
            max_diff_percent: 0.0
          error:
            max_diff_percent: 1.0
          fatal:
            max_diff_percent: 5.0
```

- A data quality issue at a `warning` severity level is raised when the error is > 0.0% (the measures differ even slightly)

- A data quality issue at an `error` severity level is raised when the error is > 1.0%

- A data quality issue at a `fatal` severity level is raised when the error is > 5.0%


## Data accuracy vs comparison checks
DQOps also supports another type of data accuracy check, designed for comparing tables across data sources.

The table comparison checks are defined in a [`comparison`](how-to-reconcile-data-and-detect-differences.md) category. A table comparison also requires additional configuration. 
Please read the manual for [comparing tables between data sources](../working-with-dqo/compare-tables-between-data-sources.md) in DQOps to learn more.
Table comparison checks also support comparing the tables using an additional *GROUP BY* clause, to compare groups of rows.

The following table describes the differences between the data quality checks in the `accuracy` and the `comparison` categories.

| Category      | Run in a single transaction | Compare data between data sources | Compare with grouping (*GROUP BY*) | 
|---------------|:---------------------------:|:---------------------------------:|:----------------------------------:|
| `accuracy`    |    :material-check-bold:    |                                   |                                    |
| `comparison`  |                             |      :material-check-bold:        |       :material-check-bold:        |


## List of accuracy checks at a table level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*total_row_count_match_percent*](../checks/table/accuracy/total-row-count-match-percent.md)|[Accuracy](../dqo-concepts/data-quality-dimensions.md#data-accuracy)|A table-level check that compares the row count of the current (tested) table with the row count of another table that is referenced. This check ensures that the difference between the row counts is below the maximum accepted percentage of difference. This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.|:material-check-bold:|


**Reference and samples**

The full list of all data quality checks in this category is located in the [table/accuracy](../checks/table/accuracy/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## List of accuracy checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*total_sum_match_percent*](../checks/column/accuracy/total-sum-match-percent.md)|[Accuracy](../dqo-concepts/data-quality-dimensions.md#data-accuracy)|A column-level check that ensures that the difference between the sum of all values in the tested column and the sum of values in another column in a referenced table is below a maximum accepted percentage of difference. This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.|:material-check-bold:|
|[*total_min_match_percent*](../checks/column/accuracy/total-min-match-percent.md)|[Accuracy](../dqo-concepts/data-quality-dimensions.md#data-accuracy)|A column-level check that ensures that the difference between the minimum value in the tested column and the minimum value in another column in a referenced table is below a maximum accepted percentage of difference. This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.|:material-check-bold:|
|[*total_max_match_percent*](../checks/column/accuracy/total-max-match-percent.md)|[Accuracy](../dqo-concepts/data-quality-dimensions.md#data-accuracy)|A column-level check that ensures that the difference between the maximum value in the tested column and the maximum value in another column in a referenced table is below a maximum accepted percentage of difference. This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.|:material-check-bold:|
|[*total_average_match_percent*](../checks/column/accuracy/total-average-match-percent.md)|[Accuracy](../dqo-concepts/data-quality-dimensions.md#data-accuracy)|A column-level check that ensures that the difference between the average value in the tested column and the average value of another column in the referenced table is below the maximum accepted percentage of difference. This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.|:material-check-bold:|
|[*total_not_null_count_match_percent*](../checks/column/accuracy/total-not-null-count-match-percent.md)|[Accuracy](../dqo-concepts/data-quality-dimensions.md#data-accuracy)|A column-level check that ensures that the difference between the count of null values in the tested column and the count of null values in another column in a referenced table is below a maximum accepted percentage of difference. This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.|:material-check-bold:|


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/accuracy](../checks/column/accuracy/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
