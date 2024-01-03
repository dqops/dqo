
## **contains email percent**
**Full sensor name**
```
column/pii/contains_email_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a valid email value in a column.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"[A-Za-z_]+[A-Za-z0-9._]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}")
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "[A-Za-z_]+[A-Za-z0-9._]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{lib.render_target_column('analyzed_table')}} , '[A-Za-z0-9_][A-Za-z0-9_.-]*[@]{1}[A-Za-z0-9][A-Za-z0-9-]*[A-Za-z0-9]([.]{1}([A-Za-z]{2,3})){1,2}')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{lib.render_target_column('analyzed_table')}} , '[A-Za-z0-9_][A-Za-z0-9_.-]*[@]{1}[A-Za-z0-9][A-Za-z0-9-]*[A-Za-z0-9]([.]{1}([A-Za-z]{2,3})){1,2}')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING({{lib.render_target_column('analyzed_table')}} from '[a-zA-Z0-9.!#$%&''*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*') IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '[A-Za-z_]+[A-Za-z0-9._]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{lib.render_target_column('analyzed_table')}} ~ '[a-zA-Z0-9.!#$%&''*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(/.D/:\.[a-zA-Z0-9-]+)*'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},'^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "[A-Za-z_]+[A-Za-z0-9._]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT_BIG(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{lib.render_target_column('analyzed_table')}} LIKE '%[_a-zA-Z0-9.!#$%&''+/=?^`{|}~-]%@[_a-zA-Z0-9-]%.[_a-zA-Z0-9-]%'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '[A-Za-z_]+[A-Za-z0-9._]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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

## **contains ip4 percent**
**Full sensor name**
```
column/pii/contains_ip4_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a valid IP4 value in a column.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r"((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])")
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])")
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{lib.render_target_column('analyzed_table')}} , '((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{lib.render_target_column('analyzed_table')}} , '((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING({{lib.render_target_column('analyzed_table')}} from '((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])') IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{lib.render_target_column('analyzed_table')}} ~ '((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},'.*((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9]).*')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), "((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])")
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT_BIG(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '%[0-9]%.%[0-9]%.%[0-9]%.%[0-9]%'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG(*)
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
                WHEN COUNT(*) = 0 THEN 0.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), '((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
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

## **contains ip6 percent**
**Full sensor name**
```
column/pii/contains_ip6_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a valid IP6 value in a column.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN
                        REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            r"([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                        REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            r"[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN
                        REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                        REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN
                        REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                    '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                        REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                     '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN
                        REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                    '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                        REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                     '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                     '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                         REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }},
                                      '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                         THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN
                        REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                        REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                            '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{lib.render_target_column('analyzed_table')}} ~ '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}'
                        OR {{lib.render_target_column('analyzed_table')}} ~ '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}).*' )
                        OR REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '.*('[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}'}).*' )
                    THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN
                        REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}") OR
                        REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                            "[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}")
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT_BIG(*) = 0 THEN 0.0
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
            ) / COUNT_BIG(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN
                        REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                            '([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}') OR
                        REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                            '[a-f0-9A-F]{1,4}:([a-f0-9A-F]{1,4}:|:[a-f0-9A-F]{1,4}):([a-f0-9A-F]{1,4}:){0,5}([a-f0-9A-F]{1,4}){0,1}')
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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

