# Review the results of data quality monitoring on dashboards

DQOps has multiple built-in data quality dashboards for displaying [data quality KPI](../../dqo-concepts/data-quality-kpis/data-quality-kpis.md).
Our dashboards use [Looker Studio](https://lookerstudio.google.com/overview) (formerly Google Data Studio) business
intelligence environment.

In DQOps, you can choose from a variety of built-in dashboard groups.

1. Profiling, Monitoring, and Partitions groups each contain subgroups for analyzing results from profiling, monitoring, and partition check types, respectively.
    * **Current table status** dashboards allow data engineers and data owners for viewing the data quality statuses of the tables and columns grouped by [data quality dimensions](../../dqo-concepts/data-quality-dimensions/data-quality-dimensions.md) or check categories. Dashboards within this group also allow for a quick evaluation of the current issue severity status per table, column, data quality dimension, [data grouping](../../dqo-concepts/data-grouping/data-grouping.md), [check category](../../../dqo-concepts/checks/#categories-of-checks), and per day of the month.
    * **Highest issue severity per day** dashboards allow for reviewing and filtering a summary number of issues that arise from data quality checks per day. The summary can be categorized by table, column, [data grouping](../../dqo-concepts/data-grouping/data-grouping.md), or [check category](../../dqo-concepts/checks/#categories-of-checks). These dashboards help data engineers and data owners identify areas (tables or data pipelines) with the highest number of data quality issues that should be addressed.
    * **Data quality issues count** dashboards display number of issues or failed checks per table or per check.
    * **Data quality KPIs** dashboards show high-level data quality KPIs, aggregated on a macro scale that can be shared at a corporate level. With these dashboards, senior management can review data quality KPIs per table, column, [data quality dimensions](../../dqo-concepts/data-quality-dimensions/data-quality-dimensions.md), [check category](../../../dqo-concepts/checks/#categories-of-checks), [data grouping](../../dqo-concepts/data-grouping/data-grouping.md) and day.
    * **Check results** dashboards allows to review the details of the issues resulted from executed checks.
    * **Data Quality Dimensions** group contains dashboards dedicated for different data quality dimension such as availability, completeness, timeliness or volume. 
    * **PII** dashboards display results from checks detecting Personal Identifiable Information data such as phone numbers, emails, zipcodes, or IP addresses.
    * **Schema changes** dashboards allow to view tables with changes in the number, list, or order of columns. 
   
2. **DQOps usage** group of dashboards contains summaries of executed checks  Within this group, there are also dashboards showing checks that are no longer in use.
    * **Statistics of executed checks** subgroup of dashboards contains summaries of executed checks per table, column, [data grouping](../../dqo-concepts/data-grouping/data-grouping.md), and [check category](../../../dqo-concepts/checks/#categories-of-checks).
    * **Execution errors** dashboards allow to view details and history of execution errors.
    * **Checks no longer in use** dashboards show summaries of checks that are no longer in use.

3. **Aggregated results for all check types** group contains issues counts and KPIs aggregates for all check types (profiling, monitoring, and partitions).

## Prerequisite

To be able to display results on data quality dashboards, you need to have a DQOps account and synchronize locally stored results with it.  
To synchronize the data just click on the **Synchronize** button in the upper right corner of the navigation tab or run `cloud sync all` command in DQOps Shell.

You can read more about `cloud` command in [Command-line specification section](../../command-line-interface/cloud.md).

## View results on dashboards

To view dashboards, simply go to the **Data Quality Dashboard** section, and select the dashboard of interest from the tree view on the left.

For example, to review the summary KPIs of all run monitoring checks you can use the **KPIs scorecard - summary** dashboard.
You can find it in the **Monitoring** group, **Data quality KPIs** subgroup.

![KPIs scorecard - summary](https://dqops.com/docs/images/working-with-dqo/data-quality-dashboards/kpis-scorecard-dashboards.png)

You can [view more examples of dashboards](../../dqo-concepts/data-quality-dashboards/data-quality-dashboards.md) in the Concept section. 
