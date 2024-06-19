---
title: DQOps data quality numeric sensors
---
# DQOps data quality numeric sensors
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **numeric** category supported by DQOps are listed below. Those sensors are measured on a column level.

---


## integer in range percent
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (can return date, integer, string, datetime, etc.).

**Sensor summary**

The integer in range percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/integer_in_range_percent`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`min_value`</span>|Minimum value range variable.|*long*| ||
|<span class="no-wrap-code">`max_value`</span>|Maximum value range variable.|*long*| ||






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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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



## invalid latitude count
Column level sensor that counts invalid latitude in a column.

**Sensor summary**

The invalid latitude count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/invalid_latitude_count`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 0
                ELSE 1
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
         {{- lib.render_where_clause(table_alias_prefix='original_table') }}
         ) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 0
                ELSE 1
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



## invalid longitude count
Column level sensor that counts invalid longitude in a column.

**Sensor summary**

The invalid longitude count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/invalid_longitude_count`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 0
                ELSE 1
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
           {{- lib.render_where_clause(table_alias_prefix='original_table') }}
           ) analyzed_table
      {{- lib.render_group_by() -}}
      {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 0
                ELSE 1
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
                WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 0
                ELSE 1
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



## mean
Column level sensor that counts the average (mean) of values in a column.

**Sensor summary**

The mean sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/mean`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
    SELECT
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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



## negative count
Column level sensor that counts negative values in a column.

**Sensor summary**

The negative count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/negative_count`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}
    ) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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



## negative percent
Column level sensor that counts percentage of negative values in a column.

**Sensor summary**

The negative percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/negative_percent`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |







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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
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



## non negative count
Column level sensor that counts non negative values in a column.

**Sensor summary**

The non negative count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/non_negative_count`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} >= 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} >= 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} >= 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} >= 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} >= 0 THEN 1
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
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}
    ) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} >= 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} >= 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} >= 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} >= 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} >= 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} >= 0 THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} >= 0 THEN 1
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



## non negative percent
Column level sensor that calculates the percent of non-negative values in a column.

**Sensor summary**

The non negative percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/non_negative_percent`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |







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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= 0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= 0 THEN 1
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



## number above max value count
Column level sensor that calculates the count of values that are above than a given value in a column.

**Sensor summary**

The number above max value count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/number_above_max_value_count`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_value`</span>|This field can be used to define custom value. In order to define custom value, user should write correct value as an integer. If value is not defined by user then default value is 0|*double*| ||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
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
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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
           {{- lib.render_where_clause(table_alias_prefix='original_table') }}
           ) analyzed_table
      {{- lib.render_group_by() -}}
      {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
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
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}}
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
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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



## number above max value percent
Column level sensor that calculates the percentage of values that are above than a given value in a column.

**Sensor summary**

The number above max value percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/number_above_max_value_percent`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`max_value`</span>|This field can be used to define custom value. In order to define custom value, user should write correct value as an integer. If value is not defined by user then default value is 0|*double*| ||






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
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} > {{(parameters.max_value)}} THEN 1
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



## number below min value count
Column level sensor that calculates the count of values that are below than a given value in a column.

**Sensor summary**

The number below min value count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/number_below_min_value_count`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`min_value`</span>|This field can be used to define custom value. In order to define custom value, user should write correct value as an integer. If value is not defined by user then default value is 0|*double*| ||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}}
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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
              {{- lib.render_where_clause(table_alias_prefix='original_table') }}
          ) analyzed_table
          {{- lib.render_group_by() -}}
          {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}}
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}}
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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
                WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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



## number below min value percent
Column level sensor that calculates the percentage of values that are below than a given value in a column.

**Sensor summary**

The number below min value percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/number_below_min_value_percent`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`min_value`</span>|This field can be used to define custom value. In order to define custom value, user should write correct value as an integer. If value is not defined by user then default value is 0|*double*| ||






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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table')}} < {{(parameters.min_value)}} THEN 1
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



## number in range percent
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (can return date, integer, string, datetime, etc.).

**Sensor summary**

The number in range percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/number_in_range_percent`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`min_value`</span>|Minimum value for the range.|*double*| ||
|<span class="no-wrap-code">`max_value`</span>|Maximum value for the range.|*double*| ||






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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END as actual_value
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END as actual_value
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= {{ parameters.min_value }} AND {{ lib.render_target_column('analyzed_table') }} <= {{ parameters.max_value }} THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END as actual_value
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



## percentile
Column level sensor that finds the median in a given column.

**Sensor summary**

The percentile sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/percentile`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`percentile_value`</span>|Median (50th percentile), must equal 0.5|*double*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

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
=== "Databricks"

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
=== "DuckDB"

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
=== "Oracle"

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
=== "PostgreSQL"

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
=== "Presto"

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
=== "Redshift"

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
=== "Snowflake"

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
=== "Spark"

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
=== "SQL Server"

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
=== "Trino"

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
___



## population stddev
Column level sensor that calculates population standard deviation in a given column.

**Sensor summary**

The population stddev sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/population_stddev`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
    SELECT
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDDEV({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDEVP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDDEV_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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



## population variance
Column level sensor that calculates population variance in a given column.

**Sensor summary**

The population variance sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/population_variance`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        VAR_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VAR_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VAR_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VAR_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VAR_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
    SELECT
        VAR_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VAR_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VAR_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VAR_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VAR_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VARP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VAR_POP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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



## sample stddev
Column level sensor that calculates sample standard deviation in a given column.

**Sensor summary**

The sample stddev sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/sample_stddev`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        STDDEV_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDDEV_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDDEV_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDDEV_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDDEV_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
    SELECT
        STDDEV_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDDEV_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDDEV_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDDEV_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDDEV_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDEV({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        STDDEV_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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



## sample variance
Column level sensor that calculates sample variance in a given column.

**Sensor summary**

The sample variance sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/sample_variance`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        VAR_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VAR_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VAR_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VAR_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VAR_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
    SELECT
        VAR_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VAR_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VAR_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VAR_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VAR_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VARP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        VAR_SAMP({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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



## sum
Column level sensor that counts the sum of values in a column.

**Sensor summary**

The sum sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/sum`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
    SELECT
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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



## valid latitude percent
Column level sensor that counts percentage of valid latitude in a column.

**Sensor summary**

The valid latitude percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/valid_latitude_percent`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |







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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END as actual_value
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END as actual_value
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_valuee
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -90.0 AND {{ lib.render_target_column('analyzed_table') }} <= 90.0 THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END as actual_value
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



## valid longitude percent
Column level sensor that counts percentage of valid longitude in a column.

**Sensor summary**

The valid longitude percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | numeric | <span class="no-wrap-code">`column/numeric/valid_longitude_percent`</span> | [*sensors/column/numeric*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/numeric/) |







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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END as actual_value
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
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END as actual_value
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} >= -180.0 AND {{ lib.render_target_column('analyzed_table') }} <= 180.0 THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END as actual_value
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
