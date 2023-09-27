# BigQuery

BigQuery is a fully managed enterprise data warehouse from Google Cloud that helps manage and analyze data with built-in
features like machine learning, geospatial analysis, and business intelligence.

## Prerequisite credentials

To add BigQuery data source connection to DQO you need the following:

  - A BiqQuery service account with **BigQuery > BigQuery Job User** permission
  - A service account key in JSON format for JSON key authentication. For details refer to [Create and delete service account keys](https://cloud.google.com/iam/docs/keys-create-delete)
  - A working [Google Cloud CLI](https://cloud.google.com/sdk/docs/install) if you want to use [Google Application Credentials](./#using-google-application-credentials-authentication) authentication

## Adding BigQuery connection using the graphical interface

1. Go to Data Sources section and click **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png)

2. Select BiqQuery database type.

    ![Selecting BigQuery database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-bigquery.png)

3. Add connection settings.

    ![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-bigquery.png)

    | BigQuery connection settings                                                                               | Description                                                                                                                                                                                                                                                                                                                                 | 
    |------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
    | Connection name                                                                                            | The name of the connection that will be created in DQO. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters, hyphens and underscore.                                                                             |
    | Source GCP project ID                                                                                      | Name of the project that has datasets that will be imported.                                                                                                                                                                                                                                                                                |
    | Authentication mode to the Google Cloud                                                                    | Type of authentication mode to the Google Cloud. You can select from the 3 options:<br/>- Google Application Credentials,<br/>- JSON Key Content<br/> - JSON Key Path                                                                                                                                                                       |
    | GCP project to create BigQuery jobs, where the authenticated principal has bigquery.jobs.create permission | Google Cloud Platform project which will be used to create BigQuery jobs. In this project, the authenticated user must have bigquery.jobs.create permission. You can select from the 3 options:<br/>- Create jobs in source project<br/>- Create jobs in default project from credentials<br/> - Create jobs in selected billing project ID |
    | Billing GCP project ID                                                                                     | The ID of the selected billing GCP project. In this project, the authenticated user must have bigquery.jobs.create permission. This field is active when you select the "Create jobs in selected billing project ID" option.                                                                                                                |
    | Quota GCP project ID                                                                                       | The Google Cloud Platform project ID which is used for invocation.                                                                                                                                                                                                                                                                          |

    DQO allows you to dynamically replace properties in connection settings with environment variables. To use it, simply
    change "clear text" to ${ENV_VAR} using the drop-down menu at the end of the variable entry field and type your variable.

    For example:

    ![Adding connection settings - environmental variables](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-bigquery-envvar.jpg)

4. After filling in the connection settings, click the **Test Connection** button to test the connection.
5. Click the **Save** connection button when the test is successful otherwise you can check the details of what went wrong.
6. Import the selected data resources (source schemas and tables) by clicking on the **Import Tables** button next to
   the name of the source schema from which you want to import tables. 

    ![Importing schemas](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-schemas.png)

7. Select the tables you want to import or import all tables using the buttons in the upper right corner.

    ![Importing tables](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables.png)

8. When new tables are imported, DQO automatically enables profiling and monitoring checks, such as row count, table availability and checks detecting schema changes. These checks are scheduled to run daily at 12:00 p.m. By clicking on the Advisor at the top of the page, you can quickly collect basic statistics, run profiling checks or modify the schedule for newly imported tables.

    ![Importing tables - advisor](https://dqops.com/docs/images/working-with-dqo/adding-connections/importing-tables-advisor.png)

## Adding BigQuery connection using DQO Shell

To add a connection run the following command in DQO Shell.

```
dqo> connection add
```

Fill in the data you will be asked for.

``` 
Connection name (--name): connection1
Database provider type (--provider):
 [ 1] bigquery
 [ 2] snowflake
 [ 3] postgresql
 [ 4] redshift
 [ 5] sqlserver
 [ 6] mysql
 [ 7] oracle
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

DQO will ask you to select the schema from which the tables will be imported.

You can also add the schema and table name as a parameter to import tables in just a single step.

```
dqo> table import --connection={connection name} 
--schema={schema name}
--table={table name}
```
DQO supports the use of the asterisk character * as a wildcard when selecting schemas and tables, which can substitute
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
    billing_project_id: XXXXXXX
    authentication_mode: google_application_credentials
    quota_project_id: project
```

## Using Google Application Credentials authentication

[Application Default Credentials](https://cloud.google.com/docs/authentication/provide-credentials-adc) is a strategy used by the Google authentication libraries to automatically find 
credentials based on the application environment.

DQO allows authentication using Google Application Credentials. 

To provide your user credentials to DQO, use the Google Cloud CLI:

1. [Install the gcloud CLI](https://cloud.google.com/sdk/docs/install), if you haven't already.
2. Run `gcloud auth application-default login` command in shell or command line to create your credential file.
3. Log in at login screen. The credentials will apply to all API calls that make use of the Application Default Credentials client library.