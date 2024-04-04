---
title: How to detect table schema changes
---
# How to detect table schema changes
Read this guide to learn how DQOps detects table schema changes, such as missing columns, data type changes, or reordering columns.

The table schema change detection checks are configured in the `schema` category in DQOps.

## What is a schema change
The source tables can be modified in source systems for many reasons. 
A new version of the application is installed. A fact table is expanded to include new dimensions.
Or, the previous column data type couldn't store some data.
An unexpected table schema change affects data pipelines and can also affect downstream systems.

### Types of schema changes
DQOps can detect the following types of table schema changes.

- The count of columns in a table has changed. 
  This change is caused by adding or removing a column, 
  but monitoring the column count will not detect that a column was renamed, which does not change the column count.

- A known column is missing.

- A list of columns has changed. This type of check also detects that a column was renamed.

- The list and order of columns has changed. This check detects that the columns were reordered. 
  Column reordering happens when new Parquet or CSV files are built using a different transformation logic.

- The column data types changed. DQOps can detect that the data type has changed;
  for example, an integer column was changed to a float column,
  or a *varchar(50)* column was extended to *varchar(100)* to fit longer values.

### Schema checks in DQOps
DQOps uses two types of table schema detection checks.

- Table-level schema checks are configured for a whole table. 
  They identify any schema changes but cannot recognize a modified column.

- Column-level schema checks are configured on a column that was known when importing the table metadata.
  DQOps can detect missing columns or column data type changes, identifying the changed column.


## Table-level schema changes
DQOps supports the following schema change detection checks configured on a table level.

- The [*column_count*](../checks/table/schema/column-count.md) check counts the columns in the table and compares it to an expected number of columns

- The [*column_count_changed*](../checks/table/schema/column-count-changed.md) check captures the count of columns in a table and compares it to the last known column count.
  It detects when the column count has changed.

- The [*column_list_changed*](../checks/table/schema/column-list-changed.md) check detects when any column is added, removed, or renamed.

- The [*column_list_or_order_changed*](../checks/table/schema/column-list-or-order-changed.md) check detects when any column is added, removed, or renamed. 
  It also detects when the columns change their position in the table (columns are reordered).

- The [*column_types_changed*](../checks/table/schema/column-types-changed.md) check detects when any column is added, removed, or renamed.
  Or when the data type, nullability status, or the column's length are changed.

### Configuring table-level checks in UI
The only schema change detection check that has a parameter is the [*column_count*](../checks/table/schema/column-count.md) check. 
It requires a value of the **expected_value** parameter, the desired column count.

