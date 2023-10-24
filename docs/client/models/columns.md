
## ColumnComparisonDailyMonitoringChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|this||Map&lt;string, [ColumnComparisonDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columncomparisondailymonitoringchecksspec)&gt;|


___  

## CustomCheckSpecMap  
Dictionary of custom checks indexed by a check name.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|this||Map&lt;string, [CustomCheckSpec](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspec)&gt;|


___  

## ColumnDailyMonitoringCheckCategoriesSpec  
Container of column level daily monitoring checks. Contains categories of daily monitoring checks.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[nulls](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnnullsdailymonitoringchecksspec)|Daily monitoring checks of nulls in the column|[ColumnNullsDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnnullsdailymonitoringchecksspec)|
|[numeric](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnnumericdailymonitoringchecksspec)|Daily monitoring checks of numeric in the column|[ColumnNumericDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnnumericdailymonitoringchecksspec)|
|[strings](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnstringsdailymonitoringchecksspec)|Daily monitoring checks of strings in the column|[ColumnStringsDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnstringsdailymonitoringchecksspec)|
|[uniqueness](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnuniquenessdailymonitoringchecksspec)|Daily monitoring checks of uniqueness in the column|[ColumnUniquenessDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnuniquenessdailymonitoringchecksspec)|
|[datetime](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columndatetimedailymonitoringchecksspec)|Daily monitoring checks of datetime in the column|[ColumnDatetimeDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columndatetimedailymonitoringchecksspec)|
|[pii](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnpiidailymonitoringchecksspec)|Daily monitoring checks of Personal Identifiable Information (PII) in the column|[ColumnPiiDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnpiidailymonitoringchecksspec)|
|[sql](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnsqldailymonitoringchecksspec)|Daily monitoring checks of custom SQL checks in the column|[ColumnSqlDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnsqldailymonitoringchecksspec)|
|[bool](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnbooldailymonitoringchecksspec)|Daily monitoring checks of booleans in the column|[ColumnBoolDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnbooldailymonitoringchecksspec)|
|[integrity](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnintegritydailymonitoringchecksspec)|Daily monitoring checks of integrity in the column|[ColumnIntegrityDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnintegritydailymonitoringchecksspec)|
|[accuracy](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnaccuracydailymonitoringchecksspec)|Daily monitoring checks of accuracy in the column|[ColumnAccuracyDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnaccuracydailymonitoringchecksspec)|
|[datatype](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columndatatypedailymonitoringchecksspec)|Daily monitoring checks of datatype in the column|[ColumnDatatypeDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columndatatypedailymonitoringchecksspec)|
|[anomaly](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnanomalydailymonitoringchecksspec)|Daily monitoring checks of anomaly in the column|[ColumnAnomalyDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnanomalydailymonitoringchecksspec)|
|[schema](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnschemadailymonitoringchecksspec)|Daily monitoring column schema checks|[ColumnSchemaDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnschemadailymonitoringchecksspec)|
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
|this||Map&lt;string, [ColumnComparisonDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columncomparisondailypartitionedchecksspec)&gt;|


___  

