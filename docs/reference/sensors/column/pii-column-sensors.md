---
title: DQOps data quality pii sensors
---
# DQOps data quality pii sensors
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **pii** category supported by DQOps are listed below. Those sensors are measured on a column level.

---


## contains email percent
Column level sensor that calculates the percentage of rows with a valid email value in a column.

**Sensor summary**

The contains email percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | pii | <span class="no-wrap-code">`column/pii/contains_email_percent`</span> | [*sensors/column/pii*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/pii/) |







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
                    WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        r"(?:^|[ \t.,:;\"''`|\n\r])[a-zA-Z0-9.!#$%&''*+\/=?^_`{|}~-]{0,63}[a-zA-Z0-9!#$%&''*+\/=?^_`{|}~-]@[a-zA-Z0-9-.]+[.][a-zA-Z]{2,4}(?:[ \t.,:;\"''`|\n\r]|$)")
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
                    WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        "(?:^|[ \t.,:;\"''`|\n\r])[a-zA-Z0-9.!#$%&''*+\/=?^_`{|}~-]{0,63}[a-zA-Z0-9!#$%&''*+\/=?^_`{|}~-]@[a-zA-Z0-9-.]+[.][a-zA-Z]{2,4}(?:[ \t.,:;\"''`|\n\r]|$)")
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
                    WHEN REGEXP_MATCHES({{lib.render_target_column('analyzed_table')}},
                         '(?:^|[ \t.,:;"''`|\n\r])[a-zA-Z0-9.!#$%&''*+\/=?^_`{|}~-]{0,63}[a-zA-Z0-9!#$%&''*+\/=?^_`{|}~-]@[a-zA-Z0-9-.]+[.][a-zA-Z]{2,4}(?:[ \t.,:;"''`|\n\r]|$)') IS TRUE
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
                    WHEN {{ lib.render_regex(lib.render_target_column('analyzed_table'),
                         '(?:^|[ \t.,:;"''`|\n\r])[a-zA-Z0-9.!#$%&''*+\/=?^_`{|}~-]{0,63}[a-zA-Z0-9!#$%&''*+\/=?^_`{|}~-]@[a-zA-Z0-9-.]+[.][a-zA-Z]{2,4}(?:[ \t.,:;"''`|\n\r]|$)' ) }}
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
                    WHEN REGEXP_LIKE({{lib.render_target_column('analyzed_table')}} ,
                         '(^|[ \t.,:;"''`|\n\r])[a-zA-Z0-9.!#$%&''*+\/=?^_`{|}~-]{0,63}[a-zA-Z0-9!#$%&''*+\/=?^_`{|}~-]@[-a-zA-Z0-9.]+[.][a-zA-Z]{2,4}([ \t.,:;"''`|\n\r]|$)')
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
                    WHEN SUBSTRING({{lib.render_target_column('analyzed_table')}} from
                         '(^|[ \t.,:;"''`|\n\r])[a-zA-Z0-9.!#$%&''*+\/=?^_`{|}~-]{0,63}[a-zA-Z0-9!#$%&''*+\/=?^_`{|}~-]@[-a-zA-Z0-9.]+[.][a-zA-Z]{2,4}([ \t.,:;"''`|\n\r]|$)') IS NOT NULL
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
                    WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                         '(^|[ \t.,:;"''`|\n\r])[a-zA-Z0-9.!#$%&''*+\/=?^_`{|}~-]{0,63}[a-zA-Z0-9!#$%&''*+\/=?^_`{|}~-]@[-a-zA-Z0-9.]+[.][a-zA-Z]{2,4}([ \t.,:;"''`|\n\r]|$)')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        AS actual_value
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
                    WHEN {{lib.render_target_column('analyzed_table')}} ~ '(^|[ \t.,:;"''`|\n\r])[a-zA-Z0-9.!#$%&''*+\/=?^_`{|}~-]{0,63}[a-zA-Z0-9!#$%&''*+\/=?^_`{|}~-]@[-a-zA-Z0-9.]+[.][a-zA-Z]{2,4}([ \t.,:;"''`|\n\r]|$)'
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
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                    '(^|[ \t.,:;"''`|\n\r])[a-zA-Z0-9.!#$%&''*+\/=?^_`{|}~-]{0,63}[a-zA-Z0-9!#$%&''*+\/=?^_`{|}~-]@[-a-zA-Z0-9.]+[.][a-zA-Z]{2,4}([ \t.,:;"''`|\n\r]|$)')
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
                    WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                         "(?:^|[ \t.,:;\"''`|\n\r])[a-zA-Z0-9.!#$%&''*+\/=?^_`{|}~-]{0,63}[a-zA-Z0-9!#$%&''*+\/=?^_`{|}~-]@[a-zA-Z0-9-.]+[.][a-zA-Z]{2,4}(?:[ \t.,:;\"''`|\n\r]|$)")
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
                        {{ lib.render_target_column('analyzed_table') }} LIKE '%[-a-zA-Z0-9!#$%&''*+\/=?^_`{|}~]@%.__%'
                            AND len({{ lib.render_target_column('analyzed_table') }}) - len(replace({{ lib.render_target_column('analyzed_table') }}, '@', '')) = 1	-- single use of @ char
                            AND right({{ lib.render_target_column('analyzed_table') }}, len({{ lib.render_target_column('analyzed_table') }}) - charindex('@', {{ lib.render_target_column('analyzed_table') }})) NOT LIKE '%[^-a-zA-Z0-9.]%' -- domain check
                            AND len(left({{ lib.render_target_column('analyzed_table') }}, charindex('@', {{ lib.render_target_column('analyzed_table') }}))) < 64 -- local part length
                            AND {{ lib.render_target_column('analyzed_table') }} not like '%@.%'
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
                    WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                         '(^|[ \t.,:;"''`|\n\r])[a-zA-Z0-9.!#$%&''*+\/=?^_`{|}~-]{0,63}[a-zA-Z0-9!#$%&''*+\/=?^_`{|}~-]@[-a-zA-Z0-9.]+[.][a-zA-Z]{2,4}([ \t.,:;"''`|\n\r]|$)')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        AS actual_value
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



