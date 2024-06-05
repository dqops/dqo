---
title: dqo connection command-line command
---
# dqo connection command-line command
The reference of the **connection** command in DQOps. Modify or list connections



___

## dqo connection list

List connections that match a given condition


**Description**


Lists all the created connections for the logged-in user that match the conditions specified in the options. It allows the user to filter connections based on various parameters.




**Command-line synopsis**

```
$ dqo [dqo options...] connection list [-h] [-fw] [-hl] [-n=<name>] [-of=<outputFormat>]
                 [-d=<dimensions>]... [-l=<labels>]...

```

**DQOps shell synopsis**

```
dqo> connection list [-h] [-fw] [-hl] [-n=<name>] [-of=<outputFormat>]
                 [-d=<dimensions>]... [-l=<labels>]...

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="connection list-d" class="no-wrap-code">`-d`</div><div id="connection list--dimension" class="no-wrap-code">`--dimension`</div>|Dimension filter| ||
|<div id="connection list-fw" class="no-wrap-code">`-fw`</div><div id="connection list--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="connection list--headless" class="no-wrap-code">`--headless`</div><div id="connection list-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="connection list-h" class="no-wrap-code">`-h`</div><div id="connection list--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="connection list-l" class="no-wrap-code">`-l`</div><div id="connection list--label" class="no-wrap-code">`--label`</div>|Label filter| ||
|<div id="connection list-n" class="no-wrap-code">`-n`</div><div id="connection list--name" class="no-wrap-code">`--name`</div>|Connection name filter| ||
|<div id="connection list-of" class="no-wrap-code">`-of`</div><div id="connection list--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo connection add

Add a connection with specified details


**Description**


Creates a new connection to the database with the specified details such as connection name, database type, hostname, username, and password. It allows the user to connect to the database from the application to perform various operations on the database.




**Command-line synopsis**

```
$ dqo [dqo options...] connection add [-h] [-fw] [-hl] [--sqlserver-disable-encryption]
                [--athena-aws-authentication-mode=<awsAuthenticationMode>]
                [--athena-output-location=<athenaOutputLocation>]
                [--athena-region=<athenaRegion>]
                [--athena-work-group=<athenaWorkGroup>]
                [--bigquery-authentication-mode=<authenticationMode>]
                [--bigquery-billing-project-id=<billingProjectId>]
                [--bigquery-jobs-create-project=<jobsCreateProject>]
                [--bigquery-json-key-content=<jsonKeyContent>]
                [--bigquery-json-key-path=<jsonKeyPath>]
                [--bigquery-quota-project-id=<quotaProjectId>]
                [--bigquery-source-project-id=<sourceProjectId>]
                [--databricks-access-token=<accessToken>]
                [--databricks-catalog=<catalog>] [--databricks-host=<host>]
                [--databricks-http-path=<httpPath>]
                [--databricks-password=<password>] [--databricks-port=<port>]
                [--databricks-user=<user>]
                [--duckdb-aws-authentication-mode=<awsAuthenticationMode>]
                [--duckdb-azure-account-name=<accountName>]
                [--duckdb-azure-authentication-mode=<azureAuthenticationMode>]
                [--duckdb-azure-client-id=<clientId>]
                [--duckdb-azure-client-secret=<clientSecret>]
                [--duckdb-azure-tenant-id=<tenantId>]
                [--duckdb-database=<database>]
                [--duckdb-directories=<directoriesString>]
                [--duckdb-files-format-type=<filesFormatType>]
                [--duckdb-password=<password>] [--duckdb-read-mode=<readMode>]
                [--duckdb-region=<region>]
                [--duckdb-storage-type=<storageType>] [--duckdb-user=<user>]
                [--mysql-database=<database>]
                [--mysql-engine=<mysqlEngineType>] [--mysql-host=<host>]
                [--mysql-password=<password>] [--mysql-port=<port>]
                [--mysql-sslmode=<sslmode>] [--mysql-user=<user>] [-n=<name>]
                [-of=<outputFormat>] [--oracle-database=<database>]
                [--oracle-host=<host>]
                [--oracle-initialization-sql=<initializationSql>]
                [--oracle-password=<password>] [--oracle-port=<port>]
                [--oracle-user=<user>] [--postgresql-database=<database>]
                [--postgresql-host=<host>] [--postgresql-options=<options>]
                [--postgresql-password=<password>] [--postgresql-port=<port>]
                [--postgresql-sslmode=<sslmode>] [--postgresql-user=<user>]
                [--presto-database=<database>] [--presto-host=<host>]
                [--presto-password=<password>] [--presto-port=<port>]
                [--presto-user=<user>]
                [--redshift-authentication-mode=<redshiftAuthenticationMode>]
                [--redshift-database=<database>] [--redshift-host=<host>]
                [--redshift-password=<password>] [--redshift-port=<port>]
                [--redshift-user=<user>]
                [--single-store-parameters-spec=<singleStoreDbParametersSpec>]
                [--snowflake-account=<account>]
                [--snowflake-database=<database>]
                [--snowflake-password=<password>] [--snowflake-role=<role>]
                [--snowflake-user=<user>] [--snowflake-warehouse=<warehouse>]
                [--spark-host=<host>] [--spark-password=<password>]
                [--spark-port=<port>] [--spark-user=<user>]
                [--sqlserver-authentication-mode=<authenticationMode>]
                [--sqlserver-database=<database>] [--sqlserver-host=<host>]
                [--sqlserver-password=<password>] [--sqlserver-port=<port>]
                [--sqlserver-user=<user>] [-t=<providerType>]
                [--trino-catalog=<catalog>] [--trino-engine=<trinoEngineType>]
                [--trino-host=<host>] [--trino-password=<password>]
                [--trino-port=<port>] [--trino-user=<user>]
                [-D=<String=String>]... [-Duck=<String=String>]...
                [-E=<String=String>]... [-F=<String=String>]...
                [-K=<String=String>]... [-M=<String=String>]...
                [-O=<String=String>]... [-P=<String=String>]...
                [-R=<String=String>]... [-S=<String=String>]...
                [-T=<String=String>]...

