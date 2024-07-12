---
title: How to activate data observability for Azure
---

# How to activate data observability for Azure

This guide shows how to activate data observability for Azure by connecting DQOps. 
The example will use the Azure Blob Storage for storing data. 

## Prerequisites

- Data in CSV, JSON or Parquet format (compressed files allowed), located in a Storage Container in your Storage Account.
- [DQOps installation](../getting-started/installation.md)

## Add connection to Azure using the user interface

### **Navigate to the connection settings**

To navigate to the Azure connection settings:

1. Go to the Data Sources section and click the **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png){ loading=lazy; width="1200px" }

2. Select the DuckDB connection.

    ![Selecting DuckDB connection type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-duckdb.png){ loading=lazy; width="1200px" }


### **Fill in the connection settings**

After navigating to the Azure connection settings, you will need to fill in its details.

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-json.png){ loading=lazy; width="1200px" }

Fill the **Connection name** any name you want.

Change the **Files location** to **Azure Blob Storage**, to work with files located in Azure.

Select the **File Format** suitable to your files located in Azure. You can choose from CSV, JSON or Parquet file format.

To complete the configuration you need to set the:

- **Azure authentication mode**
- **Path**


## Choose the Azure authentication mode

DQOps requires permissions to establish the connection to the Azure storage.

You can choose from a variety of authentication methods that will allow to connect to your data:

- Connection String
- Credential Chain
- Service Principal
- Default Credential

Below you can find how to get credentials for each of the authentication methods.

### **Connection String**

The connection string is created on the Storage Account level. 
It allows access to all files in each of the Storage Containers created in the Storage Account.

You can find the connection string in the Storage Account details. 
Open the Storage Account menu section in Azure Portal. Select the **Security + networking**, then **Access keys**.

