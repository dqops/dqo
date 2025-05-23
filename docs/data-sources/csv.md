---
title: How to Monitor CSV Files? Data Observability Configuration
---
# How to Monitor CSV Files? Data Observability Configuration
Learn how to monitor CSV Files and enable data observability to detect schema changes, data anomalies, volume fluctuations, and data quality issues.

## Overview

DQOps supports monitoring of data quality in CSV files, which can be stored locally or remotely in cloud storage. 
When importing a CSV file, you can select either a single file or an entire directory containing multiple files. 
DQOps will create a table from the CSV file, which will allow you to profile it and monitor its data quality.

## Prerequisite credentials

Additional configuration is required **only when using remote storage** (AWS S3, Azure Blob Storage or Google Cloud Storage).

When using remote cloud storage, make sure your account has access to the remote directory containing CSV files. 
The permissions granted should allow you to list the files and directories, as well as read the contents of the files.

!!! note "DQOps free version limits"

    DuckDB extensions are not included in the free version of DQOps.
    If your company network restricts access to external resources, 
    analyzing the quality of data in the cloud (AWS, Azure, GCP) 
    and data formats (Iceberg and Delta Lake) may not be possible. 

    For more details, please [contact DQOps sales](https://dqops.com/contact-us/).



## Add a connection to CSV files using the user interface

### **Navigate to the connection settings**

To navigate to the CSV connection settings:

1. Go to the Data Sources section and click the **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection2.png){ loading=lazy; width="1200px" }

2. Select the CSV file connection option.

    ![Selecting CSV database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-csv2.png){ loading=lazy; width="1200px" }


### **Fill in the connection settings**

After navigating to the CSV connection settings, you will need to fill in its details.

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-csv.png){ loading=lazy; width="1200px" }

| CSV connection settings   | Property name in YAML configuration file | Description                                                                                                                                                                                                                                                                                                                | 
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

The following example shows a folder structure with CSV files.

``` { .asc .annotate }
/usr/share
    ├───...
    └───clients_data(1)
        ├───annual_report_2022.csv
        ├───annual_report_2023.parquet
        ├───market_dictionary.json
        └───sales(2)
            ├───dec_2023.csv
            ├───jan_2024.csv
            ├───feb_2024.csv
            └───...
```

1.  Setting the path prefix to the **/usr/share/clients_data** allows to load its children: annual_report_2022.csv or the sales folder with all files appearing directly in it (without subfolders of sales). The rest of files are omitted since they do not match the csv file format. The path has to be absolute.
2.  Setting the path prefix to the **/usr/share/clients_data/sales** allows to load a single file from the sales folder.

If you want to load the annual_report_2022.csv or the sales folder with all files appearing directly in it (without subfolders of sales), set the path prefix to the /usr/share/clients_data.
The rest of files are omitted since they do not match the csv file format.
The path has to be absolute.

To load a single file from the sales folder, the path prefix must be set to the file’s parent folder: /usr/share/clients_data/sales


### Working with partitioned files

To work with partitioned files, you need to set the `hive-partition` parameter in CSV format settings.
The option can be found under the **Additional CSV format options** panel.

Hive partitioning divides a table into multiple files based on the catalog structure.
Each catalog level is associated with a column and the catalogs are named in the format of column_name=value.

The partitions of the data set and types of columns are discovered automatically.


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
Now we can import CSV files.

1. Import the selected virtual schemas by clicking on the **Import Tables** button next to the source schema name from which you want to import tables.

    ![Importing schemas](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/importing-schemas.png){ loading=lazy; width="1200px" }

2. Select the tables (folders with CSV files or just the files) you want to import or import all tables using the buttons in the upper right corner.

    ![Importing tables](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/importing-tables-csv.png){ loading=lazy; width="1200px" }

Upon import, you will receive information that a new tables have been imported. You can then begin collecting basic statistics
and profiling data by running default data profiling checks. Simply click on the **Start profiling** button to initiate this process.

![Collect basic statistics and profile data with default profiling checks](https://dqops.com/docs/images/getting-started/collect-basic-statistics-and-profile-data.png)

!!! info "Automatically activated checks"

    Once new tables are imported, DQOps automatically activates [profiling and monitoring checks](../dqo-concepts/definition-of-data-quality-checks/index.md) which are which are pre-enabled by [data quality policies](../dqo-concepts/data-observability.md#automatic-activation-of-checks).
    These checks detect volume anomalies, data freshness anomalies, empty tables, table availability, schema changes, anomalies in the count of distinct values, and null percent anomalies. The profiling checks are scheduled 
    to run at 12:00 p.m. on the 1st day of every month, and the monitoring checks are scheduled to run daily at 12:00 p.m.
    
    [**Profiling checks**](../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md) are designed to assess
    the initial data quality score of a data source. Profiling checks are also useful for exploring and experimenting with 
    various types of checks and determining the most suitable ones for regular data quality monitoring.
    
    [**Monitoring checks**](../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md) are 
    standard checks that monitor the data quality of a table or column. They can also be referred to as **Data Observability** checks.
    These checks capture a single data quality result for the entire table or column.


### Register single file as table

After creating a connection, you can register a single table.

To view the schema, expand the connection in the tree view on the left.

Then, click on the three dots icon next to the schema name(1.) and select the **Add table** (2.) option. 
This will open the **Add table** popup modal.

![Register table](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/register-single-table-1.png){ loading=lazy }

Enter the table name and the path absolute to the file. Save the new table configuration.

!!! tip "Use of the relative path"

    If the schema specifies the folder path, use only the file name with an extension instead of an absolute path.

!!! tip "Path in table name"

    If you use the absolute file path, you only need to fill in the table name.

![Register table](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/register-single-table-2.png)

After saving the new table configuration, the new table will be present under the schema. 
You can view the list of columns by clicking on "Columns" under the table in the three view on the left.

You can verify the import tables job in the notification panel on the right corner.

![Register table](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/register-single-table-3.png)

If the job completes successfully, the created table will be imported and ready to use.

![Register table](https://dqops.com/docs/images/working-with-dqo/adding-connections/duckdb/register-single-table-4.png){ loading=lazy; width="1200px" }

## Add a CSV connection using DQOps Shell

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
 [ 2] clickhouse
 [ 3] databricks
 [ 4] db2
 [ 5] duckdb
 [ 6] hana
 [ 7] mariadb
 [ 8] mysql
 [ 9] oracle
 [10] postgresql
 [11] presto
 [12] questdb
 [13] redshift
 [14] snowflake
 [15] spark
 [16] sqlserver
 [17] teradata
 [18] trino
Please enter one of the [] values: 5
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
    source_files_type: csv
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

!!! warning "AWS system default credentials"

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
