# uniqueness


___

## **unique count** checks  

**Description**  
Column level check that ensures that there are no more than a maximum number of nulls in a monitored column.

___

### **unique count**  
  
**Check description**  
Verifies that the number of unique values in a column does not exceed the minimum accepted count.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|unique_count|adhoc| |[unique_count](../../../sensors/column/#unique-count)|[min_count](../../../rules/comparison/#min-count)|
  
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
        uniqueness:
          unique_count:
            error:
              min_count: 5
            warning:
              min_count: 5
            fatal:
              min_count: 5
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table') }})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT(analyzed_table.`target_column`)
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
        COUNT(
            DISTINCT(analyzed_table."target_column")
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
        COUNT(
            DISTINCT("target_column")
        ) AS actual_value,
        LOCALTIMESTAMP AS time_period,
        LOCALTIMESTAMP AS time_period_utc
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
        uniqueness:
          unique_count:
            error:
              min_count: 5
            warning:
              min_count: 5
            fatal:
              min_count: 5
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table') }})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT(analyzed_table.`target_column`)
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
        COUNT(
            DISTINCT(analyzed_table."target_column")
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
        COUNT(
            DISTINCT("target_column")
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

### **daily checkpoint unique count**  
  
**Check description**  
Verifies that the number of unique values in a column does not exceed the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_unique_count|checkpoint|daily|[unique_count](../../../sensors/column/#unique-count)|[min_count](../../../rules/comparison/#min-count)|
  
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
          uniqueness:
            daily_checkpoint_unique_count:
              error:
                min_count: 5
              warning:
                min_count: 5
              fatal:
                min_count: 5
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table') }})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT(analyzed_table.`target_column`)
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
        COUNT(
            DISTINCT(analyzed_table."target_column")
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
        COUNT(
            DISTINCT("target_column")
        ) AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
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
          uniqueness:
            daily_checkpoint_unique_count:
              error:
                min_count: 5
              warning:
                min_count: 5
              fatal:
                min_count: 5
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table') }})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT(analyzed_table.`target_column`)
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
        COUNT(
            DISTINCT(analyzed_table."target_column")
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
        COUNT(
            DISTINCT("target_column")
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

### **monthly checkpoint unique count**  
  
**Check description**  
Verifies that the number of unique values in a column does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_unique_count|checkpoint|monthly|[unique_count](../../../sensors/column/#unique-count)|[min_count](../../../rules/comparison/#min-count)|
  
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
          uniqueness:
            monthly_checkpoint_unique_count:
              error:
                min_count: 5
              warning:
                min_count: 5
              fatal:
                min_count: 5
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table') }})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT(analyzed_table.`target_column`)
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
        COUNT(
            DISTINCT(analyzed_table."target_column")
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
        COUNT(
            DISTINCT("target_column")
        ) AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
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
          uniqueness:
            monthly_checkpoint_unique_count:
              error:
                min_count: 5
              warning:
                min_count: 5
              fatal:
                min_count: 5
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table') }})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT(analyzed_table.`target_column`)
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
        COUNT(
            DISTINCT(analyzed_table."target_column")
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
        COUNT(
            DISTINCT("target_column")
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

### **daily partition unique count**  
  
**Check description**  
Verifies that the number of unique values in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_unique_count|partitioned|daily|[unique_count](../../../sensors/column/#unique-count)|[min_count](../../../rules/comparison/#min-count)|
  
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
          uniqueness:
            daily_partition_unique_count:
              error:
                min_count: 5
              warning:
                min_count: 5
              fatal:
                min_count: 5
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table') }})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT(analyzed_table.`target_column`)
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
        COUNT(
            DISTINCT(analyzed_table."target_column")
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
        COUNT(
            DISTINCT("target_column")
        ) AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
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
          uniqueness:
            daily_partition_unique_count:
              error:
                min_count: 5
              warning:
                min_count: 5
              fatal:
                min_count: 5
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table') }})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT(analyzed_table.`target_column`)
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
        COUNT(
            DISTINCT(analyzed_table."target_column")
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
        COUNT(
            DISTINCT("target_column")
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

### **monthly partition unique count**  
  
**Check description**  
Verifies that the number of unique values in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_unique_count|partitioned|monthly|[unique_count](../../../sensors/column/#unique-count)|[min_count](../../../rules/comparison/#min-count)|
  
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
          uniqueness:
            monthly_partition_unique_count:
              error:
                min_count: 5
              warning:
                min_count: 5
              fatal:
                min_count: 5
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table') }})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT(analyzed_table.`target_column`)
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
        COUNT(
            DISTINCT(analyzed_table."target_column")
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
        COUNT(
            DISTINCT("target_column")
        ) AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
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
          uniqueness:
            monthly_partition_unique_count:
              error:
                min_count: 5
              warning:
                min_count: 5
              fatal:
                min_count: 5
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table') }})
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
        COUNT(
            DISTINCT({{ lib.render_target_column('analyzed_table')}})
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
        COUNT(
            DISTINCT(analyzed_table.`target_column`)
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
        COUNT(
            DISTINCT(analyzed_table."target_column")
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
        COUNT(
            DISTINCT("target_column")
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


## **unique percent** checks  

**Description**  
Column level check that ensures that there are no more than a maximum number of nulls in a monitored column.

___

### **unique percent**  
  
**Check description**  
Verifies that the percentage of unique values in a column does not exceed the minimum accepted count.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|unique_percent|adhoc| |[unique_percent](../../../sensors/column/#unique-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
        uniqueness:
          unique_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table.`target_column`) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table."target_column") / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT "target_column") / COUNT("target_column")
        END AS actual_value,
        LOCALTIMESTAMP AS time_period,
        LOCALTIMESTAMP AS time_period_utc
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
        uniqueness:
          unique_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table.`target_column`) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table."target_column") / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT "target_column") / COUNT("target_column")
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

### **daily checkpoint unique percent**  
  
**Check description**  
Verifies that the percentage of unique values in a column does not exceed the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_unique_percent|checkpoint|daily|[unique_percent](../../../sensors/column/#unique-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
          uniqueness:
            daily_checkpoint_unique_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table.`target_column`) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table."target_column") / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT "target_column") / COUNT("target_column")
        END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
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
          uniqueness:
            daily_checkpoint_unique_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table.`target_column`) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table."target_column") / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT "target_column") / COUNT("target_column")
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

### **monthly checkpoint unique percent**  
  
**Check description**  
Verifies that the percentage of unique values in a column does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_unique_percent|checkpoint|monthly|[unique_percent](../../../sensors/column/#unique-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
          uniqueness:
            monthly_checkpoint_unique_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table.`target_column`) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table."target_column") / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT "target_column") / COUNT("target_column")
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
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
          uniqueness:
            monthly_checkpoint_unique_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table.`target_column`) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table."target_column") / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT "target_column") / COUNT("target_column")
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

### **daily partition unique percent**  
  
**Check description**  
Verifies that the percentage of unique values in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_unique_percent|partitioned|daily|[unique_percent](../../../sensors/column/#unique-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
          uniqueness:
            daily_partition_unique_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table.`target_column`) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table."target_column") / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT "target_column") / COUNT("target_column")
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
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
          uniqueness:
            daily_partition_unique_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table.`target_column`) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table."target_column") / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT "target_column") / COUNT("target_column")
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

### **monthly partition unique percent**  
  
**Check description**  
Verifies that the percentage of unique values in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_unique_percent|partitioned|monthly|[unique_percent](../../../sensors/column/#unique-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
          uniqueness:
            monthly_partition_unique_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table.`target_column`) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table."target_column") / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT "target_column") / COUNT("target_column")
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
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
          uniqueness:
            monthly_partition_unique_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table')}}) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }}) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT(analyzed_table.`target_column`) = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table.`target_column`) / COUNT(analyzed_table.`target_column`)
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
            WHEN COUNT(analyzed_table."target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT analyzed_table."target_column") / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0
                THEN 100.0
            ELSE 100.0 * COUNT(DISTINCT "target_column") / COUNT("target_column")
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


## **duplicate count** checks  

**Description**  
Column level check that ensures that there are no more than a maximum number of nulls in a monitored column.

___

### **duplicate count**  
  
**Check description**  
Verifies that the number of duplicate values in a column does not exceed the maximum accepted count.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|duplicate_count|adhoc| |[duplicate_count](../../../sensors/column/#duplicate-count)|[max_count](../../../rules/comparison/#max-count)|
  
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
        uniqueness:
          duplicate_count:
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
            WHEN COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}})) = 1 THEN COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            ELSE (COUNT({{ lib.render_target_column('analyzed_table')}}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}}))) AS actual_value
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
        COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT(analyzed_table.`target_column`))
        AS actual_value,
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
            WHEN COUNT(DISTINCT(analyzed_table."target_column")) = 1 THEN COUNT(analyzed_table."target_column") AS actual_value
            ELSE (COUNT(analyzed_table."target_column") - COUNT(DISTINCT(analyzed_table."target_column"))) AS actual_value
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
        COUNT("target_column") - COUNT(DISTINCT("target_column"))
        AS actual_value,
        LOCALTIMESTAMP AS time_period,
        LOCALTIMESTAMP AS time_period_utc
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
        uniqueness:
          duplicate_count:
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
            WHEN COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}})) = 1 THEN COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            ELSE (COUNT({{ lib.render_target_column('analyzed_table')}}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}}))) AS actual_value
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
        COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT(analyzed_table.`target_column`))
        AS actual_value,
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
            WHEN COUNT(DISTINCT(analyzed_table."target_column")) = 1 THEN COUNT(analyzed_table."target_column") AS actual_value
            ELSE (COUNT(analyzed_table."target_column") - COUNT(DISTINCT(analyzed_table."target_column"))) AS actual_value
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
        COUNT("target_column") - COUNT(DISTINCT("target_column"))
        AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        LOCALTIMESTAMP AS time_period,
        LOCALTIMESTAMP AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily checkpoint duplicate count**  
  
