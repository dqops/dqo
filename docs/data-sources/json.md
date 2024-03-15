# JSON
Read this guide to learn how to configure DQOps to use JSON files from the UI, command-line interface, or directly in YAML files. All parameters are documented.

## Overview

DQOps supports monitoring of data quality in JSON files, which can be stored locally or remotely in cloud storage.
When importing a JSON file, you can select either a single file or an entire directory containing multiple files.
DQOps will create a table from the JSON file, which will allow you to profile it and monitor its data quality.

## Prerequisite credentials

Additional configuration is required **only for using remote storage** (AWS S3).

When using remote cloud storage, make sure your account has access to the remote directory containing JSON files.
The permissions granted should allow you to list the files and directories, as well as read the contents of the files.

## Add connection to JSON files using the user interface

### **Navigate to the connection settings**

To navigate to the JSON connection settings:

1. Go to the Data Sources section and click the **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png)

2. Select the JSON file connection option.

    ![Selecting JSON database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-json.png)


### **Fill in the connection settings**

After navigating to the JSON connection settings, you will need to fill in its details.

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-json.png)

| JSON connection settings | Property name in YAML configuration file | Description                                                                                                                                                                                                                                                            | 
|--------------------------|------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Connection name          |                                          | The name of the connection that will be created in DQOps. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters.                              |
| Parallel jobs limit      |                                          | A limit on the number of jobs that can run simultaneously. Leave empty to disable the limit.                                                                                                                                                                                                                        |
| Files location           | `storage-type`                           | You have the option to import files stored locally or on AWS S3. If you choose to work with files on AWS S3, it is recommended that you create a specialized user in IAM. This user should be used as a service account and given permission to list and read objects. |
| File format              | `files-format-type`                      | Type of source files for DuckDB.                                                                                                                                                                                                                                       |
| User name/Key ID         | `user`                                   | DuckDB user name for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution. When both are not set the default value will be loaded from credentials in your DQOps' userhome.                                  |
| Password/Secret Key      | `password`                               | DuckDB password for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution. When both are not set the default value will be loaded from credentials in your DQOps' userhome.                                   |
| Region                   | `region`                                 | The region for the storage credentials for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution. When both are not set the default value will be loaded from credentials in your DQOps' userhome.            |
| Virtual schema name      | `directories`                            | An alias for the parent directory with data. The virtual schema name is a key of the directories mapping.                                                                                                                                                              |
| Path                     | `directories`                            | The path prefix to the parent directory with data. The path must be absolute. The virtual schema name is a value of the directories mapping.                                                                                                                           |
| JDBC connection property |                                          | Optional setting. DQOps supports using the JDBC driver to access DuckDB.                                                                                                                                                                                               |



### Setting the path to data import

To import files, you need to set the path first. 
The path can lead to files located either locally or remotely.

The following example shows a folder structure with JSON files.

``` { .asc .annotate }
/usr/share
    ├───...
    └───clients_data(1)
        ├───market_dictionary.json
        └───sales(2)
            ├───dec_2023.json
            ├───jan_2024.json
            ├───feb_2024.json
            └───...
```

1.  Setting the path prefix to the **/usr/share/clients_data** allows to load its children: market_dictionary.json or the sales folder with all files appearing directly in it (without subfolders of sales). The path has to be absolute.
2.  Setting the path prefix to the **/usr/share/clients_data/sales** allows to load a single file from the sales folder.

If you want to load the market_dictionary.json or the sales folder with all files appearing directly in it (without subfolders of sales), set the path prefix to the /usr/share/clients_data.
The path has to be absolute.

To load a single file from the sales folder, the path prefix must be set to the file’s parent folder: /usr/share/clients_data/sales


### Working with partitioned files

To work efficiently with partitions, you need to set the `hive-partition` parameter in JSON format settings.
The option is available under the **Additional JSON format options** panel.