## ColumnDailyPartitionedCheckCategoriesSpec  
Container of data quality partitioned checks on a column level that are checking numeric values at a daily level.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[nulls](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnnullsdailypartitionedchecksspec)|Daily partitioned checks of nulls in the column|[ColumnNullsDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnnullsdailypartitionedchecksspec)|
|[numeric](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnnumericdailypartitionedchecksspec)|Daily partitioned checks of numeric in the column|[ColumnNumericDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnnumericdailypartitionedchecksspec)|
|[strings](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnstringsdailypartitionedchecksspec)|Daily partitioned checks of strings in the column|[ColumnStringsDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnstringsdailypartitionedchecksspec)|
|[uniqueness](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnuniquenessdailypartitionedchecksspec)|Daily partitioned checks of uniqueness in the column|[ColumnUniquenessDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnuniquenessdailypartitionedchecksspec)|
|[datetime](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columndatetimedailypartitionedchecksspec)|Daily partitioned checks of datetime in the column|[ColumnDatetimeDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columndatetimedailypartitionedchecksspec)|
|[pii](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnpiidailypartitionedchecksspec)|Daily partitioned checks of Personal Identifiable Information (PII) in the column|[ColumnPiiDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnpiidailypartitionedchecksspec)|
|[sql](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnsqldailypartitionedchecksspec)|Daily partitioned checks using custom SQL expressions evaluated on the column|[ColumnSqlDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnsqldailypartitionedchecksspec)|
|[bool](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnbooldailypartitionedchecksspec)|Daily partitioned checks for booleans in the column|[ColumnBoolDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnbooldailypartitionedchecksspec)|
|[integrity](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnintegritydailypartitionedchecksspec)|Daily partitioned checks for integrity in the column|[ColumnIntegrityDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnintegritydailypartitionedchecksspec)|
|[accuracy](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnaccuracydailypartitionedchecksspec)|Daily partitioned checks for accuracy in the column|[ColumnAccuracyDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnaccuracydailypartitionedchecksspec)|
|[datatype](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columndatatypedailypartitionedchecksspec)|Daily partitioned checks for datatype in the column|[ColumnDatatypeDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columndatatypedailypartitionedchecksspec)|
|[anomaly](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnanomalydailypartitionedchecksspec)|Daily partitioned checks for anomaly in the column|[ColumnAnomalyDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnanomalydailypartitionedchecksspec)|
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
|[type_snapshot](\docs\reference\yaml\tableyaml\#columntypesnapshotspec)|Column data type that was retrieved when the table metadata was imported.|[ColumnTypeSnapshotSpec](\docs\reference\yaml\tableyaml\#columntypesnapshotspec)|
|[run_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run all checks within this column.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)|
|[run_profiling_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run profiling checks within this column.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)|
|[run_monitoring_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run monitoring checks within this column.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)|
|[run_partition_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run partition partitioned checks within this column.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)|
|[collect_statistics_job_template](\docs\client\models\#statisticscollectorsearchfilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collector within this column.|[StatisticsCollectorSearchFilters](\docs\client\models\#statisticscollectorsearchfilters)|
|[data_clean_job_template](\docs\client\models\#deletestoreddataqueuejobparameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this column.|[DeleteStoredDataQueueJobParameters](\docs\client\models\#deletestoreddataqueuejobparameters)|
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
|[table](\docs\client\models\columns\#physicaltablename)|Physical table name including the schema and table names.|[PhysicalTableName](\docs\client\models\columns\#physicaltablename)|
|column_name|Column name.|string|
|column_hash|Column hash that identifies the column using a unique hash code.|long|
|[spec](\docs\reference\yaml\tableyaml\#columnspec)|Full column specification.|[ColumnSpec](\docs\reference\yaml\tableyaml\#columnspec)|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|


___  

## ColumnComparisonMonthlyMonitoringChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|this||Map&lt;string, [ColumnComparisonMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columncomparisonmonthlymonitoringchecksspec)&gt;|


___  

## ColumnMonthlyMonitoringCheckCategoriesSpec  
Container of column level monthly monitoring checks. Contains categories of monthly monitoring checks.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[nulls](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnnullsmonthlymonitoringchecksspec)|Monthly monitoring checks of nulls in the column|[ColumnNullsMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnnullsmonthlymonitoringchecksspec)|
|[numeric](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnnumericmonthlymonitoringchecksspec)|Monthly monitoring checks of numeric in the column|[ColumnNumericMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnnumericmonthlymonitoringchecksspec)|
|[strings](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnstringsmonthlymonitoringchecksspec)|Monthly monitoring checks of strings in the column|[ColumnStringsMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnstringsmonthlymonitoringchecksspec)|
|[uniqueness](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnuniquenessmonthlymonitoringchecksspec)|Monthly monitoring checks of uniqueness in the column|[ColumnUniquenessMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnuniquenessmonthlymonitoringchecksspec)|
|[datetime](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columndatetimemonthlymonitoringchecksspec)|Monthly monitoring checks of datetime in the column|[ColumnDatetimeMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columndatetimemonthlymonitoringchecksspec)|
|[pii](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnpiimonthlymonitoringchecksspec)|Monthly monitoring checks of Personal Identifiable Information (PII) in the column|[ColumnPiiMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnpiimonthlymonitoringchecksspec)|
|[sql](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnsqlmonthlymonitoringchecksspec)|Monthly monitoring checks of custom SQL checks in the column|[ColumnSqlMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnsqlmonthlymonitoringchecksspec)|
|[bool](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnboolmonthlymonitoringchecksspec)|Monthly monitoring checks of booleans in the column|[ColumnBoolMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnboolmonthlymonitoringchecksspec)|
|[integrity](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnintegritymonthlymonitoringchecksspec)|Monthly monitoring checks of integrity in the column|[ColumnIntegrityMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnintegritymonthlymonitoringchecksspec)|
|[accuracy](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnaccuracymonthlymonitoringchecksspec)|Monthly monitoring checks of accuracy in the column|[ColumnAccuracyMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnaccuracymonthlymonitoringchecksspec)|
|[datatype](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columndatatypemonthlymonitoringchecksspec)|Monthly monitoring checks of datatype in the column|[ColumnDatatypeMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columndatatypemonthlymonitoringchecksspec)|
|[anomaly](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnanomalymonthlymonitoringchecksspec)|Monthly monitoring checks of anomaly in the column|[ColumnAnomalyMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnanomalymonthlymonitoringchecksspec)|
|[schema](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnschemamonthlymonitoringchecksspec)|Monthly monitoring column schema checks|[ColumnSchemaMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnschemamonthlymonitoringchecksspec)|
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
|this||Map&lt;string, [ColumnComparisonMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columncomparisonmonthlypartitionedchecksspec)&gt;|


___  

## ColumnMonthlyPartitionedCheckCategoriesSpec  
Container of data quality partitioned checks on a column level that are checking numeric values at a monthly level.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[nulls](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnnullsmonthlypartitionedchecksspec)|Monthly partitioned checks of nulls values in the column|[ColumnNullsMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnnullsmonthlypartitionedchecksspec)|
|[numeric](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnnumericmonthlypartitionedchecksspec)|Monthly partitioned checks of numeric values in the column|[ColumnNumericMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnnumericmonthlypartitionedchecksspec)|
|[strings](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnstringsmonthlypartitionedchecksspec)|Monthly partitioned checks of strings values in the column|[ColumnStringsMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnstringsmonthlypartitionedchecksspec)|
|[uniqueness](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnuniquenessmonthlypartitionedchecksspec)|Monthly partitioned checks of uniqueness values in the column|[ColumnUniquenessMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnuniquenessmonthlypartitionedchecksspec)|
|[datetime](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columndatetimemonthlypartitionedchecksspec)|Monthly partitioned checks of datetime values in the column|[ColumnDatetimeMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columndatetimemonthlypartitionedchecksspec)|
|[pii](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnpiimonthlypartitionedchecksspec)|Monthly partitioned checks of Personal Identifiable Information (PII) in the column|[ColumnPiiMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnpiimonthlypartitionedchecksspec)|
|[sql](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnsqlmonthlypartitionedchecksspec)|Monthly partitioned checks using custom SQL expressions and conditions on the column|[ColumnSqlMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnsqlmonthlypartitionedchecksspec)|
|[bool](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnboolmonthlypartitionedchecksspec)|Monthly partitioned checks for booleans in the column|[ColumnBoolMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnboolmonthlypartitionedchecksspec)|
|[integrity](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnintegritymonthlypartitionedchecksspec)|Monthly partitioned checks for integrity in the column|[ColumnIntegrityMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnintegritymonthlypartitionedchecksspec)|
|[accuracy](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnaccuracymonthlypartitionedchecksspec)|Monthly partitioned checks for accuracy in the column|[ColumnAccuracyMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnaccuracymonthlypartitionedchecksspec)|
|[datatype](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columndatatypemonthlypartitionedchecksspec)|Monthly partitioned checks for datatype in the column|[ColumnDatatypeMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columndatatypemonthlypartitionedchecksspec)|
|[anomaly](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnanomalymonthlypartitionedchecksspec)|Monthly partitioned checks for anomaly in the column|[ColumnAnomalyMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnanomalymonthlypartitionedchecksspec)|
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
|this||Map&lt;string, [ColumnComparisonProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columncomparisonprofilingchecksspec)&gt;|


___  

## ColumnProfilingCheckCategoriesSpec  
Container of column level, preconfigured checks.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[nulls](\docs\reference\yaml\profiling\column-profiling-checks\#columnnullsprofilingchecksspec)|Configuration of column level checks that verify nulls and blanks.|[ColumnNullsProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnnullsprofilingchecksspec)|
|[numeric](\docs\reference\yaml\profiling\column-profiling-checks\#columnnumericprofilingchecksspec)|Configuration of column level checks that verify negative values.|[ColumnNumericProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnnumericprofilingchecksspec)|
|[strings](\docs\reference\yaml\profiling\column-profiling-checks\#columnstringsprofilingchecksspec)|Configuration of strings checks on a column level.|[ColumnStringsProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnstringsprofilingchecksspec)|
|[uniqueness](\docs\reference\yaml\profiling\column-profiling-checks\#columnuniquenessprofilingchecksspec)|Configuration of uniqueness checks on a column level.|[ColumnUniquenessProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnuniquenessprofilingchecksspec)|
|[datetime](\docs\reference\yaml\profiling\column-profiling-checks\#columndatetimeprofilingchecksspec)|Configuration of datetime checks on a column level.|[ColumnDatetimeProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columndatetimeprofilingchecksspec)|
|[pii](\docs\reference\yaml\profiling\column-profiling-checks\#columnpiiprofilingchecksspec)|Configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnpiiprofilingchecksspec)|
|[sql](\docs\reference\yaml\profiling\column-profiling-checks\#columnsqlprofilingchecksspec)|Configuration of SQL checks that use custom SQL aggregated expressions and SQL conditions in data quality checks.|[ColumnSqlProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnsqlprofilingchecksspec)|
|[bool](\docs\reference\yaml\profiling\column-profiling-checks\#columnboolprofilingchecksspec)|Configuration of booleans checks on a column level.|[ColumnBoolProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnboolprofilingchecksspec)|
|[integrity](\docs\reference\yaml\profiling\column-profiling-checks\#columnintegrityprofilingchecksspec)|Configuration of integrity checks on a column level.|[ColumnIntegrityProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnintegrityprofilingchecksspec)|
|[accuracy](\docs\reference\yaml\profiling\column-profiling-checks\#columnaccuracyprofilingchecksspec)|Configuration of accuracy checks on a column level.|[ColumnAccuracyProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnaccuracyprofilingchecksspec)|
|[datatype](\docs\reference\yaml\profiling\column-profiling-checks\#columndatatypeprofilingchecksspec)|Configuration of datatype checks on a column level.|[ColumnDatatypeProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columndatatypeprofilingchecksspec)|
|[anomaly](\docs\reference\yaml\profiling\column-profiling-checks\#columnanomalyprofilingchecksspec)|Configuration of anomaly checks on a column level.|[ColumnAnomalyProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnanomalyprofilingchecksspec)|
|[schema](\docs\reference\yaml\profiling\column-profiling-checks\#columnschemaprofilingchecksspec)|Configuration of schema checks on a column level.|[ColumnSchemaProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnschemaprofilingchecksspec)|
|[comparisons](#columncomparisonprofilingchecksspecmap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonProfilingChecksSpecMap](#columncomparisonprofilingchecksspecmap)|
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](#customcheckspecmap)|


___  

## ColumnStatisticsModel  
Column model that returns the basic fields from a column specification and a summary of the most recent statistics collection.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|[table](\docs\client\models\columns\#physicaltablename)|Physical table name including the schema and table names.|[PhysicalTableName](\docs\client\models\columns\#physicaltablename)|
|column_name|Column name.|string|
|column_hash|Column hash that identifies the column using a unique hash code.|long|
|disabled|Disables all data quality checks on the column. Data quality checks will not be executed.|boolean|
|has_any_configured_checks|True when the column has any checks configured.|boolean|
|[type_snapshot](\docs\reference\yaml\tableyaml\#columntypesnapshotspec)|Column data type that was retrieved when the table metadata was imported.|[ColumnTypeSnapshotSpec](\docs\reference\yaml\tableyaml\#columntypesnapshotspec)|
|[collect_column_statistics_job_template](\docs\client\models\#statisticscollectorsearchfilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors for this column|[StatisticsCollectorSearchFilters](\docs\client\models\#statisticscollectorsearchfilters)|
|can_collect_statistics|Boolean flag that decides if the current user can collect statistics.|boolean|


___  

## TableColumnsStatisticsModel  
Model that returns a summary of the column statistics (the basic profiling results) for a single table, showing statistics for all columns.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|[table](\docs\client\models\columns\#physicaltablename)|Physical table name including the schema and table names.|[PhysicalTableName](\docs\client\models\columns\#physicaltablename)|
|[collect_column_statistics_job_template](\docs\client\models\#statisticscollectorsearchfilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors for all columns on this table.|[StatisticsCollectorSearchFilters](\docs\client\models\#statisticscollectorsearchfilters)|
|can_collect_statistics|Boolean flag that decides if the current user can collect statistics.|boolean|


___  

