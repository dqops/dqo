**percentile 90 in range** checks  

**Description**  
Column-level check that ensures that the percentile 90 of values in a monitored column is in a set range.

___

## **profile percentile 90 in range**  
  
**Check description**  
Verifies that the percentile 90 of all values in a column is not outside the set range.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|profile_percentile_90_in_range|profiling| |Reasonableness|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/Comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=profile_percentile_90_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=profile_percentile_90_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=profile_percentile_90_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_percentile_90_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -col=column_name -ch=profile_percentile_90_in_range
```
**Check structure (YAML)**
```yaml
      profiling_checks:
        numeric:
          profile_percentile_90_in_range:
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
**Sample configuration (YAML)**  
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.
  
```yaml hl_lines="13-26"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
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
          profile_percentile_90_in_range:
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

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

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
??? example "Oracle"

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
            PERCENTILE_CONT(0.9)
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
??? example "PostgreSQL"

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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Redshift"

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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Snowflake"

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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Spark"

    === "Sensor template for Spark"

        ```sql+jinja
        {% import '/dialects/spark.sql.jinja2' as lib with context -%}
        
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
                PERCENTILE(
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
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
        FROM(
            SELECT
                PERCENTILE(
                (analyzed_table.`target_column`),
                0.9)
                OVER (PARTITION BY
                    
            DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)),
            TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)))
                    
                ) AS actual_value,
            DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
            TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "SQL Server"

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
                PERCENTILE_CONT(0.9)
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

  
Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"
      
    **Sample configuration with data grouping enabled (YAML)**  
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="11-21 44-49"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
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
            numeric:
              profile_percentile_90_in_range:
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

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

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
                    0.9)
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
    ??? example "Oracle"

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
                PERCENTILE_CONT(0.9)
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
    ??? example "PostgreSQL"

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
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

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
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

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
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
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
                    PERCENTILE(
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM(
                SELECT
                    PERCENTILE(
                    (analyzed_table.`target_column`),
                    0.9)
                    OVER (PARTITION BY
                        
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)),
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)))
                        
                analyzed_table.`country` AS grouping_level_1
                analyzed_table.`state` AS grouping_level_2
                    ) AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
                FROM `<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

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
                    PERCENTILE_CONT(0.9)
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

## **daily percentile 90 in range**  
  
**Check description**  
Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_percentile_90_in_range|monitoring|daily|Reasonableness|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/Comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_percentile_90_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_percentile_90_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_percentile_90_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_percentile_90_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_percentile_90_in_range
```
**Check structure (YAML)**
```yaml
      monitoring_checks:
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
**Sample configuration (YAML)**  
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.
  
```yaml hl_lines="13-27"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
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

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

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
??? example "Oracle"

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
            PERCENTILE_CONT(0.9)
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
??? example "PostgreSQL"

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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Redshift"

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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Snowflake"

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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
            TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Spark"

    === "Sensor template for Spark"

        ```sql+jinja
        {% import '/dialects/spark.sql.jinja2' as lib with context -%}
        
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
                PERCENTILE(
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
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
        FROM(
            SELECT
                PERCENTILE(
                (analyzed_table.`target_column`),
                0.9)
                OVER (PARTITION BY
                    
            CAST(CURRENT_TIMESTAMP() AS DATE),
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                    
                ) AS actual_value,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "SQL Server"

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
                PERCENTILE_CONT(0.9)
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

  
Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"
      
    **Sample configuration with data grouping enabled (YAML)**  
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="11-21 45-50"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
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

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

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
                    0.9)
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
    ??? example "Oracle"

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
                PERCENTILE_CONT(0.9)
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
    ??? example "PostgreSQL"

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
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

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
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

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
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
                TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
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
                    PERCENTILE(
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM(
                SELECT
                    PERCENTILE(
                    (analyzed_table.`target_column`),
                    0.9)
                    OVER (PARTITION BY
                        
                CAST(CURRENT_TIMESTAMP() AS DATE),
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE))
                        
                analyzed_table.`country` AS grouping_level_1
                analyzed_table.`state` AS grouping_level_2
                    ) AS actual_value,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
                FROM `<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

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
                    PERCENTILE_CONT(0.9)
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

## **monthly percentile 90 in range**  
  
**Check description**  
Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_percentile_90_in_range|monitoring|monthly|Reasonableness|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/Comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_percentile_90_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_percentile_90_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_percentile_90_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_percentile_90_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_percentile_90_in_range
```
**Check structure (YAML)**
```yaml
      monitoring_checks:
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
**Sample configuration (YAML)**  
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.
  
