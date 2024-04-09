# Create and run table comparisons 
Read this manual to understand how to compare tables between data sources using DQOps. Table comparison (reconciliation) enables detection of data accuracy issues.

## Overview

Table comparison enables you to identify differences between two tables. It is useful when you want to compare datasets
and validate successful data replication or migration. You can compare tables between any [supported data sources](../data-sources/index.md).

## Types of comparison checks 

Using Table comparison you can monitor tables using the following comparison checks:

**Table-level checks:**

- [row count match](../checks/table/comparisons/row-count-match.md) compares the row count of the current (parent) table to the row count of the reference table.
- [column count match](../checks/table/comparisons/column-count-match.md) compares the column count of the current (parent) table to the column count of the reference table.

**Column-level checks:**

- [sum match](../checks/column/comparisons/sum-match.md) compares the sum of the values in the tested column to the sum of values in a reference column from the reference table.
- [min match](../checks/column/comparisons/min-match.md) compares the minimum value in the tested column to the minimum value in a reference column from the reference table.
- [max match](../checks/column/comparisons/max-match.md) compares the maximum value in the tested column to the maximum value in a reference column from the reference table.
- [mean match](../checks/column/comparisons/mean-match.md) compares the mean (average) of the values in the tested column to the mean (average) of values in a reference column from the reference table.
- [not null count match](../checks/column/comparisons/not-null-count-match.md) compares the count of not null values in the tested column to the count of not null values in a reference column from the reference table.
- [null count match](../checks/column/comparisons/null-count-match.md) compares the count of null values in the tested column to the count of null values in a reference column from the reference table. 

## Create a new table comparison

Table comparisons can be categorized into three types: profiling, monitoring, and partition. You can read more about
these [types of checks in the DQOps concept section](../dqo-concepts/definition-of-data-quality-checks/index.md).


### ***Navigate to table comparison screen***

To navigate to table comparison screen, follow these steps:

![Create a table comparison](https://dqops.com/docs/images/working-with-dqo/comparisons/create-table-comparison2.png){ loading=lazy; width="1200px" }

1. Select the **Profiling**, **Monitoring checks**, or **Partition checks** section and select the table of interest from the tree view on the left. This will be your source table.

2. Select **Table Comparisons/Daily Comparisons/Monthly Comparisons** tab
3. Click the **New table comparison configuration** button.

### **Select the reference table**

On the table comparison screen, enter a unique name for your comparison and select the reference table by filtering the
connection, schema, and table name from the dropdown menu.

![Select column for comparison](https://dqops.com/docs/images/working-with-dqo/comparisons/select-column-for-comparison.png){ loading=lazy; width="1200px" }

### **Optional data grouping in comparisons**

You can group your data before comparison by using a discriminator column containing data such as location, business units, vendor, etc.
If you want to use data grouping, select columns on compared (source) and reference tables. DQOps compares up to 1000 distinct data group rows by default.
You can modify this limit by changing the `--dqo.sensors.limit.sensor-readout-limit` parameter.
[Learn more about data groupings](../dqo-concepts/measuring-data-quality-with-data-grouping.md).

![Optional data grouping on comparison](https://dqops.com/docs/images/working-with-dqo/comparisons/optional-data-grouping-on-comparison.png){ loading=lazy; width="1200px" }

### **Save a new table comparison**

To save a new table comparison, simply click the **Save** button located in the upper right corner.

This will show a comparison screen where you can match the reference columns to the source columns. DQOps will automatically map the 
columns between the tables based on the column names, but you can make adjustments if necessary.

![Select columns checks](https://dqops.com/docs/images/working-with-dqo/comparisons/selecting-columns-checks2.png){ loading=lazy; width="1200px" }

## Run table comparison

The right part of the comparison screen has a list of comparison checks which you can run. To activate the comparison 
checks you want to run, simply click on the checkboxes.

Once you have activated the checks, you can set the severity level for each one. You can choose from three levels: warning, error, and fatal.
[Learn more about the severity levels](../dqo-concepts/definition-of-data-quality-checks/index.md#issue-severity-levels).

![Table comparisons severity levels](https://dqops.com/docs/images/working-with-dqo/comparisons/table-comparisions-severity-levels2.png){ loading=lazy; width="1200px" }
    
When you create a new table comparison a new category of checks named Comparisons/[name of the comparison] is also 
created on the [Check editor](../dqo-concepts/dqops-user-interface-overview.md#check-editor) with a list of table and column checks.
On the Editor screen you can activate and deactivate comparison checks, set severity levels, run checks and view the results.

![List of checks](https://dqops.com/docs/images/working-with-dqo/comparisons/comparison-check-editor.png){ loading=lazy; width="1200px" }

After making all the necessary adjustments, click the **Save** button to save the changes.  

Once all the required elements have been set, run the comparison between two tables by clicking the **Compare Tables** button. 
After completion of the job, the checkbox will be highlighted with the color of the result: green for valid, yellow for 
warning, orange for error and red for fatal. 

![Table reconciliation summary with discrepancies on columns](https://dqops.com/docs/images/working-with-dqo/comparisons/results2.png){ loading=lazy; width="1200px" }


## View detailed results of table comparison

By expanding the rows, you can view the more detailed results. Additionally, if you set the grouping columns, you will 
be able to view a list of mismatches in the data groupings.

![Table reconciliation details at a column level](https://dqops.com/docs/images/working-with-dqo/comparisons/results-detils.png){ loading=lazy; width="1200px" }

In a situation when you see the results for a table comparison checks that have been collected earlier, you can delete 
the old results by clicking the **Delete results** button. 

![Deleting table comparison results](https://dqops.com/docs/images/working-with-dqo/comparisons/delete-results2.png){ loading=lazy; width="1200px" }


## Deleting table comparison

To remove an entire table comparison configuration, go to the list of table comparisons and click the **Delete** button. 

![Deleting table comparison results](https://dqops.com/docs/images/working-with-dqo/comparisons/deleting-comparison2.png){ loading=lazy; width="1200px" }


## Next steps
- Read the concept of [table comparison (data reconciliation) data quality checks](../categories-of-data-quality-checks/how-to-reconcile-data-and-detect-differences.md) supported by DQOps.
- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../dqops-installation/run-dqops-as-docker-container.md).
- DQOps provide you with summary statistics about your table and column. This information can be valuable in deciding which data quality checks and threshold levels should be set to monitor data quality. For more details about [Basic data statistics, click here](collecting-basic-data-statistics.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](managing-data-quality-incidents-with-dqops.md) and [Slack notifications](../integrations/slack/configuring-slack-notifications.md). 