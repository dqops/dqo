# connection

___
### **dqo connection list**

List connections that match a given condition

**Description**

Lists all the created connections for the logged-in user that match the conditions specified in the options. It allows the user to filter connections based on various parameters.


**Command-line synopsis**
```
$ dqo [dqo options...] connection list [-h] [-fw] [-hl] [-n=<name>] [-of=<outputFormat>]
                 [-d=<dimensions>]... [-l=<labels>]...

```
**DQOps Shell synopsis**
```
dqo> connection list [-h] [-fw] [-hl] [-n=<name>] [-of=<outputFormat>]
                 [-d=<dimensions>]... [-l=<labels>]...

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-d`<br/>`--dimension`<br/>|Dimension filter| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-l`<br/>`--label`<br/>|Label filter| ||
|`-n`<br/>`--name`<br/>|Connection name filter| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo connection add**

Add a connection with specified details

**Description**

Creates a new connection to the database with the specified details such as connection name, database type, hostname, username, and password. It allows the user to connect to the database from the application to perform various operations on the database.


**Command-line synopsis**
```
$ dqo [dqo options...] connection add [-h] [-fw] [-hl] [--sqlserver-disable-encryption]
                [--bigquery-authentication-mode=<authenticationMode>]
                [--bigquery-billing-project-id=<billingProjectId>]
                [--bigquery-jobs-create-project=<jobsCreateProject>]
                [--bigquery-json-key-content=<jsonKeyContent>]
                [--bigquery-json-key-path=<jsonKeyPath>]
                [--bigquery-quota-project-id=<quotaProjectId>]
                [--bigquery-source-project-id=<sourceProjectId>]
                [--mysql-database=<database>] [--mysql-host=<host>]
                [--mysql-options=<options>] [--mysql-password=<password>]
                [--mysql-port=<port>] [--mysql-sslmode=<sslmode>]
                [--mysql-user=<user>] [-n=<name>] [-of=<outputFormat>]
                [--oracle-database=<database>] [--oracle-host=<host>]
                [--oracle-initialization-sql=<initializationSql>]
                [--oracle-options=<options>] [--oracle-password=<password>]
                [--oracle-port=<port>] [--oracle-user=<user>]
                [--postgresql-database=<database>] [--postgresql-host=<host>]
                [--postgresql-options=<options>]
                [--postgresql-password=<password>] [--postgresql-port=<port>]
                [--postgresql-sslmode=<sslmode>] [--postgresql-user=<user>]
                [--redshift-database=<database>] [--redshift-host=<host>]
                [--redshift-options=<options>] [--redshift-password=<password>]
                [--redshift-port=<port>] [--redshift-user=<user>]
                [--snowflake-account=<account>]
                [--snowflake-database=<database>]
                [--snowflake-password=<password>] [--snowflake-role=<role>]
                [--snowflake-user=<user>] [--snowflake-warehouse=<warehouse>]
                [--sqlserver-database=<database>] [--sqlserver-host=<host>]
                [--sqlserver-options=<options>]
                [--sqlserver-password=<password>] [--sqlserver-port=<port>]
                [--sqlserver-user=<user>] [-t=<providerType>]
                [-F=<String=String>]... [-M=<String=String>]...
                [-O=<String=String>]... [-P=<String=String>]...
                [-R=<String=String>]... [-S=<String=String>]...

