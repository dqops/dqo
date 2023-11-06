
## Object  
  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|


___  

## DqoSettingsModel  
REST API model that returns a key/value list of all DQOps configuration properties.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|properties|Dictionary of all effective DQOps system properties, retrieved from the default configuration files, user configuration files, environment variables and &#x27;dqo&#x27; command arguments.|Dict[string, [Object](#object)]|


___  

## DqoUserRole  
DQOps user role within a data domain or a whole account level.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|editor<br/>viewer<br/>admin<br/>none<br/>operator<br/>|

___  

## DqoUserProfileModel  
The model that describes the current user and his access rights.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|user|User email.|string|
|tenant|DQOps Cloud tenant.|string|
|license_type|DQOps Cloud license type.|string|
|trial_period_expires_at|The date and time when the trial period of a PERSONAL DQOps license expires and the account is downgraded to a FREE license.|string|
|connections_limit|Limit of the number of connections that could be synchronized to the DQOps Cloud data quality warehouse.|integer|
|users_limit|Limit of the number of users that could be added to a DQOps environment.|integer|
|months_limit|Limit of the number of recent months (excluding the current month) that could be synchronized to the DQOps Cloud data quality warehouse.|integer|
|connection_tables_limit|Limit of the number of tables inside each connection that could be synchronized to the DQOps Cloud data quality warehouse.|integer|
|tables_limit|Limit of the total number of tables that could be synchronized to the DQOps Cloud data quality warehouse.|integer|
|jobs_limit|Limit of the number of supported concurrent jobs that DQOps can run in parallel on this instance.|integer|
|[account_role](#dqouserrole)|User role that limits possible operations that the current user can perform.|[DqoUserRole](#dqouserrole)|
|can_manage_account|User is the administrator of the account and can perform security related actions, such as managing users.|boolean|
|can_view_any_object|User can view any object and view all results.|boolean|
|can_manage_scheduler|User can start and stop the job scheduler.|boolean|
|can_cancel_jobs|User can cancel running jobs.|boolean|
|can_run_checks|User can run data quality checks.|boolean|
|can_delete_data|User can delete data quality results.|boolean|
|can_collect_statistics|User can collect statistics.|boolean|
|can_manage_data_sources|User can manage data sources: create connections, import tables, change the configuration of connections, tables, columns. Change any settings in the Data Sources section.|boolean|
|can_synchronize|User can trigger the synchronization with DQOps Cloud.|boolean|
|can_edit_comments|User can edit comments on connections, tables, columns.|boolean|
|can_edit_labels|User can edit labels on connections, tables, columns.|boolean|
|can_manage_definitions|User can manage definitions of sensors, rules, checks and the default data quality check configuration that is applied on imported tables.|boolean|
|can_compare_tables|User can define table comparison configurations and compare tables.|boolean|
|can_manage_users|User can manage other users, add users to a multi-user account, change access rights, reset passwords.|boolean|
|can_manage_and_view_shared_credentials|User can manage shared credentials and view (or download) already defined shared credentials.|boolean|


___  

