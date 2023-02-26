# datetime


___

## **date values in future percent** checks  

**Description**  
Column level check that ensures that there are no more than a set percentage of date values in future in a monitored column.

___

### **date values in future percent**  
  
**Check description**  
Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|date_values_in_future_percent|adhoc| |[date_values_in_future_percent](../../../sensors/column/#date-values-in-future-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=date_values_in_future_percent
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
        datetime:
          date_values_in_future_percent:
            error:
              max_percent: 2.0
            warning:
              max_percent: 1.0
            fatal:
              max_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-22"
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
  columns:
    target_column:
      checks:
        datetime:
          date_values_in_future_percent:
            error:
              max_percent: 2.0
            warning:
              max_percent: 1.0
            fatal:
              max_percent: 5.0
      labels:
      - This is the column that is analyzed for data quality issues
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table.`target_column` AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table."target_column" AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 39-44"
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
  columns:
    target_column:
      checks:
        datetime:
          date_values_in_future_percent:
            error:
              max_percent: 2.0
            warning:
              max_percent: 1.0
            fatal:
              max_percent: 5.0
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table.`target_column` AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table."target_column" AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily checkpoint date values in future percent**  
  
**Check description**  
Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_date_values_in_future_percent|checkpoint|daily|[date_values_in_future_percent](../../../sensors/column/#date-values-in-future-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_checkpoint_date_values_in_future_percent
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
```yaml hl_lines="0-23"
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
  columns:
    target_column:
      checkpoints:
        daily:
          datetime:
            daily_checkpoint_date_values_in_future_percent:
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
      labels:
      - This is the column that is analyzed for data quality issues
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table.`target_column` AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table."target_column" AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 40-45"
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
  columns:
    target_column:
      checkpoints:
        daily:
          datetime:
            daily_checkpoint_date_values_in_future_percent:
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table.`target_column` AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table."target_column" AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly checkpoint date values in future percent**  
  
**Check description**  
Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_date_values_in_future_percent|checkpoint|monthly|[date_values_in_future_percent](../../../sensors/column/#date-values-in-future-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_checkpoint_date_values_in_future_percent
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
```yaml hl_lines="0-23"
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
  columns:
    target_column:
      checkpoints:
        monthly:
          datetime:
            monthly_checkpoint_date_values_in_future_percent:
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
      labels:
      - This is the column that is analyzed for data quality issues
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table.`target_column` AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table."target_column" AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 40-45"
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
  columns:
    target_column:
      checkpoints:
        monthly:
          datetime:
            monthly_checkpoint_date_values_in_future_percent:
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table.`target_column` AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table."target_column" AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily partition date values in future percent**  
  
**Check description**  
Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_date_values_in_future_percent|partitioned|daily|[date_values_in_future_percent](../../../sensors/column/#date-values-in-future-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_date_values_in_future_percent
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
          datetime:
            daily_partition_date_values_in_future_percent:
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-23"
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
  columns:
    target_column:
      partitioned_checks:
        daily:
          datetime:
            daily_partition_date_values_in_future_percent:
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
      labels:
      - This is the column that is analyzed for data quality issues
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table.`target_column` AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table."target_column" AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 40-45"
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
  columns:
    target_column:
      partitioned_checks:
        daily:
          datetime:
            daily_partition_date_values_in_future_percent:
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table.`target_column` AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table."target_column" AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly partition date values in future percent**  
  
**Check description**  
Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_date_values_in_future_percent|partitioned|monthly|[date_values_in_future_percent](../../../sensors/column/#date-values-in-future-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_date_values_in_future_percent
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
          datetime:
            monthly_partition_date_values_in_future_percent:
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-23"
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
  columns:
    target_column:
      partitioned_checks:
        monthly:
          datetime:
            monthly_partition_date_values_in_future_percent:
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
      labels:
      - This is the column that is analyzed for data quality issues
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table.`target_column` AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table."target_column" AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 40-45"
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
  columns:
    target_column:
      partitioned_checks:
        monthly:
          datetime:
            monthly_partition_date_values_in_future_percent:
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
            ) / COUNT(*)
        END AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table.`target_column` AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SAFE_CAST(analyzed_table."target_column" AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ("target_column")::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___


## **datetime value in range date percent** checks  

**Description**  
Column level check that ensures that there are no more than a set percentage of date values in given range in a monitored column.

___

### **datetime value in range date percent**  
  
**Check description**  
Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|datetime_value_in_range_date_percent|adhoc| |[value_in_range_date_percent](../../../sensors/column/#value-in-range-date-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=datetime_value_in_range_date_percent
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
        datetime:
          datetime_value_in_range_date_percent:
            parameters:
              include_min_value: true
              include_max_value: true
            error:
              max_percent: 2.0
            warning:
              max_percent: 1.0
            fatal:
              max_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-25"
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
  columns:
    target_column:
      checks:
        datetime:
          datetime_value_in_range_date_percent:
            parameters:
              include_min_value: true
              include_max_value: true
            error:
              max_percent: 2.0
            warning:
              max_percent: 1.0
            fatal:
              max_percent: 5.0
      labels:
      - This is the column that is analyzed for data quality issues
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) -%}
        {%- if include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {%- endmacro -%}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'TIMESTAMP' or lib.target_column_data_type == 'TIMESTAMPTZ' or lib.target_column_data_type == 'VARCHAR'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'timestamp' or lib.target_column_data_type == 'timestamp with time zone' or lib.target_column_data_type == 'varchar'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table.`target_column` AS DATE) >= '' AND SAFE_CAST(analyzed_table.`target_column` AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table."target_column" AS DATE) >= '' AND SAFE_CAST(analyzed_table."target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 42-47"
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
  columns:
    target_column:
      checks:
        datetime:
          datetime_value_in_range_date_percent:
            parameters:
              include_min_value: true
              include_max_value: true
            error:
              max_percent: 2.0
            warning:
              max_percent: 1.0
            fatal:
              max_percent: 5.0
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) -%}
        {%- if include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {%- endmacro -%}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'TIMESTAMP' or lib.target_column_data_type == 'TIMESTAMPTZ' or lib.target_column_data_type == 'VARCHAR'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'timestamp' or lib.target_column_data_type == 'timestamp with time zone' or lib.target_column_data_type == 'varchar'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table.`target_column` AS DATE) >= '' AND SAFE_CAST(analyzed_table.`target_column` AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
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
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table."target_column" AS DATE) >= '' AND SAFE_CAST(analyzed_table."target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
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
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
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
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily checkpoint datetime value in range date percent**  
  
**Check description**  
Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_datetime_value_in_range_date_percent|checkpoint|daily|[value_in_range_date_percent](../../../sensors/column/#value-in-range-date-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_checkpoint_datetime_value_in_range_date_percent
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
```yaml hl_lines="0-26"
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
  columns:
    target_column:
      checkpoints:
        daily:
          datetime:
            daily_checkpoint_datetime_value_in_range_date_percent:
              parameters:
                include_min_value: true
                include_max_value: true
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
      labels:
      - This is the column that is analyzed for data quality issues
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) -%}
        {%- if include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {%- endmacro -%}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'TIMESTAMP' or lib.target_column_data_type == 'TIMESTAMPTZ' or lib.target_column_data_type == 'VARCHAR'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'timestamp' or lib.target_column_data_type == 'timestamp with time zone' or lib.target_column_data_type == 'varchar'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table.`target_column` AS DATE) >= '' AND SAFE_CAST(analyzed_table.`target_column` AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table."target_column" AS DATE) >= '' AND SAFE_CAST(analyzed_table."target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 43-48"
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
  columns:
    target_column:
      checkpoints:
        daily:
          datetime:
            daily_checkpoint_datetime_value_in_range_date_percent:
              parameters:
                include_min_value: true
                include_max_value: true
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) -%}
        {%- if include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {%- endmacro -%}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'TIMESTAMP' or lib.target_column_data_type == 'TIMESTAMPTZ' or lib.target_column_data_type == 'VARCHAR'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'timestamp' or lib.target_column_data_type == 'timestamp with time zone' or lib.target_column_data_type == 'varchar'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table.`target_column` AS DATE) >= '' AND SAFE_CAST(analyzed_table.`target_column` AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
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
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table."target_column" AS DATE) >= '' AND SAFE_CAST(analyzed_table."target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
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
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
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
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly checkpoint datetime value in range date percent**  
  
**Check description**  
Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_datetime_value_in_range_date_percent|checkpoint|monthly|[value_in_range_date_percent](../../../sensors/column/#value-in-range-date-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_checkpoint_datetime_value_in_range_date_percent
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
```yaml hl_lines="0-26"
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
  columns:
    target_column:
      checkpoints:
        monthly:
          datetime:
            monthly_checkpoint_datetime_value_in_range_date_percent:
              parameters:
                include_min_value: true
                include_max_value: true
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
      labels:
      - This is the column that is analyzed for data quality issues
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) -%}
        {%- if include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {%- endmacro -%}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'TIMESTAMP' or lib.target_column_data_type == 'TIMESTAMPTZ' or lib.target_column_data_type == 'VARCHAR'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'timestamp' or lib.target_column_data_type == 'timestamp with time zone' or lib.target_column_data_type == 'varchar'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table.`target_column` AS DATE) >= '' AND SAFE_CAST(analyzed_table.`target_column` AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table."target_column" AS DATE) >= '' AND SAFE_CAST(analyzed_table."target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 43-48"
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
  columns:
    target_column:
      checkpoints:
        monthly:
          datetime:
            monthly_checkpoint_datetime_value_in_range_date_percent:
              parameters:
                include_min_value: true
                include_max_value: true
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) -%}
        {%- if include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {%- endmacro -%}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'TIMESTAMP' or lib.target_column_data_type == 'TIMESTAMPTZ' or lib.target_column_data_type == 'VARCHAR'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'timestamp' or lib.target_column_data_type == 'timestamp with time zone' or lib.target_column_data_type == 'varchar'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table.`target_column` AS DATE) >= '' AND SAFE_CAST(analyzed_table.`target_column` AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
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
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table."target_column" AS DATE) >= '' AND SAFE_CAST(analyzed_table."target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
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
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
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
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily partition datetime value in range date percent**  
  
**Check description**  
Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_datetime_value_in_range_date_percent|partitioned|daily|[value_in_range_date_percent](../../../sensors/column/#value-in-range-date-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_datetime_value_in_range_date_percent
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
          datetime:
            daily_partition_datetime_value_in_range_date_percent:
              parameters:
                include_min_value: true
                include_max_value: true
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-26"
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
  columns:
    target_column:
      partitioned_checks:
        daily:
          datetime:
            daily_partition_datetime_value_in_range_date_percent:
              parameters:
                include_min_value: true
                include_max_value: true
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
      labels:
      - This is the column that is analyzed for data quality issues
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) -%}
        {%- if include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {%- endmacro -%}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'TIMESTAMP' or lib.target_column_data_type == 'TIMESTAMPTZ' or lib.target_column_data_type == 'VARCHAR'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'timestamp' or lib.target_column_data_type == 'timestamp with time zone' or lib.target_column_data_type == 'varchar'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table.`target_column` AS DATE) >= '' AND SAFE_CAST(analyzed_table.`target_column` AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table."target_column" AS DATE) >= '' AND SAFE_CAST(analyzed_table."target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 43-48"
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
  columns:
    target_column:
      partitioned_checks:
        daily:
          datetime:
            daily_partition_datetime_value_in_range_date_percent:
              parameters:
                include_min_value: true
                include_max_value: true
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) -%}
        {%- if include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {%- endmacro -%}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'TIMESTAMP' or lib.target_column_data_type == 'TIMESTAMPTZ' or lib.target_column_data_type == 'VARCHAR'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'timestamp' or lib.target_column_data_type == 'timestamp with time zone' or lib.target_column_data_type == 'varchar'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table.`target_column` AS DATE) >= '' AND SAFE_CAST(analyzed_table.`target_column` AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
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
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table."target_column" AS DATE) >= '' AND SAFE_CAST(analyzed_table."target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
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
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
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
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly partition datetime value in range date percent**  
  
**Check description**  
Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_datetime_value_in_range_date_percent|partitioned|monthly|[value_in_range_date_percent](../../../sensors/column/#value-in-range-date-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_datetime_value_in_range_date_percent
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
          datetime:
            monthly_partition_datetime_value_in_range_date_percent:
              parameters:
                include_min_value: true
                include_max_value: true
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-26"
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
  columns:
    target_column:
      partitioned_checks:
        monthly:
          datetime:
            monthly_partition_datetime_value_in_range_date_percent:
              parameters:
                include_min_value: true
                include_max_value: true
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
      labels:
      - This is the column that is analyzed for data quality issues
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) -%}
        {%- if include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {%- endmacro -%}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'TIMESTAMP' or lib.target_column_data_type == 'TIMESTAMPTZ' or lib.target_column_data_type == 'VARCHAR'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'timestamp' or lib.target_column_data_type == 'timestamp with time zone' or lib.target_column_data_type == 'varchar'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table.`target_column` AS DATE) >= '' AND SAFE_CAST(analyzed_table.`target_column` AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table."target_column" AS DATE) >= '' AND SAFE_CAST(analyzed_table."target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 43-48"
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
  columns:
    target_column:
      partitioned_checks:
        monthly:
          datetime:
            monthly_partition_datetime_value_in_range_date_percent:
              parameters:
                include_min_value: true
                include_max_value: true
              error:
                max_percent: 2.0
              warning:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) -%}
        {%- if include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {%- endmacro -%}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'TIMESTAMP' or lib.target_column_data_type == 'TIMESTAMPTZ' or lib.target_column_data_type == 'VARCHAR'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_range(lower_bound, upper_bound, include_lower_bound = true, include_upper_bound = true) %}
        {%- if include_lower_bound and include_upper_bound -%}
     {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif not include_lower_bound and include_upper_bound -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(upper_bound) }}
        {%- elif include_lower_bound and not include_upper_bound -%}
    {{ render_date_format_cast() }} >= {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- else -%}
    {{ render_date_format_cast() }} > {{ lib.make_text_constant(lower_bound) }} AND {{ render_date_format_cast() }} < {{ lib.make_text_constant(upper_bound) }}
        {%- endif -%}
    {% endmacro %}
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'timestamp' or lib.target_column_data_type == 'timestamp with time zone' or lib.target_column_data_type == 'varchar'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_range(parameters.min_value, parameters.max_value, parameters.include_min_value, parameters.include_max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table.`target_column` AS DATE) >= '' AND SAFE_CAST(analyzed_table.`target_column` AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
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
        100.0 * SUM(
            CASE
                WHEN SAFE_CAST(analyzed_table."target_column" AS DATE) >= '' AND SAFE_CAST(analyzed_table."target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
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
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
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
        100.0 * SUM(
            CASE
                WHEN CAST("target_column" AS DATE) >= '' AND CAST("target_column" AS DATE) <= '' THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

