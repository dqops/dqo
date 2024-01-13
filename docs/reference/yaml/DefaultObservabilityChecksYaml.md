# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## DefaultProfilingTableObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability profiling checks that will be detecting anomalies
 for all tables that are imported. This configuration of checks is copied to the list of enabled table level checks on all tables that are imported, for profiling checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](./profiling/table-profiling-checks.md#TableVolumeProfilingChecksSpec)|The default configuration of volume data quality checks on a table level.|[TableVolumeProfilingChecksSpec](./profiling/table-profiling-checks.md#TableVolumeProfilingChecksSpec)| | | |
|[availability](./profiling/table-profiling-checks.md#TableAvailabilityProfilingChecksSpec)|The default configuration of the table availability data quality checks on a table level.|[TableAvailabilityProfilingChecksSpec](./profiling/table-profiling-checks.md#TableAvailabilityProfilingChecksSpec)| | | |
|[schema](./profiling/table-profiling-checks.md#TableSchemaProfilingChecksSpec)|The default configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaProfilingChecksSpec](./profiling/table-profiling-checks.md#TableSchemaProfilingChecksSpec)| | | |
|[custom](./profiling/table-profiling-checks.md#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](./profiling/table-profiling-checks.md#CustomCheckSpecMap)| | | |









___


## DefaultMonthlyMonitoringColumnObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability monthly monitoring checks that will be detecting anomalies
 for all columns that are imported. This configuration of checks is copied to the list of enabled column level checks on all columns that are imported, for monthly monitoring checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](./monitoring/column-monthly-monitoring-checks.md#ColumnNullsMonthlyMonitoringChecksSpec)|The default configuration of column level checks that verify nulls and blanks.|[ColumnNullsMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#ColumnNullsMonthlyMonitoringChecksSpec)| | | |
|[numeric](./monitoring/column-monthly-monitoring-checks.md#ColumnNumericMonthlyMonitoringChecksSpec)|The default configuration of column level checks that verify negative values.|[ColumnNumericMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#ColumnNumericMonthlyMonitoringChecksSpec)| | | |
|[text](./monitoring/column-monthly-monitoring-checks.md#ColumnTextMonthlyMonitoringChecksSpec)|The default configuration of strings checks on a column level.|[ColumnTextMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#ColumnTextMonthlyMonitoringChecksSpec)| | | |
|[uniqueness](./monitoring/column-monthly-monitoring-checks.md#ColumnUniquenessMonthlyMonitoringChecksSpec)|The default configuration of uniqueness checks on a column level.|[ColumnUniquenessMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#ColumnUniquenessMonthlyMonitoringChecksSpec)| | | |
|[datetime](./monitoring/column-monthly-monitoring-checks.md#ColumnDatetimeMonthlyMonitoringChecksSpec)|The default configuration of datetime checks on a column level.|[ColumnDatetimeMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#ColumnDatetimeMonthlyMonitoringChecksSpec)| | | |
|[pii](./monitoring/column-monthly-monitoring-checks.md#ColumnPiiMonthlyMonitoringChecksSpec)|The default configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#ColumnPiiMonthlyMonitoringChecksSpec)| | | |
|[bool](./monitoring/column-monthly-monitoring-checks.md#ColumnBoolMonthlyMonitoringChecksSpec)|The default configuration of booleans checks on a column level.|[ColumnBoolMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#ColumnBoolMonthlyMonitoringChecksSpec)| | | |
|[datatype](./monitoring/column-monthly-monitoring-checks.md#ColumnDatatypeMonthlyMonitoringChecksSpec)|The default configuration of datatype checks on a column level.|[ColumnDatatypeMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#ColumnDatatypeMonthlyMonitoringChecksSpec)| | | |
|[anomaly](./monitoring/column-monthly-monitoring-checks.md#ColumnAnomalyMonthlyMonitoringChecksSpec)|The default configuration of anomaly checks on a column level.|[ColumnAnomalyMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#ColumnAnomalyMonthlyMonitoringChecksSpec)| | | |
|[schema](./monitoring/column-monthly-monitoring-checks.md#ColumnSchemaMonthlyMonitoringChecksSpec)|The default configuration of schema checks on a column level.|[ColumnSchemaMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#ColumnSchemaMonthlyMonitoringChecksSpec)| | | |
|[custom](./profiling/table-profiling-checks.md#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](./profiling/table-profiling-checks.md#CustomCheckSpecMap)| | | |









___


## DefaultProfilingObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability profiling checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for profiling checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[table](./DefaultObservabilityChecksYaml.md#DefaultProfilingTableObservabilityCheckSettingsSpec)|The default configuration of profiling checks on a table level.|[DefaultProfilingTableObservabilityCheckSettingsSpec](./DefaultObservabilityChecksYaml.md#DefaultProfilingTableObservabilityCheckSettingsSpec)| | | |
|[column](./DefaultObservabilityChecksYaml.md#DefaultProfilingColumnObservabilityCheckSettingsSpec)|The default configuration of profiling checks on a column level.|[DefaultProfilingColumnObservabilityCheckSettingsSpec](./DefaultObservabilityChecksYaml.md#DefaultProfilingColumnObservabilityCheckSettingsSpec)| | | |









___


## DefaultDailyMonitoringTableObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability daily monitoring checks that will be detecting anomalies
 for all tables that are imported. This configuration of checks is copied to the list of enabled table level checks on all tables that are imported, for daily monitoring checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](./monitoring/table-daily-monitoring-checks.md#TableVolumeDailyMonitoringChecksSpec)|The default configuration of volume data quality checks on a table level.|[TableVolumeDailyMonitoringChecksSpec](./monitoring/table-daily-monitoring-checks.md#TableVolumeDailyMonitoringChecksSpec)| | | |
|[availability](./monitoring/table-daily-monitoring-checks.md#TableAvailabilityDailyMonitoringChecksSpec)|The default configuration of the table availability data quality checks on a table level.|[TableAvailabilityDailyMonitoringChecksSpec](./monitoring/table-daily-monitoring-checks.md#TableAvailabilityDailyMonitoringChecksSpec)| | | |
|[schema](./monitoring/table-daily-monitoring-checks.md#TableSchemaDailyMonitoringChecksSpec)|The default configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaDailyMonitoringChecksSpec](./monitoring/table-daily-monitoring-checks.md#TableSchemaDailyMonitoringChecksSpec)| | | |
|[custom](./profiling/table-profiling-checks.md#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](./profiling/table-profiling-checks.md#CustomCheckSpecMap)| | | |









___


## DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability daily monitoring checks that will be detecting anomalies
 for all columns that are imported. This configuration of checks is copied to the list of enabled column level checks on all columns that are imported, for daily monitoring checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](./monitoring/column-daily-monitoring-checks.md#ColumnNullsDailyMonitoringChecksSpec)|The default configuration of column level checks that verify nulls and blanks.|[ColumnNullsDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#ColumnNullsDailyMonitoringChecksSpec)| | | |
|[numeric](./monitoring/column-daily-monitoring-checks.md#ColumnNumericDailyMonitoringChecksSpec)|The default configuration of column level checks that verify negative values.|[ColumnNumericDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#ColumnNumericDailyMonitoringChecksSpec)| | | |
|[text](./monitoring/column-daily-monitoring-checks.md#ColumnTextDailyMonitoringChecksSpec)|The default configuration of strings checks on a column level.|[ColumnTextDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#ColumnTextDailyMonitoringChecksSpec)| | | |
|[uniqueness](./monitoring/column-daily-monitoring-checks.md#ColumnUniquenessDailyMonitoringChecksSpec)|The default configuration of uniqueness checks on a column level.|[ColumnUniquenessDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#ColumnUniquenessDailyMonitoringChecksSpec)| | | |
|[datetime](./monitoring/column-daily-monitoring-checks.md#ColumnDatetimeDailyMonitoringChecksSpec)|The default configuration of datetime checks on a column level.|[ColumnDatetimeDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#ColumnDatetimeDailyMonitoringChecksSpec)| | | |
|[pii](./monitoring/column-daily-monitoring-checks.md#ColumnPiiDailyMonitoringChecksSpec)|The default configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#ColumnPiiDailyMonitoringChecksSpec)| | | |
|[bool](./monitoring/column-daily-monitoring-checks.md#ColumnBoolDailyMonitoringChecksSpec)|The default configuration of booleans checks on a column level.|[ColumnBoolDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#ColumnBoolDailyMonitoringChecksSpec)| | | |
|[datatype](./monitoring/column-daily-monitoring-checks.md#ColumnDatatypeDailyMonitoringChecksSpec)|The default configuration of datatype checks on a column level.|[ColumnDatatypeDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#ColumnDatatypeDailyMonitoringChecksSpec)| | | |
|[anomaly](./monitoring/column-daily-monitoring-checks.md#ColumnAnomalyDailyMonitoringChecksSpec)|The default configuration of anomaly checks on a column level.|[ColumnAnomalyDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#ColumnAnomalyDailyMonitoringChecksSpec)| | | |
|[schema](./monitoring/column-daily-monitoring-checks.md#ColumnSchemaDailyMonitoringChecksSpec)|The default configuration of schema checks on a column level.|[ColumnSchemaDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#ColumnSchemaDailyMonitoringChecksSpec)| | | |
|[custom](./profiling/table-profiling-checks.md#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](./profiling/table-profiling-checks.md#CustomCheckSpecMap)| | | |









___


## DefaultObservabilityChecksSpec
The default configuration of checks that are enabled as data observability checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profiling](./DefaultObservabilityChecksYaml.md#DefaultProfilingObservabilityCheckSettingsSpec)|Default configuration of profiling checks that are enabled on tables and columns that are imported into DQOps.|[DefaultProfilingObservabilityCheckSettingsSpec](./DefaultObservabilityChecksYaml.md#DefaultProfilingObservabilityCheckSettingsSpec)| | | |
|[monitoring_daily](./DefaultObservabilityChecksYaml.md#DefaultDailyMonitoringObservabilityCheckSettingsSpec)|Default configuration of daily monitoring checks (executed once a day, storing or overriding one result per day) that are enabled on tables and columns that are imported into DQOps.|[DefaultDailyMonitoringObservabilityCheckSettingsSpec](./DefaultObservabilityChecksYaml.md#DefaultDailyMonitoringObservabilityCheckSettingsSpec)| | | |
|[monitoring_monthly](./DefaultObservabilityChecksYaml.md#DefaultMonthlyMonitoringObservabilityCheckSettingsSpec)|Default configuration of daily monitoring checks (executed once a day, storing or overriding one result per month) that are enabled on tables and columns that are imported into DQOps.|[DefaultMonthlyMonitoringObservabilityCheckSettingsSpec](./DefaultObservabilityChecksYaml.md#DefaultMonthlyMonitoringObservabilityCheckSettingsSpec)| | | |









___


## DefaultDailyMonitoringObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability daily monitoring checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for daily monitoring checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[table](./DefaultObservabilityChecksYaml.md#DefaultDailyMonitoringTableObservabilityCheckSettingsSpec)|The default configuration of daily monitoring checks on a table level.|[DefaultDailyMonitoringTableObservabilityCheckSettingsSpec](./DefaultObservabilityChecksYaml.md#DefaultDailyMonitoringTableObservabilityCheckSettingsSpec)| | | |
|[column](./DefaultObservabilityChecksYaml.md#DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec)|The default configuration of daily monitoring checks on a column level.|[DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec](./DefaultObservabilityChecksYaml.md#DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec)| | | |









___


## DefaultProfilingColumnObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability profiling checks that will be detecting anomalies
 for all columns that are imported. This configuration of checks is copied to the list of enabled column level checks on all columns that are imported, for profiling checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](./profiling/column-profiling-checks.md#ColumnNullsProfilingChecksSpec)|The default configuration of column level checks that verify nulls and blanks.|[ColumnNullsProfilingChecksSpec](./profiling/column-profiling-checks.md#ColumnNullsProfilingChecksSpec)| | | |
|[numeric](./profiling/column-profiling-checks.md#ColumnNumericProfilingChecksSpec)|The default configuration of column level checks that verify negative values.|[ColumnNumericProfilingChecksSpec](./profiling/column-profiling-checks.md#ColumnNumericProfilingChecksSpec)| | | |
|[text](./profiling/column-profiling-checks.md#ColumnTextProfilingChecksSpec)|The default configuration of strings checks on a column level.|[ColumnTextProfilingChecksSpec](./profiling/column-profiling-checks.md#ColumnTextProfilingChecksSpec)| | | |
|[uniqueness](./profiling/column-profiling-checks.md#ColumnUniquenessProfilingChecksSpec)|The default configuration of uniqueness checks on a column level.|[ColumnUniquenessProfilingChecksSpec](./profiling/column-profiling-checks.md#ColumnUniquenessProfilingChecksSpec)| | | |
|[datetime](./profiling/column-profiling-checks.md#ColumnDatetimeProfilingChecksSpec)|The default configuration of datetime checks on a column level.|[ColumnDatetimeProfilingChecksSpec](./profiling/column-profiling-checks.md#ColumnDatetimeProfilingChecksSpec)| | | |
|[pii](./profiling/column-profiling-checks.md#ColumnPiiProfilingChecksSpec)|The default configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiProfilingChecksSpec](./profiling/column-profiling-checks.md#ColumnPiiProfilingChecksSpec)| | | |
|[bool](./profiling/column-profiling-checks.md#ColumnBoolProfilingChecksSpec)|The default configuration of booleans checks on a column level.|[ColumnBoolProfilingChecksSpec](./profiling/column-profiling-checks.md#ColumnBoolProfilingChecksSpec)| | | |
|[datatype](./profiling/column-profiling-checks.md#ColumnDatatypeProfilingChecksSpec)|The default configuration of datatype checks on a column level.|[ColumnDatatypeProfilingChecksSpec](./profiling/column-profiling-checks.md#ColumnDatatypeProfilingChecksSpec)| | | |
|[anomaly](./profiling/column-profiling-checks.md#ColumnAnomalyProfilingChecksSpec)|The default configuration of anomaly checks on a column level.|[ColumnAnomalyProfilingChecksSpec](./profiling/column-profiling-checks.md#ColumnAnomalyProfilingChecksSpec)| | | |
|[schema](./profiling/column-profiling-checks.md#ColumnSchemaProfilingChecksSpec)|The default configuration of schema checks on a column level.|[ColumnSchemaProfilingChecksSpec](./profiling/column-profiling-checks.md#ColumnSchemaProfilingChecksSpec)| | | |
|[custom](./profiling/table-profiling-checks.md#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](./profiling/table-profiling-checks.md#CustomCheckSpecMap)| | | |









___


## DefaultObservabilityChecksYaml
The configuration of default data quality checks that are activated for all imported tables and columns.
 The default observability checks are stored in the *$DQO_USER_HOME/settings/defaultchecks.dqochecks.yaml* file in the DQOps user&#x27;s home folder.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|kind||enum|default_schedules<br/>settings<br/>default_notifications<br/>rule<br/>sensor<br/>source<br/>check<br/>dashboards<br/>default_checks<br/>table<br/>provider_sensor<br/>file_index<br/>| | |
|[spec](./DefaultObservabilityChecksYaml.md#DefaultObservabilityChecksSpec)||[DefaultObservabilityChecksSpec](./DefaultObservabilityChecksYaml.md#DefaultObservabilityChecksSpec)| | | |









___


## DefaultMonthlyMonitoringObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability monthly monitoring checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for monthly profiling checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[table](./DefaultObservabilityChecksYaml.md#DefaultMonthlyMonitoringTableObservabilityCheckSettingsSpec)|The default configuration of monthly monitoring checks on a table level.|[DefaultMonthlyMonitoringTableObservabilityCheckSettingsSpec](./DefaultObservabilityChecksYaml.md#DefaultMonthlyMonitoringTableObservabilityCheckSettingsSpec)| | | |
|[column](./DefaultObservabilityChecksYaml.md#DefaultMonthlyMonitoringColumnObservabilityCheckSettingsSpec)|The default configuration of monthly monitoring checks on a column level.|[DefaultMonthlyMonitoringColumnObservabilityCheckSettingsSpec](./DefaultObservabilityChecksYaml.md#DefaultMonthlyMonitoringColumnObservabilityCheckSettingsSpec)| | | |









___


## DefaultMonthlyMonitoringTableObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability monthly monitoring checks that will be detecting anomalies
 for all tables that are imported. This configuration of checks is copied to the list of enabled table level checks on all tables that are imported, for monthly monitoring checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](./monitoring/table-monthly-monitoring-checks.md#TableVolumeMonthlyMonitoringChecksSpec)|The default configuration of volume data quality checks on a table level.|[TableVolumeMonthlyMonitoringChecksSpec](./monitoring/table-monthly-monitoring-checks.md#TableVolumeMonthlyMonitoringChecksSpec)| | | |
|[availability](./monitoring/table-monthly-monitoring-checks.md#TableAvailabilityMonthlyMonitoringChecksSpec)|The default configuration of the table availability data quality checks on a table level.|[TableAvailabilityMonthlyMonitoringChecksSpec](./monitoring/table-monthly-monitoring-checks.md#TableAvailabilityMonthlyMonitoringChecksSpec)| | | |
|[schema](./monitoring/table-monthly-monitoring-checks.md#TableSchemaMonthlyMonitoringChecksSpec)|The default configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaMonthlyMonitoringChecksSpec](./monitoring/table-monthly-monitoring-checks.md#TableSchemaMonthlyMonitoringChecksSpec)| | | |
|[custom](./profiling/table-profiling-checks.md#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](./profiling/table-profiling-checks.md#CustomCheckSpecMap)| | | |









___


