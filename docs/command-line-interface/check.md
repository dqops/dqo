# dqo check command
The reference of the **check** command in DQOps. Commands related to checks and rules



___

## dqo check run

Run data quality checks that match a given condition


**Description**


Run data quality checks on your dataset that match a given condition. The command output is a table with the results that provides insight into the data quality.




**Command-line synopsis**

```
$ dqo [dqo options...] check run [-deh] [--daily-partitioning-include-today] [-fe] [-fw] [-hl]
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

**DQOps shell synopsis**

```
dqo> check run [-deh] [--daily-partitioning-include-today] [-fe] [-fw] [-hl]
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



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="check run-cat" class="no-wrap-code">`-cat`</div><div id="check run--category" class="no-wrap-code">`--category`</div>|Check category name (volume, nulls, numeric, etc.)| ||
|<div id="check run-ch" class="no-wrap-code">`-ch`</div><div id="check run--check" class="no-wrap-code">`--check`</div>|Data quality check name, supports patterns like &#x27;*_id&#x27;| ||
|<div id="check run-ct" class="no-wrap-code">`-ct`</div><div id="check run--check-type" class="no-wrap-code">`--check-type`</div>|Data quality check type (profiling, monitoring, partitioned)| |*profiling*<br/>*monitoring*<br/>*partitioned*<br/>|
|<div id="check run-col" class="no-wrap-code">`-col`</div><div id="check run--column" class="no-wrap-code">`--column`</div>|Column name, supports patterns like &#x27;*_id&#x27;| ||
|<div id="check run-c" class="no-wrap-code">`-c`</div><div id="check run--connection" class="no-wrap-code">`--connection`</div>|Connection name, supports patterns like &#x27;conn*&#x27;| ||
|<div id="check run--daily-partitioning-include-today" class="no-wrap-code">`--daily-partitioning-include-today`</div>|Analyze also today and later days when running daily partitioned checks. By default, daily partitioned checks will not analyze today and future dates. Setting true will disable filtering the end dates.| ||
|<div id="check run--daily-partitioning-recent-days" class="no-wrap-code">`--daily-partitioning-recent-days`</div>|The number of recent days to analyze incrementally by daily partitioned data quality checks.| ||
|<div id="check run-tag" class="no-wrap-code">`-tag`</div><div id="check run--data-grouping-level-tag" class="no-wrap-code">`--data-grouping-level-tag`</div>|Data grouping hierarchy level filter (tag)| ||
|<div id="check run-d" class="no-wrap-code">`-d`</div><div id="check run--dummy" class="no-wrap-code">`--dummy`</div>|Runs data quality check in a dummy mode, sensors are not executed on the target database, but the rest of the process is performed| ||
|<div id="check run-e" class="no-wrap-code">`-e`</div><div id="check run--enabled" class="no-wrap-code">`--enabled`</div>|Runs only enabled or only disabled sensors, by default only enabled sensors are executed| ||
|<div id="check run-f" class="no-wrap-code">`-f`</div><div id="check run--fail-at" class="no-wrap-code">`--fail-at`</div>|Lowest data quality issue severity level (warning, error, fatal) that will cause the command to return with an error code. Use &#x27;none&#x27; to return always a success error code.| |*warning*<br/>*error*<br/>*fatal*<br/>*none*<br/>|
|<div id="check run-fe" class="no-wrap-code">`-fe`</div><div id="check run--fail-on-execution-errors" class="no-wrap-code">`--fail-on-execution-errors`</div>|Returns a command status code 4 (when called from the command line) if any execution errors were raised during the execution, the default value is true.| ||
|<div id="check run-fw" class="no-wrap-code">`-fw`</div><div id="check run--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="check run--from-date" class="no-wrap-code">`--from-date`</div>|Analyze the data since the given date (inclusive). The date should be an ISO 8601 date (yyyy-MM-dd). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the beginning date overrides recent days and recent months.| ||
|<div id="check run--from-date-time" class="no-wrap-code">`--from-date-time`</div>|Analyze the data since the given date and time (inclusive). The date and time should be an ISO 8601 local date and time without the time zone (yyyy-MM-dd HH\:mm:ss). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the beginning date and time overrides recent days and recent months.| ||
|<div id="check run--from-date-time-offset" class="no-wrap-code">`--from-date-time-offset`</div>|Analyze the data since the given date and time with a time zone offset (inclusive). The date and time should be an ISO 8601 date and time followed by a time zone offset (yyyy-MM-dd HH\:mm:ss). For example: 2023-02-20 14:10:00+02. The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the beginning date and time overrides recent days and recent months.| ||
|<div id="check run-t" class="no-wrap-code">`-t`</div><div id="check run--table" class="no-wrap-code">`--table`</div><div id="check run--full-table-name" class="no-wrap-code">`--full-table-name`</div>|Full table name (schema.table), supports wildcard patterns &#x27;sch*.tab*&#x27;| ||
|<div id="check run--headless" class="no-wrap-code">`--headless`</div><div id="check run-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="check run-h" class="no-wrap-code">`-h`</div><div id="check run--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="check run-l" class="no-wrap-code">`-l`</div><div id="check run--label" class="no-wrap-code">`--label`</div>|Label filter| ||
|<div id="check run-m" class="no-wrap-code">`-m`</div><div id="check run--mode" class="no-wrap-code">`--mode`</div>|Reporting mode (silent, summary, info, debug)| |*silent*<br/>*summary*<br/>*info*<br/>*debug*<br/>|
|<div id="check run--monthly-partitioning-include-current-month" class="no-wrap-code">`--monthly-partitioning-include-current-month`</div>|Analyze also the current month and later months when running monthly partitioned checks. By default, monthly partitioned checks will not analyze the current month and future months. Setting true will disable filtering the end dates.| ||
|<div id="check run--monthly-partitioning-recent-months" class="no-wrap-code">`--monthly-partitioning-recent-months`</div>|The number of recent months to analyze incrementally by monthly partitioned data quality checks.| ||
|<div id="check run-of" class="no-wrap-code">`-of`</div><div id="check run--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="check run-s" class="no-wrap-code">`-s`</div><div id="check run--sensor" class="no-wrap-code">`--sensor`</div>|Data quality sensor name (sensor definition or sensor name), supports patterns like &#x27;table/validity/*&#x27;| ||
|<div id="check run-ts" class="no-wrap-code">`-ts`</div><div id="check run--time-scale" class="no-wrap-code">`--time-scale`</div>|Time scale for monitoring and partitioned checks (daily, monthly, etc.)| |*daily*<br/>*monthly*<br/>|
|<div id="check run--to-date" class="no-wrap-code">`--to-date`</div>|Analyze the data until the given date (exclusive, the given date and the following dates are not analyzed). The date should be an ISO 8601 date (YYYY-MM-DD). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the end date overrides the parameters to disable analyzing today or the current month.| ||
|<div id="check run--to-date-time" class="no-wrap-code">`--to-date-time`</div>|Analyze the data until the given date and time (exclusive). The date should be an ISO 8601 date (yyyy-MM-dd). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the end date and time overrides the parameters to disable analyzing today or the current month.| ||
|<div id="check run--to-date-time-offset" class="no-wrap-code">`--to-date-time-offset`</div>|Analyze the data until the given date and time with a time zone offset (exclusive). The date and time should be an ISO 8601 date and time followed by a time zone offset (yyyy-MM-dd HH\:mm:ss). For example: 2023-02-20 14:10:00+02. The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the end date and time overrides the parameters to disable analyzing today or the current month.| ||






