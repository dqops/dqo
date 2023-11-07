
## DefaultNotificationsYaml  
The default configuration of notifications. Notifications are published by calling webhooks defined in this object.
 The default notification settings are stored in the *$DQO_USER_HOME/settings/defaultnotifications.dqonotifications.yaml* file in the DQOps user&#x27;s home folder.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|[kind](#specificationkind)||[SpecificationKind](#specificationkind)|default_schedules<br/>settings<br/>default_notifications<br/>rule<br/>sensor<br/>source<br/>check<br/>dashboards<br/>default_checks<br/>table<br/>provider_sensor<br/>file_index<br/>| | |
|[spec](\docs\reference\yaml\connectionyaml\#incidentwebhooknotificationsspec)||[IncidentWebhookNotificationsSpec](\docs\reference\yaml\connectionyaml\#incidentwebhooknotificationsspec)| | | |









___  

