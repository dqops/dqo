# Run data quality check

In the Getting started example, we describe how to run a table-level row_count check using graphic interface.
For a full description of how to run checks using graphic interface and CLI, see [Working with DQO](../../working-with-dqo/adding-data-source-connection/index.md).
For more information about checks, see [DQO concepts section](../../dqo-concepts/checks/index.md). 


Go to the Profiling section
On the tree view on the left, find a tabletableor columncolumnof interest by expanding the added connection.
In the list of checks on the right, enable the selected data quality checks by clicking the switch.
Adjust the threshold levels or leave the default option.
Clickin the upper right corner.
Run data quality check by clicking the Run Check icon.


```

dqo.ai> cloud sync all
```

``` 
sources local <-> cloud synchronization started
sources local <-> cloud synchronization finished
sources synchronization between local DQO User Home and DQO Cloud finished.

sensors local <-> cloud synchronization started
sensors local <-> cloud synchronization finished
sensors synchronization between local DQO User Home and DQO Cloud finished.

rules local <-> cloud synchronization started
rules local <-> cloud synchronization finished
rules synchronization between local DQO User Home and DQO Cloud finished.

checks local <-> cloud synchronization started
checks local <-> cloud synchronization finished
checks synchronization between local DQO User Home and DQO Cloud finished.

data_sensor_readouts local <-> cloud synchronization started
data_sensor_readouts local <-> cloud synchronization finished
data_sensor_readouts synchronization between local DQO User Home and DQO Cloud finished.

data_check_results local <-> cloud synchronization started
data_check_results local <-> cloud synchronization finished
data_check_results synchronization between local DQO User Home and DQO Cloud finished.

data_errors local <-> cloud synchronization started
data_errors local <-> cloud synchronization finished
data_errors synchronization between local DQO User Home and DQO Cloud finished.

data_statistics local <-> cloud synchronization started
data_statistics local <-> cloud synchronization finished
data_statistics synchronization between local DQO User Home and DQO Cloud finished.

```