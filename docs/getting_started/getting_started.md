# Getting started

On this page we will show the most fundamental command to run data quality checks with DQO.ai.

**If you haven't installed the application yet, please follow the [set-up tutorial](set_up/set_up.md).**

## Running DQO.ai for the first time
Pip installation sets an environmental variable `dqo` in a virtual environment, which enables you to run the 
application just by running in the terminal

```
dqo
```

!!! Info
    Running DQO.ai for the first time requires you to set up a user home (DQO.ai will do it for you after receiving 
    permission). 

    User home can be put in different directories, so it matters where you initialize it.
    DQO.ai starts searching user home in a current directory. If it is not found, DQO.ai will continue search in parent
    directories.

[//]: # ()
[//]: # (## Running examples)

[//]: # (Before using the application, we recommend checking out the [examples]&#40;../examples/running_examples.md&#41;.)

## Adding connections

Working connection is a fundamental aspect of running data quality checks. Here you can find the 
detailed documentation for
[managing connections](../commands/connection/connection.md).

All the connections require two things specified:

- connection name,
- provider.

After running command
```
connection add
```
DQO will automatically ask you to provide a connection's name and select a provider. You can specify them in the
command itself.

After connection is added, you can display the list of existing connections by running
```
connection list
```

## Importing tables
To access table configuration, you need to first [import](../commands/table/table.md#import) them:

```
table import -c=<connection_name>
```

Just like connections, you can list imported tables. Here is command to list tables from newly added connection

```
table list -c=<connection_name>
```

## Editing tables

then you will be able to [edit](../commands/table/table.md#edit) them where you can define checks. Run the following command
```
table edit -c=<connection_name> -t=<schema_name>.<table_name>
```

an editor should open, where you can define data quality checks 
[list of data quality checks](../check_reference/list_of_checks/list_of_checks.md).

## Running examples

If you downloaded DQO.ai from GitHub you can find ready-to-run examples. You can see 
[here](../examples/running_examples.md) how to run them.

However, if you installed DQO.ai via pip, you won't find them. You can clone them separately

## Workflow example
Here is an example showing how to perform discussed steps on a real dataset, which you can find on 
`bigquery-public=data` project on GCP.

!!! Info "Prerequisites"
    Because this example uses a dataset from BigQuery, you have to download and install Google Cloud SDK.
    After installing [Google Cloud CLI](https://cloud.google.com/sdk/docs/install), log in to your GCP account 
    (you can start one for free), by running:
    ```
    gcloud auth application-default login
    ```
    After setting up your GCP account, create your GCP project. That will be your GCP billing project used to run SQL
    sensors on the public datasets provided by Google.

The used table is `bigquery-public-data.austin_311.311_service_requests`, here are first 5 rows of `unique_key` column
on which a data uniqueness check will be performed

```
+-------------+
| unique_key  |
+-------------+
| 22-00171701 |
| 22-00171642 |
| 22-00171478 |
| 22-00171047 |
| 22-00170638 |
+-------------+
```

### Connection
There is a couple of things you have to specify to successfully establish a connection with BigQuery. In the following
example we use our project `dqo-ai-testing`, anywhere this name appears, change it to your project ID:

- connection name
- provider name
- source GCP project ID, so in this case `bigquery-public-data`
- billing project ID
- authentication mode (we recommend using `google_application_credentials`) 
- quota project ID

``` 
dqo.ai> connection add --name=connection_1 --provider=bigquery
Source GCP project ID (-P=bigquery-source-project-id) [dqo-ai-tesing]: bigquery-public-data
Billing GCP project ID (-P=bigquery-billing-project-id) [dqo-ai-testing]: dqo-ai-testing
GCP Authentication Mode [google_application_credentials]:
 [ 1] google_application_credentials (default)
 [ 2] json_key_content
 [ 3] json_key_path
Please enter one of the [] values: 1
GCP quota (billing) project ID (-P=bigquery-quota-project-id) [dqo-ai-testing]:
Connection connection_1 was successfully added.
Run 'table import -c=connection_1' to import tables.
```

Here is the whole command in a headless mode that enables you to add connection at once:

```
connection add --name=connection_1 --provider=bigquery -P=bigquery-source-project-id=bigquery-public-data -P=bigquery-billing-project-id=dqo-ai-testing -P=bigquery-quota-project-id=dqo-ai-testing -P=bigquery-authentication-mode=google_application_credentials
```

You can list your connections.

```
dqo.ai> connection list
+-------------------+---------------+---------------+--------+----------------------+
|Hash Id            |Connection Name|Connection Type|JDBC Url|Physical Database Name|
+-------------------+---------------+---------------+--------+----------------------+
|1846543424268075406|connection_1   |bigquery       |        |                      |
+-------------------+---------------+---------------+--------+----------------------+
```

### Table import

There are 192 datasets on `bigquery-public-data` at the moment of writing this page. We will use the first dataset,
which contains only one table `311_service_requests`.

```
dqo.ai> table import --connection=connection_1
Select the schema (database, etc.) from which tables will be imported:
  [  1]  austin_311
  [  2]  austin_bikeshare
  [  3]  austin_crime
...
  [191]  world_bank_wdi
  [192]  worldpop
Please enter one of the [] values: 1
───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────
The following tables were imported:
+-----------+--------------------+------------+
|Schema name|Table name          |Column count|
+-----------+--------------------+------------+
|austin_311 |311_service_requests|22          |
+-----------+--------------------+------------+

```

### Table edit
To open up an editor with table configuration use `table edit` command with specified connection and table name
```
dqo.ai> table edit --connection=connection_1 --table=austin_311.311_service_requests
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```

### Setting up check
Here is a sample YAML table configuration: 
```yaml linenums="1" hl_lines="16-26"
--8<-- "docs/getting_started/yamls/example.yaml"
```
highlighted lines define used sensor along with `min_count` rule. Here is rendered query that is sent to BigQuery.

```SQL
{{ process_template_request( get_request( "docs/getting_started/requests/example.json" ) ) }}
```

To run checks on this specific table, you have to provide connection name, table name (including schema name) in 
`check run` command.

```
check run --conncetion=connection_1 --table=austin_311.311_service_requests
```

To run this specific check you can specify the name of the check, and the column name: 

```
check run --conncetion=connection_1 --table=austin_311.311_service_requests --check=distinct_count_percent --column=unique_key
```
