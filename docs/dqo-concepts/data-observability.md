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

## How DQOps activates data observability?
DQOps applies data observability by automatically activating data quality checks on monitored data sources. 

Upon installation, DQOps creates **Data quality policy** configuration files. These policies automatically 
activate checks on all tables and columns in DQOps.

You can enable, disable or customize the existing policies, and also add a new policies.
This customization can be done using the user interface or through the policies YAML files located 
in the [*patterns* folder](dqops-user-home-folder.md#data-quality-policies).

### Automatic activation of checks
DQOps makes it easy to distinguish between automatically activated and manually activated data quality checks within the 
[data quality check editor](dqops-user-interface-overview.md#check-editor). 

- Automatically activated checks are pre-enabled by policies and appear with a light green toggle button.
- Manually activated checks that have been manually activated are indicated by a darker green toggle button. When a check is manually activated on a table or column, DQOps uses the check's parameters from
    the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) file instead of the configuration from a check policy file.

Deactivating a check on the table or column is also possible to prevent it from running.

![Default data observability checks activated on table in DQOps](https://dqops.com/docs/images/concepts/data-observability/default-data-quality-checks-activated-automatically2.png){ loading=lazy; width="1200px" }


### Data quality policies
The data quality policies, also called patterns, are configurations of data quality checks. These policies are configured in YAML files
in the [*patterns* folder](dqops-user-home-folder.md#data-quality-policies). 
Every configuration is identified by a policy name and has a set of filters for target tables
and columns on which DQOps applies these checks.

The default content of the [*patterns* folder](dqops-user-home-folder.md#data-quality-policies) is shown below.

``` { .asc hl_lines="4-23" }
$DQO_USER_HOME
├───...
├───patterns
│   ├───Detect big day-to-day data volume changes.dqotablepattern.yaml
│   ├───Detect data freshness anomalies daily.dqotablepattern.yaml
│   ├───Detect data volume anomalies.dqotablepattern.yaml
│   ├───Detect empty tables daily.dqotablepattern.yaml
│   ├───Detect table availability issues daily.dqotablepattern.yaml
│   ├───Detect table schema changes.dqotablepattern.yaml
│   ├───Detect anomalies in the count of distinct values.dqocolumnpattern.yaml
│   ├───Detect anomalies in the percentage of null values.dqocolumnpattern.yaml
│   ├───Detect anomalies in the sum and average of numeric values.dqocolumnpattern.yaml
│   ├───Detect anomalies in the sum and average of numeric values at a partition level.dqocolumnpattern.yaml
│   ├───Detect change of the data type of values in text columns.dqocolumnpattern.yaml
│   ├───Detect column schema changes.dqocolumnpattern.yaml
│   ├───Detect columns containing any null values.dqocolumnpattern.yaml
│   ├───Detect empty columns.dqocolumnpattern.yaml
│   ├───Detect outliers in numeric values.dqocolumnpattern.yaml
│   ├───Detect outliers in numeric values across daily partitions.dqocolumnpattern.yaml
│   ├───Detect significant changes in the percentage of null values.dqocolumnpattern.yaml
│   ├───Profile text columns to detect PII values (sensitive data).dqocolumnpattern.yaml
│   ├───Track the count and percentage of null values.dqocolumnpattern.yaml
│   ├───Track volume of daily partitions.dqotablepattern.yaml
└───...   
```

### Policy file format 
The structure of the check policies YAML files is very similar to the structure of
the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) YAML files.
The definition of the default table-level checks also has the *profiling_checks*, *monitoring_checks*, 
and *partitioned_checks* nodes.

The structure of the table-level [*&lt;policy_name&gt;.dqotablespattern.yaml*](../reference/yaml/TableLevelDataQualityPolicyYaml.md) file is shown below.

``` { .yaml linenums="1" .annotate }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_table_checks
spec:
  priority: 1000 #(1)!
  description: ...
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


The structure of the column-level [*&lt;policy_name&gt;.dqocolumnspattern.yaml*](../reference/yaml/ColumnLevelDataQualityPolicyYaml.md)
file is very similar. The main differences are highlighted.

``` { .yaml linenums="1" .annotate hl_lines="1 4" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ColumnDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_column_checks
spec:
  priority: 1000
  description: ...
  target:
    connection: ...
    schema: ...
    table: ...
  profiling_checks: ...
  monitoring_checks: ...
  partitioned_checks: ...
```

### Policies priority
DQOps activates the policies on tables and columns according to priority.
The configuration files with a lower priority are activated first.
If an earlier priority file already had a configuration for some data quality check,
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

## Observability status in UI
DQOps automatically activates monitoring checks to track various aspects of your data health.
The **Observability status** screen provides a quick overview of any data quality problems for the selected
table or column. This allows you to proactively identify potential issues before they impact downstream processes.

With that screen, you can analyze trends in schema changes, volume, and freshness anomalies at table level
and distinct count and null percent anomalies at the column level. The screen also displays a summary of data quality issues 
for the table or columns of choice. 

### Table-level observability status

To access **Observability status** at the table level, click on the **Monitoring checks** or **Partition checks** sections,
select the table of interest and select the **Observability status** tab.

The screen below  provides an example of the **Observability status** screen at the table level in the **Monitoring section**.

![Table-level Observability status](https://dqops.com/docs/images/concepts/data-observability/table-level-observability-status1.png){ loading=lazy; width="1200px" }

The 5 blocks at the top of the screen show the last 15 results from checks run in 5 schema category:

- **Detect if the column list or data type has changed** ([daily_column_types_changed](../checks/table/schema/column-types-changed.md#daily-column-types-changed) check)
- **Detect if column list or order has changed** ([daily_column_list_or_order_changed](../checks/table/schema/column-list-or-order-changed.md#daily-column-list-or-order-changed) check)
- **Detect if columns were added or removed** ([daily_column_list_changed](../checks/table/schema/column-list-changed.md#daily-column-list-changed) check)
- **Detect change of column count** ([daily_column_count_changed](../checks/table/schema/column-count-changed.md#daily-column-count-changed) check)
- **Expected column count** ([daily_column_count](../checks/table/schema/column-count.md#daily-column-count) check)

The results are displayed as squares with colors indicating the result of the run check:

- Green for a correct result
- Yellow for a warning
- Orange for an error
- Red for a fatal error
- Black for execution error

Below are line graphs that display table freshness and volume anomalies:

- **Table freshness anomaly** ([daily_data_freshness_anomaly](../checks/table/timeliness/data-freshness.md#daily-data-freshness) check)
- **Row count anomaly** ([daily_row_count_anomaly](../checks/table/volume/row-count-anomaly.md#daily-row-count-anomaly) check)

Towards the bottom of the screen, there is a column chart that provides a summary of data quality issues for the selected table.

The **Observability status** screen at the table level in the Partition checks section displays a line graph showing 
**Partition row count anomaly** with data from the [daily_partition_row_count_anomaly](../checks/table/volume/row-count-anomaly.md#daily-partition-row-count-anomaly)
check and a column chart showing data quality issues.

### Column-level observability status

To access **Observability status** at the column level, click on the **Monitoring checks** or **Partition checks** sections.
Then, select the column of interest and select the **Observability status** tab.

The screen below provides an example of the **Observability status** screen at the column level in the **Monitoring section**.

![Column-level Observability status](https://dqops.com/docs/images/concepts/data-observability/column-level-observability-status1.png){ loading=lazy; width="1200px" }

The two blocks at the top of the screen display the last 15 results from the checks on two schema categories:

- **Verify if the column exists** ([daily_column_exists](../checks/column/schema/column-exists.md#daily-column-exists) check)
- **Verify if the column data type has changed** ([daily_column_type_changed](../checks/column/schema/column-type-changed.md#daily-column-type-changed) check)

Below, there are line graphs that show anomalies in distinct count and null percentage:

- **Null percent anomaly** ([daily_nulls_percent_anomaly](../checks/column/nulls/nulls-percent-anomaly.md#daily-nulls-percent-anomaly) check)
- **Distinct count anomaly** ([daily_distinct_count_anomaly](../checks/column/uniqueness/distinct-count-anomaly.md#daily-distinct-count-anomaly) check)

At the bottom of the screen, there is a column chart summarizing data quality issues for the selected column.

The **Observability status** screen at the column level in the **Partition checks** section displays
two line graphs for **Null percent anomaly** ([daily_partition_nulls_percent_anomaly](../checks/column/nulls/nulls-percent-anomaly.md#daily-partition-nulls-percent-anomaly) check) and 
**Distinct count anomaly** ([daily_partition_distinct_count_anomaly](../checks/column/uniqueness/distinct-count-anomaly.md#daily-partition-distinct-count-anomaly) check), as well as a column chart 
showing data quality issues.


## Target tables and columns
DQOps supports creating multiple data quality check configurations, named check policies or patterns. 
Each policy file contains an optional target table or target column filter
for columns or tables on which the checks are activated.

The filters for the target table or the target column are defined in an optional *target* node.
The filters support simple prefix and suffix filters, such as `*`, `*_id`, `fact_*`.

!!! note "Default data quality check policy"

    The default policy files [*&lt;policy_name&gt;.dqocolumnpattern.yaml*](../reference/yaml/ColumnLevelDataQualityPolicyYaml.md) 
    and [*&lt;policy_name&gt;.dqotablespattern.yaml*](../reference/yaml/TableLevelDataQualityPolicyYaml.md)
    do not have the configuration of the target table and column. They are applied to all tables and columns.


### Target table
All supported filters for [table-level check patterns](../reference/yaml/TableLevelDataQualityPolicyYaml.md) are shown below.
When a filter parameter is not configured, or the value is `*`, DQOps skips the filter parameter.

``` { .yaml linenums="1" .annotate hl_lines="8-13" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_table_checks
spec:
  priority: 1000
  description:
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

``` { .yaml linenums="1" .annotate hl_lines="14-16" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ColumnDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_column_checks
spec:
  priority: 1000
  description:
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

``` { .yaml linenums="1" .annotate hl_lines="8" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_table_checks
spec:
  priority: 1000
  description:
  target:
    table: "fact_sales,dim_pro*"
```

The following example shows how to target multiple columns.

``` { .yaml linenums="1" .annotate hl_lines="8" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/ColumnDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_column_checks
spec:
  priority: 1000
  description:
  target:
    column: "customer_id,product_id"
```

## Deactivating the policy
The configurations of data quality policies can be deactivated. DQOps does not apply the deactivated policies.
Each checks configuration file has a *disabled* boolean flag. The following examples show how to turn off a policy.

The following example shows how to disable a table-level policy.

``` { .yaml linenums="1" .annotate hl_lines="6" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableDefaultChecksPatternYaml-schema.json
apiVersion: dqo/v1
kind: default_table_checks
spec:
  priority: 1000
  disabled: true
  description:
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
  description:
  target:
    column: "customer_id,product_id"
```

## Configuring check policies in UI
The configuration of the data quality check policies (patterns) in DQOps is found in the *Data quality policies* node of the *Configuration* section.

### Table-level check policies
The list of data quality policies at the table level is located under the **Table-level quality policies'** node.
The screen displays the table-level policies. The toggle buttons on the left side of the **Quality policy name** are used to
activate or deactivate the policy. The list also shows connection, schema, and table search filters if they were set.
You can add a new policy, by clicking on the **Add data quality policy** button.

![List of table-level default data quality check policies in DQOps](https://dqops.com/docs/images/concepts/data-observability/table-level-data-quality-checks-policies1.png){ loading=lazy; width="1200px" }

Clicking on the name of the policy or the **Modify** action button allows to modify the policy configuration.
The search filter for the target patterns is configured on the *Target table* tab. 

![Table-level policy configuration in DQOps](https://dqops.com/docs/images/concepts/data-observability/table-level-policy-configuration1.png){ loading=lazy; width="1200px" }

The *Profiling*, *Monitoring*, and *Partition* tabs show a data quality check configuration editor.
The following screenshot shows the configuration of the **Detect big day-to-day data volume changes** policy with activated [*daily_row_count_change*](../checks/table/volume/row-count-change.md) check.

![Detect big day-to-day data volume changes policy in DQOps](https://dqops.com/docs/images/concepts/data-observability/detect-big-day-to-day-data-volume-changes-policy-configuration1.png){ loading=lazy; width="1200px" }

### Column-level check policies
The list of data quality check policies at the column level is located under the *Column-level checks patterns* node.
The screen displays the column-level policies. The toggle buttons on the left side of the **Quality policy name** are used to
activate or deactivate the policy. The list also shows connection, schema, and table search filters if they were set.
You can add a new policy, by clicking on the **Add data quality policy** button.

![List of column-level default data quality check policies in DQOps](https://dqops.com/docs/images/concepts/data-observability/column-level-data-quality-checks-policies1.png){ loading=lazy; width="1200px" }

Clicking on the name of the policy or the **Modify** action button allows to modify the policy configuration.
The search filter for the target patterns is configured on the *Target table* tab.

![Target column filters pattern for activating data quality checks in DQOps](https://dqops.com/docs/images/concepts/data-observability/column-level-policy-configuration1.png){ loading=lazy; width="1200px" }
Detect anomalies in the count of distinct values

The *Profiling*, *Monitoring*, and *Partition* tabs show a data quality check configuration editor.
The following screenshot shows the default configuration of the **Detect anomalies in the count of distinct values** policy with activated [*daily_distinct_count_anomaly*](../checks/column/uniqueness/distinct-count-anomaly.md) check.

![Detect anomalies in the count of distinct values policy in DQOps](https://dqops.com/docs/images/concepts/data-observability/detect-anomalies-in-the-count-of-distinct-values-policy1.png){ loading=lazy; width="1200px" }

## Table-level policies
The policies that DQOps can activate on all tables are described below.

The configuration can be changed using a policy editor screen in the Configuration section of the [DQOps user interface](dqops-user-interface-overview.md#configuration-tree-view).
or by editing the [*patterns/&lt;policy_name&gt;.dqotablepattern.yaml*](../reference/yaml/TableLevelDataQualityPolicyYaml.md)
file directly or 

### Detect big day-to-day data volume changes
Monitors data volume of the whole table daily and raises an issue when the volume has increased of decreased significantly.

This policy is activated by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                                       | Data quality check                                                                                                               | Description                                                                                                     | Data quality rule                                                                                                                        |
|------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------|
| [volume](../categories-of-data-quality-checks/how-to-detect-data-volume-issues-and-changes.md) | <span class="no-wrap-code ">[`daily_row_count_change`](../checks/table/volume/row-count-change.md#daily-row-count-change)</span> | Detects significant changes in the table volume measured as a percentage increase or decrease in the row count. | Raises a *warning* severity issue when the increase or decrease in row count since yesterday (or the last known row count) is above 10%. |

### Detect data freshness anomalies daily
Monitors data freshness anomalies daily.

This policy is activated by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                                            | Data quality check                                                                                                                                     | Description                                                                                                                                                                                                                                                                                          | Data quality rule                                                                                                 |
|-----------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------|
| [timeliness](../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md) | <span class="no-wrap-code ">[`daily_data_freshness_anomaly`](../checks/table/timeliness/data-freshness-anomaly.md#daily-data-freshness-anomaly)</span> | Measures the freshness of the table and detects anomalous (outstanding) delays in data freshness. The table must be properly configured to [support timeliness checks](../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md#configure-dqops-for-timeliness-checks). | Raises a *warning* severity issue when the data is outdated and the delay is in the top 1% of the longest delays. |

### Detect data volume anomalies
Monitors data volume of the whole table (using daily monitoring checks) and for each daily partition, using daily partition checks.

This policy is activated by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                                       | Data quality check                                                                                                                                      | Description                                                                                                                                                                            | Data quality rule                                                                                                                |
|------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------|
| [volume](../categories-of-data-quality-checks/how-to-detect-data-volume-issues-and-changes.md) | <span class="no-wrap-code ">[`daily_row_count_anomaly`](../checks/table/volume/row-count-anomaly.md#daily-row-count-anomaly)</span>                     | Detects anomalies in table volume (table's row count). Identifies the most significant volume increases or decreases since the previous day or the last known row count.               | Raises a *warning* severity issue when the increase or decrease in row count is in the top 1% of the biggest day-to-day changes. |
| [volume](../categories-of-data-quality-checks/how-to-detect-data-volume-issues-and-changes.md) | <span class="no-wrap-code ">[`daily_partition_row_count_anomaly`](../checks/table/volume/row-count-anomaly.md#daily-partition-row-count-anomaly)</span> | Detects anomalies in volume of daily partitions (table's row count). Identifies the most significant volume increases or decreases since the previous day or the last known row count. | Raises a *warning* severity issue when the increase or decrease in row count is in the top 1% of the biggest day-to-day changes. |

### Detect empty tables daily
Detects empty tables using daily monitoring checks.

This policy is activated by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                                       | Data quality check                                                                                          | Description                                                            | Data quality rule                                                  |
|------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------|--------------------------------------------------------------------|
| [volume](../categories-of-data-quality-checks/how-to-detect-data-volume-issues-and-changes.md) | <span class="no-wrap-code ">[`daily_row_count`](../checks/table/volume/row-count.md#daily-row-count)</span> | Captures the daily row count of the table and identifies empty tables. | Raises a *warning* severity issue when an empty table is detected. |

### Detect table availability issues daily
Monitors table availability issues daily

This policy is activated by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                                               | Data quality check                                                                                                                           | Description                                                                                                                                                                   | Data quality rule                                                                                                         |
|--------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|
| [availability](../categories-of-data-quality-checks/how-to-table-availability-issues-and-downtimes.md) | <span class="no-wrap-code ">[`daily_table_availability`](../checks/table/availability/table-availability.md#daily-table-availability)</span> | Verifies the availability of the table by running a simple query. Detects when the table is missing or not accessible due to invalid credentials or missing permission issues | Raises a *warning* severity issue when the table is not available, the table is missing, or the credentials are outdated. |

### Detect table schema changes
Monitors the table schema and raises issues when the schema of the table was changed.

This policy is activated by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                             | Data quality check                                                                                                                                                   | Description                                                                                                                                                                                                                                                    | Data quality rule                                                                                                           |
|--------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------|
| [schema](../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md) | <span class="no-wrap-code ">[`daily_column_count_changed`](../checks/table/schema/column-count-changed.md#daily-column-count-changed)</span>                         | Monitors the table's schema and detects when the column count has changed since the previous day or the last known column count.                                                                                                                               | Raises a *warning* severity issue when the column count has changed since the last known column count value.                |
| [schema](../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md) | <span class="no-wrap-code ">[`daily_column_list_changed`](../checks/table/schema/column-list-changed.md#daily-column-list-changed)</span>                            | Monitors the table's schema and detects when the list of columns has changed. Detects that new columns were added or removed since the previous day or the last known column list but does not care about the order of columns.                                | Raises a *warning* severity issue when any columns are added or removed.                                                    |
| [schema](../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md) | <span class="no-wrap-code ">[`daily_column_list_or_order_changed`](../checks/table/schema/column-list-or-order-changed.md#daily-column-list-or-order-changed)</span> | Monitors the table's schema and detects when the list or order of columns has changed. Detects that new columns were added, reordered or removed since the previous day or the last known column list.                                                         | Raises a *warning* severity issue when any columns are added or removed or any column changes the position in the table.    |
| [schema](../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md) | <span class="no-wrap-code ">[`daily_column_types_changed`](../checks/table/schema/column-types-changed.md#daily-column-types-changed)</span>                         | Monitors the table's schema and detects when the physical data types of columns have changed. It also detects whether columns were added or removed since the previous day or the last known column list. This check does not care about the order of columns. | Raises a *warning* severity issue when any columns are added or removed or the physical data type of any column is changed. |

### Track volume of daily partitions
Monitors volume (row count) of daily partitions.

This policy is activated by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                                       | Data quality check                                                                                                              | Description                                                                                                                     | Data quality rule                                     |
|------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------|
| [volume](../categories-of-data-quality-checks/how-to-detect-data-volume-issues-and-changes.md) | <span class="no-wrap-code ">[`daily_partition_row_count`](../checks/table/volume/row-count.md#daily-partition-row-count)</span> | Captures the volume (row count) of daily partitions. The partition volume can be reviewed on the DQOps data quality dashboards. | _no rules (use the dashboards to review the results)_ |


## Column-level policies
The policies that DQOps can activate on all columns are described below.

The configuration can be changed using a policy editor screen in the Configuration section of the [DQOps user interface](dqops-user-interface-overview.md#configuration-tree-view) or
by editing the [*patterns/&lt;policy_name&gt;.dqocolumnpattern.yaml*](../reference/yaml/ColumnLevelDataQualityPolicyYaml.md) files directly.

### Detect anomalies in the count of distinct values
Monitors the count of distinct values in a column and raises an issue when an anomaly is detected.

This policy is activated by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                                                  | Data quality check                                                                                                                                                          | Description                                                                                                                                                                                                            | Data quality rule                                                                                                                                            |
|-----------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [uniqueness](../categories-of-data-quality-checks/how-to-detect-data-uniqueness-issues-and-duplicates.md) | <span class="no-wrap-code ">[`daily_distinct_count_anomaly`](../checks/column/uniqueness/distinct-count-anomaly.md#daily-distinct-count-anomaly)</span>                     | Detects anomalies in the count of distinct (unique) values. Identifies the most significant increases or decreases in the count of distinct values since the previous day or the last known value.                     | Raises a *warning* severity issue when the increase or decrease in the count of distinct values is in the top 1% of the most significant day-to-day changes. |
| [uniqueness](../categories-of-data-quality-checks/how-to-detect-data-uniqueness-issues-and-duplicates.md) | <span class="no-wrap-code ">[`daily_partition_distinct_count_anomaly`](../checks/column/uniqueness/distinct-count-anomaly.md#daily-partition-distinct-count-anomaly)</span> | Detects anomalies in the count of distinct (unique) values of daily partitions. Identifies the most significant increases or decreases in the count of distinct values since the previous day or the last known value. | Raises a *warning* severity issue when the increase or decrease in the count of distinct values is in the top 1% of the most significant day-to-day changes. |

### Detect anomalies in the percentage of null values
Monitors the scale of null values in columns and raises an issue when the day-to-day change is significant.

This policy is activated by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                                              | Data quality check                                                                                                                                                  | Description                                                                                                                                                                                               | Data quality rule                                                                                                                              |
|-------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md) | <span class="no-wrap-code ">[`daily_nulls_percent_anomaly`](../checks/column/nulls/nulls-percent-anomaly.md#daily-nulls-percent-anomaly)</span>                     | Detects anomalies in the percentage of null values. Identifies the most significant increases or decreases in the rate of null values since the previous day or the last known value.                     | Raises a *warning* severity issue when the increase or decrease in the percentage of nulls is in the top 1% of the biggest day-to-day changes. |
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md) | <span class="no-wrap-code ">[`daily_partition_nulls_percent_anomaly`](../checks/column/nulls/nulls-percent-anomaly.md#daily-partition-nulls-percent-anomaly)</span> | Detects anomalies in the percentage of null values in daily partitions. Identifies the most significant increases or decreases in the rate of null values since the previous day or the last known value. | Raises a *warning* severity issue when the increase or decrease in the percentage of nulls is in the top 1% of the biggest day-to-day changes. |

### Detect anomalies in the sum and average of numeric values
Monitors the sum and average (mean) aggregated values of numeric values and raises a data quality issue when the value changes too much day-to-day.

This policy is activated by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                                     | Data quality check                                                                                                     | Description                                                                                                                                                                                                                                       | Data quality rule                                                                                                                                          |
|----------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [anomaly](../categories-of-data-quality-checks/how-to-detect-anomaly-data-quality-issues.md) | <span class="no-wrap-code ">[`daily_sum_anomaly`](../checks/column/anomaly/sum-anomaly.md#daily-sum-anomaly)</span>    | Detects anomalies in the sum of numeric values. Identifies the most significant increases or decreases in the sum of values since the previous day or the last known value. **_DQOps activates this check only on numeric columns._**             | Raises a *warning* severity issue when the increase or decrease in the sum of numeric values is in the top 1% of the most significant day-to-day changes.  |
| [anomaly](../categories-of-data-quality-checks/how-to-detect-anomaly-data-quality-issues.md) | <span class="no-wrap-code ">[`daily_mean_anomaly`](../checks/column/anomaly/mean-anomaly.md#daily-mean-anomaly)</span> | Detects anomalies in the mean (average) of numeric values. Identifies the most significant increases or decreases in the mean of values since the previous day or the last known value. **_DQOps activates this check only on numeric columns._** | Raises a *warning* severity issue when the increase or decrease in the mean of numeric values is in the top 1% of the most significant day-to-day changes. |


### Detect anomalies in the sum and average of numeric values at a partition level
Monitors the sum and average (mean) aggregated values of numeric values and raises a data quality issue when the value 
changes too much between daily partitions.

This policy is activated by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                                     | Data quality check                                                                                                                         | Description                                                                                                                                                                                                                                                           | Data quality rule                                                                                                                                                             |
|----------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [anomaly](../categories-of-data-quality-checks/how-to-detect-anomaly-data-quality-issues.md) | <span class="no-wrap-code ">[`daily_partition_sum_anomaly`](../checks/column/anomaly/sum-anomaly.md#daily-partition-sum-anomaly)</span>    | Detects anomalies in the sum of numeric values in daily partitions. Identifies the most significant increases or decreases in the sum of values since the previous day or the last known value. **_DQOps activates this check only on numeric columns._**             | Raises a *warning* severity issue when the increase or decrease in the sum of numeric values in daily partition is in the top 1% of the most significant day-to-day changes.  |
| [anomaly](../categories-of-data-quality-checks/how-to-detect-anomaly-data-quality-issues.md) | <span class="no-wrap-code ">[`daily_partition_mean_anomaly`](../checks/column/anomaly/mean-anomaly.md#daily-partition-mean-anomaly)</span> | Detects anomalies in the mean (average) of numeric values in daily partitions. Identifies the most significant increases or decreases in the mean of values since the previous day or the last known value. **_DQOps activates this check only on numeric columns._** | Raises a *warning* severity issue when the increase or decrease in the mean of numeric values in daily partition is in the top 1% of the most significant day-to-day changes. |

### Detect change of the data type of values in text columns
Detects when the values stored in a text column change their type. This policy should be activated on raw tables in the
landing zones for table that store all values (also numeric an dates) in text columns.

This policy is activated by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                            | Data quality check                                                                                                                                                                                         | Description                                                                                                                                                                         | Data quality rule                                                                                                                                                                                                                                                                                                           |
|-------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [datatype](../categories-of-data-quality-checks/how-to-detect-data-type-changes.md) | <span class="no-wrap-code ">[`daily_detected_datatype_in_text_changed`](../checks/column/datatype/detected-datatype-in-text-changed.md#daily-detected-datatype-in-text-changed)</span>                     | Analyzes values in text columns to detect if all values are convertible to the same data type (boolean, numeric, date, etc). **_DQOps activates this check only on text columns._** | Raises a *warning* severity issue when the values found in a text column are in a different format or a new value that is not convertible to the previously detected data type is found. For example, the column *customer_id* in the landing zone table always contained integer values, and a non-numeric value appeared. |
| [datatype](../categories-of-data-quality-checks/how-to-detect-data-type-changes.md) | <span class="no-wrap-code ">[`daily_partition_detected_datatype_in_text_changed`](../checks/column/datatype/detected-datatype-in-text-changed.md#daily-partition-detected-datatype-in-text-changed)</span> | Analyzes values in text columns to detect if all values are convertible to the same data type (boolean, numeric, date, etc). **_DQOps activates this check only on text columns._** | Raises a *warning* severity issue when the values found in a text column are in a different format or a new value that is not convertible to the previously detected data type is found. For example, the column *customer_id* in the landing zone table always contained integer values, and a non-numeric value appeared. |

### Detect column schema changes
Monitors the schema of columns registered in DQOps. Raises a data quality issue when the column is missing, or its data has changed.

This policy is activated by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                             | Data quality check                                                                                                                         | Description                                                                                                                                                                 | Data quality rule                                                                                                      |
|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| [schema](../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md) | <span class="no-wrap-code ">[`daily_column_exists`](../checks/column/schema/column-exists.md#daily-column-exists)</span>                   | Verifies that the column exists in the monitored table.                                                                                                                     | Raises a *warning* severity issue when the column is missing.                                                          |
| [schema](../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md) | <span class="no-wrap-code ">[`daily_column_type_changed`](../checks/column/schema/column-type-changed.md#daily-column-type-changed)</span> | Detects changes to the physical data type of the column since the last known data type. DQOps detects a change in the data type, length, precision, scale, and nullability. | Raises a *warning* severity issue when the physical data type of the column is changed since the last known data type. |


### Detect columns containing any null values
Detects columns containing any null values using both monitoring checks and daily partitioned checks.

This policy is disabled by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                                              | Data quality check                                                                                                                    | Description                                                                                                                     | Data quality rule                                                           |
|-------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------|
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md) | <span class="no-wrap-code ">[`daily_nulls_count`](../checks/column/nulls/nulls-count.md#daily-nulls-count)</span>                     | Counts null values in a monitored column. Detects partially incomplete columns that contain any null values.                    | Raises a *warning* severity issue when an empty row is detected in a table. |
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md) | <span class="no-wrap-code ">[`daily_partition_nulls_count`](../checks/column/nulls/nulls-count.md#daily-partition-nulls-count)</span> | Counts null values in a monitored column in daily partition. Detects partially incomplete columns that contain any null values. | Raises a *warning* severity issue when an empty row is detected in a table. |

### Detect empty columns
Detects empty columns using both monitoring checks an daily partitioned checks.

This policy is disabled by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                                              | Data quality check                                                                                                                                         | Description                                          | Data quality rule                                                |
|-------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------|------------------------------------------------------------------|
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md) | <span class="no-wrap-code ">[`daily_empty_column_found`](../checks/column/nulls/empty-column-found.md#daily-empty-column-found)</span>                     | Detects empty columns that contain only null values. | Raises a *warning* severity issue when an empty column is found. |
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md) | <span class="no-wrap-code ">[`daily_partition_empty_column_found`](../checks/column/nulls/empty-column-found.md#daily-partition-empty-column-found)</span> | Detects empty columns that contain only null values. | Raises a *warning* severity issue when an empty column is found. |

### Detect outliers in numeric values
Monitors numeric columns to detect new smallest (min) or biggest (max) value, which must be an anomaly.

This policy is disabled by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                                     | Data quality check                                                                                                  | Description                                                                                                                                                                                                                         | Data quality rule                                                                                                                                             |
|----------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [anomaly](../categories-of-data-quality-checks/how-to-detect-anomaly-data-quality-issues.md) | <span class="no-wrap-code ">[`daily_min_anomaly`](../checks/column/anomaly/min-anomaly.md#daily-min-anomaly)</span> | Detects anomalies in the minimum numeric values. Identifies the most significant increases or decreases in the min values since the previous day or the last known value. **_DQOps activates this check only on numeric columns._** | Raises a *warning* severity issue when the increase or decrease in the minimum of numeric values is in the top 1% of the most significant day-to-day changes. |
| [anomaly](../categories-of-data-quality-checks/how-to-detect-anomaly-data-quality-issues.md) | <span class="no-wrap-code ">[`daily_max_anomaly`](../checks/column/anomaly/max-anomaly.md#daily-max-anomaly)</span> | Detects anomalies in the maximum numeric values. Identifies the most significant increases or decreases in the max values since the previous day or the last known value. **_DQOps activates this check only on numeric columns._** | Raises a *warning* severity issue when the increase or decrease in the maximum of numeric values is in the top 1% of the most significant day-to-day changes. |

### Detect outliers in numeric values across daily partitions
Monitors numeric columns to detect new smallest (min) or biggest (max) value for each daily partition.
Raises a data quality issue when the partition contains a big or small value that exceeds regular ranges.

This policy is activated by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                                     | Data quality check                                                                                                                      | Description | Data quality rule                                                                                                                                             |
|----------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|-------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [anomaly](../categories-of-data-quality-checks/how-to-detect-anomaly-data-quality-issues.md) | <span class="no-wrap-code ">[`daily_partition_min_anomaly`](../checks/column/anomaly/min-anomaly.md#daily-partition-min-anomaly)</span> | Detects anomalies in the minimum numeric values. Identifies the most significant increases or decreases in the min values since the previous day or the last known value. **_DQOps activates this check only on numeric columns._**            | Raises a *warning* severity issue when the increase or decrease in the minimum of numeric values is in the top 1% of the most significant day-to-day changes. |
| [anomaly](../categories-of-data-quality-checks/how-to-detect-anomaly-data-quality-issues.md) | <span class="no-wrap-code ">[`daily_partition_max_anomaly`](../checks/column/anomaly/max-anomaly.md#daily-partition-max-anomaly)</span> | Detects anomalies in the maximum numeric values. Identifies the most significant increases or decreases in the max values since the previous day or the last known value. **_DQOps activates this check only on numeric columns._**            | Raises a *warning* severity issue when the increase or decrease in the maximum of numeric values is in the top 1% of the most significant day-to-day changes. |

### Detect significant changes in the percentage of null values
Monitors the percentage of null values in columns and raises an issue when the day-to-day change is above a threshold.

This policy is activated by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                                              | Data quality check                                                                                                                                               | Description                                                                                                                                                                             | Data quality rule                                                                                                                                             |
|-------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md) | <span class="no-wrap-code ">[`daily_nulls_percent_change`](../checks/column/nulls/nulls-percent-change.md#daily-nulls-percent-change)</span>                     | Detects significant changes in the percentage of null values measured as a percentage increase or decrease in the rate of null values compared to the previous day.                     | Raises a *warning* severity issue when the increase or decrease in the percentage of nulls since yesterday (or the last known nulls percentage) is above 10%. |
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md) | <span class="no-wrap-code ">[`daily_partition_nulls_percent_change`](../checks/column/nulls/nulls-percent-change.md#daily-partition-nulls-percent-change)</span> | Detects significant changes in the percentage of null values in daily partitions measured as a percentage increase or decrease in the rate of null values compared to the previous day. | Raises a *warning* severity issue when the increase or decrease in the percentage of nulls since yesterday (or the last known nulls percentage) is above 10%. |

### Profile text columns to detect PII values (sensitive data)
Activates data profiling checks on all text columns to detect if they contain sensitive data (emails, phone numbers).
Enabling this policy allows the data quality rule miner to set up PII checks when sensitive values are identified.

This policy is activated by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                                   | Data quality check                                                                                                                                               | Description                                    | Data quality rule |
|--------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------|-------------------|
| [pii](../categories-of-data-quality-checks/how-to-detect-pii-values-and-sensitive-data.md) | <span class="no-wrap-code ">[`profile_contains_usa_phone_percent`](../checks/column/pii/contains-usa-phone-percent.md#profile-contains-usa-phone-percent)</span> | Detects USA phone numbers inside text columns. | _no rules_        |
| [pii](../categories-of-data-quality-checks/how-to-detect-pii-values-and-sensitive-data.md) | <span class="no-wrap-code ">[`profile_contains_email_percent`](../checks/column/pii/contains-email-percent.md#profile-contains-email-percent)</span>             | Detects emails inside text columns.            | _no rules_        |
		
### Track the count and percentage of null values
Monitors the count and the percentage of null values without raising data quality issues.

This policy is activated by default.

The table below provides a description of the checks activated by this policy.

| Category                                                                                              | Data quality check                                                                                                                          | Description                                                                                                                      | Data quality rule                                     |
|-------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------|
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md) | <span class="no-wrap-code ">[`daily_nulls_count`](../checks/column/nulls/nulls-count.md#daily-nulls-count)</span>                           | Counts null values in a monitored column. Detects partially incomplete columns that contain any null values.                     | _no rules (use the dashboards to review the results)_ |
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md) | <span class="no-wrap-code ">[`daily_nulls_percent`](../checks/column/nulls/nulls-percent.md#daily-nulls-percent)</span>                     | Measures the percentage of null values in a column.                                                                              | _no rules (use the dashboards to review the results)_ |
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md) | <span class="no-wrap-code ">[`daily_partition_nulls_count`](../checks/column/nulls/nulls-count.md#daily-partition-nulls-count)</span>       | Counts null values in a monitored column in daily partitions. Detects partially incomplete columns that contain any null values. | _no rules (use the dashboards to review the results)_ |
| [nulls](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md) | <span class="no-wrap-code ">[`daily_partition_nulls_percent`](../checks/column/nulls/nulls-percent.md#daily-partition-nulls-percent)</span> | Measures the percentage of null values in a column in daily partitions.                                                          | _no rules (use the dashboards to review the results)_ |

## Next steps
- Learn how to [monitor, review and react to data quality issues](../working-with-dqo/daily-monitoring-of-data-quality.md) detected by the default data quality checks.
- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../dqops-installation/run-dqops-as-docker-container.md).
- Check the sample which shows [how to use the default DQOps data quality checks to detect empty tables](../examples/data-quality-monitoring/detect-empty-tables.md) and view the results on data quality dashboards.
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- DQOps provide you with summary statistics about your table and column. This information can be valuable in deciding which data quality checks and threshold levels should be set to monitor data quality. For more details about [Basic data statistics, click here](../working-with-dqo/collecting-basic-data-statistics.md).