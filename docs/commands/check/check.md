# Check 

## run
__Synopsis__
 <pre><code>check run  [-hl] [-h] [-of=&lt;outputFormat&gt] [-c=&lt;connetion&gt] [-t=&lt;table&gt] [-col=&lt;column&gt] [-k=&lt;check&gt] [-s=&lt;sensor&gt] [-e] [-d] [-m=&lt;mode&gt] </code></pre>
___
__Description__
Run checks matching specified filters
___
__Options__
=== "Options"

    | Command                | Description                                                               |
    |------------------------|---------------------------------------------------------------------------|
    |`-hl` `--headless` | Run the command in an headless (no user input allowed) mode |
    |`-h` `--help`| Show the help for the command and parameters |
    |`-of` `--output-format` <br>=&lt;outputFormat&gt;</br>| Output format for tabular responses | 
    |`-c` `--connection` <br>=&lt;connetion&gt;</br>| Connection name, supports patterns like 'conn*' |
    |`-t` `--table` <br>=&lt;table&gt;</br>| Full table name (schema.table), supports patterns like 'sch*.tab*' |
    |`-col` `--column` <br>=&lt;column&gt;</br>| Column name, supports patterns like '*_id' |       
    |`-k` `--check` <br>=&lt;check&gt;</br>| Data quality check name, supports patterns like '*_id' |
    |`-s` `--sensor` <br>=&lt;sensor&gt;</br>| Data quality sensor name (sensor definition or sensor name), supports patterns like 'table/validity/*' |
    |`-e` `--enabled`| Runs only enabled or only disabled sensors, by default only enabled sensors are executed |
    |`-d` `--dummy`| Runs data quality check in a dummy mode, sensors are not executed on the target database, but the rest of the process is performed |
    |`-m` `--mode` <br>=&lt;mode&gt;</br>| Reporting mode (silent, summary, debug) |
___

## Examples
Here we would like to discuss and show the use of command `check run` with different flags.

## Connection name
The basic `check run` command, without any specified flags, executes all the defined checks across imported tables 
is existing connections. With `-c` or `--connection` user can specify the connection.

```
check run -c=<connection_name>
```

All the defined checks on tables
existing in this connection will be executed.


```
dqo.ai> check run -c=covid_italy
Check evaluation summary per table:
+-----------+------------------------------+------+--------------+-------------+------------+---------------+-------------+
|Connection |Table                         |Checks|Sensor results|Valid results|Alerts (low)|Alerts (medium)|Alerts (high)|
+-----------+------------------------------+------+--------------+-------------+------------+---------------+-------------+
|covid_italy|covid19_italy.data_by_province|6     |6             |6            |0           |0              |0            |
+-----------+------------------------------+------+--------------+-------------+------------+---------------+-------------+
|covid_italy|covid19_italy.data_by_region  |2     |2             |1            |1           |0              |0            |
+-----------+------------------------------+------+--------------+-------------+------------+---------------+-------------+
|covid_italy|covid19_italy.national_trends |3     |3             |0            |2           |1              |0            |
+-----------+------------------------------+------+--------------+-------------+------------+---------------+-------------+
dqo.ai>
```

## Table name
It is also possible to run checks defined on a singular table. To do that user needs to specify table name with
`-t` or `--table`

!!! tip
    It is convenient to combine `-c` connection with following `-t` table. By doing so, code completion shows available
    tables in a specified connection thus it is easier to find the desired one.
    ```
    dqo.ai> check run -c=covid_italy -t=covid19_italy.
    covid19_italy.data_by_province   covid19_italy.data_by_region     covid19_italy.national_trends
    ```

## Reporting mode
Reporting mode is a way of informing the user about check executions. There are three reporting modes: summary, silent,
and debug. Each of them saves check readings and alerts in a `.parquet` file that can be found at 
`userhome/.data/readings/` and `userhome/.data/alerts/` respectively.

The default mode is summary.
### --mode=summary
In this mode after check execution user is provided with summary information in a form of table.
The table contains information about check's connection, table, the number of checks performed on a table, the number 
of valid results, and the number of severity alerts.

```
dqo.ai> check run -c=connection -t=schema_name.table_name -m=summary
Check evaluation summary per table:
+----------+----------------------+------+--------------+-------------+------------+---------------+-------------+
|Connection|Table                 |Checks|Sensor results|Valid results|Alerts (low)|Alerts (medium)|Alerts (high)|
+----------+----------------------+------+--------------+-------------+------------+---------------+-------------+
|connection|schema_name.table_name|5     |5             |1            |1           |3              |0            |
+----------+----------------------+------+--------------+-------------+------------+---------------+-------------+
```

