
## ColumnComparisonDailyMonitoringChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|self||Dict[string, [ColumnComparisonDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columncomparisondailymonitoringchecksspec)]|


___

## CustomCheckSpecMap
Dictionary of custom checks indexed by a check name.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|self||Dict[string, [CustomCheckSpec](../../reference/yaml/profiling/table-profiling-checks.md#customcheckspec)]|


___

## ColumnDailyMonitoringCheckCategoriesSpec
Container of column level daily monitoring checks. Contains categories of daily monitoring checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[nulls](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnnullsdailymonitoringchecksspec)|Daily monitoring checks of nulls in the column|[ColumnNullsDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnnullsdailymonitoringchecksspec)|
|[uniqueness](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnuniquenessdailymonitoringchecksspec)|Daily monitoring checks of uniqueness in the column|[ColumnUniquenessDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnuniquenessdailymonitoringchecksspec)|
|[accepted_values](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnacceptedvaluesdailymonitoringchecksspec)|Configuration of accepted values checks on a column level|[ColumnAcceptedValuesDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnacceptedvaluesdailymonitoringchecksspec)|
|[text](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columntextdailymonitoringchecksspec)|Daily monitoring checks of text values in the column|[ColumnTextDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columntextdailymonitoringchecksspec)|
|[blanks](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnblanksdailymonitoringchecksspec)|Configuration of column level checks that detect blank and whitespace values|[ColumnBlanksDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnblanksdailymonitoringchecksspec)|
|[patterns](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnpatternsdailymonitoringchecksspec)|Daily monitoring checks of pattern matching on a column level|[ColumnPatternsDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnpatternsdailymonitoringchecksspec)|
|[pii](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnpiidailymonitoringchecksspec)|Daily monitoring checks of Personal Identifiable Information (PII) in the column|[ColumnPiiDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnpiidailymonitoringchecksspec)|
|[numeric](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnnumericdailymonitoringchecksspec)|Daily monitoring checks of numeric values in the column|[ColumnNumericDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnnumericdailymonitoringchecksspec)|
|[anomaly](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnanomalydailymonitoringchecksspec)|Daily monitoring checks of anomalies in numeric columns|[ColumnAnomalyDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnanomalydailymonitoringchecksspec)|
|[datetime](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columndatetimedailymonitoringchecksspec)|Daily monitoring checks of datetime in the column|[ColumnDatetimeDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columndatetimedailymonitoringchecksspec)|
|[bool](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnbooldailymonitoringchecksspec)|Daily monitoring checks of booleans in the column|[ColumnBoolDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnbooldailymonitoringchecksspec)|
|[integrity](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnintegritydailymonitoringchecksspec)|Daily monitoring checks of integrity in the column|[ColumnIntegrityDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnintegritydailymonitoringchecksspec)|
|[accuracy](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnaccuracydailymonitoringchecksspec)|Daily monitoring checks of accuracy in the column|[ColumnAccuracyDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnaccuracydailymonitoringchecksspec)|
|[custom_sql](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columncustomsqldailymonitoringchecksspec)|Daily monitoring checks of custom SQL checks in the column|[ColumnCustomSqlDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columncustomsqldailymonitoringchecksspec)|
|[datatype](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columndatatypedailymonitoringchecksspec)|Daily monitoring checks of datatype in the column|[ColumnDatatypeDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columndatatypedailymonitoringchecksspec)|
|[schema](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnschemadailymonitoringchecksspec)|Daily monitoring column schema checks|[ColumnSchemaDailyMonitoringChecksSpec](../../reference/yaml/monitoring/column-daily-monitoring-checks.md#columnschemadailymonitoringchecksspec)|
|[comparisons](#columncomparisondailymonitoringchecksspecmap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonDailyMonitoringChecksSpecMap](#columncomparisondailymonitoringchecksspecmap)|
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](#customcheckspecmap)|


___

## ColumnComparisonDailyPartitionedChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|self||Dict[string, [ColumnComparisonDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columncomparisondailypartitionedchecksspec)]|


___

## ColumnDailyPartitionedCheckCategoriesSpec
Container of data quality partitioned checks on a column level that are checking numeric values at a daily level.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[nulls](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnnullsdailypartitionedchecksspec)|Daily partitioned checks of nulls in the column|[ColumnNullsDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnnullsdailypartitionedchecksspec)|
|[uniqueness](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnuniquenessdailypartitionedchecksspec)|Daily partitioned checks of uniqueness in the column|[ColumnUniquenessDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnuniquenessdailypartitionedchecksspec)|
|[accepted_values](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnacceptedvaluesdailypartitionedchecksspec)|Configuration of accepted values checks on a column level|[ColumnAcceptedValuesDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnacceptedvaluesdailypartitionedchecksspec)|
|[text](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columntextdailypartitionedchecksspec)|Daily partitioned checks of text values in the column|[ColumnTextDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columntextdailypartitionedchecksspec)|
|[blanks](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnblanksdailypartitionedchecksspec)|Configuration of column level checks that detect blank and whitespace values|[ColumnBlanksDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnblanksdailypartitionedchecksspec)|
|[patterns](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnpatternsdailypartitionedchecksspec)|Daily partitioned pattern match checks on a column level|[ColumnPatternsDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnpatternsdailypartitionedchecksspec)|
|[pii](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnpiidailypartitionedchecksspec)|Daily partitioned checks of Personal Identifiable Information (PII) in the column|[ColumnPiiDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnpiidailypartitionedchecksspec)|
|[numeric](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnnumericdailypartitionedchecksspec)|Daily partitioned checks of numeric values in the column|[ColumnNumericDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnnumericdailypartitionedchecksspec)|
|[anomaly](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnanomalydailypartitionedchecksspec)|Daily partitioned checks for anomalies in numeric columns|[ColumnAnomalyDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnanomalydailypartitionedchecksspec)|
|[datetime](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columndatetimedailypartitionedchecksspec)|Daily partitioned checks of datetime in the column|[ColumnDatetimeDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columndatetimedailypartitionedchecksspec)|
|[bool](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnbooldailypartitionedchecksspec)|Daily partitioned checks for booleans in the column|[ColumnBoolDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnbooldailypartitionedchecksspec)|
|[integrity](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnintegritydailypartitionedchecksspec)|Daily partitioned checks for integrity in the column|[ColumnIntegrityDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columnintegritydailypartitionedchecksspec)|
|[custom_sql](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columncustomsqldailypartitionedchecksspec)|Daily partitioned checks using custom SQL expressions evaluated on the column|[ColumnCustomSqlDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columncustomsqldailypartitionedchecksspec)|
|[datatype](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columndatatypedailypartitionedchecksspec)|Daily partitioned checks for datatype in the column|[ColumnDatatypeDailyPartitionedChecksSpec](../../reference/yaml/partitioned/column-daily-partitioned-checks.md#columndatatypedailypartitionedchecksspec)|
|[comparisons](#columncomparisondailypartitionedchecksspecmap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonDailyPartitionedChecksSpecMap](#columncomparisondailypartitionedchecksspecmap)|
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](#customcheckspecmap)|


___

## PhysicalTableName
Physical table name that is a combination of a schema name and a physical table name (without any quoting or escaping).


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|schema_name|Schema name|string|
|table_name|Table name|string|


___

## ColumnListModel
Column list model that returns the basic fields from a column specification, excluding nested nodes like a list of activated checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|[table](#physicaltablename)|Physical table name including the schema and table names.|[PhysicalTableName](#physicaltablename)|
|column_name|Column names.|string|
|sql_expression|SQL expression.|string|
|column_hash|Column hash that identifies the column using a unique hash code.|long|
|disabled|Disables all data quality checks on the column. Data quality checks will not be executed.|boolean|
|has_any_configured_checks|True when the column has any checks configured.|boolean|
|has_any_configured_profiling_checks|True when the column has any profiling checks configured.|boolean|
|has_any_configured_monitoring_checks|True when the column has any monitoring checks configured.|boolean|
|has_any_configured_partition_checks|True when the column has any partition checks configured.|boolean|
|[type_snapshot](../../reference/yaml/TableYaml.md#columntypesnapshotspec)|Column data type that was retrieved when the table metadata was imported.|[ColumnTypeSnapshotSpec](../../reference/yaml/TableYaml.md#columntypesnapshotspec)|
|[run_checks_job_template](./Common.md#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run all checks within this column.|[CheckSearchFilters](./Common.md#checksearchfilters)|
|[run_profiling_checks_job_template](./Common.md#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run profiling checks within this column.|[CheckSearchFilters](./Common.md#checksearchfilters)|
|[run_monitoring_checks_job_template](./Common.md#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run monitoring checks within this column.|[CheckSearchFilters](./Common.md#checksearchfilters)|
|[run_partition_checks_job_template](./Common.md#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run partition partitioned checks within this column.|[CheckSearchFilters](./Common.md#checksearchfilters)|
|[collect_statistics_job_template](./jobs.md#StatisticsCollectorSearchFilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collector within this column.|[StatisticsCollectorSearchFilters](./jobs.md#StatisticsCollectorSearchFilters)|
|[data_clean_job_template](./jobs.md#DeleteStoredDataQueueJobParameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this column.|[DeleteStoredDataQueueJobParameters](./jobs.md#DeleteStoredDataQueueJobParameters)|
|can_edit|Boolean flag that decides if the current user can update or delete the column.|boolean|
|can_collect_statistics|Boolean flag that decides if the current user can collect statistics.|boolean|
|can_run_checks|Boolean flag that decides if the current user can run checks.|boolean|
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean|


___

## ColumnModel
Table model that returns the specification of a single column in the REST Api.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|[table](./columns.md#physicaltablename)|Physical table name including the schema and table names.|[PhysicalTableName](./columns.md#physicaltablename)|
|column_name|Column name.|string|
|column_hash|Column hash that identifies the column using a unique hash code.|long|
|[spec](../../reference/yaml/TableYaml.md#columnspec)|Full column specification.|[ColumnSpec](../../reference/yaml/TableYaml.md#columnspec)|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|
|yaml_parsing_error|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|string|


___

## ColumnComparisonMonthlyMonitoringChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|self||Dict[string, [ColumnComparisonMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columncomparisonmonthlymonitoringchecksspec)]|


___

## ColumnMonthlyMonitoringCheckCategoriesSpec
Container of column level monthly monitoring checks. Contains categories of monthly monitoring checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[nulls](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnnullsmonthlymonitoringchecksspec)|Monthly monitoring checks of nulls in the column|[ColumnNullsMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnnullsmonthlymonitoringchecksspec)|
|[uniqueness](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnuniquenessmonthlymonitoringchecksspec)|Monthly monitoring checks of uniqueness in the column|[ColumnUniquenessMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnuniquenessmonthlymonitoringchecksspec)|
|[accepted_values](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnacceptedvaluesmonthlymonitoringchecksspec)|Configuration of accepted values checks on a column level|[ColumnAcceptedValuesMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnacceptedvaluesmonthlymonitoringchecksspec)|
|[text](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columntextmonthlymonitoringchecksspec)|Monthly monitoring checks of text values in the column|[ColumnTextMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columntextmonthlymonitoringchecksspec)|
|[blanks](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnblanksmonthlymonitoringchecksspec)|Configuration of column level checks that detect blank and whitespace values|[ColumnBlanksMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnblanksmonthlymonitoringchecksspec)|
|[patterns](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnpatternsmonthlymonitoringchecksspec)|Monthly monitoring checks of pattern matching on a column level|[ColumnPatternsMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnpatternsmonthlymonitoringchecksspec)|
|[pii](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnpiimonthlymonitoringchecksspec)|Monthly monitoring checks of Personal Identifiable Information (PII) in the column|[ColumnPiiMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnpiimonthlymonitoringchecksspec)|
|[numeric](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnnumericmonthlymonitoringchecksspec)|Monthly monitoring checks of numeric values in the column|[ColumnNumericMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnnumericmonthlymonitoringchecksspec)|
|[anomaly](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnanomalymonthlymonitoringchecksspec)|Monthly monitoring checks of anomalies in numeric columns|[ColumnAnomalyMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnanomalymonthlymonitoringchecksspec)|
|[datetime](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columndatetimemonthlymonitoringchecksspec)|Monthly monitoring checks of datetime in the column|[ColumnDatetimeMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columndatetimemonthlymonitoringchecksspec)|
|[bool](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnboolmonthlymonitoringchecksspec)|Monthly monitoring checks of booleans in the column|[ColumnBoolMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnboolmonthlymonitoringchecksspec)|
|[integrity](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnintegritymonthlymonitoringchecksspec)|Monthly monitoring checks of integrity in the column|[ColumnIntegrityMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnintegritymonthlymonitoringchecksspec)|
|[accuracy](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnaccuracymonthlymonitoringchecksspec)|Monthly monitoring checks of accuracy in the column|[ColumnAccuracyMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnaccuracymonthlymonitoringchecksspec)|
|[custom_sql](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columncustomsqlmonthlymonitoringchecksspec)|Monthly monitoring checks of custom SQL checks in the column|[ColumnCustomSqlMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columncustomsqlmonthlymonitoringchecksspec)|
|[datatype](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columndatatypemonthlymonitoringchecksspec)|Monthly monitoring checks of datatype in the column|[ColumnDatatypeMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columndatatypemonthlymonitoringchecksspec)|
|[schema](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnschemamonthlymonitoringchecksspec)|Monthly monitoring column schema checks|[ColumnSchemaMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/column-monthly-monitoring-checks.md#columnschemamonthlymonitoringchecksspec)|
|[comparisons](#columncomparisonmonthlymonitoringchecksspecmap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonMonthlyMonitoringChecksSpecMap](#columncomparisonmonthlymonitoringchecksspecmap)|
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](#customcheckspecmap)|


___

## ColumnComparisonMonthlyPartitionedChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|self||Dict[string, [ColumnComparisonMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columncomparisonmonthlypartitionedchecksspec)]|


___

## ColumnMonthlyPartitionedCheckCategoriesSpec
Container of data quality partitioned checks on a column level that are checking numeric values at a monthly level.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[nulls](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnnullsmonthlypartitionedchecksspec)|Monthly partitioned checks of nulls in the column|[ColumnNullsMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnnullsmonthlypartitionedchecksspec)|
|[uniqueness](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnuniquenessmonthlypartitionedchecksspec)|Monthly partitioned checks of uniqueness in the column|[ColumnUniquenessMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnuniquenessmonthlypartitionedchecksspec)|
|[accepted_values](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnacceptedvaluesmonthlypartitionedchecksspec)|Configuration of accepted values checks on a column level|[ColumnAcceptedValuesMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnacceptedvaluesmonthlypartitionedchecksspec)|
|[text](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columntextmonthlypartitionedchecksspec)|Monthly partitioned checks of text values in the column|[ColumnTextMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columntextmonthlypartitionedchecksspec)|
|[blanks](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnblanksmonthlypartitionedchecksspec)|Configuration of column level checks that detect blank and whitespace values|[ColumnBlanksMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnblanksmonthlypartitionedchecksspec)|
|[patterns](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnpatternsmonthlypartitionedchecksspec)|Monthly partitioned pattern match checks on a column level|[ColumnPatternsMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnpatternsmonthlypartitionedchecksspec)|
|[pii](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnpiimonthlypartitionedchecksspec)|Monthly partitioned checks of Personal Identifiable Information (PII) in the column|[ColumnPiiMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnpiimonthlypartitionedchecksspec)|
|[numeric](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnnumericmonthlypartitionedchecksspec)|Monthly partitioned checks of numeric values in the column|[ColumnNumericMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnnumericmonthlypartitionedchecksspec)|
|[anomaly](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnanomalymonthlypartitionedchecksspec)|Monthly partitioned checks for anomalies in numeric columns|[ColumnAnomalyMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnanomalymonthlypartitionedchecksspec)|
|[datetime](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columndatetimemonthlypartitionedchecksspec)|Monthly partitioned checks of datetime in the column|[ColumnDatetimeMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columndatetimemonthlypartitionedchecksspec)|
|[bool](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnboolmonthlypartitionedchecksspec)|Monthly partitioned checks for booleans in the column|[ColumnBoolMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnboolmonthlypartitionedchecksspec)|
|[integrity](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnintegritymonthlypartitionedchecksspec)|Monthly partitioned checks for integrity in the column|[ColumnIntegrityMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columnintegritymonthlypartitionedchecksspec)|
|[custom_sql](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columncustomsqlmonthlypartitionedchecksspec)|Monthly partitioned checks using custom SQL expressions evaluated on the column|[ColumnCustomSqlMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columncustomsqlmonthlypartitionedchecksspec)|
|[datatype](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columndatatypemonthlypartitionedchecksspec)|Monthly partitioned checks for datatype in the column|[ColumnDatatypeMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/column-monthly-partitioned-checks.md#columndatatypemonthlypartitionedchecksspec)|
|[comparisons](#columncomparisonmonthlypartitionedchecksspecmap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonMonthlyPartitionedChecksSpecMap](#columncomparisonmonthlypartitionedchecksspecmap)|
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](#customcheckspecmap)|


___

## ColumnComparisonProfilingChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|self||Dict[string, [ColumnComparisonProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columncomparisonprofilingchecksspec)]|


___

## ColumnProfilingCheckCategoriesSpec
Container of column level, preconfigured profiling checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[nulls](../../reference/yaml/profiling/column-profiling-checks.md#columnnullsprofilingchecksspec)|Configuration of column level checks that detect null values.|[ColumnNullsProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnnullsprofilingchecksspec)|
|[uniqueness](../../reference/yaml/profiling/column-profiling-checks.md#columnuniquenessprofilingchecksspec)|Configuration of uniqueness checks on a column level.|[ColumnUniquenessProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnuniquenessprofilingchecksspec)|
|[accepted_values](../../reference/yaml/profiling/column-profiling-checks.md#columnacceptedvaluesprofilingchecksspec)|Configuration of accepted values checks on a column level.|[ColumnAcceptedValuesProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnacceptedvaluesprofilingchecksspec)|
|[text](../../reference/yaml/profiling/column-profiling-checks.md#columntextprofilingchecksspec)|Configuration of column level checks that verify text values.|[ColumnTextProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columntextprofilingchecksspec)|
|[blanks](../../reference/yaml/profiling/column-profiling-checks.md#columnblanksprofilingchecksspec)|Configuration of column level checks that detect blank and whitespace values.|[ColumnBlanksProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnblanksprofilingchecksspec)|
|[patterns](../../reference/yaml/profiling/column-profiling-checks.md#columnpatternsprofilingchecksspec)|Configuration of pattern match checks on a column level.|[ColumnPatternsProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnpatternsprofilingchecksspec)|
|[pii](../../reference/yaml/profiling/column-profiling-checks.md#columnpiiprofilingchecksspec)|Configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnpiiprofilingchecksspec)|
|[numeric](../../reference/yaml/profiling/column-profiling-checks.md#columnnumericprofilingchecksspec)|Configuration of column level checks that verify numeric values.|[ColumnNumericProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnnumericprofilingchecksspec)|
|[anomaly](../../reference/yaml/profiling/column-profiling-checks.md#columnanomalyprofilingchecksspec)|Configuration of anomaly checks on a column level that detect anomalies in numeric columns.|[ColumnAnomalyProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnanomalyprofilingchecksspec)|
|[datetime](../../reference/yaml/profiling/column-profiling-checks.md#columndatetimeprofilingchecksspec)|Configuration of datetime checks on a column level.|[ColumnDatetimeProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columndatetimeprofilingchecksspec)|
|[bool](../../reference/yaml/profiling/column-profiling-checks.md#columnboolprofilingchecksspec)|Configuration of booleans checks on a column level.|[ColumnBoolProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnboolprofilingchecksspec)|
|[integrity](../../reference/yaml/profiling/column-profiling-checks.md#columnintegrityprofilingchecksspec)|Configuration of integrity checks on a column level.|[ColumnIntegrityProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnintegrityprofilingchecksspec)|
|[accuracy](../../reference/yaml/profiling/column-profiling-checks.md#columnaccuracyprofilingchecksspec)|Configuration of accuracy checks on a column level.|[ColumnAccuracyProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnaccuracyprofilingchecksspec)|
|[custom_sql](../../reference/yaml/profiling/column-profiling-checks.md#columncustomsqlprofilingchecksspec)|Configuration of SQL checks that use custom SQL aggregated expressions and SQL conditions in data quality checks.|[ColumnCustomSqlProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columncustomsqlprofilingchecksspec)|
|[datatype](../../reference/yaml/profiling/column-profiling-checks.md#columndatatypeprofilingchecksspec)|Configuration of datatype checks on a column level.|[ColumnDatatypeProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columndatatypeprofilingchecksspec)|
|[schema](../../reference/yaml/profiling/column-profiling-checks.md#columnschemaprofilingchecksspec)|Configuration of schema checks on a column level.|[ColumnSchemaProfilingChecksSpec](../../reference/yaml/profiling/column-profiling-checks.md#columnschemaprofilingchecksspec)|
|[comparisons](#columncomparisonprofilingchecksspecmap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonProfilingChecksSpecMap](#columncomparisonprofilingchecksspecmap)|
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](#customcheckspecmap)|


___

## ColumnStatisticsModel
Column model that returns the basic fields from a column specification and a summary of the most recent statistics collection.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|[table](./columns.md#physicaltablename)|Physical table name including the schema and table names.|[PhysicalTableName](./columns.md#physicaltablename)|
|column_name|Column name.|string|
|column_hash|Column hash that identifies the column using a unique hash code.|long|
|disabled|Disables all data quality checks on the column. Data quality checks will not be executed.|boolean|
|has_any_configured_checks|True when the column has any checks configured.|boolean|
|[type_snapshot](../../reference/yaml/TableYaml.md#columntypesnapshotspec)|Column data type that was retrieved when the table metadata was imported.|[ColumnTypeSnapshotSpec](../../reference/yaml/TableYaml.md#columntypesnapshotspec)|
|[collect_column_statistics_job_template](./jobs.md#StatisticsCollectorSearchFilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors for this column|[StatisticsCollectorSearchFilters](./jobs.md#StatisticsCollectorSearchFilters)|
|can_collect_statistics|Boolean flag that decides if the current user can collect statistics.|boolean|


___

## TableColumnsStatisticsModel
Model that returns a summary of the column statistics (the basic profiling results) for a single table, showing statistics for all columns.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|[table](./columns.md#physicaltablename)|Physical table name including the schema and table names.|[PhysicalTableName](./columns.md#physicaltablename)|
|[collect_column_statistics_job_template](./jobs.md#StatisticsCollectorSearchFilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors for all columns on this table.|[StatisticsCollectorSearchFilters](./jobs.md#StatisticsCollectorSearchFilters)|
|can_collect_statistics|Boolean flag that decides if the current user can collect statistics.|boolean|


___

