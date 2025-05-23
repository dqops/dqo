---
title: Median change data quality checks, SQL examples
---
# Median change data quality checks, SQL examples

This check detects that the median of numeric values has changed more than *max_percent* from the last measured median.


___
The **median change** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile median change


**Check description**

Verifies that the median in a column changed in a fixed rate since the last readout.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`profile_median_change`</span>|Maximum relative change in the median of numeric values since the last known value|[anomaly](../../../categories-of-data-quality-checks/how-to-detect-anomaly-data-quality-issues.md)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)| |[Consistency](../../../dqo-concepts/data-quality-dimensions.md#data-consistency)|[*percentile*](../../../reference/sensors/column/numeric-column-sensors.md#percentile)|[*change_percent*](../../../reference/rules/Change.md#change-percent)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the profile median change data quality check.

??? example "Managing profile median change check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=profile_median_change --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_median_change --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_median_change --enable-warning
                            -Wmax_percent=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=profile_median_change --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_median_change --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_median_change --enable-error
                            -Emax_percent=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *profile_median_change* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=profile_median_change
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_median_change
        ```

        You can also run this check on all tables (and columns)  on which the *profile_median_change* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=profile_median_change
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-17"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      profiling_checks:
        anomaly:
          profile_median_change:
            parameters:
              percentile_value: 0.5
            warning:
              max_percent: 10.0
            error:
              max_percent: 20.0
            fatal:
              max_percent: 50.0
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
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE_CONT(
                    ({{ lib.render_target_column('analyzed_table')}}),
                    {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value
            FROM(
                SELECT
                    PERCENTILE_CONT(
                    (analyzed_table.`target_column`),
                    0.5)
                    OVER (PARTITION BY
                        NULL
                    ) AS actual_value
                FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"

            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
            
            SELECT
                quantile({{ parameters.percentile_value }})({{ lib.render_target_column('analyzed_table')}}) AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                ORDER BY {{ lib.render_target_column('original_table')}}
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for ClickHouse"

            ```sql
            SELECT
                quantile(0.5)(analyzed_table."target_column") AS actual_value
            FROM(
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
                ORDER BY original_table."target_column"
            ) analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE(
                    ({{ lib.render_target_column('analyzed_table')}}),
                    {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value
            FROM(
                SELECT
                    PERCENTILE(
                    (analyzed_table.`target_column`),
                    0.5)
                    OVER (PARTITION BY
                        NULL
                    ) AS actual_value
                FROM `<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"

            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value
            FROM(
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
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
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value
            FROM  AS analyzed_table
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(analyzed_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                time_period,
                time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE_CONT({{ parameters.percentile_value }})
                        WITHIN GROUP (ORDER BY {{ lib.render_target_column('original_table')}})
                        OVER (
                            {%- if lib.data_groupings is not none or lib.time_series is not none %}
                            PARTITION BY
                            {%- endif -%}
                                    {{ render_local_time_dimension_projection('original_table') -}}
                                    {{ render_local_data_grouping_projections('original_table') }}
                                ) AS actual_value
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(indentation = '    ', table_alias_prefix='original_table') -}}
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"

            ```sql
            SELECT
                MAX(analyzed_table.actual_value) AS actual_value
            FROM(
                SELECT
                    PERCENTILE_CONT(0.5)
                        WITHIN GROUP (ORDER BY original_table."target_column")
                        OVER (
                                ) AS actual_value
                FROM "<target_schema>"."<target_table>" original_table) analyzed_table
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value
            FROM(
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
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
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.5)
                    OVER (PARTITION BY
                        NULL
                    ) AS actual_value
                FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"

            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            
            SELECT
                APPROX_PERCENTILE({{ lib.render_target_column('analyzed_table')}} * 1.0, {{ parameters.percentile_value }}, 2) AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for QuestDB"

            ```sql
            SELECT
                APPROX_PERCENTILE(analyzed_table."target_column" * 1.0, 0.5, 2) AS actual_value
            FROM(
                SELECT
                    original_table.*
                FROM "<target_table>" original_table
            ) analyzed_table
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
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
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
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE(
                    ({{ lib.render_target_column('analyzed_table')}}),
                    {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value
            FROM(
                SELECT
                    PERCENTILE(
                    (analyzed_table.`target_column`),
                    0.5)
                    OVER (PARTITION BY
                        NULL
                    ) AS actual_value
                FROM `<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}CAST({{ lib.render_time_dimension_expression(table_alias_prefix) }} AS DATETIME)
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_time_period_columns() -%}
                {% if lib.time_series is not none -%}
                    nested_table.[time_period], nested_table.[time_period_utc]
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.[time_period] AS time_period,
                nested_table.[time_period_utc] AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE_CONT({{ parameters.percentile_value }})
                    WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {% if lib.time_series is not none or (data_groupings is not none and (data_groupings | length()) > 0) -%}
            GROUP BY {{render_time_period_columns()}} {{- lib.render_data_grouping_projections('analyzed_table', set_leading_comma=(lib.time_series is not none)) }}
            ORDER BY {{render_time_period_columns()}} {{- lib.render_data_grouping_projections('analyzed_table', set_leading_comma=(lib.time_series is not none)) }}
            {%- endif -%}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value
            FROM(
                SELECT
                    PERCENTILE_CONT(0.5)
                    WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                    OVER (PARTITION BY
                        NULL
                    ) AS actual_value
                FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"

            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
            
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}} * 1.0) AS actual_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Teradata"

            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column" * 1.0) AS actual_value
            FROM "<target_schema>"."<target_table>" analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.5)
                    OVER (PARTITION BY
                        NULL
                    ) AS actual_value
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-13 29-34"
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
            anomaly:
              profile_median_change:
                parameters:
                  percentile_value: 0.5
                warning:
                  max_percent: 10.0
                error:
                  max_percent: 20.0
                fatal:
                  max_percent: 50.0
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
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE_CONT(
                    ({{ lib.render_target_column('analyzed_table')}}),
                    {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM(
                SELECT
                    PERCENTILE_CONT(
                    (analyzed_table.`target_column`),
                    0.5)
                    OVER (PARTITION BY
                        analyzed_table.`country`,
                        analyzed_table.`state`
                    ) AS actual_value
                FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"
            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
            
            SELECT
                quantile({{ parameters.percentile_value }})({{ lib.render_target_column('analyzed_table')}}) AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                ORDER BY {{ lib.render_target_column('original_table')}}
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for ClickHouse"
            ```sql
            SELECT
                quantile(0.5)(analyzed_table."target_column") AS actual_value,
                analyzed_table.grouping_level_1,
                analyzed_table.grouping_level_2
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_schema>"."<target_table>" original_table
                ORDER BY original_table."target_column"
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE(
                    ({{ lib.render_target_column('analyzed_table')}}),
                    {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM(
                SELECT
                    PERCENTILE(
                    (analyzed_table.`target_column`),
                    0.5)
                    OVER (PARTITION BY
                        analyzed_table.`country`,
                        analyzed_table.`state`
                    ) AS actual_value
                FROM `<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "DB2"

        === "Sensor template for DB2"
            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"
            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
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
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM  AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "HANA"

        === "Sensor template for HANA"
            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(analyzed_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                time_period,
                time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE_CONT({{ parameters.percentile_value }})
                        WITHIN GROUP (ORDER BY {{ lib.render_target_column('original_table')}})
                        OVER (
                            {%- if lib.data_groupings is not none or lib.time_series is not none %}
                            PARTITION BY
                            {%- endif -%}
                                    {{ render_local_time_dimension_projection('original_table') -}}
                                    {{ render_local_data_grouping_projections('original_table') }}
                                ) AS actual_value
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(indentation = '    ', table_alias_prefix='original_table') -}}
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"
            ```sql
            SELECT
                MAX(analyzed_table.actual_value) AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM(
                SELECT
                    PERCENTILE_CONT(0.5)
                        WITHIN GROUP (ORDER BY original_table."target_column")
                        OVER (
                            PARTITION BY
                        original_table."country",
                        original_table."state"
                                ) AS actual_value
                FROM "<target_schema>"."<target_table>" original_table) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
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
            
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
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
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.5)
                    OVER (PARTITION BY
                        analyzed_table."country",
                        analyzed_table."state"
                    ) AS actual_value
                FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"
            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            
            SELECT
                APPROX_PERCENTILE({{ lib.render_target_column('analyzed_table')}} * 1.0, {{ parameters.percentile_value }}, 2) AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for QuestDB"
            ```sql
            SELECT
                APPROX_PERCENTILE(analyzed_table."target_column" * 1.0, 0.5, 2) AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
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
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
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
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE(
                    ({{ lib.render_target_column('analyzed_table')}}),
                    {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM(
                SELECT
                    PERCENTILE(
                    (analyzed_table.`target_column`),
                    0.5)
                    OVER (PARTITION BY
                        analyzed_table.`country`,
                        analyzed_table.`state`
                    ) AS actual_value
                FROM `<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}CAST({{ lib.render_time_dimension_expression(table_alias_prefix) }} AS DATETIME)
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_time_period_columns() -%}
                {% if lib.time_series is not none -%}
                    nested_table.[time_period], nested_table.[time_period_utc]
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.[time_period] AS time_period,
                nested_table.[time_period_utc] AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE_CONT({{ parameters.percentile_value }})
                    WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {% if lib.time_series is not none or (data_groupings is not none and (data_groupings | length()) > 0) -%}
            GROUP BY {{render_time_period_columns()}} {{- lib.render_data_grouping_projections('analyzed_table', set_leading_comma=(lib.time_series is not none)) }}
            ORDER BY {{render_time_period_columns()}} {{- lib.render_data_grouping_projections('analyzed_table', set_leading_comma=(lib.time_series is not none)) }}
            {%- endif -%}
            ```
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2
            FROM(
                SELECT
                    PERCENTILE_CONT(0.5)
                    WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                    OVER (PARTITION BY
                        analyzed_table.[country],
                        analyzed_table.[state]
                    ) AS actual_value
                FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"
            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
            
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}} * 1.0) AS actual_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Teradata"
            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column" * 1.0) AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "<target_schema>"."<target_table>" analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.5)
                    OVER (PARTITION BY
                        analyzed_table."country",
                        analyzed_table."state"
                    ) AS actual_value
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    
___


## daily median change


**Check description**

Verifies that the median in a column changed in a fixed rate since the last readout.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_median_change`</span>|Maximum relative change in the median of numeric values since the last known value|[anomaly](../../../categories-of-data-quality-checks/how-to-detect-anomaly-data-quality-issues.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|daily|[Consistency](../../../dqo-concepts/data-quality-dimensions.md#data-consistency)|[*percentile*](../../../reference/sensors/column/numeric-column-sensors.md#percentile)|[*change_percent*](../../../reference/rules/Change.md#change-percent)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily median change data quality check.

??? example "Managing daily median change check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_median_change --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_median_change --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_median_change --enable-warning
                            -Wmax_percent=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_median_change --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_median_change --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_median_change --enable-error
                            -Emax_percent=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_median_change* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_median_change
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_median_change
        ```

        You can also run this check on all tables (and columns)  on which the *daily_median_change* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=daily_median_change
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-18"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      monitoring_checks:
        daily:
          anomaly:
            daily_median_change:
              parameters:
                percentile_value: 0.5
              warning:
                max_percent: 10.0
              error:
                max_percent: 20.0
              fatal:
                max_percent: 50.0
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
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE_CONT(
                    ({{ lib.render_target_column('analyzed_table')}}),
                    {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value
            FROM(
                SELECT
                    PERCENTILE_CONT(
                    (analyzed_table.`target_column`),
                    0.5)
                    OVER (PARTITION BY
                        NULL
                    ) AS actual_value
                FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"

            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
            
            SELECT
                quantile({{ parameters.percentile_value }})({{ lib.render_target_column('analyzed_table')}}) AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                ORDER BY {{ lib.render_target_column('original_table')}}
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for ClickHouse"

            ```sql
            SELECT
                quantile(0.5)(analyzed_table."target_column") AS actual_value
            FROM(
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
                ORDER BY original_table."target_column"
            ) analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE(
                    ({{ lib.render_target_column('analyzed_table')}}),
                    {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value
            FROM(
                SELECT
                    PERCENTILE(
                    (analyzed_table.`target_column`),
                    0.5)
                    OVER (PARTITION BY
                        NULL
                    ) AS actual_value
                FROM `<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"

            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value
            FROM(
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
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
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value
            FROM  AS analyzed_table
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(analyzed_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                time_period,
                time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE_CONT({{ parameters.percentile_value }})
                        WITHIN GROUP (ORDER BY {{ lib.render_target_column('original_table')}})
                        OVER (
                            {%- if lib.data_groupings is not none or lib.time_series is not none %}
                            PARTITION BY
                            {%- endif -%}
                                    {{ render_local_time_dimension_projection('original_table') -}}
                                    {{ render_local_data_grouping_projections('original_table') }}
                                ) AS actual_value
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(indentation = '    ', table_alias_prefix='original_table') -}}
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"

            ```sql
            SELECT
                MAX(analyzed_table.actual_value) AS actual_value
            FROM(
                SELECT
                    PERCENTILE_CONT(0.5)
                        WITHIN GROUP (ORDER BY original_table."target_column")
                        OVER (
                                ) AS actual_value
                FROM "<target_schema>"."<target_table>" original_table) analyzed_table
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value
            FROM(
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
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
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.5)
                    OVER (PARTITION BY
                        NULL
                    ) AS actual_value
                FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"

            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            
            SELECT
                APPROX_PERCENTILE({{ lib.render_target_column('analyzed_table')}} * 1.0, {{ parameters.percentile_value }}, 2) AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for QuestDB"

            ```sql
            SELECT
                APPROX_PERCENTILE(analyzed_table."target_column" * 1.0, 0.5, 2) AS actual_value
            FROM(
                SELECT
                    original_table.*
                FROM "<target_table>" original_table
            ) analyzed_table
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
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
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
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE(
                    ({{ lib.render_target_column('analyzed_table')}}),
                    {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value
            FROM(
                SELECT
                    PERCENTILE(
                    (analyzed_table.`target_column`),
                    0.5)
                    OVER (PARTITION BY
                        NULL
                    ) AS actual_value
                FROM `<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}CAST({{ lib.render_time_dimension_expression(table_alias_prefix) }} AS DATETIME)
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_time_period_columns() -%}
                {% if lib.time_series is not none -%}
                    nested_table.[time_period], nested_table.[time_period_utc]
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.[time_period] AS time_period,
                nested_table.[time_period_utc] AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE_CONT({{ parameters.percentile_value }})
                    WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {% if lib.time_series is not none or (data_groupings is not none and (data_groupings | length()) > 0) -%}
            GROUP BY {{render_time_period_columns()}} {{- lib.render_data_grouping_projections('analyzed_table', set_leading_comma=(lib.time_series is not none)) }}
            ORDER BY {{render_time_period_columns()}} {{- lib.render_data_grouping_projections('analyzed_table', set_leading_comma=(lib.time_series is not none)) }}
            {%- endif -%}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value
            FROM(
                SELECT
                    PERCENTILE_CONT(0.5)
                    WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                    OVER (PARTITION BY
                        NULL
                    ) AS actual_value
                FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"

            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
            
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}} * 1.0) AS actual_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Teradata"

            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column" * 1.0) AS actual_value
            FROM "<target_schema>"."<target_table>" analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.5)
                    OVER (PARTITION BY
                        NULL
                    ) AS actual_value
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-13 30-35"
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
              anomaly:
                daily_median_change:
                  parameters:
                    percentile_value: 0.5
                  warning:
                    max_percent: 10.0
                  error:
                    max_percent: 20.0
                  fatal:
                    max_percent: 50.0
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
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE_CONT(
                    ({{ lib.render_target_column('analyzed_table')}}),
                    {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM(
                SELECT
                    PERCENTILE_CONT(
                    (analyzed_table.`target_column`),
                    0.5)
                    OVER (PARTITION BY
                        analyzed_table.`country`,
                        analyzed_table.`state`
                    ) AS actual_value
                FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"
            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
            
            SELECT
                quantile({{ parameters.percentile_value }})({{ lib.render_target_column('analyzed_table')}}) AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                ORDER BY {{ lib.render_target_column('original_table')}}
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for ClickHouse"
            ```sql
            SELECT
                quantile(0.5)(analyzed_table."target_column") AS actual_value,
                analyzed_table.grouping_level_1,
                analyzed_table.grouping_level_2
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_schema>"."<target_table>" original_table
                ORDER BY original_table."target_column"
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE(
                    ({{ lib.render_target_column('analyzed_table')}}),
                    {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM(
                SELECT
                    PERCENTILE(
                    (analyzed_table.`target_column`),
                    0.5)
                    OVER (PARTITION BY
                        analyzed_table.`country`,
                        analyzed_table.`state`
                    ) AS actual_value
                FROM `<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "DB2"

        === "Sensor template for DB2"
            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"
            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
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
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM  AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "HANA"

        === "Sensor template for HANA"
            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(analyzed_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                time_period,
                time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE_CONT({{ parameters.percentile_value }})
                        WITHIN GROUP (ORDER BY {{ lib.render_target_column('original_table')}})
                        OVER (
                            {%- if lib.data_groupings is not none or lib.time_series is not none %}
                            PARTITION BY
                            {%- endif -%}
                                    {{ render_local_time_dimension_projection('original_table') -}}
                                    {{ render_local_data_grouping_projections('original_table') }}
                                ) AS actual_value
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(indentation = '    ', table_alias_prefix='original_table') -}}
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"
            ```sql
            SELECT
                MAX(analyzed_table.actual_value) AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM(
                SELECT
                    PERCENTILE_CONT(0.5)
                        WITHIN GROUP (ORDER BY original_table."target_column")
                        OVER (
                            PARTITION BY
                        original_table."country",
                        original_table."state"
                                ) AS actual_value
                FROM "<target_schema>"."<target_table>" original_table) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
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
            
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
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
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.5)
                    OVER (PARTITION BY
                        analyzed_table."country",
                        analyzed_table."state"
                    ) AS actual_value
                FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"
            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            
            SELECT
                APPROX_PERCENTILE({{ lib.render_target_column('analyzed_table')}} * 1.0, {{ parameters.percentile_value }}, 2) AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for QuestDB"
            ```sql
            SELECT
                APPROX_PERCENTILE(analyzed_table."target_column" * 1.0, 0.5, 2) AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
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
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
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
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE(
                    ({{ lib.render_target_column('analyzed_table')}}),
                    {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM(
                SELECT
                    PERCENTILE(
                    (analyzed_table.`target_column`),
                    0.5)
                    OVER (PARTITION BY
                        analyzed_table.`country`,
                        analyzed_table.`state`
                    ) AS actual_value
                FROM `<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}CAST({{ lib.render_time_dimension_expression(table_alias_prefix) }} AS DATETIME)
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_time_period_columns() -%}
                {% if lib.time_series is not none -%}
                    nested_table.[time_period], nested_table.[time_period_utc]
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.[time_period] AS time_period,
                nested_table.[time_period_utc] AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE_CONT({{ parameters.percentile_value }})
                    WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {% if lib.time_series is not none or (data_groupings is not none and (data_groupings | length()) > 0) -%}
            GROUP BY {{render_time_period_columns()}} {{- lib.render_data_grouping_projections('analyzed_table', set_leading_comma=(lib.time_series is not none)) }}
            ORDER BY {{render_time_period_columns()}} {{- lib.render_data_grouping_projections('analyzed_table', set_leading_comma=(lib.time_series is not none)) }}
            {%- endif -%}
            ```
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2
            FROM(
                SELECT
                    PERCENTILE_CONT(0.5)
                    WITHIN GROUP (ORDER BY analyzed_table.[target_column])
                    OVER (PARTITION BY
                        analyzed_table.[country],
                        analyzed_table.[state]
                    ) AS actual_value
                FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"
            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
            
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}} * 1.0) AS actual_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Teradata"
            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column" * 1.0) AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "<target_schema>"."<target_table>" analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                MAX(nested_table.actual_value) AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST(analyzed_table."target_column" AS DOUBLE),
                        0.5)
                    OVER (PARTITION BY
                        analyzed_table."country",
                        analyzed_table."state"
                    ) AS actual_value
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    
___


## daily partition median change


**Check description**

Verifies that the median in a column changed in a fixed rate since the last readout.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_partition_median_change`</span>|Maximum relative change in the median of numeric values since the last known value|[anomaly](../../../categories-of-data-quality-checks/how-to-detect-anomaly-data-quality-issues.md)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|daily|[Consistency](../../../dqo-concepts/data-quality-dimensions.md#data-consistency)|[*percentile*](../../../reference/sensors/column/numeric-column-sensors.md#percentile)|[*change_percent*](../../../reference/rules/Change.md#change-percent)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily partition median change data quality check.

??? example "Managing daily partition median change check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_partition_median_change --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_median_change --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_median_change --enable-warning
                            -Wmax_percent=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_partition_median_change --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_median_change --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_median_change --enable-error
                            -Emax_percent=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_partition_median_change* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_partition_median_change
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_partition_median_change
        ```

        You can also run this check on all tables (and columns)  on which the *daily_partition_median_change* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=daily_partition_median_change
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="12-23"
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
          anomaly:
            daily_partition_median_change:
              parameters:
                percentile_value: 0.5
              warning:
                max_percent: 10.0
              error:
                max_percent: 20.0
              fatal:
                max_percent: 50.0
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
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE_CONT(
                    ({{ lib.render_target_column('analyzed_table')}}),
                    {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
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
                        CAST(analyzed_table.`date_column` AS DATE),
                        TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE))
                    ) AS actual_value,
                    CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                    TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
                FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"

            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
            
            SELECT
                quantile({{ parameters.percentile_value }})({{ lib.render_target_column('analyzed_table')}}) AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                ORDER BY {{ lib.render_target_column('original_table')}}
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for ClickHouse"

            ```sql
            SELECT
                quantile(0.5)(analyzed_table."target_column") AS actual_value,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                CAST(original_table."date_column" AS DATE) AS time_period,
                toDateTime64(CAST(original_table."date_column" AS DATE), 3) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
                ORDER BY original_table."target_column"
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE(
                    ({{ lib.render_target_column('analyzed_table')}}),
                    {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
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
                    0.5)
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
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"

            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                CAST(original_table."date_column" AS DATE) AS time_period,
                TIMESTAMP(CAST(original_table."date_column" AS DATE)) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
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
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM  AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(analyzed_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                time_period,
                time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE_CONT({{ parameters.percentile_value }})
                        WITHIN GROUP (ORDER BY {{ lib.render_target_column('original_table')}})
                        OVER (
                            {%- if lib.data_groupings is not none or lib.time_series is not none %}
                            PARTITION BY
                            {%- endif -%}
                                    {{ render_local_time_dimension_projection('original_table') -}}
                                    {{ render_local_data_grouping_projections('original_table') }}
                                ) AS actual_value
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(indentation = '    ', table_alias_prefix='original_table') -}}
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"

            ```sql
            SELECT
                MAX(analyzed_table.actual_value) AS actual_value,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    PERCENTILE_CONT(0.5)
                        WITHIN GROUP (ORDER BY original_table."target_column")
                        OVER (
                            PARTITION BY
                        CAST(original_table."date_column" AS DATE),
                         CAST(original_table."date_column" AS DATE)
                                ) AS actual_value,
                CAST(original_table."date_column" AS DATE) AS time_period,
                TO_TIMESTAMP(CAST(original_table."date_column" AS DATE)) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table) analyzed_table
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
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
                PERCENTILE_CONT(0.5)
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
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
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
                        0.5)
                    OVER (PARTITION BY
                        CAST(analyzed_table."date_column" AS date),
                         CAST(analyzed_table."date_column" AS date)
                    ) AS actual_value,
                    CAST(analyzed_table."date_column" AS date) AS time_period,
                    CAST(CAST(analyzed_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"

            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            
            SELECT
                APPROX_PERCENTILE({{ lib.render_target_column('analyzed_table')}} * 1.0, {{ parameters.percentile_value }}, 2) AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for QuestDB"

            ```sql
            SELECT
                APPROX_PERCENTILE(analyzed_table."target_column" * 1.0, 0.5, 2) AS actual_value,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                CAST(DATE_TRUNC('day', original_table."date_column") AS DATE) AS time_period,
                CAST((CAST(DATE_TRUNC('day', original_table."date_column") AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_table>" original_table
            ) analyzed_table
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
                PERCENTILE_CONT(0.5)
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
                PERCENTILE_CONT(0.5)
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
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE(
                    ({{ lib.render_target_column('analyzed_table')}}),
                    {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
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
                    0.5)
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
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}CAST({{ lib.render_time_dimension_expression(table_alias_prefix) }} AS DATETIME)
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_time_period_columns() -%}
                {% if lib.time_series is not none -%}
                    nested_table.[time_period], nested_table.[time_period_utc]
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.[time_period] AS time_period,
                nested_table.[time_period_utc] AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE_CONT({{ parameters.percentile_value }})
                    WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {% if lib.time_series is not none or (data_groupings is not none and (data_groupings | length()) > 0) -%}
            GROUP BY {{render_time_period_columns()}} {{- lib.render_data_grouping_projections('analyzed_table', set_leading_comma=(lib.time_series is not none)) }}
            ORDER BY {{render_time_period_columns()}} {{- lib.render_data_grouping_projections('analyzed_table', set_leading_comma=(lib.time_series is not none)) }}
            {%- endif -%}
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
                        CAST(analyzed_table.[date_column] AS date),
                        CAST(CAST(analyzed_table.[date_column] AS date) AS DATETIME)
                    ) AS actual_value,
                    CAST(analyzed_table.[date_column] AS date) AS time_period,
                    CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
                FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table) AS nested_table
            GROUP BY nested_table.[time_period], nested_table.[time_period_utc]
            ORDER BY nested_table.[time_period], nested_table.[time_period_utc]
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"

            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
            
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}} * 1.0) AS actual_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Teradata"

            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column" * 1.0) AS actual_value,
                CAST(analyzed_table."date_column" AS DATE) AS time_period,
                CAST(CAST(analyzed_table."date_column" AS DATE) AS TIMESTAMP) AS time_period_utc
            FROM "<target_schema>"."<target_table>" analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
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
                        0.5)
                    OVER (PARTITION BY
                        CAST(analyzed_table."date_column" AS date),
                         CAST(analyzed_table."date_column" AS date)
                    ) AS actual_value,
                    CAST(analyzed_table."date_column" AS date) AS time_period,
                    CAST(CAST(analyzed_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="10-4 40-45"
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
              anomaly:
                daily_partition_median_change:
                  parameters:
                    percentile_value: 0.5
                  warning:
                    max_percent: 10.0
                  error:
                    max_percent: 20.0
                  fatal:
                    max_percent: 50.0
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
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE_CONT(
                    ({{ lib.render_target_column('analyzed_table')}}),
                    {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
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
                        CAST(analyzed_table.`date_column` AS DATE),
                        TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)),
                        analyzed_table.`country`,
                        analyzed_table.`state`
                    ) AS actual_value,
                    CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                    TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
                FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"
            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
            
            SELECT
                quantile({{ parameters.percentile_value }})({{ lib.render_target_column('analyzed_table')}}) AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                ORDER BY {{ lib.render_target_column('original_table')}}
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for ClickHouse"
            ```sql
            SELECT
                quantile(0.5)(analyzed_table."target_column") AS actual_value,
                analyzed_table.grouping_level_1,
                analyzed_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                CAST(original_table."date_column" AS DATE) AS time_period,
                toDateTime64(CAST(original_table."date_column" AS DATE), 3) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
                ORDER BY original_table."target_column"
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE(
                    ({{ lib.render_target_column('analyzed_table')}}),
                    {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
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
                    0.5)
                    OVER (PARTITION BY
                        CAST(analyzed_table.`date_column` AS DATE),
                        TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)),
                        analyzed_table.`country`,
                        analyzed_table.`state`
                    ) AS actual_value,
                    CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                    TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
                FROM `<target_schema>`.`<target_table>` AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "DB2"

        === "Sensor template for DB2"
            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"
            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                CAST(original_table."date_column" AS DATE) AS time_period,
                TIMESTAMP(CAST(original_table."date_column" AS DATE)) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
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
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column") AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM  AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "HANA"

        === "Sensor template for HANA"
            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(analyzed_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                time_period,
                time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE_CONT({{ parameters.percentile_value }})
                        WITHIN GROUP (ORDER BY {{ lib.render_target_column('original_table')}})
                        OVER (
                            {%- if lib.data_groupings is not none or lib.time_series is not none %}
                            PARTITION BY
                            {%- endif -%}
                                    {{ render_local_time_dimension_projection('original_table') -}}
                                    {{ render_local_data_grouping_projections('original_table') }}
                                ) AS actual_value
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(indentation = '    ', table_alias_prefix='original_table') -}}
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"
            ```sql
            SELECT
                MAX(analyzed_table.actual_value) AS actual_value,
                time_period,
                time_period_utc,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM(
                SELECT
                    PERCENTILE_CONT(0.5)
                        WITHIN GROUP (ORDER BY original_table."target_column")
                        OVER (
                            PARTITION BY
                        CAST(original_table."date_column" AS DATE),
                         CAST(original_table."date_column" AS DATE),
                        original_table."country",
                        original_table."state"
                                ) AS actual_value,
                CAST(original_table."date_column" AS DATE) AS time_period,
                TO_TIMESTAMP(CAST(original_table."date_column" AS DATE)) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table) analyzed_table
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
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
                PERCENTILE_CONT(0.5)
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
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
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
                        0.5)
                    OVER (PARTITION BY
                        CAST(analyzed_table."date_column" AS date),
                         CAST(analyzed_table."date_column" AS date),
                        analyzed_table."country",
                        analyzed_table."state"
                    ) AS actual_value,
                    CAST(analyzed_table."date_column" AS date) AS time_period,
                    CAST(CAST(analyzed_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"
            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            
            SELECT
                APPROX_PERCENTILE({{ lib.render_target_column('analyzed_table')}} * 1.0, {{ parameters.percentile_value }}, 2) AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for QuestDB"
            ```sql
            SELECT
                APPROX_PERCENTILE(analyzed_table."target_column" * 1.0, 0.5, 2) AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                CAST(DATE_TRUNC('day', original_table."date_column") AS DATE) AS time_period,
                CAST((CAST(DATE_TRUNC('day', original_table."date_column") AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_table>" original_table
            ) analyzed_table
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
                PERCENTILE_CONT(0.5)
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
                PERCENTILE_CONT(0.5)
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
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}TIMESTAMP({{ lib.render_time_dimension_expression(table_alias_prefix) }})
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.`time_period` AS time_period,
                nested_table.`time_period_utc` AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE(
                    ({{ lib.render_target_column('analyzed_table')}}),
                    {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
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
                    0.5)
                    OVER (PARTITION BY
                        CAST(analyzed_table.`date_column` AS DATE),
                        TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)),
                        analyzed_table.`country`,
                        analyzed_table.`state`
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
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }}CAST({{ lib.render_time_dimension_expression(table_alias_prefix) }} AS DATETIME)
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_time_period_columns() -%}
                {% if lib.time_series is not none -%}
                    nested_table.[time_period], nested_table.[time_period_utc]
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table.[time_period] AS time_period,
                nested_table.[time_period_utc] AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    PERCENTILE_CONT({{ parameters.percentile_value }})
                    WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table
                {{- lib.render_where_clause(indentation = '    ') -}}) AS nested_table
            {% if lib.time_series is not none or (data_groupings is not none and (data_groupings | length()) > 0) -%}
            GROUP BY {{render_time_period_columns()}} {{- lib.render_data_grouping_projections('analyzed_table', set_leading_comma=(lib.time_series is not none)) }}
            ORDER BY {{render_time_period_columns()}} {{- lib.render_data_grouping_projections('analyzed_table', set_leading_comma=(lib.time_series is not none)) }}
            {%- endif -%}
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
                        CAST(analyzed_table.[date_column] AS date),
                        CAST(CAST(analyzed_table.[date_column] AS date) AS DATETIME),
                        analyzed_table.[country],
                        analyzed_table.[state]
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
    ??? example "Teradata"

        === "Sensor template for Teradata"
            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
            
            SELECT
                PERCENTILE_CONT({{ parameters.percentile_value }})
                WITHIN GROUP (ORDER BY {{ lib.render_target_column('analyzed_table')}} * 1.0) AS actual_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Teradata"
            ```sql
            SELECT
                PERCENTILE_CONT(0.5)
                WITHIN GROUP (ORDER BY analyzed_table."target_column" * 1.0) AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS DATE) AS time_period,
                CAST(CAST(analyzed_table."date_column" AS DATE) AS TIMESTAMP) AS time_period_utc
            FROM "<target_schema>"."<target_table>" analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro render_local_time_dimension_projection(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.time_series is not none -%}
                    {{- lib.eol() -}}
                    {{ indentation }}{{ lib.render_time_dimension_expression(table_alias_prefix) }},{{ lib.eol() -}}
                    {{ indentation }} {{ lib.render_time_dimension_expression(table_alias_prefix) }}
                    {{- "," if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}
                {%- endif -%}
            {%- endmacro -%}
            
            {%- macro render_local_data_grouping_projections(table_alias_prefix = 'analyzed_table', indentation = '            ') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ lib.eol() }}{{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ lib.eol() }}{{ indentation }}{{ table_alias_prefix }}.{{ lib.quote_identifier(data_grouping_level.column) }}
                            {%- endif -%}
                            {{ "," if not loop.last }}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                MAX(nested_table.actual_value) AS actual_value {{-"," if lib.time_series is not none -}}
                {% if lib.time_series is not none %}
                nested_table."time_period" AS time_period,
                nested_table."time_period_utc" AS time_period_utc
                {%- endif -%}
                {{- lib.render_data_grouping_projections('analyzed_table') }}
            FROM(
                SELECT
                    APPROX_PERCENTILE(
                        CAST({{ lib.render_target_column('analyzed_table')}} AS DOUBLE),
                        {{ parameters.percentile_value }})
                    OVER (PARTITION BY
                        {%- if lib.data_groupings is none and lib.time_series is none %}
                        NULL
                        {%- endif -%}
                        {{render_local_time_dimension_projection('analyzed_table') -}}
                        {{render_local_data_grouping_projections('analyzed_table') }}
                    ) AS actual_value
                    {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
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
                        0.5)
                    OVER (PARTITION BY
                        CAST(analyzed_table."date_column" AS date),
                         CAST(analyzed_table."date_column" AS date),
                        analyzed_table."country",
                        analyzed_table."state"
                    ) AS actual_value,
                    CAST(analyzed_table."date_column" AS date) AS time_period,
                    CAST(CAST(analyzed_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table) AS nested_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    
___



## What's next
- Learn how to [configure data quality checks](../../../dqo-concepts/configuring-data-quality-checks-and-rules.md) in DQOps
- Look at the examples of [running data quality checks](../../../dqo-concepts/running-data-quality-checks.md), targeting tables and columns
