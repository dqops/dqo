---
title: DQOps REST API default_column_check_patterns models reference
---
# DQOps REST API default_column_check_patterns models reference
The references of all objects used by [default_column_check_patterns](../operations/default_column_check_patterns.md) REST API operations are listed below.


## DataTypeCategory
Enumeration of common data type categories of data types. The providers will use this information to answer
 which of their native data types matches a category. Some sensors (and profilers) cannot operate on some data types.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|numeric_integer<br/>numeric_decimal<br/>numeric_float<br/>datetime_timestamp<br/>datetime_datetime<br/>datetime_date<br/>datetime_time<br/>text<br/>clob<br/>json<br/>bool<br/>binary<br/>array<br/>other<br/>|

___

## TargetColumnPatternSpec
The configuration of a column pattern to match default column checks. Includes also the pattern for the target table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`column`</span>|The target column name filter. Accepts wildcards in the format: *id, *, c_*.|*string*|
|<span class="no-wrap-code">`data_type`</span>|The target column data type filter. Filters by a physical (database specific) data type name imported from the data source. Accepts wildcards in the format: *int, *, big*.|*string*|
|<span class="no-wrap-code">[`data_type_category`](#datatypecategory)</span>|The filter for a target data type category.|*[DataTypeCategory](#datatypecategory)*|
|<span class="no-wrap-code">`connection`</span>|The data source connection name filter. Accepts wildcards in the format: *conn, *, conn*.|*string*|
|<span class="no-wrap-code">`schema`</span>|The schema name filter. Accepts wildcards in the format: *_prod, *, pub*.|*string*|
|<span class="no-wrap-code">`table`</span>|The table name filter. Accepts wildcards in the format: *_customers, *, fact_*.|*string*|
|<span class="no-wrap-code">`stage`</span>|The table stage filter. Accepts wildcards in the format: *_landing, *, staging_*.|*string*|
|<span class="no-wrap-code">`table_priority`</span>|The maximum table priority (inclusive) for tables that are covered by the default checks.|*integer*|
|<span class="no-wrap-code">`label`</span>|The label filter. Accepts wildcards in the format: *_customers, *, fact_*. The label must be present on the connection or table.|*string*|


___

## DefaultColumnChecksPatternListModel
The listing model of column-level default check patterns that is returned by the REST API.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name.|*string*|
|<span class="no-wrap-code">`priority`</span>|The priority of the pattern. Patterns with lower values are applied before patterns with higher priority values.|*integer*|
|<span class="no-wrap-code">[`target_column`](#targetcolumnpatternspec)</span>|The filters for the target column.|*[TargetColumnPatternSpec](#targetcolumnpatternspec)*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|
|<span class="no-wrap-code">`yaml_parsing_error`</span>|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|*string*|


___

## ColumnMonitoringCheckCategoriesSpec
Container of column level monitoring, divided by the time window (daily, monthly, etc.)


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`daily`](./columns.md#columndailymonitoringcheckcategoriesspec)</span>|Configuration of daily monitoring evaluated at a column level.|*[ColumnDailyMonitoringCheckCategoriesSpec](./columns.md#columndailymonitoringcheckcategoriesspec)*|
|<span class="no-wrap-code">[`monthly`](./columns.md#columnmonthlymonitoringcheckcategoriesspec)</span>|Configuration of monthly monitoring evaluated at a column level.|*[ColumnMonthlyMonitoringCheckCategoriesSpec](./columns.md#columnmonthlymonitoringcheckcategoriesspec)*|


___

## ColumnPartitionedCheckCategoriesSpec
Container of column level partitioned checks, divided by the time window (daily, monthly, etc.)


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`daily`](./columns.md#columndailypartitionedcheckcategoriesspec)</span>|Configuration of day partitioned data quality checks evaluated at a column level.|*[ColumnDailyPartitionedCheckCategoriesSpec](./columns.md#columndailypartitionedcheckcategoriesspec)*|
|<span class="no-wrap-code">[`monthly`](./columns.md#columnmonthlypartitionedcheckcategoriesspec)</span>|Configuration of monthly partitioned data quality checks evaluated at a column level.|*[ColumnMonthlyPartitionedCheckCategoriesSpec](./columns.md#columnmonthlypartitionedcheckcategoriesspec)*|


___

## ColumnDefaultChecksPatternSpec
The default configuration of column-level data quality checks that are enabled as data observability checks to analyze basic measures and detect anomalies on columns.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`priority`</span>|The priority of the pattern. Patterns with lower values are applied before patterns with higher priority values.|*integer*|
|<span class="no-wrap-code">[`target`](./default_column_check_patterns.md#targetcolumnpatternspec)</span>|The target column filter that are filtering the column, table and connection on which the default checks are applied.|*[TargetColumnPatternSpec](./default_column_check_patterns.md#targetcolumnpatternspec)*|
|<span class="no-wrap-code">[`profiling_checks`](./columns.md#columnprofilingcheckcategoriesspec)</span>|Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.|*[ColumnProfilingCheckCategoriesSpec](./columns.md#columnprofilingcheckcategoriesspec)*|
|<span class="no-wrap-code">[`monitoring_checks`](#columnmonitoringcheckcategoriesspec)</span>|Configuration of table level monitoring checks. Monitoring checks are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A monitoring check stores only the most recent data quality check result for each period of time.|*[ColumnMonitoringCheckCategoriesSpec](#columnmonitoringcheckcategoriesspec)*|
|<span class="no-wrap-code">[`partitioned_checks`](#columnpartitionedcheckcategoriesspec)</span>|Configuration of table level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.|*[ColumnPartitionedCheckCategoriesSpec](#columnpartitionedcheckcategoriesspec)*|


___

## DefaultColumnChecksPatternModel
Default column-level checks pattern model that is returned by the REST API. Describes a configuration of data quality checks for a named pattern. DQOps applies these checks on columns that match the filter.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|
|<span class="no-wrap-code">[`pattern_spec`](#columndefaultcheckspatternspec)</span>|The default checks specification.|*[ColumnDefaultChecksPatternSpec](#columndefaultcheckspatternspec)*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|
|<span class="no-wrap-code">`yaml_parsing_error`</span>|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|*string*|


___

