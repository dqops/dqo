# connection

___
### **dqo connection list**

**Description**

List connections which match filters


**Summary (Shell)**
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

**Description**

Add connection with specified details


**Summary (Shell)**
```
dqo.ai> connection add [-h] [-fw] [-hl] [--postgresql-ssl] [--redshift-ssl]
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
                [-t=<providerType>] [-F=<String=String>]...
                [-P=<String=String>]... [-R=<String=String>]...

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
|`--postgresql-database`<br/>|PostgreSQL database name. The value could be in the format null to use dynamic substitution.| ||
|`--postgresql-host`<br/>|PostgreSQL host name| ||
|`--postgresql-options`<br/>|PostgreSQL connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--postgresql-password`<br/>|PostgreSQL database password. The value could be in the format null to use dynamic substitution.| ||
|`--postgresql-port`<br/>|PostgreSQL port number| ||
|`--postgresql-ssl`<br/>|Connect to PostgreSQL using SSL| ||
|`--postgresql-user`<br/>|PostgreSQL user name. The value could be in the format null to use dynamic substitution.| ||
|`-t`<br/>`--provider`<br/>|Connection provider type| |bigquery<br/>snowflake<br/>postgresql<br/>redshift<br/>|
|`--redshift-database`<br/>|Redshift database name. The value could be in the format null to use dynamic substitution.| ||
|`--redshift-host`<br/>|Redshift host name| ||
|`--redshift-options`<br/>|Redshift connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--redshift-password`<br/>|Redshift database password. The value could be in the format null to use dynamic substitution.| ||
|`--redshift-port`<br/>|Redshift port number| ||
|`--redshift-ssl`<br/>|Connect to Redshift using SSL| ||
|`--redshift-user`<br/>|Redshift user name. The value could be in the format null to use dynamic substitution.| ||
|`--snowflake-account`<br/>|Snowflake account name, e.q. &lt;account&gt;, &lt;account&gt;-&lt;locator&gt;, &lt;account&gt;.&lt;region&gt; or &lt;account&gt;.&lt;region&gt;.&lt;platform&gt;.| ||
|`--snowflake-database`<br/>|Snowflake database name. The value could be in the format null to use dynamic substitution.| ||
|`--snowflake-password`<br/>|Snowflake database password. The value could be in the format null to use dynamic substitution.| ||
|`--snowflake-role`<br/>|Snowflake role name.| ||
|`--snowflake-user`<br/>|Snowflake user name. The value could be in the format null to use dynamic substitution.| ||
|`--snowflake-warehouse`<br/>|Snowflake warehouse name.| ||
|`-F`<br/>|Snowflake additional properties that are added to the JDBC connection string| ||
|`-P`<br/>|PostgreSQL additional properties that are added to the JDBC connection string| ||
|`-R`<br/>|Redshift additional properties that are added to the JDBC connection string| ||




___
### **dqo connection remove**

**Description**

Remove connection or connections which match filters


**Summary (Shell)**
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

**Description**

Update connection or connections which match filters


**Summary (Shell)**
```
dqo.ai> connection update [-h] [-fw] [-hl] [--postgresql-ssl] [--redshift-ssl]
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
                   [--snowflake-warehouse=<warehouse>] [-F=<String=String>]...
                   [-P=<String=String>]... [-R=<String=String>]...

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
|`--postgresql-database`<br/>|PostgreSQL database name. The value could be in the format null to use dynamic substitution.| ||
|`--postgresql-host`<br/>|PostgreSQL host name| ||
|`--postgresql-options`<br/>|PostgreSQL connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--postgresql-password`<br/>|PostgreSQL database password. The value could be in the format null to use dynamic substitution.| ||
|`--postgresql-port`<br/>|PostgreSQL port number| ||
|`--postgresql-ssl`<br/>|Connect to PostgreSQL using SSL| ||
|`--postgresql-user`<br/>|PostgreSQL user name. The value could be in the format null to use dynamic substitution.| ||
|`--redshift-database`<br/>|Redshift database name. The value could be in the format null to use dynamic substitution.| ||
|`--redshift-host`<br/>|Redshift host name| ||
|`--redshift-options`<br/>|Redshift connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes.| ||
|`--redshift-password`<br/>|Redshift database password. The value could be in the format null to use dynamic substitution.| ||
|`--redshift-port`<br/>|Redshift port number| ||
|`--redshift-ssl`<br/>|Connect to Redshift using SSL| ||
|`--redshift-user`<br/>|Redshift user name. The value could be in the format null to use dynamic substitution.| ||
|`--snowflake-account`<br/>|Snowflake account name, e.q. &lt;account&gt;, &lt;account&gt;-&lt;locator&gt;, &lt;account&gt;.&lt;region&gt; or &lt;account&gt;.&lt;region&gt;.&lt;platform&gt;.| ||
|`--snowflake-database`<br/>|Snowflake database name. The value could be in the format null to use dynamic substitution.| ||
|`--snowflake-password`<br/>|Snowflake database password. The value could be in the format null to use dynamic substitution.| ||
|`--snowflake-role`<br/>|Snowflake role name.| ||
|`--snowflake-user`<br/>|Snowflake user name. The value could be in the format null to use dynamic substitution.| ||
|`--snowflake-warehouse`<br/>|Snowflake warehouse name.| ||
|`-F`<br/>|Snowflake additional properties that are added to the JDBC connection string| ||
|`-P`<br/>|PostgreSQL additional properties that are added to the JDBC connection string| ||
|`-R`<br/>|Redshift additional properties that are added to the JDBC connection string| ||




___
### **dqo connection schema list**

**Description**

List schemas in source connection


**Summary (Shell)**
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

**Description**

List tables for connection


**Summary (Shell)**
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

**Description**

Show table for connection


**Summary (Shell)**
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
|`-t`<br/>`--table`<br/>|Full table name| ||




___
### **dqo connection edit**

**Description**

Edit connection which match filters


**Summary (Shell)**
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



