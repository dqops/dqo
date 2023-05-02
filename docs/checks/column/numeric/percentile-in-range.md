**percentile in range** checks  

**Description**  
Column level check that ensures that the percentile of values in a monitored column is in a set range.

___

## **percentile in range**  
  
**Check description**  
Verifies that the percentile of all values in a column is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|percentile_in_range|profiling| |[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=percentile_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=percentile_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=percentile_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=percentile_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=percentile_in_range
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        numeric:
          percentile_in_range:
            warning:
              from: 10.0
              to: 20.5
            error:
              from: 10.0
              to: 20.5
            fatal:
              from: 10.0
              to: 20.5
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
        numeric:
          percentile_in_range:
            warning:
              from: 10.0
              to: 20.5
            error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            )
            OVER (PARTITION BY
                
        CURRENT_TIMESTAMP(),
        TIMESTAMP(CURRENT_TIMESTAMP())
                
            ) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT()
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT()
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT()
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        SYSDATETIMEOFFSET(),
        CAST((SYSDATETIMEOFFSET()) AS DATETIME)
                
            ) AS actual_value,
        SYSDATETIMEOFFSET() AS time_period,
        CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
            numeric:
              percentile_in_range:
                warning:
                  from: 10.0
                  to: 20.5
                error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                )
                OVER (PARTITION BY
                    
            CURRENT_TIMESTAMP(),
            TIMESTAMP(CURRENT_TIMESTAMP())
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT()
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            SYSDATETIMEOFFSET(),
            CAST((SYSDATETIMEOFFSET()) AS DATETIME)
                    
                ) AS actual_value,
            SYSDATETIMEOFFSET() AS time_period,
            CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **median in range**  
  
**Check description**  
Verifies that the median of all values in a column is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|median_in_range|profiling| |[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=median_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=median_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=median_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=median_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=median_in_range
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        numeric:
          median_in_range:
            parameters:
              percentile_value: 0.5
            warning:
              from: 10.0
              to: 20.5
            error:
              from: 10.0
              to: 20.5
            fatal:
              from: 10.0
              to: 20.5
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
        numeric:
          median_in_range:
            parameters:
              percentile_value: 0.5
            warning:
              from: 10.0
              to: 20.5
            error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.5)
            OVER (PARTITION BY
                
        CURRENT_TIMESTAMP(),
        TIMESTAMP(CURRENT_TIMESTAMP())
                
            ) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        SYSDATETIMEOFFSET(),
        CAST((SYSDATETIMEOFFSET()) AS DATETIME)
                
            ) AS actual_value,
        SYSDATETIMEOFFSET() AS time_period,
        CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
            numeric:
              median_in_range:
                parameters:
                  percentile_value: 0.5
                warning:
                  from: 10.0
                  to: 20.5
                error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.5)
                OVER (PARTITION BY
                    
            CURRENT_TIMESTAMP(),
            TIMESTAMP(CURRENT_TIMESTAMP())
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            SYSDATETIMEOFFSET(),
            CAST((SYSDATETIMEOFFSET()) AS DATETIME)
                    
                ) AS actual_value,
            SYSDATETIMEOFFSET() AS time_period,
            CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **percentile 10 in range**  
  
**Check description**  
Verifies that the percentile 10 of all values in a column is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|percentile_10_in_range|profiling| |[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=percentile_10_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=percentile_10_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=percentile_10_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=percentile_10_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=percentile_10_in_range
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        numeric:
          percentile_10_in_range:
            parameters:
              percentile_value: 0.1
            warning:
              from: 10.0
              to: 20.5
            error:
              from: 10.0
              to: 20.5
            fatal:
              from: 10.0
              to: 20.5
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
        numeric:
          percentile_10_in_range:
            parameters:
              percentile_value: 0.1
            warning:
              from: 10.0
              to: 20.5
            error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.1)
            OVER (PARTITION BY
                
        CURRENT_TIMESTAMP(),
        TIMESTAMP(CURRENT_TIMESTAMP())
                
            ) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        SYSDATETIMEOFFSET(),
        CAST((SYSDATETIMEOFFSET()) AS DATETIME)
                
            ) AS actual_value,
        SYSDATETIMEOFFSET() AS time_period,
        CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
            numeric:
              percentile_10_in_range:
                parameters:
                  percentile_value: 0.1
                warning:
                  from: 10.0
                  to: 20.5
                error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.1)
                OVER (PARTITION BY
                    
            CURRENT_TIMESTAMP(),
            TIMESTAMP(CURRENT_TIMESTAMP())
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.1)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            SYSDATETIMEOFFSET(),
            CAST((SYSDATETIMEOFFSET()) AS DATETIME)
                    
                ) AS actual_value,
            SYSDATETIMEOFFSET() AS time_period,
            CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **percentile 25 in range**  
  
