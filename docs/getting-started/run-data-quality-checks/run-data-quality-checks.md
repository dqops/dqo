# Run data quality check

Describe how to enable and run data quality checks in UI



## Setting up a check
To set up a basic data quality check, table editing information needs to be provided.
To do this, use the command below :
```
table edit -c=connection_1 --table=austin_311.311_service_requests
```
Following message appears:
```
dqo.ai> table edit -c=connection_1 --table=austin_311.311_service_requests
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```
and VS Code launches.

Now the YAML file can be modified to set up a data quality check.

Here is how the YAML file looks like:
```yaml linenums="1" hl_lines="16-26"
--8<-- "docs/getting_started/yamls/example.yaml"
```
Checks are added below a column and its descriptions that is chosen to be checked.

In our case it is the column named "unique_key".
Those are the highlighted lines. They define used sensor along with [min_count](../../rule_reference/comparison/min_count.md) rule.

Firstly write `checks:` below a chosen column, then write a [dimension](../../dqo-concepts/sensors/sensors.md) name and a [sensor](../../sensor_reference/what_is_a_sensor.md) name in our case this is `uniqueness` and [distinct_count_percent](../../sensor_reference/uniqueness/distinct_count_percent/distinct_count_percent.md).

Next write `rules:` and define it, in this example this is [min_count](../../rule_reference/comparison/min_count.md).

Save the file.

Here is a rendered query that is later sent to BigQuery.

```SQL
{{ process_template_request( get_request( "docs/getting_started/requests/example.json" ) ) }}
```


Once the check is configured now it is possible to run the check.

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
