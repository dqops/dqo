---
title: How to activate data observability with Azure Data Factory
---
# How to activate data observability with Azure Data Factory
Read this guide to learn how to use DQOps API from Azure Data Factory to activate monitoring.

## Overview

You can easily utilize Azure Data Factory to call DQOps API for:

- running data quality checks,
- collect statistics about tables,
- import the table schema to DQOps,
- import data quality tables, 
 
and dozens of others.

for running data quality checks.
The guide will use the data stored in Azure Blob Storage.


## Prerequisite

Before you integrate DQOps into Azure Data Factory pipelines you need to configure a data source in DOQps.
You can easily connect to the data source located on [Azure](../../data-sources/azure.md), [Databricks](../../data-sources/databricks.md) or anywhere else. 
[DQOps supports all commonly used databases, check out the complete list here](../../data-sources/index.md).


## Azure Data Factory integration

Accessing the DQOps API involves using a personal API key to authenticate requests. 

### Getting the personal API Key

You can easily get your personal, user-unique API Key through the DQOps Web UI.

1. Open your DQOps instance's Web UI in your browser.
2. Click on your portrait in the top-right corner.
3. Click on the "Generate API Key" button.

![User portrait menu](../../images/api-key.png "User portrait menu"){ loading=lazy } &nbsp;&nbsp;&nbsp;&nbsp; ![Generate API Key](../../images/generated-api-key.png "Generate API Key"){ loading=lazy }

You can copy the generated API Key and use it in Azure Data Factory.


### Run checks

Run checks job in DQOps executes checks depending on your selection. 
You can specify to only those checks that are attached to the specific table on a connection, with a specific names for label, quality dimention, type of checks and much more.

To create the run checks job in DQOps from Azure Data Factory create a new pipeline and put **Web** activity.

![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/add-web-activity.png){ loading=lazy; }

Click on the Web activity and open the **Settings** tab.

Fill the URL with the link that calls run checks endpoint. It is crucial to set the wait parameter which makes the DQOps backend wait for finishing the execution. Calling the run checks endpoint without the wait parameter will run checks not allowing to see the results in Azure Data Factory. 

```
https://<your_DQOps_instance>/api/jobs/runchecks?wait=true
```

Replace **&lt;your_DQOps_instance&gt;** with your DQOps instance address.

Set method as **POST**.

The body of the request contain the filtering configuration that points DQOps which checks should be run.

The below example runs all profiling checks that are enabled (turned on) on the table "files.readouts" from the "azure-storage-parquet" connection.
It also collects error samples on checks that fail.

```text
{
    "check_search_filters": {
        "connection": "azure-storage-parquet",
        "fullTableName": "files.readouts",
        "enabled": true, 
        "checkType": "profiling"
    },
    "collect_error_samples": true
}
```

The **Authentication combo box** leave as **None**. 

The last thing to fill are **Headers**.

The names **accept** and **Content-Type** should contain value as **applicaiton/json**.

The name **Authorization** contain the token starting with **Bearer** and space character. The rest of the value is your personal API Key.

![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/activity-settings.png){ loading=lazy; }

Save the pipeline by clicking on **Publish all** button.

Now the pipeline can be executed. Navigate to **Add trigger** and select the **Trigger now**

![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/trigger-now.png){ loading=lazy; }

In a moment you should receive a notification about the execution completion of the pipeline. 

Click the **view pipeline run** link.

![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/view-run-pipeline.png){ loading=lazy; }

Select the output of the Web activity.

![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/check-run-details.png){ loading=lazy; }

The DQOps API returned data available in the JSON object.

![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/result.png){ loading=lazy; }

```json5 hl_lines="6-14"
{
	"jobId": {
		"jobId": 1720691395000000,
		"createdAt": "2024-07-11T09:49:55.017423827Z"
	},
	"result": {
		"highest_severity": "warning",
		"executed_checks": 143,
		"valid_results": 143,
		"warnings": 47,
		"errors": 0,
		"fatals": 0,
		"execution_errors": 0
	},
	"status": "finished",
    // ...
}
```

You can see running checks resulted with 47 execution warnings. The data has a quality issue and needs to be reviewed.

The execution of the run checks API call can be incorporated into existing pipelines to make branches during pipeline execution.

### Integrate run checks action with your pipeline

You can integrate run checks that can disallow to copy new data to the corrupted data source.

