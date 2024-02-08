# Detecting empty and incomplete columns
Read this guide to learn how to detect empty columns or incomplete columns containing too many null values in a dataset.

The data quality checks that detect empty and incomplete columns are configured in the `nulls` category in DQOps.

## Issues with empty and incomplete columns
DQOps categorizes data quality issues with empty and incomplete columns in the *Completeness* data quality dimension.

### Types of completeness issues
We identify three types of data completeness issues related to the null values in a dataset.

- Incomplete columns that contain a few null values.

- Probably incomplete columns that are expected to contain some null values, 
  but the measured percentage of null values is higher than anticipated.

- Empty columns that have no values. Empty columns were most likely defined to store some information that was never provided.

- Inconsistently incomplete columns whose percentage of null values changes over time. 
  DQOps detects these types of issues using time series anomaly detection.

### The causes of completeness issues
Null values appear in the dataset for several reasons.

- A human error during a data entry leaves a required field empty.

- The field is optional, and the process followed by users does not require entering the information.

- A bug in the data entry form skipped the validation of required fields.

- A bug in the data transformation or mapping code did not pass the value of a required field downstream.

- The field was required, but the restriction was lifted later in time

### Problems caused by incomplete columns
Null values can cause problems in several data analytics and data transformation areas.

- Dashboards will show lower numbers because null values are not included in calculations.

- Some SQL queries that use filters may fail to return any records when a column has any null values. 
  It is caused by Three-Valued Logic used by SQL language to compare values to a null value. 
  For example, an SQL filter: `WHERE product_id NOT IN (SELECT nullable_product_id FROM table_with_nulls)`
  will stop working due to a comparison to a null value.
  *This query type is a particular case that forces any SQL-compliant database engine to return no rows.* 
  These types of data quality issues are hard to find, especially when a Business Intelligence engine generates the queries.


## Incomplete columns
We say that a column is incomplete when it contains some null values.
The following example shows the data profiling statistics of a column with over 16% of null values.

![Column with null values profiling statistics](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/null-count-profiling-statistics-min.png){ loading=lazy }

### Detect incomplete columns with UI
DQOps uses a [*nulls_count*](../checks/column/nulls/nulls-count.md) data quality check to count null values. 
It raises a data quality issue when any null values are found.

The default value of the *max_count* parameter is 0, which asserts that no null values are present.

![Detect incomplete columns with some null values using a data quality check](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/incomplete-column-with-some-nulls-check-in-editor-min.png){ loading=lazy }

### Detect incomplete columns in YAML
The following example shows a [*nulls_count*](../checks/column/nulls/nulls-count.md) check configured in a YAML file.

``` { .yaml linenums="1" hl_lines="13-15" }
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
          nulls:
            daily_nulls_count:
              error:
                max_count: 0
```

## Partially incomplete columns
A partially incomplete column is nullable, and it is acceptable to store some null values in the column, but the column contains too many null values.

We can define a minimum completeness level in many ways:

- A maximum number of rows containing a null value in the column.
  We can use the [*nulls_count*](../checks/column/nulls/nulls-count.md) data quality check with a higher value of the *max_count* parameter.

- A maximum percentage of null values.
  DQOps has a [*nulls_percent*](../checks/column/nulls/nulls-percent.md) data quality check for that purpose.

- A minimum number of rows that must have a non-null value.
  DQOps has a dedicated data quality check [*not_nulls_count*](../checks/column/nulls/not-nulls-count.md) that also detects empty columns.

- Or a minimum percentage of non-null values in a column. 
  The data quality check [*not_nulls_percent*](../checks/column/nulls/not-nulls-percent.md) supports this case.

### Detect in UI
The [*nulls_percent*](../checks/column/nulls/nulls-percent.md) check measures the percentage of null values in a column.
DQOps supports configuring multiple alert severity levels by using a different threshold. 

The following example raises a warning severity issue when the percent of the null value is above 16%.
An issue at an error severity level is raised when the percent of null values exceeds 20%.

![Detect incomplete columns with a minimum accepted percentage of nulls](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/detect-incomplete-columns-with-accepted-percent-of-nulls-min.png){ loading=lazy }

### Detect in YAML
The configuration of the [*nulls_percent*](../checks/column/nulls/nulls-percent.md) check is straightforward in YAML.

``` { .yaml linenums="1" hl_lines="13-17" }
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
          nulls:
            daily_nulls_percent:
              warning:
                max_percent: 16.0
              error:
                max_percent: 20.0 
```

## Detect empty columns
We say a column is empty when it has no values and all rows contain only nulls.