```
**DQOps Shell synopsis**
```
dqo> connection add [-h] [-fw] [-hl] [--sqlserver-disable-encryption]
                [--bigquery-authentication-mode=<authenticationMode>]
                [--bigquery-billing-project-id=<billingProjectId>]
                [--bigquery-jobs-create-project=<jobsCreateProject>]
                [--bigquery-json-key-content=<jsonKeyContent>]
                [--bigquery-json-key-path=<jsonKeyPath>]
                [--bigquery-quota-project-id=<quotaProjectId>]
                [--bigquery-source-project-id=<sourceProjectId>]
                [--mysql-database=<database>] [--mysql-host=<host>]
                [--mysql-options=<options>] [--mysql-password=<password>]
                [--mysql-port=<port>] [--mysql-sslmode=<sslmode>]
                [--mysql-user=<user>] [-n=<name>] [-of=<outputFormat>]
                [--oracle-database=<database>] [--oracle-host=<host>]
                [--oracle-initialization-sql=<initializationSql>]
                [--oracle-options=<options>] [--oracle-password=<password>]
                [--oracle-port=<port>] [--oracle-user=<user>]
                [--postgresql-database=<database>] [--postgresql-host=<host>]
                [--postgresql-options=<options>]
                [--postgresql-password=<password>] [--postgresql-port=<port>]
                [--postgresql-sslmode=<sslmode>] [--postgresql-user=<user>]
                [--redshift-database=<database>] [--redshift-host=<host>]
                [--redshift-options=<options>] [--redshift-password=<password>]
                [--redshift-port=<port>] [--redshift-user=<user>]
                [--snowflake-account=<account>]
                [--snowflake-database=<database>]
                [--snowflake-password=<password>] [--snowflake-role=<role>]
                [--snowflake-user=<user>] [--snowflake-warehouse=<warehouse>]
                [--sqlserver-database=<database>] [--sqlserver-host=<host>]
                [--sqlserver-options=<options>]
                [--sqlserver-password=<password>] [--sqlserver-port=<port>]
                [--sqlserver-user=<user>] [-t=<providerType>]
                [-F=<String=String>]... [-M=<String=String>]...
                [-O=<String=String>]... [-P=<String=String>]...
                [-R=<String=String>]... [-S=<String=String>]...

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`--bigquery-authentication-mode`<br/>|Bigquery authentication mode. The default value uses the current GCP application default credentials. The default GCP credentials is the Service Account of a VM in GCP cloud, a GCP JSON key file whose path is in the GOOGLE_APPLICATION_CREDENTIALS environment variable, or it is the default GCP credentials obtained on a user&#x27;s computer by running &#x27;gcloud auth application-default login&#x27; from the command line.| |google_application_credentials<br/>json_key_content<br/>json_key_path<br/>|
|`--bigquery-billing-project-id`<br/>|Bigquery billing GCP project id. This is the project used as the default GCP project. The calling user must have a bigquery.jobs.create permission in this project.| ||
|`--bigquery-jobs-create-project`<br/>|Configures the way how to select the project that will be used to start BigQuery jobs and will be used for billing. The user/service identified by the credentials must have bigquery.jobs.create permission in that project.| |create_jobs_in_source_project<br/>create_jobs_in_default_project_from_credentials<br/>create_jobs_in_selected_billing_project_id<br/>|
|`--bigquery-json-key-content`<br/>|Bigquery service account key content as JSON.| ||
|`--bigquery-json-key-path`<br/>|Path to a GCP service account key JSON file used to authenticate to Bigquery.| ||
|`--bigquery-quota-project-id`<br/>|Bigquery quota GCP project id.| ||
|`--bigquery-source-project-id`<br/>|Bigquery source GCP project id. This is the project that has datasets that will be imported.| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`--mysql-database`<br/>|MySQL database name. The value can be in the null format to use dynamic substitution.| ||
|`--mysql-host`<br/>|MySQL host name| ||
|`--mysql-options`<br/>|MySQL connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--mysql-password`<br/>|MySQL database password. The value can be in the null format to use dynamic substitution.| ||
|`--mysql-port`<br/>|MySQL port number| ||
|`--mysql-sslmode`<br/>|SslMode MySQL connection parameter| |DISABLED<br/>PREFERRED<br/>REQUIRED<br/>VERIFY_CA<br/>VERIFY_IDENTITY<br/>|
|`--mysql-user`<br/>|MySQL user name. The value can be in the null format to use dynamic substitution.| ||
|`-n`<br/>`--name`<br/>|Connection name| ||
|`--oracle-database`<br/>|Oracle database name. The value can be in the null format to use dynamic substitution.| ||
|`--oracle-host`<br/>|Oracle host name| ||
|`--oracle-initialization-sql`<br/>|Custom SQL that is executed after connecting to Oracle. This SQL script can configure the default language, for example: alter session set NLS_DATE_FORMAT&#x3D;&#x27;YYYY-DD-MM HH24:MI:SS&#x27;| ||
|`--oracle-options`<br/>|Oracle connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--oracle-password`<br/>|Oracle database password. The value can be in the null format to use dynamic substitution.| ||
|`--oracle-port`<br/>|Oracle port number| ||
|`--oracle-user`<br/>|Oracle user name. The value can be in the null format to use dynamic substitution.| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`--postgresql-database`<br/>|PostgreSQL database name. The value can be in the null format to use dynamic substitution.| ||
|`--postgresql-host`<br/>|PostgreSQL host name| ||
|`--postgresql-options`<br/>|PostgreSQL connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--postgresql-password`<br/>|PostgreSQL database password. The value can be in the null format to use dynamic substitution.| ||
|`--postgresql-port`<br/>|PostgreSQL port number| ||
|`--postgresql-sslmode`<br/>|Connect to PostgreSQL using sslmode connection parameter| |disable<br/>allow<br/>prefer<br/>require<br/>verify_ca<br/>verify_full<br/>|
|`--postgresql-user`<br/>|PostgreSQL user name. The value can be in the null format to use dynamic substitution.| ||
|`-t`<br/>`--provider`<br/>|Connection provider type| |bigquery<br/>snowflake<br/>postgresql<br/>redshift<br/>sqlserver<br/>mysql<br/>oracle<br/>|
|`--redshift-database`<br/>|Redshift database name. The value can be in the null format to use dynamic substitution.| ||
|`--redshift-host`<br/>|Redshift host name| ||
|`--redshift-options`<br/>|Redshift connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--redshift-password`<br/>|Redshift database password. The value can be in the null format to use dynamic substitution.| ||
|`--redshift-port`<br/>|Redshift port number| ||
|`--redshift-user`<br/>|Redshift user name. The value can be in the null format to use dynamic substitution.| ||
|`--snowflake-account`<br/>|Snowflake account name, e.q. &lt;account&gt;, &lt;account&gt;-&lt;locator&gt;, &lt;account&gt;.&lt;region&gt; or &lt;account&gt;.&lt;region&gt;.&lt;platform&gt;.| ||
|`--snowflake-database`<br/>|Snowflake database name. The value can be in the null format to use dynamic substitution.| ||
|`--snowflake-password`<br/>|Snowflake database password. The value can be in the null format to use dynamic substitution.| ||
|`--snowflake-role`<br/>|Snowflake role name.| ||
|`--snowflake-user`<br/>|Snowflake user name. The value can be in the null format to use dynamic substitution.| ||
|`--snowflake-warehouse`<br/>|Snowflake warehouse name.| ||
|`--sqlserver-database`<br/>|SQL Server database name. The value can be in the null format to use dynamic substitution.| ||
|`--sqlserver-disable-encryption`<br/>|Disable SSL encryption parameter. The default value is false. You may need to disable encryption when SQL Server is started in Docker.| ||
|`--sqlserver-host`<br/>|SQL Server host name| ||
|`--sqlserver-options`<br/>|SQL Server connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--sqlserver-password`<br/>|SQL Server database password. The value can be in the null format to use dynamic substitution.| ||
|`--sqlserver-port`<br/>|SQL Server port number| ||
|`--sqlserver-user`<br/>|SQL Server user name. The value can be in the null format to use dynamic substitution.| ||
|`-F`<br/>|Snowflake additional properties that are added to the JDBC connection string| ||
|`-M`<br/>|MySQL additional properties that are added to the JDBC connection string| ||
|`-O`<br/>|Oracle&#x27;s additional properties that are added to the JDBC connection string| ||
|`-P`<br/>|PostgreSQL additional properties that are added to the JDBC connection string| ||
|`-R`<br/>|Redshift additional properties that are added to the JDBC connection string| ||
|`-S`<br/>|SQL Server additional properties that are added to the JDBC connection string| ||




___
### **dqo connection remove**

Remove the connection(s) that match a given condition

**Description**

Removes the connection or connections that match the conditions specified in the options. It allows the user to remove any unwanted connections that are no longer needed.


**Command-line synopsis**
```
$ dqo [dqo options...] connection remove [-h] [-fw] [-hl] [-n=<name>] [-of=<outputFormat>]