## contains ip4 percent
Column level sensor that calculates the percentage of rows with a valid IP4 value in a column.

**Sensor summary**

The contains ip4 percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | pii | <span class="no-wrap-code">`column/pii/contains_ip4_percent`</span> | [*sensors/column/pii*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/pii/) |







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
                    WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        r"(^|[ \t.,:;\"'`|\n\r])((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])([ \t.,:;\"'`|\n\r]|$)")
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
                    WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        "(^|[ \t.,:;""'`|\n\r])((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])([ \t.,:;""'`|\n\r]|$)")
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
                    WHEN REGEXP_MATCHES({{lib.render_target_column('analyzed_table')}},
                        '(^|[ \t.,:;"''`|\n\r])((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])([ \t.,:;"''`|\n\r]|$)') IS TRUE
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
                    WHEN {{ lib.render_regex(lib.render_target_column('analyzed_table'),
                        '(^|[ \t.,:;"''`|\n\r])((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])([ \t.,:;"''`|\n\r]|$)') }}
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
                    WHEN REGEXP_LIKE({{lib.render_target_column('analyzed_table')}} ,
                        '(^|[ \t.,:;"''`|\n\r])((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])([ \t.,:;"''`|\n\r]|$)')
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
                    WHEN SUBSTRING({{lib.render_target_column('analyzed_table')}} from
                        '(^|[ \t.,:;"''`|\n\r])((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])([ \t.,:;"''`|\n\r]|$)') IS NOT NULL
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
        CAST(
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                            '(^|[ \t.,:;"''`|\n\r])((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])([ \t.,:;"''`|\n\r]|$)')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
            END
        AS DOUBLE) AS actual_value
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
                    WHEN {{lib.render_target_column('analyzed_table')}} ~ '(^|[ \t.,:;"''`|\n\r])((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])([ \t.,:;"''`|\n\r]|$)'
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
                    WHEN ({{ lib.render_target_column('analyzed_table') }} REGEXP '(^|[ \t.,:;"''`|\n\r])((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])([ \t.,:;"''`|\n\r]|$)')
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
                    WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        "(^|[ \t.,:;\"'`|\n\r])((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])([ \t.,:;\"'`|\n\r]|$)")
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '%[ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + '][0-9]%.%[0-9]%.%[0-9]%.%[0-9][ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + ']%' OR
                      {{ lib.render_target_column('analyzed_table') }} LIKE '%[ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + '][0-9]%.%[0-9]%.%[0-9]%.%[0-9]' OR
                      {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9]%.%[0-9]%.%[0-9]%.%[0-9][ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + ']%' OR
                      {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9]%.%[0-9]%.%[0-9]%.%[0-9]'
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
        CAST(
            CASE
                WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                            '(^|[ \t.,:;"''`|\n\r])((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])([ \t.,:;"''`|\n\r]|$)')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT({{ lib.render_target_column('analyzed_table') }})
            END
        AS DOUBLE) AS actual_value
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



