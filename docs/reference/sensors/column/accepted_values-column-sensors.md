---
title: DQOps data quality accepted values sensors
---
# DQOps data quality accepted values sensors
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **accepted values** category supported by DQOps are listed below. Those sensors are measured on a column level.

---


## expected numbers in use count
Column level sensor that counts how many expected numeric values are used in a tested column. Finds unique column values from the set of expected numeric values and counts them.
 This sensor is useful to analyze numeric columns that have a low number of unique values and it should be tested if all possible values from the list of expected values are used in any row.
 The typical types of tested columns are numeric status or type columns.

**Sensor summary**

The expected numbers in use count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | accepted_values | <span class="no-wrap-code">`column/accepted_values/expected_numbers_in_use_count`</span> | [*sensors/column/accepted_values*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/accepted_values/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`expected_values`</span>|List of expected numeric values that should be found in the tested column.|*integer_list*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
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
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM (
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}
        ) analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"

    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
        0
        {%- else -%}
        COUNT_BIG(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN MAX(0)
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values | length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM (
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}
        ) analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___



## expected text values in use count
Column level sensor that counts how many expected string values are used in a tested column. Finds unique column values from the set of expected string values and counts them.
 This sensor is useful to analyze string columns that have a low number of unique values and it should be tested if all possible values from the list of expected values are used in any row.
 The typical type of columns analyzed using this sensor are currency, country, status or gender columns.

**Sensor summary**

The expected text values in use count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | accepted_values | <span class="no-wrap-code">`column/accepted_values/expected_text_values_in_use_count`</span> | [*sensors/column/accepted_values*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/accepted_values/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`expected_values`</span>|List of expected string values that should be found in the tested column.|*string_list*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{ lib.make_text_constant(i) }},
            {%- else -%}
                {{ lib.make_text_constant(i) }}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
        0
        {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{ render_else() }}
        END AS actual_value,
        MAX(CAST({{ parameters.expected_values | length }} AS INT)) AS expected_value_alias
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

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
    {% endmacro -%}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"

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
    {% endmacro -%}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN MAX(0)
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"

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
    {% endmacro -%}
    
    {%- macro render_else() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        MAX(0)
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) expected_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

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
    {% endmacro -%}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"

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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CAST({{ actual_value() }} AS BIGINT) AS actual_value,
        MAX({{ parameters.expected_values | length }} ) AS expected_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM (
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}
        ) analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"

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
    {% endmacro -%}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"

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
    {% endmacro -%}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {%- for i in values_list -%}
            {%- if not loop.last -%}
                {{ lib.make_text_constant(i) }},
            {%- else -%}
                {{ lib.make_text_constant(i) }}
            {%- endif -%}
        {%- endfor -%}
    {%- endmacro -%}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
        0
        {%- else -%}
            COUNT(DISTINCT
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                    ELSE NULL
                END
            )
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN NULL
            ELSE {{ render_else() }}
        END AS actual_value,
        MAX(CAST({{ parameters.expected_values | length }} AS INT)) AS expected_value_alias
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"

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
    {% endmacro -%}
    
    {%- macro render_else() -%}
        {%- if parameters.expected_values|length == 0 -%}
        0
        {%- else -%}
        COUNT_BIG(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN NULL
            ELSE {{render_else()}}
        END AS actual_value,
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"

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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        0
        {%- else -%}
        COUNT(DISTINCT
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                    THEN {{ lib.render_target_column('analyzed_table') }}
                ELSE NULL
            END
        )
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CAST({{ actual_value() }} AS BIGINT) AS actual_value,
        MAX({{ parameters.expected_values | length }} ) AS expected_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM (
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}
        ) analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___



## expected texts in top values count
Column level sensor that counts how many expected string values are among the TOP most popular values in the column.
 The sensor will first count the number of occurrences of each column&#x27;s value and will pick the TOP X most popular values (configurable by the &#x27;top&#x27; parameter).
 Then, it will compare the list of most popular values to the given list of expected values that should be most popular.
 This sensor will return the number of expected values that were found within the &#x27;top&#x27; most popular column values.
 This sensor is useful in analyzing string columns with frequently occurring values, such as country codes for countries with the most customers.
 The sensor can detect if any of the most popular value (an expected value) is no longer one of the top X most popular values.

**Sensor summary**

