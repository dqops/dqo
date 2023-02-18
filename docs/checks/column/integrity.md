# integrity


___

## **foreign key not match count** checks  

**Description**  
Column level check that ensures that there are no more than a maximum number of values not matching values in another table column.

___

### **foreign key not match count**  
  
**Check description**  
Verifies that the number of values in a column that does not match values in another table column does not exceed the set count.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|foreign_key_not_match_count|adhoc| |[foreign_key_not_match_count](../../../sensors/column/#foreign-key-not-match-count)|[max_count](../../../rules/comparison/#max-count)|
  
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
        integrity:
          foreign_key_not_match_count:
            parameters:
              foreign_table: dim_customer
              foreign_column: customer_id
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
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    {%- macro render_foreign_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.foreign_column) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    LEFT OUTER JOIN `your-google-project-id`.`target_schema`.`dim_customer` AS foreign_table
    ON analyzed_table.`target_column` = foreign_table.`customer_id`
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    ```
=== "postgresql"
      
    ```
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
        integrity:
          foreign_key_not_match_count:
            parameters:
              foreign_table: dim_customer
              foreign_column: customer_id
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
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    {%- macro render_foreign_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.foreign_column) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table.`country` AS stream_level_1,
        analyzed_table.`state` AS stream_level_2,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    LEFT OUTER JOIN `your-google-project-id`.`target_schema`.`dim_customer` AS foreign_table
    ON analyzed_table.`target_column` = foreign_table.`customer_id`
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    ```
=== "postgresql"
      
    ```
    ```






___

### **daily checkpoint foreign key not match count**  
  
**Check description**  
Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_foreign_key_not_match_count|checkpoint|daily|[foreign_key_not_match_count](../../../sensors/column/#foreign-key-not-match-count)|[max_count](../../../rules/comparison/#max-count)|
  
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
          integrity:
            daily_checkpoint_foreign_key_not_match_count:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
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
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    {%- macro render_foreign_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.foreign_column) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    LEFT OUTER JOIN `your-google-project-id`.`target_schema`.`dim_customer` AS foreign_table
    ON analyzed_table.`target_column` = foreign_table.`customer_id`
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    ```
=== "postgresql"
      
    ```
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
          integrity:
            daily_checkpoint_foreign_key_not_match_count:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
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
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    {%- macro render_foreign_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.foreign_column) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table.`country` AS stream_level_1,
        analyzed_table.`state` AS stream_level_2,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    LEFT OUTER JOIN `your-google-project-id`.`target_schema`.`dim_customer` AS foreign_table
    ON analyzed_table.`target_column` = foreign_table.`customer_id`
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    ```
=== "postgresql"
      
    ```
    ```






___

### **monthly checkpoint foreign key not match count**  
  
**Check description**  
Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_foreign_key_not_match_count|checkpoint|monthly|[foreign_key_not_match_count](../../../sensors/column/#foreign-key-not-match-count)|[max_count](../../../rules/comparison/#max-count)|
  
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
          integrity:
            monthly_checkpoint_foreign_key_not_match_count:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
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
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    {%- macro render_foreign_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.foreign_column) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    LEFT OUTER JOIN `your-google-project-id`.`target_schema`.`dim_customer` AS foreign_table
    ON analyzed_table.`target_column` = foreign_table.`customer_id`
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    ```
=== "postgresql"
      
    ```
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
          integrity:
            monthly_checkpoint_foreign_key_not_match_count:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
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
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    {%- macro render_foreign_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.foreign_column) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table.`country` AS stream_level_1,
        analyzed_table.`state` AS stream_level_2,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    LEFT OUTER JOIN `your-google-project-id`.`target_schema`.`dim_customer` AS foreign_table
    ON analyzed_table.`target_column` = foreign_table.`customer_id`
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    ```
=== "postgresql"
      
    ```
    ```






___

### **daily partition foreign key not match count**  
  
**Check description**  
Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_foreign_key_not_match_count|partitioned|daily|[foreign_key_not_match_count](../../../sensors/column/#foreign-key-not-match-count)|[max_count](../../../rules/comparison/#max-count)|
  
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
          integrity:
            daily_partition_foreign_key_not_match_count:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
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
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    {%- macro render_foreign_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.foreign_column) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    LEFT OUTER JOIN `your-google-project-id`.`target_schema`.`dim_customer` AS foreign_table
    ON analyzed_table.`target_column` = foreign_table.`customer_id`
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    ```
=== "postgresql"
      
    ```
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
          integrity:
            daily_partition_foreign_key_not_match_count:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
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
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    {%- macro render_foreign_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.foreign_column) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table.`country` AS stream_level_1,
        analyzed_table.`state` AS stream_level_2,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    LEFT OUTER JOIN `your-google-project-id`.`target_schema`.`dim_customer` AS foreign_table
    ON analyzed_table.`target_column` = foreign_table.`customer_id`
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    ```
=== "postgresql"
      
    ```
    ```






___

### **monthly partition foreign key not match count**  
  
**Check description**  
Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_foreign_key_not_match_count|partitioned|monthly|[foreign_key_not_match_count](../../../sensors/column/#foreign-key-not-match-count)|[max_count](../../../rules/comparison/#max-count)|
  
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
          integrity:
            monthly_partition_foreign_key_not_match_count:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
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
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    {%- macro render_foreign_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.foreign_column) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    LEFT OUTER JOIN `your-google-project-id`.`target_schema`.`dim_customer` AS foreign_table
    ON analyzed_table.`target_column` = foreign_table.`customer_id`
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    ```
=== "postgresql"
      
    ```
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
          integrity:
            monthly_partition_foreign_key_not_match_count:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
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
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    {%- macro render_foreign_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.foreign_column) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro quote_identifier(name) -%}
        {{ dialect_settings.quote_begin }}{{ name | replace(dialect_settings.quote_end, dialect_settings.quote_escape) }}{{ dialect_settings.quote_end }}
    {%- endmacro %}
    
    {%- macro render_joined_table(joined_tab) -%}
        {{ quote_identifier(connection.bigquery.source_project_id) }}.{{ quote_identifier(table.target.schema_name) }}.{{ quote_identifier(joined_tab) }}
    {%- endmacro %}
    
    {%- macro render_joined_column(table_alias_prefix = '') -%}
        {{ quote_identifier(parameters.joined_col) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ render_foreign_column(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ render_foreign_column(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table.`country` AS stream_level_1,
        analyzed_table.`state` AS stream_level_2,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table
    LEFT OUTER JOIN `your-google-project-id`.`target_schema`.`dim_customer` AS foreign_table
    ON analyzed_table.`target_column` = foreign_table.`customer_id`
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "snowflake"
      
    ```
    ```
=== "postgresql"
      
    ```
    ```






___

