# percentile 10 in range data quality checks

A column-level check that ensures that the 10th percentile of values in the monitored column is within the expected range.


___
The **percentile 10 in range** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile percentile 10 in range


**Check description**

Verifies that the percentile 10 of all values in a column is not outside the expected range.

|Data quality check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|<span class="no-wrap-code">`profile_percentile_10_in_range`</span>|profiling| |Reasonableness|[*percentile*](../../../reference/sensors/column/numeric-column-sensors.md#percentile)|[*between_floats*](../../../reference/rules/Comparison.md#between-floats)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the profile percentile 10 in range data quality check.

??? example "Managing profile percentile 10 in range check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=profile_percentile_10_in_range --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_percentile_10_in_range --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_percentile_10_in_range --enable-warning
                            -Wfrom=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=profile_percentile_10_in_range --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_percentile_10_in_range --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_percentile_10_in_range --enable-error
                            -Efrom=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *profile_percentile_10_in_range* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=profile_percentile_10_in_range
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_percentile_10_in_range
        ```

        You can also run this check on all tables (and columns)  on which the *profile_percentile_10_in_range* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=profile_percentile_10_in_range
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-14"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      profiling_checks:
        numeric:
          profile_percentile_10_in_range:
            parameters:
              percentile_value: 0.1
            error:
              from: 10.0
              to: 20.5
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [percentile](../../../reference/sensors/column/numeric-column-sensors.md#percentile)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

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
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
            FROM(
                SELECT
                    PERCENTILE(
                    (analyzed_table.`target_column`),
                    0.1)
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)),
                 DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date))
                        
                    ) AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
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
                    0.1)
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
                    PERCENTILE_CONT(0.1)
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
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)),
                 DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date))
                        
                    ) AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-15 26-31"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
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
              profile_percentile_10_in_range:
                parameters:
                  percentile_value: 0.1
                error:
                  from: 10.0
                  to: 20.5
          labels:
          - This is the column that is analyzed for data quality issues
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [percentile](../../../reference/sensors/column/numeric-column-sensors.md#percentile)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

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
                    0.1)
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
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
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
        === "Rendered SQL for Databricks"
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
                    0.1)
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)),
                 DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date))
                        
                analyzed_table."country" AS grouping_level_1
                analyzed_table."state" AS grouping_level_2
                    ) AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
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
                    0.1)
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
                    PERCENTILE_CONT(0.1)
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
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)),
                 DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date))
                        
                analyzed_table."country" AS grouping_level_1
                analyzed_table."state" AS grouping_level_2
                    ) AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    
___


## daily percentile 10 in range


**Check description**

Verifies that the percentile 10 of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.

|Data quality check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|<span class="no-wrap-code">`daily_percentile_10_in_range`</span>|monitoring|daily|Reasonableness|[*percentile*](../../../reference/sensors/column/numeric-column-sensors.md#percentile)|[*between_floats*](../../../reference/rules/Comparison.md#between-floats)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the daily percentile 10 in range data quality check.

??? example "Managing daily percentile 10 in range check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_percentile_10_in_range --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_percentile_10_in_range --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_percentile_10_in_range --enable-warning
                            -Wfrom=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_percentile_10_in_range --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_percentile_10_in_range --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_percentile_10_in_range --enable-error
                            -Efrom=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_percentile_10_in_range* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_percentile_10_in_range
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_percentile_10_in_range
        ```

        You can also run this check on all tables (and columns)  on which the *daily_percentile_10_in_range* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=daily_percentile_10_in_range
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-15"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      monitoring_checks:
        daily:
          numeric:
            daily_percentile_10_in_range:
              parameters:
                percentile_value: 0.1
              error:
                from: 10.0
                to: 20.5
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [percentile](../../../reference/sensors/column/numeric-column-sensors.md#percentile)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

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
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
            FROM(
                SELECT
                    PERCENTILE(
                    (analyzed_table.`target_column`),
                    0.1)
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                CAST(CURRENT_TIMESTAMP AS date),
                 CAST(CURRENT_TIMESTAMP AS date)
                        
                    ) AS actual_value,
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
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
                    0.1)
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
                    PERCENTILE_CONT(0.1)
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
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                CAST(CURRENT_TIMESTAMP AS date),
                 CAST(CURRENT_TIMESTAMP AS date)
                        
                    ) AS actual_value,
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-15 27-32"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
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
                daily_percentile_10_in_range:
                  parameters:
                    percentile_value: 0.1
                  error:
                    from: 10.0
                    to: 20.5
          labels:
          - This is the column that is analyzed for data quality issues
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [percentile](../../../reference/sensors/column/numeric-column-sensors.md#percentile)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

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
                    0.1)
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
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
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
        === "Rendered SQL for Databricks"
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
                    0.1)
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                CAST(CURRENT_TIMESTAMP AS date),
                 CAST(CURRENT_TIMESTAMP AS date)
                        
                analyzed_table."country" AS grouping_level_1
                analyzed_table."state" AS grouping_level_2
                    ) AS actual_value,
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
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
                    0.1)
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
                    PERCENTILE_CONT(0.1)
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
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                CAST(CURRENT_TIMESTAMP AS date),
                 CAST(CURRENT_TIMESTAMP AS date)
                        
                analyzed_table."country" AS grouping_level_1
                analyzed_table."state" AS grouping_level_2
                    ) AS actual_value,
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    
___


