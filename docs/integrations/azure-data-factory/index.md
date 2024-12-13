---
title: How to Run Data Quality Checks in Azure Data Factory
---
# How to Run Data Quality Checks in Azure Data Factory
Read this guide to learn how to use data quality checks in Azure Data Factory data pipelines, and how to handle data quality issues.

## Overview

By integrating Azure Data Factory with the DQOps API, you can streamline data quality processes and gain deeper insights
into your data. This integration empowers you to:

- Run data quality checks
- Collect comprehensive statistics about tables
- Import table schema to DQOps
- Get table data quality status, 

This guide provides a practical demonstration of running data quality checks on data residing in Azure Blob Storage.


## Prerequisite

Before you integrate DQOps into Azure Data Factory pipelines, you need to configure a data source in DOQps.
You can easily connect to the data source located on [Azure](../../data-sources/azure.md), [Databricks](../../data-sources/databricks.md), or elsewhere. 
DQOps supports all commonly used databases. [You can check out the complete list of supported databases here](../../data-sources/index.md).


## Azure Data Factory integration

To interact with the DQOps API, you will need a unique personal API key. This key acts as your authentication credential.

### Getting the personal API Key

You can easily get your personal, user-unique API Key through the DQOps Web UI.

1. Open your DQOps instance's Web UI in your browser.
2. Click on the user profile icon in the top-right corner of the interface.
3. Click on the **Generate API Key** button to create a new key.
4. Copy the generated API key and use it in your Azure Data Factory pipeline.

![User portrait menu](../../images/api-key.png "User portrait menu"){ loading=lazy } &nbsp;&nbsp;&nbsp;&nbsp; ![Generate API Key](../../images/generated-api-key.png "Generate API Key"){ loading=lazy }


### Run checks

DQOps offers granular control over which checks are executed.
You can specify checks attached to a table on a connection, with specific names for labels, quality dimensions, types of checks, and more.

To create the run checks job in DQOps from Azure Data Factory:

1. Create a new pipeline and add a new **Web** activity.

    ![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/add-web-activity.png){ loading=lazy; }

2. Click on the Web activity and open the **Settings** tab.

    ![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/activity-settings.png){ loading=lazy; }

3. Configure the **Setting** as follows:

    - Fill the **URL** with the link that calls the run checks endpoint. It is crucial to set the wait parameter, which makes the DQOps backend wait for the execution to finish.
         Calling the run checks endpoint without the wait parameter will run checks without allowing you to see the results in Azure Data Factory.

         Construct the URL using the following template and replacing **&lt;your_DQOps_instance&gt;** with your actual DQOps instance address.
         ```
         https://<your_DQOps_instance>/api/jobs/runchecks?wait=true
         ```
      
    - Set the **Method** as **POST**.
    - Specify the checks to be executed in the **Body** of the request. The following example runs all activated profiling checks on the "files.readouts" table from the "azure-storage-parquet"
       connection and collects error samples on failed checks.

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

    - Leave the **Authentication combo box** as **None**.
    - In the **Headers** section, add three variables.
        The name **Authorization** contains the token starting with **Bearer** and space character. The rest of the value is your personal API Key.
        The names **accept** and **Content-Type** should contain value as **application/json**.

4. Save the pipeline by clicking on **Publish all** button.

To initiate the pipeline execution, follow these steps:

1. Navigate to **Add trigger** and select the **Trigger now**

    ![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/trigger-now.png){ loading=lazy; }

    In a moment, you should receive a notification about the completion of the pipeline execution. 

2. Click the **view pipeline run** link to access detailed execution information

    ![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/view-run-pipeline.png){ loading=lazy; }

3. Select the output of the Web activity to retrieve the DQOps API response in JSON format.

    ![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/check-run-details.png){ loading=lazy; }

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

You can see that running checks resulted in 47 execution warnings. The data has data quality issues and requires review.

The run checks API call can be integrated into existing pipelines to create conditional branches during pipeline execution.

### Integrate run checks action with your pipeline

To integrate run checks with your pipeline, you can disallow copying new data to a corrupted data source. To do this, you need to use the returned JSON from DQOps.

First, call DQOps to fetch the status of your data, then verify the status. If it is successful, the data is copied. 
If not, the pipeline is marked as failed, and the code of the issue is returned.

![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/run-checks-pipeline.png){ loading=lazy; }

To create this pipeline, add the **If condition** activity to the previously configured Web activity (now named "DQOps run checks") 
and fill the **Expression** field with:

```text
@contains(activity('DQOps run checks').output.result.highest_severity, 'valid')
```

The expression verifies that the highest severity from the returned JSON is "valid."

The "valid" status means that no issues have been detected. If any issues are detected, the status becomes the severity 
of the issue, such as warning, error, or fatal.

To mark the issue, add the **Fail** activity in the False section of the If condition activity.

