# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## DefaultSchedulesYaml
The configuration of default schedules for running data quality checks.
 The default schedules are stored in the *$DQO_USER_HOME/settings/defaultschedules.dqoschedules.yaml* file in the DQOps user&#x27;s home folder.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`api_version`</span>|DQOps YAML schema version|*string*| |dqo/v1| |
|<span class="no-wrap-code ">`kind`</span>|File type|*enum*|*source*<br/>*table*<br/>*sensor*<br/>*provider_sensor*<br/>*rule*<br/>*check*<br/>*settings*<br/>*file_index*<br/>*dashboards*<br/>*default_schedules*<br/>*default_checks*<br/>*default_table_checks*<br/>*default_column_checks*<br/>*default_notifications*<br/>|default_schedules| |
|<span class="no-wrap-code ">[`spec`](./ConnectionYaml.md#defaultschedulesspec)</span>|The configuration object with the definition of the default schedules|*[DefaultSchedulesSpec](./ConnectionYaml.md#defaultschedulesspec)*| | | |









___


