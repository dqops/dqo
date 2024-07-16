---
title: How to activate data observability for DuckDB
---
# How to activate data observability for DuckDB
Read this guide to learn how to configure DQOps to use CSV, JSON, or Parquet files from the UI, command-line interface, or directly in YAML files, and activate monitoring.

## Overview

DQOps supports monitoring of data quality in CSV, JSON, or Parquet files, which can be stored locally or remotely in cloud storage. 
When importing files, you can select either a single file or an entire directory containing multiple files. 
DQOps will create a table from the files, which will allow you to profile it and monitor its data quality.

## Prerequisite credentials

Additional configuration is required **only when using remote storage** (AWS S3, Azure Blob Storage or Google Cloud Storage).

When using remote cloud storage, make sure your account has access to the remote directory containing CSV, JSON, or Parquet files. 
The permissions granted should allow you to list the files and directories, as well as read the contents of the files.

## Add a connection to the files using the user interface

### **Navigate to the connection settings**

To navigate to the DuckDB connection settings:

1. Go to the Data Sources section and click the **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png){ loading=lazy; width="1200px" }

2. Select the DuckDB file connection option.

    ![Selecting CSV database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-duckdb.png){ loading=lazy; width="1200px" }


### **Fill in the connection settings**

After navigating to the DuckDB connection settings, you will need to fill in its details.

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-duckdb.png){ loading=lazy; width="1200px" }

| DuckDB connection settings | Property name in YAML configuration file | Description                                                                                                                                                                                                                                                                                                                | 
|----------------------------|------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Connection name            |                                          | The name of the connection that will be created in DQOps. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters.                                                                                  |
| Parallel jobs limit        |                                          | A limit on the number of jobs that can run simultaneously. Leave empty to disable the limit.                                                                                                                                                                                                                               |
| Files location             | `storage_type`                           | You have the option to import files stored locally or remotely at AWS S3, Azure Blob Storage or Google Cloud Storage. If you choose to work with files remotely, it is recommended that you create a specialized user in IAM. This user should be used as a service account and given permission to list and read objects. |
| File format                | `files_format_type`                      | Type of source files for DuckDB.                                                                                                                                                                                                                                                                                           |
| Aws authentication mode    | `aws_authentication_mode`                | (Available when using AWS S3) Authentication mode to AWS S3. Supports also a ${DUCKDB_AWS_AUTHENTICATION_MODE} configuration with a custom environment variable.                                                                                                                                                           |
| Access Key ID              | `user`                                   | (Available when using AWS S3) Access Key ID for AWS authentication. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                                               |
| Secret Access Key          | `password`                               | (Available when using AWS S3) Secret Access Key for AWS authentication. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                                           |
| Region                     | `region`                                 | (Available when using AWS S3) The region for the storage credentials for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution. When not set the default value will be loaded from .credentials/AWS_default_config file in your DQOps' userhome                   |
| Azure authentication mode  | `azure_authentication_mode`              | (Available when using Azure Blob Storage) Authentication mode to Azure Blob Storage. Supports also a ${DUCKDB_AZURE_AUTHENTICATION_MODE} configuration with a custom environment variable.                                                                                                                                 |
| Connection string          | `password`                               | (Available when using Azure Blob Storage files location with Connection String authentication mode) Connection string to the Azure Storage Account. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                               |
| Tenant ID                  | `tenant_id`                              | (Available when using Azure Blob Storage files location with Service Principal authentication mode) Tenant ID. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                    |
| Client ID                  | `client_id`                              | (Available when using Azure Blob Storage files location with Service Principal authentication mode) Client ID. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                    |
| Client Secret              | `client_secret`                          | (Available when using Azure Blob Storage files location with Service Principal authentication mode) Client Secret. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                |
| Storage account name       | `storage_account_name`                   | (Available when using Azure Blob Storage files location with Credential Chain or Service Principal authentication mode) Storage account name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                     |
| Access Key                 | `user`                                   | (Available when using Google Cloud Storage files location) The interoperability access key. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                                                                                                                               |
| Secret                     | `password`                               | (Available when using Google Cloud Storage files location) The interoperability secret. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.                                                                                                                                                                                                                                                   |
| Virtual schema name        | `directories`                            | An alias for the parent directory with data. The virtual schema name is a key of the directories mapping.                                                                                                                                                                                                                  |
| Path                       | `directories`                            | The path prefix to the parent directory with data. The path must be absolute. The virtual schema name is a value of the directories mapping.                                                                                                                                                                               |
| JDBC connection property   |                                          | Optional setting. DQOps supports using the JDBC driver to access DuckDB.                                                                                                                                                                                                                                                   |

Click the link for a detailed description of the next steps to add the connection in a supported file format.

- [CSV](./csv.md#Setting-the-path-to-data)
- [JSON](./json.md#Setting-the-path-to-data)
- [Parquet](./parquet.md#Setting-the-path-to-data)