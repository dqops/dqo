Default settings management for configuring the default data quality checks that are configured for all imported tables and columns.  


___  
## get_default_data_observability_daily_monitoring_column_checks  
Returns UI model to show and edit the default configuration of the daily monitoring (Data Observability and monitoring) checks that are configured for all imported columns on a column level.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/get_default_data_observability_daily_monitoring_column_checks.py)
  

**GET**
```
http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/daily/column  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_model](\docs\client\models\#checkcontainermodel)||[CheckContainerModel](\docs\client\models\#checkcontainermodel)|








**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/daily/column^
		-H "Accept: application/json"

    ```


___  
## get_default_data_observability_daily_monitoring_table_checks  
Returns UI model to show and edit the default configuration of the daily monitoring (Data Observability and monitoring) checks that are configured for all imported tables on a table level.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/get_default_data_observability_daily_monitoring_table_checks.py)
  

**GET**
```
http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/daily/table  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_model](\docs\client\models\#checkcontainermodel)||[CheckContainerModel](\docs\client\models\#checkcontainermodel)|








**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/daily/table^
		-H "Accept: application/json"

    ```


___  
## get_default_data_observability_monthly_monitoring_column_checks  
Returns UI model to show and edit the default configuration of the monthly monitoring (Data Observability end of month scores) checks that are configured for all imported columns on a column level.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/get_default_data_observability_monthly_monitoring_column_checks.py)
  

**GET**
```
http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/monthly/column  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_model](\docs\client\models\#checkcontainermodel)||[CheckContainerModel](\docs\client\models\#checkcontainermodel)|








**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/monthly/column^
		-H "Accept: application/json"

    ```


___  
## get_default_data_observability_monthly_monitoring_table_checks  
Returns UI model to show and edit the default configuration of the monthly monitoring (Data Observability end of month scores) checks that are configured for all imported tables on a table level.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/get_default_data_observability_monthly_monitoring_table_checks.py)
  

**GET**
```
http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/monthly/table  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_model](\docs\client\models\#checkcontainermodel)||[CheckContainerModel](\docs\client\models\#checkcontainermodel)|








**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/monthly/table^
		-H "Accept: application/json"

    ```


___  
## get_default_profiling_column_checks  
Returns UI model to show and edit the default configuration of the profiling checks that are configured for all imported column on a column level.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/get_default_profiling_column_checks.py)
  

**GET**
```
http://localhost:8888/api/defaults/defaultchecks/profiling/column  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_model](\docs\client\models\#checkcontainermodel)||[CheckContainerModel](\docs\client\models\#checkcontainermodel)|








**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/defaults/defaultchecks/profiling/column^
		-H "Accept: application/json"

    ```


___  
## get_default_profiling_table_checks  
Returns UI model to show and edit the default configuration of the profiling checks that are configured for all imported tables on a table level.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/get_default_profiling_table_checks.py)
  

**GET**
```
http://localhost:8888/api/defaults/defaultchecks/profiling/table  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_model](\docs\client\models\#checkcontainermodel)||[CheckContainerModel](\docs\client\models\#checkcontainermodel)|








**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/defaults/defaultchecks/profiling/table^
		-H "Accept: application/json"

    ```


___  
## get_default_schedule  
Returns spec to show and edit the default configuration of schedules.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/get_default_schedule.py)
  

**GET**
```
http://localhost:8888/api/defaults/defaultschedule/{schedulingGroup}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[monitoring_schedule_spec](\docs\client\models\#monitoringschedulespec)||[MonitoringScheduleSpec](\docs\client\models\#monitoringschedulespec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|[scheduling_group](\docs\client\models\#checkrunschedulegroup)|Check scheduling group (named schedule)|[CheckRunScheduleGroup](\docs\client\models\#checkrunschedulegroup)|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/defaults/defaultschedule/"partitioned_daily"^
		-H "Accept: application/json"

    ```


___  
## get_default_webhooks  
Returns spec to show and edit the default configuration of webhooks.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/get_default_webhooks.py)
  

**GET**
```
http://localhost:8888/api/defaults/defaultwebhooks  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[incident_webhook_notifications_spec](\docs\reference\yaml\connectionyaml\#incidentwebhooknotificationsspec)||[IncidentWebhookNotificationsSpec](\docs\reference\yaml\connectionyaml\#incidentwebhooknotificationsspec)|








**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/defaults/defaultwebhooks^
		-H "Accept: application/json"

    ```