## monthly percentile 10 in range


**Check description**

Verifies that the percentile 10 of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.

|Data quality check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|<span class="no-wrap-code">`monthly_percentile_10_in_range`</span>|monitoring|monthly|Reasonableness|[*percentile*](../../../reference/sensors/column/numeric-column-sensors.md#percentile)|[*between_floats*](../../../reference/rules/Comparison.md#between-floats)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the monthly percentile 10 in range data quality check.

??? example "Managing monthly percentile 10 in range check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_percentile_10_in_range --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_percentile_10_in_range --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_percentile_10_in_range --enable-warning
                            -Wfrom=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_percentile_10_in_range --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_percentile_10_in_range --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_percentile_10_in_range --enable-error
                            -Efrom=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_percentile_10_in_range* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_percentile_10_in_range
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_percentile_10_in_range
        ```

        You can also run this check on all tables (and columns)  on which the *monthly_percentile_10_in_range* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=monthly_percentile_10_in_range
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-15"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      monitoring_checks:
        monthly:
          numeric:
            monthly_percentile_10_in_range:
              parameters:
                percentile_value: 0.1
              error:
                from: 10.0
                to: 20.5
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [percentile](../../../reference/sensors/column/numeric-column-sensors.md#percentile)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

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
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
            FROM(
                SELECT
                    PERCENTILE(
                    (analyzed_table.`target_column`),
                    0.1)
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)),
                 DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date))
                        
                    ) AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
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
                    0.1)
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
                    PERCENTILE_CONT(0.1)
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
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)),
                 DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date))
                        
                    ) AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-15 27-32"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
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
                monthly_percentile_10_in_range:
                  parameters:
                    percentile_value: 0.1
                  error:
                    from: 10.0
                    to: 20.5
          labels:
          - This is the column that is analyzed for data quality issues
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [percentile](../../../reference/sensors/column/numeric-column-sensors.md#percentile)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

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
                    0.1)
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
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
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
        === "Rendered SQL for Databricks"
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
                    0.1)
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)),
                 DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date))
                        
                analyzed_table."country" AS grouping_level_1
                analyzed_table."state" AS grouping_level_2
                    ) AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
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
                    0.1)
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
                    PERCENTILE_CONT(0.1)
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
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)),
                 DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date))
                        
                analyzed_table."country" AS grouping_level_1
                analyzed_table."state" AS grouping_level_2
                    ) AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    
___


## daily partition percentile 10 in range


**Check description**

Verifies that the percentile 10 of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.

|Data quality check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|<span class="no-wrap-code">`daily_partition_percentile_10_in_range`</span>|partitioned|daily|Reasonableness|[*percentile*](../../../reference/sensors/column/numeric-column-sensors.md#percentile)|[*between_floats*](../../../reference/rules/Comparison.md#between-floats)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the daily partition percentile 10 in range data quality check.

??? example "Managing daily partition percentile 10 in range check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_partition_percentile_10_in_range --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_percentile_10_in_range --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_percentile_10_in_range --enable-warning
                            -Wfrom=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_partition_percentile_10_in_range --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_percentile_10_in_range --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_percentile_10_in_range --enable-error
                            -Efrom=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_partition_percentile_10_in_range* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_partition_percentile_10_in_range
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_partition_percentile_10_in_range
        ```

        You can also run this check on all tables (and columns)  on which the *daily_partition_percentile_10_in_range* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=daily_partition_percentile_10_in_range
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="12-20"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    partition_by_column: date_column
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
              error:
                from: 10.0
                to: 20.5
      labels:
      - This is the column that is analyzed for data quality issues
    date_column:
      labels:
      - "date or datetime column used as a daily or monthly partitioning key, dates\
        \ (and times) are truncated to a day or a month by the sensor's query for\
        \ partitioned checks"

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [percentile](../../../reference/sensors/column/numeric-column-sensors.md#percentile)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

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
                    0.1)
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
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
            FROM(
                SELECT
                    PERCENTILE(
                    (analyzed_table.`target_column`),
                    0.1)
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                CAST(analyzed_table."date_column" AS date),
                 CAST(analyzed_table."date_column" AS date)
                        
                    ) AS actual_value,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST(CAST(analyzed_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
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
                    0.1)
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
                    PERCENTILE_CONT(0.1)
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
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                CAST(analyzed_table."date_column" AS date),
                 CAST(analyzed_table."date_column" AS date)
                        
                    ) AS actual_value,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST(CAST(analyzed_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="10-20 37-42"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
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
                daily_partition_percentile_10_in_range:
                  parameters:
                    percentile_value: 0.1
                  error:
                    from: 10.0
                    to: 20.5
          labels:
          - This is the column that is analyzed for data quality issues
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
    [percentile](../../../reference/sensors/column/numeric-column-sensors.md#percentile)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

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
                    0.1)
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
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
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
        === "Rendered SQL for Databricks"
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
                    0.1)
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                CAST(analyzed_table."date_column" AS date),
                 CAST(analyzed_table."date_column" AS date)
                        
                analyzed_table."country" AS grouping_level_1
                analyzed_table."state" AS grouping_level_2
                    ) AS actual_value,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST(CAST(analyzed_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
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
                    0.1)
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
                    PERCENTILE_CONT(0.1)
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
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                CAST(analyzed_table."date_column" AS date),
                 CAST(analyzed_table."date_column" AS date)
                        
                analyzed_table."country" AS grouping_level_1
                analyzed_table."state" AS grouping_level_2
                    ) AS actual_value,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST(CAST(analyzed_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    
___


## monthly partition percentile 10 in range


**Check description**

Verifies that the percentile 10 of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.

|Data quality check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|<span class="no-wrap-code">`monthly_partition_percentile_10_in_range`</span>|partitioned|monthly|Reasonableness|[*percentile*](../../../reference/sensors/column/numeric-column-sensors.md#percentile)|[*between_floats*](../../../reference/rules/Comparison.md#between-floats)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the monthly partition percentile 10 in range data quality check.

??? example "Managing monthly partition percentile 10 in range check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_partition_percentile_10_in_range --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_partition_percentile_10_in_range --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_partition_percentile_10_in_range --enable-warning
                            -Wfrom=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_partition_percentile_10_in_range --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_partition_percentile_10_in_range --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_partition_percentile_10_in_range --enable-error
                            -Efrom=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_partition_percentile_10_in_range* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_partition_percentile_10_in_range
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_partition_percentile_10_in_range
        ```

        You can also run this check on all tables (and columns)  on which the *monthly_partition_percentile_10_in_range* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=monthly_partition_percentile_10_in_range
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="12-20"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    partition_by_column: date_column
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
              error:
                from: 10.0
                to: 20.5
      labels:
      - This is the column that is analyzed for data quality issues
    date_column:
      labels:
      - "date or datetime column used as a daily or monthly partitioning key, dates\
        \ (and times) are truncated to a day or a month by the sensor's query for\
        \ partitioned checks"

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [percentile](../../../reference/sensors/column/numeric-column-sensors.md#percentile)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

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
                    0.1)
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
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
            FROM(
                SELECT
                    PERCENTILE(
                    (analyzed_table.`target_column`),
                    0.1)
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)),
                 DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))
                        
                    ) AS actual_value,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
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
                    0.1)
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
                    PERCENTILE_CONT(0.1)
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
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)),
                 DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))
                        
                    ) AS actual_value,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="10-20 37-42"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
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
                monthly_partition_percentile_10_in_range:
                  parameters:
                    percentile_value: 0.1
                  error:
                    from: 10.0
                    to: 20.5
          labels:
          - This is the column that is analyzed for data quality issues
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
    [percentile](../../../reference/sensors/column/numeric-column-sensors.md#percentile)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

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
                    0.1)
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
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
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
        === "Rendered SQL for Databricks"
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
                    0.1)
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)),
                 DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))
                        
                analyzed_table."country" AS grouping_level_1
                analyzed_table."state" AS grouping_level_2
                    ) AS actual_value,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
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
                PERCENTILE_CONT(0.1)
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
                PERCENTILE_CONT(0.1)
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
                    0.1)
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
                    PERCENTILE_CONT(0.1)
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
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '    ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
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
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }}
                    )
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM(
                SELECT
                   APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.1
                    )
                    OVER (PARTITION BY
                        
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)),
                 DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))
                        
                analyzed_table."country" AS grouping_level_1
                analyzed_table."state" AS grouping_level_2
                    ) AS actual_value,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    
___



## What's next
- Learn how to [configure data quality checks](../../../dqo-concepts/configuring-data-quality-checks-and-rules.md) in DQOps
- Look at the examples of [running data quality checks](../../../dqo-concepts/running-data-quality-checks.md), targeting tables and columns
