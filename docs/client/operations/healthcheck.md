Health check service for checking if the DQOps service is up and operational.  


___  
## is_healthy  
Checks if the DQOps instance is healthy and operational. Returns a text &quot;OK&quot; and a HTTP status code 200 when the service is active and can accept jobs,  or returns a text &quot;UNAVAILABLE&quot; and a HTTP status code 503 when the service is still starting or is shutting down.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/healthcheck/is_healthy.py)
  

**GET**
```
http://localhost:8888/api/ishealthy  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|string||string|








**Usage examples**  

=== "curl"
      
    ```bash
    curl http://localhost:8888/api/ishealthy^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.healthcheck import is_healthy
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	is_healthy.sync(
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.healthcheck import is_healthy
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = is_healthy.asyncio(
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.healthcheck import is_healthy
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	is_healthy.sync(
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.healthcheck import is_healthy
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = is_healthy.asyncio(
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    "sample_string_value"
    ```


