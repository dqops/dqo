**percentile in range** checks  

**Description**  
Column level check that ensures that the percentile of values in a monitored column is in a set range.

___

## **percentile in range**  
  
**Check description**  
Verifies that the percentile of all values in a column is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|percentile_in_range|adhoc| |[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=percentile_in_range
```
**Check structure (Yaml)**
```yaml
      checks:
        numeric:
          percentile_in_range:
            error:
              from: 10.0
              to: 20.5
            warning:
              from: 10.0
              to: 20.5
            fatal:
              from: 10.0
              to: 20.5
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
        numeric:
          percentile_in_range:
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), ) OVER (PARTITION BY
                TIMESTAMP(CURRENT_TIMESTAMP())
            )
        AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT() WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
            numeric:
              percentile_in_range:
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), ) OVER (PARTITION BY
                    TIMESTAMP(CURRENT_TIMESTAMP())
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT() WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **percentile50 in range**  
  
**Check description**  
Verifies that the percentile 50 of all values in a column is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|percentile50_in_range|adhoc| |[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=percentile50_in_range
```
**Check structure (Yaml)**
```yaml
      checks:
        numeric:
          percentile50_in_range:
            parameters:
              percentile_value: 0.5
            error:
              from: 10.0
              to: 20.5
            warning:
              from: 10.0
              to: 20.5
            fatal:
              from: 10.0
              to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-27"
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
        numeric:
          percentile50_in_range:
            parameters:
              percentile_value: 0.5
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.5) OVER (PARTITION BY
                TIMESTAMP(CURRENT_TIMESTAMP())
            )
        AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
            numeric:
              percentile50_in_range:
                parameters:
                  percentile_value: 0.5
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.5) OVER (PARTITION BY
                    TIMESTAMP(CURRENT_TIMESTAMP())
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **percentile10 in range**  
  
**Check description**  
Verifies that the percentile 10 of all values in a column is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|percentile10_in_range|adhoc| |[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=percentile10_in_range
```
**Check structure (Yaml)**
```yaml
      checks:
        numeric:
          percentile10_in_range:
            parameters:
              percentile_value: 0.1
            error:
              from: 10.0
              to: 20.5
            warning:
              from: 10.0
              to: 20.5
            fatal:
              from: 10.0
              to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-27"
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
        numeric:
          percentile10_in_range:
            parameters:
              percentile_value: 0.1
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.1) OVER (PARTITION BY
                TIMESTAMP(CURRENT_TIMESTAMP())
            )
        AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
            numeric:
              percentile10_in_range:
                parameters:
                  percentile_value: 0.1
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.1) OVER (PARTITION BY
                    TIMESTAMP(CURRENT_TIMESTAMP())
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **percentile25 in range**  
  
**Check description**  
Verifies that the percentile 25 of all values in a column is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|percentile25_in_range|adhoc| |[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=percentile25_in_range
```
**Check structure (Yaml)**
```yaml
      checks:
        numeric:
          percentile25_in_range:
            parameters:
              percentile_value: 0.25
            error:
              from: 10.0
              to: 20.5
            warning:
              from: 10.0
              to: 20.5
            fatal:
              from: 10.0
              to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-27"
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
        numeric:
          percentile25_in_range:
            parameters:
              percentile_value: 0.25
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.25) OVER (PARTITION BY
                TIMESTAMP(CURRENT_TIMESTAMP())
            )
        AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
            numeric:
              percentile25_in_range:
                parameters:
                  percentile_value: 0.25
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.25) OVER (PARTITION BY
                    TIMESTAMP(CURRENT_TIMESTAMP())
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **percentile75 in range**  
  
**Check description**  
Verifies that the percentile 75 of all values in a column is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|percentile75_in_range|adhoc| |[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=percentile75_in_range
```
**Check structure (Yaml)**
```yaml
      checks:
        numeric:
          percentile75_in_range:
            parameters:
              percentile_value: 0.75
            error:
              from: 10.0
              to: 20.5
            warning:
              from: 10.0
              to: 20.5
            fatal:
              from: 10.0
              to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-27"
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
        numeric:
          percentile75_in_range:
            parameters:
              percentile_value: 0.75
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.75) OVER (PARTITION BY
                TIMESTAMP(CURRENT_TIMESTAMP())
            )
        AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
            numeric:
              percentile75_in_range:
                parameters:
                  percentile_value: 0.75
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.75) OVER (PARTITION BY
                    TIMESTAMP(CURRENT_TIMESTAMP())
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **percentile90 in range**  
  
**Check description**  
Verifies that the percentile 90 of all values in a column is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|percentile90_in_range|adhoc| |[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=percentile90_in_range
```
**Check structure (Yaml)**
```yaml
      checks:
        numeric:
          percentile90_in_range:
            parameters:
              percentile_value: 0.9
            error:
              from: 10.0
              to: 20.5
            warning:
              from: 10.0
              to: 20.5
            fatal:
              from: 10.0
              to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-27"
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
        numeric:
          percentile90_in_range:
            parameters:
              percentile_value: 0.9
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.9) OVER (PARTITION BY
                TIMESTAMP(CURRENT_TIMESTAMP())
            )
        AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
            numeric:
              percentile90_in_range:
                parameters:
                  percentile_value: 0.9
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.9) OVER (PARTITION BY
                    TIMESTAMP(CURRENT_TIMESTAMP())
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily checkpoint percentile in range**  
  
**Check description**  
Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_percentile_in_range|checkpoint|daily|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_checkpoint_percentile_in_range
```
**Check structure (Yaml)**
```yaml
      checkpoints:
        daily:
          numeric:
            daily_checkpoint_percentile_in_range:
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
      checkpoints:
        daily:
          numeric:
            daily_checkpoint_percentile_in_range:
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), ) OVER (PARTITION BY
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
            )
        AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT() WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                daily_checkpoint_percentile_in_range:
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), ) OVER (PARTITION BY
                    TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT() WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily checkpoint percentile50 in range**  
  
**Check description**  
Verifies that the percentile 50 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_percentile50_in_range|checkpoint|daily|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_checkpoint_percentile50_in_range
```
**Check structure (Yaml)**
```yaml
      checkpoints:
        daily:
          numeric:
            daily_checkpoint_percentile50_in_range:
              parameters:
                percentile_value: 0.5
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            daily_checkpoint_percentile50_in_range:
              parameters:
                percentile_value: 0.5
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.5) OVER (PARTITION BY
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
            )
        AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                daily_checkpoint_percentile50_in_range:
                  parameters:
                    percentile_value: 0.5
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.5) OVER (PARTITION BY
                    TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily checkpoint percentile10 in range**  
  
**Check description**  
Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_percentile10_in_range|checkpoint|daily|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_checkpoint_percentile10_in_range
```
**Check structure (Yaml)**
```yaml
      checkpoints:
        daily:
          numeric:
            daily_checkpoint_percentile10_in_range:
              parameters:
                percentile_value: 0.1
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            daily_checkpoint_percentile10_in_range:
              parameters:
                percentile_value: 0.1
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.1) OVER (PARTITION BY
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
            )
        AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                daily_checkpoint_percentile10_in_range:
                  parameters:
                    percentile_value: 0.1
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.1) OVER (PARTITION BY
                    TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily checkpoint percentile25 in range**  
  
**Check description**  
Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_percentile25_in_range|checkpoint|daily|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_checkpoint_percentile25_in_range
```
**Check structure (Yaml)**
```yaml
      checkpoints:
        daily:
          numeric:
            daily_checkpoint_percentile25_in_range:
              parameters:
                percentile_value: 0.25
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            daily_checkpoint_percentile25_in_range:
              parameters:
                percentile_value: 0.25
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.25) OVER (PARTITION BY
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
            )
        AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                daily_checkpoint_percentile25_in_range:
                  parameters:
                    percentile_value: 0.25
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.25) OVER (PARTITION BY
                    TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily checkpoint percentile75 in range**  
  
**Check description**  
Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_percentile75_in_range|checkpoint|daily|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_checkpoint_percentile75_in_range
```
**Check structure (Yaml)**
```yaml
      checkpoints:
        daily:
          numeric:
            daily_checkpoint_percentile75_in_range:
              parameters:
                percentile_value: 0.75
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            daily_checkpoint_percentile75_in_range:
              parameters:
                percentile_value: 0.75
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.75) OVER (PARTITION BY
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
            )
        AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                daily_checkpoint_percentile75_in_range:
                  parameters:
                    percentile_value: 0.75
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.75) OVER (PARTITION BY
                    TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily checkpoint percentile90 in range**  
  
**Check description**  
Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_checkpoint_percentile90_in_range|checkpoint|daily|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_checkpoint_percentile90_in_range
```
**Check structure (Yaml)**
```yaml
      checkpoints:
        daily:
          numeric:
            daily_checkpoint_percentile90_in_range:
              parameters:
                percentile_value: 0.9
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            daily_checkpoint_percentile90_in_range:
              parameters:
                percentile_value: 0.9
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.9) OVER (PARTITION BY
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
            )
        AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                daily_checkpoint_percentile90_in_range:
                  parameters:
                    percentile_value: 0.9
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.9) OVER (PARTITION BY
                    TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly checkpoint percentile in range**  
  
**Check description**  
Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_percentile_in_range|checkpoint|monthly|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_checkpoint_percentile_in_range
```
**Check structure (Yaml)**
```yaml
      checkpoints:
        monthly:
          numeric:
            monthly_checkpoint_percentile_in_range:
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
      checkpoints:
        monthly:
          numeric:
            monthly_checkpoint_percentile_in_range:
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), ) OVER (PARTITION BY
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
            )
        AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT() WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                monthly_checkpoint_percentile_in_range:
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), ) OVER (PARTITION BY
                    TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT() WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly checkpoint percentile50 in range**  
  
**Check description**  
Verifies that the percentile 50 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_percentile50_in_range|checkpoint|monthly|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_checkpoint_percentile50_in_range
```
**Check structure (Yaml)**
```yaml
      checkpoints:
        monthly:
          numeric:
            monthly_checkpoint_percentile50_in_range:
              parameters:
                percentile_value: 0.5
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            monthly_checkpoint_percentile50_in_range:
              parameters:
                percentile_value: 0.5
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.5) OVER (PARTITION BY
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
            )
        AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                monthly_checkpoint_percentile50_in_range:
                  parameters:
                    percentile_value: 0.5
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.5) OVER (PARTITION BY
                    TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly checkpoint percentile10 in range**  
  
**Check description**  
Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_percentile10_in_range|checkpoint|monthly|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_checkpoint_percentile10_in_range
```
**Check structure (Yaml)**
```yaml
      checkpoints:
        monthly:
          numeric:
            monthly_checkpoint_percentile10_in_range:
              parameters:
                percentile_value: 0.1
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            monthly_checkpoint_percentile10_in_range:
              parameters:
                percentile_value: 0.1
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.1) OVER (PARTITION BY
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
            )
        AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                monthly_checkpoint_percentile10_in_range:
                  parameters:
                    percentile_value: 0.1
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.1) OVER (PARTITION BY
                    TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly checkpoint percentile25 in range**  
  
**Check description**  
Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_percentile25_in_range|checkpoint|monthly|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_checkpoint_percentile25_in_range
```
**Check structure (Yaml)**
```yaml
      checkpoints:
        monthly:
          numeric:
            monthly_checkpoint_percentile25_in_range:
              parameters:
                percentile_value: 0.25
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            monthly_checkpoint_percentile25_in_range:
              parameters:
                percentile_value: 0.25
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.25) OVER (PARTITION BY
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
            )
        AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                monthly_checkpoint_percentile25_in_range:
                  parameters:
                    percentile_value: 0.25
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.25) OVER (PARTITION BY
                    TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly checkpoint percentile75 in range**  
  
**Check description**  
Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_percentile75_in_range|checkpoint|monthly|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_checkpoint_percentile75_in_range
```
**Check structure (Yaml)**
```yaml
      checkpoints:
        monthly:
          numeric:
            monthly_checkpoint_percentile75_in_range:
              parameters:
                percentile_value: 0.75
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            monthly_checkpoint_percentile75_in_range:
              parameters:
                percentile_value: 0.75
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.75) OVER (PARTITION BY
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
            )
        AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                monthly_checkpoint_percentile75_in_range:
                  parameters:
                    percentile_value: 0.75
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.75) OVER (PARTITION BY
                    TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly checkpoint percentile90 in range**  
  
**Check description**  
Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_checkpoint_percentile90_in_range|checkpoint|monthly|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_checkpoint_percentile90_in_range
```
**Check structure (Yaml)**
```yaml
      checkpoints:
        monthly:
          numeric:
            monthly_checkpoint_percentile90_in_range:
              parameters:
                percentile_value: 0.9
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            monthly_checkpoint_percentile90_in_range:
              parameters:
                percentile_value: 0.9
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.9) OVER (PARTITION BY
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
            )
        AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                monthly_checkpoint_percentile90_in_range:
                  parameters:
                    percentile_value: 0.9
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.9) OVER (PARTITION BY
                    TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily partition percentile in range**  
  
**Check description**  
Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_percentile_in_range|partitioned|daily|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_percentile_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          numeric:
            daily_partition_percentile_in_range:
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            daily_partition_percentile_in_range:
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), ) OVER (PARTITION BY
                TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
            )
        AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT() WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                daily_partition_percentile_in_range:
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), ) OVER (PARTITION BY
                    TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT() WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily partition percentile50 in range**  
  
**Check description**  
Verifies that the percentile 50 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_percentile50_in_range|partitioned|daily|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_percentile50_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          numeric:
            daily_partition_percentile50_in_range:
              parameters:
                percentile_value: 0.5
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            daily_partition_percentile50_in_range:
              parameters:
                percentile_value: 0.5
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.5) OVER (PARTITION BY
                TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
            )
        AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                daily_partition_percentile50_in_range:
                  parameters:
                    percentile_value: 0.5
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.5) OVER (PARTITION BY
                    TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily partition percentile10 in range**  
  
**Check description**  
Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_percentile10_in_range|partitioned|daily|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_percentile10_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          numeric:
            daily_partition_percentile10_in_range:
              parameters:
                percentile_value: 0.1
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            daily_partition_percentile10_in_range:
              parameters:
                percentile_value: 0.1
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.1) OVER (PARTITION BY
                TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
            )
        AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                daily_partition_percentile10_in_range:
                  parameters:
                    percentile_value: 0.1
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.1) OVER (PARTITION BY
                    TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily partition percentile25 in range**  
  
**Check description**  
Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_percentile25_in_range|partitioned|daily|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_percentile25_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          numeric:
            daily_partition_percentile25_in_range:
              parameters:
                percentile_value: 0.25
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            daily_partition_percentile25_in_range:
              parameters:
                percentile_value: 0.25
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.25) OVER (PARTITION BY
                TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
            )
        AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                daily_partition_percentile25_in_range:
                  parameters:
                    percentile_value: 0.25
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.25) OVER (PARTITION BY
                    TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily partition percentile75 in range**  
  
**Check description**  
Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_percentile75_in_range|partitioned|daily|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_percentile75_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          numeric:
            daily_partition_percentile75_in_range:
              parameters:
                percentile_value: 0.75
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            daily_partition_percentile75_in_range:
              parameters:
                percentile_value: 0.75
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.75) OVER (PARTITION BY
                TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
            )
        AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                daily_partition_percentile75_in_range:
                  parameters:
                    percentile_value: 0.75
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.75) OVER (PARTITION BY
                    TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily partition percentile90 in range**  
  
**Check description**  
Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_percentile90_in_range|partitioned|daily|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_percentile90_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          numeric:
            daily_partition_percentile90_in_range:
              parameters:
                percentile_value: 0.9
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            daily_partition_percentile90_in_range:
              parameters:
                percentile_value: 0.9
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.9) OVER (PARTITION BY
                TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
            )
        AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                daily_partition_percentile90_in_range:
                  parameters:
                    percentile_value: 0.9
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.9) OVER (PARTITION BY
                    TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly partition percentile in range**  
  
**Check description**  
Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_percentile_in_range|partitioned|monthly|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_percentile_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          numeric:
            monthly_partition_percentile_in_range:
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            monthly_partition_percentile_in_range:
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), ) OVER (PARTITION BY
                TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
            )
        AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT() WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                monthly_partition_percentile_in_range:
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), ) OVER (PARTITION BY
                    TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT() WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT() WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly partition percentile50 in range**  
  
**Check description**  
Verifies that the percentile 50 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_percentile50_in_range|partitioned|monthly|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_percentile50_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          numeric:
            monthly_partition_percentile50_in_range:
              parameters:
                percentile_value: 0.5
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            monthly_partition_percentile50_in_range:
              parameters:
                percentile_value: 0.5
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.5) OVER (PARTITION BY
                TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
            )
        AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                monthly_partition_percentile50_in_range:
                  parameters:
                    percentile_value: 0.5
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.5) OVER (PARTITION BY
                    TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly partition percentile10 in range**  
  
**Check description**  
Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_percentile10_in_range|partitioned|monthly|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_percentile10_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          numeric:
            monthly_partition_percentile10_in_range:
              parameters:
                percentile_value: 0.1
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            monthly_partition_percentile10_in_range:
              parameters:
                percentile_value: 0.1
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.1) OVER (PARTITION BY
                TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
            )
        AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                monthly_partition_percentile10_in_range:
                  parameters:
                    percentile_value: 0.1
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.1) OVER (PARTITION BY
                    TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly partition percentile25 in range**  
  
**Check description**  
Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_percentile25_in_range|partitioned|monthly|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_percentile25_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          numeric:
            monthly_partition_percentile25_in_range:
              parameters:
                percentile_value: 0.25
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            monthly_partition_percentile25_in_range:
              parameters:
                percentile_value: 0.25
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.25) OVER (PARTITION BY
                TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
            )
        AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                monthly_partition_percentile25_in_range:
                  parameters:
                    percentile_value: 0.25
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.25) OVER (PARTITION BY
                    TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly partition percentile75 in range**  
  
**Check description**  
Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_percentile75_in_range|partitioned|monthly|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_percentile75_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          numeric:
            monthly_partition_percentile75_in_range:
              parameters:
                percentile_value: 0.75
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            monthly_partition_percentile75_in_range:
              parameters:
                percentile_value: 0.75
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.75) OVER (PARTITION BY
                TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
            )
        AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                monthly_partition_percentile75_in_range:
                  parameters:
                    percentile_value: 0.75
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.75) OVER (PARTITION BY
                    TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly partition percentile90 in range**  
  
**Check description**  
Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_percentile90_in_range|partitioned|monthly|[percentile](../../../../reference/sensors/column/numeric%20column%20sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Run check (Shell)**  
To run a check provide connection and table name (including schema name) in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -c=connection_name -t=table_name
```
It is also possible to run a check on a specific column. In order to do this, add the name of the check and the column name to the above
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_percentile90_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          numeric:
            monthly_partition_percentile90_in_range:
              parameters:
                percentile_value: 0.9
              error:
                from: 10.0
                to: 20.5
              warning:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-28"
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
          numeric:
            monthly_partition_percentile90_in_range:
              parameters:
                percentile_value: 0.9
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
            )
        AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table)
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
        FROM(
            SELECT
                PERCENTILE_CONT((analyzed_table.`target_column`), 0.9) OVER (PARTITION BY
                TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
            )
        AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
        DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
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
              numeric:
                monthly_partition_percentile90_in_range:
                  parameters:
                    percentile_value: 0.9
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_time_dimension_projection(table_alias_prefix = '', indentation = '            ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(({{ lib.render_target_column('analyzed_table')}}), {{ parameters.percentile_value }}) OVER (PARTITION BY{{render_time_dimension_projection('analyzed_table') }}
                )
            AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table)
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT MAX(actual_value) AS actual_value, time_period, time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT((analyzed_table.`target_column`), 0.9) OVER (PARTITION BY
                    TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
                )
            AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`target_schema`.`target_table` AS analyzed_table)
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }}) WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9) WITHIN GROUP (ORDER BY "target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('month', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."target_schema"."target_table" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___