**Check description**  
Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_duplicate_count|checkpoint|daily|[duplicate_count](../../../sensors/column/#duplicate-count)|[max_count](../../../rules/comparison/#max-count)|
  
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
          uniqueness:
            daily_checkpoint_duplicate_count:
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
            WHEN COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}})) = 1 THEN COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            ELSE (COUNT({{ lib.render_target_column('analyzed_table')}}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}}))) AS actual_value
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
        COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT(analyzed_table.`target_column`))
        AS actual_value,
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
            WHEN COUNT(DISTINCT(analyzed_table."target_column")) = 1 THEN COUNT(analyzed_table."target_column") AS actual_value
            ELSE (COUNT(analyzed_table."target_column") - COUNT(DISTINCT(analyzed_table."target_column"))) AS actual_value
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
        COUNT("target_column") - COUNT(DISTINCT("target_column"))
        AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
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
          uniqueness:
            daily_checkpoint_duplicate_count:
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
            WHEN COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}})) = 1 THEN COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            ELSE (COUNT({{ lib.render_target_column('analyzed_table')}}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}}))) AS actual_value
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
        COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT(analyzed_table.`target_column`))
        AS actual_value,
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
            WHEN COUNT(DISTINCT(analyzed_table."target_column")) = 1 THEN COUNT(analyzed_table."target_column") AS actual_value
            ELSE (COUNT(analyzed_table."target_column") - COUNT(DISTINCT(analyzed_table."target_column"))) AS actual_value
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
        COUNT("target_column") - COUNT(DISTINCT("target_column"))
        AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly checkpoint duplicate count**  
  
