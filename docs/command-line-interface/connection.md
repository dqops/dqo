# dqo connection command
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
|<p id="connection list-d">`-d`</p><br/><p id="connection list--dimension">`--dimension`</p><br/>|Dimension filter| ||
|<p id="connection list-fw">`-fw`</p><br/><p id="connection list--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="connection list--headless">`--headless`</p><br/><p id="connection list-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="connection list-h">`-h`</p><br/><p id="connection list--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="connection list-l">`-l`</p><br/><p id="connection list--label">`--label`</p><br/>|Label filter| ||
|<p id="connection list-n">`-n`</p><br/><p id="connection list--name">`--name`</p><br/>|Connection name filter| ||
|<p id="connection list-of">`-of`</p><br/><p id="connection list--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






___

## dqo connection add

Add a connection with specified details


**Description**


Creates a new connection to the database with the specified details such as connection name, database type, hostname, username, and password. It allows the user to connect to the database from the application to perform various operations on the database.




**Command-line synopsis**

```
$ dqo [dqo options...] connection add [-h] [-fw] [-hl] [--sqlserver-disable-encryption]
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
                [--databricks-options=<options>]
                [--databricks-password=<password>] [--databricks-port=<port>]
                [--databricks-user=<user>] [--mysql-database=<database>]
                [--mysql-host=<host>] [--mysql-options=<options>]
                [--mysql-password=<password>] [--mysql-port=<port>]
                [--mysql-sslmode=<sslmode>] [--mysql-user=<user>] [-n=<name>]
                [-of=<outputFormat>] [--oracle-database=<database>]
                [--oracle-host=<host>]
                [--oracle-initialization-sql=<initializationSql>]
                [--oracle-options=<options>] [--oracle-password=<password>]
                [--oracle-port=<port>] [--oracle-user=<user>]
                [--postgresql-database=<database>] [--postgresql-host=<host>]
                [--postgresql-options=<options>]
                [--postgresql-password=<password>] [--postgresql-port=<port>]
                [--postgresql-sslmode=<sslmode>] [--postgresql-user=<user>]
                [--presto-database=<database>] [--presto-host=<host>]
                [--presto-password=<password>] [--presto-port=<port>]
                [--presto-user=<user>] [--redshift-database=<database>]
                [--redshift-host=<host>] [--redshift-options=<options>]
                [--redshift-password=<password>] [--redshift-port=<port>]
                [--redshift-user=<user>] [--snowflake-account=<account>]
                [--snowflake-database=<database>]
                [--snowflake-password=<password>] [--snowflake-role=<role>]
                [--snowflake-user=<user>] [--snowflake-warehouse=<warehouse>]
                [--spark-host=<host>] [--spark-options=<options>]
                [--spark-password=<password>] [--spark-port=<port>]
                [--spark-user=<user>] [--sqlserver-database=<database>]
                [--sqlserver-host=<host>] [--sqlserver-options=<options>]
                [--sqlserver-password=<password>] [--sqlserver-port=<port>]
                [--sqlserver-user=<user>] [-t=<providerType>]
                [--trino-catalog=<catalog>] [--trino-engine=<trinoEngineType>]
                [--trino-host=<host>] [--trino-port=<port>]
                [--trino-user=<user>] [-D=<String=String>]...
                [-E=<String=String>]... [-F=<String=String>]...
                [-K=<String=String>]... [-M=<String=String>]...
                [-O=<String=String>]... [-P=<String=String>]...
                [-R=<String=String>]... [-S=<String=String>]...
                [-T=<String=String>]...

```

**DQOps shell synopsis**

