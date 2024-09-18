---
title: DQOps REST API jobs operations
---
# DQOps REST API jobs operations
Jobs management controller that supports starting new jobs, such as running selected data quality checks. Provides access to the job queue for incremental monitoring.


___
## cancel_job
Cancels a running job

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/cancel_job.py) to see the source code on GitHub.


**DELETE**
```
http://localhost:8888/api/jobs/jobs/{jobId}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`job_id`</span>|Job id|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X DELETE http://localhost:8888/api/jobs/jobs/123123124324324^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import cancel_job
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = cancel_job.sync(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import cancel_job
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await cancel_job.asyncio(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import cancel_job
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = cancel_job.sync(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import cancel_job
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await cancel_job.asyncio(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    



___
## collect_error_samples
Starts a new background job that will run selected data quality checks to collect their error samples

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/collect_error_samples.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/jobs/collecterrorsamples
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`collect_error_samples_result`](../models/jobs.md#collecterrorsamplesresult)</span>||*[CollectErrorSamplesResult](../models/jobs.md#collecterrorsamplesresult)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`job_business_key`</span>|Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.|*string*| |
|<span class="no-wrap-code">`wait`</span>|Wait until the error sampling finish to run, the default value is true (queue a background job and wait for the job to finish, up to waitTimeout seconds)|*boolean*| |
|<span class="no-wrap-code">`wait_timeout`</span>|The wait timeout in seconds, when the wait timeout elapses and the checks are still running, only the job id is returned without the results. The default timeout is 120 seconds, but it can be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|*long*| |




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Data quality check run configuration (target checks and an optional time range)|*[CollectErrorSamplesParameters](../models/jobs.md#collecterrorsamplesparameters)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/jobs/collecterrorsamples^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"data_scope\":\"table\",\"dummy_sensor_execution\":false}"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "jobId" : {
		    "jobId" : 123456789,
		    "createdAt" : "2007-10-11T13:42:00Z"
		  },
		  "result" : {
		    "executed_error_samplers" : 3,
		    "columns_analyzed" : 1,
		    "columns_successfully_analyzed" : 0,
		    "total_error_samplers_failed" : 1,
		    "total_error_samples_collected" : 2
		  },
		  "status" : "finished"
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import collect_error_samples
	from dqops.client.models import CollectErrorSamplesParameters, \
	                                ErrorSamplesDataScope
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CollectErrorSamplesParameters(
		data_scope=ErrorSamplesDataScope.TABLE,
		dummy_sensor_execution=False
	)
	
	call_result = collect_error_samples.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CollectErrorSamplesResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=ErrorSamplerResult(
				executed_error_samplers=3,
				columns_analyzed=1,
				columns_successfully_analyzed=0,
				total_error_samplers_failed=1,
				total_error_samples_collected=2
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import collect_error_samples
	from dqops.client.models import CollectErrorSamplesParameters, \
	                                ErrorSamplesDataScope
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CollectErrorSamplesParameters(
		data_scope=ErrorSamplesDataScope.TABLE,
		dummy_sensor_execution=False
	)
	
	call_result = await collect_error_samples.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CollectErrorSamplesResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=ErrorSamplerResult(
				executed_error_samplers=3,
				columns_analyzed=1,
				columns_successfully_analyzed=0,
				total_error_samplers_failed=1,
				total_error_samples_collected=2
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import collect_error_samples
	from dqops.client.models import CollectErrorSamplesParameters, \
	                                ErrorSamplesDataScope
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CollectErrorSamplesParameters(
		data_scope=ErrorSamplesDataScope.TABLE,
		dummy_sensor_execution=False
	)
	
	call_result = collect_error_samples.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CollectErrorSamplesResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=ErrorSamplerResult(
				executed_error_samplers=3,
				columns_analyzed=1,
				columns_successfully_analyzed=0,
				total_error_samplers_failed=1,
				total_error_samples_collected=2
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import collect_error_samples
	from dqops.client.models import CollectErrorSamplesParameters, \
	                                ErrorSamplesDataScope
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CollectErrorSamplesParameters(
		data_scope=ErrorSamplesDataScope.TABLE,
		dummy_sensor_execution=False
	)
	
	call_result = await collect_error_samples.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CollectErrorSamplesResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=ErrorSamplerResult(
				executed_error_samplers=3,
				columns_analyzed=1,
				columns_successfully_analyzed=0,
				total_error_samplers_failed=1,
				total_error_samples_collected=2
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    



___
## collect_statistics_on_data_groups
Starts a new background job that will run selected data statistics collectors on tables, calculating separate metric for each data grouping

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/collect_statistics_on_data_groups.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/jobs/collectstatistics/withgrouping
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`collect_statistics_queue_job_result`](../models/jobs.md#collectstatisticsqueuejobresult)</span>||*[CollectStatisticsQueueJobResult](../models/jobs.md#collectstatisticsqueuejobresult)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`job_business_key`</span>|Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.|*string*| |
|<span class="no-wrap-code">`wait`</span>|Wait until the statistic collection job finishes to run, the default value is true (queue a background job and wait for the job to finish, up to waitTimeout seconds)|*boolean*| |
|<span class="no-wrap-code">`wait_timeout`</span>|The wait timeout in seconds, when the wait timeout elapses and the job is still running, only the job id is returned without the results. The default timeout is 120 seconds, but it can be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|*long*| |




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Data statistics collectors filter|*[StatisticsCollectorSearchFilters](../models/jobs.md#statisticscollectorsearchfilters)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/jobs/collectstatistics/withgrouping^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"columnNames\":[\"sample_column\"],\"collectorCategory\":\"sample_category\"}"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "jobId" : {
		    "jobId" : 123456789,
		    "createdAt" : "2007-10-11T13:42:00Z"
		  },
		  "result" : {
		    "executed_statistics_collectors" : 3,
		    "columns_analyzed" : 1,
		    "columns_successfully_analyzed" : 0,
		    "total_collectors_failed" : 1,
		    "total_collected_results" : 2
		  },
		  "status" : "finished"
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import collect_statistics_on_data_groups
	from dqops.client.models import StatisticsCollectorSearchFilters
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = StatisticsCollectorSearchFilters(
		column_names=[
			'sample_column'
		],
		collector_category='sample_category',
		connection='sample_connection',
		full_table_name='sample_schema.sample_table',
		enabled=True
	)
	
	call_result = collect_statistics_on_data_groups.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CollectStatisticsQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=CollectStatisticsResult(
				executed_statistics_collectors=3,
				columns_analyzed=1,
				columns_successfully_analyzed=0,
				total_collectors_failed=1,
				total_collected_results=2
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import collect_statistics_on_data_groups
	from dqops.client.models import StatisticsCollectorSearchFilters
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = StatisticsCollectorSearchFilters(
		column_names=[
			'sample_column'
		],
		collector_category='sample_category',
		connection='sample_connection',
		full_table_name='sample_schema.sample_table',
		enabled=True
	)
	
	call_result = await collect_statistics_on_data_groups.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CollectStatisticsQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=CollectStatisticsResult(
				executed_statistics_collectors=3,
				columns_analyzed=1,
				columns_successfully_analyzed=0,
				total_collectors_failed=1,
				total_collected_results=2
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import collect_statistics_on_data_groups
	from dqops.client.models import StatisticsCollectorSearchFilters
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = StatisticsCollectorSearchFilters(
		column_names=[
			'sample_column'
		],
		collector_category='sample_category',
		connection='sample_connection',
		full_table_name='sample_schema.sample_table',
		enabled=True
	)
	
	call_result = collect_statistics_on_data_groups.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CollectStatisticsQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=CollectStatisticsResult(
				executed_statistics_collectors=3,
				columns_analyzed=1,
				columns_successfully_analyzed=0,
				total_collectors_failed=1,
				total_collected_results=2
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import collect_statistics_on_data_groups
	from dqops.client.models import StatisticsCollectorSearchFilters
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = StatisticsCollectorSearchFilters(
		column_names=[
			'sample_column'
		],
		collector_category='sample_category',
		connection='sample_connection',
		full_table_name='sample_schema.sample_table',
		enabled=True
	)
	
	call_result = await collect_statistics_on_data_groups.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CollectStatisticsQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=CollectStatisticsResult(
				executed_statistics_collectors=3,
				columns_analyzed=1,
				columns_successfully_analyzed=0,
				total_collectors_failed=1,
				total_collected_results=2
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    



___
## collect_statistics_on_table
Starts a new background job that will run selected data statistics collectors for the entire table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/collect_statistics_on_table.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/jobs/collectstatistics/table
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`collect_statistics_queue_job_result`](../models/jobs.md#collectstatisticsqueuejobresult)</span>||*[CollectStatisticsQueueJobResult](../models/jobs.md#collectstatisticsqueuejobresult)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`job_business_key`</span>|Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.|*string*| |
|<span class="no-wrap-code">`wait`</span>|Wait until the statistic collection job finishes to run, the default value is true (queue a background job and wait for the job to finish, up to waitTimeout seconds)|*boolean*| |
|<span class="no-wrap-code">`wait_timeout`</span>|The wait timeout in seconds, when the wait timeout elapses and the job is still running, only the job id is returned without the results. The default timeout is 120 seconds, but it can be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|*long*| |




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Data statistics collectors filter|*[StatisticsCollectorSearchFilters](../models/jobs.md#statisticscollectorsearchfilters)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/jobs/collectstatistics/table^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"columnNames\":[\"sample_column\"],\"collectorCategory\":\"sample_category\"}"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "jobId" : {
		    "jobId" : 123456789,
		    "createdAt" : "2007-10-11T13:42:00Z"
		  },
		  "result" : {
		    "executed_statistics_collectors" : 3,
		    "columns_analyzed" : 1,
		    "columns_successfully_analyzed" : 0,
		    "total_collectors_failed" : 1,
		    "total_collected_results" : 2
		  },
		  "status" : "finished"
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import collect_statistics_on_table
	from dqops.client.models import StatisticsCollectorSearchFilters
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = StatisticsCollectorSearchFilters(
		column_names=[
			'sample_column'
		],
		collector_category='sample_category',
		connection='sample_connection',
		full_table_name='sample_schema.sample_table',
		enabled=True
	)
	
	call_result = collect_statistics_on_table.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CollectStatisticsQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=CollectStatisticsResult(
				executed_statistics_collectors=3,
				columns_analyzed=1,
				columns_successfully_analyzed=0,
				total_collectors_failed=1,
				total_collected_results=2
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import collect_statistics_on_table
	from dqops.client.models import StatisticsCollectorSearchFilters
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = StatisticsCollectorSearchFilters(
		column_names=[
			'sample_column'
		],
		collector_category='sample_category',
		connection='sample_connection',
		full_table_name='sample_schema.sample_table',
		enabled=True
	)
	
	call_result = await collect_statistics_on_table.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CollectStatisticsQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=CollectStatisticsResult(
				executed_statistics_collectors=3,
				columns_analyzed=1,
				columns_successfully_analyzed=0,
				total_collectors_failed=1,
				total_collected_results=2
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import collect_statistics_on_table
	from dqops.client.models import StatisticsCollectorSearchFilters
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = StatisticsCollectorSearchFilters(
		column_names=[
			'sample_column'
		],
		collector_category='sample_category',
		connection='sample_connection',
		full_table_name='sample_schema.sample_table',
		enabled=True
	)
	
	call_result = collect_statistics_on_table.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CollectStatisticsQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=CollectStatisticsResult(
				executed_statistics_collectors=3,
				columns_analyzed=1,
				columns_successfully_analyzed=0,
				total_collectors_failed=1,
				total_collected_results=2
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import collect_statistics_on_table
	from dqops.client.models import StatisticsCollectorSearchFilters
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = StatisticsCollectorSearchFilters(
		column_names=[
			'sample_column'
		],
		collector_category='sample_category',
		connection='sample_connection',
		full_table_name='sample_schema.sample_table',
		enabled=True
	)
	
	call_result = await collect_statistics_on_table.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CollectStatisticsQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=CollectStatisticsResult(
				executed_statistics_collectors=3,
				columns_analyzed=1,
				columns_successfully_analyzed=0,
				total_collectors_failed=1,
				total_collected_results=2
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    



___
## delete_stored_data
Starts a new background job that will delete stored data about check results, sensor readouts etc.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/delete_stored_data.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/jobs/deletestoreddata
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`delete_stored_data_queue_job_result`](../models/jobs.md#deletestoreddataqueuejobresult)</span>||*[DeleteStoredDataQueueJobResult](../models/jobs.md#deletestoreddataqueuejobresult)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`job_business_key`</span>|Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.|*string*| |
|<span class="no-wrap-code">`wait`</span>|Wait until the import tables job finishes to run, the default value is true (queue a background job and wait for the job to finish, up to waitTimeout seconds)|*boolean*| |
|<span class="no-wrap-code">`wait_timeout`</span>|The wait timeout in seconds, when the wait timeout elapses and the delete stored data job is still running, only the job id is returned without the results. The default timeout is 120 seconds, but it can be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|*long*| |




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Delete stored data job parameters|*[DeleteStoredDataQueueJobParameters](../models/jobs.md#deletestoreddataqueuejobparameters)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/jobs/deletestoreddata^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"deleteErrorSamples\":true,\"deleteIncidents\":true,\"deleteChecksConfiguration\":false,\"columnNames\":[\"sample_column\"]}"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "jobId" : {
		    "jobId" : 123456789,
		    "createdAt" : "2007-10-11T13:42:00Z"
		  },
		  "status" : "queued"
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import delete_stored_data
	from dqops.client.models import DeleteStoredDataQueueJobParameters
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DeleteStoredDataQueueJobParameters(
		connection='sample_connection',
		full_table_name='sample_schema.sample_table',
		delete_errors=True,
		delete_statistics=True,
		delete_check_results=True,
		delete_sensor_readouts=True,
		delete_error_samples=True,
		delete_incidents=True,
		delete_checks_configuration=False,
		column_names=[
			'sample_column'
		]
	)
	
	call_result = delete_stored_data.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DeleteStoredDataQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			status=DqoJobStatus.QUEUED
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import delete_stored_data
	from dqops.client.models import DeleteStoredDataQueueJobParameters
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DeleteStoredDataQueueJobParameters(
		connection='sample_connection',
		full_table_name='sample_schema.sample_table',
		delete_errors=True,
		delete_statistics=True,
		delete_check_results=True,
		delete_sensor_readouts=True,
		delete_error_samples=True,
		delete_incidents=True,
		delete_checks_configuration=False,
		column_names=[
			'sample_column'
		]
	)
	
	call_result = await delete_stored_data.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DeleteStoredDataQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			status=DqoJobStatus.QUEUED
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import delete_stored_data
	from dqops.client.models import DeleteStoredDataQueueJobParameters
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DeleteStoredDataQueueJobParameters(
		connection='sample_connection',
		full_table_name='sample_schema.sample_table',
		delete_errors=True,
		delete_statistics=True,
		delete_check_results=True,
		delete_sensor_readouts=True,
		delete_error_samples=True,
		delete_incidents=True,
		delete_checks_configuration=False,
		column_names=[
			'sample_column'
		]
	)
	
	call_result = delete_stored_data.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DeleteStoredDataQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			status=DqoJobStatus.QUEUED
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import delete_stored_data
	from dqops.client.models import DeleteStoredDataQueueJobParameters
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DeleteStoredDataQueueJobParameters(
		connection='sample_connection',
		full_table_name='sample_schema.sample_table',
		delete_errors=True,
		delete_statistics=True,
		delete_check_results=True,
		delete_sensor_readouts=True,
		delete_error_samples=True,
		delete_incidents=True,
		delete_checks_configuration=False,
		column_names=[
			'sample_column'
		]
	)
	
	call_result = await delete_stored_data.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DeleteStoredDataQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			status=DqoJobStatus.QUEUED
		)
        ```
    
    
    