**Check description**  
Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_duplicate_count|checkpoint|monthly|[duplicate_count](../../../sensors/column/#duplicate-count)|[max_count](../../../rules/comparison/#max-count)|
  
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
          uniqueness:
            monthly_checkpoint_duplicate_count:
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
            WHEN COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}})) = 1 THEN COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            ELSE (COUNT({{ lib.render_target_column('analyzed_table')}}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}}))) AS actual_value
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
        COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT(analyzed_table.`target_column`))
        AS actual_value,
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
            WHEN COUNT(DISTINCT(analyzed_table."target_column")) = 1 THEN COUNT(analyzed_table."target_column") AS actual_value
            ELSE (COUNT(analyzed_table."target_column") - COUNT(DISTINCT(analyzed_table."target_column"))) AS actual_value
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
        COUNT("target_column") - COUNT(DISTINCT("target_column"))
        AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
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
          uniqueness:
            monthly_checkpoint_duplicate_count:
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
            WHEN COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}})) = 1 THEN COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            ELSE (COUNT({{ lib.render_target_column('analyzed_table')}}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}}))) AS actual_value
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
        COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT(analyzed_table.`target_column`))
        AS actual_value,
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
            WHEN COUNT(DISTINCT(analyzed_table."target_column")) = 1 THEN COUNT(analyzed_table."target_column") AS actual_value
            ELSE (COUNT(analyzed_table."target_column") - COUNT(DISTINCT(analyzed_table."target_column"))) AS actual_value
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
        COUNT("target_column") - COUNT(DISTINCT("target_column"))
        AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily partition duplicate count**  
  
