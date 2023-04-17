# Run data quality check 

In the Getting started example, we describe how to run a table-level [row_count](../../checks/table/standard/row-count.md)
and column-level [nulls_percent](../../checks/column/nulls/not-nulls-percent.md) checks on BigQuery public dataset Austin
Crime Data using the graphic interface.

For a full description of how to run checks using the graphic interface and CLI, see [Working with DQO section](../../working-with-dqo/run-data-quality-checks/index.md).

For more information about checks, see [DQO concepts section](../../dqo-concepts/checks/index.md). 

## Run table-level advanced profiling check using graphic interface

1. Go to the Profiling section.

    You can use the Profiling tab at the top of the screen. Another option is to expand the tree view of the newly added connection on the 
left side, click on the "crime" table and use the "Advanced Profiling" link.

    ![Navigating to profiling section](https://docs.dqo.ai/docs/images/getting-started/austin-crime-table-view-link-to-profiling.jpg)

2. Enable [row_count](../../checks/table/standard/row-count.md) data quality check. This check verifies that the number of
   rows in the table does not exceed the minimum accepted count set as the threshold level.

    In the profiling section, check that you are on the "crime" table in the tree view n the left side. In the list of 
checks on the right, enable the row count data quality check by clicking the switch.

    ![Enable row_count data quality check](https://docs.dqo.ai/docs/images/getting-started/austin-crime-run-row_count-check.jpg)

   Leave the default value of the error threshold level as "0".  You can read more about threshold severity levels in [DQO concepts section](../../dqo-concepts/checks/#severity-levels).

3. Run [row_count](../../checks/table/standard/row-count.md) data quality check.

    Run row_count data quality check by clicking the Run Check icon.

    ![Runnin check](https://docs.dqo.ai/docs/images/getting-started/austin-crime-run-row_count-check.jpg)
    
    A green square should appear next to the name of the checks indicating that the results of the run check is valid.
    You can view the details by placing the mouse cursor on the green square.

4. Click the "Check Details" icon to view more details of the results.

    ![Checking results](https://docs.dqo.ai/docs/images/getting-started/austin-crime-row_count-check-results.jpg)

    A table will appear with more details about the run check.

## Run column-level advanced profiling check using graphic interface

1. In the tree view on the left navigate to "clearance_status" column

2. Enable [nulls_percent](../../checks/column/nulls/not-nulls-percent.md) column level check on "clearance_status" column. This check ensures
that there are no more than a set percentage of null values in the monitored column.

    Add Warning and Fatal thresholds. Leave the default options (1 for Warning, 2 for Error and 5 for Fatal)   

3. Run check by clicking the Run Check icon.

    This time an orange square should appear indicating that the test detected and Error in the data.

4. Click the "Check Details" icon to view more details of the results.

    The screen with the results should look as the one below. 

    ![Checking results](https://docs.dqo.ai/docs/images/getting-started/austin-crime-nulls_percent-check-results.jpg)

## Next step

Now that you have run the checks, you can [inspect the results on the dashboards](../../getting-started/run-data-quality-checks/inspect-results-on-dashbords.md).
