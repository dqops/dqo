---
title: dqo collect command-line command
---
# dqo collect command-line command
The reference of the **collect** command in DQOps. Commands related to collecting statistics and samples



___

## dqo collect errorsamples

Run data quality checks that match a given condition and collects error samples


**Description**


Run data quality checks on your dataset that match a given condition and capture their error samples. The command output is a table with the results that provides insight into the list of invalid values.




**Command-line synopsis**

```
$ dqo [dqo options...] collect errorsamples [-deh] [--daily-partitioning-include-today] [-fe] [-fw]
                      [-hl] [--monthly-partitioning-include-current-month]
                      [-c=<connection>] [-cat=<checkCategory>] [-ch=<check>]
                      [-col=<column>] [-ct=<checkType>]
                      [--daily-partitioning-recent-days=<dailyPartitioningRecent
                      Days>] [--from-date=<fromDate>]
                      [--from-date-time=<fromDateTime>]
                      [--from-date-time-offset=<fromDateTimeOffset>]
                      [-m=<mode>]
                      [--monthly-partitioning-recent-months=<monthlyPartitioning
                      RecentMonths>] [-of=<outputFormat>] [-s=<sensor>]
                      [-sc=<scope>] [-t=<table>] [--to-date=<toDate>]
                      [--to-date-time=<toDateTime>]
                      [--to-date-time-offset=<toDateTimeOffset>]
                      [-ts=<timeScale>] [-l=<labels>]... [-tag=<tags>]...

```

**DQOps shell synopsis**

