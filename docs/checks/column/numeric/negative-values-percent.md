---
title: Negative values percent data quality checks, SQL examples
---
# Negative values percent data quality checks, SQL examples

This check finds negative values in a numeric column. It measures the percentage of negative values and raises a data quality issue
 when the rate of negative values exceeds the maximum accepted percentage.


___
The **negative values percent** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile negative values percent


**Check description**

Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`profile_negative_values_percent`</span>|Maximum percentage of negative values|[numeric](../../../categories-of-data-quality-checks/how-to-detect-data-quality-issues-in-numeric-fields.md)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)| |[Validity](../../../dqo-concepts/data-quality-dimensions.md#data-validity)|[*negative_percent*](../../../reference/sensors/column/numeric-column-sensors.md#negative-percent)|[*max_percent*](../../../reference/rules/Comparison.md#max-percent)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the profile negative values percent data quality check.

??? example "Managing profile negative values percent check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=profile_negative_values_percent --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_negative_values_percent --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_negative_values_percent --enable-warning
                            -Wmax_percent=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=profile_negative_values_percent --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_negative_values_percent --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_negative_values_percent --enable-error
                            -Emax_percent=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *profile_negative_values_percent* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=profile_negative_values_percent
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_negative_values_percent
        ```

        You can also run this check on all tables (and columns)  on which the *profile_negative_values_percent* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=profile_negative_values_percent
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-15"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      profiling_checks:
        numeric:
          profile_negative_values_percent:
            warning:
              max_percent: 0.0
            error:
              max_percent: 1.0
            fatal:
              max_percent: 5.0
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [negative_percent](../../../reference/sensors/column/numeric-column-sensors.md#negative-percent)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

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
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"

            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for ClickHouse"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM(
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

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
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM  AS analyzed_table
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
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
            FROM (
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM (
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"

            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for MariaDB"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value
            FROM `<target_table>` AS analyzed_table
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

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
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value
            FROM `<target_table>` AS analyzed_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

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
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM(
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

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
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM (
                SELECT
                    original_table.*
                FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"

            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT({{ lib.render_target_column('analyzed_table') }}), 0.0)
                AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for QuestDB"

            ```sql
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column"), 0.0)
                AS actual_value
            FROM(
                SELECT
                    original_table.*
                FROM "<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

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
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

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
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

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
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

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
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(analyzed_table.[target_column])
                END AS actual_value
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"

            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for Teradata"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM (
                SELECT
                    original_table.*
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-13 27-32"
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
            numeric:
              profile_negative_values_percent:
                warning:
                  max_percent: 0.0
                error:
                  max_percent: 1.0
                fatal:
                  max_percent: 5.0
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
    [negative_percent](../../../reference/sensors/column/numeric-column-sensors.md#negative-percent)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"
            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for ClickHouse"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "DB2"

        === "Sensor template for DB2"
            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
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
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM  AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "HANA"

        === "Sensor template for HANA"
            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
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
            FROM (
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"
            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for MariaDB"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
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
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"
            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT({{ lib.render_target_column('analyzed_table') }}), 0.0)
                AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for QuestDB"
            ```sql
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column"), 0.0)
                AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(analyzed_table.[target_column])
                END AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state]
            ORDER BY level_1, level_2
                    , 
                
            
                
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"
            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for Teradata"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    
___


## daily negative values percent


**Check description**

Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_negative_values_percent`</span>|Maximum percentage of negative values|[numeric](../../../categories-of-data-quality-checks/how-to-detect-data-quality-issues-in-numeric-fields.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|daily|[Validity](../../../dqo-concepts/data-quality-dimensions.md#data-validity)|[*negative_percent*](../../../reference/sensors/column/numeric-column-sensors.md#negative-percent)|[*max_percent*](../../../reference/rules/Comparison.md#max-percent)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily negative values percent data quality check.

??? example "Managing daily negative values percent check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_negative_values_percent --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_negative_values_percent --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_negative_values_percent --enable-warning
                            -Wmax_percent=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_negative_values_percent --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_negative_values_percent --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_negative_values_percent --enable-error
                            -Emax_percent=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_negative_values_percent* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_negative_values_percent
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_negative_values_percent
        ```

        You can also run this check on all tables (and columns)  on which the *daily_negative_values_percent* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=daily_negative_values_percent
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-16"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      monitoring_checks:
        daily:
          numeric:
            daily_negative_values_percent:
              warning:
                max_percent: 0.0
              error:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [negative_percent](../../../reference/sensors/column/numeric-column-sensors.md#negative-percent)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

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
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"

            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for ClickHouse"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM(
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

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
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM  AS analyzed_table
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
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
            FROM (
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM (
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"

            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for MariaDB"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value
            FROM `<target_table>` AS analyzed_table
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

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
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value
            FROM `<target_table>` AS analyzed_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

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
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM(
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

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
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM (
                SELECT
                    original_table.*
                FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"

            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT({{ lib.render_target_column('analyzed_table') }}), 0.0)
                AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for QuestDB"

            ```sql
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column"), 0.0)
                AS actual_value
            FROM(
                SELECT
                    original_table.*
                FROM "<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

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
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

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
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

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
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

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
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(analyzed_table.[target_column])
                END AS actual_value
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"

            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for Teradata"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM (
                SELECT
                    original_table.*
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-13 28-33"
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
              numeric:
                daily_negative_values_percent:
                  warning:
                    max_percent: 0.0
                  error:
                    max_percent: 1.0
                  fatal:
                    max_percent: 5.0
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
    [negative_percent](../../../reference/sensors/column/numeric-column-sensors.md#negative-percent)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"
            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for ClickHouse"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "DB2"

        === "Sensor template for DB2"
            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
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
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM  AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "HANA"

        === "Sensor template for HANA"
            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
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
            FROM (
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"
            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for MariaDB"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
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
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"
            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT({{ lib.render_target_column('analyzed_table') }}), 0.0)
                AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for QuestDB"
            ```sql
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column"), 0.0)
                AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(analyzed_table.[target_column])
                END AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state]
            ORDER BY level_1, level_2
                    , 
                
            
                
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"
            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for Teradata"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    
___


## monthly negative values percent


**Check description**

Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`monthly_negative_values_percent`</span>|Maximum percentage of negative values|[numeric](../../../categories-of-data-quality-checks/how-to-detect-data-quality-issues-in-numeric-fields.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|monthly|[Validity](../../../dqo-concepts/data-quality-dimensions.md#data-validity)|[*negative_percent*](../../../reference/sensors/column/numeric-column-sensors.md#negative-percent)|[*max_percent*](../../../reference/rules/Comparison.md#max-percent)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the monthly negative values percent data quality check.

??? example "Managing monthly negative values percent check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_negative_values_percent --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_negative_values_percent --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_negative_values_percent --enable-warning
                            -Wmax_percent=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_negative_values_percent --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_negative_values_percent --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_negative_values_percent --enable-error
                            -Emax_percent=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_negative_values_percent* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_negative_values_percent
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_negative_values_percent
        ```

        You can also run this check on all tables (and columns)  on which the *monthly_negative_values_percent* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=monthly_negative_values_percent
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-16"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      monitoring_checks:
        monthly:
          numeric:
            monthly_negative_values_percent:
              warning:
                max_percent: 0.0
              error:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [negative_percent](../../../reference/sensors/column/numeric-column-sensors.md#negative-percent)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

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
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"

            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for ClickHouse"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM(
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

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
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM  AS analyzed_table
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
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
            FROM (
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM (
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"

            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for MariaDB"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value
            FROM `<target_table>` AS analyzed_table
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

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
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value
            FROM `<target_table>` AS analyzed_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

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
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM(
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

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
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM (
                SELECT
                    original_table.*
                FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"

            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT({{ lib.render_target_column('analyzed_table') }}), 0.0)
                AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for QuestDB"

            ```sql
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column"), 0.0)
                AS actual_value
            FROM(
                SELECT
                    original_table.*
                FROM "<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

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
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

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
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

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
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

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
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(analyzed_table.[target_column])
                END AS actual_value
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"

            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for Teradata"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value
            FROM (
                SELECT
                    original_table.*
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-13 28-33"
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
              numeric:
                monthly_negative_values_percent:
                  warning:
                    max_percent: 0.0
                  error:
                    max_percent: 1.0
                  fatal:
                    max_percent: 5.0
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
    [negative_percent](../../../reference/sensors/column/numeric-column-sensors.md#negative-percent)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"
            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for ClickHouse"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "DB2"

        === "Sensor template for DB2"
            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
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
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM  AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "HANA"

        === "Sensor template for HANA"
            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
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
            FROM (
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"
            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for MariaDB"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
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
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"
            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT({{ lib.render_target_column('analyzed_table') }}), 0.0)
                AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for QuestDB"
            ```sql
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column"), 0.0)
                AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(analyzed_table.[target_column])
                END AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state]
            ORDER BY level_1, level_2
                    , 
                
            
                
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"
            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for Teradata"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    
___


## daily partition negative values percent


**Check description**

Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_partition_negative_values_percent`</span>|Maximum percentage of negative values|[numeric](../../../categories-of-data-quality-checks/how-to-detect-data-quality-issues-in-numeric-fields.md)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|daily|[Validity](../../../dqo-concepts/data-quality-dimensions.md#data-validity)|[*negative_percent*](../../../reference/sensors/column/numeric-column-sensors.md#negative-percent)|[*max_percent*](../../../reference/rules/Comparison.md#max-percent)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily partition negative values percent data quality check.

??? example "Managing daily partition negative values percent check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_partition_negative_values_percent --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_negative_values_percent --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_negative_values_percent --enable-warning
                            -Wmax_percent=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_partition_negative_values_percent --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_negative_values_percent --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_negative_values_percent --enable-error
                            -Emax_percent=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_partition_negative_values_percent* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_partition_negative_values_percent
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_partition_negative_values_percent
        ```

        You can also run this check on all tables (and columns)  on which the *daily_partition_negative_values_percent* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=daily_partition_negative_values_percent
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="12-21"
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
          numeric:
            daily_partition_negative_values_percent:
              warning:
                max_percent: 0.0
              error:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
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
    [negative_percent](../../../reference/sensors/column/numeric-column-sensors.md#negative-percent)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

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
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"

            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for ClickHouse"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                CAST(analyzed_table."date_column" AS DATE) AS time_period,
                toDateTime64(CAST(analyzed_table."date_column" AS DATE), 3) AS time_period_utc
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                CAST(original_table."date_column" AS DATE) AS time_period,
                TIMESTAMP(CAST(original_table."date_column" AS DATE)) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

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
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM  AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
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
            FROM (
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                CAST(original_table."date_column" AS DATE) AS time_period,
                TO_TIMESTAMP(CAST(original_table."date_column" AS DATE)) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"

            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for MariaDB"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

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
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

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
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                TRUNC(CAST(original_table."date_column" AS DATE)) AS time_period,
                CAST(TRUNC(CAST(original_table."date_column" AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

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
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                CAST(original_table."date_column" AS date) AS time_period,
                CAST(CAST(original_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"

            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT({{ lib.render_target_column('analyzed_table') }}), 0.0)
                AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for QuestDB"

            ```sql
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column"), 0.0)
                AS actual_value,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                CAST(DATE_TRUNC('day', original_table."date_column") AS DATE) AS time_period,
                CAST((CAST(DATE_TRUNC('day', original_table."date_column") AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

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
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

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
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                TO_TIMESTAMP(CAST(analyzed_table."date_column" AS date)) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

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
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

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
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(analyzed_table.[target_column])
                END AS actual_value,
                CAST(analyzed_table.[date_column] AS date) AS time_period,
                CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date)
            ORDER BY CAST(analyzed_table.[date_column] AS date)
            
                
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"

            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for Teradata"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                CAST(analyzed_table."date_column" AS DATE) AS time_period,
                CAST(CAST(analyzed_table."date_column" AS DATE) AS TIMESTAMP) AS time_period_utc
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                CAST(original_table."date_column" AS date) AS time_period,
                CAST(CAST(original_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="10-4 38-43"
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
              numeric:
                daily_partition_negative_values_percent:
                  warning:
                    max_percent: 0.0
                  error:
                    max_percent: 1.0
                  fatal:
                    max_percent: 5.0
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
    [negative_percent](../../../reference/sensors/column/numeric-column-sensors.md#negative-percent)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"
            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for ClickHouse"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS DATE) AS time_period,
                toDateTime64(CAST(analyzed_table."date_column" AS DATE), 3) AS time_period_utc
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "DB2"

        === "Sensor template for DB2"
            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                CAST(original_table."date_column" AS DATE) AS time_period,
                TIMESTAMP(CAST(original_table."date_column" AS DATE)) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
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
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM  AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "HANA"

        === "Sensor template for HANA"
            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
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
            FROM (
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                CAST(original_table."date_column" AS DATE) AS time_period,
                TO_TIMESTAMP(CAST(original_table."date_column" AS DATE)) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"
            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for MariaDB"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
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
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                TRUNC(CAST(original_table."date_column" AS DATE)) AS time_period,
                CAST(TRUNC(CAST(original_table."date_column" AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                CAST(original_table."date_column" AS date) AS time_period,
                CAST(CAST(original_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"
            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT({{ lib.render_target_column('analyzed_table') }}), 0.0)
                AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for QuestDB"
            ```sql
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column"), 0.0)
                AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                CAST(DATE_TRUNC('day', original_table."date_column") AS DATE) AS time_period,
                CAST((CAST(DATE_TRUNC('day', original_table."date_column") AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                TO_TIMESTAMP(CAST(analyzed_table."date_column" AS date)) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(analyzed_table.[target_column])
                END AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                CAST(analyzed_table.[date_column] AS date) AS time_period,
                CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state], CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date)
            ORDER BY level_1, level_2CAST(analyzed_table.[date_column] AS date)
            
                
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"
            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for Teradata"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS DATE) AS time_period,
                CAST(CAST(analyzed_table."date_column" AS DATE) AS TIMESTAMP) AS time_period_utc
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                CAST(original_table."date_column" AS date) AS time_period,
                CAST(CAST(original_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    
___


## monthly partition negative values percent


**Check description**

Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`monthly_partition_negative_values_percent`</span>|Maximum percentage of negative values|[numeric](../../../categories-of-data-quality-checks/how-to-detect-data-quality-issues-in-numeric-fields.md)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|monthly|[Validity](../../../dqo-concepts/data-quality-dimensions.md#data-validity)|[*negative_percent*](../../../reference/sensors/column/numeric-column-sensors.md#negative-percent)|[*max_percent*](../../../reference/rules/Comparison.md#max-percent)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the monthly partition negative values percent data quality check.

??? example "Managing monthly partition negative values percent check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_partition_negative_values_percent --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_partition_negative_values_percent --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_partition_negative_values_percent --enable-warning
                            -Wmax_percent=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_partition_negative_values_percent --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_partition_negative_values_percent --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_partition_negative_values_percent --enable-error
                            -Emax_percent=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_partition_negative_values_percent* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_partition_negative_values_percent
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_partition_negative_values_percent
        ```

        You can also run this check on all tables (and columns)  on which the *monthly_partition_negative_values_percent* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=monthly_partition_negative_values_percent
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="12-21"
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
          numeric:
            monthly_partition_negative_values_percent:
              warning:
                max_percent: 0.0
              error:
                max_percent: 1.0
              fatal:
                max_percent: 5.0
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
    [negative_percent](../../../reference/sensors/column/numeric-column-sensors.md#negative-percent)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

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
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"

            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for ClickHouse"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                DATE_TRUNC('month', CAST(analyzed_table."date_column" AS DATE)) AS time_period,
                toDateTime64(DATE_TRUNC('month', CAST(analyzed_table."date_column" AS DATE)), 3) AS time_period_utc
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                DATE_TRUNC('MONTH', CAST(original_table."date_column" AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(original_table."date_column" AS DATE))) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

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
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM  AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
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
            FROM (
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                SERIES_ROUND(CAST(original_table."date_column" AS DATE), 'INTERVAL 1 MONTH', ROUND_DOWN) AS time_period,
                TO_TIMESTAMP(SERIES_ROUND(CAST(original_table."date_column" AS DATE), 'INTERVAL 1 MONTH', ROUND_DOWN)) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"

            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for MariaDB"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

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
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

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
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                TRUNC(CAST(original_table."date_column" AS DATE), 'MONTH') AS time_period,
                CAST(TRUNC(CAST(original_table."date_column" AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

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
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"

            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT({{ lib.render_target_column('analyzed_table') }}), 0.0)
                AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for QuestDB"

            ```sql
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column"), 0.0)
                AS actual_value,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                CAST(DATE_TRUNC('month', original_table."date_column") AS DATE) AS time_period,
                CAST((CAST(DATE_TRUNC('month', original_table."date_column") AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

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
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

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
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

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
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

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
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(analyzed_table.[target_column])
                END AS actual_value,
                DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
                CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0)
            ORDER BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)
            
                
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"

            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for Teradata"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                TRUNC(CAST(analyzed_table."date_column" AS DATE), 'MM') AS time_period,
                CAST(TRUNC(CAST(analyzed_table."date_column" AS DATE), 'MM') AS TIMESTAMP) AS time_period_utc
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="10-4 38-43"
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
              numeric:
                monthly_partition_negative_values_percent:
                  warning:
                    max_percent: 0.0
                  error:
                    max_percent: 1.0
                  fatal:
                    max_percent: 5.0
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
    [negative_percent](../../../reference/sensors/column/numeric-column-sensors.md#negative-percent)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"
            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for ClickHouse"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('month', CAST(analyzed_table."date_column" AS DATE)) AS time_period,
                toDateTime64(DATE_TRUNC('month', CAST(analyzed_table."date_column" AS DATE)), 3) AS time_period_utc
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "DB2"

        === "Sensor template for DB2"
            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for DB2"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(original_table."date_column" AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(original_table."date_column" AS DATE))) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
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
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM  AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "HANA"

        === "Sensor template for HANA"
            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
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
            FROM (
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for HANA"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                SERIES_ROUND(CAST(original_table."date_column" AS DATE), 'INTERVAL 1 MONTH', ROUND_DOWN) AS time_period,
                TO_TIMESTAMP(SERIES_ROUND(CAST(original_table."date_column" AS DATE), 'INTERVAL 1 MONTH', ROUND_DOWN)) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"
            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for MariaDB"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`date_column`, '%Y-%m-01 00:00:00'))) AS time_period_utc
            FROM `<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
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
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                TRUNC(CAST(original_table."date_column" AS DATE), 'MONTH') AS time_period,
                CAST(TRUNC(CAST(original_table."date_column" AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"
            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT({{ lib.render_target_column('analyzed_table') }}), 0.0)
                AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
            FROM(
                SELECT
                    original_table.*
                    {{- lib.render_data_grouping_projections('original_table') }}
                    {{- lib.render_time_dimension_projection('original_table') }}
                FROM {{ lib.render_target_table() }} original_table
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for QuestDB"
            ```sql
            SELECT
                COALESCE(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column"), 0.0)
                AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                CAST(DATE_TRUNC('month', original_table."date_column") AS DATE) AS time_period,
                CAST((CAST(DATE_TRUNC('month', original_table."date_column") AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS time_period_utc
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(analyzed_table.[target_column])
                END AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
                CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state], DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0)
            ORDER BY level_1, level_2DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)
            
                
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"
            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
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
        === "Rendered SQL for Teradata"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                TRUNC(CAST(analyzed_table."date_column" AS DATE), 'MM') AS time_period,
                CAST(TRUNC(CAST(analyzed_table."date_column" AS DATE), 'MM') AS TIMESTAMP) AS time_period_utc
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
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
            ) analyzed_table
            {{- lib.render_where_clause() -}}
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" < 0 THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
            
                            analyzed_table.grouping_level_1,
            
                            analyzed_table.grouping_level_2
            ,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                original_table."country" AS grouping_level_1,
                original_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    
___



## What's next
- Learn how to [configure data quality checks](../../../dqo-concepts/configuring-data-quality-checks-and-rules.md) in DQOps
- Look at the examples of [running data quality checks](../../../dqo-concepts/running-data-quality-checks.md), targeting tables and columns
