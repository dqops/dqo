# Create and run table comparisons 

Table comparison enables you to identify differences between two tables. It is useful when you want to compare datasets
and validate successful data replication or migration. You can compare tables between any [supported data sources](../../working-with-dqo/adding-data-source-connection/index.md).

Using Table comparison you can monitor tables using the following comparison checks:

**Table-level checks:**

- [row count match](../../checks/table/comparisons/row-count-match.md) compares the row count of the current (parent) table to the row count of the reference table.
- [column count match](../../checks/table/comparisons/column-count-match.md) compares the column count of the current (parent) table to the column count of the reference table.

**Column-level checks:**

- [sum match](../../checks/column/comparisons/sum-match.md) compares the sum of the values in the tested column to the sum of values in a reference column from the reference table.
- [min match](../../checks/column/comparisons/min-match.md) compares the minimum value in the tested column to the minimum value in a reference column from the reference table.
- [max match](../../checks/column/comparisons/max-match.md) compares the maximum value in the tested column to the maximum value in a reference column from the reference table.
- [mean match](../../checks/column/comparisons/mean-match.md) compares the mean (average) of the values in the tested column to the mean (average) of values in a reference column from the reference table.
- [not null count match](../../checks/column/comparisons/not-null-count-match.md) compares the count of not null values in the tested column to the count of not null values in a reference column from the reference table.
- [null count match](../../checks/column/comparisons/null-count-match.md) compares the count of null values in the tested column to the count of null values in a reference column from the reference table. 

## Create a new table comparison

Just like checks, table comparison can be in any of the three types: profiling, monitoring, or partition. Read more about
different [types of checks in the DQOps concept section](../../dqo-concepts/checks/index.md).

To create a new table comparison follow these steps:

1. Select **Profiling**, **Monitoring checks**, or **Partition checks** section in the top Navigation bar and select the table of interest from the tree view on the left. This will be your source table.

2. Select **Table Comparisons/Daily Comparisons/Monthly Comparisons** tab and click the **New table comparison configuration** button.

    ![Create a table comparison](https://dqops.com/docs/images/working-with-dqo/comparisons/create-table-comparison1.png)

3. Enter a unique name for your comparison and select the reference table by filtering the connection, schema, and table name.

    ![Select table columns](https://dqops.com/docs/images/working-with-dqo/comparisons/select-table-columns1.png)

4. You have the option to group your data before comparison by a discriminator column containing data such as location, business units, vendor, etc.
    If you want to use data grouping select columns on compared (source) and reference tables. DQOps compares up to 1000 distinct data group rows by default.
    You can change this limit by modifying the `--dqo.sensors.limit.sensor-readout-limit` parameter.
    [Learn more about data groupings](../../dqo-concepts/data-grouping/data-grouping.md).

5. To save a new table comparison, click the **Save** button in the upper right corner and a comparison screen will appear.

6. The comparison screen allows the selection of comparison checks and columns you would like to compare between compared (source) and reference tables.
    DQOps will map the columns between the tables automatically based on the column names. You can make adjustments if necessary.
    To enable comparison checks which you would like to run, click on the checkboxes.

    ![Select columns checks](https://dqops.com/docs/images/working-with-dqo/comparisons/selecting-columns-checks1.png)

7. For each enabled check, you can specify the severity level by expanding the row with the name of the table or column.
   You can choose from 3 severity levels: warning, error, and fatal. [Learn more about the severity levels](../../../dqo-concepts/checks/#severity-levels).

    ![Select severity levels](https://dqops.com/docs/images/working-with-dqo/comparisons/severity-levels1.png)
    
    When you create a new table comparison a new category of checks named Comparisons/[name of the comparison] is also created on the list of table and column checks.
    On the screen with a list of checks you can enable and disable comparison checks, set severity levels, run checks and view the results.

    ![List of checks](https://dqops.com/docs/images/working-with-dqo/comparisons/list-of-checks.png)

8. After making all the adjustments, click the **Save** button to save the changes.  

## Run table comparison

Once all the required elements have been set, run the comparison between two tables by clicking the **Compare Tables** button. 
After completion of the job, the checkbox will be highlighted with the color of the result: green for valid, yellow for warning, orange for error and red for fatal. 
By expanding the rows, you can view the more detailed results. Additionally, if you set the grouping columns, you will be able to view a list of mismatches in the data groupings.

![Results](https://dqops.com/docs/images/working-with-dqo/comparisons/results1.png)

In a situation when you see the results for a table comparison checks that have been collected earlier, you can delete the old results by clicking the **Delete results** button. 

![Deleting results](https://dqops.com/docs/images/working-with-dqo/comparisons/delete-results1.png)

## Deleting table comparison

To remove an entire table comparison configuration, go to the list of table comparisons and click the **Delete** button. 

![Deleting comparison](https://dqops.com/docs/images/working-with-dqo/comparisons/deleting-comparison1.png)