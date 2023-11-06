
## DefaultNotificationsYaml  
The default configuration of notifications. Notifications are published by calling webhooks defined in this object.
 The default notification settings are stored in the *$DQO_USER_HOME/settings/defaultnotifications.dqonotifications.yaml* file in the DQOps user&#x27;s home folder.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|[kind](#specificationkind)||[SpecificationKind](#specificationkind)|table<br/>default_schedules<br/>dashboards<br/>source<br/>sensor<br/>check<br/>default_checks<br/>rule<br/>file_index<br/>settings<br/>default_notifications<br/>provider_sensor<br/>| | |
|[spec](\docs\reference\yaml\connectionyaml\#incidentwebhooknotificationsspec)||[IncidentWebhookNotificationsSpec](\docs\reference\yaml\connectionyaml\#incidentwebhooknotificationsspec)| | | |









___  

