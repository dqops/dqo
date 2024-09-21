---
title: DQOps YAML file definitions
---
# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## LocalSettingsYaml
DQOps local settings that are stored in the *$DQO_USER_HOME/.localsettings.dqosettings.yaml* file in the user&#x27;s DQOps home folder.
 The local settings contain the current DQOps Cloud API Key and other settings. The local settings take precedence over parameters
 passed when starting DQOps.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`api_version`</span>|DQOps YAML schema version|*string*| |dqo/v1| |
|<span class="no-wrap-code ">`kind`</span>|File type|*enum*|*source*<br/>*table*<br/>*sensor*<br/>*provider_sensor*<br/>*rule*<br/>*check*<br/>*settings*<br/>*file_index*<br/>*dashboards*<br/>*default_schedules*<br/>*default_checks*<br/>*default_table_checks*<br/>*default_column_checks*<br/>*default_notifications*<br/>|settings| |
|<span class="no-wrap-code ">[`spec`](./LocalSettingsYaml.md#localsettingsspec)</span>|The object that stores the configuration settings of a local DQOps instance|*[LocalSettingsSpec](./LocalSettingsYaml.md#localsettingsspec)*| | | |



___

## LocalSettingsSpec
Local settings specification.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`editor_name`</span>|Editor name spec (VSC, Eclipse, Intellij)|*string*| | | |
|<span class="no-wrap-code ">`editor_path`</span>|Editor path on user&#x27;s computer|*string*| | | |
|<span class="no-wrap-code ">`api_key`</span>|DQOps CLoud API key|*string*| | | |
|<span class="no-wrap-code ">`disable_cloud_sync`</span>|Disable synchronization with DQOps Cloud|*boolean*| | | |
|<span class="no-wrap-code ">`instance_signature_key`</span>|DQOps instance signature key used to sign keys. This should be a Base64 encoded binary key at a 32 bytes length.|*string*| | | |
|<span class="no-wrap-code ">`time_zone`</span>|Default IANA time zone name of the server. This time zone is used to convert the time of UTC timestamps values returned from databases to a uniform local date and time. The default value is the local time zone of the DQOps server instance.|*string*| | | |
|<span class="no-wrap-code ">[`smtp_server_configuration`](./LocalSettingsYaml.md#smtpserverconfigurationspec)</span>|SMTP server configuration for incident notifications.|*[SmtpServerConfigurationSpec](./LocalSettingsYaml.md#smtpserverconfigurationspec)*| | | |
|<span class="no-wrap-code ">[`data_domains`](./LocalSettingsYaml.md#localdatadomainspecmap)</span>|The dictionary containing the configuration of local data domains.|*[LocalDataDomainSpecMap](./LocalSettingsYaml.md#localdatadomainspecmap)*| | | |



___

## SmtpServerConfigurationSpec
SMTP server configuration specification.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`host`</span>|SMTP server host|*string*| | | |
|<span class="no-wrap-code ">`port`</span>|SMTP server port|*string*| | | |
|<span class="no-wrap-code ">`use_ssl`</span>|SMTP server use SSL option|*boolean*| | | |
|<span class="no-wrap-code ">`username`</span>|SMTP server username|*string*| | | |
|<span class="no-wrap-code ">`password`</span>|SMTP server password|*string*| | | |



___

## LocalDataDomainSpecMap
Dictionary of local data domains.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`self`</span>||*Dict[string, [LocalDataDomainSpec](./LocalSettingsYaml.md#localdatadomainspec)]*| | | |



___

## LocalDataDomainSpec
Specification of the local data domain.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`display_name`</span>|Data domain display name, which is a user-friendly name to be shown in the UI|*string*| | | |
|<span class="no-wrap-code ">`enable_scheduler`</span>|Enables the job scheduler for this domain.|*boolean*| | | |



___

