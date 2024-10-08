---
title: DQOps REST API check_results operations
---
# DQOps REST API check_results operations
Returns all the data quality check results of executed checks on tables and columns.


___
## get_column_monitoring_checks_results
Returns a complete view of the recent column level monitoring executions for the monitoring at a requested time scale

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_column_monitoring_checks_results.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/results
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_results_list_model`</span>||*List[[CheckResultsListModel](../models/check_results.md#checkresultslistmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`data_group`</span>|Data group|*string*| |
|<span class="no-wrap-code">`month_start`</span>|Month start boundary|*string*| |
|<span class="no-wrap-code">`month_end`</span>|Month end boundary|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |
|<span class="no-wrap-code">`category`</span>|Check category name|*string*| |
|<span class="no-wrap-code">`table_comparison`</span>|Table comparison name|*string*| |
|<span class="no-wrap-code">[`load_mode`](../models/check_results.md#checkresultsdetailedloadmode)</span>|Results load mode|*[CheckResultsDetailedLoadMode](../models/check_results.md#checkresultsdetailedloadmode)*| |
|<span class="no-wrap-code">`max_results_per_check`</span>|Maximum number of results per check, the default is 100|*long*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily/results^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "dataGroup" : "sample_data_grouping",
		  "checkResultEntries" : [ {
		    "id" : "3854372",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 100.0,
		    "expectedValue" : 110.0,
		    "warningLowerBound" : 105.0,
		    "warningUpperBound" : 115.0,
		    "errorLowerBound" : 95.0,
		    "errorUpperBound" : 125.0,
		    "fatalLowerBound" : 85.0,
		    "fatalUpperBound" : 135.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T14:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T14:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "-8597741925434587255",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 110.0,
		    "expectedValue" : 120.0,
		    "warningLowerBound" : 115.0,
		    "warningUpperBound" : 125.0,
		    "errorLowerBound" : 105.0,
		    "errorUpperBound" : 135.0,
		    "fatalLowerBound" : 95.0,
		    "fatalUpperBound" : 145.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T15:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T15:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "8520797026624525526",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 120.0,
		    "expectedValue" : 130.0,
		    "warningLowerBound" : 125.0,
		    "warningUpperBound" : 135.0,
		    "errorLowerBound" : 115.0,
		    "errorUpperBound" : 145.0,
		    "fatalLowerBound" : 105.0,
		    "fatalUpperBound" : 155.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T16:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T16:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "879763789071004098",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 130.0,
		    "expectedValue" : 140.0,
		    "warningLowerBound" : 135.0,
		    "warningUpperBound" : 145.0,
		    "errorLowerBound" : 125.0,
		    "errorUpperBound" : 155.0,
		    "fatalLowerBound" : 115.0,
		    "fatalUpperBound" : 165.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T17:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T17:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  } ]
		}, {
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "dataGroup" : "sample_data_grouping",
		  "checkResultEntries" : [ {
		    "id" : "3854372",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 100.0,
		    "expectedValue" : 110.0,
		    "warningLowerBound" : 105.0,
		    "warningUpperBound" : 115.0,
		    "errorLowerBound" : 95.0,
		    "errorUpperBound" : 125.0,
		    "fatalLowerBound" : 85.0,
		    "fatalUpperBound" : 135.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T14:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T14:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "-8597741925434587255",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 110.0,
		    "expectedValue" : 120.0,
		    "warningLowerBound" : 115.0,
		    "warningUpperBound" : 125.0,
		    "errorLowerBound" : 105.0,
		    "errorUpperBound" : 135.0,
		    "fatalLowerBound" : 95.0,
		    "fatalUpperBound" : 145.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T15:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T15:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "8520797026624525526",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 120.0,
		    "expectedValue" : 130.0,
		    "warningLowerBound" : 125.0,
		    "warningUpperBound" : 135.0,
		    "errorLowerBound" : 115.0,
		    "errorUpperBound" : 145.0,
		    "fatalLowerBound" : 105.0,
		    "fatalUpperBound" : 155.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T16:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T16:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "879763789071004098",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 130.0,
		    "expectedValue" : 140.0,
		    "warningLowerBound" : 135.0,
		    "warningUpperBound" : 145.0,
		    "errorLowerBound" : 125.0,
		    "errorUpperBound" : 155.0,
		    "fatalLowerBound" : 115.0,
		    "fatalUpperBound" : 165.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T17:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T17:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  } ]
		}, {
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "dataGroup" : "sample_data_grouping",
		  "checkResultEntries" : [ {
		    "id" : "3854372",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 100.0,
		    "expectedValue" : 110.0,
		    "warningLowerBound" : 105.0,
		    "warningUpperBound" : 115.0,
		    "errorLowerBound" : 95.0,
		    "errorUpperBound" : 125.0,
		    "fatalLowerBound" : 85.0,
		    "fatalUpperBound" : 135.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T14:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T14:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "-8597741925434587255",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 110.0,
		    "expectedValue" : 120.0,
		    "warningLowerBound" : 115.0,
		    "warningUpperBound" : 125.0,
		    "errorLowerBound" : 105.0,
		    "errorUpperBound" : 135.0,
		    "fatalLowerBound" : 95.0,
		    "fatalUpperBound" : 145.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T15:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T15:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "8520797026624525526",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 120.0,
		    "expectedValue" : 130.0,
		    "warningLowerBound" : 125.0,
		    "warningUpperBound" : 135.0,
		    "errorLowerBound" : 115.0,
		    "errorUpperBound" : 145.0,
		    "fatalLowerBound" : 105.0,
		    "fatalUpperBound" : 155.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T16:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T16:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "879763789071004098",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 130.0,
		    "expectedValue" : 140.0,
		    "warningLowerBound" : 135.0,
		    "warningUpperBound" : 145.0,
		    "errorLowerBound" : 125.0,
		    "errorUpperBound" : 155.0,
		    "fatalLowerBound" : 115.0,
		    "fatalUpperBound" : 165.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T17:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T17:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  } ]
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_monitoring_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode, \
	                                CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_monitoring_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_monitoring_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode, \
	                                CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_monitoring_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_monitoring_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode, \
	                                CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_monitoring_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_monitoring_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode, \
	                                CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_monitoring_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    



___
## get_column_partitioned_checks_results
Returns an overview of the most recent column level partitioned checks executions for a requested time scale

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_column_partitioned_checks_results.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/results
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_results_list_model`</span>||*List[[CheckResultsListModel](../models/check_results.md#checkresultslistmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`data_group`</span>|Data group|*string*| |
|<span class="no-wrap-code">`month_start`</span>|Month start boundary|*string*| |
|<span class="no-wrap-code">`month_end`</span>|Month end boundary|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |
|<span class="no-wrap-code">`category`</span>|Check category name|*string*| |
|<span class="no-wrap-code">`table_comparison`</span>|Table comparison name|*string*| |
|<span class="no-wrap-code">[`load_mode`](../models/check_results.md#checkresultsdetailedloadmode)</span>|Results load mode|*[CheckResultsDetailedLoadMode](../models/check_results.md#checkresultsdetailedloadmode)*| |
|<span class="no-wrap-code">`max_results_per_check`</span>|Maximum number of results per check, the default is 100|*long*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily/results^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "dataGroup" : "sample_data_grouping",
		  "checkResultEntries" : [ {
		    "id" : "3854372",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 100.0,
		    "expectedValue" : 110.0,
		    "warningLowerBound" : 105.0,
		    "warningUpperBound" : 115.0,
		    "errorLowerBound" : 95.0,
		    "errorUpperBound" : 125.0,
		    "fatalLowerBound" : 85.0,
		    "fatalUpperBound" : 135.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T14:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T14:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "-8597741925434587255",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 110.0,
		    "expectedValue" : 120.0,
		    "warningLowerBound" : 115.0,
		    "warningUpperBound" : 125.0,
		    "errorLowerBound" : 105.0,
		    "errorUpperBound" : 135.0,
		    "fatalLowerBound" : 95.0,
		    "fatalUpperBound" : 145.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T15:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T15:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "8520797026624525526",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 120.0,
		    "expectedValue" : 130.0,
		    "warningLowerBound" : 125.0,
		    "warningUpperBound" : 135.0,
		    "errorLowerBound" : 115.0,
		    "errorUpperBound" : 145.0,
		    "fatalLowerBound" : 105.0,
		    "fatalUpperBound" : 155.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T16:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T16:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "879763789071004098",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 130.0,
		    "expectedValue" : 140.0,
		    "warningLowerBound" : 135.0,
		    "warningUpperBound" : 145.0,
		    "errorLowerBound" : 125.0,
		    "errorUpperBound" : 155.0,
		    "fatalLowerBound" : 115.0,
		    "fatalUpperBound" : 165.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T17:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T17:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  } ]
		}, {
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "dataGroup" : "sample_data_grouping",
		  "checkResultEntries" : [ {
		    "id" : "3854372",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 100.0,
		    "expectedValue" : 110.0,
		    "warningLowerBound" : 105.0,
		    "warningUpperBound" : 115.0,
		    "errorLowerBound" : 95.0,
		    "errorUpperBound" : 125.0,
		    "fatalLowerBound" : 85.0,
		    "fatalUpperBound" : 135.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T14:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T14:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "-8597741925434587255",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 110.0,
		    "expectedValue" : 120.0,
		    "warningLowerBound" : 115.0,
		    "warningUpperBound" : 125.0,
		    "errorLowerBound" : 105.0,
		    "errorUpperBound" : 135.0,
		    "fatalLowerBound" : 95.0,
		    "fatalUpperBound" : 145.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T15:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T15:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "8520797026624525526",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 120.0,
		    "expectedValue" : 130.0,
		    "warningLowerBound" : 125.0,
		    "warningUpperBound" : 135.0,
		    "errorLowerBound" : 115.0,
		    "errorUpperBound" : 145.0,
		    "fatalLowerBound" : 105.0,
		    "fatalUpperBound" : 155.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T16:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T16:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "879763789071004098",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 130.0,
		    "expectedValue" : 140.0,
		    "warningLowerBound" : 135.0,
		    "warningUpperBound" : 145.0,
		    "errorLowerBound" : 125.0,
		    "errorUpperBound" : 155.0,
		    "fatalLowerBound" : 115.0,
		    "fatalUpperBound" : 165.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T17:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T17:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  } ]
		}, {
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "dataGroup" : "sample_data_grouping",
		  "checkResultEntries" : [ {
		    "id" : "3854372",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 100.0,
		    "expectedValue" : 110.0,
		    "warningLowerBound" : 105.0,
		    "warningUpperBound" : 115.0,
		    "errorLowerBound" : 95.0,
		    "errorUpperBound" : 125.0,
		    "fatalLowerBound" : 85.0,
		    "fatalUpperBound" : 135.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T14:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T14:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "-8597741925434587255",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 110.0,
		    "expectedValue" : 120.0,
		    "warningLowerBound" : 115.0,
		    "warningUpperBound" : 125.0,
		    "errorLowerBound" : 105.0,
		    "errorUpperBound" : 135.0,
		    "fatalLowerBound" : 95.0,
		    "fatalUpperBound" : 145.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T15:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T15:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "8520797026624525526",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 120.0,
		    "expectedValue" : 130.0,
		    "warningLowerBound" : 125.0,
		    "warningUpperBound" : 135.0,
		    "errorLowerBound" : 115.0,
		    "errorUpperBound" : 145.0,
		    "fatalLowerBound" : 105.0,
		    "fatalUpperBound" : 155.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T16:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T16:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "879763789071004098",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 130.0,
		    "expectedValue" : 140.0,
		    "warningLowerBound" : 135.0,
		    "warningUpperBound" : 145.0,
		    "errorLowerBound" : 125.0,
		    "errorUpperBound" : 155.0,
		    "fatalLowerBound" : 115.0,
		    "fatalUpperBound" : 165.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T17:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T17:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  } ]
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_partitioned_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode, \
	                                CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_partitioned_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_partitioned_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode, \
	                                CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_partitioned_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_partitioned_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode, \
	                                CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_partitioned_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_partitioned_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode, \
	                                CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_partitioned_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    



___
## get_column_profiling_checks_results
Returns an overview of the most recent check executions for all column level data quality profiling checks on a column

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_column_profiling_checks_results.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/results
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_results_list_model`</span>||*List[[CheckResultsListModel](../models/check_results.md#checkresultslistmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`data_group`</span>|Data group|*string*| |
|<span class="no-wrap-code">`month_start`</span>|Month start boundary|*string*| |
|<span class="no-wrap-code">`month_end`</span>|Month end boundary|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |
|<span class="no-wrap-code">`category`</span>|Check category name|*string*| |
|<span class="no-wrap-code">`table_comparison`</span>|Table comparison name|*string*| |
|<span class="no-wrap-code">[`load_mode`](../models/check_results.md#checkresultsdetailedloadmode)</span>|Results load mode|*[CheckResultsDetailedLoadMode](../models/check_results.md#checkresultsdetailedloadmode)*| |
|<span class="no-wrap-code">`max_results_per_check`</span>|Maximum number of results per check, the default is 100|*long*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/results^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "dataGroup" : "sample_data_grouping",
		  "checkResultEntries" : [ {
		    "id" : "3854372",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 100.0,
		    "expectedValue" : 110.0,
		    "warningLowerBound" : 105.0,
		    "warningUpperBound" : 115.0,
		    "errorLowerBound" : 95.0,
		    "errorUpperBound" : 125.0,
		    "fatalLowerBound" : 85.0,
		    "fatalUpperBound" : 135.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T14:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T14:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "-8597741925434587255",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 110.0,
		    "expectedValue" : 120.0,
		    "warningLowerBound" : 115.0,
		    "warningUpperBound" : 125.0,
		    "errorLowerBound" : 105.0,
		    "errorUpperBound" : 135.0,
		    "fatalLowerBound" : 95.0,
		    "fatalUpperBound" : 145.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T15:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T15:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "8520797026624525526",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 120.0,
		    "expectedValue" : 130.0,
		    "warningLowerBound" : 125.0,
		    "warningUpperBound" : 135.0,
		    "errorLowerBound" : 115.0,
		    "errorUpperBound" : 145.0,
		    "fatalLowerBound" : 105.0,
		    "fatalUpperBound" : 155.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T16:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T16:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "879763789071004098",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 130.0,
		    "expectedValue" : 140.0,
		    "warningLowerBound" : 135.0,
		    "warningUpperBound" : 145.0,
		    "errorLowerBound" : 125.0,
		    "errorUpperBound" : 155.0,
		    "fatalLowerBound" : 115.0,
		    "fatalUpperBound" : 165.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T17:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T17:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  } ]
		}, {
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "dataGroup" : "sample_data_grouping",
		  "checkResultEntries" : [ {
		    "id" : "3854372",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 100.0,
		    "expectedValue" : 110.0,
		    "warningLowerBound" : 105.0,
		    "warningUpperBound" : 115.0,
		    "errorLowerBound" : 95.0,
		    "errorUpperBound" : 125.0,
		    "fatalLowerBound" : 85.0,
		    "fatalUpperBound" : 135.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T14:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T14:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "-8597741925434587255",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 110.0,
		    "expectedValue" : 120.0,
		    "warningLowerBound" : 115.0,
		    "warningUpperBound" : 125.0,
		    "errorLowerBound" : 105.0,
		    "errorUpperBound" : 135.0,
		    "fatalLowerBound" : 95.0,
		    "fatalUpperBound" : 145.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T15:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T15:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "8520797026624525526",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 120.0,
		    "expectedValue" : 130.0,
		    "warningLowerBound" : 125.0,
		    "warningUpperBound" : 135.0,
		    "errorLowerBound" : 115.0,
		    "errorUpperBound" : 145.0,
		    "fatalLowerBound" : 105.0,
		    "fatalUpperBound" : 155.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T16:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T16:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "879763789071004098",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 130.0,
		    "expectedValue" : 140.0,
		    "warningLowerBound" : 135.0,
		    "warningUpperBound" : 145.0,
		    "errorLowerBound" : 125.0,
		    "errorUpperBound" : 155.0,
		    "fatalLowerBound" : 115.0,
		    "fatalUpperBound" : 165.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T17:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T17:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  } ]
		}, {
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "dataGroup" : "sample_data_grouping",
		  "checkResultEntries" : [ {
		    "id" : "3854372",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 100.0,
		    "expectedValue" : 110.0,
		    "warningLowerBound" : 105.0,
		    "warningUpperBound" : 115.0,
		    "errorLowerBound" : 95.0,
		    "errorUpperBound" : 125.0,
		    "fatalLowerBound" : 85.0,
		    "fatalUpperBound" : 135.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T14:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T14:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "-8597741925434587255",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 110.0,
		    "expectedValue" : 120.0,
		    "warningLowerBound" : 115.0,
		    "warningUpperBound" : 125.0,
		    "errorLowerBound" : 105.0,
		    "errorUpperBound" : 135.0,
		    "fatalLowerBound" : 95.0,
		    "fatalUpperBound" : 145.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T15:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T15:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "8520797026624525526",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 120.0,
		    "expectedValue" : 130.0,
		    "warningLowerBound" : 125.0,
		    "warningUpperBound" : 135.0,
		    "errorLowerBound" : 115.0,
		    "errorUpperBound" : 145.0,
		    "fatalLowerBound" : 105.0,
		    "fatalUpperBound" : 155.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T16:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T16:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "879763789071004098",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 130.0,
		    "expectedValue" : 140.0,
		    "warningLowerBound" : 135.0,
		    "warningUpperBound" : 145.0,
		    "errorLowerBound" : 125.0,
		    "errorUpperBound" : 155.0,
		    "fatalLowerBound" : 115.0,
		    "fatalUpperBound" : 165.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T17:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T17:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  } ]
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_profiling_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_profiling_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_profiling_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_profiling_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_profiling_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_profiling_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_profiling_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_profiling_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    



___
## get_table_data_quality_status
Read the most recent results of executed data quality checks on the table and return the current table&#x27;s data quality status - the number of failed data quality checks if the table has active data quality issues. Also returns the names of data quality checks that did not pass most recently. This operation verifies only the status of the most recently executed data quality checks. Previous data quality issues are not counted.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_table_data_quality_status.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/status
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_current_data_quality_status_model`](../models/check_results.md#tablecurrentdataqualitystatusmodel)</span>||*[TableCurrentDataQualityStatusModel](../models/check_results.md#tablecurrentdataqualitystatusmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`months`</span>|Optional filter - the number of months to review the data quality check results. For partitioned checks, it is the number of months to analyze. The default value is 1 (which is the current month and 1 previous month).|*long*| |
|<span class="no-wrap-code">`since`</span>|Optional filter that accepts an UTC timestamp to read only data quality check results captured since that timestamp.|*string*| |
|<span class="no-wrap-code">`profiling`</span>|Optional check type filter to detect the current status of the profiling checks results. The default value is false, excluding profiling checks from the current table status detection. If enabled, only the status of the most recent check result is retrieved.|*boolean*| |
|<span class="no-wrap-code">`monitoring`</span>|Optional check type filter to detect the current status of the monitoring checks results. The default value is true, including monitoring checks in the current table status detection. If enabled, only the status of the most recent check result is retrieved.|*boolean*| |
|<span class="no-wrap-code">`partitioned`</span>|Optional check type filter to detect the current status of the partitioned checks results. The default value is true, including partitioned checks in the current table status detection. Detection of the status of partitioned checks is different. When enabled, DQOps checks the highest severity status of all partitions since the **since** date or within the last **months**.|*boolean*| |
|<span class="no-wrap-code">[`check_time_scale`](../models/common.md#checktimescale)</span>|Optional time scale filter for monitoring and partitioned checks (values: daily or monthly).|*[CheckTimeScale](../models/common.md#checktimescale)*| |
|<span class="no-wrap-code">`data_group`</span>|Optional data group|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Optional check name|*string*| |
|<span class="no-wrap-code">`category`</span>|Optional check category name|*string*| |
|<span class="no-wrap-code">`table_comparison`</span>|Optional table comparison name|*string*| |
|<span class="no-wrap-code">`quality_dimension`</span>|Optional data quality dimension|*string*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/status^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "data_domain" : "",
		  "connection_name" : "sample_connection",
		  "schema_name" : "sample_schema",
		  "table_name" : "sample_table",
		  "total_row_count" : 122000,
		  "data_freshness_delay_days" : 1.221,
		  "current_severity" : "warning",
		  "highest_historical_severity" : "error",
		  "last_check_executed_at" : "2007-10-14T16:42:42Z",
		  "executed_checks" : 8,
		  "valid_results" : 3,
		  "warnings" : 5,
		  "errors" : 0,
		  "fatals" : 0,
		  "execution_errors" : 0,
		  "data_quality_kpi" : 100.0,
		  "checks" : {
		    "table_sample_check_1" : {
		      "current_severity" : "warning",
		      "highest_historical_severity" : "error",
		      "last_executed_at" : "2007-10-14T14:13:42Z",
		      "check_type" : "profiling",
		      "category" : "sample_category",
		      "quality_dimension" : "sample_quality_dimension",
		      "executed_checks" : 0,
		      "valid_results" : 0,
		      "warnings" : 1,
		      "errors" : 1,
		      "fatals" : 0,
		      "execution_errors" : 0
		    },
		    "table_sample_check_2" : {
		      "current_severity" : "valid",
		      "highest_historical_severity" : "error",
		      "last_executed_at" : "2007-10-14T14:39:42Z",
		      "check_type" : "profiling",
		      "category" : "sample_category",
		      "quality_dimension" : "sample_quality_dimension",
		      "executed_checks" : 0,
		      "valid_results" : 0,
		      "warnings" : 1,
		      "errors" : 1,
		      "fatals" : 0,
		      "execution_errors" : 0
		    }
		  },
		  "columns" : {
		    "sample_column_1" : {
		      "current_severity" : "warning",
		      "highest_historical_severity" : "error",
		      "last_check_executed_at" : "2007-10-14T16:42:42Z",
		      "executed_checks" : 3,
		      "valid_results" : 1,
		      "warnings" : 2,
		      "errors" : 0,
		      "fatals" : 0,
		      "execution_errors" : 0,
		      "checks" : {
		        "sample_check_1" : {
		          "current_severity" : "warning",
		          "highest_historical_severity" : "error",
		          "last_executed_at" : "2007-10-14T14:13:42Z",
		          "check_type" : "profiling",
		          "category" : "sample_category",
		          "quality_dimension" : "sample_quality_dimension",
		          "executed_checks" : 0,
		          "valid_results" : 0,
		          "warnings" : 1,
		          "errors" : 1,
		          "fatals" : 0,
		          "execution_errors" : 0
		        },
		        "sample_check_2" : {
		          "current_severity" : "valid",
		          "highest_historical_severity" : "error",
		          "last_executed_at" : "2007-10-14T14:45:42Z",
		          "check_type" : "profiling",
		          "category" : "sample_category",
		          "quality_dimension" : "sample_quality_dimension",
		          "executed_checks" : 0,
		          "valid_results" : 0,
		          "warnings" : 1,
		          "errors" : 1,
		          "fatals" : 0,
		          "execution_errors" : 0
		        },
		        "sample_check_3" : {
		          "current_severity" : "warning",
		          "highest_historical_severity" : "error",
		          "last_executed_at" : "2007-10-14T16:42:42Z",
		          "check_type" : "profiling",
		          "category" : "sample_category",
		          "quality_dimension" : "sample_quality_dimension",
		          "executed_checks" : 0,
		          "valid_results" : 0,
		          "warnings" : 1,
		          "errors" : 1,
		          "fatals" : 0,
		          "execution_errors" : 0
		        }
		      },
		      "dimensions" : { }
		    },
		    "sample_column_2" : {
		      "current_severity" : "warning",
		      "highest_historical_severity" : "error",
		      "last_check_executed_at" : "2007-10-14T16:42:42Z",
		      "executed_checks" : 3,
		      "valid_results" : 1,
		      "warnings" : 2,
		      "errors" : 0,
		      "fatals" : 0,
		      "execution_errors" : 0,
		      "checks" : {
		        "sample_check_1" : {
		          "current_severity" : "warning",
		          "highest_historical_severity" : "error",
		          "last_executed_at" : "2007-10-14T14:13:42Z",
		          "check_type" : "profiling",
		          "category" : "sample_category",
		          "quality_dimension" : "sample_quality_dimension",
		          "executed_checks" : 0,
		          "valid_results" : 0,
		          "warnings" : 1,
		          "errors" : 1,
		          "fatals" : 0,
		          "execution_errors" : 0
		        },
		        "sample_check_2" : {
		          "current_severity" : "valid",
		          "highest_historical_severity" : "error",
		          "last_executed_at" : "2007-10-14T14:45:42Z",
		          "check_type" : "profiling",
		          "category" : "sample_category",
		          "quality_dimension" : "sample_quality_dimension",
		          "executed_checks" : 0,
		          "valid_results" : 0,
		          "warnings" : 1,
		          "errors" : 1,
		          "fatals" : 0,
		          "execution_errors" : 0
		        },
		        "sample_check_3" : {
		          "current_severity" : "warning",
		          "highest_historical_severity" : "error",
		          "last_executed_at" : "2007-10-14T16:42:42Z",
		          "check_type" : "profiling",
		          "category" : "sample_category",
		          "quality_dimension" : "sample_quality_dimension",
		          "executed_checks" : 0,
		          "valid_results" : 0,
		          "warnings" : 1,
		          "errors" : 1,
		          "fatals" : 0,
		          "execution_errors" : 0
		        }
		      },
		      "dimensions" : { }
		    }
		  },
		  "dimensions" : { },
		  "table_exist" : true
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_data_quality_status
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_data_quality_status.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableCurrentDataQualityStatusModel(
			data_domain='',
			connection_name='sample_connection',
			schema_name='sample_schema',
			table_name='sample_table',
			total_row_count=122000,
			data_freshness_delay_days=1.221,
			current_severity=RuleSeverityLevel.WARNING,
			highest_historical_severity=RuleSeverityLevel.ERROR,
			last_check_executed_at='2007-10-14T16:42:42Z',
			executed_checks=8,
			valid_results=3,
			warnings=5,
			errors=0,
			fatals=0,
			execution_errors=0,
			data_quality_kpi=100.0,
			checks={
				'table_sample_check_1': CheckCurrentDataQualityStatusModel(
					current_severity=CheckResultStatus.WARNING,
					highest_historical_severity=RuleSeverityLevel.ERROR,
					last_executed_at='2007-10-14T14:13:42Z',
					check_type=CheckType.PROFILING,
					category='sample_category',
					quality_dimension='sample_quality_dimension',
					executed_checks=0,
					valid_results=0,
					warnings=1,
					errors=1,
					fatals=0,
					execution_errors=0
				),
				'table_sample_check_2': CheckCurrentDataQualityStatusModel(
					current_severity=CheckResultStatus.VALID,
					highest_historical_severity=RuleSeverityLevel.ERROR,
					last_executed_at='2007-10-14T14:39:42Z',
					check_type=CheckType.PROFILING,
					category='sample_category',
					quality_dimension='sample_quality_dimension',
					executed_checks=0,
					valid_results=0,
					warnings=1,
					errors=1,
					fatals=0,
					execution_errors=0
				)
			},
			columns={
				'sample_column_1': ColumnCurrentDataQualityStatusModel(
					current_severity=RuleSeverityLevel.WARNING,
					highest_historical_severity=RuleSeverityLevel.ERROR,
					last_check_executed_at='2007-10-14T16:42:42Z',
					executed_checks=3,
					valid_results=1,
					warnings=2,
					errors=0,
					fatals=0,
					execution_errors=0,
					checks={
						'sample_check_1': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.WARNING,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T14:13:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						),
						'sample_check_2': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.VALID,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T14:45:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						),
						'sample_check_3': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.WARNING,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T16:42:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						)
					},
					dimensions={
					
					}
				),
				'sample_column_2': ColumnCurrentDataQualityStatusModel(
					current_severity=RuleSeverityLevel.WARNING,
					highest_historical_severity=RuleSeverityLevel.ERROR,
					last_check_executed_at='2007-10-14T16:42:42Z',
					executed_checks=3,
					valid_results=1,
					warnings=2,
					errors=0,
					fatals=0,
					execution_errors=0,
					checks={
						'sample_check_1': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.WARNING,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T14:13:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						),
						'sample_check_2': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.VALID,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T14:45:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						),
						'sample_check_3': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.WARNING,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T16:42:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						)
					},
					dimensions={
					
					}
				)
			},
			dimensions={
			
			},
			table_exist=True
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_data_quality_status
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_data_quality_status.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableCurrentDataQualityStatusModel(
			data_domain='',
			connection_name='sample_connection',
			schema_name='sample_schema',
			table_name='sample_table',
			total_row_count=122000,
			data_freshness_delay_days=1.221,
			current_severity=RuleSeverityLevel.WARNING,
			highest_historical_severity=RuleSeverityLevel.ERROR,
			last_check_executed_at='2007-10-14T16:42:42Z',
			executed_checks=8,
			valid_results=3,
			warnings=5,
			errors=0,
			fatals=0,
			execution_errors=0,
			data_quality_kpi=100.0,
			checks={
				'table_sample_check_1': CheckCurrentDataQualityStatusModel(
					current_severity=CheckResultStatus.WARNING,
					highest_historical_severity=RuleSeverityLevel.ERROR,
					last_executed_at='2007-10-14T14:13:42Z',
					check_type=CheckType.PROFILING,
					category='sample_category',
					quality_dimension='sample_quality_dimension',
					executed_checks=0,
					valid_results=0,
					warnings=1,
					errors=1,
					fatals=0,
					execution_errors=0
				),
				'table_sample_check_2': CheckCurrentDataQualityStatusModel(
					current_severity=CheckResultStatus.VALID,
					highest_historical_severity=RuleSeverityLevel.ERROR,
					last_executed_at='2007-10-14T14:39:42Z',
					check_type=CheckType.PROFILING,
					category='sample_category',
					quality_dimension='sample_quality_dimension',
					executed_checks=0,
					valid_results=0,
					warnings=1,
					errors=1,
					fatals=0,
					execution_errors=0
				)
			},
			columns={
				'sample_column_1': ColumnCurrentDataQualityStatusModel(
					current_severity=RuleSeverityLevel.WARNING,
					highest_historical_severity=RuleSeverityLevel.ERROR,
					last_check_executed_at='2007-10-14T16:42:42Z',
					executed_checks=3,
					valid_results=1,
					warnings=2,
					errors=0,
					fatals=0,
					execution_errors=0,
					checks={
						'sample_check_1': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.WARNING,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T14:13:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						),
						'sample_check_2': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.VALID,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T14:45:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						),
						'sample_check_3': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.WARNING,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T16:42:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						)
					},
					dimensions={
					
					}
				),
				'sample_column_2': ColumnCurrentDataQualityStatusModel(
					current_severity=RuleSeverityLevel.WARNING,
					highest_historical_severity=RuleSeverityLevel.ERROR,
					last_check_executed_at='2007-10-14T16:42:42Z',
					executed_checks=3,
					valid_results=1,
					warnings=2,
					errors=0,
					fatals=0,
					execution_errors=0,
					checks={
						'sample_check_1': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.WARNING,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T14:13:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						),
						'sample_check_2': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.VALID,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T14:45:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						),
						'sample_check_3': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.WARNING,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T16:42:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						)
					},
					dimensions={
					
					}
				)
			},
			dimensions={
			
			},
			table_exist=True
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_data_quality_status
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_data_quality_status.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableCurrentDataQualityStatusModel(
			data_domain='',
			connection_name='sample_connection',
			schema_name='sample_schema',
			table_name='sample_table',
			total_row_count=122000,
			data_freshness_delay_days=1.221,
			current_severity=RuleSeverityLevel.WARNING,
			highest_historical_severity=RuleSeverityLevel.ERROR,
			last_check_executed_at='2007-10-14T16:42:42Z',
			executed_checks=8,
			valid_results=3,
			warnings=5,
			errors=0,
			fatals=0,
			execution_errors=0,
			data_quality_kpi=100.0,
			checks={
				'table_sample_check_1': CheckCurrentDataQualityStatusModel(
					current_severity=CheckResultStatus.WARNING,
					highest_historical_severity=RuleSeverityLevel.ERROR,
					last_executed_at='2007-10-14T14:13:42Z',
					check_type=CheckType.PROFILING,
					category='sample_category',
					quality_dimension='sample_quality_dimension',
					executed_checks=0,
					valid_results=0,
					warnings=1,
					errors=1,
					fatals=0,
					execution_errors=0
				),
				'table_sample_check_2': CheckCurrentDataQualityStatusModel(
					current_severity=CheckResultStatus.VALID,
					highest_historical_severity=RuleSeverityLevel.ERROR,
					last_executed_at='2007-10-14T14:39:42Z',
					check_type=CheckType.PROFILING,
					category='sample_category',
					quality_dimension='sample_quality_dimension',
					executed_checks=0,
					valid_results=0,
					warnings=1,
					errors=1,
					fatals=0,
					execution_errors=0
				)
			},
			columns={
				'sample_column_1': ColumnCurrentDataQualityStatusModel(
					current_severity=RuleSeverityLevel.WARNING,
					highest_historical_severity=RuleSeverityLevel.ERROR,
					last_check_executed_at='2007-10-14T16:42:42Z',
					executed_checks=3,
					valid_results=1,
					warnings=2,
					errors=0,
					fatals=0,
					execution_errors=0,
					checks={
						'sample_check_1': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.WARNING,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T14:13:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						),
						'sample_check_2': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.VALID,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T14:45:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						),
						'sample_check_3': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.WARNING,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T16:42:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						)
					},
					dimensions={
					
					}
				),
				'sample_column_2': ColumnCurrentDataQualityStatusModel(
					current_severity=RuleSeverityLevel.WARNING,
					highest_historical_severity=RuleSeverityLevel.ERROR,
					last_check_executed_at='2007-10-14T16:42:42Z',
					executed_checks=3,
					valid_results=1,
					warnings=2,
					errors=0,
					fatals=0,
					execution_errors=0,
					checks={
						'sample_check_1': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.WARNING,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T14:13:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						),
						'sample_check_2': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.VALID,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T14:45:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						),
						'sample_check_3': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.WARNING,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T16:42:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						)
					},
					dimensions={
					
					}
				)
			},
			dimensions={
			
			},
			table_exist=True
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_data_quality_status
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_data_quality_status.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableCurrentDataQualityStatusModel(
			data_domain='',
			connection_name='sample_connection',
			schema_name='sample_schema',
			table_name='sample_table',
			total_row_count=122000,
			data_freshness_delay_days=1.221,
			current_severity=RuleSeverityLevel.WARNING,
			highest_historical_severity=RuleSeverityLevel.ERROR,
			last_check_executed_at='2007-10-14T16:42:42Z',
			executed_checks=8,
			valid_results=3,
			warnings=5,
			errors=0,
			fatals=0,
			execution_errors=0,
			data_quality_kpi=100.0,
			checks={
				'table_sample_check_1': CheckCurrentDataQualityStatusModel(
					current_severity=CheckResultStatus.WARNING,
					highest_historical_severity=RuleSeverityLevel.ERROR,
					last_executed_at='2007-10-14T14:13:42Z',
					check_type=CheckType.PROFILING,
					category='sample_category',
					quality_dimension='sample_quality_dimension',
					executed_checks=0,
					valid_results=0,
					warnings=1,
					errors=1,
					fatals=0,
					execution_errors=0
				),
				'table_sample_check_2': CheckCurrentDataQualityStatusModel(
					current_severity=CheckResultStatus.VALID,
					highest_historical_severity=RuleSeverityLevel.ERROR,
					last_executed_at='2007-10-14T14:39:42Z',
					check_type=CheckType.PROFILING,
					category='sample_category',
					quality_dimension='sample_quality_dimension',
					executed_checks=0,
					valid_results=0,
					warnings=1,
					errors=1,
					fatals=0,
					execution_errors=0
				)
			},
			columns={
				'sample_column_1': ColumnCurrentDataQualityStatusModel(
					current_severity=RuleSeverityLevel.WARNING,
					highest_historical_severity=RuleSeverityLevel.ERROR,
					last_check_executed_at='2007-10-14T16:42:42Z',
					executed_checks=3,
					valid_results=1,
					warnings=2,
					errors=0,
					fatals=0,
					execution_errors=0,
					checks={
						'sample_check_1': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.WARNING,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T14:13:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						),
						'sample_check_2': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.VALID,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T14:45:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						),
						'sample_check_3': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.WARNING,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T16:42:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						)
					},
					dimensions={
					
					}
				),
				'sample_column_2': ColumnCurrentDataQualityStatusModel(
					current_severity=RuleSeverityLevel.WARNING,
					highest_historical_severity=RuleSeverityLevel.ERROR,
					last_check_executed_at='2007-10-14T16:42:42Z',
					executed_checks=3,
					valid_results=1,
					warnings=2,
					errors=0,
					fatals=0,
					execution_errors=0,
					checks={
						'sample_check_1': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.WARNING,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T14:13:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						),
						'sample_check_2': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.VALID,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T14:45:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						),
						'sample_check_3': CheckCurrentDataQualityStatusModel(
							current_severity=CheckResultStatus.WARNING,
							highest_historical_severity=RuleSeverityLevel.ERROR,
							last_executed_at='2007-10-14T16:42:42Z',
							check_type=CheckType.PROFILING,
							category='sample_category',
							quality_dimension='sample_quality_dimension',
							executed_checks=0,
							valid_results=0,
							warnings=1,
							errors=1,
							fatals=0,
							execution_errors=0
						)
					},
					dimensions={
					
					}
				)
			},
			dimensions={
			
			},
			table_exist=True
		)
        ```
    
    
    



___
## get_table_issues_histogram
Generates a histograms of data quality issues for each day on a table, returning the number of data quality issues on that day. The other histograms are by a column name and by a check name.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_table_issues_histogram.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/histogram
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`issue_histogram_model`](../models/common.md#issuehistogrammodel)</span>||*[IssueHistogramModel](../models/common.md#issuehistogrammodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`executed_since`</span>|Optional date filter to find issues returned since that date. When missing, loads issues executed during the current and previous months|*string*| |
|<span class="no-wrap-code">`filter`</span>|Optional full text search filter that supports *prefix, suffix* and nest*ed filter expressions|*string*| |
|<span class="no-wrap-code">`days`</span>|Optional filter for a number of recent days to read the related issues|*long*| |
|<span class="no-wrap-code">`date`</span>|Optional date filter to select one day|*string*| |
|<span class="no-wrap-code">`column`</span>|Optional column name filter|*string*| |
|<span class="no-wrap-code">`check`</span>|Optional check name filter|*string*| |
|<span class="no-wrap-code">[`check_type`](../models/common.md#checktype)</span>|Optional check type filter, when not provided, returns a combined histogram of monitoring and partition checks|*[CheckType](../models/common.md#checktype)*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/histogram^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "hasProfilingIssues" : false,
		  "hasDailyMonitoringIssues" : false,
		  "hasMonthlyMonitoringIssues" : false,
		  "hasDailyPartitionedIssues" : false,
		  "hasMonthlyPartitionedIssues" : false,
		  "days" : { },
		  "columns" : { },
		  "checks" : { }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_issues_histogram
	from dqops.client.models import CheckType
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_issues_histogram.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        IssueHistogramModel(
			has_profiling_issues=False,
			has_daily_monitoring_issues=False,
			has_monthly_monitoring_issues=False,
			has_daily_partitioned_issues=False,
			has_monthly_partitioned_issues=False,
			days={
			
			},
			columns={
			
			},
			checks={
			
			}
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_issues_histogram
	from dqops.client.models import CheckType
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_issues_histogram.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        IssueHistogramModel(
			has_profiling_issues=False,
			has_daily_monitoring_issues=False,
			has_monthly_monitoring_issues=False,
			has_daily_partitioned_issues=False,
			has_monthly_partitioned_issues=False,
			days={
			
			},
			columns={
			
			},
			checks={
			
			}
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_issues_histogram
	from dqops.client.models import CheckType
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_issues_histogram.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        IssueHistogramModel(
			has_profiling_issues=False,
			has_daily_monitoring_issues=False,
			has_monthly_monitoring_issues=False,
			has_daily_partitioned_issues=False,
			has_monthly_partitioned_issues=False,
			days={
			
			},
			columns={
			
			},
			checks={
			
			}
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_issues_histogram
	from dqops.client.models import CheckType
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_issues_histogram.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        IssueHistogramModel(
			has_profiling_issues=False,
			has_daily_monitoring_issues=False,
			has_monthly_monitoring_issues=False,
			has_daily_partitioned_issues=False,
			has_monthly_partitioned_issues=False,
			days={
			
			},
			columns={
			
			},
			checks={
			
			}
		)
        ```
    
    
    



