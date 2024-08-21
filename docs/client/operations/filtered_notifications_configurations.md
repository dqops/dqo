---
title: DQOps REST API filtered_notifications_configurations operations
---
# DQOps REST API filtered_notifications_configurations operations
Operations for managing the configuration of filtered notifications on a connection level in DQOps.


___
## create_connection_filtered_notification_configuration
Creates a new filtered notification configuration on a connection level

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/filtered_notifications_configurations/create_connection_filtered_notification_configuration.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/connections/{connectionName}/filterednotifications
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Filtered notification model|*[FilteredNotificationModel](../models/filtered_notifications_configurations.md#filterednotificationmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/filterednotifications^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import create_connection_filtered_notification_configuration
	from dqops.client.models import FilteredNotificationModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = FilteredNotificationModel()
	
	call_result = create_connection_filtered_notification_configuration.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import create_connection_filtered_notification_configuration
	from dqops.client.models import FilteredNotificationModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = FilteredNotificationModel()
	
	call_result = await create_connection_filtered_notification_configuration.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import create_connection_filtered_notification_configuration
	from dqops.client.models import FilteredNotificationModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = FilteredNotificationModel()
	
	call_result = create_connection_filtered_notification_configuration.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import create_connection_filtered_notification_configuration
	from dqops.client.models import FilteredNotificationModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = FilteredNotificationModel()
	
	call_result = await create_connection_filtered_notification_configuration.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## create_default_filtered_notification_configuration
Creates a new filtered notification configuration at default notifications

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/filtered_notifications_configurations/create_default_filtered_notification_configuration.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/default/filterednotifications
```





**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Filtered notification model|*[FilteredNotificationModel](../models/filtered_notifications_configurations.md#filterednotificationmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/default/filterednotifications^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import create_default_filtered_notification_configuration
	from dqops.client.models import FilteredNotificationModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = FilteredNotificationModel()
	
	call_result = create_default_filtered_notification_configuration.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import create_default_filtered_notification_configuration
	from dqops.client.models import FilteredNotificationModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = FilteredNotificationModel()
	
	call_result = await create_default_filtered_notification_configuration.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import create_default_filtered_notification_configuration
	from dqops.client.models import FilteredNotificationModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = FilteredNotificationModel()
	
	call_result = create_default_filtered_notification_configuration.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import create_default_filtered_notification_configuration
	from dqops.client.models import FilteredNotificationModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = FilteredNotificationModel()
	
	call_result = await create_default_filtered_notification_configuration.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## delete_connection_filtered_notification_configuration
Deletes a filtered notification configuration from a connection

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/filtered_notifications_configurations/delete_connection_filtered_notification_configuration.py) to see the source code on GitHub.


**DELETE**
```
http://localhost:8888/api/connections/{connectionName}/filterednotifications/{filteredNotificationName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`filtered_notification_name`</span>|Filtered notification name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X DELETE http://localhost:8888/api/connections/sample_connection/filterednotifications/fact*^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import delete_connection_filtered_notification_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = delete_connection_filtered_notification_configuration.sync(
	    'sample_connection',
	    'fact*',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import delete_connection_filtered_notification_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await delete_connection_filtered_notification_configuration.asyncio(
	    'sample_connection',
	    'fact*',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import delete_connection_filtered_notification_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = delete_connection_filtered_notification_configuration.sync(
	    'sample_connection',
	    'fact*',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import delete_connection_filtered_notification_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await delete_connection_filtered_notification_configuration.asyncio(
	    'sample_connection',
	    'fact*',
	    client=dqops_client
	)
	
    ```

    



___
## delete_default_filtered_notification_configuration
Deletes a filtered notification configuration from default notifications

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/filtered_notifications_configurations/delete_default_filtered_notification_configuration.py) to see the source code on GitHub.


**DELETE**
```
http://localhost:8888/api/default/filterednotifications/{filteredNotificationName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`filtered_notification_name`</span>|Filtered notification name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X DELETE http://localhost:8888/api/default/filterednotifications/fact*^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import delete_default_filtered_notification_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = delete_default_filtered_notification_configuration.sync(
	    'fact*',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import delete_default_filtered_notification_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await delete_default_filtered_notification_configuration.asyncio(
	    'fact*',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import delete_default_filtered_notification_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = delete_default_filtered_notification_configuration.sync(
	    'fact*',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import delete_default_filtered_notification_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await delete_default_filtered_notification_configuration.asyncio(
	    'fact*',
	    client=dqops_client
	)
	
    ```

    



___
## get_connection_filtered_notification_configuration
Returns a model of the filtered notification configuration

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/filtered_notifications_configurations/get_connection_filtered_notification_configuration.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/filterednotifications/{filteredNotificationName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`filtered_notification_model`](../models/filtered_notifications_configurations.md#filterednotificationmodel)</span>||*[FilteredNotificationModel](../models/filtered_notifications_configurations.md#filterednotificationmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`filtered_notification_name`</span>|Filtered notification name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/filterednotifications/fact*^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        { }
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import get_connection_filtered_notification_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_connection_filtered_notification_configuration.sync(
	    'sample_connection',
	    'fact*',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        FilteredNotificationModel()
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import get_connection_filtered_notification_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_connection_filtered_notification_configuration.asyncio(
	    'sample_connection',
	    'fact*',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        FilteredNotificationModel()
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import get_connection_filtered_notification_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_connection_filtered_notification_configuration.sync(
	    'sample_connection',
	    'fact*',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        FilteredNotificationModel()
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import get_connection_filtered_notification_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_connection_filtered_notification_configuration.asyncio(
	    'sample_connection',
	    'fact*',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        FilteredNotificationModel()
        ```
    
    
    



___
## get_connection_filtered_notifications_configurations
Returns the list of filtered notification configurations on a connection

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/filtered_notifications_configurations/get_connection_filtered_notifications_configurations.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/filterednotifications
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`filtered_notification_model`</span>||*List[[FilteredNotificationModel](../models/filtered_notifications_configurations.md#filterednotificationmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/filterednotifications^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ { }, { }, { } ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import get_connection_filtered_notifications_configurations
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_connection_filtered_notifications_configurations.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			FilteredNotificationModel(),
			FilteredNotificationModel(),
			FilteredNotificationModel()
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import get_connection_filtered_notifications_configurations
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_connection_filtered_notifications_configurations.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			FilteredNotificationModel(),
			FilteredNotificationModel(),
			FilteredNotificationModel()
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import get_connection_filtered_notifications_configurations
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_connection_filtered_notifications_configurations.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			FilteredNotificationModel(),
			FilteredNotificationModel(),
			FilteredNotificationModel()
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import get_connection_filtered_notifications_configurations
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_connection_filtered_notifications_configurations.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			FilteredNotificationModel(),
			FilteredNotificationModel(),
			FilteredNotificationModel()
		]
        ```
    
    
    



___
## get_default_filtered_notification_configuration
Returns a model of the filtered notification from default notifications

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/filtered_notifications_configurations/get_default_filtered_notification_configuration.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/default/filterednotifications/{filteredNotificationName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`filtered_notification_model`](../models/filtered_notifications_configurations.md#filterednotificationmodel)</span>||*[FilteredNotificationModel](../models/filtered_notifications_configurations.md#filterednotificationmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`filtered_notification_name`</span>|Filtered notification name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/default/filterednotifications/fact*^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        { }
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import get_default_filtered_notification_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_filtered_notification_configuration.sync(
	    'fact*',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        FilteredNotificationModel()
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import get_default_filtered_notification_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_filtered_notification_configuration.asyncio(
	    'fact*',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        FilteredNotificationModel()
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import get_default_filtered_notification_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_filtered_notification_configuration.sync(
	    'fact*',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        FilteredNotificationModel()
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import get_default_filtered_notification_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_filtered_notification_configuration.asyncio(
	    'fact*',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        FilteredNotificationModel()
        ```
    
    
    



___
## get_default_filtered_notifications_configurations
Returns the list of filtered notification configurations on default notifications

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/filtered_notifications_configurations/get_default_filtered_notifications_configurations.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/default/filterednotifications
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`filtered_notification_model`</span>||*List[[FilteredNotificationModel](../models/filtered_notifications_configurations.md#filterednotificationmodel)]*|








**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/default/filterednotifications^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ { }, { }, { } ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import get_default_filtered_notifications_configurations
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_filtered_notifications_configurations.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			FilteredNotificationModel(),
			FilteredNotificationModel(),
			FilteredNotificationModel()
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import get_default_filtered_notifications_configurations
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_filtered_notifications_configurations.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			FilteredNotificationModel(),
			FilteredNotificationModel(),
			FilteredNotificationModel()
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import get_default_filtered_notifications_configurations
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_filtered_notifications_configurations.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			FilteredNotificationModel(),
			FilteredNotificationModel(),
			FilteredNotificationModel()
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import get_default_filtered_notifications_configurations
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_filtered_notifications_configurations.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			FilteredNotificationModel(),
			FilteredNotificationModel(),
			FilteredNotificationModel()
		]
        ```
    
    
    



___
## update_connection_filtered_notification_configuration
Updates a filtered notification configuration according to the provided model

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/filtered_notifications_configurations/update_connection_filtered_notification_configuration.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/filterednotifications/{filteredNotificationName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`filtered_notification_name`</span>|Filtered notification name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Filtered notification model|*[FilteredNotificationModel](../models/filtered_notifications_configurations.md#filterednotificationmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/filterednotifications/fact*^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import update_connection_filtered_notification_configuration
	from dqops.client.models import FilteredNotificationModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = FilteredNotificationModel()
	
	call_result = update_connection_filtered_notification_configuration.sync(
	    'sample_connection',
	    'fact*',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import update_connection_filtered_notification_configuration
	from dqops.client.models import FilteredNotificationModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = FilteredNotificationModel()
	
	call_result = await update_connection_filtered_notification_configuration.asyncio(
	    'sample_connection',
	    'fact*',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import update_connection_filtered_notification_configuration
	from dqops.client.models import FilteredNotificationModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = FilteredNotificationModel()
	
	call_result = update_connection_filtered_notification_configuration.sync(
	    'sample_connection',
	    'fact*',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import update_connection_filtered_notification_configuration
	from dqops.client.models import FilteredNotificationModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = FilteredNotificationModel()
	
	call_result = await update_connection_filtered_notification_configuration.asyncio(
	    'sample_connection',
	    'fact*',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_default_filtered_notification_configuration
Updates a filtered notification configuration on default notifications according to the provided model

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/filtered_notifications_configurations/update_default_filtered_notification_configuration.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/default/filterednotifications/{filteredNotificationName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`filtered_notification_name`</span>|Filtered notification name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Filtered notification model|*[FilteredNotificationModel](../models/filtered_notifications_configurations.md#filterednotificationmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/default/filterednotifications/fact*^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import update_default_filtered_notification_configuration
	from dqops.client.models import FilteredNotificationModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = FilteredNotificationModel()
	
	call_result = update_default_filtered_notification_configuration.sync(
	    'fact*',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import update_default_filtered_notification_configuration
	from dqops.client.models import FilteredNotificationModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = FilteredNotificationModel()
	
	call_result = await update_default_filtered_notification_configuration.asyncio(
	    'fact*',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import update_default_filtered_notification_configuration
	from dqops.client.models import FilteredNotificationModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = FilteredNotificationModel()
	
	call_result = update_default_filtered_notification_configuration.sync(
	    'fact*',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.filtered_notifications_configurations import update_default_filtered_notification_configuration
	from dqops.client.models import FilteredNotificationModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = FilteredNotificationModel()
	
	call_result = await update_default_filtered_notification_configuration.asyncio(
	    'fact*',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