___
## get_all_jobs
Retrieves a list of all queued and recently finished jobs.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/get_all_jobs.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/jobs/jobs
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`dqo_job_queue_initial_snapshot_model`](../models/jobs.md#dqojobqueueinitialsnapshotmodel)</span>||*[DqoJobQueueInitialSnapshotModel](../models/jobs.md#dqojobqueueinitialsnapshotmodel)*|








**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/jobs/jobs^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "jobs" : [ ],
		  "folderSynchronizationStatus" : {
		    "sources" : "unchanged",
		    "sensors" : "unchanged",
		    "rules" : "unchanged",
		    "checks" : "unchanged",
		    "settings" : "unchanged",
		    "credentials" : "unchanged",
		    "dictionaries" : "unchanged",
		    "patterns" : "unchanged",
		    "data_sensor_readouts" : "unchanged",
		    "data_check_results" : "unchanged",
		    "data_statistics" : "unchanged",
		    "data_errors" : "unchanged",
		    "data_incidents" : "unchanged"
		  },
		  "lastSequenceNumber" : 3854372
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import get_all_jobs
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_jobs.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoJobQueueInitialSnapshotModel(
			jobs=[
			
			],
			folder_synchronization_status=CloudSynchronizationFoldersStatusModel(
				sources=FolderSynchronizationStatus.UNCHANGED,
				sensors=FolderSynchronizationStatus.UNCHANGED,
				rules=FolderSynchronizationStatus.UNCHANGED,
				checks=FolderSynchronizationStatus.UNCHANGED,
				settings=FolderSynchronizationStatus.UNCHANGED,
				credentials=FolderSynchronizationStatus.UNCHANGED,
				dictionaries=FolderSynchronizationStatus.UNCHANGED,
				patterns=FolderSynchronizationStatus.UNCHANGED,
				data_sensor_readouts=FolderSynchronizationStatus.UNCHANGED,
				data_check_results=FolderSynchronizationStatus.UNCHANGED,
				data_statistics=FolderSynchronizationStatus.UNCHANGED,
				data_errors=FolderSynchronizationStatus.UNCHANGED,
				data_incidents=FolderSynchronizationStatus.UNCHANGED
			),
			last_sequence_number=3854372
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import get_all_jobs
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_jobs.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoJobQueueInitialSnapshotModel(
			jobs=[
			
			],
			folder_synchronization_status=CloudSynchronizationFoldersStatusModel(
				sources=FolderSynchronizationStatus.UNCHANGED,
				sensors=FolderSynchronizationStatus.UNCHANGED,
				rules=FolderSynchronizationStatus.UNCHANGED,
				checks=FolderSynchronizationStatus.UNCHANGED,
				settings=FolderSynchronizationStatus.UNCHANGED,
				credentials=FolderSynchronizationStatus.UNCHANGED,
				dictionaries=FolderSynchronizationStatus.UNCHANGED,
				patterns=FolderSynchronizationStatus.UNCHANGED,
				data_sensor_readouts=FolderSynchronizationStatus.UNCHANGED,
				data_check_results=FolderSynchronizationStatus.UNCHANGED,
				data_statistics=FolderSynchronizationStatus.UNCHANGED,
				data_errors=FolderSynchronizationStatus.UNCHANGED,
				data_incidents=FolderSynchronizationStatus.UNCHANGED
			),
			last_sequence_number=3854372
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import get_all_jobs
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_jobs.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoJobQueueInitialSnapshotModel(
			jobs=[
			
			],
			folder_synchronization_status=CloudSynchronizationFoldersStatusModel(
				sources=FolderSynchronizationStatus.UNCHANGED,
				sensors=FolderSynchronizationStatus.UNCHANGED,
				rules=FolderSynchronizationStatus.UNCHANGED,
				checks=FolderSynchronizationStatus.UNCHANGED,
				settings=FolderSynchronizationStatus.UNCHANGED,
				credentials=FolderSynchronizationStatus.UNCHANGED,
				dictionaries=FolderSynchronizationStatus.UNCHANGED,
				patterns=FolderSynchronizationStatus.UNCHANGED,
				data_sensor_readouts=FolderSynchronizationStatus.UNCHANGED,
				data_check_results=FolderSynchronizationStatus.UNCHANGED,
				data_statistics=FolderSynchronizationStatus.UNCHANGED,
				data_errors=FolderSynchronizationStatus.UNCHANGED,
				data_incidents=FolderSynchronizationStatus.UNCHANGED
			),
			last_sequence_number=3854372
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import get_all_jobs
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_jobs.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoJobQueueInitialSnapshotModel(
			jobs=[
			
			],
			folder_synchronization_status=CloudSynchronizationFoldersStatusModel(
				sources=FolderSynchronizationStatus.UNCHANGED,
				sensors=FolderSynchronizationStatus.UNCHANGED,
				rules=FolderSynchronizationStatus.UNCHANGED,
				checks=FolderSynchronizationStatus.UNCHANGED,
				settings=FolderSynchronizationStatus.UNCHANGED,
				credentials=FolderSynchronizationStatus.UNCHANGED,
				dictionaries=FolderSynchronizationStatus.UNCHANGED,
				patterns=FolderSynchronizationStatus.UNCHANGED,
				data_sensor_readouts=FolderSynchronizationStatus.UNCHANGED,
				data_check_results=FolderSynchronizationStatus.UNCHANGED,
				data_statistics=FolderSynchronizationStatus.UNCHANGED,
				data_errors=FolderSynchronizationStatus.UNCHANGED,
				data_incidents=FolderSynchronizationStatus.UNCHANGED
			),
			last_sequence_number=3854372
		)
        ```
    
    
    



___
## get_job
Retrieves the current status of a single job, identified by a job id.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/get_job.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/jobs/jobs/{jobId}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`dqo_job_history_entry_model`](../models/jobs.md#dqojobhistoryentrymodel)</span>||*[DqoJobHistoryEntryModel](../models/jobs.md#dqojobhistoryentrymodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`job_id`</span>|Job id|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/jobs/jobs/123123124324324^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "status" : "finished",
		  "statusChangedAt" : "2007-10-11T13:42:00Z"
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import get_job
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_job.sync(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoJobHistoryEntryModel(
			status=DqoJobStatus.FINISHED,
			status_changed_at='2007-10-11T13:42:00Z'
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import get_job
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_job.asyncio(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoJobHistoryEntryModel(
			status=DqoJobStatus.FINISHED,
			status_changed_at='2007-10-11T13:42:00Z'
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import get_job
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_job.sync(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoJobHistoryEntryModel(
			status=DqoJobStatus.FINISHED,
			status_changed_at='2007-10-11T13:42:00Z'
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import get_job
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_job.asyncio(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoJobHistoryEntryModel(
			status=DqoJobStatus.FINISHED,
			status_changed_at='2007-10-11T13:42:00Z'
		)
        ```
    
    
    



___
## get_job_changes_since
Retrieves an incremental list of job changes (new jobs or job status changes)

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/get_job_changes_since.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/jobs/jobchangessince/{sequenceNumber}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`dqo_job_queue_incremental_snapshot_model`](../models/jobs.md#dqojobqueueincrementalsnapshotmodel)</span>||*[DqoJobQueueIncrementalSnapshotModel](../models/jobs.md#dqojobqueueincrementalsnapshotmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`sequence_number`</span>|Change sequence number to get job changes after that sequence|*long*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/jobs/jobchangessince/3854372^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "jobChanges" : [ ],
		  "folderSynchronizationStatus" : {
		    "sources" : "unchanged",
		    "sensors" : "unchanged",
		    "rules" : "unchanged",
		    "checks" : "unchanged",
		    "settings" : "unchanged",
		    "credentials" : "unchanged",
		    "dictionaries" : "unchanged",
		    "patterns" : "unchanged",
		    "data_sensor_readouts" : "unchanged",
		    "data_check_results" : "unchanged",
		    "data_statistics" : "unchanged",
		    "data_errors" : "unchanged",
		    "data_incidents" : "unchanged"
		  },
		  "lastSequenceNumber" : 3854372
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import get_job_changes_since
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_job_changes_since.sync(
	    3854372,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoJobQueueIncrementalSnapshotModel(
			job_changes=[
			
			],
			folder_synchronization_status=CloudSynchronizationFoldersStatusModel(
				sources=FolderSynchronizationStatus.UNCHANGED,
				sensors=FolderSynchronizationStatus.UNCHANGED,
				rules=FolderSynchronizationStatus.UNCHANGED,
				checks=FolderSynchronizationStatus.UNCHANGED,
				settings=FolderSynchronizationStatus.UNCHANGED,
				credentials=FolderSynchronizationStatus.UNCHANGED,
				dictionaries=FolderSynchronizationStatus.UNCHANGED,
				patterns=FolderSynchronizationStatus.UNCHANGED,
				data_sensor_readouts=FolderSynchronizationStatus.UNCHANGED,
				data_check_results=FolderSynchronizationStatus.UNCHANGED,
				data_statistics=FolderSynchronizationStatus.UNCHANGED,
				data_errors=FolderSynchronizationStatus.UNCHANGED,
				data_incidents=FolderSynchronizationStatus.UNCHANGED
			),
			last_sequence_number=3854372
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import get_job_changes_since
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_job_changes_since.asyncio(
	    3854372,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoJobQueueIncrementalSnapshotModel(
			job_changes=[
			
			],
			folder_synchronization_status=CloudSynchronizationFoldersStatusModel(
				sources=FolderSynchronizationStatus.UNCHANGED,
				sensors=FolderSynchronizationStatus.UNCHANGED,
				rules=FolderSynchronizationStatus.UNCHANGED,
				checks=FolderSynchronizationStatus.UNCHANGED,
				settings=FolderSynchronizationStatus.UNCHANGED,
				credentials=FolderSynchronizationStatus.UNCHANGED,
				dictionaries=FolderSynchronizationStatus.UNCHANGED,
				patterns=FolderSynchronizationStatus.UNCHANGED,
				data_sensor_readouts=FolderSynchronizationStatus.UNCHANGED,
				data_check_results=FolderSynchronizationStatus.UNCHANGED,
				data_statistics=FolderSynchronizationStatus.UNCHANGED,
				data_errors=FolderSynchronizationStatus.UNCHANGED,
				data_incidents=FolderSynchronizationStatus.UNCHANGED
			),
			last_sequence_number=3854372
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import get_job_changes_since
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_job_changes_since.sync(
	    3854372,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoJobQueueIncrementalSnapshotModel(
			job_changes=[
			
			],
			folder_synchronization_status=CloudSynchronizationFoldersStatusModel(
				sources=FolderSynchronizationStatus.UNCHANGED,
				sensors=FolderSynchronizationStatus.UNCHANGED,
				rules=FolderSynchronizationStatus.UNCHANGED,
				checks=FolderSynchronizationStatus.UNCHANGED,
				settings=FolderSynchronizationStatus.UNCHANGED,
				credentials=FolderSynchronizationStatus.UNCHANGED,
				dictionaries=FolderSynchronizationStatus.UNCHANGED,
				patterns=FolderSynchronizationStatus.UNCHANGED,
				data_sensor_readouts=FolderSynchronizationStatus.UNCHANGED,
				data_check_results=FolderSynchronizationStatus.UNCHANGED,
				data_statistics=FolderSynchronizationStatus.UNCHANGED,
				data_errors=FolderSynchronizationStatus.UNCHANGED,
				data_incidents=FolderSynchronizationStatus.UNCHANGED
			),
			last_sequence_number=3854372
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import get_job_changes_since
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_job_changes_since.asyncio(
	    3854372,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoJobQueueIncrementalSnapshotModel(
			job_changes=[
			
			],
			folder_synchronization_status=CloudSynchronizationFoldersStatusModel(
				sources=FolderSynchronizationStatus.UNCHANGED,
				sensors=FolderSynchronizationStatus.UNCHANGED,
				rules=FolderSynchronizationStatus.UNCHANGED,
				checks=FolderSynchronizationStatus.UNCHANGED,
				settings=FolderSynchronizationStatus.UNCHANGED,
				credentials=FolderSynchronizationStatus.UNCHANGED,
				dictionaries=FolderSynchronizationStatus.UNCHANGED,
				patterns=FolderSynchronizationStatus.UNCHANGED,
				data_sensor_readouts=FolderSynchronizationStatus.UNCHANGED,
				data_check_results=FolderSynchronizationStatus.UNCHANGED,
				data_statistics=FolderSynchronizationStatus.UNCHANGED,
				data_errors=FolderSynchronizationStatus.UNCHANGED,
				data_incidents=FolderSynchronizationStatus.UNCHANGED
			),
			last_sequence_number=3854372
		)
        ```
    
    
    



___
## import_tables
Starts a new background job that will import selected tables.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/import_tables.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/jobs/importtables
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`import_tables_queue_job_result`](../models/jobs.md#importtablesqueuejobresult)</span>||*[ImportTablesQueueJobResult](../models/jobs.md#importtablesqueuejobresult)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`job_business_key`</span>|Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.|*string*| |
|<span class="no-wrap-code">`wait`</span>|Wait until the import tables job finishes to run, the default value is true (queue a background job and wait for the job to finish, up to waitTimeout seconds)|*boolean*| |
|<span class="no-wrap-code">`wait_timeout`</span>|The wait timeout in seconds, when the wait timeout elapses and the import tables job is still running, only the job id is returned without the results. The default timeout is 120 seconds, but it can be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|*long*| |




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Import tables job parameters|*[ImportTablesQueueJobParameters](../models/jobs.md#importtablesqueuejobparameters)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/jobs/importtables^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connectionName\":\"sample_connection\",\"schemaName\":\"sample_schema\",\"tableNames\":[\"sample_table\"]}"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "jobId" : {
		    "jobId" : 123456789,
		    "createdAt" : "2007-10-11T13:42:00Z"
		  },
		  "result" : {
		    "source_table_specs" : [ {
		      "timestamp_columns" : {
		        "event_timestamp_column" : "col1",
		        "ingestion_timestamp_column" : "col2",
		        "partition_by_column" : "col3"
		      },
		      "incremental_time_window" : {
		        "daily_partitioning_recent_days" : 7,
		        "daily_partitioning_include_today" : true
		      },
		      "profiling_checks" : {
		        "volume" : {
		          "profile_row_count" : {
		            "error" : {
		              "min_count" : 1
		            }
		          }
		        }
		      },
		      "columns" : { }
		    } ]
		  },
		  "status" : "finished"
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import import_tables
	from dqops.client.models import ImportTablesQueueJobParameters
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ImportTablesQueueJobParameters(
		connection_name='sample_connection',
		schema_name='sample_schema',
		table_names=[
			'sample_table'
		]
	)
	
	call_result = import_tables.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ImportTablesQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=ImportTablesResult(
				source_table_specs=[
					TableSpec(
						disabled=False,
						do_not_collect_error_samples_in_profiling=False,
						always_collect_error_samples_in_monitoring=False,
						timestamp_columns=TimestampColumnsSpec(
							event_timestamp_column='col1',
							ingestion_timestamp_column='col2',
							partition_by_column='col3'
						),
						incremental_time_window=PartitionIncrementalTimeWindowSpec(
							daily_partitioning_recent_days=7,
							daily_partitioning_include_today=True,
							monthly_partitioning_include_current_month=False
						),
						groupings=DataGroupingConfigurationSpecMap(),
						table_comparisons=TableComparisonConfigurationSpecMap(),
						profiling_checks=TableProfilingCheckCategoriesSpec(
							volume=TableVolumeProfilingChecksSpec(
								profile_row_count=TableRowCountCheckSpec(
									parameters=TableVolumeRowCountSensorParametersSpec(),
									error=MinCountRule1ParametersSpec(min_count=1),
									disabled=False,
									exclude_from_kpi=False,
									include_in_sla=False,
									always_collect_error_samples=False
								)
							)
						),
						monitoring_checks=TableMonitoringCheckCategoriesSpec(),
						partitioned_checks=TablePartitionedCheckCategoriesSpec(),
						columns=ColumnSpecMap(),
						advanced_properties={
						
						}
					)
				]
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import import_tables
	from dqops.client.models import ImportTablesQueueJobParameters
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ImportTablesQueueJobParameters(
		connection_name='sample_connection',
		schema_name='sample_schema',
		table_names=[
			'sample_table'
		]
	)
	
	call_result = await import_tables.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ImportTablesQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=ImportTablesResult(
				source_table_specs=[
					TableSpec(
						disabled=False,
						do_not_collect_error_samples_in_profiling=False,
						always_collect_error_samples_in_monitoring=False,
						timestamp_columns=TimestampColumnsSpec(
							event_timestamp_column='col1',
							ingestion_timestamp_column='col2',
							partition_by_column='col3'
						),
						incremental_time_window=PartitionIncrementalTimeWindowSpec(
							daily_partitioning_recent_days=7,
							daily_partitioning_include_today=True,
							monthly_partitioning_include_current_month=False
						),
						groupings=DataGroupingConfigurationSpecMap(),
						table_comparisons=TableComparisonConfigurationSpecMap(),
						profiling_checks=TableProfilingCheckCategoriesSpec(
							volume=TableVolumeProfilingChecksSpec(
								profile_row_count=TableRowCountCheckSpec(
									parameters=TableVolumeRowCountSensorParametersSpec(),
									error=MinCountRule1ParametersSpec(min_count=1),
									disabled=False,
									exclude_from_kpi=False,
									include_in_sla=False,
									always_collect_error_samples=False
								)
							)
						),
						monitoring_checks=TableMonitoringCheckCategoriesSpec(),
						partitioned_checks=TablePartitionedCheckCategoriesSpec(),
						columns=ColumnSpecMap(),
						advanced_properties={
						
						}
					)
				]
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import import_tables
	from dqops.client.models import ImportTablesQueueJobParameters
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ImportTablesQueueJobParameters(
		connection_name='sample_connection',
		schema_name='sample_schema',
		table_names=[
			'sample_table'
		]
	)
	
	call_result = import_tables.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ImportTablesQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=ImportTablesResult(
				source_table_specs=[
					TableSpec(
						disabled=False,
						do_not_collect_error_samples_in_profiling=False,
						always_collect_error_samples_in_monitoring=False,
						timestamp_columns=TimestampColumnsSpec(
							event_timestamp_column='col1',
							ingestion_timestamp_column='col2',
							partition_by_column='col3'
						),
						incremental_time_window=PartitionIncrementalTimeWindowSpec(
							daily_partitioning_recent_days=7,
							daily_partitioning_include_today=True,
							monthly_partitioning_include_current_month=False
						),
						groupings=DataGroupingConfigurationSpecMap(),
						table_comparisons=TableComparisonConfigurationSpecMap(),
						profiling_checks=TableProfilingCheckCategoriesSpec(
							volume=TableVolumeProfilingChecksSpec(
								profile_row_count=TableRowCountCheckSpec(
									parameters=TableVolumeRowCountSensorParametersSpec(),
									error=MinCountRule1ParametersSpec(min_count=1),
									disabled=False,
									exclude_from_kpi=False,
									include_in_sla=False,
									always_collect_error_samples=False
								)
							)
						),
						monitoring_checks=TableMonitoringCheckCategoriesSpec(),
						partitioned_checks=TablePartitionedCheckCategoriesSpec(),
						columns=ColumnSpecMap(),
						advanced_properties={
						
						}
					)
				]
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import import_tables
	from dqops.client.models import ImportTablesQueueJobParameters
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ImportTablesQueueJobParameters(
		connection_name='sample_connection',
		schema_name='sample_schema',
		table_names=[
			'sample_table'
		]
	)
	
	call_result = await import_tables.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ImportTablesQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=ImportTablesResult(
				source_table_specs=[
					TableSpec(
						disabled=False,
						do_not_collect_error_samples_in_profiling=False,
						always_collect_error_samples_in_monitoring=False,
						timestamp_columns=TimestampColumnsSpec(
							event_timestamp_column='col1',
							ingestion_timestamp_column='col2',
							partition_by_column='col3'
						),
						incremental_time_window=PartitionIncrementalTimeWindowSpec(
							daily_partitioning_recent_days=7,
							daily_partitioning_include_today=True,
							monthly_partitioning_include_current_month=False
						),
						groupings=DataGroupingConfigurationSpecMap(),
						table_comparisons=TableComparisonConfigurationSpecMap(),
						profiling_checks=TableProfilingCheckCategoriesSpec(
							volume=TableVolumeProfilingChecksSpec(
								profile_row_count=TableRowCountCheckSpec(
									parameters=TableVolumeRowCountSensorParametersSpec(),
									error=MinCountRule1ParametersSpec(min_count=1),
									disabled=False,
									exclude_from_kpi=False,
									include_in_sla=False,
									always_collect_error_samples=False
								)
							)
						),
						monitoring_checks=TableMonitoringCheckCategoriesSpec(),
						partitioned_checks=TablePartitionedCheckCategoriesSpec(),
						columns=ColumnSpecMap(),
						advanced_properties={
						
						}
					)
				]
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    



___
## is_cron_scheduler_running
Checks if the DQOps internal CRON scheduler is running and processing jobs scheduled using cron expressions.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/is_cron_scheduler_running.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/jobs/scheduler/isrunning
```







**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/jobs/scheduler/isrunning^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import is_cron_scheduler_running
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = is_cron_scheduler_running.sync(
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import is_cron_scheduler_running
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await is_cron_scheduler_running.asyncio(
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import is_cron_scheduler_running
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = is_cron_scheduler_running.sync(
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import is_cron_scheduler_running
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await is_cron_scheduler_running.asyncio(
	    client=dqops_client
	)
	
    ```

    



___
## run_checks
Starts a new background job that will run selected data quality checks

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/run_checks.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/jobs/runchecks
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`run_checks_queue_job_result`](../models/jobs.md#runchecksqueuejobresult)</span>||*[RunChecksQueueJobResult](../models/jobs.md#runchecksqueuejobresult)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`job_business_key`</span>|Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.|*string*| |
|<span class="no-wrap-code">`wait`</span>|Wait until the checks finish to run, the default value is true (queue a background job and wait for the job to finish, up to waitTimeout seconds)|*boolean*| |
|<span class="no-wrap-code">`wait_timeout`</span>|The wait timeout in seconds, when the wait timeout elapses and the checks are still running, only the job id is returned without the results. The default timeout is 120 seconds, but it can be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|*long*| |




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Data quality check run configuration (target checks and an optional time range)|*[RunChecksParameters](../models/jobs.md#runchecksparameters)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/jobs/runchecks^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"check_search_filters\":{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"column\":\"sample_column\",\"columnDataType\":\"string\"}}"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "jobId" : {
		    "jobId" : 123456789,
		    "createdAt" : "2007-10-11T13:42:00Z"
		  },
		  "result" : {
		    "highest_severity" : "error",
		    "executed_checks" : 10,
		    "valid_results" : 7,
		    "warnings" : 1,
		    "errors" : 2,
		    "fatals" : 0,
		    "execution_errors" : 0
		  },
		  "status" : "finished"
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import run_checks
	from dqops.client.models import CheckSearchFilters, \
	                                RunChecksParameters
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = RunChecksParameters(
		check_search_filters=CheckSearchFilters(
			column='sample_column',
			column_data_type='string',
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		dummy_execution=False
	)
	
	call_result = run_checks.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        RunChecksQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=RunChecksResult(
				highest_severity=RuleSeverityLevel.ERROR,
				executed_checks=10,
				valid_results=7,
				warnings=1,
				errors=2,
				fatals=0,
				execution_errors=0
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import run_checks
	from dqops.client.models import CheckSearchFilters, \
	                                RunChecksParameters
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = RunChecksParameters(
		check_search_filters=CheckSearchFilters(
			column='sample_column',
			column_data_type='string',
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		dummy_execution=False
	)
	
	call_result = await run_checks.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        RunChecksQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=RunChecksResult(
				highest_severity=RuleSeverityLevel.ERROR,
				executed_checks=10,
				valid_results=7,
				warnings=1,
				errors=2,
				fatals=0,
				execution_errors=0
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import run_checks
	from dqops.client.models import CheckSearchFilters, \
	                                RunChecksParameters
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = RunChecksParameters(
		check_search_filters=CheckSearchFilters(
			column='sample_column',
			column_data_type='string',
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		dummy_execution=False
	)
	
	call_result = run_checks.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        RunChecksQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=RunChecksResult(
				highest_severity=RuleSeverityLevel.ERROR,
				executed_checks=10,
				valid_results=7,
				warnings=1,
				errors=2,
				fatals=0,
				execution_errors=0
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import run_checks
	from dqops.client.models import CheckSearchFilters, \
	                                RunChecksParameters
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = RunChecksParameters(
		check_search_filters=CheckSearchFilters(
			column='sample_column',
			column_data_type='string',
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		dummy_execution=False
	)
	
	call_result = await run_checks.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        RunChecksQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=RunChecksResult(
				highest_severity=RuleSeverityLevel.ERROR,
				executed_checks=10,
				valid_results=7,
				warnings=1,
				errors=2,
				fatals=0,
				execution_errors=0
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    



___
## start_cron_scheduler
Starts the job scheduler that runs monitoring jobs that are scheduled by assigning cron expressions.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/start_cron_scheduler.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/jobs/scheduler/status/start
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`spring_error_payload`](../models/jobs.md#springerrorpayload)</span>||*[SpringErrorPayload](../models/jobs.md#springerrorpayload)*|








**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/jobs/scheduler/status/start^
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
	from dqops.client.api.jobs import start_cron_scheduler
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = start_cron_scheduler.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        SpringErrorPayload()
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import start_cron_scheduler
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await start_cron_scheduler.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        SpringErrorPayload()
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import start_cron_scheduler
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = start_cron_scheduler.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        SpringErrorPayload()
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import start_cron_scheduler
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await start_cron_scheduler.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        SpringErrorPayload()
        ```
    
    
    



___
## stop_cron_scheduler
Stops the job scheduler that runs monitoring jobs that are scheduled by assigning cron expressions.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/stop_cron_scheduler.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/jobs/scheduler/status/stop
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`spring_error_payload`](../models/jobs.md#springerrorpayload)</span>||*[SpringErrorPayload](../models/jobs.md#springerrorpayload)*|








**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/jobs/scheduler/status/stop^
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
	from dqops.client.api.jobs import stop_cron_scheduler
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = stop_cron_scheduler.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        SpringErrorPayload()
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import stop_cron_scheduler
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await stop_cron_scheduler.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        SpringErrorPayload()
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import stop_cron_scheduler
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = stop_cron_scheduler.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        SpringErrorPayload()
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import stop_cron_scheduler
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await stop_cron_scheduler.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        SpringErrorPayload()
        ```
    
    
    



___
## synchronize_folders
Starts multiple file synchronization jobs that will synchronize files from selected DQOps User home folders to the DQOps Cloud. The default synchronization mode is a full synchronization (upload local files, download new files from the cloud).

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/synchronize_folders.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/jobs/synchronize
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`synchronize_multiple_folders_queue_job_result`](../models/jobs.md#synchronizemultiplefoldersqueuejobresult)</span>||*[SynchronizeMultipleFoldersQueueJobResult](../models/jobs.md#synchronizemultiplefoldersqueuejobresult)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`job_business_key`</span>|Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.|*string*| |
|<span class="no-wrap-code">`wait`</span>|Wait until the synchronize multiple folders job finishes to run, the default value is true (queue a background job and wait for the job to finish, up to waitTimeout seconds)|*boolean*| |
|<span class="no-wrap-code">`wait_timeout`</span>|The wait timeout in seconds, when the wait timeout elapses and the synchronization with the DQOps Cloud is still running, only the job id is returned without the results. The default timeout is 120 seconds, but it can be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|*long*| |




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Selection of folders that should be synchronized to the DQOps Cloud|*[SynchronizeMultipleFoldersDqoQueueJobParameters](../models/jobs.md#synchronizemultiplefoldersdqoqueuejobparameters)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/jobs/synchronize^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"direction\":\"full\",\"forceRefreshNativeTables\":false,\"detectCronSchedules\":false,\"sources\":true,\"sensors\":true,\"rules\":true,\"checks\":true,\"settings\":true,\"credentials\":true,\"dictionaries\":true,\"patterns\":true,\"dataSensorReadouts\":true,\"dataCheckResults\":true,\"dataStatistics\":true,\"dataErrors\":true,\"dataIncidents\":true,\"synchronizeFolderWithLocalChanges\":false}"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "status" : "queued"
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import synchronize_folders
	from dqops.client.models import FileSynchronizationDirection, \
	                                SynchronizeMultipleFoldersDqoQueueJobParameters
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = SynchronizeMultipleFoldersDqoQueueJobParameters(
		direction=FileSynchronizationDirection.FULL,
		force_refresh_native_tables=False,
		detect_cron_schedules=False,
		sources=True,
		sensors=True,
		rules=True,
		checks=True,
		settings=True,
		credentials=True,
		dictionaries=True,
		patterns=True,
		data_sensor_readouts=True,
		data_check_results=True,
		data_statistics=True,
		data_errors=True,
		data_incidents=True,
		synchronize_folder_with_local_changes=False
	)
	
	call_result = synchronize_folders.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        SynchronizeMultipleFoldersQueueJobResult(status=DqoJobStatus.QUEUED)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import synchronize_folders
	from dqops.client.models import FileSynchronizationDirection, \
	                                SynchronizeMultipleFoldersDqoQueueJobParameters
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = SynchronizeMultipleFoldersDqoQueueJobParameters(
		direction=FileSynchronizationDirection.FULL,
		force_refresh_native_tables=False,
		detect_cron_schedules=False,
		sources=True,
		sensors=True,
		rules=True,
		checks=True,
		settings=True,
		credentials=True,
		dictionaries=True,
		patterns=True,
		data_sensor_readouts=True,
		data_check_results=True,
		data_statistics=True,
		data_errors=True,
		data_incidents=True,
		synchronize_folder_with_local_changes=False
	)
	
	call_result = await synchronize_folders.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        SynchronizeMultipleFoldersQueueJobResult(status=DqoJobStatus.QUEUED)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import synchronize_folders
	from dqops.client.models import FileSynchronizationDirection, \
	                                SynchronizeMultipleFoldersDqoQueueJobParameters
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = SynchronizeMultipleFoldersDqoQueueJobParameters(
		direction=FileSynchronizationDirection.FULL,
		force_refresh_native_tables=False,
		detect_cron_schedules=False,
		sources=True,
		sensors=True,
		rules=True,
		checks=True,
		settings=True,
		credentials=True,
		dictionaries=True,
		patterns=True,
		data_sensor_readouts=True,
		data_check_results=True,
		data_statistics=True,
		data_errors=True,
		data_incidents=True,
		synchronize_folder_with_local_changes=False
	)
	
	call_result = synchronize_folders.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        SynchronizeMultipleFoldersQueueJobResult(status=DqoJobStatus.QUEUED)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import synchronize_folders
	from dqops.client.models import FileSynchronizationDirection, \
	                                SynchronizeMultipleFoldersDqoQueueJobParameters
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = SynchronizeMultipleFoldersDqoQueueJobParameters(
		direction=FileSynchronizationDirection.FULL,
		force_refresh_native_tables=False,
		detect_cron_schedules=False,
		sources=True,
		sensors=True,
		rules=True,
		checks=True,
		settings=True,
		credentials=True,
		dictionaries=True,
		patterns=True,
		data_sensor_readouts=True,
		data_check_results=True,
		data_statistics=True,
		data_errors=True,
		data_incidents=True,
		synchronize_folder_with_local_changes=False
	)
	
	call_result = await synchronize_folders.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        SynchronizeMultipleFoldersQueueJobResult(status=DqoJobStatus.QUEUED)
        ```
    
    
    



___
## wait_for_collect_error_samples_job
Waits for a job to finish. Returns the status of a finished job or a current state of a job that is still running, but the wait timeout elapsed.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/wait_for_collect_error_samples_job.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/jobs/collecterrorsamples/{jobId}/wait
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`collect_error_samples_result`](../models/jobs.md#collecterrorsamplesresult)</span>||*[CollectErrorSamplesResult](../models/jobs.md#collecterrorsamplesresult)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`job_id`</span>|Job id, it can be a job business key assigned to the job or a job id generated by DQOps|*string*|:material-check-bold:|
|<span class="no-wrap-code">`wait_timeout`</span>|The wait timeout in seconds, when the wait timeout elapses and the job is still running, the method returns the job model that is not yet finished and has no results. The default timeout is 120 seconds, but it can be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|*long*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/jobs/collecterrorsamples/123123124324324/wait^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "jobId" : {
		    "jobId" : 123456789,
		    "createdAt" : "2007-10-11T13:42:00Z"
		  },
		  "result" : {
		    "executed_error_samplers" : 3,
		    "columns_analyzed" : 1,
		    "columns_successfully_analyzed" : 0,
		    "total_error_samplers_failed" : 1,
		    "total_error_samples_collected" : 2
		  },
		  "status" : "finished"
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import wait_for_collect_error_samples_job
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = wait_for_collect_error_samples_job.sync(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CollectErrorSamplesResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=ErrorSamplerResult(
				executed_error_samplers=3,
				columns_analyzed=1,
				columns_successfully_analyzed=0,
				total_error_samplers_failed=1,
				total_error_samples_collected=2
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import wait_for_collect_error_samples_job
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await wait_for_collect_error_samples_job.asyncio(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CollectErrorSamplesResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=ErrorSamplerResult(
				executed_error_samplers=3,
				columns_analyzed=1,
				columns_successfully_analyzed=0,
				total_error_samplers_failed=1,
				total_error_samples_collected=2
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import wait_for_collect_error_samples_job
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = wait_for_collect_error_samples_job.sync(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CollectErrorSamplesResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=ErrorSamplerResult(
				executed_error_samplers=3,
				columns_analyzed=1,
				columns_successfully_analyzed=0,
				total_error_samplers_failed=1,
				total_error_samples_collected=2
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import wait_for_collect_error_samples_job
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await wait_for_collect_error_samples_job.asyncio(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CollectErrorSamplesResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=ErrorSamplerResult(
				executed_error_samplers=3,
				columns_analyzed=1,
				columns_successfully_analyzed=0,
				total_error_samplers_failed=1,
				total_error_samples_collected=2
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    



___
## wait_for_job
Waits for a job to finish. Returns the status of a finished job or a current state of a job that is still running, but the wait timeout elapsed.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/wait_for_job.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/jobs/jobs/{jobId}/wait
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`dqo_job_history_entry_model`](../models/jobs.md#dqojobhistoryentrymodel)</span>||*[DqoJobHistoryEntryModel](../models/jobs.md#dqojobhistoryentrymodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`job_id`</span>|Job id|*string*|:material-check-bold:|
|<span class="no-wrap-code">`wait_timeout`</span>|The wait timeout in seconds, when the wait timeout elapses and the job is still running, the method returns the job model that is not yet finished and has no results. The default timeout is 120 seconds, but it can be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|*long*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/jobs/jobs/123123124324324/wait^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "status" : "finished",
		  "statusChangedAt" : "2007-10-11T13:42:00Z"
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import wait_for_job
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = wait_for_job.sync(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoJobHistoryEntryModel(
			status=DqoJobStatus.FINISHED,
			status_changed_at='2007-10-11T13:42:00Z'
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import wait_for_job
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await wait_for_job.asyncio(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoJobHistoryEntryModel(
			status=DqoJobStatus.FINISHED,
			status_changed_at='2007-10-11T13:42:00Z'
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import wait_for_job
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = wait_for_job.sync(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoJobHistoryEntryModel(
			status=DqoJobStatus.FINISHED,
			status_changed_at='2007-10-11T13:42:00Z'
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import wait_for_job
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await wait_for_job.asyncio(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoJobHistoryEntryModel(
			status=DqoJobStatus.FINISHED,
			status_changed_at='2007-10-11T13:42:00Z'
		)
        ```
    
    
    



___
## wait_for_run_checks_job
Waits for a job to finish. Returns the status of a finished job or a current state of a job that is still running, but the wait timeout elapsed.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/wait_for_run_checks_job.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/jobs/runchecks/{jobId}/wait
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`run_checks_queue_job_result`](../models/jobs.md#runchecksqueuejobresult)</span>||*[RunChecksQueueJobResult](../models/jobs.md#runchecksqueuejobresult)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`job_id`</span>|Job id, it can be a job business key assigned to the job or a job id generated by DQOps|*string*|:material-check-bold:|
|<span class="no-wrap-code">`wait_timeout`</span>|The wait timeout in seconds, when the wait timeout elapses and the job is still running, the method returns the job model that is not yet finished and has no results. The default timeout is 120 seconds, but it can be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|*long*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/jobs/runchecks/123123124324324/wait^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "jobId" : {
		    "jobId" : 123456789,
		    "createdAt" : "2007-10-11T13:42:00Z"
		  },
		  "result" : {
		    "highest_severity" : "error",
		    "executed_checks" : 10,
		    "valid_results" : 7,
		    "warnings" : 1,
		    "errors" : 2,
		    "fatals" : 0,
		    "execution_errors" : 0
		  },
		  "status" : "finished"
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import wait_for_run_checks_job
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = wait_for_run_checks_job.sync(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        RunChecksQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=RunChecksResult(
				highest_severity=RuleSeverityLevel.ERROR,
				executed_checks=10,
				valid_results=7,
				warnings=1,
				errors=2,
				fatals=0,
				execution_errors=0
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import wait_for_run_checks_job
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await wait_for_run_checks_job.asyncio(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        RunChecksQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=RunChecksResult(
				highest_severity=RuleSeverityLevel.ERROR,
				executed_checks=10,
				valid_results=7,
				warnings=1,
				errors=2,
				fatals=0,
				execution_errors=0
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import wait_for_run_checks_job
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = wait_for_run_checks_job.sync(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        RunChecksQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=RunChecksResult(
				highest_severity=RuleSeverityLevel.ERROR,
				executed_checks=10,
				valid_results=7,
				warnings=1,
				errors=2,
				fatals=0,
				execution_errors=0
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.jobs import wait_for_run_checks_job
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await wait_for_run_checks_job.asyncio(
	    '123123124324324',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        RunChecksQueueJobResult(
			job_id=DqoQueueJobId(
				job_id=123456789,
				created_at='2007-10-11T13:42:00Z'
			),
			result=RunChecksResult(
				highest_severity=RuleSeverityLevel.ERROR,
				executed_checks=10,
				valid_results=7,
				warnings=1,
				errors=2,
				fatals=0,
				execution_errors=0
			),
			status=DqoJobStatus.FINISHED
		)
        ```
    
    
    



