# Data quality dashboards

DQO has multiple built-in data quality dashboards for displaying [data quality KPI](../data-quality-kpis/data-quality-kpis.md).
Our dashboards use [Looker Studio](https://lookerstudio.google.com/overview) (formerly Google Data Studio) business 
intelligence environment. We chose Looker Studio because there is no per-user license fee, which allows granting access
to all parties that might be involved in the issue resolution process.

All data quality results are synchronized to a private data quality data warehouse in the Google Cloud. 
Data quality projects implemented with DQO receive a complementary Looker Studio instance connected to a data quality 
data warehouse. DQO customers can ask the vendor to access a custom Looker Studio data source, providing direct access 
to the data quality lakehouse.

## Groups of dashboards

There are several groups and subgroups of data quality dashboards dedicated to analyzing data quality.

1. Profiling, Monitoring, and Partitions groups each contain subgroups for analyzing results from profiling, monitoring, and partitions check types. 
    - **Current table status** dashboards allow data engineers and data owners for viewing the statuses of the tables and columns grouped by [data quality dimensions](../data-quality-dimensions/data-quality-dimensions.md) or check categories. Dashboards within this group also allow for a quick evaluation of the current issue severity status per table, column, data quality dimension, [data grouping](../data-grouping/data-grouping.md), [check category](../../checks/#categories-of-checks), and per day of the month.
    - **Data quality issues count** dashboards allow for reviewing and filtering a summary number of issues that arise from data quality checks. The summary can be categorized by table, [data grouping](../data-grouping/data-grouping.md), day, or [check category](../../checks/#categories-of-checks). These dashboards help data engineers and data owners identify areas (tables or data pipelines) with the highest number of data quality issues that should be addressed.
    - **Data quality KPIs** dashboards show high-level data quality KPIs, aggregated on a macro scale that can be shared at a corporate level. With these dashboards, senior management can review data quality KPIs per table, column, [data quality dimensions](../data-quality-dimensions/data-quality-dimensions.md), [check category](../../checks/#categories-of-checks), and [data grouping](../data-grouping/data-grouping.md). They can also review how KPIs changed during each day of the month.

2. **Statistics of data sources**  group of dashboards contains summaries for check categories such as availability, volume, data completeness, and timeliness.

3. **DQO usage** group of dashboards contains summaries of executed checks per table, column, [data grouping](../data-grouping/data-grouping.md), and [check category](../../checks/#categories-of-checks). Within this group, there are also dashboards showing checks that are no longer in use.

4. **Aggregated results for all check types** group is similar to the first category but contains aggregates for all check types (profiling, monitoring, and partitions)

On each dashboard, data can filter by clicking on the filters on the top part of the dashboard, as well as clicking on the name of the connection, schema, data group, etc.



## What's next
- [Learn how to review results of data quality monitoring on dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)

