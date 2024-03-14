# Parquet
Read this guide to learn how to configure DQOps to use Parquet files from the UI, command-line interface, or directly in YAML files. All parameters are documented.

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

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png)

2. Select the Parquet file connection option.

    ![Selecting Parquet database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-parquet.png)


### **Fill in the connection settings**

After navigating to the Parquet connection settings, you will need to fill in its details.

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-parquet.png)

| Parquet connection settings | Property name in YAML configuration file | Description                                                                                                                                                                                                                               | 
|-------------------------|------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Connection name         |                                          | The name of the connection that will be created in DQOps. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters.                   |
| Parallel jobs limit     |                                          | New limit. A null value will disable the limit.                                                                                                                                                                                                             |
| Files location          | `storage-type`                           | The files location: Local or AWS S3. To work with files hosted remotely (e.g. AWS S3), it is recommended to create a special user in IAM to be used as a service account with permission to list and read objects.                                          |
| File format             | `files-format-type`                      | Type of source files for DuckDB.                                                                                                                                                                                                                            |
| User name/Key ID        | `user`                                   | DuckDB user name for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution. When both are not set the default value will be loaded from credentials in your DQOps' userhome.                       |
| Password/Secret Key     | `password`                               | DuckDB password for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution. When both are not set the default value will be loaded from credentials in your DQOps' userhome.                        |
| Region                  | `region`                                 | The region for the storage credentials for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution. When both are not set the default value will be loaded from credentials in your DQOps' userhome. |
| Virtual schema name     | `directories`                            | An alias for the parent directory with data. The virtual schema name is a key of the directories mapping.                                                                                                                                                   |
| Path                    | `directories`                            | The path prefix to the parent directory with data. The path must be absolute. The virtual schema name is a value of the directories mapping.                                                                                                                |
| JDBC connection property |                                          | Optional setting. DQOps supports using the JDBC driver to access DuckDB.                                                                                                                                                                                    |


### Setting the path to data

The path determines which files could be imported.
The path can lead to files located locally or remotely.

``` { .asc .annotate }
/usr/share
    ├───...
    └───clients_data(1)
        ├───market_dictionary.parquet
        └───sales(2)
            ├───dec_2023.parquet
            ├───jan_2024.parquet
            ├───feb_2024.parquet
            └───...
```

1.  Setting the path prefix to the /usr/share/clients_data allows to load its children: market_dictionary.parquet or the sales folder with all files appearing directly in it (without subfolders of sales).
    The path has to be absolute.
2.  To load a single file from the sales folder, the path prefix must be set to the file’s parent folder: /usr/share/clients_data/sales

For example when we have a local file system shown above and you want to load the market_dictionary.parquet or the sales folder with all files appearing directly in it (without subfolders of sales), set the path prefix to the /usr/share/clients_data.
The path has to be absolute.

To load a single file from the sales folder, the path prefix must be set to the file’s parent folder: /usr/share/clients_data/sales


### Working with partitioned files

To work efficiently with partitions, you need to set the `hive-partition` parameter in Parquet format settings.
The option is available under the **Additional Parquet format options** panel.

Hive partitioning splits the table into multiple files under the catalog structure.
Each of the catalog levels corresponds to a column.
The catalogs are named in the column_name=value convention.

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

![Adding connection JDBC settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-JDBC-properties2.png)

To remove the property click on the trash icon at the end of the input field.

After filling in the connection settings, click the **Test Connection** button to test the connection.

Click the **Save** connection button when the test is successful otherwise, you can check the details of what went wrong.


### Import metadata using the user interface

When you add a new connection, it will appear in the tree view on the left, and you will be redirected to the Import Metadata screen.
Now we can import Parquet files.

1. Import the selected virtual schemas by clicking on the **Import Tables** button next to the source schema name from which you want to import tables.

   ![Importing schemas](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-schemas.png)

2. Select the tables (folders with Parquet files or just the files) you want to import or import all tables using the buttons in the upper right corner.

   ![Importing tables](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables.png)

When new tables are imported, DQOps automatically activates profiling and monitoring checks, such as row count,
table availability, and checks detecting schema changes. These checks are scheduled to run daily at 12:00 p.m.
By clicking on the Advisor at the top of the page, you can quickly collect basic statistics, run profiling checks,
or modify the schedule for newly imported tables.

![Importing tables - advisor](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables-advisor.png)


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

## Next steps

- Learn about more advanced importing when [working with files](../working-with-dqo/working-with-files.md)
- We have provided a variety of use cases that use openly available datasets from [Google Cloud](https://cloud.google.com/datasets) to help you in using DQOps effectively. You can find the [full list of use cases here](../examples/index.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.