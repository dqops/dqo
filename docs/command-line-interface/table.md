# table

___
### **dqo table import**

Import tables from a specified database

**Description**

Import the tables from the specified database into the application. It allows the user to import the tables from the database into the application for performing various database operations.


**Command-line synopsis**
```
$ dqo [dqo options...] table import [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]
              [-s=<schema>] [-t=<table>]

```
**DQOps Shell synopsis**
```
dqo> table import [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]
              [-s=<schema>] [-t=<table>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-c`<br/>`--connection`<br/>|Connection Name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-s`<br/>`--schema`<br/>|Schema Name| ||
|`-t`<br/>`--table`<br/>|Table Name| ||




___
### **dqo table edit**

Edit table that matches a given condition

**Description**

Edit the table or tables that match the filter conditions specified in the options. It allows the user to modify the details of an existing table in the application.


**Command-line synopsis**
```
$ dqo [dqo options...] table edit [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>] [-t=<table>]

```
**DQOps Shell synopsis**
```
dqo> table edit [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>] [-t=<table>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-c`<br/>`--connection`<br/>|Connection Name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Full table name (schema.table), supports wildcard patterns &#x27;sch*.tab*&#x27;| ||




___
### **dqo table add**

Add table with specified name

**Description**

Add a new table with the specified name to the database. It allows the user to create a new table in the application for performing various operations.


**Command-line synopsis**
```
$ dqo [dqo options...] table add [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
           [-t=<fullTableName>]

```
**DQOps Shell synopsis**
```
dqo> table add [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
           [-t=<fullTableName>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-c`<br/>`--connection`<br/>|Connection Name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Table name| ||




___
### **dqo table remove**

Remove tables that match a given condition

**Description**

Remove one or more tables that match a given condition. It allows user to use various filters, such as table names to narrow down the set of tables to remove.


**Command-line synopsis**
```
$ dqo [dqo options...] table remove [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
              [-t=<fullTableName>]

```
**DQOps Shell synopsis**
```
dqo> table remove [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
              [-t=<fullTableName>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-c`<br/>`--connection`<br/>|Connection Name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Table| ||




___
### **dqo table update**

Update tables that match a given condition

**Description**

Update the structure of one or more tables that match a given condition. It allows user to use various filters, such as table names  to narrow down the set of tables to update.


**Command-line synopsis**
```
$ dqo [dqo options...] table update [-h] [-fw] [-hl] [-c=<connectionName>] [-n=<newTableName>]
              [-of=<outputFormat>] [-t=<fullTableName>]

```
**DQOps Shell synopsis**
```
dqo> table update [-h] [-fw] [-hl] [-c=<connectionName>] [-n=<newTableName>]
              [-of=<outputFormat>] [-t=<fullTableName>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-c`<br/>`--connection`<br/>|Connection Name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-n`<br/>`--newTable`<br/>|New table name| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Table| ||




___
### **dqo table list**

List tables filtered by the given conditions

**Description**

List all the tables that match a given condition. It allows the user to use various filters, such as table name or schema names to list filtered tables.


**Command-line synopsis**
```
$ dqo [dqo options...] table list [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
            [-t=<tableName>] [-d=<dimensions>]... [-l=<labels>]...

```
**DQOps Shell synopsis**
```
dqo> table list [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
            [-t=<tableName>] [-d=<dimensions>]... [-l=<labels>]...

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
|`-t`<br/>`--table`<br/>|Table name filter| ||



