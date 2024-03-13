# DuckDB
Read this guide to learn how to configure DQOps to use CSV, JSON, or Parquet files from the UI, command-line interface, or directly in YAML files. 
All parameters are documented.

## Overview

DQOps supports monitoring of data quality in CSV, JSON, or Parquet files, which can be stored locally or remotely in cloud storage. 
When importing files, you can select either a single file or an entire directory containing multiple files. 
DQOps will create a table from the files, which will allow you to profile it and monitor its data quality.

## Prerequisite credentials

Additional configuration is required **only for using remote storage** (AWS S3).

When using remote cloud storage, make sure your account has access to the remote directory containing CSV, JSON, or Parquet files. 
The permissions granted should allow you to list the files and directories, as well as read the contents of the files.

## Add connection to the files using the user interface

### **Navigate to the connection settings**

To navigate to the DuckDB connection settings:

1. Go to the Data Sources section and click the **+ Add connection** button in the upper left corner. // todo: screen

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png)

2. Select the DuckDB file connection option. // todo: screen

    ![Selecting CSV database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-duckdb.png)


### **Fill in the connection settings**

After navigating to the DuckDB connection settings, you will need to fill in its details. // todo: screen

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-duckdb.png)

| DuckDB connection settings | Property name in YAML configuration file | Description                                                                                                                                                                                                                               | 
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

Selecting the expected file format will allow to edit the additional format options.

There file formats are available:

- [CSV](./csv.md#Additional-CSV-format-options)
- [JSON](./json.md#Additional-CSV-format-options)
- [Parquet](./parquet.md#Additional-CSV-format-options)

**Open one of the pages for the format options description and configuration detals.**

### **Reference of all connection parameters**
Complete documentation of all connection parameters used in the `spec.duckdb` node is
described in the reference section of the [DuckdbParametersSpec](../reference/yaml/ConnectionYaml.md#duckdbparametersspec)
YAML file format.

## Next steps

- We have provided a variety of use cases that use openly available datasets from [Google Cloud](https://cloud.google.com/datasets) to help you in using DQOps effectively. You can find the [full list of use cases here](../examples/index.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.