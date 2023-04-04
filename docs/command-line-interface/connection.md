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
**DQO Shell synopsis**
```
dqo.ai> connection list [-h] [-fw] [-hl] [-n=<name>] [-of=<outputFormat>]
                 [-d=<dimensions>]... [-l=<labels>]...

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`-d`<br/>`--dimension`<br/>|Dimension filter| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
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
$ dqo [dqo options...] connection add [-h] [-fw] [-hl] [--postgresql-ssl] [--redshift-ssl]
                [--sqlserver-ssl]
                [--bigquery-authentication-mode=<authenticationMode>]
                [--bigquery-billing-project-id=<billingProjectId>]
                [--bigquery-json-key-content=<jsonKeyContent>]
                [--bigquery-json-key-path=<jsonKeyPath>]
                [--bigquery-quota-project-id=<quotaProjectId>]
                [--bigquery-source-project-id=<sourceProjectId>] [-n=<name>]
                [-of=<outputFormat>] [--postgresql-database=<database>]
                [--postgresql-host=<host>] [--postgresql-options=<options>]
                [--postgresql-password=<password>] [--postgresql-port=<port>]
                [--postgresql-user=<user>] [--redshift-database=<database>]
                [--redshift-host=<host>] [--redshift-options=<options>]
                [--redshift-password=<password>] [--redshift-port=<port>]
                [--redshift-user=<user>] [--snowflake-account=<account>]
                [--snowflake-database=<database>]
                [--snowflake-password=<password>] [--snowflake-role=<role>]
                [--snowflake-user=<user>] [--snowflake-warehouse=<warehouse>]
                [--sqlserver-database=<database>] [--sqlserver-host=<host>]
                [--sqlserver-options=<options>]
                [--sqlserver-password=<password>] [--sqlserver-port=<port>]
                [--sqlserver-user=<user>] [-t=<providerType>]
                [-F=<String=String>]... [-P=<String=String>]...
                [-R=<String=String>]...
                [--sqlserver-properties=<String=String>]...

```
**DQO Shell synopsis**
```
dqo.ai> connection add [-h] [-fw] [-hl] [--postgresql-ssl] [--redshift-ssl]
                [--sqlserver-ssl]
                [--bigquery-authentication-mode=<authenticationMode>]
                [--bigquery-billing-project-id=<billingProjectId>]
                [--bigquery-json-key-content=<jsonKeyContent>]
                [--bigquery-json-key-path=<jsonKeyPath>]
                [--bigquery-quota-project-id=<quotaProjectId>]
                [--bigquery-source-project-id=<sourceProjectId>] [-n=<name>]
                [-of=<outputFormat>] [--postgresql-database=<database>]
                [--postgresql-host=<host>] [--postgresql-options=<options>]
                [--postgresql-password=<password>] [--postgresql-port=<port>]
                [--postgresql-user=<user>] [--redshift-database=<database>]
                [--redshift-host=<host>] [--redshift-options=<options>]
                [--redshift-password=<password>] [--redshift-port=<port>]
                [--redshift-user=<user>] [--snowflake-account=<account>]
                [--snowflake-database=<database>]
                [--snowflake-password=<password>] [--snowflake-role=<role>]
                [--snowflake-user=<user>] [--snowflake-warehouse=<warehouse>]
                [--sqlserver-database=<database>] [--sqlserver-host=<host>]
                [--sqlserver-options=<options>]
                [--sqlserver-password=<password>] [--sqlserver-port=<port>]
                [--sqlserver-user=<user>] [-t=<providerType>]
                [-F=<String=String>]... [-P=<String=String>]...
                [-R=<String=String>]...
                [--sqlserver-properties=<String=String>]...

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`--bigquery-authentication-mode`<br/>|Bigquery authentication mode.| |google_application_credentials<br/>json_key_content<br/>json_key_path<br/>|
|`--bigquery-billing-project-id`<br/>|Bigquery billing GCP project id. This is the project used as the default GCP project. The calling user must have a bigquery.jobs.create permission in this project.| ||
|`--bigquery-json-key-content`<br/>|Bigquery service account key content as JSON.| ||
|`--bigquery-json-key-path`<br/>|Path to a GCP service account key JSON file used to authenticate to Bigquery.| ||
|`--bigquery-quota-project-id`<br/>|Bigquery quota GCP project id.| ||
|`--bigquery-source-project-id`<br/>|Bigquery source GCP project id. This is the project that has datasets that will be imported.| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-n`<br/>`--name`<br/>|Connection name| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`--postgresql-database`<br/>|PostgreSQL database name. The value can be in the null format to use dynamic substitution.| ||
|`--postgresql-host`<br/>|PostgreSQL host name| ||
|`--postgresql-options`<br/>|PostgreSQL connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--postgresql-password`<br/>|PostgreSQL database password. The value can be in the null format to use dynamic substitution.| ||
|`--postgresql-port`<br/>|PostgreSQL port number| ||
|`--postgresql-ssl`<br/>|Connect to PostgreSQL using SSL| ||
|`--postgresql-user`<br/>|PostgreSQL user name. The value can be in the null format to use dynamic substitution.| ||
|`-t`<br/>`--provider`<br/>|Connection provider type| |bigquery<br/>snowflake<br/>postgresql<br/>redshift<br/>sqlserver<br/>|
|`--redshift-database`<br/>|Redshift database name.  The value can be in the null format to use dynamic substitution.| ||
|`--redshift-host`<br/>|Redshift host name| ||
|`--redshift-options`<br/>|Redshift connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--redshift-password`<br/>|Redshift database password.  The value can be in the null format to use dynamic substitution.| ||
|`--redshift-port`<br/>|Redshift port number| ||
|`--redshift-ssl`<br/>|Connect to Redshift using SSL| ||
|`--redshift-user`<br/>|Redshift user name.  The value can be in the null format to use dynamic substitution.| ||
|`--snowflake-account`<br/>|Snowflake account name, e.q. &lt;account&gt;, &lt;account&gt;-&lt;locator&gt;, &lt;account&gt;.&lt;region&gt; or &lt;account&gt;.&lt;region&gt;.&lt;platform&gt;.| ||
|`--snowflake-database`<br/>|Snowflake database name.  The value can be in the null format to use dynamic substitution.| ||
|`--snowflake-password`<br/>|Snowflake database password.  The value can be in the null format to use dynamic substitution.| ||
|`--snowflake-role`<br/>|Snowflake role name.| ||
|`--snowflake-user`<br/>|Snowflake user name.  The value can be in the null format to use dynamic substitution.| ||
|`--snowflake-warehouse`<br/>|Snowflake warehouse name.| ||
|`--sqlserver-database`<br/>|SQL Server database name. The value could be in the format null to use dynamic substitution.| ||
|`--sqlserver-host`<br/>|SQL Server host name| ||
|`--sqlserver-options`<br/>|SQL Server connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--sqlserver-password`<br/>|SQL Server database password. The value could be in the format null to use dynamic substitution.| ||
|`--sqlserver-port`<br/>|SQL Server port number| ||
|`--sqlserver-properties`<br/>|SQL Server additional properties that are added to the JDBC connection string| ||
|`--sqlserver-ssl`<br/>|Connecting to SQL Server with SSL disabled| ||
|`--sqlserver-user`<br/>|SQL Server user name. The value could be in the format null to use dynamic substitution.| ||
|`-F`<br/>|Snowflake additional properties that are added to the JDBC connection string| ||
|`-P`<br/>|PostgreSQL additional properties that are added to the JDBC connection string| ||
|`-R`<br/>|Redshift additional properties that are added to the JDBC connection string| ||




___
### **dqo connection remove**

Remove the connection(s) that match a given condition

**Description**

Removes the connection or connections that match the conditions specified in the options. It allows the user to remove any unwanted connections that are no longer needed.


**Command-line synopsis**
```
$ dqo [dqo options...] connection remove [-h] [-fw] [-hl] [-n=<name>] [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo.ai> connection remove [-h] [-fw] [-hl] [-n=<name>] [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
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
$ dqo [dqo options...] connection update [-h] [-fw] [-hl] [--postgresql-ssl] [--redshift-ssl]
                   [--sqlserver-ssl]
                   [--bigquery-authentication-mode=<authenticationMode>]
                   [--bigquery-billing-project-id=<billingProjectId>]
                   [--bigquery-json-key-content=<jsonKeyContent>]
                   [--bigquery-json-key-path=<jsonKeyPath>]
                   [--bigquery-quota-project-id=<quotaProjectId>]
                   [--bigquery-source-project-id=<sourceProjectId>] [-n=<name>]
                   [-of=<outputFormat>] [--postgresql-database=<database>]
                   [--postgresql-host=<host>] [--postgresql-options=<options>]
                   [--postgresql-password=<password>]
                   [--postgresql-port=<port>] [--postgresql-user=<user>]
                   [--redshift-database=<database>] [--redshift-host=<host>]
                   [--redshift-options=<options>]
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
                   [-P=<String=String>]... [-R=<String=String>]...
                   [--sqlserver-properties=<String=String>]...

```
**DQO Shell synopsis**
```
dqo.ai> connection update [-h] [-fw] [-hl] [--postgresql-ssl] [--redshift-ssl]
                   [--sqlserver-ssl]
                   [--bigquery-authentication-mode=<authenticationMode>]
                   [--bigquery-billing-project-id=<billingProjectId>]
                   [--bigquery-json-key-content=<jsonKeyContent>]
                   [--bigquery-json-key-path=<jsonKeyPath>]
                   [--bigquery-quota-project-id=<quotaProjectId>]
                   [--bigquery-source-project-id=<sourceProjectId>] [-n=<name>]
                   [-of=<outputFormat>] [--postgresql-database=<database>]
                   [--postgresql-host=<host>] [--postgresql-options=<options>]
                   [--postgresql-password=<password>]
                   [--postgresql-port=<port>] [--postgresql-user=<user>]
                   [--redshift-database=<database>] [--redshift-host=<host>]
                   [--redshift-options=<options>]
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
                   [-P=<String=String>]... [-R=<String=String>]...
                   [--sqlserver-properties=<String=String>]...

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`--bigquery-authentication-mode`<br/>|Bigquery authentication mode.| |google_application_credentials<br/>json_key_content<br/>json_key_path<br/>|
|`--bigquery-billing-project-id`<br/>|Bigquery billing GCP project id. This is the project used as the default GCP project. The calling user must have a bigquery.jobs.create permission in this project.| ||
|`--bigquery-json-key-content`<br/>|Bigquery service account key content as JSON.| ||
|`--bigquery-json-key-path`<br/>|Path to a GCP service account key JSON file used to authenticate to Bigquery.| ||
|`--bigquery-quota-project-id`<br/>|Bigquery quota GCP project id.| ||
|`--bigquery-source-project-id`<br/>|Bigquery source GCP project id. This is the project that has datasets that will be imported.| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-n`<br/>`--name`<br/>|Connection name, supports wildcards for changing multiple connections at once, i.e. &quot;conn*&quot;| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`--postgresql-database`<br/>|PostgreSQL database name. The value can be in the null format to use dynamic substitution.| ||
|`--postgresql-host`<br/>|PostgreSQL host name| ||
|`--postgresql-options`<br/>|PostgreSQL connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--postgresql-password`<br/>|PostgreSQL database password. The value can be in the null format to use dynamic substitution.| ||
|`--postgresql-port`<br/>|PostgreSQL port number| ||
|`--postgresql-ssl`<br/>|Connect to PostgreSQL using SSL| ||
|`--postgresql-user`<br/>|PostgreSQL user name. The value can be in the null format to use dynamic substitution.| ||
|`--redshift-database`<br/>|Redshift database name.  The value can be in the null format to use dynamic substitution.| ||
|`--redshift-host`<br/>|Redshift host name| ||
|`--redshift-options`<br/>|Redshift connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--redshift-password`<br/>|Redshift database password.  The value can be in the null format to use dynamic substitution.| ||
|`--redshift-port`<br/>|Redshift port number| ||
|`--redshift-ssl`<br/>|Connect to Redshift using SSL| ||
|`--redshift-user`<br/>|Redshift user name.  The value can be in the null format to use dynamic substitution.| ||
|`--snowflake-account`<br/>|Snowflake account name, e.q. &lt;account&gt;, &lt;account&gt;-&lt;locator&gt;, &lt;account&gt;.&lt;region&gt; or &lt;account&gt;.&lt;region&gt;.&lt;platform&gt;.| ||
|`--snowflake-database`<br/>|Snowflake database name.  The value can be in the null format to use dynamic substitution.| ||
|`--snowflake-password`<br/>|Snowflake database password.  The value can be in the null format to use dynamic substitution.| ||
|`--snowflake-role`<br/>|Snowflake role name.| ||
|`--snowflake-user`<br/>|Snowflake user name.  The value can be in the null format to use dynamic substitution.| ||
|`--snowflake-warehouse`<br/>|Snowflake warehouse name.| ||
|`--sqlserver-database`<br/>|SQL Server database name. The value could be in the format null to use dynamic substitution.| ||
|`--sqlserver-host`<br/>|SQL Server host name| ||
|`--sqlserver-options`<br/>|SQL Server connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--sqlserver-password`<br/>|SQL Server database password. The value could be in the format null to use dynamic substitution.| ||
|`--sqlserver-port`<br/>|SQL Server port number| ||
|`--sqlserver-properties`<br/>|SQL Server additional properties that are added to the JDBC connection string| ||
|`--sqlserver-ssl`<br/>|Connecting to SQL Server with SSL disabled| ||
|`--sqlserver-user`<br/>|SQL Server user name. The value could be in the format null to use dynamic substitution.| ||
|`-F`<br/>|Snowflake additional properties that are added to the JDBC connection string| ||
|`-P`<br/>|PostgreSQL additional properties that are added to the JDBC connection string| ||
|`-R`<br/>|Redshift additional properties that are added to the JDBC connection string| ||




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
**DQO Shell synopsis**
```
dqo.ai> connection schema list [-h] [-fw] [-hl] [-n=<name>] [-of=<outputFormat>]
                        [-d=<dimensions>]... [-l=<labels>]...

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`-d`<br/>`--dimension`<br/>|Dimension filter| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
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
**DQO Shell synopsis**
```
dqo.ai> connection table list [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]
                       [-s=<schema>] [-t=<table>] [-d=<dimensions>]...
                       [-l=<labels>]...

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`-c`<br/>`--connection`<br/>|Connection name| ||
|`-d`<br/>`--dimension`<br/>|Dimension filter| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
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
**DQO Shell synopsis**
```
dqo.ai> connection table show [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]
                       [-t=<table>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`-c`<br/>`--connection`<br/>|Connection name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
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
$ dqo [dqo options...] connection edit [-h] [-fw] [-hl] [-n=<connection>] [-of=<outputFormat>]

```
**DQO Shell synopsis**
```
dqo.ai> connection edit [-h] [-fw] [-hl] [-n=<connection>] [-of=<outputFormat>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`-n`<br/>`--connection`<br/>|Connection Name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|



