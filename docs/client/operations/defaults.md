---
title: DQOps REST API defaults operations
---
# DQOps REST API defaults operations
Default settings management for configuring the default data quality checks that are configured for all imported tables and columns.


___
## get_default_schedule
Returns spec to show and edit the default configuration of schedules.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/get_default_schedule.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/defaults/defaultschedule/{schedulingGroup}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`monitoring_schedule_spec`](../models/common.md#monitoringschedulespec)</span>||*[MonitoringScheduleSpec](../models/common.md#monitoringschedulespec)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">[`scheduling_group`](../models/common.md#checkrunschedulegroup)</span>|Check scheduling group (named schedule)|*[CheckRunScheduleGroup](../models/common.md#checkrunschedulegroup)*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/defaults/defaultschedule/partitioned_daily^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "cron_expression" : "0 12 1 * *"
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_schedule
	from dqops.client.models import CheckRunScheduleGroup
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_schedule.sync(
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        MonitoringScheduleSpec(
			cron_expression='0 12 1 * *',
			disabled=False
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_schedule
	from dqops.client.models import CheckRunScheduleGroup
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_schedule.asyncio(
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        MonitoringScheduleSpec(
			cron_expression='0 12 1 * *',
			disabled=False
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_schedule
	from dqops.client.models import CheckRunScheduleGroup
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_schedule.sync(
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        MonitoringScheduleSpec(
			cron_expression='0 12 1 * *',
			disabled=False
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_schedule
	from dqops.client.models import CheckRunScheduleGroup
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_schedule.asyncio(
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        MonitoringScheduleSpec(
			cron_expression='0 12 1 * *',
			disabled=False
		)
        ```
    
    
    



___
## get_default_webhooks
Returns spec to show and edit the default configuration of webhooks.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/get_default_webhooks.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/defaults/defaultwebhooks
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`incident_webhook_notifications_spec`](../../reference/yaml/ConnectionYaml.md#incidentwebhooknotificationsspec)</span>||*[IncidentWebhookNotificationsSpec](../../reference/yaml/ConnectionYaml.md#incidentwebhooknotificationsspec)*|








**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/defaults/defaultwebhooks^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "incident_opened_webhook_url" : "https://sample_url.com/opened",
		  "incident_acknowledged_webhook_url" : "https://sample_url.com/acknowledged",
		  "incident_resolved_webhook_url" : "https://sample_url.com/resolved",
		  "incident_muted_webhook_url" : "https://sample_url.com/muted"
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_webhooks
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_webhooks.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        IncidentWebhookNotificationsSpec(
			incident_opened_webhook_url='https://sample_url.com/opened',
			incident_acknowledged_webhook_url='https://sample_url.com/acknowledged',
			incident_resolved_webhook_url='https://sample_url.com/resolved',
			incident_muted_webhook_url='https://sample_url.com/muted'
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_webhooks
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_webhooks.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        IncidentWebhookNotificationsSpec(
			incident_opened_webhook_url='https://sample_url.com/opened',
			incident_acknowledged_webhook_url='https://sample_url.com/acknowledged',
			incident_resolved_webhook_url='https://sample_url.com/resolved',
			incident_muted_webhook_url='https://sample_url.com/muted'
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_webhooks
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_webhooks.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        IncidentWebhookNotificationsSpec(
			incident_opened_webhook_url='https://sample_url.com/opened',
			incident_acknowledged_webhook_url='https://sample_url.com/acknowledged',
			incident_resolved_webhook_url='https://sample_url.com/resolved',
			incident_muted_webhook_url='https://sample_url.com/muted'
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_webhooks
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_webhooks.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        IncidentWebhookNotificationsSpec(
			incident_opened_webhook_url='https://sample_url.com/opened',
			incident_acknowledged_webhook_url='https://sample_url.com/acknowledged',
			incident_resolved_webhook_url='https://sample_url.com/resolved',
			incident_muted_webhook_url='https://sample_url.com/muted'
		)
        ```
    
    
    



___
## update_default_schedules
New configuration of the default schedules.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/update_default_schedules.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/defaults/defaultschedule/{schedulingGroup}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">[`scheduling_group`](../models/common.md#checkrunschedulegroup)</span>|Check scheduling group (named schedule)|*[CheckRunScheduleGroup](../models/common.md#checkrunschedulegroup)*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Spec with default schedules changes to be applied to the default configuration.|*[MonitoringScheduleSpec](../models/common.md#monitoringschedulespec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/defaults/defaultschedule/partitioned_daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"cron_expression\":\"0 12 1 * *\"}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_schedules
	from dqops.client.models import CheckRunScheduleGroup, \
	                                MonitoringScheduleSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = MonitoringScheduleSpec(
		cron_expression='0 12 1 * *',
		disabled=False
	)
	
	call_result = update_default_schedules.sync(
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_schedules
	from dqops.client.models import CheckRunScheduleGroup, \
	                                MonitoringScheduleSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = MonitoringScheduleSpec(
		cron_expression='0 12 1 * *',
		disabled=False
	)
	
	call_result = await update_default_schedules.asyncio(
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_schedules
	from dqops.client.models import CheckRunScheduleGroup, \
	                                MonitoringScheduleSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = MonitoringScheduleSpec(
		cron_expression='0 12 1 * *',
		disabled=False
	)
	
	call_result = update_default_schedules.sync(
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_schedules
	from dqops.client.models import CheckRunScheduleGroup, \
	                                MonitoringScheduleSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = MonitoringScheduleSpec(
		cron_expression='0 12 1 * *',
		disabled=False
	)
	
	call_result = await update_default_schedules.asyncio(
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_default_webhooks
New configuration of the default webhooks.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/update_default_webhooks.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/defaults/defaultwebhooks
```





**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Spec with default notification webhooks changes to be applied to the default configuration|*[IncidentWebhookNotificationsSpec](../../reference/yaml/ConnectionYaml.md#incidentwebhooknotificationsspec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/defaults/defaultwebhooks^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"incident_opened_webhook_url\":\"https://sample_url.com/opened\",\"incident_acknowledged_webhook_url\":\"https://sample_url.com/acknowledged\",\"incident_resolved_webhook_url\":\"https://sample_url.com/resolved\",\"incident_muted_webhook_url\":\"https://sample_url.com/muted\"}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_webhooks
	from dqops.client.models import IncidentWebhookNotificationsSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = IncidentWebhookNotificationsSpec(
		incident_opened_webhook_url='https://sample_url.com/opened',
		incident_acknowledged_webhook_url='https://sample_url.com/acknowledged',
		incident_resolved_webhook_url='https://sample_url.com/resolved',
		incident_muted_webhook_url='https://sample_url.com/muted'
	)
	
	call_result = update_default_webhooks.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_webhooks
	from dqops.client.models import IncidentWebhookNotificationsSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = IncidentWebhookNotificationsSpec(
		incident_opened_webhook_url='https://sample_url.com/opened',
		incident_acknowledged_webhook_url='https://sample_url.com/acknowledged',
		incident_resolved_webhook_url='https://sample_url.com/resolved',
		incident_muted_webhook_url='https://sample_url.com/muted'
	)
	
	call_result = await update_default_webhooks.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_webhooks
	from dqops.client.models import IncidentWebhookNotificationsSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = IncidentWebhookNotificationsSpec(
		incident_opened_webhook_url='https://sample_url.com/opened',
		incident_acknowledged_webhook_url='https://sample_url.com/acknowledged',
		incident_resolved_webhook_url='https://sample_url.com/resolved',
		incident_muted_webhook_url='https://sample_url.com/muted'
	)
	
	call_result = update_default_webhooks.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_webhooks
	from dqops.client.models import IncidentWebhookNotificationsSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = IncidentWebhookNotificationsSpec(
		incident_opened_webhook_url='https://sample_url.com/opened',
		incident_acknowledged_webhook_url='https://sample_url.com/acknowledged',
		incident_resolved_webhook_url='https://sample_url.com/resolved',
		incident_muted_webhook_url='https://sample_url.com/muted'
	)
	
	call_result = await update_default_webhooks.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



