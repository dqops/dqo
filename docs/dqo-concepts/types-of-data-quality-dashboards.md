---
title: Types of Data Quality Dashboards - Examples and Best Practices
---
# Types of Data Quality Dashboards - Examples and Best Practices
Data quality dashboards provide a centralized view of key metrics, allowing data teams to monitor and improve the health and reliability of their data.

## Overview

DQOps has multiple built-in data quality dashboards for displaying [data quality KPI](definition-of-data-quality-kpis.md).
DQOps dashboards use [Looker Studio](https://lookerstudio.google.com/overview) (formerly Google Data Studio) business 
intelligence environment. We chose Looker Studio because there is no per-user license fee, which allows granting access
to all parties that might be involved in the issue resolution process.

All data quality results are synchronized to a private data quality data warehouse in the Google Cloud. 
Data quality projects implemented with DQOps receive a complementary Looker Studio instance connected to a data quality 
data warehouse. DQOps customers can ask the vendor to access a custom Looker Studio data source, providing direct access 
to the data quality lakehouse.

!!! info "Complimentary data quality data warehouse for FREE accounts"

    DQOps provides a complimentary [data quality data lake](architecture/dqops-architecture.md#data-quality-data-lake)
    and [data quality data warehouse](architecture/dqops-architecture.md#data-quality-data-warehouse) for users
    who are using a **FREE** license of DQOps Cloud.
    
    Due to capacity constraints, a limited number of new users who
    [register a FREE DQOps Cloud account](https://cloud.dqops.com/registration) are granted access to the
    data warehouse, allowing them to synchronize data quality results with the data quality data warehouse and
    use the data quality dashboards for free. New grants are released every day.


## Navigating dashboards

To view dashboards, simply go to the **Data quality dashboard** section, and select the dashboard of interest from the tree
view on the left. There are several groups and subgroups of data quality dashboards dedicated to analyzing results from 
data quality checks. 

For example, to review the summary KPIs of all executed monitoring checks you can use the **KPIs scorecard - summary** dashboard.
You can find it in the **Monitoring** group, **Data quality KPIs** subgroup.

![KPIs scorecard - summary](https://dqops.com/docs/images/working-with-dqo/data-quality-dashboards/kpis-scorecard-dashboards3.png){ loading=lazy; width="1200px" }

When you hover over the name of the dashboard in the tree view, thumbnails are displayed to help you identify the
type of dashboard.

### **Switching view between basic and advanced dashboards**

In DQOps the dashboards are divided into two groups: basic and advanced dashboards.

The toggle button **Show advanced dashboards** at the top of the tree view allows to toggle between
displaying only basic dashboards or both basic and more advanced dashboards.

![Advanced dashboards toggle button](https://dqops.com/docs/images/working-with-dqo/data-quality-dashboards/advanced-dashboards-toggle-button.png){ loading=lazy }


### **Filtering**

On each dashboard, data can be filtered. You can filter data by clicking on the dropdown buttons located on the dark gray top part
of the dashboard, or clicking on the elements in the tables such as the Connection, Schema, Dimension, Data group, etc.
Some dashboards have additional filtering options such as checks boxes or input fields.

![Dashboards filtering](https://dqops.com/docs/images/concepts/data-quality-dashboards/dashboards-filtering.png){ loading=lazy; width="1200px" }

The following example shows the filtering of data by a connection named **bigquery-public-data** after clicking on it.

![Dashboards - connection filtering example](https://dqops.com/docs/images/concepts/data-quality-dashboards/dashboards-connection-filtering-example.png){ loading=lazy; width="1200px" }

Data on dashboards is displayed for a specific time range, most often for the current month.
Depending on the type of dashboard, you can change the specific time range using **Time window** dropdown button located
on the top part of the dashboard.

![Time window filtering](https://dqops.com/docs/images/concepts/data-quality-dashboards/dashboard-time-window-filtering.png){ loading=lazy; width="1200px" }

Other dashboards have buttons to change the view between the current and previous month.

![Current/previous month switch](https://dqops.com/docs/images/concepts/data-quality-dashboards/dashboards-current-previous-month-switch.png){ loading=lazy; width="1200px" }

## Profiling, Monitoring, and Partitions groups

Profiling, Monitoring, and Partitions groups each contain subgroups for analyzing results from profiling, monitoring, 
and partition check types, respectively.

[**Profiling checks**](definition-of-data-quality-checks/data-profiling-checks.md) are useful for exploring and experimenting with 
various types of checks and determining the most suitable ones for regular data quality monitoring. When the profiling 
data quality check is run, only one sensor readout is saved per month. That is why profiling group does not have 
dashboards displaying data per day of the month or histograms.

[**Monitoring checks**](definition-of-data-quality-checks/data-observability-monitoring-checks.md) are standard checks that monitor the data quality of
a table or column. There are two categories of monitoring checks: daily checks and monthly checks. The daily monitoring 
checks store the most recent sensor readouts for each day when the data quality check was run. This means that if you run
a check several times a day only the most recent readout is stored. The previous readouts for that day will be overwritten.

[**Partition checks**](definition-of-data-quality-checks/partition-checks.md) are designed to measure the data quality in partitioned
data. In contrast to monitoring checks, partition checks produce separate monitoring results for each partition. 
There are two categories of partition checks: daily checks and monthly checks. The daily partition checks store the most
recent sensor readouts for each partition and each day when the data quality check was run. This means that if you run 
check several times a day only the most recent readout is stored. The previous readouts for that day will be overwritten.


Below are the list of major subgroups within Profiling, Monitoring, and Partitions groups

### **Current status**

**Current status** dashboards allow data engineers and data owners to quickly evaluate the data quality of monitored
tables and columns. The dashboards display a color-coded status that indicates the severity level detected by run
checks. When the status is green, it means that the monitored column has no data quality issues. However, if the status
is yellow, orange, or red, it indicates that there were some issues detected. The dashboard also displays the number
of detected issues per severity threshold, making it easier to identify and address tables and columns with issues.

These dashboards allow filtering data by:

* time frame, 
* connection,
* priority
* schema,
* data group
* stage
* data quality dimension,
* check category,
* table, 
* column.
 
**Current table status** dashboard is a representative of this group.

![Current table status](https://dqops.com/docs/images/concepts/data-quality-dashboards/current-table-status3.png){ loading=lazy; width="1200px" }


### **Highest issue severity per day**

**Highest issue severity per day** advanced dashboards allow for reviewing and filtering a summary number of issues that arise 
from data quality checks per day. Depending on the dashboard, the summary is grouped by table, column, 
[data grouping](measuring-data-quality-with-data-grouping.md), or [check category](../checks/index.md).
These dashboards help evaluate the areas with the highest number of data quality issues that should be addressed. 
It also allows to review how the issue severity changed per day of the month.

These dashboards allow filtering data by:

* current and previous month,
* connection,
* schema,
* data group,
* data quality dimension,
* check category,
* check name,
* table,
* column.

**Highest issue severity per table and day** is a representative of this group.

![Highest issue severity per table and day](https://dqops.com/docs/images/concepts/data-quality-dashboards/highest-issue-severity-per-table-and-day.png){ loading=lazy; width="1200px" }


### **Data quality issues count**

**Data quality issues count** advanced dashboards allow evaluation of the number issues and its severity level per table, or per check category. 
Another two types of dashboards in this subgroup allows reviewing the total number of failed checks (warning, error, fatal)
per day of the month, or per table and day of the month.

**Data quality failed checks per table and day** is a representative of this group.

This dashboard allows filtering data by:

* severity
* current and previous month,
* connection,
* schema,
* stage
* priority,
* data group,
* data quality dimension,
* check category,
* check name,
* table,
* day of the month.

![Data quality failed checks per table and day](https://dqops.com/docs/images/concepts/data-quality-dashboards/data-quality-failed-checks-per-table-and-day.png){ loading=lazy; width="1200px" }


### **Data quality KPIs**

**Data quality KPIs** dashboards show high-level data quality KPIs, aggregated on a macro scale that can be shared at a 
corporate level. DQOps calculates data quality KPIs as a percentage of passed [data quality checks](definition-of-data-quality-checks/index.md) 
for each connection, schema, data grouping, etc.

With these dashboards, senior management can review a summary of data quality KPIs or evaluate the KPIs per table, column, 
[data quality dimensions](data-quality-dimensions.md), 
[check category](../checks/index.md), [data grouping](measuring-data-quality-with-data-grouping.md) and day.

**KPIs per table and day** is a representative of advanced dashboards in this group. It allows reviewing data quality KPIs per table
and day of the month. 

This dashboard allows filtering data by:

* current and previous month,
* connection,
* schema,
* data group,
* stage,
* priority,
* data quality dimension,
* check category,
* check name,
* column
* table,
* day of the month.

![KPIs per table and day](https://dqops.com/docs/images/concepts/data-quality-dashboards/kpis-per-table-and-day.png){ loading=lazy; width="1200px" }


### **Check results**

**Check results** advanced dashboards allow to review the details of the executed checks results.

**History of data quality check results** is a representative of this group. This dashboard displays all executed
checks run on tables and columns and allows reviewing their set parameters, as well as actual and expected values.

This dashboard allows filtering data by:

* time window (from last 7 days to last 6 months)
* connection,
* schema,
* data group,
* data quality dimension,
* check category,
* stages,
* priorities,
* table,
* column,
* check name,
* issue severity
* day

![History of data quality check results](https://dqops.com/docs/images/concepts/data-quality-dashboards/history-of-data-quality-check-results.png){ loading=lazy; width="1200px" }


### **Data Quality Dimensions**

**Data Quality Dimensions** group contains dashboards dedicated for different data quality dimension such as availability,
completeness, or timeliness. 

**Availability**, **Completeness**, and **Validity** dimensions has at least two dashboards. Basic dashboard
dedicated to reviewing current issues and the advanced dashboard for reviewing the history of issues. 


**Current completeness issues on columns** is a representative of **Completeness** subgroup. This dashboard summarizes
results from most recently executed null checks on columns ([null_count](../checks/column/nulls/nulls-count.md), [null_percent](../checks/column/nulls/nulls-percent.md),
[not_nulls_count](../checks/column/nulls/not-nulls-count.md) and [not_nulls_percent](../checks/column/nulls/not-nulls-percent.md)).

This dashboard allows filtering data by:

* time window (from last 7 days to last 3 months)
* connection,
* schema,
* data group,
* check category,
* check name,
* stages,
* priorities,
* table,
* column,
* issue severity.


![Current completeness issues on columns](https://dqops.com/docs/images/concepts/data-quality-dashboards/current-completeness-issues-on-columns2.png){ loading=lazy; width="1200px" }


**Timeliness** dimensions has specialized dashboards for evaluating:

* Current timeliness issues
* History of timeliness issues
* Table freshness - tables with the most current data,
* Table freshness - tables with the oldest data,
* Table staleness - tables most recently loaded
* Table staleness - tables no longer loaded
* History of table freshness
* Minimum, maximum and average delay

**Table freshness - tables with the oldest data** is a representative of **Timeliness** subgroup. This dashboard summarizes
tables with the longest delay measured by the [table_freshness](../checks/table/timeliness/data-freshness.md) check.

This dashboard allows filtering data by:

* time window (from last 7 days to last 3 months)
* connection,
* schema,
* data group,
* stages,
* priorities,
* check name,
* table.

![Table freshness - tables with the oldest data](https://dqops.com/docs/images/concepts/data-quality-dashboards/table-freshness-tables-with-the-oldest-data.png){ loading=lazy; width="1200px" }


### **Volume**

**Volume** dashboards in the **Monitoring** group help to evaluate the largest tables by number of rows and empty
or too small tables. While dashboards in the **Partitions** group (**Partition volume statistics**) display daily 
partitions row count and too small partitions.

**Largest tables by number of rows** is a representative of **Volume** subgroup. This dashboard displays tables monitored
with [row_count](../checks/table/volume/row-count.md) check and allows review the number of rows in these tables. 

This dashboard allows filtering data by:

* time window (from last 7 days to last 3 months)
* row count
* connection,
* schema,
* data group,
* stages,
* table.

![Largest tables by number of rows](https://dqops.com/docs/images/concepts/data-quality-dashboards/largest-tables-by-number-of-rows.png){ loading=lazy; width="1200px" }


### **PII**

**PII** dashboards display results from column checks detecting the percentage of Personal Identifiable Information in data.
PII includes phone numbers ([contains_usa_phone_percent](../checks/column/pii/contains-usa-phone-percent.md)),
emails ([contains_email_percent](../checks/column/pii/contains-email-percent.md)),
zipcodes ([contains_usa_zipcode_percent](../checks/column/pii/contains-usa-zipcode-percent.md)), or
IP addresses ([contains_ip4_percent](../checks/column/pii/contains-ip4-percent.md),
[contains_ip6_percent](../checks/column/pii/contains-ip6-percent.md)).

**PII data detected** dashboard is a representative of PII subgroup.  This dashboard displays columns monitored with PII checks mentioned above.

This dashboard allows filtering data by:

* time window (from last 7 days to last 6 months)
* connection,
* schema,
* data group,
* check name,
* severity
* stages,
* priority,
* table,
* column.

![PII data detected dashboard](https://dqops.com/docs/images/concepts/data-quality-dashboards/pii-data-detected-dashboard.png){ loading=lazy; width="1200px" }

### **Schema changes**

**Schema changes** dashboards allow to view tables with schema change issues. There are several dashboards dedicated to
schema change:
* Table-level issues
    - Summary of changes in columns
    - Expected vs actual column count
    - Column count changed
    - Column list changed
    - Column list or order changed
    - Column types changed
* Column-level issues
    - Column exists
    - Column type changed

**Summary of changes in columns** is a representative of Schema changes group. This dashboard summarizes issues obtained
from all table-level schema change checks ([column count](../checks/table/schema/column-count.md),
[column count changed](../checks/table/schema/column-count-changed.md), [column list changed](../checks/table/schema/column-list-changed.md),
[column list or order changed,](../checks/table/schema/column-list-or-order-changed.md),
[column types changed](../checks/table/schema/column-types-changed.md)). 

This dashboard allows filtering data by:

* time window (from last 7 days to last 6 months)
* connection,
* schema,
* stages,
* priorities,
* check name, 
* severity
* table.

![Schema changes - summary of changes in columns](https://dqops.com/docs/images/concepts/data-quality-dashboards/schema-changes-summary-of-changes-in-columns2.png){ loading=lazy; width="1200px" }

## DQOps usage

**DQOps usage** group contains dashboards summarizing executed checks, executed errors and checks no longer in use.


### **Statistics of executed checks** 

**Statistics of executed checks** subgroup of dashboards contains summaries of executed checks per table, column, 
[data grouping](measuring-data-quality-with-data-grouping.md), and [check category](../checks/index.md).

### **Execution errors**

**Execution errors** dashboards allow to view details and history of execution errors.

**Details of execution errors** is a representative of this group. It allows to view detail source of the error (table, column and check),
time when the error occurred and detailed error message. It also displays daily distribution of errors.

This dashboard allows filtering data by:

* time window (from last 7 days to last 6 months)
* connection,
* schema,
* data group,
* table,
* column, 
* dimension, 
* check type, 
* check category,
* check name.

![Details of execution errors](https://dqops.com/docs/images/concepts/data-quality-dashboards/details-of-execution-errors.png){ loading=lazy; width="1200px" }

### **Checks no longer in use**

**Checks no longer in use - summary table** dashboard shows summary tables of checks that are no longer in use.

This dashboard allows filtering data by:

* time window (older than one month to older than a year)
* connection,
* table,
* column
* check type,
* quality dimension,
* check category.

![Checks no longer in use - summary tables dashboard](https://dqops.com/docs/images/concepts/data-quality-dashboards/checks-no-longer-in-use.png){ loading=lazy; width="1200px" }

## FAQ
The following knowledgebase answers the most popular questions about data quality dashboards. 

### What are data quality dashboards?
Data quality dashboards are interactive tools that visually track and analyze key data quality metrics. 
Think of them as a central hub for all your data quality information. 
They provide an overview of the health and reliability of your data,  presented in a way that's easy to understand and act on.

These dashboards are used by various teams within an organization:

* **Data engineers** rely on them to monitor data pipelines and pinpoint any issues affecting data reliability.
* **Data analysts** use them to verify the trustworthiness of datasets before using them for analysis.
* **Data owners** need them to ensure their databases meet data quality standards and quickly address any problems.

Dashboards can present data quality metrics for various levels of granularity: 
from entire data sources down to individual datasets, tables, files, or even columns. 
They track these metrics across different data quality dimensions like accuracy, completeness, consistency, timeliness, and validity.

Perhaps most importantly, data quality dashboards provide historical context. 
By displaying trends over time, they show how data quality is improving (or declining) and highlight areas that need attention. 
This allows teams to proactively address data quality issues and prevent them from impacting business decisions.

### What are data quality dashboard examples?
Data quality dashboards can be implemented on various levels within an organization to provide different perspectives on data health. 
Here are a few examples:

*  **Scorecard dashboards** offer a high-level overview, like a bird's-eye view of the current data quality status for each data source. 
   These are useful for executives and managers who need a quick understanding of overall data health.

*  **Operational dashboards** dive deeper, showing a prioritized list of tables with the lowest data quality scores. 
   This helps data stewards and engineers focus their efforts on the areas that need the most attention. 
   Imagine a dashboard that flags tables with the most missing values or those that haven't been updated recently.

*  **Detailed dashboards** provide the most granular information, useful for root cause analysis of specific data quality problems. 
   These might include:

     * **Data volume history**: showing trends in data volume over time to identify anomalies or inconsistencies.
     * **Open data quality issues**: a list of logged issues with their current status and assigned owners.
     * **Data freshness metrics**: highlighting tables with outdated data that might need refreshing.

### What is a data quality monitoring dashboard?
Data quality monitoring dashboards provide a concise overview of the health of your most important datasets. 
They display data quality scores across various dimensions (completeness, accuracy, etc.), 
highlight potential problems like missing values or outdated tables, and help identify unreliable data pipelines.  
Data teams use these dashboards to understand data health, plan improvements, and proactively address issues.

### What is a data quality metrics dashboard?
Data quality metrics dashboards focus on detailed data quality metrics for databases, sources, and tables.
They display metrics across various data quality dimensions (completeness, timeliness, etc.) and may use advanced formulas 
like data quality KPIs to show aggregated data quality scores. This helps pinpoint specific areas needing improvement.

### What is a data quality dashboard template?
A data quality dashboard template is a pre-built dashboard with predefined metrics and visualizations, often connected to a data quality metrics database. 
Good templates are easy to customize, allowing you to add or remove metrics, adjust visualizations, and adapt to different data domains. 
For example, DQOps offers over 50 customizable templates.

### What is a data quality KPI dashboard?
A data quality KPI dashboard tracks the overall health of your data using Key Performance Indicators (KPIs). 
It presents a single, aggregated KPI score for each data asset (like a data source or database) and 
also breaks down the score by data quality dimensions (completeness, timeliness, etc.). 
This helps you quickly identify areas with low scores and pinpoint issues like outdated tables or incomplete data.

### How to make a data quality dashboard in Power BI?
To make a data quality dashboard in Power BI, you need a data quality metrics database,
a defined data model, and visualizations for data sources and tables. 
Define your data quality KPI formula and measure it for dimensions like completeness. 
DQOps can simplify this by collecting and storing metrics in a data lake that you can replicate for Power BI.

### How to measure data quality in a data warehouse?
Measuring data quality in a data warehouse requires a multi-layered approach:

* **Landing Zone**: Track timeliness, completeness, and validity of raw data as it enters the warehouse.
* **Cleansing Zone**: Verify consistency and accuracy after data cleansing and transformation.
* **Fact and Dimension Tables**: Use data reconciliation checks to ensure accuracy and consistency between related tables.

Monitor these layers with a data observability tool and track key metrics on data quality dashboards for a comprehensive view of data health.

## What's next

- Check the [Data quality KPIs concept guide](./definition-of-data-quality-kpis.md), to learn how to measure the data quality KPI score with DQOps, what is the formula used to calculate the score, and how to improve data quality KPIs.
- DQOps allows you to [create custom data quality dashboards using Looker Studio](../integrations/looker-studio/creating-custom-data-quality-dashboards.md). Check the guide to learn more.
- You might also be interested in learning [how to use data quality dashboards](../working-with-dqo/review-the-data-quality-results-on-dashboards.md).