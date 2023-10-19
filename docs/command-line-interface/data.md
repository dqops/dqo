# data

___
### **dqo data delete**

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
**DQOps Shell synopsis**
```
dqo> data delete [-h] [-cr] [-er] [-fw] [-hl] [-sr] [-st] [-b=<begin>]
             [-c=<connection>] [-cat=<checkCategory>] [-ch=<check>]
             [-col=<column>] [-ct=<checkType>] [-ds=<dataGroupTag>] [-e=<end>]
             [-of=<outputFormat>] [-qd=<qualityDimension>] [-s=<sensor>]
             [-sc=<statisticsCollector>] [-stc=<statisticsCategory>]
             [-stt=<statisticsTarget>] [-t=<table>] [-tc=<tableComparison>]
             [-tg=<timeGradient>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-b`<br/>`--begin`<br/>|Beginning of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD| ||
|`-cat`<br/>`--category`<br/>|Check category name (volume, nulls, numeric, etc.)| ||
|`-ch`<br/>`--check`<br/>|Data quality check name| ||
|`-cr`<br/>`--check-results`<br/>|Delete the check results| ||
|`-ct`<br/>`--check-type`<br/>|Data quality check type (profiling, monitoring, partitioned)| |profiling<br/>monitoring<br/>partitioned<br/>|
|`-col`<br/>`--column`<br/>|Column name| ||
|`-c`<br/>`--connection`<br/>|Connection name| ||
|`-ds`<br/>`--data-group-tag`<br/>|Data group dimension level filter, that filters by tags.| ||
|`-e`<br/>`--end`<br/>|End of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD| ||
|`-er`<br/>`--errors`<br/>|Delete the execution errors| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-qd`<br/>`--quality-dimension`<br/>|Data quality dimension| ||
|`-s`<br/>`--sensor`<br/>|Data quality sensor name (sensor definition or sensor name)| ||
|`-sr`<br/>`--sensor-readouts`<br/>|Delete the sensor readouts| ||
|`-st`<br/>`--statistics`<br/>|Delete the statistics| ||
|`-stc`<br/>`--statistics-category`<br/>|Statistics category name (volume, nulls, numeric, etc.)| ||
|`-sc`<br/>`--statistics-collector`<br/>|Data quality statistics collector name| ||
|`-stt`<br/>`--statistics-target`<br/>|Data quality statistics target (table, column)| |table<br/>column<br/>|
|`-t`<br/>`--table`<br/>|Full table name (schema.table), supports wildcard patterns &#x27;sch*.tab*&#x27;| ||
|`-tc`<br/>`--table-comparison`<br/>|Table comparison name.| ||
|`-tg`<br/>`--time-gradient`<br/>|Time gradient of the sensor| ||




___
### **dqo data repair**

Verify integrity of parquet files used to store data and removes corrupted files

**Description**

Verify integrity of parquet files used to store data and removes corrupted files. Be careful when using this command, as it permanently deletes the selected data and cannot be undone.


**Command-line synopsis**
```
$ dqo [dqo options...] data repair [-h] [-cr] [-er] [-fw] [-hl] [-sr] [-st] -c=<connection>
             [-of=<outputFormat>] [-t=<table>]

```
**DQOps Shell synopsis**
```
dqo> data repair [-h] [-cr] [-er] [-fw] [-hl] [-sr] [-st] -c=<connection>
             [-of=<outputFormat>] [-t=<table>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-cr`<br/>`--check-results`<br/>|Repair the check results| ||
|`-c`<br/>`--connection`<br/>|Connection name|:material-check-bold:||
|`-er`<br/>`--errors`<br/>|Repair the execution errors| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-sr`<br/>`--sensor-readouts`<br/>|Repair the sensor readouts| ||
|`-st`<br/>`--statistics`<br/>|Repair the statistics| ||
|`-t`<br/>`--table`<br/>|Full table name (schema.table), supports wildcard patterns &#x27;sch*.tab*&#x27;| ||



