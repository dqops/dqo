---
title: DQOps REST API data_domains operations
---
# DQOps REST API data_domains operations
Data domain management API to create different data domains.


___
## create_data_domain
Creates a new data domain given a data domain display name.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_domains/create_data_domain.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/domains/
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`local_data_domain_model`](../models/data_domains.md#localdatadomainmodel)</span>||*[LocalDataDomainModel](../models/data_domains.md#localdatadomainmodel)*|






**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Data domain display name|*string*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/domains/^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"\"sample_string_value\""
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "domain_name" : "sales",
		  "display_name" : "Sales data domain",
		  "created_at" : "2024-09-14T18:33:00Z",
		  "enable_scheduler" : true
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import create_data_domain
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = 'sample_string_value'
	
	call_result = create_data_domain.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        LocalDataDomainModel(
			domain_name='sales',
			display_name='Sales data domain',
			created_at=OffsetDateTime(
				offset=ZoneOffset(
					total_seconds=0,
					id='Z'
				)
			),
			enable_scheduler=True
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import create_data_domain
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = 'sample_string_value'
	
	call_result = await create_data_domain.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        LocalDataDomainModel(
			domain_name='sales',
			display_name='Sales data domain',
			created_at=OffsetDateTime(
				offset=ZoneOffset(
					total_seconds=0,
					id='Z'
				)
			),
			enable_scheduler=True
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import create_data_domain
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = 'sample_string_value'
	
	call_result = create_data_domain.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        LocalDataDomainModel(
			domain_name='sales',
			display_name='Sales data domain',
			created_at=OffsetDateTime(
				offset=ZoneOffset(
					total_seconds=0,
					id='Z'
				)
			),
			enable_scheduler=True
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import create_data_domain
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = 'sample_string_value'
	
	call_result = await create_data_domain.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        LocalDataDomainModel(
			domain_name='sales',
			display_name='Sales data domain',
			created_at=OffsetDateTime(
				offset=ZoneOffset(
					total_seconds=0,
					id='Z'
				)
			),
			enable_scheduler=True
		)
        ```
    
    
    



___
## delete_data_domain
Deletes a data domain. The domain is deleted in the DQOps SaaS cloud and locally.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_domains/delete_data_domain.py) to see the source code on GitHub.


**DELETE**
```
http://localhost:8888/api/domains/{dataDomainName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`data_domain_name`</span>|Data domain name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X DELETE http://localhost:8888/api/domains/crm^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import delete_data_domain
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = delete_data_domain.sync(
	    'crm',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import delete_data_domain
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await delete_data_domain.asyncio(
	    'crm',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import delete_data_domain
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = delete_data_domain.sync(
	    'crm',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import delete_data_domain
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await delete_data_domain.asyncio(
	    'crm',
	    client=dqops_client
	)
	
    ```

    



___
## get_local_data_domains
Returns a list of local data domains that this instance is maintaining. Data domains are supported only in an ENTERPRISE versions of DQOps.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_domains/get_local_data_domains.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/domains/
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`local_data_domain_model`</span>||*List[[LocalDataDomainModel](../models/data_domains.md#localdatadomainmodel)]*|








**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/domains/^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "domain_name" : "sales",
		  "display_name" : "Sales data domain",
		  "created_at" : "2024-09-14T18:33:00Z",
		  "enable_scheduler" : true
		}, {
		  "domain_name" : "sales",
		  "display_name" : "Sales data domain",
		  "created_at" : "2024-09-14T18:33:00Z",
		  "enable_scheduler" : true
		}, {
		  "domain_name" : "sales",
		  "display_name" : "Sales data domain",
		  "created_at" : "2024-09-14T18:33:00Z",
		  "enable_scheduler" : true
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import get_local_data_domains
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_local_data_domains.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			LocalDataDomainModel(
				domain_name='sales',
				display_name='Sales data domain',
				created_at=OffsetDateTime(
					offset=ZoneOffset(
						total_seconds=0,
						id='Z'
					)
				),
				enable_scheduler=True
			),
			LocalDataDomainModel(
				domain_name='sales',
				display_name='Sales data domain',
				created_at=OffsetDateTime(
					offset=ZoneOffset(
						total_seconds=0,
						id='Z'
					)
				),
				enable_scheduler=True
			),
			LocalDataDomainModel(
				domain_name='sales',
				display_name='Sales data domain',
				created_at=OffsetDateTime(
					offset=ZoneOffset(
						total_seconds=0,
						id='Z'
					)
				),
				enable_scheduler=True
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import get_local_data_domains
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_local_data_domains.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			LocalDataDomainModel(
				domain_name='sales',
				display_name='Sales data domain',
				created_at=OffsetDateTime(
					offset=ZoneOffset(
						total_seconds=0,
						id='Z'
					)
				),
				enable_scheduler=True
			),
			LocalDataDomainModel(
				domain_name='sales',
				display_name='Sales data domain',
				created_at=OffsetDateTime(
					offset=ZoneOffset(
						total_seconds=0,
						id='Z'
					)
				),
				enable_scheduler=True
			),
			LocalDataDomainModel(
				domain_name='sales',
				display_name='Sales data domain',
				created_at=OffsetDateTime(
					offset=ZoneOffset(
						total_seconds=0,
						id='Z'
					)
				),
				enable_scheduler=True
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import get_local_data_domains
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_local_data_domains.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			LocalDataDomainModel(
				domain_name='sales',
				display_name='Sales data domain',
				created_at=OffsetDateTime(
					offset=ZoneOffset(
						total_seconds=0,
						id='Z'
					)
				),
				enable_scheduler=True
			),
			LocalDataDomainModel(
				domain_name='sales',
				display_name='Sales data domain',
				created_at=OffsetDateTime(
					offset=ZoneOffset(
						total_seconds=0,
						id='Z'
					)
				),
				enable_scheduler=True
			),
			LocalDataDomainModel(
				domain_name='sales',
				display_name='Sales data domain',
				created_at=OffsetDateTime(
					offset=ZoneOffset(
						total_seconds=0,
						id='Z'
					)
				),
				enable_scheduler=True
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import get_local_data_domains
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_local_data_domains.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			LocalDataDomainModel(
				domain_name='sales',
				display_name='Sales data domain',
				created_at=OffsetDateTime(
					offset=ZoneOffset(
						total_seconds=0,
						id='Z'
					)
				),
				enable_scheduler=True
			),
			LocalDataDomainModel(
				domain_name='sales',
				display_name='Sales data domain',
				created_at=OffsetDateTime(
					offset=ZoneOffset(
						total_seconds=0,
						id='Z'
					)
				),
				enable_scheduler=True
			),
			LocalDataDomainModel(
				domain_name='sales',
				display_name='Sales data domain',
				created_at=OffsetDateTime(
					offset=ZoneOffset(
						total_seconds=0,
						id='Z'
					)
				),
				enable_scheduler=True
			)
		]
        ```
    
    
    



___
## switch_to_data_domain
Switches to a different data domain. This operation sends a special cookie and redirects the user to the home screen.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_domains/switch_to_data_domain.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/domains/{dataDomainName}/switch
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`data_domain_name`</span>|Data domain name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/domains/crm/switch^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import switch_to_data_domain
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = switch_to_data_domain.sync(
	    'crm',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import switch_to_data_domain
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await switch_to_data_domain.asyncio(
	    'crm',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import switch_to_data_domain
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = switch_to_data_domain.sync(
	    'crm',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import switch_to_data_domain
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await switch_to_data_domain.asyncio(
	    'crm',
	    client=dqops_client
	)
	
    ```

    



___
## synchronize_data_domains
Synchronizes the domains in the SaaS cloud to this instance. All data domains will be created locally.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_domains/synchronize_data_domains.py) to see the source code on GitHub.


**PATCH**
```
http://localhost:8888/api/domains/
```







**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PATCH http://localhost:8888/api/domains/^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import synchronize_data_domains
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = synchronize_data_domains.sync(
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import synchronize_data_domains
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await synchronize_data_domains.asyncio(
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import synchronize_data_domains
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = synchronize_data_domains.sync(
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_domains import synchronize_data_domains
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await synchronize_data_domains.asyncio(
	    client=dqops_client
	)
	
    ```

    