**Check description**  
Verifies that the percentile 25 of all values in a column is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|percentile_25_in_range|profiling| |[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=percentile_25_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=percentile_25_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=percentile_25_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=percentile_25_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=percentile_25_in_range
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        numeric:
          percentile_25_in_range:
            parameters:
              percentile_value: 0.25
            warning:
              from: 10.0
              to: 20.5
            error:
              from: 10.0
              to: 20.5
            fatal:
              from: 10.0
              to: 20.5
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
        numeric:
          percentile_25_in_range:
            parameters:
              percentile_value: 0.25
            warning:
              from: 10.0
              to: 20.5
            error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.25)
            OVER (PARTITION BY
                
        CURRENT_TIMESTAMP(),
        TIMESTAMP(CURRENT_TIMESTAMP())
                
            ) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        SYSDATETIMEOFFSET(),
        CAST((SYSDATETIMEOFFSET()) AS DATETIME)
                
            ) AS actual_value,
        SYSDATETIMEOFFSET() AS time_period,
        CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
            numeric:
              percentile_25_in_range:
                parameters:
                  percentile_value: 0.25
                warning:
                  from: 10.0
                  to: 20.5
                error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.25)
                OVER (PARTITION BY
                    
            CURRENT_TIMESTAMP(),
            TIMESTAMP(CURRENT_TIMESTAMP())
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.25)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            SYSDATETIMEOFFSET(),
            CAST((SYSDATETIMEOFFSET()) AS DATETIME)
                    
                ) AS actual_value,
            SYSDATETIMEOFFSET() AS time_period,
            CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **percentile 75 in range**  
  
**Check description**  
Verifies that the percentile 75 of all values in a column is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|percentile_75_in_range|profiling| |[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=percentile_75_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=percentile_75_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=percentile_75_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=percentile_75_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=percentile_75_in_range
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        numeric:
          percentile_75_in_range:
            parameters:
              percentile_value: 0.75
            warning:
              from: 10.0
              to: 20.5
            error:
              from: 10.0
              to: 20.5
            fatal:
              from: 10.0
              to: 20.5
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
        numeric:
          percentile_75_in_range:
            parameters:
              percentile_value: 0.75
            warning:
              from: 10.0
              to: 20.5
            error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.75)
            OVER (PARTITION BY
                
        CURRENT_TIMESTAMP(),
        TIMESTAMP(CURRENT_TIMESTAMP())
                
            ) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        SYSDATETIMEOFFSET(),
        CAST((SYSDATETIMEOFFSET()) AS DATETIME)
                
            ) AS actual_value,
        SYSDATETIMEOFFSET() AS time_period,
        CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
            numeric:
              percentile_75_in_range:
                parameters:
                  percentile_value: 0.75
                warning:
                  from: 10.0
                  to: 20.5
                error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.75)
                OVER (PARTITION BY
                    
            CURRENT_TIMESTAMP(),
            TIMESTAMP(CURRENT_TIMESTAMP())
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.75)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            SYSDATETIMEOFFSET(),
            CAST((SYSDATETIMEOFFSET()) AS DATETIME)
                    
                ) AS actual_value,
            SYSDATETIMEOFFSET() AS time_period,
            CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **percentile 90 in range**  
  