___
## get_table_monitoring_checks_results
Returns the complete results of the most recent table level monitoring executions for the monitoring at a requested time scale

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_table_monitoring_checks_results.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/results
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_results_list_model`</span>||*List[[CheckResultsListModel](../models/check_results.md#checkresultslistmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`data_group`</span>|Data group|*string*| |
|<span class="no-wrap-code">`month_start`</span>|Month start boundary|*string*| |
|<span class="no-wrap-code">`month_end`</span>|Month end boundary|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |
|<span class="no-wrap-code">`category`</span>|Check category name|*string*| |
|<span class="no-wrap-code">`table_comparison`</span>|Table comparison name|*string*| |
|<span class="no-wrap-code">[`load_mode`](../models/check_results.md#checkresultsdetailedloadmode)</span>|Results load mode|*[CheckResultsDetailedLoadMode](../models/check_results.md#checkresultsdetailedloadmode)*| |
|<span class="no-wrap-code">`max_results_per_check`</span>|Maximum number of results per check, the default is 100|*long*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/daily/results^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "dataGroup" : "sample_data_grouping",
		  "checkResultEntries" : [ {
		    "id" : "3854372",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 100.0,
		    "expectedValue" : 110.0,
		    "warningLowerBound" : 105.0,
		    "warningUpperBound" : 115.0,
		    "errorLowerBound" : 95.0,
		    "errorUpperBound" : 125.0,
		    "fatalLowerBound" : 85.0,
		    "fatalUpperBound" : 135.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T14:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T14:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "-8597741925434587255",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 110.0,
		    "expectedValue" : 120.0,
		    "warningLowerBound" : 115.0,
		    "warningUpperBound" : 125.0,
		    "errorLowerBound" : 105.0,
		    "errorUpperBound" : 135.0,
		    "fatalLowerBound" : 95.0,
		    "fatalUpperBound" : 145.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T15:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T15:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "8520797026624525526",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 120.0,
		    "expectedValue" : 130.0,
		    "warningLowerBound" : 125.0,
		    "warningUpperBound" : 135.0,
		    "errorLowerBound" : 115.0,
		    "errorUpperBound" : 145.0,
		    "fatalLowerBound" : 105.0,
		    "fatalUpperBound" : 155.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T16:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T16:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "879763789071004098",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 130.0,
		    "expectedValue" : 140.0,
		    "warningLowerBound" : 135.0,
		    "warningUpperBound" : 145.0,
		    "errorLowerBound" : 125.0,
		    "errorUpperBound" : 155.0,
		    "fatalLowerBound" : 115.0,
		    "fatalUpperBound" : 165.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T17:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T17:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  } ]
		}, {
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "dataGroup" : "sample_data_grouping",
		  "checkResultEntries" : [ {
		    "id" : "3854372",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 100.0,
		    "expectedValue" : 110.0,
		    "warningLowerBound" : 105.0,
		    "warningUpperBound" : 115.0,
		    "errorLowerBound" : 95.0,
		    "errorUpperBound" : 125.0,
		    "fatalLowerBound" : 85.0,
		    "fatalUpperBound" : 135.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T14:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T14:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "-8597741925434587255",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 110.0,
		    "expectedValue" : 120.0,
		    "warningLowerBound" : 115.0,
		    "warningUpperBound" : 125.0,
		    "errorLowerBound" : 105.0,
		    "errorUpperBound" : 135.0,
		    "fatalLowerBound" : 95.0,
		    "fatalUpperBound" : 145.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T15:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T15:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "8520797026624525526",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 120.0,
		    "expectedValue" : 130.0,
		    "warningLowerBound" : 125.0,
		    "warningUpperBound" : 135.0,
		    "errorLowerBound" : 115.0,
		    "errorUpperBound" : 145.0,
		    "fatalLowerBound" : 105.0,
		    "fatalUpperBound" : 155.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T16:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T16:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "879763789071004098",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 130.0,
		    "expectedValue" : 140.0,
		    "warningLowerBound" : 135.0,
		    "warningUpperBound" : 145.0,
		    "errorLowerBound" : 125.0,
		    "errorUpperBound" : 155.0,
		    "fatalLowerBound" : 115.0,
		    "fatalUpperBound" : 165.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T17:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T17:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  } ]
		}, {
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "dataGroup" : "sample_data_grouping",
		  "checkResultEntries" : [ {
		    "id" : "3854372",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 100.0,
		    "expectedValue" : 110.0,
		    "warningLowerBound" : 105.0,
		    "warningUpperBound" : 115.0,
		    "errorLowerBound" : 95.0,
		    "errorUpperBound" : 125.0,
		    "fatalLowerBound" : 85.0,
		    "fatalUpperBound" : 135.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T14:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T14:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "-8597741925434587255",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 110.0,
		    "expectedValue" : 120.0,
		    "warningLowerBound" : 115.0,
		    "warningUpperBound" : 125.0,
		    "errorLowerBound" : 105.0,
		    "errorUpperBound" : 135.0,
		    "fatalLowerBound" : 95.0,
		    "fatalUpperBound" : 145.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T15:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T15:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "8520797026624525526",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 120.0,
		    "expectedValue" : 130.0,
		    "warningLowerBound" : 125.0,
		    "warningUpperBound" : 135.0,
		    "errorLowerBound" : 115.0,
		    "errorUpperBound" : 145.0,
		    "fatalLowerBound" : 105.0,
		    "fatalUpperBound" : 155.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T16:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T16:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "879763789071004098",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 130.0,
		    "expectedValue" : 140.0,
		    "warningLowerBound" : 135.0,
		    "warningUpperBound" : 145.0,
		    "errorLowerBound" : 125.0,
		    "errorUpperBound" : 155.0,
		    "fatalLowerBound" : 115.0,
		    "fatalUpperBound" : 165.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T17:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T17:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  } ]
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_monitoring_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode, \
	                                CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_monitoring_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_monitoring_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode, \
	                                CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_monitoring_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_monitoring_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode, \
	                                CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_monitoring_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_monitoring_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode, \
	                                CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_monitoring_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    



___
## get_table_partitioned_checks_results
Returns a complete view of the recent table level partitioned checks executions for a requested time scale

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_table_partitioned_checks_results.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/results
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_results_list_model`</span>||*List[[CheckResultsListModel](../models/check_results.md#checkresultslistmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`data_group`</span>|Data group|*string*| |
|<span class="no-wrap-code">`month_start`</span>|Month start boundary|*string*| |
|<span class="no-wrap-code">`month_end`</span>|Month end boundary|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |
|<span class="no-wrap-code">`category`</span>|Check category name|*string*| |
|<span class="no-wrap-code">`table_comparison`</span>|Table comparison name|*string*| |
|<span class="no-wrap-code">[`load_mode`](../models/check_results.md#checkresultsdetailedloadmode)</span>|Results load mode|*[CheckResultsDetailedLoadMode](../models/check_results.md#checkresultsdetailedloadmode)*| |
|<span class="no-wrap-code">`max_results_per_check`</span>|Maximum number of results per check, the default is 100|*long*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/daily/results^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "dataGroup" : "sample_data_grouping",
		  "checkResultEntries" : [ {
		    "id" : "3854372",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 100.0,
		    "expectedValue" : 110.0,
		    "warningLowerBound" : 105.0,
		    "warningUpperBound" : 115.0,
		    "errorLowerBound" : 95.0,
		    "errorUpperBound" : 125.0,
		    "fatalLowerBound" : 85.0,
		    "fatalUpperBound" : 135.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T14:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T14:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "-8597741925434587255",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 110.0,
		    "expectedValue" : 120.0,
		    "warningLowerBound" : 115.0,
		    "warningUpperBound" : 125.0,
		    "errorLowerBound" : 105.0,
		    "errorUpperBound" : 135.0,
		    "fatalLowerBound" : 95.0,
		    "fatalUpperBound" : 145.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T15:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T15:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "8520797026624525526",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 120.0,
		    "expectedValue" : 130.0,
		    "warningLowerBound" : 125.0,
		    "warningUpperBound" : 135.0,
		    "errorLowerBound" : 115.0,
		    "errorUpperBound" : 145.0,
		    "fatalLowerBound" : 105.0,
		    "fatalUpperBound" : 155.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T16:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T16:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "879763789071004098",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 130.0,
		    "expectedValue" : 140.0,
		    "warningLowerBound" : 135.0,
		    "warningUpperBound" : 145.0,
		    "errorLowerBound" : 125.0,
		    "errorUpperBound" : 155.0,
		    "fatalLowerBound" : 115.0,
		    "fatalUpperBound" : 165.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T17:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T17:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  } ]
		}, {
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "dataGroup" : "sample_data_grouping",
		  "checkResultEntries" : [ {
		    "id" : "3854372",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 100.0,
		    "expectedValue" : 110.0,
		    "warningLowerBound" : 105.0,
		    "warningUpperBound" : 115.0,
		    "errorLowerBound" : 95.0,
		    "errorUpperBound" : 125.0,
		    "fatalLowerBound" : 85.0,
		    "fatalUpperBound" : 135.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T14:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T14:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "-8597741925434587255",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 110.0,
		    "expectedValue" : 120.0,
		    "warningLowerBound" : 115.0,
		    "warningUpperBound" : 125.0,
		    "errorLowerBound" : 105.0,
		    "errorUpperBound" : 135.0,
		    "fatalLowerBound" : 95.0,
		    "fatalUpperBound" : 145.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T15:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T15:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "8520797026624525526",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 120.0,
		    "expectedValue" : 130.0,
		    "warningLowerBound" : 125.0,
		    "warningUpperBound" : 135.0,
		    "errorLowerBound" : 115.0,
		    "errorUpperBound" : 145.0,
		    "fatalLowerBound" : 105.0,
		    "fatalUpperBound" : 155.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T16:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T16:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "879763789071004098",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 130.0,
		    "expectedValue" : 140.0,
		    "warningLowerBound" : 135.0,
		    "warningUpperBound" : 145.0,
		    "errorLowerBound" : 125.0,
		    "errorUpperBound" : 155.0,
		    "fatalLowerBound" : 115.0,
		    "fatalUpperBound" : 165.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T17:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T17:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  } ]
		}, {
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "dataGroup" : "sample_data_grouping",
		  "checkResultEntries" : [ {
		    "id" : "3854372",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 100.0,
		    "expectedValue" : 110.0,
		    "warningLowerBound" : 105.0,
		    "warningUpperBound" : 115.0,
		    "errorLowerBound" : 95.0,
		    "errorUpperBound" : 125.0,
		    "fatalLowerBound" : 85.0,
		    "fatalUpperBound" : 135.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T14:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T14:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "-8597741925434587255",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 110.0,
		    "expectedValue" : 120.0,
		    "warningLowerBound" : 115.0,
		    "warningUpperBound" : 125.0,
		    "errorLowerBound" : 105.0,
		    "errorUpperBound" : 135.0,
		    "fatalLowerBound" : 95.0,
		    "fatalUpperBound" : 145.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T15:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T15:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "8520797026624525526",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 120.0,
		    "expectedValue" : 130.0,
		    "warningLowerBound" : 125.0,
		    "warningUpperBound" : 135.0,
		    "errorLowerBound" : 115.0,
		    "errorUpperBound" : 145.0,
		    "fatalLowerBound" : 105.0,
		    "fatalUpperBound" : 155.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T16:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T16:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "879763789071004098",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 130.0,
		    "expectedValue" : 140.0,
		    "warningLowerBound" : 135.0,
		    "warningUpperBound" : 145.0,
		    "errorLowerBound" : 125.0,
		    "errorUpperBound" : 155.0,
		    "fatalLowerBound" : 115.0,
		    "fatalUpperBound" : 165.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T17:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T17:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  } ]
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_partitioned_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode, \
	                                CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioned_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_partitioned_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode, \
	                                CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioned_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_partitioned_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode, \
	                                CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioned_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_partitioned_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode, \
	                                CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioned_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    



___
## get_table_profiling_checks_results
Returns the complete results of the most recent check executions for all table level data quality profiling checks on a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_table_profiling_checks_results.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/results
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_results_list_model`</span>||*List[[CheckResultsListModel](../models/check_results.md#checkresultslistmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`data_group`</span>|Data group|*string*| |
|<span class="no-wrap-code">`month_start`</span>|Month start boundary|*string*| |
|<span class="no-wrap-code">`month_end`</span>|Month end boundary|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |
|<span class="no-wrap-code">`category`</span>|Check category name|*string*| |
|<span class="no-wrap-code">`table_comparison`</span>|Table comparison name|*string*| |
|<span class="no-wrap-code">[`load_mode`](../models/check_results.md#checkresultsdetailedloadmode)</span>|Results load mode|*[CheckResultsDetailedLoadMode](../models/check_results.md#checkresultsdetailedloadmode)*| |
|<span class="no-wrap-code">`max_results_per_check`</span>|Maximum number of results per check, the default is 100|*long*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling/results^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "dataGroup" : "sample_data_grouping",
		  "checkResultEntries" : [ {
		    "id" : "3854372",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 100.0,
		    "expectedValue" : 110.0,
		    "warningLowerBound" : 105.0,
		    "warningUpperBound" : 115.0,
		    "errorLowerBound" : 95.0,
		    "errorUpperBound" : 125.0,
		    "fatalLowerBound" : 85.0,
		    "fatalUpperBound" : 135.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T14:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T14:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "-8597741925434587255",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 110.0,
		    "expectedValue" : 120.0,
		    "warningLowerBound" : 115.0,
		    "warningUpperBound" : 125.0,
		    "errorLowerBound" : 105.0,
		    "errorUpperBound" : 135.0,
		    "fatalLowerBound" : 95.0,
		    "fatalUpperBound" : 145.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T15:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T15:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "8520797026624525526",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 120.0,
		    "expectedValue" : 130.0,
		    "warningLowerBound" : 125.0,
		    "warningUpperBound" : 135.0,
		    "errorLowerBound" : 115.0,
		    "errorUpperBound" : 145.0,
		    "fatalLowerBound" : 105.0,
		    "fatalUpperBound" : 155.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T16:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T16:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "879763789071004098",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 130.0,
		    "expectedValue" : 140.0,
		    "warningLowerBound" : 135.0,
		    "warningUpperBound" : 145.0,
		    "errorLowerBound" : 125.0,
		    "errorUpperBound" : 155.0,
		    "fatalLowerBound" : 115.0,
		    "fatalUpperBound" : 165.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T17:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T17:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  } ]
		}, {
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "dataGroup" : "sample_data_grouping",
		  "checkResultEntries" : [ {
		    "id" : "3854372",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 100.0,
		    "expectedValue" : 110.0,
		    "warningLowerBound" : 105.0,
		    "warningUpperBound" : 115.0,
		    "errorLowerBound" : 95.0,
		    "errorUpperBound" : 125.0,
		    "fatalLowerBound" : 85.0,
		    "fatalUpperBound" : 135.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T14:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T14:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "-8597741925434587255",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 110.0,
		    "expectedValue" : 120.0,
		    "warningLowerBound" : 115.0,
		    "warningUpperBound" : 125.0,
		    "errorLowerBound" : 105.0,
		    "errorUpperBound" : 135.0,
		    "fatalLowerBound" : 95.0,
		    "fatalUpperBound" : 145.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T15:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T15:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "8520797026624525526",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 120.0,
		    "expectedValue" : 130.0,
		    "warningLowerBound" : 125.0,
		    "warningUpperBound" : 135.0,
		    "errorLowerBound" : 115.0,
		    "errorUpperBound" : 145.0,
		    "fatalLowerBound" : 105.0,
		    "fatalUpperBound" : 155.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T16:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T16:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "879763789071004098",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 130.0,
		    "expectedValue" : 140.0,
		    "warningLowerBound" : 135.0,
		    "warningUpperBound" : 145.0,
		    "errorLowerBound" : 125.0,
		    "errorUpperBound" : 155.0,
		    "fatalLowerBound" : 115.0,
		    "fatalUpperBound" : 165.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T17:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T17:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  } ]
		}, {
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "dataGroup" : "sample_data_grouping",
		  "checkResultEntries" : [ {
		    "id" : "3854372",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 100.0,
		    "expectedValue" : 110.0,
		    "warningLowerBound" : 105.0,
		    "warningUpperBound" : 115.0,
		    "errorLowerBound" : 95.0,
		    "errorUpperBound" : 125.0,
		    "fatalLowerBound" : 85.0,
		    "fatalUpperBound" : 135.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T14:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T14:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "-8597741925434587255",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 110.0,
		    "expectedValue" : 120.0,
		    "warningLowerBound" : 115.0,
		    "warningUpperBound" : 125.0,
		    "errorLowerBound" : 105.0,
		    "errorUpperBound" : 135.0,
		    "fatalLowerBound" : 95.0,
		    "fatalUpperBound" : 145.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T15:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T15:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "8520797026624525526",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 120.0,
		    "expectedValue" : 130.0,
		    "warningLowerBound" : 125.0,
		    "warningUpperBound" : 135.0,
		    "errorLowerBound" : 115.0,
		    "errorUpperBound" : 145.0,
		    "fatalLowerBound" : 105.0,
		    "fatalUpperBound" : 155.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T16:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T16:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  }, {
		    "id" : "879763789071004098",
		    "checkHash" : 0,
		    "checkCategory" : "sample_category",
		    "checkName" : "sample_check",
		    "checkDisplayName" : "sample_target/sample_category/sample_check",
		    "checkType" : "profiling",
		    "actualValue" : 130.0,
		    "expectedValue" : 140.0,
		    "warningLowerBound" : 135.0,
		    "warningUpperBound" : 145.0,
		    "errorLowerBound" : 125.0,
		    "errorUpperBound" : 155.0,
		    "fatalLowerBound" : 115.0,
		    "fatalUpperBound" : 165.0,
		    "severity" : 2,
		    "columnName" : "sample_column",
		    "dataGroup" : "sample_data_grouping",
		    "durationMs" : 142,
		    "executedAt" : "2023-10-01T17:00:00Z",
		    "timeGradient" : "hour",
		    "timePeriod" : "2023-10-01T17:00:00",
		    "includeInKpi" : true,
		    "includeInSla" : true,
		    "provider" : "BigQuery",
		    "qualityDimension" : "sample_quality_dimension",
		    "sensorName" : "sample_target/sample_category/table/volume/row_count"
		  } ]
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_profiling_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_profiling_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_profiling_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_profiling_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_profiling_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_profiling_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_profiling_checks_results
	from dqops.client.models import CheckResultsDetailedLoadMode
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_profiling_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			),
			CheckResultsListModel(
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				data_group='sample_data_grouping',
				check_result_entries=[
					CheckResultEntryModel(
						id='3854372',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=100.0,
						expected_value=110.0,
						warning_lower_bound=105.0,
						warning_upper_bound=115.0,
						error_lower_bound=95.0,
						error_upper_bound=125.0,
						fatal_lower_bound=85.0,
						fatal_upper_bound=135.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T14:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T14:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='-8597741925434587255',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=110.0,
						expected_value=120.0,
						warning_lower_bound=115.0,
						warning_upper_bound=125.0,
						error_lower_bound=105.0,
						error_upper_bound=135.0,
						fatal_lower_bound=95.0,
						fatal_upper_bound=145.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T15:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T15:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='8520797026624525526',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=120.0,
						expected_value=130.0,
						warning_lower_bound=125.0,
						warning_upper_bound=135.0,
						error_lower_bound=115.0,
						error_upper_bound=145.0,
						fatal_lower_bound=105.0,
						fatal_upper_bound=155.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T16:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T16:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					),
					CheckResultEntryModel(
						id='879763789071004098',
						check_hash=0,
						check_category='sample_category',
						check_name='sample_check',
						check_display_name='sample_target/sample_category/sample_check',
						check_type=CheckType.PROFILING,
						actual_value=130.0,
						expected_value=140.0,
						warning_lower_bound=135.0,
						warning_upper_bound=145.0,
						error_lower_bound=125.0,
						error_upper_bound=155.0,
						fatal_lower_bound=115.0,
						fatal_upper_bound=165.0,
						severity=2,
						column_name='sample_column',
						data_group='sample_data_grouping',
						duration_ms=142,
						executed_at='2023-10-01T17:00:00Z',
						time_gradient=TimePeriodGradient.HOUR,
						time_period=Some date/time value: [2023-10-01T17:00],
						include_in_kpi=True,
						include_in_sla=True,
						provider='BigQuery',
						quality_dimension='sample_quality_dimension',
						sensor_name='sample_target/sample_category/table/volume/row_count'
					)
				]
			)
		]
        ```
    
    
    



