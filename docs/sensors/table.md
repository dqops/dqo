#table

##<b>standard</b> table sensors
___

###<b>row_count</b>
<b>Full sensor name</b>
```
table/standard/row_count
```
<b>Description</b>
<br/>
Tabular sensor that executes a row count query on a table.
<br/>


<b>SQL Template (Jinja2)</b>

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


##<b>timeliness</b> table sensors
___

###<b>days_since_most_recent_event</b>
<b>Full sensor name</b>
```
table/timeliness/days_since_most_recent_event
```
<b>Description</b>
<br/>
Tabular sensor that runs a query calculating maximum days since the most recent event.
<br/>


<b>SQL Template (Jinja2)</b>

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
<b>Full sensor name</b>
```
table/timeliness/partition_reload_lag
```
<b>Description</b>
<br/>
Tabular sensor that runs a query calculating maximum difference in days between ingestion timestamp and event timestamp rows.
<br/>


<b>SQL Template (Jinja2)</b>

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
<b>Full sensor name</b>
```
table/timeliness/days_since_most_recent_ingestion
```
<b>Description</b>
<br/>
Tabular sensor that runs a query calculating the time difference in days between the current date and most recent data loading timestamp (staleness).
<br/>


<b>SQL Template (Jinja2)</b>

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
<b>Full sensor name</b>
```
table/timeliness/data_ingestion_delay
```
<b>Description</b>
<br/>
Tabular sensor that runs a query calculating the time difference in days between the most recent transaction timestamp and the most recent data loading timestamp.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/table/timeliness/data_ingestion_delay/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/timeliness/data_ingestion_delay/snowflake.sql.jinja2"
    ```

___


##<b>sql</b> table sensors
___

###<b>sql_condition_passed_percent</b>
<b>Full sensor name</b>
```
table/sql/sql_condition_passed_percent
```
<b>Description</b>
<br/>
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>sql_condition</td>
<td>SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} placeholder that is replaced with a full table name.</td>
<td>string_type</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

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
<b>Full sensor name</b>
```
table/sql/sql_condition_failed_count
```
<b>Description</b>
<br/>
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that do not meet the condition.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>sql_condition</td>
<td>SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} placeholder that is replaced with a full table name.</td>
<td>string_type</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

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
<b>Full sensor name</b>
```
table/sql/sql_aggregated_expression
```
<b>Description</b>
<br/>
Table level sensor that executes a given SQL expression on a table.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>sql_expression</td>
<td>SQL aggregate expression that returns a numeric value calculated from rows. The expression is evaluated on a whole table or withing a GROUP BY clause for daily partitions and/or data streams. The expression can use {table} placeholder that is replaced with a full table name.</td>
<td>string_type</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

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
<b>Full sensor name</b>
```
table/sql/sql_condition_passed_count
```
<b>Description</b>
<br/>
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that meet the condition.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>sql_condition</td>
<td>SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} placeholder that is replaced with a full table name.</td>
<td>string_type</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

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
<b>Full sensor name</b>
```
table/sql/sql_condition_failed_percent
```
<b>Description</b>
<br/>
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that do not meet the condition.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>sql_condition</td>
<td>SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} placeholder that is replaced with a full table name.</td>
<td>string_type</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_failed_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_failed_percent/snowflake.sql.jinja2"
    ```

___

