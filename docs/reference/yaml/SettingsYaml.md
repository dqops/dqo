
## SettingsSpec  
Settings specification.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|editor_name|Editor name spec (VSC, Eclipse, Intellj)|string| | | |
|editor_path|Editor path on user&#x27;s computer|string| | | |
|api_key|Api key|string| | | |
|instance_signature_key|DQO instance signature key used to sign keys. This should be a Base64 encoded binary key at a 32 bytes length.|string| | | |
|time_zone|Default IANA time zone name of the server. This time zone is used to convert the time of UTC timestamps values returned from databases to a uniform local date and time. The default value is the local time zone of the DQO server instance.|string| | | |
|[default_schedules](\docs\reference\yaml\connectionyaml\#recurringschedulesspec)|Configuration of the default schedules that are assigned to new connections to data sources that are imported. The settings that are configured take precedence over configuration from the DQO command line parameters and environment variables.|[RecurringSchedulesSpec](\docs\reference\yaml\connectionyaml\#recurringschedulesspec)| | | |
|[default_data_observability_checks](#defaultobservabilitychecksettingsspec)|The default configuration of Data Observability checks that are tracking volume, detecting schema drifts and basic anomalies on data.|[DefaultObservabilityCheckSettingsSpec](#defaultobservabilitychecksettingsspec)| | | |









___  

## DefaultObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profiling](#defaultprofilingobservabilitychecksettingsspec)|Default configuration of advanced profiling checks that are enabled on tables and columns that are imported into DQO.|[DefaultProfilingObservabilityCheckSettingsSpec](#defaultprofilingobservabilitychecksettingsspec)| | | |
|[recurring_daily](#defaultdailyrecurringobservabilitychecksettingsspec)|Default configuration of daily recurring checks (executed once a day, storing or overriding one result per day) that are enabled on tables and columns that are imported into DQO.|[DefaultDailyRecurringObservabilityCheckSettingsSpec](#defaultdailyrecurringobservabilitychecksettingsspec)| | | |
|[recurring_monthly](#defaultmonthlyrecurringobservabilitychecksettingsspec)|Default configuration of daily recurring checks (executed once a day, storing or overriding one result per month) that are enabled on tables and columns that are imported into DQO.|[DefaultMonthlyRecurringObservabilityCheckSettingsSpec](#defaultmonthlyrecurringobservabilitychecksettingsspec)| | | |









___  

## DefaultMonthlyRecurringObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability monthly recurring checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for monthly profiling checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[table_volume](\docs\reference\yaml\recurring\table-monthly-recurring-checks\#tablevolumemonthlyrecurringchecksspec)|The default configuration of volume data quality checks on a table level.|[TableVolumeMonthlyRecurringChecksSpec](\docs\reference\yaml\recurring\table-monthly-recurring-checks\#tablevolumemonthlyrecurringchecksspec)| | | |
|[table_availability](\docs\reference\yaml\recurring\table-monthly-recurring-checks\#tableavailabilitymonthlyrecurringchecksspec)|The default configuration of the table availability data quality checks on a table level.|[TableAvailabilityMonthlyRecurringChecksSpec](\docs\reference\yaml\recurring\table-monthly-recurring-checks\#tableavailabilitymonthlyrecurringchecksspec)| | | |
|[table_schema](\docs\reference\yaml\recurring\table-monthly-recurring-checks\#tableschemamonthlyrecurringchecksspec)|The default configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaMonthlyRecurringChecksSpec](\docs\reference\yaml\recurring\table-monthly-recurring-checks\#tableschemamonthlyrecurringchecksspec)| | | |
|[column_nulls](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columnnullsmonthlyrecurringchecksspec)|The default configuration of column level checks that verify nulls and blanks.|[ColumnNullsMonthlyRecurringChecksSpec](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columnnullsmonthlyrecurringchecksspec)| | | |
|[column_numeric](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columnnumericmonthlyrecurringchecksspec)|The default configuration of column level checks that verify negative values.|[ColumnNumericMonthlyRecurringChecksSpec](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columnnumericmonthlyrecurringchecksspec)| | | |
|[column_strings](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columnstringsmonthlyrecurringchecksspec)|The default configuration of strings checks on a column level.|[ColumnStringsMonthlyRecurringChecksSpec](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columnstringsmonthlyrecurringchecksspec)| | | |
|[column_uniqueness](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columnuniquenessmonthlyrecurringchecksspec)|The default configuration of uniqueness checks on a column level.|[ColumnUniquenessMonthlyRecurringChecksSpec](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columnuniquenessmonthlyrecurringchecksspec)| | | |
|[column_datetime](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columndatetimemonthlyrecurringchecksspec)|The default configuration of datetime checks on a column level.|[ColumnDatetimeMonthlyRecurringChecksSpec](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columndatetimemonthlyrecurringchecksspec)| | | |
|[column_pii](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columnpiimonthlyrecurringchecksspec)|The default configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiMonthlyRecurringChecksSpec](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columnpiimonthlyrecurringchecksspec)| | | |
|[column_bool](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columnboolmonthlyrecurringchecksspec)|The default configuration of booleans checks on a column level.|[ColumnBoolMonthlyRecurringChecksSpec](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columnboolmonthlyrecurringchecksspec)| | | |
|[column_datatype](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columndatatypemonthlyrecurringchecksspec)|The default configuration of datatype checks on a column level.|[ColumnDatatypeMonthlyRecurringChecksSpec](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columndatatypemonthlyrecurringchecksspec)| | | |
|[column_anomaly](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columnanomalymonthlyrecurringchecksspec)|The default configuration of anomaly checks on a column level.|[ColumnAnomalyMonthlyRecurringChecksSpec](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columnanomalymonthlyrecurringchecksspec)| | | |
|[column_schema](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columnschemamonthlyrecurringchecksspec)|The default configuration of schema checks on a column level.|[ColumnSchemaMonthlyRecurringChecksSpec](\docs\reference\yaml\recurring\column-monthly-recurring-checks\#columnschemamonthlyrecurringchecksspec)| | | |









___  

## SettingsYaml  
DQO local settings that are stored in the .localsettings.dqosettings.yaml file in the user&#x27;s DQO home folder.
 The local settings contain the current DQO Cloud API Key and other settings. The local settings take precedence over parameters
 passed when starting DQO.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|kind||enum|table<br/>dashboards<br/>source<br/>sensor<br/>check<br/>rule<br/>file_index<br/>settings<br/>provider_sensor<br/>| | |
|[spec](#settingsspec)||[SettingsSpec](#settingsspec)| | | |









___  

## DefaultProfilingObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability advanced profiling checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for profiling checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[table_volume](\docs\reference\yaml\profiling\table-profiling-checks\#tablevolumeprofilingchecksspec)|The default configuration of volume data quality checks on a table level.|[TableVolumeProfilingChecksSpec](\docs\reference\yaml\profiling\table-profiling-checks\#tablevolumeprofilingchecksspec)| | | |
|[table_availability](\docs\reference\yaml\profiling\table-profiling-checks\#tableavailabilityprofilingchecksspec)|The default configuration of the table availability data quality checks on a table level.|[TableAvailabilityProfilingChecksSpec](\docs\reference\yaml\profiling\table-profiling-checks\#tableavailabilityprofilingchecksspec)| | | |
|[table_schema](\docs\reference\yaml\profiling\table-profiling-checks\#tableschemaprofilingchecksspec)|The default configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaProfilingChecksSpec](\docs\reference\yaml\profiling\table-profiling-checks\#tableschemaprofilingchecksspec)| | | |
|[column_nulls](\docs\reference\yaml\profiling\column-profiling-checks\#columnnullsprofilingchecksspec)|The default configuration of column level checks that verify nulls and blanks.|[ColumnNullsProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnnullsprofilingchecksspec)| | | |
|[column_numeric](\docs\reference\yaml\profiling\column-profiling-checks\#columnnumericprofilingchecksspec)|The default configuration of column level checks that verify negative values.|[ColumnNumericProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnnumericprofilingchecksspec)| | | |
|[column_strings](\docs\reference\yaml\profiling\column-profiling-checks\#columnstringsprofilingchecksspec)|The default configuration of strings checks on a column level.|[ColumnStringsProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnstringsprofilingchecksspec)| | | |
|[column_uniqueness](\docs\reference\yaml\profiling\column-profiling-checks\#columnuniquenessprofilingchecksspec)|The default configuration of uniqueness checks on a column level.|[ColumnUniquenessProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnuniquenessprofilingchecksspec)| | | |
|[column_datetime](\docs\reference\yaml\profiling\column-profiling-checks\#columndatetimeprofilingchecksspec)|The default configuration of datetime checks on a column level.|[ColumnDatetimeProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columndatetimeprofilingchecksspec)| | | |
|[column_pii](\docs\reference\yaml\profiling\column-profiling-checks\#columnpiiprofilingchecksspec)|The default configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnpiiprofilingchecksspec)| | | |
|[column_bool](\docs\reference\yaml\profiling\column-profiling-checks\#columnboolprofilingchecksspec)|The default configuration of booleans checks on a column level.|[ColumnBoolProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnboolprofilingchecksspec)| | | |
|[column_datatype](\docs\reference\yaml\profiling\column-profiling-checks\#columndatatypeprofilingchecksspec)|The default configuration of datatype checks on a column level.|[ColumnDatatypeProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columndatatypeprofilingchecksspec)| | | |
|[column_anomaly](\docs\reference\yaml\profiling\column-profiling-checks\#columnanomalyprofilingchecksspec)|The default configuration of anomaly checks on a column level.|[ColumnAnomalyProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnanomalyprofilingchecksspec)| | | |
|[column_schema](\docs\reference\yaml\profiling\column-profiling-checks\#columnschemaprofilingchecksspec)|The default configuration of schema checks on a column level.|[ColumnSchemaProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnschemaprofilingchecksspec)| | | |









___  

## DefaultDailyRecurringObservabilityCheckSettingsSpec  
The default configuration of checks that are enabled as data observability daily recurring checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for daily recurring checks only.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[table_volume](\docs\reference\yaml\recurring\table-daily-recurring-checks\#tablevolumedailyrecurringchecksspec)|The default configuration of volume data quality checks on a table level.|[TableVolumeDailyRecurringChecksSpec](\docs\reference\yaml\recurring\table-daily-recurring-checks\#tablevolumedailyrecurringchecksspec)| | | |
|[table_availability](\docs\reference\yaml\recurring\table-daily-recurring-checks\#tableavailabilitydailyrecurringchecksspec)|The default configuration of the table availability data quality checks on a table level.|[TableAvailabilityDailyRecurringChecksSpec](\docs\reference\yaml\recurring\table-daily-recurring-checks\#tableavailabilitydailyrecurringchecksspec)| | | |
|[table_schema](\docs\reference\yaml\recurring\table-daily-recurring-checks\#tableschemadailyrecurringchecksspec)|The default configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaDailyRecurringChecksSpec](\docs\reference\yaml\recurring\table-daily-recurring-checks\#tableschemadailyrecurringchecksspec)| | | |
|[column_nulls](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columnnullsdailyrecurringchecksspec)|The default configuration of column level checks that verify nulls and blanks.|[ColumnNullsDailyRecurringChecksSpec](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columnnullsdailyrecurringchecksspec)| | | |
|[column_numeric](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columnnumericdailyrecurringchecksspec)|The default configuration of column level checks that verify negative values.|[ColumnNumericDailyRecurringChecksSpec](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columnnumericdailyrecurringchecksspec)| | | |
|[column_strings](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columnstringsdailyrecurringchecksspec)|The default configuration of strings checks on a column level.|[ColumnStringsDailyRecurringChecksSpec](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columnstringsdailyrecurringchecksspec)| | | |
|[column_uniqueness](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columnuniquenessdailyrecurringchecksspec)|The default configuration of uniqueness checks on a column level.|[ColumnUniquenessDailyRecurringChecksSpec](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columnuniquenessdailyrecurringchecksspec)| | | |
|[column_datetime](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columndatetimedailyrecurringchecksspec)|The default configuration of datetime checks on a column level.|[ColumnDatetimeDailyRecurringChecksSpec](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columndatetimedailyrecurringchecksspec)| | | |
|[column_pii](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columnpiidailyrecurringchecksspec)|The default configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiDailyRecurringChecksSpec](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columnpiidailyrecurringchecksspec)| | | |
|[column_bool](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columnbooldailyrecurringchecksspec)|The default configuration of booleans checks on a column level.|[ColumnBoolDailyRecurringChecksSpec](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columnbooldailyrecurringchecksspec)| | | |
|[column_datatype](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columndatatypedailyrecurringchecksspec)|The default configuration of datatype checks on a column level.|[ColumnDatatypeDailyRecurringChecksSpec](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columndatatypedailyrecurringchecksspec)| | | |
|[column_anomaly](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columnanomalydailyrecurringchecksspec)|The default configuration of anomaly checks on a column level.|[ColumnAnomalyDailyRecurringChecksSpec](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columnanomalydailyrecurringchecksspec)| | | |
|[column_schema](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columnschemadailyrecurringchecksspec)|The default configuration of schema checks on a column level.|[ColumnSchemaDailyRecurringChecksSpec](\docs\reference\yaml\recurring\column-daily-recurring-checks\#columnschemadailyrecurringchecksspec)| | | |









___  

