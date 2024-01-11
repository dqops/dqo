Log shipping controller that accepts logs sent from a web application or external tools and aggregates them in the local DQOps instance logs.


___
## log_debug
Logs an information message in the server&#x27;s logs as a debug severity log entry.
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/log_shipping/log_debug.py)


**POST**
```
http://localhost:8888/api/logs/debug
```





**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Log entry|[ExternalLogEntry](../models/log_shipping.md#externallogentry)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X POST http://localhost:8888/api/logs/debug^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"window_location\":\"window.location\",\"message\":\"Sample log message.\"}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.log_shipping import log_debug
	from dqops.client.models import ExternalLogEntry
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ExternalLogEntry(
		window_location='window.location',
		message='Sample log message.'
	)
	
	log_debug.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.log_shipping import log_debug
	from dqops.client.models import ExternalLogEntry
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ExternalLogEntry(
		window_location='window.location',
		message='Sample log message.'
	)
	
	async_result = log_debug.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.log_shipping import log_debug
	from dqops.client.models import ExternalLogEntry
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ExternalLogEntry(
		window_location='window.location',
		message='Sample log message.'
	)
	
	log_debug.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.log_shipping import log_debug
	from dqops.client.models import ExternalLogEntry
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ExternalLogEntry(
		window_location='window.location',
		message='Sample log message.'
	)
	
	async_result = log_debug.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





___
## log_error
Logs an information message in the server&#x27;s logs as an error severity log entry.
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/log_shipping/log_error.py)


**POST**
```
http://localhost:8888/api/logs/error
```





**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Log entry|[ExternalLogEntry](../models/log_shipping.md#externallogentry)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X POST http://localhost:8888/api/logs/error^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"window_location\":\"window.location\",\"message\":\"Sample log message.\"}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.log_shipping import log_error
	from dqops.client.models import ExternalLogEntry
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ExternalLogEntry(
		window_location='window.location',
		message='Sample log message.'
	)
	
	log_error.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.log_shipping import log_error
	from dqops.client.models import ExternalLogEntry
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ExternalLogEntry(
		window_location='window.location',
		message='Sample log message.'
	)
	
	async_result = log_error.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.log_shipping import log_error
	from dqops.client.models import ExternalLogEntry
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ExternalLogEntry(
		window_location='window.location',
		message='Sample log message.'
	)
	
	log_error.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.log_shipping import log_error
	from dqops.client.models import ExternalLogEntry
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ExternalLogEntry(
		window_location='window.location',
		message='Sample log message.'
	)
	
	async_result = log_error.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





___
## log_info
Logs an information message in the server&#x27;s logs as an info severity log entry.
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/log_shipping/log_info.py)


**POST**
```
http://localhost:8888/api/logs/info
```





**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Log entry|[ExternalLogEntry](../models/log_shipping.md#externallogentry)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X POST http://localhost:8888/api/logs/info^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"window_location\":\"window.location\",\"message\":\"Sample log message.\"}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.log_shipping import log_info
	from dqops.client.models import ExternalLogEntry
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ExternalLogEntry(
		window_location='window.location',
		message='Sample log message.'
	)
	
	log_info.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.log_shipping import log_info
	from dqops.client.models import ExternalLogEntry
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ExternalLogEntry(
		window_location='window.location',
		message='Sample log message.'
	)
	
	async_result = log_info.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.log_shipping import log_info
	from dqops.client.models import ExternalLogEntry
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ExternalLogEntry(
		window_location='window.location',
		message='Sample log message.'
	)
	
	log_info.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.log_shipping import log_info
	from dqops.client.models import ExternalLogEntry
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ExternalLogEntry(
		window_location='window.location',
		message='Sample log message.'
	)
	
	async_result = log_info.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





___
## log_warn
Logs an information message in the server&#x27;s logs as a warn severity log entry.
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/log_shipping/log_warn.py)


**POST**
```
http://localhost:8888/api/logs/warn
```





**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Log entry|[ExternalLogEntry](../models/log_shipping.md#externallogentry)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X POST http://localhost:8888/api/logs/warn^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"window_location\":\"window.location\",\"message\":\"Sample log message.\"}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.log_shipping import log_warn
	from dqops.client.models import ExternalLogEntry
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ExternalLogEntry(
		window_location='window.location',
		message='Sample log message.'
	)
	
	log_warn.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.log_shipping import log_warn
	from dqops.client.models import ExternalLogEntry
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ExternalLogEntry(
		window_location='window.location',
		message='Sample log message.'
	)
	
	async_result = log_warn.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.log_shipping import log_warn
	from dqops.client.models import ExternalLogEntry
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ExternalLogEntry(
		window_location='window.location',
		message='Sample log message.'
	)
	
	log_warn.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.log_shipping import log_warn
	from dqops.client.models import ExternalLogEntry
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ExternalLogEntry(
		window_location='window.location',
		message='Sample log message.'
	)
	
	async_result = log_warn.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





