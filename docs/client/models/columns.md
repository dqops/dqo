# DQOps REST API columns models reference
The references of all objects used by [columns](/docs/client/operations/columns.md) REST API operations are listed below.


## ColumnComparisonDailyMonitoringChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [ColumnComparisonDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columncomparisondailymonitoringchecksspec)]*|


___

## CustomCheckSpecMap
Dictionary of custom checks indexed by a check name.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [CustomCheckSpec](/docs/reference/yaml/profiling/table-profiling-checks/#customcheckspec)]*|


___

## ColumnDailyMonitoringCheckCategoriesSpec
Container of column level daily monitoring checks. Contains categories of daily monitoring checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`nulls`](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnnullsdailymonitoringchecksspec)</span>|Daily monitoring checks of nulls in the column|*[ColumnNullsDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnnullsdailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`uniqueness`](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnuniquenessdailymonitoringchecksspec)</span>|Daily monitoring checks of uniqueness in the column|*[ColumnUniquenessDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnuniquenessdailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`accepted_values`](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnacceptedvaluesdailymonitoringchecksspec)</span>|Configuration of accepted values checks on a column level|*[ColumnAcceptedValuesDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnacceptedvaluesdailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`text`](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columntextdailymonitoringchecksspec)</span>|Daily monitoring checks of text values in the column|*[ColumnTextDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columntextdailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`whitespace`](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnwhitespacedailymonitoringchecksspec)</span>|Configuration of column level checks that detect blank and whitespace values|*[ColumnWhitespaceDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnwhitespacedailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`conversions`](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnconversionsdailymonitoringchecksspec)</span>|Configuration of conversion testing checks on a column level.|*[ColumnConversionsDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnconversionsdailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`patterns`](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnpatternsdailymonitoringchecksspec)</span>|Daily monitoring checks of pattern matching on a column level|*[ColumnPatternsDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnpatternsdailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`pii`](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnpiidailymonitoringchecksspec)</span>|Daily monitoring checks of Personal Identifiable Information (PII) in the column|*[ColumnPiiDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnpiidailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`numeric`](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnnumericdailymonitoringchecksspec)</span>|Daily monitoring checks of numeric values in the column|*[ColumnNumericDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnnumericdailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`anomaly`](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnanomalydailymonitoringchecksspec)</span>|Daily monitoring checks of anomalies in numeric columns|*[ColumnAnomalyDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnanomalydailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`datetime`](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columndatetimedailymonitoringchecksspec)</span>|Daily monitoring checks of datetime in the column|*[ColumnDatetimeDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columndatetimedailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`bool`](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnbooldailymonitoringchecksspec)</span>|Daily monitoring checks of booleans in the column|*[ColumnBoolDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnbooldailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`integrity`](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnintegritydailymonitoringchecksspec)</span>|Daily monitoring checks of integrity in the column|*[ColumnIntegrityDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnintegritydailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`accuracy`](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnaccuracydailymonitoringchecksspec)</span>|Daily monitoring checks of accuracy in the column|*[ColumnAccuracyDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnaccuracydailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columncustomsqldailymonitoringchecksspec)</span>|Daily monitoring checks of custom SQL checks in the column|*[ColumnCustomSqlDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columncustomsqldailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`datatype`](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columndatatypedailymonitoringchecksspec)</span>|Daily monitoring checks of datatype in the column|*[ColumnDatatypeDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columndatatypedailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`schema`](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnschemadailymonitoringchecksspec)</span>|Daily monitoring column schema checks|*[ColumnSchemaDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columnschemadailymonitoringchecksspec)*|
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
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [ColumnComparisonDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columncomparisondailypartitionedchecksspec)]*|


___