**Check description**  
Verifies that the percentile 90 of all values in a column is not outside the set range.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|percentile_90_in_range|profiling| |[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=percentile_90_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=percentile_90_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=percentile_90_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=percentile_90_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=percentile_90_in_range
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        numeric:
          percentile_90_in_range:
            parameters:
              percentile_value: 0.9
            warning:
              from: 10.0
              to: 20.5
            error:
              from: 10.0
              to: 20.5
            fatal:
              from: 10.0
              to: 20.5
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
        numeric:
          percentile_90_in_range:
            parameters:
              percentile_value: 0.9
            warning:
              from: 10.0
              to: 20.5
            error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.9)
            OVER (PARTITION BY
                
        CURRENT_TIMESTAMP(),
        TIMESTAMP(CURRENT_TIMESTAMP())
                
            ) AS actual_value,
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        SYSDATETIMEOFFSET(),
        CAST((SYSDATETIMEOFFSET()) AS DATETIME)
                
            ) AS actual_value,
        SYSDATETIMEOFFSET() AS time_period,
        CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
            numeric:
              percentile_90_in_range:
                parameters:
                  percentile_value: 0.9
                warning:
                  from: 10.0
                  to: 20.5
                error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.9)
                OVER (PARTITION BY
                    
            CURRENT_TIMESTAMP(),
            TIMESTAMP(CURRENT_TIMESTAMP())
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            SYSDATETIMEOFFSET(),
            CAST((SYSDATETIMEOFFSET()) AS DATETIME)
                    
                ) AS actual_value,
            SYSDATETIMEOFFSET() AS time_period,
            CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **daily percentile in range**  
  
**Check description**  
Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_percentile_in_range|recurring|daily|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_percentile_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_percentile_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_percentile_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_percentile_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_percentile_in_range
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        daily:
          numeric:
            daily_percentile_in_range:
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            daily_percentile_in_range:
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            )
            OVER (PARTITION BY
                
        CAST(CURRENT_TIMESTAMP() AS DATE),
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                
            ) AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT()
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT()
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT()
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        CAST(SYSDATETIMEOFFSET() AS date),
        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME)
                
            ) AS actual_value,
        CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                daily_percentile_in_range:
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                )
                OVER (PARTITION BY
                    
            CAST(CURRENT_TIMESTAMP() AS DATE),
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT()
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            CAST(SYSDATETIMEOFFSET() AS date),
            CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME)
                    
                ) AS actual_value,
            CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
            CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **daily median in range**  
  
**Check description**  
Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_median_in_range|recurring|daily|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_median_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_median_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_median_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_median_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_median_in_range
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        daily:
          numeric:
            daily_median_in_range:
              parameters:
                percentile_value: 0.5
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            daily_median_in_range:
              parameters:
                percentile_value: 0.5
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.5)
            OVER (PARTITION BY
                
        CAST(CURRENT_TIMESTAMP() AS DATE),
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                
            ) AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        CAST(SYSDATETIMEOFFSET() AS date),
        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME)
                
            ) AS actual_value,
        CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                daily_median_in_range:
                  parameters:
                    percentile_value: 0.5
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.5)
                OVER (PARTITION BY
                    
            CAST(CURRENT_TIMESTAMP() AS DATE),
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            CAST(SYSDATETIMEOFFSET() AS date),
            CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME)
                    
                ) AS actual_value,
            CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
            CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **daily percentile 10 in range**  
  
**Check description**  
Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_percentile_10_in_range|recurring|daily|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_percentile_10_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_percentile_10_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_percentile_10_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_percentile_10_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_percentile_10_in_range
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        daily:
          numeric:
            daily_percentile_10_in_range:
              parameters:
                percentile_value: 0.1
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            daily_percentile_10_in_range:
              parameters:
                percentile_value: 0.1
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.1)
            OVER (PARTITION BY
                
        CAST(CURRENT_TIMESTAMP() AS DATE),
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                
            ) AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        CAST(SYSDATETIMEOFFSET() AS date),
        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME)
                
            ) AS actual_value,
        CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                daily_percentile_10_in_range:
                  parameters:
                    percentile_value: 0.1
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.1)
                OVER (PARTITION BY
                    
            CAST(CURRENT_TIMESTAMP() AS DATE),
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.1)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            CAST(SYSDATETIMEOFFSET() AS date),
            CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME)
                    
                ) AS actual_value,
            CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
            CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **daily percentile 25 in range**  
  
**Check description**  
Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_percentile_25_in_range|recurring|daily|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_percentile_25_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_percentile_25_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_percentile_25_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_percentile_25_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_percentile_25_in_range
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        daily:
          numeric:
            daily_percentile_25_in_range:
              parameters:
                percentile_value: 0.25
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            daily_percentile_25_in_range:
              parameters:
                percentile_value: 0.25
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.25)
            OVER (PARTITION BY
                
        CAST(CURRENT_TIMESTAMP() AS DATE),
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                
            ) AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        CAST(SYSDATETIMEOFFSET() AS date),
        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME)
                
            ) AS actual_value,
        CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                daily_percentile_25_in_range:
                  parameters:
                    percentile_value: 0.25
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.25)
                OVER (PARTITION BY
                    
            CAST(CURRENT_TIMESTAMP() AS DATE),
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.25)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            CAST(SYSDATETIMEOFFSET() AS date),
            CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME)
                    
                ) AS actual_value,
            CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
            CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **daily percentile 75 in range**  
  
