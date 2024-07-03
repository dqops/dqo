---
title: DQOps YAML file definitions
---
# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## IncidentNotificationMessage
Notification message payload that is posted (HTTP POST) to a notification endpoint with the details of a new or updated data quality incident.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`incident_id`</span>|Incident ID - the primary key that identifies each data quality incident.|*string*| | | |
|<span class="no-wrap-code ">`connection`</span>|Connection name affected by a data quality incident.|*string*| | | |
|<span class="no-wrap-code ">`schema`</span>|Schema name affected by a data quality incident.|*string*| | | |
|<span class="no-wrap-code ">`table`</span>|Table name affected by a data quality incident.|*string*| | | |
|<span class="no-wrap-code ">`table_priority`</span>|Table priority of the table that was affected by a data quality incident.|*integer*| | | |
|<span class="no-wrap-code ">`incident_hash`</span>|Data quality incident hash that identifies similar incidents on the same incident grouping level.|*long*| | | |
|<span class="no-wrap-code ">`data_group_name`</span>|The data group name that was affected by a data quality incident. The data group names are created from the values of columns and tags configured in the data grouping configuration. An example data group when grouping a static tag &quot;customers&quot;  as the first level grouping and a *country* column value for the second grouping level is *customers / UK*.|*string*| | | |
|<span class="no-wrap-code ">`quality_dimension`</span>|The data quality dimension that was affected by a data quality incident.|*string*| | | |
|<span class="no-wrap-code ">`check_category`</span>|The data quality check category that was affected by a data quality incident.|*string*| | | |
|<span class="no-wrap-code ">`check_type`</span>|The data quality check type that was affected by a data quality incident.|*string*| | | |
|<span class="no-wrap-code ">`check_name`</span>|The data quality check name that was affected by a data quality incident.|*string*| | | |
|<span class="no-wrap-code ">`highest_severity`</span>|The highest failed check severity that was detected as part of this data quality incident. Possible values are: 1 - warning, 2 - error, 3 - fatal.|*integer*| | | |
|<span class="no-wrap-code ">`failed_checks_count`</span>|The total number of failed data quality checks that were seen when the incident was raised for the first time.|*integer*| | | |
|<span class="no-wrap-code ">`issue_url`</span>|The link (url) to a ticket in an external system that is tracking this incident.|*string*| | | |
|<span class="no-wrap-code ">`status`</span>|Incident status.|*enum*|*open*<br/>*acknowledged*<br/>*resolved*<br/>*muted*<br/>| | |
|<span class="no-wrap-code ">`text`</span>|Notification text in Markdown format that contains the most important fields from the class.|*string*| | | |



___

