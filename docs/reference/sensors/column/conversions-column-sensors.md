---
title: DQOps data quality conversions sensors
---
# DQOps data quality conversions sensors
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **conversions** category supported by DQOps are listed below. Those sensors are measured on a column level.

---


## text parsable to boolean percent
Column level sensor that calculates the number of rows with a text value in a text column that is parsable to a boolean type or is a well-known boolean value placeholder: yes, no, true, false, t, f, y, n, 1, 0.

**Sensor summary**

The text parsable to boolean percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | conversions | <span class="no-wrap-code">`column/conversions/text_parsable_to_boolean_percent`</span> | [*sensors/column/conversions*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/conversions/) |







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
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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
                    WHEN TRIM(LOWER({{ lib.render_target_column('analyzed_table')}})) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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
                    WHEN LOWER({{ lib.render_target_column('analyzed_table')}}) IN ('true', 'false', 't', 'f', 'y', 'n', 'yes', 'no', '1', '0')
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



## text parsable to date percent
Column level sensor that ensures that there is at least a minimum percentage of rows with a text value that is parsable to a date in an analyzed column.

**Sensor summary**

The text parsable to date percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | conversions | <span class="no-wrap-code">`column/conversions/text_parsable_to_date_percent`</span> | [*sensors/column/conversions*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/conversions/) |







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
                    WHEN SAFE_CAST({{lib.render_column_cast_to_string('analyzed_table')}} AS DATE) IS NOT NULL
                        OR SAFE_CAST({{lib.render_column_cast_to_string('analyzed_table')}} AS DATE FORMAT 'DD-MM-YYYY') IS NOT NULL
                        OR SAFE_CAST({{lib.render_column_cast_to_string('analyzed_table')}} AS DATE FORMAT 'MM-DD-YYYY') IS NOT NULL
                        OR SAFE_CAST({{lib.render_column_cast_to_string('analyzed_table')}} AS DATE FORMAT 'DD/MM/YYYY') IS NOT NULL
                        OR SAFE_CAST({{lib.render_column_cast_to_string('analyzed_table')}} AS DATE FORMAT 'MM/DD/YYYY') IS NOT NULL
                        OR SAFE_CAST({{lib.render_column_cast_to_string('analyzed_table')}} AS DATE FORMAT 'YYYY/MM/DD') IS NOT NULL
                        OR SAFE_CAST({{lib.render_column_cast_to_string('analyzed_table')}} AS DATE FORMAT 'YYYY.MM.DD') IS NOT NULL
                        OR SAFE.PARSE_DATE('%b %e, %Y', {{lib.render_column_cast_to_string('analyzed_table')}}) IS NOT NULL
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
                    WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS DATE) IS NOT NULL
                        OR TO_DATE({{lib.render_target_column('analyzed_table')}}, 'dd-MM-yyyy') IS NOT NULL
                        OR TO_DATE({{lib.render_target_column('analyzed_table')}}, 'MM-dd-yyyy') IS NOT NULL
                        OR TO_DATE({{lib.render_target_column('analyzed_table')}}, 'dd/MM/yyyy') IS NOT NULL
                        OR TO_DATE({{lib.render_target_column('analyzed_table')}}, 'MM/dd/yyyy') IS NOT NULL
                        OR TO_DATE({{lib.render_target_column('analyzed_table')}}, 'yyyy/MM/dd') IS NOT NULL
                        OR TO_DATE({{lib.render_target_column('analyzed_table')}}, 'yyyy.MM.dd') IS NOT NULL
                        OR TO_DATE({{lib.render_target_column('analyzed_table')}}, 'MMM d, yyyy') IS NOT NULL
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
                    WHEN TRY_CAST({{lib.render_target_column('analyzed_table')}}::VARCHAR AS DATETIME) IS NOT NULL
                        OR TRY_STRPTIME({{lib.render_target_column('analyzed_table')}}::VARCHAR, '%d/%m/%Y') IS NOT NULL
                        OR TRY_STRPTIME({{lib.render_target_column('analyzed_table')}}::VARCHAR, '%d-%m-%Y') IS NOT NULL
                        OR TRY_STRPTIME({{lib.render_target_column('analyzed_table')}}::VARCHAR, '%m-%d-%Y') IS NOT NULL
                        OR TRY_STRPTIME({{lib.render_target_column('analyzed_table')}}::VARCHAR, '%d/%m/%Y') IS NOT NULL
                        OR TRY_STRPTIME({{lib.render_target_column('analyzed_table')}}::VARCHAR, '%m/%d/%Y') IS NOT NULL
                        OR TRY_STRPTIME({{lib.render_target_column('analyzed_table')}}::VARCHAR, '%Y/%m/%d') IS NOT NULL
                        OR TRY_STRPTIME({{lib.render_target_column('analyzed_table')}}::VARCHAR, '%b %-d, %Y') IS NOT NULL
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
                CASE WHEN REGEXP_LIKE({{lib.render_target_column('analyzed_table')}}, '^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$')
                            OR REGEXP_LIKE({{lib.render_target_column('analyzed_table')}}, '^(?:29(\\/|-|\\.)(?:0?2|(?:Feb))\\1(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$')
                            OR REGEXP_LIKE({{lib.render_target_column('analyzed_table')}}, '^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\\1(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$')
                            OR
                            REGEXP_LIKE({{lib.render_target_column('analyzed_table')}}, '^(?:(?:(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec))(\\/|-|\\.|[ ])31)(?:[,]?\\1)|(?:(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)(\\/|-|\\.|[ ])(?:29|30))(?:[,]?\\2)))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$')
                            OR REGEXP_LIKE({{lib.render_target_column('analyzed_table')}}, '^(?:(?:0?2|(?:Feb)(\\/|-|\\.|[ ])29)(?:[,]?\\1)(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$')
                            OR REGEXP_LIKE({{lib.render_target_column('analyzed_table')}}, '^(?:(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))(\\/|-|\\.|[ ])(?:0?[1-9]|1\\d|2[0-8]))(?:[,]?\\1)(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$')
                            OR
                            REGEXP_LIKE({{lib.render_target_column('analyzed_table')}}, '^(?:(?:1[6-9]|[2-9]\\d)?\\d{2})(\\/|-|\\.)(?:(?:(?:(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec))\\1(?:31))|(?:(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\1(?:29|30))))|(?:(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\\1(?:0?[1-9]|1\\d|2[0-8])))$')
                            OR REGEXP_LIKE({{lib.render_target_column('analyzed_table')}}, '^(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(\\/|-|\\.)(?:(?:0?2|(?:Feb)\\1(?:29)))$')
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
                    WHEN REGEXP_LIKE({{lib.render_target_column('analyzed_table')}}, '^((31(\/|-|\.)(0?[13578]|1[02]|(Jan|Mar|May|Jul|Aug|Oct|Dec)))(\/|-|\.)|((29|30)(\/|-|\.)(0?[1,3-9]|1[0-2]|(Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))(\/|-|\.)))((1[6-9]|[2-9]\d)?\d{2})$')
                        OR REGEXP_LIKE({{lib.render_target_column('analyzed_table')}}, '^(29(\/|-|\.)(0?2|(Feb))(\/|-|\.)(((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$')
                        OR REGEXP_LIKE({{lib.render_target_column('analyzed_table')}}, '^(0?[1-9]|1\d|2[0-8])(\/|-|\.)((0?[1-9]|(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(1[0-2]|(Oct|Nov|Dec)))(\/|-|\.)((1[6-9]|[2-9]\d)?\d{2})$')
                        OR
                        REGEXP_LIKE({{lib.render_target_column('analyzed_table')}}, '^(((0?[13578]|1[02]|(Jan|Mar|May|Jul|Aug|Oct|Dec))(\/|-|\.|[ ])31)(([,]?[ ]?)|(\/|-|\.))|((0?[1,3-9]|1[0-2]|(Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)(\/|-|\.|[ ])(29|30))(([,]?[ ]?)|(\/|-|\.))))((1[6-9]|[2-9]\d)?\d{2})$')
                        OR REGEXP_LIKE({{lib.render_target_column('analyzed_table')}}, '^((0?2|(Feb)(\/|-|\.|[ ])29)(([,]?[ ]?)|(\/|-|\.))(((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$')
                        OR REGEXP_LIKE({{lib.render_target_column('analyzed_table')}}, '^(((0?[1-9]|(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(1[0-2]|(Oct|Nov|Dec)))(\/|-|\.|[ ])(0?[1-9]|1\d|2[0-8]))(([,]?[ ]?)|(\/|-|\.))((1[6-9]|[2-9]\d)?\d{2})$')
                        OR
                        REGEXP_LIKE({{lib.render_target_column('analyzed_table')}}, '^((1[6-9]|[2-9]\d)?\d{2})(\/|-|\.)(((0?[13578]|1[02]|(Jan|Mar|May|Jul|Aug|Oct|Dec))(\/|-|\.)(31))|((0?[1,3-9]|1[0-2]|(Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)(\/|-|\.)(29|30))))$')
                        OR REGEXP_LIKE({{lib.render_target_column('analyzed_table')}}, '^((1[6-9]|[2-9]\d)?\d{2})(\/|-|\.)(((0?[1-9]|(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(1[0-2]|(Oct|Nov|Dec)))(\/|-|\.)(0?[1-9]|1\d|2[0-8]))$')
                        OR REGEXP_LIKE({{lib.render_target_column('analyzed_table')}}, '^(((1[6-9]|[2-9]\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)))(\/|-|\.)((0?2|(Feb)(\/|-|\.)(29)))$')
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
                    WHEN {{ lib.render_target_column('analyzed_table')}}::varchar ~ '^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$'
                        OR {{ lib.render_target_column('analyzed_table')}}::varchar ~ '^(?:29(\/|-|\.)(?:0?2|(?:Feb))\1(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$'
                        OR {{ lib.render_target_column('analyzed_table')}}::varchar ~ '^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\1(?:(?:1[6-9]|[2-9]\d)?\d{2})$'
                        OR
                        {{ lib.render_target_column('analyzed_table')}}::varchar ~ '^(?:(?:(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec))(\/|-|\.|[ ])31)(?:[,]?\1)|(?:(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)(\/|-|\.|[ ])(?:29|30))(?:[,]?\2)))(?:(?:1[6-9]|[2-9]\d)?\d{2})$'
                        OR {{ lib.render_target_column('analyzed_table')}}::varchar ~ '^(?:(?:0?2|(?:Feb)(\/|-|\.|[ ])29)(?:[,]?\1)(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$'
                        OR {{ lib.render_target_column('analyzed_table')}}::varchar ~ '^(?:(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))(\/|-|\.|[ ])(?:0?[1-9]|1\d|2[0-8]))(?:[,]?\1)(?:(?:1[6-9]|[2-9]\d)?\d{2})$'
                        OR
                        {{ lib.render_target_column('analyzed_table')}}::varchar ~    '^(?:(?:1[6-9]|[2-9]\d)?\d{2})(\/|-|\.)(?:(?:(?:(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec))\1(?:31))|(?:(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\1(?:29|30))))|(?:(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\1(?:0?[1-9]|1\d|2[0-8])))$'
                        OR {{ lib.render_target_column('analyzed_table')}}::varchar ~ '^(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(\/|-|\.)(?:(?:0?2|(?:Feb)\1(?:29)))$'
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
                    WHEN TRY_CAST({{lib.render_column_cast_to_string('analyzed_table')}} AS DATE) IS NOT NULL
                        OR TRY(DATE_PARSE({{lib.render_column_cast_to_string('analyzed_table')}}, '%d/%m/%Y')) IS NOT NULL
                        OR TRY(DATE_PARSE({{lib.render_column_cast_to_string('analyzed_table')}}, '%d-%m-%Y')) IS NOT NULL
                        OR TRY(DATE_PARSE({{lib.render_column_cast_to_string('analyzed_table')}}, '%m-%d-%Y')) IS NOT NULL
                        OR TRY(DATE_PARSE({{lib.render_column_cast_to_string('analyzed_table')}}, '%d/%m/%Y')) IS NOT NULL
                        OR TRY(DATE_PARSE({{lib.render_column_cast_to_string('analyzed_table')}}, '%m/%d/%Y')) IS NOT NULL
                        OR TRY(DATE_PARSE({{lib.render_column_cast_to_string('analyzed_table')}}, '%Y/%m/%d')) IS NOT NULL
                        OR TRY(DATE_PARSE({{lib.render_column_cast_to_string('analyzed_table')}}, '%b %e, %Y')) IS NOT NULL
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
                    WHEN {{ lib.render_target_column('analyzed_table')}}::varchar ~ '^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$'
                        OR {{ lib.render_target_column('analyzed_table')}}::varchar ~ '^(?:29(\/|-|\.)(?:0?2|(?:Feb))\1(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$'
                        OR {{ lib.render_target_column('analyzed_table')}}::varchar ~ '^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\1(?:(?:1[6-9]|[2-9]\d)?\d{2})$'
                        OR
                        {{ lib.render_target_column('analyzed_table')}}::varchar ~ '^(?:(?:(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec))(\/|-|\.|[ ])31)(?:[,]?\1)|(?:(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)(\/|-|\.|[ ])(?:29|30))(?:[,]?\2)))(?:(?:1[6-9]|[2-9]\d)?\d{2})$'
                        OR {{ lib.render_target_column('analyzed_table')}}::varchar ~ '^(?:(?:0?2|(?:Feb)(\/|-|\.|[ ])29)(?:[,]?\1)(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$'
                        OR {{ lib.render_target_column('analyzed_table')}}::varchar ~ '^(?:(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))(\/|-|\.|[ ])(?:0?[1-9]|1\d|2[0-8]))(?:[,]?\1)(?:(?:1[6-9]|[2-9]\d)?\d{2})$'
                        OR
                        {{ lib.render_target_column('analyzed_table')}}::varchar ~    '^(?:(?:1[6-9]|[2-9]\d)?\d{2})(\/|-|\.)(?:(?:(?:(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec))\1(?:31))|(?:(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\1(?:29|30))))|(?:(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\1(?:0?[1-9]|1\d|2[0-8])))$'
                        OR {{ lib.render_target_column('analyzed_table')}}::varchar ~ '^(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(\/|-|\.)(?:(?:0?2|(?:Feb)\1(?:29)))$'
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
                    WHEN
                        {{ lib.render_target_column('analyzed_table')}} NOT REGEXP '^\\d+$' AND TRY_TO_DATE({{ lib.render_target_column('analyzed_table')}}) IS NOT NULL
                         OR
                         {{ lib.render_target_column('analyzed_table')}} REGEXP '^((31(\\/|-|\\.)(0?[13578]|1[02]|(Jan|Mar|May|Jul|Aug|Oct|Dec)))(\\/|-|\\.)|((29|30)(\\/|-|\\.)(0?[1,3-9]|1[0-2]|(Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))(\\/|-|\\.)))((1[6-9]|[2-9]\\d)?\\d{2})$'
                         OR {{ lib.render_target_column('analyzed_table')}} REGEXP '^(29(\\/|-|\\.)(0?2|(Feb))(\\/|-|\\.)(((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$'
                         OR {{ lib.render_target_column('analyzed_table')}} REGEXP '^(0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)((0?[1-9]|(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(1[0-2]|(Oct|Nov|Dec)))(\\/|-|\\.)((1[6-9]|[2-9]\\d)?\\d{2})$'
                         OR
                         {{ lib.render_target_column('analyzed_table')}} REGEXP '^(((0?[13578]|1[02]|(Jan|Mar|May|Jul|Aug|Oct|Dec))(\\/|-|\\.|[ ])31)([,]?(\\/|-|\\.|[ ]))|((0?[1,3-9]|1[0-2]|(Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)(\\/|-|\\.|[ ])(29|30))([,]?(\\/|-|\\.|[ ]))))((1[6-9]|[2-9]\\d)?\\d{2})$'
                         OR {{ lib.render_target_column('analyzed_table')}} REGEXP '^((0?2|(Feb)(\\/|-|\\.|[ ])29)([,]?(\\/|-|\\.|[ ]))(((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$'
                         OR {{ lib.render_target_column('analyzed_table')}} REGEXP '^(((0?[1-9]|(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(1[0-2]|(Oct|Nov|Dec)))(\\/|-|\\.|[ ])(0?[1-9]|1\\d|2[0-8]))([,]?(\\/|-|\\.|[ ]))((1[6-9]|[2-9]\\d)?\\d{2})$'
                         OR
                         {{ lib.render_target_column('analyzed_table')}} REGEXP '^((1[6-9]|[2-9]\\d)?\\d{2})(\\/|-|\\.)((((0?[13578]|1[02]|(Jan|Mar|May|Jul|Aug|Oct|Dec))(\\/|-|\\.)(31))|((0?[1,3-9]|1[0-2]|(Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)(\\/|-|\\.)(29|30))))|(((0?[1-9]|(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(1[0-2]|(Oct|Nov|Dec)))(\\/|-|\\.)(0?[1-9]|1\\d|2[0-8])))$'
                         OR {{ lib.render_target_column('analyzed_table')}} REGEXP '^(((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)))(\\/|-|\\.)((0?2|(Feb)(\\/|-|\\.)(29)))$'
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
                    WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS DATE) IS NOT NULL
                        OR TO_DATE({{lib.render_target_column('analyzed_table')}}, 'dd-MM-yyyy') IS NOT NULL
                        OR TO_DATE({{lib.render_target_column('analyzed_table')}}, 'MM-dd-yyyy') IS NOT NULL
                        OR TO_DATE({{lib.render_target_column('analyzed_table')}}, 'dd/MM/yyyy') IS NOT NULL
                        OR TO_DATE({{lib.render_target_column('analyzed_table')}}, 'MM/dd/yyyy') IS NOT NULL
                        OR TO_DATE({{lib.render_target_column('analyzed_table')}}, 'yyyy/MM/dd') IS NOT NULL
                        OR TO_DATE({{lib.render_target_column('analyzed_table')}}, 'yyyy.MM.dd') IS NOT NULL
                        OR TO_DATE({{lib.render_target_column('analyzed_table')}}, 'MMM d, yyyy') IS NOT NULL
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
                    WHEN TRY_CAST({{ lib.render_target_column('analyzed_table')}} AS DATE) IS NOT NULL
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
                    WHEN TRY_CAST({{lib.render_column_cast_to_string('analyzed_table')}} AS DATE) IS NOT NULL
                        OR TRY(DATE_PARSE({{lib.render_column_cast_to_string('analyzed_table')}}, '%d/%m/%Y')) IS NOT NULL
                        OR TRY(DATE_PARSE({{lib.render_column_cast_to_string('analyzed_table')}}, '%d-%m-%Y')) IS NOT NULL
                        OR TRY(DATE_PARSE({{lib.render_column_cast_to_string('analyzed_table')}}, '%m-%d-%Y')) IS NOT NULL
                        OR TRY(DATE_PARSE({{lib.render_column_cast_to_string('analyzed_table')}}, '%d/%m/%Y')) IS NOT NULL
                        OR TRY(DATE_PARSE({{lib.render_column_cast_to_string('analyzed_table')}}, '%m/%d/%Y')) IS NOT NULL
                        OR TRY(DATE_PARSE({{lib.render_column_cast_to_string('analyzed_table')}}, '%Y/%m/%d')) IS NOT NULL
                        OR TRY(DATE_PARSE({{lib.render_column_cast_to_string('analyzed_table')}}, '%b %e, %Y')) IS NOT NULL
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