**Check description**  
Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_percentile_75_in_range|recurring|daily|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_percentile_75_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_percentile_75_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_percentile_75_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_percentile_75_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_percentile_75_in_range
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        daily:
          numeric:
            daily_percentile_75_in_range:
              parameters:
                percentile_value: 0.75
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            daily_percentile_75_in_range:
              parameters:
                percentile_value: 0.75
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.75)
            OVER (PARTITION BY
                
        CAST(CURRENT_TIMESTAMP() AS DATE),
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                
            ) AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        CAST(SYSDATETIMEOFFSET() AS date),
        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME)
                
            ) AS actual_value,
        CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                daily_percentile_75_in_range:
                  parameters:
                    percentile_value: 0.75
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.75)
                OVER (PARTITION BY
                    
            CAST(CURRENT_TIMESTAMP() AS DATE),
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.75)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            CAST(SYSDATETIMEOFFSET() AS date),
            CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME)
                    
                ) AS actual_value,
            CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
            CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **daily percentile 90 in range**  
  
**Check description**  
Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_percentile_90_in_range|recurring|daily|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_percentile_90_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_percentile_90_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_percentile_90_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_percentile_90_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_percentile_90_in_range
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        daily:
          numeric:
            daily_percentile_90_in_range:
              parameters:
                percentile_value: 0.9
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            daily_percentile_90_in_range:
              parameters:
                percentile_value: 0.9
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.9)
            OVER (PARTITION BY
                
        CAST(CURRENT_TIMESTAMP() AS DATE),
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                
            ) AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        CAST(SYSDATETIMEOFFSET() AS date),
        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME)
                
            ) AS actual_value,
        CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                daily_percentile_90_in_range:
                  parameters:
                    percentile_value: 0.9
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.9)
                OVER (PARTITION BY
                    
            CAST(CURRENT_TIMESTAMP() AS DATE),
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            CAST(SYSDATETIMEOFFSET() AS date),
            CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME)
                    
                ) AS actual_value,
            CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
            CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **monthly percentile in range**  
  
**Check description**  
Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_percentile_in_range|recurring|monthly|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_percentile_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_percentile_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_percentile_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_percentile_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_percentile_in_range
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        monthly:
          numeric:
            monthly_percentile_in_range:
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            monthly_percentile_in_range:
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            )
            OVER (PARTITION BY
                
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH),
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                
            ) AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT()
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT()
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT()
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0),
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME)
                
            ) AS actual_value,
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                monthly_percentile_in_range:
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                )
                OVER (PARTITION BY
                    
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH),
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT()
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0),
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME)
                    
                ) AS actual_value,
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **monthly median in range**  
  
**Check description**  
Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_median_in_range|recurring|monthly|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_median_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_median_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_median_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_median_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_median_in_range
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        monthly:
          numeric:
            monthly_median_in_range:
              parameters:
                percentile_value: 0.5
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            monthly_median_in_range:
              parameters:
                percentile_value: 0.5
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.5)
            OVER (PARTITION BY
                
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH),
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                
            ) AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0),
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME)
                
            ) AS actual_value,
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                monthly_median_in_range:
                  parameters:
                    percentile_value: 0.5
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.5)
                OVER (PARTITION BY
                    
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH),
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0),
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME)
                    
                ) AS actual_value,
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **monthly percentile 10 in range**  
  
**Check description**  
Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_percentile_10_in_range|recurring|monthly|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_percentile_10_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_percentile_10_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_percentile_10_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_percentile_10_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_percentile_10_in_range
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        monthly:
          numeric:
            monthly_percentile_10_in_range:
              parameters:
                percentile_value: 0.1
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            monthly_percentile_10_in_range:
              parameters:
                percentile_value: 0.1
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.1)
            OVER (PARTITION BY
                
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH),
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                
            ) AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0),
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME)
                
            ) AS actual_value,
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                monthly_percentile_10_in_range:
                  parameters:
                    percentile_value: 0.1
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.1)
                OVER (PARTITION BY
                    
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH),
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.1)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0),
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME)
                    
                ) AS actual_value,
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **monthly percentile 25 in range**  
  
