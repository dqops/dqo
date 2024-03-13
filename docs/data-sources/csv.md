# CSV
Read this guide to learn how to configure DQOps to use CSV files from the UI, command-line interface, or directly in YAML files. All parameters are documented.

## Overview

DQOps supports monitoring of data quality in CSV files, which can be stored locally or remotely in cloud storage. 
When importing a CSV file, you can select either a single file or an entire directory containing multiple files. 
DQOps will create a table from the CSV file, which will allow you to profile it and monitor its data quality.

## Prerequisite credentials

Additional configuration is required **only for using remote storage** (AWS S3).

When using remote cloud storage, make sure your account has access to the remote directory containing CSV files. 
The permissions granted should allow you to list the files and directories, as well as read the contents of the files.

## Add connection to CSV files using the user interface

### **Navigate to the connection settings**

To navigate to the CSV connection settings:

1. Go to the Data Sources section and click the **+ Add connection** button in the upper left corner. // todo: screen

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png)

2. Select the CSV file connection option. // todo: screen

    ![Selecting CSV database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-spark.png)


### **Fill in the connection settings**

After navigating to the CSV connection settings, you will need to fill in its details. // todo: screen

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-spark1.png)

| CSV connection settings    | Property name in YAML configuration file | Description                                                                                                                                                                                                                               | 
|----------------------------|------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Connection name            |                                          | The name of the connection that will be created in DQOps. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters. |
| Parallel jobs limit        |                                          | New limit. A null value will disable the limit.                                                                                                                                                                                                 |
| Storage type               | `storage-type`                           | The storage type.                                                                                                                                                                                                                         |
| Files format               | `files-format-type`                      | Type of source files for DuckDB.                                                                                                                                                                                                          |
| JDBC connection property   |                                          | Optional setting. DQOps supports using the JDBC driver to access DuckDB.                                                                                                                                                                  |
| User name/Key ID           | `user`                                   | DuckDB user name for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                      |
| Password/Secret Key        | `password`                               | DuckDB password for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                       |
| Region                     | `region`                                 | The region for the storage credentials. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                          |
| Virtual schema name / Path | `directories`                            | Mapping the virtual schema name to the directory. The path must be absolute.                                                                                                                                                           |

**Additional CSV format options**

CSV file format properties are detected automatically based on a sample of the file data. 
The default sample size is 20480 rows.

// todo: describe that user has to click on the pannel to see propeties
// todo: copy the above description to json and parquet
The following properties can be configured for a very specific CSV format.

| Additional CSV format options | Property name in YAML configuration file | Description                                                                                                                                                                                   |
|-------------------------------|------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Compression                   | `compression`                            | The compression type for the file. By default, this will be detected automatically from the file extension (e.g., t.csv.gz will use gzip, t.csv will use none). Options are none, gzip, zstd. | 
| Date format                   | `dateformat`                             | Specifies the date format used when parsing dates.                                                                                                                                            | 
| Decimal separator             | `decimal-separator`                      | The decimal separator of numbers.                                                                                                                                                             | 
| Delimiter                     | `delim`                                  | Specifies a string separating the columns in each row (line) of the file.                                                                                                                     | 
| Escape character/string       | `escape`                                 | Specifies the string that should appear before the data character sequence that matches the quote value.                                                                                      | 
| New line                      | `new-line`                               | Set the new line character(s) in the file. Options are '\\r','\\n', or '\\r\\n'.                                                                                                              | 
| Quote                         | `quote`                                  | Specifies the quoting string to be used when a data value is quoted.                                                                                                                          | 
| Sample size                   | `sample-size`                            | The number of sample rows for automatic parameter detection.                                                                                                                                  | 
| Skip                          | `skip`                                   | The number of lines at the beginning of the file to be skipped.                                                                                                                               | 
| Timestamp format              | `timestampformat`                        | Specifies the date format used when parsing timestamps.                                                                                                                                       | 
| Treat all columns as varchar  | `all-varchar`                            | An option to skip type detection during CSV parsing and assume that all columns are of VARCHAR type.                                                                                          | 
| Allow quoted nulls            | `allow-quoted-nulls`                     | An option to allow the conversion of quoted values to NULL values.                                                                                                                            | 
| Filename                      | `filename`                               | Specifies whether an additional file name column should be included in the result.                                                                                                            | 
| Header                        | `header`                                 | Specifies that the file contains a header line with the names of each column in the file.                                                                                                     | 
| Hive partitioning             | `hive-partitioning`                      | Specifies whether to interpret the path as a hive-partitioned path.                                                                                                                           | 
| Ignore errors                 | `ignore-errors`                          | An option to ignore any parsing errors encountered - and instead ignore rows with errors.                                                                                                     | 
| Auto detect                   | `auto-detect`                            | (Not available in UI) Enables auto-detection of CSV parameters. Default is true                                                                                                               | 


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


### **Import metadata using the user interface**

When you add a new connection, it will appear in the tree view on the left, and you will be redirected to the Import Metadata screen.
Now we can import CSV files.

1. Import the selected virtual schemas by clicking on the **Import Tables** button next to the source schema name from which you want to import tables.

   ![Importing schemas](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-schemas.png)

