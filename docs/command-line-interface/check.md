# check

___
### **dqo check run**

Run data quality checks that match a given condition

**Description**

Run data quality checks on your dataset that match a given condition. The command output is a table with the results that provides insight into the data quality.


**Command-line synopsis**
```
$ dqo [dqo options...] check run [-deh] [--daily-partitioning-include-today] [-fw] [-hl]
           [--monthly-partitioning-include-current-month] [-c=<connection>]
           [-cat=<checkCategory>] [-ch=<check>] [-col=<column>]
           [-ct=<checkType>]
           [--daily-partitioning-recent-days=<dailyPartitioningRecentDays>]
           [-f=<failAt>] [--from-date=<fromDate>]
           [--from-date-time=<fromDateTime>]
           [--from-date-time-offset=<fromDateTimeOffset>] [-m=<mode>]
           [--monthly-partitioning-recent-months=<monthlyPartitioningRecentMonth
           s>] [-of=<outputFormat>] [-s=<sensor>] [-t=<table>]
           [--to-date=<toDate>] [--to-date-time=<toDateTime>]
           [--to-date-time-offset=<toDateTimeOffset>] [-ts=<timeScale>]
           [-l=<labels>]... [-tag=<tags>]...

```
**DQOps Shell synopsis**
```
dqo> check run [-deh] [--daily-partitioning-include-today] [-fw] [-hl]
           [--monthly-partitioning-include-current-month] [-c=<connection>]
           [-cat=<checkCategory>] [-ch=<check>] [-col=<column>]
           [-ct=<checkType>]
           [--daily-partitioning-recent-days=<dailyPartitioningRecentDays>]
           [-f=<failAt>] [--from-date=<fromDate>]
           [--from-date-time=<fromDateTime>]
           [--from-date-time-offset=<fromDateTimeOffset>] [-m=<mode>]
           [--monthly-partitioning-recent-months=<monthlyPartitioningRecentMonth
           s>] [-of=<outputFormat>] [-s=<sensor>] [-t=<table>]
           [--to-date=<toDate>] [--to-date-time=<toDateTime>]
           [--to-date-time-offset=<toDateTimeOffset>] [-ts=<timeScale>]
           [-l=<labels>]... [-tag=<tags>]...

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<p id="check run-cat">`-cat`</p><br/><p id="check run--category">`--category`</p><br/>|Check category name (volume, nulls, numeric, etc.)| ||
|<p id="check run-ch">`-ch`</p><br/><p id="check run--check">`--check`</p><br/>|Data quality check name, supports patterns like &#x27;*_id&#x27;| ||
|<p id="check run-ct">`-ct`</p><br/><p id="check run--check-type">`--check-type`</p><br/>|Data quality check type (profiling, monitoring, partitioned)| |profiling<br/>monitoring<br/>partitioned<br/>|
|<p id="check run-col">`-col`</p><br/><p id="check run--column">`--column`</p><br/>|Column name, supports patterns like &#x27;*_id&#x27;| ||
|<p id="check run-c">`-c`</p><br/><p id="check run--connection">`--connection`</p><br/>|Connection name, supports patterns like &#x27;conn*&#x27;| ||
|<p id="check run--daily-partitioning-include-today">`--daily-partitioning-include-today`</p><br/>|Analyze also today and later days when running daily partitioned checks. By default, daily partitioned checks will not analyze today and future dates. Setting true will disable filtering the end dates.| ||
|<p id="check run--daily-partitioning-recent-days">`--daily-partitioning-recent-days`</p><br/>|The number of recent days to analyze incrementally by daily partitioned data quality checks.| ||
|<p id="check run-tag">`-tag`</p><br/><p id="check run--data-grouping-level-tag">`--data-grouping-level-tag`</p><br/>|Data grouping hierarchy level filter (tag)| ||
|<p id="check run-d">`-d`</p><br/><p id="check run--dummy">`--dummy`</p><br/>|Runs data quality check in a dummy mode, sensors are not executed on the target database, but the rest of the process is performed| ||
|<p id="check run-e">`-e`</p><br/><p id="check run--enabled">`--enabled`</p><br/>|Runs only enabled or only disabled sensors, by default only enabled sensors are executed| ||
|<p id="check run-f">`-f`</p><br/><p id="check run--fail-at">`--fail-at`</p><br/>|Lowest data quality issue severity level (warning, error, fatal) that will cause the command to return with an error code. Use &#x27;none&#x27; to return always a success error code.| |warning<br/>error<br/>fatal<br/>none<br/>|
|<p id="check run-fw">`-fw`</p><br/><p id="check run--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="check run--from-date">`--from-date`</p><br/>|Analyze the data since the given date (inclusive). The date should be an ISO 8601 date (yyyy-MM-dd). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the beginning date overrides recent days and recent months.| ||
|<p id="check run--from-date-time">`--from-date-time`</p><br/>|Analyze the data since the given date and time (inclusive). The date and time should be an ISO 8601 local date and time without the time zone (yyyy-MM-dd HH\:mm:ss). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the beginning date and time overrides recent days and recent months.| ||
|<p id="check run--from-date-time-offset">`--from-date-time-offset`</p><br/>|Analyze the data since the given date and time with a time zone offset (inclusive). The date and time should be an ISO 8601 date and time followed by a time zone offset (yyyy-MM-dd HH\:mm:ss). For example: 2023-02-20 14:10:00+02. The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the beginning date and time overrides recent days and recent months.| ||
|<p id="check run-t">`-t`</p><br/><p id="check run--table">`--table`</p><br/><p id="check run--full-table-name">`--full-table-name`</p><br/>|Full table name (schema.table), supports wildcard patterns &#x27;sch*.tab*&#x27;| ||
|<p id="check run--headless">`--headless`</p><br/><p id="check run-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="check run-h">`-h`</p><br/><p id="check run--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="check run-l">`-l`</p><br/><p id="check run--label">`--label`</p><br/>|Label filter| ||
|<p id="check run-m">`-m`</p><br/><p id="check run--mode">`--mode`</p><br/>|Reporting mode (silent, summary, info, debug)| |silent<br/>summary<br/>info<br/>debug<br/>|
|<p id="check run--monthly-partitioning-include-current-month">`--monthly-partitioning-include-current-month`</p><br/>|Analyze also the current month and later months when running monthly partitioned checks. By default, monthly partitioned checks will not analyze the current month and future months. Setting true will disable filtering the end dates.| ||
|<p id="check run--monthly-partitioning-recent-months">`--monthly-partitioning-recent-months`</p><br/>|The number of recent months to analyze incrementally by monthly partitioned data quality checks.| ||
|<p id="check run-of">`-of`</p><br/><p id="check run--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="check run-s">`-s`</p><br/><p id="check run--sensor">`--sensor`</p><br/>|Data quality sensor name (sensor definition or sensor name), supports patterns like &#x27;table/validity/*&#x27;| ||
|<p id="check run-ts">`-ts`</p><br/><p id="check run--time-scale">`--time-scale`</p><br/>|Time scale for monitoring and partitioned checks (daily, monthly, etc.)| |daily<br/>monthly<br/>|
|<p id="check run--to-date">`--to-date`</p><br/>|Analyze the data until the given date (exclusive, the given date and the following dates are not analyzed). The date should be an ISO 8601 date (YYYY-MM-DD). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the end date overrides the parameters to disable analyzing today or the current month.| ||
|<p id="check run--to-date-time">`--to-date-time`</p><br/>|Analyze the data until the given date and time (exclusive). The date should be an ISO 8601 date (yyyy-MM-dd). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the end date and time overrides the parameters to disable analyzing today or the current month.| ||
|<p id="check run--to-date-time-offset">`--to-date-time-offset`</p><br/>|Analyze the data until the given date and time with a time zone offset (exclusive). The date and time should be an ISO 8601 date and time followed by a time zone offset (yyyy-MM-dd HH\:mm:ss). For example: 2023-02-20 14:10:00+02. The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the end date and time overrides the parameters to disable analyzing today or the current month.| ||




___
### **dqo check enable**

**Description**

Enable data quality checks matching specified filters


**Command-line synopsis**
```
$ dqo [dqo options...] check enable [-hno] [-fw] [-hl] [-c=<connection>] [-cat=<checkCategory>]
              [-ch=<check>] [-col=<column>] [-ct=<checkType>]
              [-dt=<datatypeFilter>] [-of=<outputFormat>] [-sn=<sensor>]
              [-t=<table>] [-ts=<timeScale>] [-E=<String=String>]...
              [-F=<String=String>]... [-S=<String=String>]...
              [-W=<String=String>]...