```
dqo.ai> check run -c=covid_italy -t=covid19_italy.national_trends -m=summary
Check evaluation summary per table:
+-----------+-----------------------------+------+--------------+-------------+------------+---------------+-------------+
|Connection |Table                        |Checks|Sensor results|Valid results|Alerts (low)|Alerts (medium)|Alerts (high)|
+-----------+-----------------------------+------+--------------+-------------+------------+---------------+-------------+
|covid_italy|covid19_italy.national_trends|1     |1             |1            |0           |0              |0            |
+-----------+-----------------------------+------+--------------+-------------+------------+---------------+-------------+
```

### --mode=silent
Silent mode saves the readings and the results in a `user_home` directory. It does not show any information after 
check execution (thus the name).

### --mode=debug
Debug mode (as the name suggests) is the most useful mode when there is a need to ...

It displays the greatest amount of information on checks executions.

Namely:

1. On what tables checks are run
2. Which sensor run on a table
3. Which template is used
4. Rendered query and where it is executed (provider and table)
5. Information about query execution success
6. Query result
7. Rule evaluation result
8. Check evaluation summary (the same one as in [summary mode](check.md#-modesummary))

```
dqo.ai> check run -c=covid_italy -t=covid19_italy.national_trends -m=debug
**************************************************
Executing quality checks on table covid19_italy.national_trends from connection covid_italy
**************************************************

**************************************************
Executing a sensor for a check current_delay on the table covid19_italy.national_trends using a sensor definition table/timeliness/current_delay
**************************************************

**************************************************
Calling Jinja2 rendering template table/timeliness/current_delay/bigquery.sql.jinja2
**************************************************

**************************************************
Jinja2 engine has rendered the following template:
table/timeliness/current_delay/bigquery.sql.jinja2
**************************************************

**************************************************
Executing SQL on connection covid_italy (bigquery)
SQL to be executed on the connection:
SELECT
    TIMESTAMP_DIFF(CURRENT_TIMESTAMP(),
        MAX(analyzed_table.date),
        HOUR) AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
FROM `bigquery-public-data`.`covid19_italy`.`national_trends` AS analyzed_table
GROUP BY time_period
ORDER BY time_period
**************************************************

**************************************************
Finished executing a sensor for a check current_delay on the table covid19_italy.national_trends using a sensor definition table/timeliness/current_delay, sensor result count: 1

Results returned by the sensor:
+------------+-----------+
|actual_value|time_period|
+------------+-----------+
|14          |2022-04-27 |
+------------+-----------+
**************************************************

**************************************************
Finished executing rules (thresholds) for a check current_delay on the table covid19_italy.national_trends, verified rules count: 1

Rule evaluation results:
+------------+--------------+----------------+-------------+------------+-------------------+---------------+--------+-------------------+-------------+---------------+-------------------+-------------+-----------------+------------------------------+------------------------+-----------+--------+-------------------+---------+----------------+------------------+---------------+
|actual_value|expected_value|time_period     |time_gradient|dimension_id|connection_hash    |connection_name|provider|table_hash         |schema_name  |table_name     |check_hash         |check_name   |quality_dimension|sensor_name                   |executed_at             |duration_ms|severity|rule_hash          |rule_name|high_lower_bound|medium_lower_bound|low_lower_bound|
+------------+--------------+----------------+-------------+------------+-------------------+---------------+--------+-------------------+-------------+---------------+-------------------+-------------+-----------------+------------------------------+------------------------+-----------+--------+-------------------+---------+----------------+------------------+---------------+
|14.0        |30.0          |2022-04-27T00:00|day          |0           |5420172246317321103|covid_italy    |bigquery|1244179151832232205|covid19_italy|national_trends|6418180083037863268|current_delay|timeliness       |table/timeliness/current_delay|2022-04-27T07:59:37.610Z|1828       |0       |1663691046277436078|max_count|30.0            |26.0              |24.0           |
+------------+--------------+----------------+-------------+------------+-------------------+---------------+--------+-------------------+-------------+---------------+-------------------+-------------+-----------------+------------------------------+------------------------+-----------+--------+-------------------+---------+----------------+------------------+---------------+
**************************************************

Check evaluation summary per table:
+-----------+-----------------------------+------+--------------+-------------+------------+---------------+-------------+
|Connection |Table                        |Checks|Sensor results|Valid results|Alerts (low)|Alerts (medium)|Alerts (high)|
+-----------+-----------------------------+------+--------------+-------------+------------+---------------+-------------+
|covid_italy|covid19_italy.national_trends|1     |1             |1            |0           |0              |0            |
+-----------+-----------------------------+------+--------------+-------------+------------+---------------+-------------+
```