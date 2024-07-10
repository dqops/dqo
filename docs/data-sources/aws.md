---
title: How to activate data observability for AWS S3
---

# How to activate data observability for AWS S3

This guide shows how to activate data observability for AWS by connecting DQOps. 
The example will use the S3 for storing data. 

# Prerequisites

- Data in CSV, JSON or Parquet format (compressed files allowed), located in a Bucket.
- [DQOps installation](../getting-started/installation.md)

# Add connection to AWS S3 using the user interface

### **Navigate to the connection settings**

To navigate to the AWS S3 connection settings:

1. Go to the Data Sources section and click the **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png){ loading=lazy; width="1200px" }

2. Select the DuckDB connection.

    ![Selecting DuckDB connection type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-duckdb.png){ loading=lazy; width="1200px" }


### **Fill in the connection settings**

After navigating to the AWS S3 connection settings, you will need to fill in its details.

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-json.png){ loading=lazy; width="1200px" }

Fill the **Connection name** any name you want.

Change the **Files location** to **AWS S3**, to work with files located in AWS S3.

Select the **File Format** suitable to your files located in AWS S3. You can choose from CSV, JSON or Parquet file format.

To complete the configuration you need to set the:

- **AWS authentication mode**
- **Path**


## Choose the AWS authentication mode

DQOps requires permissions to establish the connection to the AWS S3 storage.

You can choose from a variety of authentication methods that will allow to connect to your data:

- IAM
- Default Credential

Below you can find how to get credentials for each of the authentication methods.

### **IAM**

This is the recommended authentication method.

The service account is an impersonalized identity used specifically for a service with a proper permission.

This method needs to create a service account, generate a secret.

To use this method you need to create a service account in AWS.
Open **IAM**, navigate to **Users** and click the **Create user** button.

![Create service account](https://dqops.com/docs/images/data-sources/aws/aws-create-service-account.png){ loading=lazy; width="1200px" }

docs-dqo-ai/docs/images/data-sources/aws

Set the name of the service account.

![Create service account step 1](https://dqops.com/docs/images/data-sources/aws/aws-create-step-1.png){ loading=lazy; width="1200px" }

In permission options select **Attach policies directly**.
In search field type **AmazonS3ReadOnlyAccess** and select the policy.

This policy provides read only access to all available buckets in the project.
If you like to limit access you need to create a custom policy and select it here.

This is achievable by modifying the value the Resource field of a permission to specify path S3 path prefix that permission will work with.

E.g: "Resource": "arn:aws:s3:::<bucket_name_here>/*"

Abowe allows access all object inside the bucket named <bucket_name_here>.

![Create service account step 2](https://dqops.com/docs/images/data-sources/aws/aws-create-step-2.png){ loading=lazy; width="1200px" }
Then click the Create user button.

![Create service account step 3](https://dqops.com/docs/images/data-sources/aws/aws-create-step-3.png){ loading=lazy; width="1200px" }
The service account has been created.
Now you can generate access key what will be used by DQOps to access files in your bucket. Click on the name of the service account.

![Created service account](https://dqops.com/docs/images/data-sources/aws/aws-service-account-created.png){ loading=lazy; width="1200px" }

Navigate to **Security credentials** tab, scroll down to **Access keys** section and click on the **Create access key** button.

![Create access key](https://dqops.com/docs/images/data-sources/aws/create-access-key.png){ loading=lazy; width="1200px" }

Select **Application running outside AWS**

![Create access key step 1](https://dqops.com/docs/images/data-sources/aws/create-access-key-step-1.png){ loading=lazy; width="1200px" }

Put the description of your access key.

Click on Create access key.

![Create access key step 2](https://dqops.com/docs/images/data-sources/aws/create-access-key-step-2.png){ loading=lazy; width="1200px" }

Click on Show link to present the secret.

Now you have generated Access key for AWS S3. Copy Access key and Secret access key.

![Create access key step 3](https://dqops.com/docs/images/data-sources/aws/create-access-key-step-3.png){ loading=lazy; width="1200px" }

### **Default Credential**

With DQOps, you can configure credentials to access AWS S3 directly in the platform.

Please note, that any credentials and secrets shared with the DQOps Cloud or DQOps SaaS instances are stored in the .credentials folder.
This folder also contains the default credentials files for AWS S3 (**AWS_default_config** and **AWS_default_credentials**).

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


## Set the Path

Let assume you have directories with unstructured files, dataset divided into multiple files with the same structure - e.g. same header or partitioned data.
All mentioned cases are supported but differs in the configuration. 

``` { .asc .annotate }
my-bucket
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

# Import metadata using the user interface

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


# Details of new connection - all parameters description

The form of the adding a new connection page provides additional fields not mentioned before.

| File connection settings  | Property name in YAML configuration file | Description                                                                                                                                                                                                                                                                                                             | 
|---------------------------|------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Connection name           |                                          | The name of the connection that will be created in DQOps. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters.                                                                               |
| Parallel jobs limit       |                                          | A limit on the number of jobs that can run simultaneously. Leave empty to disable the limit.                                                                                                                                                                                                                            |
| Files location            | `storage_type`                           | You have the option to import files stored locally or remotely at AWS S3, Azure Blob Storage or Google Cloud Storage.                                                                                                                                                                                                   |
| File format               | `files_format_type`                      | Type of source files for DuckDB.                                                                                                                                                                                                                                                                                        |
| Access Key ID             | `user`                                   | (Available when using AWS S3 files location) Access Key ID for AWS authentication. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                             |
| Secret Access Key         | `password`                               | (Available when using AWS S3 files location) Secret Access Key for AWS authentication. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                         |
| Region                    | `region`                                 | (Available when using AWS S3 files location) The region for the storage credentials for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution. When not set the default value will be loaded from .credentials/AWS_default_config file in your DQOps' userhome |
| Virtual schema name       | `directories`                            | An alias for the parent directory with data. The virtual schema name is a key of the directories mapping.                                                                                                                                                                                                               |
| Path                      | `directories`                            | The path prefix to the parent directory with data. The path must be absolute. The virtual schema name is a value of the directories mapping.                                                                                                                                                                            |
| JDBC connection property  |                                          | Optional setting. DQOps supports using the JDBC driver to access DuckDB.                                                                                                                                                                                                                                                |


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


# Register single file as table

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


# Add connection using DQOps Shell

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
Please enter one of the [] values: 2
Type of source files for DuckDB:
 [ 1] csv
 [ 2] json
 [ 3] parquet
Please enter one of the [] values: 3
Virtual schema names and paths (in a pattern schema=path): files=s3://my-bucket/clients_data
Connection connection1 was successfully added.
Run 'table import -c=connection1' to import tables.
```

You can also run the command with parameters to add a connection in just a single step.

```
dqo> connection add --name=connection1
--provider=duckdb
--duckdb-storage-type=s3
--duckdb-files-format-type=parquet
--duckdb-directories=files=s3://my-bucket/clients_data
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
      files: s3://my-bucket/clients_data
    storage_type: s3
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
