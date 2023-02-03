# table

___
### **dqo table import**

**Description**

Import tables from a specified database

**Summary (Shell)**
```
dqo.ai>  table import [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]
              [-s=<schema>] [-t=<table>]

```


**Options**  
  
| Command | Description | Is it required? | Accepted values |
|---------|-------------|-----------------|-----------------|
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

**Summary (Shell)**
```
dqo.ai>  table edit [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>] [-t=<table>]

```


**Options**  
  
| Command | Description | Is it required? | Accepted values |
|---------|-------------|-----------------|-----------------|
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

**Summary (Shell)**
```
dqo.ai>  table add [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
           [-t=<fullTableName>]

```


**Options**  
  
| Command | Description | Is it required? | Accepted values |
|---------|-------------|-----------------|-----------------|
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

**Summary (Shell)**
```
dqo.ai>  table remove [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
              [-t=<fullTableName>]

```


**Options**  
  
| Command | Description | Is it required? | Accepted values |
|---------|-------------|-----------------|-----------------|
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

**Summary (Shell)**
```
dqo.ai>  table update [-h] [-fw] [-hl] [-c=<connectionName>] [-n=<newTableName>]
              [-of=<outputFormat>] [-t=<fullTableName>]

```


**Options**  
  
| Command | Description | Is it required? | Accepted values |
|---------|-------------|-----------------|-----------------|
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

**Summary (Shell)**
```
dqo.ai>  table list [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
            [-t=<tableName>] [-d=<dimensions>]... [-l=<labels>]...

```


**Options**  
  
| Command | Description | Is it required? | Accepted values |
|---------|-------------|-----------------|-----------------|
|`-c`<br/>`--connection`<br/>|Connection name| ||
|`-d`<br/>`--dimension`<br/>|Dimension filter| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-l`<br/>`--label`<br/>|Label filter| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Table name filter| ||