```

**DQOps shell synopsis**

```
dqo> connection add [-h] [-fw] [-hl] [--sqlserver-disable-encryption]
                [--athena-aws-authentication-mode=<awsAuthenticationMode>]
                [--athena-output-location=<athenaOutputLocation>]
                [--athena-region=<athenaRegion>]
                [--athena-work-group=<athenaWorkGroup>]
                [--bigquery-authentication-mode=<authenticationMode>]
                [--bigquery-billing-project-id=<billingProjectId>]
                [--bigquery-jobs-create-project=<jobsCreateProject>]
                [--bigquery-json-key-content=<jsonKeyContent>]
                [--bigquery-json-key-path=<jsonKeyPath>]
                [--bigquery-quota-project-id=<quotaProjectId>]
                [--bigquery-source-project-id=<sourceProjectId>]
                [--databricks-access-token=<accessToken>]
                [--databricks-catalog=<catalog>] [--databricks-host=<host>]
                [--databricks-http-path=<httpPath>]
                [--databricks-password=<password>] [--databricks-port=<port>]
                [--databricks-user=<user>]
                [--duckdb-aws-authentication-mode=<awsAuthenticationMode>]
                [--duckdb-azure-account-name=<accountName>]
                [--duckdb-azure-authentication-mode=<azureAuthenticationMode>]
                [--duckdb-azure-client-id=<clientId>]
                [--duckdb-azure-client-secret=<clientSecret>]
                [--duckdb-azure-tenant-id=<tenantId>]
                [--duckdb-database=<database>]
                [--duckdb-directories=<directoriesString>]
                [--duckdb-files-format-type=<filesFormatType>]
                [--duckdb-password=<password>] [--duckdb-read-mode=<readMode>]
                [--duckdb-region=<region>]
                [--duckdb-storage-type=<storageType>] [--duckdb-user=<user>]
                [--mysql-database=<database>]
                [--mysql-engine=<mysqlEngineType>] [--mysql-host=<host>]
                [--mysql-password=<password>] [--mysql-port=<port>]
                [--mysql-sslmode=<sslmode>] [--mysql-user=<user>] [-n=<name>]
                [-of=<outputFormat>] [--oracle-database=<database>]
                [--oracle-host=<host>]
                [--oracle-initialization-sql=<initializationSql>]
                [--oracle-password=<password>] [--oracle-port=<port>]
                [--oracle-user=<user>] [--postgresql-database=<database>]
                [--postgresql-host=<host>] [--postgresql-options=<options>]
                [--postgresql-password=<password>] [--postgresql-port=<port>]
                [--postgresql-sslmode=<sslmode>] [--postgresql-user=<user>]
                [--presto-database=<database>] [--presto-host=<host>]
                [--presto-password=<password>] [--presto-port=<port>]
                [--presto-user=<user>]
                [--redshift-authentication-mode=<redshiftAuthenticationMode>]
                [--redshift-database=<database>] [--redshift-host=<host>]
                [--redshift-password=<password>] [--redshift-port=<port>]
                [--redshift-user=<user>]
                [--single-store-parameters-spec=<singleStoreDbParametersSpec>]
                [--snowflake-account=<account>]
                [--snowflake-database=<database>]
                [--snowflake-password=<password>] [--snowflake-role=<role>]
                [--snowflake-user=<user>] [--snowflake-warehouse=<warehouse>]
                [--spark-host=<host>] [--spark-password=<password>]
                [--spark-port=<port>] [--spark-user=<user>]
                [--sqlserver-authentication-mode=<authenticationMode>]
                [--sqlserver-database=<database>] [--sqlserver-host=<host>]
                [--sqlserver-password=<password>] [--sqlserver-port=<port>]
                [--sqlserver-user=<user>] [-t=<providerType>]
                [--trino-catalog=<catalog>] [--trino-engine=<trinoEngineType>]
                [--trino-host=<host>] [--trino-password=<password>]
                [--trino-port=<port>] [--trino-user=<user>]
                [-D=<String=String>]... [-Duck=<String=String>]...
                [-E=<String=String>]... [-F=<String=String>]...
                [-K=<String=String>]... [-M=<String=String>]...
                [-O=<String=String>]... [-P=<String=String>]...
                [-R=<String=String>]... [-S=<String=String>]...
                [-T=<String=String>]...

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="connection add--athena-aws-authentication-mode" class="no-wrap-code">`--athena-aws-authentication-mode`</div>|The authentication mode for AWS Athena. Supports also a null configuration with a custom environment variable.| |*iam*<br/>*default_credentials*<br/>|
|<div id="connection add--athena-output-location" class="no-wrap-code">`--athena-output-location`</div>|The location in Amazon S3 where query results will be stored. Supports also a null configuration with a custom environment variable.| ||
|<div id="connection add--athena-region" class="no-wrap-code">`--athena-region`</div>|The AWS Athena Region where queries will be run. Supports also a null configuration with a custom environment variable.| ||
|<div id="connection add--athena-work-group" class="no-wrap-code">`--athena-work-group`</div>|The Athena WorkGroup in which queries will run. Supports also a null configuration with a custom environment variable.| ||
|<div id="connection add--bigquery-authentication-mode" class="no-wrap-code">`--bigquery-authentication-mode`</div>|Bigquery authentication mode. The default value uses the current GCP application default credentials. The default GCP credentials is the Service Account of a VM in GCP cloud, a GCP JSON key file whose path is in the GOOGLE_APPLICATION_CREDENTIALS environment variable, or it is the default GCP credentials obtained on a user's computer by running 'gcloud auth application-default login' from the command line.| |*google_application_credentials*<br/>*json_key_content*<br/>*json_key_path*<br/>|
|<div id="connection add--bigquery-billing-project-id" class="no-wrap-code">`--bigquery-billing-project-id`</div>|Bigquery billing GCP project id. This is the project used as the default GCP project. The calling user must have a bigquery.jobs.create permission in this project.| ||
|<div id="connection add--bigquery-jobs-create-project" class="no-wrap-code">`--bigquery-jobs-create-project`</div>|Configures the way how to select the project that will be used to start BigQuery jobs and will be used for billing. The user/service identified by the credentials must have bigquery.jobs.create permission in that project.| |*create_jobs_in_source_project*<br/>*create_jobs_in_default_project_from_credentials*<br/>*create_jobs_in_selected_billing_project_id*<br/>|
|<div id="connection add--bigquery-json-key-content" class="no-wrap-code">`--bigquery-json-key-content`</div>|Bigquery service account key content as JSON.| ||
|<div id="connection add--bigquery-json-key-path" class="no-wrap-code">`--bigquery-json-key-path`</div>|Path to a GCP service account key JSON file used to authenticate to Bigquery.| ||
|<div id="connection add--bigquery-quota-project-id" class="no-wrap-code">`--bigquery-quota-project-id`</div>|Bigquery quota GCP project id.| ||
|<div id="connection add--bigquery-source-project-id" class="no-wrap-code">`--bigquery-source-project-id`</div>|Bigquery source GCP project id. This is the project that has datasets that will be imported.| ||
|<div id="connection add--databricks-access-token" class="no-wrap-code">`--databricks-access-token`</div>|Databricks access token for the warehouse.| ||
|<div id="connection add--databricks-catalog" class="no-wrap-code">`--databricks-catalog`</div>|Databricks catalog name.| ||
|<div id="connection add--databricks-host" class="no-wrap-code">`--databricks-host`</div>|Databricks host name| ||
|<div id="connection add--databricks-http-path" class="no-wrap-code">`--databricks-http-path`</div>|Databricks http path to the warehouse. For example: /sql/1.0/warehouses/<warehouse instance id>| ||
|<div id="connection add--databricks-password" class="no-wrap-code">`--databricks-password`</div>|Databricks database password.| ||
|<div id="connection add--databricks-port" class="no-wrap-code">`--databricks-port`</div>|Databricks port number| ||
|<div id="connection add--databricks-user" class="no-wrap-code">`--databricks-user`</div>|Databricks user name.| ||
|<div id="connection add--duckdb-aws-authentication-mode" class="no-wrap-code">`--duckdb-aws-authentication-mode`</div>|The authentication mode for AWS. Supports also a null configuration with a custom environment variable.| |*iam*<br/>*default_credentials*<br/>|
|<div id="connection add--duckdb-azure-account-name" class="no-wrap-code">`--duckdb-azure-account-name`</div>|Azure Storage Account Name used by DuckDB Secret Manager. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--duckdb-azure-authentication-mode" class="no-wrap-code">`--duckdb-azure-authentication-mode`</div>|The authentication mode for Azure. Supports also a null configuration with a custom environment variable.| |*connection_string*<br/>*credential_chain*<br/>*service_principal*<br/>*default_credentials*<br/>|
|<div id="connection add--duckdb-azure-client-id" class="no-wrap-code">`--duckdb-azure-client-id`</div>|Azure Client ID used by DuckDB Secret Manager. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--duckdb-azure-client-secret" class="no-wrap-code">`--duckdb-azure-client-secret`</div>|Azure Client Secret used by DuckDB Secret Manager. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--duckdb-azure-tenant-id" class="no-wrap-code">`--duckdb-azure-tenant-id`</div>|Azure Tenant ID used by DuckDB Secret Manager. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--duckdb-database" class="no-wrap-code">`--duckdb-database`</div>|DuckDB database name for in-memory read mode. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--duckdb-directories" class="no-wrap-code">`--duckdb-directories`</div>|Virtual schema name to directory mappings. The path must be an absolute path.| ||
|<div id="connection add--duckdb-files-format-type" class="no-wrap-code">`--duckdb-files-format-type`</div>|Type of source files format for DuckDB.| |*csv*<br/>*json*<br/>*parquet*<br/>|
|<div id="connection add--duckdb-password" class="no-wrap-code">`--duckdb-password`</div>|DuckDB password for a remote storage type. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--duckdb-read-mode" class="no-wrap-code">`--duckdb-read-mode`</div>|DuckDB read mode.| |*in_memory*<br/>*files*<br/>|
|<div id="connection add--duckdb-region" class="no-wrap-code">`--duckdb-region`</div>|The region for the storage credentials. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--duckdb-storage-type" class="no-wrap-code">`--duckdb-storage-type`</div>|The storage type.| |*local*<br/>*s3*<br/>*azure*<br/>|
|<div id="connection add--duckdb-user" class="no-wrap-code">`--duckdb-user`</div>|DuckDB user name for a remote storage type. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add-fw" class="no-wrap-code">`-fw`</div><div id="connection add--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="connection add--headless" class="no-wrap-code">`--headless`</div><div id="connection add-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="connection add-h" class="no-wrap-code">`-h`</div><div id="connection add--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="connection add--mysql-database" class="no-wrap-code">`--mysql-database`</div>|MySQL database name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--mysql-engine" class="no-wrap-code">`--mysql-engine`</div>|MySQL engine type. Supports also a null configuration with a custom environment variable.| |*mysql*<br/>*singlestoredb*<br/>|
|<div id="connection add--mysql-host" class="no-wrap-code">`--mysql-host`</div>|MySQL host name| ||
|<div id="connection add--mysql-password" class="no-wrap-code">`--mysql-password`</div>|MySQL database password. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--mysql-port" class="no-wrap-code">`--mysql-port`</div>|MySQL port number| ||
|<div id="connection add--mysql-sslmode" class="no-wrap-code">`--mysql-sslmode`</div>|SslMode MySQL connection parameter| |*DISABLED*<br/>*PREFERRED*<br/>*REQUIRED*<br/>*VERIFY_CA*<br/>*VERIFY_IDENTITY*<br/>|
|<div id="connection add--mysql-user" class="no-wrap-code">`--mysql-user`</div>|MySQL user name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add-n" class="no-wrap-code">`-n`</div><div id="connection add--name" class="no-wrap-code">`--name`</div>|Connection name| ||
|<div id="connection add--oracle-database" class="no-wrap-code">`--oracle-database`</div>|Oracle database name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--oracle-host" class="no-wrap-code">`--oracle-host`</div>|Oracle host name| ||
|<div id="connection add--oracle-initialization-sql" class="no-wrap-code">`--oracle-initialization-sql`</div>|Custom SQL that is executed after connecting to Oracle. This SQL script can configure the default language, for example: alter session set NLS_DATE_FORMAT='YYYY-DD-MM HH24:MI:SS'| ||
|<div id="connection add--oracle-password" class="no-wrap-code">`--oracle-password`</div>|Oracle database password. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--oracle-port" class="no-wrap-code">`--oracle-port`</div>|Oracle port number| ||
|<div id="connection add--oracle-user" class="no-wrap-code">`--oracle-user`</div>|Oracle user name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add-of" class="no-wrap-code">`-of`</div><div id="connection add--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="connection add--postgresql-database" class="no-wrap-code">`--postgresql-database`</div>|PostgreSQL database name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--postgresql-host" class="no-wrap-code">`--postgresql-host`</div>|PostgreSQL host name| ||
|<div id="connection add--postgresql-options" class="no-wrap-code">`--postgresql-options`</div>|PostgreSQL connection 'options' initialization parameter. For example setting this to -c statement_timeout=5min would set the statement timeout parameter for this session to 5 minutes.| ||
|<div id="connection add--postgresql-password" class="no-wrap-code">`--postgresql-password`</div>|PostgreSQL database password. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--postgresql-port" class="no-wrap-code">`--postgresql-port`</div>|PostgreSQL port number| ||
|<div id="connection add--postgresql-sslmode" class="no-wrap-code">`--postgresql-sslmode`</div>|Connect to PostgreSQL using sslmode connection parameter| |*disable*<br/>*allow*<br/>*prefer*<br/>*require*<br/>*verify_ca*<br/>*verify_full*<br/>|
|<div id="connection add--postgresql-user" class="no-wrap-code">`--postgresql-user`</div>|PostgreSQL user name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--presto-database" class="no-wrap-code">`--presto-database`</div>|Presto database name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--presto-host" class="no-wrap-code">`--presto-host`</div>|Presto host name| ||
|<div id="connection add--presto-password" class="no-wrap-code">`--presto-password`</div>|Presto database password. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--presto-port" class="no-wrap-code">`--presto-port`</div>|Presto port number| ||
|<div id="connection add--presto-user" class="no-wrap-code">`--presto-user`</div>|Presto user name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add-t" class="no-wrap-code">`-t`</div><div id="connection add--provider" class="no-wrap-code">`--provider`</div>|Connection provider type| |*bigquery*<br/>*databricks*<br/>*mysql*<br/>*oracle*<br/>*postgresql*<br/>*duckdb*<br/>*presto*<br/>*redshift*<br/>*snowflake*<br/>*spark*<br/>*sqlserver*<br/>*trino*<br/>|
|<div id="connection add--redshift-authentication-mode" class="no-wrap-code">`--redshift-authentication-mode`</div>|The authentication mode for AWS. Supports also a null configuration with a custom environment variable.| |*iam*<br/>*default_credentials*<br/>*user_password*<br/>|
|<div id="connection add--redshift-database" class="no-wrap-code">`--redshift-database`</div>|Redshift database name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--redshift-host" class="no-wrap-code">`--redshift-host`</div>|Redshift host name| ||
|<div id="connection add--redshift-password" class="no-wrap-code">`--redshift-password`</div>|Redshift database password. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--redshift-port" class="no-wrap-code">`--redshift-port`</div>|Redshift port number| ||
|<div id="connection add--redshift-user" class="no-wrap-code">`--redshift-user`</div>|Redshift user name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--single-store-parameters-spec" class="no-wrap-code">`--single-store-parameters-spec`</div>|Single Store DB parameters spec.| ||
|<div id="connection add--snowflake-account" class="no-wrap-code">`--snowflake-account`</div>|Snowflake account name, e.q. <account>, <account>-<locator>, <account>.<region> or <account>.<region>.<platform>.| ||
|<div id="connection add--snowflake-database" class="no-wrap-code">`--snowflake-database`</div>|Snowflake database name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--snowflake-password" class="no-wrap-code">`--snowflake-password`</div>|Snowflake database password. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--snowflake-role" class="no-wrap-code">`--snowflake-role`</div>|Snowflake role name.| ||
|<div id="connection add--snowflake-user" class="no-wrap-code">`--snowflake-user`</div>|Snowflake user name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--snowflake-warehouse" class="no-wrap-code">`--snowflake-warehouse`</div>|Snowflake warehouse name.| ||
|<div id="connection add--spark-host" class="no-wrap-code">`--spark-host`</div>|Spark host name| ||
|<div id="connection add--spark-password" class="no-wrap-code">`--spark-password`</div>|Spark database password. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--spark-port" class="no-wrap-code">`--spark-port`</div>|Spark port number| ||
|<div id="connection add--spark-user" class="no-wrap-code">`--spark-user`</div>|Spark user name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--sqlserver-authentication-mode" class="no-wrap-code">`--sqlserver-authentication-mode`</div>|Authenticaiton mode for the SQL Server. The value can be in the null format to use dynamic substitution.| |*sql_password*<br/>*active_directory_password*<br/>*active_directory_service_principal*<br/>*active_directory_default*<br/>|
|<div id="connection add--sqlserver-database" class="no-wrap-code">`--sqlserver-database`</div>|SQL Server database name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--sqlserver-disable-encryption" class="no-wrap-code">`--sqlserver-disable-encryption`</div>|Disable SSL encryption parameter. The default value is false. You may need to disable encryption when SQL Server is started in Docker.| ||
|<div id="connection add--sqlserver-host" class="no-wrap-code">`--sqlserver-host`</div>|SQL Server host name| ||
|<div id="connection add--sqlserver-password" class="no-wrap-code">`--sqlserver-password`</div>|SQL Server database password. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--sqlserver-port" class="no-wrap-code">`--sqlserver-port`</div>|SQL Server port number| ||
|<div id="connection add--sqlserver-user" class="no-wrap-code">`--sqlserver-user`</div>|SQL Server user name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--trino-catalog" class="no-wrap-code">`--trino-catalog`</div>|The Trino catalog that contains the databases and the tables that will be accessed with the driver. Supports also a null configuration with a custom environment variable.| ||
|<div id="connection add--trino-engine" class="no-wrap-code">`--trino-engine`</div>|Trino engine type.| |*trino*<br/>*athena*<br/>|
|<div id="connection add--trino-host" class="no-wrap-code">`--trino-host`</div>|Trino host name.| ||
|<div id="connection add--trino-password" class="no-wrap-code">`--trino-password`</div>|Trino database password. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add--trino-port" class="no-wrap-code">`--trino-port`</div>|Trino port number.| ||
|<div id="connection add--trino-user" class="no-wrap-code">`--trino-user`</div>|Trino user name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection add-D" class="no-wrap-code">`-D`</div>|Databricks additional properties that are added to the JDBC connection string| ||
|<div id="connection add-Duck" class="no-wrap-code">`-Duck`</div>|DuckDB additional properties that are added to the JDBC connection string| ||
|<div id="connection add-E" class="no-wrap-code">`-E`</div>|Presto additional properties that are added to the JDBC connection string.| ||
|<div id="connection add-F" class="no-wrap-code">`-F`</div>|Snowflake additional properties that are added to the JDBC connection string| ||
|<div id="connection add-K" class="no-wrap-code">`-K`</div>|Spark additional properties that are added to the JDBC connection string| ||
|<div id="connection add-M" class="no-wrap-code">`-M`</div>|MySQL additional properties that are added to the JDBC connection string| ||
|<div id="connection add-O" class="no-wrap-code">`-O`</div>|Oracle's additional properties that are added to the JDBC connection string| ||
|<div id="connection add-P" class="no-wrap-code">`-P`</div>|PostgreSQL additional properties that are added to the JDBC connection string| ||
|<div id="connection add-R" class="no-wrap-code">`-R`</div>|Redshift additional properties that are added to the JDBC connection string| ||
|<div id="connection add-S" class="no-wrap-code">`-S`</div>|SQL Server additional properties that are added to the JDBC connection string| ||
|<div id="connection add-T" class="no-wrap-code">`-T`</div>|Trino additional properties that are added to the JDBC connection string| ||






