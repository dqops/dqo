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
**DQO Shell synopsis**
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
|`-cat`<br/>`--category`<br/>|Check category name (volume, nulls, numeric, etc.)| ||
|`-ch`<br/>`--check`<br/>|Data quality check name, supports patterns like &#x27;*_id&#x27;| ||
|`-ct`<br/>`--check-type`<br/>|Data quality check type (profiling, monitoring, partitioned)| |profiling<br/>monitoring<br/>partitioned<br/>|
|`-col`<br/>`--column`<br/>|Column name, supports patterns like &#x27;*_id&#x27;| ||
|`-c`<br/>`--connection`<br/>|Connection name, supports patterns like &#x27;conn*&#x27;| ||
|`--daily-partitioning-include-today`<br/>|Analyze also today and later days when running daily partitioned checks. By default, daily partitioned checks will not analyze today and future dates. Setting true will disable filtering the end dates.| ||
|`--daily-partitioning-recent-days`<br/>|The number of recent days to analyze incrementally by daily partitioned data quality checks.| ||
|`-tag`<br/>`--data-stream-level-tag`<br/>|Data stream hierarchy level filter (tag)| ||
|`-d`<br/>`--dummy`<br/>|Runs data quality check in a dummy mode, sensors are not executed on the target database, but the rest of the process is performed| ||
|`-e`<br/>`--enabled`<br/>|Runs only enabled or only disabled sensors, by default only enabled sensors are executed| ||
|`-f`<br/>`--fail-at`<br/>|Lowest data quality issue severity level (warning, error, fatal) that will cause the command to return with an error code. Use &#x27;none&#x27; to return always a success error code.| |warning<br/>error<br/>fatal<br/>none<br/>|
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--from-date`<br/>|Analyze the data since the given date (inclusive). The date should be an ISO 8601 date (yyyy-MM-dd). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the beginning date overrides recent days and recent months.| ||
|`--from-date-time`<br/>|Analyze the data since the given date and time (inclusive). The date and time should be an ISO 8601 local date and time without the time zone (yyyy-MM-dd HH\:mm:ss). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the beginning date and time overrides recent days and recent months.| ||
|`--from-date-time-offset`<br/>|Analyze the data since the given date and time with a time zone offset (inclusive). The date and time should be an ISO 8601 date and time followed by a time zone offset (yyyy-MM-dd HH\:mm:ss). For example: 2023-02-20 14:10:00+02. The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the beginning date and time overrides recent days and recent months.| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-l`<br/>`--label`<br/>|Label filter| ||
|`-m`<br/>`--mode`<br/>|Reporting mode (silent, summary, info, debug)| |silent<br/>summary<br/>info<br/>debug<br/>|
|`--monthly-partitioning-include-current-month`<br/>|Analyze also the current month and later months when running monthly partitioned checks. By default, monthly partitioned checks will not analyze the current month and future months. Setting true will disable filtering the end dates.| ||
|`--monthly-partitioning-recent-months`<br/>|The number of recent months to analyze incrementally by monthly partitioned data quality checks.| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-s`<br/>`--sensor`<br/>|Data quality sensor name (sensor definition or sensor name), supports patterns like &#x27;table/validity/*&#x27;| ||
|`-t`<br/>`--table`<br/>|Full table name (schema.table), supports wildcard patterns &#x27;sch*.tab*&#x27;| ||
|`-ts`<br/>`--time-scale`<br/>|Time scale for monitoring and partitioned checks (daily, monthly, etc.)| |daily<br/>monthly<br/>|
|`--to-date`<br/>|Analyze the data until the given date (exclusive, the given date and the following dates are not analyzed). The date should be an ISO 8601 date (YYYY-MM-DD). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the end date overrides the parameters to disable analyzing today or the current month.| ||
|`--to-date-time`<br/>|Analyze the data until the given date and time (exclusive). The date should be an ISO 8601 date (yyyy-MM-dd). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the end date and time overrides the parameters to disable analyzing today or the current month.| ||
|`--to-date-time-offset`<br/>|Analyze the data until the given date and time with a time zone offset (exclusive). The date and time should be an ISO 8601 date and time followed by a time zone offset (yyyy-MM-dd HH\:mm:ss). For example: 2023-02-20 14:10:00+02. The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the end date and time overrides the parameters to disable analyzing today or the current month.| ||




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
**DQO Shell synopsis**
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
|`-cat`<br/>`--category`<br/>|Check category name (standard, nulls, numeric, etc.)| ||
|`-ch`<br/>`--check`<br/>|Data quality check name, supports patterns like &#x27;*_id&#x27;| ||
|`-ct`<br/>`--check-type`<br/>|Data quality check type (profiling, monitoring, partitioned)| |profiling<br/>monitoring<br/>partitioned<br/>|
|`-col`<br/>`--column`<br/>|Column name, supports patterns like &#x27;*_id&#x27;| ||
|`-c`<br/>`--connection`<br/>|Connection name, supports patterns like &#x27;conn*&#x27;| ||
|`-dt`<br/>`--data-type`<br/>|Datatype of columns on which to enable checks.| ||
|`-E`<br/>`--error-rule`<br/>|Error level rule options. Usage: -E&lt;rule_name&gt;&#x3D;&lt;rule_value&gt;, --error-rule&#x3D;&lt;rule_name&gt;&#x3D;&lt;rule_value&gt;| ||
|`-F`<br/>`--fatal-rule`<br/>|Fatal level rule options. Usage: -F&lt;rule_name&gt;&#x3D;&lt;rule_value&gt;, --fatal-rule&#x3D;&lt;rule_name&gt;&#x3D;&lt;rule_value&gt;| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-n`<br/>`--nullable`<br/>|Enable check only on nullable columns (false for explicitly non-nullable columns).| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-o`<br/>`--override`<br/>|Override existing configuration of selected checks.| ||
|`-sn`<br/>`--sensor`<br/>|Data quality sensor name (sensor definition or sensor name), supports patterns like &#x27;table/validity/*&#x27;| ||
|`-S`<br/>`--sensor-param`<br/>|Configuration parameters for the sensor. Usage: -S&lt;param_name&gt;&#x3D;&lt;param_value&gt;, --sensor-param&#x3D;&lt;param_name&gt;&#x3D;&lt;param_value&gt;| ||
|`-t`<br/>`--table`<br/>|Full table name (schema.table), supports patterns like &#x27;sch*.tab*&#x27;| ||
|`-ts`<br/>`--time-scale`<br/>|Time scale for monitoring and partitioned checks (daily, monthly, etc.)| |daily<br/>monthly<br/>|
|`-W`<br/>`--warning-rule`<br/>|Warning level rule options. Usage: -W&lt;rule_name&gt;&#x3D;&lt;rule_value&gt;, --warning-rule&#x3D;&lt;rule_name&gt;&#x3D;&lt;rule_value&gt;| ||




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
**DQO Shell synopsis**
```
dqo> check disable [-hn] [-fw] [-hl] [-c=<connection>] [-cat=<checkCategory>]
               [-ch=<check>] [-col=<column>] [-ct=<checkType>]
               [-dt=<datatypeFilter>] [-of=<outputFormat>] [-s=<sensor>]
               [-t=<table>] [-ts=<timeScale>]

