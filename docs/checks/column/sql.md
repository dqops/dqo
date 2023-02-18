# sql


___

## **sql condition passed percent on column** checks  

**Description**  
Column level check that ensures that a set percentage of rows passed a custom SQL condition (expression).

___

### **sql condition passed percent on column**  
  
**Check description**  
Verifies that a minimum percentage of rows passed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|sql_condition_passed_percent_on_column|adhoc| |[sql_condition_passed_percent](../../../sensors/column/#sql-condition-passed-percent)|[min_percent](../../../rules/comparison/#min-percent)|
  
**Sample configuration (Yaml)**  
```yaml
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
        sql:
          sql_condition_passed_percent_on_column:
            parameters:
              sql_condition: "{column} + col_tax = col_total_price_with_tax"
            error:
              min_percent: 99.0
            warning:
              min_percent: 100.0
            fatal:
              min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.`target_column` IS NOT NULL
                    AND (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IS NOT NULL
                    AND (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table."target_column")
        END AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        CASE
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN "target_column" IS NOT NULL
                    AND ("target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT("target_column")
        END AS actual_value,
        LOCALTIMESTAMP AS time_period,
        TIMESTAMP(LOCALTIMESTAMP) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 41-46"
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
        sql:
          sql_condition_passed_percent_on_column:
            parameters:
              sql_condition: "{column} + col_tax = col_total_price_with_tax"
            error:
              min_percent: 99.0
            warning:
              min_percent: 100.0
            fatal:
              min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.`target_column` IS NOT NULL
                    AND (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IS NOT NULL
                    AND (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table."target_column")
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        CASE
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN "target_column" IS NOT NULL
                    AND ("target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT("target_column")
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        LOCALTIMESTAMP AS time_period,
        TIMESTAMP(LOCALTIMESTAMP) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily checkpoint sql condition passed percent on column**  
  
**Check description**  
Verifies that a minimum percentage of rows passed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_sql_condition_passed_percent_on_column|checkpoint|daily|[sql_condition_passed_percent](../../../sensors/column/#sql-condition-passed-percent)|[min_percent](../../../rules/comparison/#min-percent)|
  
**Sample configuration (Yaml)**  
```yaml
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
          sql:
            daily_checkpoint_sql_condition_passed_percent_on_column:
              parameters:
                sql_condition: "{column} + col_tax = col_total_price_with_tax"
              error:
                min_percent: 99.0
              warning:
                min_percent: 100.0
              fatal:
                min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.`target_column` IS NOT NULL
                    AND (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IS NOT NULL
                    AND (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table."target_column")
        END AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        CASE
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN "target_column" IS NOT NULL
                    AND ("target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT("target_column")
        END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        TIMESTAMP(CAST(LOCALTIMESTAMP AS date)) AS time_period_utc
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
      checkpoints:
        daily:
          sql:
            daily_checkpoint_sql_condition_passed_percent_on_column:
              parameters:
                sql_condition: "{column} + col_tax = col_total_price_with_tax"
              error:
                min_percent: 99.0
              warning:
                min_percent: 100.0
              fatal:
                min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.`target_column` IS NOT NULL
                    AND (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IS NOT NULL
                    AND (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table."target_column")
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        CASE
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN "target_column" IS NOT NULL
                    AND ("target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT("target_column")
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        TIMESTAMP(CAST(LOCALTIMESTAMP AS date)) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly checkpoint sql condition passed percent on column**  
  
**Check description**  
Verifies that a minimum percentage of rows passed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_sql_condition_passed_percent_on_column|checkpoint|monthly|[sql_condition_passed_percent](../../../sensors/column/#sql-condition-passed-percent)|[min_percent](../../../rules/comparison/#min-percent)|
  
**Sample configuration (Yaml)**  
```yaml
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
          sql:
            monthly_checkpoint_sql_condition_passed_percent_on_column:
              parameters:
                sql_condition: "{column} + col_tax = col_total_price_with_tax"
              error:
                min_percent: 99.0
              warning:
                min_percent: 100.0
              fatal:
                min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.`target_column` IS NOT NULL
                    AND (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IS NOT NULL
                    AND (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table."target_column")
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        CASE
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN "target_column" IS NOT NULL
                    AND ("target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT("target_column")
        END AS actual_value,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS time_period_utc
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
      checkpoints:
        monthly:
          sql:
            monthly_checkpoint_sql_condition_passed_percent_on_column:
              parameters:
                sql_condition: "{column} + col_tax = col_total_price_with_tax"
              error:
                min_percent: 99.0
              warning:
                min_percent: 100.0
              fatal:
                min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.`target_column` IS NOT NULL
                    AND (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IS NOT NULL
                    AND (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table."target_column")
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        CASE
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN "target_column" IS NOT NULL
                    AND ("target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT("target_column")
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily partition sql condition passed percent on column**  
  
**Check description**  
Verifies that a minimum percentage of rows passed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_sql_condition_passed_percent_on_column|partitioned|daily|[sql_condition_passed_percent](../../../sensors/column/#sql-condition-passed-percent)|[min_percent](../../../rules/comparison/#min-percent)|
  
**Sample configuration (Yaml)**  
```yaml
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
          sql:
            daily_partition_sql_condition_passed_percent_on_column:
              parameters:
                sql_condition: "{column} + col_tax = col_total_price_with_tax"
              error:
                min_percent: 99.0
              warning:
                min_percent: 100.0
              fatal:
                min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.`target_column` IS NOT NULL
                    AND (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IS NOT NULL
                    AND (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table."target_column")
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        CASE
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN "target_column" IS NOT NULL
                    AND ("target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT("target_column")
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
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
      partitioned_checks:
        daily:
          sql:
            daily_partition_sql_condition_passed_percent_on_column:
              parameters:
                sql_condition: "{column} + col_tax = col_total_price_with_tax"
              error:
                min_percent: 99.0
              warning:
                min_percent: 100.0
              fatal:
                min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.`target_column` IS NOT NULL
                    AND (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IS NOT NULL
                    AND (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table."target_column")
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        CASE
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN "target_column" IS NOT NULL
                    AND ("target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT("target_column")
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly partition sql condition passed percent on column**  
  
**Check description**  
Verifies that a minimum percentage of rows passed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_sql_condition_passed_percent_on_column|partitioned|monthly|[sql_condition_passed_percent](../../../sensors/column/#sql-condition-passed-percent)|[min_percent](../../../rules/comparison/#min-percent)|
  
**Sample configuration (Yaml)**  
```yaml
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
          sql:
            monthly_partition_sql_condition_passed_percent_on_column:
              parameters:
                sql_condition: "{column} + col_tax = col_total_price_with_tax"
              error:
                min_percent: 99.0
              warning:
                min_percent: 100.0
              fatal:
                min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.`target_column` IS NOT NULL
                    AND (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IS NOT NULL
                    AND (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table."target_column")
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        CASE
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN "target_column" IS NOT NULL
                    AND ("target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT("target_column")
        END AS actual_value,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
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
      partitioned_checks:
        monthly:
          sql:
            monthly_partition_sql_condition_passed_percent_on_column:
              parameters:
                sql_condition: "{column} + col_tax = col_total_price_with_tax"
              error:
                min_percent: 99.0
              warning:
                min_percent: 100.0
              fatal:
                min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.`target_column` IS NOT NULL
                    AND (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IS NOT NULL
                    AND (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(analyzed_table."target_column")
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        CASE
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN "target_column" IS NOT NULL
                    AND ("target_column" + col_tax = col_total_price_with_tax)
                        THEN 1
                    ELSE 0
                END
            ) / COUNT("target_column")
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___


## **sql condition failed count on column** checks  

**Description**  
Column level check that ensures that there are no more than a set number of rows fail a custom SQL condition (expression) evaluated for a given column.

___

### **sql condition failed count on column**  
  
**Check description**  
Verifies that a maximum number of rows failed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|sql_condition_failed_count_on_column|adhoc| |[sql_condition_failed_count](../../../sensors/column/#sql-condition-failed-count)|[max_count](../../../rules/comparison/#max-count)|
  
**Sample configuration (Yaml)**  
```yaml
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
        sql:
          sql_condition_failed_count_on_column:
            parameters:
              sql_condition: "{column} + col_tax = col_total_price_with_tax"
            error:
              max_count: 0
            warning:
              max_count: 10
            fatal:
              max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` IS NOT NULL
                AND NOT (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN analyzed_table."target_column" IS NOT NULL
                AND NOT (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN "target_column" IS NOT NULL
                AND NOT ("target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        LOCALTIMESTAMP AS time_period,
        TIMESTAMP(LOCALTIMESTAMP) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 41-46"
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
        sql:
          sql_condition_failed_count_on_column:
            parameters:
              sql_condition: "{column} + col_tax = col_total_price_with_tax"
            error:
              max_count: 0
            warning:
              max_count: 10
            fatal:
              max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` IS NOT NULL
                AND NOT (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" IS NOT NULL
                AND NOT (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN "target_column" IS NOT NULL
                AND NOT ("target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        LOCALTIMESTAMP AS time_period,
        TIMESTAMP(LOCALTIMESTAMP) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily checkpoint sql condition failed count on column**  
  
**Check description**  
Verifies that a maximum number of rows failed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_sql_condition_failed_count_on_column|checkpoint|daily|[sql_condition_failed_count](../../../sensors/column/#sql-condition-failed-count)|[max_count](../../../rules/comparison/#max-count)|
  
**Sample configuration (Yaml)**  
```yaml
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
          sql:
            daily_checkpoint_sql_condition_failed_count_on_column:
              parameters:
                sql_condition: "{column} + col_tax = col_total_price_with_tax"
              error:
                max_count: 0
              warning:
                max_count: 10
              fatal:
                max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` IS NOT NULL
                AND NOT (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN analyzed_table."target_column" IS NOT NULL
                AND NOT (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN "target_column" IS NOT NULL
                AND NOT ("target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        TIMESTAMP(CAST(LOCALTIMESTAMP AS date)) AS time_period_utc
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
      checkpoints:
        daily:
          sql:
            daily_checkpoint_sql_condition_failed_count_on_column:
              parameters:
                sql_condition: "{column} + col_tax = col_total_price_with_tax"
              error:
                max_count: 0
              warning:
                max_count: 10
              fatal:
                max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` IS NOT NULL
                AND NOT (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" IS NOT NULL
                AND NOT (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN "target_column" IS NOT NULL
                AND NOT ("target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        TIMESTAMP(CAST(LOCALTIMESTAMP AS date)) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly checkpoint sql condition failed count on column**  
  
**Check description**  
Verifies that a maximum number of rows failed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_sql_condition_failed_count_on_column|checkpoint|monthly|[sql_condition_failed_count](../../../sensors/column/#sql-condition-failed-count)|[max_count](../../../rules/comparison/#max-count)|
  
**Sample configuration (Yaml)**  
```yaml
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
          sql:
            monthly_checkpoint_sql_condition_failed_count_on_column:
              parameters:
                sql_condition: "{column} + col_tax = col_total_price_with_tax"
              error:
                max_count: 0
              warning:
                max_count: 10
              fatal:
                max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` IS NOT NULL
                AND NOT (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN analyzed_table."target_column" IS NOT NULL
                AND NOT (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN "target_column" IS NOT NULL
                AND NOT ("target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS time_period_utc
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
      checkpoints:
        monthly:
          sql:
            monthly_checkpoint_sql_condition_failed_count_on_column:
              parameters:
                sql_condition: "{column} + col_tax = col_total_price_with_tax"
              error:
                max_count: 0
              warning:
                max_count: 10
              fatal:
                max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` IS NOT NULL
                AND NOT (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" IS NOT NULL
                AND NOT (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN "target_column" IS NOT NULL
                AND NOT ("target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily partition sql condition failed count on column**  
  
**Check description**  
Verifies that a maximum number of rows failed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_sql_condition_failed_count_on_column|partitioned|daily|[sql_condition_failed_count](../../../sensors/column/#sql-condition-failed-count)|[max_count](../../../rules/comparison/#max-count)|
  
**Sample configuration (Yaml)**  
```yaml
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
          sql:
            daily_partition_sql_condition_failed_count_on_column:
              parameters:
                sql_condition: "{column} + col_tax = col_total_price_with_tax"
              error:
                max_count: 0
              warning:
                max_count: 10
              fatal:
                max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` IS NOT NULL
                AND NOT (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN analyzed_table."target_column" IS NOT NULL
                AND NOT (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN "target_column" IS NOT NULL
                AND NOT ("target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
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
      partitioned_checks:
        daily:
          sql:
            daily_partition_sql_condition_failed_count_on_column:
              parameters:
                sql_condition: "{column} + col_tax = col_total_price_with_tax"
              error:
                max_count: 0
              warning:
                max_count: 10
              fatal:
                max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` IS NOT NULL
                AND NOT (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" IS NOT NULL
                AND NOT (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN "target_column" IS NOT NULL
                AND NOT ("target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly partition sql condition failed count on column**  
  
**Check description**  
Verifies that a maximum number of rows failed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_sql_condition_failed_count_on_column|partitioned|monthly|[sql_condition_failed_count](../../../sensors/column/#sql-condition-failed-count)|[max_count](../../../rules/comparison/#max-count)|
  
**Sample configuration (Yaml)**  
```yaml
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
          sql:
            monthly_partition_sql_condition_failed_count_on_column:
              parameters:
                sql_condition: "{column} + col_tax = col_total_price_with_tax"
              error:
                max_count: 0
              warning:
                max_count: 10
              fatal:
                max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` IS NOT NULL
                AND NOT (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN analyzed_table."target_column" IS NOT NULL
                AND NOT (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN "target_column" IS NOT NULL
                AND NOT ("target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
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
      partitioned_checks:
        monthly:
          sql:
            monthly_partition_sql_condition_failed_count_on_column:
              parameters:
                sql_condition: "{column} + col_tax = col_total_price_with_tax"
              error:
                max_count: 0
              warning:
                max_count: 10
              fatal:
                max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN analyzed_table.`target_column` IS NOT NULL
                AND NOT (analyzed_table.`target_column` + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
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
        SUM(
            CASE
                WHEN analyzed_table."target_column" IS NOT NULL
                AND NOT (analyzed_table."target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN "target_column" IS NOT NULL
                AND NOT ("target_column" + col_tax = col_total_price_with_tax)
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___


## **sql aggregate expr column** checks  

**Description**  
Column level check that calculates a given SQL aggregate expression on a column and compares it with a maximum accepted value.

___

### **sql aggregate expr column**  
  
**Check description**  
Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|sql_aggregate_expr_column|adhoc| |[sql_aggregated_expression](../../../sensors/column/#sql-aggregated-expression)|[between_floats](../../../rules/comparison/#between-floats)|
  
**Sample configuration (Yaml)**  
```yaml
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
        sql:
          sql_aggregate_expr_column:
            parameters:
              sql_expression: "MAX({column})"
            error:
              from: 10.0
              to: 20.5
            warning:
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
        (MAX(analyzed_table.`target_column`)) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        (MAX(analyzed_table."target_column")) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (MAX("target_column")) AS actual_value,
        LOCALTIMESTAMP AS time_period,
        TIMESTAMP(LOCALTIMESTAMP) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 44-49"
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
        sql:
          sql_aggregate_expr_column:
            parameters:
              sql_expression: "MAX({column})"
            error:
              from: 10.0
              to: 20.5
            warning:
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
        (MAX(analyzed_table.`target_column`)) AS actual_value,
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
        (MAX(analyzed_table."target_column")) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (MAX("target_column")) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        LOCALTIMESTAMP AS time_period,
        TIMESTAMP(LOCALTIMESTAMP) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily checkpoint sql aggregate expr column**  
  
**Check description**  
Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_sql_aggregate_expr_column|checkpoint|daily|[sql_aggregated_expression](../../../sensors/column/#sql-aggregated-expression)|[between_floats](../../../rules/comparison/#between-floats)|
  
**Sample configuration (Yaml)**  
```yaml
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
          sql:
            daily_checkpoint_sql_aggregate_expr_column:
              parameters:
                sql_expression: "MAX({column})"
              error:
                from: 10.0
                to: 20.5
              warning:
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
        (MAX(analyzed_table.`target_column`)) AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        (MAX(analyzed_table."target_column")) AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (MAX("target_column")) AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        TIMESTAMP(CAST(LOCALTIMESTAMP AS date)) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 45-50"
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
          sql:
            daily_checkpoint_sql_aggregate_expr_column:
              parameters:
                sql_expression: "MAX({column})"
              error:
                from: 10.0
                to: 20.5
              warning:
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
        (MAX(analyzed_table.`target_column`)) AS actual_value,
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
        (MAX(analyzed_table."target_column")) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (MAX("target_column")) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        TIMESTAMP(CAST(LOCALTIMESTAMP AS date)) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly checkpoint sql aggregate expr column**  
  
**Check description**  
Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_sql_aggregate_expr_column|checkpoint|monthly|[sql_aggregated_expression](../../../sensors/column/#sql-aggregated-expression)|[between_floats](../../../rules/comparison/#between-floats)|
  
**Sample configuration (Yaml)**  
```yaml
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
          sql:
            monthly_checkpoint_sql_aggregate_expr_column:
              parameters:
                sql_expression: "MAX({column})"
              error:
                from: 10.0
                to: 20.5
              warning:
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
        (MAX(analyzed_table.`target_column`)) AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        (MAX(analyzed_table."target_column")) AS actual_value,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (MAX("target_column")) AS actual_value,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 45-50"
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
          sql:
            monthly_checkpoint_sql_aggregate_expr_column:
              parameters:
                sql_expression: "MAX({column})"
              error:
                from: 10.0
                to: 20.5
              warning:
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
        (MAX(analyzed_table.`target_column`)) AS actual_value,
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
        (MAX(analyzed_table."target_column")) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (MAX("target_column")) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily partition sql aggregate expr column**  
  
**Check description**  
Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_sql_aggregate_expr_column|partitioned|daily|[sql_aggregated_expression](../../../sensors/column/#sql-aggregated-expression)|[between_floats](../../../rules/comparison/#between-floats)|
  
**Sample configuration (Yaml)**  
```yaml
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
          sql:
            daily_partition_sql_aggregate_expr_column:
              parameters:
                sql_expression: "MAX({column})"
              error:
                from: 10.0
                to: 20.5
              warning:
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
        (MAX(analyzed_table.`target_column`)) AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        (MAX(analyzed_table."target_column")) AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (MAX("target_column")) AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 45-50"
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
          sql:
            daily_partition_sql_aggregate_expr_column:
              parameters:
                sql_expression: "MAX({column})"
              error:
                from: 10.0
                to: 20.5
              warning:
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
        (MAX(analyzed_table.`target_column`)) AS actual_value,
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
        (MAX(analyzed_table."target_column")) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (MAX("target_column")) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly partition sql aggregate expr column**  
  
**Check description**  
Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_sql_aggregate_expr_column|partitioned|monthly|[sql_aggregated_expression](../../../sensors/column/#sql-aggregated-expression)|[between_floats](../../../rules/comparison/#between-floats)|
  
**Sample configuration (Yaml)**  
```yaml
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
          sql:
            monthly_partition_sql_aggregate_expr_column:
              parameters:
                sql_expression: "MAX({column})"
              error:
                from: 10.0
                to: 20.5
              warning:
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
**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
        (MAX(analyzed_table.`target_column`)) AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        (MAX(analyzed_table."target_column")) AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (MAX("target_column")) AS actual_value,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 45-50"
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
          sql:
            monthly_partition_sql_aggregate_expr_column:
              parameters:
                sql_expression: "MAX({column})"
              error:
                from: 10.0
                to: 20.5
              warning:
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
**SQL Template with a data stream (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) }}) AS actual_value
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
        (MAX(analyzed_table.`target_column`)) AS actual_value,
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
        (MAX(analyzed_table."target_column")) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (MAX("target_column")) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TIMESTAMP(DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

