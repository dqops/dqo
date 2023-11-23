
## DefaultDailyMonitoringObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability daily monitoring checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for daily monitoring checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[table](../DefaultObservabilityChecksYaml/#DefaultDailyMonitoringTableObservabilityCheckSettingsSpec)|The default configuration of daily monitoring checks on a table level.|[DefaultDailyMonitoringTableObservabilityCheckSettingsSpec](../DefaultObservabilityChecksYaml/#DefaultDailyMonitoringTableObservabilityCheckSettingsSpec)| | | |
|[column](../DefaultObservabilityChecksYaml/#DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec)|The default configuration of daily monitoring checks on a column level.|[DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec](../DefaultObservabilityChecksYaml/#DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec)| | | |









___  

## DefaultObservabilityChecksYaml  
The configuration of default data quality checks that are activated for all imported tables and columns.
 The default observability checks are stored in the *$DQO_USER_HOME/settings/defaultchecks.dqochecks.yaml* file in the DQOps user&#x27;s home folder.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|kind||enum|default_schedules<br/>settings<br/>default_notifications<br/>rule<br/>sensor<br/>source<br/>check<br/>dashboards<br/>default_checks<br/>table<br/>provider_sensor<br/>file_index<br/>| | |
|[spec](../DefaultObservabilityChecksYaml/#DefaultObservabilityChecksSpec)||[DefaultObservabilityChecksSpec](../DefaultObservabilityChecksYaml/#DefaultObservabilityChecksSpec)| | | |









___  

## DefaultProfilingObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability profiling checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for profiling checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[table](../DefaultObservabilityChecksYaml/#DefaultProfilingTableObservabilityCheckSettingsSpec)|The default configuration of profiling checks on a table level.|[DefaultProfilingTableObservabilityCheckSettingsSpec](../DefaultObservabilityChecksYaml/#DefaultProfilingTableObservabilityCheckSettingsSpec)| | | |
|[column](../DefaultObservabilityChecksYaml/#DefaultProfilingColumnObservabilityCheckSettingsSpec)|The default configuration of profiling checks on a column level.|[DefaultProfilingColumnObservabilityCheckSettingsSpec](../DefaultObservabilityChecksYaml/#DefaultProfilingColumnObservabilityCheckSettingsSpec)| | | |









___  

## DefaultProfilingTableObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability profiling checks that will be detecting anomalies
 for all tables that are imported. This configuration of checks is copied to the list of enabled table level checks on all tables that are imported, for profiling checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](../profiling/table-profiling-checks/#TableVolumeProfilingChecksSpec)|The default configuration of volume data quality checks on a table level.|[TableVolumeProfilingChecksSpec](../profiling/table-profiling-checks/#TableVolumeProfilingChecksSpec)| | | |
|[availability](../profiling/table-profiling-checks/#TableAvailabilityProfilingChecksSpec)|The default configuration of the table availability data quality checks on a table level.|[TableAvailabilityProfilingChecksSpec](../profiling/table-profiling-checks/#TableAvailabilityProfilingChecksSpec)| | | |
|[schema](../profiling/table-profiling-checks/#TableSchemaProfilingChecksSpec)|The default configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaProfilingChecksSpec](../profiling/table-profiling-checks/#TableSchemaProfilingChecksSpec)| | | |
|[custom](../profiling/table-profiling-checks/#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../profiling/table-profiling-checks/#CustomCheckSpecMap)| | | |









___  

## DefaultMonthlyMonitoringTableObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability monthly monitoring checks that will be detecting anomalies
 for all tables that are imported. This configuration of checks is copied to the list of enabled table level checks on all tables that are imported, for monthly monitoring checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](../monitoring/table-monthly-monitoring-checks/#TableVolumeMonthlyMonitoringChecksSpec)|The default configuration of volume data quality checks on a table level.|[TableVolumeMonthlyMonitoringChecksSpec](../monitoring/table-monthly-monitoring-checks/#TableVolumeMonthlyMonitoringChecksSpec)| | | |
|[availability](../monitoring/table-monthly-monitoring-checks/#TableAvailabilityMonthlyMonitoringChecksSpec)|The default configuration of the table availability data quality checks on a table level.|[TableAvailabilityMonthlyMonitoringChecksSpec](../monitoring/table-monthly-monitoring-checks/#TableAvailabilityMonthlyMonitoringChecksSpec)| | | |
|[schema](../monitoring/table-monthly-monitoring-checks/#TableSchemaMonthlyMonitoringChecksSpec)|The default configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaMonthlyMonitoringChecksSpec](../monitoring/table-monthly-monitoring-checks/#TableSchemaMonthlyMonitoringChecksSpec)| | | |
|[custom](../profiling/table-profiling-checks/#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../profiling/table-profiling-checks/#CustomCheckSpecMap)| | | |









___  

## DefaultObservabilityChecksSpec  
The default configuration of checks that are enabled as data observability checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profiling](../DefaultObservabilityChecksYaml/#DefaultProfilingObservabilityCheckSettingsSpec)|Default configuration of profiling checks that are enabled on tables and columns that are imported into DQOps.|[DefaultProfilingObservabilityCheckSettingsSpec](../DefaultObservabilityChecksYaml/#DefaultProfilingObservabilityCheckSettingsSpec)| | | |
|[monitoring_daily](../DefaultObservabilityChecksYaml/#DefaultDailyMonitoringObservabilityCheckSettingsSpec)|Default configuration of daily monitoring checks (executed once a day, storing or overriding one result per day) that are enabled on tables and columns that are imported into DQOps.|[DefaultDailyMonitoringObservabilityCheckSettingsSpec](../DefaultObservabilityChecksYaml/#DefaultDailyMonitoringObservabilityCheckSettingsSpec)| | | |
|[monitoring_monthly](../DefaultObservabilityChecksYaml/#DefaultMonthlyMonitoringObservabilityCheckSettingsSpec)|Default configuration of daily monitoring checks (executed once a day, storing or overriding one result per month) that are enabled on tables and columns that are imported into DQOps.|[DefaultMonthlyMonitoringObservabilityCheckSettingsSpec](../DefaultObservabilityChecksYaml/#DefaultMonthlyMonitoringObservabilityCheckSettingsSpec)| | | |









___  

## DefaultDailyMonitoringTableObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability daily monitoring checks that will be detecting anomalies
 for all tables that are imported. This configuration of checks is copied to the list of enabled table level checks on all tables that are imported, for daily monitoring checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](../monitoring/table-daily-monitoring-checks/#TableVolumeDailyMonitoringChecksSpec)|The default configuration of volume data quality checks on a table level.|[TableVolumeDailyMonitoringChecksSpec](../monitoring/table-daily-monitoring-checks/#TableVolumeDailyMonitoringChecksSpec)| | | |
|[availability](../monitoring/table-daily-monitoring-checks/#TableAvailabilityDailyMonitoringChecksSpec)|The default configuration of the table availability data quality checks on a table level.|[TableAvailabilityDailyMonitoringChecksSpec](../monitoring/table-daily-monitoring-checks/#TableAvailabilityDailyMonitoringChecksSpec)| | | |
|[schema](../monitoring/table-daily-monitoring-checks/#TableSchemaDailyMonitoringChecksSpec)|The default configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaDailyMonitoringChecksSpec](../monitoring/table-daily-monitoring-checks/#TableSchemaDailyMonitoringChecksSpec)| | | |
|[custom](../profiling/table-profiling-checks/#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../profiling/table-profiling-checks/#CustomCheckSpecMap)| | | |









___  

## DefaultMonthlyMonitoringColumnObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability monthly monitoring checks that will be detecting anomalies
 for all columns that are imported. This configuration of checks is copied to the list of enabled column level checks on all columns that are imported, for monthly monitoring checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](../monitoring/column-monthly-monitoring-checks/#ColumnNullsMonthlyMonitoringChecksSpec)|The default configuration of column level checks that verify nulls and blanks.|[ColumnNullsMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#ColumnNullsMonthlyMonitoringChecksSpec)| | | |
|[numeric](../monitoring/column-monthly-monitoring-checks/#ColumnNumericMonthlyMonitoringChecksSpec)|The default configuration of column level checks that verify negative values.|[ColumnNumericMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#ColumnNumericMonthlyMonitoringChecksSpec)| | | |
|[strings](../monitoring/column-monthly-monitoring-checks/#ColumnStringsMonthlyMonitoringChecksSpec)|The default configuration of strings checks on a column level.|[ColumnStringsMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#ColumnStringsMonthlyMonitoringChecksSpec)| | | |
|[uniqueness](../monitoring/column-monthly-monitoring-checks/#ColumnUniquenessMonthlyMonitoringChecksSpec)|The default configuration of uniqueness checks on a column level.|[ColumnUniquenessMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#ColumnUniquenessMonthlyMonitoringChecksSpec)| | | |
|[datetime](../monitoring/column-monthly-monitoring-checks/#ColumnDatetimeMonthlyMonitoringChecksSpec)|The default configuration of datetime checks on a column level.|[ColumnDatetimeMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#ColumnDatetimeMonthlyMonitoringChecksSpec)| | | |
|[pii](../monitoring/column-monthly-monitoring-checks/#ColumnPiiMonthlyMonitoringChecksSpec)|The default configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#ColumnPiiMonthlyMonitoringChecksSpec)| | | |
|[bool](../monitoring/column-monthly-monitoring-checks/#ColumnBoolMonthlyMonitoringChecksSpec)|The default configuration of booleans checks on a column level.|[ColumnBoolMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#ColumnBoolMonthlyMonitoringChecksSpec)| | | |
|[datatype](../monitoring/column-monthly-monitoring-checks/#ColumnDatatypeMonthlyMonitoringChecksSpec)|The default configuration of datatype checks on a column level.|[ColumnDatatypeMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#ColumnDatatypeMonthlyMonitoringChecksSpec)| | | |
|[anomaly](../monitoring/column-monthly-monitoring-checks/#ColumnAnomalyMonthlyMonitoringChecksSpec)|The default configuration of anomaly checks on a column level.|[ColumnAnomalyMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#ColumnAnomalyMonthlyMonitoringChecksSpec)| | | |
|[schema](../monitoring/column-monthly-monitoring-checks/#ColumnSchemaMonthlyMonitoringChecksSpec)|The default configuration of schema checks on a column level.|[ColumnSchemaMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#ColumnSchemaMonthlyMonitoringChecksSpec)| | | |
|[custom](../profiling/table-profiling-checks/#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../profiling/table-profiling-checks/#CustomCheckSpecMap)| | | |









___  

## DefaultProfilingColumnObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability profiling checks that will be detecting anomalies
 for all columns that are imported. This configuration of checks is copied to the list of enabled column level checks on all columns that are imported, for profiling checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](../profiling/column-profiling-checks/#ColumnNullsProfilingChecksSpec)|The default configuration of column level checks that verify nulls and blanks.|[ColumnNullsProfilingChecksSpec](../profiling/column-profiling-checks/#ColumnNullsProfilingChecksSpec)| | | |
|[numeric](../profiling/column-profiling-checks/#ColumnNumericProfilingChecksSpec)|The default configuration of column level checks that verify negative values.|[ColumnNumericProfilingChecksSpec](../profiling/column-profiling-checks/#ColumnNumericProfilingChecksSpec)| | | |
|[strings](../profiling/column-profiling-checks/#ColumnStringsProfilingChecksSpec)|The default configuration of strings checks on a column level.|[ColumnStringsProfilingChecksSpec](../profiling/column-profiling-checks/#ColumnStringsProfilingChecksSpec)| | | |
|[uniqueness](../profiling/column-profiling-checks/#ColumnUniquenessProfilingChecksSpec)|The default configuration of uniqueness checks on a column level.|[ColumnUniquenessProfilingChecksSpec](../profiling/column-profiling-checks/#ColumnUniquenessProfilingChecksSpec)| | | |
|[datetime](../profiling/column-profiling-checks/#ColumnDatetimeProfilingChecksSpec)|The default configuration of datetime checks on a column level.|[ColumnDatetimeProfilingChecksSpec](../profiling/column-profiling-checks/#ColumnDatetimeProfilingChecksSpec)| | | |
|[pii](../profiling/column-profiling-checks/#ColumnPiiProfilingChecksSpec)|The default configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiProfilingChecksSpec](../profiling/column-profiling-checks/#ColumnPiiProfilingChecksSpec)| | | |
|[bool](../profiling/column-profiling-checks/#ColumnBoolProfilingChecksSpec)|The default configuration of booleans checks on a column level.|[ColumnBoolProfilingChecksSpec](../profiling/column-profiling-checks/#ColumnBoolProfilingChecksSpec)| | | |
|[datatype](../profiling/column-profiling-checks/#ColumnDatatypeProfilingChecksSpec)|The default configuration of datatype checks on a column level.|[ColumnDatatypeProfilingChecksSpec](../profiling/column-profiling-checks/#ColumnDatatypeProfilingChecksSpec)| | | |
|[anomaly](../profiling/column-profiling-checks/#ColumnAnomalyProfilingChecksSpec)|The default configuration of anomaly checks on a column level.|[ColumnAnomalyProfilingChecksSpec](../profiling/column-profiling-checks/#ColumnAnomalyProfilingChecksSpec)| | | |
|[schema](../profiling/column-profiling-checks/#ColumnSchemaProfilingChecksSpec)|The default configuration of schema checks on a column level.|[ColumnSchemaProfilingChecksSpec](../profiling/column-profiling-checks/#ColumnSchemaProfilingChecksSpec)| | | |
|[custom](../profiling/table-profiling-checks/#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../profiling/table-profiling-checks/#CustomCheckSpecMap)| | | |









___  

## DefaultMonthlyMonitoringObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability monthly monitoring checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for monthly profiling checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[table](../DefaultObservabilityChecksYaml/#DefaultMonthlyMonitoringTableObservabilityCheckSettingsSpec)|The default configuration of monthly monitoring checks on a table level.|[DefaultMonthlyMonitoringTableObservabilityCheckSettingsSpec](../DefaultObservabilityChecksYaml/#DefaultMonthlyMonitoringTableObservabilityCheckSettingsSpec)| | | |
|[column](../DefaultObservabilityChecksYaml/#DefaultMonthlyMonitoringColumnObservabilityCheckSettingsSpec)|The default configuration of monthly monitoring checks on a column level.|[DefaultMonthlyMonitoringColumnObservabilityCheckSettingsSpec](../DefaultObservabilityChecksYaml/#DefaultMonthlyMonitoringColumnObservabilityCheckSettingsSpec)| | | |









___  

## DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability daily monitoring checks that will be detecting anomalies
 for all columns that are imported. This configuration of checks is copied to the list of enabled column level checks on all columns that are imported, for daily monitoring checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](../monitoring/column-daily-monitoring-checks/#ColumnNullsDailyMonitoringChecksSpec)|The default configuration of column level checks that verify nulls and blanks.|[ColumnNullsDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#ColumnNullsDailyMonitoringChecksSpec)| | | |
|[numeric](../monitoring/column-daily-monitoring-checks/#ColumnNumericDailyMonitoringChecksSpec)|The default configuration of column level checks that verify negative values.|[ColumnNumericDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#ColumnNumericDailyMonitoringChecksSpec)| | | |
|[strings](../monitoring/column-daily-monitoring-checks/#ColumnStringsDailyMonitoringChecksSpec)|The default configuration of strings checks on a column level.|[ColumnStringsDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#ColumnStringsDailyMonitoringChecksSpec)| | | |
|[uniqueness](../monitoring/column-daily-monitoring-checks/#ColumnUniquenessDailyMonitoringChecksSpec)|The default configuration of uniqueness checks on a column level.|[ColumnUniquenessDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#ColumnUniquenessDailyMonitoringChecksSpec)| | | |
|[datetime](../monitoring/column-daily-monitoring-checks/#ColumnDatetimeDailyMonitoringChecksSpec)|The default configuration of datetime checks on a column level.|[ColumnDatetimeDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#ColumnDatetimeDailyMonitoringChecksSpec)| | | |
|[pii](../monitoring/column-daily-monitoring-checks/#ColumnPiiDailyMonitoringChecksSpec)|The default configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#ColumnPiiDailyMonitoringChecksSpec)| | | |
|[bool](../monitoring/column-daily-monitoring-checks/#ColumnBoolDailyMonitoringChecksSpec)|The default configuration of booleans checks on a column level.|[ColumnBoolDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#ColumnBoolDailyMonitoringChecksSpec)| | | |
|[datatype](../monitoring/column-daily-monitoring-checks/#ColumnDatatypeDailyMonitoringChecksSpec)|The default configuration of datatype checks on a column level.|[ColumnDatatypeDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#ColumnDatatypeDailyMonitoringChecksSpec)| | | |
|[anomaly](../monitoring/column-daily-monitoring-checks/#ColumnAnomalyDailyMonitoringChecksSpec)|The default configuration of anomaly checks on a column level.|[ColumnAnomalyDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#ColumnAnomalyDailyMonitoringChecksSpec)| | | |
|[schema](../monitoring/column-daily-monitoring-checks/#ColumnSchemaDailyMonitoringChecksSpec)|The default configuration of schema checks on a column level.|[ColumnSchemaDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#ColumnSchemaDailyMonitoringChecksSpec)| | | |
|[custom](../profiling/table-profiling-checks/#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../profiling/table-profiling-checks/#CustomCheckSpecMap)| | | |









___  