___

## dqo connection remove

Remove the connection(s) that match a given condition


**Description**


Removes the connection or connections that match the conditions specified in the options. It allows the user to remove any unwanted connections that are no longer needed.




**Command-line synopsis**

```
$ dqo [dqo options...] connection remove [-h] [-fw] [-hl] [-n=<name>] [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> connection remove [-h] [-fw] [-hl] [-n=<name>] [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="connection remove-fw" class="no-wrap-code">`-fw`</div><div id="connection remove--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="connection remove--headless" class="no-wrap-code">`--headless`</div><div id="connection remove-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="connection remove-h" class="no-wrap-code">`-h`</div><div id="connection remove--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="connection remove-n" class="no-wrap-code">`-n`</div><div id="connection remove--name" class="no-wrap-code">`--name`</div>|Connection name| ||
|<div id="connection remove-of" class="no-wrap-code">`-of`</div><div id="connection remove--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo connection update

Update the connection(s) that match a given condition


**Description**


Update the connection or connections that match the conditions specified in the options with new details. It allows the user to modify existing connections in the application.




**Command-line synopsis**

```
$ dqo [dqo options...] connection update [-h] [-fw] [-hl] [--sqlserver-disable-encryption]
                   [--athena-aws-authentication-mode=<awsAuthenticationMode>]
                   [--athena-output-location=<athenaOutputLocation>]
                   [--athena-region=<athenaRegion>]
                   [--athena-work-group=<athenaWorkGroup>]
                   [--bigquery-authentication-mode=<authenticationMode>]
                   [--bigquery-billing-project-id=<billingProjectId>]
                   [--bigquery-jobs-create-project=<jobsCreateProject>]
                   [--bigquery-json-key-content=<jsonKeyContent>]
                   [--bigquery-json-key-path=<jsonKeyPath>]
                   [--bigquery-quota-project-id=<quotaProjectId>]
                   [--bigquery-source-project-id=<sourceProjectId>]
                   [--databricks-access-token=<accessToken>]
                   [--databricks-catalog=<catalog>] [--databricks-host=<host>]
                   [--databricks-http-path=<httpPath>]
                   [--databricks-password=<password>]
                   [--databricks-port=<port>] [--databricks-user=<user>]
                   [--duckdb-aws-authentication-mode=<awsAuthenticationMode>]
                   [--duckdb-azure-account-name=<accountName>]
                   [--duckdb-azure-authentication-mode=<azureAuthenticationMode>
                   ] [--duckdb-azure-client-id=<clientId>]
                   [--duckdb-azure-client-secret=<clientSecret>]
                   [--duckdb-azure-tenant-id=<tenantId>]
                   [--duckdb-database=<database>]
                   [--duckdb-directories=<directoriesString>]
                   [--duckdb-files-format-type=<filesFormatType>]
                   [--duckdb-password=<password>]
                   [--duckdb-read-mode=<readMode>] [--duckdb-region=<region>]
                   [--duckdb-storage-type=<storageType>] [--duckdb-user=<user>]
                   [--mysql-database=<database>]
                   [--mysql-engine=<mysqlEngineType>] [--mysql-host=<host>]
                   [--mysql-password=<password>] [--mysql-port=<port>]
                   [--mysql-sslmode=<sslmode>] [--mysql-user=<user>]
                   [-n=<name>] [-of=<outputFormat>]
                   [--oracle-database=<database>] [--oracle-host=<host>]
                   [--oracle-initialization-sql=<initializationSql>]
                   [--oracle-password=<password>] [--oracle-port=<port>]
                   [--oracle-user=<user>] [--postgresql-database=<database>]
                   [--postgresql-host=<host>] [--postgresql-options=<options>]
                   [--postgresql-password=<password>]
                   [--postgresql-port=<port>] [--postgresql-sslmode=<sslmode>]
                   [--postgresql-user=<user>] [--presto-database=<database>]
                   [--presto-host=<host>] [--presto-password=<password>]
                   [--presto-port=<port>] [--presto-user=<user>]
                   [--redshift-authentication-mode=<redshiftAuthenticationMode>]
                    [--redshift-database=<database>] [--redshift-host=<host>]
                   [--redshift-password=<password>] [--redshift-port=<port>]
                   [--redshift-user=<user>]
                   [--single-store-parameters-spec=<singleStoreDbParametersSpec>
                   ] [--snowflake-account=<account>]
                   [--snowflake-database=<database>]
                   [--snowflake-password=<password>] [--snowflake-role=<role>]
                   [--snowflake-user=<user>]
                   [--snowflake-warehouse=<warehouse>] [--spark-host=<host>]
                   [--spark-password=<password>] [--spark-port=<port>]
                   [--spark-user=<user>]
                   [--sqlserver-authentication-mode=<authenticationMode>]
                   [--sqlserver-database=<database>] [--sqlserver-host=<host>]
                   [--sqlserver-password=<password>] [--sqlserver-port=<port>]
                   [--sqlserver-user=<user>] [--trino-catalog=<catalog>]
                   [--trino-engine=<trinoEngineType>] [--trino-host=<host>]
                   [--trino-password=<password>] [--trino-port=<port>]
                   [--trino-user=<user>] [-D=<String=String>]...
                   [-Duck=<String=String>]... [-E=<String=String>]...
                   [-F=<String=String>]... [-K=<String=String>]...
                   [-M=<String=String>]... [-O=<String=String>]...
                   [-P=<String=String>]... [-R=<String=String>]...
                   [-S=<String=String>]... [-T=<String=String>]...