## contains ip6 percent
Column level sensor that calculates the percentage of rows with a valid IP6 value in a column.

**Sensor summary**

The contains ip6 percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | pii | <span class="no-wrap-code">`column/pii/contains_ip6_percent`</span> | [*sensors/column/pii*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/pii/) |







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
                    WHEN
                        REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            r"(^|[ \t.,:;\"'`|\n\r])([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}([ \t.,:;\"'`|\n\r]|$)") OR
                        REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            r"(^|[ \t.,:;\"'`|\n\r])[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}([ \t.,:;\"'`|\n\r]|$)")
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
                        REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            "(^|[ \t.,:;""'`|\n\r])([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}([ \t.,:;""'`|\n\r]|$)") OR
                        REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            "(^|[ \t.,:;""'`|\n\r])[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}([ \t.,:;""'`|\n\r]|$)")
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
                    WHEN REGEXP_MATCHES({{ lib.render_target_column('analyzed_table') }},
                             '(^|[ \t.,:;"''`|\n\r])([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}([ \t.,:;"''`|\n\r]|$)') OR
                         REGEXP_MATCHES({{ lib.render_target_column('analyzed_table') }},
                             '(^|[ \t.,:;"''`|\n\r])[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}([ \t.,:;"''`|\n\r]|$)')
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
                    WHEN {{ lib.render_regex(lib.render_target_column('analyzed_table'),
                         '(^|[ \t.,:;"''`|\n\r])([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}([ \t.,:;"''`|\n\r]|$)') }} OR
                         {{ lib.render_regex(lib.render_target_column('analyzed_table'),
                         '(^|[ \t.,:;"''`|\n\r])[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}([ \t.,:;"''`|\n\r]|$)') }}
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
                        REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                    '(^|[ \t.,:;"''`|\n\r])([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}([ \t.,:;"''`|\n\r]|$)') OR
                        REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                    '(^|[ \t.,:;"''`|\n\r])[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}([ \t.,:;"''`|\n\r]|$)')
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
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                     '(^|[ \t.,:;"''`|\n\r])([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}([ \t.,:;"''`|\n\r]|$)') OR
                         REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                     '(^|[ \t.,:;"''`|\n\r])[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}([ \t.,:;"''`|\n\r]|$)')
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
                    WHEN
                        REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                            '(^|[ \t.,:;"''`|\n\r])([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}([ \t.,:;"''`|\n\r]|$)') OR
                        REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                            '(^|[ \t.,:;"''`|\n\r])[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}([ \t.,:;"''`|\n\r]|$)')
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
                    WHEN {{lib.render_target_column('analyzed_table')}} ~ '(^|[ \t.,:;"''`|\n\r])([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}([ \t.,:;"''`|\n\r]|$)'
                        OR {{lib.render_target_column('analyzed_table')}} ~ '(^|[ \t.,:;"''`|\n\r])[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}([ \t.,:;"''`|\n\r]|$)'
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
                    WHEN ({{ lib.render_target_column('analyzed_table') }} REGEXP '(^|[ \t.,:;"''`|\n\r])(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4})([ \t.,:;"''`|\n\r]|$)' )
                        OR ({{ lib.render_target_column('analyzed_table') }} REGEXP '(^|[ \t.,:;"''`|\n\r])[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}([ \t.,:;"''`|\n\r]|$)' )
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
                        REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            "(^|[ \t.,:;\"'`|\n\r])([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}([ \t.,:;\"'`|\n\r]|$)") OR
                        REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            "(^|[ \t.,:;\"'`|\n\r])[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}([ \t.,:;\"'`|\n\r]|$)")
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
    
    {% set byte = "[0-9A-Fa-f]" %}
    {% set qbyte = byte + byte + byte + byte %}
    {% set negbyte = "[^g-z]" %}
    {% set tested_column = "CAST(" ~ lib.render_target_column('analyzed_table') ~ " AS NVARCHAR(MAX))" %}
    {% set colons_count = "len(" ~ lib.render_target_column('analyzed_table') ~ ") - len(replace(" ~  lib.render_target_column('analyzed_table') ~ ", ':', ''))" %}
    
    SELECT
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ tested_column }} LIKE '%{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}:{{qbyte}}%'
                        OR
                        {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            AND {{ tested_column }} LIKE '%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%:{{negbyte}}%'
                        OR -- 6x bytes
                        ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                            OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                            OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                            OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                        ) AND {{ colons_count }} = 7
                        OR -- 5x bytes
                        ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                            OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                            OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                            OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                            OR {{ tested_column }} LIKE '%::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                        ) AND {{ colons_count }} = 6
                        OR -- 4x bytes
                        ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%'
                            OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                            OR {{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                            OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%{{negbyte}}:%'
                        ) AND {{ colons_count }} = 5
                        OR -- 3x bytes
                        ({{ tested_column }} LIKE '%{{negbyte}}:%{{negbyte}}::%{{negbyte}}:%'
                            OR {{ tested_column }} LIKE '%{{negbyte}}::%{{negbyte}}:%{{negbyte}}:%'
                           ) AND {{ colons_count }} = 4
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
                        REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                            '(^|[ \t.,:;"''`|\n\r])([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}([ \t.,:;"''`|\n\r]|$)') OR
                        REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                            '(^|[ \t.,:;"''`|\n\r])[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}([ \t.,:;"''`|\n\r]|$)')
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



