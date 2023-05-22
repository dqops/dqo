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

    ![Click three-dot icon](https://dqops.com/docs/images/working-with-dqo/delete-data-quality-results/click-three-dot-icon.jpg)

2. From the drop-down menu select the **Delete data** command.

    ![Select Delete data command](https://dqops.com/docs/images/working-with-dqo/delete-data-quality-results/delete-data-command.jpg)
   
3. In the modal window, you can specify whether you want to delete all data, data for a specific time range or a specific category.

    ![Delete data modal window](https://dqops.com/docs/images/working-with-dqo/delete-data-quality-results/delete-data-modal-window.jpg)

4. Click the **Delete** button to delete the selected data.


##  Delete data quality results using the DQO Shell

To delete data quality results using the DQO Shell, use the [data delete command](../../command-line-interface/data.md). 

To delete all the data for a connection run the following command

```
date delete
```

Type the name of the connection you want to delete e.g. 

```
Connection name (--connection): **testconnection**
```

A summary of deleted data similar to the following table will be displayed.

```
3 affected partitions.
+--------------------+--------------+------------------+----------+-------------+-----------------+
|Data type           |Connection    |Table             |Month     |Affected rows|Partition deleted|
+--------------------+--------------+------------------+----------+-------------+-----------------+
|data_sensor_readouts|testconnection|austin_crime.crime|2023-05-01|2            |true             |
+--------------------+--------------+------------------+----------+-------------+-----------------+
|data_check_results  |testconnection|austin_crime.crime|2023-05-01|2            |true             |
+--------------------+--------------+------------------+----------+-------------+-----------------+
|data_statistics     |testconnection|austin_crime.crime|2023-05-01|131          |true             |
+--------------------+--------------+------------------+----------+-------------+-----------------+
```

Using various parameters, you can limit the data that will be deleted to a specific table, column, time period, data type,
check name, check category type, and more. For a full description of the `data delete` command and its parameters, see the [Command-line interface section](../../command-line-interface/data.md).