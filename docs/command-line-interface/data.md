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
|<p id="data delete-b">`-b`</p><br/><p id="data delete--begin">`--begin`</p><br/>|Beginning of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD| ||
|<p id="data delete-cat">`-cat`</p><br/><p id="data delete--category">`--category`</p><br/>|Check category name (volume, nulls, numeric, etc.)| ||
|<p id="data delete-ch">`-ch`</p><br/><p id="data delete--check">`--check`</p><br/>|Data quality check name| ||
|<p id="data delete-cr">`-cr`</p><br/><p id="data delete--check-results">`--check-results`</p><br/>|Delete the check results| ||
|<p id="data delete-ct">`-ct`</p><br/><p id="data delete--check-type">`--check-type`</p><br/>|Data quality check type (profiling, monitoring, partitioned)| |profiling<br/>monitoring<br/>partitioned<br/>|
|<p id="data delete-col">`-col`</p><br/><p id="data delete--column">`--column`</p><br/>|Column name| ||
|<p id="data delete-c">`-c`</p><br/><p id="data delete--connection">`--connection`</p><br/>|Connection name| ||
|<p id="data delete-ds">`-ds`</p><br/><p id="data delete--data-group-tag">`--data-group-tag`</p><br/>|Data group dimension level filter, that filters by tags.| ||
|<p id="data delete-e">`-e`</p><br/><p id="data delete--end">`--end`</p><br/>|End of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD| ||
|<p id="data delete-er">`-er`</p><br/><p id="data delete--errors">`--errors`</p><br/>|Delete the execution errors| ||
|<p id="data delete-fw">`-fw`</p><br/><p id="data delete--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="data delete-t">`-t`</p><br/><p id="data delete--table">`--table`</p><br/><p id="data delete--full-table-name">`--full-table-name`</p><br/>|Full table name (schema.table), supports wildcard patterns &#x27;sch*.tab*&#x27;| ||
|<p id="data delete--headless">`--headless`</p><br/><p id="data delete-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="data delete-h">`-h`</p><br/><p id="data delete--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="data delete-of">`-of`</p><br/><p id="data delete--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="data delete-qd">`-qd`</p><br/><p id="data delete--quality-dimension">`--quality-dimension`</p><br/>|Data quality dimension| ||
|<p id="data delete-s">`-s`</p><br/><p id="data delete--sensor">`--sensor`</p><br/>|Data quality sensor name (sensor definition or sensor name)| ||
|<p id="data delete-sr">`-sr`</p><br/><p id="data delete--sensor-readouts">`--sensor-readouts`</p><br/>|Delete the sensor readouts| ||
|<p id="data delete-st">`-st`</p><br/><p id="data delete--statistics">`--statistics`</p><br/>|Delete the statistics| ||
|<p id="data delete-stc">`-stc`</p><br/><p id="data delete--statistics-category">`--statistics-category`</p><br/>|Statistics category name (volume, nulls, numeric, etc.)| ||
|<p id="data delete-sc">`-sc`</p><br/><p id="data delete--statistics-collector">`--statistics-collector`</p><br/>|Data quality statistics collector name| ||
|<p id="data delete-stt">`-stt`</p><br/><p id="data delete--statistics-target">`--statistics-target`</p><br/>|Data quality statistics target (table, column)| |table<br/>column<br/>|
|<p id="data delete-tc">`-tc`</p><br/><p id="data delete--table-comparison">`--table-comparison`</p><br/>|Table comparison name.| ||
|<p id="data delete-tg">`-tg`</p><br/><p id="data delete--time-gradient">`--time-gradient`</p><br/>|Time gradient of the sensor| ||






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
|<p id="data repair-cr">`-cr`</p><br/><p id="data repair--check-results">`--check-results`</p><br/>|Repair the check results| ||
|<p id="data repair-c">`-c`</p><br/><p id="data repair--connection">`--connection`</p><br/>|Connection name|:material-check-bold:||
|<p id="data repair-er">`-er`</p><br/><p id="data repair--errors">`--errors`</p><br/>|Repair the execution errors| ||
|<p id="data repair-fw">`-fw`</p><br/><p id="data repair--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="data repair-t">`-t`</p><br/><p id="data repair--table">`--table`</p><br/><p id="data repair--full-table-name">`--full-table-name`</p><br/>|Full table name (schema.table), supports wildcard patterns &#x27;sch*.tab*&#x27;| ||
|<p id="data repair--headless">`--headless`</p><br/><p id="data repair-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="data repair-h">`-h`</p><br/><p id="data repair--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="data repair-of">`-of`</p><br/><p id="data repair--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="data repair-sr">`-sr`</p><br/><p id="data repair--sensor-readouts">`--sensor-readouts`</p><br/>|Repair the sensor readouts| ||
|<p id="data repair-st">`-st`</p><br/><p id="data repair--statistics">`--statistics`</p><br/>|Repair the statistics| ||