**Check description**  
Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_percentile_25_in_range|recurring|monthly|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_percentile_25_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_percentile_25_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_percentile_25_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_percentile_25_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_percentile_25_in_range
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        monthly:
          numeric:
            monthly_percentile_25_in_range:
              parameters:
                percentile_value: 0.25
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            monthly_percentile_25_in_range:
              parameters:
                percentile_value: 0.25
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.25)
            OVER (PARTITION BY
                
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH),
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                
            ) AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0),
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME)
                
            ) AS actual_value,
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                monthly_percentile_25_in_range:
                  parameters:
                    percentile_value: 0.25
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.25)
                OVER (PARTITION BY
                    
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH),
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.25)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0),
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME)
                    
                ) AS actual_value,
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **monthly percentile 75 in range**  
  
**Check description**  
Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_percentile_75_in_range|recurring|monthly|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_percentile_75_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_percentile_75_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_percentile_75_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_percentile_75_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_percentile_75_in_range
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        monthly:
          numeric:
            monthly_percentile_75_in_range:
              parameters:
                percentile_value: 0.75
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            monthly_percentile_75_in_range:
              parameters:
                percentile_value: 0.75
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.75)
            OVER (PARTITION BY
                
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH),
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                
            ) AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0),
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME)
                
            ) AS actual_value,
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                monthly_percentile_75_in_range:
                  parameters:
                    percentile_value: 0.75
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.75)
                OVER (PARTITION BY
                    
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH),
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.75)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0),
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME)
                    
                ) AS actual_value,
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **monthly percentile 90 in range**  
  
**Check description**  
Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_percentile_90_in_range|recurring|monthly|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_percentile_90_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_percentile_90_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_percentile_90_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_percentile_90_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_percentile_90_in_range
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        monthly:
          numeric:
            monthly_percentile_90_in_range:
              parameters:
                percentile_value: 0.9
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            monthly_percentile_90_in_range:
              parameters:
                percentile_value: 0.9
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.9)
            OVER (PARTITION BY
                
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH),
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                
            ) AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0),
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME)
                
            ) AS actual_value,
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                monthly_percentile_90_in_range:
                  parameters:
                    percentile_value: 0.9
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.9)
                OVER (PARTITION BY
                    
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH),
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0),
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME)
                    
                ) AS actual_value,
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **daily partition percentile in range**  
  
