**daily partition reload lag** checks  

**Description**  
Table level check that calculates maximum difference in days between ingestion timestamp and event timestamp rows.

___

## **daily partition reload lag**  
  
**Check description**  
Daily partition checkpoint calculating the longest time a row waited to be load  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_reload_lag|partitioned|daily|[partition_reload_lag](../../../../reference/sensors/table/timeliness%20table%20sensors/#partition-reload-lag)|[max_days](../../../../reference/rules/comparison/#max-days)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_reload_lag
```
**Check structure (Yaml)**
```yaml
  partitioned_checks:
    daily:
      timeliness:
        daily_partition_reload_lag:
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
        daily_partition_reload_lag:
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
    
    {% macro render_ingestion_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        MAX(
            TIMESTAMP_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        MAX(
            DATE_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                DAY
            )
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        MAX(
            DATETIME_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        MAX(
            TIMESTAMP_DIFF(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
        MAX(
            TIMESTAMP_DIFF(
                SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP),
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                MILLISECOND
            )
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
    
    {% macro render_ingestion_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        MAX(
            TIMESTAMP_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        MAX(
            DATE_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                DAY
            )
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        MAX(
            DATETIME_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        MAX(
            TIMESTAMP_DIFF(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
        MAX(
            TIMESTAMP_DIFF(
                SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP),
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP),
                MILLISECOND
            )
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
    
    {% macro render_ingestion_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        MAX(
            EXTRACT(EPOCH FROM (
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            ))
        ) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
        MAX(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            )
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        MAX(
            EXTRACT(EPOCH FROM (
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            ))
        ) / 24.0 / 3600.0
        {%- else -%}
        MAX(
            EXTRACT(EPOCH FROM (
                ({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP - ({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP
            ))
        ) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
        MAX(
            EXTRACT(EPOCH FROM (
                (analyzed_table."col_inserted_at")::TIMESTAMP - (analyzed_table."col_event_timestamp")::TIMESTAMP
            ))
        ) / 24.0 / 3600.0 AS actual_value,
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
    {% macro render_ingestion_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        MAX(
            EXTRACT(EPOCH FROM (
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            ))
        ) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        MAX(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            )
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        MAX(
            EXTRACT(EPOCH FROM (
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            ))
        ) / 24.0 / 3600.0
        {%- else -%}
        MAX(
            EXTRACT(EPOCH FROM (
                ({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP - ({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP
            ))
        ) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
        MAX(
            EXTRACT(EPOCH FROM (
                (analyzed_table."col_inserted_at")::TIMESTAMP - (analyzed_table."col_event_timestamp")::TIMESTAMP
            ))
        ) / 24.0 / 3600.0 AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
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
          timeliness:
            daily_partition_reload_lag:
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
        
        {% macro render_ingestion_event_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            MAX(
                TIMESTAMP_DIFF(
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                    {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                    MILLISECOND
                )
            ) / 24.0 / 3600.0 / 1000.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            MAX(
                DATE_DIFF(
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                    {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                    DAY
                )
            )
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
            MAX(
                DATETIME_DIFF(
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                    {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                    MILLISECOND
                )
            ) / 24.0 / 3600.0 / 1000.0
            {%- else -%}
            MAX(
                TIMESTAMP_DIFF(
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                    MILLISECOND
                )
            ) / 24.0 / 3600.0 / 1000.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_ingestion_event_diff() }} AS actual_value
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
            MAX(
                TIMESTAMP_DIFF(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP),
                    SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                    MILLISECOND
                )
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
        
        {% macro render_ingestion_event_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            MAX(
                TIMESTAMP_DIFF(
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                    {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                    MILLISECOND
                )
            ) / 24.0 / 3600.0 / 1000.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            MAX(
                DATE_DIFF(
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                    {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                    DAY
                )
            )
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
            MAX(
                DATETIME_DIFF(
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                    {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                    MILLISECOND
                )
            ) / 24.0 / 3600.0 / 1000.0
            {%- else -%}
            MAX(
                TIMESTAMP_DIFF(
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                    MILLISECOND
                )
            ) / 24.0 / 3600.0 / 1000.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_ingestion_event_diff() }} AS actual_value
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
            MAX(
                TIMESTAMP_DIFF(
                    SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP),
                    SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP),
                    MILLISECOND
                )
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
        
        {% macro render_ingestion_event_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
            MAX(
                EXTRACT(EPOCH FROM (
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
                ))
            ) / 24.0 / 3600.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            MAX(
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
                )
            )
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
            MAX(
                EXTRACT(EPOCH FROM (
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
                ))
            ) / 24.0 / 3600.0
            {%- else -%}
            MAX(
                EXTRACT(EPOCH FROM (
                    ({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP - ({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP
                ))
            ) / 24.0 / 3600.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_ingestion_event_diff() }} AS actual_value
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
            MAX(
                EXTRACT(EPOCH FROM (
                    (analyzed_table."col_inserted_at")::TIMESTAMP - (analyzed_table."col_event_timestamp")::TIMESTAMP
                ))
            ) / 24.0 / 3600.0 AS actual_value,
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
        {% macro render_ingestion_event_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            MAX(
                EXTRACT(EPOCH FROM (
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
                ))
            ) / 24.0 / 3600.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            MAX(
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
                )
            )
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
            MAX(
                EXTRACT(EPOCH FROM (
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
                ))
            ) / 24.0 / 3600.0
            {%- else -%}
            MAX(
                EXTRACT(EPOCH FROM (
                    ({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP - ({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP
                ))
            ) / 24.0 / 3600.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_ingestion_event_diff() }} AS actual_value
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
            MAX(
                EXTRACT(EPOCH FROM (
                    (analyzed_table."col_inserted_at")::TIMESTAMP - (analyzed_table."col_event_timestamp")::TIMESTAMP
                ))
            ) / 24.0 / 3600.0 AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly partition reload lag**  
  
**Check description**  
Monthly partition checkpoint calculating the longest time a row waited to be load  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_reload_lag|partitioned|monthly|[partition_reload_lag](../../../../reference/sensors/table/timeliness%20table%20sensors/#partition-reload-lag)|[max_days](../../../../reference/rules/comparison/#max-days)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_reload_lag
```
**Check structure (Yaml)**
```yaml
  partitioned_checks:
    monthly:
      timeliness:
        monthly_partition_reload_lag:
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
        monthly_partition_reload_lag:
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
    
    {% macro render_ingestion_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        MAX(
            TIMESTAMP_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        MAX(
            DATE_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                DAY
            )
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        MAX(
            DATETIME_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        MAX(
            TIMESTAMP_DIFF(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
        MAX(
            TIMESTAMP_DIFF(
                SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP),
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                MILLISECOND
            )
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
    
    {% macro render_ingestion_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        MAX(
            TIMESTAMP_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        MAX(
            DATE_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                DAY
            )
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        MAX(
            DATETIME_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        MAX(
            TIMESTAMP_DIFF(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
        MAX(
            TIMESTAMP_DIFF(
                SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP),
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP),
                MILLISECOND
            )
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
    
    {% macro render_ingestion_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        MAX(
            EXTRACT(EPOCH FROM (
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            ))
        ) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
        MAX(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            )
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        MAX(
            EXTRACT(EPOCH FROM (
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            ))
        ) / 24.0 / 3600.0
        {%- else -%}
        MAX(
            EXTRACT(EPOCH FROM (
                ({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP - ({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP
            ))
        ) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
        MAX(
            EXTRACT(EPOCH FROM (
                (analyzed_table."col_inserted_at")::TIMESTAMP - (analyzed_table."col_event_timestamp")::TIMESTAMP
            ))
        ) / 24.0 / 3600.0 AS actual_value,
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
    {% macro render_ingestion_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        MAX(
            EXTRACT(EPOCH FROM (
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            ))
        ) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        MAX(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            )
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        MAX(
            EXTRACT(EPOCH FROM (
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            ))
        ) / 24.0 / 3600.0
        {%- else -%}
        MAX(
            EXTRACT(EPOCH FROM (
                ({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP - ({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP
            ))
        ) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
        MAX(
            EXTRACT(EPOCH FROM (
                (analyzed_table."col_inserted_at")::TIMESTAMP - (analyzed_table."col_event_timestamp")::TIMESTAMP
            ))
        ) / 24.0 / 3600.0 AS actual_value,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
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
          timeliness:
            monthly_partition_reload_lag:
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
        
        {% macro render_ingestion_event_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            MAX(
                TIMESTAMP_DIFF(
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                    {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                    MILLISECOND
                )
            ) / 24.0 / 3600.0 / 1000.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            MAX(
                DATE_DIFF(
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                    {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                    DAY
                )
            )
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
            MAX(
                DATETIME_DIFF(
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                    {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                    MILLISECOND
                )
            ) / 24.0 / 3600.0 / 1000.0
            {%- else -%}
            MAX(
                TIMESTAMP_DIFF(
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                    MILLISECOND
                )
            ) / 24.0 / 3600.0 / 1000.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_ingestion_event_diff() }} AS actual_value
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
            MAX(
                TIMESTAMP_DIFF(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP),
                    SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                    MILLISECOND
                )
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
        
        {% macro render_ingestion_event_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            MAX(
                TIMESTAMP_DIFF(
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                    {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                    MILLISECOND
                )
            ) / 24.0 / 3600.0 / 1000.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            MAX(
                DATE_DIFF(
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                    {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                    DAY
                )
            )
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
            MAX(
                DATETIME_DIFF(
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                    {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                    MILLISECOND
                )
            ) / 24.0 / 3600.0 / 1000.0
            {%- else -%}
            MAX(
                TIMESTAMP_DIFF(
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                    MILLISECOND
                )
            ) / 24.0 / 3600.0 / 1000.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_ingestion_event_diff() }} AS actual_value
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
            MAX(
                TIMESTAMP_DIFF(
                    SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP),
                    SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP),
                    MILLISECOND
                )
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
        
        {% macro render_ingestion_event_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
            MAX(
                EXTRACT(EPOCH FROM (
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
                ))
            ) / 24.0 / 3600.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            MAX(
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
                )
            )
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
            MAX(
                EXTRACT(EPOCH FROM (
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
                ))
            ) / 24.0 / 3600.0
            {%- else -%}
            MAX(
                EXTRACT(EPOCH FROM (
                    ({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP - ({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP
                ))
            ) / 24.0 / 3600.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_ingestion_event_diff() }} AS actual_value
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
            MAX(
                EXTRACT(EPOCH FROM (
                    (analyzed_table."col_inserted_at")::TIMESTAMP - (analyzed_table."col_event_timestamp")::TIMESTAMP
                ))
            ) / 24.0 / 3600.0 AS actual_value,
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
        {% macro render_ingestion_event_diff() -%}
            {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
            MAX(
                EXTRACT(EPOCH FROM (
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
                ))
            ) / 24.0 / 3600.0
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            MAX(
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
                )
            )
            {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' and
            table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
            MAX(
                EXTRACT(EPOCH FROM (
                    {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
                ))
            ) / 24.0 / 3600.0
            {%- else -%}
            MAX(
                EXTRACT(EPOCH FROM (
                    ({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP - ({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP
                ))
            ) / 24.0 / 3600.0
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            {{ render_ingestion_event_diff() }} AS actual_value
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
            MAX(
                EXTRACT(EPOCH FROM (
                    (analyzed_table."col_inserted_at")::TIMESTAMP - (analyzed_table."col_event_timestamp")::TIMESTAMP
                ))
            ) / 24.0 / 3600.0 AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___
