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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=foreign_key_not_match_count
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CURRENT_TIMESTAMP() AS time_period,
        TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily checkpoint foreign key not match count**  
  
**Check description**  
Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_foreign_key_not_match_count|checkpoint|daily|[foreign_key_not_match_count](../../../sensors/column/#foreign-key-not-match-count)|[max_count](../../../rules/comparison/#max-count)|
  
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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_checkpoint_foreign_key_not_match_count
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly checkpoint foreign key not match count**  
  
**Check description**  
Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_foreign_key_not_match_count|checkpoint|monthly|[foreign_key_not_match_count](../../../sensors/column/#foreign-key-not-match-count)|[max_count](../../../rules/comparison/#max-count)|
  
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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_checkpoint_foreign_key_not_match_count
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily partition foreign key not match count**  
  
**Check description**  
Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_foreign_key_not_match_count|partitioned|daily|[foreign_key_not_match_count](../../../sensors/column/#foreign-key-not-match-count)|[max_count](../../../rules/comparison/#max-count)|
  
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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_foreign_key_not_match_count
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly partition foreign key not match count**  
  
**Check description**  
Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_foreign_key_not_match_count|partitioned|monthly|[foreign_key_not_match_count](../../../sensors/column/#foreign-key-not-match-count)|[max_count](../../../rules/comparison/#max-count)|
  
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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_foreign_key_not_match_count
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___


## **foreign key match percent** checks  

**Description**  
Column level check that ensures that there are no more than a minimum percentage of values matching values in another table column.

___

### **foreign key match percent**  
  
**Check description**  
Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|foreign_key_match_percent|adhoc| |[foreign_key_match_percent](../../../sensors/column/#foreign-key-match-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=foreign_key_match_percent
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
        integrity:
          foreign_key_match_percent:
            parameters:
              foreign_table: dim_customer
              foreign_column: customer_id
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
        integrity:
          foreign_key_match_percent:
            parameters:
              foreign_table: dim_customer
              foreign_column: customer_id
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.redshift.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.postgresql.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
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
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
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
        integrity:
          foreign_key_match_percent:
            parameters:
              foreign_table: dim_customer
              foreign_column: customer_id
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.redshift.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.postgresql.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
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
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CURRENT_TIMESTAMP() AS time_period,
        TO_TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily checkpoint foreign key match percent**  
  
**Check description**  
Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_foreign_key_match_percent|checkpoint|daily|[foreign_key_match_percent](../../../sensors/column/#foreign-key-match-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_checkpoint_foreign_key_match_percent
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
          integrity:
            daily_checkpoint_foreign_key_match_percent:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.redshift.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.postgresql.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
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
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
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
          integrity:
            daily_checkpoint_foreign_key_match_percent:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.redshift.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.postgresql.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
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
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(CURRENT_TIMESTAMP() AS date) AS time_period,
        TO_TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly checkpoint foreign key match percent**  
  
**Check description**  
Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_foreign_key_match_percent|checkpoint|monthly|[foreign_key_match_percent](../../../sensors/column/#foreign-key-match-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_checkpoint_foreign_key_match_percent
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
          integrity:
            monthly_checkpoint_foreign_key_match_percent:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.redshift.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.postgresql.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
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
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
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
          integrity:
            monthly_checkpoint_foreign_key_match_percent:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.redshift.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.postgresql.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
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
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **daily partition foreign key match percent**  
  
**Check description**  
Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_foreign_key_match_percent|partitioned|daily|[foreign_key_match_percent](../../../sensors/column/#foreign-key-match-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_foreign_key_match_percent
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
          integrity:
            daily_partition_foreign_key_match_percent:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
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
          integrity:
            daily_partition_foreign_key_match_percent:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.redshift.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.postgresql.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
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
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
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
          integrity:
            daily_partition_foreign_key_match_percent:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.redshift.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.postgresql.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
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
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

### **monthly partition foreign key match percent**  
  
**Check description**  
Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_foreign_key_match_percent|partitioned|monthly|[foreign_key_match_percent](../../../sensors/column/#foreign-key-match-percent)|[max_percent](../../../rules/comparison/#max-percent)|
  
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
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_foreign_key_match_percent
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
          integrity:
            monthly_partition_foreign_key_match_percent:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
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
          integrity:
            monthly_partition_foreign_key_match_percent:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.redshift.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.postgresql.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
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
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
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
          integrity:
            monthly_partition_foreign_key_match_percent:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
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
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.bigquery.source_project_id) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.snowflake.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.redshift.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
        {{ lib.quote_identifier(connection.postgresql.database) }}.{{ lib.quote_identifier(table.target.schema_name) }}.{{ lib.quote_identifier(foreign_table) }}
    {%- endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
                WHEN foreign_table.`customer_id` IS NULL AND analyzed_table.`target_column` IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
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
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database"."target_schema"."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "redshift"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM ""."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN ""."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```
=== "postgresql"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND "target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        analyzed_table."country" AS stream_level_1,
        analyzed_table."state" AS stream_level_2,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."target_schema"."target_table" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database"."target_schema"."dim_customer" AS foreign_table
    ON "target_column" = foreign_table."customer_id"
    GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
    ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
    ```






___

