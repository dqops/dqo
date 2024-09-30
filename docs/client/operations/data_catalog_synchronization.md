---
title: DQOps REST API data_catalog_synchronization operations
---
# DQOps REST API data_catalog_synchronization operations
Operations related to synchronization of data quality health results to the a data catalog.


___
## push_data_quality_status_to_data_catalog
Pushes the data quality status of tables matching the search filters to the data catalog.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_catalog_synchronization/push_data_quality_status_to_data_catalog.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/datacatalog/sync/pushdatahealth
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection`</span>|Optional connection name filter, accepts filters in the form: fullname, *suffix, prefix*, *contains*.|*string*| |
|<span class="no-wrap-code">`schema`</span>|Optional schema name filter, accepts filters in the form: fullname, *suffix, prefix*, *contains*.|*string*| |
|<span class="no-wrap-code">`table`</span>|Optional table name filter, accepts filters in the form: fullname, *suffix, prefix*, *contains*.|*string*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/datacatalog/sync/pushdatahealth^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_catalog_synchronization import push_data_quality_status_to_data_catalog
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = push_data_quality_status_to_data_catalog.sync(
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_catalog_synchronization import push_data_quality_status_to_data_catalog
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await push_data_quality_status_to_data_catalog.asyncio(
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_catalog_synchronization import push_data_quality_status_to_data_catalog
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = push_data_quality_status_to_data_catalog.sync(
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_catalog_synchronization import push_data_quality_status_to_data_catalog
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await push_data_quality_status_to_data_catalog.asyncio(
	    client=dqops_client
	)
	
    ```

    