```

**DQOps shell synopsis**

```
dqo> connection update [-h] [-fw] [-hl] [--sqlserver-disable-encryption]
                   [--athena-aws-authentication-mode=<awsAuthenticationMode>]
                   [--athena-output-location=<athenaOutputLocation>]
                   [--athena-region=<athenaRegion>]
                   [--athena-work-group=<athenaWorkGroup>]
                   [--bigquery-authentication-mode=<authenticationMode>]
                   [--bigquery-billing-project-id=<billingProjectId>]
                   [--bigquery-jobs-create-project=<jobsCreateProject>]
                   [--bigquery-json-key-content=<jsonKeyContent>]
                   [--bigquery-json-key-path=<jsonKeyPath>]
                   [--bigquery-quota-project-id=<quotaProjectId>]
                   [--bigquery-source-project-id=<sourceProjectId>]
                   [--databricks-access-token=<accessToken>]
                   [--databricks-catalog=<catalog>] [--databricks-host=<host>]
                   [--databricks-http-path=<httpPath>]
                   [--databricks-password=<password>]
                   [--databricks-port=<port>] [--databricks-user=<user>]
                   [--duckdb-aws-authentication-mode=<awsAuthenticationMode>]
                   [--duckdb-azure-account-name=<accountName>]
                   [--duckdb-azure-authentication-mode=<azureAuthenticationMode>
                   ] [--duckdb-azure-client-id=<clientId>]
                   [--duckdb-azure-client-secret=<clientSecret>]
                   [--duckdb-azure-tenant-id=<tenantId>]
                   [--duckdb-database=<database>]
                   [--duckdb-directories=<directoriesString>]
                   [--duckdb-files-format-type=<filesFormatType>]
                   [--duckdb-password=<password>]
                   [--duckdb-read-mode=<readMode>] [--duckdb-region=<region>]
                   [--duckdb-storage-type=<storageType>] [--duckdb-user=<user>]
                   [--mysql-database=<database>]
                   [--mysql-engine=<mysqlEngineType>] [--mysql-host=<host>]
                   [--mysql-password=<password>] [--mysql-port=<port>]
                   [--mysql-sslmode=<sslmode>] [--mysql-user=<user>]
                   [-n=<name>] [-of=<outputFormat>]
                   [--oracle-database=<database>] [--oracle-host=<host>]
                   [--oracle-initialization-sql=<initializationSql>]
                   [--oracle-password=<password>] [--oracle-port=<port>]
                   [--oracle-user=<user>] [--postgresql-database=<database>]
                   [--postgresql-host=<host>] [--postgresql-options=<options>]
                   [--postgresql-password=<password>]
                   [--postgresql-port=<port>] [--postgresql-sslmode=<sslmode>]
                   [--postgresql-user=<user>] [--presto-database=<database>]
                   [--presto-host=<host>] [--presto-password=<password>]
                   [--presto-port=<port>] [--presto-user=<user>]
                   [--redshift-authentication-mode=<redshiftAuthenticationMode>]
                    [--redshift-database=<database>] [--redshift-host=<host>]
                   [--redshift-password=<password>] [--redshift-port=<port>]
                   [--redshift-user=<user>]
                   [--single-store-parameters-spec=<singleStoreDbParametersSpec>
                   ] [--snowflake-account=<account>]
                   [--snowflake-database=<database>]
                   [--snowflake-password=<password>] [--snowflake-role=<role>]
                   [--snowflake-user=<user>]
                   [--snowflake-warehouse=<warehouse>] [--spark-host=<host>]
                   [--spark-password=<password>] [--spark-port=<port>]
                   [--spark-user=<user>]
                   [--sqlserver-authentication-mode=<authenticationMode>]
                   [--sqlserver-database=<database>] [--sqlserver-host=<host>]
                   [--sqlserver-password=<password>] [--sqlserver-port=<port>]
                   [--sqlserver-user=<user>] [--trino-catalog=<catalog>]
                   [--trino-engine=<trinoEngineType>] [--trino-host=<host>]
                   [--trino-password=<password>] [--trino-port=<port>]
                   [--trino-user=<user>] [-D=<String=String>]...
                   [-Duck=<String=String>]... [-E=<String=String>]...
                   [-F=<String=String>]... [-K=<String=String>]...
                   [-M=<String=String>]... [-O=<String=String>]...
                   [-P=<String=String>]... [-R=<String=String>]...
                   [-S=<String=String>]... [-T=<String=String>]...

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="connection update--athena-aws-authentication-mode" class="no-wrap-code">`--athena-aws-authentication-mode`</div>|The authentication mode for AWS Athena. Supports also a null configuration with a custom environment variable.| |*iam*<br/>*default_credentials*<br/>|
|<div id="connection update--athena-output-location" class="no-wrap-code">`--athena-output-location`</div>|The location in Amazon S3 where query results will be stored. Supports also a null configuration with a custom environment variable.| ||
|<div id="connection update--athena-region" class="no-wrap-code">`--athena-region`</div>|The AWS Athena Region where queries will be run. Supports also a null configuration with a custom environment variable.| ||
|<div id="connection update--athena-work-group" class="no-wrap-code">`--athena-work-group`</div>|The Athena WorkGroup in which queries will run. Supports also a null configuration with a custom environment variable.| ||
|<div id="connection update--bigquery-authentication-mode" class="no-wrap-code">`--bigquery-authentication-mode`</div>|Bigquery authentication mode. The default value uses the current GCP application default credentials. The default GCP credentials is the Service Account of a VM in GCP cloud, a GCP JSON key file whose path is in the GOOGLE_APPLICATION_CREDENTIALS environment variable, or it is the default GCP credentials obtained on a user's computer by running 'gcloud auth application-default login' from the command line.| |*google_application_credentials*<br/>*json_key_content*<br/>*json_key_path*<br/>|
|<div id="connection update--bigquery-billing-project-id" class="no-wrap-code">`--bigquery-billing-project-id`</div>|Bigquery billing GCP project id. This is the project used as the default GCP project. The calling user must have a bigquery.jobs.create permission in this project.| ||
|<div id="connection update--bigquery-jobs-create-project" class="no-wrap-code">`--bigquery-jobs-create-project`</div>|Configures the way how to select the project that will be used to start BigQuery jobs and will be used for billing. The user/service identified by the credentials must have bigquery.jobs.create permission in that project.| |*create_jobs_in_source_project*<br/>*create_jobs_in_default_project_from_credentials*<br/>*create_jobs_in_selected_billing_project_id*<br/>|
|<div id="connection update--bigquery-json-key-content" class="no-wrap-code">`--bigquery-json-key-content`</div>|Bigquery service account key content as JSON.| ||
|<div id="connection update--bigquery-json-key-path" class="no-wrap-code">`--bigquery-json-key-path`</div>|Path to a GCP service account key JSON file used to authenticate to Bigquery.| ||
|<div id="connection update--bigquery-quota-project-id" class="no-wrap-code">`--bigquery-quota-project-id`</div>|Bigquery quota GCP project id.| ||
|<div id="connection update--bigquery-source-project-id" class="no-wrap-code">`--bigquery-source-project-id`</div>|Bigquery source GCP project id. This is the project that has datasets that will be imported.| ||
|<div id="connection update--databricks-access-token" class="no-wrap-code">`--databricks-access-token`</div>|Databricks access token for the warehouse.| ||
|<div id="connection update--databricks-catalog" class="no-wrap-code">`--databricks-catalog`</div>|Databricks catalog name.| ||
|<div id="connection update--databricks-host" class="no-wrap-code">`--databricks-host`</div>|Databricks host name| ||
|<div id="connection update--databricks-http-path" class="no-wrap-code">`--databricks-http-path`</div>|Databricks http path to the warehouse. For example: /sql/1.0/warehouses/<warehouse instance id>| ||
|<div id="connection update--databricks-password" class="no-wrap-code">`--databricks-password`</div>|Databricks database password.| ||
|<div id="connection update--databricks-port" class="no-wrap-code">`--databricks-port`</div>|Databricks port number| ||
|<div id="connection update--databricks-user" class="no-wrap-code">`--databricks-user`</div>|Databricks user name.| ||
|<div id="connection update--duckdb-aws-authentication-mode" class="no-wrap-code">`--duckdb-aws-authentication-mode`</div>|The authentication mode for AWS. Supports also a null configuration with a custom environment variable.| |*iam*<br/>*default_credentials*<br/>|
|<div id="connection update--duckdb-azure-account-name" class="no-wrap-code">`--duckdb-azure-account-name`</div>|Azure Storage Account Name used by DuckDB Secret Manager. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--duckdb-azure-authentication-mode" class="no-wrap-code">`--duckdb-azure-authentication-mode`</div>|The authentication mode for Azure. Supports also a null configuration with a custom environment variable.| |*connection_string*<br/>*credential_chain*<br/>*service_principal*<br/>*default_credentials*<br/>|
|<div id="connection update--duckdb-azure-client-id" class="no-wrap-code">`--duckdb-azure-client-id`</div>|Azure Client ID used by DuckDB Secret Manager. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--duckdb-azure-client-secret" class="no-wrap-code">`--duckdb-azure-client-secret`</div>|Azure Client Secret used by DuckDB Secret Manager. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--duckdb-azure-tenant-id" class="no-wrap-code">`--duckdb-azure-tenant-id`</div>|Azure Tenant ID used by DuckDB Secret Manager. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--duckdb-database" class="no-wrap-code">`--duckdb-database`</div>|DuckDB database name for in-memory read mode. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--duckdb-directories" class="no-wrap-code">`--duckdb-directories`</div>|Virtual schema name to directory mappings. The path must be an absolute path.| ||
|<div id="connection update--duckdb-files-format-type" class="no-wrap-code">`--duckdb-files-format-type`</div>|Type of source files format for DuckDB.| |*csv*<br/>*json*<br/>*parquet*<br/>|
|<div id="connection update--duckdb-password" class="no-wrap-code">`--duckdb-password`</div>|DuckDB password for a remote storage type. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--duckdb-read-mode" class="no-wrap-code">`--duckdb-read-mode`</div>|DuckDB read mode.| |*in_memory*<br/>*files*<br/>|
|<div id="connection update--duckdb-region" class="no-wrap-code">`--duckdb-region`</div>|The region for the storage credentials. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--duckdb-storage-type" class="no-wrap-code">`--duckdb-storage-type`</div>|The storage type.| |*local*<br/>*s3*<br/>*azure*<br/>|
|<div id="connection update--duckdb-user" class="no-wrap-code">`--duckdb-user`</div>|DuckDB user name for a remote storage type. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update-fw" class="no-wrap-code">`-fw`</div><div id="connection update--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="connection update--headless" class="no-wrap-code">`--headless`</div><div id="connection update-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="connection update-h" class="no-wrap-code">`-h`</div><div id="connection update--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="connection update--mysql-database" class="no-wrap-code">`--mysql-database`</div>|MySQL database name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--mysql-engine" class="no-wrap-code">`--mysql-engine`</div>|MySQL engine type. Supports also a null configuration with a custom environment variable.| |*mysql*<br/>*singlestoredb*<br/>|
|<div id="connection update--mysql-host" class="no-wrap-code">`--mysql-host`</div>|MySQL host name| ||
|<div id="connection update--mysql-password" class="no-wrap-code">`--mysql-password`</div>|MySQL database password. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--mysql-port" class="no-wrap-code">`--mysql-port`</div>|MySQL port number| ||
|<div id="connection update--mysql-sslmode" class="no-wrap-code">`--mysql-sslmode`</div>|SslMode MySQL connection parameter| |*DISABLED*<br/>*PREFERRED*<br/>*REQUIRED*<br/>*VERIFY_CA*<br/>*VERIFY_IDENTITY*<br/>|
|<div id="connection update--mysql-user" class="no-wrap-code">`--mysql-user`</div>|MySQL user name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update-n" class="no-wrap-code">`-n`</div><div id="connection update--name" class="no-wrap-code">`--name`</div>|Connection name, supports wildcards for changing multiple connections at once, i.e. "conn*"| ||
|<div id="connection update--oracle-database" class="no-wrap-code">`--oracle-database`</div>|Oracle database name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--oracle-host" class="no-wrap-code">`--oracle-host`</div>|Oracle host name| ||
|<div id="connection update--oracle-initialization-sql" class="no-wrap-code">`--oracle-initialization-sql`</div>|Custom SQL that is executed after connecting to Oracle. This SQL script can configure the default language, for example: alter session set NLS_DATE_FORMAT='YYYY-DD-MM HH24:MI:SS'| ||
|<div id="connection update--oracle-password" class="no-wrap-code">`--oracle-password`</div>|Oracle database password. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--oracle-port" class="no-wrap-code">`--oracle-port`</div>|Oracle port number| ||
|<div id="connection update--oracle-user" class="no-wrap-code">`--oracle-user`</div>|Oracle user name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update-of" class="no-wrap-code">`-of`</div><div id="connection update--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="connection update--postgresql-database" class="no-wrap-code">`--postgresql-database`</div>|PostgreSQL database name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--postgresql-host" class="no-wrap-code">`--postgresql-host`</div>|PostgreSQL host name| ||
|<div id="connection update--postgresql-options" class="no-wrap-code">`--postgresql-options`</div>|PostgreSQL connection 'options' initialization parameter. For example setting this to -c statement_timeout=5min would set the statement timeout parameter for this session to 5 minutes.| ||
|<div id="connection update--postgresql-password" class="no-wrap-code">`--postgresql-password`</div>|PostgreSQL database password. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--postgresql-port" class="no-wrap-code">`--postgresql-port`</div>|PostgreSQL port number| ||
|<div id="connection update--postgresql-sslmode" class="no-wrap-code">`--postgresql-sslmode`</div>|Connect to PostgreSQL using sslmode connection parameter| |*disable*<br/>*allow*<br/>*prefer*<br/>*require*<br/>*verify_ca*<br/>*verify_full*<br/>|
|<div id="connection update--postgresql-user" class="no-wrap-code">`--postgresql-user`</div>|PostgreSQL user name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--presto-database" class="no-wrap-code">`--presto-database`</div>|Presto database name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--presto-host" class="no-wrap-code">`--presto-host`</div>|Presto host name| ||
|<div id="connection update--presto-password" class="no-wrap-code">`--presto-password`</div>|Presto database password. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--presto-port" class="no-wrap-code">`--presto-port`</div>|Presto port number| ||
|<div id="connection update--presto-user" class="no-wrap-code">`--presto-user`</div>|Presto user name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--redshift-authentication-mode" class="no-wrap-code">`--redshift-authentication-mode`</div>|The authentication mode for AWS. Supports also a null configuration with a custom environment variable.| |*iam*<br/>*default_credentials*<br/>*user_password*<br/>|
|<div id="connection update--redshift-database" class="no-wrap-code">`--redshift-database`</div>|Redshift database name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--redshift-host" class="no-wrap-code">`--redshift-host`</div>|Redshift host name| ||
|<div id="connection update--redshift-password" class="no-wrap-code">`--redshift-password`</div>|Redshift database password. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--redshift-port" class="no-wrap-code">`--redshift-port`</div>|Redshift port number| ||
|<div id="connection update--redshift-user" class="no-wrap-code">`--redshift-user`</div>|Redshift user name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--single-store-parameters-spec" class="no-wrap-code">`--single-store-parameters-spec`</div>|Single Store DB parameters spec.| ||
|<div id="connection update--snowflake-account" class="no-wrap-code">`--snowflake-account`</div>|Snowflake account name, e.q. <account>, <account>-<locator>, <account>.<region> or <account>.<region>.<platform>.| ||
|<div id="connection update--snowflake-database" class="no-wrap-code">`--snowflake-database`</div>|Snowflake database name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--snowflake-password" class="no-wrap-code">`--snowflake-password`</div>|Snowflake database password. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--snowflake-role" class="no-wrap-code">`--snowflake-role`</div>|Snowflake role name.| ||
|<div id="connection update--snowflake-user" class="no-wrap-code">`--snowflake-user`</div>|Snowflake user name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--snowflake-warehouse" class="no-wrap-code">`--snowflake-warehouse`</div>|Snowflake warehouse name.| ||
|<div id="connection update--spark-host" class="no-wrap-code">`--spark-host`</div>|Spark host name| ||
|<div id="connection update--spark-password" class="no-wrap-code">`--spark-password`</div>|Spark database password. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--spark-port" class="no-wrap-code">`--spark-port`</div>|Spark port number| ||
|<div id="connection update--spark-user" class="no-wrap-code">`--spark-user`</div>|Spark user name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--sqlserver-authentication-mode" class="no-wrap-code">`--sqlserver-authentication-mode`</div>|Authenticaiton mode for the SQL Server. The value can be in the null format to use dynamic substitution.| |*sql_password*<br/>*active_directory_password*<br/>*active_directory_service_principal*<br/>*active_directory_default*<br/>|
|<div id="connection update--sqlserver-database" class="no-wrap-code">`--sqlserver-database`</div>|SQL Server database name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--sqlserver-disable-encryption" class="no-wrap-code">`--sqlserver-disable-encryption`</div>|Disable SSL encryption parameter. The default value is false. You may need to disable encryption when SQL Server is started in Docker.| ||
|<div id="connection update--sqlserver-host" class="no-wrap-code">`--sqlserver-host`</div>|SQL Server host name| ||
|<div id="connection update--sqlserver-password" class="no-wrap-code">`--sqlserver-password`</div>|SQL Server database password. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--sqlserver-port" class="no-wrap-code">`--sqlserver-port`</div>|SQL Server port number| ||
|<div id="connection update--sqlserver-user" class="no-wrap-code">`--sqlserver-user`</div>|SQL Server user name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--trino-catalog" class="no-wrap-code">`--trino-catalog`</div>|The Trino catalog that contains the databases and the tables that will be accessed with the driver. Supports also a null configuration with a custom environment variable.| ||
|<div id="connection update--trino-engine" class="no-wrap-code">`--trino-engine`</div>|Trino engine type.| |*trino*<br/>*athena*<br/>|
|<div id="connection update--trino-host" class="no-wrap-code">`--trino-host`</div>|Trino host name.| ||
|<div id="connection update--trino-password" class="no-wrap-code">`--trino-password`</div>|Trino database password. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update--trino-port" class="no-wrap-code">`--trino-port`</div>|Trino port number.| ||
|<div id="connection update--trino-user" class="no-wrap-code">`--trino-user`</div>|Trino user name. The value can be in the null format to use dynamic substitution.| ||
|<div id="connection update-D" class="no-wrap-code">`-D`</div>|Databricks additional properties that are added to the JDBC connection string| ||
|<div id="connection update-Duck" class="no-wrap-code">`-Duck`</div>|DuckDB additional properties that are added to the JDBC connection string| ||
|<div id="connection update-E" class="no-wrap-code">`-E`</div>|Presto additional properties that are added to the JDBC connection string.| ||
|<div id="connection update-F" class="no-wrap-code">`-F`</div>|Snowflake additional properties that are added to the JDBC connection string| ||
|<div id="connection update-K" class="no-wrap-code">`-K`</div>|Spark additional properties that are added to the JDBC connection string| ||
|<div id="connection update-M" class="no-wrap-code">`-M`</div>|MySQL additional properties that are added to the JDBC connection string| ||
|<div id="connection update-O" class="no-wrap-code">`-O`</div>|Oracle's additional properties that are added to the JDBC connection string| ||
|<div id="connection update-P" class="no-wrap-code">`-P`</div>|PostgreSQL additional properties that are added to the JDBC connection string| ||
|<div id="connection update-R" class="no-wrap-code">`-R`</div>|Redshift additional properties that are added to the JDBC connection string| ||
|<div id="connection update-S" class="no-wrap-code">`-S`</div>|SQL Server additional properties that are added to the JDBC connection string| ||
|<div id="connection update-T" class="no-wrap-code">`-T`</div>|Trino additional properties that are added to the JDBC connection string| ||






