---
title: How to detect data quality issues with data observability
---
# How to detect data quality issues with data observability
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

These default configurations are also called **data quality policies**. 

### Automatic activation of checks
The [data quality check editor](dqops-user-interface-overview.md#check-editor) in DQOps
shows automatically activated data quality checks as enabled but using a gray color.

When a check is manually enabled on a table or column, DQOps uses the check's parameters from
the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) file instead of the default configuration from a default check pattern file.
Disabling a check on the table or column is also possible to prevent it from running.

![Default data observability checks activated on table in DQOps](https://dqops.com/docs/images/concepts/data-observability/default-data-quality-checks-activated-automatically-min.png){ loading=lazy; width="1200px" }


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
    label: "prod" #(1)!
    stage: "landing"
    table_priority: 3
    column: "*_id"
    data_type: "VARCHAR"
    data_type_category: string
```

1.  The label fields supports labels defined on the data source, table or column.

The target column parameters are listed in the following table.

| Filter parameter     | Description                                                                                                                                         |
|----------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| `column`             | The target column name.                                                                                                                             |
| `data_type`          | The physical data type of the target column, if it is stored in the [type_snapshot.column_type](configuring-table-metadata.md#column-schema) field. |
| `data_type_category` | The category of the data type detected by DQOps. DQOps detects a database independent category of the data type.                                    |


### Targeting multiple data assets 
All filters support targeting multiple objects, except the *data_type_category* parameter, which uses well-known values from an enumeration.
Targeting multiple data assets, such as multiple connections, schemas, tables, columns, labels, or data types, 
is supported by providing all the target data names separated by a comma.

The following example shows how to target multiple tables.

``` { .yaml linenums="1" .annotate hl_lines="7" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_table_checks
spec:
  priority: 1000
  target:
    table: "fact_sales,dim_pro*"
```

The following example shows how to target multiple columns.

``` { .yaml linenums="1" .annotate hl_lines="7" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ColumnDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_column_checks
spec:
  priority: 1000
  target:
    column: "customer_id,product_id"
```

## Deactivating the policy
The default configurations of data quality checks (policies) can be deactivated. DQOps does not apply the disabled policies.
Each default checks configuration file has a *disabled* boolean flag. The following examples show how to turn off a policy.

The following example shows how to disable a table-level policy.

``` { .yaml linenums="1" .annotate hl_lines="6" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_table_checks
spec:
  priority: 1000
  disabled: true
  target:
    table: "fact_sales,dim_pro*"
```

The following example shows how to disable a column-level policy.

``` { .yaml linenums="1" .annotate hl_lines="6" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ColumnDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_column_checks
spec:
  priority: 1000
  disabled: true
  target:
    column: "customer_id,product_id"
```

## Configuring check patterns in UI
The configuration of the default data quality check patterns in DQOps is found in the *Default checks configuration* node of the *Configuration* section.

### Table-level check patterns
The list of table-level data quality checks patterns is under the *Table-level checks patterns* node.

The listing screen shows a list of patterns and the filters to match the target tables.

![List of table-level default data quality check patterns in DQOps](https://dqops.com/docs/images/concepts/data-observability/table-level-data-quality-checks-patterns-min.png){ loading=lazy; width="1200px" }

The search filter for the target patterns is configured on the *Target table* tab.

![Target table filters pattern for activating data quality checks in DQOps](https://dqops.com/docs/images/concepts/data-observability/target-table-filters-configuration-for-default-data-quality-checks-min.png){ loading=lazy; width="1200px" }

The *Profiling*, *Monitoring Daily*, *Monitoring Monthly*, *Partition Daily*, and *Partition Monthly* tabs show a data quality check configuration editor. 
The following screenshot shows the default configuration of the [*Volume*](../categories-of-data-quality-checks/how-to-detect-data-volume-issues-and-changes.md) and
[*Timeliness*](../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md) data quality checks that is provided in DQOps in the **default** pattern.

![Default table-level data quality checks activated by DQOps](https://dqops.com/docs/images/concepts/data-observability/default-table-level-data-quality-checks-editor-short-min.png){ loading=lazy; width="1200px" }

### Column-level check patterns
The list of column-level data quality checks patterns is under the *Column-level checks patterns* node.

The listing screen shows a list of patterns and the filters to match the target columns.

![List of column-level default data quality check patterns in DQOps](https://dqops.com/docs/images/concepts/data-observability/column-level-data-quality-checks-patterns-min.png){ loading=lazy; width="1200px" }

The search filter for the target patterns is configured on the *Target column* tab.

![Target column filters pattern for activating data quality checks in DQOps](https://dqops.com/docs/images/concepts/data-observability/target-column-filters-configuration-for-default-data-quality-checks-min.png){ loading=lazy; width="1200px" }

The *Profiling*, *Monitoring Daily*, *Monitoring Monthly*, *Partition Daily*, and *Partition Monthly* tabs show a data quality check configuration editor.
The following screenshot shows the default configuration of the [*Nulls*](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md) and
[*Uniqueness*](../categories-of-data-quality-checks/how-to-detect-data-uniqueness-issues-and-duplicates.md) data quality checks that is provided in DQOps in the **default** pattern.

![Default column-level data quality checks activated by DQOps](https://dqops.com/docs/images/concepts/data-observability/default-column-level-data-quality-checks-editor-short-min.png){ loading=lazy; width="1200px" }


## Default table-level checks
The default configuration of table-level checks that DQOps activates on
all tables is described below for each [type of data quality check](definition-of-data-quality-checks/index.md#types-of-checks).

The configuration can be changed by editing 
the [*patterns/default.dqotablepattern.yaml*](../reference/yaml/TableDefaultChecksPatternYaml.md)
file directly or using a check pattern editor screen in the Configuration section of 
the [DQOps user interface](dqops-user-interface-overview.md#configuration-tree-view).

The content of the default [*patterns/default.dqotablepattern.yaml*](../reference/yaml/TableDefaultChecksPatternYaml.md)
file is described below for each [type of data quality check](definition-of-data-quality-checks/index.md#types-of-checks).

### Default profiling checks
The default configuration of table-level [profiling checks](definition-of-data-quality-checks/data-profiling-checks.md)
focuses on capturing the most basic metrics of imported tables usable for [initial data profiling of new data sources](definition-of-data-quality-kpis.md#profile-tables).

The default table-level profiling checks are described in the table below.

| Category                                                                                            | Data quality check                                                                                                                 | Description                                                                                                                                                                                                                              | Data quality rule                                                  |
|-----------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------|
| [volume](../categories-of-data-quality-checks/how-to-detect-data-volume-issues-and-changes.md)      | <span class="no-wrap-code ">[`profile_row_count`](../checks/table/volume/row-count.md#profile-row-count)</span>                    | Captures the row count of the table and identifies empty tables.                                                                                                                                                                         | Raises a *warning* severity issue when an empty table is detected. |
| [timeliness](../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md) | <span class="no-wrap-code ">[`profile_data_freshness`](../checks/table/timeliness/data-freshness.md#profile-data-freshness)</span> | Measures the freshness of the table. The table must be properly configured to [support timeliness checks](../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md#configure-dqops-for-timeliness-checks).  | _no rules_                                                         |
| [schema](../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md)                | <span class="no-wrap-code ">[`profile_column_count`](../checks/table/schema/column-count.md#profile-column-count)</span>           | Captures the column count in the table.                                                                                                                                                                                                  | _no rules_                                                         |


The following extract of the *patterns/default.dqotablepattern.yaml* file shows the configuration
of the default table-level [profiling checks](definition-of-data-quality-checks/data-profiling-checks.md).

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

### Default daily monitoring checks
The default configuration of table-level [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md)
focuses on anomaly detection, schema change detection, monitoring data freshness (timeliness),
and detecting issues with table availability.

The default table-level monitoring checks are described in the table below.

| Category                                                                                               | Data quality check                                                                                                                                                   | Description                                                                                                                                                                                                                                                              | Data quality rule                                                                                                                        |
|--------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------|
| [volume](../categories-of-data-quality-checks/how-to-detect-data-volume-issues-and-changes.md)         | <span class="no-wrap-code ">[`daily_row_count`](../checks/table/volume/row-count.md#daily-row-count)</span>                                                          | Captures the daily row count of the table and identifies empty tables.                                                                                                                                                                                                   | Raises a *warning* severity issue when an empty table is detected.                                                                       |
| [volume](../categories-of-data-quality-checks/how-to-detect-data-volume-issues-and-changes.md)         | <span class="no-wrap-code ">[`daily_row_count_anomaly`](../checks/table/volume/row-count-anomaly.md#daily-row-count-anomaly)</span>                                  | Detects anomalies in table volume (table's row count). Identifies the most significant volume increases or decreases since the previous day or the last known row count.                                                                                                 | Raises a *warning* severity issue when the increase or decrease in row count is in the top 1% of the biggest day-to-day changes.         |
| [volume](../categories-of-data-quality-checks/how-to-detect-data-volume-issues-and-changes.md)         | <span class="no-wrap-code ">[`daily_row_count_change`](../checks/table/volume/row-count-change.md#daily-row-count-change)</span>                                     | Detects significant changes in the table volume measured as a percentage increase or decrease in the row count.                                                                                                                                                          | Raises a *warning* severity issue when the increase or decrease in row count since yesterday (or the last known row count) is above 10%. |
| [timeliness](../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md)    | <span class="no-wrap-code ">[`daily_data_freshness`](../checks/table/timeliness/data-freshness.md#daily-data-freshness)</span>                                       | Measures the freshness of the table. The table must be properly configured to [support timeliness checks](../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md#configure-dqops-for-timeliness-checks).                                  | Raises a *warning* severity issue when the data is outdated by two days.                                                                 |
| [timeliness](../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md)    | <span class="no-wrap-code ">[`daily_data_staleness`](../checks/table/timeliness/data-staleness.md#daily-data-staleness)</span>                                       | Measures the staleness (the time since the last update) of the table. The table must be properly configured to [support timeliness checks](../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md#configure-dqops-for-timeliness-checks). | Raises a *warning* severity issue when the data has not been loaded or updated for two days.                                             |
| [availability](../categories-of-data-quality-checks/how-to-table-availability-issues-and-downtimes.md) | <span class="no-wrap-code ">[`daily_table_availability`](../checks/table/availability/table-availability.md#daily-table-availability)</span>                         | Verifies the availability of the table by running a simple query. Detects when the table is missing or not accessible due to invalid credentials or missing permission issues                                                                                            | Raises a *warning* severity issue when the table is not available, the table is missing, or the credentials are outdated.                |
| [schema](../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md)                   | <span class="no-wrap-code ">[`daily_column_count`](../checks/table/schema/column-count.md#daily-column-count)</span>                                                 | Captures the column count in the table and stores the value in the data quality data warehouse.                                                                                                                                                                          | _no rules_                                                                                                                               |
| [schema](../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md)                   | <span class="no-wrap-code ">[`daily_column_count_changed`](../checks/table/schema/column-count-changed.md#daily-column-count-changed)</span>                         | Monitors the table's schema and detects when the column count has changed since the previous day or the last known column count.                                                                                                                                         | Raises a *warning* severity issue when the column count has changed since the last known column count value.                             |
| [schema](../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md)                   | <span class="no-wrap-code ">[`daily_column_list_changed`](../checks/table/schema/column-list-changed.md#daily-column-list-changed)</span>                            | Monitors the table's schema and detects when the list of columns has changed. Detects that new columns were added or removed since the previous day or the last known column list but does not care about the order of columns.                                          | Raises a *warning* severity issue when any columns are added or removed.                                                                 |
| [schema](../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md)                   | <span class="no-wrap-code ">[`daily_column_list_or_order_changed`](../checks/table/schema/column-list-or-order-changed.md#daily-column-list-or-order-changed)</span> | Monitors the table's schema and detects when the list or order of columns has changed. Detects that new columns were added, reordered or removed since the previous day or the last known column list.                                                                   | Raises a *warning* severity issue when any columns are added or removed or any column changes the position in the table.                 |
| [schema](../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md)                   | <span class="no-wrap-code ">[`daily_column_types_changed`](../checks/table/schema/column-types-changed.md#daily-column-types-changed)</span>                         | Monitors the table's schema and detects when the physical data types of columns have changed. It also detects whether columns were added or removed since the previous day or the last known column list. This check does not care about the order of columns.           | Raises a *warning* severity issue when any columns are added or removed or the physical data type of any column is changed.              |


The following extract of the *patterns/default.dqotablepattern.yaml* file shows the configuration
of the default table-level [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md).

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

### Default partition checks
The default configuration of table-level [partition checks](definition-of-data-quality-checks/partition-checks.md)
focuses on analyzing the volume of daily partitions, especially detecting anomalies such as too-big or too-small partitions.

The default table-level partition checks are described in the table below.

| Category                                                                                               | Data quality check                                                                                                                                      | Description                                                                                                                                                                      | Data quality rule                                                                                                                                    |
|--------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------|
| [volume](../categories-of-data-quality-checks/how-to-detect-data-volume-issues-and-changes.md)         | <span class="no-wrap-code ">[`daily_partition_row_count`](../checks/table/volume/row-count.md#daily-partition-row-count)</span>                         | Captures the volume (row count) of daily partitions. The partition volume can be reviewed on the DQOps data quality dashboards.                                                  | _no rules_                                                                                                                                           |
| [volume](../categories-of-data-quality-checks/how-to-detect-data-volume-issues-and-changes.md)         | <span class="no-wrap-code ">[`daily_partition_row_count_anomaly`](../checks/table/volume/row-count-anomaly.md#daily-partition-row-count-anomaly)</span> | Detects anomalies in partition volume (partition's row count). Identifies the most significant volume increases or decreases since the previous day or the last known row count. | Raises a *warning* severity issue when the increase or decrease in partition's row count is in the top 1% of the biggest day-to-day changes.         |
| [volume](../categories-of-data-quality-checks/how-to-detect-data-volume-issues-and-changes.md)         | <span class="no-wrap-code ">[`daily_partition_row_count_change`](../checks/table/volume/row-count-change.md#daily-partition-row-count-change)</span>    | Detects significant changes in the partition volume measured as a percentage increase or decrease in the row count compared to the previous daily partition.                     | Raises a *warning* severity issue when the increase or decrease in partition's row count since yesterday (or the last known row count) is above 10%. |

The following extract of the *patterns/default.dqotablepattern.yaml* file shows the configuration
of the default table-level [partition checks](definition-of-data-quality-checks/partition-checks.md).

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


## Default column-level checks
The default configuration of column-level checks that DQOps activates on
all column is described below for each [type of data quality check](definition-of-data-quality-checks/index.md#types-of-checks).

The configuration can be changed by editing
the [*patterns/default.dqocolumnpattern.yaml*](../reference/yaml/ColumnDefaultChecksPatternYaml.md)
file directly or using a check pattern editor screen in the Configuration section of
the [DQOps user interface](dqops-user-interface-overview.md#configuration-tree-view).

The content of the default [*patterns/default.dqocolumnpattern.yaml*](../reference/yaml/ColumnDefaultChecksPatternYaml.md)
file is described below for each [type of data quality check](definition-of-data-quality-checks/index.md#types-of-checks).


### Default profiling checks
The default configuration of column-level [profiling checks](definition-of-data-quality-checks/data-profiling-checks.md)
focuses on detecting empty or partially incomplete columns that is usable for [initial data profiling of new data sources](definition-of-data-quality-kpis.md#profile-tables).

The default column-level profiling checks are described in the table below.

| Category                                                                                              | Data quality check                                                                                                                | Description                                                                                                  | Data quality rule                                                                                |
|-------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------|
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md) | <span class="no-wrap-code ">[`profile_nulls_count`](../checks/column/nulls/nulls-count.md#profile-nulls-count)</span>             | Counts null values in a monitored column. Detects partially incomplete columns that contain any null values. | Raises a *warning* severity issue when null values are detected in a column.                     |
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md) | <span class="no-wrap-code ">[`profile_nulls_percent`](../checks/column/nulls/nulls-percent.md#profile-nulls-percent)</span>       | Measures the percentage of null values in a column.                                                          | _no rules_                                                                                       |
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md) | <span class="no-wrap-code ">[`profile_not_nulls_count`](../checks/column/nulls/not-nulls-count.md#profile-not-nulls-count)</span> | Detects empty columns by counting not null values.                                                           | Raises a *warning* severity issue when an empty column containing only null values is detected.  |

The following extract of the *patterns/default.dqocolumnpattern.yaml* file shows the configuration
of the default column-level [profiling checks](definition-of-data-quality-checks/data-profiling-checks.md).

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

### Default daily monitoring checks
The default configuration of column-level [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md)
focuses on detecting anomalies related to null values, numeric values, and column schema changes.

The default column-level monitoring checks are described in the table below.

| Category                                                                                                  | Data quality check                                                                                                                                                                     | Description                                                                                                                                                                                                                                       | Data quality rule                                                                                                                                                                                                                                                                                                           |
|-----------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md)     | <span class="no-wrap-code ">[`daily_nulls_count`](../checks/column/nulls/nulls-count.md#daily-nulls-count)</span>                                                                      | Counts null values in a monitored column. Detects partially incomplete columns that contain any null values.                                                                                                                                      | _no rules (use the dashboards to review the results)_                                                                                                                                                                                                                                                                       |
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md)     | <span class="no-wrap-code ">[`daily_nulls_percent`](../checks/column/nulls/nulls-percent.md#daily-nulls-percent)</span>                                                                | Measures the percentage of null values in a column.                                                                                                                                                                                               | _no rules (use the dashboards to review the results)_                                                                                                                                                                                                                                                                       |
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md)     | <span class="no-wrap-code ">[`daily_nulls_percent_anomaly`](../checks/column/nulls/nulls-percent-anomaly.md#daily-nulls-percent-anomaly)</span>                                        | Detects anomalies in the percentage of null values. Identifies the most significant increases or decreases in the rate of null values since the previous day or the last known value.                                                             | Raises a *warning* severity issue when the increase or decrease in the percentage of nulls is in the top 1% of the biggest day-to-day changes.                                                                                                                                                                              |
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md)     | <span class="no-wrap-code ">[`daily_not_nulls_percent`](../checks/column/nulls/not-nulls-percent.md#daily-not-nulls-percent)</span>                                                    | Detects empty columns by counting not null values.                                                                                                                                                                                                | _no rules (use the dashboards to review the results)_                                                                                                                                                                                                                                                                       |
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md)     | <span class="no-wrap-code ">[`daily_nulls_percent_change`](../checks/column/nulls/nulls-percent-change.md#daily-nulls-percent-change)</span>                                           | Detects significant changes in the percentage of null values measured as a percentage increase or decrease in the rate of null values compared to the previous day.                                                                               | Raises a *warning* severity issue when the increase or decrease in the percentage of nulls since yesterday (or the last known nulls percentage) is above 10%.                                                                                                                                                               |
| [uniqueness](../categories-of-data-quality-checks/how-to-detect-data-uniqueness-issues-and-duplicates.md) | <span class="no-wrap-code ">[`daily_distinct_count_anomaly`](../checks/column/uniqueness/distinct-count-anomaly.md#daily-distinct-count-anomaly)</span>                                | Detects anomalies in the count of distinct (unique) values. Identifies the most significant increases or decreases in the count of distinct values since the previous day or the last known value.                                                | Raises a *warning* severity issue when the increase or decrease in the count of distinct values is in the top 1% of the most significant day-to-day changes.                                                                                                                                                                |
| [anomaly](../categories-of-data-quality-checks/how-to-detect-anomaly-data-quality-issues.md)              | <span class="no-wrap-code ">[`daily_sum_anomaly`](../checks/column/anomaly/sum-anomaly.md#daily-sum-anomaly)</span>                                                                    | Detects anomalies in the sum of numeric values. Identifies the most significant increases or decreases in the sum of values since the previous day or the last known value. **_DQOps activates this check only on numeric columns._**             | Raises a *warning* severity issue when the increase or decrease in the sum of numeric values is in the top 1% of the most significant day-to-day changes.                                                                                                                                                                   |
| [anomaly](../categories-of-data-quality-checks/how-to-detect-anomaly-data-quality-issues.md)              | <span class="no-wrap-code ">[`daily_mean_anomaly`](../checks/column/anomaly/mean-anomaly.md#daily-mean-anomaly)</span>                                                                 | Detects anomalies in the mean (average) of numeric values. Identifies the most significant increases or decreases in the mean of values since the previous day or the last known value. **_DQOps activates this check only on numeric columns._** | Raises a *warning* severity issue when the increase or decrease in the sum of numeric values is in the top 1% of the most significant day-to-day changes.                                                                                                                                                                   |
| [datatype](../categories-of-data-quality-checks/how-to-detect-data-type-changes.md)                       | <span class="no-wrap-code ">[`daily_detected_datatype_in_text_changed`](../checks/column/datatype/detected-datatype-in-text-changed.md#daily-detected-datatype-in-text-changed)</span> | Analyzes values in text columns to detect if all values are convertible to the same data type (boolean, numeric, date, etc). **_DQOps activates this check only on text columns._**                                                               | Raises a *warning* severity issue when the values found in a text column are in a different format or a new value that is not convertible to the previously detected data type is found. For example, the column *customer_id* in the landing zone table always contained integer values, and a non-numeric value appeared. |
| [schema](../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md)                      | <span class="no-wrap-code ">[`daily_column_exists`](../checks/column/schema/column-exists.md#daily-column-exists)</span>                                                               | Verifies that the column exists in the monitored table.                                                                                                                                                                                           | Raises a *warning* severity issue when the column is missing.                                                                                                                                                                                                                                                               |
| [schema](../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md)                      | <span class="no-wrap-code ">[`daily_column_type_changed`](../checks/column/schema/column-type-changed.md#daily-column-type-changed)</span>                                             | Detects changes to the physical data type of the column since the last known data type. DQOps detects a change in the data type, length, precision, scale, and nullability.                                                                       | Raises a *warning* severity issue when the physical data type of the column is changed since the last known data type.                                                                                                                                                                                                      |

The following extract of the *patterns/default.dqocolumnpattern.yaml* file shows the configuration
of the default column-level [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md).

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
        daily_nulls_percent_change:
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


### Default daily partitioned checks
The default configuration of column-level [partition checks](definition-of-data-quality-checks/partition-checks.md)
focuses on detecting anomalies related to null values, numeric values and distinct values across daily partitions.

The default column-level daily partition checks are described in the table below.

| Category                                                                                                  | Data quality check                                                                                                                                                                                         | Description                                                                                                                                                                                                                                                 | Data quality rule                                                                                                                                                                                                                                                                                                           |
|-----------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md)     | <span class="no-wrap-code ">[`daily_partition_nulls_count`](../checks/column/nulls/nulls-count.md#daily-partition-nulls-count)</span>                                                                      | Counts null values in a monitored column. Detects partially incomplete columns that contain any null values.                                                                                                                                                | _no rules (use the dashboards to review the results)_                                                                                                                                                                                                                                                                       |
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md)     | <span class="no-wrap-code ">[`daily_partition_nulls_percent`](../checks/column/nulls/nulls-percent.md#daily-nulls-percent)</span>                                                                          | Measures the percentage of null values in a column.                                                                                                                                                                                                         | _no rules (use the dashboards to review the results)_                                                                                                                                                                                                                                                                       |
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md)     | <span class="no-wrap-code ">[`daily_partition_nulls_percent_anomaly`](../checks/column/nulls/nulls-percent-anomaly.md#daily-nulls-percent-anomaly)</span>                                                  | Detects anomalies in the percentage of null values. Identifies the most significant increases or decreases in the rate of null values since the previous day or the last known value.                                                                       | Raises a *warning* severity issue when the increase or decrease in the percentage of nulls is in the top 1% of the biggest day-to-day changes.                                                                                                                                                                              |
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md)     | <span class="no-wrap-code ">[`daily_partition_not_nulls_percent`](../checks/column/nulls/not-nulls-percent.md#daily-not-nulls-percent)</span>                                                              | Detects empty columns by counting not null values.                                                                                                                                                                                                          | _no rules (use the dashboards to review the results)_                                                                                                                                                                                                                                                                       |
| [uniqueness](../categories-of-data-quality-checks/how-to-detect-data-uniqueness-issues-and-duplicates.md) | <span class="no-wrap-code ">[`daily_partition_distinct_count_anomaly`](../checks/column/uniqueness/distinct-count-anomaly.md#daily-partition-distinct-count-anomaly)</span>                                | Detects anomalies in the count of distinct (unique) values. Identifies the most significant increases or decreases in the count of distinct values since the previous day or the last known value.                                                          | Raises a *warning* severity issue when the increase or decrease in the count of distinct values is in the top 1% of the most significant day-to-day changes.                                                                                                                                                                |
| [anomaly](../categories-of-data-quality-checks/how-to-detect-anomaly-data-quality-issues.md)              | <span class="no-wrap-code ">[`daily_partition_sum_anomaly`](../checks/column/anomaly/sum-anomaly.md#daily-partition-sum-anomaly)</span>                                                                    | Detects anomalies in the sum of numeric values. Identifies the most significant increases or decreases in the sum of values since the previous day or the last known value. **_DQOps activates this check only on numeric columns._**                       | Raises a *warning* severity issue when the increase or decrease in the sum of numeric values is in the top 1% of the most significant day-to-day changes.                                                                                                                                                                   |
| [anomaly](../categories-of-data-quality-checks/how-to-detect-anomaly-data-quality-issues.md)              | <span class="no-wrap-code ">[`daily_partition_mean_anomaly`](../checks/column/anomaly/mean-anomaly.md#daily-partition-mean-anomaly)</span>                                                                 | Detects anomalies in the mean (average) of numeric values. Identifies the most significant increases or decreases in the mean of values since the previous day or the last known value. **_DQOps activates this check only on numeric columns._**           | Raises a *warning* severity issue when the increase or decrease in the mean of numeric values is in the top 1% of the most significant day-to-day changes.                                                                                                                                                                  |
| [anomaly](../categories-of-data-quality-checks/how-to-detect-anomaly-data-quality-issues.md)              | <span class="no-wrap-code ">[`daily_partition_min_anomaly`](../checks/column/anomaly/min-anomaly.md#daily-partition-min-anomaly)</span>                                                                    | Detects anomalies as a new minimal numeric value (outlier detection). Identifies the most significant increases or decreases in the minimal value since the previous day or the last known value. **_DQOps activates this check only on numeric columns._** | Raises a *warning* severity issue when the increase or decrease in the minimum of numeric values is in the top 1% of the most significant day-to-day changes.                                                                                                                                                               |
| [anomaly](../categories-of-data-quality-checks/how-to-detect-anomaly-data-quality-issues.md)              | <span class="no-wrap-code ">[`daily_partition_max_anomaly`](../checks/column/anomaly/max-anomaly.md#daily-partition-max-anomaly)</span>                                                                    | Detects anomalies as a new maximal numeric value (outlier detection). Identifies the most significant increases or decreases in the maximal value since the previous day or the last known value. **_DQOps activates this check only on numeric columns._** | Raises a *warning* severity issue when the increase or decrease in the maximum of numeric values is in the top 1% of the most significant day-to-day changes.                                                                                                                                                               |
| [datatype](../categories-of-data-quality-checks/how-to-detect-data-type-changes.md)                       | <span class="no-wrap-code ">[`daily_partition_detected_datatype_in_text_changed`](../checks/column/datatype/detected-datatype-in-text-changed.md#daily-partition-detected-datatype-in-text-changed)</span> | Analyzes values in text columns to detect if all values are convertible to the same data type (boolean, numeric, date, etc). **_DQOps activates this check only on text columns._**                                                                         | Raises a *warning* severity issue when the values found in a text column are in a different format or a new value that is not convertible to the previously detected data type is found. For example, the column *customer_id* in the landing zone table always contained integer values, and a non-numeric value appeared. |

The following extract of the *patterns/default.dqocolumnpattern.yaml* file shows the configuration
of the default column-level [partition checks](definition-of-data-quality-checks/partition-checks.md).

``` { .yaml linenums="1" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ColumnDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_column_checks
spec:
  partitioned_checks:
    daily:
      nulls:
        daily_partition_nulls_count: {}
        daily_partition_nulls_percent: {}
        daily_partition_nulls_percent_anomaly:
          warning:
            anomaly_percent: 1.0
        daily_partition_not_nulls_percent: {}
      uniqueness:
        daily_partition_distinct_count_anomaly:
          warning:
            anomaly_percent: 1.0
      anomaly:
        daily_partition_sum_anomaly:
          warning:
            anomaly_percent: 1.0
        daily_partition_mean_anomaly:
          warning:
            anomaly_percent: 1.0
        daily_partition_min_anomaly:
          warning:
            anomaly_percent: 1.0
        daily_partition_max_anomaly:
          warning:
            anomaly_percent: 1.0
      datatype:
        daily_partition_detected_datatype_in_text_changed:
          warning: {}
```


## Next steps
- Learn how to [monitor, review and react to data quality issues](../working-with-dqo/daily-monitoring-of-data-quality.md) detected by the default data quality checks.
- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../dqops-installation/run-dqops-as-docker-container.md).
- Check the sample which shows [how to use the default DQOps data quality checks to detect empty tables](../examples/data-quality-monitoring/detect-empty-tables.md) and view the results on data quality dashboards.
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- DQOps provide you with summary statistics about your table and column. This information can be valuable in deciding which data quality checks and threshold levels should be set to monitor data quality. For more details about [Basic data statistics, click here](../working-with-dqo/collecting-basic-data-statistics.md).