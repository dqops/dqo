# whitespace text percent data quality checks

Column-level check that ensures that there are no more than a maximum percent of whitespace texts in a monitored column.


___
The **whitespace text percent** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile whitespace text percent


**Check description**

Verifies that the percentage of whitespace strings in a column does not exceed the minimum accepted percentage.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|profile_whitespace_text_percent|profiling| |Completeness|[whitespace_text_percent](../../../reference/sensors/column/blanks-column-sensors.md#whitespace-text-percent)|[max_percent](../../../reference/rules/Comparison.md#max-percent)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the profile whitespace text percent data quality check.

??? example "Managing profile whitespace text percent check from DQOps shell"

    === "Activate check"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

        ```
        dqo> check activate -c=connection_name -ch=profile_whitespace_text_percent
        ```

    === "Run check on connection"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

        ```
        dqo> check run -c=connection_name -ch=profile_whitespace_text_percent
        ```

    === "Run check on table"

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_whitespace_text_percent
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
        blanks:
          profile_whitespace_text_percent:
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
    [whitespace_text_percent](../../../reference/sensors/column/blanks-column-sensors.md#whitespace-text-percent)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
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
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
                CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
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
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LEN({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] IS NOT NULL
                            AND LEN(analyzed_table.[target_column]) <> 0
                            AND TRIM(analyzed_table.[target_column]) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
                END AS actual_value,
                DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
                CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-15 27-32"
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
            blanks:
              profile_whitespace_text_percent:
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
    [whitespace_text_percent](../../../reference/sensors/column/blanks-column-sensors.md#whitespace-text-percent)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
                CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
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
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
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
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LEN({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] IS NOT NULL
                            AND LEN(analyzed_table.[target_column]) <> 0
                            AND TRIM(analyzed_table.[target_column]) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
                END AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
                CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state]
            ORDER BY level_1, level_2
                    , 
                
            
                
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
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
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    






___


## daily whitespace text percent


**Check description**

Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_whitespace_text_percent|monitoring|daily|Completeness|[whitespace_text_percent](../../../reference/sensors/column/blanks-column-sensors.md#whitespace-text-percent)|[max_percent](../../../reference/rules/Comparison.md#max-percent)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the daily whitespace text percent data quality check.

??? example "Managing daily whitespace text percent check from DQOps shell"

    === "Activate check"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

        ```
        dqo> check activate -c=connection_name -ch=daily_whitespace_text_percent
        ```

    === "Run check on connection"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

        ```
        dqo> check run -c=connection_name -ch=daily_whitespace_text_percent
        ```

    === "Run check on table"

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_whitespace_text_percent
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
          blanks:
            daily_whitespace_text_percent:
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
    [whitespace_text_percent](../../../reference/sensors/column/blanks-column-sensors.md#whitespace-text-percent)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
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
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS time_period,
                CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
                TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
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
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LEN({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] IS NOT NULL
                            AND LEN(analyzed_table.[target_column]) <> 0
                            AND TRIM(analyzed_table.[target_column]) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
                END AS actual_value,
                CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
                CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-15 28-33"
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
              blanks:
                daily_whitespace_text_percent:
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
    [whitespace_text_percent](../../../reference/sensors/column/blanks-column-sensors.md#whitespace-text-percent)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS time_period,
                CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
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
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
                TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
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
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LEN({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] IS NOT NULL
                            AND LEN(analyzed_table.[target_column]) <> 0
                            AND TRIM(analyzed_table.[target_column]) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
                END AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
                CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state]
            ORDER BY level_1, level_2
                    , 
                
            
                
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
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
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    






___


## monthly whitespace text percent


**Check description**

Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_whitespace_text_percent|monitoring|monthly|Completeness|[whitespace_text_percent](../../../reference/sensors/column/blanks-column-sensors.md#whitespace-text-percent)|[max_percent](../../../reference/rules/Comparison.md#max-percent)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the monthly whitespace text percent data quality check.

??? example "Managing monthly whitespace text percent check from DQOps shell"

    === "Activate check"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

        ```
        dqo> check activate -c=connection_name -ch=monthly_whitespace_text_percent
        ```

    === "Run check on connection"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

        ```
        dqo> check run -c=connection_name -ch=monthly_whitespace_text_percent
        ```

    === "Run check on table"

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_whitespace_text_percent
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
          blanks:
            monthly_whitespace_text_percent:
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
    [whitespace_text_percent](../../../reference/sensors/column/blanks-column-sensors.md#whitespace-text-percent)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
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
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
                CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
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
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LEN({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] IS NOT NULL
                            AND LEN(analyzed_table.[target_column]) <> 0
                            AND TRIM(analyzed_table.[target_column]) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
                END AS actual_value,
                DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
                CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-15 28-33"
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
              blanks:
                monthly_whitespace_text_percent:
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
    [whitespace_text_percent](../../../reference/sensors/column/blanks-column-sensors.md#whitespace-text-percent)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
                CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
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
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
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
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LEN({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] IS NOT NULL
                            AND LEN(analyzed_table.[target_column]) <> 0
                            AND TRIM(analyzed_table.[target_column]) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
                END AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
                CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state]
            ORDER BY level_1, level_2
                    , 
                
            
                
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
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
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    






___


## daily partition whitespace text percent


**Check description**

Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_partition_whitespace_text_percent|partitioned|daily|Completeness|[whitespace_text_percent](../../../reference/sensors/column/blanks-column-sensors.md#whitespace-text-percent)|[max_percent](../../../reference/rules/Comparison.md#max-percent)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the daily partition whitespace text percent data quality check.

??? example "Managing daily partition whitespace text percent check from DQOps shell"

    === "Activate check"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

        ```
        dqo> check activate -c=connection_name -ch=daily_partition_whitespace_text_percent
        ```

    === "Run check on connection"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

        ```
        dqo> check run -c=connection_name -ch=daily_partition_whitespace_text_percent
        ```

    === "Run check on table"

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_partition_whitespace_text_percent
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
          blanks:
            daily_partition_whitespace_text_percent:
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
    [whitespace_text_percent](../../../reference/sensors/column/blanks-column-sensors.md#whitespace-text-percent)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
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
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                TRUNC(CAST(original_table."date_column" AS DATE)) AS time_period,
                CAST(TRUNC(CAST(original_table."date_column" AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                CAST(original_table."date_column" AS date) AS time_period,
                CAST(CAST(original_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LEN({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] IS NOT NULL
                            AND LEN(analyzed_table.[target_column]) <> 0
                            AND TRIM(analyzed_table.[target_column]) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
                END AS actual_value,
                CAST(analyzed_table.[date_column] AS date) AS time_period,
                CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date)
            ORDER BY CAST(analyzed_table.[date_column] AS date)
            
                
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                CAST(original_table."date_column" AS date) AS time_period,
                CAST(CAST(original_table."date_column" AS date) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="10-20 38-43"
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
              blanks:
                daily_partition_whitespace_text_percent:
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
    [whitespace_text_percent](../../../reference/sensors/column/blanks-column-sensors.md#whitespace-text-percent)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                FROM "<target_schema>"."<target_table>" original_table) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
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
                FROM ""."<target_schema>"."<target_table>" original_table
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LEN({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] IS NOT NULL
                            AND LEN(analyzed_table.[target_column]) <> 0
                            AND TRIM(analyzed_table.[target_column]) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
                END AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                CAST(analyzed_table.[date_column] AS date) AS time_period,
                CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state], CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date)
            ORDER BY level_1, level_2CAST(analyzed_table.[date_column] AS date)
            
                
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
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
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    






___


## monthly partition whitespace text percent


**Check description**

Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_partition_whitespace_text_percent|partitioned|monthly|Completeness|[whitespace_text_percent](../../../reference/sensors/column/blanks-column-sensors.md#whitespace-text-percent)|[max_percent](../../../reference/rules/Comparison.md#max-percent)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the monthly partition whitespace text percent data quality check.

??? example "Managing monthly partition whitespace text percent check from DQOps shell"

    === "Activate check"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

        ```
        dqo> check activate -c=connection_name -ch=monthly_partition_whitespace_text_percent
        ```

    === "Run check on connection"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

        ```
        dqo> check run -c=connection_name -ch=monthly_partition_whitespace_text_percent
        ```

    === "Run check on table"

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_partition_whitespace_text_percent
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
          blanks:
            monthly_partition_whitespace_text_percent:
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
    [whitespace_text_percent](../../../reference/sensors/column/blanks-column-sensors.md#whitespace-text-percent)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
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
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                time_period,
                time_period_utc
            FROM(
                SELECT
                    original_table.*,
                TRUNC(CAST(original_table."date_column" AS DATE), 'MONTH') AS time_period,
                CAST(TRUNC(CAST(original_table."date_column" AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LEN({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] IS NOT NULL
                            AND LEN(analyzed_table.[target_column]) <> 0
                            AND TRIM(analyzed_table.[target_column]) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
                END AS actual_value,
                DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
                CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0)
            ORDER BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)
            
                
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(original_table."date_column" AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="10-20 38-43"
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
              blanks:
                monthly_partition_whitespace_text_percent:
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
    [whitespace_text_percent](../../../reference/sensors/column/blanks-column-sensors.md#whitespace-text-percent)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date_column` AS DATE), MONTH)) AS time_period_utc
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND analyzed_table.`target_column` <> ''
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_target_column('analyzed_table') }} <> ''
                            AND TRIM({{ lib.render_target_column('analyzed_table') }}) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                FROM "<target_schema>"."<target_table>" original_table) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
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
                FROM ""."<target_schema>"."<target_table>" original_table
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND LENGTH(analyzed_table."target_column") <> 0
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LENGTH({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`target_column` IS NOT NULL
                            AND LENGTH(analyzed_table.`target_column`) <> 0
                            AND TRIM(analyzed_table.`target_column`) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
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
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND LEN({{ lib.render_target_column('analyzed_table')}}) <> 0
                            AND TRIM({{ lib.render_target_column('analyzed_table')}}) = ''
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.[target_column] IS NOT NULL
                            AND LEN(analyzed_table.[target_column]) <> 0
                            AND TRIM(analyzed_table.[target_column]) = ''
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(*)
                END AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
                CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state], DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0)
            ORDER BY level_1, level_2DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)
            
                
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                            AND {{ lib.render_column_cast_to_string('analyzed_table')}} <> ''
                            AND TRIM({{ lib.render_column_cast_to_string('analyzed_table')}}) = ''
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN analyzed_table."target_column" IS NOT NULL
                            AND analyzed_table."target_column" <> ''
                            AND TRIM(analyzed_table."target_column") = ''
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(*)
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
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    






___

