# dqo table command
The reference of the **table** command in DQOps. Modify or list tables



___

## dqo table import

Import tables from a specified database


**Description**


Import the tables from the specified database into the application. It allows the user to import the tables from the database into the application for performing various database operations.




**Command-line synopsis**

```
$ dqo [dqo options...] table import [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]
              [-s=<schema>] [-t=<table>]

```

**DQOps shell synopsis**

```
dqo> table import [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>]
              [-s=<schema>] [-t=<table>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="table import-c" class="no-wrap-code">`-c`</div><div id="table import--connection" class="no-wrap-code">`--connection`</div>|Connection name| ||
|<div id="table import-fw" class="no-wrap-code">`-fw`</div><div id="table import--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="table import--headless" class="no-wrap-code">`--headless`</div><div id="table import-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="table import-h" class="no-wrap-code">`-h`</div><div id="table import--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="table import-of" class="no-wrap-code">`-of`</div><div id="table import--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="table import-s" class="no-wrap-code">`-s`</div><div id="table import--schema" class="no-wrap-code">`--schema`</div>|Schema name| ||
|<div id="table import-t" class="no-wrap-code">`-t`</div><div id="table import--table" class="no-wrap-code">`--table`</div>|Table name, without the schema name.| ||






___

## dqo table edit

Edit table that matches a given condition


**Description**


Edit the table or tables that match the filter conditions specified in the options. It allows the user to modify the details of an existing table in the application.




**Command-line synopsis**

```
$ dqo [dqo options...] table edit [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>] [-t=<table>]