```
**DQOps Shell synopsis**
```
dqo> check enable [-hno] [-fw] [-hl] [-c=<connection>] [-cat=<checkCategory>]
              [-ch=<check>] [-col=<column>] [-ct=<checkType>]
              [-dt=<datatypeFilter>] [-of=<outputFormat>] [-sn=<sensor>]
              [-t=<table>] [-ts=<timeScale>] [-E=<String=String>]...
              [-F=<String=String>]... [-S=<String=String>]...
              [-W=<String=String>]...

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<p id="check enable-cat">`-cat`</p><br/><p id="check enable--category">`--category`</p><br/>|Check category name (standard, nulls, numeric, etc.)| ||
|<p id="check enable-ch">`-ch`</p><br/><p id="check enable--check">`--check`</p><br/>|Data quality check name, supports patterns like &#x27;*_id&#x27;| ||
|<p id="check enable-ct">`-ct`</p><br/><p id="check enable--check-type">`--check-type`</p><br/>|Data quality check type (profiling, monitoring, partitioned)| |profiling<br/>monitoring<br/>partitioned<br/>|
|<p id="check enable-col">`-col`</p><br/><p id="check enable--column">`--column`</p><br/>|Column name, supports patterns like &#x27;*_id&#x27;| ||
|<p id="check enable-c">`-c`</p><br/><p id="check enable--connection">`--connection`</p><br/>|Connection name, supports patterns like &#x27;conn*&#x27;| ||
|<p id="check enable-dt">`-dt`</p><br/><p id="check enable--data-type">`--data-type`</p><br/>|Datatype of columns on which to enable checks.| ||
|<p id="check enable-E">`-E`</p><br/><p id="check enable--error-rule">`--error-rule`</p><br/>|Error level rule options. Usage: -E&lt;rule_name&gt;&#x3D;&lt;rule_value&gt;, --error-rule&#x3D;&lt;rule_name&gt;&#x3D;&lt;rule_value&gt;| ||
|<p id="check enable-F">`-F`</p><br/><p id="check enable--fatal-rule">`--fatal-rule`</p><br/>|Fatal level rule options. Usage: -F&lt;rule_name&gt;&#x3D;&lt;rule_value&gt;, --fatal-rule&#x3D;&lt;rule_name&gt;&#x3D;&lt;rule_value&gt;| ||
|<p id="check enable-fw">`-fw`</p><br/><p id="check enable--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="check enable-t">`-t`</p><br/><p id="check enable--table">`--table`</p><br/><p id="check enable--full-table-name">`--full-table-name`</p><br/>|Full table name (schema.table), supports patterns like &#x27;sch*.tab*&#x27;| ||
|<p id="check enable--headless">`--headless`</p><br/><p id="check enable-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="check enable-h">`-h`</p><br/><p id="check enable--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="check enable-n">`-n`</p><br/><p id="check enable--nullable">`--nullable`</p><br/>|Enable check only on nullable columns (false for explicitly non-nullable columns).| ||
|<p id="check enable-of">`-of`</p><br/><p id="check enable--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="check enable-o">`-o`</p><br/><p id="check enable--override">`--override`</p><br/>|Override existing configuration of selected checks.| ||
|<p id="check enable-sn">`-sn`</p><br/><p id="check enable--sensor">`--sensor`</p><br/>|Data quality sensor name (sensor definition or sensor name), supports patterns like &#x27;table/validity/*&#x27;| ||
|<p id="check enable-S">`-S`</p><br/><p id="check enable--sensor-param">`--sensor-param`</p><br/>|Configuration parameters for the sensor. Usage: -S&lt;param_name&gt;&#x3D;&lt;param_value&gt;, --sensor-param&#x3D;&lt;param_name&gt;&#x3D;&lt;param_value&gt;| ||
|<p id="check enable-ts">`-ts`</p><br/><p id="check enable--time-scale">`--time-scale`</p><br/>|Time scale for monitoring and partitioned checks (daily, monthly, etc.)| |daily<br/>monthly<br/>|
|<p id="check enable-W">`-W`</p><br/><p id="check enable--warning-rule">`--warning-rule`</p><br/>|Warning level rule options. Usage: -W&lt;rule_name&gt;&#x3D;&lt;rule_value&gt;, --warning-rule&#x3D;&lt;rule_name&gt;&#x3D;&lt;rule_value&gt;| ||




