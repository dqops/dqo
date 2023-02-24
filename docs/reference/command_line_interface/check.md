# check

___
### **dqo check run**

**Description**

Run data quality checks matching specified filters


**Summary (Shell)**
```
dqo.ai> check run [-deh] [-fw] [-hl] [-c=<connection>] [-cat=<checkCategory>]
           [-ch=<check>] [-col=<column>] [-ct=<checkType>] [-f=<failAt>]
           [-m=<mode>] [-of=<outputFormat>] [-s=<sensor>] [-t=<table>]
           [-ts=<timeScale>] [-l=<labels>]... [-tag=<tags>]...

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Is it required? | Accepted values |
|-----------------------------------------------|-------------|-----------------|-----------------|
|`-cat`<br/>`--category`<br/>|Check category name (standard, nulls, numeric, etc.)| ||
|`-ch`<br/>`--check`<br/>|Data quality check name, supports patterns like &#x27;*_id&#x27;| ||
|`-ct`<br/>`--check-type`<br/>|Data quality check type (adhoc, checkpoint, partitioned)| |ADHOC<br/>CHECKPOINT<br/>PARTITIONED<br/>|
|`-col`<br/>`--column`<br/>|Column name, supports patterns like &#x27;*_id&#x27;| ||
|`-c`<br/>`--connection`<br/>|Connection name, supports patterns like &#x27;conn*&#x27;| ||
|`-tag`<br/>`--data-stream-level-tag`<br/>|Data stream hierarchy level filter (tag)| ||
|`-d`<br/>`--dummy`<br/>|Runs data quality check in a dummy mode, sensors are not executed on the target database, but the rest of the process is performed| ||
|`-e`<br/>`--enabled`<br/>|Runs only enabled or only disabled sensors, by default only enabled sensors are executed| ||
|`-f`<br/>`--fail-at`<br/>|Lowest data quality issue severity level (warning, error, fatal) that will cause the command to return with an error code. Use &#x27;none&#x27; to return always a success error code.| |warning<br/>error<br/>fatal<br/>none<br/>|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`-hl`<br/>`--headless`<br/>|Run the command in an headless (no user input allowed) mode| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-l`<br/>`--label`<br/>|Label filter| ||
|`-m`<br/>`--mode`<br/>|Reporting mode (silent, summary, info, debug)| |silent<br/>summary<br/>info<br/>debug<br/>|
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-s`<br/>`--sensor`<br/>|Data quality sensor name (sensor definition or sensor name), supports patterns like &#x27;table/validity/*&#x27;| ||
|`-t`<br/>`--table`<br/>|Full table name (schema.table), supports patterns like &#x27;sch*.tab*&#x27;| ||
|`-ts`<br/>`--time-scale`<br/>|Time scale for checkpoint and partitioned checks (daily, monthly, etc.)| |daily<br/>monthly<br/>|