The remaining schema change detection checks are configured by enabling the check at an expected [data quality issue severity level](../dqo-concepts/definition-of-data-quality-checks/index.md#issue-severity-levels).
The change detection checks should be configured as
[daily monitoring checks](../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md#daily-monitoring-checks)
to detect day-to-day changes.

![Enabled data quality checks for table schema drift detection](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/table-schema-drift-monitoring-enabled-without-changes-min.png){ loading=lazy }

### Example of a schema change
In the meantime, a new column was added to the table using an SQL statement shown below.

``` { .sql }
ALTER TABLE dqops-testing.kaggle_covidlive.covid_live
ADD COLUMN int64_field_31 INTEGER;
```

The following screenshot shows the data quality check editor on the next day.

![Data quality checks that detected table schema changes](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/table-schema-drift-monitoring-detected-changes-min.png){ loading=lazy }

DQOps detected new data quality issues for all types of schema changes because adding a new column is detected by all table-level checks.
You can also review the [example of detecting table schema changes](../examples/schema/detect-table-schema-changes.md),
which shows how to detect other, more subtle changes to the table schema.

### Configuring table-level checks in YAML
The configuration of table-level schema change detection checks in a DQOPs YAML file is shown below.

``` { .yaml linenums="1" hl_lines="8-18"}
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  monitoring_checks:
    daily:
      schema:
        daily_column_count:
          error:
            expected_value: 31
        daily_column_count_changed:
          error: {}
        daily_column_list_changed:
          error: {}
        daily_column_list_or_order_changed:
          error: {}
        daily_column_types_changed:
          error: {}
```


## Column-level schema changes
DQOps supports the following schema change detection checks configured on a column level.

- The [*column_exists*](../checks/column/schema/column-exists.md) check detects missing columns. 
  It verifies that the column is still present in the table.

- The [*column_type_changed*](../checks/column/schema/column-type-changed.md) check detects if the column data type, nullability status, 
  or the column's length, precision, or scale has changed.


### Configuring column-level checks in UI
The column-level schema change detection checks are configured using the [data quality check editor](../dqo-concepts/dqops-user-interface-overview.md#check-editor)
on a column level.

![Configuring column schema change detection data quality checks](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/column-schema-checks-enabled-in-editor-min.png){ loading=lazy }


### Configuring column-level checks in YAML
The column's schema change detection checks are configured for each column.

``` { .yaml linenums="1" hl_lines="10-13" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    Country:
      monitoring_checks:
        daily:
          schema:
            daily_column_exists:
              warning: {}
            daily_column_type_changed:
              warning: {}
```

## Reviewing schema changes on dashboards
DQOps supports several data quality dashboards that are showing recent table schema changes.

### Recent table schema changes
The summary dashboard shows all recent table schema changes of any type.

![Data quality dashboard showing recent table schema changes](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/summary-of-table-schema-changes-dashboard-min.png){ loading=lazy }

### Detecting column count changes
The column count changes dashboard shows only the schema changes detected by the [*column_count_changed*](../checks/table/schema/column-count-changed.md) check.
DQOps has similar [data quality dashboards](../dqo-concepts/types-of-data-quality-dashboards.md)
in the schema folder for different schema changes.

![Data quality dashboard showing table schema changes for a count of columns](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/column-count-changed-dashboard-min.png){ loading=lazy }


## Use cases
| **Name of the example**                                                          | **Description**                                                                                            |
|:---------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------------------------------|
| [Detect table schema changes](../examples/schema/detect-table-schema-changes.md) | This example shows how to detect schema changes on the table using several schema change detection checks. |


## List of schema checks at a table level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*column_count*](../checks/table/schema/column-count.md)|[Completeness](../dqo-concepts/data-quality-dimensions.md#data-completeness)|A table-level check that retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to an expected number of columns.|:material-check-bold:|
|[*column_count_changed*](../checks/table/schema/column-count-changed.md)|[Consistency](../dqo-concepts/data-quality-dimensions.md#data-consistency)|A table-level check that detects if the number of columns in the table has changed since the last time the check (checkpoint) was run. This check retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to the last known number of columns that was captured and is stored in the data quality check results database.|:material-check-bold:|
|[*column_list_changed*](../checks/table/schema/column-list-changed.md)|[Consistency](../dqo-concepts/data-quality-dimensions.md#data-consistency)|A table-level check that detects if the list of columns has changed since the last time the check was run. This check will retrieve the metadata of a tested table and calculate a hash of the column names. The hash will not depend on the order of columns, only on the column names. A data quality issue will be detected if new columns were added or columns that existed during the previous test were dropped.| |
|[*column_list_or_order_changed*](../checks/table/schema/column-list-or-order-changed.md)|[Consistency](../dqo-concepts/data-quality-dimensions.md#data-consistency)|A table-level check that detects if the list of columns and the order of columns have changed since the last time the check was run. This check will retrieve the metadata of a tested table and calculate a hash of the column names. The hash will depend on the order of columns. A data quality issue will be detected if new columns were added, columns that existed during the previous test were dropped or the columns were reordered.| |
|[*column_types_changed*](../checks/table/schema/column-types-changed.md)|[Consistency](../dqo-concepts/data-quality-dimensions.md#data-consistency)|A table-level check that detects if the column names or column types have changed since the last time the check was run. This check calculates a hash of the column names and all the components of the column&#x27;s data type: the data type name, length, scale, precision and nullability. A data quality issue will be detected if the hash of the column data types has changed. This check does not depend on the order of columns, the columns can be reordered as long as all columns are still present and the data types match since the last time they were tested.| |


**Reference and samples**

The full list of all data quality checks in this category is located in the [table/schema](../checks/table/schema/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## List of schema checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*column_exists*](../checks/column/schema/column-exists.md)|[Completeness](../dqo-concepts/data-quality-dimensions.md#data-completeness)|A column-level check that reads the metadata of the monitored table and verifies if the column still exists in the data source. The data quality sensor returns a value of 1.0 when the column is found or 0.0 when the column is not found.|:material-check-bold:|
|[*column_type_changed*](../checks/column/schema/column-type-changed.md)|[Consistency](../dqo-concepts/data-quality-dimensions.md#data-consistency)|A column-level check that detects if the data type of the column has changed since the last retrieval. This check calculates the hash of all the components of the column&#x27;s data type: the data type name, length, scale, precision and nullability. A data quality issue will be detected if the hash of the column&#x27;s data types has changed.|:material-check-bold:|


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/schema](../checks/column/schema/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
