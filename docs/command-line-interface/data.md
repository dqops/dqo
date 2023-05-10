# data

___
### **dqo data clean**

Delete stored data that matches a given condition

**Description**

Delete stored data that matches certain conditions.It is important to use caution when using this command, as it will permanently delete the selected data and cannot be undone.


**Command-line synopsis**
```
$ dqo [dqo options...] data clean [-h] [-cr] [-er] [-fw] [-hl] [-sr] [-st] -b=<begin> -c=<connection>
            [-cat=<checkCategory>] [-ch=<check>] [-col=<column>]
            [-ct=<checkType>] [-ds=<dataStream>] -e=<end> [-of=<outputFormat>]
            [-qd=<qualityDimension>] [-s=<sensor>] [-sc=<statisticsCollector>]
            [-stc=<statisticsCategory>] [-stt=<statisticsTarget>] -t=<table>
            [-tg=<timeGradient>]

```
**DQO Shell synopsis**
```
dqo.ai> data clean [-h] [-cr] [-er] [-fw] [-hl] [-sr] [-st] -b=<begin> -c=<connection>
            [-cat=<checkCategory>] [-ch=<check>] [-col=<column>]
            [-ct=<checkType>] [-ds=<dataStream>] -e=<end> [-of=<outputFormat>]
            [-qd=<qualityDimension>] [-s=<sensor>] [-sc=<statisticsCollector>]
            [-stc=<statisticsCategory>] [-stt=<statisticsTarget>] -t=<table>
            [-tg=<timeGradient>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-b`<br/>`--begin`<br/>|Beginning of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD|:material-check-bold:||
|`-cat`<br/>`--category`<br/>|Check category name (standard, nulls, numeric, etc.)| ||
|`-ch`<br/>`--check`<br/>|Data quality check name| ||
|`-cr`<br/>`--check-results`<br/>|Delete the check results| ||
|`-ct`<br/>`--check-type`<br/>|Data quality check type (profiling, recurring, partitioned)| |PROFILING<br/>RECURRING<br/>PARTITIONED<br/>|
|`-col`<br/>`--column`<br/>|Column name| ||
|`-c`<br/>`--connection`<br/>|Connection name|:material-check-bold:||
|`-ds`<br/>`--data-stream`<br/>|Data stream hierarchy level filter (tag)| ||
|`-e`<br/>`--end`<br/>|End of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD|:material-check-bold:||
|`-er`<br/>`--errors`<br/>|Delete the errors| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-qd`<br/>`--quality-dimension`<br/>|Data quality dimension| ||
|`-s`<br/>`--sensor`<br/>|Data quality sensor name (sensor definition or sensor name)| ||
|`-sr`<br/>`--sensor-readouts`<br/>|Delete the sensor readouts| ||
|`-st`<br/>`--statistics`<br/>|Delete the statistics| ||
|`-stc`<br/>`--statistics-category`<br/>|Statistics category name (standard, nulls, numeric, etc.)| ||
|`-sc`<br/>`--statistics-collector`<br/>|Data quality statistics collector name| ||
|`-stt`<br/>`--statistics-target`<br/>|Data quality statistics target (table, column)| |table<br/>column<br/>|
|`-t`<br/>`--table`<br/>|Full table name (schema.table), supports wildcard patterns &#x27;sch*.tab*&#x27;|:material-check-bold:||
|`-tg`<br/>`--time-gradient`<br/>|Time gradient of the sensor| ||




___
### **dqo data repair**

Verify integrity of stored data and repair corrupted files

**Description**

Verify integrity of parquet files present in the stored data and delete the corrupted ones. It is important to use caution when using this command, as it will permanently delete the selected data and cannot be undone.


**Command-line synopsis**
```
$ dqo [dqo options...] data repair [-h] [-cr] [-er] [-fw] [-hl] [-sr] [-st] -c=<connection>
             [-of=<outputFormat>] [-t=<table>]

```
**DQO Shell synopsis**
```
dqo.ai> data repair [-h] [-cr] [-er] [-fw] [-hl] [-sr] [-st] -c=<connection>
             [-of=<outputFormat>] [-t=<table>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-cr`<br/>`--check-results`<br/>|Repair the check results| ||
|`-c`<br/>`--connection`<br/>|Connection name|:material-check-bold:||
|`-er`<br/>`--errors`<br/>|Repair the errors| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-sr`<br/>`--sensor-readouts`<br/>|Repair the sensor readouts| ||
|`-st`<br/>`--statistics`<br/>|Repair the statistics| ||
|`-t`<br/>`--table`<br/>|Full table name (schema.table), supports wildcard patterns &#x27;sch*.tab*&#x27;| ||



