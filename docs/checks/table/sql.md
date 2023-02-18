# sql


___

## **sql condition passed percent on table** checks  

**Description**  
Table level check that ensures that a minimum percentage of rows passed a custom SQL condition (expression).

___

### **sql condition passed percent on table**  
  
**Check description**  
Verifies that a set percentage of rows passed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|sql_condition_passed_percent_on_table|adhoc| |[sql_condition_passed_percent](../../../sensors/table/#sql-condition-passed-percent)|[min_percent](../../../rules/comparison/#min-percent)|
  
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
  checks:
    sql:
      sql_condition_passed_percent_on_table:
        parameters:
          sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
        error:
          min_percent: 99.0
        warning:
          min_percent: 100.0
        fatal:
          min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        LOCALTIMESTAMP AS time_period,
        LOCALTIMESTAMP AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 38-43"
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
    sql:
      sql_condition_passed_percent_on_table:
        parameters:
          sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
        error:
          min_percent: 99.0
        warning:
          min_percent: 100.0
        fatal:
          min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CURRENT_TIMESTAMP() AS time_period,
        TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        LOCALTIMESTAMP AS time_period,
        LOCALTIMESTAMP AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily checkpoint sql condition passed percent on table**  
  
**Check description**  
Verifies that a set percentage of rows passed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_sql_condition_passed_percent_on_table|checkpoint|daily|[sql_condition_passed_percent](../../../sensors/table/#sql-condition-passed-percent)|[min_percent](../../../rules/comparison/#min-percent)|
  
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
  checkpoints:
    daily:
      sql:
        daily_checkpoint_sql_condition_passed_percent_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            min_percent: 99.0
          warning:
            min_percent: 100.0
          fatal:
            min_percent: 95.0
        min_sql_condition_passed_percent_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            min_percent: 99.0
          warning:
            min_percent: 100.0
          fatal:
            min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 48-53"
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
      sql:
        daily_checkpoint_sql_condition_passed_percent_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            min_percent: 99.0
          warning:
            min_percent: 100.0
          fatal:
            min_percent: 95.0
        min_sql_condition_passed_percent_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            min_percent: 99.0
          warning:
            min_percent: 100.0
          fatal:
            min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly checkpoint sql condition passed percent on table**  
  
**Check description**  
Verifies that a set percentage of rows passed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_sql_condition_passed_percent_on_table|checkpoint|monthly|[sql_condition_passed_percent](../../../sensors/table/#sql-condition-passed-percent)|[min_percent](../../../rules/comparison/#min-percent)|
  
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
  checkpoints:
    monthly:
      sql:
        monthly_checkpoint_sql_condition_passed_percent_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            min_percent: 99.0
          warning:
            min_percent: 100.0
          fatal:
            min_percent: 95.0
        min_sql_condition_passed_percent_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            min_percent: 99.0
          warning:
            min_percent: 100.0
          fatal:
            min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 48-53"
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
      sql:
        monthly_checkpoint_sql_condition_passed_percent_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            min_percent: 99.0
          warning:
            min_percent: 100.0
          fatal:
            min_percent: 95.0
        min_sql_condition_passed_percent_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            min_percent: 99.0
          warning:
            min_percent: 100.0
          fatal:
            min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily partition sql condition passed percent on table**  
  
**Check description**  
Verifies that a set percentage of rows passed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_sql_condition_passed_percent_on_table|partitioned|daily|[sql_condition_passed_percent](../../../sensors/table/#sql-condition-passed-percent)|[min_percent](../../../rules/comparison/#min-percent)|
  
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
  partitioned_checks:
    daily:
      sql:
        daily_partition_sql_condition_passed_percent_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            min_percent: 99.0
          warning:
            min_percent: 100.0
          fatal:
            min_percent: 95.0
        min_sql_condition_passed_percent_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            min_percent: 99.0
          warning:
            min_percent: 100.0
          fatal:
            min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 48-53"
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
      sql:
        daily_partition_sql_condition_passed_percent_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            min_percent: 99.0
          warning:
            min_percent: 100.0
          fatal:
            min_percent: 95.0
        min_sql_condition_passed_percent_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            min_percent: 99.0
          warning:
            min_percent: 100.0
          fatal:
            min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly partition sql condition passed percent on table**  
  
**Check description**  
Verifies that a set percentage of rows passed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_sql_condition_passed_percent_on_table|partitioned|monthly|[sql_condition_passed_percent](../../../sensors/table/#sql-condition-passed-percent)|[min_percent](../../../rules/comparison/#min-percent)|
  
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
  partitioned_checks:
    monthly:
      sql:
        monthly_partition_sql_condition_passed_percent_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            min_percent: 99.0
          warning:
            min_percent: 100.0
          fatal:
            min_percent: 95.0
        min_sql_condition_passed_percent_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            min_percent: 99.0
          warning:
            min_percent: 100.0
          fatal:
            min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 48-53"
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
      sql:
        monthly_partition_sql_condition_passed_percent_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            min_percent: 99.0
          warning:
            min_percent: 100.0
          fatal:
            min_percent: 95.0
        min_sql_condition_passed_percent_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            min_percent: 99.0
          warning:
            min_percent: 100.0
          fatal:
            min_percent: 95.0
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
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                         replace('{table}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
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
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
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
                                 WHEN (SUM(col_total_impressions) > SUM(col_total_clicks))
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___


## **sql condition failed count on table** checks  

**Description**  
Table level check that ensures that there are no more than a maximum number of rows fail a custom SQL condition (expression).

___

### **sql condition failed count on table**  
  
**Check description**  
Verifies that a set number of rows failed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|sql_condition_failed_count_on_table|adhoc| |[sql_condition_failed_count](../../../sensors/table/#sql-condition-failed-count)|[max_count](../../../rules/comparison/#max-count)|
  
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
  checks:
    sql:
      sql_condition_failed_count_on_table:
        parameters:
          sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
        error:
          max_count: 0
        warning:
          max_count: 10
        fatal:
          max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        LOCALTIMESTAMP AS time_period,
        LOCALTIMESTAMP AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 38-43"
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
    sql:
      sql_condition_failed_count_on_table:
        parameters:
          sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
        error:
          max_count: 0
        warning:
          max_count: 10
        fatal:
          max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CURRENT_TIMESTAMP() AS time_period,
        TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        LOCALTIMESTAMP AS time_period,
        LOCALTIMESTAMP AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily checkpoint sql condition failed count on table**  
  
**Check description**  
Verifies that a set number of rows failed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_sql_condition_failed_count_on_table|checkpoint|daily|[sql_condition_failed_count](../../../sensors/table/#sql-condition-failed-count)|[max_count](../../../rules/comparison/#max-count)|
  
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
  checkpoints:
    daily:
      sql:
        daily_checkpoint_sql_condition_failed_count_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            max_count: 0
          warning:
            max_count: 10
          fatal:
            max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
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
  checkpoints:
    daily:
      sql:
        daily_checkpoint_sql_condition_failed_count_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            max_count: 0
          warning:
            max_count: 10
          fatal:
            max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly checkpoint sql condition failed count on table**  
  
**Check description**  
Verifies that a set number of rows failed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_sql_condition_failed_count_on_table|checkpoint|monthly|[sql_condition_failed_count](../../../sensors/table/#sql-condition-failed-count)|[max_count](../../../rules/comparison/#max-count)|
  
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
  checkpoints:
    monthly:
      sql:
        monthly_checkpoint_sql_condition_failed_count_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            max_count: 0
          warning:
            max_count: 10
          fatal:
            max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
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
  checkpoints:
    monthly:
      sql:
        monthly_checkpoint_sql_condition_failed_count_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            max_count: 0
          warning:
            max_count: 10
          fatal:
            max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily partition sql condition failed count on table**  
  
**Check description**  
Verifies that a set number of rows failed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_sql_condition_failed_count_on_table|partitioned|daily|[sql_condition_failed_count](../../../sensors/table/#sql-condition-failed-count)|[max_count](../../../rules/comparison/#max-count)|
  
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
  partitioned_checks:
    daily:
      sql:
        daily_partition_sql_condition_failed_count_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            max_count: 0
          warning:
            max_count: 10
          fatal:
            max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
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
  partitioned_checks:
    daily:
      sql:
        daily_partition_sql_condition_failed_count_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            max_count: 0
          warning:
            max_count: 10
          fatal:
            max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly partition sql condition failed count on table**  
  
**Check description**  
Verifies that a set number of rows failed a custom SQL condition (expression).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_sql_condition_failed_count_on_table|partitioned|monthly|[sql_condition_failed_count](../../../sensors/table/#sql-condition-failed-count)|[max_count](../../../rules/comparison/#max-count)|
  
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
  partitioned_checks:
    monthly:
      sql:
        monthly_partition_sql_condition_failed_count_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            max_count: 0
          warning:
            max_count: 10
          fatal:
            max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
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
  partitioned_checks:
    monthly:
      sql:
        monthly_partition_sql_condition_failed_count_on_table:
          parameters:
            sql_condition: SUM(col_total_impressions) > SUM(col_total_clicks)
          error:
            max_count: 0
          warning:
            max_count: 10
          fatal:
            max_count: 0
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
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT ({{ parameters.sql_condition |
                            replace('{table}', 'analyzed_table') }})
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
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
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        SUM(
            CASE
                WHEN NOT (SUM(col_total_impressions) > SUM(col_total_clicks))
                     THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___


## **sql aggregate expr table** checks  

**Description**  
Table level check that calculates a given SQL aggregate expression and compares it with a maximum accepted value.

___

### **sql aggregate expr table**  
  
**Check description**  
Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|sql_aggregate_expr_table|adhoc| |[sql_aggregated_expression](../../../sensors/table/#sql-aggregated-expression)|[max_value](../../../rules/comparison/#max-value)|
  
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
  checks:
    sql:
      sql_aggregate_expr_table:
        parameters:
          sql_expression: SUM(col_net_price) + SUM(col_tax)
        error:
          max_value: 1.5
        warning:
          max_value: 1.5
        fatal:
          max_value: 1.5
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
    SELECT
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        LOCALTIMESTAMP AS time_period,
        LOCALTIMESTAMP AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
**Sample configuration with a data stream (Yaml)**  
```yaml hl_lines="12-19 38-43"
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
    sql:
      sql_aggregate_expr_table:
        parameters:
          sql_expression: SUM(col_net_price) + SUM(col_tax)
        error:
          max_value: 1.5
        warning:
          max_value: 1.5
        fatal:
          max_value: 1.5
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
    SELECT
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
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
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CURRENT_TIMESTAMP() AS time_period,
        TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        LOCALTIMESTAMP AS time_period,
        LOCALTIMESTAMP AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily checkpoint sql aggregate expr table**  
  
**Check description**  
Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_sql_aggregate_expr_table|checkpoint|daily|[sql_aggregated_expression](../../../sensors/table/#sql-aggregated-expression)|[max_value](../../../rules/comparison/#max-value)|
  
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
  checkpoints:
    daily:
      sql:
        daily_checkpoint_sql_aggregate_expr_table:
          parameters:
            sql_expression: SUM(col_net_price) + SUM(col_tax)
          error:
            max_value: 1.5
          warning:
            max_value: 1.5
          fatal:
            max_value: 1.5
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
    SELECT
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
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
  checkpoints:
    daily:
      sql:
        daily_checkpoint_sql_aggregate_expr_table:
          parameters:
            sql_expression: SUM(col_net_price) + SUM(col_tax)
          error:
            max_value: 1.5
          warning:
            max_value: 1.5
          fatal:
            max_value: 1.5
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
    SELECT
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
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
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly checkpoint sql aggregate expr table**  
  
**Check description**  
Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_sql_aggregate_expr_table|checkpoint|monthly|[sql_aggregated_expression](../../../sensors/table/#sql-aggregated-expression)|[max_value](../../../rules/comparison/#max-value)|
  
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
  checkpoints:
    monthly:
      sql:
        monthly_checkpoint_sql_aggregate_expr_table:
          parameters:
            sql_expression: SUM(col_net_price) + SUM(col_tax)
          error:
            max_value: 1.5
          warning:
            max_value: 1.5
          fatal:
            max_value: 1.5
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
    SELECT
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
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
  checkpoints:
    monthly:
      sql:
        monthly_checkpoint_sql_aggregate_expr_table:
          parameters:
            sql_expression: SUM(col_net_price) + SUM(col_tax)
          error:
            max_value: 1.5
          warning:
            max_value: 1.5
          fatal:
            max_value: 1.5
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
    SELECT
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
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
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily partition sql aggregate expr table**  
  
**Check description**  
Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_sql_aggregate_expr_table|partitioned|daily|[sql_aggregated_expression](../../../sensors/table/#sql-aggregated-expression)|[max_value](../../../rules/comparison/#max-value)|
  
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
  partitioned_checks:
    daily:
      sql:
        daily_partition_sql_aggregate_expr_table:
          parameters:
            sql_expression: SUM(col_net_price) + SUM(col_tax)
          error:
            max_value: 1.5
          warning:
            max_value: 1.5
          fatal:
            max_value: 1.5
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
    SELECT
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
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
  partitioned_checks:
    daily:
      sql:
        daily_partition_sql_aggregate_expr_table:
          parameters:
            sql_expression: SUM(col_net_price) + SUM(col_tax)
          error:
            max_value: 1.5
          warning:
            max_value: 1.5
          fatal:
            max_value: 1.5
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
    SELECT
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
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
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly partition sql aggregate expr table**  
  
**Check description**  
Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_sql_aggregate_expr_table|partitioned|monthly|[sql_aggregated_expression](../../../sensors/table/#sql-aggregated-expression)|[max_value](../../../rules/comparison/#max-value)|
  
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
  partitioned_checks:
    monthly:
      sql:
        monthly_partition_sql_aggregate_expr_table:
          parameters:
            sql_expression: SUM(col_net_price) + SUM(col_tax)
          error:
            max_value: 1.5
          warning:
            max_value: 1.5
          fatal:
            max_value: 1.5
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
    SELECT
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    SELECT
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
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
  partitioned_checks:
    monthly:
      sql:
        monthly_partition_sql_aggregate_expr_table:
          parameters:
            sql_expression: SUM(col_net_price) + SUM(col_tax)
          error:
            max_value: 1.5
          warning:
            max_value: 1.5
          fatal:
            max_value: 1.5
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
    SELECT
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        ({{ parameters.sql_expression |
           replace('{table}', 'analyzed_table') }}) AS actual_value
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
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
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
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    SELECT
        (SUM(col_net_price) + SUM(col_tax)) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

