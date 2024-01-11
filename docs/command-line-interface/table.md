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
|<p id="table import-c">`-c`</p><br/><p id="table import--connection">`--connection`</p><br/>|Connection name| ||
|<p id="table import-fw">`-fw`</p><br/><p id="table import--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="table import--headless">`--headless`</p><br/><p id="table import-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="table import-h">`-h`</p><br/><p id="table import--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="table import-of">`-of`</p><br/><p id="table import--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="table import-s">`-s`</p><br/><p id="table import--schema">`--schema`</p><br/>|Schema name| ||
|<p id="table import-t">`-t`</p><br/><p id="table import--table">`--table`</p><br/>|Table name, without the schema name.| ||






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
|<p id="table edit-c">`-c`</p><br/><p id="table edit--connection">`--connection`</p><br/>|Connection Name| ||
|<p id="table edit-fw">`-fw`</p><br/><p id="table edit--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="table edit-t">`-t`</p><br/><p id="table edit--table">`--table`</p><br/><p id="table edit--full-table-name">`--full-table-name`</p><br/>|Full table name (schema.table), supports wildcard patterns &#x27;sch*.tab*&#x27;| ||
|<p id="table edit--headless">`--headless`</p><br/><p id="table edit-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="table edit-h">`-h`</p><br/><p id="table edit--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="table edit-of">`-of`</p><br/><p id="table edit--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="table add-c">`-c`</p><br/><p id="table add--connection">`--connection`</p><br/>|Connection Name| ||
|<p id="table add-fw">`-fw`</p><br/><p id="table add--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="table add--headless">`--headless`</p><br/><p id="table add-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="table add-h">`-h`</p><br/><p id="table add--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="table add-of">`-of`</p><br/><p id="table add--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="table add-t">`-t`</p><br/><p id="table add--table">`--table`</p><br/>|Table name| ||






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
|<p id="table remove-c">`-c`</p><br/><p id="table remove--connection">`--connection`</p><br/>|Connection Name| ||
|<p id="table remove-fw">`-fw`</p><br/><p id="table remove--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="table remove-t">`-t`</p><br/><p id="table remove--table">`--table`</p><br/><p id="table remove--full-table-name">`--full-table-name`</p><br/>|Full table name filter in the form &quot;schema.table&quot;, but also supporting patterns: public.*, *.customers, landing*.customer*.| ||
|<p id="table remove--headless">`--headless`</p><br/><p id="table remove-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="table remove-h">`-h`</p><br/><p id="table remove--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="table remove-of">`-of`</p><br/><p id="table remove--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="table update-c">`-c`</p><br/><p id="table update--connection">`--connection`</p><br/>|Connection Name| ||
|<p id="table update-fw">`-fw`</p><br/><p id="table update--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="table update-t">`-t`</p><br/><p id="table update--table">`--table`</p><br/><p id="table update--full-table-name">`--full-table-name`</p><br/>|Full table name in the form &quot;schema.table&quot;.| ||
|<p id="table update--headless">`--headless`</p><br/><p id="table update-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="table update-h">`-h`</p><br/><p id="table update--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="table update-n">`-n`</p><br/><p id="table update--newTable">`--newTable`</p><br/>|New table name| ||
|<p id="table update-of">`-of`</p><br/><p id="table update--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|






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
|<p id="table list-c">`-c`</p><br/><p id="table list--connection">`--connection`</p><br/>|Connection name| ||
|<p id="table list-d">`-d`</p><br/><p id="table list--dimension">`--dimension`</p><br/>|Dimension filter| ||
|<p id="table list-fw">`-fw`</p><br/><p id="table list--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="table list-t">`-t`</p><br/><p id="table list--table">`--table`</p><br/><p id="table list--full-table-name">`--full-table-name`</p><br/>|Full table name filter in the form &quot;schema.table&quot;, but also supporting patterns: public.*, *.customers, landing*.customer*.| ||
|<p id="table list--headless">`--headless`</p><br/><p id="table list-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="table list-h">`-h`</p><br/><p id="table list--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="table list-l">`-l`</p><br/><p id="table list--label">`--label`</p><br/>|Label filter| ||
|<p id="table list-of">`-of`</p><br/><p id="table list--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|