___

## dqo connection schema list

List schemas in the specified connection


**Description**


It allows the user to view the summary of all schemas in a selected connection.




**Command-line synopsis**

```
$ dqo [dqo options...] connection schema list [-h] [-fw] [-hl] [-n=<name>] [-of=<outputFormat>]
                        [-d=<dimensions>]... [-l=<labels>]...

```

**DQOps shell synopsis**

```
dqo> connection schema list [-h] [-fw] [-hl] [-n=<name>] [-of=<outputFormat>]
                        [-d=<dimensions>]... [-l=<labels>]...

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="connection schema list-d" class="no-wrap-code">`-d`</div><div id="connection schema list--dimension" class="no-wrap-code">`--dimension`</div>|Dimension filter| ||
|<div id="connection schema list-fw" class="no-wrap-code">`-fw`</div><div id="connection schema list--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="connection schema list--headless" class="no-wrap-code">`--headless`</div><div id="connection schema list-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="connection schema list-h" class="no-wrap-code">`-h`</div><div id="connection schema list--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="connection schema list-l" class="no-wrap-code">`-l`</div><div id="connection schema list--label" class="no-wrap-code">`--label`</div>|Label filter| ||
|<div id="connection schema list-n" class="no-wrap-code">`-n`</div><div id="connection schema list--name" class="no-wrap-code">`--name`</div>|Connection name filter| ||
|<div id="connection schema list-of" class="no-wrap-code">`-of`</div><div id="connection schema list--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo connection table list

List tables for the specified connection and schema name.


**Description**


List all the tables available in the database for the specified connection and schema. It allows the user to view all the tables in the database.




**Command-line synopsis**

```
$ dqo [dqo options...] connection table list [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]
                       [-s=<schema>] [-t=<tableNameContains>]
                       [-d=<dimensions>]... [-l=<labels>]...

