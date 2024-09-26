# DQOps data lineage integration with Marquez
This guide shows the data lineage integration between DQOps and Marquez.

## Overview

DQOps enables the loading of existing data lineages from Marquez without any manual setup required from the user.
This example shows how table-level lineage can be used to quickly search for the root cause of an issue.

## Example dataset

To illustrate integration of DQOps with Marquez, let's consider a sales platform that processes customer data through three stages:

1. **Landing Stage**: Raw data is ingested from various sources (e.g., `customer_address_ca_raw`, `customers_ca_raw`).
2. **Cleansing Stage**: Data is standardized, de-duplicated, and merged into unified tables (`customers`, `customer_address`).
3. **Data Mart Stage**: Cleaned data is transformed into a dimensional representation for analytics (`dim_customers` table).

## Marquez data representation
Marquez provides a visual representation of the data lineage, allowing you to easily understand the relationships between
tables and their dependencies. The image below shows an example of how Marquez might represent the data lineage for our sales pipeline.

![marquez-data-lineage](https://dqops.com/docs/images/integrations/data-lineage/marquez/marquez-data-lineage.png)


## DQOps data lineage

DQOps supports both table-level and column-level data lineage. 
You can either manually configure mappings or **import lineage directly from Marquez**.

The `dim_customers` table depends on data from the source tables. 
Therefore, any data quality issues present in `dim_customers` table may have originated from the source tables.

Searching for issues in tables created using a complex pipeline can be time-consuming.  
This search may need to be repeated after each pipeline fix to ensure that the issue has been resolved. .
Utilizing data lineage in DQOps enables us to quickly identify the root cause of any data quality issues

### Using data lineage in DQOps

In this example, data quality checks have been set up on various dimensions for the tables.

To check the quality status of the tables in the schema, select the schema from the tree view and click in the **Data quality summary** tab.

An issue with uniqueness can be observed in the `dim_customers` table from the data mart.

![dqops-data-lineage-table-summary](https://dqops.com/docs/images/integrations/data-lineage/marquez/dqops-data-lineage-table-summery.png)

Now, we can view the **Data lineage** which has been imported in our example from Marquez. 

First, click on the target table in the tree view on the left and select the **Data lineage** tab.

The **Data lineage** screen displays the status of the source tables.
The circles represent the results from data quality checks for at least three dimensions: Completeness, Validity, and Consistency. 
The color of the circle represents the results: white for no checks were run, green for correct results, orange for error results, 
red for fatal error results, and grey for check execution errors. Hovering over a circle will display more details.

When you expand the source tables, you can see the quality statuses of the next-level source tables that influence your target table, as shown on the screen below.
You can see that the observed issue comes from the `customers_ca_raw` table in the Ingestion stage schema named **landing**.

![dqops-data-lineage](https://dqops.com/docs/images/integrations/data-lineage/marquez/dqops-data-lineage2.png)


!!! note "Activating data lineage synchronization"

    Data lineage synchronization is a paid feature. Please [contact DQOps sales](https://dqops.com/contact-us) for more information.