```yaml hl_lines="13-27"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
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

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

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
??? example "Oracle"

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
            PERCENTILE_CONT(0.9)
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
??? example "PostgreSQL"

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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Redshift"

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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Snowflake"

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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Spark"

    === "Sensor template for Spark"

        ```sql+jinja
        {% import '/dialects/spark.sql.jinja2' as lib with context -%}
        
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
                PERCENTILE(
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
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
        FROM(
            SELECT
                PERCENTILE(
                (analyzed_table.`target_column`),
                0.9)
                OVER (PARTITION BY
                    
            DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)),
            TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)))
                    
                ) AS actual_value,
            DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
            TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "SQL Server"

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
                PERCENTILE_CONT(0.9)
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

  
Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"
      
    **Sample configuration with data grouping enabled (YAML)**  
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="11-21 45-50"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
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

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

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
                    0.9)
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
    ??? example "Oracle"

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
                PERCENTILE_CONT(0.9)
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
    ??? example "PostgreSQL"

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
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

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
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

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
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
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
                    PERCENTILE(
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM(
                SELECT
                    PERCENTILE(
                    (analyzed_table.`target_column`),
                    0.9)
                    OVER (PARTITION BY
                        
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)),
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)))
                        
                analyzed_table.`country` AS grouping_level_1
                analyzed_table.`state` AS grouping_level_2
                    ) AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
                FROM `<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

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
                    PERCENTILE_CONT(0.9)
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

## **daily partition percentile 90 in range**  
  
**Check description**  
Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_partition_percentile_90_in_range|partitioned|daily|Reasonableness|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/Comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_partition_percentile_90_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_partition_percentile_90_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_partition_percentile_90_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_partition_percentile_90_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_partition_percentile_90_in_range
```
**Check structure (YAML)**
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
**Sample configuration (YAML)**  
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.
  
```yaml hl_lines="14-28"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
    partition_by_column: date_column
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
    date_column:
      labels:
      - "date or datetime column used as a daily or monthly partitioning key, dates\
        \ (and times) are truncated to a day or a month by the sensor's query for\
        \ partitioned checks"

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

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
                0.9)
                OVER (PARTITION BY
                    
            CAST(analyzed_table.`date_column` AS DATE),
            TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE))
                    
                ) AS actual_value,
            CAST(analyzed_table.`date_column` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Oracle"

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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            time_period,
            time_period_utc
        FROM(
            SELECT
                original_table.*,
            TRUNC(CAST(original_table."date_column" AS DATE)) AS time_period,
            CAST(TRUNC(CAST(original_table."date_column" AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "<target_schema>"."<target_table>" original_table
        ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "PostgreSQL"

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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            CAST(analyzed_table."date_column" AS date) AS time_period,
            CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Redshift"

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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            CAST(analyzed_table."date_column" AS date) AS time_period,
            CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Snowflake"

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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            CAST(analyzed_table."date_column" AS date) AS time_period,
            TO_TIMESTAMP(CAST(analyzed_table."date_column" AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Spark"

    === "Sensor template for Spark"

        ```sql+jinja
        {% import '/dialects/spark.sql.jinja2' as lib with context -%}
        
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
                PERCENTILE(
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
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
        FROM(
            SELECT
                PERCENTILE(
                (analyzed_table.`target_column`),
                0.9)
                OVER (PARTITION BY
                    
            CAST(analyzed_table.`date_column` AS DATE),
            TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE))
                    
                ) AS actual_value,
            CAST(analyzed_table.`date_column` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "SQL Server"

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
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            CAST(analyzed_table.[date_column] AS date),
            CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME)
                    
                ) AS actual_value,
            CAST(analyzed_table.[date_column] AS date) AS time_period,
            CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
        ```

  
Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"
      
    **Sample configuration with data grouping enabled (YAML)**  
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="12-22 51-56"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
        partition_by_column: date_column
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
        date_column:
          labels:
          - "date or datetime column used as a daily or monthly partitioning key, dates\
            \ (and times) are truncated to a day or a month by the sensor's query for\
            \ partitioned checks"
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

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
                    0.9)
                    OVER (PARTITION BY
                        
                CAST(analyzed_table.`date_column` AS DATE),
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE))
                        
                analyzed_table.`country` AS grouping_level_1
                analyzed_table.`state` AS grouping_level_2
                    ) AS actual_value,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
                FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

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
                PERCENTILE_CONT(0.9)
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
                TRUNC(CAST(original_table."date_column" AS DATE)) AS time_period,
                CAST(TRUNC(CAST(original_table."date_column" AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

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
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

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
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

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
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                TO_TIMESTAMP(CAST(analyzed_table."date_column" AS date)) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
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
                    PERCENTILE(
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM(
                SELECT
                    PERCENTILE(
                    (analyzed_table.`target_column`),
                    0.9)
                    OVER (PARTITION BY
                        
                CAST(analyzed_table.`date_column` AS DATE),
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE))
                        
                analyzed_table.`country` AS grouping_level_1
                analyzed_table.`state` AS grouping_level_2
                    ) AS actual_value,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
                FROM `<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

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
                    PERCENTILE_CONT(0.9)
                    WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                    OVER (PARTITION BY
                        
                CAST(analyzed_table.[date_column] AS date),
                CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME)
                        
                    ) AS actual_value,
                CAST(analyzed_table.[date_column] AS date) AS time_period,
                CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
                FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
            GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2
            ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2
            ```
    






___

## **monthly partition percentile 90 in range**  
  
**Check description**  
Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_partition_percentile_90_in_range|partitioned|monthly|Reasonableness|[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)|[between_floats](../../../../reference/rules/Comparison/#between-floats)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_partition_percentile_90_in_range
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_partition_percentile_90_in_range
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_partition_percentile_90_in_range
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_partition_percentile_90_in_range
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_partition_percentile_90_in_range
```
**Check structure (YAML)**
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
**Sample configuration (YAML)**  
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.
  
