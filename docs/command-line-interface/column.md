# dqo column command
The reference of the **column** command in DQOps. Modify and list columns



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
|<div id="column add-C" class="no-wrap-code">`-C`</div><div id="column add--column" class="no-wrap-code">`--column`</div>|Column name| ||
|<div id="column add-c" class="no-wrap-code">`-c`</div><div id="column add--connection" class="no-wrap-code">`--connection`</div>|Connection name| ||
|<div id="column add-d" class="no-wrap-code">`-d`</div><div id="column add--dataType" class="no-wrap-code">`--dataType`</div>|Data type| ||
|<div id="column add-fw" class="no-wrap-code">`-fw`</div><div id="column add--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="column add-t" class="no-wrap-code">`-t`</div><div id="column add--table" class="no-wrap-code">`--table`</div><div id="column add--full-table-name" class="no-wrap-code">`--full-table-name`</div>|Full table name in the form &quot;schema.table&quot;.| ||
|<div id="column add--headless" class="no-wrap-code">`--headless`</div><div id="column add-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="column add-h" class="no-wrap-code">`-h`</div><div id="column add--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="column add-of" class="no-wrap-code">`-of`</div><div id="column add--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="column add-e" class="no-wrap-code">`-e`</div><div id="column add--sql-expression" class="no-wrap-code">`--sql-expression`</div>|SQL expression for a calculated column| ||






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
|<div id="column remove-C" class="no-wrap-code">`-C`</div><div id="column remove--column" class="no-wrap-code">`--column`</div>|Column name, supports patterns: c_*, *_id, prefix*suffix.| ||
|<div id="column remove-c" class="no-wrap-code">`-c`</div><div id="column remove--connection" class="no-wrap-code">`--connection`</div>|Connection name| ||
|<div id="column remove-fw" class="no-wrap-code">`-fw`</div><div id="column remove--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="column remove-t" class="no-wrap-code">`-t`</div><div id="column remove--table" class="no-wrap-code">`--table`</div><div id="column remove--full-table-name" class="no-wrap-code">`--full-table-name`</div>|Full table name filter in the form &quot;schema.table&quot;, but also supporting patterns: public.*, *.customers, landing*.customer*.| ||
|<div id="column remove--headless" class="no-wrap-code">`--headless`</div><div id="column remove-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="column remove-h" class="no-wrap-code">`-h`</div><div id="column remove--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="column remove-of" class="no-wrap-code">`-of`</div><div id="column remove--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






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
|<div id="column update-C" class="no-wrap-code">`-C`</div><div id="column update--column" class="no-wrap-code">`--column`</div>|Column name| ||
|<div id="column update-c" class="no-wrap-code">`-c`</div><div id="column update--connection" class="no-wrap-code">`--connection`</div>|Connection name| ||
|<div id="column update-d" class="no-wrap-code">`-d`</div><div id="column update--dataType" class="no-wrap-code">`--dataType`</div>|Data type| ||
|<div id="column update-fw" class="no-wrap-code">`-fw`</div><div id="column update--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="column update-t" class="no-wrap-code">`-t`</div><div id="column update--table" class="no-wrap-code">`--table`</div><div id="column update--full-table-name" class="no-wrap-code">`--full-table-name`</div>|Full table name in the form &quot;schema.table&quot;.| ||
|<div id="column update--headless" class="no-wrap-code">`--headless`</div><div id="column update-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="column update-h" class="no-wrap-code">`-h`</div><div id="column update--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="column update-of" class="no-wrap-code">`-of`</div><div id="column update--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="column update-e" class="no-wrap-code">`-e`</div><div id="column update--sql-expression" class="no-wrap-code">`--sql-expression`</div>|SQL expression for a calculated column| ||






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
|<div id="column list-C" class="no-wrap-code">`-C`</div><div id="column list--column" class="no-wrap-code">`--column`</div>|Connection name filter| ||
|<div id="column list-c" class="no-wrap-code">`-c`</div><div id="column list--connection" class="no-wrap-code">`--connection`</div>|Connection name filter| ||
|<div id="column list-fw" class="no-wrap-code">`-fw`</div><div id="column list--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="column list-t" class="no-wrap-code">`-t`</div><div id="column list--table" class="no-wrap-code">`--table`</div><div id="column list--full-table-name" class="no-wrap-code">`--full-table-name`</div>|Full table name filter in the form &quot;schema.table&quot;, but also supporting patterns: public.*, *.customers, landing*.customer*.| ||
|<div id="column list--headless" class="no-wrap-code">`--headless`</div><div id="column list-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="column list-h" class="no-wrap-code">`-h`</div><div id="column list--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="column list-l" class="no-wrap-code">`-l`</div><div id="column list--label" class="no-wrap-code">`--label`</div>|Label filter| ||
|<div id="column list-of" class="no-wrap-code">`-of`</div><div id="column list--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="column list-tg" class="no-wrap-code">`-tg`</div><div id="column list--tags" class="no-wrap-code">`--tags`</div>|Data grouping static tag filter| ||






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
|<div id="column enable-C" class="no-wrap-code">`-C`</div><div id="column enable--column" class="no-wrap-code">`--column`</div>|Column name| ||
|<div id="column enable-c" class="no-wrap-code">`-c`</div><div id="column enable--connection" class="no-wrap-code">`--connection`</div>|Connection name| ||
|<div id="column enable-fw" class="no-wrap-code">`-fw`</div><div id="column enable--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="column enable-t" class="no-wrap-code">`-t`</div><div id="column enable--table" class="no-wrap-code">`--table`</div><div id="column enable--full-table-name" class="no-wrap-code">`--full-table-name`</div>|Full table name in the form &quot;schema.table&quot;.| ||
|<div id="column enable--headless" class="no-wrap-code">`--headless`</div><div id="column enable-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="column enable-h" class="no-wrap-code">`-h`</div><div id="column enable--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="column enable-of" class="no-wrap-code">`-of`</div><div id="column enable--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






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
|<div id="column disable-C" class="no-wrap-code">`-C`</div><div id="column disable--column" class="no-wrap-code">`--column`</div>|Column name| ||
|<div id="column disable-c" class="no-wrap-code">`-c`</div><div id="column disable--connection" class="no-wrap-code">`--connection`</div>|Connection name| ||
|<div id="column disable-fw" class="no-wrap-code">`-fw`</div><div id="column disable--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="column disable-t" class="no-wrap-code">`-t`</div><div id="column disable--table" class="no-wrap-code">`--table`</div><div id="column disable--full-table-name" class="no-wrap-code">`--full-table-name`</div>|Full table name in the form &quot;schema.table&quot;.| ||
|<div id="column disable--headless" class="no-wrap-code">`--headless`</div><div id="column disable-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="column disable-h" class="no-wrap-code">`-h`</div><div id="column disable--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="column disable-of" class="no-wrap-code">`-of`</div><div id="column disable--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






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
|<div id="column rename-C" class="no-wrap-code">`-C`</div><div id="column rename--column" class="no-wrap-code">`--column`</div>|Column name| ||
|<div id="column rename-c" class="no-wrap-code">`-c`</div><div id="column rename--connection" class="no-wrap-code">`--connection`</div>|Connection name| ||
|<div id="column rename-fw" class="no-wrap-code">`-fw`</div><div id="column rename--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="column rename-t" class="no-wrap-code">`-t`</div><div id="column rename--table" class="no-wrap-code">`--table`</div><div id="column rename--full-table-name" class="no-wrap-code">`--full-table-name`</div>|Full table name in the form &quot;schema.table&quot;.| ||
|<div id="column rename--headless" class="no-wrap-code">`--headless`</div><div id="column rename-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="column rename-h" class="no-wrap-code">`-h`</div><div id="column rename--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="column rename-n" class="no-wrap-code">`-n`</div><div id="column rename--newColumn" class="no-wrap-code">`--newColumn`</div>|New column name| ||
|<div id="column rename-of" class="no-wrap-code">`-of`</div><div id="column rename--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|