```
**DQOps Shell synopsis**
```
dqo> connection remove [-h] [-fw] [-hl] [-n=<name>] [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-n`<br/>`--name`<br/>|Connection name| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo connection update**

Update the connection(s) that match a given condition

**Description**

Update the connection or connections that match the conditions specified in the options with new details. It allows the user to modify existing connections in the application.


**Command-line synopsis**
```
$ dqo [dqo options...] connection update [-h] [-fw] [-hl] [--sqlserver-disable-encryption]
                   [--bigquery-authentication-mode=<authenticationMode>]
                   [--bigquery-billing-project-id=<billingProjectId>]
                   [--bigquery-jobs-create-project=<jobsCreateProject>]
                   [--bigquery-json-key-content=<jsonKeyContent>]
                   [--bigquery-json-key-path=<jsonKeyPath>]
                   [--bigquery-quota-project-id=<quotaProjectId>]
                   [--bigquery-source-project-id=<sourceProjectId>]
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
                   [--postgresql-user=<user>] [--redshift-database=<database>]
                   [--redshift-host=<host>] [--redshift-options=<options>]
                   [--redshift-password=<password>] [--redshift-port=<port>]
                   [--redshift-user=<user>] [--snowflake-account=<account>]
                   [--snowflake-database=<database>]
                   [--snowflake-password=<password>] [--snowflake-role=<role>]
                   [--snowflake-user=<user>]
                   [--snowflake-warehouse=<warehouse>]
                   [--sqlserver-database=<database>] [--sqlserver-host=<host>]
                   [--sqlserver-options=<options>]
                   [--sqlserver-password=<password>] [--sqlserver-port=<port>]
                   [--sqlserver-user=<user>] [-F=<String=String>]...
                   [-M=<String=String>]... [-O=<String=String>]...
                   [-P=<String=String>]... [-R=<String=String>]...
                   [-S=<String=String>]...

```
**DQOps Shell synopsis**
```
dqo> connection update [-h] [-fw] [-hl] [--sqlserver-disable-encryption]
                   [--bigquery-authentication-mode=<authenticationMode>]
                   [--bigquery-billing-project-id=<billingProjectId>]
                   [--bigquery-jobs-create-project=<jobsCreateProject>]
                   [--bigquery-json-key-content=<jsonKeyContent>]
                   [--bigquery-json-key-path=<jsonKeyPath>]
                   [--bigquery-quota-project-id=<quotaProjectId>]
                   [--bigquery-source-project-id=<sourceProjectId>]
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
                   [--postgresql-user=<user>] [--redshift-database=<database>]
                   [--redshift-host=<host>] [--redshift-options=<options>]
                   [--redshift-password=<password>] [--redshift-port=<port>]
                   [--redshift-user=<user>] [--snowflake-account=<account>]
                   [--snowflake-database=<database>]
                   [--snowflake-password=<password>] [--snowflake-role=<role>]
                   [--snowflake-user=<user>]
                   [--snowflake-warehouse=<warehouse>]
                   [--sqlserver-database=<database>] [--sqlserver-host=<host>]
                   [--sqlserver-options=<options>]
                   [--sqlserver-password=<password>] [--sqlserver-port=<port>]
                   [--sqlserver-user=<user>] [-F=<String=String>]...
                   [-M=<String=String>]... [-O=<String=String>]...
                   [-P=<String=String>]... [-R=<String=String>]...
                   [-S=<String=String>]...

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`--bigquery-authentication-mode`<br/>|Bigquery authentication mode. The default value uses the current GCP application default credentials. The default GCP credentials is the Service Account of a VM in GCP cloud, a GCP JSON key file whose path is in the GOOGLE_APPLICATION_CREDENTIALS environment variable, or it is the default GCP credentials obtained on a user&#x27;s computer by running &#x27;gcloud auth application-default login&#x27; from the command line.| |google_application_credentials<br/>json_key_content<br/>json_key_path<br/>|
|`--bigquery-billing-project-id`<br/>|Bigquery billing GCP project id. This is the project used as the default GCP project. The calling user must have a bigquery.jobs.create permission in this project.| ||
|`--bigquery-jobs-create-project`<br/>|Configures the way how to select the project that will be used to start BigQuery jobs and will be used for billing. The user/service identified by the credentials must have bigquery.jobs.create permission in that project.| |create_jobs_in_source_project<br/>create_jobs_in_default_project_from_credentials<br/>create_jobs_in_selected_billing_project_id<br/>|
|`--bigquery-json-key-content`<br/>|Bigquery service account key content as JSON.| ||
|`--bigquery-json-key-path`<br/>|Path to a GCP service account key JSON file used to authenticate to Bigquery.| ||
|`--bigquery-quota-project-id`<br/>|Bigquery quota GCP project id.| ||
|`--bigquery-source-project-id`<br/>|Bigquery source GCP project id. This is the project that has datasets that will be imported.| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`--mysql-database`<br/>|MySQL database name. The value can be in the null format to use dynamic substitution.| ||
|`--mysql-host`<br/>|MySQL host name| ||
|`--mysql-options`<br/>|MySQL connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--mysql-password`<br/>|MySQL database password. The value can be in the null format to use dynamic substitution.| ||
|`--mysql-port`<br/>|MySQL port number| ||
|`--mysql-sslmode`<br/>|SslMode MySQL connection parameter| |DISABLED<br/>PREFERRED<br/>REQUIRED<br/>VERIFY_CA<br/>VERIFY_IDENTITY<br/>|
|`--mysql-user`<br/>|MySQL user name. The value can be in the null format to use dynamic substitution.| ||
|`-n`<br/>`--name`<br/>|Connection name, supports wildcards for changing multiple connections at once, i.e. &quot;conn*&quot;| ||
|`--oracle-database`<br/>|Oracle database name. The value can be in the null format to use dynamic substitution.| ||
|`--oracle-host`<br/>|Oracle host name| ||
|`--oracle-initialization-sql`<br/>|Custom SQL that is executed after connecting to Oracle. This SQL script can configure the default language, for example: alter session set NLS_DATE_FORMAT&#x3D;&#x27;YYYY-DD-MM HH24:MI:SS&#x27;| ||
|`--oracle-options`<br/>|Oracle connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--oracle-password`<br/>|Oracle database password. The value can be in the null format to use dynamic substitution.| ||
|`--oracle-port`<br/>|Oracle port number| ||
|`--oracle-user`<br/>|Oracle user name. The value can be in the null format to use dynamic substitution.| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`--postgresql-database`<br/>|PostgreSQL database name. The value can be in the null format to use dynamic substitution.| ||
|`--postgresql-host`<br/>|PostgreSQL host name| ||
|`--postgresql-options`<br/>|PostgreSQL connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--postgresql-password`<br/>|PostgreSQL database password. The value can be in the null format to use dynamic substitution.| ||
|`--postgresql-port`<br/>|PostgreSQL port number| ||
|`--postgresql-sslmode`<br/>|Connect to PostgreSQL using sslmode connection parameter| |disable<br/>allow<br/>prefer<br/>require<br/>verify_ca<br/>verify_full<br/>|
|`--postgresql-user`<br/>|PostgreSQL user name. The value can be in the null format to use dynamic substitution.| ||
|`--redshift-database`<br/>|Redshift database name. The value can be in the null format to use dynamic substitution.| ||
|`--redshift-host`<br/>|Redshift host name| ||
|`--redshift-options`<br/>|Redshift connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--redshift-password`<br/>|Redshift database password. The value can be in the null format to use dynamic substitution.| ||
|`--redshift-port`<br/>|Redshift port number| ||
|`--redshift-user`<br/>|Redshift user name. The value can be in the null format to use dynamic substitution.| ||
|`--snowflake-account`<br/>|Snowflake account name, e.q. &lt;account&gt;, &lt;account&gt;-&lt;locator&gt;, &lt;account&gt;.&lt;region&gt; or &lt;account&gt;.&lt;region&gt;.&lt;platform&gt;.| ||
|`--snowflake-database`<br/>|Snowflake database name. The value can be in the null format to use dynamic substitution.| ||
|`--snowflake-password`<br/>|Snowflake database password. The value can be in the null format to use dynamic substitution.| ||
|`--snowflake-role`<br/>|Snowflake role name.| ||
|`--snowflake-user`<br/>|Snowflake user name. The value can be in the null format to use dynamic substitution.| ||
|`--snowflake-warehouse`<br/>|Snowflake warehouse name.| ||
|`--sqlserver-database`<br/>|SQL Server database name. The value can be in the null format to use dynamic substitution.| ||
|`--sqlserver-disable-encryption`<br/>|Disable SSL encryption parameter. The default value is false. You may need to disable encryption when SQL Server is started in Docker.| ||
|`--sqlserver-host`<br/>|SQL Server host name| ||
|`--sqlserver-options`<br/>|SQL Server connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--sqlserver-password`<br/>|SQL Server database password. The value can be in the null format to use dynamic substitution.| ||
|`--sqlserver-port`<br/>|SQL Server port number| ||
|`--sqlserver-user`<br/>|SQL Server user name. The value can be in the null format to use dynamic substitution.| ||
|`-F`<br/>|Snowflake additional properties that are added to the JDBC connection string| ||
|`-M`<br/>|MySQL additional properties that are added to the JDBC connection string| ||
|`-O`<br/>|Oracle&#x27;s additional properties that are added to the JDBC connection string| ||
|`-P`<br/>|PostgreSQL additional properties that are added to the JDBC connection string| ||
|`-R`<br/>|Redshift additional properties that are added to the JDBC connection string| ||
|`-S`<br/>|SQL Server additional properties that are added to the JDBC connection string| ||




___
### **dqo connection schema list**

List schemas in the specified connection

**Description**

It allows the user to view the summary of all schemas in a selected connection.


**Command-line synopsis**
```
$ dqo [dqo options...] connection schema list [-h] [-fw] [-hl] [-n=<name>] [-of=<outputFormat>]
                        [-d=<dimensions>]... [-l=<labels>]...

```
**DQOps Shell synopsis**
```
dqo> connection schema list [-h] [-fw] [-hl] [-n=<name>] [-of=<outputFormat>]
                        [-d=<dimensions>]... [-l=<labels>]...

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-d`<br/>`--dimension`<br/>|Dimension filter| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-l`<br/>`--label`<br/>|Label filter| ||
|`-n`<br/>`--name`<br/>|Connection name filter| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|




___
### **dqo connection table list**

List tables for the specified connection and schema name.

**Description**

List all the tables available in the database for the specified connection and schema. It allows the user to view all the tables in the database.


**Command-line synopsis**
```
$ dqo [dqo options...] connection table list [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]
                       [-s=<schema>] [-t=<table>] [-d=<dimensions>]...
                       [-l=<labels>]...

```
**DQOps Shell synopsis**
```
dqo> connection table list [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]
                       [-s=<schema>] [-t=<table>] [-d=<dimensions>]...
                       [-l=<labels>]...

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-c`<br/>`--connection`<br/>|Connection name| ||
|`-d`<br/>`--dimension`<br/>|Dimension filter| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-l`<br/>`--label`<br/>|Label filter| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-s`<br/>`--schema`<br/>|Schema name| ||
|`-t`<br/>`--table`<br/>|Table name| ||




___
### **dqo connection table show**

Show table for connection

**Description**

Show the details of the specified table in the database for the specified connection. It allows the user to view the details of a specific table in the database.


**Command-line synopsis**
```
$ dqo [dqo options...] connection table show [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]
                       [-t=<table>]

```
**DQOps Shell synopsis**
```
dqo> connection table show [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]
                       [-t=<table>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-c`<br/>`--connection`<br/>|Connection name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Full table name (schema.table), supports wildcard patterns &#x27;sch*.tab*&#x27;| ||




___
### **dqo connection edit**

Edit connection that matches a given condition

**Description**

Edit the connection or connections that match the filter conditions specified in the options. It allows the user to modify the details of an existing connection in the application.


**Command-line synopsis**
```
$ dqo [dqo options...] connection edit [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]

```
**DQOps Shell synopsis**
```
dqo> connection edit [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-c`<br/>`--connection`<br/>|Connection Name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|



