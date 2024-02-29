# Data observability
Read this guide to learn how to automatically activate data quality checks in DQOps to profile data and monitor the most common data quality issues daily.

## Purpose of data observability
Data observability is a critical practice within data management aimed at ensuring that
data pipelines and processes are transparent, reliable, and understandable. 
At its core, data observability provides organizations with the ability to monitor,
understand, and troubleshoot their data infrastructure in real-time.

This includes monitoring data ingestion, transformation, and storage processes to
detect anomalies, errors, or deviations from expected behavior. 
Moreover, data observability empowers data teams to proactively address potential issues before they escalate,
thus minimizing the risk of data downtime, inaccuracies, or disruptions to business operations.
By implementing robust data observability practices, companies can gain insights into 
the health and performance of their data pipelines, identify and resolve issues promptly,
and maintain data integrity throughout the entire data lifecycle.

DQOps enables data observability by automatically activating selected data quality checks on data sources.

### Data profiling
When the metadata of new tables is imported into DQOps, 
it is essential to run various data quality checks to assess the initial data quality score of these tables.
The default configuration of [profiling checks](definition-of-data-quality-checks/data-profiling-checks.md)
enables running all supported data quality checks without manually activating them individually.

### Daily monitoring
The [daily monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md)
are the primary group of data quality checks that should be run every day.
DQOps calculates the [data quality KPI score](definition-of-data-quality-kpis.md) as the percentage of passed checks. 
The data quality results collected by the daily monitoring checks are end-of-day snapshots of the data quality status. 
A high percentage of passed checks ensures the data source is reliable and trustworthy.

### Partition monitoring
The [partition checks in DQOps](definition-of-data-quality-checks/partition-checks.md)
analyze daily and monthly partitions of date partitioned tables. 
The default data observability checks configured in DQOps monitor the partition volume and detect anomalies across partitions.


## How DQOps activates data observability
DQOps applies data observability by automatically activating data quality checks on monitored data sources. 

A new installation of DQOps creates two default configuration files, 
one with table-level checks and another for column-level checks. 
The default data quality checks are automatically activated on all tables and columns imported into DQOps,
but they can be disabled or reconfigured in the DQOps table configuration files [*.dqotable.yaml*](../reference/yaml/TableYaml.md) 
as described in the guide for [configuring data quality checks](configuring-data-quality-checks-and-rules.md).

