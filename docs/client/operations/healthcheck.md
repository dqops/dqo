# DQOps REST API healthcheck operations
Health check operations for checking if the DQOps service is up and operational. Used for monitoring by load balancers.


___
## is_healthy
Checks if the DQOps instance is healthy and operational. Returns a text &quot;OK&quot; and a HTTP status code 200 when the service is active and can accept jobs,  or returns a text &quot;UNAVAILABLE&quot; and a HTTP status code 503 when the service is still starting or is shutting down.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/healthcheck/is_healthy.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/ishealthy
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`string`</span>||*string*|








**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/ishealthy^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```js
        "sample_string_value"
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.healthcheck import is_healthy
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = is_healthy.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```python
        'sample_string_value'
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.healthcheck import is_healthy
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await is_healthy.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```python
        'sample_string_value'
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.healthcheck import is_healthy
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = is_healthy.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```python
        'sample_string_value'
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.healthcheck import is_healthy
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await is_healthy.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```python
        'sample_string_value'
        ```
    
    
    



