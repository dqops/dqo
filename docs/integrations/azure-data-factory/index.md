---
title: How to monitor data observability with Azure Data Factory
---
# How to monitor data observability with Azure Data Factory
Read this guide to learn how to use DQOps API from Azure Data Factory to activate monitoring.

## Overview

You can easily utilize Azure Data Factory to call DQOps API for:

- running data quality checks,
- collect basic statistics about tables,
- import the table schema to DQOps,
- import data quality tables, 
 
and dozens of others.

for running data quality checks.
The guide will use the data stored in Azure Blob Storage.


## Prerequisite

Before you integrate DQOps into Azure Data Factory pipelines you need to configure a data source in DOQps.
You can easily connect to the data source located on [Azure](../../data-sources/azure.md), [Databricks](../../data-sources/databricks.md) or anywhere else. 
[DQOps supports all commonly used databases, check out the complete list here](../../data-sources/index.md).


# Azure Data Factory integration

Accessing the DQOps API involves using a personal API key to authenticate requests. 

## Getting the personal API Key

You can easily get your personal, user-unique API Key through the DQOps Web UI.

1. Open your DQOps instance's Web UI in your browser.
2. Click on your portrait in the top-right corner.
3. Click on the "Generate API Key" button.

![User portrait menu](../../images/api-key.png "User portrait menu"){ loading=lazy } &nbsp;&nbsp;&nbsp;&nbsp; ![Generate API Key](../../images/generated-api-key.png "Generate API Key"){ loading=lazy }

You can copy the generated API Key and use it in Azure Data Factory.


## Run checks

In Azure Data Factory open Orchestrate to create a new pipeline.

Put **Web** activity.

// screen: add-web-activity.png

Click on the Web activity and open the **Settings** tab.

Fill the URL with the link that calls run checks endpoint. It is crucial to set the wait parameter which makes the DQOps backend wait for finishing the execution. Calling the run checks endpoint without the wait parameter will run checks not allowing to see the results in Azure Data Factory. 

https://&lt;your_DQOps_instance&gt;/api/jobs/runchecks?wait=true

Replace **&lt;your_DQOps_instance&gt;** with your DQOps instance address.

Set method as **POST**.

The body of the request contain the filtering configuration that points DQOps which checks should be run.

The below example run all profiling checks that are enabled (turned on) on the table "files.readouts" from the "azure-storage-parquet" connection.
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

// screen: activity-settings.png

Save the pipeline by clicking on **Publish all** button.

Now the pipeline can be executed. Navigate to **Add trigger** and select the **Trigger now**

// screen: trigger-now.png

In a moment you should receive a notification about the execution completion of the pipeline. 

Click the **view pipeline run** link.

// screen: view-run-pipeline.png

Select the output of the Web activity.

// screen: check-run-details.png

The DQOps API returned data available in the result object of the whole JSON.

// screen: result.png

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
	"billingReference": {
		"activityType": "ExternalActivity",
		"billableDuration": [
			{
				"meterType": "AzureIR",
				"duration": 0.016666666666666666,
				"unit": "Hours"
			}
		]
	}
}
```

You can see running checks resulted with 47 execution warnings. The data has a quality issue and needs to be reviewed.

The execution of the run checks API call can be incorporated into existing pipelines to make branches during pipeline execution.

## Integrate run checks action with your pipeline

You can integrate run checks that can disallow to copy new data to the corrupted data source.

To achieve this you need to base on the returned JSON from DQOps.

The example first calls the DQOps to fetch the status of your data. Then the status is verified. 
If it succeeded the data is copied. Otherwise the pipeline is marked as failed returning the code of the issue.

// screen: run-checks-pipeline.png

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
The code 4 will be set of another issues such as sensors execution errors or user misconfigurations in DQOps.

// screen: if-statement-configuration.png

If no issues are present, the copy data activity is executed. 

Otherwise you will see the failure on the Monitor page with the error message.

// screen: pipeline-status.png

The details of the error provides you the error code as well.

// screen: error-details.png

// screen: error-details-json.png


Instead of Fail activity you can also run another REST API that will inform about data quality issues as fast as is detected.

## Collect basic statistics




## Import the table schema to DQOps




## Import data quality tables



