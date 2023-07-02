**median change 7 days** checks  

**Description**  
Column level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout from last week.

___

## **median change 7 days**  
  
**Check description**  
Verifies that the median in a column changed in a fixed rate since last readout from last week.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|median_change_7_days|profiling| |[percentile](../../../../reference/sensors/Column/numeric-column-sensors/#percentile)|[within_change_7_days](../../../../reference/rules/Change/#within-change-7-days)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=median_change_7_days
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=median_change_7_days
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=median_change_7_days
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=median_change_7_days
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=median_change_7_days
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        anomaly:
          median_change_7_days:
            parameters:
              percentile_value: 0.5
            warning:
              max_within: 10.0
              exact: false
            error:
              max_within: 10.0
              exact: false
            fatal:
              max_within: 10.0
              exact: false
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
        anomaly:
          median_change_7_days:
            parameters:
              percentile_value: 0.5
            warning:
              max_within: 10.0
              exact: false
            error:
              max_within: 10.0
              exact: false
            fatal:
              max_within: 10.0
              exact: false
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
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS grouping_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_grouping_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_grouping_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
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
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
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
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
        TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_groupings is not none and (data_groupings | length()) > 0 -%}
            {%- for attribute in data_groupings -%}
                {%- with data_grouping_level = data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_grouping_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS grouping_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_grouping_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_grouping_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_grouping_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_grouping_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
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
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="0-0 43-48"
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
      groupings:
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
            anomaly:
              median_change_7_days:
                parameters:
                  percentile_value: 0.5
                warning:
                  max_within: 10.0
                  exact: false
                error:
                  max_within: 10.0
                  exact: false
                fatal:
                  max_within: 10.0
                  exact: false
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
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS grouping_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_grouping_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_grouping_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.5)
                OVER (PARTITION BY
                    
            CURRENT_TIMESTAMP(),
            TIMESTAMP(CURRENT_TIMESTAMP())
                    
            analyzed_table.`country` AS grouping_level_1
            analyzed_table.`state` AS grouping_level_2
                ) AS actual_value,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
            TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_groupings is not none and (data_groupings | length()) > 0 -%}
                {%- for attribute in data_groupings -%}
                    {%- with data_grouping_level = data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_grouping_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS grouping_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_grouping_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_grouping_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_grouping_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_grouping_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2
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
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2
        ```
    





___

## **daily median change 7 days**  
  
**Check description**  
Verifies that the median in a column changed in a fixed rate since last readout from last week.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_median_change_7_days|recurring|daily|[percentile](../../../../reference/sensors/Column/numeric-column-sensors/#percentile)|[within_change_7_days](../../../../reference/rules/Change/#within-change-7-days)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_median_change_7_days
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_median_change_7_days
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_median_change_7_days
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_median_change_7_days
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_median_change_7_days
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        daily:
          anomaly:
            daily_median_change_7_days:
              parameters:
                percentile_value: 0.5
              warning:
                max_within: 10.0
                exact: false
              error:
                max_within: 10.0
                exact: false
              fatal:
                max_within: 10.0
                exact: false
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
          anomaly:
            daily_median_change_7_days:
              parameters:
                percentile_value: 0.5
              warning:
                max_within: 10.0
                exact: false
              error:
                max_within: 10.0
                exact: false
              fatal:
                max_within: 10.0
                exact: false
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
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS grouping_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_grouping_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_grouping_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
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
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
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
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
        TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_groupings is not none and (data_groupings | length()) > 0 -%}
            {%- for attribute in data_groupings -%}
                {%- with data_grouping_level = data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_grouping_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS grouping_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_grouping_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_grouping_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_grouping_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_grouping_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
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
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="0-0 44-49"
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
      groupings:
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
              anomaly:
                daily_median_change_7_days:
                  parameters:
                    percentile_value: 0.5
                  warning:
                    max_within: 10.0
                    exact: false
                  error:
                    max_within: 10.0
                    exact: false
                  fatal:
                    max_within: 10.0
                    exact: false
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
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS grouping_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_grouping_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_grouping_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.5)
                OVER (PARTITION BY
                    
            CAST(CURRENT_TIMESTAMP() AS DATE),
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                    
            analyzed_table.`country` AS grouping_level_1
            analyzed_table.`state` AS grouping_level_2
                ) AS actual_value,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
            TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_groupings is not none and (data_groupings | length()) > 0 -%}
                {%- for attribute in data_groupings -%}
                    {%- with data_grouping_level = data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_grouping_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS grouping_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_grouping_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_grouping_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_grouping_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_grouping_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2
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
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2
        ```
    





___

## **daily partition median change 7 days**  
  
**Check description**  
Verifies that the median in a column changed in a fixed rate since last readout from last week.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_median_change_7_days|partitioned|daily|[percentile](../../../../reference/sensors/Column/numeric-column-sensors/#percentile)|[within_change_7_days](../../../../reference/rules/Change/#within-change-7-days)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_partition_median_change_7_days
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_partition_median_change_7_days
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_partition_median_change_7_days
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_partition_median_change_7_days
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_median_change_7_days
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          anomaly:
            daily_partition_median_change_7_days:
              parameters:
                percentile_value: 0.5
              warning:
                max_within: 10.0
                exact: false
              error:
                max_within: 10.0
                exact: false
              fatal:
                max_within: 10.0
                exact: false
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
          anomaly:
            daily_partition_median_change_7_days:
              parameters:
                percentile_value: 0.5
              warning:
                max_within: 10.0
                exact: false
              error:
                max_within: 10.0
                exact: false
              fatal:
                max_within: 10.0
                exact: false
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
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.time_series is not none -%}
            {{- lib.eol() -}}
            {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS grouping_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.`time_period` AS time_period,
        nested_table.`time_period_utc` AS time_period_utc
        {{- lib.render_data_grouping_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT(
            ({{ lib.render_target_column('analyzed_table')}}),
            {{ parameters.percentile_value }})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_grouping_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
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
                
        CAST(analyzed_table.`` AS DATE),
        TIMESTAMP(CAST(analyzed_table.`` AS DATE))
                
            ) AS actual_value,
        CAST(analyzed_table.`` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        CAST(analyzed_table."" AS date) AS time_period,
        CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        CAST(analyzed_table."" AS date) AS time_period,
        CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        CAST(analyzed_table."" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if time_series is not none -%}
            {{- lib.eol() -}}
            {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
            {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
        {%- endif -%}
    {%- endmacro -%}
    
    {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
        {%- if data_groupings is not none and (data_groupings | length()) > 0 -%}
            {%- for attribute in data_groupings -%}
                {%- with data_grouping_level = data_groupings[attribute] -%}
                    {%- if data_grouping_level.source == 'tag' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                    {%- elif data_grouping_level.source == 'column_value' -%}
                        {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_grouping_level.column) }}
                    {%- endif -%}
                {%- endwith %} AS grouping_{{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
        {{- lib.render_data_grouping_projections('analyzed_table') }}
    FROM(
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
            OVER (PARTITION BY
                {{render_local_time_dimension_projection('analyzed_table')}}
                {{render_local_data_grouping_projections('analyzed_table') }}
            ) AS actual_value
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_grouping_projections('analyzed_table') }}
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_grouping_projections('analyzed_table') }}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        MAX(nested_table.actual_value) AS actual_value,
        nested_table.[time_period] AS time_period,
        nested_table.[time_period_utc] AS time_period_utc
    FROM(
        SELECT
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table.[target_column])
            OVER (PARTITION BY
                
        CAST([] AS date),
        CAST((CAST([] AS date)) AS DATETIME)
                
            ) AS actual_value,
        CAST([] AS date) AS time_period,
        CAST((CAST([] AS date)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="0-0 44-49"
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
      groupings:
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
              anomaly:
                daily_partition_median_change_7_days:
                  parameters:
                    percentile_value: 0.5
                  warning:
                    max_within: 10.0
                    exact: false
                  error:
                    max_within: 10.0
                    exact: false
                  fatal:
                    max_within: 10.0
                    exact: false
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
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.time_series is not none -%}
                {{- lib.eol() -}}
                {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                {%- for attribute in lib.data_groupings -%}
                    {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS grouping_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
            {{- lib.render_data_grouping_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT(
                ({{ lib.render_target_column('analyzed_table')}}),
                {{ parameters.percentile_value }})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_grouping_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(
                (analyzed_table.`target_column`),
                0.5)
                OVER (PARTITION BY
                    
            CAST(analyzed_table.`` AS DATE),
            TIMESTAMP(CAST(analyzed_table.`` AS DATE))
                    
            analyzed_table.`country` AS grouping_level_1
            analyzed_table.`state` AS grouping_level_2
                ) AS actual_value,
            CAST(analyzed_table.`` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`` AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(analyzed_table."" AS date) AS time_period,
            CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(analyzed_table."" AS date) AS time_period,
            CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(analyzed_table."" AS date) AS time_period,
            TO_TIMESTAMP(CAST(analyzed_table."" AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        
        {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if time_series is not none -%}
                {{- lib.eol() -}}
                {{- indentation -}}{{- lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                {{- indentation -}}CAST(({{- lib.render_time_dimension_expression(table_alias_prefix) }}) AS DATETIME)
            {%- endif -%}
        {%- endmacro -%}
        
        {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
            {%- if data_groupings is not none and (data_groupings | length()) > 0 -%}
                {%- for attribute in data_groupings -%}
                    {%- with data_grouping_level = data_groupings[attribute] -%}
                        {%- if data_grouping_level.source == 'tag' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                        {%- elif data_grouping_level.source == 'column_value' -%}
                            {{- lib.eol() -}}{{ indentation }}{{ table_alias_prefix }}.{{ quote_identifier(data_grouping_level.column) }}
                        {%- endif -%}
                    {%- endwith %} AS grouping_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
        {%- endmacro -%}
        
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc
            {{- lib.render_data_grouping_projections('analyzed_table') }}
        FROM(
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                OVER (PARTITION BY
                    {{render_local_time_dimension_projection('analyzed_table')}}
                    {{render_local_data_grouping_projections('analyzed_table') }}
                ) AS actual_value
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_grouping_projections('analyzed_table') }}
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc] {{- lib.render_data_grouping_projections('analyzed_table') }}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.[time_period] AS time_period,
            nested_table.[time_period_utc] AS time_period_utc,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2
        FROM(
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            CAST([] AS date),
            CAST((CAST([] AS date)) AS DATETIME)
                    
                ) AS actual_value,
            CAST([] AS date) AS time_period,
            CAST((CAST([] AS date)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2
        ```
    





___
