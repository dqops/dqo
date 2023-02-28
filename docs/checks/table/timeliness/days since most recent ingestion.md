**days since most recent ingestion** checks  

**Description**  
Table level check that calculates the minimal number of days between event timestamp and ingestion timestamp. .

___

## **days since most recent ingestion**  
  
**Check description**  
Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|days_since_most_recent_ingestion|adhoc| |[days_since_most_recent_ingestion](../../../../reference/sensors/table/timeliness%20table%20sensors/#days-since-most-recent-ingestion)|[max_days](../../../../reference/rules/comparison/#max-days)|
  
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=days_since_most_recent_ingestion
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=days_since_most_recent_ingestion
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=days_since_most_recent_ingestion
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=days_since_most_recent_ingestion
```
**Check structure (Yaml)**
```yaml
  checks:
    timeliness:
      days_since_most_recent_ingestion:
        error:
          max_days: 2.0
        warning:
          max_days: 1.0
        fatal:
          max_days: 1.0
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
    timeliness:
      days_since_most_recent_ingestion:
        error:
          max_days: 2.0
        warning:
          max_days: 1.0
        fatal:
          max_days: 1.0
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
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
        timeliness:
          days_since_most_recent_ingestion:
            error:
              max_days: 2.0
            warning:
              max_days: 1.0
            fatal:
              max_days: 1.0
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
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            DATE_DIFF(
                CURRENT_DATE(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                DAY
            )
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
            DATETIME_DIFF(
                CURRENT_DATETIME(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- else -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            DATE_DIFF(
                CURRENT_DATE(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                DAY
            )
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
            DATETIME_DIFF(
                CURRENT_DATETIME(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- else -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```
        SELECT
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CURRENT_TIMESTAMP() AS time_period,
            TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
                CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- else -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
            )) / 24.0 / 3600.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
            )) / 24.0 / 3600.0 AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
                CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- else -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
            )) / 24.0 / 3600.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
            )) / 24.0 / 3600.0 AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily checkpoint days since most recent ingestion**  
  
**Check description**  
Daily checkpoint calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_days_since_most_recent_ingestion|checkpoint|daily|[days_since_most_recent_ingestion](../../../../reference/sensors/table/timeliness%20table%20sensors/#days-since-most-recent-ingestion)|[max_days](../../../../reference/rules/comparison/#max-days)|
  
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_checkpoint_days_since_most_recent_ingestion
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_checkpoint_days_since_most_recent_ingestion
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_checkpoint_days_since_most_recent_ingestion
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_checkpoint_days_since_most_recent_ingestion
```
**Check structure (Yaml)**
```yaml
  checkpoints:
    daily:
      timeliness:
        daily_checkpoint_days_since_most_recent_ingestion:
          error:
            max_days: 2.0
          warning:
            max_days: 1.0
          fatal:
            max_days: 1.0
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
      timeliness:
        daily_checkpoint_days_since_most_recent_ingestion:
          error:
            max_days: 2.0
          warning:
            max_days: 1.0
          fatal:
            max_days: 1.0
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
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
          timeliness:
            daily_checkpoint_days_since_most_recent_ingestion:
              error:
                max_days: 2.0
              warning:
                max_days: 1.0
              fatal:
                max_days: 1.0
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
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            DATE_DIFF(
                CURRENT_DATE(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                DAY
            )
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
            DATETIME_DIFF(
                CURRENT_DATETIME(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- else -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            DATE_DIFF(
                CURRENT_DATE(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                DAY
            )
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
            DATETIME_DIFF(
                CURRENT_DATETIME(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- else -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```
        SELECT
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
            TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
        FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
                CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- else -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
            )) / 24.0 / 3600.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
            )) / 24.0 / 3600.0 AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
                CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- else -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
            )) / 24.0 / 3600.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
            )) / 24.0 / 3600.0 AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly checkpoint days since most recent ingestion**  
  