**Check description**  
Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_percentile_in_range|partitioned|daily|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_partition_percentile_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_partition_percentile_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_partition_percentile_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_partition_percentile_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_percentile_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          numeric:
            daily_partition_percentile_in_range:
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            daily_partition_percentile_in_range:
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            )
            OVER (PARTITION BY
                
        CAST(analyzed_table.`col_event_timestamp` AS DATE),
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
                
            ) AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT()
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT()
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT()
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        CAST([col_event_timestamp] AS date),
        CAST((CAST([col_event_timestamp] AS date)) AS DATETIME)
                
            ) AS actual_value,
        CAST([col_event_timestamp] AS date) AS time_period,
        CAST((CAST([col_event_timestamp] AS date)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                daily_partition_percentile_in_range:
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                )
                OVER (PARTITION BY
                    
            CAST(analyzed_table.`col_event_timestamp` AS DATE),
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT()
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            CAST([col_event_timestamp] AS date),
            CAST((CAST([col_event_timestamp] AS date)) AS DATETIME)
                    
                ) AS actual_value,
            CAST([col_event_timestamp] AS date) AS time_period,
            CAST((CAST([col_event_timestamp] AS date)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **daily partition median in range**  
  
**Check description**  
Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_median_in_range|partitioned|daily|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_partition_median_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_partition_median_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_partition_median_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_partition_median_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_median_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          numeric:
            daily_partition_median_in_range:
              parameters:
                percentile_value: 0.5
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            daily_partition_median_in_range:
              parameters:
                percentile_value: 0.5
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.5)
            OVER (PARTITION BY
                
        CAST(analyzed_table.`col_event_timestamp` AS DATE),
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
                
            ) AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        CAST([col_event_timestamp] AS date),
        CAST((CAST([col_event_timestamp] AS date)) AS DATETIME)
                
            ) AS actual_value,
        CAST([col_event_timestamp] AS date) AS time_period,
        CAST((CAST([col_event_timestamp] AS date)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                daily_partition_median_in_range:
                  parameters:
                    percentile_value: 0.5
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.5)
                OVER (PARTITION BY
                    
            CAST(analyzed_table.`col_event_timestamp` AS DATE),
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            CAST([col_event_timestamp] AS date),
            CAST((CAST([col_event_timestamp] AS date)) AS DATETIME)
                    
                ) AS actual_value,
            CAST([col_event_timestamp] AS date) AS time_period,
            CAST((CAST([col_event_timestamp] AS date)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **daily partition percentile 10 in range**  
  
**Check description**  
Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_percentile_10_in_range|partitioned|daily|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_partition_percentile_10_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_partition_percentile_10_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_partition_percentile_10_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_partition_percentile_10_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_percentile_10_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          numeric:
            daily_partition_percentile_10_in_range:
              parameters:
                percentile_value: 0.1
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            daily_partition_percentile_10_in_range:
              parameters:
                percentile_value: 0.1
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.1)
            OVER (PARTITION BY
                
        CAST(analyzed_table.`col_event_timestamp` AS DATE),
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
                
            ) AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        CAST([col_event_timestamp] AS date),
        CAST((CAST([col_event_timestamp] AS date)) AS DATETIME)
                
            ) AS actual_value,
        CAST([col_event_timestamp] AS date) AS time_period,
        CAST((CAST([col_event_timestamp] AS date)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                daily_partition_percentile_10_in_range:
                  parameters:
                    percentile_value: 0.1
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.1)
                OVER (PARTITION BY
                    
            CAST(analyzed_table.`col_event_timestamp` AS DATE),
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.1)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            CAST([col_event_timestamp] AS date),
            CAST((CAST([col_event_timestamp] AS date)) AS DATETIME)
                    
                ) AS actual_value,
            CAST([col_event_timestamp] AS date) AS time_period,
            CAST((CAST([col_event_timestamp] AS date)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **daily partition percentile 25 in range**  
  
**Check description**  
Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_percentile_25_in_range|partitioned|daily|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_partition_percentile_25_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_partition_percentile_25_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_partition_percentile_25_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_partition_percentile_25_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_percentile_25_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          numeric:
            daily_partition_percentile_25_in_range:
              parameters:
                percentile_value: 0.25
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            daily_partition_percentile_25_in_range:
              parameters:
                percentile_value: 0.25
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.25)
            OVER (PARTITION BY
                
        CAST(analyzed_table.`col_event_timestamp` AS DATE),
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
                
            ) AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        CAST([col_event_timestamp] AS date),
        CAST((CAST([col_event_timestamp] AS date)) AS DATETIME)
                
            ) AS actual_value,
        CAST([col_event_timestamp] AS date) AS time_period,
        CAST((CAST([col_event_timestamp] AS date)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                daily_partition_percentile_25_in_range:
                  parameters:
                    percentile_value: 0.25
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.25)
                OVER (PARTITION BY
                    
            CAST(analyzed_table.`col_event_timestamp` AS DATE),
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.25)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            CAST([col_event_timestamp] AS date),
            CAST((CAST([col_event_timestamp] AS date)) AS DATETIME)
                    
                ) AS actual_value,
            CAST([col_event_timestamp] AS date) AS time_period,
            CAST((CAST([col_event_timestamp] AS date)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **daily partition percentile 75 in range**  
  
**Check description**  
Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_percentile_75_in_range|partitioned|daily|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_partition_percentile_75_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_partition_percentile_75_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_partition_percentile_75_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_partition_percentile_75_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_percentile_75_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          numeric:
            daily_partition_percentile_75_in_range:
              parameters:
                percentile_value: 0.75
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            daily_partition_percentile_75_in_range:
              parameters:
                percentile_value: 0.75
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.75)
            OVER (PARTITION BY
                
        CAST(analyzed_table.`col_event_timestamp` AS DATE),
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
                
            ) AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        CAST([col_event_timestamp] AS date),
        CAST((CAST([col_event_timestamp] AS date)) AS DATETIME)
                
            ) AS actual_value,
        CAST([col_event_timestamp] AS date) AS time_period,
        CAST((CAST([col_event_timestamp] AS date)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                daily_partition_percentile_75_in_range:
                  parameters:
                    percentile_value: 0.75
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.75)
                OVER (PARTITION BY
                    
            CAST(analyzed_table.`col_event_timestamp` AS DATE),
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.75)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            CAST([col_event_timestamp] AS date),
            CAST((CAST([col_event_timestamp] AS date)) AS DATETIME)
                    
                ) AS actual_value,
            CAST([col_event_timestamp] AS date) AS time_period,
            CAST((CAST([col_event_timestamp] AS date)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **daily partition percentile 90 in range**  
  
**Check description**  
Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_percentile_90_in_range|partitioned|daily|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=daily_partition_percentile_90_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=daily_partition_percentile_90_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=daily_partition_percentile_90_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=daily_partition_percentile_90_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_percentile_90_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          numeric:
            daily_partition_percentile_90_in_range:
              parameters:
                percentile_value: 0.9
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            daily_partition_percentile_90_in_range:
              parameters:
                percentile_value: 0.9
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.9)
            OVER (PARTITION BY
                
        CAST(analyzed_table.`col_event_timestamp` AS DATE),
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
                
            ) AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        CAST([col_event_timestamp] AS date),
        CAST((CAST([col_event_timestamp] AS date)) AS DATETIME)
                
            ) AS actual_value,
        CAST([col_event_timestamp] AS date) AS time_period,
        CAST((CAST([col_event_timestamp] AS date)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                daily_partition_percentile_90_in_range:
                  parameters:
                    percentile_value: 0.9
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.9)
                OVER (PARTITION BY
                    
            CAST(analyzed_table.`col_event_timestamp` AS DATE),
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            CAST([col_event_timestamp] AS date),
            CAST((CAST([col_event_timestamp] AS date)) AS DATETIME)
                    
                ) AS actual_value,
            CAST([col_event_timestamp] AS date) AS time_period,
            CAST((CAST([col_event_timestamp] AS date)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **monthly partition percentile in range**  
  
**Check description**  
Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_percentile_in_range|partitioned|monthly|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_partition_percentile_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_partition_percentile_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_partition_percentile_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_partition_percentile_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_percentile_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          numeric:
            monthly_partition_percentile_in_range:
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            monthly_partition_percentile_in_range:
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            )
            OVER (PARTITION BY
                
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH),
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
                
            ) AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT()
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT()
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT()
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1),
        CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME)
                
            ) AS actual_value,
        DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1) AS time_period,
        CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                monthly_partition_percentile_in_range:
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                )
                OVER (PARTITION BY
                    
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH),
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT()
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT()
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1),
            CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME)
                    
                ) AS actual_value,
            DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1) AS time_period,
            CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **monthly partition median in range**  
  
