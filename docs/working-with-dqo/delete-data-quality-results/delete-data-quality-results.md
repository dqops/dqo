# Delete data quality results

In DQO, there are two ways to delete stored data quality check results:

- using the graphical interface 
- using DQO Shell

In DQO, sensor readouts and check results are stored as Apache Parquet files following the Apache
Hive compatible folder tree, partitioned by connection name, table name, and month.

For more information where and how the sensor readouts and check results are stored, see [DQO concepts section](../../dqo-concepts/data-storage/data-storage.md).

You can learn [how to run data quality checks here](../run-data-quality-checks/run-data-quality-checks.md).


## Delete data quality results using the graphical interface

Using graphical interface you can delete data quality results at the connection, table, column, group of checks or individual check level.

To delete the data quality results at the connection, table or column level follow the steps below

1. In DQO graphical interface, click on the three-dot icon next to the name of a connection, table or column in the tree view.

    ![Click three-dot icon](https://dqo.ai/docs/images/working-with-dqo/delete-data-quality-results/click-three-dot-icon.jpg)

2. From the drop-down menu select the **Delete data** command.

    ![Select Delete data command](https://dqo.ai/docs/images/working-with-dqo/delete-data-quality-results/delete-data-command.jpg)
   
3. In the modal window, you can specify whether you want to delete all data, data for a specific time range or a specific category.

    ![Delete data modal window](https://dqo.ai/docs/images/working-with-dqo/delete-data-quality-results/delete-data-modal-window.jpg)

4. Click the **Delete** button to delete the selected data.


##  Delete data quality results using the DQO Shell

To delete data quality results using the DQO Shell, use the [data delete command](https://dqo.ai/docs/command-line-interface/data/#dqo-data-delete). 

Data delete command requires the following arguments:

- Connection name `--connection=<connection>`
- Full table name (schema.table), supports wildcard patterns 'sch*.tab*' `--table=<table>`
- Beginning of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD `--begin=<begin>`
- End of the period for deletion. Date in format YYYY.MM or YYYY.MM.DD `--end=<en`

For example toTo delete all data from May 2023 for the connection "testconnection", table "austin_crime.crime" run the 
following command in DQO Shell:

```
 dqo.ai> data delete --connection=testconnection --table=austin_crime.crime --begin=2023.05 --end=2023.05
```
   
For a full description of the `data delete` command see the [Command-line interface section](https://dqo.ai/docs/command-line-interface/data/#dqo-data-delete).