# text length above max length percent data quality checks

This check finds texts that are longer than the maximum accepted text length.
 It measures the percentage of texts that are too long and raises a data quality issue when too many invalid texts are found.


___
The **text length above max length percent** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile text length above max length percent


**Check description**

The check measures the percentage of text values in the column that is above the length defined by the user as a parameter.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`profile_text_length_above_max_length_percent`</span>|[text](../../../categories-of-data-quality-checks/how-to-detect-data-quality-issues-in-text-fields.md)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)| |Reasonableness|[*text_length_above_max_length_percent*](../../../reference/sensors/column/text-column-sensors.md#text-length-above-max-length-percent)|[*max_percent*](../../../reference/rules/Comparison.md#max-percent)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the profile text length above max length percent data quality check.

??? example "Managing profile text length above max length percent check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=profile_text_length_above_max_length_percent --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_text_length_above_max_length_percent --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_text_length_above_max_length_percent --enable-warning
                            -Wmax_percent=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=profile_text_length_above_max_length_percent --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_text_length_above_max_length_percent --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_text_length_above_max_length_percent --enable-error
                            -Emax_percent=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *profile_text_length_above_max_length_percent* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=profile_text_length_above_max_length_percent
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_text_length_above_max_length_percent
        ```

        You can also run this check on all tables (and columns)  on which the *profile_text_length_above_max_length_percent* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=profile_text_length_above_max_length_percent
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-17"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      profiling_checks:
        text:
          profile_text_length_above_max_length_percent:
            parameters:
              max_length: 100
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
    [text_length_above_max_length_percent](../../../reference/sensors/column/text-column-sensors.md#text-length-above-max-length-percent)
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
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM  AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table."target_column" AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LEN(analyzed_table.[target_column]) > 100
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(analyzed_table.[target_column])
                END AS actual_value,
                DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
                CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-13 29-34"
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
            text:
              profile_text_length_above_max_length_percent:
                parameters:
                  max_length: 100
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
    [text_length_above_max_length_percent](../../../reference/sensors/column/text-column-sensors.md#text-length-above-max-length-percent)
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM  AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
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
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table."target_column" AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LEN(analyzed_table.[target_column]) > 100
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(analyzed_table.[target_column])
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
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
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
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    
___


## daily text length above max length percent


**Check description**

The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_text_length_above_max_length_percent`</span>|[text](../../../categories-of-data-quality-checks/how-to-detect-data-quality-issues-in-text-fields.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|daily|Reasonableness|[*text_length_above_max_length_percent*](../../../reference/sensors/column/text-column-sensors.md#text-length-above-max-length-percent)|[*max_percent*](../../../reference/rules/Comparison.md#max-percent)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily text length above max length percent data quality check.

??? example "Managing daily text length above max length percent check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_text_length_above_max_length_percent --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_text_length_above_max_length_percent --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_text_length_above_max_length_percent --enable-warning
                            -Wmax_percent=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_text_length_above_max_length_percent --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_text_length_above_max_length_percent --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_text_length_above_max_length_percent --enable-error
                            -Emax_percent=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_text_length_above_max_length_percent* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_text_length_above_max_length_percent
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_text_length_above_max_length_percent
        ```

        You can also run this check on all tables (and columns)  on which the *daily_text_length_above_max_length_percent* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=daily_text_length_above_max_length_percent
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-18"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      monitoring_checks:
        daily:
          text:
            daily_text_length_above_max_length_percent:
              parameters:
                max_length: 100
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
    [text_length_above_max_length_percent](../../../reference/sensors/column/text-column-sensors.md#text-length-above-max-length-percent)
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
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
                END AS actual_value,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM  AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table."target_column" AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LEN(analyzed_table.[target_column]) > 100
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(analyzed_table.[target_column])
                END AS actual_value,
                CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
                CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-13 30-35"
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
              text:
                daily_text_length_above_max_length_percent:
                  parameters:
                    max_length: 100
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
    [text_length_above_max_length_percent](../../../reference/sensors/column/text-column-sensors.md#text-length-above-max-length-percent)
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM  AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
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
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table."target_column" AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LEN(analyzed_table.[target_column]) > 100
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(analyzed_table.[target_column])
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
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
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
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    
___


## monthly text length above max length percent


**Check description**

The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`monthly_text_length_above_max_length_percent`</span>|[text](../../../categories-of-data-quality-checks/how-to-detect-data-quality-issues-in-text-fields.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|monthly|Reasonableness|[*text_length_above_max_length_percent*](../../../reference/sensors/column/text-column-sensors.md#text-length-above-max-length-percent)|[*max_percent*](../../../reference/rules/Comparison.md#max-percent)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the monthly text length above max length percent data quality check.

??? example "Managing monthly text length above max length percent check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_text_length_above_max_length_percent --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_text_length_above_max_length_percent --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_text_length_above_max_length_percent --enable-warning
                            -Wmax_percent=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_text_length_above_max_length_percent --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_text_length_above_max_length_percent --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_text_length_above_max_length_percent --enable-error
                            -Emax_percent=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_text_length_above_max_length_percent* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_text_length_above_max_length_percent
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_text_length_above_max_length_percent
        ```

        You can also run this check on all tables (and columns)  on which the *monthly_text_length_above_max_length_percent* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=monthly_text_length_above_max_length_percent
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-18"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      monitoring_checks:
        monthly:
          text:
            monthly_text_length_above_max_length_percent:
              parameters:
                max_length: 100
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
    [text_length_above_max_length_percent](../../../reference/sensors/column/text-column-sensors.md#text-length-above-max-length-percent)
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
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM  AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table."target_column" AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LEN(analyzed_table.[target_column]) > 100
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(analyzed_table.[target_column])
                END AS actual_value,
                DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
                CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    ) AS DOUBLE) / COUNT(analyzed_table."target_column")
                END AS actual_value,
                time_period,
                time_period_utc
            FROM (
                SELECT
                    original_table.*,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-13 30-35"
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
              text:
                monthly_text_length_above_max_length_percent:
                  parameters:
                    max_length: 100
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
    [text_length_above_max_length_percent](../../../reference/sensors/column/text-column-sensors.md#text-length-above-max-length-percent)
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM  AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
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
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table."target_column" AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LEN(analyzed_table.[target_column]) > 100
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT_BIG(analyzed_table.[target_column])
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
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
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
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM "your_trino_catalog"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    
___


## daily partition text length above max length percent


**Check description**

The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_partition_text_length_above_max_length_percent`</span>|[text](../../../categories-of-data-quality-checks/how-to-detect-data-quality-issues-in-text-fields.md)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|daily|Reasonableness|[*text_length_above_max_length_percent*](../../../reference/sensors/column/text-column-sensors.md#text-length-above-max-length-percent)|[*max_percent*](../../../reference/rules/Comparison.md#max-percent)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily partition text length above max length percent data quality check.

??? example "Managing daily partition text length above max length percent check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_partition_text_length_above_max_length_percent --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_text_length_above_max_length_percent --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_text_length_above_max_length_percent --enable-warning
                            -Wmax_percent=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_partition_text_length_above_max_length_percent --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_text_length_above_max_length_percent --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_partition_text_length_above_max_length_percent --enable-error
                            -Emax_percent=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_partition_text_length_above_max_length_percent* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_partition_text_length_above_max_length_percent
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_partition_text_length_above_max_length_percent
        ```

        You can also run this check on all tables (and columns)  on which the *daily_partition_text_length_above_max_length_percent* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=daily_partition_text_length_above_max_length_percent
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="12-23"
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
          text:
            daily_partition_text_length_above_max_length_percent:
              parameters:
                max_length: 100
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
    [text_length_above_max_length_percent](../../../reference/sensors/column/text-column-sensors.md#text-length-above-max-length-percent)
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
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
                END AS actual_value,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM  AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
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
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
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
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table."target_column" AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LEN(analyzed_table.[target_column]) > 100
                                THEN 1
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
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
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

    ```yaml hl_lines="10-4 40-45"
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
              text:
                daily_partition_text_length_above_max_length_percent:
                  parameters:
                    max_length: 100
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
    [text_length_above_max_length_percent](../../../reference/sensors/column/text-column-sensors.md#text-length-above-max-length-percent)
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                CAST(analyzed_table.`date_column` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date_column` AS DATE)) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM  AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
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
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
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
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table."target_column" AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LEN(analyzed_table.[target_column]) > 100
                                THEN 1
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
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
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


## monthly partition text length above max length percent


**Check description**

The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`monthly_partition_text_length_above_max_length_percent`</span>|[text](../../../categories-of-data-quality-checks/how-to-detect-data-quality-issues-in-text-fields.md)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|monthly|Reasonableness|[*text_length_above_max_length_percent*](../../../reference/sensors/column/text-column-sensors.md#text-length-above-max-length-percent)|[*max_percent*](../../../reference/rules/Comparison.md#max-percent)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the monthly partition text length above max length percent data quality check.

??? example "Managing monthly partition text length above max length percent check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_partition_text_length_above_max_length_percent --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_partition_text_length_above_max_length_percent --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_partition_text_length_above_max_length_percent --enable-warning
                            -Wmax_percent=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_partition_text_length_above_max_length_percent --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_partition_text_length_above_max_length_percent --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_partition_text_length_above_max_length_percent --enable-error
                            -Emax_percent=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_partition_text_length_above_max_length_percent* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_partition_text_length_above_max_length_percent
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_partition_text_length_above_max_length_percent
        ```

        You can also run this check on all tables (and columns)  on which the *monthly_partition_text_length_above_max_length_percent* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=monthly_partition_text_length_above_max_length_percent
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="12-23"
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
          text:
            monthly_partition_text_length_above_max_length_percent:
              parameters:
                max_length: 100
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
    [text_length_above_max_length_percent](../../../reference/sensors/column/text-column-sensors.md#text-length-above-max-length-percent)
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
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
                END AS actual_value,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM  AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
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
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
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
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table."target_column" AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LEN(analyzed_table.[target_column]) > 100
                                THEN 1
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
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
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

    ```yaml hl_lines="10-4 40-45"
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
              text:
                monthly_partition_text_length_above_max_length_percent:
                  parameters:
                    max_length: 100
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
    [text_length_above_max_length_percent](../../../reference/sensors/column/text-column-sensors.md#text-length-above-max-length-percent)
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
                END AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table.`date_column` AS DATE))) AS time_period_utc
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
                END AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM  AS analyzed_table
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
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table.`target_column`) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ lib.render_target_column('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
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
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
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
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table."target_column" AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table."target_column")
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table.`target_column`) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LENGTH(CAST(analyzed_table.`target_column` AS STRING)) > 100
                                THEN 1
                            ELSE 0
                        END
                    )/ COUNT(analyzed_table.`target_column`)
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                CASE
                    WHEN COUNT_BIG(analyzed_table.[target_column]) = 0 THEN 0.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN LEN(analyzed_table.[target_column]) > 100
                                THEN 1
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
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro render_column_cast_to_string(analyzed_table_to_render) -%}
                {%- if (lib.target_column_data_type == 'STRING') -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- elif (lib.target_column_data_type == 'BIGNUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGDECIMAL') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'FLOAT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT64') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'NUMERIC') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'SMALLINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'INTEGER') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BIGINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TINYINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BYTEINT') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATE') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'DATETIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIME') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'TIMESTAMP') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- elif (lib.target_column_data_type == 'BOOLEAN') -%}
                    TRY_CAST({{ lib.render_target_column(analyzed_table_to_render) }} AS STRING)
                {%- else -%}
                    {{ lib.render_target_column(analyzed_table_to_render) }}
                {%- endif -%}
            {% endmacro -%}
            
            SELECT
                CASE
                    WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH({{ render_column_cast_to_string('analyzed_table')}}) > {{(parameters.max_length)}}
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
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                CASE
                    WHEN COUNT(analyzed_table."target_column") = 0 THEN 0.0
                    ELSE CAST(100.0 * SUM(
                        CASE
                            WHEN LENGTH(analyzed_table."target_column") > 100
                                THEN 1
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
