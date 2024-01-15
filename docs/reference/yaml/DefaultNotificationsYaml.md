# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## DefaultNotificationsYaml
The default configuration of notifications. Notifications are published by calling webhooks defined in this object.
 The default notification settings are stored in the *$DQO_USER_HOME/settings/defaultnotifications.dqonotifications.yaml* file in the DQOps user&#x27;s home folder.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|kind||enum|source<br/>table<br/>sensor<br/>provider_sensor<br/>rule<br/>check<br/>settings<br/>file_index<br/>dashboards<br/>default_schedules<br/>default_checks<br/>default_notifications<br/>| | |
|[spec](./ConnectionYaml.md#IncidentWebhookNotificationsSpec)||[IncidentWebhookNotificationsSpec](./ConnectionYaml.md#IncidentWebhookNotificationsSpec)| | | |









___


