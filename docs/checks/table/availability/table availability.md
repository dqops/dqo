**table availability** checks  

**Description**  
Table level check that verifies that a query can be executed on a table and that the server does not return errors, that the table exists, and that there are accesses to it.

___

## **table availability**  
  
**Check description**  
Verifies that the number of rows in a table does not exceed the minimum accepted count.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|table_availability|adhoc| |[table_availability](../../../../reference/sensors/table/availability%20table%20sensors/#table-availability)|[max_failures](../../../../reference/rules/comparison/#max-failures)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=table_availability
```
**Check structure (Yaml)**
```yaml
  checks:
    availability:
      table_availability:
        error:
          max_failures: 5
        warning:
          max_failures: 1
        fatal:
          max_failures: 10
```
**Sample configuration (Yaml)**  
```yaml hl_lines="12-20"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: target_schema
    table_name: target_table
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
    partitioned_checks_timestamp_source: event_timestamp
  checks:
    availability:
      table_availability:
        error:
          max_failures: 5
        warning:
          max_failures: 1
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
            FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
            
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
        CURRENT_TIMESTAMP() AS time_period,
        TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM
        (
            SELECT
                *,
        CURRENT_TIMESTAMP() AS time_period,
        TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
            FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
            
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
            FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
            
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
            FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **Configuration with a data stream**  
??? info "Click to see more"  
    **Sample configuration with a data stream (Yaml)**  
    ```yaml hl_lines="12-19 36-41"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      target:
        schema_name: target_schema
        table_name: target_table
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
        partitioned_checks_timestamp_source: event_timestamp
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      checks:
        availability:
          table_availability:
            error:
              max_failures: 5
            warning:
              max_failures: 1
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
                FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
                
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
            CURRENT_TIMESTAMP() AS time_period,
            TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM
            (
                SELECT
                    *,
            CURRENT_TIMESTAMP() AS time_period,
            TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
                FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
                
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
                FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
                
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
                FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
                
                LIMIT 1
            ) AS tab_scan
        GROUP BY time_period
        ORDER BY time_period
        ```
    





___

## **daily checkpoint table availability**  
  
**Check description**  
Verifies availability on table in database using simple row count  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_table_availability|checkpoint|daily|[table_availability](../../../../reference/sensors/table/availability%20table%20sensors/#table-availability)|[max_failures](../../../../reference/rules/comparison/#max-failures)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_checkpoint_table_availability
```
**Check structure (Yaml)**
```yaml
  checkpoints:
    daily:
      availability:
        daily_checkpoint_table_availability:
          error:
            max_failures: 5
          warning:
            max_failures: 1
          fatal:
            max_failures: 10
```
**Sample configuration (Yaml)**  
```yaml hl_lines="12-21"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: target_schema
    table_name: target_table
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
    partitioned_checks_timestamp_source: event_timestamp
  checkpoints:
    daily:
      availability:
        daily_checkpoint_table_availability:
          error:
            max_failures: 5
          warning:
            max_failures: 1
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
            FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
            
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
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM
        (
            SELECT
                *,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
            FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
            
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
            FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
            
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
            FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **Configuration with a data stream**  
??? info "Click to see more"  
    **Sample configuration with a data stream (Yaml)**  
    ```yaml hl_lines="12-19 37-42"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      target:
        schema_name: target_schema
        table_name: target_table
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
        partitioned_checks_timestamp_source: event_timestamp
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      checkpoints:
        daily:
          availability:
            daily_checkpoint_table_availability:
              error:
                max_failures: 5
              warning:
                max_failures: 1
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
                FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
                
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
            CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
            TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
        FROM
            (
                SELECT
                    *,
            CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
            TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
                FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
                
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
                FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
                
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
                FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
                
                LIMIT 1
            ) AS tab_scan
        GROUP BY time_period
        ORDER BY time_period
        ```
    





___

## **monthly partition table availability**  
  
**Check description**  
Verifies availability on table in database using simple row count  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_table_availability|checkpoint|monthly|[table_availability](../../../../reference/sensors/table/availability%20table%20sensors/#table-availability)|[max_failures](../../../../reference/rules/comparison/#max-failures)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_table_availability
```
**Check structure (Yaml)**
```yaml
  checkpoints:
    monthly:
      availability:
        monthly_partition_table_availability:
          error:
            max_failures: 5
          warning:
            max_failures: 1
          fatal:
            max_failures: 10
```
**Sample configuration (Yaml)**  
```yaml hl_lines="12-21"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: target_schema
    table_name: target_table
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
    partitioned_checks_timestamp_source: event_timestamp
  checkpoints:
    monthly:
      availability:
        monthly_partition_table_availability:
          error:
            max_failures: 5
          warning:
            max_failures: 1
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
            FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
            
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
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM
        (
            SELECT
                *,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
            FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
            
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
            FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
            
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
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM
        (
            SELECT
                *,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **Configuration with a data stream**  
??? info "Click to see more"  
    **Sample configuration with a data stream (Yaml)**  
    ```yaml hl_lines="12-19 37-42"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      target:
        schema_name: target_schema
        table_name: target_table
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
        partitioned_checks_timestamp_source: event_timestamp
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      checkpoints:
        monthly:
          availability:
            monthly_partition_table_availability:
              error:
                max_failures: 5
              warning:
                max_failures: 1
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
                FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
                
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
            DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
        FROM
            (
                SELECT
                    *,
            DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
                FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
                
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
                FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
                
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
            DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM
            (
                SELECT
                    *,
            DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
                
                LIMIT 1
            ) AS tab_scan
        GROUP BY time_period
        ORDER BY time_period
        ```
    





___

## **daily partition table availability**  
  
**Check description**  
Verifies availability on table in database using simple row count  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_table_availability|partitioned|daily|[table_availability](../../../../reference/sensors/table/availability%20table%20sensors/#table-availability)|[max_failures](../../../../reference/rules/comparison/#max-failures)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_table_availability
```
**Check structure (Yaml)**
```yaml
  partitioned_checks:
    daily:
      availability:
        daily_partition_table_availability:
          error:
            max_failures: 5
          warning:
            max_failures: 1
          fatal:
            max_failures: 10
```
**Sample configuration (Yaml)**  
```yaml hl_lines="12-21"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: target_schema
    table_name: target_table
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
    partitioned_checks_timestamp_source: event_timestamp
  partitioned_checks:
    daily:
      availability:
        daily_partition_table_availability:
          error:
            max_failures: 5
          warning:
            max_failures: 1
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
        CAST(tab_scan.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(tab_scan.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM
        (
            SELECT
                *,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
            
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
        CAST(tab_scan."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(tab_scan."col_event_timestamp" AS date)) AS time_period_utc
    FROM
        (
            SELECT
                *,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
            FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
            
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
        CAST(tab_scan."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(tab_scan."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM
        (
            SELECT
                *,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
            
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
        CAST(tab_scan."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(tab_scan."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM
        (
            SELECT
                *,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **Configuration with a data stream**  
??? info "Click to see more"  
    **Sample configuration with a data stream (Yaml)**  
    ```yaml hl_lines="12-19 37-42"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      target:
        schema_name: target_schema
        table_name: target_table
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
        partitioned_checks_timestamp_source: event_timestamp
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      partitioned_checks:
        daily:
          availability:
            daily_partition_table_availability:
              error:
                max_failures: 5
              warning:
                max_failures: 1
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
            CAST(tab_scan.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(tab_scan.`col_event_timestamp` AS DATE)) AS time_period_utc
        FROM
            (
                SELECT
                    *,
            CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
                FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
                
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
            CAST(tab_scan."col_event_timestamp" AS date) AS time_period,
            TO_TIMESTAMP(CAST(tab_scan."col_event_timestamp" AS date)) AS time_period_utc
        FROM
            (
                SELECT
                    *,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
                FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
                
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
            CAST(tab_scan."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(tab_scan."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM
            (
                SELECT
                    *,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
                
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
            CAST(tab_scan."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(tab_scan."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM
            (
                SELECT
                    *,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
                
                LIMIT 1
            ) AS tab_scan
        GROUP BY time_period
        ORDER BY time_period
        ```
    





___

## **monthly checkpoint table availability**  
  
**Check description**  
Verifies availability on table in database using simple row count  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_table_availability|partitioned|monthly|[table_availability](../../../../reference/sensors/table/availability%20table%20sensors/#table-availability)|[max_failures](../../../../reference/rules/comparison/#max-failures)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_checkpoint_table_availability
```
**Check structure (Yaml)**
```yaml
  partitioned_checks:
    monthly:
      availability:
        monthly_checkpoint_table_availability:
          error:
            max_failures: 5
          warning:
            max_failures: 1
          fatal:
            max_failures: 10
```
**Sample configuration (Yaml)**  
```yaml hl_lines="12-21"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: target_schema
    table_name: target_table
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
    partitioned_checks_timestamp_source: event_timestamp
  partitioned_checks:
    monthly:
      availability:
        monthly_checkpoint_table_availability:
          error:
            max_failures: 5
          warning:
            max_failures: 1
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
        DATE_TRUNC(CAST(tab_scan.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(tab_scan.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM
        (
            SELECT
                *,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
            
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
        DATE_TRUNC('MONTH', CAST(tab_scan."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(tab_scan."col_event_timestamp" AS date))) AS time_period_utc
    FROM
        (
            SELECT
                *,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
            FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
            
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
        DATE_TRUNC('MONTH', CAST(tab_scan."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(tab_scan."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM
        (
            SELECT
                *,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
            
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
        DATE_TRUNC('month', CAST(tab_scan."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(tab_scan."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM
        (
            SELECT
                *,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
            
            LIMIT 1
        ) AS tab_scan
    GROUP BY time_period
    ORDER BY time_period
    ```
### **Configuration with a data stream**  
??? info "Click to see more"  
    **Sample configuration with a data stream (Yaml)**  
    ```yaml hl_lines="12-19 37-42"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      target:
        schema_name: target_schema
        table_name: target_table
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
        partitioned_checks_timestamp_source: event_timestamp
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      partitioned_checks:
        monthly:
          availability:
            monthly_checkpoint_table_availability:
              error:
                max_failures: 5
              warning:
                max_failures: 1
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
            DATE_TRUNC(CAST(tab_scan.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(tab_scan.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
        FROM
            (
                SELECT
                    *,
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
                FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
                
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
            DATE_TRUNC('MONTH', CAST(tab_scan."col_event_timestamp" AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(tab_scan."col_event_timestamp" AS date))) AS time_period_utc
        FROM
            (
                SELECT
                    *,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
                FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
                
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
            DATE_TRUNC('MONTH', CAST(tab_scan."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(tab_scan."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM
            (
                SELECT
                    *,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
                
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
            DATE_TRUNC('month', CAST(tab_scan."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(tab_scan."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM
            (
                SELECT
                    *,
            DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
                
                LIMIT 1
            ) AS tab_scan
        GROUP BY time_period
        ORDER BY time_period
        ```
    





___