## text parsable to float percent
Column level sensor that calculates the percentage of rows with text values in an analyzed column that are parsable to a float (numeric) value.

**Sensor summary**

The text parsable to float percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | conversions | <span class="no-wrap-code">`column/conversions/text_parsable_to_float_percent`</span> | [*sensors/column/conversions*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/conversions/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS FLOAT64)
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
            ELSE 100.0 * COUNT(
                CAST({{ lib.render_target_column('analyzed_table') }} AS FLOAT)
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{ lib.render_data_grouping_projections('analyzed_table') }}
        {{ lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{ lib.render_where_clause() }}
    {{ lib.render_group_by() }}
    {{ lib.render_order_by() }}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE WHEN REGEXP_MATCHES({{lib.render_target_column('analyzed_table') }}, '^([0-9]+\.?[0-9]*|\.[0-9]+)$') IS TRUE THEN 1 ELSE 0 END
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
                {{ lib.render_regex(lib.render_target_column('analyzed_table'), '^([0-9]+\.?[0-9]*|\.[0-9]+)$') }}
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
                CASE WHEN REGEXP_LIKE({{lib.render_target_column('analyzed_table') }}, '^([0-9]+\.?[0-9]*|\.[0-9]+)$') THEN 1 ELSE 0 END
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
                CASE WHEN {{ lib.render_target_column('analyzed_table') }} ~ '^([0-9]+\.?[0-9]*|\.[0-9]+)$' THEN 1 ELSE 0 END
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
            ELSE CAST(100.0 * COUNT(
                TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS DOUBLE)
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
                CASE WHEN {{ lib.render_target_column('analyzed_table') }} ~ '^([0-9]+\.?[0-9]*|\.[0-9]+)$' THEN 1 ELSE 0 END
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
            ELSE 100.0 * COUNT(
                TRY_TO_NUMERIC({{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * COUNT(
                CAST({{ lib.render_target_column('analyzed_table') }} AS FLOAT)
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{ lib.render_data_grouping_projections('analyzed_table') }}
        {{ lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{ lib.render_where_clause() }}
    {{ lib.render_group_by() }}
    {{ lib.render_order_by() }}
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                TRY_CONVERT(FLOAT, {{ lib.render_target_column('analyzed_table') }})
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
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * COUNT(
                TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS DOUBLE)
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



## text parsable to integer percent
Column level sensor that calculates the percentage of rows with text values in an analyzed column that are parsable to an integer value.

**Sensor summary**

The text parsable to integer percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | conversions | <span class="no-wrap-code">`column/conversions/text_parsable_to_integer_percent`</span> | [*sensors/column/conversions*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/conversions/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * COUNT(
                SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS INT64)
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
            ELSE 100.0 * COUNT(
                CAST({{ lib.render_target_column('analyzed_table') }} AS INTEGER)
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
                CASE WHEN REGEXP_MATCHES({{lib.render_target_column('analyzed_table') }}, '^[0-9]*$') IS TRUE THEN 1 ELSE 0 END
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
                {{ lib.render_regex(lib.render_target_column('analyzed_table'), '^[0-9]+$') }}
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
                CASE WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '^[0-9]*$') THEN 1 ELSE 0 END
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
                CASE WHEN {{ lib.render_target_column('analyzed_table') }} ~ '^[0-9]+$' THEN 1 ELSE 0 END
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
            ELSE CAST(100.0 * COUNT(
                TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS BIGINT)
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
                CASE WHEN {{ lib.render_target_column('analyzed_table') }} ~ '^[0-9]+$' THEN 1 ELSE 0 END
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
            ELSE 100.0 * COUNT(
                TRY_TO_NUMBER({{ lib.render_target_column('analyzed_table') }})
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
            ELSE 100.0 * COUNT(
                CAST({{ lib.render_target_column('analyzed_table') }} AS INTEGER)
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
            ELSE 100.0 * COUNT(
                TRY_CONVERT(INT, {{ lib.render_target_column('analyzed_table') }})
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
    {# We should think about unifying the COUNT() IN different sensors. I changed it TO * here. -#}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE CAST(100.0 * COUNT(
                TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS BIGINT)
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