![Connection string](https://dqops.com/docs/images/data-sources/azure/connection-string.png){ loading=lazy; }


### **Credential Chain** 

The credential chain uses environment variables and accounts stored locally used for applications running locally.
That is why it will work on local DQOps instance only.

You can sign in interactively to Azure with use of Azure CLI command: **az login**

After you succeed with the command **restart the DQOps** process allowing it to load the fresh account credentials.


### **Service Principal** 

This is the recommended authentication method.

The service principal is an impersonalized identity used specifically for a service with a proper permission.

This method requires creating a service account, generating a secret and adding role assignment to the container.

Start with creating a service account in Azure.
Open **Enterprise applications** and click the **New application**.

![New enterprise application](https://dqops.com/docs/images/data-sources/azure/new-enterprise-application.png){ loading=lazy; }

Then **Create your own application**.

![New your own enterprise application](https://dqops.com/docs/images/data-sources/azure/new-enterprise-application-your-own.png){ loading=lazy; }

Fill the name with your service account and create it.

![Create your own application](https://dqops.com/docs/images/data-sources/azure/on-right-create-your-own-application.png){ loading=lazy; }

Now the service account is ready but it does not have any credentials available to be used.

To create credentials open the **App registrations** in Azure Entra ID. 
Select **All applications**, then select the name of the service account.

![App registration](https://dqops.com/docs/images/data-sources/azure/app-registrations.png){ loading=lazy; }

Then navigate to **Certificates & secrets** and click the **New client secret**

![App registration](https://dqops.com/docs/images/data-sources/azure/create-new-client-secret.png){ loading=lazy; }

Then fill the name of a new client secret and create it.

Now the secret is ready. Save the **Value** of the key, which is your **Client Secret**.

![App registration](https://dqops.com/docs/images/data-sources/azure/client-secret.png){ loading=lazy; }

The last thing to be done is to add the permission of your service account to the storage account.

Open the container you will work with and select the **Access Control (IAM)**.
Click on **Add** and select the **Add role assignment**.

![App registration](https://dqops.com/docs/images/data-sources/azure/add-iam.png){ loading=lazy; }

In Role tab, search for **Storage Blob Data Reader** and click on the present role below.
The role adds read permissions to the Storage Container.

![App registration](https://dqops.com/docs/images/data-sources/azure/add-iam-role.png){ loading=lazy; }

In Members tab, click on the **Select members** and type the name of the service account, then click Enter.

The name of the service account will appear when the full name is typed.

Select it and click Select.

![App registration](https://dqops.com/docs/images/data-sources/azure/add-iam-member.png){ loading=lazy; }

To add a connection in DQOps with use of Service Principal authentication mode you need the following:

- Storage Account Name
- Tenant ID
- Client ID
- Client Secret

The **Client Secret** you saved.

Tenant ID and Client ID are available in the App registrations Overview section of the Azure Entra ID.

![App registration](https://dqops.com/docs/images/data-sources/azure/credentials.png){ loading=lazy; }


### **Default Credential** 

With DQOps, you can configure credentials to access Azure Blob Storage directly in the platform.

Please note, that any credentials and secrets shared with the DQOps Cloud or DQOps SaaS instances are stored in the .credentials folder.
This folder also contains the default credentials files for Azure Blob Storage (**Azure_default_credentials**).

``` { .asc .annotate hl_lines="4" }
$DQO_USER_HOME
├───...
└───.credentials       
    ├───Azure_default_credentials
    └─...   
```

If you wish to use Azure authentication, you need service principal credentials that must be replaced in Azure file content.

To set the credential file for Azure in DQOps, follow steps:

1. Open the Configuration in menu.
2. Select Shared credentials from the tree view on the left.
3. Click the edit link on the “Azure_default_credentials” file.

![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/credentials/azure-shared-credentials-ui2.png)

4. In the text area, edit the tenant_id, client_id, client_secret and account_name, replacing the placeholder text.

![Edit connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/credentials/edit-azure-shared-credential2.png)

5. Click the **Save** button, to save changes, go back to the main **Shared credentials** view.


## Set the Path

Let assume you have directories with unstructured files, dataset divided into multiple files with the same structure - e.g. same header or partitioned data.
All mentioned cases are supported but differs in the configuration. 

``` { .asc .annotate }
my-container
├───...
└───clients_data
    ├───reports
    │   ├───annual_report_2022.csv(1)
    │   ├───annual_report_2023.parquet
    │   ├───market_dictionary.json
    │   └───...
    ├───market_specification(2)
    │   ├───US.csv
    │   ├───Canada.csv
    │   ├───Germany.csv
    │   └───...
    └───sales(3)
        ├───d=2024-01
        │   ├───m=US
        │   ├───m=CA
        │   ├───m=GE
        │   ├───m=YP
        │   └───...
        ├───d=2024-02
        ├───d=2024-03
        └───...     
```

1.  Connect to a specific file - e.g. annual_report_2022.csv by setting prefix to **/my_container/clients_data/reports**. A selection of the file is available after saving the new connection configuration.
2.  Connect to all files in path - e.g. whole market_specification folder by setting prefix to **/my_container/clients_data**. A selection of the folder is available after saving the new connection configuration.
3.  Connect to partitioned data - e.g. sales folder with partitioning by date and market - set prefix to **/my_container/clients_data** and select **Hive partitioning** checkbox from Additional format options. A selection of the **sales** folder is available after saving the new connection configuration.

You can connect to a specific file, e.g. annual_report_2022.csv (set prefix to **/usr/share/clients_data/reports**),
all files with the same structure in path, e.g. whole market_specification folder (set prefix to **/usr/share/clients_data**) 
or hive style partitioned data, e.g. sales folder with partitioning by date and market - (set prefix to **/usr/share/clients_data** and select **Hive partitioning** checkbox from Additional format options).

The path is a directory containing files. You cannot use a full file path. 
The prefix cannot contain the name of a file.

A selection of files or directories is available **after Saving the new connection**.

## Import metadata using the user interface

When you add a new connection, it will appear in the tree view on the left, and you will be redirected to the Import Metadata screen.
Now we can import files.

1. Import the selected virtual schemas by clicking on the **Import Tables** button next to the source schema name from which you want to import tables.

    ![Importing schemas](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/importing-schemas.png){ loading=lazy; width="1200px" }

2. Select the tables (folders with files of previously selected file format or just the files) you want to import or import all tables using the buttons in the upper right corner.

    ![Importing tables](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/importing-tables-csv.png){ loading=lazy; width="1200px" }

When new tables are imported, DQOps automatically activates profiling and monitoring checks, such as row count,
table availability, and checks detecting schema changes. These checks are scheduled to run daily at 12:00 p.m.
By clicking on the Advisor at the top of the page, you can quickly collect basic statistics, run profiling checks,
or modify the schedule for newly imported tables.

![Importing tables - advisor](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/importing-tables-advisor-csv.png){ loading=lazy; width="1200px" }


## Detailed parameters description of new connection

The form of the adding a new connection page provides additional fields not mentioned before.

| File connection settings  | Property name in YAML configuration file | Description                                                                                                                                                                                                                                  | 
|---------------------------|------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Connection name           |                                          | The name of the connection that will be created in DQOps. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters.    |
| Parallel jobs limit       |                                          | A limit on the number of jobs that can run simultaneously. Leave empty to disable the limit.                                                                                                                                                 |
| Files location            | `storage_type`                           | You have the option to import files stored locally or remotely at AWS S3, Azure Blob Storage or Google Cloud Storage.                                                                                                                        |
| File format               | `files_format_type`                      | Type of source files for DuckDB.                                                                                                                                                                                                             |
| Azure authentication mode | `azure_authentication_mode`              | (Available when using Azure Blob Storage files location) Authentication mode to Azure Blob Storage. Supports also a ${DUCKDB_AZURE_AUTHENTICATION_MODE} configuration with a custom environment variable.                                    |
| Connection string         | `password`                               | (Available when using Azure Blob Storage files location with Connection String authentication mode) Connection string to the Azure Storage Account. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution. |
| Tenant ID                 | `tenant_id`                              | (Available when using Azure Blob Storage files location with Service Principal authentication mode) Tenant ID. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                      |
| Client ID                 | `client_id`                              | (Available when using Azure Blob Storage files location with Service Principal authentication mode) Client ID. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                      |
| Client Secret             | `client_secret`                          | (Available when using Azure Blob Storage files location with Service Principal authentication mode) Client Secret. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                  |
| Storage account name      | `storage_account_name`                   | (Available when using Azure Blob Storage files location with Credential Chain or Service Principal authentication mode) Storage account name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.       |
| Virtual schema name       | `directories`                            | An alias for the parent directory with data. The virtual schema name is a key of the directories mapping.                                                                                                                                    |
| Path                      | `directories`                            | The path prefix to the parent directory with data. The path must be absolute. The virtual schema name is a value of the directories mapping.                                                                                                 |
| JDBC connection property  |                                          | Optional setting. DQOps supports using the JDBC driver to access DuckDB.                                                                                                                                                                     |


The next configuration depends on the file format. You can choose from the three of them:

- CSV
- JSON
- Parquet


### Additional CSV format options

CSV file format properties are detected automatically based on a sample of the file data.
The default sample size is 20480 rows.

In **case of invalid import** of the data, expand the **Additional CSV format options** panel with file format options by clicking on it in UI.

The following properties can be configured for a very specific CSV format.

| Additional CSV format options | Property name in YAML configuration file | Description                                                                                                                                                                                   |
|-------------------------------|------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Compression                   | `compression`                            | The compression type for the file. By default, this will be detected automatically from the file extension (e.g., t.csv.gz will use gzip, t.csv will use none). Options are none, gzip, zstd. | 
| Date format                   | `dateformat`                             | Specifies the date format used when parsing dates.                                                                                                                                            | 
| Decimal separator             | `decimal_separator`                      | The decimal separator of numbers.                                                                                                                                                             | 
| Delimiter                     | `delim`                                  | Specifies a string separating the columns in each row (line) of the file.                                                                                                                     | 
| Escape character/string       | `escape`                                 | Specifies the string that should appear before the data character sequence that matches the quote value.                                                                                      | 
| New line                      | `new_line`                               | Set the new line character(s) in the file. Options are '\\r','\\n', or '\\r\\n'.                                                                                                              | 
| Quote                         | `quote`                                  | Specifies the quoting string to be used when a data value is quoted.                                                                                                                          | 
| Sample size                   | `sample_size`                            | The number of sample rows for automatic parameter detection.                                                                                                                                  | 
| Skip                          | `skip`                                   | The number of lines at the beginning of the file to be skipped.                                                                                                                               | 
| Timestamp format              | `timestampformat`                        | Specifies the date format used when parsing timestamps.                                                                                                                                       | 
| Treat all columns as varchar  | `all_varchar`                            | An option to skip type detection during CSV parsing and assume that all columns are of VARCHAR type.                                                                                          | 
| Allow quoted nulls            | `allow_quoted_nulls`                     | An option to allow the conversion of quoted values to NULL values.                                                                                                                            | 
| Filename                      | `filename`                               | Specifies whether an additional file name column should be included in the result.                                                                                                            | 
| Header                        | `header`                                 | Specifies that the file contains a header line with the names of each column in the file.                                                                                                     | 
| Hive partitioning             | `hive_partitioning`                      | Specifies whether to interpret the path as a hive-partitioned path.                                                                                                                           | 
| Ignore errors                 | `ignore_errors`                          | An option to ignore any parsing errors encountered - and instead ignore rows with errors.                                                                                                     | 
| Auto detect                   | `auto_detect`                            | (Not available in UI) Enables auto-detection of CSV parameters. Default is true                                                                                                               | 


### Additional JSON format options

JSON file format properties are detected automatically based on a sample of the file data.
The default sample size is 20480 rows.

In **case of invalid import** of the data, expand the **Additional JSON format options** panel with file format options by clicking on it in UI.

The following properties can be configured for a very specific JSON format.

| Additional JSON format options | Property name in YAML configuration file | Description                                                                                                                                                                                     |
|--------------------------------|------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Compression                    | `compression`                            | The compression type for the file. By default, this will be detected automatically from the file extension (e.g., t.json.gz will use gzip, t.json will use none). Options are none, gzip, zstd. | 
| Date format                    | `dateformat`                             | Specifies the date format used when parsing dates.                                                                                                                                              | 
| Json Format                    | `format`                                 | Json format. Can be one of \['auto', 'unstructured', 'newline_delimited', 'array'\].                                                                                                            | 
| Maximum depth                  | `maximum_depth`                          | Maximum nesting depth to which the automatic schema detection detects types. Set to -1 to fully detect nested JSON types.                                                                       | 
| Maximum object size            | `maximum_object_size`                    | The maximum size of a JSON object (in bytes).                                                                                                                                                   | 
| Records                        | `records`                                | Can be one of ['auto', 'true', 'false'].                                                                                                                                                        | 
| Sample size                    | `sample_size`                            | The number of sample rows for automatic parameter detection.                                                                                                                                    | 
| Timestamp format               | `timestampformat`                        | Specifies the date format used when parsing timestamps.                                                                                                                                         | 
| Convert strings to integers    | `convert_strings_to_integers`            | Specifies whether strings representing integer values should be converted to a numerical type.                                                                                                  | 
| Filename                       | `filename`                               | Specifies whether an additional file name column should be included in the result.                                                                                                              | 
| Hive partitioning              | `hive_partitioning`                      | Specifies whether to interpret the path as a hive-partitioned path.                                                                                                                             | 
| Ignore errors                  | `ignore_errors`                          | An option to ignore any parsing errors encountered - and instead ignore rows with errors.                                                                                                       | 
| Auto detect                    | `auto_detect`                            | (Not available in UI) Whether to auto-detect detect the names of the keys and data types of the values automatically.                                                                           | 


### Additional Parquet format options

Click on the **Additional Parquet format options** panel to configure the file format options.

The Parquet's format properties can be configured with the following settings.

| Additional Parquet format options | Property name in YAML configuration file   | Description                                                                                                                                                                                        |
|-----------------------------------|--------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Binary as string                  | `binary-as-string`                         | Parquet files generated by legacy writers do not correctly set the UTF8 flag for strings, causing string columns to be loaded as BLOB instead. Set this to true to load binary columns as strings. | 
| Filename                          | `filename`                                 | Specifies whether or not an extra filename column should be included in the result.                                                                                                                | 
| File row number                   | `file-row-number`                          | Specifies whether or not to include the file_row_number column.                                                                                                                                    | 
| Hive partitioning                 | `hive-partitioning`                        | Specifies whether to interpret the path as a hive-partitioned path.                                                                                                                                | 
| Union by name                     | `union-by-name`                            | Specifies whether the columns of multiple schemas should be unified by name, rather than by position.                                                                                              | 


### Working with partitioned files

To work with partitioned files, you need to set the `hive-partition` parameter in CSV format settings.
The option can be found under the **Additional <used_format> format options** panel.

Hive partitioning divides a table into multiple files based on the catalog structure.
Each catalog level is associated with a column and the catalogs are named in the format of column_name=value.

The partitions of the data set and types of columns are discovered automatically.


### Environment variables in parameters

DQOps allows you to dynamically replace properties in connection settings with environment variables. To use it, simply
change "clear text" to ${ENV_VAR} using the drop-down menu at the end of the variable entry field and type your variable.

For example:

![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-envvar.jpg)

To add optional JDBC connection properties, just type the **JDBC connection property** and the **Value**. The value
can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.

For example:

![Adding connection JDBC settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-JDBC-properties2.png){ loading=lazy; width="1200px" }

To remove the property click on the trash icon at the end of the input field.

After filling in the connection settings, click the **Test Connection** button to test the connection.

Click the **Save** connection button when the test is successful otherwise, you can check the details of what went wrong.


## Register single file as table

After creating a connection, you can register a single table.

To view the schema, expand the connection in the tree view on the left.

Then, click on the three dots icon next to the schema name(1.) and select the **Add table** (2.) option.
This will open the **Add table** popup modal.

![Register table](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/register-single-table-1.png){ loading=lazy }

Enter the table name and the path absolute to the file. Save the new table configuration.

!!! tip "Use of the relative path"

    If the schema specifies the folder path, use only the file name with extension instead of an absolute path.

!!! tip "Path in table name"

    If you use the absolute file path, you only need to fill in the table name.

![Register table](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/register-single-table-2.png){ loading=lazy }

After saving the new table configuration, the new table will be present under the schema.
You can view the list of columns by clicking on "Columns" under the table in the three view on the left.

You can verify the import tables job in the notification panel on the right corner.

![Register table](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/register-single-table-3.png){ loading=lazy }

If the job completes successfully, the created table will be imported and ready to use.

![Register table](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/register-single-table-4.png){ loading=lazy; width="1200px" }


## Add connection using DQOps Shell

The following examples use parquet file format. To connect to csv or json, put the expected file format instead of "parquet" in the example commands. 

To add a connection run the following command in DQOps Shell.

```
dqo> connection add
```

Fill in the data you will be asked for.

Select the **duckdb** provider, which provides support for the Parquet file format.


```
Connection name (--name): connection1
Database provider type (--provider):
 [ 1] bigquery
 [ 2] databricks
 [ 3] mysql
 [ 4] oracle
 [ 5] postgresql
 [ 6] duckdb
 [ 7] presto
 [ 8] redshift
 [ 9] snowflake
 [10] spark
 [11] sqlserver
 [12] trino
Please enter one of the [] values: 6
Type of storage [local]:
 [ 1] local (default)
 [ 2] s3
 [ 3] azure
 [ 4] gcs
Please enter one of the [] values: 3
Type of source files for DuckDB:
 [ 1] csv
 [ 2] json
 [ 3] parquet
Please enter one of the [] values: 3
Virtual schema names and paths (in a pattern schema=path): files=az://my-container/clients_data
Connection connection1 was successfully added.
Run 'table import -c=connection1' to import tables.
```

You can also run the command with parameters to add a connection in just a single step.

```
dqo> connection add --name=connection1
--provider=duckdb
--duckdb-storage-type=azure
--duckdb-files-format-type=parquet
--duckdb-directories=files=az://my-container/clients_data
```

After adding connection run `table import -c=connection1` to select schemas and import tables.

DQOps will ask you to select the schema from which the tables will be imported.

You can also add the schema and table name as a parameter to import tables in just a single step.

```
dqo> table import --connection={connection name}
--schema={virtual schema name}
--table={file or folder}
```


DQOps supports the use of the asterisk character * as a wildcard when selecting schemas and tables, which can substitute
any number of characters. For example, use  pub* to find all schema a name with a name starting with "pub". The *
character can be used at the beginning, in the middle or at the end of the name.


## Connections configuration files

Connection configurations are stored in the YAML files in the `./sources` folder. The name of the connection is also
the name of the folder where the configuration file is stored.

Below is a sample YAML file showing an example configuration of the Parquet data source connection.

``` yaml
apiVersion: dqo/v1
kind: source
spec:
  provider_type: duckdb
  duckdb:
    read_mode: files
    source_files_type: parquet
    directories:
      files: az://my-container/clients_data
    storage_type: azure
```

### **Reference of all connection parameters**
Complete documentation of all connection parameters used in the `spec.duckdb` node is
described in the reference section of the [DuckdbParametersSpec](../reference/yaml/ConnectionYaml.md#duckdbparametersspec)
YAML file format.


## Next steps

- Learn about more advanced importing when [working with files](../working-with-dqo/working-with-files.md)
- We have provided a variety of use cases that use openly available datasets from [Google Cloud](https://cloud.google.com/datasets) to help you in using DQOps effectively. You can find the [full list of use cases here](../examples/index.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.
