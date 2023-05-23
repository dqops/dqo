# Review results on the dashboards

In the [previous step](../run-data-quality-checks/run-data-quality-checks.md) we run two data quality check: table-level
[row_count](../../checks/table/volume/row-count.md) and column-level [nulls_percent](../../checks/column/nulls/not-nulls-percent.md)
on BigQuery public dataset Austin Crime Data. 

Now let's review the results on the data quality dashboards. This is a unique feature of DQO allowing for the display of [data quality KPI](../../dqo-concepts/data-quality-kpis/data-quality-kpis.md).

In DQO, you can choose from a variety of built-in dashboard groups.

**Governance dashboards** allows to review summarized key data metrics per [check category](../../checks/#categories-of-checks),
[data quality dimensions](../../dqo-concepts/data-quality-dimensions/data-quality-dimensions.md) or [data streams](../../dqo-concepts/data-stream-segmentation/data-stream-segmentation.md).
Government dashboards are useful for senior management to review aggregated key data metrics.

**Issue Details** are useful for data engineers and data owners to better understand data dynamics during the investigation phase when the data quality issue is being diagnosed and
later to confirm whether it has been resolved.

You can read more about the data quality dashboards in [Concept section](../../dqo-concepts/data-quality-dashboards/data-quality-dashboards.md).

## Prerequisite

To be able to display results on data quality dashboards you need to synchronize locally stored results with your DQO Cloud account.  
To synchronize all the data just run `cloud sync all` command in DQO Shell.

You can read more about `cloud` command in [Command-line specification section](../../command-line-interface/cloud.md).

## Total data quality KPIs dashboard

To review the summary KPIs of all run checks you can use the **Total data quality KPIs** dashboard.
You can find it in the Governance group.

1. Go to the **Data Quality Dashboards** section using the navigation bar at the top of the screen

    ![Navigating to Data Quality Dashboards section](https://dqops.com/docs/images/getting-started/data-quality-dashboards-section.jpg)
    
2. From the tree view on the left, select the **Total DQ KPIs** dashboard in the Governance group. 

    ![Total DQ KPIs dashboard](https://dqops.com/docs/images/getting-started/total-dq-kpis-dashboard.jpg)

    In our example we ran two data quality checks. The result of the [row_count](../../checks/table/volume/row-count.md) 
    check was Correct, while [nulls_percent](../../checks/column/nulls/not-nulls-percent.md) resulted in an Error.

    The calculated percentage of passed data quality checks (KPI) in our example is 50%. You can see that there is on passed data quality
    check and one failed.  

## Total issue details dashboard

To review the details which tables were affected by the issues you can use the **Total issue details** dashboard. 
You can find it in the Issue Details group.

1. From the tree view on the left of the Data Quality Dashboard section, select the **Total issue details** dashboard in the Issue Details group. 

    ![Total issue details dashboard](https://dqops.com/docs/images/getting-started/total-issue-details-dashboard.jpg)

    On the **Total issue details** dashboard, you can see the results broken down by categories of checks, as well as the 
    list of tables affected by the issues. In our example, there is only one table with one data quality issue. 
    You can filter the results by clicking on the check category or the table.  


You have completed our quick tutorial and now know how to add connections, run data quality checks and view results in 
DQO using the graphical interface.