```
dqo> collect errorsamples [-deh] [--daily-partitioning-include-today] [-fe] [-fw]
                      [-hl] [--monthly-partitioning-include-current-month]
                      [-c=<connection>] [-cat=<checkCategory>] [-ch=<check>]
                      [-col=<column>] [-ct=<checkType>]
                      [--daily-partitioning-recent-days=<dailyPartitioningRecent
                      Days>] [--from-date=<fromDate>]
                      [--from-date-time=<fromDateTime>]
                      [--from-date-time-offset=<fromDateTimeOffset>]
                      [-m=<mode>]
                      [--monthly-partitioning-recent-months=<monthlyPartitioning
                      RecentMonths>] [-of=<outputFormat>] [-s=<sensor>]
                      [-sc=<scope>] [-t=<table>] [--to-date=<toDate>]
                      [--to-date-time=<toDateTime>]
                      [--to-date-time-offset=<toDateTimeOffset>]
                      [-ts=<timeScale>] [-l=<labels>]... [-tag=<tags>]...

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<div id="collect errorsamples-cat" class="no-wrap-code">`-cat`</div><div id="collect errorsamples--category" class="no-wrap-code">`--category`</div>|Check category name (volume, nulls, numeric, etc.)| ||
|<div id="collect errorsamples-ch" class="no-wrap-code">`-ch`</div><div id="collect errorsamples--check" class="no-wrap-code">`--check`</div>|Data quality check name, supports patterns like '*_id'| ||
|<div id="collect errorsamples-ct" class="no-wrap-code">`-ct`</div><div id="collect errorsamples--check-type" class="no-wrap-code">`--check-type`</div>|Data quality check type (profiling, monitoring, partitioned)| |*profiling*<br/>*monitoring*<br/>*partitioned*<br/>|
|<div id="collect errorsamples-col" class="no-wrap-code">`-col`</div><div id="collect errorsamples--column" class="no-wrap-code">`--column`</div>|Column name, supports patterns like '*_id'| ||
|<div id="collect errorsamples-c" class="no-wrap-code">`-c`</div><div id="collect errorsamples--connection" class="no-wrap-code">`--connection`</div>|Connection name, supports patterns like 'conn*'| ||
|<div id="collect errorsamples--daily-partitioning-include-today" class="no-wrap-code">`--daily-partitioning-include-today`</div>|Analyze also today and later days when running daily partitioned checks. By default, daily partitioned checks will not analyze today and future dates. Setting true will disable filtering the end dates.| ||
|<div id="collect errorsamples--daily-partitioning-recent-days" class="no-wrap-code">`--daily-partitioning-recent-days`</div>|The number of recent days to analyze incrementally by daily partitioned data quality checks.| ||
|<div id="collect errorsamples-tag" class="no-wrap-code">`-tag`</div><div id="collect errorsamples--data-grouping-level-tag" class="no-wrap-code">`--data-grouping-level-tag`</div>|Data grouping hierarchy level filter (tag)| ||
|<div id="collect errorsamples-d" class="no-wrap-code">`-d`</div><div id="collect errorsamples--dummy" class="no-wrap-code">`--dummy`</div>|Runs data quality check in a dummy mode, sensors are not executed on the target database, but the rest of the process is performed| ||
|<div id="collect errorsamples-e" class="no-wrap-code">`-e`</div><div id="collect errorsamples--enabled" class="no-wrap-code">`--enabled`</div>|Runs only enabled or only disabled sensors, by default only enabled sensors are executed| ||
|<div id="collect errorsamples-fe" class="no-wrap-code">`-fe`</div><div id="collect errorsamples--fail-on-execution-errors" class="no-wrap-code">`--fail-on-execution-errors`</div>|Returns a command status code 4 (when called from the command line) if any execution errors were raised during the execution, the default value is true.| ||
|<div id="collect errorsamples-fw" class="no-wrap-code">`-fw`</div><div id="collect errorsamples--file-write" class="no-wrap-code">`--file-write`</div>|Write command response to a file| ||
|<div id="collect errorsamples--from-date" class="no-wrap-code">`--from-date`</div>|Analyze the data since the given date (inclusive). The date should be an ISO 8601 date (yyyy-MM-dd). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the beginning date overrides recent days and recent months.| ||
|<div id="collect errorsamples--from-date-time" class="no-wrap-code">`--from-date-time`</div>|Analyze the data since the given date and time (inclusive). The date and time should be an ISO 8601 local date and time without the time zone (yyyy-MM-dd HH\:mm:ss). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the beginning date and time overrides recent days and recent months.| ||
|<div id="collect errorsamples--from-date-time-offset" class="no-wrap-code">`--from-date-time-offset`</div>|Analyze the data since the given date and time with a time zone offset (inclusive). The date and time should be an ISO 8601 date and time followed by a time zone offset (yyyy-MM-dd HH\:mm:ss). For example: 2023-02-20 14:10:00+02. The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the beginning date and time overrides recent days and recent months.| ||
|<div id="collect errorsamples-t" class="no-wrap-code">`-t`</div><div id="collect errorsamples--table" class="no-wrap-code">`--table`</div><div id="collect errorsamples--full-table-name" class="no-wrap-code">`--full-table-name`</div>|Full table name (schema.table), supports wildcard patterns 'sch*.tab*'| ||
|<div id="collect errorsamples--headless" class="no-wrap-code">`--headless`</div><div id="collect errorsamples-hl" class="no-wrap-code">`-hl`</div>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<div id="collect errorsamples-h" class="no-wrap-code">`-h`</div><div id="collect errorsamples--help" class="no-wrap-code">`--help`</div>|Show the help for the command and parameters| ||
|<div id="collect errorsamples-l" class="no-wrap-code">`-l`</div><div id="collect errorsamples--label" class="no-wrap-code">`--label`</div>|Label filter| ||
|<div id="collect errorsamples-m" class="no-wrap-code">`-m`</div><div id="collect errorsamples--mode" class="no-wrap-code">`--mode`</div>|Reporting mode (silent)| |*silent*<br/>|
|<div id="collect errorsamples--monthly-partitioning-include-current-month" class="no-wrap-code">`--monthly-partitioning-include-current-month`</div>|Analyze also the current month and later months when running monthly partitioned checks. By default, monthly partitioned checks will not analyze the current month and future months. Setting true will disable filtering the end dates.| ||
|<div id="collect errorsamples--monthly-partitioning-recent-months" class="no-wrap-code">`--monthly-partitioning-recent-months`</div>|The number of recent months to analyze incrementally by monthly partitioned data quality checks.| ||
|<div id="collect errorsamples-of" class="no-wrap-code">`-of`</div><div id="collect errorsamples--output-format" class="no-wrap-code">`--output-format`</div>|Output format for tabular responses| |*TABLE*<br/>*CSV*<br/>*JSON*<br/>|
|<div id="collect errorsamples-sc" class="no-wrap-code">`-sc`</div><div id="collect errorsamples--scope" class="no-wrap-code">`--scope`</div>|Error sampling scope that is used for tables with data grouping. Error samples can be collected for the whole table, or for each data grouping. By default, collects error samples for a whole table.| |*table*<br/>*data_group*<br/>|
|<div id="collect errorsamples-s" class="no-wrap-code">`-s`</div><div id="collect errorsamples--sensor" class="no-wrap-code">`--sensor`</div>|Data quality sensor name (sensor definition or sensor name), supports patterns like 'table/validity/*'| ||
|<div id="collect errorsamples-ts" class="no-wrap-code">`-ts`</div><div id="collect errorsamples--time-scale" class="no-wrap-code">`--time-scale`</div>|Time scale for monitoring and partitioned checks (daily, monthly, etc.)| |*daily*<br/>*monthly*<br/>|
|<div id="collect errorsamples--to-date" class="no-wrap-code">`--to-date`</div>|Analyze the data until the given date (exclusive, the given date and the following dates are not analyzed). The date should be an ISO 8601 date (YYYY-MM-DD). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the end date overrides the parameters to disable analyzing today or the current month.| ||
|<div id="collect errorsamples--to-date-time" class="no-wrap-code">`--to-date-time`</div>|Analyze the data until the given date and time (exclusive). The date should be an ISO 8601 date (yyyy-MM-dd). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the end date and time overrides the parameters to disable analyzing today or the current month.| ||
|<div id="collect errorsamples--to-date-time-offset" class="no-wrap-code">`--to-date-time-offset`</div>|Analyze the data until the given date and time with a time zone offset (exclusive). The date and time should be an ISO 8601 date and time followed by a time zone offset (yyyy-MM-dd HH\:mm:ss). For example: 2023-02-20 14:10:00+02. The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the end date and time overrides the parameters to disable analyzing today or the current month.| ||





