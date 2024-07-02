---
title: DQOps REST API incidents models reference
---
# DQOps REST API incidents models reference
The references of all objects used by [incidents](../operations/incidents.md) REST API operations are listed below.


## CheckResultEntryModel
Detailed results for a single check. Represent one row in the check results table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`id`</span>|Check result primary key|*string*|
|<span class="no-wrap-code">`check_hash`</span>|Check hash, do not set a value when writing results to DQOps|*long*|
|<span class="no-wrap-code">`check_category`</span>|Check category name|*string*|
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*|
|<span class="no-wrap-code">`check_display_name`</span>|Check display name|*string*|
|<span class="no-wrap-code">[`check_type`](./common.md#checktype)</span>|Check type|*[CheckType](./common.md#checktype)*|
|<span class="no-wrap-code">`actual_value`</span>|Actual value|*double*|
|<span class="no-wrap-code">`expected_value`</span>|Expected value|*double*|
|<span class="no-wrap-code">`warning_lower_bound`</span>|Warning lower bound|*double*|
|<span class="no-wrap-code">`warning_upper_bound`</span>|Warning upper bound|*double*|
|<span class="no-wrap-code">`error_lower_bound`</span>|Error lower bound|*double*|
|<span class="no-wrap-code">`error_upper_bound`</span>|Error upper bound|*double*|
|<span class="no-wrap-code">`fatal_lower_bound`</span>|Fatal lower bound|*double*|
|<span class="no-wrap-code">`fatal_upper_bound`</span>|Fatal upper bound|*double*|
|<span class="no-wrap-code">`severity`</span>|Issue severity, 0 - valid, 1 - warning, 2 - error, 3 - fatal|*integer*|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|
|<span class="no-wrap-code">`data_group`</span>|Data group name|*string*|
|<span class="no-wrap-code">`duration_ms`</span>|Duration (ms)|*integer*|
|<span class="no-wrap-code">[`time_gradient`](./error_samples.md#timeperiodgradient)</span>|Time gradient|*[TimePeriodGradient](./error_samples.md#timeperiodgradient)*|
|<span class="no-wrap-code">`time_period`</span>|Time period|*datetime*|
|<span class="no-wrap-code">`include_in_kpi`</span>|Include in KPI|*boolean*|
|<span class="no-wrap-code">`include_in_sla`</span>|Include in SLA|*boolean*|
|<span class="no-wrap-code">`provider`</span>|Provider name|*string*|
|<span class="no-wrap-code">`quality_dimension`</span>|Data quality dimension|*string*|
|<span class="no-wrap-code">`sensor_name`</span>|Sensor name|*string*|
|<span class="no-wrap-code">`table_comparison`</span>|Table comparison name|*string*|


___

## CheckResultSortOrder
Enumeration of columns names on a {@link CheckResultEntryModel CheckResultEntryModel} that can be sorted.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|executedAt<br/>checkHash<br/>checkCategory<br/>checkName<br/>checkDisplayName<br/>checkType<br/>actualValue<br/>expectedValue<br/>severity<br/>columnName<br/>dataGroup<br/>timeGradient<br/>timePeriod<br/>qualityDimension<br/>sensorName<br/>updatedAt<br/>|

___

## IncidentDailyIssuesCount
A model that stores a daily number of incidents.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`warnings`</span>|The number of failed data quality checks that generated a warning severity data quality issue.|*integer*|
|<span class="no-wrap-code">`errors`</span>|The number of failed data quality checks that generated an error severity data quality issue.|*integer*|
|<span class="no-wrap-code">`fatals`</span>|The number of failed data quality checks that generated a fatal severity data quality issue.|*integer*|
|<span class="no-wrap-code">`total_count`</span>|The total count of failed data quality checks on this day.|*integer*|


___

## IncidentIssueHistogramModel
Model that returns histograms of the data quality issue occurrences related to a data quality incident.
 The dates in the daily histogram are using the default timezone of the DQOps server.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`has_profiling_issues`</span>|True when this data quality incident is based on data quality issues from profiling checks within the filters applied to search for linked data quality issues.|*boolean*|
|<span class="no-wrap-code">`has_daily_monitoring_issues`</span>|True when this data quality incident is based on data quality issues from daily monitoring checks within the filters applied to search for linked data quality issues.|*boolean*|
|<span class="no-wrap-code">`has_monthly_monitoring_issues`</span>|True when this data quality incident is based on data quality issues from monthly monitoring checks within the filters applied to search for linked data quality issues.|*boolean*|
|<span class="no-wrap-code">`has_daily_partitioned_issues`</span>|True when this data quality incident is based on data quality issues from daily partitioned checks within the filters applied to search for linked data quality issues.|*boolean*|
|<span class="no-wrap-code">`has_monthly_partitioned_issues`</span>|True when this data quality incident is based on data quality issues from monthly partitioned checks within the filters applied to search for linked data quality issues.|*boolean*|
|<span class="no-wrap-code">`days`</span>|A map of the numbers of data quality issues per day, the day uses the DQOps server timezone.|*Dict[date, [IncidentDailyIssuesCount](#incidentdailyissuescount)]*|
|<span class="no-wrap-code">`columns`</span>|A map of column names with the most data quality issues related to the incident. The map returns the count of issues as the value.|*Dict[string, integer]*|
|<span class="no-wrap-code">`checks`</span>|A map of data quality check names with the most data quality issues related to the incident. The map returns the count of issues as the value.|*Dict[string, integer]*|


___

## IncidentStatus
Enumeration of the statuses used in the &quot;status&quot; field of the &quot;incidents&quot; table.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|open<br/>acknowledged<br/>resolved<br/>muted<br/>|

___

## IncidentModel
Data quality incident model shown on an incident details screen.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`incident_id`</span>|Incident ID - the primary key that identifies each data quality incident.|*string*|
|<span class="no-wrap-code">`connection`</span>|Connection name affected by a data quality incident.|*string*|
|<span class="no-wrap-code">`year`</span>|The year when the incident was first seen. This value is required to load an incident's monthly partition.|*integer*|
|<span class="no-wrap-code">`month`</span>|The month when the incident was first seen. This value is required to load an incident's monthly partition.|*integer*|
|<span class="no-wrap-code">`schema`</span>|Schema name affected by a data quality incident.|*string*|
|<span class="no-wrap-code">`table`</span>|Table name affected by a data quality incident.|*string*|
|<span class="no-wrap-code">`table_priority`</span>|Table priority of the table that was affected by a data quality incident.|*integer*|
|<span class="no-wrap-code">`incident_hash`</span>|Data quality incident hash that identifies similar incidents on the same incident grouping level.|*long*|
|<span class="no-wrap-code">`data_group`</span>|The data group that was affected by a data quality incident.|*string*|
|<span class="no-wrap-code">`quality_dimension`</span>|The data quality dimension that was affected by a data quality incident.|*string*|
|<span class="no-wrap-code">`check_category`</span>|The data quality check category that was affected by a data quality incident.|*string*|
|<span class="no-wrap-code">`check_type`</span>|The data quality check type that was affected by a data quality incident.|*string*|
|<span class="no-wrap-code">`check_name`</span>|The data quality check name that was affected by a data quality incident.|*string*|
|<span class="no-wrap-code">`highest_severity`</span>|The highest failed check severity that was detected as part of this data quality incident. Possible values are: 1 - warning, 2 - error, 3 - fatal.|*integer*|
|<span class="no-wrap-code">`minimum_severity`</span>|The minimum severity of the data quality incident, copied from the incident configuration at a connection or table at the time when the incident was first seen. Possible values are: 1 - warning, 2 - error, 3 - fatal.|*integer*|
|<span class="no-wrap-code">`failed_checks_count`</span>|The total number of failed data quality checks that were seen when the incident was raised for the first time.|*integer*|
|<span class="no-wrap-code">`issue_url`</span>|The link (url) to a ticket in an external system that is tracking this incident.|*string*|
|<span class="no-wrap-code">[`status`](./incidents.md#incidentstatus)</span>|Incident status.|*[IncidentStatus](./incidents.md#incidentstatus)*|


___

## IncidentSortOrder
Incident sort order columns.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|table<br/>tablePriority<br/>firstSeen<br/>lastSeen<br/>dataGroup<br/>qualityDimension<br/>checkName<br/>highestSeverity<br/>failedChecksCount<br/>|

___

## IncidentsPerConnectionModel
Simple model that returns a list of connections and a number of open (new) data quality incidents per connection.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection`</span>|Connection (data source) name.|*string*|
|<span class="no-wrap-code">`open_incidents`</span>|Count of open (new) data quality incidents.|*integer*|


___

## SortDirection
REST api model sort direction.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|asc<br/>desc<br/>|

___

## TopIncidentGrouping
Enumeration of groupings for incidents.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|dimension<br/>category<br/>connection<br/>|

___

## TopIncidentsModel
Summary model with the most recent incidents grouped by one attribute (data quality dimension, data quality check category, etc).


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`grouping`](./incidents.md#topincidentgrouping)</span>|Incident grouping used to group the top incidents in the dictionary of top incidents.|*[TopIncidentGrouping](./incidents.md#topincidentgrouping)*|
|<span class="no-wrap-code">[`status`](./incidents.md#incidentstatus)</span>|Incident status of the incidents that are returned.|*[IncidentStatus](./incidents.md#incidentstatus)*|
|<span class="no-wrap-code">`top_incidents`</span>|Dictionary of the top incidents, grouped by the grouping such as the data quality dimension or a data quality check category. The incidents are sorted by the first seen descending (the most recent first).|*Dict[string, List[[IncidentModel](./incidents.md#incidentmodel)]]*|


___

