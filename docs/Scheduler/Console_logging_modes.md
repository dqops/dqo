The `run` and `scheduler start` command can be both run in different modes:
* debug
* info
* silent
* summary

### Debug mode

```

```

### Info mode

```
dqo.ai> run -m=info
DQO was started in a server mode.
Press any key key to stop the application.
SOURCES local <-> cloud synchronization started
SOURCES local <-> cloud synchronization finished
SENSORS local <-> cloud synchronization started
SENSORS local <-> cloud synchronization finished
RULES local <-> cloud synchronization started
RULES local <-> cloud synchronization finished
DATA_READINGS local <-> cloud synchronization started
DATA_READINGS local <-> cloud synchronization finished
DATA_ALERTS local <-> cloud synchronization started
DATA_ALERTS local <-> cloud synchronization finished
DATA_READINGS local <-> cloud synchronization started
DATA_READINGS local <-> cloud synchronization finished
DATA_ALERTS local <-> cloud synchronization started
DATA_ALERTS local <-> cloud synchronization finished
**************************************************
Executing data quality checks on table austin_311.311_service_requests from connection connection_1
**************************************************

**************************************************
Executing a sensor for a check distinct_count_percent on the table austin_311.311_service_requests using a sensor definition column/uniqueness/distinct_count_percent
**************************************************

**************************************************
Finished executing rules (thresholds) for a check distinct_count_percent on the table austin_311.311_service_requests, verified rules count: 1
**************************************************

+------------+-------------------------------+------+--------------+-------------+------------+---------------+-------------+
|Connection  |Table                          |Checks|Sensor results|Valid results|Alerts (low)|Alerts (medium)|Alerts (high)|
+------------+-------------------------------+------+--------------+-------------+------------+---------------+-------------+
|connection_1|austin_311.311_service_requests|1     |1             |1            |0           |0              |0            |
+------------+-------------------------------+------+--------------+-------------+------------+---------------+-------------+
DATA_READINGS local <-> cloud synchronization started
DATA_READINGS local <-> cloud synchronization finished
DATA_ALERTS local <-> cloud synchronization started
DATA_ALERTS local <-> cloud synchronization finished
```

### Silent mode

```
dqo.ai> run -m=silent
DQO was started in a server mode.
Press any key key to stop the application.
SOURCES local <-> cloud synchronization started
SOURCES local <-> cloud synchronization finished
SENSORS local <-> cloud synchronization started
SENSORS local <-> cloud synchronization finished
RULES local <-> cloud synchronization started
RULES local <-> cloud synchronization finished
DATA_READINGS local <-> cloud synchronization started
DATA_READINGS local <-> cloud synchronization finished
DATA_ALERTS local <-> cloud synchronization started
DATA_ALERTS local <-> cloud synchronization finished
DATA_READINGS local <-> cloud synchronization started
DATA_READINGS local <-> cloud synchronization finished
DATA_ALERTS local <-> cloud synchronization started
DATA_ALERTS local <-> cloud synchronization finished
```

### Summary mode

This is the default mode.

```
dqo.ai> run -m=summary
DQO was started in a server mode.
Press any key key to stop the application.
SOURCES local <-> cloud synchronization started
SOURCES local <-> cloud synchronization finished
SENSORS local <-> cloud synchronization started
SENSORS local <-> cloud synchronization finished
RULES local <-> cloud synchronization started
RULES local <-> cloud synchronization finished
DATA_READINGS local <-> cloud synchronization started
DATA_READINGS local <-> cloud synchronization finished
DATA_ALERTS local <-> cloud synchronization started
DATA_ALERTS local <-> cloud synchronization finished
DATA_READINGS local <-> cloud synchronization started
DATA_READINGS local <-> cloud synchronization finished
DATA_ALERTS local <-> cloud synchronization started
DATA_ALERTS local <-> cloud synchronization finished
+------------+-------------------------------+------+--------------+-------------+------------+---------------+-------------+
|Connection  |Table                          |Checks|Sensor results|Valid results|Alerts (low)|Alerts (medium)|Alerts (high)|
+------------+-------------------------------+------+--------------+-------------+------------+---------------+-------------+
|connection_1|austin_311.311_service_requests|1     |1             |1            |0           |0              |0            |
+------------+-------------------------------+------+--------------+-------------+------------+---------------+-------------+
DATA_READINGS local <-> cloud synchronization started
DATA_READINGS local <-> cloud synchronization finished
DATA_ALERTS local <-> cloud synchronization started
DATA_ALERTS local <-> cloud synchronization finished
```