___
### **dqo check disable**

**Description**

Disable data quality checks matching specified filters


**Command-line synopsis**
```
$ dqo [dqo options...] check disable [-hn] [-fw] [-hl] [-c=<connection>] [-cat=<checkCategory>]
               [-ch=<check>] [-col=<column>] [-ct=<checkType>]
               [-dt=<datatypeFilter>] [-of=<outputFormat>] [-s=<sensor>]
               [-t=<table>] [-ts=<timeScale>]

```
**DQOps Shell synopsis**
```
dqo> check disable [-hn] [-fw] [-hl] [-c=<connection>] [-cat=<checkCategory>]
               [-ch=<check>] [-col=<column>] [-ct=<checkType>]
               [-dt=<datatypeFilter>] [-of=<outputFormat>] [-s=<sensor>]
               [-t=<table>] [-ts=<timeScale>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<p id="check disable-cat">`-cat`</p><br/><p id="check disable--category">`--category`</p><br/>|Check category name (standard, nulls, numeric, etc.)| ||
|<p id="check disable-ch">`-ch`</p><br/><p id="check disable--check">`--check`</p><br/>|Data quality check name, supports patterns like &#x27;*_id&#x27;| ||
|<p id="check disable-ct">`-ct`</p><br/><p id="check disable--check-type">`--check-type`</p><br/>|Data quality check type (profiling, monitoring, partitioned)| |profiling<br/>monitoring<br/>partitioned<br/>|
|<p id="check disable-col">`-col`</p><br/><p id="check disable--column">`--column`</p><br/>|Column name, supports patterns like &#x27;*_id&#x27;| ||
|<p id="check disable-c">`-c`</p><br/><p id="check disable--connection">`--connection`</p><br/>|Connection name, supports patterns like &#x27;conn*&#x27;| ||
|<p id="check disable-dt">`-dt`</p><br/><p id="check disable--data-type">`--data-type`</p><br/>|Datatype of columns on which to disable checks.| ||
|<p id="check disable-fw">`-fw`</p><br/><p id="check disable--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="check disable-t">`-t`</p><br/><p id="check disable--table">`--table`</p><br/><p id="check disable--full-table-name">`--full-table-name`</p><br/>|Full table name (schema.table), supports patterns like &#x27;sch*.tab*&#x27;| ||
|<p id="check disable--headless">`--headless`</p><br/><p id="check disable-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="check disable-h">`-h`</p><br/><p id="check disable--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="check disable-n">`-n`</p><br/><p id="check disable--nullable">`--nullable`</p><br/>|Disable check only on nullable columns (false for explicitly non-nullable columns).| ||
|<p id="check disable-of">`-of`</p><br/><p id="check disable--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="check disable-s">`-s`</p><br/><p id="check disable--sensor">`--sensor`</p><br/>|Data quality sensor name (sensor definition or sensor name), supports patterns like &#x27;table/validity/*&#x27;| ||
|<p id="check disable-ts">`-ts`</p><br/><p id="check disable--time-scale">`--time-scale`</p><br/>|Time scale for monitoring and partitioned checks (daily, monthly, etc.)| |daily<br/>monthly<br/>|