## ColumnDailyPartitionedCheckCategoriesSpec
Container of data quality partitioned checks on a column level that are checking numeric values at a daily level.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`nulls`](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnnullsdailypartitionedchecksspec)</span>|Daily partitioned checks of nulls in the column|*[ColumnNullsDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnnullsdailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`uniqueness`](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnuniquenessdailypartitionedchecksspec)</span>|Daily partitioned checks of uniqueness in the column|*[ColumnUniquenessDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnuniquenessdailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`accepted_values`](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnacceptedvaluesdailypartitionedchecksspec)</span>|Configuration of accepted values checks on a column level|*[ColumnAcceptedValuesDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnacceptedvaluesdailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`text`](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columntextdailypartitionedchecksspec)</span>|Daily partitioned checks of text values in the column|*[ColumnTextDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columntextdailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`whitespace`](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnwhitespacedailypartitionedchecksspec)</span>|Configuration of column level checks that detect blank and whitespace values|*[ColumnWhitespaceDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnwhitespacedailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`conversions`](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnconversionsdailypartitionedchecksspec)</span>|Configuration of conversion testing checks on a column level.|*[ColumnConversionsDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnconversionsdailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`patterns`](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnpatternsdailypartitionedchecksspec)</span>|Daily partitioned pattern match checks on a column level|*[ColumnPatternsDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnpatternsdailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`pii`](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnpiidailypartitionedchecksspec)</span>|Daily partitioned checks of Personal Identifiable Information (PII) in the column|*[ColumnPiiDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnpiidailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`numeric`](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnnumericdailypartitionedchecksspec)</span>|Daily partitioned checks of numeric values in the column|*[ColumnNumericDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnnumericdailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`anomaly`](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnanomalydailypartitionedchecksspec)</span>|Daily partitioned checks for anomalies in numeric columns|*[ColumnAnomalyDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnanomalydailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`datetime`](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columndatetimedailypartitionedchecksspec)</span>|Daily partitioned checks of datetime in the column|*[ColumnDatetimeDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columndatetimedailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`bool`](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnbooldailypartitionedchecksspec)</span>|Daily partitioned checks for booleans in the column|*[ColumnBoolDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnbooldailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`integrity`](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnintegritydailypartitionedchecksspec)</span>|Daily partitioned checks for integrity in the column|*[ColumnIntegrityDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columnintegritydailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columncustomsqldailypartitionedchecksspec)</span>|Daily partitioned checks using custom SQL expressions evaluated on the column|*[ColumnCustomSqlDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columncustomsqldailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`datatype`](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columndatatypedailypartitionedchecksspec)</span>|Daily partitioned checks for datatype in the column|*[ColumnDatatypeDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columndatatypedailypartitionedchecksspec)*|
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
|<span class="no-wrap-code">[`type_snapshot`](/docs/reference/yaml/TableYaml/#columntypesnapshotspec)</span>|Column data type that was retrieved when the table metadata was imported.|*[ColumnTypeSnapshotSpec](/docs/reference/yaml/TableYaml/#columntypesnapshotspec)*|
|<span class="no-wrap-code">[`run_checks_job_template`](/docs/client/models/common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run all checks within this column.|*[CheckSearchFilters](/docs/client/models/common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_profiling_checks_job_template`](/docs/client/models/common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run profiling checks within this column.|*[CheckSearchFilters](/docs/client/models/common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_monitoring_checks_job_template`](/docs/client/models/common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run monitoring checks within this column.|*[CheckSearchFilters](/docs/client/models/common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_partition_checks_job_template`](/docs/client/models/common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run partition partitioned checks within this column.|*[CheckSearchFilters](/docs/client/models/common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`collect_statistics_job_template`](/docs/client/models/jobs.md#statisticscollectorsearchfilters)</span>|Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collector within this column.|*[StatisticsCollectorSearchFilters](/docs/client/models/jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">[`data_clean_job_template`](/docs/client/models/jobs.md#deletestoreddataqueuejobparameters)</span>|Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this column.|*[DeleteStoredDataQueueJobParameters](/docs/client/models/jobs.md#deletestoreddataqueuejobparameters)*|
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
|<span class="no-wrap-code">[`table`](/docs/client/models/columns.md#physicaltablename)</span>|Physical table name including the schema and table names.|*[PhysicalTableName](/docs/client/models/columns.md#physicaltablename)*|
|<span class="no-wrap-code">`column_name`</span>|Column name.|*string*|
|<span class="no-wrap-code">`column_hash`</span>|Column hash that identifies the column using a unique hash code.|*long*|
|<span class="no-wrap-code">[`spec`](/docs/reference/yaml/TableYaml/#columnspec)</span>|Full column specification.|*[ColumnSpec](/docs/reference/yaml/TableYaml/#columnspec)*|
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
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [ColumnComparisonMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columncomparisonmonthlymonitoringchecksspec)]*|


___

## ColumnMonthlyMonitoringCheckCategoriesSpec
Container of column level monthly monitoring checks. Contains categories of monthly monitoring checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`nulls`](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnnullsmonthlymonitoringchecksspec)</span>|Monthly monitoring checks of nulls in the column|*[ColumnNullsMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnnullsmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`uniqueness`](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnuniquenessmonthlymonitoringchecksspec)</span>|Monthly monitoring checks of uniqueness in the column|*[ColumnUniquenessMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnuniquenessmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`accepted_values`](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnacceptedvaluesmonthlymonitoringchecksspec)</span>|Configuration of accepted values checks on a column level|*[ColumnAcceptedValuesMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnacceptedvaluesmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`text`](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columntextmonthlymonitoringchecksspec)</span>|Monthly monitoring checks of text values in the column|*[ColumnTextMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columntextmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`whitespace`](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnwhitespacemonthlymonitoringchecksspec)</span>|Configuration of column level checks that detect blank and whitespace values|*[ColumnWhitespaceMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnwhitespacemonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`conversions`](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnconversionsmonthlymonitoringchecksspec)</span>|Configuration of conversion testing checks on a column level.|*[ColumnConversionsMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnconversionsmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`patterns`](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnpatternsmonthlymonitoringchecksspec)</span>|Monthly monitoring checks of pattern matching on a column level|*[ColumnPatternsMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnpatternsmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`pii`](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnpiimonthlymonitoringchecksspec)</span>|Monthly monitoring checks of Personal Identifiable Information (PII) in the column|*[ColumnPiiMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnpiimonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`numeric`](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnnumericmonthlymonitoringchecksspec)</span>|Monthly monitoring checks of numeric values in the column|*[ColumnNumericMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnnumericmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`datetime`](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columndatetimemonthlymonitoringchecksspec)</span>|Monthly monitoring checks of datetime in the column|*[ColumnDatetimeMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columndatetimemonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`bool`](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnboolmonthlymonitoringchecksspec)</span>|Monthly monitoring checks of booleans in the column|*[ColumnBoolMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnboolmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`integrity`](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnintegritymonthlymonitoringchecksspec)</span>|Monthly monitoring checks of integrity in the column|*[ColumnIntegrityMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnintegritymonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`accuracy`](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnaccuracymonthlymonitoringchecksspec)</span>|Monthly monitoring checks of accuracy in the column|*[ColumnAccuracyMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnaccuracymonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columncustomsqlmonthlymonitoringchecksspec)</span>|Monthly monitoring checks of custom SQL checks in the column|*[ColumnCustomSqlMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columncustomsqlmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`datatype`](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columndatatypemonthlymonitoringchecksspec)</span>|Monthly monitoring checks of datatype in the column|*[ColumnDatatypeMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columndatatypemonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`schema`](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnschemamonthlymonitoringchecksspec)</span>|Monthly monitoring column schema checks|*[ColumnSchemaMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnschemamonthlymonitoringchecksspec)*|
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
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [ColumnComparisonMonthlyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columncomparisonmonthlypartitionedchecksspec)]*|


___

## ColumnMonthlyPartitionedCheckCategoriesSpec
Container of data quality partitioned checks on a column level that are checking numeric values at a monthly level.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`nulls`](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnnullsmonthlypartitionedchecksspec)</span>|Monthly partitioned checks of nulls in the column|*[ColumnNullsMonthlyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnnullsmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`uniqueness`](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnuniquenessmonthlypartitionedchecksspec)</span>|Monthly partitioned checks of uniqueness in the column|*[ColumnUniquenessMonthlyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnuniquenessmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`accepted_values`](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnacceptedvaluesmonthlypartitionedchecksspec)</span>|Configuration of accepted values checks on a column level|*[ColumnAcceptedValuesMonthlyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnacceptedvaluesmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`text`](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columntextmonthlypartitionedchecksspec)</span>|Monthly partitioned checks of text values in the column|*[ColumnTextMonthlyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columntextmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`whitespace`](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnwhitespacemonthlypartitionedchecksspec)</span>|Configuration of column level checks that detect blank and whitespace values|*[ColumnWhitespaceMonthlyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnwhitespacemonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`conversions`](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnconversionsmonthlypartitionedchecksspec)</span>|Configuration of conversion testing checks on a column level.|*[ColumnConversionsMonthlyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnconversionsmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`patterns`](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnpatternsmonthlypartitionedchecksspec)</span>|Monthly partitioned pattern match checks on a column level|*[ColumnPatternsMonthlyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnpatternsmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`pii`](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnpiimonthlypartitionedchecksspec)</span>|Monthly partitioned checks of Personal Identifiable Information (PII) in the column|*[ColumnPiiMonthlyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnpiimonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`numeric`](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnnumericmonthlypartitionedchecksspec)</span>|Monthly partitioned checks of numeric values in the column|*[ColumnNumericMonthlyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnnumericmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`datetime`](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columndatetimemonthlypartitionedchecksspec)</span>|Monthly partitioned checks of datetime in the column|*[ColumnDatetimeMonthlyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columndatetimemonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`bool`](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnboolmonthlypartitionedchecksspec)</span>|Monthly partitioned checks for booleans in the column|*[ColumnBoolMonthlyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnboolmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`integrity`](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnintegritymonthlypartitionedchecksspec)</span>|Monthly partitioned checks for integrity in the column|*[ColumnIntegrityMonthlyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnintegritymonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columncustomsqlmonthlypartitionedchecksspec)</span>|Monthly partitioned checks using custom SQL expressions evaluated on the column|*[ColumnCustomSqlMonthlyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columncustomsqlmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`datatype`](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columndatatypemonthlypartitionedchecksspec)</span>|Monthly partitioned checks for datatype in the column|*[ColumnDatatypeMonthlyPartitionedChecksSpec](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columndatatypemonthlypartitionedchecksspec)*|
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
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [ColumnComparisonProfilingChecksSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columncomparisonprofilingchecksspec)]*|


___

## ColumnProfilingCheckCategoriesSpec
Container of column level, preconfigured profiling checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`nulls`](/docs/reference/yaml/profiling/column-profiling-checks/#columnnullsprofilingchecksspec)</span>|Configuration of column level checks that detect null values.|*[ColumnNullsProfilingChecksSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columnnullsprofilingchecksspec)*|
|<span class="no-wrap-code">[`uniqueness`](/docs/reference/yaml/profiling/column-profiling-checks/#columnuniquenessprofilingchecksspec)</span>|Configuration of uniqueness checks on a column level.|*[ColumnUniquenessProfilingChecksSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columnuniquenessprofilingchecksspec)*|
|<span class="no-wrap-code">[`accepted_values`](/docs/reference/yaml/profiling/column-profiling-checks/#columnacceptedvaluesprofilingchecksspec)</span>|Configuration of accepted values checks on a column level.|*[ColumnAcceptedValuesProfilingChecksSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columnacceptedvaluesprofilingchecksspec)*|
|<span class="no-wrap-code">[`text`](/docs/reference/yaml/profiling/column-profiling-checks/#columntextprofilingchecksspec)</span>|Configuration of column level checks that verify text values.|*[ColumnTextProfilingChecksSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columntextprofilingchecksspec)*|
|<span class="no-wrap-code">[`whitespace`](/docs/reference/yaml/profiling/column-profiling-checks/#columnwhitespaceprofilingchecksspec)</span>|Configuration of column level checks that detect blank and whitespace values.|*[ColumnWhitespaceProfilingChecksSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columnwhitespaceprofilingchecksspec)*|
|<span class="no-wrap-code">[`conversions`](/docs/reference/yaml/profiling/column-profiling-checks/#columnconversionsprofilingchecksspec)</span>|Configuration of conversion testing checks on a column level.|*[ColumnConversionsProfilingChecksSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columnconversionsprofilingchecksspec)*|
|<span class="no-wrap-code">[`patterns`](/docs/reference/yaml/profiling/column-profiling-checks/#columnpatternsprofilingchecksspec)</span>|Configuration of pattern match checks on a column level.|*[ColumnPatternsProfilingChecksSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columnpatternsprofilingchecksspec)*|
|<span class="no-wrap-code">[`pii`](/docs/reference/yaml/profiling/column-profiling-checks/#columnpiiprofilingchecksspec)</span>|Configuration of Personal Identifiable Information (PII) checks on a column level.|*[ColumnPiiProfilingChecksSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columnpiiprofilingchecksspec)*|
|<span class="no-wrap-code">[`numeric`](/docs/reference/yaml/profiling/column-profiling-checks/#columnnumericprofilingchecksspec)</span>|Configuration of column level checks that verify numeric values.|*[ColumnNumericProfilingChecksSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columnnumericprofilingchecksspec)*|
|<span class="no-wrap-code">[`anomaly`](/docs/reference/yaml/profiling/column-profiling-checks/#columnanomalyprofilingchecksspec)</span>|Configuration of anomaly checks on a column level that detect anomalies in numeric columns.|*[ColumnAnomalyProfilingChecksSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columnanomalyprofilingchecksspec)*|
|<span class="no-wrap-code">[`datetime`](/docs/reference/yaml/profiling/column-profiling-checks/#columndatetimeprofilingchecksspec)</span>|Configuration of datetime checks on a column level.|*[ColumnDatetimeProfilingChecksSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columndatetimeprofilingchecksspec)*|
|<span class="no-wrap-code">[`bool`](/docs/reference/yaml/profiling/column-profiling-checks/#columnboolprofilingchecksspec)</span>|Configuration of booleans checks on a column level.|*[ColumnBoolProfilingChecksSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columnboolprofilingchecksspec)*|
|<span class="no-wrap-code">[`integrity`](/docs/reference/yaml/profiling/column-profiling-checks/#columnintegrityprofilingchecksspec)</span>|Configuration of integrity checks on a column level.|*[ColumnIntegrityProfilingChecksSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columnintegrityprofilingchecksspec)*|
|<span class="no-wrap-code">[`accuracy`](/docs/reference/yaml/profiling/column-profiling-checks/#columnaccuracyprofilingchecksspec)</span>|Configuration of accuracy checks on a column level.|*[ColumnAccuracyProfilingChecksSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columnaccuracyprofilingchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](/docs/reference/yaml/profiling/column-profiling-checks/#columncustomsqlprofilingchecksspec)</span>|Configuration of SQL checks that use custom SQL aggregated expressions and SQL conditions in data quality checks.|*[ColumnCustomSqlProfilingChecksSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columncustomsqlprofilingchecksspec)*|
|<span class="no-wrap-code">[`datatype`](/docs/reference/yaml/profiling/column-profiling-checks/#columndatatypeprofilingchecksspec)</span>|Configuration of datatype checks on a column level.|*[ColumnDatatypeProfilingChecksSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columndatatypeprofilingchecksspec)*|
|<span class="no-wrap-code">[`schema`](/docs/reference/yaml/profiling/column-profiling-checks/#columnschemaprofilingchecksspec)</span>|Configuration of schema checks on a column level.|*[ColumnSchemaProfilingChecksSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columnschemaprofilingchecksspec)*|
|<span class="no-wrap-code">[`comparisons`](#columncomparisonprofilingchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[ColumnComparisonProfilingChecksSpecMap](#columncomparisonprofilingchecksspecmap)*|
|<span class="no-wrap-code">[`custom`](#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](#customcheckspecmap)*|


___

## ColumnStatisticsModel
Column model that returns the basic fields from a column specification and a summary of the most recent statistics collection.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">[`table`](/docs/client/models/columns.md#physicaltablename)</span>|Physical table name including the schema and table names.|*[PhysicalTableName](/docs/client/models/columns.md#physicaltablename)*|
|<span class="no-wrap-code">`column_name`</span>|Column name.|*string*|
|<span class="no-wrap-code">`column_hash`</span>|Column hash that identifies the column using a unique hash code.|*long*|
|<span class="no-wrap-code">`disabled`</span>|Disables all data quality checks on the column. Data quality checks will not be executed.|*boolean*|
|<span class="no-wrap-code">`has_any_configured_checks`</span>|True when the column has any checks configured.|*boolean*|
|<span class="no-wrap-code">[`type_snapshot`](/docs/reference/yaml/TableYaml/#columntypesnapshotspec)</span>|Column data type that was retrieved when the table metadata was imported.|*[ColumnTypeSnapshotSpec](/docs/reference/yaml/TableYaml/#columntypesnapshotspec)*|
|<span class="no-wrap-code">[`collect_column_statistics_job_template`](/docs/client/models/jobs.md#statisticscollectorsearchfilters)</span>|Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors for this column|*[StatisticsCollectorSearchFilters](/docs/client/models/jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">`can_collect_statistics`</span>|Boolean flag that decides if the current user can collect statistics.|*boolean*|


___

## TableColumnsStatisticsModel
Model that returns a summary of the column statistics (the basic profiling results) for a single table, showing statistics for all columns.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">[`table`](/docs/client/models/columns.md#physicaltablename)</span>|Physical table name including the schema and table names.|*[PhysicalTableName](/docs/client/models/columns.md#physicaltablename)*|
|<span class="no-wrap-code">[`collect_column_statistics_job_template`](/docs/client/models/jobs.md#statisticscollectorsearchfilters)</span>|Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors for all columns on this table.|*[StatisticsCollectorSearchFilters](/docs/client/models/jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">`can_collect_statistics`</span>|Boolean flag that decides if the current user can collect statistics.|*boolean*|


___