```
dqo> connection add [-h] [-fw] [-hl] [--sqlserver-disable-encryption]
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
                [--databricks-options=<options>]
                [--databricks-password=<password>] [--databricks-port=<port>]
                [--databricks-user=<user>] [--mysql-database=<database>]
                [--mysql-host=<host>] [--mysql-options=<options>]
                [--mysql-password=<password>] [--mysql-port=<port>]
                [--mysql-sslmode=<sslmode>] [--mysql-user=<user>] [-n=<name>]
                [-of=<outputFormat>] [--oracle-database=<database>]
                [--oracle-host=<host>]
                [--oracle-initialization-sql=<initializationSql>]
                [--oracle-options=<options>] [--oracle-password=<password>]
                [--oracle-port=<port>] [--oracle-user=<user>]
                [--postgresql-database=<database>] [--postgresql-host=<host>]
                [--postgresql-options=<options>]
                [--postgresql-password=<password>] [--postgresql-port=<port>]
                [--postgresql-sslmode=<sslmode>] [--postgresql-user=<user>]
                [--presto-database=<database>] [--presto-host=<host>]
                [--presto-password=<password>] [--presto-port=<port>]
                [--presto-user=<user>] [--redshift-database=<database>]
                [--redshift-host=<host>] [--redshift-options=<options>]
                [--redshift-password=<password>] [--redshift-port=<port>]
                [--redshift-user=<user>] [--snowflake-account=<account>]
                [--snowflake-database=<database>]
                [--snowflake-password=<password>] [--snowflake-role=<role>]
                [--snowflake-user=<user>] [--snowflake-warehouse=<warehouse>]
                [--spark-host=<host>] [--spark-options=<options>]
                [--spark-password=<password>] [--spark-port=<port>]
                [--spark-user=<user>] [--sqlserver-database=<database>]
                [--sqlserver-host=<host>] [--sqlserver-options=<options>]
                [--sqlserver-password=<password>] [--sqlserver-port=<port>]
                [--sqlserver-user=<user>] [-t=<providerType>]
                [--trino-catalog=<catalog>] [--trino-engine=<trinoEngineType>]
                [--trino-host=<host>] [--trino-port=<port>]
                [--trino-user=<user>] [-D=<String=String>]...
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
|<p id="connection add--athena-output-location">`--athena-output-location`</p><br/>|The location in Amazon S3 where query results will be stored. Supports also a null configuration with a custom environment variable.| ||
|<p id="connection add--athena-region">`--athena-region`</p><br/>|The AWS Athena Region where queries will be run. Supports also a null configuration with a custom environment variable.| ||
|<p id="connection add--athena-work-group">`--athena-work-group`</p><br/>|The Athena WorkGroup in which queries will run. Supports also a null configuration with a custom environment variable.| ||
|<p id="connection add--bigquery-authentication-mode">`--bigquery-authentication-mode`</p><br/>|Bigquery authentication mode. The default value uses the current GCP application default credentials. The default GCP credentials is the Service Account of a VM in GCP cloud, a GCP JSON key file whose path is in the GOOGLE_APPLICATION_CREDENTIALS environment variable, or it is the default GCP credentials obtained on a user&#x27;s computer by running &#x27;gcloud auth application-default login&#x27; from the command line.| |google_application_credentials<br/>json_key_content<br/>json_key_path<br/>|
|<p id="connection add--bigquery-billing-project-id">`--bigquery-billing-project-id`</p><br/>|Bigquery billing GCP project id. This is the project used as the default GCP project. The calling user must have a bigquery.jobs.create permission in this project.| ||
|<p id="connection add--bigquery-jobs-create-project">`--bigquery-jobs-create-project`</p><br/>|Configures the way how to select the project that will be used to start BigQuery jobs and will be used for billing. The user/service identified by the credentials must have bigquery.jobs.create permission in that project.| |create_jobs_in_source_project<br/>create_jobs_in_default_project_from_credentials<br/>create_jobs_in_selected_billing_project_id<br/>|
|<p id="connection add--bigquery-json-key-content">`--bigquery-json-key-content`</p><br/>|Bigquery service account key content as JSON.| ||
|<p id="connection add--bigquery-json-key-path">`--bigquery-json-key-path`</p><br/>|Path to a GCP service account key JSON file used to authenticate to Bigquery.| ||
|<p id="connection add--bigquery-quota-project-id">`--bigquery-quota-project-id`</p><br/>|Bigquery quota GCP project id.| ||
|<p id="connection add--bigquery-source-project-id">`--bigquery-source-project-id`</p><br/>|Bigquery source GCP project id. This is the project that has datasets that will be imported.| ||
|<p id="connection add--databricks-access-token">`--databricks-access-token`</p><br/>|Databricks access token for the warehouse.| ||
|<p id="connection add--databricks-catalog">`--databricks-catalog`</p><br/>|Databricks catalog name.| ||
|<p id="connection add--databricks-host">`--databricks-host`</p><br/>|Databricks host name| ||
|<p id="connection add--databricks-http-path">`--databricks-http-path`</p><br/>|Databricks http path to the warehouse. For example: /sql/1.0/warehouses/&lt;warehouse instance id&gt;| ||
|<p id="connection add--databricks-options">`--databricks-options`</p><br/>|Databricks connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|<p id="connection add--databricks-password">`--databricks-password`</p><br/>|Databricks database password.| ||
|<p id="connection add--databricks-port">`--databricks-port`</p><br/>|Databricks port number| ||
|<p id="connection add--databricks-user">`--databricks-user`</p><br/>|Databricks user name.| ||
|<p id="connection add-fw">`-fw`</p><br/><p id="connection add--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="connection add--headless">`--headless`</p><br/><p id="connection add-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="connection add-h">`-h`</p><br/><p id="connection add--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="connection add--mysql-database">`--mysql-database`</p><br/>|MySQL database name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--mysql-host">`--mysql-host`</p><br/>|MySQL host name| ||
|<p id="connection add--mysql-options">`--mysql-options`</p><br/>|MySQL connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|<p id="connection add--mysql-password">`--mysql-password`</p><br/>|MySQL database password. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--mysql-port">`--mysql-port`</p><br/>|MySQL port number| ||
|<p id="connection add--mysql-sslmode">`--mysql-sslmode`</p><br/>|SslMode MySQL connection parameter| |DISABLED<br/>PREFERRED<br/>REQUIRED<br/>VERIFY_CA<br/>VERIFY_IDENTITY<br/>|
|<p id="connection add--mysql-user">`--mysql-user`</p><br/>|MySQL user name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add-n">`-n`</p><br/><p id="connection add--name">`--name`</p><br/>|Connection name| ||
|<p id="connection add--oracle-database">`--oracle-database`</p><br/>|Oracle database name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--oracle-host">`--oracle-host`</p><br/>|Oracle host name| ||
|<p id="connection add--oracle-initialization-sql">`--oracle-initialization-sql`</p><br/>|Custom SQL that is executed after connecting to Oracle. This SQL script can configure the default language, for example: alter session set NLS_DATE_FORMAT&#x3D;&#x27;YYYY-DD-MM HH24:MI:SS&#x27;| ||
|<p id="connection add--oracle-options">`--oracle-options`</p><br/>|Oracle connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|<p id="connection add--oracle-password">`--oracle-password`</p><br/>|Oracle database password. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--oracle-port">`--oracle-port`</p><br/>|Oracle port number| ||
|<p id="connection add--oracle-user">`--oracle-user`</p><br/>|Oracle user name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add-of">`-of`</p><br/><p id="connection add--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="connection add--postgresql-database">`--postgresql-database`</p><br/>|PostgreSQL database name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--postgresql-host">`--postgresql-host`</p><br/>|PostgreSQL host name| ||
|<p id="connection add--postgresql-options">`--postgresql-options`</p><br/>|PostgreSQL connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|<p id="connection add--postgresql-password">`--postgresql-password`</p><br/>|PostgreSQL database password. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--postgresql-port">`--postgresql-port`</p><br/>|PostgreSQL port number| ||
|<p id="connection add--postgresql-sslmode">`--postgresql-sslmode`</p><br/>|Connect to PostgreSQL using sslmode connection parameter| |disable<br/>allow<br/>prefer<br/>require<br/>verify_ca<br/>verify_full<br/>|
|<p id="connection add--postgresql-user">`--postgresql-user`</p><br/>|PostgreSQL user name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--presto-database">`--presto-database`</p><br/>|Presto database name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--presto-host">`--presto-host`</p><br/>|Presto host name| ||
|<p id="connection add--presto-password">`--presto-password`</p><br/>|Presto database password. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--presto-port">`--presto-port`</p><br/>|Presto port number| ||
|<p id="connection add--presto-user">`--presto-user`</p><br/>|Presto user name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add-t">`-t`</p><br/><p id="connection add--provider">`--provider`</p><br/>|Connection provider type| |bigquery<br/>snowflake<br/>postgresql<br/>redshift<br/>sqlserver<br/>presto<br/>trino<br/>mysql<br/>oracle<br/>spark<br/>databricks<br/>|
|<p id="connection add--redshift-database">`--redshift-database`</p><br/>|Redshift database name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--redshift-host">`--redshift-host`</p><br/>|Redshift host name| ||
|<p id="connection add--redshift-options">`--redshift-options`</p><br/>|Redshift connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|<p id="connection add--redshift-password">`--redshift-password`</p><br/>|Redshift database password. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--redshift-port">`--redshift-port`</p><br/>|Redshift port number| ||
|<p id="connection add--redshift-user">`--redshift-user`</p><br/>|Redshift user name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--snowflake-account">`--snowflake-account`</p><br/>|Snowflake account name, e.q. &lt;account&gt;, &lt;account&gt;-&lt;locator&gt;, &lt;account&gt;.&lt;region&gt; or &lt;account&gt;.&lt;region&gt;.&lt;platform&gt;.| ||
|<p id="connection add--snowflake-database">`--snowflake-database`</p><br/>|Snowflake database name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--snowflake-password">`--snowflake-password`</p><br/>|Snowflake database password. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--snowflake-role">`--snowflake-role`</p><br/>|Snowflake role name.| ||
|<p id="connection add--snowflake-user">`--snowflake-user`</p><br/>|Snowflake user name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--snowflake-warehouse">`--snowflake-warehouse`</p><br/>|Snowflake warehouse name.| ||
|<p id="connection add--spark-host">`--spark-host`</p><br/>|Spark host name| ||
|<p id="connection add--spark-options">`--spark-options`</p><br/>|Spark connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|<p id="connection add--spark-password">`--spark-password`</p><br/>|Spark database password. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--spark-port">`--spark-port`</p><br/>|Spark port number| ||
|<p id="connection add--spark-user">`--spark-user`</p><br/>|Spark user name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--sqlserver-database">`--sqlserver-database`</p><br/>|SQL Server database name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--sqlserver-disable-encryption">`--sqlserver-disable-encryption`</p><br/>|Disable SSL encryption parameter. The default value is false. You may need to disable encryption when SQL Server is started in Docker.| ||
|<p id="connection add--sqlserver-host">`--sqlserver-host`</p><br/>|SQL Server host name| ||
|<p id="connection add--sqlserver-options">`--sqlserver-options`</p><br/>|SQL Server connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|<p id="connection add--sqlserver-password">`--sqlserver-password`</p><br/>|SQL Server database password. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--sqlserver-port">`--sqlserver-port`</p><br/>|SQL Server port number| ||
|<p id="connection add--sqlserver-user">`--sqlserver-user`</p><br/>|SQL Server user name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add--trino-catalog">`--trino-catalog`</p><br/>|The Trino catalog that contains the databases and the tables that will be accessed with the driver. Supports also a null configuration with a custom environment variable.| ||
|<p id="connection add--trino-engine">`--trino-engine`</p><br/>|Trino engine type.| |trino<br/>athena<br/>|
|<p id="connection add--trino-host">`--trino-host`</p><br/>|Trino host name.| ||
|<p id="connection add--trino-port">`--trino-port`</p><br/>|Trino port number.| ||
|<p id="connection add--trino-user">`--trino-user`</p><br/>|Trino user name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection add-D">`-D`</p><br/>|Databricks additional properties that are added to the JDBC connection string| ||
|<p id="connection add-E">`-E`</p><br/>|Presto additional properties that are added to the JDBC connection string| ||
|<p id="connection add-F">`-F`</p><br/>|Snowflake additional properties that are added to the JDBC connection string| ||
|<p id="connection add-K">`-K`</p><br/>|Spark additional properties that are added to the JDBC connection string| ||
|<p id="connection add-M">`-M`</p><br/>|MySQL additional properties that are added to the JDBC connection string| ||
|<p id="connection add-O">`-O`</p><br/>|Oracle&#x27;s additional properties that are added to the JDBC connection string| ||
|<p id="connection add-P">`-P`</p><br/>|PostgreSQL additional properties that are added to the JDBC connection string| ||
|<p id="connection add-R">`-R`</p><br/>|Redshift additional properties that are added to the JDBC connection string| ||
|<p id="connection add-S">`-S`</p><br/>|SQL Server additional properties that are added to the JDBC connection string| ||
|<p id="connection add-T">`-T`</p><br/>|Trino additional properties that are added to the JDBC connection string| ||






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
|<p id="connection remove-fw">`-fw`</p><br/><p id="connection remove--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="connection remove--headless">`--headless`</p><br/><p id="connection remove-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="connection remove-h">`-h`</p><br/><p id="connection remove--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="connection remove-n">`-n`</p><br/><p id="connection remove--name">`--name`</p><br/>|Connection name| ||
|<p id="connection remove-of">`-of`</p><br/><p id="connection remove--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






___

## dqo connection update

Update the connection(s) that match a given condition


**Description**


Update the connection or connections that match the conditions specified in the options with new details. It allows the user to modify existing connections in the application.




**Command-line synopsis**

```
$ dqo [dqo options...] connection update [-h] [-fw] [-hl] [--sqlserver-disable-encryption]
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
                   [--databricks-options=<options>]
                   [--databricks-password=<password>]
                   [--databricks-port=<port>] [--databricks-user=<user>]
                   [--mysql-database=<database>] [--mysql-host=<host>]
                   [--mysql-options=<options>] [--mysql-password=<password>]
                   [--mysql-port=<port>] [--mysql-sslmode=<sslmode>]
                   [--mysql-user=<user>] [-n=<name>] [-of=<outputFormat>]
                   [--oracle-database=<database>] [--oracle-host=<host>]
                   [--oracle-initialization-sql=<initializationSql>]
                   [--oracle-options=<options>] [--oracle-password=<password>]
                   [--oracle-port=<port>] [--oracle-user=<user>]
                   [--postgresql-database=<database>]
                   [--postgresql-host=<host>] [--postgresql-options=<options>]
                   [--postgresql-password=<password>]
                   [--postgresql-port=<port>] [--postgresql-sslmode=<sslmode>]
                   [--postgresql-user=<user>] [--presto-database=<database>]
                   [--presto-host=<host>] [--presto-password=<password>]
                   [--presto-port=<port>] [--presto-user=<user>]
                   [--redshift-database=<database>] [--redshift-host=<host>]
                   [--redshift-options=<options>]
                   [--redshift-password=<password>] [--redshift-port=<port>]
                   [--redshift-user=<user>] [--snowflake-account=<account>]
                   [--snowflake-database=<database>]
                   [--snowflake-password=<password>] [--snowflake-role=<role>]
                   [--snowflake-user=<user>]
                   [--snowflake-warehouse=<warehouse>] [--spark-host=<host>]
                   [--spark-options=<options>] [--spark-password=<password>]
                   [--spark-port=<port>] [--spark-user=<user>]
                   [--sqlserver-database=<database>] [--sqlserver-host=<host>]
                   [--sqlserver-options=<options>]
                   [--sqlserver-password=<password>] [--sqlserver-port=<port>]
                   [--sqlserver-user=<user>] [--trino-catalog=<catalog>]
                   [--trino-engine=<trinoEngineType>] [--trino-host=<host>]
                   [--trino-port=<port>] [--trino-user=<user>]
                   [-D=<String=String>]... [-E=<String=String>]...
                   [-F=<String=String>]... [-K=<String=String>]...
                   [-M=<String=String>]... [-O=<String=String>]...
                   [-P=<String=String>]... [-R=<String=String>]...
                   [-S=<String=String>]... [-T=<String=String>]...

