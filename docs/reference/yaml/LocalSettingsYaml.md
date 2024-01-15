# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## LocalSettingsYaml
DQOps local settings that are stored in the *$DQO_USER_HOME/.localsettings.dqosettings.yaml* file in the user&#x27;s DQOps home folder.
 The local settings contain the current DQOps Cloud API Key and other settings. The local settings take precedence over parameters
 passed when starting DQOps.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|kind||enum|source<br/>table<br/>sensor<br/>provider_sensor<br/>rule<br/>check<br/>settings<br/>file_index<br/>dashboards<br/>default_schedules<br/>default_checks<br/>default_notifications<br/>| | |
|[spec](./LocalSettingsYaml.md#LocalSettingsSpec)||[LocalSettingsSpec](./LocalSettingsYaml.md#LocalSettingsSpec)| | | |









___


## LocalSettingsSpec
Local settings specification.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|editor_name|Editor name spec (VSC, Eclipse, Intellij)|string| | | |
|editor_path|Editor path on user&#x27;s computer|string| | | |
|api_key|Api key|string| | | |
|instance_signature_key|DQOps instance signature key used to sign keys. This should be a Base64 encoded binary key at a 32 bytes length.|string| | | |
|time_zone|Default IANA time zone name of the server. This time zone is used to convert the time of UTC timestamps values returned from databases to a uniform local date and time. The default value is the local time zone of the DQOps server instance.|string| | | |









___


