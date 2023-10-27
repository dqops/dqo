
## DefaultObservabilityChecksYaml  
The configuration of default data quality checks that are activated for all imported tables and columns.
 The default observability checks are stored in the settings/defaultchecks.dqochecks.yaml file in the DQOps user&#x27;s home folder.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|[kind](#specificationkind)||[SpecificationKind](#specificationkind)|table<br/>default_schedules<br/>dashboards<br/>source<br/>sensor<br/>check<br/>default_checks<br/>rule<br/>file_index<br/>settings<br/>default_notifications<br/>provider_sensor<br/>| | |
|[spec](#defaultobservabilitychecksspec)||[DefaultObservabilityChecksSpec](#defaultobservabilitychecksspec)| | | |









___  

## DefaultObservabilityChecksSpec  
The default configuration of checks that are enabled as data observability checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profiling](#defaultprofilingobservabilitychecksettingsspec)|Default configuration of profiling checks that are enabled on tables and columns that are imported into DQOps.|[DefaultProfilingObservabilityCheckSettingsSpec](#defaultprofilingobservabilitychecksettingsspec)| | | |
|[monitoring_daily](#defaultdailymonitoringobservabilitychecksettingsspec)|Default configuration of daily monitoring checks (executed once a day, storing or overriding one result per day) that are enabled on tables and columns that are imported into DQOps.|[DefaultDailyMonitoringObservabilityCheckSettingsSpec](#defaultdailymonitoringobservabilitychecksettingsspec)| | | |
|[monitoring_monthly](#defaultmonthlymonitoringobservabilitychecksettingsspec)|Default configuration of daily monitoring checks (executed once a day, storing or overriding one result per month) that are enabled on tables and columns that are imported into DQOps.|[DefaultMonthlyMonitoringObservabilityCheckSettingsSpec](#defaultmonthlymonitoringobservabilitychecksettingsspec)| | | |









___  

## DefaultProfilingColumnObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability profiling checks that will be detecting anomalies
 for all columns that are imported. This configuration of checks is copied to the list of enabled column level checks on all columns that are imported, for profiling checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](\docs\reference\yaml\profiling\column-profiling-checks\#columnnullsprofilingchecksspec)|The default configuration of column level checks that verify nulls and blanks.|[ColumnNullsProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnnullsprofilingchecksspec)| | | |
|[numeric](\docs\reference\yaml\profiling\column-profiling-checks\#columnnumericprofilingchecksspec)|The default configuration of column level checks that verify negative values.|[ColumnNumericProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnnumericprofilingchecksspec)| | | |
|[strings](\docs\reference\yaml\profiling\column-profiling-checks\#columnstringsprofilingchecksspec)|The default configuration of strings checks on a column level.|[ColumnStringsProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnstringsprofilingchecksspec)| | | |
|[uniqueness](\docs\reference\yaml\profiling\column-profiling-checks\#columnuniquenessprofilingchecksspec)|The default configuration of uniqueness checks on a column level.|[ColumnUniquenessProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnuniquenessprofilingchecksspec)| | | |
|[datetime](\docs\reference\yaml\profiling\column-profiling-checks\#columndatetimeprofilingchecksspec)|The default configuration of datetime checks on a column level.|[ColumnDatetimeProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columndatetimeprofilingchecksspec)| | | |
|[pii](\docs\reference\yaml\profiling\column-profiling-checks\#columnpiiprofilingchecksspec)|The default configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnpiiprofilingchecksspec)| | | |
|[bool](\docs\reference\yaml\profiling\column-profiling-checks\#columnboolprofilingchecksspec)|The default configuration of booleans checks on a column level.|[ColumnBoolProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnboolprofilingchecksspec)| | | |
|[datatype](\docs\reference\yaml\profiling\column-profiling-checks\#columndatatypeprofilingchecksspec)|The default configuration of datatype checks on a column level.|[ColumnDatatypeProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columndatatypeprofilingchecksspec)| | | |
|[anomaly](\docs\reference\yaml\profiling\column-profiling-checks\#columnanomalyprofilingchecksspec)|The default configuration of anomaly checks on a column level.|[ColumnAnomalyProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnanomalyprofilingchecksspec)| | | |
|[schema](\docs\reference\yaml\profiling\column-profiling-checks\#columnschemaprofilingchecksspec)|The default configuration of schema checks on a column level.|[ColumnSchemaProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnschemaprofilingchecksspec)| | | |
|[custom](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)| | | |









___  

## DefaultMonthlyMonitoringColumnObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability monthly monitoring checks that will be detecting anomalies
 for all columns that are imported. This configuration of checks is copied to the list of enabled column level checks on all columns that are imported, for monthly monitoring checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnnullsmonthlymonitoringchecksspec)|The default configuration of column level checks that verify nulls and blanks.|[ColumnNullsMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnnullsmonthlymonitoringchecksspec)| | | |
|[numeric](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnnumericmonthlymonitoringchecksspec)|The default configuration of column level checks that verify negative values.|[ColumnNumericMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnnumericmonthlymonitoringchecksspec)| | | |
|[strings](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnstringsmonthlymonitoringchecksspec)|The default configuration of strings checks on a column level.|[ColumnStringsMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnstringsmonthlymonitoringchecksspec)| | | |
|[uniqueness](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnuniquenessmonthlymonitoringchecksspec)|The default configuration of uniqueness checks on a column level.|[ColumnUniquenessMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnuniquenessmonthlymonitoringchecksspec)| | | |
|[datetime](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columndatetimemonthlymonitoringchecksspec)|The default configuration of datetime checks on a column level.|[ColumnDatetimeMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columndatetimemonthlymonitoringchecksspec)| | | |
|[pii](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnpiimonthlymonitoringchecksspec)|The default configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnpiimonthlymonitoringchecksspec)| | | |
|[bool](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnboolmonthlymonitoringchecksspec)|The default configuration of booleans checks on a column level.|[ColumnBoolMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnboolmonthlymonitoringchecksspec)| | | |
|[datatype](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columndatatypemonthlymonitoringchecksspec)|The default configuration of datatype checks on a column level.|[ColumnDatatypeMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columndatatypemonthlymonitoringchecksspec)| | | |
|[anomaly](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnanomalymonthlymonitoringchecksspec)|The default configuration of anomaly checks on a column level.|[ColumnAnomalyMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnanomalymonthlymonitoringchecksspec)| | | |
|[schema](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnschemamonthlymonitoringchecksspec)|The default configuration of schema checks on a column level.|[ColumnSchemaMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnschemamonthlymonitoringchecksspec)| | | |
|[custom](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)| | | |









___  

## DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability daily monitoring checks that will be detecting anomalies
 for all columns that are imported. This configuration of checks is copied to the list of enabled column level checks on all columns that are imported, for daily monitoring checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnnullsdailymonitoringchecksspec)|The default configuration of column level checks that verify nulls and blanks.|[ColumnNullsDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnnullsdailymonitoringchecksspec)| | | |
|[numeric](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnnumericdailymonitoringchecksspec)|The default configuration of column level checks that verify negative values.|[ColumnNumericDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnnumericdailymonitoringchecksspec)| | | |
|[strings](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnstringsdailymonitoringchecksspec)|The default configuration of strings checks on a column level.|[ColumnStringsDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnstringsdailymonitoringchecksspec)| | | |
|[uniqueness](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnuniquenessdailymonitoringchecksspec)|The default configuration of uniqueness checks on a column level.|[ColumnUniquenessDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnuniquenessdailymonitoringchecksspec)| | | |
|[datetime](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columndatetimedailymonitoringchecksspec)|The default configuration of datetime checks on a column level.|[ColumnDatetimeDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columndatetimedailymonitoringchecksspec)| | | |
|[pii](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnpiidailymonitoringchecksspec)|The default configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnpiidailymonitoringchecksspec)| | | |
|[bool](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnbooldailymonitoringchecksspec)|The default configuration of booleans checks on a column level.|[ColumnBoolDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnbooldailymonitoringchecksspec)| | | |
|[datatype](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columndatatypedailymonitoringchecksspec)|The default configuration of datatype checks on a column level.|[ColumnDatatypeDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columndatatypedailymonitoringchecksspec)| | | |
|[anomaly](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnanomalydailymonitoringchecksspec)|The default configuration of anomaly checks on a column level.|[ColumnAnomalyDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnanomalydailymonitoringchecksspec)| | | |
|[schema](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnschemadailymonitoringchecksspec)|The default configuration of schema checks on a column level.|[ColumnSchemaDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnschemadailymonitoringchecksspec)| | | |
|[custom](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)| | | |









___  

## DefaultProfilingObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability profiling checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for profiling checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[table](#defaultprofilingtableobservabilitychecksettingsspec)|The default configuration of profiling checks on a table level.|[DefaultProfilingTableObservabilityCheckSettingsSpec](#defaultprofilingtableobservabilitychecksettingsspec)| | | |
|[column](#defaultprofilingcolumnobservabilitychecksettingsspec)|The default configuration of profiling checks on a column level.|[DefaultProfilingColumnObservabilityCheckSettingsSpec](#defaultprofilingcolumnobservabilitychecksettingsspec)| | | |









___  

## DefaultMonthlyMonitoringObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability monthly monitoring checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for monthly profiling checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[table](#defaultmonthlymonitoringtableobservabilitychecksettingsspec)|The default configuration of monthly monitoring checks on a table level.|[DefaultMonthlyMonitoringTableObservabilityCheckSettingsSpec](#defaultmonthlymonitoringtableobservabilitychecksettingsspec)| | | |
|[column](#defaultmonthlymonitoringcolumnobservabilitychecksettingsspec)|The default configuration of monthly monitoring checks on a column level.|[DefaultMonthlyMonitoringColumnObservabilityCheckSettingsSpec](#defaultmonthlymonitoringcolumnobservabilitychecksettingsspec)| | | |









___  

## DefaultDailyMonitoringTableObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability daily monitoring checks that will be detecting anomalies
 for all tables that are imported. This configuration of checks is copied to the list of enabled table level checks on all tables that are imported, for daily monitoring checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tablevolumedailymonitoringchecksspec)|The default configuration of volume data quality checks on a table level.|[TableVolumeDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tablevolumedailymonitoringchecksspec)| | | |
|[availability](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tableavailabilitydailymonitoringchecksspec)|The default configuration of the table availability data quality checks on a table level.|[TableAvailabilityDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tableavailabilitydailymonitoringchecksspec)| | | |
|[schema](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tableschemadailymonitoringchecksspec)|The default configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tableschemadailymonitoringchecksspec)| | | |
|[custom](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)| | | |









___  

## DefaultProfilingTableObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability profiling checks that will be detecting anomalies
 for all tables that are imported. This configuration of checks is copied to the list of enabled table level checks on all tables that are imported, for profiling checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](\docs\reference\yaml\profiling\table-profiling-checks\#tablevolumeprofilingchecksspec)|The default configuration of volume data quality checks on a table level.|[TableVolumeProfilingChecksSpec](\docs\reference\yaml\profiling\table-profiling-checks\#tablevolumeprofilingchecksspec)| | | |
|[availability](\docs\reference\yaml\profiling\table-profiling-checks\#tableavailabilityprofilingchecksspec)|The default configuration of the table availability data quality checks on a table level.|[TableAvailabilityProfilingChecksSpec](\docs\reference\yaml\profiling\table-profiling-checks\#tableavailabilityprofilingchecksspec)| | | |
|[schema](\docs\reference\yaml\profiling\table-profiling-checks\#tableschemaprofilingchecksspec)|The default configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaProfilingChecksSpec](\docs\reference\yaml\profiling\table-profiling-checks\#tableschemaprofilingchecksspec)| | | |
|[custom](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)| | | |









___  

## DefaultDailyMonitoringObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability daily monitoring checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for daily monitoring checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[table](#defaultdailymonitoringtableobservabilitychecksettingsspec)|The default configuration of daily monitoring checks on a table level.|[DefaultDailyMonitoringTableObservabilityCheckSettingsSpec](#defaultdailymonitoringtableobservabilitychecksettingsspec)| | | |
|[column](#defaultdailymonitoringcolumnobservabilitychecksettingsspec)|The default configuration of daily monitoring checks on a column level.|[DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec](#defaultdailymonitoringcolumnobservabilitychecksettingsspec)| | | |









___  

## DefaultMonthlyMonitoringTableObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability monthly monitoring checks that will be detecting anomalies
 for all tables that are imported. This configuration of checks is copied to the list of enabled table level checks on all tables that are imported, for monthly monitoring checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tablevolumemonthlymonitoringchecksspec)|The default configuration of volume data quality checks on a table level.|[TableVolumeMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tablevolumemonthlymonitoringchecksspec)| | | |
|[availability](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tableavailabilitymonthlymonitoringchecksspec)|The default configuration of the table availability data quality checks on a table level.|[TableAvailabilityMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tableavailabilitymonthlymonitoringchecksspec)| | | |
|[schema](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tableschemamonthlymonitoringchecksspec)|The default configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tableschemamonthlymonitoringchecksspec)| | | |
|[custom](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)| | | |









___  

