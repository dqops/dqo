# Create and run table comparisons 

Table comparison enables you to identify differences between two tables. It is useful when you want to compare datasets and validate successful data replication or migration. You can compare tables between any supported data sources.

   Using Table comparison you can monitor tables using the following comparison checks:

 - Table-level checks:
    - row_count_match compares the row count of the current (parent) table to the row count of the reference table.
    - column_count_match compares the column count of the current (parent) table to the column count of the reference table.
 - Column-level checks:
    - sum match
    - min match
    - max match
    - mean match
    - not null count match
    - null count match
<!-- lista z kropkami -->
## Create a new table comparison

Just like checks, table comparison can be in any of the three types: profiling, monitoring, or partition. Read more about different types of checks in the DQOps concept section.

1. Go to **Profiling, Monitoring checks, or Partition checks** and select the table of interest from the tree view on the left. This will be your source table.

2. Select **Table Comparisons/Daily Comparisons/Monthly Comparisons** tab and click the New table comparison configuration button.

    ![Create table comparison](https://dqops.com/docs/images/working-with-dqo/comparisons/create-table-comparison.png)

3. Enter a unique name for your comparison and select the reference table by filtering the connection, schema, and table name.

    ![Select table columns](https://dqops.com/docs/images/working-with-dqo/comparisons/select-table-columns.png)

4. You have the option to select columns from compared and reference tables for data grouping, which will be used to compare data. DQOps compares up to 1000 distinct data group rows by default. You can change this limit by modyfying  --dqo.sensors.limit.sensor-readout-limit parameter. Learn more about data groupings. 

5. To save a new table comparison, click the Save button in the upper right corner and a comparison screen will appear

6. The comparison screen allows the selection of comparison checks and columns you would like to compare between the source and target.. DQOps will map the columns between the tables automatically based on the column names. You can make adjustments if necessary.

![Select columns checks](https://dqops.com/docs/images/working-with-dqo/comparisons/selecting-columns-checks.png)

7. For each enabled check, you can specify the severity level by expanding the row with the name of the table or column. You can choose from 3 severity levels in DQOps: warning, error, and fatal. Learn more about the severity levels. 

![Select severity levels](https://dqops.com/docs/images/working-with-dqo/comparisons/severity-levels.png)

8. Click the Save button after making all the adjustments.  

## Run table comparison

Once all the requisite elements have been set, run the comparison between two tables by clicking the    "Compare Tables" button. 
After completion of the job, the results can be accessed by expanding the rows. This will reveal a comprehensive list of valid results, as well as warnings, fatals and errors. Additionally, if you set the grouping columns, you will be able to view a list of missmatches in data groupings.

To delete the results click Delete results button.

## Deleting table comparison

To remove an entire table comparison configuration go to the list of table comparison configurations and Click the  Delete button. This action will remove a table comparison configuration.

![Deleting comparison](https://dqops.com/docs/images/working-with-dqo/comparisons/deleting-comparison.png)