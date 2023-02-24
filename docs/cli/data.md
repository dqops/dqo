# data

___
### **dqo data clean**

**Description**

Delete stored data data matching specified filters


**Summary (Shell)**
```
dqo.ai> data clean [-h] [-er] [-fw] [-hl] [-rr] [-sr] [-st] -b=<begin> -c=<connection>
            [-cat=<checkCategory>] [-ch=<check>] [-col=<column>]
            [-ct=<checkType>] [-ds=<dataStream>] -e=<end> [-of=<outputFormat>]
            [-qd=<qualityDimension>] [-s=<sensor>] [-sc=<statisticsCollector>]
            [-stc=<statisticsCategory>] [-stt=<statisticsTarget>] -t=<table>
            [-tg=<timeGradient>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`-b`<br/>`--begin`<br/>|Beginning of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD|Yes||
|`-cat`<br/>`--category`<br/>|Check category name (standard, nulls, numeric, etc.)| ||
|`-ch`<br/>`--check`<br/>|Data quality check name| ||
|`-ct`<br/>`--check-type`<br/>|Data quality check type (adhoc, checkpoint, partitioned)| |ADHOC<br/>CHECKPOINT<br/>PARTITIONED<br/>|
|`-col`<br/>`--column`<br/>|Column name| ||
|`-c`<br/>`--connection`<br/>|Connection name|Yes||
|`-ds`<br/>`--data-stream`<br/>|Data stream hierarchy level filter (tag)| ||
|`-e`<br/>`--end`<br/>|End of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD|Yes||
|`-er`<br/>`--errors`<br/>|Delete the errors| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-qd`<br/>`--quality-dimension`<br/>|Data quality dimension| ||
|`-rr`<br/>`--rule-results`<br/>|Delete the rule results| ||
|`-s`<br/>`--sensor`<br/>|Data quality sensor name (sensor definition or sensor name)| ||
|`-sr`<br/>`--sensor-readouts`<br/>|Delete the sensor readouts| ||
|`-st`<br/>`--statistics`<br/>|Delete the statistics| ||
|`-stc`<br/>`--statistics-category`<br/>|Statistics category name (standard, nulls, numeric, etc.)| ||
|`-sc`<br/>`--statistics-collector`<br/>|Data quality statistics collector name| ||
|`-stt`<br/>`--statistics-target`<br/>|Data quality statistics target (table, column)| |table<br/>column<br/>|
|`-t`<br/>`--table`<br/>|Full table name (schema.table)|Yes||
|`-tg`<br/>`--time-gradient`<br/>|Time gradient of the sensor| ||



