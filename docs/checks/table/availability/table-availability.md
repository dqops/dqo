**table availability** checks  

**Description**  
Table level check that verifies that a query can be executed on a table and that the server does not return errors, that the table exists, and that there are accesses to it.

___

## **table availability**  
  
**Check description**  
Verifies that the number of rows in a table does not exceed the minimum accepted count.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|table_availability|profiling| |[table_availability](../../../../reference/sensors/table/availability-table-sensors/#table-availability)|[max_failures](../../../../reference/rules/comparison/#max-failures)|
  
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=table_availability
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=table_availability
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=table_availability
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=table_availability
```
**Check structure (Yaml)**
```yaml
  profiling_checks:
    availability:
      table_availability:
        warning:
          max_failures: 1
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
      table_availability:
        warning:
          max_failures: 1
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
      
    ```
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
      
    ```
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM
        (
            SELECT
                *,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
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
      
    ```
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value,
        TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
        TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
    FROM
        (
            SELECT
                *,
        TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
        TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
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
      
    ```
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM
        (
            SELECT
                *,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
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
      
    ```
    SELECT
        CASE
           WHEN COUNT(*) > 0 THEN COUNT(*)
           ELSE 1.0
        END AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM
        (
            SELECT
                *,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 35-40"
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
      profiling_checks:
        availability:
          table_availability:
            warning:
              max_failures: 1
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
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
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
        ```
        SELECT
            CASE
               WHEN COUNT(*) > 0 THEN COUNT(*)
               ELSE 1.0
            END AS actual_value,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM
            (
                SELECT
                    *,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
                FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                
                LIMIT 1
            ) AS tab_scan
        GROUP BY time_period
        ORDER BY time_period
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
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
        ```
        SELECT
            CASE
               WHEN COUNT(*) > 0 THEN COUNT(*)
               ELSE 1.0
            END AS actual_value,
            TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
            TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
        FROM
            (
                SELECT
                    *,
            TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
            TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
                FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                
                LIMIT 1
            ) AS tab_scan
        GROUP BY time_period
        ORDER BY time_period
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
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
        ```
        SELECT
            CASE
               WHEN COUNT(*) > 0 THEN COUNT(*)
               ELSE 1.0
            END AS actual_value,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM
            (
                SELECT
                    *,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                
                LIMIT 1
            ) AS tab_scan
        GROUP BY time_period
        ORDER BY time_period
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
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
        ```
        SELECT
            CASE
               WHEN COUNT(*) > 0 THEN COUNT(*)
               ELSE 1.0
            END AS actual_value,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM
            (
                SELECT
                    *,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                
                LIMIT 1
            ) AS tab_scan
        GROUP BY time_period
        ORDER BY time_period
        ```
    





___

## **daily table availability**  
  
**Check description**  
Verifies availability on table in database using simple row count  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_table_availability|recurring|daily|[table_availability](../../../../reference/sensors/table/availability-table-sensors/#table-availability)|[max_failures](../../../../reference/rules/comparison/#max-failures)|
  
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_table_availability
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_table_availability
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_table_availability
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_table_availability
```
**Check structure (Yaml)**
```yaml
  recurring_checks:
    daily:
      availability:
        daily_table_availability:
          warning:
            max_failures: 1
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
            max_failures: 1
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
      
    ```
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
      
    ```
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
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
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
      
    ```
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
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
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
      
    ```
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
      
    ```
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
      
    ```
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
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 36-41"
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
      recurring_checks:
        daily:
          availability:
            daily_table_availability:
              warning:
                max_failures: 1
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
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
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
        ```
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
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
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
        ```
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
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
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
        ```
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
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
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
        ```
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
    





___

## **monthly table availability**  
  
**Check description**  
Verifies availability on table in database using simple row count  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_table_availability|recurring|monthly|[table_availability](../../../../reference/sensors/table/availability-table-sensors/#table-availability)|[max_failures](../../../../reference/rules/comparison/#max-failures)|
  
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_table_availability
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_table_availability
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_table_availability
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_table_availability
```
**Check structure (Yaml)**
```yaml
  recurring_checks:
    monthly:
      availability:
        monthly_table_availability:
          warning:
            max_failures: 1
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
            max_failures: 1
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
      
    ```
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
      
    ```
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
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
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
      
    ```
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
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
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
      
    ```
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
      
    ```
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
      
    ```
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
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 36-41"
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
      recurring_checks:
        monthly:
          availability:
            monthly_table_availability:
              warning:
                max_failures: 1
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
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
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
        ```
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
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
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
        ```
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
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
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
        ```
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
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
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
        ```
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
    





___
