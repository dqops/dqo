# Table comparison 

Table comparison is a new DQO feature that enables users to compare two tables. With this feature, users can compare two data groupings from different tables. 
It is especially useful when dealing with two similar tables and you want to identify differences and if one has changed significantly compared to the other.
By conducting these comparisons, users can gain valuable insights into how data evolves over time or how it might vary between different sources.

## Creating table comparison

1. Go to **profiling/monitoring/partitioned** section on table level.

2. Select **Table Comparisons/Daily Comparisons/Monthly Comparisons** tab and click "New table comparison configuration"

    ![Create table comparison](https://dqops.com/docs/images/working-with-dqo/comparisons/create-table-comparison.png)

3. Enter comparsion name and select connection, schema, and reference table. 
    If table is selected will it be possible to select columns from 2 tables.
    (It is important that the number of columns is selected in both tables)
    Note: DQO suppors up to 1000 rows so if Column distinct count is above 1000, or it is not collected warning will be displayed 

    ![Create table comparison](https://dqops.com/docs/images/working-with-dqo/comparisons/select-table-columns.png)

4. When everything is finished, click Save button and you will see table comparison screen that is ready to use.

## Using table comparison

Now then table comparison configuration is created and ready to use, it is possible to execute checks between 2 tables. 
For table level are available 2 checks: row count and column count. For choosen columns it is possible to compare then due to
min, max, mean, sum, null count and not null count checks. 

Thats how to select columns and active selected checks:

![Create table comparison](https://dqops.com/docs/images/working-with-dqo/comparisons/selecting-columns-checks.png)

For each enabled check, you can specify severity levels for warnings, fatals, and errors by expanding the row where the check is currently located.

![Create table comparison](https://dqops.com/docs/images/working-with-dqo/comparisons/severity-levels.png)

<!-- ![Create table comparison](https://dqops.com/docs/images/working-with-dqo/comparisons/4.png) -->

<!-- -now then table comparison is created it is possible to enable checks to execute. 

-(for table level: Column Count and Row count)

-(for column level: Min, Max, Mean, Sum, Null Count, Not Null Count)

-here user can select columns to compare  -->

When everything needed is selected it is possible to compare those 2 tables click "Compare tables" button. 

Clicking on this button will execute the job, that runs checks. After job is finished it is posible to see the result by exteding rows....

After seing the results it is possible to delete them by clicking "Delete results": 

## Deleting table comparison

If it is needed to delete whole table comparison configuration, not only check results, its can be done from list of table comparisons configuration by clicking: 
![Create table comparison](https://dqops.com/docs/images/working-with-dqo/comparisons/deleting-comparison.png)