DQOps detects empty columns using the [*not_nulls_count*](../checks/column/nulls/not-nulls-count.md)
data quality check with the default configuration. 

The [*not_nulls_count*](../checks/column/nulls/not-nulls-count.md) check has a rule parameter *min_count* that verifies a minimum number of rows containing a value. 
The default value is 1 row, which finds empty columns not passing that limit.

### Detect empty columns in UI
The [*not_nulls_count*](../checks/column/nulls/not-nulls-count.md) check configured with the default settings finds empty columns.
The following screen shows a valid column that was not empty.

![Detect empty columns in tables with a data quality check](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/detect-empty-tables-check-min.png){ loading=lazy }

### Detect empty columns in UI
The configuration of the [*not_nulls_count*](../checks/column/nulls/not-nulls-count.md) that detects empty columns is shown below.

``` { .yaml linenums="1" hl_lines="13-15" }
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
          nulls:
            daily_not_nulls_count:
              error:
                min_count: 1
```


## Detect a minimum number of non-null values
The configuration of the [*not_nulls_count*](../checks/column/nulls/not-nulls-count.md) check is easy to adapt to detect columns
that should have at least a given number of non-null values. 

The minimum accepted number of non-null values is configured by setting the *min_count* parameter to a desired count.

### Detect in UI
The following example shows how to assert that a column contains at least 1500000 non-null values.

![Detect columns with too little non-null values in a column](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/detect-columns-with-too-little-not-null-values-min.png){ loading=lazy }

### Detect in YAML
The configuration of the [*not_nulls_count*](../checks/column/nulls/not-nulls-count.md) check in a YAML file only uses a different value of the *min_count* parameter.

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
          nulls:
            daily_not_nulls_count:
              error:
                min_count: 1500000
```

## Anomalies of data completeness
An unexpected change in the percentage of null value is a noticeable data anomaly.
DQOps uses time series anomaly detection to identify these issues.
The [*nulls_percent_anomaly*](../checks/column/nulls/nulls-percent-anomaly.md) check measures the percentage of null values for each day and raises data quality issues for anomalies.

The [*nulls_percent_anomaly*](../checks/column/nulls/nulls-percent-anomaly.md) check supports two methods of operation.

- A [daily monitoring](../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md#daily-monitoring-checks)
  check measures the percentage of all rows in a monitored table that contain null values.

- A [daily partition](../dqo-concepts/definition-of-data-quality-checks/partition-checks.md#daily-partitioning)
  check analyzes every daily partition. 
  The check raises a data quality issue when the percentage of null values between daily partitions changes.
  It can happen when the transformation logic in the data pipeline was recently modified,
  and an invalid transformation has problems with data conversion.

### Configuring completeness anomaly detection in UI
The following sample shows how to configure the 
[*daily_partition_nulls_percent_anomaly*](../checks/column/nulls/nulls-percent-anomaly.md#daily-partition-nulls-percent-anomaly)
check for detecting null anomalies across daily partitions. 
The configuration of the
[*daily_nulls_percent_anomaly*](../checks/column/nulls/nulls-percent-anomaly.md#daily-nulls-percent-anomaly) check
that monitors the whole table every day is the same,
but the [*daily_nulls_percent_anomaly*](../checks/column/nulls/nulls-percent-anomaly.md#daily-nulls-percent-anomaly) check 
requires 30 days of monitoring before it will show any results.

![Detect anomalies in the percentage of null values in a column](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/detect-anomalies-in-percent-of-null-values-in-date-partitions-min.png){ loading=lazy }

### Configuring completeness anomaly detection in YAML
The [*nulls_percent_anomaly*](../checks/column/nulls/nulls-percent-anomaly.md) check only requires the configuration of the *anomaly_percent* parameters
for each [issue severity level](../dqo-concepts/definition-of-data-quality-checks/index.md#issue-severity-levels).

This example shows the configuration of a daily monitoring check that measures the percentage of null values in the whole table.

``` { .yaml linenums="1" hl_lines="13-15" }
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
          nulls:
            daily_not_nulls_count:
              error:
                min_count: 1500000
