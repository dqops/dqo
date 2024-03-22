# DQOps REST API dictionaries operations
Operations for managing data dictionary CSV files in DQOps. Data dictionaries can be used in *accepted_values* data quality checks.


___
## create_dictionary
Creates (adds) a new data dictionary CSV file, which creates a file in the DQOps user&#x27;s home dictionaries/ folder named as the dictionary and with the content that is provided in this call.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dictionaries/create_dictionary.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/dictionaries
```





**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Data dictionary model|*[DataDictionaryModel](../models/dictionaries.md#datadictionarymodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/dictionaries^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"dictionary_name\":\"sample_dictionary\",\"file_content\":\"USD\nEUR\nGBP\nAUD\nCHF\n\"}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import create_dictionary
	from dqops.client.models import DataDictionaryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DataDictionaryModel(
		dictionary_name='sample_dictionary',
		file_content='USD
		EUR
		GBP
		AUD
		CHF
		'
	)
	
	call_result = create_dictionary.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import create_dictionary
	from dqops.client.models import DataDictionaryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DataDictionaryModel(
		dictionary_name='sample_dictionary',
		file_content='USD
		EUR
		GBP
		AUD
		CHF
		'
	)
	
	call_result = await create_dictionary.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import create_dictionary
	from dqops.client.models import DataDictionaryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DataDictionaryModel(
		dictionary_name='sample_dictionary',
		file_content='USD
		EUR
		GBP
		AUD
		CHF
		'
	)
	
	call_result = create_dictionary.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import create_dictionary
	from dqops.client.models import DataDictionaryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DataDictionaryModel(
		dictionary_name='sample_dictionary',
		file_content='USD
		EUR
		GBP
		AUD
		CHF
		'
	)
	
	call_result = await create_dictionary.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## delete_dictionary
Deletes a data dictionary CSV file from the DQOps user&#x27;s home dictionaries/ folder.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dictionaries/delete_dictionary.py) to see the source code on GitHub.


**DELETE**
```
http://localhost:8888/api/dictionaries/{dictionaryName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`dictionary_name`</span>|Data dictionary name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X DELETE http://localhost:8888/api/dictionaries/status_codes.csv^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import delete_dictionary
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = delete_dictionary.sync(
	    'status_codes.csv',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import delete_dictionary
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await delete_dictionary.asyncio(
	    'status_codes.csv',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import delete_dictionary
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = delete_dictionary.sync(
	    'status_codes.csv',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import delete_dictionary
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await delete_dictionary.asyncio(
	    'status_codes.csv',
	    client=dqops_client
	)
	
    ```

    



___
## download_dictionary
Downloads a data dictionary CSV file

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dictionaries/download_dictionary.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/dictionaries/{dictionaryName}/download
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`dictionary_name`</span>|Data dictionary CSV file name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/dictionaries/status_codes.csv/download^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import download_dictionary
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = download_dictionary.sync(
	    'status_codes.csv',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import download_dictionary
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await download_dictionary.asyncio(
	    'status_codes.csv',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import download_dictionary
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = download_dictionary.sync(
	    'status_codes.csv',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import download_dictionary
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await download_dictionary.asyncio(
	    'status_codes.csv',
	    client=dqops_client
	)
	
    ```

    



___
## get_all_dictionaries
Returns a list of all data dictionary CSV files that are present in the DQOps user&#x27;s home dictionaries/ folder.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dictionaries/get_all_dictionaries.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/dictionaries
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`data_dictionary_list_model`</span>||*List[[DataDictionaryListModel](../models/dictionaries.md#datadictionarylistmodel)]*|








**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/dictionaries^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "can_edit" : false,
		  "can_access_dictionary" : false
		}, {
		  "can_edit" : false,
		  "can_access_dictionary" : false
		}, {
		  "can_edit" : false,
		  "can_access_dictionary" : false
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import get_all_dictionaries
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_dictionaries.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			DataDictionaryListModel(
				can_edit=False,
				can_access_dictionary=False
			),
			DataDictionaryListModel(
				can_edit=False,
				can_access_dictionary=False
			),
			DataDictionaryListModel(
				can_edit=False,
				can_access_dictionary=False
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import get_all_dictionaries
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_dictionaries.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			DataDictionaryListModel(
				can_edit=False,
				can_access_dictionary=False
			),
			DataDictionaryListModel(
				can_edit=False,
				can_access_dictionary=False
			),
			DataDictionaryListModel(
				can_edit=False,
				can_access_dictionary=False
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import get_all_dictionaries
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_dictionaries.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			DataDictionaryListModel(
				can_edit=False,
				can_access_dictionary=False
			),
			DataDictionaryListModel(
				can_edit=False,
				can_access_dictionary=False
			),
			DataDictionaryListModel(
				can_edit=False,
				can_access_dictionary=False
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import get_all_dictionaries
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_dictionaries.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			DataDictionaryListModel(
				can_edit=False,
				can_access_dictionary=False
			),
			DataDictionaryListModel(
				can_edit=False,
				can_access_dictionary=False
			),
			DataDictionaryListModel(
				can_edit=False,
				can_access_dictionary=False
			)
		]
        ```
    
    
    



___
## get_dictionary
Returns the content of a data dictionary CSV file as a model object

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dictionaries/get_dictionary.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/dictionaries/{dictionaryName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`data_dictionary_model`](../models/dictionaries.md#datadictionarymodel)</span>||*[DataDictionaryModel](../models/dictionaries.md#datadictionarymodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`dictionary_name`</span>|Data dictionary CSV file name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/dictionaries/status_codes.csv^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "dictionary_name" : "sample_dictionary",
		  "file_content" : "USD\nEUR\nGBP\nAUD\nCHF\n"
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import get_dictionary
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_dictionary.sync(
	    'status_codes.csv',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DataDictionaryModel(
			dictionary_name='sample_dictionary',
			file_content='USD
			EUR
			GBP
			AUD
			CHF
			'
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import get_dictionary
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_dictionary.asyncio(
	    'status_codes.csv',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DataDictionaryModel(
			dictionary_name='sample_dictionary',
			file_content='USD
			EUR
			GBP
			AUD
			CHF
			'
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import get_dictionary
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_dictionary.sync(
	    'status_codes.csv',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DataDictionaryModel(
			dictionary_name='sample_dictionary',
			file_content='USD
			EUR
			GBP
			AUD
			CHF
			'
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import get_dictionary
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_dictionary.asyncio(
	    'status_codes.csv',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DataDictionaryModel(
			dictionary_name='sample_dictionary',
			file_content='USD
			EUR
			GBP
			AUD
			CHF
			'
		)
        ```
    
    
    



___
## update_dictionary
Updates an existing data dictionary CSV file, replacing the dictionary&#x27;s file content.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dictionaries/update_dictionary.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/dictionaries/{dictionaryName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`dictionary_name`</span>|Data dictionary file name that will be updated|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Data dictionary model|*[DataDictionaryModel](../models/dictionaries.md#datadictionarymodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/dictionaries/status_codes.csv^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"dictionary_name\":\"sample_dictionary\",\"file_content\":\"USD\nEUR\nGBP\nAUD\nCHF\n\"}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import update_dictionary
	from dqops.client.models import DataDictionaryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DataDictionaryModel(
		dictionary_name='sample_dictionary',
		file_content='USD
		EUR
		GBP
		AUD
		CHF
		'
	)
	
	call_result = update_dictionary.sync(
	    'status_codes.csv',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import update_dictionary
	from dqops.client.models import DataDictionaryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DataDictionaryModel(
		dictionary_name='sample_dictionary',
		file_content='USD
		EUR
		GBP
		AUD
		CHF
		'
	)
	
	call_result = await update_dictionary.asyncio(
	    'status_codes.csv',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import update_dictionary
	from dqops.client.models import DataDictionaryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DataDictionaryModel(
		dictionary_name='sample_dictionary',
		file_content='USD
		EUR
		GBP
		AUD
		CHF
		'
	)
	
	call_result = update_dictionary.sync(
	    'status_codes.csv',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dictionaries import update_dictionary
	from dqops.client.models import DataDictionaryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DataDictionaryModel(
		dictionary_name='sample_dictionary',
		file_content='USD
		EUR
		GBP
		AUD
		CHF
		'
	)
	
	call_result = await update_dictionary.asyncio(
	    'status_codes.csv',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