```yaml hl_lines="14-28"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
    partition_by_column: date_column
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
    date_column:
      labels:
      - "date or datetime column used as a daily or monthly partitioning key, dates\
        \ (and times) are truncated to a day or a month by the sensor's query for\
        \ partitioned checks"

```

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

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
                0.9)
                OVER (PARTITION BY
                    
            DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH),
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH))
                    
                ) AS actual_value,
            DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Oracle"

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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            time_period,
            time_period_utc
        FROM(
            SELECT
                original_table.*,
            TRUNC(CAST(original_table."date_column" AS DATE), 'MONTH') AS time_period,
            CAST(TRUNC(CAST(original_table."date_column" AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "<target_schema>"."<target_table>" original_table
        ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "PostgreSQL"

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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Redshift"

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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Snowflake"

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
            PERCENTILE_CONT(0.9)
            WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Spark"

    === "Sensor template for Spark"

        ```sql+jinja
        {% import '/dialects/spark.sql.jinja2' as lib with context -%}
        
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
                PERCENTILE(
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
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            MAX(nested_table.actual_value) AS actual_value,
            nested_table.`time_period` AS time_period,
            nested_table.`time_period_utc` AS time_period_utc
        FROM(
            SELECT
                PERCENTILE(
                (analyzed_table.`target_column`),
                0.9)
                OVER (PARTITION BY
                    
            DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)),
            TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)))
                    
                ) AS actual_value,
            DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
            TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "SQL Server"

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
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                OVER (PARTITION BY
                    
            DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1),
            CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME)
                    
                ) AS actual_value,
            DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
            CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
        GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
        ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
        ```

  
Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"
      
    **Sample configuration with data grouping enabled (YAML)**  
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="12-22 51-56"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
        partition_by_column: date_column
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
        date_column:
          labels:
          - "date or datetime column used as a daily or monthly partitioning key, dates\
            \ (and times) are truncated to a day or a month by the sensor's query for\
            \ partitioned checks"
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [percentile](../../../../reference/sensors/column/numeric-column-sensors/#percentile)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

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
                    0.9)
                    OVER (PARTITION BY
                        
                DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH),
                TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH))
                        
                analyzed_table.`country` AS grouping_level_1
                analyzed_table.`state` AS grouping_level_2
                    ) AS actual_value,
                DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH)) AS time_period_utc
                FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

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
                PERCENTILE_CONT(0.9)
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
                TRUNC(CAST(original_table."date_column" AS DATE), 'MONTH') AS time_period,
                CAST(TRUNC(CAST(original_table."date_column" AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

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
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

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
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

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
                PERCENTILE_CONT(0.9)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
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
                    PERCENTILE(
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM(
                SELECT
                    PERCENTILE(
                    (analyzed_table.`target_column`),
                    0.9)
                    OVER (PARTITION BY
                        
                DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)),
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)))
                        
                analyzed_table.`country` AS grouping_level_1
                analyzed_table.`state` AS grouping_level_2
                    ) AS actual_value,
                DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
                FROM `<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

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
                    PERCENTILE_CONT(0.9)
                    WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                    OVER (PARTITION BY
                        
                DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1),
                CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME)
                        
                    ) AS actual_value,
                DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
                CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
                FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
            GROUP BY nested_table.[time_period], nested_table.[time_period_utc],
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2
            ORDER BY nested_table.[time_period], nested_table.[time_period_utc],
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2
            ```
    






___