**Check description**  
Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_median_in_range|partitioned|monthly|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_partition_median_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_partition_median_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_partition_median_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_partition_median_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_median_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          numeric:
            monthly_partition_median_in_range:
              parameters:
                percentile_value: 0.5
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            monthly_partition_median_in_range:
              parameters:
                percentile_value: 0.5
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.5)
            OVER (PARTITION BY
                
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH),
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
                
            ) AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1),
        CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME)
                
            ) AS actual_value,
        DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1) AS time_period,
        CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                monthly_partition_median_in_range:
                  parameters:
                    percentile_value: 0.5
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.5)
                OVER (PARTITION BY
                    
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH),
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1),
            CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME)
                    
                ) AS actual_value,
            DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1) AS time_period,
            CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **monthly partition percentile 10 in range**  
  
**Check description**  
Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_percentile_10_in_range|partitioned|monthly|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_partition_percentile_10_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_partition_percentile_10_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_partition_percentile_10_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_partition_percentile_10_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_percentile_10_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          numeric:
            monthly_partition_percentile_10_in_range:
              parameters:
                percentile_value: 0.1
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            monthly_partition_percentile_10_in_range:
              parameters:
                percentile_value: 0.1
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.1)
            OVER (PARTITION BY
                
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH),
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
                
            ) AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.1)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1),
        CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME)
                
            ) AS actual_value,
        DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1) AS time_period,
        CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                monthly_partition_percentile_10_in_range:
                  parameters:
                    percentile_value: 0.1
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.1)
                OVER (PARTITION BY
                    
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH),
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.1)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.1)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1),
            CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME)
                    
                ) AS actual_value,
            DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1) AS time_period,
            CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **monthly partition percentile 25 in range**  
  
