---
title: How to activate data observability for Parquet files
---
# How to activate data observability for Parquet files
Read this guide to learn how to configure DQOps to use Parquet files from the UI, command-line interface, or directly in YAML files, and activate monitoring.

## Overview

DQOps supports monitoring of data quality in Parquet files, which can be stored locally or remotely in cloud storage.
When importing a Parquet file, you can select either a single file or an entire directory containing multiple files.
DQOps will create a table from the Parquet file, which will allow you to profile it and monitor its data quality.

## Prerequisite credentials

Additional configuration is required **only for using remote storage** (AWS S3).

When using remote cloud storage, make sure your account has access to the remote directory containing Parquet files.
The permissions granted should allow you to list the files and directories, as well as read the contents of the files.

## Add connection to Parquet files using the user interface

### **Navigate to the connection settings**

To navigate to the Parquet connection settings:

1. Go to the Data Sources section and click the **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png){ loading=lazy; width="1200px" }

2. Select the Parquet file connection option.

    ![Selecting Parquet database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-parquet.png){ loading=lazy; width="1200px" }


### **Fill in the connection settings**

After navigating to the Parquet connection settings, you will need to fill in its details.

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-parquet.png){ loading=lazy; width="1200px" }

| Parquet connection settings | Property name in YAML configuration file | Description                                                                                                                                                                                                                                                                | 
|-----------------------------|------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Connection name             |                                          | The name of the connection that will be created in DQOps. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters.                                  |
| Parallel jobs limit         |                                          | A limit on the number of jobs that can run simultaneously. Leave empty to disable the limit.                                                                                                                                                                               |
| Files location              | `storage_type`                           | You have the option to import files stored locally or on AWS S3. If you choose to work with files on AWS S3, it is recommended that you create a specialized user in IAM. This user should be used as a service account and given permission to list and read objects.     |
| File format                 | `files_format_type`                      | Type of source files for DuckDB.                                                                                                                                                                                                                                           |
| Aws authentication mode     | `duckdb_aws_authentication_mode`         | Available when using AWS S3. Authentication mode to AWS S3. Supports also a ${REDSHIFT_AUTHENTICATION_MODE} configuration with a custom environment variable.                                                                                                              |
| Access Key ID               | `user`                                   | Available when using AWS S3. Access Key ID for AWS authentication. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                |
| Secret Access Key           | `password`                               | Available when using AWS S3. Secret Access Key for AWS authentication. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                            |
| Region                      | `region`                                 | The region for the storage credentials for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution. When not set the default value will be loaded from .credentials/AWS_default_config file in your DQOps' userhome |
| Virtual schema name         | `directories`                            | An alias for the parent directory with data. The virtual schema name is a key of the directories mapping.                                                                                                                                                                  |
| Path                        | `directories`                            | The path prefix to the parent directory with data. The path must be absolute. The virtual schema name is a value of the directories mapping.                                                                                                                               |
| JDBC connection property    |                                          | Optional setting. DQOps supports using the JDBC driver to access DuckDB.                                                                                                                                                                                                   |


### Setting the path to data import

To import files, you need to set the path first.
The path can lead to files located either locally or remotely.

The following example shows a folder structure with Parquet files.

``` { .asc .annotate }
/usr/share
    ├───...
    └───clients_data(1)
        ├───annual_report_2022.csv
        ├───annual_report_2023.parquet
        ├───market_dictionary.json
        └───sales(2)
            ├───dec_2023.parquet
            ├───jan_2024.parquet
            ├───feb_2024.parquet
            └───...
```

1.  Setting the path prefix to the **/usr/share/clients_data** allows to load its children: annual_report_2023.parquet or the sales folder with all files appearing directly in it (without subfolders of sales). The rest of files are omitted since they do not match the parquet file format. The path has to be absolute.
2.  Setting the path prefix to the **/usr/share/clients_data/sales** allows to load a single file from the sales folder.

If you want to load the annual_report_2023.parquet or the sales folder with all files appearing directly in it (without subfolders of sales), set the path prefix to the /usr/share/clients_data.
The rest of files are omitted since they do not match the parquet file format.
The path has to be absolute.

To load a single file from the sales folder, the path prefix must be set to the file’s parent folder: /usr/share/clients_data/sales


### Working with partitioned files

To work with partitioned files, you need to set the `hive-partition` parameter in Parquet format settings.
The option can be found under the **Additional Parquet format options** panel.