## **contains usa phone percent**
**Full sensor name**
```
column/pii/contains_usa_phone_percent
```
**Description**  
Column level sensor that calculates the percent of values that contains a USA phone number in a column.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        r"((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1/)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))"
                    ) THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        "((((\\(\\+1\\)|(\\+1)|(\\([0][0][1]\\)|([0][0][1]))|\\(1\\)|(1))[\\s.-]?)?(\\(?\\d{3}\\)?[\\s.-]?)(\\d{3}[\\s.-]?)(\\d{4})))"
                    ) THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE(replace(replace(replace(replace({{ lib.render_target_column('analyzed_table') }}, '+', ''), '(', ''), ')', ''), '-', '') , '\\d{10}\\d?')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1/)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1/)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))') IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                        '((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1/)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))'
                    ) THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN regexp_substr(replace(replace(replace({{ lib.render_target_column('analyzed_table') }}, '(', ''), ')', ''), '-', ''), '^\\d{10}\\d?$') IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE WHEN REGEXP_SUBSTR({{ lib.render_target_column('analyzed_table') }},$$\+1\([0-9]{3}\)[0-9]{4}$$) IS NOT NULL THEN 1
                    WHEN REGEXP_SUBSTR({{ lib.render_target_column('analyzed_table') }},$$[0-9]{3}-[0-9]{3}-[0-9]{4}$$) IS NOT NULL THEN 1
                    WHEN REGEXP_SUBSTR({{ lib.render_target_column('analyzed_table') }},$$[0-9]{3}\.[0-9]{3}\.[0-9]{4}$$) IS NOT NULL THEN 1
                    WHEN REGEXP_SUBSTR({{ lib.render_target_column('analyzed_table') }},$$\+1-[0-9]{3}-[0-9]{3}-[0-9]{4}$$) IS NOT NULL THEN 1
                    WHEN REGEXP_SUBSTR({{ lib.render_target_column('analyzed_table') }},$$\d{4} \d{4} \d{4}$$) IS NOT NULL THEN 1
                    WHEN REGEXP_SUBSTR({{ lib.render_target_column('analyzed_table') }},$$\+?1?\-?\d{3}\-?\d{3}\-?\d{4}$$) IS NOT NULL THEN 1
                    WHEN REGEXP_SUBSTR({{ lib.render_target_column('analyzed_table') }},$$\+1\([0-9]{3}\)[0-9]{4}$$) IS NOT NULL THEN 1
                    WHEN REGEXP_SUBSTR({{ lib.render_target_column('analyzed_table') }},$$\([0-9]{3}\)[0-9]{7}$$) IS NOT NULL THEN 1
                    WHEN REGEXP_SUBSTR({{ lib.render_target_column('analyzed_table') }},$$\(\+1\)\d{10,11}$$) IS NOT NULL THEN 1
                    WHEN REGEXP_SUBSTR({{ lib.render_target_column('analyzed_table') }},$$\(1/)\d{10,11}$$) IS NOT NULL THEN 1
                    WHEN REGEXP_SUBSTR({{ lib.render_target_column('analyzed_table') }},$$1\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$$) IS NOT NULL THEN 1
                    WHEN REGEXP_SUBSTR({{ lib.render_target_column('analyzed_table') }},$$\+1\([0-9]{3}\)[0-9]{7}$$) IS NOT NULL THEN 1
                    WHEN REGEXP_SUBSTR({{ lib.render_target_column('analyzed_table') }},$$\d{10,11}$$) IS NOT NULL THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        "((((\\(\\+1\\)|(\\+1)|(\\([0][0][1]\\)|([0][0][1]))|\\(1\\)|(1))[\\s.-]?)?(\\(?\\d{3}\\)?[\\s.-]?)(\\d{3}[\\s.-]?)(\\d{4})))"
                    ) THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT_BIG(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '^%+1([0-9][0-9][0-9])[0-9][0-9][0-9][0-9]' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '^%[0-9][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9][0-9]' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '^%+1-[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '^%[0-9][0-9][0-9][0-9] [0-9][0-9][0-9][0-9] [0-9][0-9][0-9][0-9]' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '^%[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '^%+?1?-?[0-9][0-9][0-9]-?[0-9][0-9][0-9]-?[0-9][0-9][0-9][0-9]' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '^%+1([0-9][0-9][0-9])[0-9][0-9][0-9][0-9]' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '^%([0-9][0-9][0-9])[0-9][0-9][0-9][0-9][0-9][0-9][0-9]' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '^%(+1)%[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '^%(1)%[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' THEN 1
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '^%1([0-9][0-9][0-9])-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]' THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                        '((((\(\+1\)|(\+1)|(\([0][0][1]\)|([0][0][1]))|\(1/)|(1))[\s.-]?)?(\(?\d{3}\)?[\s.-]?)(\d{3}[\s.-]?)(\d{4})))'
                    ) THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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

## **contains usa zipcode percent**
**Full sensor name**
```
column/pii/contains_usa_zipcode_percent
```
**Description**  
Column level sensor that calculates the percent of values that contain a USA ZIP code number in a column.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        r"[0-9]{5}(?:-[0-9]{4})?"
                    ) THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        "[0-9]{5}(?:-[0-9]{4})?"
                    ) THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '[0-9]{5}(?:-[0-9]{4})?')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, '[0-9]{5}(?:-[0-9]{4})?')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING({{ lib.render_target_column('analyzed_table') }} from '[0-9]{5}(?:-[0-9]{4})?') IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                        '[0-9]{5}(?:-[0-9]{4})?'
                    ) THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} ~ '[0-9]{5}(/.D/:-[0-9]{4})?'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} REGEXP '^[0-9]{5}(/.D/:-[0-9]{4})?$'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS STRING),
                        "[0-9]{5}(?:-[0-9]{4})?"
                    ) THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
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
            WHEN COUNT_BIG(*) = 0 THEN 0.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE '[0-9][0-9][0-9][0-9][0-9]%'
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG(*)
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
            WHEN COUNT(*) = 0 THEN 0.0
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE(
                        CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR),
                        '[0-9]{5}(?:-[0-9]{4})?'
                    ) THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
