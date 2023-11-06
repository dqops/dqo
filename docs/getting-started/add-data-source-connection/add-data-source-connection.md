# Add data source connection

After [installation and starting DQO](../installation/installation.md), we describe how to add a connection to [BigQuery public dataset Austin Crime Data](https://console.cloud.google.com/marketplace/details/city-of-austin/austin-crime) 
using the graphical interface.

For a full description of how to add a data source connection to other providers or add connection using CLI, see [Working with DQO section](../../working-with-dqo/adding-data-source-connection/index.md).
You can find more information about [navigating the DQO graphical interface here](../../dqo-concepts/user-interface-overview/user-interface-overview.md). 

## Prerequisite credentials

To add BigQuery data source connection to DQO you need the following:

- A BiqQuery service account with **BigQuery > BigQuery Job User** permission. [You can create a free trial Google Cloud account here](https://cloud.google.com/free).
- A service account key in JSON format for JSON key authentication. For details refer to [Create and delete service account keys](https://cloud.google.com/iam/docs/keys-create-delete).
- A working [Google Cloud CLI](https://cloud.google.com/sdk/docs/install) if you want to use [Google Application Credentials authentication](../../../working-with-dqo/adding-data-source-connection/bigquery/#using-google-application-credentials-authentication).

## Add BigQuery connection using the graphical interface

1. Go to the **Data Sources** section and click **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png)
   
2. Select **BiqQuery** database type.

    ![Selecting BigQuery database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-bigquery.png)

3. Add connection settings.

    ![Adding connection settings](https://dqops.com/docs/images/working-with-dqo/adding-connections/connection-settings-bigquery.png)

    | BigQuery connection settings                                                                               | Description                                                                                                                                                                                                                                                                                                                                 | 
    |------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
    | Connection name                                                                                            | The name of the connection that will be created in DQO. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters, hyphens and underscore. For example, "**testconnection**"                                           |
    | Source GCP project ID                                                                                      | Name of the project that has datasets that will be imported. In our example, it is "**big-query-public-data**".                                                                                                                                                                                                                             |
    | Authentication mode to the Google Cloud                                                                    | Type of authentication mode to the Google Cloud. You can select from the 3 options:<br/>- Google Application Credentials,<br/>- JSON Key Content<br/> - JSON Key Path                                                                                                                                                                       |
    | GCP project to create BigQuery jobs, where the authenticated principal has bigquery.jobs.create permission | Google Cloud Platform project which will be used to create BigQuery jobs. In this project, the authenticated user must have bigquery.jobs.create permission. You can select from the 3 options:<br/>- Create jobs in source project<br/>- Create jobs in default project from credentials<br/> - Create jobs in selected billing project ID |
    | Billing GCP project ID                                                                                     | The ID of the selected billing GCP project. In this project, the authenticated user must have bigquery.jobs.create permission. This field is active when you select the "Create jobs in selected billing project ID" option.                                                                                                                |
    | Quota GCP project ID                                                                                       | The Google Cloud Platform project ID which is used for invocation.                                                                                                                                                                                                                                                                          |

4. After filling in the connection settings, click the **Test Connection** button to test the connection.

5. Click the **Save** connection button when the test is successful to add a new connection. Otherwise, you can check the details of what went wrong.
    

## Import metadata using the graphical interface

When you add a new connection, it will appear in the tree view on the left, and you will be redirected to the Import Metadata screen.
Now we can import schemas and tables.

1. Import the "austin_crime" schema by clicking on the **Import Tables** button.

    ![Importing schemas](https://dqops.com/docs/images/getting-started/importing-schema-austin-crime.png)

2. There is only one table in the dataset. Import the table by clicking **Import all tables** buttons in the upper right corner.

    ![Importing tables](https://dqops.com/docs/images/getting-started/importing-tables-austin-crime.png)


## Initiate automatic monitoring and review scheduling

Once new tables are imported, DQO automatically enables [profiling and monitoring checks](../../dqo-concepts/checks/index.md). These checks include row count,
table availability and checks detecting schema changes. They are scheduled to run daily at 12:00 p.m.

Upon import, you will receive information at the top of the page. If you click on the orange bar, you can open an Advisor.
Within the Advisor, you can collect basic statistics, run profiling checks or modify the schedule for newly imported tables.

1. Run basic statistics and profiling checks by clicking on the appropriate buttons.

    We will evaluate the results from basic statistics and profiling checks at the next step of the Getting started. 

    ![Running basic statistics and profiling checks](https://dqops.com/docs/images/getting-started/running-basics-statistics-and-profiling-checks.png)

2. Review scheduling for profiling and daily monitoring checks by clicking **Review scheduling** button. 

    ![Review scheduling](https://dqops.com/docs/images/getting-started/review-scheduling.png)

    You will be linked to **Data Source** section, **Schedule** tab where you can review scheduling settings for the added connection.
    The scheduling is enabled by default. You can turn it off by clicking the notification icon in the upper right corner and 
    then clicking the **Job scheduler** toggle button. 
      
    ![Reviewing data source details](https://dqops.com/docs/images/getting-started/reviewing-data-source-section2.png)

    There are several tabs to explore that differs depending on the selection of the elements in the tree view on the left (connection, schema, table or column):

    At the connection level there are the following tabs: 

    - **Connection** - provide details about the connection parameters.
    - **Schedule** - allows setting schedule for running checks. [Learn how to configure schedules](../../working-with-dqo/schedules/index.md).
    - **Comments** - allows adding comments to your connection.
    - **Labels** - allows adding labels to your connection.
    - **Schemas** - displays currently imported schemas and allows importing more schemas and tables.
    - **Default grouping template** - allows setting up data grouping globally at the data source level. [Learn how to configure data grouping](../../working-with-dqo/set-up-data-grouping/set-up-data-grouping.md).
    - **Incidents and notifications** - allows configuring incidents and Webhooks for notifications. [Learn more about incidents](../../working-with-dqo/incidents-and-notifications/incidents.md) that let you keep track of the issues that arise during data quality monitoring. [Learn how to configure notifications](../../integrations/webhooks/index.md) whenever a new incident is created or modified.

3. Check the details of the imported table by expanding the tree view on the left and selecting the "crime" table.

    ![Reviewing table details](https://dqops.com/docs/images/getting-started/reviewing-table-details.png)
   
    At the table level there are the following tabs:

    - **Table** - provide details about the table and allows you to add filter, priority or stage name (for example, "Ingestion").
    - **Schedule** - allows setting schedule for running checks. [Learn how to configure schedules](../../working-with-dqo/schedules/index.md).
    - **Comments** - allows adding comments to your tables.
    - **Labels** - allows adding labels to your tables.
    - **Data groupings** - allows setting up data grouping at the table level. [Learn how to configure data grouping](../../working-with-dqo/set-up-data-grouping/set-up-data-grouping.md).
    - **Date and time columns** - allows [configuring event and ingestion timestamp columns for timeliness checks]( ../../../working-with-dqo/run-data-quality-checks/run-data-quality-checks/#configure-event-and-ingestion-timestamp-columns-for-timeliness-checks), as well as [date or datetime column for partition checks](../../../working-with-dqo/run-data-quality-checks/run-data-quality-checks/#configure-date-or-datetime-column-for-partition-checks).
    - **Incident configuration** - allows configuring incidents. [Learn more about incidents](../../working-with-dqo/incidents-and-notifications/incidents.md) that let you keep track of the issues that arise during data quality monitoring.


## Next step

Now that you have connected a data source and initiated automatic monitoring, it is time to [review the results and run additional checks](../review-results-and-run-monitoring-checks/review-results-and-run-monitoring-checks.md).