```

**DQOps shell synopsis**

```
dqo> connection update [-h] [-fw] [-hl] [--sqlserver-disable-encryption]
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
                   [--databricks-options=<options>]
                   [--databricks-password=<password>]
                   [--databricks-port=<port>] [--databricks-user=<user>]
                   [--mysql-database=<database>] [--mysql-host=<host>]
                   [--mysql-options=<options>] [--mysql-password=<password>]
                   [--mysql-port=<port>] [--mysql-sslmode=<sslmode>]
                   [--mysql-user=<user>] [-n=<name>] [-of=<outputFormat>]
                   [--oracle-database=<database>] [--oracle-host=<host>]
                   [--oracle-initialization-sql=<initializationSql>]
                   [--oracle-options=<options>] [--oracle-password=<password>]
                   [--oracle-port=<port>] [--oracle-user=<user>]
                   [--postgresql-database=<database>]
                   [--postgresql-host=<host>] [--postgresql-options=<options>]
                   [--postgresql-password=<password>]
                   [--postgresql-port=<port>] [--postgresql-sslmode=<sslmode>]
                   [--postgresql-user=<user>] [--presto-database=<database>]
                   [--presto-host=<host>] [--presto-password=<password>]
                   [--presto-port=<port>] [--presto-user=<user>]
                   [--redshift-database=<database>] [--redshift-host=<host>]
                   [--redshift-options=<options>]
                   [--redshift-password=<password>] [--redshift-port=<port>]
                   [--redshift-user=<user>] [--snowflake-account=<account>]
                   [--snowflake-database=<database>]
                   [--snowflake-password=<password>] [--snowflake-role=<role>]
                   [--snowflake-user=<user>]
                   [--snowflake-warehouse=<warehouse>] [--spark-host=<host>]
                   [--spark-options=<options>] [--spark-password=<password>]
                   [--spark-port=<port>] [--spark-user=<user>]
                   [--sqlserver-database=<database>] [--sqlserver-host=<host>]
                   [--sqlserver-options=<options>]
                   [--sqlserver-password=<password>] [--sqlserver-port=<port>]
                   [--sqlserver-user=<user>] [--trino-catalog=<catalog>]
                   [--trino-engine=<trinoEngineType>] [--trino-host=<host>]
                   [--trino-port=<port>] [--trino-user=<user>]
                   [-D=<String=String>]... [-E=<String=String>]...
                   [-F=<String=String>]... [-K=<String=String>]...
                   [-M=<String=String>]... [-O=<String=String>]...
                   [-P=<String=String>]... [-R=<String=String>]...
                   [-S=<String=String>]... [-T=<String=String>]...

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<p id="connection update--athena-output-location">`--athena-output-location`</p><br/>|The location in Amazon S3 where query results will be stored. Supports also a null configuration with a custom environment variable.| ||
|<p id="connection update--athena-region">`--athena-region`</p><br/>|The AWS Athena Region where queries will be run. Supports also a null configuration with a custom environment variable.| ||
|<p id="connection update--athena-work-group">`--athena-work-group`</p><br/>|The Athena WorkGroup in which queries will run. Supports also a null configuration with a custom environment variable.| ||
|<p id="connection update--bigquery-authentication-mode">`--bigquery-authentication-mode`</p><br/>|Bigquery authentication mode. The default value uses the current GCP application default credentials. The default GCP credentials is the Service Account of a VM in GCP cloud, a GCP JSON key file whose path is in the GOOGLE_APPLICATION_CREDENTIALS environment variable, or it is the default GCP credentials obtained on a user&#x27;s computer by running &#x27;gcloud auth application-default login&#x27; from the command line.| |google_application_credentials<br/>json_key_content<br/>json_key_path<br/>|
|<p id="connection update--bigquery-billing-project-id">`--bigquery-billing-project-id`</p><br/>|Bigquery billing GCP project id. This is the project used as the default GCP project. The calling user must have a bigquery.jobs.create permission in this project.| ||
|<p id="connection update--bigquery-jobs-create-project">`--bigquery-jobs-create-project`</p><br/>|Configures the way how to select the project that will be used to start BigQuery jobs and will be used for billing. The user/service identified by the credentials must have bigquery.jobs.create permission in that project.| |create_jobs_in_source_project<br/>create_jobs_in_default_project_from_credentials<br/>create_jobs_in_selected_billing_project_id<br/>|
|<p id="connection update--bigquery-json-key-content">`--bigquery-json-key-content`</p><br/>|Bigquery service account key content as JSON.| ||
|<p id="connection update--bigquery-json-key-path">`--bigquery-json-key-path`</p><br/>|Path to a GCP service account key JSON file used to authenticate to Bigquery.| ||
|<p id="connection update--bigquery-quota-project-id">`--bigquery-quota-project-id`</p><br/>|Bigquery quota GCP project id.| ||
|<p id="connection update--bigquery-source-project-id">`--bigquery-source-project-id`</p><br/>|Bigquery source GCP project id. This is the project that has datasets that will be imported.| ||
|<p id="connection update--databricks-access-token">`--databricks-access-token`</p><br/>|Databricks access token for the warehouse.| ||
|<p id="connection update--databricks-catalog">`--databricks-catalog`</p><br/>|Databricks catalog name.| ||
|<p id="connection update--databricks-host">`--databricks-host`</p><br/>|Databricks host name| ||
|<p id="connection update--databricks-http-path">`--databricks-http-path`</p><br/>|Databricks http path to the warehouse. For example: /sql/1.0/warehouses/&lt;warehouse instance id&gt;| ||
|<p id="connection update--databricks-options">`--databricks-options`</p><br/>|Databricks connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|<p id="connection update--databricks-password">`--databricks-password`</p><br/>|Databricks database password.| ||
|<p id="connection update--databricks-port">`--databricks-port`</p><br/>|Databricks port number| ||
|<p id="connection update--databricks-user">`--databricks-user`</p><br/>|Databricks user name.| ||
|<p id="connection update-fw">`-fw`</p><br/><p id="connection update--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="connection update--headless">`--headless`</p><br/><p id="connection update-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="connection update-h">`-h`</p><br/><p id="connection update--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="connection update--mysql-database">`--mysql-database`</p><br/>|MySQL database name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--mysql-host">`--mysql-host`</p><br/>|MySQL host name| ||
|<p id="connection update--mysql-options">`--mysql-options`</p><br/>|MySQL connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|<p id="connection update--mysql-password">`--mysql-password`</p><br/>|MySQL database password. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--mysql-port">`--mysql-port`</p><br/>|MySQL port number| ||
|<p id="connection update--mysql-sslmode">`--mysql-sslmode`</p><br/>|SslMode MySQL connection parameter| |DISABLED<br/>PREFERRED<br/>REQUIRED<br/>VERIFY_CA<br/>VERIFY_IDENTITY<br/>|
|<p id="connection update--mysql-user">`--mysql-user`</p><br/>|MySQL user name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update-n">`-n`</p><br/><p id="connection update--name">`--name`</p><br/>|Connection name, supports wildcards for changing multiple connections at once, i.e. &quot;conn*&quot;| ||
|<p id="connection update--oracle-database">`--oracle-database`</p><br/>|Oracle database name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--oracle-host">`--oracle-host`</p><br/>|Oracle host name| ||
|<p id="connection update--oracle-initialization-sql">`--oracle-initialization-sql`</p><br/>|Custom SQL that is executed after connecting to Oracle. This SQL script can configure the default language, for example: alter session set NLS_DATE_FORMAT&#x3D;&#x27;YYYY-DD-MM HH24:MI:SS&#x27;| ||
|<p id="connection update--oracle-options">`--oracle-options`</p><br/>|Oracle connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|<p id="connection update--oracle-password">`--oracle-password`</p><br/>|Oracle database password. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--oracle-port">`--oracle-port`</p><br/>|Oracle port number| ||
|<p id="connection update--oracle-user">`--oracle-user`</p><br/>|Oracle user name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update-of">`-of`</p><br/><p id="connection update--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="connection update--postgresql-database">`--postgresql-database`</p><br/>|PostgreSQL database name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--postgresql-host">`--postgresql-host`</p><br/>|PostgreSQL host name| ||
|<p id="connection update--postgresql-options">`--postgresql-options`</p><br/>|PostgreSQL connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|<p id="connection update--postgresql-password">`--postgresql-password`</p><br/>|PostgreSQL database password. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--postgresql-port">`--postgresql-port`</p><br/>|PostgreSQL port number| ||
|<p id="connection update--postgresql-sslmode">`--postgresql-sslmode`</p><br/>|Connect to PostgreSQL using sslmode connection parameter| |disable<br/>allow<br/>prefer<br/>require<br/>verify_ca<br/>verify_full<br/>|
|<p id="connection update--postgresql-user">`--postgresql-user`</p><br/>|PostgreSQL user name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--presto-database">`--presto-database`</p><br/>|Presto database name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--presto-host">`--presto-host`</p><br/>|Presto host name| ||
|<p id="connection update--presto-password">`--presto-password`</p><br/>|Presto database password. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--presto-port">`--presto-port`</p><br/>|Presto port number| ||
|<p id="connection update--presto-user">`--presto-user`</p><br/>|Presto user name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--redshift-database">`--redshift-database`</p><br/>|Redshift database name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--redshift-host">`--redshift-host`</p><br/>|Redshift host name| ||
|<p id="connection update--redshift-options">`--redshift-options`</p><br/>|Redshift connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|<p id="connection update--redshift-password">`--redshift-password`</p><br/>|Redshift database password. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--redshift-port">`--redshift-port`</p><br/>|Redshift port number| ||
|<p id="connection update--redshift-user">`--redshift-user`</p><br/>|Redshift user name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--snowflake-account">`--snowflake-account`</p><br/>|Snowflake account name, e.q. &lt;account&gt;, &lt;account&gt;-&lt;locator&gt;, &lt;account&gt;.&lt;region&gt; or &lt;account&gt;.&lt;region&gt;.&lt;platform&gt;.| ||
|<p id="connection update--snowflake-database">`--snowflake-database`</p><br/>|Snowflake database name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--snowflake-password">`--snowflake-password`</p><br/>|Snowflake database password. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--snowflake-role">`--snowflake-role`</p><br/>|Snowflake role name.| ||
|<p id="connection update--snowflake-user">`--snowflake-user`</p><br/>|Snowflake user name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--snowflake-warehouse">`--snowflake-warehouse`</p><br/>|Snowflake warehouse name.| ||
|<p id="connection update--spark-host">`--spark-host`</p><br/>|Spark host name| ||
|<p id="connection update--spark-options">`--spark-options`</p><br/>|Spark connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|<p id="connection update--spark-password">`--spark-password`</p><br/>|Spark database password. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--spark-port">`--spark-port`</p><br/>|Spark port number| ||
|<p id="connection update--spark-user">`--spark-user`</p><br/>|Spark user name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--sqlserver-database">`--sqlserver-database`</p><br/>|SQL Server database name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--sqlserver-disable-encryption">`--sqlserver-disable-encryption`</p><br/>|Disable SSL encryption parameter. The default value is false. You may need to disable encryption when SQL Server is started in Docker.| ||
|<p id="connection update--sqlserver-host">`--sqlserver-host`</p><br/>|SQL Server host name| ||
|<p id="connection update--sqlserver-options">`--sqlserver-options`</p><br/>|SQL Server connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|<p id="connection update--sqlserver-password">`--sqlserver-password`</p><br/>|SQL Server database password. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--sqlserver-port">`--sqlserver-port`</p><br/>|SQL Server port number| ||
|<p id="connection update--sqlserver-user">`--sqlserver-user`</p><br/>|SQL Server user name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update--trino-catalog">`--trino-catalog`</p><br/>|The Trino catalog that contains the databases and the tables that will be accessed with the driver. Supports also a null configuration with a custom environment variable.| ||
|<p id="connection update--trino-engine">`--trino-engine`</p><br/>|Trino engine type.| |trino<br/>athena<br/>|
|<p id="connection update--trino-host">`--trino-host`</p><br/>|Trino host name.| ||
|<p id="connection update--trino-port">`--trino-port`</p><br/>|Trino port number.| ||
|<p id="connection update--trino-user">`--trino-user`</p><br/>|Trino user name. The value can be in the null format to use dynamic substitution.| ||
|<p id="connection update-D">`-D`</p><br/>|Databricks additional properties that are added to the JDBC connection string| ||
|<p id="connection update-E">`-E`</p><br/>|Presto additional properties that are added to the JDBC connection string| ||
|<p id="connection update-F">`-F`</p><br/>|Snowflake additional properties that are added to the JDBC connection string| ||
|<p id="connection update-K">`-K`</p><br/>|Spark additional properties that are added to the JDBC connection string| ||
|<p id="connection update-M">`-M`</p><br/>|MySQL additional properties that are added to the JDBC connection string| ||
|<p id="connection update-O">`-O`</p><br/>|Oracle&#x27;s additional properties that are added to the JDBC connection string| ||
|<p id="connection update-P">`-P`</p><br/>|PostgreSQL additional properties that are added to the JDBC connection string| ||
|<p id="connection update-R">`-R`</p><br/>|Redshift additional properties that are added to the JDBC connection string| ||
|<p id="connection update-S">`-S`</p><br/>|SQL Server additional properties that are added to the JDBC connection string| ||
|<p id="connection update-T">`-T`</p><br/>|Trino additional properties that are added to the JDBC connection string| ||






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
|<p id="connection schema list-d">`-d`</p><br/><p id="connection schema list--dimension">`--dimension`</p><br/>|Dimension filter| ||
|<p id="connection schema list-fw">`-fw`</p><br/><p id="connection schema list--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="connection schema list--headless">`--headless`</p><br/><p id="connection schema list-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="connection schema list-h">`-h`</p><br/><p id="connection schema list--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="connection schema list-l">`-l`</p><br/><p id="connection schema list--label">`--label`</p><br/>|Label filter| ||
|<p id="connection schema list-n">`-n`</p><br/><p id="connection schema list--name">`--name`</p><br/>|Connection name filter| ||
|<p id="connection schema list-of">`-of`</p><br/><p id="connection schema list--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






