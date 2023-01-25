#table sensors:

##category: <b>standard</b>
___

###<b>row_count</b>
Tabular sensor that executes a row count query on a table.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/table/standard/row_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/standard/row_count/snowflake.sql.jinja2"
    ```

=== "postgresql"
    ```
    --8<-- "home/sensors/table/standard/row_count/postgresql.sql.jinja2"
    ```

___


##category: <b>timeliness</b>
___

###<b>days_since_most_recent_event</b>
Tabular sensor that runs a query calculating maximum days since the most recent event.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/table/timeliness/days_since_most_recent_event/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/timeliness/days_since_most_recent_event/snowflake.sql.jinja2"
    ```

___

###<b>partition_reload_lag</b>
Tabular sensor that runs a query calculating maximum difference in days between ingestion timestamp and event timestamp rows.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/table/timeliness/partition_reload_lag/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/timeliness/partition_reload_lag/snowflake.sql.jinja2"
    ```

___

###<b>days_since_most_recent_ingestion</b>
Tabular sensor that runs a query calculating the time difference in days between the current date and most recent data loading timestamp (staleness).
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/table/timeliness/days_since_most_recent_ingestion/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/timeliness/days_since_most_recent_ingestion/snowflake.sql.jinja2"
    ```

___

###<b>data_ingestion_delay</b>
Tabular sensor that runs a query calculating the time difference in days between the most recent transaction timestamp and the most recent data loading timestamp.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/table/timeliness/data_ingestion_delay/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/timeliness/data_ingestion_delay/snowflake.sql.jinja2"
    ```

___


##category: <b>sql</b>
___

###<b>sql_condition_passed_percent</b>
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_passed_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_passed_percent/snowflake.sql.jinja2"
    ```

___

###<b>sql_condition_failed_count</b>
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that do not meet the condition.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_failed_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_failed_count/snowflake.sql.jinja2"
    ```

___

###<b>sql_aggregated_expression</b>
Table level sensor that executes a given SQL expression on a table.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/table/sql/sql_aggregated_expression/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/sql/sql_aggregated_expression/snowflake.sql.jinja2"
    ```

___

###<b>sql_condition_passed_count</b>
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that meet the condition.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_passed_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_passed_count/snowflake.sql.jinja2"
    ```

___

###<b>sql_condition_failed_percent</b>
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that do not meet the condition.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_failed_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_failed_percent/snowflake.sql.jinja2"
    ```

___

