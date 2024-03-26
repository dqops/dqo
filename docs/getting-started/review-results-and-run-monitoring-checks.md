# Review initial results and run monitoring checks
This guide will show you how to run the first data quality check, and how to review the first results and data quality issues in DQOps. 

## Overview

After [adding your first connection, and initiating automatic monitoring](add-data-source-connection.md),
we describe how to review the initial results from the basic statistics and profiling checks, as well as how to run monitoring checks. 

Once new tables are imported, DQOps automatically activates the following profiling and monitoring checks.
All checks are scheduled to run daily at 12:00 a.m. For more [general information about checks, see the DQOps concepts](../dqo-concepts/definition-of-data-quality-checks/index.md) section.


## Review basic statistics results

Basic statistics provides you with summary information about your tables and columns. This information is
valuable in deciding which data quality checks and threshold levels should be set to monitor data quality.

In the previous step we have collected basic statistics for imported tables and column using the Advisor. 

To review the results: 

1. Go to the **Profiling** section. Select the "austin_crime" table on the tree view on the left. The results are displayed under **Basic Statistics** tab. 

    You can filter the columns by simply clicking on the sorting icon next to any column header.

    For detailed description of each column go to the [Basics statistics section](../working-with-dqo/collecting-basic-data-statistics.md). // todo screen

    ![Basic statistics results for austin crimes](https://dqops.com/docs/images/getting-started/austin-crimes-statistics.png)

2. To view detailed statistics, click on the name of the column or navigate to the single column on the tree view. // todo screen
 
    ![Basic statistics results for austin crimes - details](https://dqops.com/docs/images/getting-started/austin-crimes-address-column-statistics.png)


## Review profiling checks results

// todo: add the table preview from the "table quality status" in profiling tab, then add description

[**Profiling checks**](../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md) enable you to run more advanced data analyses than
[**Basic data statistics**](../working-with-dqo/collecting-basic-data-statistics.md). Profiling checks are also useful for
exploring and experimenting with various types of checks and determining the most suitable ones for regular data quality monitoring.

In the previous step we have run profiling checks for imported tables and column using the Advisor. Now, let's review the results.

### Navigate to the Profiling checks editor

To navigate to the Profiling checks editor, click on **Profiling** section, the "austin_crime" table on the tree view, and **Profiling checks** tab. // todo screen

![Profiling checks list](https://dqops.com/docs/images/getting-started/profiling-checks-list.png)

Here you can view the list of all table profiling checks. Noticed the checks that have been activated upon importing new tables have switched on the toggle button.
The icons located before the name of each check allow you to: activate and deactivate it, configure settings, run a check, review results, and get more information about it.

For activated checks, notice a square next to the name indicating the results of the check runs initiated by the Advisor:

- green for a valid result
- yellow for a warning
- orange for an error
- red for a fatal error

### Review the profiling checks results

To review the profiling checks results, click the **Results** icon to view more details of the results. // todo screen

![Checking results](https://dqops.com/docs/images/getting-started/checking-results.png)

A table will appear with more details about the run check. The check displayed Valid results with the actual value 116 675. 

You can also review the results of other table checks on the list, as well as review the list of the column-level checks. 
Just select the column of interest from the tree view on the left. 

On the list of checks you can activate other checks, change their thresholds and run them.

Note that some activated checks, for example column-level profile_nulls_count and profile_null_percent, do not have thresholds levels set.
For those check a [sensor](../dqo-concepts/definition-of-data-quality-sensors.md) will be executed, and you can view its result on Results details, **Sensor readouts** tab.
Based on the results, you can set the threshold for these checks.

### Default data profiling checks
DQOps activates the following [default data quality checks](../dqo-concepts/data-observability.md) to
perform [initial data profiling](../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md#initial-data-quality-kpi-score).

| Target | Check name                                                             | Description                                                                                       |
|--------|------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------|
| table  | [profile row count](../checks/table/volume/row-count.md)               | Captures the row count of the table and identifies empty tables.                                  |
| table  | [profile data freshness](../checks/table/timeliness/data-freshness.md) | Measures data freshness of the table.                                                             |
| table  | [profile column count](../checks/table/schema/column-count.md)         | Retrieves the metadata of the monitored table from the data source and captures the column count. |
| column | [profile nulls count](../checks/column/nulls/nulls-count.md)           | Counts null values in every column and detects incomplete columns that contain null values.       |
| column | [profile nulls percent](../checks/column/nulls/nulls-percent.md)       | Measures the percentage of null values in every column.                                           |
| column | [profile not nulls count](../checks/column/nulls/not-nulls-count.md)   | Counts not null values in every column and detects empty columns that contain only null values.   |



## Run monitoring checks

[**Monitoring checks**](../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md) are standard checks that provide the data observability by monitoring the data quality of a
table or column. These checks create a single data quality result for the entire table or column. There are two categories
of monitoring checks: daily checks and monthly checks. When run multiple times per day, the **daily checks** store only
the most recent result for each day. **Monthly checks** store the most recent results for each month the data quality
checks were run.

Now let's run monitoring checks.

1. Go to the **Monitoring Checks** section, and select the "austin_crime" table from the tree view and **Daily** tab. 

    Another option is to use the **Monitoring checks** link.

    ![Monitoring checks section](https://dqops.com/docs/images/getting-started/monitoring-checks-section.png)

2. Click the **Run check** icon next to daily_row_count check. This check verifies that the number of rows in the table
    does not exceed the minimum accepted count set as the threshold level which is 1 in this case.
   
    You can read more about [issue severity levels in DQOps concepts section](../dqo-concepts/definition-of-data-quality-checks/index.md#issue-severity-levels).

    ![Running check](https://dqops.com/docs/images/getting-started/run-daily-row-count-check.png)
    
    A green square should appear next to the name of the checks indicating that the result of the run check is valid.
    You can view the details by placing the mouse cursor on the green square or view more detail results by clicking the
    **Results** icon

    You can activate and run other monitoring checks and adjust their thresholds.

### Default daily monitoring checks
DQOps activates the following [daily monitoring checks](../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)
on every table and column to apply [data observability](../dqo-concepts/data-observability.md) of the data source.

| Target | Check name                                                                                                | Description                                                                                                                                                                                                                                |
|--------|-----------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| table  | [daily row count](../checks/table/volume/row-count.md)                                                    | Captures the row count of the table every day and identifies empty tables.                                                                                                                                                                 |
| table  | [daily row count anomaly](../checks/table/volume/row-count-anomaly.md)                                    | Detects day-to-day anomalies in the table volume. Raises a data quality issue when the increase or decrease in the row count is in the top 1% of most significant changes.                                                                 |
| table  | [daily row count change](../checks/table/volume/row-count-change.md)                                      | Detects significant day-to-day changes in the table volume. Raises a data quality issue when the increase or decrease in the row count is greater than 10%.                                                                                |
| table  | [daily data freshness](../checks/table/timeliness/data-freshness.md)                                      | Measures data freshness of the table. Raises a data quality issue when the data is outdated by 2 days.                                                                                                                                     |
| table  | [daily data staleness](../checks/table/timeliness/data-staleness.md)                                      | Measures data staleness (the time since the last data loading) of the table. Raises a data quality issue when the table was not updated for 2 or more days.                                                                                |
| table  | [daily table availability](../checks/table/availability/table-availability.md)                            | Verifies that a table exists, can be accessed, and queried without errors. Detects corrupted tables and expired credentials to data sources.                                                                                               |
| table  | [daily column count](../checks/table/schema/column-count.md)                                              | Retrieves the metadata of the monitored table from the data source and counts the number of columns.                                                                                                                                       |
| table  | [daily column count changed](../checks/table/schema/column-count-changed.md)                              | Detects whether the number of columns in a table has changed since the last time the check (checkpoint) was run.                                                                                                                           |
| table  | [daily column list changed](../checks/table/schema/column-list-changed.md)                                | Detects if the list of columns has changed since the last time the check was run.                                                                                                                                                          |
| table  | [daily column list or order changed](../checks/table/schema/column-list-or-order-changed.md)              | Detects whether the list of columns and the order of columns have changed since the last time the check was run.                                                                                                                           |
| table  | [daily column types changed](../checks/table/schema/column-types-changed.md)                              | Detects if the column names or column types have changed since the last time the check was run.                                                                                                                                            |
| column | [daily nulls count](../checks/column/nulls/nulls-count.md)                                                | Counts null values in every column without raising any data quality issues.                                                                                                                                                                |
| column | [daily nulls percent](../checks/column/nulls/nulls-percent.md)                                            | Measures the percentage of null values in every column without raising any data quality issues.                                                                                                                                            |
| column | [daily nulls percent anomaly](../checks/column/nulls/nulls-percent-anomaly.md)                            | Measures the percentage of null values in every column and detects anomalous changes in the percentage of null value. Raises a data quality issue for the top 1% biggest day-to-day changes.                                               |
| column | [daily not nulls percent](../checks/column/nulls/not-nulls-percent.md)                                    | Measures the percentage of null values in every column without raising any data quality issues.                                                                                                                                            |
| column | [daily nulls percent change](../checks/column/nulls/nulls-percent-change.md)                              | Detects significant day-to-day changes in the percentage of null values in every column. Raises a data quality issue when the increase or decrease in the percentage of null values is greater than 10%.                                   |
| column | [daily distinct count anomaly](../checks/column/uniqueness/distinct-count-anomaly.md)                     | Counts distinct values in every column and detects anomalous changes in the percentage of null value. Raises a data quality issue for the top 1% biggest day-to-day changes of the count of distinct values.                               |
| column | [daily detected datatype in text changed](../checks/column/datatype/detected-datatype-in-text-changed.md) | Scans all values in a text column and detects the data type of all values in a column. Raises a data quality issue when the type of texts changes. For example, when a column contained always numeric values, but a text value was found. |
| column | [daily sum anomaly](../checks/column/anomaly/sum-anomaly.md)                                              | Sums values in all numeric columns. Detects day-to-day anomalies in the sum of numeric values. Raises a data quality issue for the top 1% biggest day-to-day changes.                                                                      |
| column | [daily mean anomaly](../checks/column/anomaly/mean-anomaly.md)                                            | Calculates a mean (average) value in all numeric columns. Detects day-to-day anomalies of the mean of numeric values. Raises a data quality issue for the top 1% biggest day-to-day changes.                                               |
| column | [daily column exists](../checks/column/schema/column-exists.md)                                           | Reads the metadata of the monitored table and verifies that the column still exists in the data source.                                                                                                                                    |
| column | [daily column type changed](../checks/column/schema/column-type-changed.md)                               | Detects if the data type of the column has changed since the last time it was retrieved.                                                                                                                                                   |



## Next step

Now that you have reviewed the initial results from basic statistics and profiling checks and run monitoring checks, 
you can [review the results on the dashboards](review-results-on-dashboards.md).
