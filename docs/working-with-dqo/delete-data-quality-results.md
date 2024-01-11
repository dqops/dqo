# Delete data quality results
Read this guide to learn how to delete a subset of outdated data quality results, especially when tables were decommissioned or checks were run by mistake.

## Overview

In DQOps, there are two ways to delete stored data quality results:

- using the user interface 
- using DQOps Shell

In DQOps, sensor readouts and check results are stored as Apache Parquet files following the Apache
Hive compatible folder tree, partitioned by connection name, table name, and month.

For more information where and how the sensor readouts and check results are stored, see [DQOps concepts section](../dqo-concepts/data-storage-of-data-quality-results.md).

You can learn [how to run data quality checks here](run-data-quality-checks.md).


## Delete data quality results using the user interface

Using user interface you can delete data quality results at the connection, table, column, group of checks or individual check level.

To delete the data quality results at the connection, schema, table or column level follow the steps below

1. In DQOps user interface, click on the three-dot icon next to the name of a connection, schema, table or column in the tree view.

    ![Click three-dot icon](https://dqops.com/docs/images/working-with-dqo/delete-data-quality-results/click-three-dot-icon2.png)

2. From the drop-down menu select the **Delete data** command.

    ![Select Delete data command](https://dqops.com/docs/images/working-with-dqo/delete-data-quality-results/delete-data-command2.png)
   
3. In the window, you can specify whether you want to delete all data, data for a specific time range, check type, or time gradient.
    You can also decide to delete only results from basic statistics, checks, sensors or execution errors.

    ![Delete data window](https://dqops.com/docs/images/working-with-dqo/delete-data-quality-results/delete-data-window2.png)

4. Click the **Delete** button to delete the selected data.


##  Delete data quality results using the DQOps Shell

To delete data quality results using the DQOps Shell, use the [data delete command](../command-line-interface/data.md). 

To delete all the data for a connection run the following command

```
dqo> date delete
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
check name, check category type, and more. For a full description of the `data delete` command and its parameters, see the [Command-line interface section](../command-line-interface/data.md).