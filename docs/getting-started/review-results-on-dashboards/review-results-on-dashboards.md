# Review results on dashboards

In the [previous step](../review-results-and-run-monitoring-checks/review-results-and-run-monitoring-checks.md) we reviewed
the initial results from the basic statistics and profiling checks, as well as run one monitoring check. Now let's review
the results on the data quality dashboards. 

In DQOps, you can choose from a variety of built-in dashboard groups.
You can read more about the [data quality dashboards](../../dqo-concepts/data-quality-dashboards/data-quality-dashboards.md)
in the DQOps concepts section.

## Prerequisite

To be able to display results on data quality dashboards you need to synchronize locally stored results with your DQOps Cloud account.  
To synchronize all the data just click on the **Synchronize** button in the upper right corner of the navigation bar.

## Review the total number of issues raised by the profiling and monitoring checks

To see how many issues have been raised by profiling checks, you can use the **Count summary** dashboard located in the **Profiling** group.
To access it, go to the **Data Quality Dashboard** section and select the **Count summary** dashboard from the tree view on the left.

Once you are on the **Count summary** dashboard, you will be able to review a summary of executed tests (checks) and their results,
categorized by type of check. You can filter the data on the dashboard by simply clicking on the filters on top part of the dashboard,
as well as clicking on the name of the connection, schema, data group etc.

![Total issue details dashboard](https://dqops.com/docs/images/getting-started/profiling-count-summary-dashboard.png)

In our example, we ran 38 profiling checks (2 table checks and 2 checks for each of the 18 columns) and none of them failed.
In the **Results per check** table you can see the results broken down by check name


To review the issues raised by the monitoring checks, simply select the **Count summary** dashboard in the **Monitoring** group.

![Total issue details dashboard](https://dqops.com/docs/images/getting-started/monitoring-count-summary-dashboard.png)

Here we can see the correct result from one daily_row_count check that we executed previously.

## Review the data quality KPIs

Data quality KPIs are the calculated percentage of passed data quality checks.
Data quality KPIs dashboards show high-level data quality KPIs, aggregated on a macro scale that can be shared at a corporate level.
To read more about [data quality KPIs, check DQO concept section](../../dqo-concepts/data-quality-kpis/data-quality-kpis.md).

Data quality KPIs dashboards review key data metrics per connection,
[data quality dimensions](../../dqo-concepts/data-quality-dimensions/data-quality-dimensions.md),
[check category](../../dqo-concepts/checks/index.md#categories-of-checks) and
[data grouping](../../dqo-concepts/data-grouping/data-grouping.md).

To review the summary KPIs of all run checks use the **KPIs scorecard - summary** dashboards.

Go to the **Data Quality Dashboards** section and select the **Profiling** group.
Next click on **Data quality KPIs** dashboards group and **KPIs scorecard - summary** dashboard.

![KPIs scorecard dashboard](https://dqops.com/docs/images/getting-started/profiling-kpis-scorecard-dashboard.png)
    
The calculated percentage of passed data quality checks (KPI) in our example is 100%. On other dashboards in KPIs category,
you can also review KPIs results broken down by table, data quality dimension or check category. 

You have completed our quick tutorial, and now you know how to add connections, run basic statistics and data quality checks, as well as view results.

## Next steps

- We have provided a variety of use cases which use openly available datasets [Google Cloud](https://cloud.google.com/datasets)
  to help you in using DQOps effectively. You can find the [full list of use cases here](../../examples/index.md). 
- DQOps allows you to keep track of the issues that arise during data quality monitoring and 
  send alert notifications directly to Slack. 
  Learn more about [incidents](../../working-with-dqo/incidents-and-notifications/incidents.md) and 
  [Slack notifications](../../integrations/slack/configuring-slack-notifications.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. 
  Learn how [data grouping in DQOps](../../working-with-dqo/set-up-data-grouping/set-up-data-grouping.md)
  can help you to calculate separate data quality KPI scores for different groups of rows.
