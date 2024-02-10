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
|<span class="no-wrap-code ">[`table`](./profiling/table-profiling-checks.md#tableprofilingcheckcategoriesspec)</span>|The default configuration of profiling checks on a table level.|*[TableProfilingCheckCategoriesSpec](./profiling/table-profiling-checks.md#tableprofilingcheckcategoriesspec)*| | | |
|<span class="no-wrap-code ">[`column`](./profiling/column-profiling-checks.md#columnprofilingcheckcategoriesspec)</span>|The default configuration of profiling checks on a column level.|*[ColumnProfilingCheckCategoriesSpec](./profiling/column-profiling-checks.md#columnprofilingcheckcategoriesspec)*| | | |









___


## DefaultDailyMonitoringObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability daily monitoring checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for daily monitoring checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`table`](./monitoring/table-daily-monitoring-checks.md#tabledailymonitoringcheckcategoriesspec)</span>|The default configuration of daily monitoring checks on a table level.|*[TableDailyMonitoringCheckCategoriesSpec](./monitoring/table-daily-monitoring-checks.md#tabledailymonitoringcheckcategoriesspec)*| | | |
|<span class="no-wrap-code ">[`column`](./monitoring/column-daily-monitoring-checks.md#columndailymonitoringcheckcategoriesspec)</span>|The default configuration of daily monitoring checks on a column level.|*[ColumnDailyMonitoringCheckCategoriesSpec](./monitoring/column-daily-monitoring-checks.md#columndailymonitoringcheckcategoriesspec)*| | | |









___


## DefaultMonthlyMonitoringObservabilityCheckSettingsSpec
The default configuration of checks that are enabled as data observability monthly monitoring checks that will be detecting anomalies
 for all columns and tables that are imported. This configuration of checks is copied to the list of enabled checks on all tables and columns that are imported, for monthly profiling checks only.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`table`](./monitoring/table-monthly-monitoring-checks.md#tablemonthlymonitoringcheckcategoriesspec)</span>|The default configuration of monthly monitoring checks on a table level.|*[TableMonthlyMonitoringCheckCategoriesSpec](./monitoring/table-monthly-monitoring-checks.md#tablemonthlymonitoringcheckcategoriesspec)*| | | |
|<span class="no-wrap-code ">[`column`](./monitoring/column-monthly-monitoring-checks.md#columnmonthlymonitoringcheckcategoriesspec)</span>|The default configuration of monthly monitoring checks on a column level.|*[ColumnMonthlyMonitoringCheckCategoriesSpec](./monitoring/column-monthly-monitoring-checks.md#columnmonthlymonitoringcheckcategoriesspec)*| | | |









___


