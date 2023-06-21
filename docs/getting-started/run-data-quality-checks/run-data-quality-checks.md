# Run data quality check 

After [adding your first connection in the previous step](../adding-data-source-connection/adding-data-source-connection.md),
we describe how to run first checks. 

In our example on BigQuery public dataset Austin Crime Data you will enable and run a table-level [row_count](../../checks/table/volume/row-count.md)
and column-level [nulls_percent](../../checks/column/nulls/not-nulls-percent.md) checks using the graphical interface.

For more information about checks, see [DQO concepts section](../../dqo-concepts/checks/index.md). 

## Run table-level advanced profiling check

1. In DQO User Interface Console go to the Profiling section.

    Click **Profiling** in the navigation bar at the top of the screen. 

    Another option is to expand the tree view of the newly added connection on the left side, click on the "crime" table and use the "Advanced Profiling" link.

    ![Navigating to profiling section](https://dqops.com/docs/images/getting-started/austin-crime-table-view-link-to-profiling.jpg)

2. Enable [row_count](../../checks/table/volume/row-count.md) table-level data quality check on "crime" table.

    Row_count check verifies that the number of rows in the table does not exceed the minimum accepted count set as the threshold level.
   
    To enable the row_count check that you are on the "crime" table in the tree view on the left side.
    In the list of checks on the right, enable the row count data quality check by clinking switch button
    Leave the default value of the error threshold level as "0".  You can read more about threshold severity levels in [DQO concepts section](../../dqo-concepts/checks/#severity-levels).

    ![Enable row_count data quality check](https://dqops.com/docs/images/getting-started/austin-crime-run-row_count-check.jpg)

3. Run [row_count](../../checks/table/volume/row-count.md) data quality check by clicking the Run Check icon

    ![Running check](https://dqops.com/docs/images/getting-started/austin-crime-run-row_count-check.jpg)
    
    A green square should appear next to the name of the checks indicating that the results of the run check is valid.
    You can view the details by placing the mouse cursor on the green square.

4. Click the "Check Details" icon to view more details of the results.

    ![Checking results](https://dqops.com/docs/images/getting-started/austin-crime-row_count-check-results.jpg)

    A table will appear with more details about the run check.


## Run column-level advanced profiling check

1. In the tree view on the left navigate to "clearance_status" column

2. Enable [nulls_percent](../../checks/column/nulls/not-nulls-percent.md) column level check on "clearance_status" column. 

    Nulls_percent check ensures that there are no more than a set percentage of null values in the monitored column.

    Add Warning and Fatal thresholds. Leave the default options (1 for Warning, 2 for Error and 5 for Fatal)   

3. Run check by clicking the Run Check icon.

    This time an orange square should appear indicating that the test detected and Error in the data.

4. Click the "Check Details" icon to view more details of the results.

    The screen with the results should look as the one below. 

    ![Checking results](https://dqops.com/docs/images/getting-started/austin-crime-nulls_percent-check-results.jpg)

5. Synchronize locally stored results with your DQO Cloud account to be able to view the results on the dashboards. 

    To synchronize all the data click on the **Synchronize** button in the upper right corner or just run `cloud sync all` command in DQO Shell.

    You can read more about `cloud` command in [Command-line specification section](../../command-line-interface/cloud.md).


## Next step

Now that you have run the checks, you can [review the results on the dashboards](../../getting-started/review-results-on-dashboards/review-results-on-dashboards.md).