___

## dqo check activate


**Description**


Activates data quality checks matching specified filters




**Command-line synopsis**

```
$ dqo [dqo options...] check activate [-efhnow] [-de] [-df] [-dw] [-fw] [-hl] [-c=<connection>]
                [-cat=<checkCategory>] [-ch=<check>] [-col=<column>]
                [-ct=<checkType>] [-dt=<datatypeFilter>] [-of=<outputFormat>]
                [-sn=<sensor>] [-t=<table>] [-ts=<timeScale>]
                [-E=<String=String>]... [-F=<String=String>]...
                [-S=<String=String>]... [-W=<String=String>]...

```

**DQOps shell synopsis**

```
dqo> check activate [-efhnow] [-de] [-df] [-dw] [-fw] [-hl] [-c=<connection>]
                [-cat=<checkCategory>] [-ch=<check>] [-col=<column>]
                [-ct=<checkType>] [-dt=<datatypeFilter>] [-of=<outputFormat>]
                [-sn=<sensor>] [-t=<table>] [-ts=<timeScale>]
                [-E=<String=String>]... [-F=<String=String>]...
                [-S=<String=String>]... [-W=<String=String>]...

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="check activate-cat" class="no-wrap-code">`-cat`</div><div id="check activate--category" class="no-wrap-code">`--category`</div>|Check category name (standard, nulls, numeric, etc.)| ||
|<div id="check activate-ch" class="no-wrap-code">`-ch`</div><div id="check activate--check" class="no-wrap-code">`--check`</div>|Data quality check name, supports patterns like &#x27;*_id&#x27;| ||
|<div id="check activate-ct" class="no-wrap-code">`-ct`</div><div id="check activate--check-type" class="no-wrap-code">`--check-type`</div>|Data quality check type (profiling, monitoring, partitioned)| |*profiling*<br/>*monitoring*<br/>*partitioned*<br/>|
|<div id="check activate-col" class="no-wrap-code">`-col`</div><div id="check activate--column" class="no-wrap-code">`--column`</div>|Column name, supports patterns like &#x27;*_id&#x27;| ||
|<div id="check activate-c" class="no-wrap-code">`-c`</div><div id="check activate--connection" class="no-wrap-code">`--connection`</div>|Connection name, supports patterns like &#x27;conn*&#x27;| ||
|<div id="check activate-dt" class="no-wrap-code">`-dt`</div><div id="check activate--data-type" class="no-wrap-code">`--data-type`</div>|Datatype of columns on which to enable checks.| ||
|<div id="check activate-de" class="no-wrap-code">`-de`</div><div id="check activate--disable-error" class="no-wrap-code">`--disable-error`</div>|Disables an error severity rule. Use this option to update already activated data quality checks, together with the --override option.| ||
|<div id="check activate-df" class="no-wrap-code">`-df`</div><div id="check activate--disable-fatal" class="no-wrap-code">`--disable-fatal`</div>|Disables a fatal severity rule. Use this option to update already activated data quality checks, together with the --override option.| ||
|<div id="check activate-dw" class="no-wrap-code">`-dw`</div><div id="check activate--disable-warning" class="no-wrap-code">`--disable-warning`</div>|Disables a warning severity rule. Use this option to update already activated data quality checks, together with the --override option.| ||
|<div id="check activate-e" class="no-wrap-code">`-e`</div><div id="check activate--enable-error" class="no-wrap-code">`--enable-error`</div>|Enables an error severity rule. An error severity rule is also enabled when any warning rule parameters are passed using the -Eparam_nam&#x3D;value parameters| ||
|<div id="check activate-f" class="no-wrap-code">`-f`</div><div id="check activate--enable-fatal" class="no-wrap-code">`--enable-fatal`</div>|Enables a fatal severity rule. A fatal severity rule is also enabled when any fatal rule parameters are passed using the -Fparam_nam&#x3D;value parameters| ||
|<div id="check activate-w" class="no-wrap-code">`-w`</div><div id="check activate--enable-warning" class="no-wrap-code">`--enable-warning`</div>|Enables a warning severity rule. A warning severity rule is also enabled when any warning rule parameters are passed using the -Wparam_nam&#x3D;value parameters| ||
|<div id="check activate-fw" class="no-wrap-code">`-fw`</div><div id="check activate--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="check activate-t" class="no-wrap-code">`-t`</div><div id="check activate--table" class="no-wrap-code">`--table`</div><div id="check activate--full-table-name" class="no-wrap-code">`--full-table-name`</div>|Full table name (schema.table), supports patterns like &#x27;sch*.tab*&#x27;| ||
|<div id="check activate--headless" class="no-wrap-code">`--headless`</div><div id="check activate-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="check activate-h" class="no-wrap-code">`-h`</div><div id="check activate--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="check activate-n" class="no-wrap-code">`-n`</div><div id="check activate--nullable" class="no-wrap-code">`--nullable`</div>|Enable check only on nullable columns (false for explicitly non-nullable columns).| ||
|<div id="check activate-of" class="no-wrap-code">`-of`</div><div id="check activate--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="check activate-o" class="no-wrap-code">`-o`</div><div id="check activate--override" class="no-wrap-code">`--override`</div>|Override existing configuration of selected checks.| ||
|<div id="check activate-sn" class="no-wrap-code">`-sn`</div><div id="check activate--sensor" class="no-wrap-code">`--sensor`</div>|Data quality sensor name (sensor definition or sensor name), supports patterns like &#x27;table/validity/*&#x27;| ||
|<div id="check activate-ts" class="no-wrap-code">`-ts`</div><div id="check activate--time-scale" class="no-wrap-code">`--time-scale`</div>|Time scale for monitoring and partitioned checks (daily, monthly, etc.)| |*daily*<br/>*monthly*<br/>|
|<div id="check activate-E" class="no-wrap-code">`-E`</div>|Error level rule options. Usage: -E&lt;rule_name&gt;&#x3D;&lt;rule_value&gt;, --error-rule&#x3D;&lt;rule_name&gt;&#x3D;&lt;rule_value&gt;| ||
|<div id="check activate-F" class="no-wrap-code">`-F`</div>|Fatal level rule options. Usage: -F&lt;rule_name&gt;&#x3D;&lt;rule_value&gt;, --fatal-rule&#x3D;&lt;rule_name&gt;&#x3D;&lt;rule_value&gt;| ||
|<div id="check activate-S" class="no-wrap-code">`-S`</div>|Configuration parameters for the sensor. Usage: -S&lt;param_name&gt;&#x3D;&lt;param_value&gt;, --sensor-param&#x3D;&lt;param_name&gt;&#x3D;&lt;param_value&gt;| ||
|<div id="check activate-W" class="no-wrap-code">`-W`</div>|Warning level rule options. Usage: -W&lt;rule_name&gt;&#x3D;&lt;rule_value&gt;, --warning-rule&#x3D;&lt;rule_name&gt;&#x3D;&lt;rule_value&gt;| ||






___

## dqo check deactivate


**Description**


Deactivates data quality checks matching specified filters




**Command-line synopsis**

```
$ dqo [dqo options...] check deactivate [-hn] [-fw] [-hl] [-c=<connection>] [-cat=<checkCategory>]
                  [-ch=<check>] [-col=<column>] [-ct=<checkType>]
                  [-dt=<datatypeFilter>] [-of=<outputFormat>] [-s=<sensor>]
                  [-t=<table>] [-ts=<timeScale>]