___

## dqo connection table list

List tables for the specified connection and schema name.


**Description**


List all the tables available in the database for the specified connection and schema. It allows the user to view all the tables in the database.




**Command-line synopsis**

```
$ dqo [dqo options...] connection table list [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]
                       [-s=<schema>] [-t=<table>] [-d=<dimensions>]...
                       [-l=<labels>]...

```

**DQOps shell synopsis**

```
dqo> connection table list [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]
                       [-s=<schema>] [-t=<table>] [-d=<dimensions>]...
                       [-l=<labels>]...

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<p id="connection table list-c">`-c`</p><br/><p id="connection table list--connection">`--connection`</p><br/>|Connection name| ||
|<p id="connection table list-d">`-d`</p><br/><p id="connection table list--dimension">`--dimension`</p><br/>|Dimension filter| ||
|<p id="connection table list-fw">`-fw`</p><br/><p id="connection table list--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="connection table list--headless">`--headless`</p><br/><p id="connection table list-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="connection table list-h">`-h`</p><br/><p id="connection table list--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="connection table list-l">`-l`</p><br/><p id="connection table list--label">`--label`</p><br/>|Label filter| ||
|<p id="connection table list-of">`-of`</p><br/><p id="connection table list--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="connection table list-s">`-s`</p><br/><p id="connection table list--schema">`--schema`</p><br/>|Schema name| ||
|<p id="connection table list-t">`-t`</p><br/><p id="connection table list--table">`--table`</p><br/>|Table name, without the schema name.| ||






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
|<p id="connection table show-c">`-c`</p><br/><p id="connection table show--connection">`--connection`</p><br/>|Connection name| ||
|<p id="connection table show-fw">`-fw`</p><br/><p id="connection table show--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="connection table show-t">`-t`</p><br/><p id="connection table show--table">`--table`</p><br/><p id="connection table show--full-table-name">`--full-table-name`</p><br/>|Full table name (schema.table), supports wildcard patterns &#x27;sch*.tab*&#x27;| ||
|<p id="connection table show--headless">`--headless`</p><br/><p id="connection table show-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="connection table show-h">`-h`</p><br/><p id="connection table show--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="connection table show-of">`-of`</p><br/><p id="connection table show--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="connection edit-c">`-c`</p><br/><p id="connection edit--connection">`--connection`</p><br/>|Connection Name| ||
|<p id="connection edit-fw">`-fw`</p><br/><p id="connection edit--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="connection edit--headless">`--headless`</p><br/><p id="connection edit-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="connection edit-h">`-h`</p><br/><p id="connection edit--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="connection edit-of">`-of`</p><br/><p id="connection edit--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|