```

**DQOps shell synopsis**

```
dqo> connection table list [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]
                       [-s=<schema>] [-t=<tableNameContains>]
                       [-d=<dimensions>]... [-l=<labels>]...

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="connection table list-c" class="no-wrap-code">`-c`</div><div id="connection table list--connection" class="no-wrap-code">`--connection`</div>|Connection name| ||
|<div id="connection table list-d" class="no-wrap-code">`-d`</div><div id="connection table list--dimension" class="no-wrap-code">`--dimension`</div>|Dimension filter| ||
|<div id="connection table list-fw" class="no-wrap-code">`-fw`</div><div id="connection table list--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="connection table list--headless" class="no-wrap-code">`--headless`</div><div id="connection table list-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="connection table list-h" class="no-wrap-code">`-h`</div><div id="connection table list--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="connection table list-l" class="no-wrap-code">`-l`</div><div id="connection table list--label" class="no-wrap-code">`--label`</div>|Label filter| ||
|<div id="connection table list-of" class="no-wrap-code">`-of`</div><div id="connection table list--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="connection table list-s" class="no-wrap-code">`-s`</div><div id="connection table list--schema" class="no-wrap-code">`--schema`</div>|Schema name| ||
|<div id="connection table list-t" class="no-wrap-code">`-t`</div><div id="connection table list--table" class="no-wrap-code">`--table`</div>|Table name or a fragment of the table name| ||






