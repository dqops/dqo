# Delete data quality results

In DQO, there are two ways to delete stored data quality check results:

- using the graphical interface 
- using DQO Shell

In DQO, sensor readouts and check results are stored as Apache Parquet files following the Apache
Hive compatible folder tree, partitioned by connection name, table name, and month.

For more information how the sensor readouts and check results are stored, see [DQO concepts section](../../dqo-concepts/data-storage/data-storage.md).

You can learn [how to run data quality checks here](../run-data-quality-checks/run-data-quality-checks.md).


## Delete data quality results using the graphical interface

Using graphical interface you can delete data quality results at the connection, table, column, group of checks or individual check level.


To delete the data quality results at the connection, table or column level follow the steps below

1. In DQO graphical interface, click on the three-dot icon next to the name of a connection, table or column in the tree view.

    ![Click three-dot icon](https://dqo.ai/docs/images/working-with-dqo/delete-data-quality-results/click-three-dot-icon.jpg)

2. From the drop-down menu select the **Delete data** command.

   ![Select Delete data command](https://dqo.ai/docs/images/working-with-dqo/delete-data-quality-results/delete-data-command.jpg)
   


##  Delete data quality results using the DQO Shell

To delete dataquality results using the DQO Shell, follow the steps below. 

1. Run the following command in DQO Shell to edit YAMl configuration file and define data quality checks.
    ```
    dqo.ai> table edit
    ```
   




## What's next

- [Learn about setting schedules](../schedules/index.md) to easily customize when checks are run.