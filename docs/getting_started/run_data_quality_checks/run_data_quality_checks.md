This section describes how to run data quality checks from the shell

Once the check is configured (see [how to configure rules](../../rule_reference/how_to_configure_rules.md)) now it is possible to run the check.

### Run check 
To run a check provide connection and table names (including schema name) in
`check run` command.

```
check run -c=connection_1 --table=austin_311.311_service_requests
```

It is also possible to run a check on a specific column.
In order to do this, add the name of the check and the column name to the above:

```
check run -c=connection_1 --table=austin_311.311_service_requests --check=distinct_count_percent --column=unique_key
```
The result :

```
Check evaluation summary per table:
+------------+-------------------------------+------+------------+-----------+----------+-------------+-----------+
|Connection  |Table                          |Checks|Sensor      |Valid      |Alerts    |Alerts       |Alerts     |
|            |                               |      |results     |results    |(low)     |(medium)     |(high)     |
+------------+-------------------------------+------+------------+-----------+----------+-------------+-----------+
|connection_1|austin_311.311_service_requests|1     |1           |1          |0         |0            |0          |
+------------+-------------------------------+------+------------+-----------+----------+-------------+-----------+
```
A command can also be executed in a debug mode. Add `--mode=debug` at the end of a command.
```
check run -c=connection_1 --table=austin_311.311_service_requests --check=distinct_count_percent --column=unique_key --mode=debug
```
And the result of that command is:

``` 
**************************************************
Executing quality checks on table austin_311.311_service_requests from connection connection_1
**************************************************

**************************************************
Executing a sensor for a check distinct_count_percent on the table austin_311.311_service_requests using a sensor definition column/uniqueness/distinct_count_percent
**************************************************

**************************************************
Calling Jinja2 rendering template column/uniqueness/distinct_count_percent/bigquery.sql.jinja2
JSON parameters sent to the Jinja2 template:
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
|100.0       |2022-09-08 |
```
