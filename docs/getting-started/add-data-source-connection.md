---
title: Creating a connection to a data source and starting data quality assessment
---
# Creating a connection to a data source and profiling data
This guide shows how to connect a data source to DQOps, import the metadata, and start data quality assessment.

## Creating a connection to a data source

After [installation and starting DQOps](installation.md), we describe how to import locally stored CSV file using the user interface, 
initiate automatic monitoring and run basic statistics.

For a full description of how to add a data source connection to other providers or add connection using the DQOps Shell,
see [Working with DQOps section](../data-sources/index.md).

You can find more information about [navigating the DQOps user interface here](../dqo-concepts/dqops-user-interface-overview.md). 

!!! info "How to analyze other data sources"

    Instead of importing a CSV file, you can follow the manuals for connecting to other data sources and continue
    this getting started guide from the [import metadata using the user interface](#import-metadata-using-the-user-interface) step.

    DQOps supports integration with relational databases, data lakes, cloud data warehouses, object storage services, 
    data processing frameworks, query engines and flat files. You can find [the full list of supported datasources here](../data-sources/index.md). 

    Links to some supported data sources are shown below.

    [![Athena](https://dqops.com/docs/images/connections/athena2.png){ class=glightbox-ignored-image }](../data-sources/athena.md)
    &nbsp; &nbsp; &nbsp; [![Redshift](https://dqops.com/docs/images/connections/amazon-redshift1.png){ class=glightbox-ignored-image }](../data-sources/redshift.md)
    &nbsp; &nbsp; &nbsp; [![BigQuery](https://dqops.com/docs/images/connections/bigquery.png){ class=glightbox-ignored-image }](../data-sources/bigquery.md)
    &nbsp; &nbsp; &nbsp; [![Snowflake](https://dqops.com/docs/images/connections/snowflake.png){ class=glightbox-ignored-image }](../data-sources/snowflake.md)
    &nbsp; &nbsp; &nbsp; [![PostgreSQL](https://dqops.com/docs/images/connections/postgresql.png){ class=glightbox-ignored-image }](../data-sources/postgresql.md)
    &nbsp; &nbsp; &nbsp; [![Oracle](https://dqops.com/docs/images/connections/oracle2.png){ class=glightbox-ignored-image }](../data-sources/oracle.md)
    &nbsp; &nbsp; &nbsp; [![MySQL](https://dqops.com/docs/images/connections/mysql.png){ class=glightbox-ignored-image }](../data-sources/mysql.md)
    &nbsp; &nbsp; &nbsp; [![SQL Server](https://dqops.com/docs/images/connections/microsoft-sql-server.png){ class=glightbox-ignored-image }](../data-sources/sql-server.md)
    &nbsp; &nbsp; &nbsp; [![Spark](https://dqops.com/docs/images/connections/spark.png){ class=glightbox-ignored-image }](../data-sources/spark.md)
    &nbsp; &nbsp; &nbsp; [![Databricks](https://dqops.com/docs/images/connections/databricks.png){ class=glightbox-ignored-image }](../data-sources/databricks.md)
    &nbsp; &nbsp; &nbsp; [![Parquet](https://dqops.com/docs/images/connections/parquet-icon2.png){ class=glightbox-ignored-image }](../data-sources/parquet.md)


### Download the sample CSV file

In the example, we will use a sample CSV file named **orders.csv**. This file contains a fragment of the [public dataset of a fictitious ecommerce clothing site TheLook](https://console.cloud.google.com/marketplace/product/bigquery-public-data/thelook-ecommerce),
but feel free to use your own CSV file if you prefer. 

To download the sample CSV file, [open the GitHub webpage](https://github.com/dqops/dqo/blob/develop/dqops/sampledata/files/csv/thelook-ecommerce/orders.csv),
click on the three dots button in the upper right corner, and select the **Download** command.

![Download sample CSV file from GitHub](https://dqops.com/docs/images/getting-started/github-download.png){ loading=lazy; width="400px" }
                                       
In our example, we have downloaded the file to a folder named __demo_files__. Remember the absolute path to the file, 
as we will need it when configuring the connection.

In our example, the path is **C:\demo_files\orders.csv**

![Adding connection](https://dqops.com/docs/images/getting-started/file-explorer3.png){ loading=lazy; width="700px" }

Below is a table that shows a fragment of the data included in the sample CSV file.

| order_id | user_id | status     | gender | created_at         | returned_at | shipped_at         | delivered_at      | num_of_item | 
|----------|---------|------------|--------|--------------------|-------------|--------------------|-------------------|-------------|
| 1        | 3       | Shipped    | F      | 8/12/2019 15:15:00 |             | 8/15/2019 14:08:00 |                   | 1           |
| 2        | 5       | Shipped    | M      | 1/20/2022 9:17:00  |             | 1/22/2022 2:25:00  |                   | 1           |
| 3        | 6       | Processing | F      | 7/23/2022 11:33:00 |             |                    |                   | 4           |
| 4        | 6       | Completed  | F      | 3/1/2020 11:33:00  |             | 3/2/2020 15:57:00  | 3/4/2020 16:32:00 | 3           |
| ...      | ...     | ...        | ...    | ...                | ...         | ...                | ...               | ...         |


## Add a connection to a CSV file using the user interface

### **Navigate to the connection settings**

To navigate to the CSV connection settings:

1. Go to the **Data Sources** section and click the **+ Add connection** button in the upper left corner.

    ![Adding connection](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection2.png){ loading=lazy; width="1200px" }
   
2. Select **CSV** connection type.

   ![Selecting CSV database type](https://dqops.com/docs/images/working-with-dqo/adding-connections/adding-connection-csv2.png){ loading=lazy; width="1200px" }

### **Fill in the connection settings**

After opening the CSV connection settings, fill in the connection details.

If you are importing a CSV file stored locally, you only need to complete the settings described below.
For information on other settings and how to import CSV files from AWS S3, refer to [the CSV connection documentation](../data-sources/csv.md)."

![Adding connection](https://dqops.com/docs/images/getting-started/connection-settings-csv-filled1.png)

| CSV connection settings | Description                                                                                                                                                                                                                               | 
|-------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Connection name         | The name of the connection that will be created in DQOps. This will also be the name of the folder where the connection configuration files are stored. The name of the connection must be unique and consist of alphanumeric characters. |
| Virtual schema name     | An alias for the parent directory with data. We leave the default value "files".                                                                                                                                                          |
| Path                    | The path prefix to the parent directory with data. The path must be absolute.                                                                                                                                                             || Path                     | The path prefix to the parent directory with data. The path must be absolute. The virtual schema name is a value of the directories mapping.                                                                                              |

After entering the connection settings, click on the **Test Connection** button to test the connection.
It will inform you if the path to the CSV file is correct.

If the test is successful, click the **Save** button to add a new connection. If the test fails, you can review the details to identify the issue.

## **Import metadata using the user interface**

When you add a new connection, it will appear in the tree view on the left. You will then be redirected to the **Import metadata** screen,
where you can import files. 


1. Import the "files" schema by clicking the **Import tables** button.

    ![Importing schemas](https://dqops.com/docs/images/getting-started/importing-schema-files2.png){ loading=lazy; width="1200px" }

2. Import the "orders.csv" file by clicking on the **Import all tables** button in the upper right corner.

    ![Importing tables](https://dqops.com/docs/images/getting-started/importing-orders-csv4.png){ loading=lazy; width="1200px" }

DQOps will create a table from the **orders.csv** file in the virtual schema "files", which will allow you to profile it and monitor its data quality.


## Start data quality assessment

DQOps simplifies the data quality assessment process which is a routine activity for data governance and quality teams. 

Upon import, you will receive information that a new tables have been imported. You can then begin collecting basic statistics
and profiling data by running default data profiling checks. Simply click on the **Start profiling** button to initiate this process. 

![Collect basic statistics and profile data with default profiling checks](https://dqops.com/docs/images/getting-started/collect-basic-statistics-and-profile-data.png)

!!! info "Automatically activated checks"

    Once new tables are imported, DQOps automatically activates [profiling and monitoring checks](../dqo-concepts/definition-of-data-quality-checks/index.md).
    These checks include row count, table availability, and checks detecting schema changes. The profiling checks are scheduled 
    to run at 1:00 a.m. on the 1st day of every month, and the monitoring checks are scheduled to run daily at 12:00 p.m.
    
    [**Profiling checks**](../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md) are designed to assess
    the initial data quality score of a data source. Profiling checks are also useful for exploring and experimenting with 
    various types of checks and determining the most suitable ones for regular data quality monitoring.
    
    [**Monitoring checks**](../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md) are 
    standard checks that monitor the data quality of a table or column. They can also be referred to as **Data Observability** checks.
    These checks capture a single data quality result for the entire table or column.

### Default data profiling checks

Below is a list of the [data profiling checks](../dqo-concepts/data-observability.md) automatically activated according
to [default Table and Column-level data quality policies](../dqo-concepts/data-observability.md#configuring-check-policies-in-ui).

| Target | Check name                                                                               | Description                                    |
|--------|------------------------------------------------------------------------------------------|------------------------------------------------|
| column | [profile contains usa phone percent](../checks/column/pii/contains-usa-phone-percent.md) | Detects USA phone numbers inside text columns. |
| column | [profile contains email percent](../checks/column/pii/contains-email-percent.md)         | Detects emails inside text columns.            |

### Default daily monitoring checks
Below is a list of the [daily monitoring checks](../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)
automatically activated according to [default Table and Column-level data quality policies](../dqo-concepts/data-observability.md#configuring-check-policies-in-ui).

| Target | Check name                                                                                                | Description                                                                                                                                                                                                                                                                                          |
|--------|-----------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| table  | [daily row count](../checks/table/volume/row-count.md)                                                    | Captures the row count of the table every day and identifies empty tables.                                                                                                                                                                                                                           |
| table  | [daily row count anomaly](../checks/table/volume/row-count-anomaly.md)                                    | Detects day-to-day anomalies in the table volume. Raises a data quality issue when the increase or decrease in the row count is in the top 1% of most significant changes.                                                                                                                           |
| table  | [daily row count change](../checks/table/volume/row-count-change.md)                                      | Detects significant day-to-day changes in the table volume. Raises a data quality issue when the increase or decrease in the row count is greater than 10%.                                                                                                                                          |
| table  | [daily freshness anomaly](../checks/table/timeliness/data-freshness-anomaly.md)                           | Measures the freshness of the table and detects anomalous (outstanding) delays in data freshness. The table must be properly configured to [support timeliness checks](../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md#configure-dqops-for-timeliness-checks). |
| table  | [daily table availability](../checks/table/availability/table-availability.md)                            | Verifies that a table exists, can be accessed, and queried without errors. Detects corrupted tables and expired credentials to data sources.                                                                                                                                                         |
| table  | [daily column count](../checks/table/schema/column-count.md)                                              | Retrieves the metadata of the monitored table from the data source and counts the number of columns.                                                                                                                                                                                                 |
| table  | [daily column count changed](../checks/table/schema/column-count-changed.md)                              | Detects whether the number of columns in a table has changed since the last time the check (checkpoint) was run.                                                                                                                                                                                     |
| column | [daily nulls count](../checks/column/nulls/nulls-count.md)                                                | Counts null values in every column without raising any data quality issues.                                                                                                                                                                                                                          |
| column | [daily nulls percent](../checks/column/nulls/nulls-percent.md)                                            | Measures the percentage of null values in every column without raising any data quality issues.                                                                                                                                                                                                      |
| column | [daily nulls percent anomaly](../checks/column/nulls/nulls-percent-anomaly.md)                            | Measures the percentage of null values in every column and detects anomalous changes in the percentage of null value. Raises a data quality issue for the top 1% biggest day-to-day changes.                                                                                                         |
| column | [daily not nulls count](../checks/column/nulls/not-nulls-count.md)                                        | Verifies that a column contains a minimum number of non-null values                                                                                                                                                                                                                                  |
| column | [daily not nulls percent](../checks/column/nulls/not-nulls-percent.md)                                    | Measures the percentage of null values in every column without raising any data quality issues.                                                                                                                                                                                                      |
| column | [daily empty column found](../checks/column/nulls/empty-column-found.md)                                  | Detects empty columns that contain only null values.                                                                                                                                                                                                                                                 |
| column | [daily distinct count anomaly](../checks/column/uniqueness/distinct-count-anomaly.md)                     | Counts distinct values in every column and detects anomalous changes in the percentage of null value. Raises a data quality issue for the top 1% biggest day-to-day changes of the count of distinct values.                                                                                         |
| column | [daily detected datatype in text changed](../checks/column/datatype/detected-datatype-in-text-changed.md) | Scans all values in a text column and detects the data type of all values in a column. Raises a data quality issue when the type of texts changes. For example, when a column contained always numeric values, but a text value was found.                                                           |
| column | [daily column exists](../checks/column/schema/column-exists.md)                                           | Reads the metadata of the monitored table and verifies that the column still exists in the data source.                                                                                                                                                                                              |
| column | [daily column type changed](../checks/column/schema/column-type-changed.md)                               | Detects if the data type of the column has changed since the last time it was retrieved.                                                                                                                                                                                                             |

For more [general information about checks, see the DQOps concepts](../dqo-concepts/definition-of-data-quality-checks/index.md) section.


### Review scheduling

To review scheduling for profiling and daily monitoring checks

1. Go to the **Data source** section.
2. Select the connection from the tree view on the left. 
3. Select the **Schedule** tab where you can review scheduling settings for the added connection.

![Review scheduling](https://dqops.com/docs/images/getting-started/review-scheduling-csv4.png){ loading=lazy; width="1200px" }

The scheduling is enabled by default. You can turn it off by clicking the **Notification icon** in the upper right corner and 
then clicking the **Job scheduler** toggle button.
      
![Reviewing data source details](https://dqops.com/docs/images/getting-started/reviewing-job-scheduler2.png){ loading=lazy; width="1200px" }


## Explore the connection-level tabs in the Data sources section

There are several tabs to explore in the **Data sources** section that differ depending on the selection of the elements in the tree view on the left (connection, schema, table or column):
The following tabs are shown at the connection level:

- **Connection:** Provides details about the connection parameters.
- **Schedule:** Allows you to [configure of the check execution schedule](../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md) at the connection level.
- **Comments:** Allows adding comments to your connection.
- **Labels:** Allows adding labels to your connection.
- **Schemas:** Allow importing schemas and tables. 
- **Data quality summary:** Displays summaries of the data quality status. You have the option to choose one of two subtabs:
    - **Tables:** This subtab provides a summary of the data quality status for tables in this connection.
    - **Columns:** This subtab provides a summary of the data quality status for columns in this connection.
- **Default grouping template:** Allows setting up data grouping globally at the data source level. [Learn how to configure data grouping](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md).
- **Incident grouping**: Allows configuring incidents grouping level. [Learn more about incidents](../dqo-concepts/grouping-data-quality-issues-to-incidents.md) that let you keep track of the issues that arise during data quality monitoring.
- **Notifications:** Allows configuring incidents and Webhooks for notifications. [Learn how to configure notifications](../dqo-concepts/grouping-data-quality-issues-to-incidents.md#configure-notification-for-an-incident) whenever a new incident is created or modified.

## Explore the table-level tabs in the Data sources section

At the table level in the **Data sources** section, there are the following tabs:

- **Table:** Provides details about the table and allows you to add filter, priority or stage name (for example, "Ingestion").
- **Schedule:** Allows setting schedule for running checks. [Learn how to configure schedules](../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md).
- **Comments:** Allows adding comments to your tables.
- **Labels:** Allows adding labels to your tables.
- **Data groupings:** Allows setting up data grouping at the table level. [Learn how to configure data grouping](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md).
- **Date and time columns:** Allows [configuring event and ingestion timestamp columns for timeliness checks](../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md#configure-dqops-for-timeliness-checks), as well as [date or datetime column for partition checks](../dqo-concepts/definition-of-data-quality-checks/partition-checks.md#setting-up-date-partitioning-column).
- **Incident configuration:** Allows configuring incidents. [Learn more about incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) that let you keep track of the issues that arise during data quality monitoring.

![Reviewing table details](https://dqops.com/docs/images/getting-started/reviewing-table-level-tabs2.png){ loading=lazy; width="1200px" }

## Next step

Now that you have connected a data source and initiated data assessment, it is time to 
[review the results, automatically configure data quality checks to detect the most common data quality issues and create an incident notification](review-results-and-run-monitoring-checks.md).