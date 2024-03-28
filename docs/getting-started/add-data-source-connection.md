# Add connection to a data source
This guide shows how to connect a data source to DQOps, import the metadata, and operate with the DQOps user interface.

## Overview

After [installation and starting DQOps](installation.md), we describe how to import locally stored CSV file using the user interface, 
initiate automatic monitoring and run basic statistics.

For a full description of how to add a data source connection to other providers or add connection using the DQOps Shell,
see [Working with DQOps section](../data-sources/index.md).

You can find more information about [navigating the DQOps user interface here](../dqo-concepts/dqops-user-interface-overview.md). 

!!! info "How to analyze other data sources"

    Instead of importing a CSV file, you can follow the manuals for connecting to other data sources and continue
    this getting started guide from the [import metadata using the user interface](#import-metadata-using-the-user-interface) step.

    Links to some supported data sources are shown below.

    [![Athena](https://dqops.com/docs/images/connections/athena2.png){ class=glightbox-ignored-image }](../data-sources/athena.md)
    &nbsp; &nbsp; &nbsp; [![PostgreSQL](https://dqops.com/docs/images/connections/postgresql.png){ class=glightbox-ignored-image }](../data-sources/postgresql.md)
    &nbsp; &nbsp; &nbsp; [![BigQuery](https://dqops.com/docs/images/connections/bigquery.png){ class=glightbox-ignored-image }](../data-sources/bigquery.md)
    &nbsp; &nbsp; &nbsp; [![Parquet](https://dqops.com/docs/images/connections/parquet-icon2.png){ class=glightbox-ignored-image }](../data-sources/parquet.md)


### Download the sample CSV file

In the example, we will use a sample CSV file named **austin_crime.csv**. This file contains a fragment of the [public dataset Austin Crime Data](https://console.cloud.google.com/marketplace/details/city-of-austin/austin-crime),
but feel free to use your own CSV file if you prefer. 

To download the sample CSV file, [open the GitHub webpage](https://github.com/dqops/dqo/blob/develop/dqops/sampledata/files/csv/austin_crime_sample/austin_crime.csv),
click on the three dots button in the upper right corner, and select the **Download** command.

![Download sample CSV file from GitHub](https://dqops.com/docs/images/getting-started/github-download.png)
                                       
In our example, we have downloaded the file to a folder named __demo_files__. Remember the absolute path to the file, 
as we will need it when configuring the connection.

In our example, the path is **C:\demo_files\austin_crime.csv**

![Adding connection](https://dqops.com/docs/images/getting-started/file-explorer.png)

Below is a table that shows a fragment of the data included in the sample CSV file.

| unique_key | address                        | census_tract | clearance_date                 | clearance_status | council_district_code | description              | district | latitude | longitude | location | location_description | primary_type | timestamp                      | x_coordinate | y_coordinate | year | zipcode |
|------------|--------------------------------|--------------|--------------------------------|------------------|-----------------------|--------------------------|----------|----------|-----------|----------|----------------------|--------------|--------------------------------|--------------|--------------|------|---------|
| 2015821204 | "1713 MULLEN DR Austin, TX"    |              | 2015-03-25 12:00:00.000000 UTC | Not cleared      |                       | THEFT                    | UK       |          |           |          | 1713 MULLEN DR       | Theft        | 2015-03-23 12:00:00.000000 UTC |              |              | 2015 |         |
| 2015150483 | "Austin, TX"                   |              | 2015-01-27 12:00:00.000000 UTC | Not cleared      |                       | RAPE                     | B        |          |           |          | nan                  | Rape         | 2015-01-15 12:00:00.000000 UTC |              |              | 2015 |         | 
| 2015331540 | "5510 S IH 35 SVRD Austin, TX" |              | 2015-02-11 12:00:00.000000 UTC | Not cleared      |                       | BURGLARY OF VEHICLE      | UK       |          |           |          | 5510 S IH 35 SVRD    | Theft        | 2015-02-02 12:00:00.000000 UTC |              |              | 2015 |         |
| 2015331238 | "7928 US HWY 71 W Austin, TX"  |              | 2015-02-12 12:00:00.000000 UTC | Not cleared      |                       | THEFT OF HEAVY EQUIPMENT | UK       |          |           |          | 7928 US HWY 71 W     | Theft        | 2015-02-02 12:00:00.000000 UTC |              |              | 2015 |         |
| ...        | ...                            | ...          | ...                            | ...              | ...                   | ...                      | ...      | ...      | ...       | ...      | ...                  | ...          | ...                            | ...          | ...          | ...  | ...     |


## Add connection to a CSV file using the user interface

### **Navigate to the connection settings**

To navigate to the CSV connection settings:

1. Go to the **Data Sources** section and click **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection.png)
   
2. Select **CSV** connection type.

   ![Selecting CSV database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-csv.png)

### **Fill in the connection settings**

After navigating to the CSV connection settings, you will need to fill in the connection details.

To import a CSV file stored locally, we only need to fill in settings described below. 
You can read about other settings and how to import CSV files from AWS S3 in the [CSV connection documentation](../data-sources/csv.md). 

![Adding connection](https://dqops.com/docs/images/getting-started/connection-settings-csv-filled1.png)

| CSV connection settings | Description                                                                                                                                                                                                                               | 
|-------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Connection name         | The name of the connection that will be created in DQOps. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters. |
| Virtual schema name     | An alias for the parent directory with data. We leave the default value "files".                                                                                                                                                          |
| Path                    | The path prefix to the parent directory with data. The path must be absolute.                                                                                                                                                             || Path                     | The path prefix to the parent directory with data. The path must be absolute. The virtual schema name is a value of the directories mapping.                                                                                              |

After filling in the connection settings, click the **Test Connection** button to test the connection.
It will inform you if the path to the CSV file is correct.

Click the **Save** button when the test is successful to add a new connection. 
Otherwise, you can check the details of what went wrong.

## **Import metadata using the user interface**

When you add a new connection, it will appear in the tree view on the left, and you will be redirected to the **Import metadata** screen,
where you can import files. 


1. Import the "files" schema by clicking the **Import tables** button.

    ![Importing schemas](https://dqops.com/docs/images/getting-started/importing-schema-files.png)

2. Click on the checkbox next to the file name **austin_crime.csv** and import it by clicking **Import selected tables** button in the upper right corner.

    ![Importing tables](https://dqops.com/docs/images/getting-started/importing-tables-austin-crime-csv.png)

DQOps will create a table from the **austin_crime.csv** file in the virtual schema "files", which will allow you to profile it and monitor its data quality.

## Initiate automatic monitoring and review scheduling

Once new tables are imported, DQOps automatically activates [profiling and monitoring checks](../dqo-concepts/definition-of-data-quality-checks/index.md).
These checks include row count, table availability, and checks detecting schema changes. They are scheduled to run daily at 12:00 p.m.

[**Profiling checks**](../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md) are designed to assess
the initial data quality score of a data source. Profiling checks are also useful for exploring and experimenting with 
various types of checks and determining the most suitable ones for regular data quality monitoring.

[**Monitoring checks**](../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md) are 
standard checks that monitor the data quality of a table or column. They can also be referred to as **Data Observability** checks.
These checks capture a single data quality result for the entire table or column. There are two categories
of monitoring checks: *daily* checks and *monthly* checks.

Upon import, you will receive information at the top of the page, it is called an **Advisor**.
If you click on the orange bar, you can open the Advisor. Within the Advisor, you can collect basic statistics, 
run profiling checks, or modify the schedule for newly imported tables.

### Run basic statistics and profiling checks with the Advisor

To Run basic statistics and profiling checks, click on the appropriate buttons on the advisor.

We will evaluate the results from basic statistics and profiling checks at the next step of the Getting started.

![Running basic statistics and profiling checks](https://dqops.com/docs/images/getting-started/running-basics-statistics-and-profiling-checks-csv.png)

### Review scheduling with the Advisor

To review scheduling for profiling and daily monitoring checks, click on the **Review scheduling** button.

![Review scheduling](https://dqops.com/docs/images/getting-started/review-scheduling-csv.png)

You will be linked to **Data Source** section, **Schedule** tab where you can review scheduling settings for the added connection.

The scheduling is enabled by default. You can turn it off by clicking the notification icon in the upper right corner and 
then clicking the **Job scheduler** toggle button.
      
![Reviewing data source details](https://dqops.com/docs/images/getting-started/reviewing-data-source-section-csv1.png)


## Explore the connection-level tabs in the Data sources section

There are several tabs to explore in the **Data sources** section that differs depending on the selection of the elements in the tree view on the left (connection, schema, table or column):
The following tabs are shown at the connection level:

- **Connection** - provide details about the connection parameters.
- **Schedule** - allows setting schedule for running checks. [Learn how to configure schedules](../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md).
- **Comments** - allows adding comments to your connection.
- **Labels** - allows adding labels to your connection.
- **Schemas** - displays currently imported schemas and allows importing more schemas and tables.
- **Default grouping template** - allows setting up data grouping globally at the data source level. [Learn how to configure data grouping](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md).
- **Incidents and notifications** - allows configuring incidents and Webhooks for notifications. [Learn more about incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) that let you keep track of the issues that arise during data quality monitoring. [Learn how to configure notifications](../integrations/webhooks/index.md) whenever a new incident is created or modified.

## Explore the table-level tabs in the Data sources section

At the table level in the **Data sources** section, there are the following tabs:

- **Table** - provide details about the table and allows you to add filter, priority or stage name (for example, "Ingestion").
- **Schedule** - allows setting schedule for running checks. [Learn how to configure schedules](../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md).
- **Comments** - allows adding comments to your tables.
- **Labels** - allows adding labels to your tables.
- **Data groupings** - allows setting up data grouping at the table level. [Learn how to configure data grouping](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md).
- **Date and time columns** - allows [configuring event and ingestion timestamp columns for timeliness checks](../working-with-dqo/run-data-quality-checks.md#configure-event-and-ingestion-timestamp-columns-for-timeliness-checks), as well as [date or datetime column for partition checks](../working-with-dqo/run-data-quality-checks.md#configure-date-or-datetime-column-for-partition-checks).
- **Incident configuration** - allows configuring incidents. [Learn more about incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) that let you keep track of the issues that arise during data quality monitoring.

You can check the details of the imported table by expanding the tree view on the left and selecting the "austin_crime.csv" table.

![Reviewing table details](https://dqops.com/docs/images/getting-started/reviewing-table-details-csv.png)

## Next step

Now that you have connected a data source and initiated automatic monitoring,
it is time to [review the results and run additional checks](review-results-and-run-monitoring-checks.md).