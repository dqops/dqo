---
title: DQOps REST API columns models reference
---
# DQOps REST API columns models reference
The references of all objects used by [columns](../operations/columns.md) REST API operations are listed below.


## ColumnComparisonDailyMonitoringChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [ColumnComparisonDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columncomparisondailymonitoringchecksspec)]*|


___

## CustomCheckSpecMap
Dictionary of custom checks indexed by a check name.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [CustomCheckSpec](../../reference/yaml/profiling/table-profiling-checks.md#customcheckspec)]*|


___

## ColumnDailyMonitoringCheckCategoriesSpec
Container of column level daily monitoring checks. Contains categories of daily monitoring checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`nulls`](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnnullsdailymonitoringchecksspec)</span>|Daily monitoring checks of nulls in the column|*[ColumnNullsDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnnullsdailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`uniqueness`](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnuniquenessdailymonitoringchecksspec)</span>|Daily monitoring checks of uniqueness in the column|*[ColumnUniquenessDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnuniquenessdailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`accepted_values`](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnacceptedvaluesdailymonitoringchecksspec)</span>|Configuration of accepted values checks on a column level|*[ColumnAcceptedValuesDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnacceptedvaluesdailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`text`](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columntextdailymonitoringchecksspec)</span>|Daily monitoring checks of text values in the column|*[ColumnTextDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columntextdailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`whitespace`](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnwhitespacedailymonitoringchecksspec)</span>|Configuration of column level checks that detect blank and whitespace values|*[ColumnWhitespaceDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnwhitespacedailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`conversions`](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnconversionsdailymonitoringchecksspec)</span>|Configuration of conversion testing checks on a column level.|*[ColumnConversionsDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnconversionsdailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`patterns`](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnpatternsdailymonitoringchecksspec)</span>|Daily monitoring checks of pattern matching on a column level|*[ColumnPatternsDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnpatternsdailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`pii`](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnpiidailymonitoringchecksspec)</span>|Daily monitoring checks of Personal Identifiable Information (PII) in the column|*[ColumnPiiDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnpiidailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`numeric`](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnnumericdailymonitoringchecksspec)</span>|Daily monitoring checks of numeric values in the column|*[ColumnNumericDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnnumericdailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`anomaly`](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnanomalydailymonitoringchecksspec)</span>|Daily monitoring checks of anomalies in numeric columns|*[ColumnAnomalyDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnanomalydailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`datetime`](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columndatetimedailymonitoringchecksspec)</span>|Daily monitoring checks of datetime in the column|*[ColumnDatetimeDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columndatetimedailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`bool`](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnbooldailymonitoringchecksspec)</span>|Daily monitoring checks of booleans in the column|*[ColumnBoolDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnbooldailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`integrity`](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnintegritydailymonitoringchecksspec)</span>|Daily monitoring checks of integrity in the column|*[ColumnIntegrityDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnintegritydailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`accuracy`](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnaccuracydailymonitoringchecksspec)</span>|Daily monitoring checks of accuracy in the column|*[ColumnAccuracyDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnaccuracydailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columncustomsqldailymonitoringchecksspec)</span>|Daily monitoring checks of custom SQL checks in the column|*[ColumnCustomSqlDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columncustomsqldailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`datatype`](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columndatatypedailymonitoringchecksspec)</span>|Daily monitoring checks of datatype in the column|*[ColumnDatatypeDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columndatatypedailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`schema`](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnschemadailymonitoringchecksspec)</span>|Daily monitoring column schema checks|*[ColumnSchemaDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnschemadailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`comparisons`](#columncomparisondailymonitoringchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[ColumnComparisonDailyMonitoringChecksSpecMap](#columncomparisondailymonitoringchecksspecmap)*|
|<span class="no-wrap-code">[`custom`](#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](#customcheckspecmap)*|


___

## ColumnComparisonDailyPartitionedChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [ColumnComparisonDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columncomparisondailypartitionedchecksspec)]*|


___

## ColumnDailyPartitionedCheckCategoriesSpec
Container of data quality partitioned checks on a column level that are checking numeric values at a daily level.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`nulls`](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnnullsdailypartitionedchecksspec)</span>|Daily partitioned checks of nulls in the column|*[ColumnNullsDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnnullsdailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`uniqueness`](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnuniquenessdailypartitionedchecksspec)</span>|Daily partitioned checks of uniqueness in the column|*[ColumnUniquenessDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnuniquenessdailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`accepted_values`](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnacceptedvaluesdailypartitionedchecksspec)</span>|Configuration of accepted values checks on a column level|*[ColumnAcceptedValuesDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnacceptedvaluesdailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`text`](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columntextdailypartitionedchecksspec)</span>|Daily partitioned checks of text values in the column|*[ColumnTextDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columntextdailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`whitespace`](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnwhitespacedailypartitionedchecksspec)</span>|Configuration of column level checks that detect blank and whitespace values|*[ColumnWhitespaceDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnwhitespacedailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`conversions`](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnconversionsdailypartitionedchecksspec)</span>|Configuration of conversion testing checks on a column level.|*[ColumnConversionsDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnconversionsdailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`patterns`](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnpatternsdailypartitionedchecksspec)</span>|Daily partitioned pattern match checks on a column level|*[ColumnPatternsDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnpatternsdailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`pii`](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnpiidailypartitionedchecksspec)</span>|Daily partitioned checks of Personal Identifiable Information (PII) in the column|*[ColumnPiiDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnpiidailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`numeric`](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnnumericdailypartitionedchecksspec)</span>|Daily partitioned checks of numeric values in the column|*[ColumnNumericDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnnumericdailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`anomaly`](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnanomalydailypartitionedchecksspec)</span>|Daily partitioned checks for anomalies in numeric columns|*[ColumnAnomalyDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnanomalydailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`datetime`](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columndatetimedailypartitionedchecksspec)</span>|Daily partitioned checks of datetime in the column|*[ColumnDatetimeDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columndatetimedailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`bool`](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnbooldailypartitionedchecksspec)</span>|Daily partitioned checks for booleans in the column|*[ColumnBoolDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnbooldailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`integrity`](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnintegritydailypartitionedchecksspec)</span>|Daily partitioned checks for integrity in the column|*[ColumnIntegrityDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnintegritydailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columncustomsqldailypartitionedchecksspec)</span>|Daily partitioned checks using custom SQL expressions evaluated on the column|*[ColumnCustomSqlDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columncustomsqldailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`datatype`](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columndatatypedailypartitionedchecksspec)</span>|Daily partitioned checks for datatype in the column|*[ColumnDatatypeDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columndatatypedailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`comparisons`](#columncomparisondailypartitionedchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[ColumnComparisonDailyPartitionedChecksSpecMap](#columncomparisondailypartitionedchecksspecmap)*|
|<span class="no-wrap-code">[`custom`](#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](#customcheckspecmap)*|


___

## PhysicalTableName
Physical table name that is a combination of a schema name and a physical table name (without any quoting or escaping).


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|


___

## ColumnListModel
Column list model that returns the basic fields from a column specification, excluding nested nodes like a list of activated checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">[`table`](#physicaltablename)</span>|Physical table name including the schema and table names.|*[PhysicalTableName](#physicaltablename)*|
|<span class="no-wrap-code">`column_name`</span>|Column names.|*string*|
|<span class="no-wrap-code">`sql_expression`</span>|SQL expression.|*string*|
|<span class="no-wrap-code">`column_hash`</span>|Column hash that identifies the column using a unique hash code.|*long*|
|<span class="no-wrap-code">`disabled`</span>|Disables all data quality checks on the column. Data quality checks will not be executed.|*boolean*|
|<span class="no-wrap-code">`has_any_configured_checks`</span>|True when the column has any checks configured.|*boolean*|
|<span class="no-wrap-code">`has_any_configured_profiling_checks`</span>|True when the column has any profiling checks configured.|*boolean*|
|<span class="no-wrap-code">`has_any_configured_monitoring_checks`</span>|True when the column has any monitoring checks configured.|*boolean*|
|<span class="no-wrap-code">`has_any_configured_partition_checks`</span>|True when the column has any partition checks configured.|*boolean*|
|<span class="no-wrap-code">[`type_snapshot`](../../reference/yaml/TableYaml.md#columntypesnapshotspec)</span>|Column data type that was retrieved when the table metadata was imported.|*[ColumnTypeSnapshotSpec](../../reference/yaml/TableYaml.md#columntypesnapshotspec)*|
|<span class="no-wrap-code">[`run_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run all checks within this column.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_profiling_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run profiling checks within this column.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_monitoring_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run monitoring checks within this column.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_partition_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run partition partitioned checks within this column.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`collect_statistics_job_template`](./jobs.md#statisticscollectorsearchfilters)</span>|Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collector within this column.|*[StatisticsCollectorSearchFilters](./jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">[`data_clean_job_template`](./jobs.md#deletestoreddataqueuejobparameters)</span>|Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this column.|*[DeleteStoredDataQueueJobParameters](./jobs.md#deletestoreddataqueuejobparameters)*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete the column.|*boolean*|
|<span class="no-wrap-code">`can_collect_statistics`</span>|Boolean flag that decides if the current user can collect statistics.|*boolean*|
|<span class="no-wrap-code">`can_run_checks`</span>|Boolean flag that decides if the current user can run checks.|*boolean*|
|<span class="no-wrap-code">`can_delete_data`</span>|Boolean flag that decides if the current user can delete data (results).|*boolean*|


___

## ColumnModel
Table model that returns the specification of a single column in the REST Api.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">[`table`](./columns.md#physicaltablename)</span>|Physical table name including the schema and table names.|*[PhysicalTableName](./columns.md#physicaltablename)*|
|<span class="no-wrap-code">`column_name`</span>|Column name.|*string*|
|<span class="no-wrap-code">`column_hash`</span>|Column hash that identifies the column using a unique hash code.|*long*|
|<span class="no-wrap-code">[`spec`](../../reference/yaml/TableYaml.md#columnspec)</span>|Full column specification.|*[ColumnSpec](../../reference/yaml/TableYaml.md#columnspec)*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|
|<span class="no-wrap-code">`yaml_parsing_error`</span>|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|*string*|


___

## ColumnComparisonMonthlyMonitoringChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [ColumnComparisonMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columncomparisonmonthlymonitoringchecksspec)]*|


___

## ColumnMonthlyMonitoringCheckCategoriesSpec
Container of column level monthly monitoring checks. Contains categories of monthly monitoring checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`nulls`](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnnullsmonthlymonitoringchecksspec)</span>|Monthly monitoring checks of nulls in the column|*[ColumnNullsMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnnullsmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`uniqueness`](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnuniquenessmonthlymonitoringchecksspec)</span>|Monthly monitoring checks of uniqueness in the column|*[ColumnUniquenessMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnuniquenessmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`accepted_values`](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnacceptedvaluesmonthlymonitoringchecksspec)</span>|Configuration of accepted values checks on a column level|*[ColumnAcceptedValuesMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnacceptedvaluesmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`text`](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columntextmonthlymonitoringchecksspec)</span>|Monthly monitoring checks of text values in the column|*[ColumnTextMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columntextmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`whitespace`](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnwhitespacemonthlymonitoringchecksspec)</span>|Configuration of column level checks that detect blank and whitespace values|*[ColumnWhitespaceMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnwhitespacemonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`conversions`](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnconversionsmonthlymonitoringchecksspec)</span>|Configuration of conversion testing checks on a column level.|*[ColumnConversionsMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnconversionsmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`patterns`](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnpatternsmonthlymonitoringchecksspec)</span>|Monthly monitoring checks of pattern matching on a column level|*[ColumnPatternsMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnpatternsmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`pii`](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnpiimonthlymonitoringchecksspec)</span>|Monthly monitoring checks of Personal Identifiable Information (PII) in the column|*[ColumnPiiMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnpiimonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`numeric`](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnnumericmonthlymonitoringchecksspec)</span>|Monthly monitoring checks of numeric values in the column|*[ColumnNumericMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnnumericmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`datetime`](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columndatetimemonthlymonitoringchecksspec)</span>|Monthly monitoring checks of datetime in the column|*[ColumnDatetimeMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columndatetimemonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`bool`](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnboolmonthlymonitoringchecksspec)</span>|Monthly monitoring checks of booleans in the column|*[ColumnBoolMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnboolmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`integrity`](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnintegritymonthlymonitoringchecksspec)</span>|Monthly monitoring checks of integrity in the column|*[ColumnIntegrityMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnintegritymonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`accuracy`](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnaccuracymonthlymonitoringchecksspec)</span>|Monthly monitoring checks of accuracy in the column|*[ColumnAccuracyMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnaccuracymonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columncustomsqlmonthlymonitoringchecksspec)</span>|Monthly monitoring checks of custom SQL checks in the column|*[ColumnCustomSqlMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columncustomsqlmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`datatype`](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columndatatypemonthlymonitoringchecksspec)</span>|Monthly monitoring checks of datatype in the column|*[ColumnDatatypeMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columndatatypemonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`schema`](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnschemamonthlymonitoringchecksspec)</span>|Monthly monitoring column schema checks|*[ColumnSchemaMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnschemamonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`comparisons`](#columncomparisonmonthlymonitoringchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[ColumnComparisonMonthlyMonitoringChecksSpecMap](#columncomparisonmonthlymonitoringchecksspecmap)*|
|<span class="no-wrap-code">[`custom`](#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](#customcheckspecmap)*|


___

## ColumnComparisonMonthlyPartitionedChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [ColumnComparisonMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columncomparisonmonthlypartitionedchecksspec)]*|


___

## ColumnMonthlyPartitionedCheckCategoriesSpec
Container of data quality partitioned checks on a column level that are checking numeric values at a monthly level.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`nulls`](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnnullsmonthlypartitionedchecksspec)</span>|Monthly partitioned checks of nulls in the column|*[ColumnNullsMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnnullsmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`uniqueness`](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnuniquenessmonthlypartitionedchecksspec)</span>|Monthly partitioned checks of uniqueness in the column|*[ColumnUniquenessMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnuniquenessmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`accepted_values`](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnacceptedvaluesmonthlypartitionedchecksspec)</span>|Configuration of accepted values checks on a column level|*[ColumnAcceptedValuesMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnacceptedvaluesmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`text`](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columntextmonthlypartitionedchecksspec)</span>|Monthly partitioned checks of text values in the column|*[ColumnTextMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columntextmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`whitespace`](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnwhitespacemonthlypartitionedchecksspec)</span>|Configuration of column level checks that detect blank and whitespace values|*[ColumnWhitespaceMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnwhitespacemonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`conversions`](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnconversionsmonthlypartitionedchecksspec)</span>|Configuration of conversion testing checks on a column level.|*[ColumnConversionsMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnconversionsmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`patterns`](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnpatternsmonthlypartitionedchecksspec)</span>|Monthly partitioned pattern match checks on a column level|*[ColumnPatternsMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnpatternsmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`pii`](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnpiimonthlypartitionedchecksspec)</span>|Monthly partitioned checks of Personal Identifiable Information (PII) in the column|*[ColumnPiiMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnpiimonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`numeric`](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnnumericmonthlypartitionedchecksspec)</span>|Monthly partitioned checks of numeric values in the column|*[ColumnNumericMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnnumericmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`datetime`](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columndatetimemonthlypartitionedchecksspec)</span>|Monthly partitioned checks of datetime in the column|*[ColumnDatetimeMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columndatetimemonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`bool`](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnboolmonthlypartitionedchecksspec)</span>|Monthly partitioned checks for booleans in the column|*[ColumnBoolMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnboolmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`integrity`](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnintegritymonthlypartitionedchecksspec)</span>|Monthly partitioned checks for integrity in the column|*[ColumnIntegrityMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnintegritymonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columncustomsqlmonthlypartitionedchecksspec)</span>|Monthly partitioned checks using custom SQL expressions evaluated on the column|*[ColumnCustomSqlMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columncustomsqlmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`datatype`](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columndatatypemonthlypartitionedchecksspec)</span>|Monthly partitioned checks for datatype in the column|*[ColumnDatatypeMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columndatatypemonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`comparisons`](#columncomparisonmonthlypartitionedchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[ColumnComparisonMonthlyPartitionedChecksSpecMap](#columncomparisonmonthlypartitionedchecksspecmap)*|
|<span class="no-wrap-code">[`custom`](#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](#customcheckspecmap)*|


___

## ColumnComparisonProfilingChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [ColumnComparisonProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columncomparisonprofilingchecksspec)]*|


___

## ColumnProfilingCheckCategoriesSpec
Container of column level, preconfigured profiling checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`nulls`](../../reference/yaml/profiling/column-profiling-checks.md#columnnullsprofilingchecksspec)</span>|Configuration of column level checks that detect null values.|*[ColumnNullsProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnnullsprofilingchecksspec)*|
|<span class="no-wrap-code">[`uniqueness`](../../reference/yaml/profiling/column-profiling-checks.md#columnuniquenessprofilingchecksspec)</span>|Configuration of uniqueness checks on a column level.|*[ColumnUniquenessProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnuniquenessprofilingchecksspec)*|
|<span class="no-wrap-code">[`accepted_values`](../../reference/yaml/profiling/column-profiling-checks.md#columnacceptedvaluesprofilingchecksspec)</span>|Configuration of accepted values checks on a column level.|*[ColumnAcceptedValuesProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnacceptedvaluesprofilingchecksspec)*|
|<span class="no-wrap-code">[`text`](../../reference/yaml/profiling/column-profiling-checks.md#columntextprofilingchecksspec)</span>|Configuration of column level checks that verify text values.|*[ColumnTextProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columntextprofilingchecksspec)*|
|<span class="no-wrap-code">[`whitespace`](../../reference/yaml/profiling/column-profiling-checks.md#columnwhitespaceprofilingchecksspec)</span>|Configuration of column level checks that detect blank and whitespace values.|*[ColumnWhitespaceProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnwhitespaceprofilingchecksspec)*|
|<span class="no-wrap-code">[`conversions`](../../reference/yaml/profiling/column-profiling-checks.md#columnconversionsprofilingchecksspec)</span>|Configuration of conversion testing checks on a column level.|*[ColumnConversionsProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnconversionsprofilingchecksspec)*|
|<span class="no-wrap-code">[`patterns`](../../reference/yaml/profiling/column-profiling-checks.md#columnpatternsprofilingchecksspec)</span>|Configuration of pattern match checks on a column level.|*[ColumnPatternsProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnpatternsprofilingchecksspec)*|
|<span class="no-wrap-code">[`pii`](../../reference/yaml/profiling/column-profiling-checks.md#columnpiiprofilingchecksspec)</span>|Configuration of Personal Identifiable Information (PII) checks on a column level.|*[ColumnPiiProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnpiiprofilingchecksspec)*|
|<span class="no-wrap-code">[`numeric`](../../reference/yaml/profiling/column-profiling-checks.md#columnnumericprofilingchecksspec)</span>|Configuration of column level checks that verify numeric values.|*[ColumnNumericProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnnumericprofilingchecksspec)*|
|<span class="no-wrap-code">[`anomaly`](../../reference/yaml/profiling/column-profiling-checks.md#columnanomalyprofilingchecksspec)</span>|Configuration of anomaly checks on a column level that detect anomalies in numeric columns.|*[ColumnAnomalyProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnanomalyprofilingchecksspec)*|
|<span class="no-wrap-code">[`datetime`](../../reference/yaml/profiling/column-profiling-checks.md#columndatetimeprofilingchecksspec)</span>|Configuration of datetime checks on a column level.|*[ColumnDatetimeProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columndatetimeprofilingchecksspec)*|
|<span class="no-wrap-code">[`bool`](../../reference/yaml/profiling/column-profiling-checks.md#columnboolprofilingchecksspec)</span>|Configuration of booleans checks on a column level.|*[ColumnBoolProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnboolprofilingchecksspec)*|
|<span class="no-wrap-code">[`integrity`](../../reference/yaml/profiling/column-profiling-checks.md#columnintegrityprofilingchecksspec)</span>|Configuration of integrity checks on a column level.|*[ColumnIntegrityProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnintegrityprofilingchecksspec)*|
|<span class="no-wrap-code">[`accuracy`](../../reference/yaml/profiling/column-profiling-checks.md#columnaccuracyprofilingchecksspec)</span>|Configuration of accuracy checks on a column level.|*[ColumnAccuracyProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnaccuracyprofilingchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](../../reference/yaml/profiling/column-profiling-checks.md#columncustomsqlprofilingchecksspec)</span>|Configuration of SQL checks that use custom SQL aggregated expressions and SQL conditions in data quality checks.|*[ColumnCustomSqlProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columncustomsqlprofilingchecksspec)*|
|<span class="no-wrap-code">[`datatype`](../../reference/yaml/profiling/column-profiling-checks.md#columndatatypeprofilingchecksspec)</span>|Configuration of datatype checks on a column level.|*[ColumnDatatypeProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columndatatypeprofilingchecksspec)*|
|<span class="no-wrap-code">[`schema`](../../reference/yaml/profiling/column-profiling-checks.md#columnschemaprofilingchecksspec)</span>|Configuration of schema checks on a column level.|*[ColumnSchemaProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnschemaprofilingchecksspec)*|
|<span class="no-wrap-code">[`comparisons`](#columncomparisonprofilingchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[ColumnComparisonProfilingChecksSpecMap](#columncomparisonprofilingchecksspecmap)*|
|<span class="no-wrap-code">[`custom`](#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](#customcheckspecmap)*|


___

## ColumnStatisticsModel
Column model that returns the basic fields from a column specification and a summary of the most recent statistics collection.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">[`table`](./columns.md#physicaltablename)</span>|Physical table name including the schema and table names.|*[PhysicalTableName](./columns.md#physicaltablename)*|
|<span class="no-wrap-code">`column_name`</span>|Column name.|*string*|
|<span class="no-wrap-code">`column_hash`</span>|Column hash that identifies the column using a unique hash code.|*long*|
|<span class="no-wrap-code">`disabled`</span>|Disables all data quality checks on the column. Data quality checks will not be executed.|*boolean*|
|<span class="no-wrap-code">`has_any_configured_checks`</span>|True when the column has any checks configured.|*boolean*|
|<span class="no-wrap-code">[`type_snapshot`](../../reference/yaml/TableYaml.md#columntypesnapshotspec)</span>|Column data type that was retrieved when the table metadata was imported.|*[ColumnTypeSnapshotSpec](../../reference/yaml/TableYaml.md#columntypesnapshotspec)*|
|<span class="no-wrap-code">[`collect_column_statistics_job_template`](./jobs.md#statisticscollectorsearchfilters)</span>|Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors for this column|*[StatisticsCollectorSearchFilters](./jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">`can_collect_statistics`</span>|Boolean flag that decides if the current user can collect statistics.|*boolean*|


___

## TableColumnsStatisticsModel
Model that returns a summary of the column statistics (the basic profiling results) for a single table, showing statistics for all columns.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">[`table`](./columns.md#physicaltablename)</span>|Physical table name including the schema and table names.|*[PhysicalTableName](./columns.md#physicaltablename)*|
|<span class="no-wrap-code">[`collect_column_statistics_job_template`](./jobs.md#statisticscollectorsearchfilters)</span>|Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors for all columns on this table.|*[StatisticsCollectorSearchFilters](./jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">`can_collect_statistics`</span>|Boolean flag that decides if the current user can collect statistics.|*boolean*|


___

