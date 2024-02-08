# expected texts in top values count data quality checks

A column-level check that counts how many expected text values are among the TOP most popular values in the column.
 The check will first count the number of occurrences of each column&#x27;s value and will pick the TOP X most popular values (configurable by the &#x27;top&#x27; parameter).
 Then, it will compare the list of most popular values to the given list of expected values that should be most popular.
 This check will verify how many supposed most popular values (provided in the &#x27;expected_values&#x27; list) were not found in the top X most popular values in the column.
 This check is helpful in analyzing string columns with frequently occurring values, such as country codes for countries with the most customers.


___
The **expected texts in top values count** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile expected texts in top values count


**Check description**

Verifies that the top X most popular column values contain all values from a list of expected values.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`profile_expected_texts_in_top_values_count`</span>|[accepted_values](../../../categories-of-data-quality-checks/how-to-validate-accepted-values-in-columns.md)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)| |Reasonableness|[*expected_texts_in_top_values_count*](../../../reference/sensors/column/accepted_values-column-sensors.md#expected-texts-in-top-values-count)|[*max_missing*](../../../reference/rules/Comparison.md#max-missing)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the profile expected texts in top values count data quality check.

??? example "Managing profile expected texts in top values count check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=profile_expected_texts_in_top_values_count --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_expected_texts_in_top_values_count --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_expected_texts_in_top_values_count --enable-warning
                            -Wmax_missing=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=profile_expected_texts_in_top_values_count --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_expected_texts_in_top_values_count --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_expected_texts_in_top_values_count --enable-error
                            -Emax_missing=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *profile_expected_texts_in_top_values_count* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=profile_expected_texts_in_top_values_count
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_expected_texts_in_top_values_count
        ```

        You can also run this check on all tables (and columns)  on which the *profile_expected_texts_in_top_values_count* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=profile_expected_texts_in_top_values_count
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-21"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      profiling_checks:
        accepted_values:
          profile_expected_texts_in_top_values_count:
            parameters:
              expected_values:
              - USD
              - GBP
              - EUR
              top: 3
            warning:
              max_missing: 0
            error:
              max_missing: 1
            fatal:
              max_missing: 2
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [expected_texts_in_top_values_count](../../../reference/sensors/column/accepted_values-column-sensors.md#expected-texts-in-top-values-count)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
                    FROM
                        `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                        TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "<target_schema>"."<target_table>" AS analyzed_table
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
                    FROM
                        `<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} top_value,
                        COUNT(*) total_values
                        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        {{ lib.render_target_column('additional_table') }} top_value
                        {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                        FROM {{ lib.render_target_table() }} additional_table) analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL actual_value,
                MAX(0) expected_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX({{ parameters.expected_values | length }})  expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX(3)  expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" top_value,
                        COUNT(*) total_values,
                time_period,
                time_period_utc
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        additional_table."target_column" top_value,
                        TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
                        CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
                    FROM
                        "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                        TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT_BIG(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                        {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
                    {%- else %}
                    GROUP BY {{ lib.render_target_column('analyzed_table') }}
                    {%- endif %}
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                    {%- for attribute in lib.data_groupings -%}
                        top_values.grouping_{{ attribute }}{{ ', ' }}
                    {%- endfor -%}
                {%- endif -%}
                top_values.time_period,
                top_values.time_period_utc
            {{ render_from_subquery() }}
            {%- endif %}
            GROUP BY time_period, time_period_utc
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ', ' }}top_values.grouping_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.[target_column] AS top_value,
                        COUNT_BIG(*) AS total_values,
                        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
                        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
                    FROM
                        [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                    WHERE analyzed_table.[target_column] IS NOT NULL
                    GROUP BY analyzed_table.[target_column]
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-15 33-38"
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
            accepted_values:
              profile_expected_texts_in_top_values_count:
                parameters:
                  expected_values:
                  - USD
                  - GBP
                  - EUR
                  top: 3
                warning:
                  max_missing: 0
                error:
                  max_missing: 1
                fatal:
                  max_missing: 2
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
    [expected_texts_in_top_values_count](../../../reference/sensors/column/accepted_values-column-sensors.md#expected-texts-in-top-values-count)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
                    FROM
                        `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                        TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "<target_schema>"."<target_table>" AS analyzed_table
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
                    FROM
                        `<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} top_value,
                        COUNT(*) total_values
                        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        {{ lib.render_target_column('additional_table') }} top_value
                        {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                        FROM {{ lib.render_target_table() }} additional_table) analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL actual_value,
                MAX(0) expected_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX({{ parameters.expected_values | length }})  expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX(3)  expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" top_value,
                        COUNT(*) total_values,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        additional_table."target_column" top_value,
                        additional_table."country" AS grouping_level_1,
                        additional_table."state" AS grouping_level_2,
                        TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
                        CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
            
                                    analyzed_table.grouping_level_1,
            
                                    analyzed_table.grouping_level_2
            ,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
                    FROM
                        "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                        TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT_BIG(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                        {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
                    {%- else %}
                    GROUP BY {{ lib.render_target_column('analyzed_table') }}
                    {%- endif %}
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                    {%- for attribute in lib.data_groupings -%}
                        top_values.grouping_{{ attribute }}{{ ', ' }}
                    {%- endfor -%}
                {%- endif -%}
                top_values.time_period,
                top_values.time_period_utc
            {{ render_from_subquery() }}
            {%- endif %}
            GROUP BY time_period, time_period_utc
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ', ' }}top_values.grouping_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
            ```
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,top_values.grouping_level_1, top_values.grouping_level_2, top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.[target_column] AS top_value,
                        COUNT_BIG(*) AS total_values,
                        analyzed_table.[country] AS grouping_level_1,
                        analyzed_table.[state] AS grouping_level_2,
                        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
                        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
                    FROM
                        [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                    WHERE analyzed_table.[target_column] IS NOT NULL
                    GROUP BY analyzed_table.[country], analyzed_table.[state], analyzed_table.[target_column]
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc, top_values.grouping_level_1, top_values.grouping_level_2
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
            
                                    analyzed_table.grouping_level_1,
            
                                    analyzed_table.grouping_level_2
            ,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    
___


## daily expected texts in top values count


**Check description**

Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each day when the data quality check was evaluated.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_expected_texts_in_top_values_count`</span>|[accepted_values](../../../categories-of-data-quality-checks/how-to-validate-accepted-values-in-columns.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|daily|Reasonableness|[*expected_texts_in_top_values_count*](../../../reference/sensors/column/accepted_values-column-sensors.md#expected-texts-in-top-values-count)|[*max_missing*](../../../reference/rules/Comparison.md#max-missing)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily expected texts in top values count data quality check.

??? example "Managing daily expected texts in top values count check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_expected_texts_in_top_values_count --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_expected_texts_in_top_values_count --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_expected_texts_in_top_values_count --enable-warning
                            -Wmax_missing=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_expected_texts_in_top_values_count --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_expected_texts_in_top_values_count --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_expected_texts_in_top_values_count --enable-error
                            -Emax_missing=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_expected_texts_in_top_values_count* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_expected_texts_in_top_values_count
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_expected_texts_in_top_values_count
        ```

        You can also run this check on all tables (and columns)  on which the *daily_expected_texts_in_top_values_count* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=daily_expected_texts_in_top_values_count
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-22"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      monitoring_checks:
        daily:
          accepted_values:
            daily_expected_texts_in_top_values_count:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
                top: 3
              warning:
                max_missing: 0
              error:
                max_missing: 1
              fatal:
                max_missing: 2
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [expected_texts_in_top_values_count](../../../reference/sensors/column/accepted_values-column-sensors.md#expected-texts-in-top-values-count)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
                    FROM
                        `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        CAST(LOCALTIMESTAMP AS date) AS time_period,
                        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "<target_schema>"."<target_table>" AS analyzed_table
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
                        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
                    FROM
                        `<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} top_value,
                        COUNT(*) total_values
                        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        {{ lib.render_target_column('additional_table') }} top_value
                        {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                        FROM {{ lib.render_target_table() }} additional_table) analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL actual_value,
                MAX(0) expected_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX({{ parameters.expected_values | length }})  expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX(3)  expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" top_value,
                        COUNT(*) total_values,
                time_period,
                time_period_utc
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        additional_table."target_column" top_value,
                        TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS time_period,
                        CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        CAST(LOCALTIMESTAMP AS date) AS time_period,
                        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        CAST(LOCALTIMESTAMP AS date) AS time_period,
                        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
                        TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
                    FROM
                        "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT_BIG(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                        {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
                    {%- else %}
                    GROUP BY {{ lib.render_target_column('analyzed_table') }}
                    {%- endif %}
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                    {%- for attribute in lib.data_groupings -%}
                        top_values.grouping_{{ attribute }}{{ ', ' }}
                    {%- endfor -%}
                {%- endif -%}
                top_values.time_period,
                top_values.time_period_utc
            {{ render_from_subquery() }}
            {%- endif %}
            GROUP BY time_period, time_period_utc
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ', ' }}top_values.grouping_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.[target_column] AS top_value,
                        COUNT_BIG(*) AS total_values,
                        CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
                        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
                    FROM
                        [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                    WHERE analyzed_table.[target_column] IS NOT NULL
                    GROUP BY analyzed_table.[target_column]
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-15 34-39"
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
              accepted_values:
                daily_expected_texts_in_top_values_count:
                  parameters:
                    expected_values:
                    - USD
                    - GBP
                    - EUR
                    top: 3
                  warning:
                    max_missing: 0
                  error:
                    max_missing: 1
                  fatal:
                    max_missing: 2
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
    [expected_texts_in_top_values_count](../../../reference/sensors/column/accepted_values-column-sensors.md#expected-texts-in-top-values-count)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
                    FROM
                        `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        CAST(LOCALTIMESTAMP AS date) AS time_period,
                        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "<target_schema>"."<target_table>" AS analyzed_table
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
                        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
                    FROM
                        `<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} top_value,
                        COUNT(*) total_values
                        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        {{ lib.render_target_column('additional_table') }} top_value
                        {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                        FROM {{ lib.render_target_table() }} additional_table) analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL actual_value,
                MAX(0) expected_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX({{ parameters.expected_values | length }})  expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX(3)  expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" top_value,
                        COUNT(*) total_values,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        additional_table."target_column" top_value,
                        additional_table."country" AS grouping_level_1,
                        additional_table."state" AS grouping_level_2,
                        TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS time_period,
                        CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        CAST(LOCALTIMESTAMP AS date) AS time_period,
                        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
            
                                    analyzed_table.grouping_level_1,
            
                                    analyzed_table.grouping_level_2
            ,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        CAST(LOCALTIMESTAMP AS date) AS time_period,
                        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
                        TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
                    FROM
                        "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT_BIG(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                        {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
                    {%- else %}
                    GROUP BY {{ lib.render_target_column('analyzed_table') }}
                    {%- endif %}
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                    {%- for attribute in lib.data_groupings -%}
                        top_values.grouping_{{ attribute }}{{ ', ' }}
                    {%- endfor -%}
                {%- endif -%}
                top_values.time_period,
                top_values.time_period_utc
            {{ render_from_subquery() }}
            {%- endif %}
            GROUP BY time_period, time_period_utc
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ', ' }}top_values.grouping_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
            ```
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,top_values.grouping_level_1, top_values.grouping_level_2, top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.[target_column] AS top_value,
                        COUNT_BIG(*) AS total_values,
                        analyzed_table.[country] AS grouping_level_1,
                        analyzed_table.[state] AS grouping_level_2,
                        CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
                        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
                    FROM
                        [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                    WHERE analyzed_table.[target_column] IS NOT NULL
                    GROUP BY analyzed_table.[country], analyzed_table.[state], analyzed_table.[target_column]
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc, top_values.grouping_level_1, top_values.grouping_level_2
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
            
                                    analyzed_table.grouping_level_1,
            
                                    analyzed_table.grouping_level_2
            ,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    
___


## monthly expected texts in top values count


**Check description**

Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each month when the data quality check was evaluated.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`monthly_expected_texts_in_top_values_count`</span>|[accepted_values](../../../categories-of-data-quality-checks/how-to-validate-accepted-values-in-columns.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|monthly|Reasonableness|[*expected_texts_in_top_values_count*](../../../reference/sensors/column/accepted_values-column-sensors.md#expected-texts-in-top-values-count)|[*max_missing*](../../../reference/rules/Comparison.md#max-missing)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the monthly expected texts in top values count data quality check.

??? example "Managing monthly expected texts in top values count check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_expected_texts_in_top_values_count --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_expected_texts_in_top_values_count --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_expected_texts_in_top_values_count --enable-warning
                            -Wmax_missing=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_expected_texts_in_top_values_count --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_expected_texts_in_top_values_count --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_expected_texts_in_top_values_count --enable-error
                            -Emax_missing=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_expected_texts_in_top_values_count* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_expected_texts_in_top_values_count
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_expected_texts_in_top_values_count
        ```

        You can also run this check on all tables (and columns)  on which the *monthly_expected_texts_in_top_values_count* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=monthly_expected_texts_in_top_values_count
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-22"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      monitoring_checks:
        monthly:
          accepted_values:
            monthly_expected_texts_in_top_values_count:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
                top: 3
              warning:
                max_missing: 0
              error:
                max_missing: 1
              fatal:
                max_missing: 2
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [expected_texts_in_top_values_count](../../../reference/sensors/column/accepted_values-column-sensors.md#expected-texts-in-top-values-count)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
                    FROM
                        `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                        TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "<target_schema>"."<target_table>" AS analyzed_table
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
                    FROM
                        `<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} top_value,
                        COUNT(*) total_values
                        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        {{ lib.render_target_column('additional_table') }} top_value
                        {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                        FROM {{ lib.render_target_table() }} additional_table) analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL actual_value,
                MAX(0) expected_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX({{ parameters.expected_values | length }})  expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX(3)  expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" top_value,
                        COUNT(*) total_values,
                time_period,
                time_period_utc
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        additional_table."target_column" top_value,
                        TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
                        CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
                    FROM
                        "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                        TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT_BIG(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                        {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
                    {%- else %}
                    GROUP BY {{ lib.render_target_column('analyzed_table') }}
                    {%- endif %}
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                    {%- for attribute in lib.data_groupings -%}
                        top_values.grouping_{{ attribute }}{{ ', ' }}
                    {%- endfor -%}
                {%- endif -%}
                top_values.time_period,
                top_values.time_period_utc
            {{ render_from_subquery() }}
            {%- endif %}
            GROUP BY time_period, time_period_utc
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ', ' }}top_values.grouping_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.[target_column] AS top_value,
                        COUNT_BIG(*) AS total_values,
                        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
                        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
                    FROM
                        [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                    WHERE analyzed_table.[target_column] IS NOT NULL
                    GROUP BY analyzed_table.[target_column]
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-15 34-39"
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
              accepted_values:
                monthly_expected_texts_in_top_values_count:
                  parameters:
                    expected_values:
                    - USD
                    - GBP
                    - EUR
                    top: 3
                  warning:
                    max_missing: 0
                  error:
                    max_missing: 1
                  fatal:
                    max_missing: 2
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
    [expected_texts_in_top_values_count](../../../reference/sensors/column/accepted_values-column-sensors.md#expected-texts-in-top-values-count)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
                    FROM
                        `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                        TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "<target_schema>"."<target_table>" AS analyzed_table
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
                    FROM
                        `<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} top_value,
                        COUNT(*) total_values
                        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        {{ lib.render_target_column('additional_table') }} top_value
                        {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                        FROM {{ lib.render_target_table() }} additional_table) analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL actual_value,
                MAX(0) expected_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX({{ parameters.expected_values | length }})  expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX(3)  expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" top_value,
                        COUNT(*) total_values,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        additional_table."target_column" top_value,
                        additional_table."country" AS grouping_level_1,
                        additional_table."state" AS grouping_level_2,
                        TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
                        CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
            
                                    analyzed_table.grouping_level_1,
            
                                    analyzed_table.grouping_level_2
            ,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
                    FROM
                        "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                        TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT_BIG(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                        {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
                    {%- else %}
                    GROUP BY {{ lib.render_target_column('analyzed_table') }}
                    {%- endif %}
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                    {%- for attribute in lib.data_groupings -%}
                        top_values.grouping_{{ attribute }}{{ ', ' }}
                    {%- endfor -%}
                {%- endif -%}
                top_values.time_period,
                top_values.time_period_utc
            {{ render_from_subquery() }}
            {%- endif %}
            GROUP BY time_period, time_period_utc
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ', ' }}top_values.grouping_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
            ```
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,top_values.grouping_level_1, top_values.grouping_level_2, top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.[target_column] AS top_value,
                        COUNT_BIG(*) AS total_values,
                        analyzed_table.[country] AS grouping_level_1,
                        analyzed_table.[state] AS grouping_level_2,
                        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
                        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
                    FROM
                        [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                    WHERE analyzed_table.[target_column] IS NOT NULL
                    GROUP BY analyzed_table.[country], analyzed_table.[state], analyzed_table.[target_column]
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc, top_values.grouping_level_1, top_values.grouping_level_2
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
            
                                    analyzed_table.grouping_level_1,
            
                                    analyzed_table.grouping_level_2
            ,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    
___


## daily partition expected texts in top values count


**Check description**

Verifies that the top X most popular column values contain all values from a list of expected values. Stores a separate data quality check result for each daily partition.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_partition_expected_texts_in_top_values_count`</span>|[accepted_values](../../../categories-of-data-quality-checks/how-to-validate-accepted-values-in-columns.md)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|daily|Reasonableness|[*expected_texts_in_top_values_count*](../../../reference/sensors/column/accepted_values-column-sensors.md#expected-texts-in-top-values-count)|[*max_missing*](../../../reference/rules/Comparison.md#max-missing)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily partition expected texts in top values count data quality check.

??? example "Managing daily partition expected texts in top values count check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_partition_expected_texts_in_top_values_count --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_expected_texts_in_top_values_count --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_expected_texts_in_top_values_count --enable-warning
                            -Wmax_missing=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_partition_expected_texts_in_top_values_count --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_expected_texts_in_top_values_count --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_expected_texts_in_top_values_count --enable-error
                            -Emax_missing=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_partition_expected_texts_in_top_values_count* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_partition_expected_texts_in_top_values_count
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_partition_expected_texts_in_top_values_count
        ```

        You can also run this check on all tables (and columns)  on which the *daily_partition_expected_texts_in_top_values_count* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=daily_partition_expected_texts_in_top_values_count
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="12-27"
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
          accepted_values:
            daily_partition_expected_texts_in_top_values_count:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
                top: 3
              warning:
                max_missing: 0
              error:
                max_missing: 1
              fatal:
                max_missing: 2
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
    [expected_texts_in_top_values_count](../../../reference/sensors/column/accepted_values-column-sensors.md#expected-texts-in-top-values-count)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                        TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
                    FROM
                        `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                        TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        CAST(analyzed_table."date_column" AS date) AS time_period,
                        CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "<target_schema>"."<target_table>" AS analyzed_table
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
                        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
                    FROM
                        `<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} top_value,
                        COUNT(*) total_values
                        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        {{ lib.render_target_column('additional_table') }} top_value
                        {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                        FROM {{ lib.render_target_table() }} additional_table) analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL actual_value,
                MAX(0) expected_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX({{ parameters.expected_values | length }})  expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX(3)  expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" top_value,
                        COUNT(*) total_values,
                time_period,
                time_period_utc
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        additional_table."target_column" top_value,
                        TRUNC(CAST(additional_table."date_column" AS DATE)) AS time_period,
                        CAST(TRUNC(CAST(additional_table."date_column" AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        CAST(analyzed_table."date_column" AS date) AS time_period,
                        CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                CAST(original_table."date_column" AS date) AS time_period,
                CAST(CAST(original_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        CAST(analyzed_table."date_column" AS date) AS time_period,
                        CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        CAST(analyzed_table."date_column" AS date) AS time_period,
                        TO_TIMESTAMP(CAST(analyzed_table."date_column" AS date)) AS time_period_utc
                    FROM
                        "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                        TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT_BIG(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                        {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
                    {%- else %}
                    GROUP BY {{ lib.render_target_column('analyzed_table') }}
                    {%- endif %}
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                    {%- for attribute in lib.data_groupings -%}
                        top_values.grouping_{{ attribute }}{{ ', ' }}
                    {%- endfor -%}
                {%- endif -%}
                top_values.time_period,
                top_values.time_period_utc
            {{ render_from_subquery() }}
            {%- endif %}
            GROUP BY time_period, time_period_utc
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ', ' }}top_values.grouping_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.[target_column] AS top_value,
                        COUNT_BIG(*) AS total_values,
                        CAST(analyzed_table.[date_column] AS date) AS time_period,
                        CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
                    FROM
                        [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                    WHERE analyzed_table.[target_column] IS NOT NULL
                    GROUP BY CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date), analyzed_table.[target_column]
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                CAST(original_table."date_column" AS date) AS time_period,
                CAST(CAST(original_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="10-20 44-49"
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
              accepted_values:
                daily_partition_expected_texts_in_top_values_count:
                  parameters:
                    expected_values:
                    - USD
                    - GBP
                    - EUR
                    top: 3
                  warning:
                    max_missing: 0
                  error:
                    max_missing: 1
                  fatal:
                    max_missing: 2
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
    [expected_texts_in_top_values_count](../../../reference/sensors/column/accepted_values-column-sensors.md#expected-texts-in-top-values-count)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                        TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
                    FROM
                        `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                        TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        CAST(analyzed_table."date_column" AS date) AS time_period,
                        CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "<target_schema>"."<target_table>" AS analyzed_table
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
                        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
                    FROM
                        `<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} top_value,
                        COUNT(*) total_values
                        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        {{ lib.render_target_column('additional_table') }} top_value
                        {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                        FROM {{ lib.render_target_table() }} additional_table) analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL actual_value,
                MAX(0) expected_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX({{ parameters.expected_values | length }})  expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX(3)  expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" top_value,
                        COUNT(*) total_values,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        additional_table."target_column" top_value,
                        additional_table."country" AS grouping_level_1,
                        additional_table."state" AS grouping_level_2,
                        TRUNC(CAST(additional_table."date_column" AS DATE)) AS time_period,
                        CAST(TRUNC(CAST(additional_table."date_column" AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        CAST(analyzed_table."date_column" AS date) AS time_period,
                        CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
            
                                    analyzed_table.grouping_level_1,
            
                                    analyzed_table.grouping_level_2
            ,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                CAST(original_table."date_column" AS date) AS time_period,
                CAST(CAST(original_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        CAST(analyzed_table."date_column" AS date) AS time_period,
                        CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        CAST(analyzed_table."date_column" AS date) AS time_period,
                        TO_TIMESTAMP(CAST(analyzed_table."date_column" AS date)) AS time_period_utc
                    FROM
                        "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                        TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT_BIG(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                        {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
                    {%- else %}
                    GROUP BY {{ lib.render_target_column('analyzed_table') }}
                    {%- endif %}
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                    {%- for attribute in lib.data_groupings -%}
                        top_values.grouping_{{ attribute }}{{ ', ' }}
                    {%- endfor -%}
                {%- endif -%}
                top_values.time_period,
                top_values.time_period_utc
            {{ render_from_subquery() }}
            {%- endif %}
            GROUP BY time_period, time_period_utc
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ', ' }}top_values.grouping_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
            ```
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,top_values.grouping_level_1, top_values.grouping_level_2, top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.[target_column] AS top_value,
                        COUNT_BIG(*) AS total_values,
                        analyzed_table.[country] AS grouping_level_1,
                        analyzed_table.[state] AS grouping_level_2,
                        CAST(analyzed_table.[date_column] AS date) AS time_period,
                        CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
                    FROM
                        [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                    WHERE analyzed_table.[target_column] IS NOT NULL
                    GROUP BY analyzed_table.[country], analyzed_table.[state], CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date), analyzed_table.[target_column]
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc, top_values.grouping_level_1, top_values.grouping_level_2
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
            
                                    analyzed_table.grouping_level_1,
            
                                    analyzed_table.grouping_level_2
            ,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                CAST(original_table."date_column" AS date) AS time_period,
                CAST(CAST(original_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    
___


## monthly partition expected texts in top values count


**Check description**

Verifies that the top X most popular column values contain all values from a list of expected values. Stores a separate data quality check result for each monthly partition.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`monthly_partition_expected_texts_in_top_values_count`</span>|[accepted_values](../../../categories-of-data-quality-checks/how-to-validate-accepted-values-in-columns.md)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|monthly|Reasonableness|[*expected_texts_in_top_values_count*](../../../reference/sensors/column/accepted_values-column-sensors.md#expected-texts-in-top-values-count)|[*max_missing*](../../../reference/rules/Comparison.md#max-missing)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the monthly partition expected texts in top values count data quality check.

??? example "Managing monthly partition expected texts in top values count check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_partition_expected_texts_in_top_values_count --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_partition_expected_texts_in_top_values_count --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_partition_expected_texts_in_top_values_count --enable-warning
                            -Wmax_missing=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_partition_expected_texts_in_top_values_count --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_partition_expected_texts_in_top_values_count --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_partition_expected_texts_in_top_values_count --enable-error
                            -Emax_missing=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_partition_expected_texts_in_top_values_count* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_partition_expected_texts_in_top_values_count
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_partition_expected_texts_in_top_values_count
        ```

        You can also run this check on all tables (and columns)  on which the *monthly_partition_expected_texts_in_top_values_count* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=monthly_partition_expected_texts_in_top_values_count
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="12-27"
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
          accepted_values:
            monthly_partition_expected_texts_in_top_values_count:
              parameters:
                expected_values:
                - USD
                - GBP
                - EUR
                top: 3
              warning:
                max_missing: 0
              error:
                max_missing: 1
              fatal:
                max_missing: 2
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
    [expected_texts_in_top_values_count](../../../reference/sensors/column/accepted_values-column-sensors.md#expected-texts-in-top-values-count)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH) AS time_period,
                        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH)) AS time_period_utc
                    FROM
                        `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                        TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "<target_schema>"."<target_table>" AS analyzed_table
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
                        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
                    FROM
                        `<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} top_value,
                        COUNT(*) total_values
                        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        {{ lib.render_target_column('additional_table') }} top_value
                        {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                        FROM {{ lib.render_target_table() }} additional_table) analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL actual_value,
                MAX(0) expected_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX({{ parameters.expected_values | length }})  expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX(3)  expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" top_value,
                        COUNT(*) total_values,
                time_period,
                time_period_utc
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        additional_table."target_column" top_value,
                        TRUNC(CAST(additional_table."date_column" AS DATE), 'MONTH') AS time_period,
                        CAST(TRUNC(CAST(additional_table."date_column" AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS time_period_utc
                    FROM
                        "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                        TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT_BIG(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                        {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
                    {%- else %}
                    GROUP BY {{ lib.render_target_column('analyzed_table') }}
                    {%- endif %}
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                    {%- for attribute in lib.data_groupings -%}
                        top_values.grouping_{{ attribute }}{{ ', ' }}
                    {%- endfor -%}
                {%- endif -%}
                top_values.time_period,
                top_values.time_period_utc
            {{ render_from_subquery() }}
            {%- endif %}
            GROUP BY time_period, time_period_utc
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ', ' }}top_values.grouping_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        analyzed_table.[target_column] AS top_value,
                        COUNT_BIG(*) AS total_values,
                        DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
                        CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
                    FROM
                        [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                    WHERE analyzed_table.[target_column] IS NOT NULL
                    GROUP BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0), analyzed_table.[target_column]
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period
                        ORDER BY top_col_values.total_values DESC) as top_values_rank
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY time_period, time_period_utc, top_value
                    ORDER BY time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="10-20 44-49"
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
              accepted_values:
                monthly_partition_expected_texts_in_top_values_count:
                  parameters:
                    expected_values:
                    - USD
                    - GBP
                    - EUR
                    top: 3
                  warning:
                    max_missing: 0
                  error:
                    max_missing: 1
                  fatal:
                    max_missing: 2
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
    [expected_texts_in_top_values_count](../../../reference/sensors/column/accepted_values-column-sensors.md#expected-texts-in-top-values-count)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH) AS time_period,
                        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH)) AS time_period_utc
                    FROM
                        `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                        TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "<target_schema>"."<target_table>" AS analyzed_table
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
                        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
                    FROM
                        `<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} top_value,
                        COUNT(*) total_values
                        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        {{ lib.render_target_column('additional_table') }} top_value
                        {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                        FROM {{ lib.render_target_table() }} additional_table) analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL actual_value,
                MAX(0) expected_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX({{ parameters.expected_values | length }})  expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) actual_value,
                MAX(3)  expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value top_value,
                    top_col_values.time_period time_period,
                    top_col_values.time_period_utc time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" top_value,
                        COUNT(*) total_values,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
                    FROM
                    (
                        SELECT
                        additional_table.*,
                        additional_table."target_column" top_value,
                        additional_table."country" AS grouping_level_1,
                        additional_table."state" AS grouping_level_2,
                        TRUNC(CAST(additional_table."date_column" AS DATE), 'MONTH') AS time_period,
                        CAST(TRUNC(CAST(additional_table."date_column" AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                        FROM "<target_schema>"."<target_table>" additional_table) analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) top_col_values
            ) top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
            
                                    analyzed_table.grouping_level_1,
            
                                    analyzed_table.grouping_level_2
            ,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM
                        "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table."target_column" AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table."country" AS grouping_level_1,
                        analyzed_table."state" AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS time_period_utc
                    FROM
                        "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                    WHERE analyzed_table."target_column" IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.`target_column` AS top_value,
                        COUNT(*) AS total_values,
                        analyzed_table.`country` AS grouping_level_1,
                        analyzed_table.`state` AS grouping_level_2,
                        DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                        TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
                    FROM
                        `<target_schema>`.`<target_table>` AS analyzed_table
                    WHERE analyzed_table.`target_column` IS NOT NULL
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        {{ lib.render_target_column('analyzed_table') }} AS top_value,
                        COUNT_BIG(*) AS total_values
                        {{- lib.render_data_grouping_projections('analyzed_table', indentation = '            ') }}
                        {{- lib.render_time_dimension_projection('analyzed_table', indentation = '            ') }}
                    FROM
                        {{ lib.render_target_table() }} AS analyzed_table
                    {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
                    {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or (lib.time_series.mode is not none and lib.time_series.mode != 'current_time') -%}
                        {{- lib.render_group_by(indentation = '        ') }}, {{ lib.render_target_column('analyzed_table') }}
                    {%- else %}
                    GROUP BY {{ lib.render_target_column('analyzed_table') }}
                    {%- endif %}
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                    {%- for attribute in lib.data_groupings -%}
                        top_values.grouping_{{ attribute }}{{ ', ' }}
                    {%- endfor -%}
                {%- endif -%}
                top_values.time_period,
                top_values.time_period_utc
            {{ render_from_subquery() }}
            {%- endif %}
            GROUP BY time_period, time_period_utc
            {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
                {%- for attribute in lib.data_groupings -%}
                    {{ ', ' }}top_values.grouping_{{ attribute }}
                {%- endfor -%}
            {%- endif -%}
            ```
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                COUNT_BIG(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,top_values.grouping_level_1, top_values.grouping_level_2, top_values.time_period,
                top_values.time_period_utc
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        analyzed_table.[target_column] AS top_value,
                        COUNT_BIG(*) AS total_values,
                        analyzed_table.[country] AS grouping_level_1,
                        analyzed_table.[state] AS grouping_level_2,
                        DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
                        CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
                    FROM
                        [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                    WHERE analyzed_table.[target_column] IS NOT NULL
                    GROUP BY analyzed_table.[country], analyzed_table.[state], DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0), analyzed_table.[target_column]
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY time_period, time_period_utc, top_values.grouping_level_1, top_values.grouping_level_2
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {%- macro extract_in_list(values_list) -%}
                {%- for i in values_list -%}
                    {%- if not loop.last -%}
                        {{lib.make_text_constant(i)}}{{", "}}
                    {%- else -%}
                        {{lib.make_text_constant(i)}}
                    {%- endif -%}
                {%- endfor -%}
            {%- endmacro -%}
            
            {%- macro render_from_subquery() -%}
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period {{- render_data_grouping('top_col_values', indentation = ' ') }}
                        ORDER BY top_col_values.total_values DESC) as top_values_rank  {{- render_data_grouping('top_col_values', indentation = ' ') }}
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values
                            {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                            {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
                        FROM (
                            SELECT
                                original_table.*,
                                {{ lib.render_target_column('original_table') }} AS top_value
                                {{- lib.render_data_grouping_projections('original_table') }}
                                {{- lib.render_time_dimension_projection('original_table') }}
                            FROM {{ lib.render_target_table() }} original_table
                            {{- lib.render_where_clause(extra_filter = lib.render_target_column('original_table') ~ ' IS NOT NULL', table_alias_prefix='original_table') }}
                        ) analyzed_table
                    {{- lib.render_where_clause(indentation = '        ') }}
                    {{- lib.render_group_by(indentation = '        ') }}, top_value
                    {{- lib.render_order_by(indentation = '        ') }}, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= {{ parameters.top }}
            {%- endmacro -%}
            
            {%- macro render_data_grouping(table_alias_prefix = '', indentation = '') -%}
                {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
                    {%- for attribute in lib.data_groupings -%}
                        {{ ',' }}
                        {%- with data_grouping_level = lib.data_groupings[attribute] -%}
                            {%- if data_grouping_level.source == 'tag' -%}
                                {{ indentation }}{{ lib.make_text_constant(data_grouping_level.tag) }}
                            {%- elif data_grouping_level.source == 'column_value' -%}
                                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute }}
                            {%- endif -%}
                        {%- endwith %}
                    {%- endfor -%}
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
            {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 %}
                NULL AS actual_value,
                MAX(0) AS expected_value
                {{- lib.render_data_grouping_projections('analyzed_table') }}
                {{- lib.render_time_dimension_projection('analyzed_table') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table
                {%- else %}
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ({{ extract_in_list(parameters.expected_values) }}) THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX({{ parameters.expected_values | length }}) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc
                {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
            {{ render_from_subquery() }}
            {%- endif -%}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN top_values.top_value IN ('USD', 'GBP', 'EUR') THEN top_values.top_value
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                top_values.time_period,
                top_values.time_period_utc,
                top_values.grouping_level_1,
                top_values.grouping_level_2
            FROM
            (
                SELECT
                    top_col_values.top_value as top_value,
                    top_col_values.time_period as time_period,
                    top_col_values.time_period_utc as time_period_utc,
                    RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                        ORDER BY top_col_values.total_values DESC) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2
                FROM
                (
                    SELECT
                        top_value,
                        COUNT(*) AS total_values,
            
                                    analyzed_table.grouping_level_1,
            
                                    analyzed_table.grouping_level_2
            ,
                        time_period,
                        time_period_utc
                        FROM (
                            SELECT
                                original_table.*,
                                original_table."target_column" AS top_value,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                            FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            WHERE original_table."target_column" IS NOT NULL
                        ) analyzed_table
                    GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc, top_value
                    ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc, total_values DESC
                ) AS top_col_values
            ) AS top_values
            WHERE top_values_rank <= 3
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    
___



## What's next
- Learn how to [configure data quality checks](../../../dqo-concepts/configuring-data-quality-checks-and-rules.md) in DQOps
- Look at the examples of [running data quality checks](../../../dqo-concepts/running-data-quality-checks.md), targeting tables and columns
