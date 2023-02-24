# timeliness


___

## **days since most recent event** checks  

**Description**  
Table level check that calculates the maximal number of days since the most recent event timestamp.

___

### **days since most recent event**  
  
**Check description**  
Calculates the number of days since the most recent event timestamp (freshness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|days_since_most_recent_event|adhoc| |[days_since_most_recent_event](../../../sensors/table/#days-since-most-recent-event)|[max_days](../../../rules/comparison/#max-days)|
  
**Set up a check (Shell)**  
To set up a basic data quality check, table editing information needs to be provided. To do this, use the command below
```
dqo.ai> table edit -c=connection_name -t=table_name
```
Following message appears
``` hl_lines="2-2"
dqo.ai> table edit -c=connection_name -t=table_name
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```
and VS Code launches. Now the YAML file can be modified to set up a data quality check. Add check in structure as at sample below and save the file.  
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../cli/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=days_since_most_recent_event
```
The example result
```
dqo.ai> check run -c=connection_name -t=table_name
Check evaluation summary per table:
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|Connection     |Table     |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|connection_name|table_name|1     |1             |0            |0       |0     |1           |0               |
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
```
**Check structure (Yaml)**
```yaml
  checks:
    timeliness:
      days_since_most_recent_event:
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
      days_since_most_recent_event:
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
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
    timeliness:
      days_since_most_recent_event:
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL with a data stream**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
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
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily checkpoint days since most recent event**  
  
**Check description**  
Daily checkpoint calculating the number of days since the most recent event timestamp (freshness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_days_since_most_recent_event|checkpoint|daily|[days_since_most_recent_event](../../../sensors/table/#days-since-most-recent-event)|[max_days](../../../rules/comparison/#max-days)|
  
**Set up a check (Shell)**  
To set up a basic data quality check, table editing information needs to be provided. To do this, use the command below
```
dqo.ai> table edit -c=connection_name -t=table_name
```
Following message appears
``` hl_lines="2-2"
dqo.ai> table edit -c=connection_name -t=table_name
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```
and VS Code launches. Now the YAML file can be modified to set up a data quality check. Add check in structure as at sample below and save the file.  
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../cli/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_checkpoint_days_since_most_recent_event
```
The example result
```
dqo.ai> check run -c=connection_name -t=table_name
Check evaluation summary per table:
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|Connection     |Table     |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|connection_name|table_name|1     |1             |0            |0       |0     |1           |0               |
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
```
**Check structure (Yaml)**
```yaml
```
**Sample configuration (Yaml)**  
```yaml hl_lines="0-21"
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
        daily_checkpoint_days_since_most_recent_event:
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
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
      timeliness:
        daily_checkpoint_days_since_most_recent_event:
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL with a data stream**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
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
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly checkpoint days since most recent event**  
  
**Check description**  
Monthly checkpoint calculating the number of days since the most recent event timestamp (freshness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_days_since_most_recent_event|checkpoint|monthly|[days_since_most_recent_event](../../../sensors/table/#days-since-most-recent-event)|[max_days](../../../rules/comparison/#max-days)|
  
**Set up a check (Shell)**  
To set up a basic data quality check, table editing information needs to be provided. To do this, use the command below
```
dqo.ai> table edit -c=connection_name -t=table_name
```
Following message appears
``` hl_lines="2-2"
dqo.ai> table edit -c=connection_name -t=table_name
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```
and VS Code launches. Now the YAML file can be modified to set up a data quality check. Add check in structure as at sample below and save the file.  
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../cli/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_checkpoint_days_since_most_recent_event
```
The example result
```
dqo.ai> check run -c=connection_name -t=table_name
Check evaluation summary per table:
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|Connection     |Table     |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|connection_name|table_name|1     |1             |0            |0       |0     |1           |0               |
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
```
**Check structure (Yaml)**
```yaml
```
**Sample configuration (Yaml)**  
```yaml hl_lines="0-21"
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
        monthly_checkpoint_days_since_most_recent_event:
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
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
      timeliness:
        monthly_checkpoint_days_since_most_recent_event:
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL with a data stream**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
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
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily partition days since most recent event**  
  
**Check description**  
Daily partition checkpoint calculating the number of days since the most recent event timestamp (freshness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_days_since_most_recent_event|partitioned|daily|[days_since_most_recent_event](../../../sensors/table/#days-since-most-recent-event)|[max_days](../../../rules/comparison/#max-days)|
  
**Set up a check (Shell)**  
To set up a basic data quality check, table editing information needs to be provided. To do this, use the command below
```
dqo.ai> table edit -c=connection_name -t=table_name
```
Following message appears
``` hl_lines="2-2"
dqo.ai> table edit -c=connection_name -t=table_name
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```
and VS Code launches. Now the YAML file can be modified to set up a data quality check. Add check in structure as at sample below and save the file.  
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../cli/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_days_since_most_recent_event
```
The example result
```
dqo.ai> check run -c=connection_name -t=table_name
Check evaluation summary per table:
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|Connection     |Table     |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|connection_name|table_name|1     |1             |0            |0       |0     |1           |0               |
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
```
**Check structure (Yaml)**
```yaml
  partitioned_checks:
    daily:
      timeliness:
        daily_partition_days_since_most_recent_event:
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
        daily_partition_days_since_most_recent_event:
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
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
        daily_partition_days_since_most_recent_event:
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL with a data stream**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
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
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly partition days since most recent event**  
  
**Check description**  
Monthly partition checkpoint calculating the number of days since the most recent event (freshness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_days_since_most_recent_event|partitioned|monthly|[days_since_most_recent_event](../../../sensors/table/#days-since-most-recent-event)|[max_days](../../../rules/comparison/#max-days)|
  
**Set up a check (Shell)**  
To set up a basic data quality check, table editing information needs to be provided. To do this, use the command below
```
dqo.ai> table edit -c=connection_name -t=table_name
```
Following message appears
``` hl_lines="2-2"
dqo.ai> table edit -c=connection_name -t=table_name
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```
and VS Code launches. Now the YAML file can be modified to set up a data quality check. Add check in structure as at sample below and save the file.  
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../cli/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_days_since_most_recent_event
```
The example result
```
dqo.ai> check run -c=connection_name -t=table_name
Check evaluation summary per table:
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|Connection     |Table     |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|connection_name|table_name|1     |1             |0            |0       |0     |1           |0               |
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
```
**Check structure (Yaml)**
```yaml
  partitioned_checks:
    monthly:
      timeliness:
        monthly_partition_days_since_most_recent_event:
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
        monthly_partition_days_since_most_recent_event:
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
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
        monthly_partition_days_since_most_recent_event:
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_current_event_diff() -%}
        {%- if table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_event_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL with a data stream**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
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
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___


## **data ingestion delay** checks  

**Description**  
Table level check that calculates the maximal number of days between event timestamp and ingestion timestamp. .

___

### **data ingestion delay**  
  
**Check description**  
Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|data_ingestion_delay|adhoc| |[data_ingestion_delay](../../../sensors/table/#data-ingestion-delay)|[max_days](../../../rules/comparison/#max-days)|
  
**Set up a check (Shell)**  
To set up a basic data quality check, table editing information needs to be provided. To do this, use the command below
```
dqo.ai> table edit -c=connection_name -t=table_name
```
Following message appears
``` hl_lines="2-2"
dqo.ai> table edit -c=connection_name -t=table_name
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```
and VS Code launches. Now the YAML file can be modified to set up a data quality check. Add check in structure as at sample below and save the file.  
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../cli/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=data_ingestion_delay
```
The example result
```
dqo.ai> check run -c=connection_name -t=table_name
Check evaluation summary per table:
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|Connection     |Table     |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|connection_name|table_name|1     |1             |0            |0       |0     |1           |0               |
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
```
**Check structure (Yaml)**
```yaml
  checks:
    timeliness:
      data_ingestion_delay:
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
      data_ingestion_delay:
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
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
    timeliness:
      data_ingestion_delay:
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL with a data stream**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
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
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily checkpoint data ingestion delay**  
  
**Check description**  
Daily checkpoint calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_data_ingestion_delay|checkpoint|daily|[data_ingestion_delay](../../../sensors/table/#data-ingestion-delay)|[max_days](../../../rules/comparison/#max-days)|
  
**Set up a check (Shell)**  
To set up a basic data quality check, table editing information needs to be provided. To do this, use the command below
```
dqo.ai> table edit -c=connection_name -t=table_name
```
Following message appears
``` hl_lines="2-2"
dqo.ai> table edit -c=connection_name -t=table_name
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```
and VS Code launches. Now the YAML file can be modified to set up a data quality check. Add check in structure as at sample below and save the file.  
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../cli/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_checkpoint_data_ingestion_delay
```
The example result
```
dqo.ai> check run -c=connection_name -t=table_name
Check evaluation summary per table:
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|Connection     |Table     |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|connection_name|table_name|1     |1             |0            |0       |0     |1           |0               |
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
```
**Check structure (Yaml)**
```yaml
```
**Sample configuration (Yaml)**  
```yaml hl_lines="0-21"
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
        daily_checkpoint_data_ingestion_delay:
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
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
      timeliness:
        daily_checkpoint_data_ingestion_delay:
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL with a data stream**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
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
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly checkpoint data ingestion delay**  
  
**Check description**  
Monthly checkpoint calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_data_ingestion_delay|checkpoint|monthly|[data_ingestion_delay](../../../sensors/table/#data-ingestion-delay)|[max_days](../../../rules/comparison/#max-days)|
  
**Set up a check (Shell)**  
To set up a basic data quality check, table editing information needs to be provided. To do this, use the command below
```
dqo.ai> table edit -c=connection_name -t=table_name
```
Following message appears
``` hl_lines="2-2"
dqo.ai> table edit -c=connection_name -t=table_name
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```
and VS Code launches. Now the YAML file can be modified to set up a data quality check. Add check in structure as at sample below and save the file.  
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../cli/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_checkpoint_data_ingestion_delay
```
The example result
```
dqo.ai> check run -c=connection_name -t=table_name
Check evaluation summary per table:
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|Connection     |Table     |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|connection_name|table_name|1     |1             |0            |0       |0     |1           |0               |
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
```
**Check structure (Yaml)**
```yaml
```
**Sample configuration (Yaml)**  
```yaml hl_lines="0-21"
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
        monthly_checkpoint_data_ingestion_delay:
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
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
      timeliness:
        monthly_checkpoint_data_ingestion_delay:
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL with a data stream**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
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
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily partition data ingestion delay**  
  
**Check description**  
Daily partition checkpoint calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_data_ingestion_delay|partitioned|daily|[data_ingestion_delay](../../../sensors/table/#data-ingestion-delay)|[max_days](../../../rules/comparison/#max-days)|
  
**Set up a check (Shell)**  
To set up a basic data quality check, table editing information needs to be provided. To do this, use the command below
```
dqo.ai> table edit -c=connection_name -t=table_name
```
Following message appears
``` hl_lines="2-2"
dqo.ai> table edit -c=connection_name -t=table_name
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```
and VS Code launches. Now the YAML file can be modified to set up a data quality check. Add check in structure as at sample below and save the file.  
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../cli/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_data_ingestion_delay
```
The example result
```
dqo.ai> check run -c=connection_name -t=table_name
Check evaluation summary per table:
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|Connection     |Table     |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|connection_name|table_name|1     |1             |0            |0       |0     |1           |0               |
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
```
**Check structure (Yaml)**
```yaml
  partitioned_checks:
    daily:
      timeliness:
        daily_partition_data_ingestion_delay:
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
        daily_partition_data_ingestion_delay:
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
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
        daily_partition_data_ingestion_delay:
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL with a data stream**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
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
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly partition data ingestion delay**  
  
**Check description**  
Monthly partition checkpoint calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_data_ingestion_delay|partitioned|monthly|[data_ingestion_delay](../../../sensors/table/#data-ingestion-delay)|[max_days](../../../rules/comparison/#max-days)|
  
**Set up a check (Shell)**  
To set up a basic data quality check, table editing information needs to be provided. To do this, use the command below
```
dqo.ai> table edit -c=connection_name -t=table_name
```
Following message appears
``` hl_lines="2-2"
dqo.ai> table edit -c=connection_name -t=table_name
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```
and VS Code launches. Now the YAML file can be modified to set up a data quality check. Add check in structure as at sample below and save the file.  
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../cli/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_data_ingestion_delay
```
The example result
```
dqo.ai> check run -c=connection_name -t=table_name
Check evaluation summary per table:
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|Connection     |Table     |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|connection_name|table_name|1     |1             |0            |0       |0     |1           |0               |
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
```
**Check structure (Yaml)**
```yaml
  partitioned_checks:
    monthly:
      timeliness:
        monthly_partition_data_ingestion_delay:
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
        monthly_partition_data_ingestion_delay:
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
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
        monthly_partition_data_ingestion_delay:
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        TIMESTAMP_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
        DATE_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATETIME' -%}
        DATETIME_DIFF(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'DATE' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'DATE' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'date' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'date' -%}
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        {%- elif table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' and
        table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
        EXTRACT(EPOCH FROM (
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_max_diff() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
**Rendered SQL with a data stream**  
=== "bigquery"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
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
=== "snowflake"
      
    ```
    SELECT
        TIMESTAMP_DIFF(
            MAX(
                SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
            ),
            MAX(
                SAFE_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___


## **days since most recent ingestion** checks  

**Description**  
Table level check that calculates the minimal number of days between event timestamp and ingestion timestamp. .

___

### **days since most recent ingestion**  
  
**Check description**  
Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|days_since_most_recent_ingestion|adhoc| |[days_since_most_recent_ingestion](../../../sensors/table/#days-since-most-recent-ingestion)|[max_days](../../../rules/comparison/#max-days)|
  
**Set up a check (Shell)**  
To set up a basic data quality check, table editing information needs to be provided. To do this, use the command below
```
dqo.ai> table edit -c=connection_name -t=table_name
```
Following message appears
``` hl_lines="2-2"
dqo.ai> table edit -c=connection_name -t=table_name
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```
and VS Code launches. Now the YAML file can be modified to set up a data quality check. Add check in structure as at sample below and save the file.  
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../cli/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=days_since_most_recent_ingestion
```
The example result
```
dqo.ai> check run -c=connection_name -t=table_name
Check evaluation summary per table:
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|Connection     |Table     |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|connection_name|table_name|1     |1             |0            |0       |0     |1           |0               |
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
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
=== "postgresql"
      
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
**Rendered SQL**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
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
=== "postgresql"
      
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
**Rendered SQL with a data stream**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
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






___

### **daily checkpoint days since most recent ingestion**  
  
**Check description**  
Daily checkpoint calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_days_since_most_recent_ingestion|checkpoint|daily|[days_since_most_recent_ingestion](../../../sensors/table/#days-since-most-recent-ingestion)|[max_days](../../../rules/comparison/#max-days)|
  
**Set up a check (Shell)**  
To set up a basic data quality check, table editing information needs to be provided. To do this, use the command below
```
dqo.ai> table edit -c=connection_name -t=table_name
```
Following message appears
``` hl_lines="2-2"
dqo.ai> table edit -c=connection_name -t=table_name
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```
and VS Code launches. Now the YAML file can be modified to set up a data quality check. Add check in structure as at sample below and save the file.  
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../cli/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_checkpoint_days_since_most_recent_ingestion
```
The example result
```
dqo.ai> check run -c=connection_name -t=table_name
Check evaluation summary per table:
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|Connection     |Table     |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|connection_name|table_name|1     |1             |0            |0       |0     |1           |0               |
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
```
**Check structure (Yaml)**
```yaml
```
**Sample configuration (Yaml)**  
```yaml hl_lines="0-21"
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
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
=== "postgresql"
      
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
**Rendered SQL**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
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
=== "postgresql"
      
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
**Rendered SQL with a data stream**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
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






___

### **monthly checkpoint days since most recent ingestion**  
  
**Check description**  
Monthly checkpoint calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_days_since_most_recent_ingestion|checkpoint|monthly|[days_since_most_recent_ingestion](../../../sensors/table/#days-since-most-recent-ingestion)|[max_days](../../../rules/comparison/#max-days)|
  
**Set up a check (Shell)**  
To set up a basic data quality check, table editing information needs to be provided. To do this, use the command below
```
dqo.ai> table edit -c=connection_name -t=table_name
```
Following message appears
``` hl_lines="2-2"
dqo.ai> table edit -c=connection_name -t=table_name
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```
and VS Code launches. Now the YAML file can be modified to set up a data quality check. Add check in structure as at sample below and save the file.  
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../cli/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_checkpoint_days_since_most_recent_ingestion
```
The example result
```
dqo.ai> check run -c=connection_name -t=table_name
Check evaluation summary per table:
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|Connection     |Table     |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|connection_name|table_name|1     |1             |0            |0       |0     |1           |0               |
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
```
**Check structure (Yaml)**
```yaml
```
**Sample configuration (Yaml)**  
```yaml hl_lines="0-21"
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
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
=== "postgresql"
      
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
**Rendered SQL**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
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
=== "postgresql"
      
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
**Rendered SQL with a data stream**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
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






___

### **daily partition days since most recent ingestion**  
  
**Check description**  
Daily partition checkpoint calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_days_since_most_recent_ingestion|partitioned|daily|[days_since_most_recent_ingestion](../../../sensors/table/#days-since-most-recent-ingestion)|[max_days](../../../rules/comparison/#max-days)|
  
**Set up a check (Shell)**  
To set up a basic data quality check, table editing information needs to be provided. To do this, use the command below
```
dqo.ai> table edit -c=connection_name -t=table_name
```
Following message appears
``` hl_lines="2-2"
dqo.ai> table edit -c=connection_name -t=table_name
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```
and VS Code launches. Now the YAML file can be modified to set up a data quality check. Add check in structure as at sample below and save the file.  
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../cli/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_days_since_most_recent_ingestion
```
The example result
```
dqo.ai> check run -c=connection_name -t=table_name
Check evaluation summary per table:
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|Connection     |Table     |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|connection_name|table_name|1     |1             |0            |0       |0     |1           |0               |
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
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
=== "postgresql"
      
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
**Rendered SQL**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
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
=== "postgresql"
      
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
**Rendered SQL with a data stream**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
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






___

### **monthly partition days since most recent ingestion**  
  
**Check description**  
Monthly partition checkpoint calculating the time difference in days between the current date and the most recent data data ingestion timestamp (staleness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_days_since_most_recent_ingestion|partitioned|monthly|[days_since_most_recent_ingestion](../../../sensors/table/#days-since-most-recent-ingestion)|[max_days](../../../rules/comparison/#max-days)|
  
**Set up a check (Shell)**  
To set up a basic data quality check, table editing information needs to be provided. To do this, use the command below
```
dqo.ai> table edit -c=connection_name -t=table_name
```
Following message appears
``` hl_lines="2-2"
dqo.ai> table edit -c=connection_name -t=table_name
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```
and VS Code launches. Now the YAML file can be modified to set up a data quality check. Add check in structure as at sample below and save the file.  
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../cli/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_days_since_most_recent_ingestion
```
The example result
```
dqo.ai> check run -c=connection_name -t=table_name
Check evaluation summary per table:
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|Connection     |Table     |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|connection_name|table_name|1     |1             |0            |0       |0     |1           |0               |
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
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
=== "postgresql"
      
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
**Rendered SQL**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
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
=== "postgresql"
      
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
**Rendered SQL with a data stream**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
    ```
    SELECT
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
        )) / 24.0 / 3600.0 AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
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






___


## **daily partition reload lag** checks  

**Description**  
Table level check that calculates maximum difference in days between ingestion timestamp and event timestamp rows.

___

### **daily partition reload lag**  
  
**Check description**  
Daily partition checkpoint calculating the longest time a row waited to be load  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_reload_lag|partitioned|daily|[partition_reload_lag](../../../sensors/table/#partition-reload-lag)|[max_days](../../../rules/comparison/#max-days)|
  
**Set up a check (Shell)**  
To set up a basic data quality check, table editing information needs to be provided. To do this, use the command below
```
dqo.ai> table edit -c=connection_name -t=table_name
```
Following message appears
``` hl_lines="2-2"
dqo.ai> table edit -c=connection_name -t=table_name
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```
and VS Code launches. Now the YAML file can be modified to set up a data quality check. Add check in structure as at sample below and save the file.  
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../cli/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_reload_lag
```
The example result
```
dqo.ai> check run -c=connection_name -t=table_name
Check evaluation summary per table:
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|Connection     |Table     |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|connection_name|table_name|1     |1             |0            |0       |0     |1           |0               |
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
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
=== "postgresql"
      
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
**Rendered SQL**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
    ```
    SELECT
        MAX(
            EXTRACT(EPOCH FROM (
                (analyzed_table."col_inserted_at")::TIMESTAMP - (analyzed_table."col_event_timestamp")::TIMESTAMP
            ))
        ) / 24.0 / 3600.0 AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
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
=== "postgresql"
      
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
**Rendered SQL with a data stream**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
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
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
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






___

### **monthly partition reload lag**  
  
**Check description**  
Monthly partition checkpoint calculating the longest time a row waited to be load  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_reload_lag|partitioned|monthly|[partition_reload_lag](../../../sensors/table/#partition-reload-lag)|[max_days](../../../rules/comparison/#max-days)|
  
**Set up a check (Shell)**  
To set up a basic data quality check, table editing information needs to be provided. To do this, use the command below
```
dqo.ai> table edit -c=connection_name -t=table_name
```
Following message appears
``` hl_lines="2-2"
dqo.ai> table edit -c=connection_name -t=table_name
Launching VS Code, remember to install YAML extension by RedHat and Better Jinja by Samuel Colvin
```
and VS Code launches. Now the YAML file can be modified to set up a data quality check. Add check in structure as at sample below and save the file.  
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../cli/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_reload_lag
```
The example result
```
dqo.ai> check run -c=connection_name -t=table_name
Check evaluation summary per table:
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|Connection     |Table     |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
|connection_name|table_name|1     |1             |0            |0       |0     |1           |0               |
+---------------+----------+------+--------------+-------------+--------+------+------------+----------------+
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
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
=== "postgresql"
      
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
**Rendered SQL**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
    ```
    SELECT
        MAX(
            EXTRACT(EPOCH FROM (
                (analyzed_table."col_inserted_at")::TIMESTAMP - (analyzed_table."col_event_timestamp")::TIMESTAMP
            ))
        ) / 24.0 / 3600.0 AS actual_value,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
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
=== "postgresql"
      
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
**Rendered SQL with a data stream**  
=== "bigquery"
      
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
=== "snowflake"
      
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
=== "redshift"
      
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
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
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






___