**Check description**  
Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_duplicate_count|partitioned|daily|[duplicate_count](../../../sensors/column/#duplicate-count)|[max_count](../../../rules/comparison/#max-count)|
  
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
          uniqueness:
            daily_partition_duplicate_count:
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
            WHEN COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}})) = 1 THEN COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            ELSE (COUNT({{ lib.render_target_column('analyzed_table')}}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}}))) AS actual_value
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
        COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT(analyzed_table.`target_column`))
        AS actual_value,
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
            WHEN COUNT(DISTINCT(analyzed_table."target_column")) = 1 THEN COUNT(analyzed_table."target_column") AS actual_value
            ELSE (COUNT(analyzed_table."target_column") - COUNT(DISTINCT(analyzed_table."target_column"))) AS actual_value
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
        COUNT("target_column") - COUNT(DISTINCT("target_column"))
        AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
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
          uniqueness:
            daily_partition_duplicate_count:
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
            WHEN COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}})) = 1 THEN COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            ELSE (COUNT({{ lib.render_target_column('analyzed_table')}}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}}))) AS actual_value
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
        COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT(analyzed_table.`target_column`))
        AS actual_value,
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
            WHEN COUNT(DISTINCT(analyzed_table."target_column")) = 1 THEN COUNT(analyzed_table."target_column") AS actual_value
            ELSE (COUNT(analyzed_table."target_column") - COUNT(DISTINCT(analyzed_table."target_column"))) AS actual_value
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
        COUNT("target_column") - COUNT(DISTINCT("target_column"))
        AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly partition duplicate count**  
  
**Check description**  
Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_duplicate_count|partitioned|monthly|[duplicate_count](../../../sensors/column/#duplicate-count)|[max_count](../../../rules/comparison/#max-count)|
  
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
          uniqueness:
            monthly_partition_duplicate_count:
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
            WHEN COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}})) = 1 THEN COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            ELSE (COUNT({{ lib.render_target_column('analyzed_table')}}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}}))) AS actual_value
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
        COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT(analyzed_table.`target_column`))
        AS actual_value,
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
            WHEN COUNT(DISTINCT(analyzed_table."target_column")) = 1 THEN COUNT(analyzed_table."target_column") AS actual_value
            ELSE (COUNT(analyzed_table."target_column") - COUNT(DISTINCT(analyzed_table."target_column"))) AS actual_value
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
        COUNT("target_column") - COUNT(DISTINCT("target_column"))
        AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
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
          uniqueness:
            monthly_partition_duplicate_count:
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
            WHEN COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}})) = 1 THEN COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            ELSE (COUNT({{ lib.render_target_column('analyzed_table')}}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table')}}))) AS actual_value
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
        COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT({{ lib.render_target_column('analyzed_table') }}))
        AS actual_value
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
        COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT(analyzed_table.`target_column`))
        AS actual_value,
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
            WHEN COUNT(DISTINCT(analyzed_table."target_column")) = 1 THEN COUNT(analyzed_table."target_column") AS actual_value
            ELSE (COUNT(analyzed_table."target_column") - COUNT(DISTINCT(analyzed_table."target_column"))) AS actual_value
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
        COUNT("target_column") - COUNT(DISTINCT("target_column"))
        AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___


## **duplicate percent** checks  

**Description**  
Column level check that ensures that there are no more than a maximum number of nulls in a monitored column.

___

### **duplicate percent**  
  
**Check description**  
Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|duplicate_percent|adhoc| |[duplicate_percent](../../../sensors/column/#duplicate-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
        uniqueness:
          duplicate_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT analyzed_table.`target_column`)
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
            ELSE 100.0 * (
                COUNT(analyzed_table."target_column") - COUNT(DISTINCT analyzed_table."target_column")
            ) / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT("target_column") - COUNT(DISTINCT "target_column")
            ) / COUNT("target_column")
        END AS actual_value,
        LOCALTIMESTAMP AS time_period,
        LOCALTIMESTAMP AS time_period_utc
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
        uniqueness:
          duplicate_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT analyzed_table.`target_column`)
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
            ELSE 100.0 * (
                COUNT(analyzed_table."target_column") - COUNT(DISTINCT analyzed_table."target_column")
            ) / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT("target_column") - COUNT(DISTINCT "target_column")
            ) / COUNT("target_column")
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