## contains usa phone percent
Column level sensor that calculates the percent of values that contains a USA phone number in a column.

**Sensor summary**

The contains usa phone percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | pii | <span class="no-wrap-code">`column/pii/contains_usa_phone_percent`</span> | [*sensors/column/pii*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/pii/) |







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
                    WHEN REGEXP_CONTAINS(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        r"(^|[ \t.,:;\"'`|\n\r])((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1/)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))([ \t.,:;\"'`|\n\r]|$)"
                    ) THEN 1
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
                    WHEN REGEXP(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        "(^|[ \t.,:;""'`|\n\r])((((\\(\\+1\\)|(\\+1)|(\\([0][0][1]\\)|([0][0][1]))|\\(1\\)|(1))[\\s.-]?)?(\\(?\\d{3}\\)?[\\s.-]?)(\\d{3}[\\s.-]?)(\\d{4})))([ \t.,:;""'`|\n\r]|$)"
                    ) THEN 1
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
                    WHEN REGEXP_MATCHES({{ lib.render_target_column('analyzed_table') }},
                         '(^|[ \t.,:;"''`|\n\r])((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1/)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))([ \t.,:;"''`|\n\r]|$)') IS TRUE
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
                    WHEN {{ lib.render_regex(lib.render_target_column('analyzed_table'), '(^|[ \t.,:;"''`|\n\r])((((\\\\(\\\\+1\\\\)|(\\\\+1)|(\\\\([0][0][1]\\\\)|([0][0][1]))|\\\\(1\\\\)|(1))[\\\\s.-]?)?(\\\\(?[0-9]{3}\\\\)?[\\\\s.-]?)([0-9]{3}[\\\\s.-]?)([0-9]{4})))([ \t.,:;"''`|\n\r]|$)') }}
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
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                            '(^|[ \t.,:;"''`|\n\r])((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1/)|(1))[[:space:].-]?)?(\(?\d{3}\)?[[:space:].-]?)(\d{3}[[:space:].-]?)(\d{4})))([ \t.,:;"''`|\n\r]|$)'
                        ) THEN 1
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
                    WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from
                         '(^|[ \t.,:;"''`|\n\r])((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1/)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))([ \t.,:;"''`|\n\r]|$)') IS NOT NULL
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
                    WHEN REGEXP_LIKE(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                        '(^|[ \t.,:;"''`|\n\r])((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1/)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))([ \t.,:;"''`|\n\r]|$)'
                    ) THEN 1
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
                    WHEN REGEXP_SUBSTR(replace(replace(replace({{ lib.render_target_column('analyzed_table') }}, '(', ''), ')', ''), '-', ''), '(^|[ \t.,:;"''`|\n\r])((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1/)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))([ \t.,:;"''`|\n\r]|$)') IS NOT NULL
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
                CASE WHEN ({{ lib.render_target_column('analyzed_table') }} REGEXP '(^|[ \\t.,:;"''`|\\n\\r])((((\\(\\+1\\)|(\\+1)|(\\([0][0][1]\\)|([0][0][1]))|\\(1\\)|(1))[\\s.-]?)?(\\(?\\d{3}\\)?[\\s.-]?)(\\d{3}[\\s.-]?)(\\d{4})))([ \\t.,:;"''`|\\n\\r]|$)')
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
                    WHEN REGEXP(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        "(^|[ \t.,:;\"'`|\n\r])((((\\(\\+1\\)|(\\+1)|(\\([0][0][1]\\)|([0][0][1]))|\\(1\\)|(1))[\\s.-]?)?(\\(?\\d{3}\\)?[\\s.-]?)(\\d{3}[\\s.-]?)(\\d{4})))([ \t.,:;\"'`|\n\r]|$)"
                    ) THEN 1
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
                    WHEN REPLACE({{ lib.render_target_column('analyzed_table') }}, ' ', '') LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' THEN 1
                    WHEN REPLACE({{ lib.render_target_column('analyzed_table') }}, ' ', '') LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' THEN 1
                    WHEN REPLACE({{ lib.render_target_column('analyzed_table') }}, ' ', '') LIKE '+1([0-9][0-9][0-9])[0-9][0-9][0-9][0-9]' THEN 1
                    WHEN REPLACE({{ lib.render_target_column('analyzed_table') }}, ' ', '') LIKE '+1([0-9][0-9][0-9])[0-9][0-9][0-9][0-9][0-9][0-9][0-9]' THEN 1
                    WHEN REPLACE({{ lib.render_target_column('analyzed_table') }}, ' ', '') LIKE '+1[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9][0-9][0-9][-.][0-9][0-9][0-9][-.][0-9][0-9][0-9][0-9]' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '+1([0-9][0-9][0-9])[0-9][0-9][0-9][0-9]' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '%1%[0-9][0-9][0-9]%[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '([0-9][0-9][0-9])[0-9][0-9][0-9][0-9][0-9][0-9][0-9]' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '(+1)%[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '(1)%[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '([0-9][0-9][0-9])-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '%[ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + ']+1([0-9][0-9][0-9])[0-9][0-9][0-9][0-9][ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + ']%' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '%[ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + '][0-9][0-9][0-9][-.][0-9][0-9][0-9][-.][0-9][0-9][0-9][0-9][ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + ']%' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '%[ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + ']+1-[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9][ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + ']%' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '%[ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + ']+%1%-%[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9][ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + ']%' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '%[ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + ']+1([0-9][0-9][0-9])[0-9][0-9][0-9][0-9][ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + ']%' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '%[ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + ']([0-9][0-9][0-9])[0-9][0-9][0-9][0-9][0-9][0-9][0-9][ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + ']%' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '%[ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + '](+1)%[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + ']%' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '%[ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + '](1.md)%[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + ']%' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '%[ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + ']([0-9][0-9][0-9])-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9][ .,:;"''`|' + CHAR(9) + CHAR(10) + CHAR(13) + ']%' THEN 1
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
                    WHEN REGEXP_LIKE(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                        '(^|[ \t.,:;"''`|\n\r])((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1/)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))([ \t.,:;"''`|\n\r]|$)'
                    ) THEN 1
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



## contains usa zipcode percent
Column level sensor that calculates the percent of values that contain a USA ZIP code number in a column.

**Sensor summary**

The contains usa zipcode percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | pii | <span class="no-wrap-code">`column/pii/contains_usa_zipcode_percent`</span> | [*sensors/column/pii*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/pii/) |







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
                    WHEN REGEXP_CONTAINS(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        r"(^|[ \t.,:;\"'`|\n\r])[0-9]{5}(?:-[0-9]{4})?([ \t.,:;\"'`|\n\r]|$)"
                    ) THEN 1
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
                    WHEN REGEXP(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        "(^|[ \t.,:;""'`|\n\r])[0-9]{5}(?:-[0-9]{4})?([ \t.,:;""'`|\n\r]|$)"
                    ) THEN 1
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
                    WHEN REGEXP_MATCHES({{ lib.render_target_column('analyzed_table') }},
                         '(^|[ \t.,:;"''`|\n\r])[0-9]{5}(?:-[0-9]{4})?([ \t.,:;"''`|\n\r]|$)') IS TRUE
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
                    WHEN {{ lib.render_regex(lib.render_target_column('analyzed_table'),
                         '(^|[ \t.,:;"''`|\n\r])[0-9]{5}(\-[0-9]{4})?([ \t.,:;"''`|\n\r]|$)') }}
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
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                         '(^|[ \t.,:;"''`|\n\r])[0-9]{5}(?:-[0-9]{4})?([ \t.,:;"''`|\n\r]|$)')
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
                    WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from
                         '(^|[ \t.,:;"''`|\n\r])[0-9]{5}(?:-[0-9]{4})?([ \t.,:;"''`|\n\r]|$)') IS NOT NULL
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
                    WHEN REGEXP_LIKE(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                        '(^|[ \t.,:;"''`|\n\r])[0-9]{5}(?:-[0-9]{4})?([ \t.,:;"''`|\n\r]|$)'
                    ) THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        AS actual_value
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} ~ '(^|[ \t.,:;"''`|\n\r])[0-9]{5}(/.D/:-[0-9]{4})?([ \t.,:;"''`|\n\r]|$)'
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '(^|[ \t.,:;"''`|\n\r])[0-9]{5}(/.D/:-[0-9]{4})?([ \t.,:;"''`|\n\r]|$)'
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
                    WHEN REGEXP(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        "(^|[ \t.,:;\"'`|\n\r])[0-9]{5}(?:-[0-9]{4})?([ \t.,:;\"'`|\n\r]|$)"
                    ) THEN 1
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '%[ \t.,:;"''`|\n\r][0-9][0-9][0-9][0-9][0-9][ \t.,:;"''`|\n\r]%' OR
                         {{ lib.render_target_column('analyzed_table') }} LIKE '%[ \t.,:;"''`|\n\r][0-9][0-9][0-9][0-9][0-9]' OR
                         {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9][0-9][0-9][0-9][0-9][ \t.,:;"''`|\n\r]%' OR
                         {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9][0-9][0-9][0-9][0-9]' OR
                         {{ lib.render_target_column('analyzed_table') }} LIKE '%[ \t.,:;"''`|\n\r][0-9][0-9][0-9][0-9][0-9]:[0-9][0-9][0-9][0-9][ \t.,:;"''`|\n\r]%' OR
                         {{ lib.render_target_column('analyzed_table') }} LIKE '%[ \t.,:;"''`|\n\r][0-9][0-9][0-9][0-9][0-9]:[0-9][0-9][0-9][0-9]' OR
                         {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9][0-9][0-9][0-9][0-9]:[0-9][0-9][0-9][0-9][ \t.,:;"''`|\n\r]%' OR
                         {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9][0-9][0-9][0-9][0-9]:[0-9][0-9][0-9][0-9]'
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
                    WHEN REGEXP_LIKE(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                        '(^|[ \t.,:;"''`|\n\r])[0-9]{5}(?:-[0-9]{4})?([ \t.,:;"''`|\n\r]|$)'
                    ) THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END
        AS actual_value
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