Hive partitioning divides a table into multiple files based on the catalog structure.
Each catalog level is associated with a column and the catalogs are named in the format of column_name=value.

The partitions of the data set and types of columns are discovered automatically.


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


### Import metadata using the user interface

When you add a new connection, it will appear in the tree view on the left, and you will be redirected to the Import Metadata screen.
Now we can import Parquet files.

1. Import the selected virtual schemas by clicking on the **Import Tables** button next to the source schema name from which you want to import tables.

   ![Importing schemas](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/importing-schemas.png){ loading=lazy; width="1200px" }

2. Select the tables (folders with Parquet files or just the files) you want to import or import all tables using the buttons in the upper right corner.

   ![Importing tables](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/importing-tables-parquet.png){ loading=lazy; width="1200px" }

When new tables are imported, DQOps automatically activates profiling and monitoring checks, such as row count,
table availability, and checks detecting schema changes. These checks are scheduled to run daily at 12:00 p.m.
By clicking on the Advisor at the top of the page, you can quickly collect basic statistics, run profiling checks,
or modify the schedule for newly imported tables.

![Importing tables - advisor](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/importing-tables-advisor-parquet.png){ loading=lazy; width="1200px" }


### Register single file as table

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

## Add Parquet connection using DQOps Shell

To add a connection run the following command in DQOps Shell.

```
dqo> connection add
```

Fill in the data you will be asked for. 

Select the **duckdb** provider, which provides support for the Parquet file format.

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
Please enter one of the [] values: 
Type of source files for DuckDB:
 [ 1] csv
 [ 2] json
 [ 3] parquet
Please enter one of the [] values: 3
Virtual schema names and paths (in a pattern schema=path): files=/usr/share/clients_data
Connection connection1 was successfully added.
Run 'table import -c=connection1' to import tables.
```

You can also run the command with parameters to add a connection in just a single step.

```
dqo> connection add --name=connection1
--provider=duckdb
--duckdb-storage-type=local
--duckdb-files-format-type=parquet
--duckdb-directories=files=/usr/share/clients_data
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
      files: /usr/share/clients_data
    storage_type: local
```

### **Reference of all connection parameters**
Complete documentation of all connection parameters used in the `spec.duckdb` node is
described in the reference section of the [DuckdbParametersSpec](../reference/yaml/ConnectionYaml.md#duckdbparametersspec)
YAML file format.

## Configure the credentials

### Using shared credentials

With DQOps, you can configure credentials to access AWS S3 directly in the platform.

Please note, that any credentials and secrets shared with the DQOps Cloud or DQOps SaaS instances are stored in the .credentials folder.
This folder also contains the default credentials files pair for AWS named **AWS_default_config** and **AWS_default_credentials**.

``` { .asc .annotate hl_lines="4-5" }
$DQO_USER_HOME
├───...
└───.credentials                                                            
    ├───AWS_default_config
    ├───AWS_default_credentials
    └─...   
```

If you wish to use AWS authentication, the content of the files must be replaced with your aws_access_key_id, aws_secret_access_key and region.
You can find more details on how to [manage access keys for IAM users](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_access-keys.html) in AWS documentation.

!!! warning

    If you do not replace the content of the files, the default credentials will be loaded from system.


To set the credential file in DQOps, follow these steps:

1. Open the Configuration section.
2. Select Shared credentials from the tree view on the left.
3. Click the edit link on the “AWS_default_credentials” file.

![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/credentials/aws-shared-credentials-ui.png)

4. In the text area, edit the aws_access_key_id and aws_secret_access_key, replacing the placeholder text.

![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/credentials/edit-aws-shared-credential.png)

5. Click the **Save** button, to save changes, go back to the main **Shared credentials** view.

6. Edit the region in AWS_default_config file and save the file.


!!! tip "Use the system default credentials after filling in the shared credential"

    If you still want to use default credentials from AWS, 
    you must manually delete the .credentials/AWS_default_config and .credentials/AWS_default_credentials files from the DQOps credentials.


## Next steps

- Learn about more advanced importing when [working with files](../working-with-dqo/working-with-files.md)
- We have provided a variety of use cases that use openly available datasets from [Google Cloud](https://cloud.google.com/datasets) to help you in using DQOps effectively. You can find the [full list of use cases here](../examples/index.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.