To achieve this you need to base on the returned JSON from DQOps.

The example first calls the DQOps to fetch the status of your data. Then the status is verified. 
If it succeeded the data is copied. Otherwise, the pipeline is marked as failed returning the code of the issue.

![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/run-checks-pipeline.png){ loading=lazy; }

To create this pipeline add the **If condition** activity to the previously configured Web activity (now named as "DQOps run checks") 
and fill the **Expression** field with:

```text
@contains(activity('DQOps run checks').output.result.highest_severity, 'valid')
```

The expression verifies that the highest severity from the returned JSON is "valid".

The valid status means that no issues have been detected. If any will, the status become the severity of the issue.
You can see there one of the following: warning, error, fatal.

For marking the issue, add the **Fail** activity in False section of the If condition activity.

For making Fail activity more informative, the returned data from DQOps API can be used.

Fill **Fail message** (simply as e.g. "Data quality issue detected") and **Error code** (the code below).

```text
@if(
    contains(activity('DQOps run checks').output.result.highest_severity, 'warning'), 1,
    if(
        contains(activity('DQOps run checks').output.result.highest_severity, 'error'), 2,
        if(
            contains(activity('DQOps run checks').output.result.highest_severity, 'fatal'), 3, 4
        )
    )
)
```

The Error code transcode the severities to the integer numbers marking severities as follows: warning - 1, error - 2, fatal - 3.
The code 4 stands for another issues such as sensors execution errors or user misconfigurations in DQOps.

![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/if-statement-configuration.png){ loading=lazy; }

If no issues are present, the copy data activity is executed. 

Otherwise, you will see the failure on the Monitor page with the error message.

![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/pipeline-status.png){ loading=lazy; }

The details of the error provides you the error code as well.

![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/error-details.png){ loading=lazy; }
![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/error-details-json.png){ loading=lazy; }

Instead of Fail activity you can also run another REST API that will inform about data quality issues as fast as is detected.

### Collect statistics

Collect statistics job in DQOps provides the summary information about your tables and columns.
This information is valuable in deciding which data quality checks and threshold levels should be set to monitor data quality.
You can specify to run this job only to the specific table on a connection.

To create the collect statistics job in DQOps from Azure Data Factory create a new pipeline and put **Web** activity.

![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/add-web-activity.png){ loading=lazy; }

Click on the Web activity and open the **Settings** tab.

Fill the URL with the link that calls collect statistics endpoint. It is crucial to set the wait parameter which makes the DQOps backend wait for finishing the execution. 
Calling the collect statistics endpoint without the wait parameter will not allow to see the results of the statistics in Azure Data Factory.

```
https://<your_DQOps_instance>/api/jobs/collectstatistics/table?wait=true
```

Replace **&lt;your_DQOps_instance&gt;** with your DQOps instance address.

Set method as **POST**.

The body of the request contain the name of the table on which statistics will be collected.

The below example collects statistics for the table "files.readouts" from the "azure-storage-parquet" connection.

```text
{
    "connection": "azure-storage-parquet",
    "fullTableName": "files.readouts",
    "enabled": true
}
```

The **Authentication combo box** leave as **None**.

The last thing to fill are **Headers**.

The names **accept** and **Content-Type** should contain value as **applicaiton/json**.

The name **Authorization** contain the token starting with **Bearer** and space character. The rest of the value is your personal API Key.

![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/activity-settings.png){ loading=lazy; }

Save the pipeline by clicking on **Publish all** button.

Now the pipeline can be executed. Navigate to **Add trigger** and select the **Trigger now**

![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/trigger-now.png){ loading=lazy; }

In a moment you should receive a notification about the execution completion of the pipeline.

Click the **view pipeline run** link.

![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/view-run-pipeline.png){ loading=lazy; }

Select the output of the Web activity.

![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/check-run-details.png){ loading=lazy; }

The DQOps API returned data available in the JSON object.

```json5 hl_lines="6-12"
{
    "jobId": {
        "jobId": 1720701935000000,
        "createdAt": "2024-07-11T12:45:35.687507400Z"
    },
    "result": {
        "executed_statistics_collectors": 43,
        "columns_analyzed": 3,
        "columns_successfully_analyzed": 3,
        "total_collectors_failed": 0,
        "total_collected_results": 140
    },
    "status": "finished",
    // ...
}
```

The statistics have been collected.


### Import the table schema to DQOps




### Import data quality tables