```

**Options**  
  
| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|`-cat`<br/>`--category`<br/>|Check category name (standard, nulls, numeric, etc.)| ||
|`-ch`<br/>`--check`<br/>|Data quality check name, supports patterns like &#x27;*_id&#x27;| ||
|`-ct`<br/>`--check-type`<br/>|Data quality check type (profiling, monitoring, partitioned)| |profiling<br/>monitoring<br/>partitioned<br/>|
|`-col`<br/>`--column`<br/>|Column name, supports patterns like &#x27;*_id&#x27;| ||
|`-c`<br/>`--connection`<br/>|Connection name, supports patterns like &#x27;conn*&#x27;| ||
|`-dt`<br/>`--data-type`<br/>|Datatype of columns on which to disable checks.| ||
|`-fw`<br/>`--file-write`<br/>|Write command response to a file| ||
|`--headless`<br/>`-hl`<br/>|Starts DQO in a headless mode. When DQO runs in a headless mode and the application cannot start because the DQO Cloud API key is missing or the DQO user home folder is not configured, DQO will stop silently instead of asking the user to approve the setup of the DQO user home folder structure and/or log into DQO Cloud.| ||
|`-h`<br/>`--help`<br/>|Show the help for the command and parameters| ||
|`-n`<br/>`--nullable`<br/>|Disable check only on nullable columns (false for explicitly non-nullable columns).| ||
|`-of`<br/>`--output-format`<br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|`-s`<br/>`--sensor`<br/>|Data quality sensor name (sensor definition or sensor name), supports patterns like &#x27;table/validity/*&#x27;| ||
|`-t`<br/>`--table`<br/>|Full table name (schema.table), supports patterns like &#x27;sch*.tab*&#x27;| ||
|`-ts`<br/>`--time-scale`<br/>|Time scale for monitoring and partitioned checks (daily, monthly, etc.)| |daily<br/>monthly<br/>|



