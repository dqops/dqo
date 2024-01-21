# dqo data command
The reference of the **data** command in DQOps. Commands related to the data



___

## dqo data delete

Deletes stored data that matches the specified conditions


**Description**


Deletes stored data that matches specified conditions. Be careful when using this command, as it permanently deletes the selected data and cannot be undone.




**Command-line synopsis**

```
$ dqo [dqo options...] data delete [-h] [-cr] [-er] [-fw] [-hl] [-sr] [-st] [-b=<begin>]
             [-c=<connection>] [-cat=<checkCategory>] [-ch=<check>]
             [-col=<column>] [-ct=<checkType>] [-ds=<dataGroupTag>] [-e=<end>]
             [-of=<outputFormat>] [-qd=<qualityDimension>] [-s=<sensor>]
             [-sc=<statisticsCollector>] [-stc=<statisticsCategory>]
             [-stt=<statisticsTarget>] [-t=<table>] [-tc=<tableComparison>]
             [-tg=<timeGradient>]

```

**DQOps shell synopsis**

```
dqo> data delete [-h] [-cr] [-er] [-fw] [-hl] [-sr] [-st] [-b=<begin>]
             [-c=<connection>] [-cat=<checkCategory>] [-ch=<check>]
             [-col=<column>] [-ct=<checkType>] [-ds=<dataGroupTag>] [-e=<end>]
             [-of=<outputFormat>] [-qd=<qualityDimension>] [-s=<sensor>]
             [-sc=<statisticsCollector>] [-stc=<statisticsCategory>]
             [-stt=<statisticsTarget>] [-t=<table>] [-tc=<tableComparison>]
             [-tg=<timeGradient>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="data delete-b" class="no-wrap-code">`-b`</div><div id="data delete--begin" class="no-wrap-code">`--begin`</div>|Beginning of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD| ||
|<div id="data delete-cat" class="no-wrap-code">`-cat`</div><div id="data delete--category" class="no-wrap-code">`--category`</div>|Check category name (volume, nulls, numeric, etc.)| ||
|<div id="data delete-ch" class="no-wrap-code">`-ch`</div><div id="data delete--check" class="no-wrap-code">`--check`</div>|Data quality check name| ||
|<div id="data delete-cr" class="no-wrap-code">`-cr`</div><div id="data delete--check-results" class="no-wrap-code">`--check-results`</div>|Delete the check results| ||
|<div id="data delete-ct" class="no-wrap-code">`-ct`</div><div id="data delete--check-type" class="no-wrap-code">`--check-type`</div>|Data quality check type (profiling, monitoring, partitioned)| |*profiling*<br/>*monitoring*<br/>*partitioned*<br/>|
|<div id="data delete-col" class="no-wrap-code">`-col`</div><div id="data delete--column" class="no-wrap-code">`--column`</div>|Column name| ||
|<div id="data delete-c" class="no-wrap-code">`-c`</div><div id="data delete--connection" class="no-wrap-code">`--connection`</div>|Connection name| ||
|<div id="data delete-ds" class="no-wrap-code">`-ds`</div><div id="data delete--data-group-tag" class="no-wrap-code">`--data-group-tag`</div>|Data group dimension level filter, that filters by tags.| ||
|<div id="data delete-e" class="no-wrap-code">`-e`</div><div id="data delete--end" class="no-wrap-code">`--end`</div>|End of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD| ||
|<div id="data delete-er" class="no-wrap-code">`-er`</div><div id="data delete--errors" class="no-wrap-code">`--errors`</div>|Delete the execution errors| ||
|<div id="data delete-fw" class="no-wrap-code">`-fw`</div><div id="data delete--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="data delete-t" class="no-wrap-code">`-t`</div><div id="data delete--table" class="no-wrap-code">`--table`</div><div id="data delete--full-table-name" class="no-wrap-code">`--full-table-name`</div>|Full table name (schema.table), supports wildcard patterns &#x27;sch*.tab*&#x27;| ||
|<div id="data delete--headless" class="no-wrap-code">`--headless`</div><div id="data delete-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="data delete-h" class="no-wrap-code">`-h`</div><div id="data delete--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="data delete-of" class="no-wrap-code">`-of`</div><div id="data delete--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="data delete-qd" class="no-wrap-code">`-qd`</div><div id="data delete--quality-dimension" class="no-wrap-code">`--quality-dimension`</div>|Data quality dimension| ||
|<div id="data delete-s" class="no-wrap-code">`-s`</div><div id="data delete--sensor" class="no-wrap-code">`--sensor`</div>|Data quality sensor name (sensor definition or sensor name)| ||
|<div id="data delete-sr" class="no-wrap-code">`-sr`</div><div id="data delete--sensor-readouts" class="no-wrap-code">`--sensor-readouts`</div>|Delete the sensor readouts| ||
|<div id="data delete-st" class="no-wrap-code">`-st`</div><div id="data delete--statistics" class="no-wrap-code">`--statistics`</div>|Delete the statistics| ||
|<div id="data delete-stc" class="no-wrap-code">`-stc`</div><div id="data delete--statistics-category" class="no-wrap-code">`--statistics-category`</div>|Statistics category name (volume, nulls, numeric, etc.)| ||
|<div id="data delete-sc" class="no-wrap-code">`-sc`</div><div id="data delete--statistics-collector" class="no-wrap-code">`--statistics-collector`</div>|Data quality statistics collector name| ||
|<div id="data delete-stt" class="no-wrap-code">`-stt`</div><div id="data delete--statistics-target" class="no-wrap-code">`--statistics-target`</div>|Data quality statistics target (table, column)| |*table*<br/>*column*<br/>|
|<div id="data delete-tc" class="no-wrap-code">`-tc`</div><div id="data delete--table-comparison" class="no-wrap-code">`--table-comparison`</div>|Table comparison name.| ||
|<div id="data delete-tg" class="no-wrap-code">`-tg`</div><div id="data delete--time-gradient" class="no-wrap-code">`--time-gradient`</div>|Time gradient of the sensor| ||






___

## dqo data repair

Verify integrity of parquet files used to store data and removes corrupted files


**Description**


Verify integrity of parquet files used to store data and removes corrupted files. Be careful when using this command, as it permanently deletes the selected data and cannot be undone.




**Command-line synopsis**

```
$ dqo [dqo options...] data repair [-h] [-cr] [-er] [-fw] [-hl] [-sr] [-st] -c=<connection>
             [-of=<outputFormat>] [-t=<table>]