```

**DQOps shell synopsis**

```
dqo> table edit [-h] [-fw] [-hl] [-c=<connection>] [-of=<outputFormat>] [-t=<table>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="table edit-c" class="no-wrap-code">`-c`</div><div id="table edit--connection" class="no-wrap-code">`--connection`</div>|Connection Name| ||
|<div id="table edit-fw" class="no-wrap-code">`-fw`</div><div id="table edit--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="table edit-t" class="no-wrap-code">`-t`</div><div id="table edit--table" class="no-wrap-code">`--table`</div><div id="table edit--full-table-name" class="no-wrap-code">`--full-table-name`</div>|Full table name (schema.table), supports wildcard patterns &#x27;sch*.tab*&#x27;| ||
|<div id="table edit--headless" class="no-wrap-code">`--headless`</div><div id="table edit-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="table edit-h" class="no-wrap-code">`-h`</div><div id="table edit--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="table edit-of" class="no-wrap-code">`-of`</div><div id="table edit--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo table add

Add table with specified name


**Description**


Add a new table with the specified name to the database. It allows the user to create a new table in the application for performing various operations.




**Command-line synopsis**

```
$ dqo [dqo options...] table add [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
           [-t=<fullTableName>]

```

**DQOps shell synopsis**

```
dqo> table add [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
           [-t=<fullTableName>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="table add-c" class="no-wrap-code">`-c`</div><div id="table add--connection" class="no-wrap-code">`--connection`</div>|Connection Name| ||
|<div id="table add-fw" class="no-wrap-code">`-fw`</div><div id="table add--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="table add--headless" class="no-wrap-code">`--headless`</div><div id="table add-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="table add-h" class="no-wrap-code">`-h`</div><div id="table add--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="table add-of" class="no-wrap-code">`-of`</div><div id="table add--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="table add-t" class="no-wrap-code">`-t`</div><div id="table add--table" class="no-wrap-code">`--table`</div>|Table name| ||






___

## dqo table remove

Remove tables that match a given condition


**Description**


Remove one or more tables that match a given condition. It allows user to use various filters, such as table names to narrow down the set of tables to remove.




**Command-line synopsis**

```
$ dqo [dqo options...] table remove [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
              [-t=<fullTableName>]

```

**DQOps shell synopsis**

```
dqo> table remove [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
              [-t=<fullTableName>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="table remove-c" class="no-wrap-code">`-c`</div><div id="table remove--connection" class="no-wrap-code">`--connection`</div>|Connection Name| ||
|<div id="table remove-fw" class="no-wrap-code">`-fw`</div><div id="table remove--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="table remove-t" class="no-wrap-code">`-t`</div><div id="table remove--table" class="no-wrap-code">`--table`</div><div id="table remove--full-table-name" class="no-wrap-code">`--full-table-name`</div>|Full table name filter in the form &quot;schema.table&quot;, but also supporting patterns: public.*, *.customers, landing*.customer*.| ||
|<div id="table remove--headless" class="no-wrap-code">`--headless`</div><div id="table remove-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="table remove-h" class="no-wrap-code">`-h`</div><div id="table remove--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="table remove-of" class="no-wrap-code">`-of`</div><div id="table remove--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo table update

Update tables that match a given condition


**Description**


Update the structure of one or more tables that match a given condition. It allows user to use various filters, such as table names  to narrow down the set of tables to update.




**Command-line synopsis**

```
$ dqo [dqo options...] table update [-h] [-fw] [-hl] [-c=<connectionName>] [-n=<newTableName>]
              [-of=<outputFormat>] [-t=<fullTableName>]

```

**DQOps shell synopsis**

```
dqo> table update [-h] [-fw] [-hl] [-c=<connectionName>] [-n=<newTableName>]
              [-of=<outputFormat>] [-t=<fullTableName>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="table update-c" class="no-wrap-code">`-c`</div><div id="table update--connection" class="no-wrap-code">`--connection`</div>|Connection Name| ||
|<div id="table update-fw" class="no-wrap-code">`-fw`</div><div id="table update--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="table update-t" class="no-wrap-code">`-t`</div><div id="table update--table" class="no-wrap-code">`--table`</div><div id="table update--full-table-name" class="no-wrap-code">`--full-table-name`</div>|Full table name in the form &quot;schema.table&quot;.| ||
|<div id="table update--headless" class="no-wrap-code">`--headless`</div><div id="table update-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="table update-h" class="no-wrap-code">`-h`</div><div id="table update--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="table update-n" class="no-wrap-code">`-n`</div><div id="table update--newTable" class="no-wrap-code">`--newTable`</div>|New table name| ||
|<div id="table update-of" class="no-wrap-code">`-of`</div><div id="table update--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|






___

## dqo table list

List tables filtered by the given conditions


**Description**


List all the tables that match a given condition. It allows the user to use various filters, such as table name or schema names to list filtered tables.




**Command-line synopsis**

```
$ dqo [dqo options...] table list [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
            [-t=<tableName>] [-d=<dimensions>]... [-l=<labels>]...

```

**DQOps shell synopsis**

```
dqo> table list [-h] [-fw] [-hl] [-c=<connectionName>] [-of=<outputFormat>]
            [-t=<tableName>] [-d=<dimensions>]... [-l=<labels>]...

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="table list-c" class="no-wrap-code">`-c`</div><div id="table list--connection" class="no-wrap-code">`--connection`</div>|Connection name| ||
|<div id="table list-d" class="no-wrap-code">`-d`</div><div id="table list--dimension" class="no-wrap-code">`--dimension`</div>|Dimension filter| ||
|<div id="table list-fw" class="no-wrap-code">`-fw`</div><div id="table list--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="table list-t" class="no-wrap-code">`-t`</div><div id="table list--table" class="no-wrap-code">`--table`</div><div id="table list--full-table-name" class="no-wrap-code">`--full-table-name`</div>|Full table name filter in the form &quot;schema.table&quot;, but also supporting patterns: public.*, *.customers, landing*.customer*.| ||
|<div id="table list--headless" class="no-wrap-code">`--headless`</div><div id="table list-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="table list-h" class="no-wrap-code">`-h`</div><div id="table list--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="table list-l" class="no-wrap-code">`-l`</div><div id="table list--label" class="no-wrap-code">`--label`</div>|Label filter| ||
|<div id="table list-of" class="no-wrap-code">`-of`</div><div id="table list--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|





