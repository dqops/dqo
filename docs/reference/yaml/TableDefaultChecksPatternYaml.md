---
title: DQOps YAML file definitions
---
# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## TableDefaultChecksPatternYaml
The configuration of default data observability checks that are applied on tables that match a search pattern.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`api_version`</span>|DQOps YAML schema version|*string*| |dqo/v1| |
|<span class="no-wrap-code ">`kind`</span>|File type|*enum*|*source*<br/>*table*<br/>*sensor*<br/>*provider_sensor*<br/>*rule*<br/>*check*<br/>*settings*<br/>*file_index*<br/>*dashboards*<br/>*default_schedules*<br/>*default_checks*<br/>*default_table_checks*<br/>*default_column_checks*<br/>*default_notifications*<br/>|default_table_checks| |
|<span class="no-wrap-code ">[`spec`](./TableDefaultChecksPatternYaml.md#tabledefaultcheckspatternspec)</span>|The specification (configuration) of default data quality checks (data observability checks) that are applied on tables matching a pattern|*[TableDefaultChecksPatternSpec](./TableDefaultChecksPatternYaml.md#tabledefaultcheckspatternspec)*| | | |



___

## TableDefaultChecksPatternSpec
The default configuration of table-level data quality checks that are enabled as data observability checks to analyze basic measures and detect anomalies on tables.
 This configuration serves as a data quality policy that defines the data quality checks that are verified on matching tables.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`priority`</span>|The priority of the pattern. Patterns with lower values are applied before patterns with higher priority values.|*integer*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this data quality check configuration. The checks will not be activated.|*boolean*| | | |
|<span class="no-wrap-code ">`description`</span>|The description (documentation) of this data quality check configuration.|*string*| | | |
|<span class="no-wrap-code ">[`target`](./TableDefaultChecksPatternYaml.md#targettablepatternspec)</span>|The target table filter that are filtering the table and connection on which the default checks are applied.|*[TargetTablePatternSpec](./TableDefaultChecksPatternYaml.md#targettablepatternspec)*| | | |
|<span class="no-wrap-code ">[`profiling_checks`](./profiling/table-profiling-checks.md#tableprofilingcheckcategoriesspec)</span>|Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.|*[TableProfilingCheckCategoriesSpec](./profiling/table-profiling-checks.md#tableprofilingcheckcategoriesspec)*| | | |
|<span class="no-wrap-code ">[`monitoring_checks`](./TableYaml.md#tablemonitoringcheckcategoriesspec)</span>|Configuration of table level monitoring checks. Monitoring checks are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A monitoring check stores only the most recent data quality check result for each period of time.|*[TableMonitoringCheckCategoriesSpec](./TableYaml.md#tablemonitoringcheckcategoriesspec)*| | | |
|<span class="no-wrap-code ">[`partitioned_checks`](./TableYaml.md#tablepartitionedcheckcategoriesspec)</span>|Configuration of table level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.|*[TablePartitionedCheckCategoriesSpec](./TableYaml.md#tablepartitionedcheckcategoriesspec)*| | | |



___

## TargetTablePatternSpec
The configuration of a table pattern to match default table checks.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`connection`</span>|The data source connection name filter. Accepts wildcards in the format: *conn, *, conn*.|*string*| | | |
|<span class="no-wrap-code ">`schema`</span>|The schema name filter. Accepts wildcards in the format: *_prod, *, pub*.|*string*| | | |
|<span class="no-wrap-code ">`table`</span>|The table name filter. Accepts wildcards in the format: *_customers, *, fact_*.|*string*| | | |
|<span class="no-wrap-code ">`stage`</span>|The table stage filter. Accepts wildcards in the format: *_landing, *, staging_*.|*string*| | | |
|<span class="no-wrap-code ">`table_priority`</span>|The maximum table priority (inclusive) for tables that are covered by the default checks.|*integer*| | | |
|<span class="no-wrap-code ">`label`</span>|The label filter. Accepts wildcards in the format: *_customers, *, fact_*. The label must be present on the connection or table.|*string*| | | |



___

