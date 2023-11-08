# Data quality dashboards

DQOps has multiple built-in data quality dashboards for displaying [data quality KPI](../data-quality-kpis/data-quality-kpis.md).
Our dashboards use [Looker Studio](https://lookerstudio.google.com/overview) (formerly Google Data Studio) business 
intelligence environment. We chose Looker Studio because there is no per-user license fee, which allows granting access
to all parties that might be involved in the issue resolution process.

All data quality results are synchronized to a private data quality data warehouse in the Google Cloud. 
Data quality projects implemented with DQOps receive a complementary Looker Studio instance connected to a data quality 
data warehouse. DQOps customers can ask the vendor to access a custom Looker Studio data source, providing direct access 
to the data quality lakehouse.

## Navigating dashboards

To view dashboards, simply go to the **Data Quality Dashboard** section, and select the dashboard of interest from the tree
view on the left. There are several groups and subgroups of data quality dashboards dedicated to analyzing results from 
data quality checks.

For example, to review the summary KPIs of all run monitoring checks you can use the **KPIs scorecard - summary** dashboard.
You can find it in the **Monitoring** group, **Data quality KPIs** subgroup.

![KPIs scorecard - summary](https://dqops.com/docs/images/working-with-dqo/data-quality-dashboards/kpis-scorecard-dashboards.png)

On each dashboard, data can be filtered. You can filter data by clicking on the filters on the top part of the dashboard,
as well as clicking on the elements in the tables such as the connection, schema, data group, etc.

## Profiling, Monitoring, and Partitions groups

Profiling, Monitoring, and Partitions groups each contain subgroups for analyzing results from profiling, monitoring, 
and partition check types, respectively.

### **Current table status**

**Current table status** dashboards allow data engineers and data owners for viewing the data quality statuses of the 
tables and columns grouped by [data quality dimensions](../data-quality-dimensions/data-quality-dimensions.md) or check 
categories. Dashboards within this group also allow for a quick evaluation of the current issue severity status per table,
column, data quality dimension, [data grouping](../data-grouping/data-grouping.md), 
[check category](../../checks/#categories-of-checks), and per day of the month.

**Current table status per data quality dimension** dashboard is a representative of Current table status group.

![Current table status per data quality dimension](https://dqops.com/docs/images/concepts/data-quality-dashboards/current-table-status-per-data-quality-dimension.png)


### **Highest issue severity per day**

**Highest issue severity per day** dashboards allow for reviewing and filtering a summary number of issues that arise 
from data quality checks per day. The summary can be categorized by table, column, 
[data grouping](../data-grouping/data-grouping.md), or [check category](../../checks/#categories-of-checks).
These dashboards help data engineers and data owners identify areas (tables or data pipelines) with the highest 
number of data quality issues that should be addressed.

**Highest issue severity per table and day** is a representative of Highest issue severity per day group.

![Highest issue severity per table and day](https://dqops.com/docs/images/concepts/data-quality-dashboards/highest-issue-severity-per-table-and-day.png)


### **Data quality issues count**

**Data quality issues count** dashboards display number of issues or failed checks per table or per check.

**Data quality failed checks per table and day** is a representative of Data quality issues count group.

![Data quality failed checks per table and day](https://dqops.com/docs/images/concepts/data-quality-dashboards/data-quality-failed-checks-per-table-and-day.png)


### **Data quality KPIs**

**Data quality KPIs** dashboards show high-level data quality KPIs, aggregated on a macro scale that can be shared at a 
corporate level. With these dashboards, senior management can review data quality KPIs per table, column, 
[data quality dimensions](../data-quality-dimensions/data-quality-dimensions.md), 
[check category](../../checks/#categories-of-checks), [data grouping](../data-grouping/data-grouping.md) and day.

**KPIs per table and day** is a representative of Data quality KPIs group.

![KPIs per table and day](https://dqops.com/docs/images/concepts/data-quality-dashboards/kpis-per-table-and-day.png)


### **Check results**

**Check results** dashboards allows to review the details of the issues resulted from executed checks.

**History of data quality check results** is a representative of Check results group.

![History of data quality check results](https://dqops.com/docs/images/concepts/data-quality-dashboards/history-of-data-quality-check-results.png)


### **Data Quality Dimensions**

**Data Quality Dimensions** group contains dashboards dedicated for different data quality dimension such as availability,
completeness, timeliness or volume.

**Current completeness issues on columns** is a representative of **Completeness** subgroup.

![Current completeness issues on columns](https://dqops.com/docs/images/concepts/data-quality-dashboards/current-completeness-issues-on-columns.png)


**Largest tables by number of rows** is a representative of **Volume** subgroup.

![Largest tables by number of rows](https://dqops.com/docs/images/concepts/data-quality-dashboards/largest-tables-by-number-of-rows.png)


### **PII**

**PII** dashboards display results from checks detecting Personal Identifiable Information data such as phone numbers, emails, zipcodes, or IP addresses.

### **Schema changes**

**Schema changes** dashboards allow to view tables with changes in the number, list, or order of columns.

**Schema changes - summary of changes in columns** is a representative of Schema changes group.

![Schema changes - summary of changes in columns](https://dqops.com/docs/images/concepts/data-quality-dashboards/schema-changes-summary-of-changes-in-columns.png)

## DQOps usage

**DQOps usage** group contains dashboards summarizing executed checks, executed errors and checks no longer in use.


### **Statistics of executed checks** 

**Statistics of executed checks** subgroup of dashboards contains summaries of executed checks per table, column, 
[data grouping](../data-grouping/data-grouping.md), and [check category](../../checks/#categories-of-checks).

### **Execution errors**

**Execution errors** dashboards allow to view details and history of execution errors.

**Details of execution errors** is a representative of Execution errors group.

![Details of execution errors](https://dqops.com/docs/images/concepts/data-quality-dashboards/details-of-execution-errors.png)

### **Checks no longer in use**

**Checks no longer in use** dashboards show summaries of checks that are no longer in use.


## Aggregated results for all check types

**Aggregated results for all check types** group contains issues counts and KPIs aggregates for all check types 
(profiling, monitoring, and partitions).