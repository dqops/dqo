---
title: How to activate data observability for Azure
---

# How to activate data observability for Azure storage

This guide shows how to enable data observability for data stored in Azure Blob Storage using DQOps. To seamlessly connect to Azure Blob Storage,
DQOps uses the DuckDB connector.

## Prerequisites

- Data in CSV, JSON, or Parquet format (compressed files allowed), stored in Azure Storage.
- [Installed DQOps](../getting-started/installation.md).
- Access permission and authentication to Azure Blob Storage.

### **Choose the Azure authentication mode**

To connect DQOps to Azure Blob Storage, you need to set the authentication method. 
DQOps supports the following authentication methods for connecting to Azure Blob Storage:

- Connection String
- Credential Chain
- Service Principal
- Default Credential

Below you can find how to get credentials for each authentication methods.

### **Connection String**

The connection string is generated at the Storage Account level. It grants access to all files in each of the Storage Containers created in the Storage Account.

You can locate the connection string in the **Storage Account** details. 
Open the Storage Account menu section in the Azure Portal, then select **Security + networking** > **Access keys**.

![Connection string](https://dqops.com/docs/images/data-sources/azure/connection-string.png){ loading=lazy; }


### **Credential Chain**

The credential chain utilizes environment variables and locally stored accounts for applications running on local machines. 
Hence, it will only work on a local DQOps instance.

To sign in interactively to Azure, use the Azure CLI command: **az login**. After successfully running the command, 
**restart the DQOps** process to enable it to load the updated account credentials.


### **Service Principal**

Service Principal is a recommended authentication method. It provides an identity specifically for applications or services to access Azure resources.

To set up Service Principal authentication create a service account, generate a client secret, and add role assignment to the container.

1. **Create Service account** in Azure.

    Open **Enterprise applications** and click the **New application**.

    ![New enterprise application](https://dqops.com/docs/images/data-sources/azure/new-enterprise-application.png){ loading=lazy; }

    Then **Create your own application**.

    ![New your own enterprise application](https://dqops.com/docs/images/data-sources/azure/new-enterprise-application-your-own.png){ loading=lazy; }

    Fill in the name with your service account and create it.

    ![Create your own application](https://dqops.com/docs/images/data-sources/azure/on-right-create-your-own-application.png){ loading=lazy; }

    Now the service account is ready, but it does not have any credentials available to be used.

2. Generate Client Secret 
   
    Open the **App registrations** in Azure Entra ID.
    Select **All applications**, then select the name of the service account.

    ![App registration](https://dqops.com/docs/images/data-sources/azure/app-registrations.png){ loading=lazy; }

    Then navigate to **Certificates & secrets** and click the **New client secret**

    ![App registration](https://dqops.com/docs/images/data-sources/azure/create-new-client-secret.png){ loading=lazy; }

    Then, fill in the name of a new client secret and create it.

    Now the secret is ready. Save the **Value** of the key, which is your **Client Secret**.

    ![App registration](https://dqops.com/docs/images/data-sources/azure/client-secret.png){ loading=lazy; }

3. Assign Roles.

    The last thing to be done is to add the permission of your service account to the storage account.

    Open the container you will work with and select the **Access Control (IAM)**.
    Click on **Add** and select the **Add role assignment**.

    ![App registration](https://dqops.com/docs/images/data-sources/azure/add-iam.png){ loading=lazy; }

    In the Role tab, search for **Storage Blob Data Reader** and click on the present role below.
    The role adds read permissions to the Storage Container.

    ![App registration](https://dqops.com/docs/images/data-sources/azure/add-iam-role.png){ loading=lazy; }

    In the Members tab, click on the **Select members** and type the name of the service account, then click Enter.

    The name of the service account will appear when the full name is typed.

    Select it and click Select.

    ![App registration](https://dqops.com/docs/images/data-sources/azure/add-iam-member.png){ loading=lazy; }

    Provide these details when configuring the Azure Blob Storage connection in DQOps.

    - **Tenant ID**
    - **Client ID**: The application (client) ID of your registered application.
    - **Client Secret**: The secret you generated.
    - **Storage Account Name**: The name of your Azure Storage account.

    Tenant ID and Client ID are available in the App registrations Overview section of the Azure Entra ID.

    ![App registration](https://dqops.com/docs/images/data-sources/azure/credentials.png){ loading=lazy; }


### **Default Credential**

In DQOps, you have the option to set up credentials for accessing Azure Blob Storage directly through the platform.

Keep in mind that any credentials and secrets shared with the DQOps Cloud or DQOps SaaS instances are stored in the .credentials folder.
This folder also contains the default credentials files for Azure Blob Storage (**Azure_default_credentials**).

``` { .asc .annotate hl_lines="4" }
$DQO_USER_HOME
├───...
└───.credentials       
    ├───Azure_default_credentials
    └─...   
```

If you want to use Azure authentication, you need service principal credentials that must be replaced in Azure file content.

To set the credential file for Azure in DQOps, follow these steps:

1. Navigate to the **Configuration** section.
2. Select **Shared credentials** from the tree view on the left.
3. Click the **edit** link on the “Azure_default_credentials” file.

    ![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/credentials/azure-shared-credentials-ui2.png)

4. In the text area, replace the placeholder text with your tenant_id, client_id, client_secret and account_name.

    ![Edit connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/credentials/edit-azure-shared-credential2.png)

5. Click the **Save** button, to save changes.


## Add a connection to Azure Blob Storage using the user interface

### **Navigate to the connection settings**

DQOps uses the DuckDB connector to work with Azure Blob Storage. To navigate to the DuckDB connector:

1. Go to the Data Sources section and click the **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png){ loading=lazy; width="1200px" }

2. Select the DuckDB connection.

    ![Selecting DuckDB connection type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-duckdb.png){ loading=lazy; width="1200px" }


### **Fill in the connection settings**

After navigating to the DuckDB connection settings, you will need to fill in its details.

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-json.png){ loading=lazy; width="1200px" }

1. Enter a unique **Connection name**.
2. Change the **Files location** to **Azure Blob Storage** to work with files located in Azure storage.
3. Select the Azure Blob Storage authentication mode and choose the appropriate method from the available options: 
    - **Connection String**: Directly input your Azure Storage connection string.
    - **Credential Chain**: Provide storage account name and utilize the default Azure credentials chain.
    - **Service Principal**: Provide the **Tenant ID**, **Client ID**, **Client Secret**, and **Storage Account name**.
    - **Default credentials**: Allow DQOps to use Azure credentials stored within the platform.
4. Select the appropriate **File Format** matching your data (CSV, JSON or Parquet).

### **Set the Path for Import configuration**

Define the location of your data in Azure Blob Storage. Here are some options, illustrated with an example directory structure:

- **Specific file**: Enter the full path to a folder (e.g., **/my-bucket/clients_data/reports**). A selection of the file is available after saving the new connection. You cannot use a full file path.
- **Folder with similar files**: Provide the path to a directory containing folder with files with the same structure (e.g., **/my-bucket/clients_data**). A selection of the folder is available after saving the new connection configuration.
- **Hive-partitioned data**: Use the path to the data directory containing the directory with partitioned data and select the **Hive partitioning** checkbox under **Additional format options** (e.g., **/my-bucket/clients_data** with partitioning by date and market in the example). A selection of the **sales** directory is available after saving the new connection configuration.


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

1.  Connect to a specific file - e.g. annual_report_2022.csv by setting prefix to **/my-bucket/clients_data/reports**. A selection of the file is available after saving the new connection configuration.
2.  Connect to all files in path - e.g. whole market_specification directory by setting prefix to **/my-bucket/clients_data/**. A selection of the directory is available after saving the new connection configuration.
3.  Connect to partitioned data - e.g. sales directory with partitioning by date and market - set prefix to **/my-bucket/clients_data** and select **Hive partitioning** checkbox from **Additional format** options. A selection of the **sales** directory is available after saving the new connection configuration.

Click **Save** to establish the connection. DQOps will display a list of accessible schemas and files based on your path configuration.

# Import metadata using the user interface

After creating the connection, it will appear in the tree view on the left, and DQOps will automatically redirect you to the **Import Metadata** screen
Now we can import files.

1. Import the selected virtual schemas by clicking on the **Import Tables** button next to the schema name.

    ![Importing schemas](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/importing-schemas.png){ loading=lazy; width="1200px" }

2. Select the specific tables (folders with files or just the files) you want to import or import all tables using the buttons in the top right corner.

    ![Importing tables](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/importing-tables-csv.png){ loading=lazy; width="1200px" }

When new tables are imported, DQOps automatically activates profiling and monitoring checks, such as row count,
table availability, and checks detecting schema changes. These checks are scheduled to run daily at 12:00 p.m.
By clicking on the Advisor at the top of the page, you can quickly collect basic statistics, run profiling checks,
or modify the schedule for newly imported tables.

![Importing tables - advisor](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/importing-tables-advisor-csv.png){ loading=lazy; width="1200px" }


## Details of new connection - all parameters description

The connection setup form includes the following fields:

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


The next configuration depends on the file format. You can choose from three options: **CSV**, **JSON**, or **Parquet**.


### Additional CSV format options

The properties of the **CSV** file format are automatically identified using a sample of the file data. The default sample size is 20480 rows.

If the data import is unsuccessful, you can access additional CSV format options by clicking on the **Additional CSV format options** panel in the user interface.

You can configure specific properties for a very specific CSV format. Here are the CSV format options, along with their 
corresponding property names in the YAML configuration file and their descriptions:

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

The properties of the **JSON** file format are automatically identified using a sample of the file data. The default sample size is 20480 rows.

If the data import is unsuccessful, you can access additional CSV format options by clicking on the **Additional JSON format options** panel in the user interface.

You can configure specific properties for a very specific JSON format. Here are the JSON format options, along with their
corresponding property names in the YAML configuration file and their descriptions:

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

You can access additional **Parquet** format options by clicking on the **Additional Parquet format options** panel in the user interface.

Here are the Parquet format options, along with their corresponding property names in the YAML configuration file and their descriptions:

| Additional Parquet format options | Property name in YAML configuration file   | Description                                                                                                                                                                                        |
|-----------------------------------|--------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Binary as string                  | `binary-as-string`                         | Parquet files generated by legacy writers do not correctly set the UTF8 flag for strings, causing string columns to be loaded as BLOB instead. Set this to true to load binary columns as strings. | 
| Filename                          | `filename`                                 | Specifies whether or not an extra filename column should be included in the result.                                                                                                                | 
| File row number                   | `file-row-number`                          | Specifies whether or not to include the file_row_number column.                                                                                                                                    | 
| Hive partitioning                 | `hive-partitioning`                        | Specifies whether to interpret the path as a hive-partitioned path.                                                                                                                                | 
| Union by name                     | `union-by-name`                            | Specifies whether the columns of multiple schemas should be unified by name, rather than by position.                                                                                              | 


### Working with partitioned files

To work with partitioned files, you need to set the `hive-partition` parameter in the format settings.
You can find this option under the **Additional <used_format> format** options panel.

Hive partitioning involves dividing a table into multiple files based on the catalog structure. 
Each catalog level is associated with a column, and the catalogs are named in the format of column_name=value.

The partitions of the data set and types of columns are automatically discovered.


### Environment variables in parameters

DQOps allows you to dynamically replace properties in connection settings with environment variables. To use it, simply
change "clear text" to ${ENV_VAR} using the drop-down menu at the end of the variable entry field and type your variable.

For example:

![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-envvar.jpg)

To add optional JDBC connection properties, just type the **JDBC connection property** and the **Value**. The value
can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.

For example:

![Adding connection JDBC settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-JDBC-properties2.png){ loading=lazy; width="1200px" }

To remove the property, click the trash icon at the end of the input field.

After filling in the connection settings, click the **Test Connection** button to test the connection.

Click the **Save** connection button when the test is successful otherwise, you can check the details of what went wrong.


## Register a single file as a table

After creating a connection, you can register a single table.

To view the schema, follow these steps:

1. Expand the connection in the tree view on the left.
2. Click on the three dots icon next to the schema name.
3. Select the **Add table** option. This will open the **Add table** popup modal.

    ![Register table](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/register-single-table-1.png){ loading=lazy }

4. Enter the table name and the absolute path to the file.
5. Save the new table configuration.

!!! tip "Use of the relative path"

    If the schema specifies the folder path, use only the file name with an extension instead of an absolute path.

!!! tip "Path in table name"

    If you use the absolute file path, you only need to fill in the table name.

![Register table](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/register-single-table-2.png){ loading=lazy }

After saving the new table configuration, the table will appear under the specified schema. 
To expand the list of columns, click on the **Columns** under the table in the three-view on the left.

You can check the status of the table import job in the notification panel located in the top right corner.

![Register table](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/register-single-table-3.png){ loading=lazy }

If the job is successful, the table will be created, imported, and ready to use.

![Register table](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/register-single-table-4.png){ loading=lazy; width="1200px" }


## Add connection using DQOps Shell

The following examples demonstrate how to import Parquet file format to Google Cloud Storage buckets. DQOps uses the DuckDB
connector to work with Azure Blob Storage. 
To import CSV or JSON files, replace `parquet` with the appropriate file format in the example commands.

To add a connection, execute the following command in DQOps Shell.

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

You can also add the schema and table name as parameters to import tables in just a single step.

```
dqo> table import --connection={connection name}
--schema={virtual schema name}
--table={file or folder}
```


DQOps supports the use of the asterisk character * as a wildcard when selecting schemas and tables, which can substitute
any number of characters. For example, use  pub* to find all schema a name with a name starting with "pub". The *
character can be used at the beginning, middle, or end of the name.


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
- We have provided a variety of use cases that use openly available datasets from [Google Cloud](https://cloud.google.com/datasets) to help you in using DQOps effectively. You can find the [complete list of use cases here](../examples/index.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.