Hive partitioning splits the table into multiple files under the catalog structure.
Each of the catalog levels corresponds to a column.
The catalogs are named in the column_name=value convention.

The partitions of the data set and types of columns are discovered automatically.


### Additional JSON format options

JSON file format properties are detected automatically based on a sample of the file data.
The default sample size is 20480 rows.

In **case of invalid import** of the data, expand the **Additional JSON format options** panel with file format options by clicking on it in UI.

The following properties can be configured for a very specific JSON format.

| Additional JSON format options | Property name in YAML configuration file | Description                                                                                                                                                                                     |
|--------------------------------|------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Compression                    | `compression`                              | The compression type for the file. By default, this will be detected automatically from the file extension (e.g., t.json.gz will use gzip, t.json will use none). Options are none, gzip, zstd. | 
| Date format                    | `dateformat`                               | Specifies the date format used when parsing dates.                                                                                                                                              | 
| Json Format                    | `format`                                   | Json format. Can be one of \['auto', 'unstructured', 'newline_delimited', 'array'\].                                                                                                            | 
| Maximum depth                  | `maximum-depth`                            | Maximum nesting depth to which the automatic schema detection detects types. Set to -1 to fully detect nested JSON types.                                                                       | 
| Maximum object size            | `maximum-object-size`                      | The maximum size of a JSON object (in bytes).                                                                                                                                                   | 
| Records                        | `records`                                  | Can be one of ['auto', 'true', 'false'].                                                                                                                                                        | 
| Sample size                    | `sample-size`                              | The number of sample rows for automatic parameter detection.                                                                                                                                    | 
| Timestamp format               | `timestampformat`                          | Specifies the date format used when parsing timestamps.                                                                                                                                         | 
| Convert strings to integers    | `convert-strings-to-integers`              | Specifies whether strings representing integer values should be converted to a numerical type.                                                                                                  | 
| Filename                       | `filename`                                 | Specifies whether an additional file name column should be included in the result.                                                                                                              | 
| Hive partitioning              | `hive-partitioning`                        | Specifies whether to interpret the path as a hive-partitioned path.                                                                                                                             | 
| Ignore errors                  | `ignore-errors`                            | An option to ignore any parsing errors encountered - and instead ignore rows with errors.                                                                                                       | 
| Auto detect                    | `auto-detect`                              | (Not available in UI) Whether to auto-detect detect the names of the keys and data types of the values automatically.                                                                           | 


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
Now we can import JSON files.

1. Import the selected virtual schemas by clicking on the **Import Tables** button next to the source schema name from which you want to import tables.

   ![Importing schemas](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-schemas.png)

2. Select the tables (folders with JSON files or just the files) you want to import or import all tables using the buttons in the upper right corner.

   ![Importing tables](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables.png)

When new tables are imported, DQOps automatically activates profiling and monitoring checks, such as row count,
table availability, and checks detecting schema changes. These checks are scheduled to run daily at 12:00 p.m.
By clicking on the Advisor at the top of the page, you can quickly collect basic statistics, run profiling checks,
or modify the schedule for newly imported tables.

![Importing tables - advisor](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables-advisor.png)


## Add JSON connection using DQOps Shell

To add a connection run the following command in DQOps Shell.

```
dqo> connection add
```

Fill in the data you will be asked for. 

Select the **duckdb** provider, which provides support for the JSON file format.

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
Please enter one of the [] values: 2
Virtual schema names and paths (in a pattern schema=path): files=/usr/share/clients_data
Connection connection1 was successfully added.
Run 'table import -c=connection1' to import tables.
```

You can also run the command with parameters to add a connection in just a single step.

```
dqo> connection add --name=connection1
--provider=duckdb
--duckdb-storage-type=local
--duckdb-files-format-type=json
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

Below is a sample YAML file showing an example configuration of the JSON data source connection.

``` yaml
apiVersion: dqo/v1
kind: source
spec:
  provider_type: duckdb
  duckdb:
    read_mode: files
    source_files_type: json
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