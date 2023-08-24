**median change 30 days** checks  

**Description**  
Column level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout from last month.

___

## **profile median change 30 days**  
  
**Check description**  
Verifies that the median in a column changed in a fixed rate since last readout from last month.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|profile_median_change_30_days|profiling| |[percentile](../../../../reference/sensors/Column/numeric-column-sensors/#percentile)|[change_percent_30_days](../../../../reference/rules/Change/#change-percent-30-days)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=profile_median_change_30_days
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=profile_median_change_30_days
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=profile_median_change_30_days
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=profile_median_change_30_days
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=profile_median_change_30_days
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        anomaly:
          profile_median_change_30_days:
            parameters:
              percentile_value: 0.5
            warning:
              max_percent: 5.0
              exact_day: false
            error:
              max_percent: 5.0
              exact_day: false
            fatal:
              max_percent: 5.0
              exact_day: false
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
          profile_median_change_30_days:
            parameters:
              percentile_value: 0.5
            warning:
              max_percent: 5.0
              exact_day: false
            error:
              max_percent: 5.0
              exact_day: false
            fatal:
              max_percent: 5.0
              exact_day: false
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
                
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH),
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                
            ) AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}
    ) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Oracle"
      
    ```sql
    SELECT
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        time_period,
        time_period_utc
    FROM(
        SELECT
            original_table.*,
        TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
        CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "<target_schema>"."<target_table>" original_table
    ) analyzed_table
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
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
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
                
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0),
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME)
                
            ) AS actual_value,
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 44-49"
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
      default_grouping_name: group_by_country_and_state
      groupings:
        group_by_country_and_state:
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
              profile_median_change_30_days:
                parameters:
                  percentile_value: 0.5
                warning:
                  max_percent: 5.0
                  exact_day: false
                error:
                  max_percent: 5.0
                  exact_day: false
                fatal:
                  max_percent: 5.0
                  exact_day: false
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
                    
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH),
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH))
                    
            analyzed_table.`country` AS grouping_level_1
            analyzed_table.`state` AS grouping_level_2
                ) AS actual_value,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM(
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}
        ) analyzed_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"
        ```sql
        SELECT
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        
                        analyzed_table.grouping_level_1,
        
                        analyzed_table.grouping_level_2
        ,
            time_period,
            time_period_utc
        FROM(
            SELECT
                original_table.*,
            original_table."country" AS grouping_level_1,
            original_table."state" AS grouping_level_2,
            TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
            CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "<target_schema>"."<target_table>" original_table
        ) analyzed_table
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
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
            DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
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
                    
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0),
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME)
                    
                ) AS actual_value,
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2
        ```
    






___

## **daily median change 30 days**  
  
**Check description**  
Verifies that the median in a column changed in a fixed rate since last readout from last month.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_median_change_30_days|monitoring|daily|[percentile](../../../../reference/sensors/Column/numeric-column-sensors/#percentile)|[change_percent_30_days](../../../../reference/rules/Change/#change-percent-30-days)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_median_change_30_days
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_median_change_30_days
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_median_change_30_days
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_median_change_30_days
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_median_change_30_days
```
**Check structure (Yaml)**
```yaml
      monitoring_checks:
        daily:
          anomaly:
            daily_median_change_30_days:
              parameters:
                percentile_value: 0.5
              warning:
                max_percent: 5.0
                exact_day: false
              error:
                max_percent: 5.0
                exact_day: false
              fatal:
                max_percent: 5.0
                exact_day: false
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
      monitoring_checks:
        daily:
          anomaly:
            daily_median_change_30_days:
              parameters:
                percentile_value: 0.5
              warning:
                max_percent: 5.0
                exact_day: false
              error:
                max_percent: 5.0
                exact_day: false
              fatal:
                max_percent: 5.0
                exact_day: false
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
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}
    ) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Oracle"
      
    ```sql
    SELECT
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        time_period,
        time_period_utc
    FROM(
        SELECT
            original_table.*,
        TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS time_period,
        CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "<target_schema>"."<target_table>" original_table
    ) analyzed_table
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

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 45-50"
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
      default_grouping_name: group_by_country_and_state
      groupings:
        group_by_country_and_state:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      columns:
        target_column:
          monitoring_checks:
            daily:
              anomaly:
                daily_median_change_30_days:
                  parameters:
                    percentile_value: 0.5
                  warning:
                    max_percent: 5.0
                    exact_day: false
                  error:
                    max_percent: 5.0
                    exact_day: false
                  fatal:
                    max_percent: 5.0
                    exact_day: false
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
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM(
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}
        ) analyzed_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"
        ```sql
        SELECT
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        
                        analyzed_table.grouping_level_1,
        
                        analyzed_table.grouping_level_2
        ,
            time_period,
            time_period_utc
        FROM(
            SELECT
                original_table.*,
            original_table."country" AS grouping_level_1,
            original_table."state" AS grouping_level_2,
            TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS time_period,
            CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "<target_schema>"."<target_table>" original_table
        ) analyzed_table
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

## **daily partition median change 30 days**  
  
**Check description**  
Verifies that the median in a column changed in a fixed rate since last readout from last month.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_median_change_30_days|partitioned|daily|[percentile](../../../../reference/sensors/Column/numeric-column-sensors/#percentile)|[change_percent_30_days](../../../../reference/rules/Change/#change-percent-30-days)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_partition_median_change_30_days
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_partition_median_change_30_days
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_partition_median_change_30_days
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_partition_median_change_30_days
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_median_change_30_days
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          anomaly:
            daily_partition_median_change_30_days:
              parameters:
                percentile_value: 0.5
              warning:
                max_percent: 5.0
                exact_day: false
              error:
                max_percent: 5.0
                exact_day: false
              fatal:
                max_percent: 5.0
                exact_day: false
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
            daily_partition_median_change_30_days:
              parameters:
                percentile_value: 0.5
              warning:
                max_percent: 5.0
                exact_day: false
              error:
                max_percent: 5.0
                exact_day: false
              fatal:
                max_percent: 5.0
                exact_day: false
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
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        PERCENTILE_CONT({{ parameters.percentile_value }})
        WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}
    ) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Oracle"
      
    ```sql
    SELECT
        PERCENTILE_CONT(0.5)
        WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        time_period,
        time_period_utc
    FROM(
        SELECT
            original_table.*,
        TRUNC(CAST(original_table."" AS DATE)) AS time_period,
        CAST(TRUNC(CAST(original_table."" AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "<target_schema>"."<target_table>" original_table
    ) analyzed_table
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
                
        CAST(analyzed_table.[] AS date),
        CAST((CAST(analyzed_table.[] AS date)) AS DATETIME)
                
            ) AS actual_value,
        CAST(analyzed_table.[] AS date) AS time_period,
        CAST((CAST(analyzed_table.[] AS date)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
    GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
    ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 45-50"
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
      default_grouping_name: group_by_country_and_state
      groupings:
        group_by_country_and_state:
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
                daily_partition_median_change_30_days:
                  parameters:
                    percentile_value: 0.5
                  warning:
                    max_percent: 5.0
                    exact_day: false
                  error:
                    max_percent: 5.0
                    exact_day: false
                  fatal:
                    max_percent: 5.0
                    exact_day: false
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
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        
        SELECT
            PERCENTILE_CONT({{ parameters.percentile_value }})
            WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM(
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}
        ) analyzed_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"
        ```sql
        SELECT
            PERCENTILE_CONT(0.5)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
        
                        analyzed_table.grouping_level_1,
        
                        analyzed_table.grouping_level_2
        ,
            time_period,
            time_period_utc
        FROM(
            SELECT
                original_table.*,
            original_table."country" AS grouping_level_1,
            original_table."state" AS grouping_level_2,
            TRUNC(CAST(original_table."" AS DATE)) AS time_period,
            CAST(TRUNC(CAST(original_table."" AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "<target_schema>"."<target_table>" original_table
        ) analyzed_table
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
                    
            CAST(analyzed_table.[] AS date),
            CAST((CAST(analyzed_table.[] AS date)) AS DATETIME)
                    
                ) AS actual_value,
            CAST(analyzed_table.[] AS date) AS time_period,
            CAST((CAST(analyzed_table.[] AS date)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2
        ```
    






___
