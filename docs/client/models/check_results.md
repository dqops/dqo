---
title: DQOps REST API check_results models reference
---
# DQOps REST API check_results models reference
The references of all objects used by [check_results](../operations/check_results.md) REST API operations are listed below.


## CheckResultsListModel
Check detailed results. Returned in the context of a single data group, with a supplied list of other data groups.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_hash`</span>|Check hash|*long*|
|<span class="no-wrap-code">`check_category`</span>|Check category name|*string*|
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*|
|<span class="no-wrap-code">`check_display_name`</span>|Check display name|*string*|
|<span class="no-wrap-code">[`check_type`](./common.md#checktype)</span>|Check type|*[CheckType](./common.md#checktype)*|
|<span class="no-wrap-code">`data_groups`</span>|Data groups list|*List[string]*|
|<span class="no-wrap-code">`data_group`</span>|Selected data group|*string*|
|<span class="no-wrap-code">`check_result_entries`</span>|Single check results|*List[[CheckResultEntryModel](./incidents.md#checkresultentrymodel)]*|


___

## TableCurrentDataQualityStatusModel
The table validity status. It is a summary of the results of the most recently executed data quality checks on the table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|The connection name in DQOps.|*string*|
|<span class="no-wrap-code">`schema_name`</span>|The schema name.|*string*|
|<span class="no-wrap-code">`table_name`</span>|The table name.|*string*|
|<span class="no-wrap-code">[`current_severity`](./common.md#ruleseveritylevel)</span>|The most recent data quality issue severity for this table. When the table is monitored using data grouping, it is the highest issue severity of all recently analyzed data groups. For partitioned checks, it is the highest severity of all results for all partitions (time periods) in the analyzed time range.|*[RuleSeverityLevel](./common.md#ruleseveritylevel)*|
|<span class="no-wrap-code">[`highest_historical_severity`](./common.md#ruleseveritylevel)</span>|The highest severity of previous executions of this data quality issue in the analyzed time range. It can be different from the *current_severity* if the data quality issue was solved and the most recently data quality issue did not detect it anymore. For partitioned checks, this field returns the same value as the *current_severity*, because data quality issues in older partitions are still valid.|*[RuleSeverityLevel](./common.md#ruleseveritylevel)*|
|<span class="no-wrap-code">`executed_checks`</span>|The total number of most recent checks that were executed on the table. Table comparison checks that are comparing groups of data are counted as the number of compared data groups.|*integer*|
|<span class="no-wrap-code">`valid_results`</span>|The number of most recent valid data quality checks that passed without raising any issues.|*integer*|
|<span class="no-wrap-code">`warnings`</span>|The number of most recent data quality checks that failed by raising a warning severity data quality issue.|*integer*|
|<span class="no-wrap-code">`errors`</span>|The number of most recent data quality checks that failed by raising an error severity data quality issue.|*integer*|
|<span class="no-wrap-code">`fatals`</span>|The number of most recent data quality checks that failed by raising a fatal severity data quality issue.|*integer*|
|<span class="no-wrap-code">`execution_errors`</span>|The number of data quality check execution errors that were reported due to access issues to the data source, invalid mapping in DQOps, invalid queries in data quality sensors or invalid python rules. When an execution error is reported, the configuration of a data quality check on a table must be updated.|*integer*|
|<span class="no-wrap-code">`data_quality_kpi`</span>|Data quality KPI score for the table, measured as a percentage of passed data quality checks. DQOps counts data quality issues at a warning severity level as passed checks. The data quality KPI score is a value in the range 0..100.|*double*|
|<span class="no-wrap-code">`checks`</span>|The dictionary of statuses for data quality checks. The keys are data quality check names, the values are the current data quality check statuses that describe the most current status.|*Dict[string, [CheckCurrentDataQualityStatusModel](./common.md#checkcurrentdataqualitystatusmodel)]*|
|<span class="no-wrap-code">`columns`</span>|Dictionary of data statues for all columns that have any known data quality results. The keys in the dictionary are the column names.|*Dict[string, [ColumnCurrentDataQualityStatusModel](./common.md#columncurrentdataqualitystatusmodel)]*|
|<span class="no-wrap-code">`dimensions`</span>|Dictionary of the current data quality statues for each data quality dimension.|*Dict[string, [DimensionCurrentDataQualityStatusModel](./common.md#dimensioncurrentdataqualitystatusmodel)]*|


___

