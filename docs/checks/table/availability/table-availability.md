**table availability** checks  

**Description**  
Table level check that verifies that a query can be executed on a table and that the server does not return errors, that the table exists, and that the table is accessible (queryable).

___

## **profile table availability**  
  
**Check description**  
Verifies availability of the table in a database using a simple row count.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|profile_table_availability|profiling| |[table_availability](../../../../reference/sensors/Table/availability-table-sensors/#table-availability)|[max_failures](../../../../reference/rules/Comparison/#max-failures)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=profile_table_availability
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=profile_table_availability
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=profile_table_availability
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=profile_table_availability
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=profile_table_availability
```
**Check structure (Yaml)**
```yaml
  profiling_checks:
    availability:
      profile_table_availability:
        warning:
          max_failures: 0
        error:
          max_failures: 5
        fatal:
          max_failures: 10
```
**Sample configuration (Yaml)**  
```yaml hl_lines="11-19"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  profiling_checks:
    availability:
      profile_table_availability:
        warning:
          max_failures: 0
        error:
          max_failures: 5
        fatal:
          max_failures: 10
  columns:
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
        {{- lib.render_time_dimension_projection('tab_scan') }}
    FROM
        (
            SELECT
                *
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM
        (
            SELECT
                *,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
        {{- lib.render_time_dimension_projection('tab_scan') }}
    FROM
        (
            SELECT
                *
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value,
        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
    FROM
        (
            SELECT
                *,
        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
        {{- lib.render_time_dimension_projection('tab_scan') }}
    FROM
        (
            SELECT
                *
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM
        (
            SELECT
                *,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
        {{- lib.render_time_dimension_projection('tab_scan') }}
    FROM
        (
            SELECT
                *
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM
        (
            SELECT
                *,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
        {{- lib.render_time_dimension_projection('tab_scan') }}
    FROM
        (
            SELECT
                *
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
    FROM
        (
            SELECT
                *,
        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
    FROM
        (
            SELECT
                *
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
    FROM
        (
            SELECT
                *
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    ```







___

## **daily table availability**  
  
**Check description**  
Verifies availability on table in database using simple row count. Stores the most recent table availability status for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_table_availability|recurring|daily|[table_availability](../../../../reference/sensors/Table/availability-table-sensors/#table-availability)|[max_failures](../../../../reference/rules/Comparison/#max-failures)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_table_availability
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_table_availability
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_table_availability
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_table_availability
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_table_availability
```
**Check structure (Yaml)**
```yaml
  recurring_checks:
    daily:
      availability:
        daily_table_availability:
          warning:
            max_failures: 0
          error:
            max_failures: 5
          fatal:
            max_failures: 10
```
**Sample configuration (Yaml)**  
```yaml hl_lines="11-20"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  recurring_checks:
    daily:
      availability:
        daily_table_availability:
          warning:
            max_failures: 0
          error:
            max_failures: 5
          fatal:
            max_failures: 10
  columns:
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
        {{- lib.render_time_dimension_projection('tab_scan') }}
    FROM
        (
            SELECT
                *
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM
        (
            SELECT
                *,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
        {{- lib.render_time_dimension_projection('tab_scan') }}
    FROM
        (
            SELECT
                *
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value,
        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
    FROM
        (
            SELECT
                *,
        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
        {{- lib.render_time_dimension_projection('tab_scan') }}
    FROM
        (
            SELECT
                *
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM
        (
            SELECT
                *,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
        {{- lib.render_time_dimension_projection('tab_scan') }}
    FROM
        (
            SELECT
                *
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM
        (
            SELECT
                *,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
        {{- lib.render_time_dimension_projection('tab_scan') }}
    FROM
        (
            SELECT
                *
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value,
        CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
        TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
    FROM
        (
            SELECT
                *,
        CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
        TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
    FROM
        (
            SELECT
                *
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
    FROM
        (
            SELECT
                *
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    ```







___

## **monthly table availability**  
  
**Check description**  
Verifies availability on table in database using simple row count. Stores the most recent table availability status for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_table_availability|recurring|monthly|[table_availability](../../../../reference/sensors/Table/availability-table-sensors/#table-availability)|[max_failures](../../../../reference/rules/Comparison/#max-failures)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_table_availability
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_table_availability
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_table_availability
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_table_availability
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_table_availability
```
**Check structure (Yaml)**
```yaml
  recurring_checks:
    monthly:
      availability:
        monthly_table_availability:
          warning:
            max_failures: 0
          error:
            max_failures: 5
          fatal:
            max_failures: 10
```
**Sample configuration (Yaml)**  
```yaml hl_lines="11-20"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  recurring_checks:
    monthly:
      availability:
        monthly_table_availability:
          warning:
            max_failures: 0
          error:
            max_failures: 5
          fatal:
            max_failures: 10
  columns:
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
        {{- lib.render_time_dimension_projection('tab_scan') }}
    FROM
        (
            SELECT
                *
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM
        (
            SELECT
                *,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
        {{- lib.render_time_dimension_projection('tab_scan') }}
    FROM
        (
            SELECT
                *
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value,
        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
    FROM
        (
            SELECT
                *,
        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
        {{- lib.render_time_dimension_projection('tab_scan') }}
    FROM
        (
            SELECT
                *
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM
        (
            SELECT
                *,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
        {{- lib.render_time_dimension_projection('tab_scan') }}
    FROM
        (
            SELECT
                *
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM
        (
            SELECT
                *,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
        {{- lib.render_time_dimension_projection('tab_scan') }}
    FROM
        (
            SELECT
                *
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
    FROM
        (
            SELECT
                *,
        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
    FROM
        (
            SELECT
                *
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{ lib.render_where_clause() }}
            LIMIT 1
        ) AS tab_scan
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value
    FROM
        (
            SELECT
                *
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    ```







___