To make the Fail activity more informative, utilize the returned data from the DQOps API.

Fill in the **Fail message** (e.g., "Data quality issue detected") and **Error code** (the code below).

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

The Error code corresponds to different severity levels by assigning integer numbers as follows: warning - 1, error - 2, fatal - 3.
The code 4 is used for other issues, such as sensor execution errors or user misconfigurations in DQOps.

![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/if-statement-configuration.png){ loading=lazy; }

If there are no issues, the copy data activity will be executed. Otherwise, you will see a failure on the Monitor page
along with an error message.

![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/pipeline-status.png){ loading=lazy; }

The error details will also provide you with the error code.

![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/error-details.png){ loading=lazy; }
![Adding connection settings](https://dqops.com/docs/images/integrations/azure-data-factory/error-details-json.png){ loading=lazy; }

Instead of the Fail activity, you can also initiate another REST API that will promptly notify about data quality issues as soon as they are detected.

### Example jobs

Here are some example jobs for DQOps REST API that provide a range of endpoints. You can access the details through the 
Swagger API at the following address:

```url
https://**<your_DQOps_instance>**/swagger-ui/#/
```

You need to replace **&lt;your_DQOps_instance&gt;** with your DQOps instance address.


=== "Collect statistics"

    The collect statistics job in DQOps provides summary information about your tables and columns.
    This information is can help in deciding which data quality checks and threshold levels should be set to monitor data quality.
    You can specify to run this job only to the specific table on a connection.

    Here is an example endpoint for collecting statistics for a table:

    ```url
    https://<your_DQOps_instance>/api/jobs/collectstatistics/table?wait=true
    ```

    The **Body** of the request should contain the name of the table for which statistics will be collected.
    
    For example, the following request collects statistics for the table "files.readouts" from the "azure-storage-parquet" connection.
    
    ```json5
    {
        "connection": "azure-storage-parquet",
        "fullTableName": "files.readouts",
        "enabled": true
    }
    ```

    Example of the response returned.

    ```json5
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

=== "Import table"

    The import table job is used to import the table schema into the existing connection in DQOps.

    ```url
    https://<your_DQOps_instance>/api/jobs/importtables/table?wait=true
    ```

    The **Body** of the request in the example below imports the table schema of "files.readouts" table from the "azure-storage-parquet" connection.
    
    ```json5
    {
        "connectionName": "azure-storage-parquet",
        "schemaName": "files",
        "tableNames": [
            "readouts"
        ]
    }
    ```

    Example of the response returned.

    ```json5
    {
      "jobId": {
        "jobId": 1720701935000000,
        "createdAt": "2024-07-11T12:45:35.687507400Z"
      }, 
      "result": {
        "source_table_specs": [{
          "incremental_time_window": {
            "daily_partitioning_recent_days": 7, 
            "monthly_partitioning_recent_months": 1
          }, 
          "columns": {
            "actual_value": {
              "type_snapshot": {
                "column_type": "DOUBLE", 
                "nullable": "True"
              }, 
              "comments": []
            }, 
            "check_category": {
              "type_snapshot": {
                "column_type": "VARCHAR", 
                "nullable": "True"
              }, 
              "comments": []
            }, 
            // ...
          }, "comments": []
        }]
      }, 
      "status": "finished"
    }
    ```


=== "Get table data quality status"

    The table data quality status provides an overall assessment of data quality status for a specific table, based on checks run in the DQOps platform.
    If there are any issues with the table, the operator will inform the severity of the issue and point towards an areas that require improvement in data quality.
    
    This job can be used to collect information about data quality before or after carrying out significant operation, potentially saving operational costs and time.

    The URL contains the name of the connection, schema and table.

    Request method: GET

    ```url
    http://<your_DQOps_instance>/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/status
    ```

    The following URL calls the API to retrieve the status for the table files.readouts from the azure-storage-parquet connection.

    ```url
    http://<your_DQOps_instance>/api/connections/azure-storage-parquet/schemas/files/tables/readouts/status
    ```

    Example of the response returned.

    ```json5
    {
        "connection_name": "azure-storage-parquet",
        "schema_name": "files",
        "table_name": "readouts",
        "current_severity": "valid",
        "highest_historical_severity": "valid",
        "last_check_executed_at": "2024-07-10T12:33:11.818Z",
        "executed_checks": 406,
        "valid_results": 399,
        "warnings": 5,
        "errors": 2,
        "fatals": 0,
        "execution_errors": 0,
        "data_quality_kpi": 100,
        "checks": {
            // ...
        },
        "columns": {
            // ...
        },
        "dimensions": {
            // ...
        }
    }
    ```

## Next steps

- We have provided a variety of use cases that use openly available datasets from [Google Cloud](https://cloud.google.com/datasets) to help you in using DQOps effectively. You can find the [full list of use cases here](../../examples/index.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../../integrations/webhooks/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.