```

The configuration of the [*daily_partition_nulls_percent_anomaly*](../checks/column/nulls/nulls-percent-anomaly.md#daily-partition-nulls-percent-anomaly)
check for analyzing daily partitions is similar.

``` { .yaml linenums="1" hl_lines="18-21" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    partition_by_column: created_date
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    street_number:
      type_snapshot:
        column_type: STRING
        nullable: true
      partitioned_checks:
        daily:
          nulls:
            daily_partition_nulls_percent_anomaly:
              warning:
                anomaly_percent: 3.0
              error:
                anomaly_percent: 0.5
```

## Completeness data quality issues
The data quality dashboards for monitoring null values are found in the *Data Quality Dimensions -> Completeness* folder.

### Whole table monitoring dashboards
The **Current completeness issues on columns** dashboard shows an aggregated view of active data quality issues with empty or incomplete columns.

This dashboard shows only the status of the most recent evaluation of all data quality checks for null values. 
When the data is fixed in the data source, and the failed data quality check is rerun,
the issue will disappear from the dashboard.

![Data quality dashboard showing empty and incomplete detected by data quality checks](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/empty-and-incomplete-columns-issues-shown-on-monitoring-dashboard-min.png){ loading=lazy }

### Partition monitoring dashboards
DQOps has a separate set of data quality dashboards for partitioned data.
These dashboards are found in the "Partitions" folder. They show issues for every daily or monthly partition.

The top section of the partition's *Current completeness issues on columns" dashboard shows the data sources, 
affected tables, and the types of completeness issues.

![Partitions with nulls shown on a dashboard - the filters](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/partition-completeness-status-dashboard-top-min.png){ loading=lazy }

The next section shows more details about incomplete or empty columns. 
The status identifies the highest severity issue by color.

![List of columns in a partitioned table that have incomplete data shown on a data quality dashboard](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/completeness-issues-dashboard-column-details-min.png){ loading=lazy }


## Use cases
| **Name of the example**                                                          | **Description**                                                                                                                                   |
|:---------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------|
| [Detect incomplete columns](../examples/data-completeness/detect-null-values.md) | This example shows how to incomplete columns that have too many null values using the [nulls_count](../checks/column/nulls/nulls-count.md) check. |

## List of nulls checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*nulls_count*](../checks/column/nulls/nulls-count.md)|Completeness|Detects incomplete columns that contain any *null* values. Counts the number of rows having a null value. Raises a data quality issue when the count of null values is above a *max_count* threshold.|:material-check-bold:|
|[*nulls_percent*](../checks/column/nulls/nulls-percent.md)|Completeness|Detects incomplete columns that contain any *null* values. Measures the percentage of rows having a null value. Raises a data quality issue when the percentage of null values is above a *max_percent* threshold. Configure this check to accept a given percentage of null values by setting the *max_percent* parameter.|:material-check-bold:|
|[*nulls_percent_anomaly*](../checks/column/nulls/nulls-percent-anomaly.md)|Completeness|Detects day-to-day anomalies in the percentage of *null* values. Measures the percentage of rows having a *null* value. Raises a data quality issue when the rate of null values increases or decreases too much.|:material-check-bold:|
|[*not_nulls_count*](../checks/column/nulls/not-nulls-count.md)|Completeness|Detects empty columns that contain only *null* values. Counts the number of rows that have non-null values. Raises a data quality issue when the count of non-null values is below *min_count*. The default value of the *min_count* parameter is 1, but DQOps supports setting a higher number to assert that a column has at least that many non-null values.|:material-check-bold:|
|[*not_nulls_percent*](../checks/column/nulls/not-nulls-percent.md)|Completeness|Detects incomplete columns that contain too few non-null values. Measures the percentage of rows that have non-null values. Raises a data quality issue when the percentage of non-null values is below *min_percentage*. The default value of the *min_percentage* parameter is 100.0, but DQOps supports setting a lower value to accept some nulls.| |
|[*nulls_percent_change*](../checks/column/nulls/nulls-percent-change.md)|Completeness|Detects relative increases or decreases in the percentage of null values since the last measured percentage. Measures the percentage of null values for each day. Raises a data quality issue when the change in the percentage of null values is above *max_percent* of the previous percentage.| |
|[*nulls_percent_change_1_day*](../checks/column/nulls/nulls-percent-change-1-day.md)|Completeness|Detects relative increases or decreases in the percentage of null values since the previous day. Measures the percentage of null values for each day. Raises a data quality issue when the change in the percentage of null values is above *max_percent* of the previous percentage.| |
|[*nulls_percent_change_7_days*](../checks/column/nulls/nulls-percent-change-7-days.md)|Completeness|Detects relative increases or decreases in the percentage of null values since the last week (seven days ago). Measures the percentage of null values for each day. Raises a data quality issue when the change in the percentage of null values is above *max_percent* of the previous percentage.| |
|[*nulls_percent_change_30_days*](../checks/column/nulls/nulls-percent-change-30-days.md)|Completeness|Detects relative increases or decreases in the percentage of null values since the last month (30 days ago). Measures the percentage of null values for each day. Raises a data quality issue when the change in the percentage of null values is above *max_percent* of the previous percentage.| |


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/nulls](../checks/column/nulls/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
