# DQOps REST API timezones operations
Operations for returning time zone names and codes supported by DQOps.


___
## get_available_zone_ids
Returns a list of available time zone ids
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/timezones/get_available_zone_ids.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/timezones
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|string||List[string]|








**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/timezones^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.timezones import get_available_zone_ids
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_available_zone_ids.sync(
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.timezones import get_available_zone_ids
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_available_zone_ids.asyncio(
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.timezones import get_available_zone_ids
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_available_zone_ids.sync(
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.timezones import get_available_zone_ids
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_available_zone_ids.asyncio(
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
    [ "sampleString_1", "sampleString_2", "sampleString_3" ]
    ```