___  
## update_default_data_observability_daily_monitoring_column_checks  
New configuration of the default daily monitoring (data observability) checks on a column level. These checks will be applied on new columns.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/update_default_data_observability_daily_monitoring_column_checks.py)
  

**PUT**
```
http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/daily/column  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the default configuration of the data observability daily monitoring checks configuration|[CheckContainerModel](\docs\client\models\#checkcontainermodel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/daily/column^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"

    ```


___  
## update_default_data_observability_daily_monitoring_table_checks  
New configuration of the default daily monitoring (data observability) checks on a table level. These checks will be applied on new tables.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/update_default_data_observability_daily_monitoring_table_checks.py)
  

**PUT**
```
http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/daily/table  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the default configuration of the data observability daily monitoring checks configuration|[CheckContainerModel](\docs\client\models\#checkcontainermodel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/daily/table^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"

    ```


___  
## update_default_data_observability_monthly_monitoring_column_checks  
New configuration of the default monthly monitoring checkpoints on a column level. These checks will be applied on new columns.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/update_default_data_observability_monthly_monitoring_column_checks.py)
  

**PUT**
```
http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/monthly/column  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the default configuration of the data observability monthly monitoring checks configuration|[CheckContainerModel](\docs\client\models\#checkcontainermodel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/monthly/column^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"

    ```


___  
## update_default_data_observability_monthly_monitoring_table_checks  
New configuration of the default monthly monitoring checkpoints on a table level. These checks will be applied on new tables.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/update_default_data_observability_monthly_monitoring_table_checks.py)
  

**PUT**
```
http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/monthly/table  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the default configuration of the data observability monthly monitoring checks configuration|[CheckContainerModel](\docs\client\models\#checkcontainermodel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/monthly/table^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"

    ```


___  
## update_default_profiling_column_checks  
New configuration of the default profiling checks on a column level. These checks will be applied to new columns.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/update_default_profiling_column_checks.py)
  

**PUT**
```
http://localhost:8888/api/defaults/defaultchecks/profiling/column  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality profiling checks configuration|[CheckContainerModel](\docs\client\models\#checkcontainermodel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/defaults/defaultchecks/profiling/column^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"

    ```


___  
## update_default_profiling_table_checks  
New configuration of the default profiling checks on a table level. These checks will be applied to new tables.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/update_default_profiling_table_checks.py)
  

**PUT**
```
http://localhost:8888/api/defaults/defaultchecks/profiling/table  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality profiling checks configuration|[CheckContainerModel](\docs\client\models\#checkcontainermodel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/defaults/defaultchecks/profiling/table^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"

    ```


___  
## update_default_schedules  
New configuration of the default schedules.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/update_default_schedules.py)
  

**PUT**
```
http://localhost:8888/api/defaults/defaultschedule/{schedulingGroup}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|[scheduling_group](\docs\client\models\#checkrunschedulegroup)|Check scheduling group (named schedule)|[CheckRunScheduleGroup](\docs\client\models\#checkrunschedulegroup)|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Spec with default schedules changes to be applied to the default configuration.|[MonitoringScheduleSpec](\docs\client\models\#monitoringschedulespec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/defaults/defaultschedule/"partitioned_daily"^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"cron_expression\":\"0 12 1 * *\"}"

    ```


___  
## update_default_webhooks  
New configuration of the default webhooks.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/update_default_webhooks.py)
  

**PUT**
```
http://localhost:8888/api/defaults/defaultwebhooks  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Spec with default notification webhooks changes to be applied to the default configuration|[IncidentWebhookNotificationsSpec](\docs\reference\yaml\connectionyaml\#incidentwebhooknotificationsspec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/defaults/defaultwebhooks^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"incident_opened_webhook_url\":\"https://sample_url.com/opened\",\"incident_acknowledged_webhook_url\":\"https://sample_url.com/acknowledged\",\"incident_resolved_webhook_url\":\"https://sample_url.com/resolved\",\"incident_muted_webhook_url\":\"https://sample_url.com/muted\"}"

    ```


