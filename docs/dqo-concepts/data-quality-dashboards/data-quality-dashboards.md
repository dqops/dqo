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

For example, to review the summary KPIs of all executed monitoring checks you can use the **KPIs scorecard - summary** dashboard.
You can find it in the **Monitoring** group, **Data quality KPIs** subgroup.

![KPIs scorecard - summary](https://dqops.com/docs/images/working-with-dqo/data-quality-dashboards/kpis-scorecard-dashboards.png)

When you hover over the name of the dashboard in the tree view, thumbnails are displayed to help you identify the 
type of dashboard.

### **Filtering**

On each dashboard, data can be filtered. You can filter data by clicking on the dropdown buttons located on the dark grey top part
of the dashboard, or clicking on the elements in the tables such as the Connection, Schema, Dimension, Data group, etc.
Some dashboards have additional filtering options such as checks boxes or input fields.

![Dashboards filtering](https://dqops.com/docs/images/concepts/data-quality-dashboards/dashboards-filtering.png)

The following example shows the filtering of data by a connection named **bigquery-public-data** after clicking on it.

![Dashboards - connection filtering example](https://dqops.com/docs/images/concepts/data-quality-dashboards/dashboards-connection-filtering-example.png)

Data on dashboards is displayed for a specific time range, most often for the current month.
Depending on the type of dashboard, you can change the specific time range using **Time window** dropdown button located
on the top part of the dashboard.

![Time window filtering](https://dqops.com/docs/images/concepts/data-quality-dashboards/dashboard-time-window-filtering.png)

Other dashboards have buttons to change the view between the current and previous month.

![Current/previous month switch](https://dqops.com/docs/images/concepts/data-quality-dashboards/dashboards-current-previous-month-switch.png)

## Profiling, Monitoring, and Partitions groups

Profiling, Monitoring, and Partitions groups each contain subgroups for analyzing results from profiling, monitoring, 
and partition check types, respectively.

[**Profiling checks**](../checks/profiling-checks/profiling-checks.md) are useful for exploring and experimenting with 
various types of checks and determining the most suitable ones for regular data quality monitoring. When the profiling 
data quality check is run, only one sensor readout is saved per month. That is why profiling group does not have 
dashboards displaying data per day of the month or histograms.

[**Monitoring checks**](../checks/monitoring-checks/monitoring-checks.md) are standard checks that monitor the data quality of
a table or column. There are two categories of monitoring checks: daily checks and monthly checks. The daily monitoring 
checks store the most recent sensor readouts for each day when the data quality check was run. This means that if you run
a check several times a day only the most recent readout is stored. The previous readouts for that day will be overwritten.

[**Partition checks**](../checks/partition-checks/partition-checks.md) are designed to measure the data quality in partitioned
data. In contrast to monitoring checks, partition checks produce separate monitoring results for each partition. 
There are two categories of partition checks: daily checks and monthly checks. The daily partition checks store the most
recent sensor readouts for each partition and each day when the data quality check was run. This means that if you run 
check several times a day only the most recent readout is stored. The previous readouts for that day will be overwritten.


Below are the list of major subgroups within Profiling, Monitoring, and Partitions groups

### **Current table status**

**Current table status** dashboards allow data engineers and data owners to quickly evaluate the data quality of monitored
tables and columns. The dashboards display a color-coded status that indicates the severity level detected by run
checks. When the status is green, it means that the monitored column has no data quality issues. However, if the status
is yellow, orange, or red, it indicates that there were some issues detected. The dashboard also displays the number
of detected issues per severity threshold, making it easier to identify and address tables and columns with issues.

These dashboards allow filtering data by:

* time frame, 
* connection,
* schema,
* data quality dimension,
* check category,
* data group,
* table, 
* column.
 
**Current table status per data quality dimension** dashboard is a representative of this group.

![Current table status per data quality dimension](https://dqops.com/docs/images/concepts/data-quality-dashboards/current-table-status-per-data-quality-dimension.png)


### **Highest issue severity per day**

**Highest issue severity per day** dashboards allow for reviewing and filtering a summary number of issues that arise 
from data quality checks per day. Depending on the dashboard, the summary is grouped by table, column, 
[data grouping](../data-grouping/data-grouping.md), or [check category](../../checks/#categories-of-checks).
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

![Highest issue severity per table and day](https://dqops.com/docs/images/concepts/data-quality-dashboards/highest-issue-severity-per-table-and-day.png)


### **Data quality issues count**

**Data quality issues** dashboards allows evaluation of the number issues and its severity level per table, or per check category. 
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

![Data quality failed checks per table and day](https://dqops.com/docs/images/concepts/data-quality-dashboards/data-quality-failed-checks-per-table-and-day.png)


### **Data quality KPIs**

**Data quality KPIs** dashboards show high-level data quality KPIs, aggregated on a macro scale that can be shared at a 
corporate level. DQOps calculates data quality KPIs as a percentage of passed [data quality checks](../checks/index.md) 
for each connection, schema, data grouping, etc.

With these dashboards, senior management can review a summary of data quality KPIs or evaluate the KPIs per table, column, 
[data quality dimensions](../data-quality-dimensions/data-quality-dimensions.md), 
[check category](../../checks/#categories-of-checks), [data grouping](../data-grouping/data-grouping.md) and day.

**KPIs per table and day** is a representative of this group. It allows reviewing data quality KPIs per table
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

![KPIs per table and day](https://dqops.com/docs/images/concepts/data-quality-dashboards/kpis-per-table-and-day.png)


### **Check results**

**Check results** dashboards allow to review the details of the executed checks results.

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

![History of data quality check results](https://dqops.com/docs/images/concepts/data-quality-dashboards/history-of-data-quality-check-results.png)


### **Data Quality Dimensions**

**Data Quality Dimensions** group contains dashboards dedicated for different data quality dimension such as availability,
completeness, or timeliness. 

**Availability**, **Completeness**, Timeliness, and **Validity** dimensions has at least two dashboards. One dedicated to reviewing current issues and the
other to history of issues. 

**Timeliness** dimensions has specialized dashboards for evaluating:

* Current timeliness issues
* History of timeliness issues
* Table freshness - tables with the most current data,
* Table freshness - tables with the oldest data,
* Table staleness - tables most recently loaded
* Table staleness - tables no longer loaded
* History of table freshness
* Minimum, maximum and average delay


**Current completeness issues on columns** is a representative of **Completeness** subgroup. This dashboard summarizes
results from most recently executed null checks on columns ([null_count](../../checks/column/nulls/nulls-count.md), [null_percent](../../checks/column/nulls/nulls-percent.md),
[not_nulls_count](../../checks/column/nulls/not-nulls-count.md) and [not_nulls_percent](../../checks/column/nulls/not-nulls-percent.md)).

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


![Current completeness issues on columns](https://dqops.com/docs/images/concepts/data-quality-dashboards/current-completeness-issues-on-columns.png)


### **Volume**

**Volume** dashboards in the **Monitoring** group help to evaluate the largest tables by number of rows and empty 
or too small tables. While dashboards in the **Partitions** group (**Partition volume statistics**) display daily 
partitions row count and too small partitions.

**Largest tables by number of rows** is a representative of **Volume** subgroup. This dashboard displays tables monitored
with [row_count](../../checks/table/volume/row-count.md) check and allows review the number of rows in these tables. 

This dashboard allows filtering data by:

* time window (from last 7 days to last 3 months)
* row count
* connection,
* schema,
* data group,
* stages,
* table.

![Largest tables by number of rows](https://dqops.com/docs/images/concepts/data-quality-dashboards/largest-tables-by-number-of-rows.png)


### **PII**

**PII** dashboards display results from column checks detecting the percentage of Personal Identifiable Information in data. 
PII includes phone numbers ([contains_usa_phone_percent](../../checks/column/pii/contains-usa-phone-percent.md)), 
emails ([contains_email_percent](../../checks/column/pii/contains-email-percent.md)), 
zipcodes ([contains_usa_zipcode_percent](../../checks/column/pii/contains-usa-zipcode-percent.md)), or 
IP addresses ([contains_ip4_percent](../../checks/column/pii/contains-ip4-percent.md), 
[contains_ip6_percent](../../checks/column/pii/contains-ip6-percent.md)).


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
from all table-level schema change checks ([column count](../../checks/table/schema/column-count.md),
[column count changed](../../checks/table/schema/column-count-changed.md), [column list changed](../../checks/table/schema/column-list-changed.md),
[column list or order changed,](../../checks/table/schema/column-list-or-order-changed.md),
[column types changed](../../checks/table/schema/column-types-changed.md)). 

This dashboard allows filtering data by:

* time window (from last 7 days to last 6 months)
* connection,
* schema,
* stages,
* priorities,
* check name, 
* severity
* table.

![Schema changes - summary of changes in columns](https://dqops.com/docs/images/concepts/data-quality-dashboards/schema-changes-summary-of-changes-in-columns.png)

## DQOps usage

**DQOps usage** group contains dashboards summarizing executed checks, executed errors and checks no longer in use.


### **Statistics of executed checks** 

**Statistics of executed checks** subgroup of dashboards contains summaries of executed checks per table, column, 
[data grouping](../data-grouping/data-grouping.md), and [check category](../../checks/#categories-of-checks).

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

![Details of execution errors](https://dqops.com/docs/images/concepts/data-quality-dashboards/details-of-execution-errors.png)

### **Checks no longer in use**

**Checks no longer in use** dashboards show summary tables and charts of checks that are no longer in use.


## Aggregated results for all check types

**Aggregated results for all check types** group contains **Data quality issues count** and **Data quality KPIs** groups of 
dashboards with results aggregated from all check types (profiling, monitoring, and partitions).