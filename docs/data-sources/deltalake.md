---
title: How to activate data observability for CSV files
---
# How to activate data observability for Delta Lake table
Read this guide to learn how to configure DQOps to use Delta Lake table from the UI, command-line interface, or directly in YAML files, and activate monitoring.

## Overview

DQOps supports monitoring of data quality in Delta Lake tables, which can be stored locally or remotely in cloud storage. 
DQOps will create a table from the Delta Lake files, which will allow you to profile it and monitor its data quality.

## Prerequisite credentials

Additional configuration is required **only when using remote storage** (AWS S3, Azure Blob Storage or Google Cloud Storage).

When using remote cloud storage, make sure your account has access to the remote directory containing Delta Lake table. 
The permissions granted should allow you to list the files and directories, as well as read the contents of the files.

## Add a connection to Delta Lake tables using the user interface

### **Navigate to the connection settings**

To navigate to the Delta Lake connection settings:

1. Go to the Data Sources section and click the **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png){ loading=lazy; width="1200px" }

2. Select the Delta Lake table connection option.

    ![Selecting Delta Lake database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-deltalake.png){ loading=lazy; width="1200px" }


### **Fill in the connection settings**

After navigating to the Delta Lake connection settings, you will need to fill in its details.

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-deltalake.png){ loading=lazy; width="1200px" }

| Delta Lake connection settings   | Property name in YAML configuration file | Description                                                                                                                                                                                                                                                                                                                | 
|---------------------------|------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Connection name           |                                          | The name of the connection that will be created in DQOps. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters.                                                                                  |
| Parallel jobs limit       |                                          | A limit on the number of jobs that can run simultaneously. Leave empty to disable the limit.                                                                                                                                                                                                                               |
| Files location            | `storage_type`                           | You have the option to import files stored locally or remotely at AWS S3, Azure Blob Storage or Google Cloud Storage. If you choose to work with files remotely, it is recommended that you create a specialized user in IAM. This user should be used as a service account and given permission to list and read objects. |
| File format               | `files_format_type`                      | Type of source files for DuckDB.                                                                                                                                                                                                                                                                                           |
| Aws authentication mode   | `aws_authentication_mode`                | (Available when using AWS S3 files location) Authentication mode to AWS S3. Supports also a ${DUCKDB_AWS_AUTHENTICATION_MODE} configuration with a custom environment variable.                                                                                                                                            |
| Access Key ID             | `user`                                   | (Available when using AWS S3 files location) Access Key ID for AWS authentication. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                                |
| Secret Access Key         | `password`                               | (Available when using AWS S3 files location) Secret Access Key for AWS authentication. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                            |
| Region                    | `region`                                 | (Available when using AWS S3 files location) The region for the storage credentials for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution. When not set the default value will be loaded from .credentials/AWS_default_config file in your DQOps' userhome    |
| Azure authentication mode | `azure_authentication_mode`              | (Available when using Azure Blob Storage files location) Authentication mode to Azure Blob Storage. Supports also a ${DUCKDB_AZURE_AUTHENTICATION_MODE} configuration with a custom environment variable.                                                                                                                  |
| Connection string         | `password`                               | (Available when using Azure Blob Storage files location with Connection String authentication mode) Connection string to the Azure Storage Account. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                               |
| Tenant ID                 | `tenant_id`                              | (Available when using Azure Blob Storage files location with Service Principal authentication mode) Tenant ID. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                    |
| Client ID                 | `client_id`                              | (Available when using Azure Blob Storage files location with Service Principal authentication mode) Client ID. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                    |
| Client Secret             | `client_secret`                          | (Available when using Azure Blob Storage files location with Service Principal authentication mode) Client Secret. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                |
| Storage account name      | `storage_account_name`                   | (Available when using Azure Blob Storage files location with Credential Chain or Service Principal authentication mode) Storage account name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                     |
| Access Key                | `user`                                   | (Available when using Google Cloud Storage files location) The interoperability access key. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                                                                                                                               |
| Secret                    | `password`                               | (Available when using Google Cloud Storage files location) The interoperability secret. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                                                                                                                                   |
| Virtual schema name       | `directories`                            | An alias for the parent directory with data. The virtual schema name is a key of the directories mapping.                                                                                                                                                                                                                  |
| Path                      | `directories`                            | The path prefix to the parent directory with data. The path must be absolute. The virtual schema name is a value of the directories mapping.                                                                                                                                                                               |
| JDBC connection property  |                                          | Optional setting. DQOps supports using the JDBC driver to access DuckDB.                                                                                                                                                                                                                                                   |     

### Setting the path to data import

To import files, you need to set the path first.
The path can lead to files located either locally or remotely.

The following example shows a folder structure with Delta Lake table.

``` { .asc .annotate }
/usr/share
    ├───another_deltalake_table
    └───deltalake_table_example
        ├───_delta_log
        │   └───... // commit files
        ├───country=US
        ├───country=CA
        └───... // another country partitions
```

To load the Delta Lake table in DQOps path prefix must be set to the table's parent folder: **/usr/share**. 
The selection of the specific Delta Lake table is done on the next step of importing the table metadata.

### Environment variables in parameters

DQOps allows you to dynamically replace properties in connection settings with environment variables. To use it, simply
change "clear text" to ${ENV_VAR} using the drop-down menu at the end of the variable entry field and type your variable.

For example:

![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-envvar.jpg){ loading=lazy; width="1200px" }