**Check description**  
Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_percentile_25_in_range|partitioned|monthly|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_partition_percentile_25_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_partition_percentile_25_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_partition_percentile_25_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_partition_percentile_25_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_percentile_25_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          numeric:
            monthly_partition_percentile_25_in_range:
              parameters:
                percentile_value: 0.25
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            monthly_partition_percentile_25_in_range:
              parameters:
                percentile_value: 0.25
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.25)
            OVER (PARTITION BY
                
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH),
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
                
            ) AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.25)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1),
        CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME)
                
            ) AS actual_value,
        DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1) AS time_period,
        CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                monthly_partition_percentile_25_in_range:
                  parameters:
                    percentile_value: 0.25
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.25)
                OVER (PARTITION BY
                    
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH),
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.25)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.25)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1),
            CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME)
                    
                ) AS actual_value,
            DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1) AS time_period,
            CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **monthly partition percentile 75 in range**  
  
**Check description**  
Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_percentile_75_in_range|partitioned|monthly|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_partition_percentile_75_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_partition_percentile_75_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_partition_percentile_75_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_partition_percentile_75_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_percentile_75_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          numeric:
            monthly_partition_percentile_75_in_range:
              parameters:
                percentile_value: 0.75
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            monthly_partition_percentile_75_in_range:
              parameters:
                percentile_value: 0.75
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.75)
            OVER (PARTITION BY
                
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH),
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
                
            ) AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.75)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1),
        CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME)
                
            ) AS actual_value,
        DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1) AS time_period,
        CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                monthly_partition_percentile_75_in_range:
                  parameters:
                    percentile_value: 0.75
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.75)
                OVER (PARTITION BY
                    
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH),
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.75)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.75)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1),
            CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME)
                    
                ) AS actual_value,
            DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1) AS time_period,
            CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___

## **monthly partition percentile 90 in range**  
  
**Check description**  
Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_percentile_90_in_range|partitioned|monthly|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo.ai> check enable -c=connection_name -ch=monthly_partition_percentile_90_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo.ai> check run -ch=monthly_partition_percentile_90_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo.ai> check run -c=connection_name -ch=monthly_partition_percentile_90_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -ch=monthly_partition_percentile_90_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo.ai> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_percentile_90_in_range
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          numeric:
            monthly_partition_percentile_90_in_range:
              parameters:
                percentile_value: 0.9
              warning:
                from: 10.0
                to: 20.5
              error:
                from: 10.0
                to: 20.5
              fatal:
                from: 10.0
                to: 20.5
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
          numeric:
            monthly_partition_percentile_90_in_range:
              parameters:
                percentile_value: 0.9
              warning:
                from: 10.0
                to: 20.5
              error:
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
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
            {%- for attribute in lib.data_streams -%}
                {%- with data_stream_level = lib.data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(
            (analyzed_table.`target_column`),
            0.9)
            OVER (PARTITION BY
                
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH),
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
                
            ) AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        PERCENTILE_CONT(0.9)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_streams is not none and (data_streams | length()) > 0 -%}
            {%- for attribute in data_streams -%}
                {%- with data_stream_level = data_streams[attribute] -%}
                    {%- if data_stream_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                    {%- elif data_stream_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS stream_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_stream_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_stream_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1),
        CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME)
                
            ) AS actual_value,
        DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1) AS time_period,
        CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME) AS time_period_utc
        FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
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
              numeric:
                monthly_partition_percentile_90_in_range:
                  parameters:
                    percentile_value: 0.9
                  warning:
                    from: 10.0
                    to: 20.5
                  error:
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
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_streams is not none and (lib.data_streams | length()) > 0 -%}
                {%- for attribute in lib.data_streams -%}
                    {%- with data_stream_level = lib.data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.9)
                OVER (PARTITION BY
                    
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH),
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH))
                    
            analyzed_table.`country` AS stream_level_1
            analyzed_table.`state` AS stream_level_2
                ) AS actual_value,
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
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
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_stream_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_streams is not none and (data_streams | length()) > 0 -%}
                {%- for attribute in data_streams -%}
                    {%- with data_stream_level = data_streams[attribute] -%}
                        {%- if data_stream_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_stream_level.tag) }}
                        {%- elif data_stream_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_stream_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS stream_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_stream_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_stream_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_stream_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1),
            CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME)
                    
                ) AS actual_value,
            DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1) AS time_period,
            CAST((DATEFROMPARTS(YEAR(CAST([col_event_timestamp] AS date)), MONTH(CAST([col_event_timestamp] AS date)), 1)) AS DATETIME) AS time_period_utc
            FROM [].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2
        ```
    





___
