# Review initial results and run monitoring checks

After [adding your first connection, and initiated automatic monitoring](../add-data-source-connection/add-data-source-connection.md)
we describe how to review the initial results from the basic statistics and profiling checks, as well as how to run monitoring checks. 

Once new tables are imported, DQO automatically enables the following profiling and monitoring checks. To learn more about each check, click on the links below. 

**Table-level checks:**

- [row_count](../../checks/table/volume/row-count.md) counts the number of rows in a table.
- [row_count_anomaly_differencing](../../checks/table/volume/row-count-anomaly-differencing.md) ensures that the row count is within a two-tailed percentile from measurements made during the last 90 days.
- [row_count_change](../../checks/table/volume/row-count-change.md) ensures that the row count changed by a fixed rate since the last readout.
- [table_availability](../../checks/table/availability/table-availability.md) verifies that a table exists, can be accessed, and queried without errors.
- [column_count](../../checks/table/schema/column-count.md) retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to an expected number of columns.
- [column_count_changed](../../checks/table/schema/column-count-changed.md) detects whether the number of columns in a table has changed since the last time the check (checkpoint) was run.
- [column_list_changed](../../checks/table/schema/column-list-changed.md) detects if the list of columns has changed since the last time the check was run.
- [column_list_or_order_changed](../../checks/table/schema/column-list-or-order-changed.md) detects whether the list of columns and the order of columns have changed since the last time the check was run.
- [column_types_changed](../../checks/table/schema/column-types-changed.md) detects if the column names or column types have changed since the last time the check was run.

**Column-level checks:**

- [nulls_count](../../checks/column/nulls/nulls-count.md) ensures that there are no more than a set number of null values in the monitored column.
- [nulls_percent](../../checks/column/nulls/nulls-percent.md) ensures that there are no more than a set percentage of null values in the monitored column.
- [nulls_percent_anomaly_stationary](../../checks/column/nulls/nulls-percent-anomaly-stationary.md) ensures that the null percent value in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.
- [nulls_percent_change_yesterday](../../checks/column/nulls/nulls-percent-change-yesterday.md) ensures that the null percent in a monitored column has changed by a fixed rate since the last readout from yesterday.
- [not_nulls_percent](../../checks/column/nulls/not-nulls-percent.md) ensures that there are no more than a set percentage of not null values in the monitored column.
- [string_datatype_changed](../../checks/column/datatype/string-datatype-changed.md) scans all values in a string column and detects the data type of all values in a column.
- [column_exists](../../checks/column/schema/column-exists.md) reads the metadata of the monitored table and verifies that the column still exists in the data source.
- [column_type_changed](../../checks/column/schema/column-type-changed.md) detects if the data type of the column has changed since the last time it was retrieved.


All checks are scheduled to run daily at 12:00 p.m.

For more general information about checks, see [DQO concepts section](../../dqo-concepts/checks/index.md). 

## Review basic statistics results

Basic statistics provides you with summary information about your tables and columns. This information is
valuable in deciding which data quality checks and threshold levels should be set to monitor data quality.

In the previous step we have collected basic statistics for imported tables and column using the Advisor. 

To review the results: 

1. Go to the **Profiling** section. Select the "crime" table on the tree view on the left. The results are displayed under **Basic Statistics** tab. 

    You can filter the columns by simply clicking on the sorting icon next to any column header.

    For detailed description of each column go to the [Basics statistics section](../../working-with-dqo/basic-data-statistics/basic-data-statistics.md). 

    ![Basic statistics results for austin crimes](https://dqops.com/docs/images/getting-started/austin-crimes-statistics.png)

2. To view detailed statistics, click on the name of the column or navigate to the single column on the tree view.
 
    ![Basic statistics results for austin crimes - details](https://dqops.com/docs/images/getting-started/austin-crimes-address-column-statistics.png)


## Review profiling checks results

[**Profiling checks**](../../dqo-concepts/checks/profiling-checks/profiling-checks.md) enable you to run more advanced data analyses than
[**Basic data statistics**](../../working-with-dqo/basic-data-statistics/basic-data-statistics.md). Profiling checks are also useful for
exploring and experimenting with various types of checks and determining the most suitable ones for regular data quality monitoring.

In the previous step we have run profiling checks for imported tables and column using the Advisor. Now, let's review the results.

1. Go to the list of profiling checks by clicking on **Profiling** section, the "crime" table on the tree view and **Profiling checks** tab.

    ![Profiling checks list](https://dqops.com/docs/images/getting-started/profiling-checks-list.png)

    Here you can view the list of all table profiling checks. Noticed the checks that have been enabled upon importing new tables have switched on the toggle button.
    The icons located before the name of each check allow you to: enable and disable it, configure settings, run a check, review results, and get more information about it.
 
    For enabled checks, notice a square next to the name indicating the results of the run check initiated by the Advisor:

    - green for a valid result
    - yellow for a warning
    - orange for an error
    - red for a fatal error

2. Click the **Results** icon to view more details of the results.

    ![Checking results](https://dqops.com/docs/images/getting-started/checking-results.png)

    A table will appear with more details about the run check. The check displayed Valid results with the actual value 116 675. 

    You can also review the results of other table checks on the list, as well as review the list of the column-level checks. 
    Just select the column of interest form the tree view on the left. 

    On the list of checks you can enable other checks, change their thresholds and run them.

    Note that some enabled checks, for example column-level profile_nulls_count and profile_null_percent, do not have thresholds levels set.
    For those check a [sensor](../../dqo-concepts/sensors/sensors.md) will be executed, and you can view its result on Results details, **Sensor readouts** tab.
    Based on the results, you can set the threshold for these checks.
    

## Run monitoring checks

[**Monitoring checks**](../../dqo-concepts/checks/monitoring-checks/monitoring-checks.md) are standard checks that monitor the data quality of a
table or column. These checks create a single data quality result for the entire table or column. There are two categories
of monitoring checks: daily checks and monthly checks. When run multiple times per day, the **daily checks** store only
the most recent result for each day. **Monthly checks** store the most recent results for each month the data quality
checks were run.

Now let's run monitoring checks.

1. Go to the **Monitoring Checks** section, and select the "crime" table from the tree view and **Daily** tab. 

    Another option is to use the **Monitoring checks** link.

    ![Monitoring checks section](https://dqops.com/docs/images/getting-started/monitoring-checks-section.png)

2. Click the **Run check** icon next to daily_row_count check. This check verifies that the number of rows in the table
    does not exceed the minimum accepted count set as the threshold level which is 1 in this case.
   
    You can read more about threshold severity levels in [DQO concepts section](../../dqo-concepts/checks/#severity-levels).

    ![Running check](https://dqops.com/docs/images/getting-started/run-daily-row-count-check.png)
    
    A green square should appear next to the name of the checks indicating that the result of the run check is valid.
    You can view the details by placing the mouse cursor on the green square or view more detail results by clicking the
   **Results** icon

    You can enable and run other monitoring checks and adjust their thresholds.

## Next step

Now that you have reviewed the initial results from basic statistics and profiling checks and run monitoring checks, you can [review the results on the dashboards](../../getting-started/review-results-on-dashboards/review-results-on-dashboards.md).