To add optional JDBC connection properties, just type the **JDBC connection property** and the **Value**. The value
can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.

For example:

![Adding connection JDBC settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-JDBC-properties2.png){ loading=lazy; width="1200px" }

To remove the property, click the trash icon at the end of the input field.

After filling in the connection settings, click the **Test Connection** button to test the connection.

Click the **Save** connection button when the test is successful otherwise, you can check the details of what went wrong.


### Import metadata using the user interface

When you add a new connection, it will appear in the tree view on the left, and you will be redirected to the Import Metadata screen.
Now we can import Delta Lake table.

1. Import the selected virtual schemas by clicking on the **Import Tables** button next to the source schema name from which you want to import tables.

    ![Importing schemas](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/importing-schemas.png){ loading=lazy; width="1200px" }

2. Select the tables (folders with Delta Lake table) you want to import or import all tables using the buttons in the upper right corner.

    ![Importing tables](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/importing-tables-deltalake.png){ loading=lazy; width="1200px" }

When new tables are imported, DQOps automatically activates profiling and monitoring checks, such as row count,
table availability, and checks detecting schema changes. These checks are scheduled to run daily at 12:00 p.m.
By clicking on the Advisor at the top of the page, you can quickly collect basic statistics, run profiling checks,
or modify the schedule for newly imported tables.

![Importing tables - advisor](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/importing-tables-advisor-deltalake.png){ loading=lazy; width="1200px" }

## Add a Delta Lake connection using DQOps Shell

To add a connection run the following command in DQOps Shell.

```
dqo> connection add
```

Fill in the data you will be asked for. 

Select the **duckdb** provider, which provides support for the Delta Lake table format.

!!! info "Windows file system"

    When using the Windows file system remember to put a double backslash (\\) in the path on the CLI prompt.
    You can also use a single slash (/).


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
Please enter one of the [] values: 
Type of source files for DuckDB:
 [ 1] csv
 [ 2] json
 [ 3] parquet
 [ 4] iceberg
 [ 5] delta_lake
Please enter one of the [] values: 5
Virtual schema names and paths (in a pattern schema=path): files=/usr/share
Connection connection1 was successfully added.
Run 'table import -c=connection1' to import tables.
```

You can also run the command with parameters to add a connection in just a single step.

```
dqo> connection add --name=connection1
--provider=duckdb
--duckdb-storage-type=local
--duckdb-files-format-type=delta_lake
--duckdb-directories=files=/usr/share
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
any number of characters. For example, use pub* to find all schema a name with a name starting with "pub". The *
character can be used at the beginning, middle, or end of the name.


## Connections configuration files

Connection configurations are stored in the YAML files in the `./sources` folder. The name of the connection is also
the name of the folder where the configuration file is stored.

Below is a sample YAML file showing an example configuration of the CSV data source connection.

``` yaml
apiVersion: dqo/v1
kind: source
spec:
  provider_type: duckdb
  duckdb:
    read_mode: in_memory
    source_files_type: delta_lake
    directories:
      files: /usr/share
    storage_type: local
```

### **Reference of all connection parameters**
Complete documentation of all connection parameters used in the `spec.duckdb` node is
described in the reference section of the [DuckdbParametersSpec](../reference/yaml/ConnectionYaml.md#duckdbparametersspec)
YAML file format.

## Configure the credentials

### Using shared credentials

With DQOps, you can configure credentials to access AWS S3 or Azure Blob Storage directly in the platform.

Please note, that any credentials and secrets shared with the DQOps Cloud or DQOps SaaS instances are stored in the .credentials folder.
This folder also contains the default credentials files for AWS S3 (**AWS_default_config** and **AWS_default_credentials**) and Azure Blob Storage (**Azure_default_credentials**).

``` { .asc .annotate hl_lines="4-5" }
$DQO_USER_HOME
├───...
└───.credentials                                                            
    ├───AWS_default_config
    ├───AWS_default_credentials
    ├───Azure_default_credentials
    └─...   
```

If you wish to use AWS authentication, the content of the files must be replaced with your aws_access_key_id, aws_secret_access_key and region.
You can find more details on how to [manage access keys for IAM users](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_access-keys.html) in AWS documentation.

If you wish to use Azure authentication, you need service principal credentials that must be replaced in Azure file content.

!!! warning 'AWS system default credentials'

    If you do not replace the content of the files, the default credentials will be loaded from system for AWS only.


To set the credential file for AWS in DQOps, follow steps:

1. Open the Configuration in menu.
2. Select Shared credentials from the tree view on the left.
3. Click the edit link on the “AWS_default_credentials” file.

![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/credentials/aws-shared-credentials-ui.png)

4. In the text area, edit the aws_access_key_id and aws_secret_access_key, replacing the placeholder text.

![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/credentials/edit-aws-shared-credential.png)

5. Click the **Save** button, to save changes, go back to the main **Shared credentials** view.

6. Edit the region in AWS_default_config file and save the file.


!!! tip "Use the AWS system default credentials after filling in the shared credential"

    If you still want to use default credentials from AWS, 
    you must manually delete the .credentials/AWS_default_config and .credentials/AWS_default_credentials files from the DQOps credentials.

    Remember that system default credentials are supported only for AWS.


## Next steps

- Learn about more advanced importing when [working with files](../working-with-dqo/working-with-files.md)
- We have provided a variety of use cases that use openly available datasets from [Google Cloud](https://cloud.google.com/datasets) to help you in using DQOps effectively. You can find the [complete list of use cases here](../examples/index.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.