### Data quality check patterns
The default data quality checks are configured in YAML files in the [*patterns* folder](dqops-user-home-folder.md#default-check-patterns). 
Every configuration is identified by a pattern name and has a set of filters for target tables
and columns on which DQOps applies these checks.

The default content of the [*patterns* folder](dqops-user-home-folder.md#default-check-patterns) is shown below.

``` { .asc hl_lines="4-5" }
$DQO_USER_HOME
├───...
├───patterns
│   ├───default.dqocolumnpattern.yaml
│   └───default.dqotablepattern.yaml
└───...   
```

The default pattern files are described below.

- The [*default.dqocolumnpattern.yaml*](../reference/yaml/ColumnDefaultChecksPatternYaml.md) file 
  contains the default configuration of column-level data quality checks activated on all columns.

- The [*default.dqotablespattern.yaml*](../reference/yaml/TableDefaultChecksPatternYaml.md) file
  contains the default configuration of table-level data quality checks activated on all tables.

### Pattern file format 
The structure of the default check pattern YAML files is very similar to the structure of
the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) YAML files.
The definition of the default table-level checks also has the *profiling_checks*, *monitoring_checks*, 
and *partitioned_checks* nodes.

The structure of the table-level [*&lt;pattern_name&gt;.dqotablespattern.yaml*](../reference/yaml/TableDefaultChecksPatternYaml.md) file is shown below.

``` { .yaml linenums="1" .annotate }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_table_checks
spec:
  priority: 1000 #(1)!
  target: #(2)!
    connection: ...
    schema: ...
    table: ...
  profiling_checks: #(3)!
    ...
  monitoring_checks: #(4)!
    daily:
      ...
    monthly:
      ... 
  partitioned_checks: #(5)!
    daily:
      ...
    monthly:
      ... 
```

1.  The priority (order) of the pattern file. DQOps sorts the pattern files by the *priority* value is ascending order
    and activates the configuration from the files with the lowest priority first.
2.  An optional filter to select target tables or columns.
3.  The configuration of [profiling checks](definition-of-data-quality-checks/data-profiling-checks.md).
4.  The configuration of [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md).
5.  The configuration of [partition checks](definition-of-data-quality-checks/partition-checks.md).


The structure of the column-level [*&lt;pattern_name&gt;.dqocolumnspattern.yaml*](../reference/yaml/ColumnDefaultChecksPatternYaml.md)
file is very similar. The main differences are highlighted.

``` { .yaml linenums="1" .annotate hl_lines="1 3" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ColumnDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_column_checks
spec:
  priority: 1000
  target:
    connection: ...
    schema: ...
    table: ...
  profiling_checks: ...
  monitoring_checks: ...
  partitioned_checks: ...
```

### Pattern priority
DQOps activates checks on tables and columns according to priority.
The configuration files with a lower priority are activated first.
If an earlier profile file already had a configuration for some data quality check,
it is not reconfigured by configuration from lower priority pattern files.

The priority field is shown below.

``` { .yaml linenums="1" .annotate hl_lines="5" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_table_checks
spec:
  priority: 1000 #(1)!
```

1.  The priority (order) of activating the pattern files.


## Target tables and columns
DQOps supports creating multiple default data quality check configurations, named check patterns. 
Each check pattern file contains an optional target table or target column filter
for columns or tables on which the checks are activated.

The filters for the target table or the target column are defined in an optional *target* node.
The filters support simple prefix and suffix filters, such as `*`, `*_id`, `fact_*`.

!!! note "Default data quality check patterns"

    The default pattern files [*default.dqocolumnpattern.yaml*](../reference/yaml/ColumnDefaultChecksPatternYaml.md)  and [*default.dqotablespattern.yaml*](../reference/yaml/TableDefaultChecksPatternYaml.md)
    do not have the configuration of the target table and column. They are applied to all tables and columns.


### Target table
All supported filters for [table-level check patterns](../reference/yaml/TableDefaultChecksPatternYaml.md) are shown below.
When a filter parameter is not configured, or the value is `*`, DQOps skips the filter parameter.

``` { .yaml linenums="1" .annotate hl_lines="7-12" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_table_checks
spec:
  priority: 1000
  target:
    connection: "dwh_*"
    schema: "public"
    table: "fact_*"
    label: "prod"
    stage: "landing"
    table_priority: 3
```

The target table parameters are listed in the following table.

| Filter parameter | Description                                                                                                                                                                   |
|------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `connection`     | The target connection name (data source name).                                                                                                                                |
| `schema`         | The target schema name.                                                                                                                                                       |
| `table`          | The target table name.                                                                                                                                                        |
| `label`          | This filter matches any [label](configuring-table-metadata.md#labels) applied on the data source or the table.                                                                |
| `stage`          | This filter matches the [`stage`](configuring-table-metadata.md#table-stage) field of a table.                                                                                |
| `table_priority` | This filter matches tables at a given [priority](configuring-table-metadata.md#table-priority) or with a higher priority (which means a lower value of the `priority` field). |

### Target column
The configuration of target columns for column-level check patterns is similar. 
All table-level filters are supported. The additional filters are specific to columns.

``` { .yaml linenums="1" .annotate hl_lines="13-15" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ColumnDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_column_checks
spec:
  priority: 1000
  target:
    connection: "dwh_*"
    schema: "public"
    table: "fact_*"
    label: "prod"
    stage: "landing"
    table_priority: 3
    column: "*_id"
    data_type: "VARCHAR"
    data_type_category: string
```

The target column parameters are listed in the following table.

| Filter parameter     | Description                                                                                                                                         |
|----------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| `column`             | The target column name.                                                                                                                             |
| `data_type`          | The physical data type of the target column, if it is stored in the [type_snapshot.column_type](configuring-table-metadata.md#column-schema) field. |
| `data_type_category` | The category of the data type detected by DQOps. DQOps detects a database independent category of the data type.                                    |


## Default profiling checks

### Table-level

``` { .yaml linenums="1" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_table_checks
spec:
  profiling_checks:
    volume:
      profile_row_count:
        warning:
          min_count: 1
    timeliness:
      profile_data_freshness: {}
    schema:
      profile_column_count: {}
```

### Column-level

``` { .yaml linenums="1" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ColumnDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_column_checks
spec:
  profiling_checks:
    nulls:
      profile_nulls_count:
        warning:
          max_count: 0
      profile_nulls_percent: {}
      profile_not_nulls_count:
        warning:
          min_count: 1
```

## Default daily monitoring checks

### Table-level

``` { .yaml linenums="1" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_table_checks
spec:
  monitoring_checks:
    daily:
      volume:
        daily_row_count:
          warning:
            min_count: 1
        daily_row_count_anomaly:
          warning:
            anomaly_percent: 1.0
        daily_row_count_change:
          warning:
            max_percent: 10.0
      timeliness:
        daily_data_freshness:
          warning:
            max_days: 2.0
        daily_data_staleness:
          warning:
            max_days: 2.0
      availability:
        daily_table_availability:
          warning:
            max_failures: 0
      schema:
        daily_column_count: {}
        daily_column_count_changed:
          warning: {}
        daily_column_list_changed:
          warning: {}
        daily_column_list_or_order_changed:
          warning: {}
        daily_column_types_changed:
          warning: {}
```


### Column-level

``` { .yaml linenums="1" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ColumnDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_column_checks
spec:
  monitoring_checks:
    daily:
      nulls:
        daily_nulls_count: {}
        daily_nulls_percent: {}
        daily_nulls_percent_anomaly:
          warning:
            anomaly_percent: 1.0
        daily_not_nulls_percent: {}
        daily_nulls_percent_change_1_day:
          warning:
            max_percent: 10.0
            exact_day: true
      uniqueness:
        daily_distinct_count_anomaly:
          warning:
            anomaly_percent: 1.0
      anomaly:
        daily_sum_anomaly:
          warning:
            anomaly_percent: 1.0
        daily_mean_anomaly:
          warning:
            anomaly_percent: 1.0
      datatype:
        daily_detected_datatype_in_text_changed:
          warning: {}
      schema:
        daily_column_exists:
          warning: {}
        daily_column_type_changed:
          warning: {} 
```


## Default partition checks

### Table-level

``` { .yaml linenums="1" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_table_checks
spec:
  partitioned_checks:
    daily:
      volume:
        daily_partition_row_count: {}
        daily_partition_row_count_anomaly:
          warning:
            anomaly_percent: 1.0
        daily_partition_row_count_change:
          warning:
            max_percent: 10.0
```


### Column-level
The default configuration of partition checks do not activate any column-level checks.


## List of default observability checks
The following table shows a list of default data quality checks and describes their purpose.

### **Profiling checks type**

| Target | Check name                                                           | Description                                                                                                                                        |
|--------|----------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| table  | [profile row count](../checks/table/volume/row-count.md)             | Counts the number of rows in a table.                                                                                                              |
| table  | [profile column count](../checks/table/schema/column-count.md)       | Retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to an expected number of columns. |
| column | [profile nulls count](../checks/column/nulls/nulls-count.md)         | Ensures that there are no more than a set number of null values in the monitored column.                                                           |
| column | [profile nulls percent](../checks/column/nulls/nulls-percent.md)     | Ensures that there are no more than a set percentage of null values in the monitored column.                                                       |
| column | [profile_not_nulls_count](../checks/column/nulls/not-nulls-count.md) | Ensures that there are no more than a set number of null values in the monitored column.                                                           |

### **Daily monitoring checks type**

| Target | Check name                                                                                                | Description                                                                                                                                        |
|--------|-----------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| table  | [daily row count](../checks/table/volume/row-count.md)                                                    | Counts the number of rows in a table.                                                                                                              |
| table  | [daily row count anomaly](../checks/table/volume/row-count-anomaly.md)                                    | Ensures that the row count is within a two-tailed percentile from measurements made during the last 90 days.                                       |
| table  | [daily row count change](../checks/table/volume/row-count-change.md)                                      | Ensures that the row count changed by a fixed rate since the last readout.                                                                         |
| table  | [daily table availability](../checks/table/availability/table-availability.md)                            | Verifies that a table exists, can be accessed, and queried without errors.                                                                         |
| table  | [daily column count](../checks/table/schema/column-count.md)                                              | Retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to an expected number of columns. |
| table  | [daily column count changed](../checks/table/schema/column-count-changed.md)                              | Detects whether the number of columns in a table has changed since the last time the check (checkpoint) was run.                                   |
| table  | [daily column list changed](../checks/table/schema/column-list-changed.md)                                | Detects if the list of columns has changed since the last time the check was run.                                                                  |
| table  | [daily column list or order changed](../checks/table/schema/column-list-or-order-changed.md)              | Detects whether the list of columns and the order of columns have changed since the last time the check was run.                                   |
| table  | [daily column types changed](../checks/table/schema/column-types-changed.md)                              | Detects if the column names or column types have changed since the last time the check was run.                                                    |
| column | [daily nulls count](../checks/column/nulls/nulls-count.md)                                                | Ensures that there are no more than a set number of null values in the monitored column.                                                           |
| column | [daily nulls percent](../checks/column/nulls/nulls-percent.md)                                            | Ensures that there are no more than a set percentage of null values in the monitored column.                                                       |
| column | [daily not nulls count](../checks/column/nulls/not-nulls-count.md)                                        | Ensures that there are no more than a set number of null values in the monitored column.                                                           |
| column | [daily not nulls percent](../checks/column/nulls/not-nulls-percent.md)                                    | Ensures that there are no more than a set percentage of not null values in the monitored column.                                                   |
| column | [daily nulls percent anomaly](../checks/column/nulls/nulls-percent-anomaly.md)                            | Ensures that the null percent value in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.        |
| column | [daily nulls percent change 1 day](../checks/column/nulls/nulls-percent-change-1-day.md)                  | Ensures that the null percent in a monitored column has changed by a fixed rate since the last readout from yesterday.                             |
| column | [daily_distinct_count_anomaly](../checks/column/uniqueness/distinct-count-anomaly.md)                     | Ensures that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days             |
| column | [daily detected datatype in text changed](../checks/column/datatype/detected-datatype-in-text-changed.md) | Scans all values in a string column and detects the data type of all values in a column.                                                           |
| column | [daily column exists](../checks/column/schema/column-exists.md)                                           | Reads the metadata of the monitored table and verifies that the column still exists in the data source.                                            |
| column | [daily column type changed](../checks/column/schema/column-type-changed.md)                               | Detects if the data type of the column has changed since the last time it was retrieved.                                                           |

### **Column type specific checks**
DQOps uses the imported data type of the column to decide what type of type specific default checks are enabled.

The following default checks are enabled only on text or numeric columns.

| Numeric columns                                                | Text columns                                                                                              |
|----------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------|
| [daily mean anomaly](../checks/column/anomaly/mean-anomaly.md) | [daily detected datatype_in_text changed](../checks/column/datatype/detected-datatype-in-text-changed.md) |
| [daily sum anomaly](../checks/column/anomaly/sum-anomaly.md)   |                                                                                                           |

## Modify configuration of default checks

The easiest way to change the default configuration is by using the *Default checks* editor in the *Configuration* section
of the DQOps user interface.

![Default checks editor](https://dqops.com/docs/images/concepts/default-checks-editor.png)

To do so, select the **Configuration** section, and then click on **Default checks configuration** on the tree view on 
the left side. Afterward, you can modify the default check configuration for **Profiling checks**, **Monitoring daily**,
and **Monitoring monthly** in the workspace on the right side.


## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../dqops-installation/run-dqops-as-docker-container.md).
- Check the sample which shows [how to use the default DQOps data quality checks to detect empty tables](../examples/data-quality-monitoring/detect-empty-tables.md) and view the results on data quality dashboards.
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- DQOps provide you with summary statistics about your table and column. This information can be valuable in deciding which data quality checks and threshold levels should be set to monitor data quality. For more details about [Basic data statistics, click here](../working-with-dqo/collecting-basic-data-statistics.md).