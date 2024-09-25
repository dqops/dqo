# DQOps data lineage integration with Marquez
This guide shows the data lineage integration between DQOps and Marquez.

## Overview

The example dataset is a three-stage pipeline solution for a sales platform that transforms customers data, 
that is used in business intelligence tools to generate insights about customer demographics, location-based trends, and other metrics.

The strength of using the data lineage in DQOps allows us to find the root cause of the data quality issue immediately.

## Example dataset

This dataset is part of a data pipeline of a _sales platform_ where information is ingested, cleansed, and finally loaded into a data mart 
for reporting and analytics, through three main stages: Landing, Cleansing, and Data mart.

Landing Stage: The Landing schema contains raw data ingested from various sources for each market.

- **customer_address_ca_raw:** Raw customer address data for Canadian customers.
- **customer_address_us_raw:** Raw customer address data for US customers.
- **customers_ca_raw:** Raw customer details (e.g., name, email) for Canadian customers.
- **customers_us_raw:** Raw customer details for US customers.

Cleansing Stage: In this stage, data from the Landing schema is standardized, de-duplicated, and merged into unified tables:

- **customers:** A consolidated, cleansed version of customer details from both Canada and the US.
- **customer_address:** A unified table that stores cleansed customer addresses from both regions.

Data mart Stage: The final, most refined stage in the pipeline where data is aggregated into a dimensional model for reporting:

- **dim_customers:** This table is a dimensional representation of customer data that combines the cleaned customer details and addresses from the Cleansing stage, structured to support efficient querying for analytics.


### Marquez data representation

The table linkage is shown on the screen below

![marquez-data-lineage](https://dqops.com/docs/images/integrations/data-lineage/marquez/marquez-data-lineage.png)


### DQOps data lineage

The tables have set up the data quality checks on various dimensions. 

One time a uniqueness issue was observed in the dim_customers table from data mart.

![dqops-data-lineage-table-summary](https://dqops.com/docs/images/integrations/data-lineage/marquez/dqops-data-lineage-table-summery.png)


As the data lineage is synchronized, the **Data lineage** tab can be opened to search for the source tables' quality status.
By expanding the source tables, it is present that the observed issue comes from the **customers_ca_raw** table at the Ingestion stage schema named **landing**.


![dqops-data-lineage](https://dqops.com/docs/images/integrations/data-lineage/marquez/dqops-data-lineage.png)


DQOps supports both, table and column level data lineage.


!!! note "How to activate data lineage synchronization"

    Data lineage synchronization is a paid feature of DQOps. Please [contact DQOps sales](https://dqops.com/contact-us) and schedule a call.

