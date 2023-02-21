# column

___
### **dqo column add**

**Description**

Add a column with specified details


**Summary (Shell)**
```
dqo.ai> column add [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
            [-d=<dataType>] [-of=<outputFormat>] [-t=<fullTableName>]

```

**Options**  
  
| Command | Description | Is it required? | Accepted values |
|---------|-------------|-----------------|-----------------|
|`-C`<br/>`--column`<br/>|Column name| ||
|`-c`<br/>`--connection`<br/>|Connection name| ||
|`-d`<br/>`--dataType`<br/>|Data type| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Table name| ||




___
### **dqo column remove**

**Description**

Remove column or columns which match filters


**Summary (Shell)**
```
dqo.ai> column remove [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-of=<outputFormat>] [-t=<fullTableName>]

```

**Options**  
  
| Command | Description | Is it required? | Accepted values |
|---------|-------------|-----------------|-----------------|
|`-C`<br/>`--column`<br/>|Column name| ||
|`-c`<br/>`--connection`<br/>|Connection name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Table name| ||




___
### **dqo column update**

**Description**

Update column or columns which match filters


**Summary (Shell)**
```
dqo.ai> column update [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-d=<dataType>] [-of=<outputFormat>] [-t=<fullTableName>]

```

**Options**  
  
| Command | Description | Is it required? | Accepted values |
|---------|-------------|-----------------|-----------------|
|`-C`<br/>`--column`<br/>|Column name| ||
|`-c`<br/>`--connection`<br/>|Connection name| ||
|`-d`<br/>`--dataType`<br/>|Data type| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Table name| ||




___
### **dqo column list**

**Description**

List columns which match filters


**Summary (Shell)**
```
dqo.ai> column list [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
             [-of=<outputFormat>] [-t=<fullTableName>] [-l=<labels>]...
             [-tg=<tags>]...

```

**Options**  
  
| Command | Description | Is it required? | Accepted values |
|---------|-------------|-----------------|-----------------|
|`-C`<br/>`--column`<br/>|Connection name filter| ||
|`-c`<br/>`--connection`<br/>|Connection name filter| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-l`<br/>`--label`<br/>|Label filter| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Table name filter| ||
|`-tg`<br/>`--tags`<br/>|Data stream tag filter| ||




___
### **dqo column enable**

**Description**

Enable column or columns which match filters


**Summary (Shell)**
```
dqo.ai> column enable [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-of=<outputFormat>] [-t=<fullTableName>]

```

**Options**  
  
| Command | Description | Is it required? | Accepted values |
|---------|-------------|-----------------|-----------------|
|`-C`<br/>`--column`<br/>|Column name| ||
|`-c`<br/>`--connection`<br/>|Connection name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Table name| ||




___
### **dqo column disable**

**Description**

Disable column or columns which match filters


**Summary (Shell)**
```
dqo.ai> column disable [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
                [-of=<outputFormat>] [-t=<fullTableName>]

```

**Options**  
  
| Command | Description | Is it required? | Accepted values |
|---------|-------------|-----------------|-----------------|
|`-C`<br/>`--column`<br/>|Column name| ||
|`-c`<br/>`--connection`<br/>|Connection name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Table name| ||




___
### **dqo column rename**

**Description**

Rename column which match filters


**Summary (Shell)**
```
dqo.ai> column rename [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-n=<newColumnName>] [-of=<outputFormat>] [-t=<fullTableName>]

```

**Options**  
  
| Command | Description | Is it required? | Accepted values |
|---------|-------------|-----------------|-----------------|
|`-C`<br/>`--column`<br/>|Column name| ||
|`-c`<br/>`--connection`<br/>|Connection name| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-n`<br/>`--newColumn`<br/>|New column name| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-t`<br/>`--table`<br/>|Table name| ||



