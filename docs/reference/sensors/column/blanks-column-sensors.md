# Data quality blanks sensors
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **blanks** category supported by DQOps are listed below. Those sensors are measured on a column level.

---


## empty text count
Column level sensor that calculates the number of rows with an empty string.

**Sensor summary**

The empty text count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | blanks | <span class="no-wrap-code">`column/blanks/empty_text_count`</span> | [*sensors/column/blanks*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/blanks/) |







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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ render_column_cast_to_string('analyzed_table')}} = ''
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ lib.render_target_column('analyzed_table')}} = ''
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ lib.render_target_column('analyzed_table')}} = ''
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ lib.render_column_cast_to_string('analyzed_table')}} = ''
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LEN({{ lib.render_target_column('analyzed_table')}}) = 0
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ lib.render_column_cast_to_string('analyzed_table')}} = ''
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



## empty text percent
Column level sensor that calculates the percentage of rows with an empty string.

**Sensor summary**

The empty text percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | blanks | <span class="no-wrap-code">`column/blanks/empty_text_percent`</span> | [*sensors/column/blanks*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/blanks/) |







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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ render_column_cast_to_string('analyzed_table')}} = ''
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ lib.render_target_column('analyzed_table')}} = ''
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ lib.render_target_column('analyzed_table')}} = ''
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ lib.render_column_cast_to_string('analyzed_table')}} = ''
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) = 0
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LEN({{ lib.render_target_column('analyzed_table')}}) = 0
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ lib.render_column_cast_to_string('analyzed_table')}} = ''
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



## null placeholder text count
Column level sensor that calculates the number of rows with a null placeholder string column value.

**Sensor summary**

The null placeholder text count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | blanks | <span class="no-wrap-code">`column/blanks/null_placeholder_text_count`</span> | [*sensors/column/blanks*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/blanks/) |







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
                WHEN LOWER({{ render_column_cast_to_string('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
                WHEN LOWER({{ lib.render_target_column('analyzed_table') }}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
                WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\''\''', '-', '')
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
                WHEN LOWER({{ lib.render_target_column('analyzed_table') }}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
                WHEN LOWER({{ lib.render_target_column('analyzed_table') }}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '''', '-', '')
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
                WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\''\''', '-', '')
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
                WHEN LOWER({{ lib.render_column_cast_to_string('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '''''', '-', '')
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
                WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '-', '')
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
                WHEN LOWER({{ lib.render_target_column('analyzed_table') }}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
                WHEN LOWER({{ lib.render_target_column('analyzed_table') }}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
                WHEN LOWER({{ lib.render_target_column('analyzed_table') }}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\\', '-', '')
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
                WHEN LOWER({{ lib.render_column_cast_to_string('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '''''', '-', '')
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



## null placeholder text percent
Column level sensor that calculates the percentage of rows with a null placeholder string column value.

**Sensor summary**

The null placeholder text percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | blanks | <span class="no-wrap-code">`column/blanks/null_placeholder_text_percent`</span> | [*sensors/column/blanks*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/blanks/) |







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
                    WHEN LOWER({{ render_column_cast_to_string('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\''\''', '-', '')
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
                    WHEN LOWER({{ lib.render_target_column('analyzed_table') }}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
                WHEN LOWER({{ lib.render_target_column('analyzed_table') }}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '''', '-', '')
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
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\''\''', '-', '')
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
                    WHEN LOWER({{ lib.render_column_cast_to_string('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '''''', '-', '')
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
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '-', '')
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
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
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
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\\', '-', '')
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
                    WHEN LOWER({{ lib.render_column_cast_to_string('analyzed_table')}}) IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '''''', '-', '')
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



## text surrounded by whitespace count
Column level sensor that calculates the number of rows with text values that are surrounded by whitespace characters in an analyzed column.

**Sensor summary**

The text surrounded by whitespace count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | blanks | <span class="no-wrap-code">`column/blanks/text_surrounded_by_whitespace_count`</span> | [*sensors/column/blanks*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/blanks/) |







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
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) <> ''
                AND ({{ render_column_cast_to_string('analyzed_table')}}) <> TRIM({{ render_column_cast_to_string('analyzed_table')}})
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
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                AND ({{ lib.render_target_column('analyzed_table') }}) <> TRIM({{ lib.render_target_column('analyzed_table') }})
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
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                AND ({{ lib.render_target_column('analyzed_table') }}) <> TRIM({{ lib.render_target_column('analyzed_table') }})
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
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        SUM(
            CASE
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) <> ''
                AND ({{ render_column_cast_to_string('analyzed_table')}}) <> TRIM({{ render_column_cast_to_string('analyzed_table')}})
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
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        SUM(
            CASE
                WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) <> ''
                AND ({{ render_column_cast_to_string('analyzed_table')}}) <> TRIM({{ render_column_cast_to_string('analyzed_table')}})
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



## text surrounded by whitespace percent
Column level sensor that calculates the percentage of rows with text values that are surrounded by whitespace characters in an analyzed column.

**Sensor summary**

The text surrounded by whitespace percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | blanks | <span class="no-wrap-code">`column/blanks/text_surrounded_by_whitespace_percent`</span> | [*sensors/column/blanks*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/blanks/) |







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
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) <> ''
                    AND ({{ render_column_cast_to_string('analyzed_table')}}) <> TRIM({{ render_column_cast_to_string('analyzed_table')}})
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
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table') }}) <> TRIM({{ lib.render_target_column('analyzed_table') }})
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
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table') }}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table') }}) <> TRIM({{ lib.render_target_column('analyzed_table') }})
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
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) <> ''
                    AND ({{ render_column_cast_to_string('analyzed_table')}}) <> TRIM({{ render_column_cast_to_string('analyzed_table')}})
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
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) <> ''
                    AND ({{ lib.render_target_column('analyzed_table')}}) <> TRIM({{ lib.render_target_column('analyzed_table')}})
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
    
    {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
        {%- if (lib.target_column_data_type == 'STRING') -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
            SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT64') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATE') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIME') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    SAFE_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS VARCHAR)
        {%- else -%}
            {{ lib.render_target_column(analyzed_table_to_render) }}
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                    AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) <> ''
                    AND ({{ render_column_cast_to_string('analyzed_table')}}) <> TRIM({{ render_column_cast_to_string('analyzed_table')}})
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



## whitespace text count
Column level sensor that calculates the number of rows with a whitespace text column value.

**Sensor summary**

The whitespace text count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | blanks | <span class="no-wrap-code">`column/blanks/whitespace_text_count`</span> | [*sensors/column/blanks*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/blanks/) |







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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ render_column_cast_to_string('analyzed_table')}} <> ''
                AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) = ''
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ lib.render_target_column('analyzed_table') }} <> ''
                AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ lib.render_target_column('analyzed_table') }} <> ''
                AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND LEN({{ lib.render_target_column('analyzed_table')}}) <> 0
                AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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



## whitespace text percent
Column level sensor that calculates the percentage of rows with a whitespace text column value.

**Sensor summary**

The whitespace text percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | blanks | <span class="no-wrap-code">`column/blanks/whitespace_text_percent`</span> | [*sensors/column/blanks*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/blanks/) |







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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ render_column_cast_to_string('analyzed_table')}} <> ''
                    AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) = ''
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ lib.render_target_column('analyzed_table') }} <> ''
                    AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ lib.render_target_column('analyzed_table') }} <> ''
                    AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                    AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND LEN({{ lib.render_target_column('analyzed_table')}}) <> 0
                    AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                    AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
