# table

___
### **dqo table import**

**Description**

Import tables from a specified database


**Command-line synopsis**
```
$ dqo [dqo options...] table import [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]
              [-s=<schema>] [-t=<table>]

```
**DQO Shell synopsis**
```
dqo.ai> table import [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]
              [-s=<schema>] [-t=<table>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`-c`<br/>`--connection`<br/>|Connection Name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-s`<br/>`--schema`<br/>|Schema Name| ||
|`-t`<br/>`--table`<br/>|Table Name| ||




___
### **dqo table edit**

**Description**

Edit table which match filters


**Command-line synopsis**
```
$ dqo [dqo options...] table edit [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>] [-t=<table>]

```
**DQO Shell synopsis**
```
dqo.ai> table edit [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>] [-t=<table>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`-c`<br/>`--connection`<br/>|Connection Name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Full table name (schema.table)| ||




___
### **dqo table add**

**Description**

Add table with specified name


**Command-line synopsis**
```
$ dqo [dqo options...] table add [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
           [-t=<fullTableName>]

```
**DQO Shell synopsis**
```
dqo.ai> table add [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
           [-t=<fullTableName>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`-c`<br/>`--connection`<br/>|Connection Name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Table name| ||




___
### **dqo table remove**

**Description**

Remove tables which match filters


**Command-line synopsis**
```
$ dqo [dqo options...] table remove [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
              [-t=<fullTableName>]

```
**DQO Shell synopsis**
```
dqo.ai> table remove [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
              [-t=<fullTableName>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`-c`<br/>`--connection`<br/>|Connection Name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Table| ||




___
### **dqo table update**

**Description**

Update tables which match filters


**Command-line synopsis**
```
$ dqo [dqo options...] table update [-h] [-fw] [-hl] [-c=<connectionName>] [-n=<newTableName>]
              [-of=<outputFormat>] [-t=<fullTableName>]

```
**DQO Shell synopsis**
```
dqo.ai> table update [-h] [-fw] [-hl] [-c=<connectionName>] [-n=<newTableName>]
              [-of=<outputFormat>] [-t=<fullTableName>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`-c`<br/>`--connection`<br/>|Connection Name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-n`<br/>`--newTable`<br/>|New table name| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Table| ||




___
### **dqo table list**

**Description**

List tables which match filters


**Command-line synopsis**
```
$ dqo [dqo options...] table list [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
            [-t=<tableName>] [-d=<dimensions>]... [-l=<labels>]...

```
**DQO Shell synopsis**
```
dqo.ai> table list [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
            [-t=<tableName>] [-d=<dimensions>]... [-l=<labels>]...

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
|`-t`<br/>`--table`<br/>|Table name filter| ||



