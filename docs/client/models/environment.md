# DQOps REST API environment models reference
The references of all objects used by [environment](../operations/environment.md) REST API operations are listed below.


## DqoSettingsModel
REST API model that returns a key/value list of all DQOps configuration properties.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`properties`</span>|Dictionary of all effective DQOps system properties, retrieved from the default configuration files, user configuration files, environment variables and 'dqo' command arguments.|*Dict[string, `Object`]*|


___

## DqoUserRole
DQOps user role within a data domain or a whole account level.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|admin<br/>editor<br/>operator<br/>viewer<br/>none<br/>|

___

## DqoUserProfileModel
The model that describes the current user and his access rights.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`user`</span>|User email.|*string*|
|<span class="no-wrap-code">`tenant`</span>|DQOps Cloud tenant.|*string*|
|<span class="no-wrap-code">`license_type`</span>|DQOps Cloud license type.|*string*|
|<span class="no-wrap-code">`trial_period_expires_at`</span>|The date and time when the trial period of a PERSONAL DQOps license expires and the account is downgraded to a FREE license.|*string*|
|<span class="no-wrap-code">`connections_limit`</span>|Limit of the number of connections that could be synchronized to the DQOps Cloud data quality warehouse.|*integer*|
|<span class="no-wrap-code">`users_limit`</span>|Limit of the number of users that could be added to a DQOps environment.|*integer*|
|<span class="no-wrap-code">`months_limit`</span>|Limit of the number of recent months (excluding the current month) that could be synchronized to the DQOps Cloud data quality warehouse.|*integer*|
|<span class="no-wrap-code">`connection_tables_limit`</span>|Limit of the number of tables inside each connection that could be synchronized to the DQOps Cloud data quality warehouse.|*integer*|
|<span class="no-wrap-code">`tables_limit`</span>|Limit of the total number of tables that could be synchronized to the DQOps Cloud data quality warehouse.|*integer*|
|<span class="no-wrap-code">`jobs_limit`</span>|Limit of the number of supported concurrent jobs that DQOps can run in parallel on this instance.|*integer*|
|<span class="no-wrap-code">[`account_role`](#dqouserrole)</span>|User role that limits possible operations that the current user can perform.|*[DqoUserRole](#dqouserrole)*|
|<span class="no-wrap-code">`data_quality_data_warehouse_enabled`</span>|True when the account has access to the DQOps Cloud's data quality data lake and data warehouse, allowing to synchronize files and use the data quality data warehouse.|*boolean*|
|<span class="no-wrap-code">`can_manage_account`</span>|User is the administrator of the account and can perform security related actions, such as managing users.|*boolean*|
|<span class="no-wrap-code">`can_view_any_object`</span>|User can view any object and view all results.|*boolean*|
|<span class="no-wrap-code">`can_manage_scheduler`</span>|User can start and stop the job scheduler.|*boolean*|
|<span class="no-wrap-code">`can_cancel_jobs`</span>|User can cancel running jobs.|*boolean*|
|<span class="no-wrap-code">`can_run_checks`</span>|User can run data quality checks.|*boolean*|
|<span class="no-wrap-code">`can_delete_data`</span>|User can delete data quality results.|*boolean*|
|<span class="no-wrap-code">`can_collect_statistics`</span>|User can collect statistics.|*boolean*|
|<span class="no-wrap-code">`can_manage_data_sources`</span>|User can manage data sources: create connections, import tables, change the configuration of connections, tables, columns. Change any settings in the Data Sources section.|*boolean*|
|<span class="no-wrap-code">`can_synchronize`</span>|User can trigger the synchronization with DQOps Cloud.|*boolean*|
|<span class="no-wrap-code">`can_edit_comments`</span>|User can edit comments on connections, tables, columns.|*boolean*|
|<span class="no-wrap-code">`can_edit_labels`</span>|User can edit labels on connections, tables, columns.|*boolean*|
|<span class="no-wrap-code">`can_manage_definitions`</span>|User can manage definitions of sensors, rules, checks and the default data quality check configuration that is applied on imported tables.|*boolean*|
|<span class="no-wrap-code">`can_compare_tables`</span>|User can define table comparison configurations and compare tables.|*boolean*|
|<span class="no-wrap-code">`can_manage_users`</span>|User can manage other users, add users to a multi-user account, change access rights, reset passwords.|*boolean*|
|<span class="no-wrap-code">`can_manage_and_view_shared_credentials`</span>|User can manage shared credentials and view (or download) already defined shared credentials.|*boolean*|
|<span class="no-wrap-code">`can_change_own_password`</span>|User can change his own password in DQOps Cloud, because the DQOps Cloud Pairing API Key is valid and synchronization is enabled.|*boolean*|


___

