# Review the results of data quality monitoring on dashboards

## Overview

DQOps has multiple built-in data quality dashboards for displaying [data quality KPI](../dqo-concepts/data-quality-kpis/data-quality-kpis.md).
Our dashboards use [Looker Studio](https://lookerstudio.google.com/overview) (formerly Google Data Studio) business
intelligence environment.

You can [learn more about different types of dashboards](../dqo-concepts/data-quality-dashboards/data-quality-dashboards.md)
in the Concept section.

## Prerequisite

To be able to display results on data quality dashboards, you need to have a DQOps account and synchronize locally stored results with it.  
To synchronize the data just click on the **Synchronize** button in the upper right corner of the navigation tab or run `cloud sync all` command in DQOps Shell.

You can read more about `cloud` command in [Command-line specification section](../command-line-interface/cloud.md).

## View results on dashboards

To view dashboards, simply go to the **Data Quality Dashboard** section, and select the dashboard of interest from the tree
view on the left. There are several groups and subgroups of data quality dashboards dedicated to analyzing results from
data quality checks. You can [learn more about different types of dashboards](../dqo-concepts/data-quality-dashboards/data-quality-dashboards.md)
in the Concept section.

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

## What's next

- [Learn how to run data quality checks](run-data-quality-checks/run-data-quality-checks.md).
- [Learn about setting schedules](schedules/index.md) to easily customize when checks are run.