___

## dqo connection table show

Show table for connection


**Description**


Show the details of the specified table in the database for the specified connection. It allows the user to view the details of a specific table in the database.




**Command-line synopsis**

```
$ dqo [dqo options...] connection table show [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]
                       [-t=<table>]

```

**DQOps shell synopsis**

```
dqo> connection table show [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]
                       [-t=<table>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="connection table show-c" class="no-wrap-code">`-c`</div><div id="connection table show--connection" class="no-wrap-code">`--connection`</div>|Connection name| ||
|<div id="connection table show-fw" class="no-wrap-code">`-fw`</div><div id="connection table show--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="connection table show-t" class="no-wrap-code">`-t`</div><div id="connection table show--table" class="no-wrap-code">`--table`</div><div id="connection table show--full-table-name" class="no-wrap-code">`--full-table-name`</div>|Full table name (schema.table), supports wildcard patterns 'sch*.tab*'| ||
|<div id="connection table show--headless" class="no-wrap-code">`--headless`</div><div id="connection table show-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="connection table show-h" class="no-wrap-code">`-h`</div><div id="connection table show--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="connection table show-of" class="no-wrap-code">`-of`</div><div id="connection table show--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo connection edit

Edit connection that matches a given condition


**Description**


Edit the connection or connections that match the filter conditions specified in the options. It allows the user to modify the details of an existing connection in the application.




**Command-line synopsis**

```
$ dqo [dqo options...] connection edit [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]

```

**DQOps shell synopsis**

```
dqo> connection edit [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="connection edit-c" class="no-wrap-code">`-c`</div><div id="connection edit--connection" class="no-wrap-code">`--connection`</div>|Connection Name| ||
|<div id="connection edit-fw" class="no-wrap-code">`-fw`</div><div id="connection edit--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="connection edit--headless" class="no-wrap-code">`--headless`</div><div id="connection edit-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="connection edit-h" class="no-wrap-code">`-h`</div><div id="connection edit--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="connection edit-of" class="no-wrap-code">`-of`</div><div id="connection edit--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|





