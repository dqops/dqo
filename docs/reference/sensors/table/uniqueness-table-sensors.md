---
title: DQOps data quality uniqueness sensors, SQL examples
---
# DQOps data quality uniqueness sensors, SQL examples
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **uniqueness** category supported by DQOps are listed below. Those sensors are measured on a table level.

---


## duplicate record count
Table sensor that executes a duplicate record count query.

**Sensor summary**

The duplicate record count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| table | uniqueness | <span class="no-wrap-code">`table/uniqueness/duplicate_record_count`</span> | [*sensors/table/uniqueness*](https://github.com/dqops/dqo/tree/develop/home/sensors/table/uniqueness/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`columns`</span>|A list of columns used for uniqueness record duplicate verification.|*string_list*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN SUM(duplicated_count) IS NULL THEN 0
            ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
            END AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS duplicated_count
        {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
        {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "ClickHouse"

    ```sql+jinja
    {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN sumOrNull(duplicated_count) IS NULL THEN 0
            ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
            END AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS duplicated_count
        {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
        {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='toString(', column_suffix=')') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN SUM(duplicated_count) IS NULL THEN 0
            ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
            END AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS duplicated_count
        {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
        {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DB2"

    ```sql+jinja
    {% import '/dialects/db2.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN SUM(duplicated_count) IS NULL THEN 0
            ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
            END AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
        FROM (
            SELECT COUNT(*) AS duplicated_count
            {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
            FROM (
                SELECT
                    {{ extract_in_list(parameters.columns) -}}
                    {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                FROM {{ lib.render_target_table() }} analyzed_table_nested
                {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
        ) analyzed_table
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN SUM(duplicated_count) IS NULL THEN 0
            ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
            END AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS duplicated_count
        {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
        {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST( ', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "HANA"

    ```sql+jinja
    {% import '/dialects/hana.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN SUM(duplicated_count) IS NULL THEN 0
            ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
            END AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
        FROM (
            SELECT COUNT(*) AS duplicated_count
            {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
            FROM (
                SELECT
                    {{ extract_in_list(parameters.columns) -}}
                    {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
        )
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MariaDB"

    ```sql+jinja
    {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN SUM(duplicated_count) IS NULL THEN 0
            ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
            END AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS duplicated_count
        {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
        {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN SUM(duplicated_count) IS NULL THEN 0
            ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
            END AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS duplicated_count
        {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
        {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN SUM(duplicated_count) IS NULL THEN 0
            ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
            END AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
        FROM (
            SELECT COUNT(*) AS duplicated_count
            {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
            FROM (
                SELECT
                    {{ extract_in_list(parameters.columns) -}}
                    {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                FROM {{ lib.render_target_table() }} analyzed_table_nested
                {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
        ) analyzed_table
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN SUM(duplicated_count) IS NULL THEN 0
            ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
            END AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS duplicated_count
        {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
        {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN SUM(duplicated_count) IS NULL THEN 0
            ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
            END AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
        FROM (
            SELECT COUNT(*) AS duplicated_count
            {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
            FROM (
                SELECT
                    {{ extract_in_list(parameters.columns) -}}
                    {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
        )
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "QuestDB"

    ```sql+jinja
    {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        COALESCE(
            SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
            , 0) AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT() AS duplicated_count
            {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
        FROM (
             SELECT
                 {{ extract_in_list(parameters.columns) -}}
                 {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                 {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
             FROM {{ lib.render_target_table() }} AS analyzed_table_nested
             {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
        )
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN SUM(duplicated_count) IS NULL THEN 0
            ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
            END AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS duplicated_count
        {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
        {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"

    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN SUM(duplicated_count) IS NULL THEN 0
            ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
            END AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS duplicated_count
        {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
        {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN SUM(duplicated_count) IS NULL THEN 0
            ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
            END AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS duplicated_count
        {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
        {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro -%}
    
    {% macro render_group_by(table_alias_prefix = 'grouping_table', indentation = '    ') %}
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
        GROUP BY
        {%- endif -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{- ',' if not loop.first -}}{{- lib.eol() }}
                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute -}}
            {%- endfor -%}
        {%- endif -%}
        {%- if lib.time_series is not none -%}
            {{ ',' if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}{{- lib.eol() -}}
            {{ indentation }}time_period,{{ lib.eol() -}}
            {{ indentation }}time_period_utc
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN SUM(duplicated_count) IS NULL THEN 0
            ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
            END AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS duplicated_count
        {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
        {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{ render_group_by('grouping_table') }}
    ```
=== "Teradata"

    ```sql+jinja
    {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN SUM(duplicated_count) IS NULL THEN 0
            ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
            END AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS duplicated_count
        {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
        {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4096))') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN SUM(duplicated_count) IS NULL THEN 0
            ELSE SUM(CASE WHEN duplicated_count > 1 THEN 1 ELSE 0 END)
            END AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
        FROM (
            SELECT COUNT(*) AS duplicated_count
            {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
            FROM (
                SELECT
                    {{ extract_in_list(parameters.columns) -}}
                    {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                FROM {{ lib.render_target_table() }} AS analyzed_table_nested
                {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
        )
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___



## duplicate record percent
Table sensor that executes a duplicate record percent query.

**Sensor summary**

The duplicate record percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| table | uniqueness | <span class="no-wrap-code">`table/uniqueness/duplicate_record_percent`</span> | [*sensors/table/uniqueness*](https://github.com/dqops/dqo/tree/develop/home/sensors/table/uniqueness/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`columns`</span>|A list of columns used for uniqueness record duplicate verification.|*string_list*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro -%}
    
    SELECT
        CASE WHEN SUM(distinct_records) IS NULL THEN 0
            ELSE (1 - SUM(distinct_records) / SUM(records_number)) * 100.0 END
            AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS records_number,
            COUNT(*) OVER (PARTITION BY {{ extract_in_list(parameters.columns) -}} ) AS distinct_records
            {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
            {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "ClickHouse"

    ```sql+jinja
    {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro -%}
    
    SELECT
        CASE WHEN sumOrNull(distinct_records) IS NULL THEN 0
            ELSE (1 - SUM(distinct_records) / SUM(records_number)) * 100.0 END
            AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS records_number,
            COUNT(*) OVER (PARTITION BY {{ extract_in_list(parameters.columns) -}} ) AS distinct_records
            {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
            {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='toString(', column_suffix=')') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro -%}
    
    SELECT
        CASE WHEN SUM(distinct_records) IS NULL THEN 0
            ELSE (1 - SUM(distinct_records) / SUM(records_number)) * 100.0 END
            AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS records_number,
            COUNT(*) OVER (PARTITION BY {{ extract_in_list(parameters.columns) -}} ) AS distinct_records
            {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
            {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DB2"

    ```sql+jinja
    {% import '/dialects/db2.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE WHEN SUM(distinct_records) IS NULL THEN 0
            ELSE (1 - SUM(distinct_records) * 1.0 / SUM(records_number)) * 100.0 END
            AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
        FROM (
            SELECT COUNT(*) AS records_number,
                COUNT(*) OVER (PARTITION BY {{ extract_in_list(parameters.columns) -}} ) AS distinct_records
                {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
            FROM (
                SELECT
                    {{ extract_in_list(parameters.columns) -}}
                    {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                FROM {{ lib.render_target_table() }} analyzed_table_nested
                {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
        ) analyzed_table
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro -%}
    
    SELECT
        CASE WHEN SUM(distinct_records) IS NULL THEN 0
            ELSE (1 - SUM(distinct_records) / SUM(records_number)) * 100.0 END
            AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS records_number,
            COUNT(*) OVER (PARTITION BY {{ extract_in_list(parameters.columns) -}} ) AS distinct_records
            {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
            {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST( ', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "HANA"

    ```sql+jinja
    {% import '/dialects/hana.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE WHEN SUM(distinct_records) IS NULL THEN 0
            ELSE (1 - SUM(distinct_records) / SUM(records_number)) * 100.0 END
            AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS records_number,
            COUNT(*) OVER (PARTITION BY {{ extract_in_list(parameters.columns) -}} ) AS distinct_records
        {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
        FROM (
            SELECT
                {{ extract_in_list(parameters.columns) -}}
                {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table_nested
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
        )
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MariaDB"

    ```sql+jinja
    {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE WHEN SUM(distinct_records) IS NULL THEN 0
            ELSE (1 - SUM(distinct_records) / SUM(records_number)) * 100.0 END
            AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS records_number,
            COUNT(*) OVER (PARTITION BY {{ extract_in_list(parameters.columns) -}} ) AS distinct_records
            {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
            {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE WHEN SUM(distinct_records) IS NULL THEN 0
            ELSE (1 - SUM(distinct_records) / SUM(records_number)) * 100.0 END
            AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS records_number,
            COUNT(*) OVER (PARTITION BY {{ extract_in_list(parameters.columns) -}} ) AS distinct_records
            {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
            {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns) ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE WHEN SUM(distinct_records) IS NULL THEN 0
            ELSE (1 - SUM(distinct_records) / SUM(records_number)) * 100.0 END
            AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
        FROM (
            SELECT COUNT(*) AS records_number,
                COUNT(*) OVER (PARTITION BY {{ extract_in_list(parameters.columns) -}} ) AS distinct_records
                {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='            ') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='            ') }}
            FROM (
                SELECT
                    {{ extract_in_list(parameters.columns) -}}
                    {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                    {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
                FROM {{ lib.render_target_table() }} analyzed_table_nested
                {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='            ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4000))') ~ ') IS NOT NULL') }}
        ) analyzed_table
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE WHEN SUM(distinct_records) IS NULL THEN 0
            ELSE (1 - SUM(distinct_records) / SUM(records_number)) * 100.0 END
            AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS records_number,
            COUNT(*) OVER (PARTITION BY {{ extract_in_list(parameters.columns) -}} ) AS distinct_records
            {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
            {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE WHEN SUM(distinct_records) IS NULL THEN 0
            ELSE (1 - SUM(distinct_records) / CAST(SUM(records_number) AS DOUBLE)) * 100.0 END
            AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS records_number,
            COUNT(*) OVER (PARTITION BY {{ extract_in_list(parameters.columns) -}} ) AS distinct_records
            {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
        FROM (
            SELECT
                {{ extract_in_list(parameters.columns) -}}
                {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table_nested
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
        )
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "QuestDB"

    ```sql+jinja
    {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}}
            {%- if not loop.last -%}
                {{- ", " if separate_by_comma else " || " -}}
            {%- endif -%}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        COALESCE(
            (1 - SUM(distinct_records) * 1.0 / SUM(records_number)) * 100.0
            , 0) AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT() AS records_number,
            COUNT_DISTINCT({{ extract_in_list(parameters.columns) -}}) AS distinct_records
            {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
            {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR', separate_by_comma=true) ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns, separate_by_comma=true) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE WHEN SUM(distinct_records) IS NULL THEN 0
            ELSE (1 - SUM(distinct_records) / SUM(records_number)) * 100.0 END
            AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS records_number,
            COUNT(*) OVER (PARTITION BY {{ extract_in_list(parameters.columns) -}} ) AS distinct_records
            {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
            {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_suffix='::VARCHAR') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"

    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE WHEN SUM(distinct_records) IS NULL THEN 0
            ELSE (1 - SUM(distinct_records) / SUM(records_number)) * 100.0 END
            AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS records_number,
            COUNT(*) OVER (PARTITION BY {{ extract_in_list(parameters.columns) -}} ) AS distinct_records
            {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
            {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE WHEN SUM(distinct_records) IS NULL THEN 0
            ELSE (1 - SUM(distinct_records) / SUM(records_number)) * 100.0 END
            AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS records_number,
            COUNT(*) OVER (PARTITION BY {{ extract_in_list(parameters.columns) -}} ) AS distinct_records
            {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
            {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS STRING)') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro -%}
    
    {% macro render_group_by(table_alias_prefix = 'grouping_table', indentation = '    ') %}
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none -%}
        GROUP BY
        {%- endif -%}
        {%- if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -%}
            {%- for attribute in lib.data_groupings -%}
                {{- ',' if not loop.first -}}{{- lib.eol() }}
                {{ indentation }}{{ table_alias_prefix }}.grouping_{{ attribute -}}
            {%- endfor -%}
        {%- endif -%}
        {%- if lib.time_series is not none -%}
            {{ ',' if lib.data_groupings is not none and (lib.data_groupings | length()) > 0 -}}{{- lib.eol() -}}
            {{ indentation }}time_period,{{ lib.eol() -}}
            {{ indentation }}time_period_utc
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        CASE WHEN SUM(distinct_records) IS NULL THEN 0
            ELSE (1 - SUM(distinct_records) * 1.0 / SUM(records_number)) * 100.0 END
            AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS records_number,
            COUNT(*) OVER (PARTITION BY {{ extract_in_list(parameters.columns) -}} ) AS distinct_records
            {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
            {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{ render_group_by('grouping_table') }}
    ```
=== "Teradata"

    ```sql+jinja
    {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE WHEN SUM(distinct_records) IS NULL THEN 0
            ELSE (1 - SUM(distinct_records) * 1.0 / SUM(records_number)) * 100.0 END
            AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS records_number,
            COUNT(*) OVER (PARTITION BY {{ extract_in_list(parameters.columns) -}} ) AS distinct_records
            {{- lib.render_data_grouping_projections('analyzed_table', indentation='        ') }}
            {{- lib.render_time_dimension_projection('analyzed_table', indentation='        ') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause(indentation='    ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR(4096))') ~ ') IS NOT NULL') }}
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {% macro extract_in_list(values_list, column_prefix = none, column_suffix = none, separate_by_comma = false) %}
        {%- set column_names = table.columns if values_list is none or (values_list | length()) == 0 else values_list -%}
        {%- for item in column_names -%}
            {{ (column_prefix) if column_prefix is not none -}} {{- lib.quote_identifier(item) -}} {{- (column_suffix) if column_suffix is not none -}} {{- ", " if not loop.last }} {{- "', ', " if separate_by_comma and not loop.last }}
        {%- endfor -%}
    {% endmacro %}
    
    SELECT
        CASE WHEN SUM(distinct_records) IS NULL THEN 0
            ELSE (1 - SUM(distinct_records) / CAST(SUM(records_number) AS DOUBLE)) * 100.0 END
            AS actual_value
        {{- lib.render_data_grouping_projections_reference('grouping_table') }}
        {{- lib.render_time_dimension_projection_reference('grouping_table') }}
    FROM (
        SELECT COUNT(*) AS records_number,
            COUNT(*) OVER (PARTITION BY {{ extract_in_list(parameters.columns) -}} ) AS distinct_records
        {{- lib.render_data_grouping_projections_reference('analyzed_table_nested', indentation='        ') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table_nested', indentation='        ') }}
        FROM (
            SELECT
                {{ extract_in_list(parameters.columns) -}}
                {{- lib.render_data_grouping_projections('analyzed_table_nested', indentation='            ') }}
                {{- lib.render_time_dimension_projection('analyzed_table_nested', indentation='            ') }}
            FROM {{ lib.render_target_table() }} AS analyzed_table_nested
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table_nested', indentation='        ', extra_filter = 'COALESCE(' ~ extract_in_list(parameters.columns, column_prefix='CAST(', column_suffix=' AS VARCHAR)') ~ ') IS NOT NULL') }}
        )
        GROUP BY {{ extract_in_list(parameters.columns) -}} {{- (", " ~ lib.render_grouping_column_names()) if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) or lib.time_series is not none }}
    ) grouping_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___




## What's next
- Learn how the [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) are defined in DQOps and what is the definition of all Jinja2 macros used in the templates
- Understand how DQOps [runs data quality checks](../../../dqo-concepts/architecture/data-quality-check-execution-flow.md), rendering templates to SQL queries
