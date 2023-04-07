**foreign key match percent** checks  

**Description**  
Column level check that ensures that there are no more than a minimum percentage of values matching values in another table column.

___

## **foreign key match percent**  
  
**Check description**  
Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|foreign_key_match_percent|profiling| |[foreign_key_match_percent](../../../../reference/sensors/column/integrity-column-sensors/#foreign-key-match-percent)|[max_percent](../../../../reference/rules/comparison/#max-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=foreign_key_match_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=foreign_key_match_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=foreign_key_match_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=foreign_key_match_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=foreign_key_match_percent
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        integrity:
          foreign_key_match_percent:
            parameters:
              foreign_table: dim_customer
              foreign_column: customer_id
            warning:
              max_percent: 1.0
            error:
              max_percent: 2.0
            fatal:
              max_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-24"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      profiling_checks:
        integrity:
          foreign_key_match_percent:
            parameters:
              foreign_table: dim_customer
              foreign_column: customer_id
            warning:
              max_percent: 1.0
            error:
              max_percent: 2.0
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
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
=== "Rendered SQL for BigQuery"
      
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
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    LEFT OUTER JOIN `your-google-project-id`.``.`dim_customer` AS foreign_table
    ON analyzed_table.`target_column` = foreign_table.`customer_id`
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
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
=== "Rendered SQL for Snowflake"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
        TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database".""."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
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
=== "Rendered SQL for PostgreSQL"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database".""."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
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
=== "Rendered SQL for Redshift"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    LEFT OUTER JOIN "your_redshift_database".""."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 41-46"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
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
          profiling_checks:
            integrity:
              foreign_key_match_percent:
                parameters:
                  foreign_table: dim_customer
                  foreign_column: customer_id
                warning:
                  max_percent: 1.0
                error:
                  max_percent: 2.0
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
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
    === "Rendered SQL for BigQuery"
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
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        LEFT OUTER JOIN `your-google-project-id`.``.`dim_customer` AS foreign_table
        ON analyzed_table.`target_column` = foreign_table.`customer_id`
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
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
    === "Rendered SQL for Snowflake"
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
            TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
            TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        LEFT OUTER JOIN "your_snowflake_database".""."dim_customer" AS foreign_table
        ON analyzed_table."target_column" = foreign_table."customer_id"
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
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
    === "Rendered SQL for PostgreSQL"
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
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        LEFT OUTER JOIN "your_postgresql_database".""."dim_customer" AS foreign_table
        ON analyzed_table."target_column" = foreign_table."customer_id"
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
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
    === "Rendered SQL for Redshift"
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
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        LEFT OUTER JOIN "your_redshift_database".""."dim_customer" AS foreign_table
        ON analyzed_table."target_column" = foreign_table."customer_id"
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily foreign key match percent**  
  
**Check description**  
Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_foreign_key_match_percent|recurring|daily|[foreign_key_match_percent](../../../../reference/sensors/column/integrity-column-sensors/#foreign-key-match-percent)|[max_percent](../../../../reference/rules/comparison/#max-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_foreign_key_match_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_foreign_key_match_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_foreign_key_match_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_foreign_key_match_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_foreign_key_match_percent
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        daily:
          integrity:
            daily_foreign_key_match_percent:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
              warning:
                max_percent: 1.0
              error:
                max_percent: 2.0
              fatal:
                max_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-25"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      recurring_checks:
        daily:
          integrity:
            daily_foreign_key_match_percent:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
              warning:
                max_percent: 1.0
              error:
                max_percent: 2.0
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
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
=== "Rendered SQL for BigQuery"
      
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
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    LEFT OUTER JOIN `your-google-project-id`.``.`dim_customer` AS foreign_table
    ON analyzed_table.`target_column` = foreign_table.`customer_id`
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
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
=== "Rendered SQL for Snowflake"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
        TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database".""."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
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
=== "Rendered SQL for PostgreSQL"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database".""."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
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
=== "Rendered SQL for Redshift"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    LEFT OUTER JOIN "your_redshift_database".""."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 42-47"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
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
          recurring_checks:
            daily:
              integrity:
                daily_foreign_key_match_percent:
                  parameters:
                    foreign_table: dim_customer
                    foreign_column: customer_id
                  warning:
                    max_percent: 1.0
                  error:
                    max_percent: 2.0
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
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
    === "Rendered SQL for BigQuery"
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
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        LEFT OUTER JOIN `your-google-project-id`.``.`dim_customer` AS foreign_table
        ON analyzed_table.`target_column` = foreign_table.`customer_id`
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
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
    === "Rendered SQL for Snowflake"
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
            CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
            TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        LEFT OUTER JOIN "your_snowflake_database".""."dim_customer" AS foreign_table
        ON analyzed_table."target_column" = foreign_table."customer_id"
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
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
    === "Rendered SQL for PostgreSQL"
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
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        LEFT OUTER JOIN "your_postgresql_database".""."dim_customer" AS foreign_table
        ON analyzed_table."target_column" = foreign_table."customer_id"
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
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
    === "Rendered SQL for Redshift"
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
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        LEFT OUTER JOIN "your_redshift_database".""."dim_customer" AS foreign_table
        ON analyzed_table."target_column" = foreign_table."customer_id"
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly foreign key match percent**  
  
**Check description**  
Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_foreign_key_match_percent|recurring|monthly|[foreign_key_match_percent](../../../../reference/sensors/column/integrity-column-sensors/#foreign-key-match-percent)|[max_percent](../../../../reference/rules/comparison/#max-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_foreign_key_match_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_foreign_key_match_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_foreign_key_match_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_foreign_key_match_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_foreign_key_match_percent
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        monthly:
          integrity:
            monthly_foreign_key_match_percent:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
              warning:
                max_percent: 1.0
              error:
                max_percent: 2.0
              fatal:
                max_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-25"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      recurring_checks:
        monthly:
          integrity:
            monthly_foreign_key_match_percent:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
              warning:
                max_percent: 1.0
              error:
                max_percent: 2.0
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
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
=== "Rendered SQL for BigQuery"
      
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
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    LEFT OUTER JOIN `your-google-project-id`.``.`dim_customer` AS foreign_table
    ON analyzed_table.`target_column` = foreign_table.`customer_id`
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
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
=== "Rendered SQL for Snowflake"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database".""."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
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
=== "Rendered SQL for PostgreSQL"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database".""."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
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
=== "Rendered SQL for Redshift"
      
    ```
    
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table."customer_id" IS NULL AND analyzed_table."target_column" IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    LEFT OUTER JOIN "your_redshift_database".""."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 42-47"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
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
          recurring_checks:
            monthly:
              integrity:
                monthly_foreign_key_match_percent:
                  parameters:
                    foreign_table: dim_customer
                    foreign_column: customer_id
                  warning:
                    max_percent: 1.0
                  error:
                    max_percent: 2.0
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
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
    === "Rendered SQL for BigQuery"
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
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        LEFT OUTER JOIN `your-google-project-id`.``.`dim_customer` AS foreign_table
        ON analyzed_table.`target_column` = foreign_table.`customer_id`
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
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
    === "Rendered SQL for Snowflake"
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
            DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        LEFT OUTER JOIN "your_snowflake_database".""."dim_customer" AS foreign_table
        ON analyzed_table."target_column" = foreign_table."customer_id"
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
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
    === "Rendered SQL for PostgreSQL"
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
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        LEFT OUTER JOIN "your_postgresql_database".""."dim_customer" AS foreign_table
        ON analyzed_table."target_column" = foreign_table."customer_id"
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
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
    === "Rendered SQL for Redshift"
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
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        LEFT OUTER JOIN "your_redshift_database".""."dim_customer" AS foreign_table
        ON analyzed_table."target_column" = foreign_table."customer_id"
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **daily partition foreign key match percent**  
  
**Check description**  
Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_foreign_key_match_percent|partitioned|daily|[foreign_key_match_percent](../../../../reference/sensors/column/integrity-column-sensors/#foreign-key-match-percent)|[max_percent](../../../../reference/rules/comparison/#max-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_partition_foreign_key_match_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_partition_foreign_key_match_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_partition_foreign_key_match_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_partition_foreign_key_match_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_foreign_key_match_percent
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
              warning:
                max_percent: 1.0
              error:
                max_percent: 2.0
              fatal:
                max_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-25"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      partitioned_checks:
        daily:
          integrity:
            daily_partition_foreign_key_match_percent:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
              warning:
                max_percent: 1.0
              error:
                max_percent: 2.0
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
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
=== "Rendered SQL for BigQuery"
      
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
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    LEFT OUTER JOIN `your-google-project-id`.``.`dim_customer` AS foreign_table
    ON analyzed_table.`target_column` = foreign_table.`customer_id`
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
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
=== "Rendered SQL for Snowflake"
      
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
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database".""."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
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
=== "Rendered SQL for PostgreSQL"
      
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
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database".""."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
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
=== "Rendered SQL for Redshift"
      
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
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    LEFT OUTER JOIN "your_redshift_database".""."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 42-47"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
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
                  warning:
                    max_percent: 1.0
                  error:
                    max_percent: 2.0
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
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
    === "Rendered SQL for BigQuery"
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
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        LEFT OUTER JOIN `your-google-project-id`.``.`dim_customer` AS foreign_table
        ON analyzed_table.`target_column` = foreign_table.`customer_id`
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
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
    === "Rendered SQL for Snowflake"
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
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        LEFT OUTER JOIN "your_snowflake_database".""."dim_customer" AS foreign_table
        ON analyzed_table."target_column" = foreign_table."customer_id"
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
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
    === "Rendered SQL for PostgreSQL"
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
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        LEFT OUTER JOIN "your_postgresql_database".""."dim_customer" AS foreign_table
        ON analyzed_table."target_column" = foreign_table."customer_id"
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
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
    === "Rendered SQL for Redshift"
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
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        LEFT OUTER JOIN "your_redshift_database".""."dim_customer" AS foreign_table
        ON analyzed_table."target_column" = foreign_table."customer_id"
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___

## **monthly partition foreign key match percent**  
  
**Check description**  
Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_foreign_key_match_percent|partitioned|monthly|[foreign_key_match_percent](../../../../reference/sensors/column/integrity-column-sensors/#foreign-key-match-percent)|[max_percent](../../../../reference/rules/comparison/#max-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_partition_foreign_key_match_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_partition_foreign_key_match_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_partition_foreign_key_match_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_partition_foreign_key_match_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_foreign_key_match_percent
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
              warning:
                max_percent: 1.0
              error:
                max_percent: 2.0
              fatal:
                max_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-25"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      partitioned_checks:
        monthly:
          integrity:
            monthly_partition_foreign_key_match_percent:
              parameters:
                foreign_table: dim_customer
                foreign_column: customer_id
              warning:
                max_percent: 1.0
              error:
                max_percent: 2.0
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
### **BigQuery**
=== "Sensor template for BigQuery"
      
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
=== "Rendered SQL for BigQuery"
      
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
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    LEFT OUTER JOIN `your-google-project-id`.``.`dim_customer` AS foreign_table
    ON analyzed_table.`target_column` = foreign_table.`customer_id`
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
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
=== "Rendered SQL for Snowflake"
      
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
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    LEFT OUTER JOIN "your_snowflake_database".""."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
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
=== "Rendered SQL for PostgreSQL"
      
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
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    LEFT OUTER JOIN "your_postgresql_database".""."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
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
=== "Rendered SQL for Redshift"
      
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
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    LEFT OUTER JOIN "your_redshift_database".""."dim_customer" AS foreign_table
    ON analyzed_table."target_column" = foreign_table."customer_id"
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 42-47"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
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
                  warning:
                    max_percent: 1.0
                  error:
                    max_percent: 2.0
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
    **BigQuery**  
      
    === "Sensor template for BigQuery"
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
    === "Rendered SQL for BigQuery"
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
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        LEFT OUTER JOIN `your-google-project-id`.``.`dim_customer` AS foreign_table
        ON analyzed_table.`target_column` = foreign_table.`customer_id`
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
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
    === "Rendered SQL for Snowflake"
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
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        LEFT OUTER JOIN "your_snowflake_database".""."dim_customer" AS foreign_table
        ON analyzed_table."target_column" = foreign_table."customer_id"
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
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
    === "Rendered SQL for PostgreSQL"
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
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        LEFT OUTER JOIN "your_postgresql_database".""."dim_customer" AS foreign_table
        ON analyzed_table."target_column" = foreign_table."customer_id"
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
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
    === "Rendered SQL for Redshift"
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
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        LEFT OUTER JOIN "your_redshift_database".""."dim_customer" AS foreign_table
        ON analyzed_table."target_column" = foreign_table."customer_id"
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    





___
