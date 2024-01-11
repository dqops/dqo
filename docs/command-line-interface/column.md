# dqo column command


Modify and list columns



___

## dqo column add

Add a column with specified details


**Description**


Add a new column to a table with specific details. The new column is added to the YAML configuration file.




**Command-line synopsis**

```
$ dqo [dqo options...] column add [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
            [-d=<dataType>] [-e=<sqlExpression>] [-of=<outputFormat>]
            [-t=<fullTableName>]

```

**DQOps shell synopsis**

```
dqo> column add [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
            [-d=<dataType>] [-e=<sqlExpression>] [-of=<outputFormat>]
            [-t=<fullTableName>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<p id="column add-C">`-C`</p><br/><p id="column add--column">`--column`</p><br/>|Column name| ||
|<p id="column add-c">`-c`</p><br/><p id="column add--connection">`--connection`</p><br/>|Connection name| ||
|<p id="column add-d">`-d`</p><br/><p id="column add--dataType">`--dataType`</p><br/>|Data type| ||
|<p id="column add-fw">`-fw`</p><br/><p id="column add--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="column add-t">`-t`</p><br/><p id="column add--table">`--table`</p><br/><p id="column add--full-table-name">`--full-table-name`</p><br/>|Full table name in the form &quot;schema.table&quot;.| ||
|<p id="column add--headless">`--headless`</p><br/><p id="column add-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="column add-h">`-h`</p><br/><p id="column add--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="column add-of">`-of`</p><br/><p id="column add--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="column add-e">`-e`</p><br/><p id="column add--sql-expression">`--sql-expression`</p><br/>|SQL expression for a calculated column| ||






___

## dqo column remove

Remove the column(s) that match a given condition


**Description**


Remove one or more columns from a table that match a specified condition. Users can filter the column.




**Command-line synopsis**

```
$ dqo [dqo options...] column remove [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-of=<outputFormat>] [-t=<fullTableName>]

