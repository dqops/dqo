# DQOps data lineage integration with Marquez
This guide shows the data lineage integration between DQOps and Marquez.

## Overview

DQOps allows the existing data lineages to be loaded from Marquez with no manual setup required from the user. 
The example shows how the table-level lineage can be used for an immediate search for the root cause of the issue.

## Example dataset

The example dataset is a three-stage pipeline solution of a _sales platform_ that transforms customers' data,
that is used in business intelligence tools to generate insights about customer demographics, location-based trends, and other metrics.

Data is ingested, cleansed, and finally loaded into a data mart for reporting and analytics, through three main stages.

Landing Stage: Contains raw data ingested from various sources for each market in tables:
- **customer_address_ca_raw**, 
- **customer_address_us_raw**, 
- **customers_ca_raw**, 
- **customers_us_raw**.

Cleansing Stage: Data is standardized, de-duplicated, and merged into unified tables: 
- **customers**, 
- **customer_address**.

Data mart Stage: Contains the most refined form of the table in a dimensional representation of 
customer data that combines the cleaned customer details and addresses from the Cleansing stage, structured to support efficient querying for analytics:
- **dim_customers**.


## Marquez data representation

The table linkage is shown on the screen below.

![marquez-data-lineage](https://dqops.com/docs/images/integrations/data-lineage/marquez/marquez-data-lineage.png)


## DQOps data lineage

DQOps supports both, table and column-level data lineage. The data lineage configuration can be performed in different ways.
You can manually point the source tables and create mappings between tables' columns.
When using Marquez, the data lineage can be imported automatically to DQOps.

The dim_customers table depends on data from the source tables. 
It means that the data quality issues present in this table can be propagated from the source tables.

Searching for an issue in tables created with a complex pipeline takes time. 
This search may be repeated after each pipeline fix to exclude the presence of the issue.
The strength of using the data lineage in DQOps allows us to find the root cause of the data quality issue immediately.

### Searching for the issue

In this example, the tables have set up the data quality checks on various dimensions.

To verify the table quality status, open the overall table summary page.

Here, a uniqueness issue can be observed in the dim_customers table from the data mart.

![dqops-data-lineage-table-summary](https://dqops.com/docs/images/integrations/data-lineage/marquez/dqops-data-lineage-table-summery.png)

The data lineage is imported from Marquez, so we can search for the issues in the source tables.

To open the **Data lineage** tab, first select the target table in the tree view.

This page presents the source tables' status. When expanding the source tables you can see the next-level source tables' quality statuses that influence your target table.

The below screen shows the expanded source tables. You can see that the observed issue comes from the **customers_ca_raw** table at the Ingestion stage schema named **landing**.


![dqops-data-lineage](https://dqops.com/docs/images/integrations/data-lineage/marquez/dqops-data-lineage2.png)



!!! note "How to activate data lineage synchronization"

    Data lineage synchronization is a paid feature of DQOps. Please [contact DQOps sales](https://dqops.com/contact-us) and schedule a call.

