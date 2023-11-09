
## ColumnComparisonDailyMonitoringChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|self||Dict[string, [ColumnComparisonDailyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columncomparisondailymonitoringchecksspec)]|


___  

## CustomCheckSpecMap  
Dictionary of custom checks indexed by a check name.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|self||Dict[string, [CustomCheckSpec](../../../reference/yaml/profiling/table-profiling-checks/#customcheckspec)]|


___  

## ColumnDailyMonitoringCheckCategoriesSpec  
Container of column level daily monitoring checks. Contains categories of daily monitoring checks.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[nulls](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnnullsdailymonitoringchecksspec)|Daily monitoring checks of nulls in the column|[ColumnNullsDailyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnnullsdailymonitoringchecksspec)|
|[numeric](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnnumericdailymonitoringchecksspec)|Daily monitoring checks of numeric in the column|[ColumnNumericDailyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnnumericdailymonitoringchecksspec)|
|[strings](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnstringsdailymonitoringchecksspec)|Daily monitoring checks of strings in the column|[ColumnStringsDailyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnstringsdailymonitoringchecksspec)|
|[uniqueness](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnuniquenessdailymonitoringchecksspec)|Daily monitoring checks of uniqueness in the column|[ColumnUniquenessDailyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnuniquenessdailymonitoringchecksspec)|
|[datetime](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columndatetimedailymonitoringchecksspec)|Daily monitoring checks of datetime in the column|[ColumnDatetimeDailyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columndatetimedailymonitoringchecksspec)|
|[pii](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnpiidailymonitoringchecksspec)|Daily monitoring checks of Personal Identifiable Information (PII) in the column|[ColumnPiiDailyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnpiidailymonitoringchecksspec)|
|[sql](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnsqldailymonitoringchecksspec)|Daily monitoring checks of custom SQL checks in the column|[ColumnSqlDailyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnsqldailymonitoringchecksspec)|
|[bool](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnbooldailymonitoringchecksspec)|Daily monitoring checks of booleans in the column|[ColumnBoolDailyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnbooldailymonitoringchecksspec)|
|[integrity](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnintegritydailymonitoringchecksspec)|Daily monitoring checks of integrity in the column|[ColumnIntegrityDailyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnintegritydailymonitoringchecksspec)|
|[accuracy](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnaccuracydailymonitoringchecksspec)|Daily monitoring checks of accuracy in the column|[ColumnAccuracyDailyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnaccuracydailymonitoringchecksspec)|
|[datatype](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columndatatypedailymonitoringchecksspec)|Daily monitoring checks of datatype in the column|[ColumnDatatypeDailyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columndatatypedailymonitoringchecksspec)|
|[anomaly](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnanomalydailymonitoringchecksspec)|Daily monitoring checks of anomaly in the column|[ColumnAnomalyDailyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnanomalydailymonitoringchecksspec)|
|[schema](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnschemadailymonitoringchecksspec)|Daily monitoring column schema checks|[ColumnSchemaDailyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-daily-monitoring-checks/#columnschemadailymonitoringchecksspec)|
|[comparisons](#ColumnComparisonDailyMonitoringChecksSpecMap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonDailyMonitoringChecksSpecMap](#ColumnComparisonDailyMonitoringChecksSpecMap)|
|[custom](#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](#CustomCheckSpecMap)|


___  

## ColumnComparisonDailyPartitionedChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|self||Dict[string, [ColumnComparisonDailyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columncomparisondailypartitionedchecksspec)]|


___  

## ColumnDailyPartitionedCheckCategoriesSpec  
Container of data quality partitioned checks on a column level that are checking numeric values at a daily level.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[nulls](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnnullsdailypartitionedchecksspec)|Daily partitioned checks of nulls in the column|[ColumnNullsDailyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnnullsdailypartitionedchecksspec)|
|[numeric](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnnumericdailypartitionedchecksspec)|Daily partitioned checks of numeric in the column|[ColumnNumericDailyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnnumericdailypartitionedchecksspec)|
|[strings](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnstringsdailypartitionedchecksspec)|Daily partitioned checks of strings in the column|[ColumnStringsDailyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnstringsdailypartitionedchecksspec)|
|[uniqueness](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnuniquenessdailypartitionedchecksspec)|Daily partitioned checks of uniqueness in the column|[ColumnUniquenessDailyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnuniquenessdailypartitionedchecksspec)|
|[datetime](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columndatetimedailypartitionedchecksspec)|Daily partitioned checks of datetime in the column|[ColumnDatetimeDailyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columndatetimedailypartitionedchecksspec)|
|[pii](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnpiidailypartitionedchecksspec)|Daily partitioned checks of Personal Identifiable Information (PII) in the column|[ColumnPiiDailyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnpiidailypartitionedchecksspec)|
|[sql](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnsqldailypartitionedchecksspec)|Daily partitioned checks using custom SQL expressions evaluated on the column|[ColumnSqlDailyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnsqldailypartitionedchecksspec)|
|[bool](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnbooldailypartitionedchecksspec)|Daily partitioned checks for booleans in the column|[ColumnBoolDailyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnbooldailypartitionedchecksspec)|
|[integrity](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnintegritydailypartitionedchecksspec)|Daily partitioned checks for integrity in the column|[ColumnIntegrityDailyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnintegritydailypartitionedchecksspec)|
|[accuracy](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnaccuracydailypartitionedchecksspec)|Daily partitioned checks for accuracy in the column|[ColumnAccuracyDailyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnaccuracydailypartitionedchecksspec)|
|[datatype](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columndatatypedailypartitionedchecksspec)|Daily partitioned checks for datatype in the column|[ColumnDatatypeDailyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columndatatypedailypartitionedchecksspec)|
|[anomaly](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnanomalydailypartitionedchecksspec)|Daily partitioned checks for anomaly in the column|[ColumnAnomalyDailyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-daily-partitioned-checks/#columnanomalydailypartitionedchecksspec)|
|[comparisons](#ColumnComparisonDailyPartitionedChecksSpecMap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonDailyPartitionedChecksSpecMap](#ColumnComparisonDailyPartitionedChecksSpecMap)|
|[custom](#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](#CustomCheckSpecMap)|


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
|[table](#PhysicalTableName)|Physical table name including the schema and table names.|[PhysicalTableName](#PhysicalTableName)|
|column_name|Column names.|string|
|sql_expression|SQL expression.|string|
|column_hash|Column hash that identifies the column using a unique hash code.|long|
|disabled|Disables all data quality checks on the column. Data quality checks will not be executed.|boolean|
|has_any_configured_checks|True when the column has any checks configured.|boolean|
|has_any_configured_profiling_checks|True when the column has any profiling checks configured.|boolean|
|has_any_configured_monitoring_checks|True when the column has any monitoring checks configured.|boolean|
|has_any_configured_partition_checks|True when the column has any partition checks configured.|boolean|
|[type_snapshot](../../../reference/yaml/TableYaml/#columntypesnapshotspec)|Column data type that was retrieved when the table metadata was imported.|[ColumnTypeSnapshotSpec](../../../reference/yaml/TableYaml/#columntypesnapshotspec)|
|[run_checks_job_template](../#CheckSearchFilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run all checks within this column.|[CheckSearchFilters](../#CheckSearchFilters)|
|[run_profiling_checks_job_template](../#CheckSearchFilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run profiling checks within this column.|[CheckSearchFilters](../#CheckSearchFilters)|
|[run_monitoring_checks_job_template](../#CheckSearchFilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run monitoring checks within this column.|[CheckSearchFilters](../#CheckSearchFilters)|
|[run_partition_checks_job_template](../#CheckSearchFilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run partition partitioned checks within this column.|[CheckSearchFilters](../#CheckSearchFilters)|
|[collect_statistics_job_template](../#StatisticsCollectorSearchFilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collector within this column.|[StatisticsCollectorSearchFilters](../#StatisticsCollectorSearchFilters)|
|[data_clean_job_template](../#DeleteStoredDataQueueJobParameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this column.|[DeleteStoredDataQueueJobParameters](../#DeleteStoredDataQueueJobParameters)|
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
|[table](../columns/#PhysicalTableName)|Physical table name including the schema and table names.|[PhysicalTableName](../columns/#PhysicalTableName)|
|column_name|Column name.|string|
|column_hash|Column hash that identifies the column using a unique hash code.|long|
|[spec](../../../reference/yaml/TableYaml/#columnspec)|Full column specification.|[ColumnSpec](../../../reference/yaml/TableYaml/#columnspec)|
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
|self||Dict[string, [ColumnComparisonMonthlyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columncomparisonmonthlymonitoringchecksspec)]|


___  

## ColumnMonthlyMonitoringCheckCategoriesSpec  
Container of column level monthly monitoring checks. Contains categories of monthly monitoring checks.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[nulls](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnnullsmonthlymonitoringchecksspec)|Monthly monitoring checks of nulls in the column|[ColumnNullsMonthlyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnnullsmonthlymonitoringchecksspec)|
|[numeric](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnnumericmonthlymonitoringchecksspec)|Monthly monitoring checks of numeric in the column|[ColumnNumericMonthlyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnnumericmonthlymonitoringchecksspec)|
|[strings](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnstringsmonthlymonitoringchecksspec)|Monthly monitoring checks of strings in the column|[ColumnStringsMonthlyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnstringsmonthlymonitoringchecksspec)|
|[uniqueness](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnuniquenessmonthlymonitoringchecksspec)|Monthly monitoring checks of uniqueness in the column|[ColumnUniquenessMonthlyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnuniquenessmonthlymonitoringchecksspec)|
|[datetime](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columndatetimemonthlymonitoringchecksspec)|Monthly monitoring checks of datetime in the column|[ColumnDatetimeMonthlyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columndatetimemonthlymonitoringchecksspec)|
|[pii](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnpiimonthlymonitoringchecksspec)|Monthly monitoring checks of Personal Identifiable Information (PII) in the column|[ColumnPiiMonthlyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnpiimonthlymonitoringchecksspec)|
|[sql](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnsqlmonthlymonitoringchecksspec)|Monthly monitoring checks of custom SQL checks in the column|[ColumnSqlMonthlyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnsqlmonthlymonitoringchecksspec)|
|[bool](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnboolmonthlymonitoringchecksspec)|Monthly monitoring checks of booleans in the column|[ColumnBoolMonthlyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnboolmonthlymonitoringchecksspec)|
|[integrity](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnintegritymonthlymonitoringchecksspec)|Monthly monitoring checks of integrity in the column|[ColumnIntegrityMonthlyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnintegritymonthlymonitoringchecksspec)|
|[accuracy](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnaccuracymonthlymonitoringchecksspec)|Monthly monitoring checks of accuracy in the column|[ColumnAccuracyMonthlyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnaccuracymonthlymonitoringchecksspec)|
|[datatype](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columndatatypemonthlymonitoringchecksspec)|Monthly monitoring checks of datatype in the column|[ColumnDatatypeMonthlyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columndatatypemonthlymonitoringchecksspec)|
|[anomaly](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnanomalymonthlymonitoringchecksspec)|Monthly monitoring checks of anomaly in the column|[ColumnAnomalyMonthlyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnanomalymonthlymonitoringchecksspec)|
|[schema](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnschemamonthlymonitoringchecksspec)|Monthly monitoring column schema checks|[ColumnSchemaMonthlyMonitoringChecksSpec](../../../reference/yaml/monitoring/column-monthly-monitoring-checks/#columnschemamonthlymonitoringchecksspec)|
|[comparisons](#ColumnComparisonMonthlyMonitoringChecksSpecMap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonMonthlyMonitoringChecksSpecMap](#ColumnComparisonMonthlyMonitoringChecksSpecMap)|
|[custom](#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](#CustomCheckSpecMap)|


___  

## ColumnComparisonMonthlyPartitionedChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|self||Dict[string, [ColumnComparisonMonthlyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columncomparisonmonthlypartitionedchecksspec)]|


___  

## ColumnMonthlyPartitionedCheckCategoriesSpec  
Container of data quality partitioned checks on a column level that are checking numeric values at a monthly level.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[nulls](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnnullsmonthlypartitionedchecksspec)|Monthly partitioned checks of nulls values in the column|[ColumnNullsMonthlyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnnullsmonthlypartitionedchecksspec)|
|[numeric](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnnumericmonthlypartitionedchecksspec)|Monthly partitioned checks of numeric values in the column|[ColumnNumericMonthlyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnnumericmonthlypartitionedchecksspec)|
|[strings](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnstringsmonthlypartitionedchecksspec)|Monthly partitioned checks of strings values in the column|[ColumnStringsMonthlyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnstringsmonthlypartitionedchecksspec)|
|[uniqueness](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnuniquenessmonthlypartitionedchecksspec)|Monthly partitioned checks of uniqueness values in the column|[ColumnUniquenessMonthlyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnuniquenessmonthlypartitionedchecksspec)|
|[datetime](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columndatetimemonthlypartitionedchecksspec)|Monthly partitioned checks of datetime values in the column|[ColumnDatetimeMonthlyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columndatetimemonthlypartitionedchecksspec)|
|[pii](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnpiimonthlypartitionedchecksspec)|Monthly partitioned checks of Personal Identifiable Information (PII) in the column|[ColumnPiiMonthlyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnpiimonthlypartitionedchecksspec)|
|[sql](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnsqlmonthlypartitionedchecksspec)|Monthly partitioned checks using custom SQL expressions and conditions on the column|[ColumnSqlMonthlyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnsqlmonthlypartitionedchecksspec)|
|[bool](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnboolmonthlypartitionedchecksspec)|Monthly partitioned checks for booleans in the column|[ColumnBoolMonthlyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnboolmonthlypartitionedchecksspec)|
|[integrity](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnintegritymonthlypartitionedchecksspec)|Monthly partitioned checks for integrity in the column|[ColumnIntegrityMonthlyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnintegritymonthlypartitionedchecksspec)|
|[accuracy](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnaccuracymonthlypartitionedchecksspec)|Monthly partitioned checks for accuracy in the column|[ColumnAccuracyMonthlyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnaccuracymonthlypartitionedchecksspec)|
|[datatype](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columndatatypemonthlypartitionedchecksspec)|Monthly partitioned checks for datatype in the column|[ColumnDatatypeMonthlyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columndatatypemonthlypartitionedchecksspec)|
|[anomaly](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnanomalymonthlypartitionedchecksspec)|Monthly partitioned checks for anomaly in the column|[ColumnAnomalyMonthlyPartitionedChecksSpec](../../../reference/yaml/partitioned/column-monthly-partitioned-checks/#columnanomalymonthlypartitionedchecksspec)|
|[comparisons](#ColumnComparisonMonthlyPartitionedChecksSpecMap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonMonthlyPartitionedChecksSpecMap](#ColumnComparisonMonthlyPartitionedChecksSpecMap)|
|[custom](#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](#CustomCheckSpecMap)|


___  

## ColumnComparisonProfilingChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|self||Dict[string, [ColumnComparisonProfilingChecksSpec](../../../reference/yaml/profiling/column-profiling-checks/#columncomparisonprofilingchecksspec)]|


___  

## ColumnProfilingCheckCategoriesSpec  
Container of column level, preconfigured checks.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[nulls](../../../reference/yaml/profiling/column-profiling-checks/#columnnullsprofilingchecksspec)|Configuration of column level checks that verify nulls and blanks.|[ColumnNullsProfilingChecksSpec](../../../reference/yaml/profiling/column-profiling-checks/#columnnullsprofilingchecksspec)|
|[numeric](../../../reference/yaml/profiling/column-profiling-checks/#columnnumericprofilingchecksspec)|Configuration of column level checks that verify negative values.|[ColumnNumericProfilingChecksSpec](../../../reference/yaml/profiling/column-profiling-checks/#columnnumericprofilingchecksspec)|
|[strings](../../../reference/yaml/profiling/column-profiling-checks/#columnstringsprofilingchecksspec)|Configuration of strings checks on a column level.|[ColumnStringsProfilingChecksSpec](../../../reference/yaml/profiling/column-profiling-checks/#columnstringsprofilingchecksspec)|
|[uniqueness](../../../reference/yaml/profiling/column-profiling-checks/#columnuniquenessprofilingchecksspec)|Configuration of uniqueness checks on a column level.|[ColumnUniquenessProfilingChecksSpec](../../../reference/yaml/profiling/column-profiling-checks/#columnuniquenessprofilingchecksspec)|
|[datetime](../../../reference/yaml/profiling/column-profiling-checks/#columndatetimeprofilingchecksspec)|Configuration of datetime checks on a column level.|[ColumnDatetimeProfilingChecksSpec](../../../reference/yaml/profiling/column-profiling-checks/#columndatetimeprofilingchecksspec)|
|[pii](../../../reference/yaml/profiling/column-profiling-checks/#columnpiiprofilingchecksspec)|Configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiProfilingChecksSpec](../../../reference/yaml/profiling/column-profiling-checks/#columnpiiprofilingchecksspec)|
|[sql](../../../reference/yaml/profiling/column-profiling-checks/#columnsqlprofilingchecksspec)|Configuration of SQL checks that use custom SQL aggregated expressions and SQL conditions in data quality checks.|[ColumnSqlProfilingChecksSpec](../../../reference/yaml/profiling/column-profiling-checks/#columnsqlprofilingchecksspec)|
|[bool](../../../reference/yaml/profiling/column-profiling-checks/#columnboolprofilingchecksspec)|Configuration of booleans checks on a column level.|[ColumnBoolProfilingChecksSpec](../../../reference/yaml/profiling/column-profiling-checks/#columnboolprofilingchecksspec)|
|[integrity](../../../reference/yaml/profiling/column-profiling-checks/#columnintegrityprofilingchecksspec)|Configuration of integrity checks on a column level.|[ColumnIntegrityProfilingChecksSpec](../../../reference/yaml/profiling/column-profiling-checks/#columnintegrityprofilingchecksspec)|
|[accuracy](../../../reference/yaml/profiling/column-profiling-checks/#columnaccuracyprofilingchecksspec)|Configuration of accuracy checks on a column level.|[ColumnAccuracyProfilingChecksSpec](../../../reference/yaml/profiling/column-profiling-checks/#columnaccuracyprofilingchecksspec)|
|[datatype](../../../reference/yaml/profiling/column-profiling-checks/#columndatatypeprofilingchecksspec)|Configuration of datatype checks on a column level.|[ColumnDatatypeProfilingChecksSpec](../../../reference/yaml/profiling/column-profiling-checks/#columndatatypeprofilingchecksspec)|
|[anomaly](../../../reference/yaml/profiling/column-profiling-checks/#columnanomalyprofilingchecksspec)|Configuration of anomaly checks on a column level.|[ColumnAnomalyProfilingChecksSpec](../../../reference/yaml/profiling/column-profiling-checks/#columnanomalyprofilingchecksspec)|
|[schema](../../../reference/yaml/profiling/column-profiling-checks/#columnschemaprofilingchecksspec)|Configuration of schema checks on a column level.|[ColumnSchemaProfilingChecksSpec](../../../reference/yaml/profiling/column-profiling-checks/#columnschemaprofilingchecksspec)|
|[comparisons](#ColumnComparisonProfilingChecksSpecMap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonProfilingChecksSpecMap](#ColumnComparisonProfilingChecksSpecMap)|
|[custom](#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](#CustomCheckSpecMap)|


___  

## ColumnStatisticsModel  
Column model that returns the basic fields from a column specification and a summary of the most recent statistics collection.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|[table](../columns/#PhysicalTableName)|Physical table name including the schema and table names.|[PhysicalTableName](../columns/#PhysicalTableName)|
|column_name|Column name.|string|
|column_hash|Column hash that identifies the column using a unique hash code.|long|
|disabled|Disables all data quality checks on the column. Data quality checks will not be executed.|boolean|
|has_any_configured_checks|True when the column has any checks configured.|boolean|
|[type_snapshot](../../../reference/yaml/TableYaml/#columntypesnapshotspec)|Column data type that was retrieved when the table metadata was imported.|[ColumnTypeSnapshotSpec](../../../reference/yaml/TableYaml/#columntypesnapshotspec)|
|[collect_column_statistics_job_template](../#StatisticsCollectorSearchFilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors for this column|[StatisticsCollectorSearchFilters](../#StatisticsCollectorSearchFilters)|
|can_collect_statistics|Boolean flag that decides if the current user can collect statistics.|boolean|


___  

## TableColumnsStatisticsModel  
Model that returns a summary of the column statistics (the basic profiling results) for a single table, showing statistics for all columns.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|[table](../columns/#PhysicalTableName)|Physical table name including the schema and table names.|[PhysicalTableName](../columns/#PhysicalTableName)|
|[collect_column_statistics_job_template](../#StatisticsCollectorSearchFilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors for all columns on this table.|[StatisticsCollectorSearchFilters](../#StatisticsCollectorSearchFilters)|
|can_collect_statistics|Boolean flag that decides if the current user can collect statistics.|boolean|


___  

