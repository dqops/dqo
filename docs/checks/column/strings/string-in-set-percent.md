**string in set percent** checks  

**Description**  
Column level check that ensures that there are no more than a maximum percent of empty strings in a monitored column.

___

## **string in set percent**  
  
**Check description**  
Verifies that the percentage of strings from a set in a column does not exceed the minimum accepted percentage.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|string_in_set_percent|profiling| |[string_in_set_percent](../../../../reference/sensors/column/strings-column-sensors/#string-in-set-percent)|[min_percent](../../../../reference/rules/comparison/#min-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=string_in_set_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=string_in_set_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=string_in_set_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=string_in_set_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=string_in_set_percent
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        strings:
          string_in_set_percent:
            parameters:
              values:
              - USD
              - GBP
              - EUR
            warning:
              min_percent: 1.0
            error:
              min_percent: 2.0
            fatal:
              min_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-26"
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
        strings:
          string_in_set_percent:
            parameters:
              values:
              - USD
              - GBP
              - EUR
            warning:
              min_percent: 1.0
            error:
              min_percent: 2.0
            fatal:
              min_percent: 5.0
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
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
        TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### ****
=== "Sensor template for "
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for "
      
    ```
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        SYSDATETIMEOFFSET() AS time_period,
        CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
    FROM [].[<target_schema>].[<target_table>] AS analyzed_table
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 43-48"
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
            strings:
              string_in_set_percent:
                parameters:
                  values:
                  - USD
                  - GBP
                  - EUR
                warning:
                  min_percent: 1.0
                error:
                  min_percent: 2.0
                fatal:
                  min_percent: 5.0
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
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
            TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    ****  
      
    === "Sensor template for "
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for "
        ```
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2,
            SYSDATETIMEOFFSET() AS time_period,
            CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table, 
                , 
            
        ORDER BY level_1, level_2
                , 
            
        
            
        ```
    





___

## **daily string in set percent**  
  
**Check description**  
Verifies that the percentage of strings from a set in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_string_in_set_percent|recurring|daily|[string_in_set_percent](../../../../reference/sensors/column/strings-column-sensors/#string-in-set-percent)|[min_percent](../../../../reference/rules/comparison/#min-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_string_in_set_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_string_in_set_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_string_in_set_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_string_in_set_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_string_in_set_percent
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        daily:
          strings:
            daily_string_in_set_percent:
              parameters:
                values:
                - USD
                - GBP
                - EUR
              warning:
                min_percent: 1.0
              error:
                min_percent: 2.0
              fatal:
                min_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-27"
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
          strings:
            daily_string_in_set_percent:
              parameters:
                values:
                - USD
                - GBP
                - EUR
              warning:
                min_percent: 1.0
              error:
                min_percent: 2.0
              fatal:
                min_percent: 5.0
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
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
        TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### ****
=== "Sensor template for "
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for "
      
    ```
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
    FROM [].[<target_schema>].[<target_table>] AS analyzed_table
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 44-49"
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
              strings:
                daily_string_in_set_percent:
                  parameters:
                    values:
                    - USD
                    - GBP
                    - EUR
                  warning:
                    min_percent: 1.0
                  error:
                    min_percent: 2.0
                  fatal:
                    min_percent: 5.0
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
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
            TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    ****  
      
    === "Sensor template for "
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for "
        ```
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2,
            CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
            CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table, 
                , 
            
        ORDER BY level_1, level_2
                , 
            
        
            
        ```
    





___

## **monthly string in set percent**  
  
**Check description**  
Verifies that the percentage of strings from set in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_string_in_set_percent|recurring|monthly|[string_in_set_percent](../../../../reference/sensors/column/strings-column-sensors/#string-in-set-percent)|[min_percent](../../../../reference/rules/comparison/#min-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_string_in_set_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_string_in_set_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_string_in_set_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_string_in_set_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_string_in_set_percent
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        monthly:
          strings:
            monthly_string_in_set_percent:
              parameters:
                values:
                - USD
                - GBP
                - EUR
              warning:
                min_percent: 1.0
              error:
                min_percent: 2.0
              fatal:
                min_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-27"
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
          strings:
            monthly_string_in_set_percent:
              parameters:
                values:
                - USD
                - GBP
                - EUR
              warning:
                min_percent: 1.0
              error:
                min_percent: 2.0
              fatal:
                min_percent: 5.0
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
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### ****
=== "Sensor template for "
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for "
      
    ```
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
    FROM [].[<target_schema>].[<target_table>] AS analyzed_table
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 44-49"
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
              strings:
                monthly_string_in_set_percent:
                  parameters:
                    values:
                    - USD
                    - GBP
                    - EUR
                  warning:
                    min_percent: 1.0
                  error:
                    min_percent: 2.0
                  fatal:
                    min_percent: 5.0
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
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    ****  
      
    === "Sensor template for "
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for "
        ```
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2,
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table, 
                , 
            
        ORDER BY level_1, level_2
                , 
            
        
            
        ```
    





___

## **daily partition string in set percent**  
  
**Check description**  
Verifies that the percentage of strings from set in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_string_in_set_percent|partitioned|daily|[string_in_set_percent](../../../../reference/sensors/column/strings-column-sensors/#string-in-set-percent)|[min_percent](../../../../reference/rules/comparison/#min-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_partition_string_in_set_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_partition_string_in_set_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_partition_string_in_set_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_partition_string_in_set_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_string_in_set_percent
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          strings:
            daily_partition_string_in_set_percent:
              parameters:
                values:
                - USD
                - GBP
                - EUR
              warning:
                min_percent: 1.0
              error:
                min_percent: 2.0
              fatal:
                min_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-27"
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
          strings:
            daily_partition_string_in_set_percent:
              parameters:
                values:
                - USD
                - GBP
                - EUR
              warning:
                min_percent: 1.0
              error:
                min_percent: 2.0
              fatal:
                min_percent: 5.0
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
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### ****
=== "Sensor template for "
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for "
      
    ```
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST([col_event_timestamp] AS date) AS time_period,
        CAST((CAST([col_event_timestamp] AS date)) AS DATETIME) AS time_period_utc
    FROM [].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY CAST([col_event_timestamp] AS date), CAST([col_event_timestamp] AS date)
    ORDER BY CAST([col_event_timestamp] AS date)
    
        
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 44-49"
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
              strings:
                daily_partition_string_in_set_percent:
                  parameters:
                    values:
                    - USD
                    - GBP
                    - EUR
                  warning:
                    min_percent: 1.0
                  error:
                    min_percent: 2.0
                  fatal:
                    min_percent: 5.0
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
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    ****  
      
    === "Sensor template for "
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for "
        ```
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2,
            CAST([col_event_timestamp] AS date) AS time_period,
            CAST((CAST([col_event_timestamp] AS date)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table, 
        GROUP BY CAST([col_event_timestamp] AS date), CAST([col_event_timestamp] AS date)
        ORDER BY level_1, level_2CAST([col_event_timestamp] AS date)
        
            
        ```
    





___

## **monthly partition string in set percent**  
  
**Check description**  
Verifies that the percentage of strings from set in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_string_in_set_percent|partitioned|monthly|[string_in_set_percent](../../../../reference/sensors/column/strings-column-sensors/#string-in-set-percent)|[min_percent](../../../../reference/rules/comparison/#min-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_partition_string_in_set_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_partition_string_in_set_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_partition_string_in_set_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_partition_string_in_set_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_string_in_set_percent
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          strings:
            monthly_partition_string_in_set_percent:
              parameters:
                values:
                - USD
                - GBP
                - EUR
              warning:
                min_percent: 1.0
              error:
                min_percent: 2.0
              fatal:
                min_percent: 5.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-27"
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
          strings:
            monthly_partition_string_in_set_percent:
              parameters:
                values:
                - USD
                - GBP
                - EUR
              warning:
                min_percent: 1.0
              error:
                min_percent: 2.0
              fatal:
                min_percent: 5.0
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
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### ****
=== "Sensor template for "
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{lib.make_text_constant(i)}}{{", "}}
            {%- else -%}
                {{lib.make_text_constant(i)}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
        0.0
        {%- else -%}
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for "
      
    ```
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1) AS time_period,
        CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME) AS time_period_utc
    FROM [].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, [col_event_timestamp]), 0)
    ORDER BY DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)
    
        
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 44-49"
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
              strings:
                monthly_partition_string_in_set_percent:
                  parameters:
                    values:
                    - USD
                    - GBP
                    - EUR
                  warning:
                    min_percent: 1.0
                  error:
                    min_percent: 2.0
                  fatal:
                    min_percent: 5.0
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
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table.`target_column` IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
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
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table."target_column" IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    ****  
      
    === "Sensor template for "
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro extract_in_list(values_list) -%}
            {%- for i in values_list -%}
                {%- if not loop.last -%}
                    {{lib.make_text_constant(i)}}{{", "}}
                {%- else -%}
                    {{lib.make_text_constant(i)}}
                {%- endif -%}
            {%- endfor -%}
        {% endmacro -%}
        
        {%- macro actual_value() -%}
            {%- if 'values' not in parameters or parameters['values']|length == 0 -%}
            0.0
            {%- else -%}
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters['values']) }})
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END
            {%- endif -%}
        {% endmacro -%}
        
        SELECT
            {{ actual_value() }} AS actual_value
            {{- lib.render_data_stream_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for "
        ```
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN analyzed_table.[target_column] IN ('USD', 'GBP', 'EUR')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2,
            DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1) AS time_period,
            CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table, 
        GROUP BY DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, [col_event_timestamp]), 0)
        ORDER BY level_1, level_2DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)
        
            
        ```
    





___