```

**DQOps shell synopsis**

```
dqo> column remove [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-of=<outputFormat>] [-t=<fullTableName>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<p id="column remove-C">`-C`</p><br/><p id="column remove--column">`--column`</p><br/>|Column name, supports patterns: c_*, *_id, prefix*suffix.| ||
|<p id="column remove-c">`-c`</p><br/><p id="column remove--connection">`--connection`</p><br/>|Connection name| ||
|<p id="column remove-fw">`-fw`</p><br/><p id="column remove--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="column remove-t">`-t`</p><br/><p id="column remove--table">`--table`</p><br/><p id="column remove--full-table-name">`--full-table-name`</p><br/>|Full table name filter in the form &quot;schema.table&quot;, but also supporting patterns: public.*, *.customers, landing*.customer*.| ||
|<p id="column remove--headless">`--headless`</p><br/><p id="column remove-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="column remove-h">`-h`</p><br/><p id="column remove--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="column remove-of">`-of`</p><br/><p id="column remove--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






___

## dqo column update

Update the column(s) that match a given condition


**Description**


Update one or more columns in a table that match a specified condition.




**Command-line synopsis**

```
$ dqo [dqo options...] column update [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-d=<dataType>] [-e=<sqlExpression>] [-of=<outputFormat>]
               [-t=<fullTableName>]

```

**DQOps shell synopsis**

```
dqo> column update [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-d=<dataType>] [-e=<sqlExpression>] [-of=<outputFormat>]
               [-t=<fullTableName>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<p id="column update-C">`-C`</p><br/><p id="column update--column">`--column`</p><br/>|Column name| ||
|<p id="column update-c">`-c`</p><br/><p id="column update--connection">`--connection`</p><br/>|Connection name| ||
|<p id="column update-d">`-d`</p><br/><p id="column update--dataType">`--dataType`</p><br/>|Data type| ||
|<p id="column update-fw">`-fw`</p><br/><p id="column update--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="column update-t">`-t`</p><br/><p id="column update--table">`--table`</p><br/><p id="column update--full-table-name">`--full-table-name`</p><br/>|Full table name in the form &quot;schema.table&quot;.| ||
|<p id="column update--headless">`--headless`</p><br/><p id="column update-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="column update-h">`-h`</p><br/><p id="column update--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="column update-of">`-of`</p><br/><p id="column update--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="column update-e">`-e`</p><br/><p id="column update--sql-expression">`--sql-expression`</p><br/>|SQL expression for a calculated column| ||






___

## dqo column list

List the columns that match a given condition


**Description**


List all the columns in a table or filter them based on a specified condition.




**Command-line synopsis**

```
$ dqo [dqo options...] column list [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
             [-of=<outputFormat>] [-t=<fullTableName>] [-l=<labels>]...
             [-tg=<tags>]...

```

**DQOps shell synopsis**

```
dqo> column list [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
             [-of=<outputFormat>] [-t=<fullTableName>] [-l=<labels>]...
             [-tg=<tags>]...

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<p id="column list-C">`-C`</p><br/><p id="column list--column">`--column`</p><br/>|Connection name filter| ||
|<p id="column list-c">`-c`</p><br/><p id="column list--connection">`--connection`</p><br/>|Connection name filter| ||
|<p id="column list-fw">`-fw`</p><br/><p id="column list--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="column list-t">`-t`</p><br/><p id="column list--table">`--table`</p><br/><p id="column list--full-table-name">`--full-table-name`</p><br/>|Full table name filter in the form &quot;schema.table&quot;, but also supporting patterns: public.*, *.customers, landing*.customer*.| ||
|<p id="column list--headless">`--headless`</p><br/><p id="column list-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="column list-h">`-h`</p><br/><p id="column list--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="column list-l">`-l`</p><br/><p id="column list--label">`--label`</p><br/>|Label filter| ||
|<p id="column list-of">`-of`</p><br/><p id="column list--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="column list-tg">`-tg`</p><br/><p id="column list--tags">`--tags`</p><br/>|Data grouping static tag filter| ||






___

## dqo column enable

Enable the column(s) filtered by the given conditions


**Description**


Enable one or more columns in a table based on a specified condition. This command will restore the data in the previously disabled columns.




**Command-line synopsis**

```
$ dqo [dqo options...] column enable [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-of=<outputFormat>] [-t=<fullTableName>]

```

**DQOps shell synopsis**

```
dqo> column enable [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-of=<outputFormat>] [-t=<fullTableName>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<p id="column enable-C">`-C`</p><br/><p id="column enable--column">`--column`</p><br/>|Column name| ||
|<p id="column enable-c">`-c`</p><br/><p id="column enable--connection">`--connection`</p><br/>|Connection name| ||
|<p id="column enable-fw">`-fw`</p><br/><p id="column enable--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="column enable-t">`-t`</p><br/><p id="column enable--table">`--table`</p><br/><p id="column enable--full-table-name">`--full-table-name`</p><br/>|Full table name in the form &quot;schema.table&quot;.| ||
|<p id="column enable--headless">`--headless`</p><br/><p id="column enable-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="column enable-h">`-h`</p><br/><p id="column enable--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="column enable-of">`-of`</p><br/><p id="column enable--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






___

## dqo column disable

Disable the column(s)filtered by the given conditions


**Description**


Disable one or more columns in a table based on a specified condition. Disabling a column will prevent it from being queried or updated until it is enabled again.




**Command-line synopsis**

```
$ dqo [dqo options...] column disable [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
                [-of=<outputFormat>] [-t=<fullTableName>]

```

**DQOps shell synopsis**

```
dqo> column disable [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
                [-of=<outputFormat>] [-t=<fullTableName>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<p id="column disable-C">`-C`</p><br/><p id="column disable--column">`--column`</p><br/>|Column name| ||
|<p id="column disable-c">`-c`</p><br/><p id="column disable--connection">`--connection`</p><br/>|Connection name| ||
|<p id="column disable-fw">`-fw`</p><br/><p id="column disable--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="column disable-t">`-t`</p><br/><p id="column disable--table">`--table`</p><br/><p id="column disable--full-table-name">`--full-table-name`</p><br/>|Full table name in the form &quot;schema.table&quot;.| ||
|<p id="column disable--headless">`--headless`</p><br/><p id="column disable-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="column disable-h">`-h`</p><br/><p id="column disable--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="column disable-of">`-of`</p><br/><p id="column disable--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






___

## dqo column rename

Rename the column filtered by the given conditions


**Description**


Rename one or more columns in a table based on a specified condition.




**Command-line synopsis**

```
$ dqo [dqo options...] column rename [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-n=<newColumnName>] [-of=<outputFormat>] [-t=<fullTableName>]

```

**DQOps shell synopsis**

```
dqo> column rename [-h] [-fw] [-hl] [-c=<connectionName>] [-C=<columnName>]
               [-n=<newColumnName>] [-of=<outputFormat>] [-t=<fullTableName>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<p id="column rename-C">`-C`</p><br/><p id="column rename--column">`--column`</p><br/>|Column name| ||
|<p id="column rename-c">`-c`</p><br/><p id="column rename--connection">`--connection`</p><br/>|Connection name| ||
|<p id="column rename-fw">`-fw`</p><br/><p id="column rename--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="column rename-t">`-t`</p><br/><p id="column rename--table">`--table`</p><br/><p id="column rename--full-table-name">`--full-table-name`</p><br/>|Full table name in the form &quot;schema.table&quot;.| ||
|<p id="column rename--headless">`--headless`</p><br/><p id="column rename-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="column rename-h">`-h`</p><br/><p id="column rename--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="column rename-n">`-n`</p><br/><p id="column rename--newColumn">`--newColumn`</p><br/>|New column name| ||
|<p id="column rename-of">`-of`</p><br/><p id="column rename--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|





