# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## DefaultObservabilityChecksYaml
The configuration of default data quality checks that are activated for all imported tables and columns.
 The default observability checks are stored in the *$DQO_USER_HOME/settings/default.dqodefaultchecks.yaml* file in the DQOps user&#x27;s home folder.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`api_version`</span>|DQOps YAML schema version|*string*| |dqo/v1| |
|<span class="no-wrap-code ">`kind`</span>|File type|*enum*|*source*<br/>*table*<br/>*sensor*<br/>*provider_sensor*<br/>*rule*<br/>*check*<br/>*settings*<br/>*file_index*<br/>*dashboards*<br/>*default_schedules*<br/>*default_checks*<br/>*default_notifications*<br/>|default_checks| |
|<span class="no-wrap-code ">[`spec`](./DefaultObservabilityChecksYaml.md#defaultobservabilitychecksspec)</span>|The configuration object with the definition of the default data observability checks|*[DefaultObservabilityChecksSpec](./DefaultObservabilityChecksYaml.md#defaultobservabilitychecksspec)*| | | |









___


## DefaultObservabilityChecksSpec
The default configuration of checks that are enabled as data observability checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profiling`](./DefaultObservabilityChecksYaml.md#defaultprofilingobservabilitychecksettingsspec)</span>|Default configuration of profiling checks that are enabled on tables and columns that are imported into DQOps.|*[DefaultProfilingObservabilityCheckSettingsSpec](./DefaultObservabilityChecksYaml.md#defaultprofilingobservabilitychecksettingsspec)*| | | |
|<span class="no-wrap-code ">[`monitoring_daily`](./DefaultObservabilityChecksYaml.md#defaultdailymonitoringobservabilitychecksettingsspec)</span>|Default configuration of daily monitoring checks (executed once a day, storing or overriding one result per day) that are enabled on tables and columns that are imported into DQOps.|*[DefaultDailyMonitoringObservabilityCheckSettingsSpec](./DefaultObservabilityChecksYaml.md#defaultdailymonitoringobservabilitychecksettingsspec)*| | | |
|<span class="no-wrap-code ">[`monitoring_monthly`](./DefaultObservabilityChecksYaml.md#defaultmonthlymonitoringobservabilitychecksettingsspec)</span>|Default configuration of daily monitoring checks (executed once a day, storing or overriding one result per month) that are enabled on tables and columns that are imported into DQOps.|*[DefaultMonthlyMonitoringObservabilityCheckSettingsSpec](./DefaultObservabilityChecksYaml.md#defaultmonthlymonitoringobservabilitychecksettingsspec)*| | | |









___


## DefaultProfilingObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability profiling checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for profiling checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`table`](./DefaultObservabilityChecksYaml.md#defaultprofilingtableobservabilitychecksettingsspec)</span>|The default configuration of profiling checks on a table level.|*[DefaultProfilingTableObservabilityCheckSettingsSpec](./DefaultObservabilityChecksYaml.md#defaultprofilingtableobservabilitychecksettingsspec)*| | | |
|<span class="no-wrap-code ">[`column`](./DefaultObservabilityChecksYaml.md#defaultprofilingcolumnobservabilitychecksettingsspec)</span>|The default configuration of profiling checks on a column level.|*[DefaultProfilingColumnObservabilityCheckSettingsSpec](./DefaultObservabilityChecksYaml.md#defaultprofilingcolumnobservabilitychecksettingsspec)*| | | |









___


## DefaultProfilingTableObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability profiling checks that will be detecting anomalies
 for all tables that are imported. This configuration of checks is copied to the list of enabled table level checks on all tables that are imported, for profiling checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`volume`](./profiling/table-profiling-checks.md#tablevolumeprofilingchecksspec)</span>|The default configuration of volume data quality checks on a table level.|*[TableVolumeProfilingChecksSpec](./profiling/table-profiling-checks.md#tablevolumeprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`availability`](./profiling/table-profiling-checks.md#tableavailabilityprofilingchecksspec)</span>|The default configuration of the table availability data quality checks on a table level.|*[TableAvailabilityProfilingChecksSpec](./profiling/table-profiling-checks.md#tableavailabilityprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`schema`](./profiling/table-profiling-checks.md#tableschemaprofilingchecksspec)</span>|The default configuration of schema (column count and schema) data quality checks on a table level.|*[TableSchemaProfilingChecksSpec](./profiling/table-profiling-checks.md#tableschemaprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`custom`](./profiling/table-profiling-checks.md#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](./profiling/table-profiling-checks.md#customcheckspecmap)*| | | |









___


## DefaultProfilingColumnObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability profiling checks that will be detecting anomalies
 for all columns that are imported. This configuration of checks is copied to the list of enabled column level checks on all columns that are imported, for profiling checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`nulls`](./profiling/column-profiling-checks.md#columnnullsprofilingchecksspec)</span>|The default configuration of column level checks that verify nulls and blanks.|*[ColumnNullsProfilingChecksSpec](./profiling/column-profiling-checks.md#columnnullsprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`numeric`](./profiling/column-profiling-checks.md#columnnumericprofilingchecksspec)</span>|The default configuration of column level checks that verify negative values.|*[ColumnNumericProfilingChecksSpec](./profiling/column-profiling-checks.md#columnnumericprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`text`](./profiling/column-profiling-checks.md#columntextprofilingchecksspec)</span>|The default configuration of strings checks on a column level.|*[ColumnTextProfilingChecksSpec](./profiling/column-profiling-checks.md#columntextprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`uniqueness`](./profiling/column-profiling-checks.md#columnuniquenessprofilingchecksspec)</span>|The default configuration of uniqueness checks on a column level.|*[ColumnUniquenessProfilingChecksSpec](./profiling/column-profiling-checks.md#columnuniquenessprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`datetime`](./profiling/column-profiling-checks.md#columndatetimeprofilingchecksspec)</span>|The default configuration of datetime checks on a column level.|*[ColumnDatetimeProfilingChecksSpec](./profiling/column-profiling-checks.md#columndatetimeprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`pii`](./profiling/column-profiling-checks.md#columnpiiprofilingchecksspec)</span>|The default configuration of Personal Identifiable Information (PII) checks on a column level.|*[ColumnPiiProfilingChecksSpec](./profiling/column-profiling-checks.md#columnpiiprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`bool`](./profiling/column-profiling-checks.md#columnboolprofilingchecksspec)</span>|The default configuration of booleans checks on a column level.|*[ColumnBoolProfilingChecksSpec](./profiling/column-profiling-checks.md#columnboolprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`datatype`](./profiling/column-profiling-checks.md#columndatatypeprofilingchecksspec)</span>|The default configuration of datatype checks on a column level.|*[ColumnDatatypeProfilingChecksSpec](./profiling/column-profiling-checks.md#columndatatypeprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`anomaly`](./profiling/column-profiling-checks.md#columnanomalyprofilingchecksspec)</span>|The default configuration of anomaly checks on a column level.|*[ColumnAnomalyProfilingChecksSpec](./profiling/column-profiling-checks.md#columnanomalyprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`schema`](./profiling/column-profiling-checks.md#columnschemaprofilingchecksspec)</span>|The default configuration of schema checks on a column level.|*[ColumnSchemaProfilingChecksSpec](./profiling/column-profiling-checks.md#columnschemaprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`custom`](./profiling/table-profiling-checks.md#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](./profiling/table-profiling-checks.md#customcheckspecmap)*| | | |









___


## DefaultDailyMonitoringObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability daily monitoring checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for daily monitoring checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`table`](./DefaultObservabilityChecksYaml.md#defaultdailymonitoringtableobservabilitychecksettingsspec)</span>|The default configuration of daily monitoring checks on a table level.|*[DefaultDailyMonitoringTableObservabilityCheckSettingsSpec](./DefaultObservabilityChecksYaml.md#defaultdailymonitoringtableobservabilitychecksettingsspec)*| | | |
|<span class="no-wrap-code ">[`column`](./DefaultObservabilityChecksYaml.md#defaultdailymonitoringcolumnobservabilitychecksettingsspec)</span>|The default configuration of daily monitoring checks on a column level.|*[DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec](./DefaultObservabilityChecksYaml.md#defaultdailymonitoringcolumnobservabilitychecksettingsspec)*| | | |









___


## DefaultDailyMonitoringTableObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability daily monitoring checks that will be detecting anomalies
 for all tables that are imported. This configuration of checks is copied to the list of enabled table level checks on all tables that are imported, for daily monitoring checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`volume`](./monitoring/table-daily-monitoring-checks.md#tablevolumedailymonitoringchecksspec)</span>|The default configuration of volume data quality checks on a table level.|*[TableVolumeDailyMonitoringChecksSpec](./monitoring/table-daily-monitoring-checks.md#tablevolumedailymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`availability`](./monitoring/table-daily-monitoring-checks.md#tableavailabilitydailymonitoringchecksspec)</span>|The default configuration of the table availability data quality checks on a table level.|*[TableAvailabilityDailyMonitoringChecksSpec](./monitoring/table-daily-monitoring-checks.md#tableavailabilitydailymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`schema`](./monitoring/table-daily-monitoring-checks.md#tableschemadailymonitoringchecksspec)</span>|The default configuration of schema (column count and schema) data quality checks on a table level.|*[TableSchemaDailyMonitoringChecksSpec](./monitoring/table-daily-monitoring-checks.md#tableschemadailymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`custom`](./profiling/table-profiling-checks.md#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](./profiling/table-profiling-checks.md#customcheckspecmap)*| | | |









___


## DefaultDailyMonitoringColumnObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability daily monitoring checks that will be detecting anomalies
 for all columns that are imported. This configuration of checks is copied to the list of enabled column level checks on all columns that are imported, for daily monitoring checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`nulls`](./monitoring/column-daily-monitoring-checks.md#columnnullsdailymonitoringchecksspec)</span>|The default configuration of column level checks that verify nulls and blanks.|*[ColumnNullsDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#columnnullsdailymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`numeric`](./monitoring/column-daily-monitoring-checks.md#columnnumericdailymonitoringchecksspec)</span>|The default configuration of column level checks that verify negative values.|*[ColumnNumericDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#columnnumericdailymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`text`](./monitoring/column-daily-monitoring-checks.md#columntextdailymonitoringchecksspec)</span>|The default configuration of strings checks on a column level.|*[ColumnTextDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#columntextdailymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`uniqueness`](./monitoring/column-daily-monitoring-checks.md#columnuniquenessdailymonitoringchecksspec)</span>|The default configuration of uniqueness checks on a column level.|*[ColumnUniquenessDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#columnuniquenessdailymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`datetime`](./monitoring/column-daily-monitoring-checks.md#columndatetimedailymonitoringchecksspec)</span>|The default configuration of datetime checks on a column level.|*[ColumnDatetimeDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#columndatetimedailymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`pii`](./monitoring/column-daily-monitoring-checks.md#columnpiidailymonitoringchecksspec)</span>|The default configuration of Personal Identifiable Information (PII) checks on a column level.|*[ColumnPiiDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#columnpiidailymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`bool`](./monitoring/column-daily-monitoring-checks.md#columnbooldailymonitoringchecksspec)</span>|The default configuration of booleans checks on a column level.|*[ColumnBoolDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#columnbooldailymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`datatype`](./monitoring/column-daily-monitoring-checks.md#columndatatypedailymonitoringchecksspec)</span>|The default configuration of datatype checks on a column level.|*[ColumnDatatypeDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#columndatatypedailymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`anomaly`](./monitoring/column-daily-monitoring-checks.md#columnanomalydailymonitoringchecksspec)</span>|The default configuration of anomaly checks on a column level.|*[ColumnAnomalyDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#columnanomalydailymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`schema`](./monitoring/column-daily-monitoring-checks.md#columnschemadailymonitoringchecksspec)</span>|The default configuration of schema checks on a column level.|*[ColumnSchemaDailyMonitoringChecksSpec](./monitoring/column-daily-monitoring-checks.md#columnschemadailymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`custom`](./profiling/table-profiling-checks.md#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](./profiling/table-profiling-checks.md#customcheckspecmap)*| | | |









___


## DefaultMonthlyMonitoringObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability monthly monitoring checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for monthly profiling checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`table`](./DefaultObservabilityChecksYaml.md#defaultmonthlymonitoringtableobservabilitychecksettingsspec)</span>|The default configuration of monthly monitoring checks on a table level.|*[DefaultMonthlyMonitoringTableObservabilityCheckSettingsSpec](./DefaultObservabilityChecksYaml.md#defaultmonthlymonitoringtableobservabilitychecksettingsspec)*| | | |
|<span class="no-wrap-code ">[`column`](./DefaultObservabilityChecksYaml.md#defaultmonthlymonitoringcolumnobservabilitychecksettingsspec)</span>|The default configuration of monthly monitoring checks on a column level.|*[DefaultMonthlyMonitoringColumnObservabilityCheckSettingsSpec](./DefaultObservabilityChecksYaml.md#defaultmonthlymonitoringcolumnobservabilitychecksettingsspec)*| | | |









___


## DefaultMonthlyMonitoringTableObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability monthly monitoring checks that will be detecting anomalies
 for all tables that are imported. This configuration of checks is copied to the list of enabled table level checks on all tables that are imported, for monthly monitoring checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`volume`](./monitoring/table-monthly-monitoring-checks.md#tablevolumemonthlymonitoringchecksspec)</span>|The default configuration of volume data quality checks on a table level.|*[TableVolumeMonthlyMonitoringChecksSpec](./monitoring/table-monthly-monitoring-checks.md#tablevolumemonthlymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`availability`](./monitoring/table-monthly-monitoring-checks.md#tableavailabilitymonthlymonitoringchecksspec)</span>|The default configuration of the table availability data quality checks on a table level.|*[TableAvailabilityMonthlyMonitoringChecksSpec](./monitoring/table-monthly-monitoring-checks.md#tableavailabilitymonthlymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`schema`](./monitoring/table-monthly-monitoring-checks.md#tableschemamonthlymonitoringchecksspec)</span>|The default configuration of schema (column count and schema) data quality checks on a table level.|*[TableSchemaMonthlyMonitoringChecksSpec](./monitoring/table-monthly-monitoring-checks.md#tableschemamonthlymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`custom`](./profiling/table-profiling-checks.md#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](./profiling/table-profiling-checks.md#customcheckspecmap)*| | | |









___


## DefaultMonthlyMonitoringColumnObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability monthly monitoring checks that will be detecting anomalies
 for all columns that are imported. This configuration of checks is copied to the list of enabled column level checks on all columns that are imported, for monthly monitoring checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`nulls`](./monitoring/column-monthly-monitoring-checks.md#columnnullsmonthlymonitoringchecksspec)</span>|The default configuration of column level checks that verify nulls and blanks.|*[ColumnNullsMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#columnnullsmonthlymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`numeric`](./monitoring/column-monthly-monitoring-checks.md#columnnumericmonthlymonitoringchecksspec)</span>|The default configuration of column level checks that verify negative values.|*[ColumnNumericMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#columnnumericmonthlymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`text`](./monitoring/column-monthly-monitoring-checks.md#columntextmonthlymonitoringchecksspec)</span>|The default configuration of strings checks on a column level.|*[ColumnTextMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#columntextmonthlymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`uniqueness`](./monitoring/column-monthly-monitoring-checks.md#columnuniquenessmonthlymonitoringchecksspec)</span>|The default configuration of uniqueness checks on a column level.|*[ColumnUniquenessMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#columnuniquenessmonthlymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`datetime`](./monitoring/column-monthly-monitoring-checks.md#columndatetimemonthlymonitoringchecksspec)</span>|The default configuration of datetime checks on a column level.|*[ColumnDatetimeMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#columndatetimemonthlymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`pii`](./monitoring/column-monthly-monitoring-checks.md#columnpiimonthlymonitoringchecksspec)</span>|The default configuration of Personal Identifiable Information (PII) checks on a column level.|*[ColumnPiiMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#columnpiimonthlymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`bool`](./monitoring/column-monthly-monitoring-checks.md#columnboolmonthlymonitoringchecksspec)</span>|The default configuration of booleans checks on a column level.|*[ColumnBoolMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#columnboolmonthlymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`datatype`](./monitoring/column-monthly-monitoring-checks.md#columndatatypemonthlymonitoringchecksspec)</span>|The default configuration of datatype checks on a column level.|*[ColumnDatatypeMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#columndatatypemonthlymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`anomaly`](./monitoring/column-monthly-monitoring-checks.md#columnanomalymonthlymonitoringchecksspec)</span>|The default configuration of anomaly checks on a column level.|*[ColumnAnomalyMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#columnanomalymonthlymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`schema`](./monitoring/column-monthly-monitoring-checks.md#columnschemamonthlymonitoringchecksspec)</span>|The default configuration of schema checks on a column level.|*[ColumnSchemaMonthlyMonitoringChecksSpec](./monitoring/column-monthly-monitoring-checks.md#columnschemamonthlymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`custom`](./profiling/table-profiling-checks.md#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](./profiling/table-profiling-checks.md#customcheckspecmap)*| | | |









___


