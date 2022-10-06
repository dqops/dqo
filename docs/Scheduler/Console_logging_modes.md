The `run` and `scheduler start` command can be both run in different modes:
* debug
* info
* silent
* summary

### Debug mode

```
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
Calling Jinja2 rendering template column/uniqueness/distinct_count_percent/bigquery.sql.jinja2
JSON parameters sent to the Jinja2 template:
{"user_home_path":"C:/Users/hviet/OneDrive/Desktop/DQO/userhome","home_type":"DQO_HOME","template_home_path":"column/uniqueness/distinct_count_percent/bigquery.sql.jinja2","parameters":{"connection":{"provider_type":"bigquery","bigquery":{"source_project_id":"bigquery-public-data","billing_project_id":"dqo-ai-testing","authentication_mode":"google_application_credentials","quota_project_id":"dqo-ai-testing"},"time_zone":"UTC"},"table":{"target":{"schema_name":"austin_311","table_name":"311_service_requests"},"columns":{"unique_key":{"type_snapshot":{"column_type":"STRING","nullable":true}},"complaint_description":{"type_snapshot":{"column_type":"STRING","nullable":true}},"source":{"type_snapshot":{"column_type":"STRING","nullable":true}},"status":{"type_snapshot":{"column_type":"STRING","nullable":true}},"status_change_date":{"type_snapshot":{"column_type":"TIMESTAMP","nullable":true}},"created_date":{"type_snapshot":{"column_type":"TIMESTAMP","nullable":true}},"last_update_date":{"type_snapshot":{"column_type":"TIMESTAMP","nullable":true}},"close_date":{"type_snapshot":{"column_type":"TIMESTAMP","nullable":true}},"incident_address":{"type_snapshot":{"column_type":"STRING","nullable":true}},"street_number":{"type_snapshot":{"column_type":"STRING","nullable":true}},"street_name":{"type_snapshot":{"column_type":"STRING","nullable":true}},"city":{"type_snapshot":{"column_type":"STRING","nullable":true}},"incident_zip":{"type_snapshot":{"column_type":"INT64","nullable":true}},"county":{"type_snapshot":{"column_type":"STRING","nullable":true}},"state_plane_x_coordinate":{"type_snapshot":{"column_type":"STRING","nullable":true}},"state_plane_y_coordinate":{"type_snapshot":{"column_type":"FLOAT64","nullable":true}},"latitude":{"type_snapshot":{"column_type":"FLOAT64","nullable":true}},"longitude":{"type_snapshot":{"column_type":"FLOAT64","nullable":true}},"location":{"type_snapshot":{"column_type":"STRING","nullable":true}},"council_district_code":{"type_snapshot":{"column_type":"INT64","nullable":true}},"map_page":{"type_snapshot":{"column_type":"STRING","nullable":true}},"map_tile":{"type_snapshot":{"column_type":"STRING","nullable":true}}}},"column":{"type_snapshot":{"column_type":"STRING","nullable":true}},"column_name":"unique_key","parameters":{"disabled":false},"effective_time_series":{"mode":"current_time","time_gradient":"day"},"effective_dimensions":{},"sensor_definition":{},"provider_sensor_definition":{"type":"sql_template","java_class_name":"ai.dqo.execution.sqltemplates.JinjaSqlTemplateSensorRunner"},"dialect_settings":{"quote_begin":"`","quote_end":"`","quote_escape":"``","table_name_includes_database_name":false}}}
**************************************************

**************************************************
Jinja2 engine has rendered the following template:
column/uniqueness/distinct_count_percent/bigquery.sql.jinja2
**************************************************

**************************************************
Executing SQL on connection connection_1 (bigquery)
SQL to be executed on the connection:
SELECT
    (count(distinct analyzed_table.`unique_key`) / count(analyzed_table.`unique_key`)) * 100 AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
FROM `bigquery-public-data`.`austin_311`.`311_service_requests` AS analyzed_table
GROUP BY time_period
ORDER BY time_period
**************************************************

**************************************************
Finished executing a sensor for a check distinct_count_percent on the table austin_311.311_service_requests using a sensor definition column/uniqueness/distinct_count_percent, sensor result count: 1

Results returned by the sensor:
+------------+-----------+
|actual_value|time_period|
+------------+-----------+
|100.0       |2022-10-06 |
+------------+-----------+
**************************************************

**************************************************
Finished executing rules (thresholds) for a check distinct_count_percent on the table austin_311.311_service_requests, verified rules count: 1

Rule evaluation results:
+------------+--------------+----------------+-------------+------------+-------------------+---------------+--------+-------------------+-----------+--------------------+-------------------+-----------+-------------------+----------------------+-----------------+----------------------------------------+------------------------+-----------+--------+-------------------+---------+----------------+------------------+---------------+
|actual_value|expected_value|time_period     |time_gradient|dimension_id|connection_hash    |connection_name|provider|table_hash         |schema_name|table_name          |column_hash        |column_name|check_hash         |check_name            |quality_dimension|sensor_name                             |executed_at             |duration_ms|severity|rule_hash          |rule_name|high_lower_bound|medium_lower_bound|low_lower_bound|
+------------+--------------+----------------+-------------+------------+-------------------+---------------+--------+-------------------+-----------+--------------------+-------------------+-----------+-------------------+----------------------+-----------------+----------------------------------------+------------------------+-----------+--------+-------------------+---------+----------------+------------------+---------------+
|100.0       |90.0          |2022-10-06T00:00|day          |0           |1846543424268075406|connection_1   |bigquery|7870576410582692297|austin_311 |311_service_requests|2503653626918607587|unique_key |1734883254493501426|distinct_count_percent|uniqueness       |column/uniqueness/distinct_count_percent|2022-10-06T11:21:01.731Z|4438       |0       |1376800555874949950|min_count|90.0            |95.0              |98.0           |
+------------+--------------+----------------+-------------+------------+-------------------+---------------+--------+-------------------+-----------+--------------------+-------------------+-----------+-------------------+----------------------+-----------------+----------------------------------------+------------------------+-----------+--------+-------------------+---------+----------------+------------------+---------------+
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