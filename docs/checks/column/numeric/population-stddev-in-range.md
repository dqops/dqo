**population stddev in range** checks  

**Description**  
Column level check that ensures that the population standard deviation is in a set range in a monitored column.

___

## **population stddev in range**  
  
**Check description**  
Verifies that the population standard deviation of all values in a column is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|population_stddev_in_range|profiling| |[population_stddev](../../../../reference/sensors/column/numeric-column-sensors/#population-stddev)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=population_stddev_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo> check run -ch=population_stddev_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=population_stddev_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=population_stddev_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=population_stddev_in_range
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        numeric:
          population_stddev_in_range:
            warning:
              from: 10.0
              to: 20.5
            error:
              from: 10.0
              to: 20.5
            fatal:
              from: 10.0
              to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-24"
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
  columns:
    target_column:
      profiling_checks:
        numeric:
          population_stddev_in_range:
            warning:
              from: 10.0
              to: 20.5
            error:
              from: 10.0
              to: 20.5
            fatal:
              from: 10.0
              to: 20.5
      labels:
      - This is the column that is analyzed for data quality issues
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
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        STDDEV(analyzed_table."target_column") AS actual_value,
        TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
        TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table."target_column") AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table."target_column") AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        STDEVP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        STDEVP(analyzed_table.[target_column]) AS actual_value,
        SYSDATETIMEOFFSET() AS time_period,
        CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CONVERT_TZ(LOCALTIMESTAMP, @@session.time_zone, '+00:00') AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 41-46"
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
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      columns:
        target_column:
          profiling_checks:
            numeric:
              population_stddev_in_range:
                warning:
                  from: 10.0
                  to: 20.5
                error:
                  from: 10.0
                  to: 20.5
                fatal:
                  from: 10.0
                  to: 20.5
          labels:
          - This is the column that is analyzed for data quality issues
        col_event_timestamp:
          labels:
          - optional column that stores the timestamp when the event/transaction happened
        col_inserted_at:
          labels:
          - optional column that stores the timestamp when row was ingested
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            STDDEV(analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
            TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            STDEVP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            STDEVP(analyzed_table.[target_column]) AS actual_value,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2,
            SYSDATETIMEOFFSET() AS time_period,
            CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table, 
                , 
            
        ORDER BY level_1, level_2
                , 
            
        
            
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CONVERT_TZ(LOCALTIMESTAMP, @@session.time_zone, '+00:00') AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily population stddev in range**  
  
**Check description**  
Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_population_stddev_in_range|recurring|daily|[population_stddev](../../../../reference/sensors/column/numeric-column-sensors/#population-stddev)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_population_stddev_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_population_stddev_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_population_stddev_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_population_stddev_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_population_stddev_in_range
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        daily:
          numeric:
            daily_population_stddev_in_range:
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-25"
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
  columns:
    target_column:
      recurring_checks:
        daily:
          numeric:
            daily_population_stddev_in_range:
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
      labels:
      - This is the column that is analyzed for data quality issues
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
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        STDDEV(analyzed_table."target_column") AS actual_value,
        CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
        TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table."target_column") AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table."target_column") AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        STDEVP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        STDEVP(analyzed_table.[target_column]) AS actual_value,
        CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CONVERT_TZ(LOCALTIMESTAMP, @@session.time_zone, '+00:00') AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 42-47"
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
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      columns:
        target_column:
          recurring_checks:
            daily:
              numeric:
                daily_population_stddev_in_range:
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
                    from: 10.0
                    to: 20.5
                  fatal:
                    from: 10.0
                    to: 20.5
          labels:
          - This is the column that is analyzed for data quality issues
        col_event_timestamp:
          labels:
          - optional column that stores the timestamp when the event/transaction happened
        col_inserted_at:
          labels:
          - optional column that stores the timestamp when row was ingested
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            STDDEV(analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
            TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            STDEVP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            STDEVP(analyzed_table.[target_column]) AS actual_value,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2,
            CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
            CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table, 
                , 
            
        ORDER BY level_1, level_2
                , 
            
        
            
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CONVERT_TZ(LOCALTIMESTAMP, @@session.time_zone, '+00:00') AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly population stddev in range**  
  
**Check description**  
Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_population_stddev_in_range|recurring|monthly|[population_stddev](../../../../reference/sensors/column/numeric-column-sensors/#population-stddev)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_population_stddev_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_population_stddev_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_population_stddev_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_population_stddev_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_population_stddev_in_range
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        monthly:
          numeric:
            monthly_population_stddev_in_range:
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-25"
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
  columns:
    target_column:
      recurring_checks:
        monthly:
          numeric:
            monthly_population_stddev_in_range:
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
      labels:
      - This is the column that is analyzed for data quality issues
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
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        STDDEV(analyzed_table."target_column") AS actual_value,
        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table."target_column") AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table."target_column") AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        STDEVP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        STDEVP(analyzed_table.[target_column]) AS actual_value,
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
        CONVERT_TZ(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'), @@session.time_zone, '+00:00') AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 42-47"
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
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      columns:
        target_column:
          recurring_checks:
            monthly:
              numeric:
                monthly_population_stddev_in_range:
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
                    from: 10.0
                    to: 20.5
                  fatal:
                    from: 10.0
                    to: 20.5
          labels:
          - This is the column that is analyzed for data quality issues
        col_event_timestamp:
          labels:
          - optional column that stores the timestamp when the event/transaction happened
        col_inserted_at:
          labels:
          - optional column that stores the timestamp when row was ingested
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            STDDEV(analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            STDEVP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            STDEVP(analyzed_table.[target_column]) AS actual_value,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2,
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table, 
                , 
            
        ORDER BY level_1, level_2
                , 
            
        
            
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
            CONVERT_TZ(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'), @@session.time_zone, '+00:00') AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily partition population stddev in range**  
  
**Check description**  
Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_population_stddev_in_range|partitioned|daily|[population_stddev](../../../../reference/sensors/column/numeric-column-sensors/#population-stddev)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_partition_population_stddev_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_partition_population_stddev_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_partition_population_stddev_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_partition_population_stddev_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_population_stddev_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          numeric:
            daily_partition_population_stddev_in_range:
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-25"
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
  columns:
    target_column:
      partitioned_checks:
        daily:
          numeric:
            daily_partition_population_stddev_in_range:
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
      labels:
      - This is the column that is analyzed for data quality issues
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
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
        CAST(analyzed_table.`` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        STDDEV(analyzed_table."target_column") AS actual_value,
        CAST(analyzed_table."" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table."target_column") AS actual_value,
        CAST(analyzed_table."" AS date) AS time_period,
        CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table."target_column") AS actual_value,
        CAST(analyzed_table."" AS date) AS time_period,
        CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        STDEVP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        STDEVP(analyzed_table.[target_column]) AS actual_value,
        CAST([] AS date) AS time_period,
        CAST((CAST([] AS date)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY CAST([] AS date), CAST([] AS date)
    ORDER BY CAST([] AS date)
    
        
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
        analyzed_table.`` AS time_period,
        CONVERT_TZ(analyzed_table.``, @@session.time_zone, '+00:00') AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 42-47"
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
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      columns:
        target_column:
          partitioned_checks:
            daily:
              numeric:
                daily_partition_population_stddev_in_range:
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
                    from: 10.0
                    to: 20.5
                  fatal:
                    from: 10.0
                    to: 20.5
          labels:
          - This is the column that is analyzed for data quality issues
        col_event_timestamp:
          labels:
          - optional column that stores the timestamp when the event/transaction happened
        col_inserted_at:
          labels:
          - optional column that stores the timestamp when row was ingested
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(analyzed_table.`` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            STDDEV(analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."" AS date) AS time_period,
            TO_TIMESTAMP(CAST(analyzed_table."" AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."" AS date) AS time_period,
            CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."" AS date) AS time_period,
            CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            STDEVP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            STDEVP(analyzed_table.[target_column]) AS actual_value,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2,
            CAST([] AS date) AS time_period,
            CAST((CAST([] AS date)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table, 
        GROUP BY CAST([] AS date), CAST([] AS date)
        ORDER BY level_1, level_2CAST([] AS date)
        
            
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            analyzed_table.`` AS time_period,
            CONVERT_TZ(analyzed_table.``, @@session.time_zone, '+00:00') AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly partition population stddev in range**  
  
**Check description**  
Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_population_stddev_in_range|partitioned|monthly|[population_stddev](../../../../reference/sensors/column/numeric-column-sensors/#population-stddev)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_partition_population_stddev_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_partition_population_stddev_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_partition_population_stddev_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_partition_population_stddev_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_population_stddev_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          numeric:
            monthly_partition_population_stddev_in_range:
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-25"
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
  columns:
    target_column:
      partitioned_checks:
        monthly:
          numeric:
            monthly_partition_population_stddev_in_range:
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
      labels:
      - This is the column that is analyzed for data quality issues
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
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        STDDEV(analyzed_table."target_column") AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table."target_column") AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table."target_column") AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        STDEVP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        STDEVP(analyzed_table.[target_column]) AS actual_value,
        DATEFROMPARTS(YEAR(CAST([] AS date)), MONTH(CAST([] AS date)), 1) AS time_period,
        CAST((DATEFROMPARTS(YEAR(CAST([] AS date)), MONTH(CAST([] AS date)), 1)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY DATEFROMPARTS(YEAR(CAST([] AS date)), MONTH(CAST([] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, []), 0)
    ORDER BY DATEFROMPARTS(YEAR(CAST([] AS date)), MONTH(CAST([] AS date)), 1)
    
        
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
        DATE_FORMAT(analyzed_table.``, '%Y-%m-01 00:00:00') AS time_period,
        CONVERT_TZ(DATE_FORMAT(analyzed_table.``, '%Y-%m-01 00:00:00'), @@session.time_zone, '+00:00') AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 42-47"
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
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      columns:
        target_column:
          partitioned_checks:
            monthly:
              numeric:
                monthly_partition_population_stddev_in_range:
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
                    from: 10.0
                    to: 20.5
                  fatal:
                    from: 10.0
                    to: 20.5
          labels:
          - This is the column that is analyzed for data quality issues
        col_event_timestamp:
          labels:
          - optional column that stores the timestamp when the event/transaction happened
        col_inserted_at:
          labels:
          - optional column that stores the timestamp when row was ingested
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(analyzed_table.`` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            STDDEV(analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            STDEVP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            STDEVP(analyzed_table.[target_column]) AS actual_value,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2,
            DATEFROMPARTS(YEAR(CAST([] AS date)), MONTH(CAST([] AS date)), 1) AS time_period,
            CAST((DATEFROMPARTS(YEAR(CAST([] AS date)), MONTH(CAST([] AS date)), 1)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table, 
        GROUP BY DATEFROMPARTS(YEAR(CAST([] AS date)), MONTH(CAST([] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, []), 0)
        ORDER BY level_1, level_2DATEFROMPARTS(YEAR(CAST([] AS date)), MONTH(CAST([] AS date)), 1)
        
            
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        SELECT
            STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            STDDEV_POP(analyzed_table.`target_column`) AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_FORMAT(analyzed_table.``, '%Y-%m-01 00:00:00') AS time_period,
            CONVERT_TZ(DATE_FORMAT(analyzed_table.``, '%Y-%m-01 00:00:00'), @@session.time_zone, '+00:00') AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___
