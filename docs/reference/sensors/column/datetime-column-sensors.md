---
title: DQOps data quality datetime sensors
---
# DQOps data quality datetime sensors
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **datetime** category supported by DQOps are listed below. Those sensors are measured on a column level.

---


## date in range percent
Column level sensor that finds the percentage of date values that are outside an accepted range. This sensor detects presence of fake or corrupted dates such as 1900-01-01 or 2099-12-31.

**Sensor summary**

The date in range percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | datetime | <span class="no-wrap-code">`column/datetime/date_in_range_percent`</span> | [*sensors/column/datetime*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/datetime/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`min_date`</span>|The earliest accepted date.|*date*| ||
|<span class="no-wrap-code">`max_date`</span>|The latest accepted date.|*date*| ||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_date) }} AND {{ lib.render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_date) }} THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_date) }} AND {{ lib.render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_date) }} THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_date) }} AND {{ lib.render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_date) }} THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_date) }} AND {{ lib.render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_date) }} THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_date) }} AND {{ lib.render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_date) }} THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_date) }} AND {{ lib.render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_date) }} THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN {{ lib.render_date_format_cast() }} >= CAST({{ lib.make_text_constant(parameters.min_date) }} AS TIMESTAMP) AND {{ lib.render_date_format_cast() }} <= CAST({{ lib.make_text_constant(parameters.max_date) }} AS TIMESTAMP) THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_date) }} AND {{ lib.render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_date) }} THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_date) }} AND {{ lib.render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_date) }} THEN 1
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_date) }} AND {{ lib.render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_date) }} THEN 1
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
    
    {% macro render_ordering_column_names() %}
        {%- if lib.time_series is not none and lib.time_series.mode != 'current_time' -%}
            ORDER BY {{ lib.render_time_dimension_expression(lib.table_alias_prefix) }}
        {%- elif (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) %}
            {{ ', ' }}
        {% endif %}
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) -%}
            {%- for attribute in lib.data_groupings -%}
                {%- if not loop.first -%}
                    {{ ', ' }}
                {%- endif -%}
                    {{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_date) }} AND {{ lib.render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_date) }} THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- render_ordering_column_names() -}}
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN {{ lib.render_date_format_cast() }} >= CAST({{ lib.make_text_constant(parameters.min_date) }} AS TIMESTAMP) AND {{ lib.render_date_format_cast() }} <= CAST({{ lib.make_text_constant(parameters.max_date) }} AS TIMESTAMP) THEN 1
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



## date values in future percent
Column level sensor that calculates the percentage of rows with a date value in the future, compared with the current date.

**Sensor summary**

The date values in future percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | datetime | <span class="no-wrap-code">`column/datetime/date_values_in_future_percent`</span> | [*sensors/column/datetime*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/datetime/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {% if lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        {% elif lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        {% elif lib.is_local_date_time(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        {% else -%}
                            SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        {% endif -%}
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN
                        {% if lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        {% elif lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        {% elif lib.is_local_date_time(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        {% else -%}
                            CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        {% endif -%}
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN
                        {% if lib.is_instant(table.columns[column_name].type_snapshot.column_typ) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        {% elif lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        {% elif lib.is_local_date_time(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        {% else -%}
                            ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        {% endif -%}
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN
                        {% if lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        {% elif lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        {% elif lib.is_local_date_time(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        {% else -%}
                            CAST({{ lib.render_target_column('analyzed_table') }} AS DATETIME) > CURRENT_TIMESTAMP()
                        {% endif -%}
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN
                        {% if lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        {% elif lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        {% elif lib.is_local_date_time(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        {% else -%}
                            CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP
                        {% endif -%}
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN
                        {% if lib.is_instant(table.columns[column_name].type_snapshot.column_typ) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        {% elif lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        {% elif lib.is_local_date_time(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        {% else -%}
                            ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        {% endif -%}
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
    
    {% macro render_value_in_future() -%}
    
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN
                        {% if lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        {% elif lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        {% elif lib.is_local_date_time(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        {% else -%}
                            TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP
                        {% endif -%}
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN
                        {% if lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        {% elif lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        {% elif lib.is_local_date_time(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        {% else -%}
                            ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        {% endif -%}
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN
                        {% if lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        {% elif lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        {% elif lib.is_local_date_time(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        {% else -%}
                            TRY_TO_TIMESTAMP({{ lib.render_target_column('analyzed_table') }}) > CURRENT_TIMESTAMP
                        {% endif -%}
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN
                        {% if lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        {% elif lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        {% elif lib.is_local_date_time(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        {% else -%}
                            CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        {% endif -%}
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
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN
                        {% if lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > SYSDATETIME()
                        {% elif lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > GETDATE()
                        {% elif lib.is_local_date_time(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > GETDATE()
                        {% else -%}
                            TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS DATETIME) > SYSDATETIME()
                        {% endif -%}
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN
                        {% if lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        {% elif lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        {% elif lib.is_local_date_time(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                            {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        {% else -%}
                            TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP
                        {% endif -%}
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



## text match date format percent
Column level sensor that calculates the percentage of text values that match an expected date format.

**Sensor summary**

The text match date format percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | datetime | <span class="no-wrap-code">`column/datetime/text_match_date_format_percent`</span> | [*sensors/column/datetime*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/datetime/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`date_format`</span>|Expected date format. The sensor will try to parse the column records and cast the data using this format.|*enum*|:material-check-bold:|*DD/MM/YYYY*<br/>*DD-MM-YYYY*<br/>*DD.MM.YYYY*<br/>*YYYY-MM-DD*<br/>|






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
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL AND
                         REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r{{lib.render_date_format_regex(parameters.date_format)}}) IS NOT FALSE
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL AND
                         REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), {{lib.render_date_format_regex(parameters.date_format)}}) IS NOT FALSE
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
                    WHEN REGEXP_MATCHES(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR), {{ lib.render_date_format(parameters.date_format) }}) IS TRUE
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
            ELSE 100.0 * SUM(CASE
                  WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL AND
                       {{ lib.render_regex(lib.render_target_column('analyzed_table'), lib.render_date_format_regex(parameters.date_format), should_make_text_constant = false ) }}
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
            ELSE 100.0 * SUM(CASE
                  WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL AND
                       REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, {{lib.render_date_format_regex(parameters.date_format)}})
                     THEN 1
                  ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL AND
                            CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) ~ {{lib.render_date_format_regex(parameters.date_format)}} IS NOT NULL
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL AND
                         REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), {{lib.render_date_format_regex(parameters.date_format)}})
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL AND
                         {{lib.render_target_column('analyzed_table')}} ~ {{lib.render_date_format_regex(parameters.date_format)}})
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL AND
                         REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, {{lib.render_date_format_regex(parameters.date_format)}})
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL AND
                         REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), {{lib.render_date_format_regex(parameters.date_format)}}) IS NOT FALSE
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL AND
                         {{ lib.render_target_column('analyzed_table') }} LIKE {{lib.render_date_format_regex(parameters.date_format)}} ESCAPE '~'
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL AND
                         REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), {{lib.render_date_format_regex(parameters.date_format)}})
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