**Check description**  
Monthly checkpoint calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_days_since_most_recent_ingestion|checkpoint|monthly|[days_since_most_recent_ingestion](../../../../reference/sensors/table/timeliness%20table%20sensors/#days-since-most-recent-ingestion)|[max_days](../../../../reference/rules/comparison/#max-days)|
  
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_checkpoint_days_since_most_recent_ingestion
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_checkpoint_days_since_most_recent_ingestion
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_checkpoint_days_since_most_recent_ingestion
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_checkpoint_days_since_most_recent_ingestion
```
**Check structure (Yaml)**
```yaml
  checkpoints:
    monthly:
      timeliness:
        monthly_checkpoint_days_since_most_recent_ingestion:
          error:
            max_days: 2.0
          warning:
            max_days: 1.0
          fatal:
            max_days: 1.0
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
      timeliness:
        monthly_checkpoint_days_since_most_recent_ingestion:
          error:
            max_days: 2.0
          warning:
            max_days: 1.0
          fatal:
            max_days: 1.0
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
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
          timeliness:
            monthly_checkpoint_days_since_most_recent_ingestion:
              error:
                max_days: 2.0
              warning:
                max_days: 1.0
              fatal:
                max_days: 1.0
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
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            DATE_DIFF(
                CURRENT_DATE(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                DAY
            )
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
            DATETIME_DIFF(
                CURRENT_DATETIME(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- else -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            DATE_DIFF(
                CURRENT_DATE(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                DAY
            )
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
            DATETIME_DIFF(
                CURRENT_DATETIME(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- else -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```
        SELECT
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
        FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
                CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- else -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
            )) / 24.0 / 3600.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
            )) / 24.0 / 3600.0 AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
                CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- else -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
            )) / 24.0 / 3600.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
            )) / 24.0 / 3600.0 AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily partition days since most recent ingestion**  
  
**Check description**  
Daily partition checkpoint calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_days_since_most_recent_ingestion|partitioned|daily|[days_since_most_recent_ingestion](../../../../reference/sensors/table/timeliness%20table%20sensors/#days-since-most-recent-ingestion)|[max_days](../../../../reference/rules/comparison/#max-days)|
  
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_partition_days_since_most_recent_ingestion
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_partition_days_since_most_recent_ingestion
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_partition_days_since_most_recent_ingestion
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_days_since_most_recent_ingestion
```
**Check structure (Yaml)**
```yaml
  partitioned_checks:
    daily:
      timeliness:
        daily_partition_days_since_most_recent_ingestion:
          error:
            max_days: 2.0
          warning:
            max_days: 1.0
          fatal:
            max_days: 1.0
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
      timeliness:
        daily_partition_days_since_most_recent_ingestion:
          error:
            max_days: 2.0
          warning:
            max_days: 1.0
          fatal:
            max_days: 1.0
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
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
          timeliness:
            daily_partition_days_since_most_recent_ingestion:
              error:
                max_days: 2.0
              warning:
                max_days: 1.0
              fatal:
                max_days: 1.0
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
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            DATE_DIFF(
                CURRENT_DATE(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                DAY
            )
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
            DATETIME_DIFF(
                CURRENT_DATETIME(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- else -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            DATE_DIFF(
                CURRENT_DATE(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                DAY
            )
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
            DATETIME_DIFF(
                CURRENT_DATETIME(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- else -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```
        SELECT
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
        FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
                CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- else -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
            )) / 24.0 / 3600.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
            )) / 24.0 / 3600.0 AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
                CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- else -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
            )) / 24.0 / 3600.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
            )) / 24.0 / 3600.0 AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly partition days since most recent ingestion**  
  
**Check description**  
Monthly partition checkpoint calculating the time difference in days between the current date and the most recent data data ingestion timestamp (staleness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_days_since_most_recent_ingestion|partitioned|monthly|[days_since_most_recent_ingestion](../../../../reference/sensors/table/timeliness%20table%20sensors/#days-since-most-recent-ingestion)|[max_days](../../../../reference/rules/comparison/#max-days)|
  
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_partition_days_since_most_recent_ingestion
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_partition_days_since_most_recent_ingestion
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_partition_days_since_most_recent_ingestion
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_days_since_most_recent_ingestion
```
**Check structure (Yaml)**
```yaml
  partitioned_checks:
    monthly:
      timeliness:
        monthly_partition_days_since_most_recent_ingestion:
          error:
            max_days: 2.0
          warning:
            max_days: 1.0
          fatal:
            max_days: 1.0
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
      timeliness:
        monthly_partition_days_since_most_recent_ingestion:
          error:
            max_days: 2.0
          warning:
            max_days: 1.0
          fatal:
            max_days: 1.0
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
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
          timeliness:
            monthly_partition_days_since_most_recent_ingestion:
              error:
                max_days: 2.0
              warning:
                max_days: 1.0
              fatal:
                max_days: 1.0
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
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            DATE_DIFF(
                CURRENT_DATE(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                DAY
            )
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
            DATETIME_DIFF(
                CURRENT_DATETIME(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- else -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            DATE_DIFF(
                CURRENT_DATE(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                DAY
            )
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
            DATETIME_DIFF(
                CURRENT_DATETIME(),
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- else -%}
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```
        SELECT
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
        FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
                CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- else -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
            )) / 24.0 / 3600.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
            )) / 24.0 / 3600.0 AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {% macro render_current_ingestion_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
                CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )) / 24.0 / 3600.0
            {%- else -%}
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
            )) / 24.0 / 3600.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_current_ingestion_diff() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
            )) / 24.0 / 3600.0 AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___
