---
title: DQOps data quality text sensors
---
# DQOps data quality text sensors
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **text** category supported by DQOps are listed below. Those sensors are measured on a column level.

---


## text length above max length count
Column level sensor that calculates the count of text values that are longer than a given length in a column.

**Sensor summary**

The text length above max length count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_length_above_max_length_count`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_length`</span>|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|*integer*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        SUM(
            CASE
                WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) > {{(parameters.max_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR)) > {{(parameters.max_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS CHAR)) > {{(parameters.max_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}::VARCHAR) > {{(parameters.max_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_target_column('analyzed_table') }}::VARCHAR) > {{(parameters.max_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) > {{(parameters.max_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) > {{(parameters.max_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LEN({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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



## text length above max length percent
Column level sensor that calculates the percentage of text values that are longer than a given length in a column.

**Sensor summary**

The text length above max length percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_length_above_max_length_percent`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_length`</span>|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|*integer*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) > {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR)) > {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS CHAR)) > {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}::VARCHAR) > {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
                    WHEN LENGTH({{ lib.render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table') }}::VARCHAR) > {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) > {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) > {{(parameters.max_length)}}
                        THEN 1
                    ELSE 0
                END
            )/ COUNT({{ lib.render_target_column('analyzed_table') }})
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
                    WHEN LEN({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
                    WHEN LENGTH({{ lib.render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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



## text length below min length count
Column level sensor that calculates the count of text values that are shorter than a given length in a column.

**Sensor summary**

The text length below min length count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_length_below_min_length_count`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`min_length`</span>|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|*integer*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        SUM(
            CASE
                WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) < {{(parameters.min_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) < {{(parameters.min_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR)) < {{(parameters.min_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS CHAR)) < {{(parameters.min_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) < {{(parameters.min_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}::VARCHAR) < {{(parameters.min_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_column_cast_to_string('analyzed_table')}}) < {{(parameters.min_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_target_column('analyzed_table') }}::VARCHAR) < {{(parameters.min_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) < {{(parameters.min_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) < {{(parameters.min_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LEN({{ lib.render_target_column('analyzed_table')}}) < {{(parameters.min_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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
        SUM(
            CASE
                WHEN LENGTH({{ lib.render_column_cast_to_string('analyzed_table')}}) < {{(parameters.min_length)}}
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
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



## text length below min length percent
Column level sensor that calculates the percentage of text values that are shorter than a given length in a column.

**Sensor summary**

The text length below min length percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_length_below_min_length_percent`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`min_length`</span>|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|*integer*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) < {{(parameters.min_length)}}
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
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) < {{(parameters.min_length)}}
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
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR)) < {{(parameters.min_length)}}
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
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS CHAR)) < {{(parameters.min_length)}}
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
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) < {{(parameters.min_length)}}
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}::VARCHAR) < {{(parameters.min_length)}}
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
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN LENGTH({{ lib.render_column_cast_to_string('analyzed_table')}}) < {{(parameters.min_length)}}
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
                    WHEN LENGTH({{ lib.render_target_column('analyzed_table') }}::VARCHAR) < {{(parameters.min_length)}}
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
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) < {{(parameters.min_length)}}
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
                    WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS STRING)) < {{(parameters.min_length)}}
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
                    WHEN LEN({{ lib.render_target_column('analyzed_table')}}) < {{(parameters.min_length)}}
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
                    WHEN LENGTH({{ lib.render_column_cast_to_string('analyzed_table')}}) < {{(parameters.min_length)}}
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



## text length in range percent
Column level sensor that calculates the percentage of text values with a length below the indicated length in a column.

**Sensor summary**

The text length in range percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_length_in_range_percent`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`min_length`</span>|Sets a minimum text length|*integer*|:material-check-bold:||
|<span class="no-wrap-code">`max_length`</span>|Sets a maximum text length|*integer*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ render_column_cast_to_string('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR) ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH(CAST({{ lib.render_target_column('analyzed_table')}} AS CHAR)) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table')}} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }}::VARCHAR ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
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
                        WHEN LENGTH( {{ lib.render_column_cast_to_string('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }}::VARCHAR ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LENGTH( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
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
            ELSE
                100.0 * SUM(
                    CASE
                        WHEN LEN( {{ lib.render_target_column('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
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
                        WHEN LENGTH( {{ lib.render_column_cast_to_string('analyzed_table') }} ) BETWEEN {{parameters.min_length}} AND {{parameters.max_length}} THEN 1
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



## text max length
Column level sensor that ensures that the length of text values in a column does not exceed the maximum accepted length.

**Sensor summary**

The text max length sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_max_length`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        MAX(
            LENGTH({{render_column_cast_to_string('analyzed_table')}})
        ) AS actual_value
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
        MAX(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
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
        MAX(
            LENGTH(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR))
        ) AS actual_value
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
        MAX(
            LENGTH(CAST({{ lib.render_target_column('analyzed_table') }} AS CHAR))
        ) AS actual_value
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
        MAX(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
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
        MAX(
            LENGTH({{ lib.render_target_column('analyzed_table') }}::VARCHAR)
        ) AS actual_value
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
        MAX(
            LENGTH({{lib.render_column_cast_to_string('analyzed_table')}})
        ) AS actual_value
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
        MAX(
            LENGTH({{ lib.render_target_column('analyzed_table') }}::VARCHAR)
        ) AS actual_value
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
        MAX(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
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
        MAX(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
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
        MAX(
            LEN({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
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
        MAX(
            LENGTH({{lib.render_column_cast_to_string('analyzed_table')}})
        ) AS actual_value
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



## text mean length
Column level sensor that ensures that the length of text values in a column does not exceed the mean accepted length.

**Sensor summary**

The text mean length sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_mean_length`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        AVG(
            LENGTH({{render_column_cast_to_string('analyzed_table')}})
        ) AS actual_value
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
        AVG(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
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
        AVG(
            LENGTH(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR))
        ) AS actual_value
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
        AVG(
            LENGTH(CAST({{ lib.render_target_column('analyzed_table') }} AS CHAR))
        ) AS actual_value
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
        AVG(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
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
        AVG(
            LENGTH({{ lib.render_target_column('analyzed_table') }}::VARCHAR)
        ) AS actual_value
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
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        AVG(
            LENGTH({{render_column_cast_to_string('analyzed_table')}})
        ) AS actual_value
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
        AVG(
            LENGTH({{ lib.render_target_column('analyzed_table') }}::VARCHAR)
        ) AS actual_value
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
        AVG(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
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
        AVG(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
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
        AVG(
            CAST(LEN({{ lib.render_target_column('analyzed_table') }}) AS FLOAT)
        ) AS actual_value
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
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
            TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        AVG(
            LENGTH({{render_column_cast_to_string('analyzed_table')}})
        ) AS actual_value
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



## text min length
Column level sensor that ensures that the length of text values in a column does not exceed the minimum accepted length.

**Sensor summary**

The text min length sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | text | <span class="no-wrap-code">`column/text/text_min_length`</span> | [*sensors/column/text*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/text/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        MIN(
            LENGTH({{render_column_cast_to_string('analyzed_table')}})
        ) AS actual_value
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
        MIN(
            LENGTH({{lib.render_target_column('analyzed_table')}})
        ) AS actual_value
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
        MIN(
            LENGTH(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR))
        ) AS actual_value
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
        MIN(
            LENGTH(CAST({{ lib.render_target_column('analyzed_table') }} AS CHAR))
        ) AS actual_value
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
        MIN(
            LENGTH({{ lib.render_target_column('analyzed_table') }})
        ) AS actual_value
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
        MIN(
            LENGTH({{ lib.render_target_column('analyzed_table') }}::VARCHAR)
        ) AS actual_value
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
        MIN(
            LENGTH({{ lib.render_column_cast_to_string('analyzed_table') }})
        ) AS actual_value
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
        MIN(
            LENGTH({{ lib.render_target_column('analyzed_table') }}::VARCHAR)
        ) AS actual_value
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
        MIN(
            LENGTH({{lib.render_target_column('analyzed_table')}})
        ) AS actual_value
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
        MIN(
            LENGTH({{lib.render_target_column('analyzed_table')}})
        ) AS actual_value
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
        MIN(
            LEN({{lib.render_target_column('analyzed_table')}})
        ) AS actual_value
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
        MIN(
            LENGTH({{ lib.render_column_cast_to_string('analyzed_table') }})
        ) AS actual_value
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
