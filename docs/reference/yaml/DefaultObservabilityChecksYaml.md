
## DefaultMonthlyMonitoringTableObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability monthly monitoring checks that will be detecting anomalies
 for all tables that are imported. This configuration of checks is copied to the list of enabled table level checks on all tables that are imported, for monthly monitoring checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](../monitoring/table-monthly-monitoring-checks/#tablevolumemonthlymonitoringchecksspec)|The default configuration of volume data quality checks on a table level.|[TableVolumeMonthlyMonitoringChecksSpec](../monitoring/table-monthly-monitoring-checks/#tablevolumemonthlymonitoringchecksspec)| | | |
|[availability](../monitoring/table-monthly-monitoring-checks/#tableavailabilitymonthlymonitoringchecksspec)|The default configuration of the table availability data quality checks on a table level.|[TableAvailabilityMonthlyMonitoringChecksSpec](../monitoring/table-monthly-monitoring-checks/#tableavailabilitymonthlymonitoringchecksspec)| | | |
|[schema](../monitoring/table-monthly-monitoring-checks/#tableschemamonthlymonitoringchecksspec)|The default configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaMonthlyMonitoringChecksSpec](../monitoring/table-monthly-monitoring-checks/#tableschemamonthlymonitoringchecksspec)| | | |
|[custom](../profiling/table-profiling-checks/#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../profiling/table-profiling-checks/#customcheckspecmap)| | | |









___  

## DefaultObservabilityChecksYaml  
The configuration of default data quality checks that are activated for all imported tables and columns.
 The default observability checks are stored in the *$DQO_USER_HOME/settings/defaultchecks.dqochecks.yaml* file in the DQOps user&#x27;s home folder.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|kind||enum|default_schedules<br/>settings<br/>default_notifications<br/>rule<br/>sensor<br/>source<br/>check<br/>dashboards<br/>default_checks<br/>table<br/>provider_sensor<br/>file_index<br/>| | |
|[spec](../defaultobservabilitychecksyaml/#defaultobservabilitychecksspec)||[DefaultObservabilityChecksSpec](../defaultobservabilitychecksyaml/#defaultobservabilitychecksspec)| | | |









___  

## DefaultMonthlyMonitoringObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability monthly monitoring checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for monthly profiling checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[table](../defaultobservabilitychecksyaml/#defaultmonthlymonitoringtableobservabilitychecksettingsspec)|The default configuration of monthly monitoring checks on a table level.|[DefaultMonthlyMonitoringTableObservabilityCheckSettingsSpec](../defaultobservabilitychecksyaml/#defaultmonthlymonitoringtableobservabilitychecksettingsspec)| | | |
|[column](../defaultobservabilitychecksyaml/#defaultmonthlymonitoringcolumnobservabilitychecksettingsspec)|The default configuration of monthly monitoring checks on a column level.|[DefaultMonthlyMonitoringColumnObservabilityCheckSettingsSpec](../defaultobservabilitychecksyaml/#defaultmonthlymonitoringcolumnobservabilitychecksettingsspec)| | | |









___  

## DefaultProfilingColumnObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability profiling checks that will be detecting anomalies
 for all columns that are imported. This configuration of checks is copied to the list of enabled column level checks on all columns that are imported, for profiling checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](../profiling/column-profiling-checks/#columnnullsprofilingchecksspec)|The default configuration of column level checks that verify nulls and blanks.|[ColumnNullsProfilingChecksSpec](../profiling/column-profiling-checks/#columnnullsprofilingchecksspec)| | | |
|[numeric](../profiling/column-profiling-checks/#columnnumericprofilingchecksspec)|The default configuration of column level checks that verify negative values.|[ColumnNumericProfilingChecksSpec](../profiling/column-profiling-checks/#columnnumericprofilingchecksspec)| | | |
|[strings](../profiling/column-profiling-checks/#columnstringsprofilingchecksspec)|The default configuration of strings checks on a column level.|[ColumnStringsProfilingChecksSpec](../profiling/column-profiling-checks/#columnstringsprofilingchecksspec)| | | |
|[uniqueness](../profiling/column-profiling-checks/#columnuniquenessprofilingchecksspec)|The default configuration of uniqueness checks on a column level.|[ColumnUniquenessProfilingChecksSpec](../profiling/column-profiling-checks/#columnuniquenessprofilingchecksspec)| | | |
|[datetime](../profiling/column-profiling-checks/#columndatetimeprofilingchecksspec)|The default configuration of datetime checks on a column level.|[ColumnDatetimeProfilingChecksSpec](../profiling/column-profiling-checks/#columndatetimeprofilingchecksspec)| | | |
|[pii](../profiling/column-profiling-checks/#columnpiiprofilingchecksspec)|The default configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiProfilingChecksSpec](../profiling/column-profiling-checks/#columnpiiprofilingchecksspec)| | | |
|[bool](../profiling/column-profiling-checks/#columnboolprofilingchecksspec)|The default configuration of booleans checks on a column level.|[ColumnBoolProfilingChecksSpec](../profiling/column-profiling-checks/#columnboolprofilingchecksspec)| | | |
|[datatype](../profiling/column-profiling-checks/#columndatatypeprofilingchecksspec)|The default configuration of datatype checks on a column level.|[ColumnDatatypeProfilingChecksSpec](../profiling/column-profiling-checks/#columndatatypeprofilingchecksspec)| | | |
|[anomaly](../profiling/column-profiling-checks/#columnanomalyprofilingchecksspec)|The default configuration of anomaly checks on a column level.|[ColumnAnomalyProfilingChecksSpec](../profiling/column-profiling-checks/#columnanomalyprofilingchecksspec)| | | |
|[schema](../profiling/column-profiling-checks/#columnschemaprofilingchecksspec)|The default configuration of schema checks on a column level.|[ColumnSchemaProfilingChecksSpec](../profiling/column-profiling-checks/#columnschemaprofilingchecksspec)| | | |
|[custom](../profiling/table-profiling-checks/#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../profiling/table-profiling-checks/#customcheckspecmap)| | | |









___  

## DefaultProfilingTableObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability profiling checks that will be detecting anomalies
 for all tables that are imported. This configuration of checks is copied to the list of enabled table level checks on all tables that are imported, for profiling checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](../profiling/table-profiling-checks/#tablevolumeprofilingchecksspec)|The default configuration of volume data quality checks on a table level.|[TableVolumeProfilingChecksSpec](../profiling/table-profiling-checks/#tablevolumeprofilingchecksspec)| | | |
|[availability](../profiling/table-profiling-checks/#tableavailabilityprofilingchecksspec)|The default configuration of the table availability data quality checks on a table level.|[TableAvailabilityProfilingChecksSpec](../profiling/table-profiling-checks/#tableavailabilityprofilingchecksspec)| | | |
|[schema](../profiling/table-profiling-checks/#tableschemaprofilingchecksspec)|The default configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaProfilingChecksSpec](../profiling/table-profiling-checks/#tableschemaprofilingchecksspec)| | | |
|[custom](../profiling/table-profiling-checks/#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../profiling/table-profiling-checks/#customcheckspecmap)| | | |









___  

## DefaultDailyMonitoringObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability daily monitoring checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for daily monitoring checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[table](../defaultobservabilitychecksyaml/#defaultdailymonitoringtableobservabilitychecksettingsspec)|The default configuration of daily monitoring checks on a table level.|[DefaultDailyMonitoringTableObservabilityCheckSettingsSpec](../defaultobservabilitychecksyaml/#defaultdailymonitoringtableobservabilitychecksettingsspec)| | | |
|[column](../defaultobservabilitychecksyaml/#defaultdailymonitoringcolumnobservabilitychecksettingsspec)|The default configuration of daily monitoring checks on a column level.|[DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec](../defaultobservabilitychecksyaml/#defaultdailymonitoringcolumnobservabilitychecksettingsspec)| | | |









___  

## DefaultDailyMonitoringTableObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability daily monitoring checks that will be detecting anomalies
 for all tables that are imported. This configuration of checks is copied to the list of enabled table level checks on all tables that are imported, for daily monitoring checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](../monitoring/table-daily-monitoring-checks/#tablevolumedailymonitoringchecksspec)|The default configuration of volume data quality checks on a table level.|[TableVolumeDailyMonitoringChecksSpec](../monitoring/table-daily-monitoring-checks/#tablevolumedailymonitoringchecksspec)| | | |
|[availability](../monitoring/table-daily-monitoring-checks/#tableavailabilitydailymonitoringchecksspec)|The default configuration of the table availability data quality checks on a table level.|[TableAvailabilityDailyMonitoringChecksSpec](../monitoring/table-daily-monitoring-checks/#tableavailabilitydailymonitoringchecksspec)| | | |
|[schema](../monitoring/table-daily-monitoring-checks/#tableschemadailymonitoringchecksspec)|The default configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaDailyMonitoringChecksSpec](../monitoring/table-daily-monitoring-checks/#tableschemadailymonitoringchecksspec)| | | |
|[custom](../profiling/table-profiling-checks/#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../profiling/table-profiling-checks/#customcheckspecmap)| | | |









___  

## DefaultObservabilityChecksSpec  
The default configuration of checks that are enabled as data observability checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profiling](../defaultobservabilitychecksyaml/#defaultprofilingobservabilitychecksettingsspec)|Default configuration of profiling checks that are enabled on tables and columns that are imported into DQOps.|[DefaultProfilingObservabilityCheckSettingsSpec](../defaultobservabilitychecksyaml/#defaultprofilingobservabilitychecksettingsspec)| | | |
|[monitoring_daily](../defaultobservabilitychecksyaml/#defaultdailymonitoringobservabilitychecksettingsspec)|Default configuration of daily monitoring checks (executed once a day, storing or overriding one result per day) that are enabled on tables and columns that are imported into DQOps.|[DefaultDailyMonitoringObservabilityCheckSettingsSpec](../defaultobservabilitychecksyaml/#defaultdailymonitoringobservabilitychecksettingsspec)| | | |
|[monitoring_monthly](../defaultobservabilitychecksyaml/#defaultmonthlymonitoringobservabilitychecksettingsspec)|Default configuration of daily monitoring checks (executed once a day, storing or overriding one result per month) that are enabled on tables and columns that are imported into DQOps.|[DefaultMonthlyMonitoringObservabilityCheckSettingsSpec](../defaultobservabilitychecksyaml/#defaultmonthlymonitoringobservabilitychecksettingsspec)| | | |









___  

## DefaultMonthlyMonitoringColumnObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability monthly monitoring checks that will be detecting anomalies
 for all columns that are imported. This configuration of checks is copied to the list of enabled column level checks on all columns that are imported, for monthly monitoring checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](../monitoring/column-monthly-monitoring-checks/#columnnullsmonthlymonitoringchecksspec)|The default configuration of column level checks that verify nulls and blanks.|[ColumnNullsMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#columnnullsmonthlymonitoringchecksspec)| | | |
|[numeric](../monitoring/column-monthly-monitoring-checks/#columnnumericmonthlymonitoringchecksspec)|The default configuration of column level checks that verify negative values.|[ColumnNumericMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#columnnumericmonthlymonitoringchecksspec)| | | |
|[strings](../monitoring/column-monthly-monitoring-checks/#columnstringsmonthlymonitoringchecksspec)|The default configuration of strings checks on a column level.|[ColumnStringsMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#columnstringsmonthlymonitoringchecksspec)| | | |
|[uniqueness](../monitoring/column-monthly-monitoring-checks/#columnuniquenessmonthlymonitoringchecksspec)|The default configuration of uniqueness checks on a column level.|[ColumnUniquenessMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#columnuniquenessmonthlymonitoringchecksspec)| | | |
|[datetime](../monitoring/column-monthly-monitoring-checks/#columndatetimemonthlymonitoringchecksspec)|The default configuration of datetime checks on a column level.|[ColumnDatetimeMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#columndatetimemonthlymonitoringchecksspec)| | | |
|[pii](../monitoring/column-monthly-monitoring-checks/#columnpiimonthlymonitoringchecksspec)|The default configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#columnpiimonthlymonitoringchecksspec)| | | |
|[bool](../monitoring/column-monthly-monitoring-checks/#columnboolmonthlymonitoringchecksspec)|The default configuration of booleans checks on a column level.|[ColumnBoolMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#columnboolmonthlymonitoringchecksspec)| | | |
|[datatype](../monitoring/column-monthly-monitoring-checks/#columndatatypemonthlymonitoringchecksspec)|The default configuration of datatype checks on a column level.|[ColumnDatatypeMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#columndatatypemonthlymonitoringchecksspec)| | | |
|[anomaly](../monitoring/column-monthly-monitoring-checks/#columnanomalymonthlymonitoringchecksspec)|The default configuration of anomaly checks on a column level.|[ColumnAnomalyMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#columnanomalymonthlymonitoringchecksspec)| | | |
|[schema](../monitoring/column-monthly-monitoring-checks/#columnschemamonthlymonitoringchecksspec)|The default configuration of schema checks on a column level.|[ColumnSchemaMonthlyMonitoringChecksSpec](../monitoring/column-monthly-monitoring-checks/#columnschemamonthlymonitoringchecksspec)| | | |
|[custom](../profiling/table-profiling-checks/#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../profiling/table-profiling-checks/#customcheckspecmap)| | | |









___  

## DefaultProfilingObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability profiling checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for profiling checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[table](../defaultobservabilitychecksyaml/#defaultprofilingtableobservabilitychecksettingsspec)|The default configuration of profiling checks on a table level.|[DefaultProfilingTableObservabilityCheckSettingsSpec](../defaultobservabilitychecksyaml/#defaultprofilingtableobservabilitychecksettingsspec)| | | |
|[column](../defaultobservabilitychecksyaml/#defaultprofilingcolumnobservabilitychecksettingsspec)|The default configuration of profiling checks on a column level.|[DefaultProfilingColumnObservabilityCheckSettingsSpec](../defaultobservabilitychecksyaml/#defaultprofilingcolumnobservabilitychecksettingsspec)| | | |









___  

## DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability daily monitoring checks that will be detecting anomalies
 for all columns that are imported. This configuration of checks is copied to the list of enabled column level checks on all columns that are imported, for daily monitoring checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](../monitoring/column-daily-monitoring-checks/#columnnullsdailymonitoringchecksspec)|The default configuration of column level checks that verify nulls and blanks.|[ColumnNullsDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#columnnullsdailymonitoringchecksspec)| | | |
|[numeric](../monitoring/column-daily-monitoring-checks/#columnnumericdailymonitoringchecksspec)|The default configuration of column level checks that verify negative values.|[ColumnNumericDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#columnnumericdailymonitoringchecksspec)| | | |
|[strings](../monitoring/column-daily-monitoring-checks/#columnstringsdailymonitoringchecksspec)|The default configuration of strings checks on a column level.|[ColumnStringsDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#columnstringsdailymonitoringchecksspec)| | | |
|[uniqueness](../monitoring/column-daily-monitoring-checks/#columnuniquenessdailymonitoringchecksspec)|The default configuration of uniqueness checks on a column level.|[ColumnUniquenessDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#columnuniquenessdailymonitoringchecksspec)| | | |
|[datetime](../monitoring/column-daily-monitoring-checks/#columndatetimedailymonitoringchecksspec)|The default configuration of datetime checks on a column level.|[ColumnDatetimeDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#columndatetimedailymonitoringchecksspec)| | | |
|[pii](../monitoring/column-daily-monitoring-checks/#columnpiidailymonitoringchecksspec)|The default configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#columnpiidailymonitoringchecksspec)| | | |
|[bool](../monitoring/column-daily-monitoring-checks/#columnbooldailymonitoringchecksspec)|The default configuration of booleans checks on a column level.|[ColumnBoolDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#columnbooldailymonitoringchecksspec)| | | |
|[datatype](../monitoring/column-daily-monitoring-checks/#columndatatypedailymonitoringchecksspec)|The default configuration of datatype checks on a column level.|[ColumnDatatypeDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#columndatatypedailymonitoringchecksspec)| | | |
|[anomaly](../monitoring/column-daily-monitoring-checks/#columnanomalydailymonitoringchecksspec)|The default configuration of anomaly checks on a column level.|[ColumnAnomalyDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#columnanomalydailymonitoringchecksspec)| | | |
|[schema](../monitoring/column-daily-monitoring-checks/#columnschemadailymonitoringchecksspec)|The default configuration of schema checks on a column level.|[ColumnSchemaDailyMonitoringChecksSpec](../monitoring/column-daily-monitoring-checks/#columnschemadailymonitoringchecksspec)| | | |
|[custom](../profiling/table-profiling-checks/#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../profiling/table-profiling-checks/#customcheckspecmap)| | | |









___  

