# Adding data source connection

After [installation and starting DQO](../installation/installation.md), we describe how to add a connection to [BigQuery public dataset Austin Crime Data](https://console.cloud.google.com/marketplace/details/city-of-austin/austin-crime) 
using the graphic interface.

For a full description of how to add a data source connection to other providers or add connection using CLI, see [Working with DQO section](../../working-with-dqo/adding-data-source-connection/index.md).

## Prerequisite credentials

To add BigQuery data source connection to DQO you need the following:

- A BiqQuery service account with **BigQuery > BigQuery Job User** permission. [You can create a free trial Google Cloud account here](https://cloud.google.com/free).
- A service account key in JSON format for JSON key authentication. For details refer to [Create and delete service account keys](https://cloud.google.com/iam/docs/keys-create-delete).
- A working [Google Cloud CLI](https://cloud.google.com/sdk/docs/install) if you want to use [Google Application Credentials authentication](./#using-google-application-credentials-authentication).

## Adding BigQuery connection using the graphic interface

1. In DQO User Interface Console go to the Data Sources section and click **+ Add connection** button in the upper left corner.

    ![Adding connection](https://docs.dqo.ai/docs/images/getting-started/adding-test-connection-test-connection.jpg)
   
2. Select BiqQuery database type.

    ![Selecting BigQuery database type](https://docs.dqo.ai/docs/images/working-with-dqo/adding-connections/adding-connection-bigquery.jpg)

3. Add connection settings.

    ![Adding connection settings](https://docs.dqo.ai/docs/images/getting-started/adding-test-connection-parameters.jpg)

    | BigQuery connection settings            | Description                                                                                                                                                                                                                                                                                      | 
    |--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
    | Connection name                         | The name of the connection that will be created in DQO. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters, hyphens and undescore. For example, "**testconnection**" |
    | Source GCP project ID                   | Name of the project that has datasets that will be imported. In our example, it is "**big-query-public-data**".                                                                                                                                                                                  |
    | Billing GCP project ID                  | Name of the project used as the default GCP project. The calling user must have a bigquery.jobs.create permission in this project.                                                                                                                                                               |
    | Authentication mode to the Google Cloud | Type of authentication mode to the Google Cloud. You can select from the 3 options:<br/>- Google Application Credentials,<br/>- JSON Key Content<br/> - JSON Key Path                                                                                                                            |
    | Quota GCP project ID                    | The Google Cloud Platform project ID which is used for invocation.                                                                                                                                                                                                                               |

4. After filling in the connection settings, click the **Test Connection** button to test the connection.

5. Click the **Save** connection button when the test is successful otherwise you can check the details of what went wrong.

6. Import the "austin_crime" schema by clicking on the **Import Tables** button.

    ![Importing schemas](https://docs.dqo.ai/docs/images/getting-started/importing-schema-austin-crime.jpg)

7. There is only one table in the dataset. Import the table by clicking **Import all tables** buttons in the upper right corner.

    ![Importing tables](https://docs.dqo.ai/docs/images/getting-started/importing-tables-austin-crime.jpg)

8. You can check the details of the imported table by expanding the tree view on the left and selecting the "crime" table.
        
     ![Viewing table details](https://docs.dqo.ai/docs/images/getting-started/austin-crime-table-view.jpg)
 
    There are several tabs to explore:

    - Table - provide details about the table and allows you to add filters or stage names (for example, "Ingestion")
    - Schedule - allows setting schedule for running checks. [Learn how to configure schedules](../../working-with-dqo/schedules/index.md)
    - Comments - allows adding comments to your tables
    - Labels - allows adding labels to your tables
    - Data streams - allows configuring columns for data streams segmentation. Learn more about [data streams segmentation in Concept section](../../dqo-concepts/data-stream-segmentation/data-stream-segmentation.md). 
    - Date and time columns - allows setting date and time columns for [partition checks type](../../dqo-concepts/checks/partition-checks/partition-checks.md) and table timeliness checks subcategory. 

## Next step

Now that you have connected a data source, it is time to [run data quality checks](../../getting-started/run-data-quality-checks/run-data-quality-checks.md).