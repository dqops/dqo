---
title: DQOps REST API default_table_check_patterns models reference
---
# DQOps REST API default_table_check_patterns models reference
The references of all objects used by [default_table_check_patterns](../operations/default_table_check_patterns.md) REST API operations are listed below.


## TargetTablePatternSpec
The configuration of a table pattern to match default table checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection`</span>|The data source connection name filter. Accepts wildcards in the format: *conn, *, conn*.|*string*|
|<span class="no-wrap-code">`schema`</span>|The schema name filter. Accepts wildcards in the format: *_prod, *, pub*.|*string*|
|<span class="no-wrap-code">`table`</span>|The table name filter. Accepts wildcards in the format: *_customers, *, fact_*.|*string*|
|<span class="no-wrap-code">`stage`</span>|The table stage filter. Accepts wildcards in the format: *_landing, *, staging_*.|*string*|
|<span class="no-wrap-code">`table_priority`</span>|The maximum table priority (inclusive) for tables that are covered by the default checks.|*integer*|
|<span class="no-wrap-code">`label`</span>|The label filter. Accepts wildcards in the format: *_customers, *, fact_*. The label must be present on the connection or table.|*string*|


___

## DefaultTableChecksPatternListModel
The listing model of table-level default check patterns that is returned by the REST API.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name.|*string*|
|<span class="no-wrap-code">`priority`</span>|The priority of the pattern. Patterns with lower values are applied before patterns with higher priority values.|*integer*|
|<span class="no-wrap-code">`disabled`</span>|Disables this data quality check configuration. The checks will not be activated.|*boolean*|
|<span class="no-wrap-code">`description`</span>|The description (documentation) of this data quality check configuration.|*string*|
|<span class="no-wrap-code">[`target_table`](#targettablepatternspec)</span>|The filters for the target table.|*[TargetTablePatternSpec](#targettablepatternspec)*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|
|<span class="no-wrap-code">`yaml_parsing_error`</span>|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|*string*|


___

## TableMonitoringCheckCategoriesSpec
Container of table level monitoring, divided by the time window (daily, monthly, etc.)


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`daily`](./tables.md#tabledailymonitoringcheckcategoriesspec)</span>|Configuration of daily monitoring evaluated at a table level.|*[TableDailyMonitoringCheckCategoriesSpec](./tables.md#tabledailymonitoringcheckcategoriesspec)*|
|<span class="no-wrap-code">[`monthly`](./tables.md#tablemonthlymonitoringcheckcategoriesspec)</span>|Configuration of monthly monitoring evaluated at a table level.|*[TableMonthlyMonitoringCheckCategoriesSpec](./tables.md#tablemonthlymonitoringcheckcategoriesspec)*|


___

## TablePartitionedCheckCategoriesSpec
Container of table level partitioned checks, divided by the time window (daily, monthly, etc.)


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`daily`](./tables.md#tabledailypartitionedcheckcategoriesspec)</span>|Configuration of day partitioned data quality checks evaluated at a table level.|*[TableDailyPartitionedCheckCategoriesSpec](./tables.md#tabledailypartitionedcheckcategoriesspec)*|
|<span class="no-wrap-code">[`monthly`](./tables.md#tablemonthlypartitionedcheckcategoriesspec)</span>|Configuration of monthly partitioned data quality checks evaluated at a table level..|*[TableMonthlyPartitionedCheckCategoriesSpec](./tables.md#tablemonthlypartitionedcheckcategoriesspec)*|


___

## TableDefaultChecksPatternSpec
The default configuration of table-level data quality checks that are enabled as data observability checks to analyze basic measures and detect anomalies on tables.
 This configuration serves as a data quality policy that defines the data quality checks that are verified on matching tables.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`priority`</span>|The priority of the pattern. Patterns with lower values are applied before patterns with higher priority values.|*integer*|
|<span class="no-wrap-code">`disabled`</span>|Disables this data quality check configuration. The checks will not be activated.|*boolean*|
|<span class="no-wrap-code">`description`</span>|The description (documentation) of this data quality check configuration.|*string*|
|<span class="no-wrap-code">[`target`](./default_table_check_patterns.md#targettablepatternspec)</span>|The target table filter that are filtering the table and connection on which the default checks are applied.|*[TargetTablePatternSpec](./default_table_check_patterns.md#targettablepatternspec)*|
|<span class="no-wrap-code">[`profiling_checks`](./tables.md#tableprofilingcheckcategoriesspec)</span>|Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.|*[TableProfilingCheckCategoriesSpec](./tables.md#tableprofilingcheckcategoriesspec)*|
|<span class="no-wrap-code">[`monitoring_checks`](#tablemonitoringcheckcategoriesspec)</span>|Configuration of table level monitoring checks. Monitoring checks are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A monitoring check stores only the most recent data quality check result for each period of time.|*[TableMonitoringCheckCategoriesSpec](#tablemonitoringcheckcategoriesspec)*|
|<span class="no-wrap-code">[`partitioned_checks`](#tablepartitionedcheckcategoriesspec)</span>|Configuration of table level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.|*[TablePartitionedCheckCategoriesSpec](#tablepartitionedcheckcategoriesspec)*|


___

## DefaultTableChecksPatternModel
Default table-level checks pattern model that is returned by the REST API. Describes a configuration of data quality checks for a named pattern. DQOps applies these checks on tables that match the filter.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|
|<span class="no-wrap-code">[`pattern_spec`](#tabledefaultcheckspatternspec)</span>|The default checks specification.|*[TableDefaultChecksPatternSpec](#tabledefaultcheckspatternspec)*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|
|<span class="no-wrap-code">`yaml_parsing_error`</span>|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|*string*|


___

