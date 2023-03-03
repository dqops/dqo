This section shows how to connect to the data source and explains YAML file with the connection specification.

### Connection
In order to successfully establish a connection with BigQuery couple of things need to be specified:

- connection name
- provider name
- source GCP project ID
- billing project ID
- authentication mode (we recommend using `google_application_credentials`)
- quota project ID

In the following example we use our project `dqo-ai-testing`, where

- connection name = `connection_1`
- provider name = `bigquery`
- source GCP project ID = `bigquery-public-data`
- billing project ID = `dqo-ai-testing`
- authentication mode = `google_application_credentials`
- quota project ID = `dqo-ai-testing`

To add a connection start by running following command:
```
connection add
```

and follow the instruction

``` 
dqo.ai> dqo.ai> connection add
Connection name (--name): connection_1
Database provider type (--provider):
 [ 1] bigquery
 [ 2] snowflake
Please enter one of the [] values: 1
Source GCP project ID (-P=bigquery-source-project-id) [dqo-ai]: bigquery-public-data
Billing GCP project ID (-P=bigquery-billing-project-id) [dqo-ai]: dqo-ai-testing
GCP Authentication Mode [google_application_credentials]:
 [ 1] google_application_credentials (default)
 [ 2] json_key_content
 [ 3] json_key_path
Please enter one of the [] values: 1
GCP quota (billing) project ID (-P=bigquery-quota-project-id) [dqo-ai-testing]: dqo-ai-testing
Connection connection_1 was successfully added.
Run 'table import -c=connection_1' to import tables.
dqo.ai>
```

Here is the whole command in a headless mode that enables adding a connection at once:

```
connection add --name=connection_1 --provider=bigquery -P=bigquery-source-project-id=bigquery-public-data -P=bigquery-billing-project-id=dqo-ai-testing -P=bigquery-quota-project-id=dqo-ai-testing -P=bigquery-authentication-mode=google_application_credentials
```

To see the list of all connections use the command :

```
connection list
```

```
dqo.ai> connection list
+-------------------+---------------+---------------+--------+----------------------+
|Hash Id            |Connection Name|Connection Type|JDBC Url|Physical Database Name|
+-------------------+---------------+---------------+--------+----------------------+
|1846543424268075406|connection_1   |bigquery       |        |                      |
+-------------------+---------------+---------------+--------+----------------------+
```

### Connection YAML file 
Here is a sample connection YAML file configuration:
```yaml linenums="1" hl_lines="16-26"
--8<-- "docs/getting_started/yamls/connection_yaml_file_example.yaml"
```

This file contains information about the configuration of the connection that was created.
The file is completed with the information that was provided when adding a new connection.
It contains information about the provider , the source of the GCP project ID , the GCP project ID for billing and the authentication mode on the GCP.