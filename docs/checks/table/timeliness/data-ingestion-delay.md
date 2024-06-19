---
title: data ingestion delay data quality checks
---
# data ingestion delay data quality checks

A table-level check that calculates the time difference between the most recent row in the table and the most recent timestamp when the last row was loaded into the data warehouse or data lake.
 To identify the most recent row, the check finds the maximum value of the timestamp column that should contain the last modification timestamp from the source.
 The timestamp when the row was loaded is identified by the most recent (maximum) value a timestamp column that was filled by the data pipeline, for example: &quot;loaded_at&quot;, &quot;updated_at&quot;, etc.
 This check requires that the data pipeline is filling an extra column with the timestamp when the data loading job has been executed.
 The names of both columns used for comparison should be specified in the &quot;timestamp_columns&quot; configuration entry on the table.


___
The **data ingestion delay** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile data ingestion delay


**Check description**

Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`profile_data_ingestion_delay`</span>|[timeliness](../../../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)| |[Timeliness](../../../dqo-concepts/data-quality-dimensions.md#data-timeliness)|[*data_ingestion_delay*](../../../reference/sensors/table/timeliness-table-sensors.md#data-ingestion-delay)|[*max_days*](../../../reference/rules/Comparison.md#max-days)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the profile data ingestion delay data quality check.

??? example "Managing profile data ingestion delay check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=profile_data_ingestion_delay --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_data_ingestion_delay --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_data_ingestion_delay --enable-warning
                            -Wmax_days=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=profile_data_ingestion_delay --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_data_ingestion_delay --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_data_ingestion_delay --enable-error
                            -Emax_days=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *profile_data_ingestion_delay* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=profile_data_ingestion_delay
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_data_ingestion_delay
        ```

        You can also run this check on all tables  on which the *profile_data_ingestion_delay* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_data_ingestion_delay
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="8-16"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  profiling_checks:
    timeliness:
      profile_data_ingestion_delay:
        warning:
          max_days: 1.0
        error:
          max_days: 2.0
        fatal:
          max_days: 1.0
  columns:
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [data_ingestion_delay](../../../reference/sensors/table/timeliness-table-sensors.md#data-ingestion-delay)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMP_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    DAY
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATETIME_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
            FROM  AS analyzed_table
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    SECOND,
                    MAX(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                    MAX(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ) / 24.0 / 3600.0 AS actual_value
            FROM `<target_table>` AS analyzed_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                     MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
            
                {%- else -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                MAX(CAST(analyzed_table."col_inserted_at" AS DATE)) - MAX((CAST(analyzed_table."col_event_timestamp" AS DATE))) AS actual_value
            FROM (
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM (
                SELECT
                    original_table.*
                FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})),
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp")),
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at"))
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                    ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                DATEDIFF(SECOND,
                    MAX(analyzed_table.[col_event_timestamp]),
                    MAX(analyzed_table.[col_inserted_at])
                ) / 24.0 / 3600.0 AS actual_value
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value
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

    ```yaml hl_lines="8-4 33-38"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      default_grouping_name: group_by_country_and_state
      groupings:
        group_by_country_and_state:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      profiling_checks:
        timeliness:
          profile_data_ingestion_delay:
            warning:
              max_days: 1.0
            error:
              max_days: 2.0
            fatal:
              max_days: 1.0
      columns:
        col_event_timestamp:
          labels:
          - optional column that stores the timestamp when the event/transaction happened
        col_inserted_at:
          labels:
          - optional column that stores the timestamp when row was ingested
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [data_ingestion_delay](../../../reference/sensors/table/timeliness-table-sensors.md#data-ingestion-delay)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMP_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    DAY
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATETIME_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM  AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    SECOND,
                    MAX(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                    MAX(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                     MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
            
                {%- else -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                MAX(CAST(analyzed_table."col_inserted_at" AS DATE)) - MAX((CAST(analyzed_table."col_event_timestamp" AS DATE))) AS actual_value,
            
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
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            
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
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})),
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp")),
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at"))
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                    ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                DATEDIFF(SECOND,
                    MAX(analyzed_table.[col_event_timestamp]),
                    MAX(analyzed_table.[col_inserted_at])
                ) / 24.0 / 3600.0 AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state]
            ORDER BY level_1, level_2
                    , 
                
            
                
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            
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


## daily data ingestion delay


**Check description**

Daily calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_data_ingestion_delay`</span>|[timeliness](../../../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|daily|[Timeliness](../../../dqo-concepts/data-quality-dimensions.md#data-timeliness)|[*data_ingestion_delay*](../../../reference/sensors/table/timeliness-table-sensors.md#data-ingestion-delay)|[*max_days*](../../../reference/rules/Comparison.md#max-days)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily data ingestion delay data quality check.

??? example "Managing daily data ingestion delay check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=daily_data_ingestion_delay --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_data_ingestion_delay --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_data_ingestion_delay --enable-warning
                            -Wmax_days=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=daily_data_ingestion_delay --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_data_ingestion_delay --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_data_ingestion_delay --enable-error
                            -Emax_days=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_data_ingestion_delay* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_data_ingestion_delay
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_data_ingestion_delay
        ```

        You can also run this check on all tables  on which the *daily_data_ingestion_delay* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_data_ingestion_delay
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="8-17"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  monitoring_checks:
    daily:
      timeliness:
        daily_data_ingestion_delay:
          warning:
            max_days: 1.0
          error:
            max_days: 2.0
          fatal:
            max_days: 1.0
  columns:
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [data_ingestion_delay](../../../reference/sensors/table/timeliness-table-sensors.md#data-ingestion-delay)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMP_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    DAY
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATETIME_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
            FROM  AS analyzed_table
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    SECOND,
                    MAX(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                    MAX(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ) / 24.0 / 3600.0 AS actual_value
            FROM `<target_table>` AS analyzed_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                     MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
            
                {%- else -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                MAX(CAST(analyzed_table."col_inserted_at" AS DATE)) - MAX((CAST(analyzed_table."col_event_timestamp" AS DATE))) AS actual_value
            FROM (
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM (
                SELECT
                    original_table.*
                FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})),
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp")),
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at"))
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                    ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                DATEDIFF(SECOND,
                    MAX(analyzed_table.[col_event_timestamp]),
                    MAX(analyzed_table.[col_inserted_at])
                ) / 24.0 / 3600.0 AS actual_value
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value
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

    ```yaml hl_lines="8-4 34-39"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      default_grouping_name: group_by_country_and_state
      groupings:
        group_by_country_and_state:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      monitoring_checks:
        daily:
          timeliness:
            daily_data_ingestion_delay:
              warning:
                max_days: 1.0
              error:
                max_days: 2.0
              fatal:
                max_days: 1.0
      columns:
        col_event_timestamp:
          labels:
          - optional column that stores the timestamp when the event/transaction happened
        col_inserted_at:
          labels:
          - optional column that stores the timestamp when row was ingested
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [data_ingestion_delay](../../../reference/sensors/table/timeliness-table-sensors.md#data-ingestion-delay)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMP_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    DAY
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATETIME_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM  AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    SECOND,
                    MAX(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                    MAX(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                     MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
            
                {%- else -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                MAX(CAST(analyzed_table."col_inserted_at" AS DATE)) - MAX((CAST(analyzed_table."col_event_timestamp" AS DATE))) AS actual_value,
            
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
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            
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
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})),
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp")),
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at"))
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                    ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                DATEDIFF(SECOND,
                    MAX(analyzed_table.[col_event_timestamp]),
                    MAX(analyzed_table.[col_inserted_at])
                ) / 24.0 / 3600.0 AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state]
            ORDER BY level_1, level_2
                    , 
                
            
                
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            
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


## monthly data ingestion delay


**Check description**

Monthly monitoring calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`monthly_data_ingestion_delay`</span>|[timeliness](../../../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|monthly|[Timeliness](../../../dqo-concepts/data-quality-dimensions.md#data-timeliness)|[*data_ingestion_delay*](../../../reference/sensors/table/timeliness-table-sensors.md#data-ingestion-delay)|[*max_days*](../../../reference/rules/Comparison.md#max-days)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the monthly data ingestion delay data quality check.

??? example "Managing monthly data ingestion delay check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=monthly_data_ingestion_delay --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_data_ingestion_delay --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_data_ingestion_delay --enable-warning
                            -Wmax_days=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=monthly_data_ingestion_delay --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_data_ingestion_delay --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_data_ingestion_delay --enable-error
                            -Emax_days=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_data_ingestion_delay* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_data_ingestion_delay
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_data_ingestion_delay
        ```

        You can also run this check on all tables  on which the *monthly_data_ingestion_delay* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_data_ingestion_delay
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="8-17"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  monitoring_checks:
    monthly:
      timeliness:
        monthly_data_ingestion_delay:
          warning:
            max_days: 1.0
          error:
            max_days: 2.0
          fatal:
            max_days: 1.0
  columns:
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [data_ingestion_delay](../../../reference/sensors/table/timeliness-table-sensors.md#data-ingestion-delay)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMP_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    DAY
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATETIME_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
            FROM  AS analyzed_table
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    SECOND,
                    MAX(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                    MAX(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ) / 24.0 / 3600.0 AS actual_value
            FROM `<target_table>` AS analyzed_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                     MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
            
                {%- else -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                MAX(CAST(analyzed_table."col_inserted_at" AS DATE)) - MAX((CAST(analyzed_table."col_event_timestamp" AS DATE))) AS actual_value
            FROM (
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM (
                SELECT
                    original_table.*
                FROM "your_trino_database"."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})),
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp")),
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at"))
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                    ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                DATEDIFF(SECOND,
                    MAX(analyzed_table.[col_event_timestamp]),
                    MAX(analyzed_table.[col_inserted_at])
                ) / 24.0 / 3600.0 AS actual_value
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value
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

    ```yaml hl_lines="8-4 34-39"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      default_grouping_name: group_by_country_and_state
      groupings:
        group_by_country_and_state:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      monitoring_checks:
        monthly:
          timeliness:
            monthly_data_ingestion_delay:
              warning:
                max_days: 1.0
              error:
                max_days: 2.0
              fatal:
                max_days: 1.0
      columns:
        col_event_timestamp:
          labels:
          - optional column that stores the timestamp when the event/transaction happened
        col_inserted_at:
          labels:
          - optional column that stores the timestamp when row was ingested
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [data_ingestion_delay](../../../reference/sensors/table/timeliness-table-sensors.md#data-ingestion-delay)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMP_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    DAY
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATETIME_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
                analyzed_table.`country` AS grouping_level_1,
                analyzed_table.`state` AS grouping_level_2
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2
            FROM  AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2
            ORDER BY grouping_level_1, grouping_level_2
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    SECOND,
                    MAX(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                    MAX(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                     MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
            
                {%- else -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                MAX(CAST(analyzed_table."col_inserted_at" AS DATE)) - MAX((CAST(analyzed_table."col_event_timestamp" AS DATE))) AS actual_value,
            
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
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            
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
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})),
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp")),
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at"))
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                    ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                DATEDIFF(SECOND,
                    MAX(analyzed_table.[col_event_timestamp]),
                    MAX(analyzed_table.[col_inserted_at])
                ) / 24.0 / 3600.0 AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state]
            ORDER BY level_1, level_2
                    , 
                
            
                
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            
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


## daily partition data ingestion delay


**Check description**

Daily partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_partition_data_ingestion_delay`</span>|[timeliness](../../../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|daily|[Timeliness](../../../dqo-concepts/data-quality-dimensions.md#data-timeliness)|[*data_ingestion_delay*](../../../reference/sensors/table/timeliness-table-sensors.md#data-ingestion-delay)|[*max_days*](../../../reference/rules/Comparison.md#max-days)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily partition data ingestion delay data quality check.

??? example "Managing daily partition data ingestion delay check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=daily_partition_data_ingestion_delay --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_partition_data_ingestion_delay --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_partition_data_ingestion_delay --enable-warning
                            -Wmax_days=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=daily_partition_data_ingestion_delay --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_partition_data_ingestion_delay --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_partition_data_ingestion_delay --enable-error
                            -Emax_days=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_partition_data_ingestion_delay* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_partition_data_ingestion_delay
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_partition_data_ingestion_delay
        ```

        You can also run this check on all tables  on which the *daily_partition_data_ingestion_delay* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_partition_data_ingestion_delay
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="12-21"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
    partition_by_column: date_column
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  partitioned_checks:
    daily:
      timeliness:
        daily_partition_data_ingestion_delay:
          warning:
            max_days: 1.0
          error:
            max_days: 2.0
          fatal:
            max_days: 1.0
  columns:
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested
    date_column:
      labels:
      - "date or datetime column used as a daily or monthly partitioning key, dates\
        \ (and times) are truncated to a day or a month by the sensor's query for\
        \ partitioned checks"

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [data_ingestion_delay](../../../reference/sensors/table/timeliness-table-sensors.md#data-ingestion-delay)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMP_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    DAY
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATETIME_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    SECOND,
                    MAX(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                    MAX(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                     MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
            
                {%- else -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                MAX(CAST(analyzed_table."col_inserted_at" AS DATE)) - MAX((CAST(analyzed_table."col_event_timestamp" AS DATE))) AS actual_value,
                time_period,
                time_period_utc
            FROM (
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})),
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp")),
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at"))
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                    ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                DATEDIFF(SECOND,
                    MAX(analyzed_table.[col_event_timestamp]),
                    MAX(analyzed_table.[col_inserted_at])
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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

    ```yaml hl_lines="12-4 43-48"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
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
      partitioned_checks:
        daily:
          timeliness:
            daily_partition_data_ingestion_delay:
              warning:
                max_days: 1.0
              error:
                max_days: 2.0
              fatal:
                max_days: 1.0
      columns:
        col_event_timestamp:
          labels:
          - optional column that stores the timestamp when the event/transaction happened
        col_inserted_at:
          labels:
          - optional column that stores the timestamp when row was ingested
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
    [data_ingestion_delay](../../../reference/sensors/table/timeliness-table-sensors.md#data-ingestion-delay)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMP_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    DAY
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATETIME_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    SECOND,
                    MAX(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                    MAX(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                     MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
            
                {%- else -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                MAX(CAST(analyzed_table."col_inserted_at" AS DATE)) - MAX((CAST(analyzed_table."col_event_timestamp" AS DATE))) AS actual_value,
            
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})),
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp")),
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at"))
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                    ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                DATEDIFF(SECOND,
                    MAX(analyzed_table.[col_event_timestamp]),
                    MAX(analyzed_table.[col_inserted_at])
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            
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


## monthly partition data ingestion delay


**Check description**

Monthly partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`monthly_partition_data_ingestion_delay`</span>|[timeliness](../../../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|monthly|[Timeliness](../../../dqo-concepts/data-quality-dimensions.md#data-timeliness)|[*data_ingestion_delay*](../../../reference/sensors/table/timeliness-table-sensors.md#data-ingestion-delay)|[*max_days*](../../../reference/rules/Comparison.md#max-days)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the monthly partition data ingestion delay data quality check.

??? example "Managing monthly partition data ingestion delay check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=monthly_partition_data_ingestion_delay --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_partition_data_ingestion_delay --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_partition_data_ingestion_delay --enable-warning
                            -Wmax_days=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=monthly_partition_data_ingestion_delay --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_partition_data_ingestion_delay --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_partition_data_ingestion_delay --enable-error
                            -Emax_days=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_partition_data_ingestion_delay* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_partition_data_ingestion_delay
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_partition_data_ingestion_delay
        ```

        You can also run this check on all tables  on which the *monthly_partition_data_ingestion_delay* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_partition_data_ingestion_delay
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="12-21"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
    partition_by_column: date_column
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  partitioned_checks:
    monthly:
      timeliness:
        monthly_partition_data_ingestion_delay:
          warning:
            max_days: 1.0
          error:
            max_days: 2.0
          fatal:
            max_days: 1.0
  columns:
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested
    date_column:
      labels:
      - "date or datetime column used as a daily or monthly partitioning key, dates\
        \ (and times) are truncated to a day or a month by the sensor's query for\
        \ partitioned checks"

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [data_ingestion_delay](../../../reference/sensors/table/timeliness-table-sensors.md#data-ingestion-delay)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMP_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    DAY
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATETIME_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    SECOND,
                    MAX(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                    MAX(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                     MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
            
                {%- else -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                MAX(CAST(analyzed_table."col_inserted_at" AS DATE)) - MAX((CAST(analyzed_table."col_event_timestamp" AS DATE))) AS actual_value,
                time_period,
                time_period_utc
            FROM (
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})),
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp")),
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at"))
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                    ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                DATEDIFF(SECOND,
                    MAX(analyzed_table.[col_event_timestamp]),
                    MAX(analyzed_table.[col_inserted_at])
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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

    ```yaml hl_lines="12-4 43-48"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
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
      partitioned_checks:
        monthly:
          timeliness:
            monthly_partition_data_ingestion_delay:
              warning:
                max_days: 1.0
              error:
                max_days: 2.0
              fatal:
                max_days: 1.0
      columns:
        col_event_timestamp:
          labels:
          - optional column that stores the timestamp when the event/transaction happened
        col_inserted_at:
          labels:
          - optional column that stores the timestamp when row was ingested
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
    [data_ingestion_delay](../../../reference/sensors/table/timeliness-table-sensors.md#data-ingestion-delay)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMP_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    DAY
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATETIME_DIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMP_DIFF(
                    MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ),
                    MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    SECOND,
                    MAX(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                    MAX(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                     MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
            
                {%- else -%}
                 MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)) - MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                MAX(CAST(analyzed_table."col_inserted_at" AS DATE)) - MAX((CAST(analyzed_table."col_event_timestamp" AS DATE))) AS actual_value,
            
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP) - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    MAX((analyzed_table."col_inserted_at")::TIMESTAMP) - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})),
                    MAX(TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp")),
                    MAX(TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at"))
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                (
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                    ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- else -%}
                DATEDIFF(SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                DATEDIFF(SECOND,
                    MAX(analyzed_table.[col_event_timestamp]),
                    MAX(analyzed_table.[col_inserted_at])
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_ingestion_event_max_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
                and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_ingestion_event_max_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    MAX(
                        TRY_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    )
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
            
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