The expected texts in top values count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | accepted_values | <span class="no-wrap-code">`column/accepted_values/expected_texts_in_top_values_count`</span> | [*sensors/column/accepted_values*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/accepted_values/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`expected_values`</span>|List of expected string values that should be found in the tested column among the TOP most popular (highest distinct count) column values.|*string_list*|:material-check-bold:||
|<span class="no-wrap-code">`top`</span>|The number of the most popular values (with the highest distinct count) that are analyzed to find the expected values.|*long*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

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
            {% if lib.time_series is not none -%}
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            {% endif -%}
            RANK() OVER({{- render_data_grouping('top_col_values', indentation = ' ', partition_by_enabled=true) }}
                ORDER BY top_col_values.total_values DESC) as top_values_rank {{- render_data_grouping('top_col_values', indentation = ' ') }}
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
            GROUP BY {{ render_grouping_columns() -}} top_value
            ORDER BY {{ render_grouping_columns() -}} total_values DESC
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {% macro render_grouping_columns() %}
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
            {{ lib.render_grouping_column_names() }} {{- ', ' -}}
        {%- endif -%}
    {% endmacro %}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '', partition_by_enabled = false) -%}
    
        {%- if partition_by_enabled == true -%}PARTITION BY
            {%- if lib.time_series is not none -%}
                {{" "}}top_col_values.time_period
            {%- elif lib.data_groupings is none -%}
                {{" "}}NULL
            {%- endif -%}
        {%- endif -%}
    
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{- "" if loop.first and lib.time_series is none and partition_by_enabled else "," -}}
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
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {%- if lib.time_series is not none -%} {{- "," }}
        top_values.time_period,
        top_values.time_period_utc
        {%- endif -%}
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"

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
            {% if lib.time_series is not none -%}
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            {% endif -%}
            RANK() OVER({{- render_data_grouping('top_col_values', indentation = ' ', partition_by_enabled=true) }}
                ORDER BY top_col_values.total_values DESC) as top_values_rank {{- render_data_grouping('top_col_values', indentation = ' ') }}
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
            GROUP BY {{ render_grouping_columns() -}} top_value
            ORDER BY {{ render_grouping_columns() -}} total_values DESC
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {% macro render_grouping_columns() %}
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
            {{ lib.render_grouping_column_names() }} {{- ', ' -}}
        {%- endif -%}
    {% endmacro %}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '', partition_by_enabled = false) -%}
    
        {%- if partition_by_enabled == true -%}PARTITION BY
            {%- if lib.time_series is not none -%}
                {{" "}}top_col_values.time_period
            {%- elif lib.data_groupings is none -%}
                {{" "}}NULL
            {%- endif -%}
        {%- endif -%}
    
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{- "" if loop.first and lib.time_series is none and partition_by_enabled else "," -}}
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
        MAX(1 + NULL) AS actual_value,
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
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {%- if lib.time_series is not none -%} {{- "," }}
        top_values.time_period,
        top_values.time_period_utc
        {%- endif -%}
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

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
            {% if lib.time_series is not none -%}
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            {% endif -%}
            RANK() OVER({{- render_data_grouping('top_col_values', indentation = ' ', partition_by_enabled=true) }}
                ORDER BY top_col_values.total_values DESC) as top_values_rank {{- render_data_grouping('top_col_values', indentation = ' ') }}
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
            GROUP BY {{ render_grouping_columns() -}} top_value
            ORDER BY {{ render_grouping_columns() -}} total_values DESC
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {% macro render_grouping_columns() %}
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
            {{ lib.render_grouping_column_names() }} {{- ', ' -}}
        {%- endif -%}
    {% endmacro %}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '', partition_by_enabled = false) -%}
    
        {%- if partition_by_enabled == true -%}PARTITION BY
            {%- if lib.time_series is not none -%}
                {{" "}}top_col_values.time_period
            {%- elif lib.data_groupings is none -%}
                {{" "}}NULL
            {%- endif -%}
        {%- endif -%}
    
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{- "" if loop.first and lib.time_series is none and partition_by_enabled else "," -}}
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
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {%- if lib.time_series is not none -%} {{- "," }}
        top_values.time_period,
        top_values.time_period_utc
        {%- endif -%}
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"

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
            {% if lib.time_series is not none -%}
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            {% endif -%}
            RANK() OVER({{- render_data_grouping('top_col_values', indentation = ' ', partition_by_enabled=true) }}
                ORDER BY top_col_values.total_values DESC) as top_values_rank {{- render_data_grouping('top_col_values', indentation = ' ') }}
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
            GROUP BY {{ render_grouping_columns() -}} top_value
            ORDER BY {{ render_grouping_columns() -}} total_values DESC
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {% macro render_grouping_columns() %}
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
            {{ lib.render_grouping_column_names() }} {{- ', ' -}}
        {%- endif -%}
    {% endmacro %}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '', partition_by_enabled = false) -%}
    
        {%- if partition_by_enabled == true -%}PARTITION BY
            {%- if lib.time_series is not none -%}
                {{" "}}top_col_values.time_period
            {%- elif lib.data_groupings is none -%}
                {{" "}}NULL
            {%- endif -%}
        {%- endif -%}
    
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{- "" if loop.first and lib.time_series is none and partition_by_enabled else "," -}}
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
        MAX(1 + NULL) AS actual_value,
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
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {%- if lib.time_series is not none -%} {{- "," }}
        top_values.time_period,
        top_values.time_period_utc
        {%- endif -%}
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"

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
            {% if lib.time_series is not none -%}
            top_col_values.time_period time_period,
            top_col_values.time_period_utc time_period_utc,
            {% endif -%}
            RANK() OVER({{- render_data_grouping('top_col_values', indentation = ' ', partition_by_enabled=true) }}
                ORDER BY top_col_values.total_values DESC) top_values_rank {{- render_data_grouping('top_col_values', indentation = ' ') }}
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} top_value,
                COUNT(*) total_values
                {{- lib.render_data_grouping_projections_reference('analyzed_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table', indentation = '            ') }}
            FROM
            (
                SELECT
                additional_table.*,
                {{ lib.render_target_column('additional_table') }} top_value
                {{- lib.render_data_grouping_projections('additional_table', indentation = '            ') }}
                {{- lib.render_time_dimension_projection('additional_table', indentation = '            ') }}
                FROM {{ lib.render_target_table() }} additional_table) analyzed_table
            {{- lib.render_where_clause(extra_filter = lib.render_target_column('analyzed_table') ~ ' IS NOT NULL', indentation = '        ') }}
            GROUP BY {{ render_grouping_columns() -}} top_value
            ORDER BY {{ render_grouping_columns() -}} total_values DESC
        ) top_col_values
    ) top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {% macro render_grouping_columns() %}
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
            {{ lib.render_grouping_column_names() }} {{- ', ' -}}
        {%- endif -%}
    {% endmacro %}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '', partition_by_enabled = false) -%}
    
        {%- if partition_by_enabled == true -%}PARTITION BY
            {%- if lib.time_series is not none -%}
                {{" "}}top_col_values.time_period
            {%- elif lib.data_groupings is none -%}
                {{" "}}NULL
            {%- endif -%}
        {%- endif -%}
    
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{- "" if loop.first and lib.time_series is none and partition_by_enabled else "," -}}
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
        MAX({{ parameters.expected_values | length }}) expected_value
        {%- if lib.time_series is not none -%} {{- "," }}
        top_values.time_period,
        top_values.time_period_utc
        {%- endif -%}
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

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
            {% if lib.time_series is not none -%}
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            {% endif -%}
            RANK() OVER({{- render_data_grouping('top_col_values', indentation = ' ', partition_by_enabled=true) }}
                ORDER BY top_col_values.total_values DESC) as top_values_rank {{- render_data_grouping('top_col_values', indentation = ' ') }}
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
            GROUP BY {{ render_grouping_columns() -}} top_value
            ORDER BY {{ render_grouping_columns() -}} total_values DESC
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {% macro render_grouping_columns() %}
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
            {{ lib.render_grouping_column_names() }} {{- ', ' -}}
        {%- endif -%}
    {% endmacro %}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '', partition_by_enabled = false) -%}
    
        {%- if partition_by_enabled == true -%}PARTITION BY
            {%- if lib.time_series is not none -%}
                {{" "}}top_col_values.time_period
            {%- elif lib.data_groupings is none -%}
                {{" "}}NULL
            {%- endif -%}
        {%- endif -%}
    
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{- "" if loop.first and lib.time_series is none and partition_by_enabled else "," -}}
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
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {%- if lib.time_series is not none -%} {{- "," }}
        top_values.time_period,
        top_values.time_period_utc
        {%- endif -%}
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"

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
            {% if lib.time_series is not none -%}
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            {% endif -%}
            RANK() OVER({{- render_data_grouping('top_col_values', indentation = ' ', partition_by_enabled=true) }}
                ORDER BY top_col_values.total_values DESC) as top_values_rank {{- render_data_grouping('top_col_values', indentation = ' ') }}
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
            GROUP BY {{ render_grouping_columns() -}} top_value
            ORDER BY {{ render_grouping_columns() -}} total_values DESC
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {% macro render_grouping_columns() %}
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
            {{ lib.render_grouping_column_names() }} {{- ', ' -}}
        {%- endif -%}
    {% endmacro %}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '', partition_by_enabled = false) -%}
    
        {%- if partition_by_enabled == true -%}PARTITION BY
            {%- if lib.time_series is not none -%}
                {{" "}}top_col_values.time_period
            {%- elif lib.data_groupings is none -%}
                {{" "}}NULL
            {%- endif -%}
        {%- endif -%}
    
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{- "" if loop.first and lib.time_series is none and partition_by_enabled else "," -}}
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
        MAX(1 + NULL) AS actual_value,
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
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {%- if lib.time_series is not none -%} {{- "," }}
        top_values.time_period,
        top_values.time_period_utc
        {%- endif -%}
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"

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
            {% if lib.time_series is not none -%}
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            {% endif -%}
            RANK() OVER({{- render_data_grouping('top_col_values', indentation = ' ', partition_by_enabled=true) }}
                ORDER BY top_col_values.total_values DESC) as top_values_rank {{- render_data_grouping('top_col_values', indentation = ' ') }}
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
            GROUP BY {{ render_grouping_columns() -}} top_value
            ORDER BY {{ render_grouping_columns() -}} total_values DESC
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {% macro render_grouping_columns() %}
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
            {{ lib.render_grouping_column_names() }} {{- ', ' -}}
        {%- endif -%}
    {% endmacro %}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '', partition_by_enabled = false) -%}
    
        {%- if partition_by_enabled == true -%}PARTITION BY
            {%- if lib.time_series is not none -%}
                {{" "}}top_col_values.time_period
            {%- elif lib.data_groupings is none -%}
                {{" "}}NULL
            {%- endif -%}
        {%- endif -%}
    
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{- "" if loop.first and lib.time_series is none and partition_by_enabled else "," -}}
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
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {%- if lib.time_series is not none -%} {{- "," }}
        top_values.time_period,
        top_values.time_period_utc
        {%- endif -%}
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"

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
            {% if lib.time_series is not none -%}
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            {% endif -%}
            RANK() OVER({{- render_data_grouping('top_col_values', indentation = ' ', partition_by_enabled=true) }}
                ORDER BY top_col_values.total_values DESC) as top_values_rank {{- render_data_grouping('top_col_values', indentation = ' ') }}
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
            GROUP BY {{ render_grouping_columns() -}} top_value
            ORDER BY {{ render_grouping_columns() -}} total_values DESC
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {% macro render_grouping_columns() %}
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
            {{ lib.render_grouping_column_names() }} {{- ', ' -}}
        {%- endif -%}
    {% endmacro %}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '', partition_by_enabled = false) -%}
    
        {%- if partition_by_enabled == true -%}PARTITION BY
            {%- if lib.time_series is not none -%}
                {{" "}}top_col_values.time_period
            {%- elif lib.data_groupings is none -%}
                {{" "}}NULL
            {%- endif -%}
        {%- endif -%}
    
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{- "" if loop.first and lib.time_series is none and partition_by_enabled else "," -}}
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
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {%- if lib.time_series is not none -%} {{- "," }}
        top_values.time_period,
        top_values.time_period_utc
        {%- endif -%}
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"

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
            {% if lib.time_series is not none -%}
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            {% endif -%}
            RANK() OVER({{- render_data_grouping('top_col_values', indentation = ' ', partition_by_enabled=true) }}
                ORDER BY top_col_values.total_values DESC) as top_values_rank {{- render_data_grouping('top_col_values', indentation = ' ') }}
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
            GROUP BY {{ render_grouping_columns() -}} top_value
            ORDER BY {{ render_grouping_columns() -}} total_values DESC
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {% macro render_grouping_columns() %}
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
            {{ lib.render_grouping_column_names() }} {{- ', ' -}}
        {%- endif -%}
    {% endmacro %}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '', partition_by_enabled = false) -%}
    
        {%- if partition_by_enabled == true -%}PARTITION BY
            {%- if lib.time_series is not none -%}
                {{" "}}top_col_values.time_period
            {%- elif lib.data_groupings is none -%}
                {{" "}}NULL
            {%- endif -%}
        {%- endif -%}
    
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{- "" if loop.first and lib.time_series is none and partition_by_enabled else "," -}}
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
        MAX(1 + NULL) AS actual_value,
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
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {%- if lib.time_series is not none -%} {{- "," }}
        top_values.time_period,
        top_values.time_period_utc
        {%- endif -%}
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"

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
            {% if lib.time_series is not none -%}
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            {% endif -%}
            RANK() OVER({{- render_data_grouping('top_col_values', indentation = ' ', partition_by_enabled=true) }}
                ORDER BY top_col_values.total_values DESC) as top_values_rank {{- render_data_grouping('top_col_values', indentation = ' ') }}
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
            GROUP BY {{ render_grouping_columns() -}} {{ lib.render_target_column('analyzed_table') }}
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {% macro render_grouping_columns() %}
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
            {{ lib.render_grouping_column_names() }} {{- ', ' -}}
        {%- endif -%}
    {% endmacro %}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '', partition_by_enabled = false) -%}
    
        {%- if partition_by_enabled == true -%}PARTITION BY
            {%- if lib.time_series is not none -%}
                {{" "}}top_col_values.time_period
            {%- elif lib.data_groupings is none -%}
                {{" "}}NULL
            {%- endif -%}
        {%- endif -%}
    
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{- "" if loop.first and lib.time_series is none and partition_by_enabled else "," -}}
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
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {%- if lib.time_series is not none -%} {{- "," }}
        top_values.time_period,
        top_values.time_period_utc
        {%- endif -%}
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {% if lib.time_series is not none -%}
    GROUP BY time_period, time_period_utc
    {%- endif -%}
    {%- if (lib.data_groupings is not none and (lib.data_groupings | length) > 0) -%}
        {% if lib.time_series is none %}GROUP BY {% endif -%}
        {%- for attribute in lib.data_groupings -%}
        {{ ', ' if lib.time_series is not none and loop.index == 1 else "" }}top_values.grouping_{{ attribute }}
        {%- endfor -%}
    {%- endif -%}
    ```
=== "Trino"

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
            {% if lib.time_series is not none -%}
            top_col_values.time_period as time_period,
            top_col_values.time_period_utc as time_period_utc,
            {% endif -%}
            RANK() OVER({{- render_data_grouping('top_col_values', indentation = ' ', partition_by_enabled=true) }}
                ORDER BY top_col_values.total_values DESC) as top_values_rank {{- render_data_grouping('top_col_values', indentation = ' ') }}
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
            GROUP BY {{ render_grouping_columns() -}} top_value
            ORDER BY {{ render_grouping_columns() -}} total_values DESC
        ) AS top_col_values
    ) AS top_values
    WHERE top_values_rank <= {{ parameters.top }}
    {%- endmacro -%}
    
    {% macro render_grouping_columns() %}
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
            {{ lib.render_grouping_column_names() }} {{- ', ' -}}
        {%- endif -%}
    {% endmacro %}
    
    {%- macro render_data_grouping(table_alias_prefix = '', indentation = '', partition_by_enabled = false) -%}
    
        {%- if partition_by_enabled == true -%}PARTITION BY
            {%- if lib.time_series is not none -%}
                {{" "}}top_col_values.time_period
            {%- elif lib.data_groupings is none -%}
                {{" "}}NULL
            {%- endif -%}
        {%- endif -%}
    
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{- "" if loop.first and lib.time_series is none and partition_by_enabled else "," -}}
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
        MAX(1 + NULL) AS actual_value,
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
        MAX({{ parameters.expected_values | length }}) AS expected_value
        {%- if lib.time_series is not none -%} {{- "," }}
        top_values.time_period,
        top_values.time_period_utc
        {%- endif -%}
        {{- render_data_grouping('top_values', indentation = lib.eol() ~ '    ') }}
    {{ render_from_subquery() }}
    {%- endif -%}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___



## number found in set percent
Column level sensor that calculates the percentage of rows for which the tested numeric column contains a value from the list of expected values.
 Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value).
 This sensor is useful for checking numeric columns that store numeric codes (such as status codes) that the only values found in the column are from a set of expected values.

**Sensor summary**

The number found in set percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | accepted_values | <span class="no-wrap-code">`column/accepted_values/number_found_in_set_percent`</span> | [*sensors/column/accepted_values*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/accepted_values/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`expected_values`</span>|A list of expected values that must be present in a numeric column, only values from this list are accepted and rows having these values in the tested column are counted as valid rows.|*integer_list*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        {#- Two approaches can be taken here. What if COUNT(*) = 0 AND value set is empty? This solution is the most convenient. -#}
        MAX(0.0)
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        {#- Two approaches can be taken here. What if COUNT(*) = 0 AND value set is empty? This solution is the most convenient. -#}
        MAX(0.0)
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        {#- Two approaches can be taken here. What if COUNT(*) = 0 AND value set is empty? This solution is the most convenient. -#}
        MAX(0.0)
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{lib.render_target_column('analyzed_table')}} IN ({{extract_in_list(parameters.expected_values)}}))
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        {#- Two approaches can be taken here. What if COUNT(*) = 0 AND value set is empty? This solution is the most convenient. -#}
        MAX(0.0)
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        {#- Two approaches can be taken here. What if COUNT(*) = 0 AND value set is empty? This solution is the most convenient. -#}
        MAX(0.0)
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
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
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
    {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        {#- Two approaches can be taken here. What if COUNT(*) = 0 AND value set is empty? This solution is the most convenient. -#}
        MAX(0.0)
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{lib.render_target_column('analyzed_table')}} IN ({{extract_in_list(parameters.expected_values)}}))
                        THEN 1
                    ELSE 0
                END
              ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        {#- Two approaches can be taken here. What if COUNT(*) = 0 AND value set is empty? This solution is the most convenient. -#}
        MAX(CAST(0.0 AS DOUBLE))
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM (
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}
    ) analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
            {#- Two approaches can be taken here. What if COUNT(*) = 0 AND value set is empty? This solution is the most convenient. -#}
            MAX(0.0)
            {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{lib.render_target_column('analyzed_table')}} IN ({{extract_in_list(parameters.expected_values)}}))
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"

    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        {#- Two approaches can be taken here. What if COUNT(*) = 0 AND value set is empty? This solution is the most convenient. -#}
        MAX(0.0)
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{lib.render_target_column('analyzed_table')}} IN ({{extract_in_list(parameters.expected_values)}}))
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        {#- Two approaches can be taken here. What if COUNT(*) = 0 AND value set is empty? This solution is the most convenient. -#}
        MAX(0.0)
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{values_list|join(', ')}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        {#- Two approaches can be taken here. What if COUNT(*) = 0 AND value set is empty? This solution is the most convenient. -#}
        MAX(0.0)
        {%- else -%}
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN ({{lib.render_target_column('analyzed_table')}} IN ({{extract_in_list(parameters.expected_values)}}))
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list) -%}
        {{ values_list|join(', ') -}}
    {% endmacro %}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        {#- Two approaches can be taken here. What if COUNT(*) = 0 AND value set is empty? This solution is the most convenient. -#}
        MAX(CAST(0.0 AS DOUBLE))
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM (
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}
    ) analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___



## text found in set percent
Column level sensor that calculates the percentage of rows for which the tested string (text) column contains a value from the list of expected values.
 Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value).
 This sensor is useful for testing that a string column with a low number of unique values (country, currency, state, gender, etc.) contains only values from a set of expected values.

**Sensor summary**

The text found in set percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | accepted_values | <span class="no-wrap-code">`column/accepted_values/text_found_in_set_percent`</span> | [*sensors/column/accepted_values*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/accepted_values/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`expected_values`</span>|A list of expected values that must be present in a string column, only values from this list are accepted and rows having these values in the tested column are counted as valid rows.|*string_list*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

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
    {% endmacro -%}
    
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        MAX(0.0)
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"

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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        MAX(0.0)
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        MAX(0.0)
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"

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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        MAX(0.0)
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"

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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        MAX(0.0)
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        MAX(0.0)
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"

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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        MAX(CAST(0.0 AS DOUBLE))
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM (
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}
        ) analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"

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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        MAX(0.0)
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"

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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        MAX(0.0)
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"

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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        MAX(0.0)
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"

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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        MAX(0.0)
        {%- else -%}
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"

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
    {% endmacro -%}
    
    {%- macro actual_value() -%}
        {%- if 'expected_values' not in parameters or parameters.expected_values|length == 0 -%}
        MAX(CAST(0.0 AS DOUBLE))
        {%- else -%}
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IN ({{ extract_in_list(parameters.expected_values) }})
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        {{ actual_value() }} AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM (
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}
        ) analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___



## text valid country code percent
Column level sensor that calculates the percentage of rows with text values with a valid country codes in an analyzed column.

**Sensor summary**

The text valid country code percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | accepted_values | <span class="no-wrap-code">`column/accepted_values/text_valid_country_code_percent`</span> | [*sensors/column/accepted_values*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/accepted_values/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM (
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}
    ) analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"

    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM (
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}
    ) analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___



## text valid currency code percent
Column level sensor that calculates the percentage of rows with text values that are valid currency codes in an analyzed column.

**Sensor summary**

The text valid currency code percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | accepted_values | <span class="no-wrap-code">`column/accepted_values/text_valid_currency_code_percent`</span> | [*sensors/column/accepted_values*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/accepted_values/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_column_cast_to_string('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table') }}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table') }}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_column_cast_to_string('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM (
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}
    ) analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"

    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_column_cast_to_string('analyzed_table')}}) IN ('ALL',	'AFN',	'ARS',	'AWG',	'AUD',	'AZN',	'BSD',	'BBD',	'BYN',	'BZD',	'BMD',	'BOB',	'BAM',	'BWP',	'BGN',	'BRL',	'BND',	'KHR',	'CAD',	'KYD',	'CLP',	'CNY',	'COP',	'CRC',	'HRK',	'CUP',	'CZK',	'DKK',	'DOP',	'XCD',	'EGP',	'SVC',	'EUR',	'FKP',	'FJD',	'GHS',	'GIP',	'GTQ',	'GGP',	'GYD',	'HNL',	'HKD',	'HUF',	'ISK',	'INR',	'IDR',	'IRR',	'IMP',	'ILS',	'JMD',	'JPY',	'JEP',	'KZT',	'KPW',	'KRW',	'KGS',	'LAK',	'LBP',	'LRD',	'MKD',	'MYR',	'MUR',	'MXN',	'MNT',	'MZN',	'NAD',	'NPR',	'ANG',	'NZD',	'NIO',	'NGN',	'NOK',	'OMR',	'PKR',	'PAB',	'PYG',	'PEN',	'PHP',	'PLN',	'QAR',	'RON',	'RUB',	'SHP',	'SAR',	'RSD',	'SCR',	'SGD',	'SBD',	'SOS',	'ZAR',	'LKR',	'SEK',	'CHF',	'SRD',	'SYP',	'TWD',	'THB',	'TTD',	'TRY',	'TVD',	'UAH',	'AED',	'GBP',	'USD',	'UYU',	'UZS',	'VEF',	'VND',	'YER',	'ZWD',	'LEK',	'؋',	'$',	'Ƒ',	'₼',	'BR',	'BZ$',	'$B',	'KM',	'P',	'ЛВ',	'R$',	'៛',	'¥',	'₡',	'KN',	'₱',	'KČ',	'KR',	'RD$', '£',	'€',	'¢',	'Q',	'L',	'FT',	'₹',	'RP',	'﷼',	'₪',	'J$',	'₩',	'₭',	'ДЕН',	'RM',	'₨',	'₮',	'د.إ',	'MT',	'C$',	'₦',	'B/.',	'GS',	'S/.', 'ZŁ',	'LEI',	'ДИН.',	'S',	'R',	'NT$',	'฿',	'TT$',	'₺',	'₴',	'$U',	'BS',	'₫', 'Z$')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM (
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}
    ) analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___




## What's next
- Learn how the [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) are defined in DQOps and what is the definition of all Jinja2 macros used in the templates
- Understand how DQOps [runs data quality checks](../../../dqo-concepts/architecture/data-quality-check-execution-flow.md), rendering templates to SQL queries