```

**DQOps shell synopsis**

```
dqo> check deactivate [-hn] [-fw] [-hl] [-c=<connection>] [-cat=<checkCategory>]
                  [-ch=<check>] [-col=<column>] [-ct=<checkType>]
                  [-dt=<datatypeFilter>] [-of=<outputFormat>] [-s=<sensor>]
                  [-t=<table>] [-ts=<timeScale>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="check deactivate-cat" class="no-wrap-code">`-cat`</div><div id="check deactivate--category" class="no-wrap-code">`--category`</div>|Check category name (standard, nulls, numeric, etc.)| ||
|<div id="check deactivate-ch" class="no-wrap-code">`-ch`</div><div id="check deactivate--check" class="no-wrap-code">`--check`</div>|Data quality check name, supports patterns like &#x27;*_id&#x27;| ||
|<div id="check deactivate-ct" class="no-wrap-code">`-ct`</div><div id="check deactivate--check-type" class="no-wrap-code">`--check-type`</div>|Data quality check type (profiling, monitoring, partitioned)| |*profiling*<br/>*monitoring*<br/>*partitioned*<br/>|
|<div id="check deactivate-col" class="no-wrap-code">`-col`</div><div id="check deactivate--column" class="no-wrap-code">`--column`</div>|Column name, supports patterns like &#x27;*_id&#x27;| ||
|<div id="check deactivate-c" class="no-wrap-code">`-c`</div><div id="check deactivate--connection" class="no-wrap-code">`--connection`</div>|Connection name, supports patterns like &#x27;conn*&#x27;| ||
|<div id="check deactivate-dt" class="no-wrap-code">`-dt`</div><div id="check deactivate--data-type" class="no-wrap-code">`--data-type`</div>|Datatype of columns on which to disable checks.| ||
|<div id="check deactivate-fw" class="no-wrap-code">`-fw`</div><div id="check deactivate--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="check deactivate-t" class="no-wrap-code">`-t`</div><div id="check deactivate--table" class="no-wrap-code">`--table`</div><div id="check deactivate--full-table-name" class="no-wrap-code">`--full-table-name`</div>|Full table name (schema.table), supports patterns like &#x27;sch*.tab*&#x27;| ||
|<div id="check deactivate--headless" class="no-wrap-code">`--headless`</div><div id="check deactivate-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="check deactivate-h" class="no-wrap-code">`-h`</div><div id="check deactivate--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="check deactivate-n" class="no-wrap-code">`-n`</div><div id="check deactivate--nullable" class="no-wrap-code">`--nullable`</div>|Disable check only on nullable columns (false for explicitly non-nullable columns).| ||
|<div id="check deactivate-of" class="no-wrap-code">`-of`</div><div id="check deactivate--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="check deactivate-s" class="no-wrap-code">`-s`</div><div id="check deactivate--sensor" class="no-wrap-code">`--sensor`</div>|Data quality sensor name (sensor definition or sensor name), supports patterns like &#x27;table/validity/*&#x27;| ||
|<div id="check deactivate-ts" class="no-wrap-code">`-ts`</div><div id="check deactivate--time-scale" class="no-wrap-code">`--time-scale`</div>|Time scale for monitoring and partitioned checks (daily, monthly, etc.)| |*daily*<br/>*monthly*<br/>|





