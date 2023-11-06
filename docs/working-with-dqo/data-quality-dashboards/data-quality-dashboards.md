# Review the results of data quality monitoring on dashboards

DQOps has multiple built-in data quality dashboards for displaying [data quality KPI](../../dqo-concepts/data-quality-kpis/data-quality-kpis.md).
Our dashboards use [Looker Studio](https://lookerstudio.google.com/overview) (formerly Google Data Studio) business
intelligence environment.

In DQOps, you can choose from a variety of built-in dashboard groups.

- **Governance dashboards** allows to review summarized key data metrics per [check category](../../checks/#categories-of-checks),
[data quality dimensions](../../dqo-concepts/data-quality-dimensions/data-quality-dimensions.md) or [data streams](../../dqo-concepts/data-grouping/data-grouping.md).
Government dashboards are useful for senior management to review aggregated key data metrics.

- **Operational dashboards** helps data engineers and data owners to identify areas (tables or data pipelines) in a data 
warehouse or data lake with the highest number of data quality issues that should be addressed.

- **Data Quality Issue Details** dashboards show detailed information about the issues at the table level. The detailed dashboards are useful for data engineers and data owners
  to better understand data dynamics during the investigation phase when the data quality issue is being diagnosed and
  later to confirm whether it has been resolved.

Learn more about [different groups of dashboards here](../../dqo-concepts/data-quality-dashboards/data-quality-dashboards.md).

## Prerequisite

To be able to display results on data quality dashboards, you need to have a DQOps account and synchronize locally stored results with it.  
To synchronize the data just click on the **Synchronize** button in the upper right corner of the navigation tab or run `cloud sync all` command in DQOps Shell.

You can read more about `cloud` command in [Command-line specification section](../../command-line-interface/cloud.md).

## Total data quality KPIs dashboard

To review the summary KPIs of all run checks you can use the **KPIs** dashboard.
You can find it in the Governance (KPIs) group.

1. Go to the **Data Quality Dashboards** section using the navigation bar at the top of the screen

    ![Navigating to Data Quality Dashboards section](https://dqops.com/docs/images/working-with-dqo/data-quality-dashboards/data-quality-dashboards-section.png)
    
2. From the tree view on the left, select the **KPIs** dashboard folder in the **Government (KPIs)** group, and then the **KPIs** dashboard. 

    ![Total DQOps KPIs dashboard](https://dqops.com/docs/images/working-with-dqo/data-quality-dashboards/kpis-dashboards.png)


## Affected tables dashboard

To review which tables were affected by data quality issues you can use **Affected Tables** dashboard.
You can find it in the **Operational** group.

1. From the tree view on the left of the **Data Quality Dashboard** section, select the **Affected Tables** dashboard in the **Operational** group.

    ![Affected Tables dashboard](https://dqops.com/docs/images/working-with-dqo/data-quality-dashboards/affected-tables-dashboard.png)

    The **Affected Table** dashboard displays issues categorized by connection, schema, data quality dimension, and check 
    category. You can filter results in other tables by clicking on any element in these tables, which can help you locate the issue.

## Issues details dashboard

To review the details which tables were affected by the issues you can use the **Issue** dashboard. 
You can find it in the **Data Quality Issue Details** group.

1. From the tree view on the left of the **Data Quality Dashboard** section, select the **Issue** dashboard in the **Data Quality Issue Details** group. 

    ![Total issue details dashboard](https://dqops.com/docs/images/working-with-dqo/data-quality-dashboards/issues-dashboard.png)

    The **Issue** dashboard displays the results categorized by checks and also lists the tables affected by the issues.