2. Select the tables (folders with CSV files or just the files) you want to import or import all tables using the buttons in the upper right corner.

   ![Importing tables](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables.png)

When new tables are imported, DQOps automatically activates profiling and monitoring checks, such as row count,
table availability, and checks detecting schema changes. These checks are scheduled to run daily at 12:00 p.m.
By clicking on the Advisor at the top of the page, you can quickly collect basic statistics, run profiling checks,
or modify the schedule for newly imported tables.

![Importing tables - advisor](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables-advisor.png)


## Add CSV connection using DQOps Shell

To add a connection run the following command in DQOps Shell.

```
dqo> connection add
```

Fill in the data you will be asked for. 

Select the **duckdb** provider, which provides support for the CSV file format.

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
Please enter one of the [] values: 1
Virtual schema names and paths (in a pattern schema=path): files=/usr/share/clients_data
Connection connection1 was successfully added.
Run 'table import -c=connection1' to import tables.
```

You can also run the command with parameters to add a connection in just a single step.

```
dqo> connection add --name=connection1
--provider=duckdb
--duckdb-storage-type=local
--duckdb-files-format-type=csv
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

## Storage settings - directory

// todo: introduction for the section

### Local file system

``` { .asc .annotate }
/usr/share
    ├───...
    └───clients_data(1)
        ├───market_dictionary.csv
        └───sales(2)
            ├───dec_2023.csv
            ├───jan_2024.csv
            ├───feb_2024.csv
            └───...
```

1.  Setting the path prefix to the /usr/share/clients_data allows to load its children: market_dictionary.csv or the sales folder with all files appearing directly in it (without subfolders of sales).
    The path has to be absolute.
2.  To load a single file from the sales folder, the path prefix must be set to the file’s parent folder: /usr/share/clients_data/sales

Setting the path prefix to the /usr/share/clients_data allows to load its children: market_dictionary.csv or the sales folder with all files appearing directly in it (without subfolders of sales).
The path has to be absolute.

To load a single file from the sales folder, the path prefix must be set to the file’s parent folder: /usr/share/clients_data/sales


### Remote cloud storage - AWS S3

``` { .asc .annotate }
s3://project_bucket_a38cfb32
    ├───...
    └───clients_data(1)
        ├───market_dictionary.csv
        └───sales(2)
            ├───dec_2023.csv
            ├───jan_2024.csv
            ├───feb_2024.csv
            └───...
```

1.  Setting the path prefix to the s3://project_bucket_a38cfb3/clients_data allows you to load its children: market_dictionary.csv or the sales folder with all the files directly in it (without subfolders of sales). 
    The path has to be absolute.
2.  To load a single file from the sales folder, the path prefix must be set to the file’s parent folder: s3://project_bucket_a38cfb32/clients_data/sales

Setting the path prefix to the s3://project_bucket_a38cfb3/clients_data allows you to load its children: market_dictionary.csv or the sales folder with all the files directly in it (without subfolders of sales). 
The path has to be absolute.

To load a single file from the sales folder, the path prefix must be set to the file’s parent folder: s3://project_bucket_a38cfb32/clients_data/sales


To work on files hosted in AWS S3, it is recommended to create a special user in IAM to be used as a service account. 
The service account must have access to the bucket with permission to list and read objects.


!!! tip "Service account credentials of the service account can be passed in three ways"

    - Default AWS credentials in your DQOps' userhome,
    - User (AccessKeyID), Password (SecretAccessKey) and Region during connection addition,
    - Setting environment variables for User (Access Key ID), Password (Secret Access Key) and Region fields. [More info here.](./csv.md#Environment-variables-in-parameters).


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
    read_mode: files
    source_files_type: csv
    directories:
      files: /usr/share/clients_data
    storage_type: local
```

### **Reference of all connection parameters**
Complete documentation of all connection parameters used in the `spec.duckdb` node is
described in the reference section of the [DuckdbParametersSpec](../reference/yaml/ConnectionYaml.md#duckdbparametersspec)
YAML file format.


### Working with multiple tables in a single schema

**Different CSV formats**

If you want to add a CSV file in a format that differs from the common CSV format used for other files in the schema, 
override the configuration on that new file (table) level.

The loaded table will try to use the properties described in the table’s YAML, if available. 
If not, it will use the properties configured in the YAML file of the table connection. 
Finally, if both are not set explicitly, the automatically detected properties of the CSV format are used.

**No common prefix for files**

When files are placed in different locations where you cannot specify a common prefix, you can use an absolute path for each file.

You have to manually create a table in the UI. Open Data Sources, expand your connection, and select three dots on the schema to which you want to add a new table.

Fill in the "File path or file name pattern" field with the absolute path to the file and add the new table by clicking the **Save** button.


!!! tip "Creating a new table manually in the user home"
    You can also add a new table manually in the sources folder of your user home by creating a new YAML file in the connection folder.

### Working with partitioned files // todo

To work efficiently with partitions, you need to set the `hive-partition` parameter in CSV format settings. 

// todo: describe that the path has to be modified on a table level when the hive partition is set

## Next steps

- We have provided a variety of use cases that use openly available datasets from [Google Cloud](https://cloud.google.com/datasets) to help you in using DQOps effectively. You can find the [full list of use cases here](../examples/index.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.