```

**DQOps shell synopsis**

```
dqo> data repair [-h] [-cr] [-er] [-fw] [-hl] [-sr] [-st] -c=<connection>
             [-of=<outputFormat>] [-t=<table>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="data repair-cr" class="no-wrap-code">`-cr`</div><div id="data repair--check-results" class="no-wrap-code">`--check-results`</div>|Repair the check results| ||
|<div id="data repair-c" class="no-wrap-code">`-c`</div><div id="data repair--connection" class="no-wrap-code">`--connection`</div>|Connection name|:material-check-bold:||
|<div id="data repair-er" class="no-wrap-code">`-er`</div><div id="data repair--errors" class="no-wrap-code">`--errors`</div>|Repair the execution errors| ||
|<div id="data repair-fw" class="no-wrap-code">`-fw`</div><div id="data repair--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="data repair-t" class="no-wrap-code">`-t`</div><div id="data repair--table" class="no-wrap-code">`--table`</div><div id="data repair--full-table-name" class="no-wrap-code">`--full-table-name`</div>|Full table name (schema.table), supports wildcard patterns &#x27;sch*.tab*&#x27;| ||
|<div id="data repair--headless" class="no-wrap-code">`--headless`</div><div id="data repair-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="data repair-h" class="no-wrap-code">`-h`</div><div id="data repair--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="data repair-of" class="no-wrap-code">`-of`</div><div id="data repair--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="data repair-sr" class="no-wrap-code">`-sr`</div><div id="data repair--sensor-readouts" class="no-wrap-code">`--sensor-readouts`</div>|Repair the sensor readouts| ||
|<div id="data repair-st" class="no-wrap-code">`-st`</div><div id="data repair--statistics" class="no-wrap-code">`--statistics`</div>|Repair the statistics| ||





