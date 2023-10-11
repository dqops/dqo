# column

___
### **dqo column add**

Add a column with specified details

**Description**

Add a new column to a table with specific details. The new column is added to the YAML configuration file.


**Command-line synopsis**
```
$ dqo [dqo options...] column add [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
            [-d=<dataType>] [-e=<sqlExpression>] [-of=<outputFormat>]
            [-t=<fullTableName>]

```
**DQOps Shell synopsis**
```
dqo> column add [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
            [-d=<dataType>] [-e=<sqlExpression>] [-of=<outputFormat>]
            [-t=<fullTableName>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-C`<br/>`--column`<br/>|Column name| ||
|`-c`<br/>`--connection`<br/>|Connection name| ||
|`-d`<br/>`--dataType`<br/>|Data type| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-e`<br/>`--sql-expression`<br/>|SQL expression for a calculated column| ||
|`-t`<br/>`--table`<br/>|Table name| ||




___
### **dqo column remove**

Remove the column(s) that match a given condition

**Description**

Remove one or more columns from a table that match a specified condition. Users can filter the column.


**Command-line synopsis**
```
$ dqo [dqo options...] column remove [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-of=<outputFormat>] [-t=<fullTableName>]

```
**DQOps Shell synopsis**
```
dqo> column remove [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-of=<outputFormat>] [-t=<fullTableName>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-C`<br/>`--column`<br/>|Column name| ||
|`-c`<br/>`--connection`<br/>|Connection name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Table name| ||




___
### **dqo column update**

Update the column(s) that match a given condition

**Description**

Update one or more columns in a table that match a specified condition.


**Command-line synopsis**
```
$ dqo [dqo options...] column update [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-d=<dataType>] [-e=<sqlExpression>] [-of=<outputFormat>]
               [-t=<fullTableName>]

```
**DQOps Shell synopsis**
```
dqo> column update [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-d=<dataType>] [-e=<sqlExpression>] [-of=<outputFormat>]
               [-t=<fullTableName>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-C`<br/>`--column`<br/>|Column name| ||
|`-c`<br/>`--connection`<br/>|Connection name| ||
|`-d`<br/>`--dataType`<br/>|Data type| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-e`<br/>`--sql-expression`<br/>|SQL expression for a calculated column| ||
|`-t`<br/>`--table`<br/>|Table name| ||




___
### **dqo column list**

List the columns that match a given condition

**Description**

List all the columns in a table or filter them based on a specified condition.


**Command-line synopsis**
```
$ dqo [dqo options...] column list [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
             [-of=<outputFormat>] [-t=<fullTableName>] [-l=<labels>]...
             [-tg=<tags>]...

```
**DQOps Shell synopsis**
```
dqo> column list [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
             [-of=<outputFormat>] [-t=<fullTableName>] [-l=<labels>]...
             [-tg=<tags>]...

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-C`<br/>`--column`<br/>|Connection name filter| ||
|`-c`<br/>`--connection`<br/>|Connection name filter| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-l`<br/>`--label`<br/>|Label filter| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Table name filter| ||
|`-tg`<br/>`--tags`<br/>|Data stream tag filter| ||




___
### **dqo column enable**

Enable the column(s) filtered by the given conditions

**Description**

Enable one or more columns in a table based on a specified condition. This command will restore the data in the previously disabled columns.


**Command-line synopsis**
```
$ dqo [dqo options...] column enable [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-of=<outputFormat>] [-t=<fullTableName>]

```
**DQOps Shell synopsis**
```
dqo> column enable [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-of=<outputFormat>] [-t=<fullTableName>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-C`<br/>`--column`<br/>|Column name| ||
|`-c`<br/>`--connection`<br/>|Connection name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Table name| ||




___
### **dqo column disable**

Disable the column(s)filtered by the given conditions

**Description**

Disable one or more columns in a table based on a specified condition. Disabling a column will prevent it from being queried or updated until it is enabled again.


**Command-line synopsis**
```
$ dqo [dqo options...] column disable [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
                [-of=<outputFormat>] [-t=<fullTableName>]

```
**DQOps Shell synopsis**
```
dqo> column disable [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
                [-of=<outputFormat>] [-t=<fullTableName>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-C`<br/>`--column`<br/>|Column name| ||
|`-c`<br/>`--connection`<br/>|Connection name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Table name| ||




___
### **dqo column rename**

Rename the column filtered by the given conditions

**Description**

Rename one or more columns in a table based on a specified condition.


**Command-line synopsis**
```
$ dqo [dqo options...] column rename [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-n=<newColumnName>] [-of=<outputFormat>] [-t=<fullTableName>]

```
**DQOps Shell synopsis**
```
dqo> column rename [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-n=<newColumnName>] [-of=<outputFormat>] [-t=<fullTableName>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-C`<br/>`--column`<br/>|Column name| ||
|`-c`<br/>`--connection`<br/>|Connection name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-n`<br/>`--newColumn`<br/>|New column name| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Table name| ||