### **daily checkpoint duplicate percent**  
  
**Check description**  
Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_duplicate_percent|checkpoint|daily|[duplicate_percent](../../../sensors/column/#duplicate-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
          uniqueness:
            daily_checkpoint_duplicate_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT analyzed_table.`target_column`)
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
            ELSE 100.0 * (
                COUNT(analyzed_table."target_column") - COUNT(DISTINCT analyzed_table."target_column")
            ) / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT("target_column") - COUNT(DISTINCT "target_column")
            ) / COUNT("target_column")
        END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
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
          uniqueness:
            daily_checkpoint_duplicate_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT analyzed_table.`target_column`)
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
            ELSE 100.0 * (
                COUNT(analyzed_table."target_column") - COUNT(DISTINCT analyzed_table."target_column")
            ) / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT("target_column") - COUNT(DISTINCT "target_column")
            ) / COUNT("target_column")
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

### **monthly checkpoint duplicate percent**  
  
**Check description**  
Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_duplicate_percent|checkpoint|monthly|[duplicate_percent](../../../sensors/column/#duplicate-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
          uniqueness:
            monthly_checkpoint_duplicate_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT analyzed_table.`target_column`)
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
            ELSE 100.0 * (
                COUNT(analyzed_table."target_column") - COUNT(DISTINCT analyzed_table."target_column")
            ) / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT("target_column") - COUNT(DISTINCT "target_column")
            ) / COUNT("target_column")
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(LOCALTIMESTAMP AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
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
          uniqueness:
            monthly_checkpoint_duplicate_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT analyzed_table.`target_column`)
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
            ELSE 100.0 * (
                COUNT(analyzed_table."target_column") - COUNT(DISTINCT analyzed_table."target_column")
            ) / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT("target_column") - COUNT(DISTINCT "target_column")
            ) / COUNT("target_column")
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

### **daily partition duplicate percent**  
  
**Check description**  
Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_duplicate_percent|partitioned|daily|[duplicate_percent](../../../sensors/column/#duplicate-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
          uniqueness:
            daily_partition_duplicate_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT analyzed_table.`target_column`)
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
            ELSE 100.0 * (
                COUNT(analyzed_table."target_column") - COUNT(DISTINCT analyzed_table."target_column")
            ) / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT("target_column") - COUNT(DISTINCT "target_column")
            ) / COUNT("target_column")
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP AS time_period_utc
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
          uniqueness:
            daily_partition_duplicate_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT analyzed_table.`target_column`)
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
            ELSE 100.0 * (
                COUNT(analyzed_table."target_column") - COUNT(DISTINCT analyzed_table."target_column")
            ) / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT("target_column") - COUNT(DISTINCT "target_column")
            ) / COUNT("target_column")
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

### **monthly partition duplicate percent**  
  
**Check description**  
Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_duplicate_percent|partitioned|monthly|[duplicate_percent](../../../sensors/column/#duplicate-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
          uniqueness:
            monthly_partition_duplicate_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT analyzed_table.`target_column`)
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
            ELSE 100.0 * (
                COUNT(analyzed_table."target_column") - COUNT(DISTINCT analyzed_table."target_column")
            ) / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT("target_column") - COUNT(DISTINCT "target_column")
            ) / COUNT("target_column")
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        DATE_TRUNC('month', (CAST(analyzed_table."col_event_timestamp" AS date) || ' 00:00:00'):: TIMESTAMP) AS time_period_utc
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
          uniqueness:
            monthly_partition_duplicate_percent:
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT({{ lib.render_target_column('analyzed_table') }}) - COUNT(DISTINCT {{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * (
                COUNT(analyzed_table.`target_column`) - COUNT(DISTINCT analyzed_table.`target_column`)
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
            ELSE 100.0 * (
                COUNT(analyzed_table."target_column") - COUNT(DISTINCT analyzed_table."target_column")
            ) / COUNT(analyzed_table."target_column")
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
            WHEN COUNT("target_column") = 0 THEN 100.0
            ELSE 100.0 * (
                COUNT("target_column") - COUNT(DISTINCT "target_column")
            ) / COUNT("target_column")
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

