---
title: How to activate data observability for Google BigQuery
---
# How to activate data observability for Google BigQuery
Read this guide to learn how to connect DQOps to BigQuery from the UI, command-line interface, or directly in YAML files, and activate monitoring.

## Overview

BigQuery is a fully managed enterprise data warehouse from Google Cloud that 
helps manage and analyze data with built-in features like machine learning, geospatial analysis, and business intelligence.

## Prerequisite credentials

To add BigQuery data source connection to DQOps you need the following:

  - A BigQuery service account with **BigQuery > BigQuery Job User** permission
  - A service account key in JSON format for JSON key authentication. For details refer to [Create and delete service account keys](https://cloud.google.com/iam/docs/keys-create-delete)
  - A working [Google Cloud CLI](https://cloud.google.com/sdk/docs/install) if you want to use [Google Application Credentials](#using-google-application-credentials-authentication) authentication

## Add BigQuery connection using the user interface

### **Navigate to the connection settings**

To navigate to the BigQuery connection settings:

1. Go to the Data Sources section and click the **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png){ loading=lazy; width="1200px" }

2. Select BiqQuery database type.

    ![Selecting BigQuery database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-bigquery.png){ loading=lazy; width="1200px" }


### **Fill in the connection settings**

After navigating to the BigQuery connection settings, you will need to fill in its details.

![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-bigquery.png)

| BigQuery connection settings                                                                               | Property name in YAML configuration file   | Description                                                                                                                                                                                                                                                                                                                                  | 
|------------------------------------------------------------------------------------------------------------|--------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Connection name                                                                                            |                                            | The name of the connection that will be created in DQOps. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters, hyphens and underscore.                                                                            |
| Source GCP project ID                                                                                      | `source_project_id`                        | Name of the project that has datasets that will be imported.                                                                                                                                                                                                                                                                                 |
| Authentication mode to the Google Cloud                                                                    | `authentication_mode`                      | Type of authentication mode to the Google Cloud. You can select from the 3 options:<br/>- Google Application Credentials,<br/>- JSON Key Content<br/> - JSON Key Path                                                                                                                                                                        |
| GCP project to create BigQuery jobs, where the authenticated principal has bigquery.jobs.create permission | `jobs_create_project`                      | Google Cloud Platform project which will be used to create BigQuery jobs. In this project, the authenticated user must have bigquery.jobs.create permission. You can select from the 3 options:<br/>- Create jobs in source project<br/>- Create jobs in default project from credentials<br/> - Create jobs in selected billing project ID  |
| Billing GCP project ID                                                                                     | `billing_project_id`                       | The ID of the selected billing GCP project. In this project, the authenticated user must have bigquery.jobs.create permission. This field is active when you select the "Create jobs in selected billing project ID" option.                                                                                                                 |
| Quota GCP project ID                                                                                       | `quota_project_id`                         | The Google Cloud Platform project ID which is used for invocation.                                                                                                                                                                                                                                                                           |

DQOps allows you to dynamically replace properties in connection settings with environment variables. To use it, simply
change "clear text" to ${ENV_VAR} using the drop-down menu at the end of the variable entry field and type your variable.

For example:

![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-bigquery-envvar.jpg)

After filling in the connection settings, click the **Test Connection** button to test the connection.

Click the **Save** connection button when the test is successful otherwise, you can check the details of what went wrong.


### **Import metadata using the user interface**

1. Import the selected data resources (source schemas and tables) by clicking on the **Import Tables** button next to
   the name of the source schema from which you want to import tables. 

    ![Importing schemas](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-schemas.png)

2. Select the tables you want to import or import all tables using the buttons in the upper right corner.

    ![Importing tables](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables.png)

When new tables are imported, DQOps automatically activates profiling and monitoring checks, such as row count, table 
availability and checks detecting schema changes. These checks are scheduled to run daily at 12:00 p.m. By clicking 
on the Advisor at the top of the page, you can quickly collect basic statistics, run profiling checks, or modify 
the schedule for newly imported tables.

![Importing tables - advisor](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables-advisor.png)


## Add BigQuery connection using DQOps Shell

To add a connection run the following command in DQOps Shell.

```
dqo> connection add
```

Fill in the data you will be asked for.

``` 
Connection name (--name): connection1
Database provider type (--provider):
 [ 1] bigquery
 [ 2] databricks
 [ 3] mysql
 [ 4] oracle
 [ 5] postgresql
 [ 6] duckdb
 [ 7] presto
 [ 8] redshift
 [ 9] snowflake
 [10] spark
 [11] sqlserver
 [12] trino
Please enter one of the [] values: 1
Source GCP project ID (--bigquery-source-project-id") [dqo-ai-testing]: dqo-ai-testing
Billing GCP project ID (--bigquery-billing-project-id), leave null to use the default GCP project from credentials (dqo-ai-testing):
GCP Authentication Mode (--bigquery-authentication-mode) [google_application_credentials]:
 [ 1] google_application_credentials (default)
 [ 2] json_key_content
 [ 3] json_key_path
Please enter one of the [] values:
GCP quota (billing) project ID (--bigquery-quota-project-id), leave blank to use the default GCP project from credentials (dqo-ai-testing):
Connection connection1 was successfully added.
Run 'table import -c=connection1' to import tables.
```

You can also run the command with parameters to add a connection in just a single step.

```
dqo> connection add --name=connection1 
--provider=bigquery
--bigquery-source-project-id=bigquery-public-data 
--bigquery-billing-project-id=dqo-ai-testing
--bigquery-quota-project-id=dqo-ai-testing 
--bigquery-authentication-mode=google_application_credentials
```

After adding connection run `table import -c=connection1` to select schemas and import tables. 

DQOps will ask you to select the schema from which the tables will be imported.

You can also add the schema and table name as a parameter to import tables in just a single step.

```
dqo> table import --connection={connection name} 
--schema={schema name}
--table={table name}
```

DQOps supports the use of the asterisk character * as a wildcard when selecting schemas and tables, which can substitute
any number of characters. For example, use  pub* to find all schema a name with a name starting with "pub". The * 
character can be used at the beginning, in the middle or at the end of the name.


## Connections configuration files

Connection configurations are stored in the YAML files in the `./sources` folder. The name of the connection is also 
the name of the folder where the configuration file is stored. 

Below is a sample YAML file showing an example configuration of the BigQuery data source connection.


``` yaml
apiVersion: dqo/v1
kind: source
spec:
  provider_type: bigquery
  bigquery:
    source_project_id: project1
    jobs_create_project: create_jobs_in_default_project_from_credentials 
    billing_project_id: XXXXXXX
    authentication_mode: google_application_credentials
    quota_project_id: project
```

### **Reference of all connection parameters**
Complete documentation of all connection parameters used in the `spec.bigquery` node is
described in the reference section of the [BigQueryParametersSpec](../reference/yaml/ConnectionYaml.md#bigqueryparametersspec)
YAML file format.

## Configure the credentials

### Using Google Application Credentials authentication

[Application Default Credentials](https://cloud.google.com/docs/authentication/provide-credentials-adc) is a strategy used by the Google authentication libraries to automatically find
credentials based on the application environment.

DQOps allows authentication using Google Application Credentials.

To provide your user credentials to DQOps, use the Google Cloud CLI:

1. [Install the gcloud CLI](https://cloud.google.com/sdk/docs/install), if you haven't already.
2. Run `gcloud auth application-default login` command in shell or command line to create your credential file.
3. Log in at login screen. The credentials will apply to all API calls that make use of the Application Default Credentials client library.


### Using shared credentials

This method use the **Authentication mode to the Google Cloud** set as **Google Application Credentials**.

With DQOps, you can configure credentials to access GCP Cloud directly in the platform.

Please note, that any credentials and secrets shared with the DQOps Cloud or DQOps SaaS instances are stored in the .credentials folder. 
This folder also contains the default credentials file for GCP named GCP_application_default_credentials.json.

``` { .asc .annotate hl_lines="4" }
$DQO_USER_HOME
├───...
└───.credentials                                                            
    ├───GCP_application_default_credentials.json
    └─...   
```

If you wish to use JSON key authentication, the content of the file must be replaced with a service account key in JSON format.
You can find more details on how to [Create and delete service account keys](https://cloud.google.com/iam/docs/keys-create-delete) in Google Cloud documentation.

!!! warning

    If you do not replace the content of the file, the Application Default Credentials will be used.


To set the credential file in DQOps, follow these steps:

1. Open the Configuration in menu.
2. Select Shared credentials from the tree view on the left.
3. Click the edit link on the “GCP_application_credentials.json” file.

    ![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/credentials/shared-credentials-ui.png)

4. In the text area, paste the key in JSON format, replacing the placeholder text.

    ![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/credentials/edit-gcp-shared-credential.png)

    ![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/credentials/replaced-gcp-shared-credential.png)

5. Click the **Save** button, to save changes.

!!! tip "Use the Application Default Credentials after filling in the shared credential"
    
    If you still want to use default credentials from Google Cloud CLI, 
    you must manually delete the .credentials/GCP_application_default_credentials.json file from the DQOps credentials.


## Next steps

- We have provided a variety of use cases that use openly available datasets from [Google Cloud](https://cloud.google.com/datasets) to help you in using DQOps effectively. You can find the [full list of use cases here](../examples/index.md). 
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.
